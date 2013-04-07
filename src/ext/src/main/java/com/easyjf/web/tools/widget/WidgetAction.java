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
package com.easyjf.web.tools.widget;

import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractCmdAction;

/**
 * 实用控件集合，给Web应用程序开发提供实用的控件。
 * 所有的控件调用通过这一个action来处理
 * @author 大峡,stef_wu
 *
 */
public class WidgetAction extends AbstractCmdAction {
	@Override
	public Page doInit(WebForm form, Module module) {
		// TODO Auto-generated method stub
		return null;
	}
	//日历控件，用来选择录入日期
	public Page doCalendar(WebForm form, Module module) {
	return module.findPage("calendar");
	}
	public Page doLoadPage(WebForm form, Module module) {
	String url=(String)form.get("url");	
	return new Page(url,url,"template");
	}
}
