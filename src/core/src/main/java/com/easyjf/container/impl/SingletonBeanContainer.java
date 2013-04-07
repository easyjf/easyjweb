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
package com.easyjf.container.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.easyjf.util.Assert;
import com.easyjf.util.I18n;
import com.easyjf.web.exception.FrameworkException;

public class SingletonBeanContainer {
	@SuppressWarnings("unchecked")
	private Map beans = new TreeMap();

	public boolean containsSingletonBean(String name) {
		return beans.containsKey(name);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getBeansByType(Class<T> type) {
		java.util.Iterator it = beans.values().iterator();
		List<T> ret = null;
		while (it.hasNext()) {
			Object obj = it.next();
			if (type.isAssignableFrom(obj.getClass())) {
				if (ret == null)
					ret = new ArrayList<T>();
				ret.add((T)obj);
			}
		}
		return ret;
	}

	public <T> T getSingletionBean(Class<T> type) {
		T ret = null;
		List<T> list = getBeansByType(type);
		if (list != null && list.size() > 0) {
			if (list.size() > 1)
				throw new FrameworkException(I18n
						.getLocaleMessage("core.container.has.many.bean.error"));
			ret =list.get(0);
		}
		return ret;
	}

	public Object getSingletionBean(String name) {
		return beans.get(name);
	}

	public void register(String name, Object bean) {
		Assert.hasText(name);
		Assert.notNull(bean);
		synchronized (beans) {
			if (!containsSingletonBean(name)) // throw new
				// com.easyjf.web.exception.FrameworkException("指定的Bean已经存在！"+name);
				beans.put(name, bean);
		}
	}

	public void removeAll() {
		beans.clear();
	}

	public <T> void removeBean(Class<T> type) {
		Object bean = getSingletionBean(type);
		beans.values().remove(bean);
	}

	public void removeBean(String name) {
		beans.remove(name);
	}
}
