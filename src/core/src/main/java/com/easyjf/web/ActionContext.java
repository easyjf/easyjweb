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

import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * <p>
 * Title:Servlet上下文件处理类
 * </p>
 * <p>
 * Description: 通过使用ThreadLocal变量,实现用户当前访问的Servlet上下文环境访问！<br>
 * 通过使用ActionContext，在用户的Action类可以访问servlet相关资源.如要访问session对象，直接使用ActionContext.getContext().getSession()即可！
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
public class ActionContext {

	public static final String HTTP_REQUEST = "request";

	public static final String HTTP_RESPONSE = "response";

	public static final String HTTP_SESSION = "session";
	
	public static final String SERVLET_CONTEXT = "servletcontext";

	public static final String HTTP_PARAMETERS = "parameters";

	public static final String WEBINVOCATION = "WebInvocation";

	public static final String WRITER = "ActionWriter";

	static ThreadLocal actionContext = new ActionContextThreadLocal();

	private Map context;

	public ActionContext(Map context) {
		this.context = context;
	}

	/**
	 * 设置当前ActionContext的值
	 * 
	 * @param context
	 *            指定的ActionContext
	 */
	public static void setContext(ActionContext context) {
		actionContext.set(context);
	}

	/**
	 * 返回绑定到当前线程特定的ActionContext.
	 * 
	 * @return 当前线程(当前Request)特定的ActionContext.
	 */
	public static ActionContext getContext() {
		ActionContext context = (ActionContext) actionContext.get();
		if (context == null) {
			context = new ActionContext(new HashMap());
			setContext(context);
		}
		return context;
	}

	public void setContextMap(Map contextMap) {
		getContext().context = contextMap;
	}

	public Map getContextMap() {
		return context;
	}

	/**
	 * 得到HttpServletRequest对象，若在用户的Action中需要直接访问HttpServletRequest，则可以直接通过该访问得到。
	 * 
	 * @return 返回当前HttpServletRequest对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest ret = null;
		if (context.containsKey(HTTP_REQUEST))
			ret = (HttpServletRequest) context.get(HTTP_REQUEST);
		return ret;
	}

	/**
	 * 得到HttpServletResponse对象，若在用户的Action中需要直接访问HttpServletResponse，则可以直接通过该访问得到。
	 * 
	 * @return 返回当前HttpServletResponse对象
	 */
	public HttpServletResponse getResponse() {
		HttpServletResponse ret = null;
		if (context.containsKey(HTTP_RESPONSE))
			ret = (HttpServletResponse) context.get(HTTP_RESPONSE);
		return ret;
	}

	/**
	 * 得到HttpSession对象，若在用户的Action中需要直接访问HttpSession，则可以直接通过该访问得到。
	 * 
	 * @return 返回当前HttpSession对象
	 */
	public HttpSession getSession() {
		HttpSession ret = null;
		if (context.containsKey(HTTP_SESSION))
			ret = (HttpSession) context.get(HTTP_SESSION);
		else {
			HttpServletRequest request = getRequest();
			if (request != null)
				ret = request.getSession();
		}
		return ret;
	}
	public ServletContext getServletContext() {
		ServletContext ret = null;
		if (context.containsKey(SERVLET_CONTEXT))
			ret = (ServletContext) context.get(SERVLET_CONTEXT);
		return ret;
	}
	/**
	 * 得到当前Request中的参数Map，若在用户的Action中需要直接访问Request中的参数Map，则可以直接通过该方法访问得到。
	 * 
	 * @return 返回当前Request调用中的参数Map即parameterMap
	 */
	public Map getParameters() {
		Map ret = null;
		if (context.containsKey(HTTP_PARAMETERS))
			ret = (Map) context.get(HTTP_PARAMETERS);
		else {
			HttpServletRequest request = getRequest();
			if (request != null)
				ret = request.getParameterMap();
		}
		return ret;
	}

	/**
	 * 得到当前调用中EasyJWeb调用参数信息，包括Module，Form，具体执行的Action等
	 * 
	 * @return 返回EasyJWeb调用参数对象
	 */
	public WebInvocationParam getWebInvocationParam() {
		return (WebInvocationParam) context.get(WEBINVOCATION);
	}

	public Object get(Object key) {
		return context.get(key);
	}

	public Writer getCustomWriter() {
		return (Writer) get(WRITER);
	}

	public String getUri()
	{
		return (String)get("FORWARD");
	}
	/**
	 * 自定义的全局跳转，当在改变默认的writer时，可以通过设置一个全局跳转，让response可以给以响应
	 * @param uri 全局跳转的uri，用于给用户反馈同步信息
	 */
	public void setUri(String uri) {
		this.put("FORWARD", uri);
	}
	public IRequestCallback getRequestCallback()
	{
		return (IRequestCallback)get("RequestCallback");
	}

	public void setRequestCallback(IRequestCallback callback) {
		this.put("RequestCallback", callback);
	}
	/**
	 * 可以手动设置模板的输出，比如下面的代码把EasyJWeb的执行结果输出到文件中：
	 * 
	 * <pre>
	 * java.io.Writer writer = new OutputStreamWriter(new FileOutputStream(new File(
	 * 		&quot;c://test.htm&quot;)), &quot;UTF-8&quot;);
	 * ActionContext.getContext().setCustomWriter(writer);
	 * </pre>
	 * 
	 * @param writer
	 */
	public void setCustomWriter(Writer writer) {
		this.put(WRITER, writer);
	}

	public void put(Object key, Object value) {
		context.put(key, value);
	}

	private static class ActionContextThreadLocal extends ThreadLocal {
		protected synchronized Object initialValue() {
			return new ActionContext(new HashMap());
		}
	}
}
