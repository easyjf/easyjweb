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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.easyjf.beans.BeanUtils;
import com.easyjf.web.components.DataField;
import com.easyjf.web.components.Function;
import com.easyjf.web.components.Panel;
import com.easyjf.web.components.Store;
import com.easyjf.web.components.form.TextField;
/**
 * 
 * @author 大峡
 *
 */
public class GridPanel extends Panel {
	private List<Column> columns = new java.util.ArrayList<Column>();
	private Boolean loadMask;
	private Store store;
	private String[] excludeColumns;
	private String[] includeColumns;
	private Boolean enableEdit;
	private boolean defaultSort;
	private String url;
	
	private String autoExpandColumn;
	private Boolean autoExpandMax;
	private Boolean autoExpandMin;
	private Boolean disableSelection;
	private Boolean enableColumnHide;
	private Boolean enableColumnMove;
	private Boolean enableColumnResize;
	private Boolean enableHdMenu;
	private Integer maxHeight;
	private Integer minColumnWidth;
	private Boolean trackMouseOver;
	private Map<String,Object>  viewConfig=new java.util.HashMap<String, Object>();

	public GridPanel() {
		this(null);
	}

	public GridPanel(String id) {
		this(id, null);
	}

	public GridPanel(String id, String title) {
		this(id, title, null);
	}

	public GridPanel(String id, String title, Integer width) {
		this(id, title, width, null);
	}

