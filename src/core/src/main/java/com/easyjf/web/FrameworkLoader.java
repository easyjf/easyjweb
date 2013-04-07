/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyjf.web;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.velocity.app.Velocity;

import com.easyjf.container.BeanDefinition;
import com.easyjf.container.Container;
import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Bean;
import com.easyjf.container.impl.BeanDefinitionImpl;
import com.easyjf.container.impl.DefaultContainer;
import com.easyjf.container.impl.WebContextContainer;
import com.easyjf.util.I18n;
import com.easyjf.util.ResolverUtil;
import com.easyjf.util.StringUtils;
import com.easyjf.web.ajax.AjaxServiceContainer;
import com.easyjf.web.ajax.AjaxUtil;
import com.easyjf.web.config.BeanConfigReader;
import com.easyjf.web.config.ConfigureResourceLoader;
import com.easyjf.web.config.DefaultWebConfig;
import com.easyjf.web.config.FileResourceLoader;
import com.easyjf.web.config.ServletContextResourceLoader;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.core.RequestScope;
import com.easyjf.web.core.SessionScope;
import com.easyjf.web.exception.FrameworkException;

/**
 * EasyJWeb加载器，用来加载EasyJWeb的各种配置文件，缺省配置信息等。 该类由ActionServlet调用并起动
 * 
 * @author 大峡
 * 
 */
public class FrameworkLoader implements Serializable {

	private static final long serialVersionUID = 1223936880827975513L;

	public final static String DefaultActionPackages = "defaultActionPackages";
	/**
	 * EasyJWeb配置文件s
	 */
	private String[] configures;

	private WebConfig webConfig = new DefaultWebConfig();

	private Container container;

	private boolean haveInitEasyJWeb = false;// 标注是否已经初始化EasyjWeb

	private ServletContext servletContext;// 用于servletContext上下文

	private static final Logger logger = Logger
			.getLogger(FrameworkLoader.class);

	private ConfigureResourceLoader resourceLoader;

