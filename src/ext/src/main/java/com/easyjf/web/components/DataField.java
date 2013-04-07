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
package com.easyjf.web.components;
/**
 * 
 * @author 大峡
 *
 */
public class DataField extends BaseComponent{
	private String name;
	private String mapping;
	private String type;
	private String sortType;
	private String format;

	public DataField() {
		this(null);
	}

	public DataField(String name) {
		this(name, null);
	}

	public DataField(String name, String mapping) {
		this(name, mapping, null);
	}

	public DataField(String name, String mapping, String type) {
		this(name, mapping, type, null);
	}

	public DataField(String name, String mapping, String type, String format) {
		this.name = name;
		this.mapping = mapping;
		this.type = type;
		this.format = format;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String clz() {
		return null;
	}
}
