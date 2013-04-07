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

import java.util.Map;

import com.easyjf.web.components.Panel;
/**
 * 
 * @author 大峡
 *
 */
public class Form extends Panel {
	private String labelAlign;
	private Integer labelWidth;
	private String method;
	private String url;
	private Boolean fileUpload;
	private Map<String,String> baseParams=new java.util.HashMap<String, String>();
	private Integer timeout;
	private Boolean  trackResetOnLoad;
	public Form() {
		this(null);
	}

	public Form(String id) {
		this(id, null);
	}

	public Form(String id, String url) {
		this(id, url, null);
	}

	public Form(String id, String url, String method) {
		this.setId(id);
		this.url = url;
		this.method = method;
		this.init();
	}

	protected void init() {
		super.init();
		this.setXtype("form");
	}

	@Override
	public String clz() {
		return "Ext.form.FormPanel";
	}

	/**
	 * 把整个表单与指定对象绑定
	 * 
	 * @param obje
	 */
	public void bind(Object obje) {

	}

	public String getLabelAlign() {
		return labelAlign;
	}

	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	public Integer getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(Boolean fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Map<String, String> getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(Map<String, String> baseParams) {
		this.baseParams = baseParams;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Boolean getTrackResetOnLoad() {
		return trackResetOnLoad;
	}

	public void setTrackResetOnLoad(Boolean trackResetOnLoad) {
		this.trackResetOnLoad = trackResetOnLoad;
	}
}
