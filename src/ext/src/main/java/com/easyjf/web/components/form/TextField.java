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

import com.easyjf.web.components.Function;

/**
 * 
 * @author 大峡
 *
 */
public class TextField extends Field {
	private String blankText;
	private Boolean disableKeyFilter;
	private String emptyText;
	private Integer maxLength;
	private String  maxLengthText;
	private Integer minLength;
	private String minLengthText;
	private Boolean selectOnFocus;
	private Function validator;
	private Boolean grow;
	private Integer growMax;
	private Integer growMin;
	public TextField() {
		this(null,null);
	}

	public TextField(String name, String label) {
		this(name,label,null);
	}

	public TextField(String name, String label, Object value) {
		this(name,label,value,null);
	}

	public TextField(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}

	protected void init() {
		this.setXtype("textfield");
	}

	@Override
	public String clz() {
		return "Ext.form.TextField";
	}

	public String getBlankText() {
		return blankText;
	}

	public void setBlankText(String blankText) {
		this.blankText = blankText;
	}

	public Boolean getDisableKeyFilter() {
		return disableKeyFilter;
	}

	public void setDisableKeyFilter(Boolean disableKeyFilter) {
		this.disableKeyFilter = disableKeyFilter;
	}

	public String getEmptyText() {
		return emptyText;
	}

	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public String getMaxLengthText() {
		return maxLengthText;
	}

	public void setMaxLengthText(String maxLengthText) {
		this.maxLengthText = maxLengthText;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getMinLengthText() {
		return minLengthText;
	}

	public void setMinLengthText(String minLengthText) {
		this.minLengthText = minLengthText;
	}

	public Boolean getSelectOnFocus() {
		return selectOnFocus;
	}

	public void setSelectOnFocus(Boolean selectOnFocus) {
		this.selectOnFocus = selectOnFocus;
	}

	public Function getValidator() {
		return validator;
	}

	public void setValidator(Function validator) {
		this.validator = validator;
	}

	public Boolean getGrow() {
		return grow;
	}

	public void setGrow(Boolean grow) {
		this.grow = grow;
	}

	public Integer getGrowMax() {
		return growMax;
	}

	public void setGrowMax(Integer growMax) {
		this.growMax = growMax;
	}

	public Integer getGrowMin() {
		return growMin;
	}

	public void setGrowMin(Integer growMin) {
		this.growMin = growMin;
	}	
}
