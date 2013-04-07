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

import java.util.ArrayList;
import java.util.List;

import com.easyjf.util.I18n;

abstract public class BaseCommand implements ICommand {

	protected List<String> errors = new ArrayList<String>();

	public void notNull(Object obj, String name) {
		if (obj == null) {
			errors.add(name + I18n.getLocaleMessage("ext.Not.empty"));
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if (str.trim().equals("")) {
				errors.add(name + I18n.getLocaleMessage("ext.Not.empty"));
			}
		}
	}

	public void isNumber(Object obj, String name) {
		this.notNull(obj, name);
		String str = obj == null ? "" : obj.toString();
		boolean ret = str.matches("\\d*");
		if (!ret) {
			errors.add(name + I18n.getLocaleMessage("ext.Must.figure"));
		}
	}

	public void isString(Object obj, String name) {
		this.notNull(obj, name);
		String str = (String) obj;
		boolean ret = str.matches("\\w*");
		if (!ret) {
			errors.add(name + I18n.getLocaleMessage("ext.Must.text"));
		}
	}

	abstract public List<String> vaild();
}
