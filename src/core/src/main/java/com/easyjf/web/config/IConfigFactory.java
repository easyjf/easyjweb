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
package com.easyjf.web.config;

import java.util.Map;

import org.dom4j.Document;

/**
 * 
 * <p>
 * Title:配置信息处理接口
 * </p>
 * <p>
 * Description:配置工厂接口定义,目的在于支持多种配置方式。
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */

public interface IConfigFactory {

	public final static String DEBUG = "com.easyjweb.debug";

	public final static String MaxUploadFileSize = "com.easyjweb.maxUploadFileSize";

	public final static String UploadSizeThreshold = "com.easyjweb.uploadSizeThreshold";

	public final static String MaxDirectJumpToActionTimes = "com.easyjweb.maxDirectJumpToActionTimes";
	
	public final static String DefaultActionPackage="com.easyjweb.defaultActionPackages";
	
	public final static String MessageResourceLoader="com.easyjweb.messageResourceLoader";
	public final static String Language="com.easyjweb.language";
	public final static String PropertiesType="com.easyjweb.propertiesType";
	public final static String PermissionVerify="com.easyjweb.permissionVerify";

	/**
	 * 初始化表单信息
	 * 
	 * @param forms
	 */
	public void initForm(Map forms);

	/**
	 * 初始化Module信息
	 * 
	 * @param module
	 */
	public void initModule(Map module);

	/**
	 * 初始化Page信息
	 * 
	 * @param page
	 */
	public void initPage(Map page);

	/**
	 * 初始化其它信息，存放到HashMap中
	 * 
	 * @return 其它
	 */
	public Map initOther();

	public Document getDoc();
}
