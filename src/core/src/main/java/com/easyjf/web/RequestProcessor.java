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

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyjf.web.errorhandler.IErrorHandlerManager;

/**
 * EasyJWeb的核心处理器，由主控Servlet即ActionServlet来调用并进行请求处理工作。
 * 在EasyJWeb中，只提供了该接口的一个唯一实现DefaultRequestProcessor，默认情况下，所有的请求都由该类负责处理。
 * 
 * @author 大峡
 * 
 */
public interface RequestProcessor extends Serializable {

	/**
	 * 处理器的入口，所有经过EasyJWeb的调用都会执行该方法，对各种数据进行相应的处理。
	 * 
	 * @param request
	 *            请求对象
	 * @param response
	 *            响应对象
	 * @throws Throwable
	 *             处理过程中，可以根据特定情况抛出异常
	 */
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Throwable;

	/**
	 * 得到EasyJWeb中的配置信息
	 * 
	 * @return EasyJWeb配置信息
	 */
	public WebConfig getWebConfig();

	/**
	 * 设置EasyJWeb配置对象
	 * 
	 * @param config
	 *            配置信息
	 */
	public void setWebConfig(WebConfig config);

	/**
	 * 设置核心处理器主控Servlet
	 * 
	 * @param servlet
	 *            主控servlet
	 */
	void setServlet(ActionServlet servlet);

	/**
	 * 获取系统中的错误处理器
	 * 
	 * @return 该处理器中所使用的错误处理器
	 */
	public IErrorHandlerManager getErrorHandlerManager();

	/**
	 * 设置系统中的错误处理器
	 * 
	 * @param errorHandlerManager
	 *            具体的错误处理器
	 */
	public void setErrorHandlerManager(IErrorHandlerManager errorHandlerManager);

}
