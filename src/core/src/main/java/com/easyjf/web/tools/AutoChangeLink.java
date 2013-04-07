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
package com.easyjf.web.tools;
/**
 * 动静态地址自动转换对象
 * @author 大峡
 *
 */
public interface AutoChangeLink {
	/**
	 * 返回对象的静态html地址
	 * @return 返回对象的静态html地址，当没有设置html地址时，返回url
	 */
	String getStaticUrl();

	/**
	 * 返回对象的动态显示地址
	 * @return 返回对象的动态显示url
	 */
	String getDynamicUrl();
}
