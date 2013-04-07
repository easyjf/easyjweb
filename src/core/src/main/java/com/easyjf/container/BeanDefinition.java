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

import com.easyjf.beans.MutablePropertyValues;

/**
 * bean的定义信息
 * 
 * @author 大峡
 * 
 */
public interface BeanDefinition {
	/**
	 * 按类别自动注入
	 */
	String Inject_By_Type = "bytype";

	/**
	 * 按名称自动注入
	 */
	String Inject_By_Name = "byname";

	/**
	 * 自动按名称或类别自动注入
	 */
	String Inject_Auto = "auto";

	/**
	 * 
	 * @return bean类名
	 */
	Class getBeanClass();

	/**
	 * @return bean名称
	 */
	String getBeanName();

	/**
	 * @return 该Bean的构造参数
	 */
	ConstructorArguments getConstructorArguments();

	/**
	 * 
	 * @return bean范围，如singleton表示单态Bean，prototype表示原型Bean，session表示会话范围的Bean
	 */
	String getScope();

	/**
	 * @return 返回构造该Bean的工厂方法，若没有工厂方法，则返回null或""
	 */
	String getFactoryMethod();

	/**
	 * 返回该Bean需要通过设值setter方法注入的属性信息
	 * 
	 * @return 设值注入的属性
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * @return 是否属于模板Bean配置，若是模板Bean则返回true，否则返回false;
	 */
	boolean isAbstract();

	/**
	 * @return 是否属于延迟加载Bean，true表示需要使用延迟加载，false表示不需要使用延迟加载
	 */
	boolean isLazy();

	/**
	 * 
	 * @return 返回Bean的自动注入方式，若为no则表示不自动注入，另外可选值为bytype,byname,auto等三种
	 */
	String getInjectType();
}
