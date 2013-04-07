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

import org.apache.velocity.context.Context;

public class CrudActionTemplateProcess implements TemplateProcess {
	private String tableName;
	private String packageName="com.easyjweb.action";
	private String beanPackage="com.easyjweb.business";
	public CrudActionTemplateProcess()
	{
		
	}
	public CrudActionTemplateProcess(String tableName)
	{
		this.tableName=tableName;
	}
	public void process(Context context) {
		// TODO Auto-generated method stub
		context.put("tableName",this.tableName);
		context.put("package", packageName);
		context.put("beanPackage", beanPackage);
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getBeanPackage() {
		return beanPackage;
	}
	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}
	
}
