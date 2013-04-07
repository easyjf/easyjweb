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
//用来部分重新生成bean——tableName.java的代码
package com.easyjf.web.tools.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.velocity.context.Context;

public class ChangeBeanTemplateProcess implements TemplateProcess {

	private String beanDir = "/src/main";

	private String defaultBeanPackage = "com.easyjweb.business";

	private String tableName;// 数据表的名字；

	private String className;// 要重新生成的类名；

	private String fileName;// 原来生成的该bean.java的文件的文件名；

	public ChangeBeanTemplateProcess() {

	}

	public ChangeBeanTemplateProcess(String tableName) {
		String mainDir = new File(System.getProperty("user.dir"))
				.getParentFile().getAbsolutePath();
		String javaDir = new File(mainDir, beanDir).getAbsolutePath();
		this.tableName = tableName;
		this.className = this.defaultBeanPackage + "." + tableName;
		this.fileName = javaDir + "/"
				+ defaultBeanPackage.replaceAll("\\.", "/") + "/" + tableName
				+ ".java";
	}

	public void process(Context context) throws Exception {
		// TODO Auto-generated method stub
		// 根据数据库得到现在所有的属性名；
		List newProperties = getAllProperties();

		// 根据原来的类得到原来的所有属性，并从现在所有的属性中去除原来已经有了的；
		handleChangedProperties(newProperties);

		// 根据原来的文件得到除了import,package,public class等声明语句（具体的看现在的changebean模版）；
		// 其中的remains对应的就是原来文件的剩余的代码；
		String remain = readInRemain();

		// 存入context生成代码文件；
		context.put("fieldList", newProperties);
		context.put("tableKeyFiled", "cid");// 这里可以考虑改为从参数中读取
		context.put("TabelIdGenerator", "com.easyjf.dbo.RandomIdGenerator");// 此处以后改为从参数中读取
		context.put("remains", remain);
	}

	private List getAllProperties() {
		List list = null;
		try {
			list = GeneratorUtil.jdbcField2Java(this.tableName);
		} catch (Exception e) {
		}
		return list;
	}

	private void handleChangedProperties(List allProp) {
		List setterNames = null;
		try {
			setterNames = new ArrayList();
			final Method[] methods = Class.forName(className).getMethods();
			for (int i = 0; i < methods.length; i++) {
				final Method method = methods[i];
				final Class[] parameterTypes = method.getParameterTypes();
				if (parameterTypes.length == 1) {
					String methodName = method.getName();
					boolean isBeanStyle = methodName.length() >= 4
							&& methodName.startsWith("set")
							&& Character.isUpperCase(methodName.charAt(3));
					if (isBeanStyle) {
						String attribute = Character.toLowerCase(methodName
								.charAt(3))
								+ methodName.substring(4);
						setterNames.add(attribute);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (setterNames.size() != 0) {
			for (Iterator it = setterNames.iterator(); it.hasNext();) {
				String propertyName = (String) it.next();
				for (Iterator alls = allProp.iterator(); alls.hasNext();) {
					Map property = (Map) alls.next();
					if (((String) property.get("name")).toLowerCase().equals(
							propertyName)) {
						allProp.remove(property);
					}
				}
			}
		}
	}

	private String readInRemain() {
		StringBuffer remains = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String s;
			while ((s = in.readLine()) != null) {
				if (s.indexOf("package") < 0 && s.indexOf("import") < 0
						&& s.indexOf("public class") < 0) {
					remains.append(s);
					remains.append("\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String ret = remains.toString().substring(0,
				remains.toString().lastIndexOf("}"));
		return ret;
	}

}
