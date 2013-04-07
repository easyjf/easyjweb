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
 * 通用的数据库查询器，根据数据库操作对象DAO执行数据库分页查询操作
 * @author 大峡
 *
 */
public class DbQuery implements IQuery {
	
	private IDAO dao;

	private int begin;

	private int max;

	private Collection paraValues;

	private Class cls;

	public DbQuery(IDAO dao, Class cls) {
		this.dao = dao;
		this.cls = cls;
	}

	public int getRows(String conditing) {
		int n = conditing.toLowerCase().indexOf("order by");
		String totalSql = conditing;
		if (n > 0)
			totalSql = conditing.substring(0, n);
		int total = ((Number) dao.uniqueResult(totalSql, paraValues)).intValue();
		return total;
	}

	public List getResult(String conditing) {
		return dao.query(cls, conditing, paraValues, begin, max);
	}

	public void setFirstResult(int begin) {
		this.begin = begin;
	}

	public void setMaxResults(int max) {
		this.max = max;
	}

	public List getResult(String conditing, int begin, int max) {
		return dao.query(cls, conditing, paraValues, begin, max);
	}

	public void setParaValues(Collection paraValues) {
		this.paraValues = paraValues;
	}
}
