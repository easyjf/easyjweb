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

public class Checkbox extends Field {
	private String boxLabel;
	private Boolean checked;
	private String inputValue;

	public Checkbox() {
		this(null, null);
	}

	public Checkbox(String name, String label) {
		this(name, label, null);
	}

	public Checkbox(String name, String label, Object value) {
		this(name, label, value, null);
	}

	public Checkbox(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}

	protected void init() {
		this.setXtype("checkbox");
	}

	@Override
	public String clz() {
		return "Ext.form.Checkbox";
	}

	public String getBoxLabel() {
		return boxLabel;
	}

	public void setBoxLabel(String boxLabel) {
		this.boxLabel = boxLabel;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
}
