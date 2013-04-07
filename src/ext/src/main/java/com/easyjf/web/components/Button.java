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
/**
 * 
 * @author daxia
 *
 */
public class Button extends BaseComponent {
	private Boolean disabled;
	private Boolean enableToggle;
	private Boolean hidden;
	private Boolean pressed;
	private Integer tabIndex;
	private String  tooltip;
	private String text;	
	private Function handler;
	public Button()
	{
		this("");
	}
	public Button(String text)
	{
		this(text,null);
	}
	public Button(String text,Function handler)
	{
		this.text=text;
		this.handler=handler;
		this.setXtype("button");
	}
	
	public String clz() {
		return "Ext.Button";
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Function getHandler() {
		return handler;
	}

	public void setHandler(Function handler) {
		this.handler = handler;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean getEnableToggle() {
		return enableToggle;
	}
	public void setEnableToggle(Boolean enableToggle) {
		this.enableToggle = enableToggle;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public Boolean getPressed() {
		return pressed;
	}
	public void setPressed(Boolean pressed) {
		this.pressed = pressed;
	}
	public Integer getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
}
