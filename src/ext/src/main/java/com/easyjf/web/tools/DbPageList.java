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
import com.easyjf.dbo.DBMapping;
import com.easyjf.dbo.DBObject;

/**
 * 使用EasyDBO实现的数据分页查询
 * 
 * @author 大峡
 * 
 */
public class DbPageList extends PageList {
	private String scope;

	private Class cls;
	
	//private IDAO dao;
	/**
	 * 默认构造子，若不根查询参数表示直接查某个表中的所有数据
	 * 
	 */
	public DbPageList() {
		this(DBObject.class, "1=1");
	}

	/**
	 * 根据类名cls及查询条件scope构造一个数据库分页查询类
	 * 
	 * @param cls
	 * @param scope
	 */
	public DbPageList(Class cls, String scope) {
		this(cls, scope, null);
	}

	/**
	 * 根据类名cls、查询条件scope及查询参数paras构造一个数据库分页查询类
	 * 
	 * @param cls
	 * @param scope
	 * @param paras
	 */
	public DbPageList(Class cls, String scope, Collection paras) {
		this(cls,scope,paras,EasyDBODAO.getInstance());
	}
	public DbPageList(Class cls, String scope, Collection paras,IDAO dao) {
		this.cls = cls;
		this.scope = scope;
		IQuery query = new DbQuery(dao, cls);
		query.setParaValues(paras);
		this.setQuery(query);
	}

	/**
	 * 执行查询操作,只有执行doList(int,int)后，才能从分页查询引擎中取出正确的数据
	 * 
	 * @param currentPage
	 * @param pageSize
	 */
	public void doList(int currentPage, int pageSize) {
		String totalSql = "select count(*) from "
				+ DBMapping.getInstance().findTable(cls).getName() + " where "
				+ scope;
		super.doList(pageSize, currentPage, totalSql, scope);
	}
	public void setDao(IDAO dao) {
		//this.dao = dao;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}	
}
