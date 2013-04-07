package com.easyjf.core.support.query;

import java.util.Collection;
import java.util.List;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.web.tools.IQuery;

public class GenericFieldQuery implements IQuery {

	private GenericDAO dao;

	private int begin;

	private int max;

	private Collection paraValues;

	public GenericFieldQuery(GenericDAO dao) {
		this.dao = dao;
	}

	public List getResult(String sql) {
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		return dao.query(sql, params, begin, max);
	}

	public List getResult(String sql, int begin, int max) {
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		return this.dao.query(sql, params, begin, max);
	}

	public int getRows(String sql) {
		int n = sql.toLowerCase().indexOf("order by");
		Object[] params = null;
		if (this.paraValues != null) {
			params = this.paraValues.toArray();
		}
		if (n > 0) {
			sql = sql.substring(0, n);
		}
		List ret = dao.query(sql, params, 0, 0);
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
