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

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.web.tools.IPageList;

public class QueryUtil {
	/**
	 * 数据查询工具，可以通过queryObject查询封装来进行数据，数据查询涉及到查询条件，参数值，还涉及到分页信息等。若页码不符合查询范围，由自动设置为第一页，若pageSize为-1，则查询所有数据
	 * 
	 * @param queryObject
	 * @param entityType
	 * @param dao
	 * @return 分页查询结果
	 */
	public static IPageList query(IQueryObject queryObject, Class entityType,
			GenericDAO dao) {
		PageObject pageObj = queryObject.getPageObj();
		int currentPage;
		int pageSize;
		currentPage = pageObj.getCurrentPage();
		pageSize = pageObj.getPageSize();
		GenericPageList pageList = new GenericPageList(entityType, queryObject,
				dao);
		pageList.doList(currentPage, pageSize);// 查询第几页，每页多少条
		return pageList;
	}
}
