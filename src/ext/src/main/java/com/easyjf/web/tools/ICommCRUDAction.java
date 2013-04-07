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

import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
/**
*
* <p>Title: 通用添删改查接口</p>
* <p>Description:定义了通用添删改查的操作接口 </p>
* <p>Copyright: Copyright (c) 2006</p>
* <p>Company: www.easyjf.com</p>
* @deprecated
* @author 蔡世友
* @version 1.0
*/
public interface ICommCRUDAction {
	/**
	 * 新增操作
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doNew(WebForm form, Module module, IActiveUser user);
	/**
	 * 添加操作，保存新增的数据
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doAdd(WebForm form, Module module, IActiveUser user);

	/**
	 * 修改操作
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doUpdate(WebForm form, Module module, IActiveUser user);

	/**
	 * 编辑操作，调出修改的内容
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doEdit(WebForm form, Module module, IActiveUser user);

	/**
	 * 删除操作
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doDel(WebForm form, Module module, IActiveUser user);

	/**
	 * 查询操作
	 * @param form
	 * @param module
	 * @param user
	 * @return 用来显示结果的视图模板
	 */
	public Page doQuery(WebForm form, Module module, IActiveUser user);
}
