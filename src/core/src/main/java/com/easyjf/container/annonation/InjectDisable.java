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
 * 用来定义不可注入的属性 EasyJWeb的IOC支持自动按类型、自动按名称或自动选择等几种方式来注入对象所依赖的属性
 * 在有的时候，我们要使用自动注入的方式注入对象依赖，而对象中一些属性不需要注入。此时即可通过使用@InjectDisable标签来设置不需要注入的属性。
 * EasyJWeb在处理自动注入的过程中，若发现@InjectDisable标签，将会忽略对这个属性的注入。
 * 例如下面的示例中，在自动注入的情况下，service将会被自动注入，而parent这一个属性将不会被注入。
 * 
 * <pre>
 * public class PersonAction implements IWebAction {
 * 	private PersonService service;
 * 	&#064;InjectDisable
 * 	private Person parent;
 * 
 * }
 * </pre>
 * 
 * 该标签可以用于方法、属性字段，及构造子上。
 * 
 * @author 大峡
 * 
 */
@Target( { METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
public @interface InjectDisable {

}
