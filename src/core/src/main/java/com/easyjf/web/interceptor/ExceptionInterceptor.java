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

import java.lang.reflect.Method;

/**
 * 异常处理拦截器，通过这个拦截器来封装系统异常处理
 * 
 * @author stef_wu,大峡
 * 
 */
public interface ExceptionInterceptor extends Interceptor {
	/**
	 * 
	 * @param e
	 *            出现的异常
	 * @param target
	 *            异常出现的对象
	 * @param method
	 *            异常抛出时调用的方法
	 * @param args
	 *            相关参数
	 * @return 返回true，则表示异常已经成功处理，不再需要作其它的处理，返回false则，表示把异常交给下一级异常处理器进行处理，如果抛出异常，则表示将直接把异常交给外部程序或发给用户。
	 */
	boolean handle(Throwable e, Object target, Method method, Object[] args) throws Exception;
}
