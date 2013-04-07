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
import java.util.List;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.web.tools.IQuery;

public class GenericQuery implements IQuery {

	private GenericDAO dao;

	private int begin;

	private int max;

	private Collection paraValues;

	public GenericQuery(GenericDAO dao) {
		this.dao = dao;
	}

	public List getResult(String condition) {
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		return dao.find(condition, params, begin, max);
	}

	public List getResult(String condition, int begin, int max) {
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		return this.dao.find(condition, params, begin, max);
	}

	public int getRows(String condition) {
		int n = condition.toLowerCase().indexOf("order by");
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		if (n > 0) {
			condition = condition.substring(0, n);
		}
		List ret = dao.query(condition, params, 0, 0);
		if (ret != null && ret.size() > 0) {
			return ((Long) ret.get(0)).intValue();
		} else {
			return 0;
		}
	}

	public void setFirstResult(int begin) {
		this.begin = begin;
	}

	public void setMaxResults(int max) {
		this.max = max;
	}

	public void setParaValues(Collection params) {
		this.paraValues = params;
	}

}
