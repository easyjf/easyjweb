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
package com.easyjf.web.components;

public class SplitButton extends Button {
	private String arrowTooltip;
	private Function arrowHandle;

	public SplitButton() {
		this(null);
	}

	public SplitButton(String text) {
		this(text, null);
	}

	public SplitButton(String text, Function handler) {
		super(text, handler);
	}

	@Override
	public String getXtype() {
		return "splitbutton";
	}

	@Override
	public String clz() {
		return "Ext.SplitButton";
	}

	public String getArrowTooltip() {
		return arrowTooltip;
	}

	public void setArrowTooltip(String arrowTooltip) {
		this.arrowTooltip = arrowTooltip;
	}

	public Function getArrowHandle() {
		return arrowHandle;
	}

	public void setArrowHandle(Function arrowHandle) {
		this.arrowHandle = arrowHandle;
	}

}
