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

import java.util.List;

public class DateField extends ComboBox {
	private List<String> disabledDates=new java.util.ArrayList<String>();
	private String disabledDatesText;
	private List<String> disabledDays=new java.util.ArrayList<String>();
	private String disabledDaysText;
	private String format;
	private String invalidText;
	private String  maxText;
	private String maxValue;
	private String minText;
	private String minValue;
	
	public DateField() {
		this(null,null);
	}

	public DateField(String name, String label) {
		this(name,label,null);
	}

	public DateField(String name, String label, Object value) {
		this(name,label,value,null);
	}

	public DateField(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}	
	protected void init()
	{
		this.setXtype("datefield");
	}
	@Override
	public String clz() {
	
		return "Ext.form.DateField";
	}

	public List<String> getDisabledDates() {
		return disabledDates;
	}

	public void setDisabledDates(List<String> disabledDates) {
		this.disabledDates = disabledDates;
	}

	public String getDisabledDatesText() {
		return disabledDatesText;
	}

	public void setDisabledDatesText(String disabledDatesText) {
		this.disabledDatesText = disabledDatesText;
	}

	public List<String> getDisabledDays() {
		return disabledDays;
	}

	public void setDisabledDays(List<String> disabledDays) {
		this.disabledDays = disabledDays;
	}

	public String getDisabledDaysText() {
		return disabledDaysText;
	}

	public void setDisabledDaysText(String disabledDaysText) {
		this.disabledDaysText = disabledDaysText;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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
