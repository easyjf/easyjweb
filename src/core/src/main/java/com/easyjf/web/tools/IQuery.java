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
import java.util.List;
/**
 * 分页查询算法接口
 * @author 大峡
 *
 */
public interface IQuery {
	/**
	 * 根据查询条件返回记录总数
	 * @param conditing
	 * @return 查询记录结果总数
	 */
	int getRows(String conditing);

	/**
	 * 根据查询条件返回符合条件的结果数
	 * @param conditing
	 * @return 根据条件获得查询结果集
	 */
	List getResult(String conditing);

	/**
	 * 设置有效结果记录的开始位置
	 * @param begin
	 */
	void setFirstResult(int begin);

	/**
	 * 最大返回记录数
	 * @param max
	 */
	void setMaxResults(int max);

	/**
	 * 设置查询参数
	 * @param paraValues
	 */
	void setParaValues(Collection paraValues);

	/**
	 * 根据查询条件，记录开始位置及最大记录数返回有效查询结果
	 * @param conditing
	 * @param begin
	 * @param max
	 * @return 指定范围内的查询结果记录集
	 */
	List getResult(String conditing, int begin, int max);
}
