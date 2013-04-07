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
package com.easyjf.container;

/**
 * 自动注入属性的配置信息
 * 
 * @author 大峡
 * 
 */
public class AutoInject {

	private Class type;

	private String name;

	/**
	 * 自动注入名
	 * 
	 * @param name
	 *            注入的bean名称
	 */
	public AutoInject(String name) {
		this.name = name;
	}

	/**
	 * 自动注入type指定的类
	 * 
	 * @param type
	 *            需要注入的类型
	 */
	public AutoInject(Class type) {
		this.type = type;
	}

	/**
	 * 
	 * @return 返回自动注入的Bean的名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置自动注入类型Bean的名称
	 * 
	 * @param name
	 *            注入的bean名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return 返回自动注入Bean的类别
	 */
	public Class getType() {
		return type;
	}

	/**
	 * 设置自动注入Bean的类别
	 * 
	 * @param type
	 *            注入的类别
	 */
	public void setType(Class type) {
		this.type = type;
	}
}
