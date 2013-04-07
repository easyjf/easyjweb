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
package com.easyjf.web.exception;

import com.easyjf.util.I18n;

/**
 * 
 * <p>
 * Title:属性不存在错误
 * </p>
 * <p>
 * Description:当用户企图执Form中不存在VO对像访问,抛出该错误!
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
public class NoPropertyException extends FrameworkException {
	static final long serialVersionUID = 88802L;

	public NoPropertyException() {
		super(I18n.getLocaleMessage("core.web.Attribute.does.not.exist.the.WebForm.not.specified.attribute"));
	}

	public NoPropertyException(String message) {
		super(I18n.getLocaleMessage("core.web.Attribute.does.not.exist") + message);
	}
}
