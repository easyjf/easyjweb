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

public class GeneratorWebEditPage extends AbstractGenerator {
	private String pageDir = "/webapps/WEB-INF/easyjweb";
	private String editPageTemplateFile = "/page/editPage.html";

	protected void initGenerator() {
		// TODO Auto-generated method stub
		tg.setTemplateName(this.editPageTemplateFile);
		tg.setTargetDir(GeneratorUtil.getRealTemplaeDir(this.pageDir));
		tg.setTemplateName(this.editPageTemplateFile);
		tg.setTargetName("/" +  this.tableName + "Edit.html");
		tg.setProcess(new PageTemplateProcess(this.tableName));
	}

	protected void parseArgs() {
		// TODO Auto-generated method stub
		
	}
}
