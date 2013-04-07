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

import javax.servlet.*;
import java.io.IOException;

/**
 * 
 * <p>
 * Title:字符转换及过滤类
 * </p>
 * <p>
 * Description: * 字符转换，EasyJWeb主要使用utf-8格式的模板，模板中有非英文字符，请使用字符过虑功能
 * 需要在Web.xml文件中设置，设置形式如下：
 * <code>
 * <!-- 定义字符处理Filter -->
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

public class CharsetFilter implements Filter {
	protected String encoding = null;

	protected FilterConfig filterConfig = null;

	protected boolean ignore = true;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (ignore || (request.getCharacterEncoding() == null)) {
			String encoding = selectEncoding(request);
			if (encoding != null)
				request.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");
		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {
		return (this.encoding);
	}
}