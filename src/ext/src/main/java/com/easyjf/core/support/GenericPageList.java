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
package com.easyjf.core.support;

import java.util.Collection;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.IQuery;
import com.easyjf.web.tools.PageList;

public class GenericPageList extends PageList {
	protected String scope;

	protected Class cls;
	public GenericPageList(Class cls,IQueryObject queryObject,GenericDAO dao)
	{
		this(cls,queryObject.getQuery(),queryObject.getParameters(),dao);
	}
	public GenericPageList(Class cls, String scope, Collection paras,
			GenericDAO dao) {
		this.cls = cls;
		this.scope = scope;
		IQuery query = new GenericQuery(dao);
		query.setParaValues(paras);
		this.setQuery(query);
	}

	/**
	 * 查询
	 * 
	 * @param currentPage
	 *            当前页数
	 * @param pageSize
	 *            一页的查询个数
	 */
	public void doList(int currentPage, int pageSize) {
		String totalSql = "select COUNT(obj) from " + cls.getName() + " obj where "
				+ scope;
		super.doList(pageSize, currentPage, totalSql, scope);
	}
}
