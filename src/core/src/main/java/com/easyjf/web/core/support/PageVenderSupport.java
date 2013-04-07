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
package com.easyjf.web.core.support;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.velocity.context.Context;

import com.easyjf.util.CommUtil;
import com.easyjf.util.HtmlUtil;
import com.easyjf.util.I18n;
import com.easyjf.util.TagUtil;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.core.ICustomGlobalsUtilBean;
import com.easyjf.web.tools.widget.Html;

/**
 * PageVender的支持工具类， 在其中主要是一些工具方法，比如将Session对象转化成Map对象等。
 * 
 * @author stefanie_wu
 * 
 */
public class PageVenderSupport {

	/**
	 * 将session对象中的值转成Map对象
	 * 
	 * @param session
	 *            HttpSession对象
	 * @return session对象值的构成的Map
	 */
	public static Map session2map(HttpSession session) {
		Map map = new HashMap();
		Enumeration e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			map.put(key, session.getAttribute(key));
		}
		return map;
	}

	/**
	 * 将请求对象中的参数转成Map对象
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return request对象中请求参数的构成的Map
	 */
	public static Map request2map(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration e = request.getAttributeNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			map.put(key, request.getAttribute(key));
		}
		return map;
	}
	public static Map request2map(ServletContext context) {
		Map map = new HashMap();
		if(context==null)return map;
		Enumeration e = context.getAttributeNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			map.put(key, context.getAttribute(key));
		}
		return map;
	}

	/**
	 * 创建工具上下文 把所有要用到的全局工具加载到内存
	 * 
	 * @param context
	 *            Velocity上下文对象
	 * @param globalUtils
	 *            需要传入的Map，作为一个Cache使用
	 */
	public static void createUtilContext(Context context, Map globalUtils) {
		if (globalUtils == null) {// 加载全局的Util
			globalUtils = new java.util.HashMap();
			List<ICustomGlobalsUtilBean> cus = FrameworkEngine.getContainer()
					.getBeans(ICustomGlobalsUtilBean.class);
			if (cus != null && cus.size() > 0) {
				for (ICustomGlobalsUtilBean cu : cus) {
					String names = cu.getName();
					if (names == null || "".equals(names)) {
						globalUtils.put(cu.getClass().getName(), cu);
						continue;
					} else {
						if (names.charAt(names.length() - 1) == ',') {
							names = names.substring(0, names.lastIndexOf(","));
						}
						String[] ns = names.split(",");
						if (ns.length > 0) {
							for (String n : ns) {
								globalUtils.put(n, cu);
							}
						}
					}
				}
			}
			globalUtils.put("HtmlUtil", HtmlUtil.getInstance());
			globalUtils.put("CommUtil", CommUtil.getInstance());
			globalUtils.put("TagUtil", TagUtil.getInstance());
			globalUtils.put("i18n", I18n.getInstance());
			globalUtils.put("lang", I18n.getInstance());
			globalUtils.put("html", Html.getInstance());
			Object authorizationUtil = FrameworkEngine.getContainer().getBean(
					com.easyjf.util.AuthorizationUtil.class);
			if (authorizationUtil != null) {
				globalUtils.put("AUTH", authorizationUtil);
				globalUtils.put("ROLE", authorizationUtil);
			}
		}
		java.util.Iterator it = globalUtils.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			context.put((String) en.getKey(), en.getValue());
		}
	}

}
