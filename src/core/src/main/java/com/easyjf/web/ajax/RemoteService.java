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
package com.easyjf.web.ajax;

import java.util.HashSet;
import java.util.Set;

/**
 * 远程访问对象的定义信息
 * 
 * @author 大峡
 * 
 */
public class RemoteService {
	private String name;

	private Class type;

	private Set allowNames = new HashSet();

	private Set denyNames = new HashSet();
	
	/**
	 * 允许远程暴露的属性名集合
	 * 
	 * @return 属性名集合
	 */
	public Set getAllowNames() {
		return allowNames;
	}

	/**
	 * 添加一个远程访问的属性
	 * 
	 * @param name
	 *            属性名
	 */
	public void addAllowName(String name) {
		allowNames.add(name);
	}

	/**
	 * 禁止一个属性远程访问
	 * 
	 * @param name
	 *            属性名
	 */
	public void addDenyName(String name) {
		denyNames.add(name);
	}

	public void setAllowNames(Set allowMethods) {
		this.allowNames = allowMethods;
	}

	/**
	 * 获取所有禁止远程访问的属性名集合
	 * @return 所以禁止远程访问的属性集合
	 */
	public Set getDenyNames() {
		return denyNames;
	}

	public void setDenyNames(Set denyMethods) {
		this.denyNames = denyMethods;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String toString() {
		return "name="+name + ";type=" + type + ";allowNames=" + allowNames + ";denyNames=" + denyNames;
	}
}
