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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.easyjf.dbo.EasyJDB;

/**
 * 
 * <p>
 * Title: IDAO的EasyDBO实现
 * </p>
 * <p>
 * Description:通过ORM系统中间件EasyDBO实现添删改查等数据库操作
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: EasyJF开源团队
 * </p>
 * 
 * @author 大峡
 * @version 0.1
 */
public class EasyDBODAO<T> implements IDAO<T> {
	private static final EasyDBODAO singleton = new EasyDBODAO();
	private EasyJDB db;

	public EasyDBODAO() {
	}

	public EasyDBODAO(EasyJDB db) {
		this.db = db;
	}

	public EasyJDB getDb() {
		return db;
	}

	public void setDb(EasyJDB db) {
		this.db = db;
	}

	public static EasyDBODAO getInstance() {
		if(singleton.getDb()==null)singleton.setDb(EasyJDB.getInstance());
		return singleton;
	}

	public boolean save(T obj) {
		return db.add(obj);
	}

	public boolean update(T obj) {
		return db.update(obj);
	}

	public boolean del(T obj) {
		return db.del(obj);
	}

	public T get(Class<T> clz, Serializable id) {
		return (T)db.get(clz, (Object) id);
	}

	public T getBy(Class<T> clz, String fieldName, Serializable value) {
		Collection paras = new ArrayList();
		paras.add(value);
		return (T)db.read(clz, fieldName + "=?", paras);
	}

	public List<T> query(Class<T> clz, String scope) {
		return db.query(clz, scope);
	}

	public List<T> query(Class<T> clz, String scope, Collection paras) {
		return db.query(clz, scope, paras);
	}

	public List<T> query(Class<T> clz, String scope, Collection paras, int begin,
			int max) {
		return db.query(clz, scope, paras, begin, max);
	}

	public int execute(String sql, Collection paras) {
		int ret = -1;
		try {
			ret = db.execute(sql, paras);
		} catch (Exception e) {
			// 这里添加错误处理代码
			e.printStackTrace();
		}
		return ret;
	}

	public int execute(String sql) {
		return execute(sql, null);
	}

	public Object uniqueResult(String sql, Collection paras) {
		return db.uniqueResult(sql, paras);
	}

	public Object uniqueResult(String sql) {
		return db.uniqueResult(sql);
	}

}
