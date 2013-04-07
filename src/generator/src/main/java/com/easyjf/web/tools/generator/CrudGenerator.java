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

import java.io.File;
/**
 * 代码生成核心门面
 * @author 大峡
 *
 */
public class CrudGenerator {
	private String templateDir = "/webapps/WEB-INF/easyjwebtools";

	private String pageDir = "/webapps/WEB-INF/easyjweb";

	private String beanDir = "/src/main";

	private String domainBeanPackage = "com.easyjweb.business";

	private String actionPackage = "com.easyjweb.action";

	private String editPageTemplateFile = "/page/editPage.html";

	private String listPageTemplateFile = "/page/listPage.html";

	private String beanTemplateFile = "/java/bean.java";

	private String actionTemplateFile = "/java/crudAction.java";

	private String tableName;

	private String mainDir;
	
	public CrudGenerator() {

	}

	public CrudGenerator(String tableName) {
		this.tableName = tableName;
	}
	/**
	 * 生成Web层的控制器Action
	 *
	 */
	public void generatorWebAction()
	{
		//生成com.easyjweb.action.tableAction.java
		TemplateGenerator tg = new TemplateGenerator();		
		String javaDir = new File(mainDir, beanDir).getAbsolutePath();		
		tg.setTemplateDir(initTemplaeDir());
		tg.setTemplateName(actionTemplateFile);
		tg.setTargetDir(javaDir);
		tg.setTargetName("/" + actionPackage.replaceAll("\\.", "/")
				+ "/" + tableName + "Action.java");
		tg.setProcess(new CrudActionTemplateProcess(tableName));
		tg.generator(false);
	}
	/**
	 * 生成Web层的模板页面
	 *
	 */
	public void generatorWebEditPage()
	{
		TemplateGenerator tg = new TemplateGenerator();		
		String templatePageDir = new File(mainDir, pageDir).getAbsolutePath();		
		// 生成tableEdit.html		
		tg.setTemplateDir(initTemplaeDir());
		tg.setTemplateName(editPageTemplateFile);
		tg.setTargetDir(templatePageDir);
		tg.setTargetName("/" + tableName + "Edit.html");
		tg.setProcess(new PageTemplateProcess(tableName));
		tg.generator(false);
	}
	public void generatorWebListPage()
	{
		TemplateGenerator tg = new TemplateGenerator();		
		String templatePageDir = new File(mainDir, pageDir).getAbsolutePath();		
		//		 生成tableList.html
		tg.setTemplateName(listPageTemplateFile);
		tg.setTargetDir(templatePageDir);
		tg.setTargetName("/" + tableName + "List.html");
		tg.setProcess(new PageTemplateProcess(tableName));
		tg.generator(false);
	}
	/**
	 * 生成简单的DomainBean
	 *
	 */
	public void generatorDomainBean()
	{
		TemplateGenerator tg = new TemplateGenerator();	
		String javaDir = new File(mainDir, beanDir).getAbsolutePath();
		// 生成com.easyjweb.business.table.java
		tg.setTemplateDir(initTemplaeDir());
		tg.setTemplateName(beanTemplateFile);
		tg.setTargetDir(javaDir);
		tg.setTargetName("/" + domainBeanPackage.replaceAll("\\.", "/") + "/"
				+ tableName + ".java");
		tg.setProcess(new BeanTemplateProcess(tableName));
		tg.generator(false);
	}
	/**
	 * 生成DAO
	 *
	 */
	public void generatorDao()
	{
		
	}
	/**
	 * 生成业务逻辑层代码
	 *
	 */
	public void generatorLogic()
	{
		
	}
	/**
	 * 生成业务逻辑层的测试代码
	 *
	 */
	public void generatorTestService()
	{
		
	}
	/**
	 * 生成数据访问层的测试代码
	 *
	 */
	public void generatorTestDao()
	{
		
	}
	public void generator() {		
	generator("crud");
	}
	public void generator(String type)
	{
		 mainDir = new File(System.getProperty("user.dir"))
			.getParentFile().getAbsolutePath();
		if("domainBean".equals(type))
			this.generatorDomainBean();
		else if("action".equals(type))
			this.generatorWebAction();
		else if("page".equals(type))
		{
			this.generatorWebEditPage();
			this.generatorWebListPage();
		}			
		else if("logic".equals(type))
			this.generatorLogic();
		else if("dao".equals(type))
			this.generatorDao();
		else if("testDao".equals(type))
			this.generatorTestDao();
		else if("testLogic".equals(type))
			this.generatorLogic();
		else if("crud".equals(type))
		{
			this.generatorWebEditPage();
			this.generatorWebListPage();
			this.generatorDomainBean();
			this.generatorWebAction();
		}		
	}

	private String initTemplaeDir()
	{
		return new File(mainDir, templateDir).getAbsolutePath();
	}	
	public String getActionTemplateFile() {
		return actionTemplateFile;
	}

	public void setActionTemplateFile(String actionTemplateFile) {
		this.actionTemplateFile = actionTemplateFile;
	}

	public String getBeanDir() {
		return beanDir;
	}

	public void setBeanDir(String beanDir) {
		this.beanDir = beanDir;
	}

	public String getBeanTemplateFile() {
		return beanTemplateFile;
	}

	public void setBeanTemplateFile(String beanTemplateFile) {
		this.beanTemplateFile = beanTemplateFile;
	}

	public String getActionPackage() {
		return actionPackage;
	}

	public void setActionPackage(String actionPackage) {
		this.actionPackage = actionPackage;
	}

	public String getDomainBeanPackage() {
		return domainBeanPackage;
	}

	public void setDomainBeanPackage(String domainBeanPackage) {
		this.domainBeanPackage = domainBeanPackage;
	}

	public String getEditPageTemplateFile() {
		return editPageTemplateFile;
	}

	public void setEditPageTemplateFile(String editPageTemplateFile) {
		this.editPageTemplateFile = editPageTemplateFile;
	}

	public String getListPageTemplateFile() {
		return listPageTemplateFile;
	}

	public void setListPageTemplateFile(String listPageTemplateFile) {
		this.listPageTemplateFile = listPageTemplateFile;
	}

	public String getPageDir() {
		return pageDir;
	}

	public void setPageDir(String pageDir) {
		this.pageDir = pageDir;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}
	public static void main(String[] args) {
		String tableName = null;
		for (int i = 0; i < args.length; i++) {
			if (!"-".equals(args[i].substring(0, 1)))
				tableName = args[i];
			if (tableName != null)
				new CrudGenerator(tableName).generator();
		}		
	}
}
