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
 * 用来指定Action的配置
 * 
 * @author 大峡
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {
	public String name() default "";// 默认的
	
	public String path() default "";// 访问路径

	public String inject() default "AutoJnjectByType";// 自动注入类型

	public String[] disInject() default {};// 禁止注入的数据

	public String[] autoInject() default {};// 自动注入的数据

	public String view() default "";// 定义view的前缀，可以通过定义每个Module的view，把很多Action设置到一个目录中
	
	public String alias() default "";//module的别名
	
	public boolean autoToken() default false;//是否自动产生token
	
	public boolean validate() default false;//是否支持自动验证
	
	public String scope() default "request";//范围，默认为request
	
	public String messageResource() default "";//消息文件子目录
}
