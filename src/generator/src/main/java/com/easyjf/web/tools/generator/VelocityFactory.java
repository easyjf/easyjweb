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
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import com.easyjf.util.I18n;

public class VelocityFactory {
	private static VelocityEngine ve;

	private VelocityFactory() {
	}

	private static VelocityEngine getVEngine(String templateDir) {
		if (ve == null)
			doInit(templateDir);
		
		ve.setProperty("file.resource.loader.path", templateDir);
		return ve;
	}

	private static void doInit(String templateDir) {
		Properties p = new Properties();
		ve = new VelocityEngine();		
		File f=new File(templateDir);
		System.out.println(f.getAbsolutePath());
		if(!f.exists())System.out.println(I18n.getLocaleMessage("generator.Template.directory") + templateDir+I18n.getLocaleMessage("generator.Does.not.exist"));
		p.setProperty("file.resource.loader.path", templateDir);
		try {
			ve.init(p);
		} catch (Exception e) {
			System.out.println(I18n.getLocaleMessage("generator.Initialization.error") + e);
		}
	}

	public static VelocityEngine getVelocityEngine(String templateDir) {

		Properties p = new Properties();
		VelocityEngine ve = new VelocityEngine();
		p.setProperty("file.resource.loader.path", templateDir);
		try {
			ve.init(p);
		} catch (Exception e) {
			System.out.println(I18n.getLocaleMessage("generator.Initialization.error") + e);
		}
		return ve;
	}

	public static Template getTemplate(String templateDir, String fileName)
			throws Exception {
		return getTemplate(templateDir, fileName, "UTF-8");
	}

	public static Template getTemplate(String templateDir, String fileName,
			String encoding) throws Exception {
		Template template = null;
		try {
			template = getVEngine(templateDir).getTemplate(fileName, encoding);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return template;
	}
}