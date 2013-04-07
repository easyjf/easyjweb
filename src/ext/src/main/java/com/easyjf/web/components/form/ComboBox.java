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

import com.easyjf.web.components.Store;

public class ComboBox extends TextField {
	private String displayField;
	private Boolean editable;
	private Boolean forceSelection;
	private Integer handleHeight;
	private String hiddenName;
	private Boolean lazyInit;
	private Boolean lazyRender;
	private String  listAlign;
	private Integer listWidth;
	private String loadingText;
	private Integer maxHeight;
	private Integer minChars;
	private Integer minListWidth;
	private Integer pageSize;
	private Integer queryDelay;
	private String  queryParam;
	private Boolean resizable;
	private Boolean selectOnFocus;
	private Store store;
	private String title;
	private String transform;
	private String valueField;
	private String valueNotFoundText;
	
	public ComboBox() {
		this(null,null);
	}

	public ComboBox(String name, String label) {
		this(name,label,null);
	}

	public ComboBox(String name, String label, Object value) {
		this(name,label,value,null);
	}

	public ComboBox(String name, String label, Object value, Integer width) {
		super(name, label, value, width);
		this.init();
	}	
	protected void init()
	{
		this.setXtype("combo");
	}
	@Override
	public String clz() {
	
		return "Ext.form.ComboBox";
	}

	public String getDisplayField() {
		return displayField;
	}

	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getForceSelection() {
		return forceSelection;
	}

	public void setForceSelection(Boolean forceSelection) {
		this.forceSelection = forceSelection;
	}

	public Integer getHandleHeight() {
		return handleHeight;
	}

	public void setHandleHeight(Integer handleHeight) {
		this.handleHeight = handleHeight;
	}

	public String getHiddenName() {
		return hiddenName;
	}

	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}

	public Boolean getLazyInit() {
		return lazyInit;
	}

	public void setLazyInit(Boolean lazyInit) {
		this.lazyInit = lazyInit;
	}

	public Boolean getLazyRender() {
		return lazyRender;
	}

	public void setLazyRender(Boolean lazyRender) {
		this.lazyRender = lazyRender;
	}

	public String getListAlign() {
		return listAlign;
	}

	public void setListAlign(String listAlign) {
		this.listAlign = listAlign;
	}

	public Integer getListWidth() {
		return listWidth;
	}

	public void setListWidth(Integer listWidth) {
		this.listWidth = listWidth;
	}

	public String getLoadingText() {
		return loadingText;
	}

	public void setLoadingText(String loadingText) {
		this.loadingText = loadingText;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Integer getMinChars() {
		return minChars;
	}

	public void setMinChars(Integer minChars) {
		this.minChars = minChars;
	}

	public Integer getMinListWidth() {
		return minListWidth;
	}

	public void setMinListWidth(Integer minListWidth) {
		this.minListWidth = minListWidth;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getQueryDelay() {
		return queryDelay;
	}

	public void setQueryDelay(Integer queryDelay) {
		this.queryDelay = queryDelay;
	}

	public String getQueryParam() {
		return queryParam;
	}

	public void setQueryParam(String queryParam) {
		this.queryParam = queryParam;
	}

	public Boolean getResizable() {
		return resizable;
	}

	public void setResizable(Boolean resizable) {
		this.resizable = resizable;
	}

	public Boolean getSelectOnFocus() {
		return selectOnFocus;
	}

	public void setSelectOnFocus(Boolean selectOnFocus) {
		this.selectOnFocus = selectOnFocus;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTransform() {
		return transform;
	}

	public void setTransform(String transform) {
		this.transform = transform;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getValueNotFoundText() {
		return valueNotFoundText;
	}

	public void setValueNotFoundText(String valueNotFoundText) {
		this.valueNotFoundText = valueNotFoundText;
	}
}
