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

import com.easyjf.beans.BeanWrapper;
import com.easyjf.web.components.BoxComponent;
/**
 * 
 * @author 大峡
 *
 */
public abstract class Field extends BoxComponent {
	private Boolean disabled;
	private String fieldLabel;
	private Boolean hideLabel;
	private String inputType;
	private String labelSeparator;
	private String name;
	private Boolean readOnly;
	private Integer tabIndex;
	private String value;

	private String invalidText;
	public Field() {

	}

	public Field(String name, String label) {
		this(name, label, null, null);
	}

	public Field(String name, String label, Object value) {
		this(name, label, value, null);
	}

	public Field(String name, String label, Object value, Integer width) {
		this.name = name;
		this.fieldLabel = label;
		this.value = (value == null ? "" : value.toString());
		this.setWidth(width);
	}

	public String clz() {
		return "Ext.form.Field";
	}

	/**
	 * 把字段与某一对象进行绑定
	 * 
	 * @param object
	 * @param property
	 */
	public void bind(Object object, String property) {
		if (this.fieldLabel == null && !this.hideLabel) {
			this.fieldLabel = property;
		}
		BeanWrapper wrapper = new BeanWrapper(object);
		Object value = wrapper.getPropertyValue(property);
		this.value = (value == null ? "" : value.toString());
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getHideLabel() {
		return hideLabel;
	}

	public void setHideLabel(Boolean hideLabel) {
		this.hideLabel = hideLabel;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getLabelSeparator() {
		return labelSeparator;
	}

	public void setLabelSeparator(String labelSeparator) {
		this.labelSeparator = labelSeparator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getInvalidText() {
		return invalidText;
	}

	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}

}