	public void setResourceLoader(ConfigureResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
		// 赋值 将根目录赋值给全局变量Globals.APP_BASE_DIR
		if (servletContext != null)
			Globals.APP_BASE_DIR = servletContext.getRealPath("/");
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

	public FrameworkLoader(String[] configures) {
		this.configures = configures;
	}

	public void initEasyJWeb() {
		/*
		 * if (haveInitEasyJWeb) return;
		 */
		logger.info(I18n.getLocaleMessage("core.execute.EasyJWeb.initialization.applications"));
		if (resourceLoader == null) {
			if (servletContext != null)
				resourceLoader = new ServletContextResourceLoader(
						servletContext);
			else
				resourceLoader = new FileResourceLoader();
		}
		initContainer();
		FrameworkEngine.setWebConfig(webConfig);// 初始化框架工具
		FrameworkEngine.setContainer(container);// 在引擎中安装容器
		AjaxUtil.setServiceContainer(new AjaxServiceContainer(container));// 初始化Ajax容器服务
		initTemplate(); // 初始化模版
		invokeApps();// 在应用启动的时候启动一些配置好的应用
		haveInitEasyJWeb = true;
		logger.info(I18n.getLocaleMessage("core.EasyJWeb.initialized"));
	}

	/**
	 * @deprecated
	 * @param configures
	 * @return
	 */
	protected java.io.InputStream[] getConfigures(String[] configures) {
		InputStream[] is = null;
		String[] s = configures;
		if (s == null || s.length < 1) {			
			s = StringUtils.tokenizeToStringArray(Globals.CONFIG_FILE, ",");
		}
		is = new InputStream[s.length + 1];
		for (int i = 0; i < s.length; i++) {
			is[i] = servletContext.getResourceAsStream(s[i]);
		}
		// 获取ajax支持 主要参考dwr
		is[s.length] = this.getClass().getResourceAsStream(
				"/com/easyjf/web/easyjf-web.xml");
		return is;
	}

	private String[] getConfigures() {
		List<String> cs=new java.util.ArrayList<String>();
		if(this.configures!=null)cs.addAll(java.util.Arrays.asList(this.configures));
		else{
			if(new java.io.File(servletContext.getRealPath(Globals.CONFIG_FILE)).exists())
			cs.add(Globals.CONFIG_FILE);
		}
		cs.add("classpath:/com/easyjf/web/easyjf-web.xml");
		//尝试加载tools配置文件
		if(this.getClass().getResource("/com/easyjf/web/tools/easyjf-web.xml")!=null)
		{
			cs.add("classpath:/com/easyjf/web/tools/easyjf-web.xml");
		}	
		return cs.toArray(new String[cs.size()]);
	}

	protected void initContainer() {
		// 读取配置文件并赋值给webConfig
		webConfig.setResourceLoader(resourceLoader);
		webConfig.setConfigures(getConfigures());
		// 根据配置文件进行初始化
		webConfig.init();
		DefaultContainer c = (servletContext == null ? new DefaultContainer()
				: new WebContextContainer(servletContext));
		c.registerBeanDefinitions(webConfig.getBeanDefinitions());
		loadDefaultAction(c);// 根据包的配置参数自动加载bean信息
		((DefaultWebConfig) webConfig).loadAlias();// 加载并处理别名
		c.registerScope("request", new RequestScope(c));
		c.registerScope("session", new SessionScope(c));
//		System.out.println(I18n.getLocaleMessage("core.started.containers.initialization"));
		c.refresh();
		this.container = c;
		// 此处用来把spring的Web应用上下文保存到指定的ServletContext属性中，此处需要进一步的修改
		/*
		 * java.util.Iterator it = c.getContainers().values().iterator(); while
		 * (it.hasNext()) { Object obj = it.next(); if (obj instanceof
		 * SpringContainer && servletContext != null) { ((SpringContainer)
		 * obj).registerWebContext(servletContext); } }
		 */
	}

	protected void loadDefaultAction(DefaultContainer container) {
		String[] packages1 = this.webConfig.getDefaultActionPackages();
		String[] packages2 = null;
		if (this.servletContext != null) {
			String dp = servletContext.getInitParameter(DefaultActionPackages);
			if (dp != null)
				packages2 = StringUtils.tokenizeToStringArray(dp, ",");
		}
		loadBeanFromPackages(container, packages1);
		loadBeanFromPackages(container, packages2);
		loadBeanFromPackages(container, new String[]{Globals.DEFAULT_ACTTION_PACKAGE});
		loadActionFromPackages(container, packages1);
		loadActionFromPackages(container, packages2);
		loadActionFromPackages(container, new String[]{Globals.DEFAULT_ACTTION_PACKAGE});
	}

	private void loadBeanFromPackages(DefaultContainer container,
			String[] packages) {
		if (packages != null && packages.length > 0) {
			java.util.Map newModules = new java.util.HashMap();
			List beanDefinitions = ((DefaultWebConfig) webConfig)
					.getBeanDefinitions();
			for (int i = 0; i < packages.length; i++) {
				logger.info(I18n.getLocaleMessage("core.in") + packages[i] + I18n.getLocaleMessage("core.view.package.and.load.the.default.Bean"));
				ResolverUtil<Bean> r = new ResolverUtil<Bean>();
				r.findAnnotated(Bean.class, packages[i]);
				java.util.Iterator it = r.getClasses().iterator();
				while (it.hasNext()) {
					Class clz = (Class) it.next();
					if (Modifier.isAbstract(clz.getModifiers()))
						continue;
					Bean ac = (Bean) clz.getAnnotation(Bean.class);
					String name = ac.name();
					if ("".equals(name)) {
						name = clz.getSimpleName().substring(0, 1)
								.toLowerCase()
								+ clz.getSimpleName().substring(1);
						if (name.endsWith("Impl"))
							name = name.substring(0, name.length()
									- "Impl".length());
					}
					// 如果容器中已经注册过这个类，或者已经具有同名的Bean，则不进行注册
					if (container.getBeanDefinition(clz)==null && !this.findBeanDefinitions(beanDefinitions, name)) {
						logger.info(I18n.getLocaleMessage("core.automatic.load.Bean") + clz.getName() + I18n.getLocaleMessage("core.beanName")
								+ name);
						BeanDefinitionImpl definition = new BeanDefinitionImpl(
								name, clz, ac.scope());
						BeanConfigReader.handleAutoInject(definition, ac
								.inject());// 处理自动注入
						beanDefinitions.add(definition);
						container.registerBeanDefinition(name, definition);
					}
				}
			}

		}
	}

	private boolean findBeanDefinitions(List definitions, String name) {
		for (int i = 0; i < definitions.size(); i++) {
			BeanDefinition definition = (BeanDefinition) definitions.get(i);
			if (definition.getBeanName().equals(name))
				return true;
		}
		return false;
	}

	private void loadActionFromPackages(DefaultContainer container,
			String[] packages) {
		if (packages != null && packages.length > 0) {
			java.util.Map newModules = new java.util.HashMap();
			for (int i = 0; i < packages.length; i++) {
				logger.info(I18n.getLocaleMessage("core.in") + packages[i] + I18n.getLocaleMessage("core.view.package.and.load.the.default.Action"));
				ResolverUtil<IWebAction> r = new ResolverUtil<IWebAction>();
				r.findImplementations(IWebAction.class, packages[i]);
				java.util.Iterator it = r.getClasses().iterator();
				while (it.hasNext()) {
					Class clz = (Class) it.next();
					if (Modifier.isAbstract(clz.getModifiers()))
						continue;
					String name = clz.getSimpleName().substring(0, 1)
							.toLowerCase()
							+ clz.getSimpleName().substring(1);
					if (name.endsWith("Action"))
						name = name.substring(0, name.length()
								- "Action".length());
					Action ac = (Action) clz.getAnnotation(Action.class);
					if (ac != null && !"".equals(ac.path()))
						name = ac.path();
					if (name.charAt(0) != '/')
						name = "/" + name;
					// 如果容器中已经注册过这个类，或者已经具有同path的module，则不进行注册	
					if (container.getBeanDefinition(clz) == null
							&& this.webConfig.getModules().get(name) == null) {
						logger.info(I18n.getLocaleMessage("core.automatic.load.Action")+" " + clz.getName()
								+ I18n.getLocaleMessage("core.path") + name);
						Module mc = new Module();
						mc.setAction(clz.getName());
						mc.setPath(name);						
						if (ac != null) {
							mc.setViews(ac.view());
							mc.setScope(ac.scope());
							mc.setAlias(ac.alias());
							if (!"".equals(ac.inject()))
								mc.setInject(ac.inject());
							mc.setAutoToken(ac.autoToken());
							mc.setMessageResource(ac.messageResource());
							mc.setValidate(ac.validate());
						}
						newModules.put(mc.getPath(), mc);
					}
				}
			}
			this.getWebConfig().getModules().putAll(newModules);
			List newBeans = BeanConfigReader.parseBeansFromModules(newModules);

			// 把Bean的注册信息添加到WebCofig中
			((DefaultWebConfig) webConfig).getBeanDefinitions()
					.addAll(newBeans);
			// 把bean信息注册到容器中
			container.registerBeanDefinitions(newBeans);
		}
	}

	/**
	 * 初始化模板 不支持velocity的配置读入，有待改进
	 * 
	 * @param config
	 * @throws ServletException
	 */
	protected void initTemplate() {
		Properties p = new Properties();
		p.setProperty("resource.loader", "file,class");
		p.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogSystem");
		p
				.setProperty("class.resource.loader.class",
						"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		if (!StringUtils.hasLength(webConfig.getTemplateBasePath()))
			webConfig.setTemplateBasePath(Globals.DEFAULT_TEMPLATE_PATH);
		String realTemplatePath = webConfig.getTemplateBasePath();
		File file = new File(webConfig.getTemplateBasePath());
		if (!file.exists() && servletContext != null)
			realTemplatePath = servletContext.getRealPath(webConfig
					.getTemplateBasePath());
		p.setProperty("file.resource.loader.path", realTemplatePath);
		try {
			Velocity.init(p);
		} catch (Exception e) {
			logger.error(I18n.getLocaleMessage("core.initialization.template.error") + e);
			throw new com.easyjf.web.exception.FrameworkException(I18n.getLocaleMessage("core.initialization.template.error"), e);
		}
	}

	/**
	 * 在应用启动的时候启动一些配置好的应用；比如后台的监控线程等;
	 */
	protected void invokeApps() {
		List apps = webConfig.getInitApps();
		for (int i = 0; i < apps.size(); i++) {
			try {
				Map app = (Map) apps.get(i);
				Method init = (Method) app.get("init-method");
				if (init != null) {
					init.invoke(app.get("classname"), new Object[] {});
					logger.debug("app " + app.get("classname") + "has started");
				}
			} catch (Exception e) {
				throw new FrameworkException(I18n.getLocaleMessage("core.initialization.procedures.for.exception"), e);
			}
		}
	}

	// 停止启动的应用；
	public void destroyApps() {
		List apps = webConfig.getInitApps();
		for (int i = 0; i < apps.size(); i++) {
			try {
				Map app = (Map) apps.get(i);
				Method des = (Method) app.get("destroy-method");
				if (des != null) {
					des.invoke(app.get("classname"), new Object[] {});
				}
			} catch (Exception e) {
				throw new FrameworkException(I18n.getLocaleMessage("core.initialization.end.for.exception"), e);
			}
		}
	}

	public Container getContainer() {
		return container;
	}

}
