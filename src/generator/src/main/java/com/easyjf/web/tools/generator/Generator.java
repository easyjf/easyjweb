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
package com.easyjf.web.tools.generator;

/**
 * 代码生成引擎接口，用于生成各式各样的代码
 * 
 * @author 大峡
 * 
 */
public interface Generator {
	/**
	 * 返回生成目标目录
	 * @return
	 */
	//public String getTargetDir();

	/**
	 * 设置生成目标目录
	 * 
	 * @param targetDir
	 *            存放生成代码的目录
	 */
	public void setTargetDir(String targetDir);

//	public String getTargetName();

	/**
	 * 设置代码的文件名称
	 * 
	 * @param targetName
	 *            生成代码的文件名
	 */
	public void setTargetName(String targetName);

//	public String getTemplateDir();

	/**
	 * 设置代码模板目录
	 * 
	 * @param templateDir
	 *            代码模板目录
	 */
	public void setTemplateDir(String templateDir);

//	public String getTemplateName();

	/**
	 * 设置代码模板名称
	 * 
	 * @param templateName
	 *            代码模板名称
	 */
	public void setTemplateName(String templateName);

	/**
	 * 设置表名称
	 * @param tableName
	 */
	public void setTableName(String tableName);
	/**
	 * 设置生成器的参数
	 * @param args 参数数组
	 */
	public void setArgs(String[] args);
	/**
	 * 执行生成操作
	 * 
	 */
	void generator(boolean append) throws java.lang.RuntimeException;
}
