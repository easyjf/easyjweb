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
public class TabPanel extends Panel {
	private Integer activeTab;
	private Boolean enableTabScroll;
	private Integer tabWidth;
	private Boolean animScroll;
	private String autoTabSelector;
	private Boolean autoTabs;
	private Boolean deferredRender;
	private Boolean layoutOnTabChange;
	private Integer minTabWidth;
	private Boolean monitorResize ;
	private Boolean plain;
	private Boolean resizeTabs;
	private Float scrollDuration;
	private String tabPosition;
	
	public TabPanel() {
		this(null);
	}

	public TabPanel(String id) {
		this(id, null);

	}

	public TabPanel(String id, Integer width) {
		this(id, width, null);
	}

	public TabPanel(String id, Integer width, Integer height) {
		this.id = id;
		this.setWidth(width);
		this.setHeight(height);
	}

	protected void init() {
		super.init();
		this.setXtype("tabpanel");
	}

	@Override
	public String clz() {
		return "Ext.TabPanel";
	}

	public Integer getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(Integer activeTab) {
		this.activeTab = activeTab;
	}

	public Boolean getEnableTabScroll() {
		return enableTabScroll;
	}

	public void setEnableTabScroll(Boolean enableTabScroll) {
		this.enableTabScroll = enableTabScroll;
	}

	public Integer getTabWidth() {
		return tabWidth;
	}

	public void setTabWidth(Integer tabWidth) {
		this.tabWidth = tabWidth;
	}

	public Boolean getAnimScroll() {
		return animScroll;
	}

	public void setAnimScroll(Boolean animScroll) {
		this.animScroll = animScroll;
	}

	public String getAutoTabSelector() {
		return autoTabSelector;
	}

	public void setAutoTabSelector(String autoTabSelector) {
		this.autoTabSelector = autoTabSelector;
	}

	public Boolean getAutoTabs() {
		return autoTabs;
	}

	public void setAutoTabs(Boolean autoTabs) {
		this.autoTabs = autoTabs;
	}

	public Boolean getDeferredRender() {
		return deferredRender;
	}

	public void setDeferredRender(Boolean deferredRender) {
		this.deferredRender = deferredRender;
	}

	public Boolean getLayoutOnTabChange() {
		return layoutOnTabChange;
	}

	public void setLayoutOnTabChange(Boolean layoutOnTabChange) {
		this.layoutOnTabChange = layoutOnTabChange;
	}

	public Integer getMinTabWidth() {
		return minTabWidth;
	}

	public void setMinTabWidth(Integer minTabWidth) {
		this.minTabWidth = minTabWidth;
	}

	public Boolean getMonitorResize() {
		return monitorResize;
	}

	public void setMonitorResize(Boolean monitorResize) {
		this.monitorResize = monitorResize;
	}

	public Boolean getPlain() {
		return plain;
	}

	public void setPlain(Boolean plain) {
		this.plain = plain;
	}

	public Boolean getResizeTabs() {
		return resizeTabs;
	}

	public void setResizeTabs(Boolean resizeTabs) {
		this.resizeTabs = resizeTabs;
	}

	public Float getScrollDuration() {
		return scrollDuration;
	}

	public void setScrollDuration(Float scrollDuration) {
		this.scrollDuration = scrollDuration;
	}

	public String getTabPosition() {
		return tabPosition;
	}

	public void setTabPosition(String tabPosition) {
		this.tabPosition = tabPosition;
	}	
}
