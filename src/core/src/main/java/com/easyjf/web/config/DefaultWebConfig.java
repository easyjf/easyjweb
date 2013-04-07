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
package com.easyjf.web.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.container.BeanDefinition;
import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.web.Globals;
import com.easyjf.web.Module;
import com.easyjf.web.WebConfig;
import com.easyjf.web.interceptor.ExceptionInterceptor;

/**
 * 
 * <p>
 * Title:配置信息
 * </p>
 * <p>
 * Description:调用配置信息处理类,处理并存放easyjf-web.xml中的配置信息
 * </p>
 * <p>
 * Copyright:Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */
public class DefaultWebConfig implements WebConfig {

	// 所有模块
	private final Map modules = new HashMap();

	private final Map moduleAlias = new HashMap();
	// 所有定义的表单
	private final Map forms = new HashMap();

	// 所有页面
	private final Map pages = new HashMap();

	// 所有初始化程序
	private final List initApps = new ArrayList();

	// 所有全局拦截器
	private final List interceptors = new ArrayList();

	// 所有错误处理程序
	private final List errorHandler = new java.util.ArrayList();

	// Bean配置信息
	private List beanDefinitions = new ArrayList();

	private List configManagers = new ArrayList();

	// 模板路径
	private String templateBasePath;

	// 是否调试
	private boolean debug = false;

	// 默认为5M
	private int maxUploadFileSize = 1024 * 1024 * 5;

	// 缓存30K
	private int uploadSizeThreshold = 1024 * 30;

	// 配置信息类二进制数据
	private String[] configures;

	// 最多直接跳转Action次数
	private Integer maxDirectJumpToActionTimes = 3;

	private java.util.List importsConfigures = new java.util.ArrayList();

	private ConfigureResourceLoader resourceLoader;

	private String[] defaultActionPackages = {};

	private boolean permissionVerify;
	public void setResourceLoader(ConfigureResourceLoader loader) {
		this.resourceLoader = loader;
	}

	private static final Logger logger = Logger
			.getLogger(DefaultWebConfig.class);

	public DefaultWebConfig() {
		configManagers.add(com.easyjf.web.ajax.AjaxConfigManager.getInstance());
	}

