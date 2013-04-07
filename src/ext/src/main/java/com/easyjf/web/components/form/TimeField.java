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

import java.util.Date;

public class TimeField extends ComboBox {
	private String altFormats;
	private String format;
	private Integer increment;
	private String invalidText;
	private String  maxText;
	private String maxValue;
	private String minText;
	private String minValue;
	public TimeField() {
		this(null,null);
	}

	public TimeField(String name, String label) {
		this(name,label,null);
	}

	public TimeField(String name, String label, Object value) {
		this(name,label,value,null);
	}

	public TimeField(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}
	protected void init()
	{
		this.setXtype("timefield");
	}
	@Override
	public String clz() {
	
		return "Ext.form.TimeField";
	}

	public String getAltFormats() {
		return altFormats;
	}

	public void setAltFormats(String altFormats) {
		this.altFormats = altFormats;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public String getInvalidText() {
		return invalidText;
	}

	public void setInvalidText(String invalidText) {
		this.invalidText = invalidText;
	}

	public String getMaxText() {
		return maxText;
	}

	public void setMaxText(String maxText) {
		this.maxText = maxText;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinText() {
		return minText;
	}

	public void setMinText(String minText) {
		this.minText = minText;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}	
}
