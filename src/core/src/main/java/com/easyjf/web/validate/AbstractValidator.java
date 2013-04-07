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
 * EasyJWeb中验证器的基类，实现了Validaotr接口。<br>
 * 提供了addError供子类调用，从而使验证结果的添加非常容易。
 * 用户自定义的验证器类均可直接继承该类实现。
 * @author 大峡
 * 
 */
public abstract class AbstractValidator implements Validator {
	/**
	 * 往验证结果信中添加验证错误
	 * @param obj 验证的目标对象
	 * @param value 要验证的值
	 * @param errors 验证错误提示结果集
	 */
	public void addError(TargetObject obj, Object value, Errors errors) {
		addError(obj, value, errors, null);
	}

	/**
	 * 往验证结果集中添加一条自定义的验证错误信息，针对类似StringRequired中的min_msg等特殊的验证提示信息使用。
	 * @param obj 验证目录对象
	 * @param value 验证的值
	 * @param errors 验证错误结果集
	 * @param customMessage 自定义的验证出错提示信息
	 */
	public void addError(TargetObject obj, Object value, Errors errors,
			String customMessage) {
		ValidateResult result = errors.getErrorObject(obj.getFieldName());
		if (result == null) {
			result = new ValidateResult(obj, value);
			errors.addError(result);
		}
		result.addErrorValidator(this);		
		if (customMessage != null)
			result.addCustomMessage(this, customMessage);
	}
}
