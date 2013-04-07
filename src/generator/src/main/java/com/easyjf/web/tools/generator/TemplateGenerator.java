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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;

import com.easyjf.util.I18n;

public class TemplateGenerator {
	// private static final Logger logger = (Logger) Logger
	// .getLogger(TemplateGenerator.class.getName());

	private String templateDir;// 模板主目录

	private String targetDir;// 生成文件存放位置主目录

	private String templateName;// 模块文件名

	private String targetName;// 生成目标文件名

	private TemplateProcess process;// 处理器，负责执行相应的商业逻辑，并把数据添加到context当中

	private Context context = null;

	private Template template = null;

	/**
	 * 生成目标文件
	 * 
	 * @param append
	 *            是否增加状态
	 */
	public void generator(boolean append) {
		context = new VelocityContext();
		File file = new File(targetDir + targetName);
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
		template = VelocityFactory.getTemplate(templateDir, templateName,
				"UTF-8");		
			if (process != null)
				process.process(context);
			mergeTemplate(template, context, file, append);
			if (file.toString().lastIndexOf("_tmp") > 0
					|| file.toString().lastIndexOf(".tmp") > 0) {
			} else {
				System.out.println(I18n.getLocaleMessage("generator.Successfully.generated.documents")+ file.getAbsolutePath());
			}
		} catch (Exception e) {
			System.out.println(I18n.getLocaleMessage("generator.code.Generation.error") + e);
			e.printStackTrace();
		}
	}

	/*
	 * public void generator() { context = new VelocityContext(); File file =
	 * new File(targetDir + targetName); if (!file.exists())
	 * file.getParentFile().mkdirs(); template =
	 * VelocityFactory.getTemplate(templateDir, templateName, "UTF-8"); try { if
	 * (process != null) process.process(context); mergeTemplate(template,
	 * context, file); System.out.println("成功生成了文件:" + file.getAbsolutePath()); }
	 * catch (Exception e) { System.out.println("生成出错，请确认数据库配置及表名是否正确！" + e);
	 * e.printStackTrace(); } }
	 */
	private void mergeTemplate(Template template, Context context, File file,
			boolean append) {
		Writer writer = null;
		VelocityWriter vw = null;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file, append),
					"UTF-8");
			vw = new VelocityWriter(writer, 4 * 1024, true);
			template.merge(context, vw);
		} catch (Exception e) {
			System.out.println(I18n.getLocaleMessage("generator.merge.error") + e);
			// logger.error("合成错误：" + e);
			e.printStackTrace();
		} finally {
			if (vw != null) {
				try {
					vw.flush();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					// logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}/*
		 * private void mergeTemplate(Template template, Context context, File
		 * file) { Writer writer = null; VelocityWriter vw = null; try { writer =
		 * new OutputStreamWriter(new FileOutputStream(file),
		 * template.getEncoding() != null ? template.getEncoding() : "UTF-8");
		 * vw = new VelocityWriter(writer, 4 * 1024, true);
		 * template.merge(context, vw); } catch (Exception e) {
		 * logger.error("合成错误：" + e); e.printStackTrace(); } finally { if (vw !=
		 * null) { try { vw.flush(); } catch (Exception e) {
		 * logger.error(e.getMessage()); e.printStackTrace(); } } } }
		 */

	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTemplateDir() {
		return templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public TemplateProcess getProcess() {
		return process;
	}

	public void setProcess(TemplateProcess process) {
		this.process = process;
	}

	public static void test() {
		TemplateGenerator tg = new TemplateGenerator();
		tg.setTemplateDir("c:\\tools");
		tg.setTemplateName("/java/bean.java");
		tg.setTargetDir("c:\\tools\\target\\");
		tg.setTargetName("/Person.java");
		tg.setProcess(new BeanTemplateProcess("Person"));
		tg.generator(false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TemplateGenerator.test();
	}
}
