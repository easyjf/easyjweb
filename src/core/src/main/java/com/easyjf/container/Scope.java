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
package com.easyjf.container;

/**
 * Bean的类型，主要用来指定Bean的生命周期
 * 
 * @author 大峡
 * 
 */
public interface Scope {
	/**
	 * 往容器中注册一个Bean
	 * 
	 * @param name　 Bean的名称
	 * @param bean　 Bean的配置信息
	 */
	Object getBean(String name, BeanDefinition beanDefinition);
}
