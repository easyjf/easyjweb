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
package com.easyjf.web;

/**
 * 
 * <p>
 * Title:EasyJWeb Action接口
 * </p>
 * <p>
 * Description: EasyJWeb的Action接口，用户的Action组件必须实现该接口，该接口只有一个execute方法需要用户实现。
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */
public interface IWebAction {
	/**
	 * Action执行接口，在EasyJWeb中，所有的请求都会调用该方法，执行相关的数据操作。
	 * 
	 * @param form
	 *            封装了本次请求的Form数据信息
	 * @param module
	 *            本次调用的Module信息
	 * @return 显示数据的模板或直接跳转的URL
	 * @throws Exception
	 */
	public Page execute(WebForm form, Module module) throws Exception;
}
