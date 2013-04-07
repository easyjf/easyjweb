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
package com.easyjf.web.interceptor;
/**
 * 简单的代码封装
 * @author 大峡
 *
 */
public class SimpleProxyFactory {
private Object target;
private Class[] interfaces;
private Interceptor[] advices;
public Object createObject() throws Exception {	
	return java.lang.reflect.Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), interfaces, new SimpleInvocationHandler(target,advices));
}
public Class getObjectType() {
	return target.getClass();
}
public boolean isSingleton() {
	return false;
}
public void setTarget(Object target) {
	this.target = target;
}
public void setAdvices(Interceptor[] advices) {
	this.advices = advices;
}
public void setInterfaces(Class[] interfaces) {
	this.interfaces = interfaces;
}
}
