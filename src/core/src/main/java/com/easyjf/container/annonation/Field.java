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

/**
 * 指定对象属性的名称、验证器、是否允许直接注入、验证器等信息
 * <pre>
 * 该标签是EasyJWeb中的一个重要标签，该标签提供的属性信息有以下几个作用：
 * 1、提供该属性相关说明信息，比如属性的名称，多国语言名称等，字段名称等；
 * 2、用来设置属性的验证器、验证信息，并提供友好的验证信息；
 * 3、保护数据安全，控制数据的外泄或者非法修改。
 * 4、提供代码生成的相关信息
 * 该标签可以直接用在字段的声明上，也可以用在属性的setter或getter方法上
 * 
 * class Person{
 * &#064;Field(name="姓名",validator=&#064;Validator(name="string",required=true;value="blank;trim;min:5;max:10"))
 * private String name;
 * 
 * private Date bornDate;
 * 
 * &#064;Field(name="出生日期")
 * public Date getBornDate()
 * {
 * return bornDate;
 * }
 * </pre>
 * @author 大峡,stef_wu
 * 
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	/**
	 * 前台表单的项，用来指定验证的表单名称项，默认为该属性的名称
	 * 
	 * @return 字段的名称
	 */
	public String fieldName() default "";

	/**
	 * 属性的名称,也即在显示的标签，默认值为fieldName
	 * 
	 * @return 属性的名称
	 */
	public String name() default "";

	/**
	 * 指定是否在页面生成
	 * 
	 * @return 若该属性不需要在前台页面中生存，则返回false，否则返回true，默认值为true
	 */
	public boolean gener() default true;
	
	/**
	 * Does the field present?
	 * @return
	 */
	public boolean present() default true;
	
	/**
	 * Is the field a editor?
	 * @return boolean
	 */
	public boolean editor() default false;
	
	/**
	 * 基于多国语言的属性名称，当设置了key的时候，在生成页面，显示验证信息等的时候，都会使用key对应的属性值来作用该属性的name
	 * 
	 * @return 该属性的多国语言的属性键
	 */
	public String key() default "";

	/**
	 * 设置使用在该属性上的验证器，当Field上只需要单个验证器时使用
	 * 
	 * @return 该属性(字段)上的验证器
	 */
	public Validator validator() default @Validator(name = "NULL");

	/**
	 * 设置使用在该属性上的多个验证器，可以通过该标签给一个属性指定多个验证器
	 * 
	 * @return 该属性(字段)上的验证器集合
	 */
	public Validator[] validators() default {};

	/**
	 * 标识该属性是否属于只写入的
	 * 一个属性不具有可写入的性质，则在使用EasyJWeb中的实用方法如WebForm.toPo(Object)等方法进行简易赋值的时候，将会忽略这些属性的值
	 * 
	 * @return 如果该属性可写入则返回true，否则返回false
	 */
	public boolean writeable() default true;

	/**
	 * 标识该属性是否属于可读的
	 * 一个属性若不可读，则在使用EasyJWeb中的实用方法如WebForm.addPo(Object)等方法把对象中的属性值添加到表示层时，则将会忽略这个属性，从而达到对数据保护的目的。
	 * 
	 * @return 若该属性可读则返回true，否则返回false
	 */
	public boolean readable() default true;
}
