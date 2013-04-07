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
package com.easyjf.core.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author easyjf.com
 * 
 * 泛型DAO接口
 */
public interface GenericDAO<T> {
	/**
	 * 根据Id查找一个类型为T的对象。
	 * 
	 * @param id
	 *            传入的ID的值
	 * @return 一个类型为T的对象
	 */
	T get(Serializable id);

	/**
	 * 持久化一个对象，该对象类型为T。
	 * 
	 * @param newInstance
	 *            需要持久化的对象，使用JPA标注。
	 */
	<T>T save(T newInstance);

	/**
	 * 根据对象id删除一个对象，该对象类型为T
	 * 
	 * @param id
	 *            需要删除的对象的id。
	 */
	void remove(Serializable id);

	/**
	 * 更新一个对象，主要用于更新一个在persistenceContext之外的一个对象。
	 * 
	 * @param transientObject
	 *            需要更新的对象，该对象不需要在persistenceContext中。
	 */
	void update(T transientObject);

	/**
	 * 根据对象的一个属性名和该属性名对应的值来查找一个对象。
	 * 
	 * @param propertyName
	 *            属性名
	 * @param value
	 *            属性名对应的值
	 * @return 一个对象，如果在该属性名和值的条件下找到多个对象，则抛出一个IllegalStateException异常
	 */
	T getBy(String propertyName, Object value);
	
	/**
	 * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
	 * 
	 * @param queryName
	 *            命名查询的名字
	 * @param params
	 *            查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
	 * @param begin
	 *            开始查询的位置
	 * @param max
	 *            需要查询的对象的个数
	 * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
	 */
	List executeNamedQuery(final String queryName, final Object[] params,
			final int begin, final int max);

	/**
	 * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找类型为T的对象。
	 * 
	 * @param query
	 *            查询的条件，使用位置参数，对象名统一为obj，查询条件从where后开始。比如：obj.name =
	 *            ?1 and obj.properties = ?2
	 * @param params
	 *            查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
	 * @param begin
	 *            开始查询的位置
	 * @param max
	 *            需要查询的对象的个数
	 * @return 一个该类型对象的List对象，如果没有查到任何数据，返回一个空的List对象。
	 */
	List<T> find(String query, Object[] params, int begin, int max);

	/**
	 * 根据一个查询条件及其参数，还有开始查找的位置和查找的个数来查找任意类型的对象。
	 * 
	 * @param jpql
	 *            完整的查询语句，使用位置参数。比如：select user from User
	 *            user where user.name = ?1 and
	 *            user.properties = ?2
	 * @param params
	 *            查询条件中的参数的值。使用Object数组，要求顺序和查询条件中的参数位置一致。
	 * @param begin
	 *            开始查询的位置
	 * @param max
	 *            需要查询的对象的个数
	 * @return 一个任意对象的List对象，如果没有查到任何数据，返回一个空的List对象。
	 */
	List query(String jpql, Object[] params, int begin, int max);

	/**
	 * 查询符合条件的所有记录
	 * @param jpql 查询语句
	 * @param params 参数列表
	 * @return 返回符合条件的数据对象,如果没有数据对象则返回null
	 */
	List query(final String jpql, Object[] params);
	/**
	 * 通过DAO接口查询任意对象
	 * @param jpql 
	 * @return 返回符合条件的所有记录集,如果没有查到数据,则返回null
	 */
	List query(final String jpql);
	/**
	 * 根据jpql语句执行批量数据更新等操作
	 * 
	 * @param jpql
	 *            需要执行jpql语句
	 * @param params
	 *            语句中附带的参数
	 * @return
	 */
	int batchUpdate(final String jpql, Object[] params);

	/**
	 * 执行SQL语句查询
	 * 
	 * @param nnq
	 * @return
	 */
	 List executeNativeNamedQuery(String nnq);
	 
	 List executeNativeNamedQuery(final String nnq,final Object[] params);
	
	 List executeNativeQuery(final String nnq, final Object[] params,
			final int begin, final int max);
	/**
	 * 执行SQL语句
	 * 
	 * @param nnq
	 * @return
	 */
	 int executeNativeSQL(final String nnq);
	/**
	 * 执行SQL语句查询，返回单个查询结果
	 * @param jpql 查询语句
	 * @return Object
	 */
	Object getSingleResult(final String jpql,final Object []params);

	 void flush();

}