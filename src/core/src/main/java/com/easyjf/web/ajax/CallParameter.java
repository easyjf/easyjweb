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
/**
 * JS远程脚本调用参数封装
 * @author 大峡
 *
 */
public class CallParameter {
private int index;
private Class type;
private String name;
private java.util.Map propetys=new java.util.HashMap();
public int getIndex() {
	return index;
}
public void setIndex(int index) {
	this.index = index;
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
public void addProperty(String name,Object value)
{
	this.propetys.put(name, value);
}
public java.util.Map getPropetys() {
	return propetys;
}
public void setPropetys(java.util.Map propetys) {
	this.propetys = propetys;
}
}
