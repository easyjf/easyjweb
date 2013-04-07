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
package com.easyjf.util;

/**
 * 权限判断标签实用工具
 * 
 * @author 大峡
 * 
 */
public interface AuthorizationUtil {
	/**
	 * 根据名称判断用户角色,从而判断用户是否刻有该权限.roleName可以是单独的名称,大小写可以任意.可以包含空格等.
	 * 若要使用多个角色,可以使用+、|、~、,等符号来表示组合关系 使用方法：#if($ROLE.is("ADMIN"))<a
	 * href="javascript:doDel()">删除</a>#end 或：#if($ROLE.is("AMDIN,Manager"))<a
	 * href="">删除所有</a>#end
	 * 
	 * @param roleName
	 * @return 若当前用户属于指定角色则返回true，否则返回false
	 */
	boolean is(String roleName);

	/**
	 * 判断一个用户是否对指定的对象有指定的操作权限
	 * 
	 * @param operation
	 *            操作,"del","create","update","read"等
	 *            使用方法：#if($ROLE.is("del",$obj))<a href="">删除该记录</a>#end
	 * @param obj
	 *            操作的对象
	 * @return 如果具有操作权限则返回true，否则返回false
	 */
	boolean is(String operation, Object obj);
	/**
	 * 得到用户
	 * @return
	 */
	Object getUser();
}
