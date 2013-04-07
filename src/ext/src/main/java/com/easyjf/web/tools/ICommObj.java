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

import java.io.Serializable;

/**
 * 具有顽强生命能力的持久层对象，通过save、update、del等方法，知道如何维护自己的持久数据。
 * 
 * @author wlr
 * 
 */
public interface ICommObj {
	/**
	 * 新增
	 * 
	 * @return 若保存成功则返回true，否则返回false
	 * @throws IdExistException
	 */
	boolean save() throws IdExistException;

	/**
	 * 修改
	 * 
	 * @return 若修改成功则返回true，否则返回false
	 */
	boolean update();

	/**
	 * 删除
	 * 
	 * @return 若删除成功则返回true，否则返回false
	 */
	boolean del();

	/**
	 * 根据id返回
	 * 
	 * @param id
	 * @return 若查找到对象则返回该对象，否则返回null
	 */
	Object get(Serializable id);
}