	public synchronized  void init() {
		// 执行配置信息初始化
		// 清空配置信息
		modules.clear();
		forms.clear();
		pages.clear();
		initApps.clear();
		interceptors.clear();
		errorHandler.clear();
		beanDefinitions.clear();
		templateBasePath = "";
		try {
			logger.info(I18n.getLocaleMessage("core.web.system.initialization"));
			parseConfigures(configures);
			while (importsConfigures.size() > 0) {
				String[] s = new String[importsConfigures.size()];
				importsConfigures.toArray(s);
				importsConfigures.clear();
				parseConfigures(s);
			}
			// 从bean定义信息中读取errorHandler模块配置信息
			for (int i = 0; i < beanDefinitions.size(); i++) {
				BeanDefinition bd = (BeanDefinition) beanDefinitions.get(i);
				if (ExceptionInterceptor.class.isAssignableFrom(bd
						.getBeanClass()))
					errorHandler.add(bd.getBeanClass());
			}
			// 把Module的信息转换成Bean信息，添加到容器配置信息中
			beanDefinitions.addAll(BeanConfigReader.parseBeansFromModules(modules));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseConfigures(String[] configures) throws Exception {
		// 可读取多个配置文件
		for (int k = 0; k < configures.length; k++) {
			logger.info(I18n.getLocaleMessage("core.web.coading.configuration.file")+configures[k]);
			parseConfig(resourceLoader.loadResource(configures[k]));
		}
	}

	public void parseConfig(java.io.InputStream in) throws Exception {
		java.io.BufferedInputStream is = new java.io.BufferedInputStream(in);
		IConfigFactory icf = new XMLConfigFactory(is);// (IConfigFactory)
		// //
		// Class.forName(
		// Globals.CONFIG_FACTORY_CLASS).newInstance();
		// 高用配置工厂方法
		icf.initForm(forms);// 表单
		icf.initModule(modules);// 模块
		icf.initPage(pages);// 页面模块配置
		Map map = new HashMap();
		map = icf.initOther();// 其它配置信息,除了form，module及page以外的其它配置信息
		if (map != null) {
			// 若设置了模版根目录则使用模版根目录路径
			if (map.get("TemplateBasePath") != null)
				templateBasePath = (String) map.get("TemplateBasePath");
			// 是否调试模式
			if (map.get(IConfigFactory.DEBUG) != null) {
				debug = Boolean.valueOf((String) map.get(IConfigFactory.DEBUG))
						.booleanValue();
			}
			// 文件上传大小
			if (map.get(IConfigFactory.MaxUploadFileSize) != null) {
				int maxSize = Integer.valueOf(
						(String) map.get(IConfigFactory.MaxUploadFileSize))
						.intValue() * 1024;
				if (maxSize > 0)
					maxUploadFileSize = maxSize;
			}
			// 上传文件缓存大小
			if (map.get(IConfigFactory.UploadSizeThreshold) != null) {
				int maxSize = Integer.valueOf(
						(String) map.get(IConfigFactory.UploadSizeThreshold))
						.intValue() * 1024;
				if (maxSize > 0)
					uploadSizeThreshold = maxSize;
			}
			// 最大直接跳转Action次数
			if (map.get(IConfigFactory.MaxDirectJumpToActionTimes) != null) {
				int maxTimes = Integer
						.valueOf(
								(String) map
										.get(IConfigFactory.MaxDirectJumpToActionTimes))
						.intValue();
				if (maxTimes >= 0)
					maxDirectJumpToActionTimes = maxTimes;
			}
			// 是否自动检查权限
			if (map.get(IConfigFactory.PermissionVerify) != null) {
					permissionVerify = Boolean.valueOf((String) map.get(IConfigFactory.PermissionVerify))
						.booleanValue();
			}
			/**
			 * 设置全局变量Globals.LOAD_MESSAGE_RESOURCE_FROM_PATH的值为true，这样即可从类加载器加载消息资源，可以提高性能，适合在产品发布的时使用
			 */
			if(map.get(IConfigFactory.MessageResourceLoader)!=null)
			{
				if("classpath".equalsIgnoreCase(map.get(IConfigFactory.MessageResourceLoader).toString()))
						Globals.LOAD_MESSAGE_RESOURCE_FROM_PATH=true;
			}
			/**
			 * 设置全局变量Globals.Language的值为指定的语言，这样就可以使得EasyJWeb后台输出指定语言的日志
			 */
			if(map.get(IConfigFactory.Language)!=null)
			{				
						Globals.LANGUAGE=new Locale(map.get(IConfigFactory.Language).toString());
			}
			/**
			 * 设置资源文件的默认格式
			 */
			if(map.get(IConfigFactory.PropertiesType)!=null)
			{				
				Globals.PROPERTIES_TYPE=map.get(IConfigFactory.PropertiesType).toString();
			}
			/**
			 * 读取系统中配置需要扫描的包
			 */
			if (map.get(IConfigFactory.DefaultActionPackage) != null) {
				String packages = (String) map
						.get(IConfigFactory.DefaultActionPackage);
				String[] ps = StringUtils.tokenizeToStringArray(packages, ",");
				if (ps != null && ps.length > 0)
					this.defaultActionPackages = ps;
			}

			importsConfigures.addAll((List) map.get("importResources"));

			// 处理需要启动时初始化的程序
			handleInitApp((List) map.get("initApp"));
			// 处理拦截器
			handleInterceptors((List) map.get("interceptors"));
			// 错误信息处理
			handleErrorHandler((List) map.get("errorHandlers"));
			// 增加Bean配置信息
			beanDefinitions.addAll((List) map.get("beanDefinitions"));
			// 将此条语句放到循环外
			// beanDefinitions.addAll(BeanConfigReader.parseBeansFromModules(modules));
			// 把Module及WebForm等相关对象转换成Bean存入到容器中
			// WebForm并没有转换成Bean存入到容器中
		}

		java.util.Iterator ocs = configManagers.iterator();
		while (ocs.hasNext()) {
			ConfigManager cfm = (ConfigManager) ocs.next();
			cfm.parseConfig(icf.getDoc());
		}

	}

	private void handleInitApp(List list) throws Exception {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// String[] appParms = ((String)
				// list.get(i)).split(";");
				Map appMap = (Map) list.get(i);
				String classname = (String) appMap.get("class");
				String initmethod = (String) appMap.get("init-method");
				String destroymethod = (String) appMap.get("destroy-method");
				Object obj = null;
				Method init_method = null;
				Map app = new HashMap();
				if (StringUtils.hasLength(classname)) {
					obj = Class.forName(classname).newInstance();
					app.put("classname", obj);
					if (StringUtils.hasLength(initmethod)) {
						init_method = obj.getClass().getMethod(initmethod);
						app.put("init-method", init_method);
					}
					if (StringUtils.hasLength(destroymethod)) {
						Method destroy_method = obj.getClass().getMethod(
								destroymethod);
						app.put("destroy-method", destroy_method);
					}
				}
				initApps.add(app);
			}
		}
	}

