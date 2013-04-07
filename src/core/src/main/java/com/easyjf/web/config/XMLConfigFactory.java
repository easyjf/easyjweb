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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.util.XmlElementUtil;
import com.easyjf.web.FormConfig;
import com.easyjf.web.FormProperty;
import com.easyjf.web.Globals;
import com.easyjf.web.Module;
import com.easyjf.web.Page;

/**
 * 
 * <p>
 * Title:XML格式配置文件信息处理
 * </p>
 * <p>
 * Description:读取XML格式的EasyJWeb框架配置文件信息；
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */

public class XMLConfigFactory implements IConfigFactory {

	private Document doc;

	private static final Logger logger = Logger
			.getLogger(XMLConfigFactory.class);

	/**
	 * @deprecated
	 * 
	 */
	public XMLConfigFactory() {
		try {
			File file = new File(Globals.CONFIG_FILE_FULL_PATH);
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				SAXReader reader = new SAXReader();
				doc = reader.read(in);
			} else {
				logger.warn(I18n.getLocaleMessage("core.web.unable.to.find.configuration.file") + Globals.CONFIG_FILE_FULL_PATH
						+ I18n.getLocaleMessage("core.web.EasyJWEB.will.automatically.use.the.default.path.processing.Action"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn(I18n.getLocaleMessage("core.web.configuration.file.errors") + e);
		}
	}

	public XMLConfigFactory(InputStream in) {
		try {
			
			SAXReader reader = new SAXReader();
			doc = reader.read(in);
		} catch (Exception e) {
			e.printStackTrace();
//			logger.warn("配置文件错误！" + e);
			logger.warn(I18n.getLocaleMessage("core.web.unable.to.find.configuration.file") + Globals.CONFIG_FILE_FULL_PATH
					+","+I18n.getLocaleMessage("core.web.configuration.file.errors")+I18n.getLocaleMessage("core.web.EasyJWEB.will.automatically.use.the.default.path.processing.Action"));
		}
	}
private Element getRootElement()
{
	if (doc == null)
		return null;
	return doc.getRootElement();
}
private Element findElement(String name,Element el)
{
	return XmlElementUtil.findElement(name, el);
}
private List findElements(String name,Element el)
{
	return XmlElementUtil.findElements(name, el);	
}
	public void initForm(Map forms){
		Element root=findElement("forms",getRootElement());
		 if(root==null)return;
		  List nodes =root.elements();
		// 读取配置文件中的forms
		// forms中form的名称必须唯一
		// 将FromConfig实例保存到forms中
		for (int i = 0; i < nodes.size(); i++) {
			Element e = (Element) nodes.get(i);
			FormConfig fc = new FormConfig();
			// 设置错误提示类型，支持js及page两种方式，主要用于支持EasyJWeb代码自动生成工具
			fc.setAlertType(e.attributeValue("alertType"));
			// Form的bean类名。该类必须继承WebForm
			fc.setBean(e.attributeValue("bean"));
			// 设置是否生成客户端校验代码
			fc.setClientValidate(e.attributeValue("clientValidate"));
			// 设置form名称
			fc.setName(e.attributeValue("name"));
			// 设置是否生成服务器端校验代码
			fc.setServerValidate(e.attributeValue("serverValidate"));
			// 设置Form类的事件类别
			String events = e.attributeValue("event");
			if (events != null && (!events.equals(""))) {
				String[] s = events.split(",");
				if (s != null) {
					List event = new ArrayList();
					for (int t = 0; t < s.length; t++) {
						if (!s[t].equals(""))
							event.add(s[t]);
					}
					fc.setEvent(event);
				}
			}
			// 设置form属性
			List lPage = findElements("property",e);	
			Map pages = new HashMap();
			for (int j = 0; j < lPage.size(); j++) {

				Element p = (Element) lPage.get(j);
				FormProperty prop = new FormProperty();
				prop.setInitial(p.attributeValue("initial"));
				prop.setName(p.attributeValue("name"));
				prop.setNotNull(p.attributeValue("notNull"));
				prop.setSize(p.attributeValue("size"));
				prop.setType(p.attributeValue("type"));
				events = p.attributeValue("event");
				if (events != null && (!events.equals(""))) {
					String[] s = events.split(",");
					if (s != null) {
						List event = new ArrayList();
						for (int t = 0; t < s.length; t++) {
							if (!s[t].equals(""))
								event.add(s[t]);
						}
						prop.setEvent(event);
					}
				}
				pages.put(prop.getName(), prop);
			}
			fc.setPropertys(pages);
			forms.put(fc.getName(), fc);
		}	
	}

	public void initModule(Map modules) {
		Element root=findElement("modules",getRootElement());
		if(root==null)return;
		
		// 读取配置文件中的modules
		// modules中module节点的名称必须唯一
		// 将FromConfig实例保存到forms中
		String injectType = "none";
		if (root.attributeValue("inject") != null)
			injectType = root.attributeValue("inject");
		List nodes =root.elements();
		for (int i = 0; i < nodes.size(); i++) {
			Element e = (Element) nodes.get(i);
			Module mc = new Module();
			// 设置模块处理的action类
			mc.setAction(e.attributeValue("action"));
			// 设置是否自动添加token		
			if(e.attributeValue("autoToken")!=null)
			mc.setAutoToken(Boolean.valueOf(e.attributeValue("autoToken")));
			if(e.attributeValue("alias")!=null)
			{
				mc.setAlias(e.attributeValue("alias"));
			}
			// 设置默认页面
			if(e.attributeValue("defaultPage")!=null)
			mc.setDefaultPage(e.attributeValue("defaultPage"));
			// 设置模块表单			
			mc.setForm(e.attributeValue("form"));
			// 设置模板路径 如/hello.ejf 的路径为/hello
			mc.setPath(e.attributeValue("path"));
			// 设置bean的作用域
			// singleton？
			if (e.attributeValue("scope") != null)
				mc.setScope(e.attributeValue("scope"));
			if (e.attributeValue("method") != null)
				mc.setMethod(e.attributeValue("method"));
			if (e.attributeValue("inject") != null)
				mc.setInject(e.attributeValue("inject"));
			else
				mc.setInject(injectType);
			if (e.attributeValue("views") != null)
				mc.setViews(e.attributeValue("views"));
			if (e.attributeValue("view") != null)
				mc.setViews(e.attributeValue("view"));
			if (e.attributeValue("validate") != null)
				mc.setValidate(Boolean.parseBoolean(e
						.attributeValue("validate")));	
			if (e.attributeValue("messageResource")!=null)
			{
				mc.setMessageResource(e.attributeValue("messageResource"));
			}
			List lPage = findElements("page",e);			
			for (int j = 0; j < lPage.size(); j++) {
				Element p = (Element) lPage.get(j);
				Page page = new Page();
				// 设置Page名称 在一个module中Page名称必须唯一
				page.setName(p.attributeValue("name"));
				if (mc.getDefaultPage() == null
						|| mc.getDefaultPage().equals("")) {
					mc.setDefaultPage(page.getName());
				}
				// 设置Page类型 html/template
				// 模版用于执行合并,html用于直接跳转
				page.setType(p.attributeValue("type"));
				// Page的url,相对路径
				page.setUrl(p.attributeValue("url"));
				if (p.attributeValue("contentType") != null)
					page.setContentType(p.attributeValue("contentType"));
				// pages.put(page.getName(), page);
				mc.getPages().put(page.getName(), page);
			}
			// 设置拦截器,并以实例形式保存
			List interceptors = findElements("interceptor",e);
			for (int j = 0; j < interceptors.size(); j++) {
				Element p = (Element) interceptors.get(j);
				String interceptorClazz = p.attributeValue("class");
				try {
					mc.getInterceptors().add(
							Class.forName(interceptorClazz).newInstance());
				} catch (Exception ex) {
				}
			}
			// 读取module中property节点，并将property节点值
			mc.setPropertyValues(BeanConfigReader.parsePropertyValues(findElements("property",e)));
			modules.put(mc.getPath(), mc);
			
		}
	}

	public void initPage(Map pages) {
		Element root=findElement("pages",getRootElement());
		if(root==null)return;
		List nodes = root.elements();
		for (int i = 0; i < nodes.size(); i++) {
			Element e = (Element) nodes.get(i);
			Page page = new Page();
			page.setName(e.attributeValue("name"));
			page.setType(e.attributeValue("type"));
			page.setUrl(e.attributeValue("url"));
			if (e.attributeValue("contentType") != null)
				page.setContentType(e.attributeValue("contentType"));
			pages.put(page.getName(), page);
		}
	}

	public Map initOther() {
		if (doc == null)
			return null;
		Map result = new HashMap();
		Element root=findElement("framework-setting",getRootElement());
		if(root==null)
		{
			root=findElement("frame-setting",getRootElement());
		}
		if(root!=null){
		// 设置模版根目录
		Element node =findElement("template-base",root);
		if (node != null)
			result.put("TemplateBasePath", node.getText());
		// 处理框架初始化程序
		node=findElement("init-app",root);
		List appList = findElements("app-class",node);
		List applist = new ArrayList();
			for (int i = 0; i < appList.size(); i++) {
				Element e = (Element) appList.get(i);
				Map app = new HashMap();
				app.put("init-method", e.attributeValue("init-method"));
				app.put("destroy-method", e.attributeValue("destroy-method"));
				app.put("class", e.getText());
				applist.add(app);
			}
		result.put("initApp", applist);

		// 初始化拦截器
		node=findElement("interceptors",root);
		List interceptors = findElements("app-class",node);
		List appinterceptorslist = new ArrayList();
		if (interceptors != null) {
			for (int i = 0; i < interceptors.size(); i++) {
				Element e = (Element) interceptors.get(i);
				Map app = new HashMap();
				app.put("name", e.attributeValue("name"));
				app.put("method", e.attributeValue("method"));
				app.put("class", e.getText());
				appinterceptorslist.add(app);
			}
		}
		result.put("interceptors", appinterceptorslist);

		// 初始化错误处理信息
		node=findElement("error-handler",root);
		List errorHandlers = findElements("app-class",node);
		if (errorHandlers != null) {
			for (int i = 0; i < errorHandlers.size(); i++) {
				Element e = (Element) errorHandlers.get(i);
				Map app = new HashMap();
				app.put("exception", e.attributeValue("exception"));
				app.put("path", e.attributeValue("path"));
				app.put("class", e.getText());
				errorHandlers.set(i, app);
			}
		}
		result.put("errorHandlers", errorHandlers);
		// 初始化系统参数，包括是否调试模式，上传设置等	
		List list = findElements("property",root);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Element el = (Element) list.get(i);
				result.put(el.attributeValue("name"), el.getText());
			}
		}
		}
		List importResources = new java.util.ArrayList();
		List list =findElements("import",getRootElement());
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Element el = (Element) list.get(i);
				String resource = el.attributeValue("resource");
				if (resource != null && !"".equals(resource)) {
					//只能导入/WEB-INF目录或者是以classpath开始的文件
					if (resource.toLowerCase().indexOf("web-inf") < 0 && resource.indexOf("classpath")!=0)
						resource = "/WEB-INF/" + resource;
					importResources.add(resource);
				}
			}
		}
		result.put("importResources", importResources);
		// 调用EasyJWeb的Bean角析处理器，将所有bean放置到 beanDefinitions中
		result.put("beanDefinitions", BeanConfigReader
				.parseBeansFromDocument(doc));
		return result;
	}

	public Document parse(String fileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		File file = new File(fileName);
		Document document = file.exists() ? reader.read(fileName) : null;
		return document;
	}

	public Document getDoc() {
		return doc;
	}

}
