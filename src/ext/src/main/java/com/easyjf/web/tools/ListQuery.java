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

import java.util.Collection;
import java.util.List;
/**
 * 分页列表查询算法实现，当前功能还不全
 * @author 大峡
 *
 */
public class ListQuery implements IQuery {
	private int begin = 0;

	private int max = 0;

	private List list = null;

	public ListQuery() {

	}

	public ListQuery(List l) {
		if (l != null) {
			this.list = l;
			this.max = l.size();
		}
	}

	public void initList(List l) {
		this.list = l;
		this.max = l.size();
	}

	public int getRows(String conditing) {

		return (list == null ? 0 : list.size());
	}

	public List getResult(String conditing) {
		return list.subList(begin, begin + max > list.size() ? list.size()
				: begin + max);
	}

	public void setFirstResult(int begin) {
		this.begin = list.size() < begin ? list.size() : begin;
	}

	public void setMaxResults(int max) {
		this.max = max;
	}

	public List getResult(String conditing, int begin, int max) {

		return list;
	}

	public void setParaValues(Collection paraValues) {
	
	}
}
