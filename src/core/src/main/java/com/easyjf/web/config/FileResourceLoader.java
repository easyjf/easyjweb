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
package com.easyjf.web.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.easyjf.util.I18n;
import com.easyjf.web.exception.FrameworkException;

public class FileResourceLoader extends AbstractResourceLoader {

	public InputStream load(String name) {
		File file = new File(name);
		try {
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				return in;
			}
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException(I18n.getLocaleMessage("core.web.ould.not.load.resource.documents") + name, e);
		}

		return null;
	}
}
