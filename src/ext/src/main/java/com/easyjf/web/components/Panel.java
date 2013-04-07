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

import java.util.List;

import com.easyjf.web.components.toolbar.Toolbar;
/**
 * 
 * @author 大峡
 *
 */
public class Panel extends Container {
	private List<Button> buttons = new java.util.ArrayList<Button>();
	private String title;
	private String html;
	private Boolean closable;
	private Boolean border;
	private String buttonAlign;
	private Boolean collapsed;
	private Boolean collapsible;
	private Boolean autoScroll;
	private Boolean bodyBorder;
	private Boolean collapseFirst;
	private Boolean draggable;
	private Boolean floating;
	private Boolean footer;
	private Boolean frame;
	private Boolean header;
	private Boolean headerAsText;
	private Boolean hideCollapseTool;
	private Boolean maskDisabled;
	private Integer minButtonWidth;
	private String shadow;	
	private Boolean titleCollapse;

	private List<IRichComponent> tbar = new java.util.ArrayList<IRichComponent>();
	private List<IRichComponent> bbar = new java.util.ArrayList<IRichComponent>();

	public Panel() {
		this(null);
	}

	public Panel(String id) {
		this(id, null, null, null);
	}

	public Panel(String id, String title) {
		this(id, title, null, null);
	}

	public Panel(String id, String title, String html) {
		this(id, title, null, null);
		this.setHtml(html);
	}

	public Panel(String id, String title, Integer width, Integer height) {
		this.setId(id);
		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);
		this.init();
	}

	protected void init() {
		this.setXtype("panel");
		this.set("initMethod", "render");
	}

	public String clz() {
		return "Ext.Panel";
	}

	public void show() {
		this.setInit(true);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public Panel addButtons(Button... buttons) {
		if (buttons != null) {
			for (Button b : buttons) {
				this.addButton(b);
			}
		}
		return this;
	}

	public Panel addButton(Button button) {
		this.buttons.add(button);
		return this;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public Boolean getClosable() {
		return closable;
	}

	public void setClosable(Boolean closable) {
		this.closable = closable;
	}

	public Boolean getBorder() {
		return border;
	}

	public void setBorder(Boolean border) {
		this.border = border;
	}

	public String getButtonAlign() {
		return buttonAlign;
	}

	public void setButtonAlign(String buttonAlign) {
		this.buttonAlign = buttonAlign;
	}

	public Boolean getCollapsed() {
		return collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	public Boolean getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(Boolean collapsible) {
		this.collapsible = collapsible;
	}

	public List<IRichComponent> getTbar() {
		return tbar;
	}

	public void addTbar(IRichComponent c) {
		this.tbar.add(c);
	}

	public void addTbar(IRichComponent... components) {
		if (components != null) {
			for (IRichComponent c : components)
				this.tbar.add(c);
		}
	}

	public void setTbar(List<IRichComponent> tbar) {
		this.tbar = tbar;
	}

	public void setTbar(Toolbar bar) {
		this.tbar.clear();
		this.set("tbar", bar);
	}

	public Object getBbar() {
		return this.bbar.isEmpty()?this.get("bbar"):this.bbar;
	}

	public void addBbar(IRichComponent c) {
		this.bbar.add(c);
	}

	public void setBbar(Toolbar bar) {
		this.bbar.clear();
		this.set("bbar", bar);
	}

	public void addBbar(IRichComponent... components) {
		if (components != null) {
			for (IRichComponent c : components)
				this.bbar.add(c);
		}
	}

	public void setBbar(List<IRichComponent> bbar) {
		this.bbar = bbar;
	}

	public Boolean getAutoScroll() {
		return autoScroll;
	}

	public void setAutoScroll(Boolean autoScroll) {
		this.autoScroll = autoScroll;
	}

	public Boolean getBodyBorder() {
		return bodyBorder;
	}

	public void setBodyBorder(Boolean bodyBorder) {
		this.bodyBorder = bodyBorder;
	}

	public Boolean getCollapseFirst() {
		return collapseFirst;
	}

	public void setCollapseFirst(Boolean collapseFirst) {
		this.collapseFirst = collapseFirst;
	}

	public Boolean getDraggable() {
		return draggable;
	}

	public void setDraggable(Boolean draggable) {
		this.draggable = draggable;
	}

	public Boolean getFloating() {
		return floating;
	}

	public void setFloating(Boolean floating) {
		this.floating = floating;
	}

	public Boolean getFooter() {
		return footer;
	}

	public void setFooter(Boolean footer) {
		this.footer = footer;
	}

	public Boolean getFrame() {
		return frame;
	}

	public void setFrame(Boolean frame) {
		this.frame = frame;
	}

	public Boolean getHeader() {
		return header;
	}

	public void setHeader(Boolean header) {
		this.header = header;
	}

	public Boolean getHeaderAsText() {
		return headerAsText;
	}

	public void setHeaderAsText(Boolean headerAsText) {
		this.headerAsText = headerAsText;
	}

	public Boolean getHideCollapseTool() {
		return hideCollapseTool;
	}

	public void setHideCollapseTool(Boolean hideCollapseTool) {
		this.hideCollapseTool = hideCollapseTool;
	}

	public Boolean getMaskDisabled() {
		return maskDisabled;
	}

	public void setMaskDisabled(Boolean maskDisabled) {
		this.maskDisabled = maskDisabled;
	}

	public Integer getMinButtonWidth() {
		return minButtonWidth;
	}

	public void setMinButtonWidth(Integer minButtonWidth) {
		this.minButtonWidth = minButtonWidth;
	}

	public String getShadow() {
		return shadow;
	}

	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	public Boolean getTitleCollapse() {
		return titleCollapse;
	}

	public void setTitleCollapse(Boolean titleCollapse) {
		this.titleCollapse = titleCollapse;
	}
	
}
