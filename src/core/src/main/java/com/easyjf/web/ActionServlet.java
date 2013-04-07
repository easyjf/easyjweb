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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.web.core.DefaultRequestProcessor;
import com.easyjf.web.core.FrameworkEngine;

/**
 * 
 * <p>
 * Title:EasyJWeb核心Servlet
 * </p>
 * <p>
 * Description: EasyJWeb核心Servlet，所有的.ejf访问都将由该Servlet处理
 * 用户必须在web.xml文件指定扩展名为.ejf的访问都指向该类或其子类。
 * 为了有效使用EasyJWeb，一个应用EasyJWeb的应用中，web.xml的配置一般包括以下信息。
 * <code>
 * <!--定义easyjweb的配置文件位置 -->
 <context-param>
 <param-name>easyjwebConfigLocation</param-name>
 <param-value>/WEB-INF/mvc.xml</param-value>
 </context-param>
 <!--定义EasyJWeb的主控Servlet  -->
 <servlet>
 <servlet-name>easyjf</servlet-name>
 <servlet-class>com.easyjf.web.ActionServlet</servlet-class>
 <load-on-startup>1</load-on-startup>
 </servlet>
 <servlet-mapping>
 <servlet-name>easyjf</servlet-name>
 <url-pattern>*.ejf</url-pattern><!--所有.ejf的扩展名都由easyjweb来处理-->
 </servlet-mapping>
 <servlet-mapping>
 <servlet-name>easyjf</servlet-name>
 <url-pattern>/ejf/*</url-pattern><!--所有/ejf/*样式的url都交由EasyJWeb来处理-->
 </servlet-mapping>
 <!-- 定义字符处理Filter -->
 <filter>
 <filter-name>CharsetFilter</filter-name>
 <filter-class>com.easyjf.web.CharsetFilter</filter-class>
 <init-param>
 <param-name>encoding</param-name>
 <param-value>UTF-8</param-value>
 </init-param>
 <init-param>
 <param-name>ignore</param-name>
 <param-value>true</param-value>
 </init-param>
 </filter>
 <filter-mapping>
 <filter-name>CharsetFilter</filter-name>
 <servlet-name>easyjf</servlet-name>
 </filter-mapping>
 * </code>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 大峡、stef_wu
 * @version 1.0
 */
public class ActionServlet extends HttpServlet implements
		javax.servlet.ServletContextListener {

	static final long serialVersionUID = 887867880L;

	private static final Logger logger =org.apache.log4j.Logger.getLogger(ActionServlet.class);

	// web.xml中配置文件参数名
	public static final String EASYJWEB_CONFIGURE_KEY = "easyjwebConfigLocation";
	
	public static final String EASYJWEB_PROCESSOR_IN_CONTANIER = "EasyJWeb-Processor";

	/**
	 * EasyJWeb加载器，用来加载EasyJWeb的各种配置文件，缺省配置信息等
	 */
	private FrameworkLoader loader;

	/**
	 * EasyJWeb的核心处理器，由主控Servlet即ActionServlet来调用并进行请求处理工作
	 */
	private RequestProcessor processor;

	/**
	 * 初始化各种配置,包括解析easyjf-web.xml
	 * 
	 * @param servletContext
	 */
	private void initEasyJWeb(ServletContext servletContext) {	
		loader = new FrameworkLoader(this.getConfigures(servletContext));
		loader.setServletContext(servletContext);// 如果是Web应用，需要设置
		// 此上下文。
		loader.initEasyJWeb();
		this.getServletContext().setAttribute(Globals.CONTAINER_CONTEXT,
				loader.getContainer());
		FrameworkEngine.setActionServlet(this);//把Servlet设置到FrameworkEngine中，以便通过FrameworkEngine来控制Servlet
	}

	public void init(ServletConfig config) throws ServletException {
		// 初始化web.xml参数值		
		super.init(config);
		initEasyJWeb(config.getServletContext());		
		//初始化Servlet配置
//		logger.debug(I18n.getLocaleMessage("core.initialization.servlet.configuration"));		
	}

	/**
	 * 得到easyJWeb的配置文件
	 * 若未在web.xml中设定easyjwebConfigLocation则使用默认的/WEB-INF/easyjf-web.xml
	 * <context-param> <param-name>easyjwebConfigLocation</param-name>
	 * <param-value> /WEB-INF/easyjf-web1.xml</param-value>
	 * </context-param>
	 */
	private String[] getConfigures(ServletContext servletContext) {		
		String paravalue = servletContext
				.getInitParameter(EASYJWEB_CONFIGURE_KEY);
		String[] s = StringUtils.hasLength(paravalue) ? StringUtils
				.tokenizeToStringArray(paravalue, ",") : null;
		return s;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doRequest(request, response);
	}

	/**
	 * 停止后台应用
	 */
	public void destroy() {
		loader.destroyApps();
		super.destroy();
	}

	/**
	 * 处理用户请求
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 初始化Request
		try {
			// 初始化上下文根，并设置框架运行模式
			doInitRequest(request, response);
			if (processor == null) {
				/**
				 * 首先尝试从容器中加载框架处理器, 若无法从容器中加载处理器,则使用默认的处理器
				 */
				Object obj = loader.getContainer().getBean(
						EASYJWEB_PROCESSOR_IN_CONTANIER);
				if (obj != null && obj instanceof RequestProcessor) {
					//成功从容器中加载处理器
					logger.debug(I18n.getLocaleMessage("core.successfully.in.containers.loaded.processor"));
					processor = (RequestProcessor) obj;
					processor.setServlet(this);
					processor.setWebConfig(loader.getWebConfig());
				} else {
					processor = new DefaultRequestProcessor(this, loader
							.getWebConfig());
				}
			}
			processor.process(request, response);
		} catch (Throwable error) {
			throw new ServletException(error);
		}
	}

	protected void doInitRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		map.put(ActionContext.HTTP_REQUEST, request);
		map.put(ActionContext.HTTP_RESPONSE, response);
		map.put(ActionContext.SERVLET_CONTEXT, getServletContext());
		ActionContext.setContext(new ActionContext(map));
		LocalManager.setLocale(request.getLocale());
		if (loader.getWebConfig().isDebug()) {// 调试模式每次都要初始化配置文件
			initEasyJWeb(this.getServletContext());
		}		
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		try {			
			initEasyJWeb(sce.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
			//系统初使化错误
			throw new java.lang.RuntimeException(I18n.getLocaleMessage("core.system.error.Initting"));
		}
	}
}
