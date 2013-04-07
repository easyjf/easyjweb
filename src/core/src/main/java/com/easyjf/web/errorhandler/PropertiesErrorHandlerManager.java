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
package com.easyjf.web.errorhandler;
 
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.easyjf.web.Globals;

public class PropertiesErrorHandlerManager extends BaseErrorHandlerManager{

	private static final String DEFAULT_ERRORHANDLER_PROPERTIES_FILE_NAME = "errorHandlerMap"
		+ ".properties";

	private static final String DEFAULT_PROPERTIES_PATH = Globals.APP_BASE_DIR
		+ Globals.DEFAULT_TEMPLATE_PATH.substring(1);
	
	private String propertiesPath;

	private String errorHandlerFileName;

	private Properties errorHanlderProp;
	
	
	public Properties getErrorHanlderProp() {
		return errorHanlderProp;
	}


	public void setErrorHanlderProp(Properties errorHanlderProp) {
		this.errorHanlderProp = errorHanlderProp;
	}


	public String getPropertiesPath() {
		return propertiesPath;
	}


	public void setPropertiesPath(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}	


	protected void init() {
		// TODO Auto-generated method stub
		initFile();
		loadProperties();
		registeMaps();
	}
	
	private void initFile() {
		if (this.errorHanlderProp == null) {
			this.errorHanlderProp = new Properties();
		}
		if (this.getPropertiesPath() == null) {
			this.setPropertiesPath(DEFAULT_PROPERTIES_PATH);
		}
		if (this.getErrorHandlerFileName() == null) {
			this.setErrorHandlerFileName(DEFAULT_ERRORHANDLER_PROPERTIES_FILE_NAME);
		}

	}

	private void loadProperties() {
		File temp = new File(this.getPropertiesPath() + File.separator
				+ this.getErrorHandlerFileName());
		if (temp.exists()) {
			try {
				this.errorHanlderProp.load(new FileInputStream(temp));
			} catch (Exception e) {
				
			}
		}
	}

	private void registeMaps() {
		Map temp = new HashMap();
		temp.putAll((Hashtable) this.getErrorHanlderProp());
		for (Iterator it = temp.keySet().iterator(); it.hasNext();) {
			String find = (String) it.next();
			String clazz = (String) temp.get(find);
			try {
				IErrorHandler e = (IErrorHandler) Class.forName(clazz).newInstance();
				this.registerHandler(find, e);
			} catch (Exception ex) {
	
			}
		}
	}


	public String getErrorHandlerFileName() {
		return errorHandlerFileName;
	}


	public void setErrorHandlerFileName(String errorHandlerFileName) {
		this.errorHandlerFileName = errorHandlerFileName;
	}
	
}
