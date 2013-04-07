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
import java.util.Map;
/**
 * 
 * @author 大峡
 *
 */
public class Store extends BaseComponent {
	private Map<String, Object> baseParams = new java.util.HashMap<String, Object>();
	private Map<String, Object> paramNames=new java.util.HashMap<String, Object>();
	private String url;
	private Boolean remoteSort;
	private String root="result";
	private String totalProperty="rowCount";	
	private List<DataField> fields = new java.util.ArrayList<DataField>();

	public Store() {
		this(null);
	}

	public Store(String url) {
		this(url, (Map) null);
	}

	public Store(String url, List<DataField> fields) {
		this(url, null, fields);
	}

	public Store(String url, Map<String, Object> baseParams) {
		this(url, baseParams, null);
	}

	public Store(String url, Map<String, Object> baseParams, List<DataField> fields) {
		this.url = url;
		if (baseParams != null)
			this.setBaseParams(baseParams);
		if (fields != null)
			this.setFields(fields);
		this.setLazy(false);
		this.paramNames.put("sort", "orderBy");
		this.paramNames.put("dir", "orderType");
	}

	public String clz() {
		return "Ext.data.JsonStore";
	}

	public Map<String, Object> getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(Map<String, Object> baseParams) {
		this.baseParams = baseParams;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getRemoteSort() {
		return remoteSort;
	}

	public void setRemoteSort(Boolean remoteSort) {
		this.remoteSort = remoteSort;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public int findField(String name)
	{
		int ret=-1;
		for(int i=0;i<this.fields.size();i++)
		{
			if(name.equals(this.fields.get(i).getName()))ret=i;
		}
		return ret;
	}
	public void addField(String name)
	{
		DataField field = new DataField(name);
		this.fields.add(field);
	}
	public void removeField(String name)
	{
		int r=findField(name);
		if(r>=0)this.fields.remove(r);
	}
	
	public List<DataField> getFields() {
		return fields;
	}

	public void setFields(List<DataField> fields) {
		this.fields = fields;
	}

	public String getTotalProperty() {
		return totalProperty;
	}

	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	public Map<String, Object> getParamNames() {
		return paramNames;
	}

	public void setParamNames(Map<String, Object> paramNames) {
		this.paramNames = paramNames;
	}
}
