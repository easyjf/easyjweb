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

import java.util.List;

/**
 * 查询对象接口
 * 
 * @author stefanie wu
 * 
 */
public interface IQueryObject {
	/**
	 * 得到一个查询条件语句,其中参数用站位符?1,?2...，也可以不用占位符如title=? and status=1
	 * 
	 * @return 返回的条件语句
	 */
	String getQuery();

	/**
	 * 得到查询语句需要的参数对象列表
	 * 
	 * @return 参数对象列表
	 */
	List getParameters();

	/**
	 * 得到关于分页页面信息对象
	 * 
	 * @return 包装分页信息对象
	 */
	PageObject getPageObj();

	/**
	 * 批量往查询对象中添加查询选项，可以是一个完整的具体查询语句，如title='111' and
	 * status>0，也可以是包括?号的语句，如title=? and status>0 <code>
	 * NewsDocQueryObject query=new NewsDocQueryObject();
	 * query.addQuery("title=?",new Object[]{"新闻"});
	 * </code>
	 * 
	 * @param scope
	 *            查询条件
	 * @param paras
	 *            参数值，如果
	 */
	IQueryObject addQuery(String scope, Object[] paras);
	/**
	 * 批量添加查询条件，可以直接添加组合查询
	 * @param scope
	 * @return
	 */
	IQueryObject addQuery(String scope);

	/**
	 * 往查询条件中逐个加入查询条件
	 * 
	 * @param field 属性名称
	 * @param para 参数值
	 * @param expression 表达式,如果为null，则使用=。
	 */
	IQueryObject addQuery(String field, Object para, String expression);
}
