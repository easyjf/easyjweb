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
 * Title:模块找不到错误
 * </p>
 * <p>
 * Description:当用户企图访问不存在的Module时,抛出该错误!
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
public class NoSuchModuleException extends FrameworkException {
	static final long serialVersionUID = 88803L;

	public NoSuchModuleException() {
		super(I18n.getLocaleMessage("core.web.Template.does.not.exist.in.the.configuration.file.is.not.provided.with.the.Template"));
	}

	public NoSuchModuleException(String message) {
		super(I18n.getLocaleMessage("core.web.Template.does.not.exist") + message);
	}
}
