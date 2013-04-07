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

public class NumberField extends TextField {
	private Boolean allowDecimals;
	private Boolean allowNegative;
	private String baseChars;
	private Integer decimalPrecision;
	private String decimalSeparator;
	private Number maxValue;
	private String maxText;
	private Number minValue;
	private String minText;
	
	public NumberField() {
		this(null,null);
	}

	public NumberField(String name, String label) {
		this(name,label,null);
	}

	public NumberField(String name, String label, Object value) {
		this(name,label,value,null);
	}

	public NumberField(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}	
	protected void init() {
		this.setXtype("numberfield");
	}

	@Override
	public String clz() {
		return "Ext.form.NumberField";
	}
	public Boolean getAllowDecimals() {
		return allowDecimals;
	}

	public void setAllowDecimals(Boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}

	public Boolean getAllowNegative() {
		return allowNegative;
	}

	public void setAllowNegative(Boolean allowNegative) {
		this.allowNegative = allowNegative;
	}

	public String getBaseChars() {
		return baseChars;
	}

	public void setBaseChars(String baseChars) {
		this.baseChars = baseChars;
	}

	public Integer getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(Integer decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public String getDecimalSeparator() {
		return decimalSeparator;
	}

	public void setDecimalSeparator(String decimalSeparator) {
		this.decimalSeparator = decimalSeparator;
	}

	public Number getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Number maxValue) {
		this.maxValue = maxValue;
	}

	public String getMaxText() {
		return maxText;
	}

	public void setMaxText(String maxText) {
		this.maxText = maxText;
	}

	public Number getMinValue() {
		return minValue;
	}

	public void setMinValue(Number minValue) {
		this.minValue = minValue;
	}

	public String getMinText() {
		return minText;
	}

	public void setMinText(String minText) {
		this.minText = minText;
	}


}
