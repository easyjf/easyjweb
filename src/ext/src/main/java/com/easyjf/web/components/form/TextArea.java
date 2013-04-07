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
package com.easyjf.web.components.form;
/**
 * 
 * @author 大峡
 *
 */
public class TextArea extends TextField {
private Boolean preventScrollbars;
	public TextArea() {
		this(null,null);
	}

	public TextArea(String name, String label) {
		this(name,label,null);
	}

	public TextArea(String name, String label, Object value) {
		this(name,label,value,null,null);
	}
	public TextArea(String name, String label, Integer width,Integer height) {
		this(name, label, null, width,height);
		this.init();
	}
	public TextArea(String name, String label, Object value, Integer width,Integer height) {
		super(name, label, value, width);
		this.setHeight(height);
		this.init();
	}
	
	protected void init()
	{
		this.setXtype("textarea");
	}
	@Override
	public String clz() {
	
		return "Ext.form.TextArea";
	}

	public Boolean getPreventScrollbars() {
		return preventScrollbars;
	}

	public void setPreventScrollbars(Boolean preventScrollbars) {
		this.preventScrollbars = preventScrollbars;
	}
}
