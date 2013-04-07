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

import java.util.Map;

/**
 * 
 * <p>
 * Title: 用户请求url缺省映射处理器
 * </p>
 * <p>
 * Description: 用户请求url缺省映射处理器
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author stef_wu、大峡
 * @version 1.0
 */
public interface IPathMappingRuler {
	public String CLASSIC_PATTERN="classic";
	/**
	 * 得到模块的名称
	 * 
	 * @return 返回该请求的模块名称
	 */
	public String getModuleName();

	/**
	 * 得到模块的缺省参数
	 * 
	 * @return 返回该请求的模块参数
	 */
	public Map getParams();

	/**
	 * 返回该请求的模块命令
	 * 
	 * @return 该请求的模块命令
	 */
	public String getCommand();
	
	public String getUrlPattern();
	
	public String getSuffix();
}
