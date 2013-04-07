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
package com.easyjf.web.components.tree;

import java.util.Map;

import com.easyjf.web.components.BaseComponent;
import com.easyjf.web.components.Function;

/**
 * 
 * @author 大峡
 * 
 */
public class TreeLoader extends BaseComponent {
	private String url;
	private Map<String, Object> baseParams = new java.util.HashMap<String, Object>();
	private Map<String, Object> baseAttrs = new java.util.HashMap<String, Object>();
	private Boolean preloadChildren;
	private String requestMethod;

	public TreeLoader() {
		this(null);
	}

	public TreeLoader(String url) {
		this(url, null);
	}

	public TreeLoader(String url, Function beforeload) {
		this.url = url;
		this.setLazy(false);
		if (beforeload != null)
			this.addListener("beforeload", beforeload);
	}

	public String clz() {
		return "Ext.tree.TreeLoader";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getBaseParams() {
		return baseParams;
	}

	public void setBaseParams(Map<String, Object> baseParams) {
		this.baseParams = baseParams;
	}

	public Map<String, Object> getBaseAttrs() {
		return baseAttrs;
	}

	public void setBaseAttrs(Map<String, Object> baseAttrs) {
		this.baseAttrs = baseAttrs;
	}

	public Boolean getPreloadChildren() {
		return preloadChildren;
	}

	public void setPreloadChildren(Boolean preloadChildren) {
		this.preloadChildren = preloadChildren;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

}
