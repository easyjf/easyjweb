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

/**
 * 
 * <p>
 * Title:表单属性配置信息
 * </p>
 * <p>
 * Description: 处理easyjf-web.xml文件中的配置有关表单字段属性的信息
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
public class FormProperty {
	private String name;

	private String initial;

	private String size;

	private String type;

	private String notNull;

	private List event;

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial == null ? "" : initial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? "" : name;
	}

	public String getNotNull() {
		return notNull;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull == null ? "" : notNull;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size == null ? "" : size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? "" : type;
	}

	public List getEvent() {
		return event;
	}

	public void setEvent(List event) {
		this.event = event;
	}

	public String toString() {
		String s;
		s = "name=" + name + ";rinitial=" + initial + ";size=" + size
				+ ";type=" + type + ";notNull=" + notNull;
		return s;
	}
}
