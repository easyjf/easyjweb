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

import java.lang.reflect.Method;
import java.util.Locale;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;

import com.easyjf.core.dao.GenericDAO;
import com.easyjf.core.dao.impl.GenericDAOImpl;
import com.easyjf.web.LocalManager;

public class I18nInteceptor implements IntroductionInterceptor {

	// private static final Logger logger =
	// Logger.getLogger(I18nInteceptor.class);

	// private static final Method
	// saveMethod=GenericDAO.class.getDeclaredMethod("save",new
	// Class[]{Object.class});
	// static {
	// try {
	// Method saveMethod =
	// GenericDAO.class.getDeclaredMethod("save",
	// new Class[] { Object.class });
	// } catch (Exception e) {
	//
	// }
	// }

	public boolean isSaveMethod(Method method) {
		boolean ret = false;
		String methodName = method.getName();
		if ("save".equals(methodName) || "update".equals(methodName))
			ret = true;
		return ret;
	}

	public boolean isFindMethod(Method method) {
		boolean ret = false;
		String methodName = method.getName();
		if ("find".equals(methodName) || "get".equals(methodName)
				|| "getBy".equals(methodName))
			ret = true;
		return ret;
	}

	public Object invoke(MethodInvocation mv) throws Throwable {
		Object returnObject = null;
		if (isSaveMethod(mv.getMethod())) {
			if (mv.getArguments()[0] instanceof LocaleSupport) {
				Object oldObject = mv.getArguments()[0];
				String oldClazzName = oldObject.getClass().getCanonicalName();
				Object newObj = createI18nClass(oldClazzName);
				com.easyjf.beans.BeanUtils.copyProperties(mv.getArguments()[0],
						newObj);
				mv.getArguments()[0] = newObj;
				returnObject = mv.proceed();
				if ("save".equals(mv.getMethod().getName()))
					com.easyjf.beans.BeanUtils
							.copyProperties(newObj, oldObject);
			} else {
				returnObject = mv.proceed();
			}

		} else if (isFindMethod(mv.getMethod())) {
			Class oldClazz = ((GenericDAOImpl) mv.getThis()).getClassType();
			if (LocaleSupport.class.isAssignableFrom(oldClazz)) {
				String oldClazzName = oldClazz.getCanonicalName();
				Class newObj = createI18nClass(oldClazzName).getClass();
				((GenericDAOImpl) mv.getThis()).setClazzType(newObj);
			}
			returnObject = mv.proceed();
			((GenericDAOImpl) mv.getThis()).setClazzType(oldClazz);
		} else
			returnObject = mv.proceed();

		return returnObject;
	}

	private Object createI18nClass(String oldClazzName)
			throws ClassNotFoundException {
		Locale local = LocalManager.getCurrentLocal();
		String localName = local.getLanguage().toUpperCase();

		String realClassName = oldClazzName + localName;
		Object newObj = com.easyjf.beans.BeanUtils.instantiateClass(Class
				.forName(realClassName));
		return newObj;
	}

	public boolean implementsInterface(Class intf) {
		// TODO Auto-generated method stub
		boolean returnboolean = GenericDAO.class.isAssignableFrom(intf);
		return returnboolean;
	}

}
