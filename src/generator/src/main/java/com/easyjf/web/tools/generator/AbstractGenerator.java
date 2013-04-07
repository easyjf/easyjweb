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

public abstract class AbstractGenerator implements Generator {

	protected String templateDir = "/webapps/WEB-INF/easyjwebtools";

	protected String tableName;

	protected String[] args;

	protected TemplateGenerator tg = new TemplateGenerator();

	public void generator(boolean append) throws RuntimeException {
		// TODO Auto-generated method stub
		this.parseArgs();// 第一步，首先解析参数
		this.initGenerator();// 第二步，设置特殊性参数
		tg.setTemplateDir(GeneratorUtil.getRealTemplaeDir(this.templateDir));// 设置模板路径
		tg.generator(false);// 执行生成
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTargetDir(String targetDir) {
		tg.setTargetDir(targetDir);
	}

	public void setTargetName(String targetName) {
		tg.setTargetName(targetName);
	}

	public void setTemplateDir(String templateDir) {
		tg.setTemplateDir(templateDir);
	}

	public void setTemplateName(String templateName) {

		tg.setTargetName(templateName);
	}

	public void setArgs(String[] args) {
		this.args = args.clone();
	}

	// 设置相关属性
	protected abstract void initGenerator();

	// 解析参数
	protected abstract void parseArgs();
}
