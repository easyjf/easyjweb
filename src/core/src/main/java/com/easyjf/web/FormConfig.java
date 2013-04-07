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

import java.util.List;
import java.util.Map;
import java.util.Iterator;

/**
 * 
 * <p>
 * Title:表单配置信息
 * </p>
 * <p>
 * Description: 处理easyjf-web.xml文件中的配置有关表单定义的信息
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

public class FormConfig {
	private String name;

	private String bean;

	private String clientValidate;

	private String serverValidate;

	private String alertType;

	private Map propertys;

	private List event;

	/**
	 * 提示类型,支持js及page两种方式，主要用于支持EasyJWeb代码自动生成工具
	 * 
	 * @return alertType
	 */
	public String getAlertType() {
		return alertType;
	}

	/**
	 * 设置错误提示类型
	 * 
	 * @param alertType
	 */
	public void setAlertType(String alertType) {
		this.alertType = alertType == null ? "" : alertType;
	}

	/**
	 * 提以Form的bean类名。默认系统自动设置为WebForm类型
	 * 
	 * @return bean
	 */
	public String getBean() {
		return bean;
	}

	/**
	 * 设置Form的bean类名。该类必须继承WebForm
	 * 
	 * @param bean
	 */
	public void setBean(String bean) {
		this.bean = bean == null ? "" : bean;
	}

	/**
	 * 是否生成客户端校验代码
	 * 
	 * @return 客户校验
	 */
	public String getClientValidate() {
		return clientValidate;
	}

	/**
	 * 设置是否生成客户端校验代码
	 * 
	 * @param clientValidate
	 */
	public void setClientValidate(String clientValidate) {
		this.clientValidate = clientValidate == null ? "" : clientValidate;
	}

	/**
	 * 返回form名称
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置form名称
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name == null ? "" : name;
	}

	/**
	 * 返回form所有属性
	 * 
	 * @return propertys
	 */
	public Map getPropertys() {
		return propertys;
	}

	/**
	 * 设置form属性
	 * 
	 * @param propertys
	 */
	public void setPropertys(Map propertys) {
		this.propertys = propertys;
	}

	/**
	 * 是否支持服务器验证
	 * 
	 * @return serverValidate
	 */
	public String getServerValidate() {
		return serverValidate;
	}

	/**
	 * 设置服是否支持务器验证
	 * 
	 * @param serverValidate
	 */
	public void setServerValidate(String serverValidate) {
		this.serverValidate = serverValidate == null ? "" : serverValidate;
	}

	/**
	 * 返回Form类的事件类别
	 * 
	 * @return event
	 */
	public List getEvent() {
		return event;
	}

	/**
	 * 设置Form类的事件类别
	 * 
	 * @param event
	 */
	public void setEvent(List event) {
		this.event = event;
	}

	/**
	 * 输出Form信息
	 */
	public String toString() {
		String s = "name=" + name + ";bean=" + bean + ";clientValidate="
				+ clientValidate + ";rserverValidate=" + serverValidate
				+ ";alertType=" + alertType + "\n\r";
		Iterator it = propertys.values().iterator();
		while (it.hasNext()) {
			s += ((FormProperty) it.next()).toString() + "\n\r";
		}
		return s;
	}
}
