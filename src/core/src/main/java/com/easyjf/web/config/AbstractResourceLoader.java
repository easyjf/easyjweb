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

import java.io.InputStream;

public abstract class AbstractResourceLoader implements ConfigureResourceLoader {

	public InputStream loadResource(String name) {
		if (name.indexOf("classpath:") == 0)
		{			
			return this.getClass().getResourceAsStream(
					name.substring("classpath:".length()));
		}
		else
			return load(name);
	}

	public abstract InputStream load(String name);
}
