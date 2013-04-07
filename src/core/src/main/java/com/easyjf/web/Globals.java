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

import java.util.Locale;

/**
 * 
 * <p>
 * Title:全局配置信息
 * </p>
 * <p>
 * Description: 配置框架的一些系统变量及值
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友 张钰 吴嘉俊 williamraym
 * @version 1.0
 */
public abstract class Globals {

	/**
	 * EasyJWeb 当前版本
	 */
	public final static String VERSION = "1.2-beta1";

	/**
	 * EasyJWeb缺省语言
	 */
	public  static Locale LANGUAGE = Locale.getDefault();

	/**
	 * 语言的资源文件名
	 */
	public final static String LANGUAGE_PROPERTIES = "com.easyjf.web.resources.easyjweb";
	/**
	 * EasyJWeb缺省的配置文件名
	 */
	public final static String CONFIG_FILE = "/WEB-INF/easyjf-web.xml";

	/**
	 * 在程序启动的时候根据环境修改为正确的路径
	 */
	public static String CONFIG_FILE_FULL_PATH = "/WEB-INF/easyjf-web.xml";

	/**
	 * 解析配置文件的工厂
	 */
	public final static String CONFIG_FACTORY_CLASS = "com.easyjf.web.config.XMLConfigFactory";

	/**
	 * 默认的Form
	 */
	public final static String DEFAULT_FORM_CLASS = "com.easyjf.web.WebForm";

	/**
	 * 默认的Action存放位置
	 */
	public final static String DEFAULT_ACTION_CLASS = "com.easyjf.web.Action";

	/**
	 * 默认action存放位置
	 */
	public final static String DEFAULT_ACTTION_PACKAGE = "com.easyjweb.action";

	/**
	 * 默认模板视图存放在/WEB-INF/views目录
	 */
	public final static String DEFAULT_TEMPLATE_PATH = "/WEB-INF/views";

	/**
	 * 映射后缀名
	 */
	public static String MAPPING_SUFFIX="ejf";
	
	/**
	 * 默认模板扩展名为html
	 */
	public static String DEFAULT_TEMPLATE_EXT = "html";

	/**
	 * 模板类型
	 */
	public final static String PAGE_TEMPLATE_TYPE = "template";

	/**
	 * 禁止重复提交
	 */
	public final static String TRANSACTION_FORBITREP_KEY = "com.easyjf.web.FORBITREP";

	/**
	 * EasyJWeb容器上下文，其他的非EasyJWeb应用可以通过这个标识从ServletContext中取得EasyJWeb容器信息
	 */
	public final static String CONTAINER_CONTEXT = "EasyJWeb_Container_Context";

	/**
	 * 是否通过Class环境加载资消息资源文件
	 */
	public static boolean LOAD_MESSAGE_RESOURCE_FROM_PATH=false;
	/**
	 * 资源文件的位置
	 */
	public static String RESOURCE_FILE_PATH = "applicationResources/";
	
	/**
	 * 指定系统中多国语言属性文件的类型，默认为properties文件，同时也支持xml属性文件
	 */
	public static String PROPERTIES_TYPE = "properties";
	/**
	 * 存放用户指定的语言
	 */
	public static String LOCALE_SESSION = "EasyJWeb_Locale_Session";
	
	/**
	 * 全局资源文件
	 */
	public static String APPLICATION_PROPERTIES_PREFIX = "application";
	
	

	/**
	 * 应用程序根目录，在Web环境中启动EasyJWeb后，该值会变成实现的应用程序目录如c:\tomcat
	 */
	public static String APP_BASE_DIR = "/";

	/**
	 * EasyJWeb容器配置文件的位置
	 */
	public static String CONTAINER_CONFIG_LOCATION = "containerConfigLocation";

	public static String SPRING_INTEGERATION_CONTAINER = "SpringIntegerationContainer";

	public static final String TOKEN_NAME = "com.easyjf.easyjweb.token";
}
