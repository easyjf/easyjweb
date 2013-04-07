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
package com.easyjf.web.validate;

/**
 * 验证器
 * 
 * @author 大峡
 * 
 */
public interface Validator {
	/**
	 * 执行具体的验证操作
	 * 
	 * @param obj
	 *            验证目标对象
	 * @param value
	 *            验证值
	 * @param errors
	 *            验证异常信息封装
	 */
	void validate(TargetObject obj, Object value, Errors errors);// 验证

	/**
	 * 验证器默认错误信息
	 * 
	 * @return 返回验证器默认的错误提示信息
	 */
	String getDefaultMessage();
}
