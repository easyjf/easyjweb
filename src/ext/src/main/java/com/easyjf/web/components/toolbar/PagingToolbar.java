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
package com.easyjf.web.components.toolbar;

import com.easyjf.web.components.Store;
/**
 * 
 * @author 大峡
 *
 */
public class PagingToolbar extends Toolbar {
	private Store store;
	private Integer pageSize = 10;
	private Boolean displayInfo;
	private String displayMsg = "Displaying topics {0} - {1} of {2}";
	private String emptyMsg = "No topics to display";

	public PagingToolbar() {
		this(null);
	}

	public PagingToolbar(Store store) {
		this.store = store;
	}

	public PagingToolbar(Store store, Integer pageSize) {
		this.store = store;
		this.pageSize = pageSize;
	}

	public String clz() {
		return "Ext.PagingToolbar";
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Boolean getDisplayInfo() {
		return displayInfo;
	}

	public void setDisplayInfo(Boolean displayInfo) {
		this.displayInfo = displayInfo;
	}

	public String getDisplayMsg() {
		return displayMsg;
	}

	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	public String getEmptyMsg() {
		return emptyMsg;
	}

	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
	}

	@Override
	public boolean isLazy() {
		return false;
	}

	@Override
	public String getInitProperty() {
		return null;
	}
	
}