	public GridPanel(String id, String title, Integer width, Integer height) {
		this.id = id;
		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);
		this.init();
	}

	protected void init() {
		super.init();
		this.setLazy(false);
	}

	public void bind(Class clz) {
		bind(clz, null);
	}

	public void bind(Class clz, String url) {
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clz);
		List<DataField> fields = new java.util.ArrayList<DataField>();
		List<Column> columns = new java.util.ArrayList<Column>();
		for (PropertyDescriptor pd : pds) {
			String name = pd.getName();
			if (pd.getReadMethod() != null
					&& pd.getReadMethod().getDeclaringClass() != Object.class
					&& isDisplay(name)) {
				Column c = createColumn(name, null);
				Field f = findField(clz, name);
				if (f != null) {
					com.easyjf.container.annonation.Field af = f
							.getAnnotation(com.easyjf.container.annonation.Field.class);
					if (af != null) {
						c.setHeader(af.name());
					}
				}
				columns.add(c);
				DataField field = new DataField(name);
				fields.add(field);
			}
		}
		Store store = new Store(url, fields);
		store.setVarName("store");
		store.setId("id");// 主键名称，默认为id
		this.setStore(store);
		this.setColumns(columns);
	}

	private Column createColumn(String name, String title) {
		Column c = new Column(name);
		columns.add(c);
		if (title != null)
			c.setHeader(title);
		if (this.defaultSort)
			c.setSortable(true);
		if (this.enableEdit != null && this.enableEdit) {
			TextField editor = new TextField(c.getDataIndex(), c.getHeader());
			editor.setLazy(false);
			c.setEditor(editor);
		}
		return c;
	}

	public void addClumn(String name, String title) {
		if (this.searchColumn(name) < 0) {
			Column c = createColumn(name, title);
			this.columns.add(c);
			this.store.addField(name);
		}
	}

	public void removeColumn(String name) {
		int r = this.searchColumn(name);
		if (r >= 0) {
			this.columns.remove(r);
			this.store.removeField(name);
		}
	}

	protected Field findField(Class clz, String name) {
		Field f = null;
		try {
			f = clz.getDeclaredField(name);
		} catch (NoSuchFieldException nfe) {
		}
		if (f == null) {
			while (clz.getSuperclass() != Object.class)
				f = findField(clz.getSuperclass(), name);
		}
		return f;
	}

	public void swap(String sName, String tName) {
		this.swap(searchColumn(sName), searchColumn(tName));
	}

	public void swap(int sIndex, int tIndex) {
		if (sIndex >= 0 && tIndex >= 0) {
			Column c = this.columns.get(sIndex);
			this.columns.set(sIndex, this.columns.get(tIndex));
			this.columns.set(tIndex, c);
		}
	}

	protected int searchColumn(String name) {
		int ret = -1;
		for (int i = 0; i < this.columns.size(); i++) {
			if (name.equals(this.columns.get(i).getDataIndex()))
				ret = i;
		}
		return ret;
	}

	public Column findColumn(String name) {
		return findColumn(this.searchColumn(name));
	}

	public Column findColumn(int index) {
		return (index >= 0 ? this.columns.get(index) : null);
	}

	protected boolean isDisplay(String name) {
		boolean ret = true;
		if (this.includeColumns != null) {
			ret = java.util.Arrays.asList(this.includeColumns).contains(name);
		}
		if (this.excludeColumns != null) {
			ret = !java.util.Arrays.asList(this.excludeColumns).contains(name);
		}
		return ret;
	}

	public void bind(Object obj) {

	}

	@Override
	public String getXtype() {
		return this.enableEdit != null && this.enableEdit ? "editorgrid"
				: "grid";
	}

	@Override
	public String clz() {
		return this.enableEdit != null && this.enableEdit ? "Ext.grid.EditorGridPanel"
				: "Ext.grid.GridPanel";
	}

	public Boolean getLoadMask() {
		return loadMask;
	}

	public void setLoadMask(Boolean loadMask) {
		this.loadMask = loadMask;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public void setExcludeColumns(String[] excludeColumns) {
		this.excludeColumns = excludeColumns;
	}

	public void setIncludeColumns(String[] includeColumns) {
		this.includeColumns = includeColumns;
	}

	public List<Column> getColumns() {
		return columns;
	}

	public void setColumns(List<Column> columns) {
		this.columns = columns;
		this.set("autoExpandColumn", columns.size() - 1);
	}

	public void setColumns(String[] columns) {
		List<Column> cs = new java.util.ArrayList<Column>();
		List<DataField> fields = new java.util.ArrayList<DataField>();
		for (int i = 0; i < columns.length; i++) {
			Column c = this.createColumn(columns[i], null);
			cs.add(c);
			DataField field = new DataField(columns[i]);
			fields.add(field);
		}
		Store store = new Store(this.url, fields);
		store.setVarName("store");
		store.setId("id");// 主键名称，默认为id
		this.setStore(store);
		this.setColumns(cs);
	}

	public void setColumns(String[][] columns) {
		List<Column> cs = new java.util.ArrayList<Column>();
		List<DataField> fields = new java.util.ArrayList<DataField>();
		for (int i = 0; i < columns.length; i++) {
			Column c = this.createColumn(columns[i][0], columns[i][1]);
			cs.add(c);
			DataField field = new DataField(columns[i][0]);
			fields.add(field);
		}
		Store store = new Store(this.url, fields);
		store.setVarName("store");
		store.setId("id");// 主键名称，默认为id
		this.setStore(store);
		this.setColumns(cs);
	}

	public void setEnableEdit(Boolean enableEdit) {
		this.enableEdit = enableEdit;
	}

	public void load() {
		//Function r=null;//(Function)this.getListeners().get("render");
		//if(r==null)r=new Function("");r.setCode(r.getCode()+";");
		this.addListener("render", new Function("this.store.load();"));
		
	}

	public void setDefaultSort(boolean defaultSort) {
		this.defaultSort = defaultSort;
	}

	public void setUrl(String url) {
		this.url = url;
		if (this.store == null)
			this.store = new Store(url);
		else
			this.store.setUrl(url);
	}
	//普通的getter及setter

	public String getAutoExpandColumn() {
		return autoExpandColumn;
	}

	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
	}

	public Boolean getAutoExpandMax() {
		return autoExpandMax;
	}

	public void setAutoExpandMax(Boolean autoExpandMax) {
		this.autoExpandMax = autoExpandMax;
	}

	public Boolean getAutoExpandMin() {
		return autoExpandMin;
	}

	public void setAutoExpandMin(Boolean autoExpandMin) {
		this.autoExpandMin = autoExpandMin;
	}

	public Boolean getDisableSelection() {
		return disableSelection;
	}

	public void setDisableSelection(Boolean disableSelection) {
		this.disableSelection = disableSelection;
	}

	public Boolean getEnableColumnHide() {
		return enableColumnHide;
	}

	public void setEnableColumnHide(Boolean enableColumnHide) {
		this.enableColumnHide = enableColumnHide;
	}

	public Boolean getEnableColumnMove() {
		return enableColumnMove;
	}

	public void setEnableColumnMove(Boolean enableColumnMove) {
		this.enableColumnMove = enableColumnMove;
	}

	public Boolean getEnableColumnResize() {
		return enableColumnResize;
	}

	public void setEnableColumnResize(Boolean enableColumnResize) {
		this.enableColumnResize = enableColumnResize;
	}

	public Boolean getEnableHdMenu() {
		return enableHdMenu;
	}

	public void setEnableHdMenu(Boolean enableHdMenu) {
		this.enableHdMenu = enableHdMenu;
	}

	public Integer getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Integer getMinColumnWidth() {
		return minColumnWidth;
	}

	public void setMinColumnWidth(Integer minColumnWidth) {
		this.minColumnWidth = minColumnWidth;
	}

	public Boolean getTrackMouseOver() {
		return trackMouseOver;
	}

	public void setTrackMouseOver(Boolean trackMouseOver) {
		this.trackMouseOver = trackMouseOver;
	}

	public Map<String, Object> getViewConfig() {
		return viewConfig;
	}

	public void setViewConfig(Map<String, Object> viewConfig) {
		this.viewConfig = viewConfig;
	}
	
}
