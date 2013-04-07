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

public class ConstructorArgumentValue implements java.lang.Comparable<ConstructorArgumentValue> {
	private Integer index;

	private Class type;

	private Object value;

	public ConstructorArgumentValue() {

	}

	public ConstructorArgumentValue(Integer index, Class type, Object value) {
		this.index = index;
		this.type = type;
		this.value = value;
	}
	
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int hashCode() {	
		return index!=null?index.hashCode():value.hashCode();
	}

	public int compareTo(ConstructorArgumentValue o) {	
		return this.index-o.index;
	}
	
}