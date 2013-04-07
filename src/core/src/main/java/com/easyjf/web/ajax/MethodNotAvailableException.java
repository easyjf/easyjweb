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
package com.easyjf.web.ajax;

import java.lang.reflect.Method;

import com.easyjf.util.I18n;
import com.easyjf.web.exception.FrameworkException;

/**
 * 方法不可用
 * 
 * @author 大峡
 * 
 */
public class MethodNotAvailableException extends FrameworkException {

	private static final long serialVersionUID = 3474609756582030887L;

	public MethodNotAvailableException() {
		super(I18n.getLocaleMessage("core.ajax.method.can.not.be.used"));
	}

	public MethodNotAvailableException(Method method) {
		super(I18n.getLocaleMessage("core.ajax.class") + method.getDeclaringClass() + I18n.getLocaleMessage("core.ajax.methed") + method
				+ I18n.getLocaleMessage("core.ajax.remote.may.not.be.used.to.call"));
	}
}
