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

public class GeneratorWebAction extends AbstractGenerator {
	private String beanDir = "/src/main";
	private String actionPackage = "com.easyjweb.action";
	private String actionTemplateFile = "/java/crudAction.java";
	private String beanPackage;
	protected void initGenerator() {
		// TODO Auto-generated method stub
		tg.setTemplateName(actionTemplateFile);
		tg.setTargetDir(GeneratorUtil.getRealTemplaeDir(beanDir));
		tg.setTargetName("/" + actionPackage.replaceAll("\\.", "/")
				+ "/" + tableName + "Action.java");
		CrudActionTemplateProcess process=new CrudActionTemplateProcess(tableName);
		process.setPackageName(this.actionPackage);
		if(beanPackage!=null)process.setBeanPackage(this.beanPackage);
		tg.setProcess(process);	
	}	
	protected void parseArgs()
	{
		if(this.args!=null && args.length>0)
		{
		for(int i=0;i<args.length;i++)
		{
			int endPrefix = args[i].indexOf('=');
			if (endPrefix > 0) {
				String prefix =GeneratorUtil.getArgKey(args[i]);
				String value=GeneratorUtil.getArgValue(args[i]);
				if("package".equalsIgnoreCase(prefix) && value!=null)this.actionPackage=value;
				else if("beanPackage".equalsIgnoreCase(prefix)&&value!=null)this.beanPackage=value;
			}
		}
		}
	}
	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}
	public void setActionTemplateFile(String actionTemplateFile) {
		this.actionTemplateFile = actionTemplateFile;
	}
	public void setBeanDir(String beanDir) {
		this.beanDir = beanDir;
	}	
}
