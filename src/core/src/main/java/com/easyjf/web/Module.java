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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easyjf.util.StringUtils;
import com.easyjf.web.core.FrameworkEngine;

/**
 * 
 * <p>
 * Title:模块配置信息
 * </p>
 * <p>
 * Description:处理模块配置文件信息,跟Action对应
 * 在EasyJWeb中，一个Module对应一个IWebAction，IWebAction是由Module来定义的。
 * 我们经常把数据或功能相关的多个功能项组装到一个模块Module中。每个Module包含不同的页面视图，可以在Action中根据调用转向不同的页面视图模板。
 * 
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
public class Module extends PropertyInfo {

	/**
	 * 映射的url
	 */
	private Boolean autoToken = false;

	private String path;

	private String form;

	/**
	 * 处理该模块的action类名
	 */
	private String action;

	/**
	 * 方法名称
	 */
	private String method="execute";
	
	private String input;

	/**
	 * 默认使用视图
	 */
	private String defaultPage;

	private Map pages = new java.util.HashMap();

	/**
	 * 模块拦截器
	 */
	private List interceptors;

	/**
	 * 该模板的初始化方式，也即生命周期，可以是request、session、prototype、singleton等几个值
	 */
	private String scope = "request";

	/**
	 * 模块所需要业务组件的注入方式，可以是byType,byName,auto,none等几种方式，默认为不使用自动注入
	 */
	private String inject = "none";// 默认将按类型自动注入模块中的字段值

	/**
	 * 该模块的视图位置
	 */
	private String views = "";

	private String[] auInject = {};

	private String[] disInject = {};

	private boolean validate = false;

	private List alias = new java.util.ArrayList();

	private String messageResource="";
	public Module() {
		this.interceptors = new ArrayList();
	}

	public List getInterceptors() {
		return this.interceptors;
	}

	public void setInterceptors(List interceptors) {
		this.interceptors = interceptors;
	}

	/**
	 * 模块处理的action类
	 * 
	 * @return action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * 设置模块处理的action类
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action == null ? "" : action;
	}

	/**
	 * 返回默认页面
	 * 
	 * @return defaultPage
	 */
	public String getDefaultPage() {
		return defaultPage;
	}

	/**
	 * 设置默认页面
	 * 
	 * @param defaultPage
	 */
	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage == null ? "" : defaultPage;
	}

	/**
	 * 模块表单
	 * 
	 * @return form
	 */
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form == null ? "" : form;
	}

	/**
	 * 模块下的所有模板或页面
	 * 
	 * @return pages 返回该模块中定义的所有Page
	 */
	public Map getPages() {
		return pages;
	}

	public void setPages(Map pages) {
		this.pages = pages;
	}

	/**
	 * 模板路径 如/hello.ejf 的路径为/hello
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? "" : path;
	}

	/**
	 * 该方法可以从当前模块中查找一个指定名称的页面视图或转向
	 * 
	 * @param pageName
	 *            要找找的页面视图名称
	 * @return 返回找到的视图模板页面或转向
	 */
	public Page findPage(String pageName) {
		Page page = (Page) pages.get(pageName);
		/*
		 * 如果没有配置Pages属性，则按照以下步骤寻找page： 1，按照module_pagename寻找， 2，按照pagename寻找；
		 */
		if (page == null || "".equals(page.getUrl())) {
			page = FrameworkEngine.findPage(this, pageName);
		}
		return page;
	}

	/**
	 * Module的范围设置，由于Module也是一个Bean，因此该属性也即定义Action的生命周期
	 * 
	 * @return 返回当前Module的生命周期
	 */
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * 当前模块中的固定输入视图模板
	 * 
	 * @return 若当前模板定义了输入视图模板，则返回输入视图模板。
	 */
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	/**
	 * 返回该Module对应的Action
	 * Bean中各元素的注入方式，包括bytype,byname,auto及no几种。默认情况下为no，表示不使用自动注入。
	 * 
	 * @return 注入的类型
	 */
	public String getInject() {
		return inject;
	}

	/**
	 * 设置Module对应的Action Bean的注入方式
	 * 
	 * @param inject
	 */
	public void setInject(String inject) {
		this.inject = inject;
	}

	/**
	 * 返回当前模块的视图基础路径
	 * 
	 * @return 如果设置了当前模块的基础视图路径，则返回该路径，否则返回null
	 */
	public String getViews() {
		return views;
	}

	/**
	 * 设置模块的基础视图路径
	 * 
	 * @param views
	 *            模块的基础视图路径
	 */
	public void setViews(String views) {
		this.views = views;
	}

	/**
	 * 该模块中自动注入的对象名称集合
	 * 
	 * @return 若在配置中设置了自动注入的集合，则返回，否则返回null
	 */
	public String[] getAuInject() {
		return auInject;
	}

	/**
	 * 设置模块中自动注入的对象集合的名称
	 * 
	 * @param auInject
	 *            需要自动注入的对象名称集合
	 */
	public void setAuInject(String[] auInject) {
		this.auInject = auInject;
	}

	public String[] getDisInject() {
		return disInject;
	}

	public void setDisInject(String[] disInject) {
		this.disInject = disInject;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	/**
	 * 返回模块信息
	 */
	public String toString() {
		String s;
		s = "path=" + path + ";action=" + action + "\n\r";
		return s;
	}

	public final Boolean getAutoToken() {
		return autoToken;
	}

	public final void setAutoToken(Boolean autoToken) {
		this.autoToken = autoToken;
	}

	public List getAlias() {
		return alias;
	}

	public String getMessageResource() {
		return messageResource;
	}

	public void setMessageResource(String messageResource) {
		this.messageResource = messageResource;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setAlias(String alias) {
		String[] as = StringUtils.tokenizeToStringArray(alias, ",");
		if (as != null && as.length > 0) {
			for (int i = 0; i < as.length; i++) {
				String a = as[i];
				if (a.indexOf(0) != '/')
					a = "/" + a;
				this.alias.add(a);
			}
		}
	}
}
