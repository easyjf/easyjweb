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

import java.util.ArrayList;
import java.util.List;

public class GeneratorTool {

	private String templateDir = "/webapps/WEB-INF/easyjwebtools";

	private String tableName;

	private String type;

	private String customerGenerator;

	private String[] args;

	private final java.util.Map generators = new java.util.HashMap();

	public GeneratorTool() {
	}

	public GeneratorTool(String tableName) {
		this.tableName = tableName;
	}

	public void generator() throws Exception {
		GeneratorUtil.registSystemGenerator(this.generators);
		if (customerGenerator != null) {
			String[] cgs = customerGenerator.split(",");
			for (int i = 0; i < cgs.length; i++) {
				Generator gen = (Generator) Class.forName(cgs[i]).newInstance();
				doGenerator(gen);
			}
		} else {
			Object obj = this.generators.get(type);
			List gens = new ArrayList();
			parseRegisterGenerator(gens, obj);
			for (int i = 0; i < gens.size(); i++) {
				Class clz = (Class) gens.get(i);
				Generator gen = (Generator) clz.newInstance();
				doGenerator(gen);
			}
		}
	}

	private void doGenerator(Generator gen) {
		gen.setTableName(this.tableName);
		gen.setTemplateDir(this.templateDir);
		gen.setArgs(args);
		gen.generator(false);
	}

	private void parseRegisterGenerator(List list, Object obj) {
		if (obj != null) {
			if (obj instanceof Class) {
				list.add(obj);
			} else if (obj instanceof List) {
				java.util.Iterator it = ((List) obj).iterator();
				while (it.hasNext()) {
					Object subObj = it.next();
					// System.out.println(subObj);
					parseRegisterGenerator(list, subObj);
				}
			}
		}
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

	public String getCustomerGenerator() {
		return customerGenerator;
	}

	public void setCustomerGenerator(String customerGenerator) {
		this.customerGenerator = customerGenerator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setArgs(String[] args) {
		this.args = args.clone();
	}

	/**
	 * 通过命令行执行生成命令,使用方法GeneratorTool [-G=XX]|[-T=XX]
	 * <tableName> 比如：
	 * -G=com.easyjf.web.tools.generator.GeneratorWebEditPage表示使用GeneratorWebEditPage来执行生成
	 * -T=action
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String tableName = null;
		String type = "crud";
		String customerGenerator = null;
		List list = new java.util.ArrayList();
		for (int i = 0; i < args.length; i++) {
			if ("-".equals(args[i].substring(0, 1))) {
				int endPrefix = args[i].indexOf('=');
				if (endPrefix > 0) {
					String prefix = args[i].substring(1, args[i].indexOf('='));
					if ("g".equalsIgnoreCase(prefix))
						customerGenerator = args[i].substring(endPrefix + 1);
					else if ("t".equalsIgnoreCase(prefix)) {
						type = args[i].substring(endPrefix + 1);
					} else
						list.add(args[i]);
				} else
					list.add(args[i]);
			} else
				tableName = args[i];
		}
		if (tableName != null) {
			GeneratorTool tools = new GeneratorTool(tableName);
			tools.setType(type);
			tools.setCustomerGenerator(customerGenerator);
			if (list.size() > 0) {
				String[] as = new String[list.size()];
				tools.setArgs((String[]) list.toArray(as));
			}
			try {
				tools.generator();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
