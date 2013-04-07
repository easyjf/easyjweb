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
 * Title:初始化应用程序
 * </p>
 * <p>
 * Description: 初始化应用程序。用于第一次运行Servlet时执行。
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

public interface IAppInit {
	/**
	 * 在EasyJWeb启动时，自定义的需要执行的程序
	 * @param servlet 具体的servlet对象
	 */
	public void init(ActionServlet servlet);
}
