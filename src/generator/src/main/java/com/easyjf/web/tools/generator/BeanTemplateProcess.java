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

import java.util.List;

import org.apache.velocity.context.Context;

public class BeanTemplateProcess implements TemplateProcess {
	private final static String defaultKeyField = "id";

	private final static String defaultKeyGenerator = "com.easyjf.dbo.NullIdGenerator";

	private String tableName;

	private String packageName = "com.easyjweb.business";

	private String keyField;

	private String keyGenerator;

	public BeanTemplateProcess() {

	}

	public BeanTemplateProcess(String tableName) {
		this.tableName = tableName;
	}

	public void process(Context context) {
		// TODO Auto-generated method stub
		try {
			List list = GeneratorUtil.jdbcField2Java(tableName);
			context.put("package", packageName);
			context.put("fieldList", list);
			context.put("tableKeyFiled", keyField == null ? defaultKeyField
					: keyField);
			context.put("tabelIdGenerator",
					keyGenerator == null ? defaultKeyGenerator : keyGenerator);
		} catch (Exception e) {

		}
		context.put("tableName", tableName);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public void setKeyGenerator(String keyGenerator) {
		this.keyGenerator = keyGenerator;
	}
}
