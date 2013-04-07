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
public class OuterFunction extends Function {
	public OuterFunction(String fn) {
		this(fn, "this");
	}

	public OuterFunction(String fn, String scope) {
		this(fn, scope, null);
	}

	public OuterFunction(String fn, String scope, String name) {
		super(name);
		this.setCode(fn + ".apply(" + scope + ",arguments);");
	}

	public OuterFunction(BaseComponent component, String method) {
		this(component, method, null);
	}

	public OuterFunction(BaseComponent component, String method, String scope) {
		String v1 = component.getVarName() != null ? component.getVarName()
				: component.getId();
		String fn = method;
		String sc = scope;
		if (sc == null)
			sc = v1 != null ? v1 : "this";
		if (v1 != null)
			fn = v1 + "." + fn;
		this.setCode(fn + ".apply(" + sc + ",arguments);");
	}
}
