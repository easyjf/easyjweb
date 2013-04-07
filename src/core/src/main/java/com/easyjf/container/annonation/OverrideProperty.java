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

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 用来定义名称重载的属性<br>
 * 该标签用于辅助WebForm.toPo(Object)等方法，从而使得能够正确的赋值
 * 
 * &#064;OverrideProperty标签一般配合@InerProperty使用，也可以单独使用。 如下面的Person<br>
 * <pre>
 * public class Person
 * {
 * &#064;OverrideProperty(name="name",newName="name1")
 * private String name;
 * } 
 * 
 * Form表单属性
 * 
 * <form ...>
 * <input type=text name="name1" value="$!name"/>
 * </form>
 * 
 * Action中的使用WebForm对Person对象赋值
 * PersonAction
 * 
 * Person p=form.toPo(Person.class);
 * System.out.println(p.getName());
 * </pre>
 * @author 大峡
 *
 */
@Target( { METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
public @interface OverrideProperty {
	/**
	 * 要重载(替换)的属性名
	 * 
	 * @return 属性名
	 */
	String name();

	/**
	 * 重载后的新名称
	 * 
	 * @return 新属性名
	 */
	String newName();
}