	private void handleInterceptors(List list) throws Exception {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				try {
					Map app = (Map) list.get(i);
					String name = (String) app.get("name");
					String method = (String) app.get("method");
					String classname = (String) app.get("class");
					Object obj = null;
					Method app_method = null;

					if (StringUtils.hasLength(classname)) {
						obj = Class.forName(classname).newInstance();
						app.put("classname", obj);
						if (StringUtils.hasLength(name)) {
							app.put("name", name);
						}
						if (StringUtils.hasLength(method)) {
							app_method = obj.getClass().getMethod(method);
							app.put("method", app_method);
						}
					}
					interceptors.add(app);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void handleErrorHandler(List list) throws Exception {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Map app = new HashMap((Map) list.get(i));
				if(StringUtils.hasLength((String)app.get("class"))){
					try{
					app.put("class", Class.forName((String)app.get("class")));
					}
					catch(Exception e){
						app.remove("class");
						e.printStackTrace();
					}
				}
				app.put("exception", Class.forName((String)app.get("exception")));
				this.errorHandler.add(app);
				}
		}
	}

	public String getTemplateBasePath() {
		return templateBasePath;
	}

	public void setTemplateBasePath(String templateBasePath) {
		this.templateBasePath = templateBasePath;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public Map getForms() {
		return forms;
	}

	public Map getModules() {
		return modules;
	}

	public Map getPages() {
		return pages;
	}

	public List getInitApps() {
		return initApps;
	}

	public List getInterceptors() {
		return interceptors;
	}

	public int getMaxUploadFileSize() {
		return maxUploadFileSize;
	}

	public void setMaxUploadFileSize(int maxUploadFileSize) {
		this.maxUploadFileSize = maxUploadFileSize;
	}

	public int getUploadSizeThreshold() {
		return uploadSizeThreshold;
	}

	public void setUploadSizeThreshold(int uploadSizeThreshold) {
		this.uploadSizeThreshold = uploadSizeThreshold;
	}

	public void setConfigures(String[] configures) {
		this.configures = configures;
	}

	public List getErrorHandler() {
		return errorHandler;
	}

	public List getBeanDefinitions() {
		return beanDefinitions;
	}

	public Integer getMaxDirectJumpToActionTimes() {
		return maxDirectJumpToActionTimes;
	}

	public String[] getDefaultActionPackages() {
		return defaultActionPackages;
	}

	public void loadAlias() {
		java.util.Iterator it = this.modules.values().iterator();
		while (it.hasNext()) {
			Module m = (Module) it.next();
			for (int i = 0; i < m.getAlias().size(); i++) {
				this.moduleAlias.put(m.getAlias().get(i), m.getPath());
			}
		}
	}

	public Module findModule(String name) {
		Module ret = (Module) this.modules.get(name);
		if (ret == null) {
			String realPath = (String) this.moduleAlias.get(name);
			if (realPath != null)
				ret = (Module) this.modules.get(realPath);
		}
		return ret;
	}

	public boolean isPermissionVerify() {
		return this.permissionVerify;
	}

}
