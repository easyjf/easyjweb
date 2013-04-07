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
 * Title: 简单的IWebAction默认实现示例
 * </p>
 * <p>
 * Description: 实现IWebAction接口,IWebAction的默认实现
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
public  class Action implements IWebAction {
	/**
	 * 默认的Action直接查询指定的default页面执行
	 */
	public Page execute(WebForm form, Module module) throws Exception {
		Page page = null;	
		if (module != null) {
			page = module.findPage(module.getDefaultPage());
		}
		return page;
	}
}
