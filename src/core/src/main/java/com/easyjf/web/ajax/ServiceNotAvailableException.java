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

import com.easyjf.util.I18n;
import com.easyjf.web.exception.FrameworkException;

public class ServiceNotAvailableException extends FrameworkException {

	private static final long serialVersionUID = 9110533433680407704L;

	public ServiceNotAvailableException() {
		super(I18n.getLocaleMessage("core.ajax.designated.service.is.not.available.may.have.limited.name"));
	}

	public ServiceNotAvailableException(String name) {
		super(I18n.getLocaleMessage("core.ajax.named") + name +I18n.getLocaleMessage("core.ajax.may.not.use.the.service.the.names.may.have.limited"));
	}
}
