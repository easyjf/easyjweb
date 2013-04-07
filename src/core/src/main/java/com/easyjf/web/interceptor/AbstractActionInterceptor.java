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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyjf.web.Page;
import com.easyjf.web.WebForm;

/**
 * @deprecated
 */
public abstract class AbstractActionInterceptor implements IActionInterceptor {

	public void doAfter(HttpServletRequest request,
			HttpServletResponse response, Page page)
			throws InterceptorException {
		// TODO Auto-generated method stub
		doPostInterceptor(request, response, page);
	}

	public boolean doBefore(HttpServletRequest request,
			HttpServletResponse response, WebForm form)
			throws InterceptorException {
		// TODO Auto-generated method stub
		return doPreInterception(request, response);
	}

	abstract protected boolean doPreInterception(HttpServletRequest request,
			HttpServletResponse response) throws InterceptorException;

	abstract protected void doPostInterceptor(HttpServletRequest request,
			HttpServletResponse response, Page page)
			throws InterceptorException;

}
