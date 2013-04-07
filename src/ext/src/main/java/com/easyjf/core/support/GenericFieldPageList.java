package com.easyjf.core.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.support.query.GenericFieldQuery;
import com.easyjf.web.tools.IPageList;
import com.easyjf.web.tools.IQuery;

/**
 * 包装通过属性查询的结果集
 * 
 * @author stefanie_wu
 * 
 */
public class GenericFieldPageList implements IPageList {

	private static final long serialVersionUID = -4479667592709565631L;

	protected String scope;

	protected String selectField;

	protected List<String> fields;

	protected Class cls;

	private int rowCount;// 记录数

	private int pages;// 总页数

	private int currentPage;// 实际页数

	private int pageSize;

	private List result;// 查询结果集

	private IQuery query;// 查询器

	/**
	 * 设置查询器
	 */
	public void setQuery(IQuery q) {
		query = q;
	}

	/**
	 * 返回查询结果集，只有在执行doList方法后才能取得正确的查询结果
	 */
	public List getResult() {
		return result;
	}

	/**
	 * 根据每页记录数，页码，统计sql及实际查询sql执行查询操作
	 */
	public void doList(int pageSize, int pageNo, String totalSQL,
			String queryHQL) {
		doList(pageSize, pageNo, totalSQL, queryHQL, null);
	}

	/**
	 * 根据每页记录数，页码，统计sql及实际查询sql及参数执行查询操作
	 */
	public void doList(int pageSize, int pageNo, String totalSQL,
			String queryHQL, Collection paraValues) {
		List rs = null;
		this.pageSize = pageSize;
		if (paraValues != null)
			query.setParaValues(paraValues);
		int total = query.getRows(totalSQL);
		if (total > 0) {
			this.rowCount = total;
			this.pages = (this.rowCount + pageSize - 1) / pageSize; // 记算总页数
			int intPageNo = (pageNo > this.pages ? this.pages : pageNo);
			if (intPageNo < 1)
				intPageNo = 1;
			this.currentPage = intPageNo;
			if (pageSize > 0) {
				query.setFirstResult((intPageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			rs = query.getResult(queryHQL);
		}
		if (rs != null && rs.size() > 0) {
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < rs.size(); i++) {
				Object[] listObject = (Object[]) rs.get(i);
				Map<String, Object> tObject = new HashMap<String, Object>();
				for (int j = 0; j < listObject.length; j++) {
					tObject.put(this.fields.get(j), listObject[j]);
				}
				ret.add(tObject);
			}
			result = ret;
		} else {
			result = new ArrayList();
		}
	}

	/**
	 * 返回总页数
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * 返回总记录数
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * 返回当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 返回每页大小
	 */
	public int getPageSize() {
		return pageSize;
	}

	public int getNextPage() {
		int p = this.currentPage + 1;
		if (p > this.pages)
			p = this.pages;
		return p;
	}

	public int getPreviousPage() {
		int p = this.currentPage - 1;
		if (p < 1)
			p = 1;
		return p;
	}

	public GenericFieldPageList(Class cls, IFieldQueryObject fqObject,
			GenericDAO dao) {
		this(cls, fqObject.getSelectFields(), fqObject.getQuery(), fqObject
				.getParameters(), dao, fqObject.getQueryFields());
	}

	public GenericFieldPageList(Class cls, String fields, String scope,
			Collection paras, GenericDAO dao, List<String> queryFields) {
		this.selectField = fields;
		this.cls = cls;
		this.scope = scope;
		IQuery query = new GenericFieldQuery(dao);
		query.setParaValues(paras);
		this.setQuery(query);
		this.setFields(queryFields);
	}

	private String getSql() {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append(selectField);
		sb.append(" from " + cls.getSimpleName());
		sb.append(" obj where ");
		sb.append(scope);
		return sb.toString();
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
		String totalSql = "select COUNT(obj.id) from " + cls.getName()
				+ " obj where " + scope;
		this.doList(pageSize, currentPage, totalSql, getSql());
	}

	public final void setFields(List<String> fields) {
		this.fields = fields;
	}

}
