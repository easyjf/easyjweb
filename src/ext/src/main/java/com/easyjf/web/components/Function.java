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
public class Function extends BaseComponent {
	private String[] paras;
	private String code;

	public Function() {
		this((String)null);
	}

	public Function(String[] paras) {
		this(paras,null);
	}
	public Function(String code) {
		this((String[]) null, code);
	}

	public Function(String id, String code) {
		this(code);
		this.setId(id);
	}

	public Function(String[] paras, String code) {
		this(null, paras, code);
	}

	public Function(String id, String[] paras, String code) {
		this.code = code;
		this.paras = paras;
		this.id = id;
		this.set("initMethod", "call");
	}

	public void execute() {
		this.setInit(true);
	}

	public String clz() {
		return null;
	}

	public String[] getParas() {
		return paras;
	}

	public void setParas(String[] paras) {
		this.paras = paras;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
