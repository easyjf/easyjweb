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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.easyjf.util.I18n;

public class GenerateFactory {
	public static void main(String[] args) throws Exception {
		generSimple();
	}

	@SuppressWarnings("unused")
	private static void generSimple() throws IOException, FileNotFoundException {
		String path = "package.properties";
		Properties properties = new Properties();
		properties.load(GenerateFactory.class.getResourceAsStream(path));
		System.out.println(properties);

		List<String[]> argsList = new ArrayList<String[]>();
		for (Object element : properties.keySet()) {
			String domainName = (String) element;
			String packageName = properties.getProperty(domainName);
			System.out.println(I18n.getLocaleMessage("generator.package.name") + packageName);
			System.out.println("Domain：" + domainName);
			String[] pair = new String[] { domainName, packageName };
			argsList.add(pair);
		}
		for (String[] arg : argsList) {
			// System.out.println(arg);
			// 参数１为ＤＯＭＡＩＮ名 ２为包名
			// AllGenerator gen = new AllGenerator(arg);
			// gen.gener();
		}
	}

}
