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
package com.easyjf.container.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.easyjf.web.validate.ValidateType;

/**
 * 验证器标签，用来在需要验证的目标类上标识验证信息
 * 验证器标签可以直接用于字段或方法上，但一般情况下都是作为@Field或@FormPO中的validator、validators等属性使用。
 * 用来定义模型(域)对象中各个属性的验证信息。
 * 
 * @author 大峡
 * 
 */
@Target( { ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD,
		ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Validator {

	/**
	 * 验证器的名称，如required，string，range等
	 * 
	 * @return 验证器的名称
	 */
	public String name();

	/**
	 * 验证器的值，使用;号作为分隔符存放各个参数。如value="required;min:5;max:20"
	 * 
	 * @return 验证器的详细值
	 */

	public String value() default "";

	/**
	 * 默认错误提示信息，当验证无法通过时显示的提示信息
	 * 
	 * @return 验证出错时的提示信息
	 */
	public String msg() default "";

	/**
	 * 字段名称，对于property及field类型的校验均可用，也是错误对象的主属性名称。可以用于多个字段，此时需要使用,隔开
	 * 
	 * @return 验证的字段名称
	 */
	public String field() default "";

	/**
	 * 定义对象的显示名称,默认情况下为field的名称，可以通过@Field中的name属性定义。
	 * 
	 * @return 验证器未通过时提示验证属性的名称
	 */
	public String displayName() default "";

	/**
	 * 校验类型，默认是对属性进行校验 可以通过该值来指定验证器作用的对象
	 * 
	 * @return 验证器的类型
	 */
	public ValidateType type() default ValidateType.Property;

	/**
	 * 是否必填字段，每个验证器都可以通过设置属性<code>required=true</code>来指定该属性为必填项
	 * 
	 * @return 若必填，则值为true，否则返回false
	 */
	public boolean required() default false;

	/**
	 * 多国语言显示建的名称，假如要使提示信息自动支持多国语言属性，则可以通过设置该值达到相应的目的
	 * 
	 * @return 提示信息多国语言标识建的名称
	 */
	public String key() default "";
}
