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
 * 简单的方法调用
 * 
 * @author 大峡
 * 
 */
public class SimpleMethodInvocation implements MethodInvocation {

	private Object target;

	private Method method;

	private Object[] args;

	public SimpleMethodInvocation(Object target, Method method, Object[] args) {
		this.target = target;
		this.method = method;
		this.args = args.clone();
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArguments() {
		return args.clone();
	}

	public Object getTarget() {

		return target;
	}

	public Object proceed() throws Throwable {
		return method.invoke(target, args);
	}
}
