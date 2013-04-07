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
package com.easyjf.web.security.acegi;

import java.lang.reflect.Method;

import com.easyjf.web.interceptor.ExceptionInterceptor;

public class AcegiExceptionHandler implements ExceptionInterceptor {
	public boolean handle(Throwable e, Object target, Method method,
			Object[] args) throws Exception{
		if(e instanceof org.acegisecurity.AcegiSecurityException)throw (Exception)e;
		return false;
	}
}
