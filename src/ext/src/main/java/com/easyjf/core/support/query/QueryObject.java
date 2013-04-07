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
package com.easyjf.core.support.query;

import java.util.ArrayList;
import java.util.List;

public class QueryObject implements IQueryObject {

	protected Integer pageSize = 10;

	protected Integer currentPage = 0;

	protected String orderBy;

	protected String orderType;

	protected List params = new ArrayList();

	protected StringBuilder queryString = new StringBuilder("1=1");

	public void setStart(Integer start) {
		if (start != null && this.getPageSize() != null) {
			this.setCurrentPage((start.intValue())
					/ this.getPageSize().intValue() + 1);
		}
	}

	public void setLimit(Integer limit) {
		this.setPageSize(limit);
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected void setParams(List params) {
		this.params = params;
	}

	public String getOrderType() {
		return orderType;
	}

	public Integer getCurrentPage() {
		if (currentPage == null) {
			currentPage = -1;
		}
		return currentPage;
	}

	public String getOrder() {
		return orderType;
	}

	public String getOrderBy() {
		return orderBy;
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
		if (this.currentPage == null || this.currentPage <= 0) {
			pageObj.setCurrentPage(1);
		}
		return pageObj;
	}

	public String getQuery() {
		customizeQuery();
		if(queryString.indexOf("1=1 and")==0 && queryString.length()>"1=1 and ".length()){
			queryString.delete(0, "1=1 and ".length());
		}
		return queryString + orderString();
	}

	protected String orderString() {
		StringBuilder orderString =new StringBuilder(" ");
		if (this.getOrderBy() != null && !"".equals(getOrderBy())) {
			orderString.append(" order by obj.").append(this.getOrderBy());
		}
		if (this.getOrderType() != null && !"".equals(getOrderType())) {
			orderString.append(" ").append(getOrderType());
		}
		return orderString.toString();
	}

	public List getParameters() {
		return this.params;
	}

	public IQueryObject addQuery(String field, Object para, String expression) {
		if (field != null && para != null) {
			queryString.append(" and ").append(field).append(" ").append(handleExpression(expression)).append(" ? ");
			params.add(para);
		}
		return this;
	}

	public IQueryObject addQuery(String field, Object para, String expression,
			String logic) {
		if (field != null && para != null) {
			queryString.append(" ").append(logic).append(" ").append(field).append(" ").append(handleExpression(expression)).append(" ? ");
			params.add(para);
		}
		return this;
	}

	public IQueryObject addQuery(String scope, Object[] paras) {
		if (scope != null) {
			queryString.append(" and ").append(scope);
			if (paras != null && paras.length > 0) {
				for (int i = 0; i < paras.length; i++)
					params.add(paras[i]);
			}
		}
		return this;
	}
	public  IQueryObject addQuery(String scope) {
		this.addQuery(scope,null);
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
}
