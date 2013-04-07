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
 * 访问请求过滤器
 * 
 * @author easyjf
 * 
 */
public interface RequestFilter {
	/**
	 * 执行过滤操作，对特定的对象进行过滤
	 * 
	 * @param value
	 *            要过滤的对象
	 * @return 返回过滤处理后的对象
	 */
	Object doFilter(Object value);
}
