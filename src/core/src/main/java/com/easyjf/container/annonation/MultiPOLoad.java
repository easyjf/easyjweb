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

import com.easyjf.web.POLoadDao;

@Target( { METHOD, CONSTRUCTOR, FIELD })
@Retention(RUNTIME)
/**
 * @author 天一
 * 这个注解用于自动加载关联的List对象，适用于OneToMany关联。目前还不完善，更新对象时会覆盖掉原来的List
 */
public @interface MultiPOLoad {
	
	String name() default "";
	
	Class targetClz();
	
	Class pkClz() default Long.class;
	
	Class loadDao() default POLoadDao.class;

}
