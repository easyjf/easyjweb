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
public  class CustomComponent extends BaseComponent implements ICustomComponent {
	private String superClz;
	public CustomComponent(String id)
	{
		this(id,null);
	}
	public CustomComponent(String id,String superClz)
	{
		this.setId(id);
		this.superClz=superClz;
		this.setGlobal(true);
	}
	
	public String clz() {
		return null;
	}

	public String superClz() {
		return this.superClz;
	}

	public void setSuperClz(String superClz) {
		this.superClz = superClz;
	}
	@Override
	public boolean isLazy() {
		return true;
	}
	
}
