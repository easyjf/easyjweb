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
package com.easyjf.web.components.grid;

import com.easyjf.web.components.BaseComponent;
import com.easyjf.web.components.Function;
import com.easyjf.web.components.form.Field;
/**
 * 
 * @author 大峡
 *
 */
public class Column extends BaseComponent {
	private String dataIndex;
	private Field editor;
	private Boolean fixed;
	private String header;
	private Function renderer;
	private Boolean sortable;
	private Integer width;
	private String align;
	private Boolean hidden;

	public Column() {
		this(null);
	}
	public Column(String dataIndex) {
		this(dataIndex, dataIndex);
	}
	public Column(String dataIndex,String header) {
		this(dataIndex, header, (Integer)null);
	}
	public Column(String dataIndex,String header,Boolean sortable) {
		this(dataIndex, header);
		this.sortable=sortable;
	}
	public Column(String dataIndex, String header, Integer width) {
		this.dataIndex = dataIndex;
		this.header = header;
		this.width = width;
	}

	public String clz() {
		return null;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public Field getEditor() {
		return editor;
	}

	public void setEditor(Field editor) {
		this.editor = editor;
	}

	public Boolean getFixed() {
		return fixed;
	}

	public void setFixed(Boolean fixed) {
		this.fixed = fixed;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Function getRenderer() {
		return renderer;
	}

	public void setRenderer(Function renderer) {
		this.renderer = renderer;
	}

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
}
