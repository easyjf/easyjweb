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
package com.easyjf.web.interceptor;

/**
 * RequestInterceptor主要是拦截用户的request请求，若返回SUCCESS则继续执行， 返回Page，则跳转到相应的Page中执行
 * 若抛出Exception则直接使用
 * 
 * @author 大峡
 * 
 */
public interface IRequestInterceptor {
	public final String SUCCESS = "SUCCESS";
	public Object doIntercept() throws Exception;
}
