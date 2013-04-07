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

import java.util.List;
import java.util.Map;

import com.easyjf.web.config.ConfigureResourceLoader;

/**
 * EasyJWeb中的配置信息，框架中所有部件或对象访问系统配置信息都是由该类访问。
 * 
 * @author 大峡
 * 
 */
public interface WebConfig {

	/**
	 * 执行配置信息初始化
	 * 
	 */
	void init();

	/**
	 * 设置配置文件信息
	 * 
	 * @param configures
	 *            使用的配置文件
	 */
	void setConfigures(String[] configures);

	/**
	 * 模板基础路径
	 * 
	 * @return 当前系统中的模板基础路径
	 */
	String getTemplateBasePath();

	/**
	 * 设置系统中的模板基础路径
	 * 
	 * @param templateBasePath
	 *            模板基础路径
	 */
	void setTemplateBasePath(String templateBasePath);

	/**
	 * 检测系统是否运行在调试状态
	 * 
	 * @return 如果在调试状态则返回true，否则返回false
	 */
	boolean isDebug();

	/**
	 * 得到当前系统中所有定义的表单WebForm
	 * 
	 * @return 系统中的所有的表单WebForm
	 */
	Map getForms();

	/**
	 * 获取系统中的的所有模块Module信息
	 * 
	 * @return 系统中的所有模块Module信息
	 */
	Map getModules();

	/**
	 * 获取系统中的所有全局页面配置信息
	 * 
	 * @return 系统中的所有全局页面信息
	 */
	Map getPages();

	/**
	 * 得到初始化程序 也即在启动EasyJWeb框架时，自动执行的初始化应用程序。
	 * 
	 * @return 系统中的初始化程序
	 */
	List getInitApps();

	/**
	 * 获取系统中的所有全局拦截器
	 * 全局拦截器是针对所有请求都会执行的，拦截器可以是前置拦截BeforeInterceptor，后拦截AfterInterceptor或者是环绕拦截AroundInterception几种
	 * 
	 * @return 系统中的全局拦截器
	 */
	List getInterceptors();

	/**
	 * 获取系统中的异常(错误)处理器
	 * EasyJWeb通过配置在系统中的这些异常处理器，统一进行所有异常(错误)的拦截处理工作。
	 * 
	 * @return 所有异常处理器
	 */
	List getErrorHandler();

	/**
	 * 获取系统中的所有Bean定义及配置信息
	 * 
	 * @return 系统中的所有Bean配置信息
	 */
	List getBeanDefinitions();

	/**
	 * 得到系统中上传文件的缓冲
	 * 
	 * @return 系统上传文件缓冲大小
	 */
	int getUploadSizeThreshold();

	/**
	 * 得到系统中允许的最大上传文件数
	 * 
	 * @return 允许上传的文件最大值
	 */
	int getMaxUploadFileSize();

	/**
	 * 设置系统中的资源加载器
	 * 
	 * @param loader
	 *            需要使用的资源加载器
	 */
	void setResourceLoader(ConfigureResourceLoader loader);

	/**
	 * 设置在Action中能直接跳转到其他模块的Action上的最大次数， 防止死锁， 默认为3次。
	 * 
	 * @return 能直接跳转的最大次数
	 */
	Integer getMaxDirectJumpToActionTimes();
	/**
	 * 获取用户默认的Action包
	 * @return 返回零配置情况下用户的默认Action包路径
	 */
	String[] getDefaultActionPackages();
	/**
	 * 从配置中查询一个Module,只有在成功启动后使用
	 * @param name module的名称
	 * @return 如果成功，则返回该Module,否则返回null
	 */
	Module findModule(String name);
	/**
	 * 是否启动Action的权限系统
	 * @return
	 */
	boolean isPermissionVerify();
}
