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
 * @author 大峡
 *
 */
public class Window extends Panel {
	private String closeAction;
	private Boolean constrain;
	private Boolean constrainHeader;
	private Button defaultButton;
	private Boolean expandOnShow;
	private Boolean maximizable;
	private Integer minHeight;
	private Integer minWidth;
	private Boolean minimizabl;
	private Boolean modal;
	private Function onEsc;
	private Boolean plain;
	private Boolean resizable;
	
	public Window() {
		this.init();
	}

	public Window(String id, String title, Integer width, Integer height) {
		super(id, title, width, height);
		this.init();
	}

	protected void init() {
		super.init();
		this.setLayout(null);
		this.set("initMethod", "show");
	}

	public String clz() {
		return "Ext.Window";
	}

	public String getCloseAction() {
		return closeAction;
	}

	public void setCloseAction(String closeAction) {
		this.closeAction = closeAction;
	}

	public Boolean getConstrain() {
		return constrain;
	}

	public void setConstrain(Boolean constrain) {
		this.constrain = constrain;
	}

	public Boolean getConstrainHeader() {
		return constrainHeader;
	}

	public void setConstrainHeader(Boolean constrainHeader) {
		this.constrainHeader = constrainHeader;
	}

	public Button getDefaultButton() {
		return defaultButton;
	}

	public void setDefaultButton(Button defaultButton) {
		this.defaultButton = defaultButton;
	}

	public Boolean getExpandOnShow() {
		return expandOnShow;
	}

	public void setExpandOnShow(Boolean expandOnShow) {
		this.expandOnShow = expandOnShow;
	}

	public Boolean getMaximizable() {
		return maximizable;
	}

	public void setMaximizable(Boolean maximizable) {
		this.maximizable = maximizable;
	}

	public Integer getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	public Integer getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(Integer minWidth) {
		this.minWidth = minWidth;
	}

	public Boolean getMinimizabl() {
		return minimizabl;
	}

	public void setMinimizabl(Boolean minimizabl) {
		this.minimizabl = minimizabl;
	}

	public Boolean getModal() {
		return modal;
	}

	public void setModal(Boolean modal) {
		this.modal = modal;
	}

	public Function getOnEsc() {
		return onEsc;
	}

	public void setOnEsc(Function onEsc) {
		this.onEsc = onEsc;
	}

	public Boolean getPlain() {
		return plain;
	}

	public void setPlain(Boolean plain) {
		this.plain = plain;
	}

	public Boolean getResizable() {
		return resizable;
	}

	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}	
}
