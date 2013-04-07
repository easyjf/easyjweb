package com.easyjf.core.support;

import java.util.ArrayList;
import java.util.List;

import com.easyjf.core.support.query.PageObject;

public class FieldQueryObject implements IFieldQueryObject {

	protected Integer pageSize = 10;

	protected Integer currentPage = 0;

	protected List<String> orderBy = new ArrayList<String>();

	protected List<String> orderType = new ArrayList<String>();

	protected List<Object> params = new java.util.ArrayList<Object>();

	protected String queryString = "1=1 ";

	protected List<String> queryFields = new ArrayList<String>();

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentPage() {
		if (currentPage == null) {
			currentPage = -1;
		}
		return currentPage;
	}

	public final List<String> getOrderBy() {
		return orderBy;
	}

	public final void setOrderBy(List<String> orderBy) {
		this.orderBy = orderBy;
	}

	public final List<String> getOrderType() {
		return orderType;
	}

	public final void setOrderType(List<String> orderType) {
		this.orderType = orderType;
	}

	public final List<Object> getParams() {
		return params;
	}

	public final void setParams(List<Object> params) {
		this.params = params;
	}

	public final String getQueryString() {
		return queryString;
	}

	public final void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public final List<String> getQueryFields() {
		return queryFields;
	}

	public final void setQueryFields(List<String> queryFields) {
		this.queryFields = queryFields;
	}

	public Integer getPageSize() {
		if (pageSize == null) {
			pageSize = -1;
		}
		return pageSize;
	}

	public PageObject getPageObj() {
		PageObject pageObj = new PageObject();
		pageObj.setCurrentPage(this.getCurrentPage());
		pageObj.setPageSize(this.getPageSize());
		if (this.currentPage == null && this.currentPage <= 0) {
			pageObj.setCurrentPage(1);
		}
		return pageObj;
	}

	public String getQuery() {
		customizeQuery();
		return queryString + orderString();
	}

	protected String orderString() {
		StringBuilder orderString = new StringBuilder(" ");
		if (getOrderBy().size() > 0 && getOrderType().size() > 0
				&& getOrderBy().size() == getOrderType().size()) {
			orderString.append(" order by ");
			for (int i = 0; i < getOrderBy().size(); i++) {
				orderString.append(getOrderBy().get(i));
				orderString.append(" ");
				orderString.append(getOrderType().get(i));
				orderString.append(", ");
			}
			String ret = orderString.toString();
			return ret.substring(0, ret.length() - 2);
		}
		return "";
	}

	public List getParameters() {
		return this.params;
	}

	public IFieldQueryObject addField(String... fields) {
		if (fields != null && fields.length > 0) {
			for (String field : fields) {
				this.getQueryFields().add(field);
			}
		}
		return this;
	}

	public String getSelectFields() {
		String fields = null;
		if (this.getQueryFields().size() == 0) {
			return "obj";
		} else {
			StringBuilder sb = new StringBuilder(" ");
			for (String field : this.getQueryFields()) {
				sb.append(field);
				sb.append(", ");
			}
			fields = sb.toString();
			return fields.substring(0, fields.length() - 2);
		}
	}

	public IFieldQueryObject addOrder(String field, OrderType orderType) {
		if (field != null && !"".equals(field) && orderType != null) {
			this.getOrderBy().add(field);
			this.getOrderType().add(orderType.toString());
		}
		return this;
	}

	public IFieldQueryObject addQuery(String field, Object para,
			String expression) {
		if (field != null && para != null) {
			queryString += " and " + field + " " + handleExpression(expression)
					+ " ? ";
			params.add(para);
		}
		return this;
	}

	public IFieldQueryObject addQuery(String field, Object para,
			String expression, String logic) {
		if (field != null && para != null) {
			queryString += " " + logic + " " + field + " "
					+ handleExpression(expression) + " ? ";
			params.add(para);
		}
		return this;
	}

	public IFieldQueryObject addQuery(String scope, Object[] paras) {
		if (scope != null) {
			queryString += " and " + scope;
			if (paras != null && paras.length > 0) {
				for (int i = 0; i < paras.length; i++)
					params.add(paras[i]);
			}
		}
		return this;
	}

	private String handleExpression(String expression) {
		if (expression == null)
			return "=";
		else
			return expression;
	}

	public void customizeQuery() {

	}

	public static void main(String[] args) {
		FieldQueryObject fqo = new FieldQueryObject();
		fqo.addField("name", "password", "id", "born");
		fqo.addOrder("name", OrderType.DESC);
		fqo.addOrder("born", OrderType.ASC);
		fqo.addQuery("obj.name", "stef%", "like");
		fqo.addQuery("obj.born", "now", ">");
		String sql = "select " + fqo.getSelectFields()
				+ " from User obj where " + fqo.getQuery();
		System.out.println(sql);
	}

}
