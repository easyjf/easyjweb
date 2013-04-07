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
package com.easyjf.util;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class XmlElementUtil {
	public static Element findElement(String name, Element el) {
		Element ret = null;
		if (el != null) {
			List e = el.elements(name);
			for (int i = 0; i < e.size(); i++) {
				Element n = (Element) e.get(i);
				if (n.getName().equals(name)) {
					ret = n;
					break;
				}
			}
		}
		return ret;
	}

	public static List findElements(String name, Element el) {
		List list = new ArrayList();
		if (el != null) {
			List e = el.elements(name);
			for (int i = 0; i < e.size(); i++) {
				Element n = (Element) e.get(i);
				if (n.getName().equals(name))
					list.add(n);
			}
		}
		return list;
	}
}
