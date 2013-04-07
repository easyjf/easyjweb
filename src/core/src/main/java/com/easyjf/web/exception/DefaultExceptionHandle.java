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
package com.easyjf.web.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.exception.MethodInvocationException;

import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.interceptor.ExceptionInterceptor;

/**
 * 这是异常链中的最后一个异常
 * 
 * @author 大峡
 * 
 */
public class DefaultExceptionHandle implements ExceptionInterceptor {

	private List<java.lang.Throwable> exceptions = new java.util.ArrayList<Throwable>();

	private String errorPage;

	private static final Logger logger = Logger
			.getLogger(DefaultExceptionHandle.class);

	public List<java.lang.Throwable> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<java.lang.Throwable> exceptions) {
		this.exceptions = exceptions;
	}

	public boolean handle(Throwable e, Object target, Method method,
			Object[] args) {
		if (exceptions.size() > 0) {// 只处理exceptions中定义的异常
			for (Throwable thr : exceptions) {
				if (thr.getClass().isAssignableFrom(e.getClass())) {
					showErrInfo(e);
					return true;
				}
			}
		} else// 如果没有定义异常，则处理所有异常
		{
			showErrInfo(e);
			return true;
		}
		// 没有处理任何异常，则返回false。
		return false;
	}

	protected void showErrInfo(Throwable e) {
		try {
			// ActionContext.getContext().getResponse().setStatus(500);
			if (errorPage != null)
				ActionContext.getContext().getResponse()
						.sendRedirect(errorPage);
			else if (FrameworkEngine.getWebConfig().isDebug())
				error(ActionContext.getContext().getRequest(), ActionContext
						.getContext().getResponse(), e);
			else
				info(ActionContext.getContext().getRequest(), ActionContext
						.getContext().getResponse(), e);
		} catch (Exception se) {
			se.printStackTrace();
			logger.error(I18n.getLocaleMessage("core.web.Exception.Handling.Framework.deal.with.serious.mistake.please.check.configuration.is.correct"));
		}
	}

	/**
	 * 输出系统框架错误信息提示
	 * 
	 * @param request
	 * @param response
	 * @param e
	 * @throws ServletException
	 */
	protected void error(HttpServletRequest request,
			HttpServletResponse response, Throwable e) throws ServletException {
		response.setStatus(response.SC_NOT_FOUND);
		e.printStackTrace();
		StringBuffer html = new StringBuffer();
		String title = request.getCharacterEncoding() != null ? I18n.getLocaleMessage("core.web.Framework.error")
				: "Framework error";
		html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		html
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\r\n");
		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
		html.append("<head><title>" + title + "</title>\r\n");
		html.append("<style type=\"text/css\">\r\n");
		html.append("#errors{\r\n");
		html.append("	clear : both;\r\n");
		html.append("}\r\n");
		html.append("ul,li{\r\n");
		html.append("	list-style-type : none;\r\n");
		html.append("}\r\n");
		html.append("#errors .title{\r\n");
		html.append("	font-weight : bold;\r\n");
		html.append("	width : 95%;\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	background-color : #FFFFAF;\r\n");
		html.append("	text-align : left;\r\n");
		html.append("	line-height : 28px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	padding-left : 30px;\r\n");
		html.append("}\r\n");
		html.append("#errors #causedBy{\r\n");
		html.append("	padding : 10px 30px 0 30px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	word-break: break-all;\r\n");
		html.append("	word-wrap:break-word;\r\n");
		html.append("}\r\n");
		html.append("#errors #details{\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	background-color : #FAFAFA;\r\n");
		html.append("	padding : 0 30px 0 30px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	word-wrap:break-word;\r\n");
		html.append("	overflow : hidden;\r\n");
		html.append("}\r\n");
		html.append(".line{\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	height : 1px;\r\n");
		html.append("	width : 95%;\r\n");
		html.append("	clear : both;\r\n");
		html.append("}\r\n");
		html.append(".copyright{\r\n");
		html.append("	clear : both;\r\n");
		html.append("	color : #aaa;\r\n");
		html.append("	text-align : center;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	padding-bottom : 20px;\r\n");
		html.append("}\r\n");
		html.append("</style>\r\r\n");
		html.append("</head>\r\n");
		html.append("<body>\r\n");
		html.append("<div id=\"errors\">\r\n");
		html.append("<div class=\"title\">" + title + "</div>\r\n");
		html.append("		<ul id=\"causedBy\"><li>\r\n");
		Throwable cause = e;
		String why = cause.getMessage();
		if (why != null && why.trim().length() > 0) {
			html.append(why);
			html
					.append("\r\n<br>"+I18n.getLocaleMessage("core.web.for.enquiries.please.detail")+"<a href='http://www.easyjf.com/' target='_blank'>http://www.easyjf.com</a>\r\n");
		}
		if (cause instanceof MethodInvocationException) {
			cause = ((MethodInvocationException) cause).getWrappedThrowable();
		}
		html.append("</li></ul>\r\n");
		html.append("	<div id=\"details\">\r\n<pre>");
		StringWriter sw = new StringWriter();
		cause.printStackTrace(new PrintWriter(sw));
		html.append(sw.toString());
		html.append("</pre>	</div>\r\n");
		html.append("</div>\r\n");
		html.append("<hr class=\"line\" />\r\n");
		html.append("<div class=\"copyright\">\r\n");
		html.append("	<span>&copy; 2006-"+java.util.Calendar.getInstance().get(Calendar.YEAR)+" EasyJF "+I18n.getLocaleMessage("core.web.simple.open.source.framework.JAVA")+"</span>\r\n");
		html.append("</div>\r\n");
		html.append("</body>\r\n");
		html.append("</html>");
		response.setContentType("text/html; charset="+ (request.getCharacterEncoding() != null?request.getCharacterEncoding():"UTF-8"));
		try {
			FrameworkEngine.getResponseWriter(response).write(html.toString());
		} catch (Exception e1) {
			throw new ServletException();
		}

	}

	protected void info(HttpServletRequest request,
			HttpServletResponse response, Throwable e) throws ServletException {
		response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
		e.printStackTrace();
		StringBuffer html = new StringBuffer();
		String title = request.getCharacterEncoding() != null ? I18n.getLocaleMessage("core.web.Tips")
				: "Framework Friendly Info!";
		html.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		html
				.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\r\n");
		html.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
		html.append("<head><title>" + title + "</title>\r\n");
		html.append("<style type=\"text/css\">\r\n");
		html.append("#errors{\r\n");
		html.append("	clear : both;\r\n");
		html.append("}\r\n");
		html.append("ul,li{\r\n");
		html.append("	list-style-type : none;\r\n");
		html.append("}\r\n");
		html.append("#errors .title{\r\n");
		html.append("	font-weight : bold;\r\n");
		html.append("	width : 95%;\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	background-color : #FFFFAF;\r\n");
		html.append("	text-align : left;\r\n");
		html.append("	line-height : 28px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	padding-left : 30px;\r\n");
		html.append("}\r\n");
		html.append("#errors #causedBy{\r\n");
		html.append("	padding : 10px 30px 0 30px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	word-break: break-all;\r\n");
		html.append("	word-wrap:break-word;\r\n");
		html.append("}\r\n");
		html.append("#errors #details{\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	background-color : #FAFAFA;\r\n");
		html.append("	padding : 0 30px 0 30px;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	word-wrap:break-word;\r\n");
		html.append("	overflow : hidden;\r\n");
		html.append("}\r\n");
		html.append(".line{\r\n");
		html.append("	border : 1px solid #E6E6E6;\r\n");
		html.append("	height : 1px;\r\n");
		html.append("	width : 95%;\r\n");
		html.append("	clear : both;\r\n");
		html.append("}\r\n");
		html.append(".copyright{\r\n");
		html.append("	clear : both;\r\n");
		html.append("	color : #aaa;\r\n");
		html.append("	text-align : center;\r\n");
		html.append("	font-size : 12px;\r\n");
		html.append("	padding-bottom : 20px;\r\n");
		html.append("}\r\n");
		html.append("</style>\r\r\n");
		html.append("</head>\r\n");
		html.append("<body>\r\n");
		html.append("<div id=\"errors\">\r\n");
		html.append("<div class=\"title\">" + title + "</div>\r\n");
		html.append("		<ul id=\"causedBy\"><li>\r\n");
		Throwable cause = e;
		String why = cause.getMessage();
		if (why != null && why.trim().length() > 0) {
			html.append(why);
			html
					.append("\r\n<br>"+I18n.getLocaleMessage("core.web.for.enquiries.please.detail")+"<a href='http://www.easyjf.com/' target='_blank'>http://www.easyjf.com</a>\r\n");
		}
		if (cause instanceof MethodInvocationException) {
			cause = ((MethodInvocationException) cause).getWrappedThrowable();
		}
		html.append("</li></ul>\r\n");
		/*
		 * html.append(" <div id=\"details\">\r\n<pre>");
		 * StringWriter sw = new StringWriter();
		 * cause.printStackTrace(new PrintWriter(sw));
		 * html.append(sw.toString()); html.append("</pre>
		 * </div>\r\n");
		 */
		html.append("</div>\r\n");
		html.append("<hr class=\"line\" />\r\n");
		html.append("<div class=\"copyright\">\r\n");
		html.append("	<span>&copy; 2006-").append(java.util.Calendar.getInstance().get(Calendar.YEAR)).append("　EasyJF "+I18n.getLocaleMessage("core.web.simple.open.source.framework.JAVA")+"</span>\r\n");
		html.append("</div>\r\n");
		html.append("</body>\r\n");
		html.append("</html>");
		response.setContentType("text/html; charset="+ (request.getCharacterEncoding() != null?request.getCharacterEncoding():"UTF-8"));
		try {
			FrameworkEngine.getResponseWriter(response).write(html.toString());
		} catch (Exception e2) {
			throw new ServletException(e);
		}
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}
}
