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
package com.easyjf.core.service.impl;

import java.util.Collection;
import java.util.List;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.service.IQueryService;

public class QueryServiceImpl implements IQueryService {
	private GenericDAO dao;

	public void setDao(GenericDAO dao) {
		this.dao = dao;
	}

	public List query(String scope, Collection params, int page, int pageSize) {
		Object[] objs = null;
		if (params != null) {
			objs = params.toArray();
		}
		return this.dao.query(scope, objs, page, pageSize);
	}

	public void batchUpdate(String jpql, Object[] params) {
		this.dao.batchUpdate(jpql, params);
	}

	public Object queryForObject(String jpql, Object[] params) {
		List ret=this.dao.query(jpql, params, 0, 1);
		if(ret!=null && ret.size()>0){
			return ret.get(0);
		}
		return null;
	}

	
}
