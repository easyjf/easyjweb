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
package com.easyjf.core.support;

import java.util.Collection;
import java.util.Locale;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.support.query.GenericPageList;
import com.easyjf.web.LocalManager;

public class GenericPageListForI18n extends GenericPageList {
	public GenericPageListForI18n(Class cls, String scope, Collection paras,
			GenericDAO dao) {
		super(cls, scope, paras, dao);
	}

	public void doList(int currentPage, int pageSize, boolean i18nEnable) {
		if (!i18nEnable) {
			super.doList(currentPage, pageSize);
		} else {
			Locale local = LocalManager.getCurrentLocal();
			String localName = local.getLanguage().toUpperCase();
			String realClassName = cls.getName() + localName;
			String totalSql = "select COUNT(o) from " + realClassName
					+ " o where " + scope;
			super.doList(pageSize, currentPage, totalSql, scope);
		}
	}

}
