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
package com.easyjf.web;

import java.io.Serializable;

/**
 * 关联对象加载器，主要用来根据主键值加载模型(域)对象中的关联属性
 * 
 * @author 大峡
 * 
 */
public interface POLoadDao {
	/**
	 * 从持久(或业务)层加载特定id值，类型为clz的对象。
	 * @param clz 类型名称
	 * @param id 主键值
	 * @return 返回加载的属性对象
	 */
	Object get(Class clz, Serializable id);
}
