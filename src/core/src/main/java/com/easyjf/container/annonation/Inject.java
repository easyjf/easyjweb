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
 * 标识需要注入的属性
 * 在EasyJWeb的IOC中，除了可以使用配置文件的方式来进行依赖注入以外，还可以使用注解标签的形式来进行注入。
 * &#064;Inject标签正是这个用来标识依赖注入的标签
 * &#064;Inject标签可以用在属性、方法或构造子上，用来指定要注入的对象
 * 
 * <pre>
 * public class PersonAction implements IWebAction
 * {
 * &#064;Inject
 * private PersonService service;
 * }
 * or 
 * public class PersonAction implements IWebAction
 * {
 * &#064;Inject(name="personService")
 * private PersonService service;
 * }
 * </pre>
 * @author 大峡
 * 
 */
@Target( { METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
public @interface Inject {
	/**
	 * 默认情况下为根据类型自动注入
	 */
	final String autoInjectByType = "AutoJnjectByType";

	/**
	 * 指定要注入的bean的名称
	 * @return 返回注入的bean名称
	 */
	String name() default autoInjectByType;// name用来指定要注入的bean的名称
}
