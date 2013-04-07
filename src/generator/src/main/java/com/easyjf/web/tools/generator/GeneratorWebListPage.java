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

import java.io.*;
public class GeneratorWebListPage extends AbstractGenerator {
	private String pageDir = "/webapps/WEB-INF/easyjweb";
	private String listPageTemplateFile = "/page/listPage.html";
	
	protected void initGenerator() {
		// TODO Auto-generated method stub
		tg.setTemplateName(listPageTemplateFile);
		tg.setTargetDir(GeneratorUtil.getRealTemplaeDir(pageDir));
		tg.setTargetName(File.separator + tableName + "List.html");
		tg.setProcess(new PageTemplateProcess(tableName));
	}

	protected void parseArgs() {
		// TODO Auto-generated method stub
		
	}
}
