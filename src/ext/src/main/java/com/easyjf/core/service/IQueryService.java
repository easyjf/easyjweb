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
package com.easyjf.core.service;

import java.util.Collection;
import java.util.List;

public interface IQueryService {
	/**
	 * 数据查询器，用来查询任意对象
	 * @param jpql 查询语句
	 * @param params 参数
	 * @param page 页码
	 * @param pageSize 每页大小
	 * @return 返回符合条件指定页码的记录
	 */
	List query(String jpql,Collection params,int page,int pageSize);
	/**
	 * 查询单个对象
	 * @param jpql 查询语句
	 * @param params 参数
	 * @return 如果查询到符合条件的记录，则返回第一个
	 */
	Object queryForObject(String jpql,Object[] params);
	/**
	 * 执行数据更新操作
	 * @param jpql 数据更新语句
	 * @param params 参数
	 */
	void batchUpdate(String jpql,Object[] params);	
}
