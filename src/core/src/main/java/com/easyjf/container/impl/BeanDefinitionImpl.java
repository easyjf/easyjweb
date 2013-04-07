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
package com.easyjf.container.impl;

import com.easyjf.beans.MutablePropertyValues;
import com.easyjf.container.BeanDefinition;
import com.easyjf.container.ConstructorArguments;

/**
 * BeanDefinition的默认实现
 * 
 * @author 大峡
 * 
 */
public class BeanDefinitionImpl implements BeanDefinition {
	private Class beanClass;

	private String beanName;

	private String factoryMethod;

	private ConstructorArguments constructorArguments = new ConstructorArguments();

	private MutablePropertyValues propertyValues = new MutablePropertyValues();

	private String scope = "singleton";

	private boolean lazy = false;

	private boolean abstra = false;

	private String injectType = "NONE";

	public BeanDefinitionImpl() {

	}

	public BeanDefinitionImpl(String beanName) {
		this.beanName = beanName;
	}

	public BeanDefinitionImpl(String beanName, Class beanClass, String scope) {
		this.beanName = beanName;
		this.beanClass = beanClass;
		this.scope = scope;
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public String getBeanName() {
		return beanName;
	}

	public ConstructorArguments getConstructorArguments() {
		return constructorArguments;
	}

	public String getFactoryMethod() {
		return factoryMethod;
	}

	public String getInjectType() {
		return injectType;
	}

	public MutablePropertyValues getPropertyValues() {
		return propertyValues;
	}

	public String getScope() {
		return scope;
	}

	public boolean isAbstract() {

		return abstra;
	}

	public boolean isLazy() {
		return lazy;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setConstructorArguments(
			ConstructorArguments constructorArguments) {
		this.constructorArguments = constructorArguments;
	}

	public void setFactoryMethod(String factoryMethod) {
		this.factoryMethod = factoryMethod;
	}

	public void setInjectType(String injectType) {
		this.injectType = injectType;
	}

	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}

	public void setPropertyValues(MutablePropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "beanName=" + beanName + ",beanClass=" + beanClass + ",scope="
				+ scope;
	}
}
