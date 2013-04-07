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
public class ViewPort extends Container {
	public ViewPort() {
		this(null);
	}

	public ViewPort(String layout) {
		this.setLayout(layout);
		this.init();
	}

	protected void init() {
		this.setInit(true);
	}

	public String clz() {
		return "Ext.Viewport";
	}
}
