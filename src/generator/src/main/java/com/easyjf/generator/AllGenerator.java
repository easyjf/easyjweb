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
package com.easyjf.generator;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.easyjf.util.I18n;
import com.easyjf.web.tools.generator.TemplateGenerator;

public class AllGenerator {

	protected String templateDir = "../templates/";

	protected String beanName;

	private String packageName;

	private Map<String, String> javafiles;

	private Map<String, String> xmlfiles;

	private Map<String, String> htmlfiles;

	private String lowerBeanName = "";
	private String pri;
	protected TemplateGenerator tg = new TemplateGenerator();
	
	private void init() {
		String bea = beanName.substring(beanName.lastIndexOf(".") + 1);
		String bealower = bea.substring(0, 1).toLowerCase() + bea.substring(1);
		this.lowerBeanName = bealower;
		addJavaFiles(bea);
		addXmlFiles();
		addHtmlFiles(bealower);
	}

	private void addJavaFiles(String bea) {
		Map<String, String> javaMapping = new HashMap<String, String>();
		javaMapping.put("actionTemplate.java", "mvc/" + bea + "Action.java");
		javaMapping.put("daotemplate.java", "dao/" + "I" + bea + "DAO.java");
		javaMapping.put("IserviceTemplate.java", "service/" + "I" + bea
				+ "Service.java");
		javaMapping.put("serviceImplTemplate.java", "service/impl/" + bea
				+ "ServiceImpl.java");
		this.javafiles = javaMapping;
	}

	private void addXmlFiles() {
		Map<String, String> xmlMapping = new HashMap<String, String>();
//		xmlMapping.put("mvc.xml",this.pri+"src/main/java/mvc-app.xml");
		xmlMapping.put("dao.xml", this.pri+"src/main/resources/dao.xml");
		xmlMapping.put("service.xml", this.pri+"src/main/resources/service.xml");
		this.xmlfiles = xmlMapping;
	}

	private void addHtmlFiles(String bealower) {
		Map<String, String> htmlMapping = new HashMap<String, String>();
		htmlMapping.put("edit.html", bealower + "/edit.html");
		htmlMapping.put("list.html", bealower + "/list.html");
//		htmlMapping.put("preview.html", bealower + "/preview.html");
		this.htmlfiles = htmlMapping;
	}

	public AllGenerator(String[] args) {
		String bean = args[0];
		String packageName = args[1];
		//System.out.println(bean);
		//System.out.println(packageName);
		
		this.beanName = bean;
		this.packageName = packageName;
	}

	public void gener() {
		init();		
		AllProcessor processor = new AllProcessor();
		processor.setBeanClass(beanName);
		processor.setPackageName(packageName);	
		//System.out.println(processor.getPackageName());
		//System.out.println(processor.getBeanClass());
		genJava(processor);
//		System.out.println("++++++++++++++++++++++++");
		genHtml(processor);
		genXml(processor);
	}

	private void genJava(AllProcessor processor) {
		for (String templateFile : this.javafiles.keySet()) {
			String targetFile = this.javafiles.get(templateFile);
			tg.setProcess(processor);
			String td = (this.pri+"src/main/java/"
					+ packageName.replaceAll("\\.", "/") + "/");
			tg.setTargetDir(td);
			tg.setTargetName(targetFile);
			tg.setTemplateDir(templateDir);
			tg.setTemplateName(templateFile);
			tg.generator(false);
		}
	}

	private void genHtml(AllProcessor processor) {
		for (String templateFile : this.htmlfiles.keySet()) {
			String targetFile = this.htmlfiles.get(templateFile);
			tg.setProcess(processor);
			// String folder = packageName
			// .substring(packageName.lastIndexOf(".") + 1);
			// tg.setTargetDir("src/main/webapp/WEB-INF/views/"
			// + folder + "/");
			tg.setTargetDir(this.pri+"src/main/webapp/WEB-INF/views/");
			tg.setTargetName(targetFile);
			tg.setTemplateDir(templateDir);
			tg.setTemplateName(templateFile);
			tg.generator(false);
		}
	}

	private Document getDocument(String xmlFileName) throws DocumentException {
		SAXReader reader = new SAXReader();
		File file = new File(xmlFileName);
		if (file.exists()) {
			return reader.read(xmlFileName);
		} else {
			return DocumentHelper.createDocument();
		}
	}

	private void genXml(AllProcessor processor) {
		for (String templateFile : this.xmlfiles.keySet()) {
			tg.setProcess(processor);
			String fileName = (String) xmlfiles.get(templateFile);
			File targetFile = new File(fileName);
			tg.setTargetDir("");
			tg.setTemplateDir(templateDir);
			tg.setTemplateName(templateFile);
			if (targetFile.exists()) {
				tg.setTargetName(fileName + "_tmp");
				tg.generator(false);
				try {
					Document doc = this.getDocument(fileName + "_tmp");
					Document document = this.getDocument(fileName);
					String existNode = "/easyjf-web/modules/module[@name='"
							+ this.lowerBeanName + "']";

					Node node = "mvc.xml".equals(templateFile) ? document
							.selectSingleNode(existNode) : findBean(document,
							this.lowerBeanName
									+ ("dao.xml".equals(templateFile) ? "Dao"
											: "Service"));
					if (node == null) {
						String appendNode = "/easyjf-web/modules", cnode = appendNode
								+ "/module";
						if (!"mvc.xml".equals(templateFile))
							appendNode = "/beans";
						Element moduleE = (Element) document
								.selectSingleNode(appendNode);
						Node n = "mvc.xml".equals(templateFile) ? doc
								.selectSingleNode(cnode)
								: findBean(
										doc,
										this.lowerBeanName
												+ ("dao.xml"
														.equals(templateFile) ? "Dao"
														: "Service"));
						if (moduleE!=null && n != null) {
							n.detach();
							moduleE.add(n);
						}
						OutputFormat format = OutputFormat.createPrettyPrint();
						XMLWriter output = new XMLWriter(new FileWriter(
								new File(fileName)), format);
						output.write(document);
						output.close();
					}
					new File(fileName + "_tmp").deleteOnExit();
					new File(fileName + "_tmp").delete();
				} catch (Exception e) {
//					e.printStackTrace();
				}
				System.out.println(I18n.getLocaleMessage("generator.Successfully.add.to.configuration.information.to.a.file") + fileName);
			} else {
				System.out.println(I18n.getLocaleMessage("generator.Successful.configuration.file.generation") + fileName);
				tg.setTargetName(fileName);
				new File(fileName + "_tmp").deleteOnExit();
				new File(fileName + "_tmp").delete();
				tg.generator(false);
			}

		}
	}

	@SuppressWarnings("unchecked")
	private Element findBean(Document document, String beanName) {
		Element beans = (Element) document.selectSingleNode("/beans");
		Element bean = null;
		if (beans != null) {
			List e = beans.elements("bean");
			for (int i = 0; i < e.size(); i++) {
				Element n = (Element) e.get(i);
				if (beanName.equals(n.attributeValue("id")))
					bean = n;
			}
		}
		return bean;
	}

	public void setPri(String pri) {
		this.pri = pri;
	}
	
}
