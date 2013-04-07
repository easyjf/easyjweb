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
package com.easyjf.web.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import com.easyjf.util.I18n;
import com.easyjf.web.Globals;

/**
 * 管理国际化资源缓存
 * 
 * @author stefanie wu
 * 
 */
public class I18NResourceCache {
	/**
	 * 缓存，保存形式为Map<资源模块名,Map<语言,属性对象>>
	 */
	private Map<String, Map<Locale, ResourceBundle>> cache;

	/**
	 * 线程级别的资源模块名
	 */
	private ThreadLocal<String> resourceName;

	/**
	 * 属性文件保存的路径，相对于根路径，默认路径为/WEB-INF/applicationResource/。
	 */
	private String basePath;

	/**
	 * 线程级别的用户本地化信息对象
	 */
	private ThreadLocal<Locale> priLocale;

	public void setLocale(Locale locale) {
		this.priLocale = new ThreadLocal<Locale>();
		priLocale.set(locale);
	}

	public I18NResourceCache(String basePath) {
		this.cache = new HashMap<String, Map<Locale, ResourceBundle>>();
		this.basePath = basePath;
	}

	/**
	 * 根据一个国际化模块的名字和一个语言名字来返回一个确定的属性集对象。
	 * 如果国际化模块的名字为空，则使用保存在当前线程中的默认国际化模块的名字，如果模块的名字不为空，则设置当前线程默认的模块名字为该值。
	 * 如果语言名字为空，则使用默认的语言集（从Action中赋值），如果不为空，则根据该语言返回属性集对象，不改变默认语言集。
	 * 
	 * @param pageName
	 *            国际化模块的名字，如cms模块的所有页面可以使用一个属性文件来保存需要国际化字段的值，而blog模块可以使用一个单独的属性文件来保存需要国际化字段的值。
	 * 
	 * @param localLanguage
	 *            语言名字，该值为Locale.getLanguage()方法返回的值。
	 * @return 返回一个属性集对象。
	 */
	public ResourceBundle getPropertyValue(String name, String localLanguage) {
		Locale locale = this.priLocale.get();
		if (localLanguage != null) {
			locale = new Locale(localLanguage);
		}
		String pageName = name;
		if (pageName == null) {
			pageName = Globals.APPLICATION_PROPERTIES_PREFIX;
			// return getApplicationResourceBundle(locale);
		}
		if (pageName != null) {
			this.resourceName = new ThreadLocal<String>();
			resourceName.set(pageName);
		}
		Map<Locale, ResourceBundle> resource = null;
		if (resourceName != null) {
			resource = cache.get(resourceName.get());
		}
		if (resource != null) {
			ResourceBundle bundle = resource.get(locale);
			if (bundle != null) {
				return bundle;
			} else {
				ResourceBundle newBundle = getResourceBundle(locale);
				resource.put(locale, newBundle);
				return newBundle;
			}
		} else {
			ResourceBundle newBundle = getResourceBundle(locale);
			Map<Locale, ResourceBundle> subMap = new HashMap<Locale, ResourceBundle>();
			subMap.put(locale, newBundle);
			cache.put(resourceName.get(), subMap);
			return newBundle;
		}
	}

	/**
	 * 用于直接从全局路径中加载消息文件，暂时不用该方法
	 * 
	 * @param locale
	 * @return
	 */
	public ResourceBundle getApplicationResourceBundle(Locale locale) {
		return java.util.ResourceBundle.getBundle(Globals.RESOURCE_FILE_PATH
				+ Globals.APPLICATION_PROPERTIES_PREFIX, locale);
	}

	private ResourceBundle getResourceBundleFromClassPath(Locale locale) {
		String propertys = resourceName.get();
		java.util.ResourceBundle bundle = null;
		bundle = java.util.ResourceBundle.getBundle(basePath + propertys,
				locale);
		return bundle;
	}

	private ResourceBundle getResourceBundleFromFile(Locale locale) {
		String propertys = resourceName.get();
		java.util.ResourceBundle bundle = null;
		String ext = "xml".equalsIgnoreCase(Globals.PROPERTIES_TYPE) ? ".xml"
				: ".properties";
		String localePrefix = I18n.getLocalePrefix(locale);
		String fileName = "WEB-INF/" + basePath + propertys + "_"
				+ localePrefix + ext;
		String originProperties = "WEB-INF/" + basePath + propertys + ext;
		java.io.File dir = new java.io.File(Globals.APP_BASE_DIR);
		try {
			File file = new File(dir, fileName);
			bundle = ".xml".equals(ext)?new XMLPropertyResourceBundle(new FileInputStream(file)):new PropertyResourceBundle(new FileInputStream(file));
		} catch (java.io.IOException e) {
			try {
				File file1 = new File(dir, originProperties);
				bundle =".xml".equals(ext)?new XMLPropertyResourceBundle(new FileInputStream(file1)):new PropertyResourceBundle(new FileInputStream(file1)); 
			} catch (java.io.IOException e1) {
				throw new MissingResourceException("Missing resource "
						+ originProperties, this.getClass().getName(),
						"getPropertiesObj");
			}
		}
		return bundle;
	}

	private ResourceBundle getResourceBundle(Locale locale) {
		return Globals.LOAD_MESSAGE_RESOURCE_FROM_PATH ? getResourceBundleFromClassPath(locale)
				: getResourceBundleFromFile(locale);
	}

	/**
	 * 用来处理xml格式的资源获取类	
	 *
	 */
	private class XMLPropertyResourceBundle extends ResourceBundle {
		public XMLPropertyResourceBundle(InputStream inputstream)
				throws IOException {
			Properties properties = new Properties();
			properties.loadFromXML(inputstream);
			lookup = new HashMap(properties);
		}

		public Object handleGetObject(String s) {
			if (s == null)
				throw new NullPointerException();
			else
				return lookup.get(s);
		}

		public Enumeration getKeys() {
			ResourceBundle resourcebundle = parent;
			return new ResourceBundleEnumeration(lookup.keySet(),
					resourcebundle == null ? null : resourcebundle.getKeys());
		}

		private Map lookup;
	}

	class ResourceBundleEnumeration implements Enumeration {

		ResourceBundleEnumeration(Set set1, Enumeration enumeration1) {
			next = null;
			set = set1;
			iterator = set1.iterator();
			enumeration = enumeration1;
		}

		public boolean hasMoreElements() {
			if (next == null)
				if (iterator.hasNext())
					next = (String) iterator.next();
				else if (enumeration != null)
					do {
						if (next != null || !enumeration.hasMoreElements())
							break;
						next = (String) enumeration.nextElement();
						if (set.contains(next))
							next = null;
					} while (true);
			return next != null;
		}

		public Object nextElement() {
			if (hasMoreElements()) {
				String s = next;
				next = null;
				return s;
			} else {
				throw new NoSuchElementException();
			}
		}
		Set set;
		Iterator iterator;
		Enumeration enumeration;
		String next;
	}
}
