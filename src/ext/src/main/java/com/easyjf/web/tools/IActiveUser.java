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
 * 当前用户类
 * @author 大峡
 *
 */
public interface IActiveUser {
	/**
	 * 获取用户名
	 * @return 返回当前操作的用户
	 */
	String getUserName();

	/**
	 * 攻取用户ip
	 * @return 当前用户的ip地址
	 */
	String getIp();
}
