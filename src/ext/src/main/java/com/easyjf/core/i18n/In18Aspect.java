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
package com.easyjf.core.i18n;

import java.util.Locale;

import org.aspectj.lang.JoinPoint;

import com.easyjf.web.LocalManager;


public class In18Aspect {
	public void onSave(JoinPoint thisJoinPoint) {
		Object obj = thisJoinPoint.getArgs()[0];
		if (obj instanceof LocaleSupport) {
			Locale local = LocalManager.getCurrentLocal();
			String localName = local.toString().toUpperCase();
			try {
				String realClassName = obj.getClass().getCanonicalName()
						+ localName;
				if (obj.getClass().getCanonicalName().indexOf(localName) < 0) {
					realClassName = realClassName + localName;
				}				
				Object newObj = com.easyjf.beans.BeanUtils
						.instantiateClass(Class.forName(realClassName));
				com.easyjf.beans.BeanUtils.copyProperties(obj, newObj);
				
				thisJoinPoint.getArgs()[0] = newObj;			
			} catch (Exception e) {				
				e.printStackTrace();
			}

		}
		System.out.println(obj);
	}

}
