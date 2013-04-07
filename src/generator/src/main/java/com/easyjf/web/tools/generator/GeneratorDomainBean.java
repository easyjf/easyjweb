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

import com.easyjf.util.JdkVersion;

public class GeneratorDomainBean extends AbstractGenerator {
	private String beanDir = "/src/main";

	private String domainBeanPackage = "com.easyjweb.business";

	private String beanTemplateFile = "/java/bean.java";
	private String beanJdk5TemplateFile="/java/bean-jdk5.java";
	protected void initGenerator() {
		// TODO Auto-generated method stub
		if(JdkVersion.getJavaVersion()>=JdkVersion.JDK_1_5)
			tg.setTemplateName(beanJdk5TemplateFile);
		else
		tg.setTemplateName(beanTemplateFile);		
		tg.setTargetDir(GeneratorUtil.getRealTemplaeDir(beanDir));
		tg.setTargetName("/" + domainBeanPackage.replaceAll("\\.", "/") + "/"
				+ tableName + ".java");
		BeanTemplateProcess process=new BeanTemplateProcess(tableName);
		process.setPackageName(domainBeanPackage);
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
				if("package".equalsIgnoreCase(prefix) && value!=null)this.domainBeanPackage=value;
			}
		}
		}
	}
	public void setBeanDir(String beanDir) {
		this.beanDir = beanDir;
	}
	public void setBeanTemplateFile(String beanTemplateFile) {
		this.beanTemplateFile = beanTemplateFile;
	}
	public void setDomainBeanPackage(String domainBeanPackage) {
		this.domainBeanPackage = domainBeanPackage;
	}

	public void setBeanJdk5TemplateFile(String beanJdk5TemplateFile) {
		this.beanJdk5TemplateFile = beanJdk5TemplateFile;
	}	
	
}
