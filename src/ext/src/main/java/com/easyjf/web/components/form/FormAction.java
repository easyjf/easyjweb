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

import com.easyjf.web.components.BaseComponent;
import com.easyjf.web.components.Function;
/**
 * 
 * @author 大峡
 *
 */
public class FormAction extends BaseComponent {
	private Function failure;
	private Function success;
	private String url;
	private String waitMsg;
	private String waitTitle;
	private String method;
	private Map<String, Object> params = new java.util.HashMap<String, Object>();

	public String clz() {
		return null;
	}

	public FormAction() {
	}

	public FormAction(String id) {
		this(id, null);
	}

	public FormAction(String id, String url) {
		this(id, url, null);
	}

	public FormAction(String id, String url, String method) {
		this.id = id;
		this.url = url;
		this.method = method;
	}

	public FormAction(String id, String url, String waitMsg, String waitTitle) {
		this.id = id;
		this.url = url;
		this.waitMsg = waitMsg;
		this.waitTitle = waitTitle;
	}

	public FormAction(String id, String url, Function success, Function failure) {
		this.id = id;
		this.url = url;
		this.success = success;
		this.failure = failure;		
	}

	public Function getFailure() {
		return failure;
	}

	public void setFailure(Function failure) {
		this.failure = failure;
	}

	public Function getSuccess() {
		return success;
	}

	public void setSuccess(Function success) {
		this.success = success;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWaitMsg() {
		return waitMsg;
	}

	public void setWaitMsg(String waitMsg) {
		this.waitMsg = waitMsg;
	}

	public String getWaitTitle() {
		return waitTitle;
	}

	public void setWaitTitle(String waitTitle) {
		this.waitTitle = waitTitle;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
