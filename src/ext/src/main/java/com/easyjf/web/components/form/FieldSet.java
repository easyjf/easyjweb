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

import com.easyjf.web.components.Panel;
/**
 * 
 * @author 大峡
 *
 */
public class FieldSet extends Panel {
	private Integer labelWidth;
	private String checkboxName;
	private Boolean checkboxToggle;

	public FieldSet() {
		this((String)null);
	}

	public FieldSet(String title) {
		this(title,null);
	}

	public FieldSet(Integer labelWidth) {
		this(null, labelWidth);
	}

	public FieldSet(String title, Integer labelWidth) {
		this.setTitle(title);
		this.labelWidth = labelWidth;
		this.init();
	}

	protected void init() {
		super.init();
		this.setXtype("fieldset");
		this.setLayout("form");
	}

	@Override
	public String clz() {
		return "Ext.form.FieldSet";
	}

	public Integer getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getCheckboxName() {
		return checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public Boolean getCheckboxToggle() {
		return checkboxToggle;
	}

	public void setCheckboxToggle(Boolean checkboxToggle) {
		this.checkboxToggle = checkboxToggle;
	}
}
