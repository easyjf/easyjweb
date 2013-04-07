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
package com.easyjf.web.tools;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 简单的数组对象查询，当前还不完善
 * 
 * @author 大峡
 * 
 */
public class ArrayQuery implements IQuery {
	private Arrays array = null;

	public ArrayQuery() {

	}

	public ArrayQuery(Arrays array) {
		this.array = array;
	}

	public void setArray(Arrays array) {
		this.array = array;
	}

	public int getRows(String conditing) {
		// TODO Auto-generated method stub
		// 随便写的代码
		return (array != null ? 1 : 0);
	}

	public List getResult(String conditing) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setFirstResult(int begin) {
		// TODO Auto-generated method stub

	}

	public void setMaxResults(int max) {
		// TODO Auto-generated method stub

	}

	public List getResult(String conditing, int begin, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParaValues(Collection paraValues) {
		// TODO Auto-generated method stub

	}

}
