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
package com.easyjf.container;

import java.util.Map;

/**
 * 构造子参数封装
 * 
 * @author 大峡
 * 
 */
public class ConstructorArguments {
	private Map values = new java.util.TreeMap();

	public ConstructorArguments concat(ConstructorArguments other) {
		this.values.putAll(other.getArguments());
		return this;
	}

	/*
	 * 添加参数
	 */
	public void addArgument(ConstructorArgumentValue value) {
		values.put(value.getIndex(), value);
	}

	public void addArgument(Integer index, Object value) {
		addArgument(index, value.getClass(), value);
	}

	public void addArgument(Integer index, Class type, Object value) {
		ConstructorArgumentValue v = new ConstructorArgumentValue(index, type,
				value);
		addArgument(v);
	}

	public Map getArguments() {
		return values;
	}

	public boolean isEmpty() {
		return values.size() > 0;
	}

	public int getArgCount() {
		return values.size();
	}
}
