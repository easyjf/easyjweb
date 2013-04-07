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
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 命名标签，用来指定一个目标的名称
 * 该标签可以用于任何其它需要标识名称的地方
 * <pre>
 * &#064;Name("myValidator")
 * public class MyValidator implements Validator
 * {
 * ...
 * }
 * </pre>
 * @author 大峡
 *
 */
@Target( { METHOD, CONSTRUCTOR, FIELD, TYPE })
@Retention(RUNTIME)
public @interface Name {
	/**
	 * 指定某一个目标的名称，这个目标可以是一个对象，也可以是一个类等，根据实际应用的环境来定
	 * @return 目标的标识名称
	 */
	public String value();
}
