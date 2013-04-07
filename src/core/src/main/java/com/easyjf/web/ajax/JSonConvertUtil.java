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
package com.easyjf.web.ajax;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.StringTokenizer;

import com.easyjf.util.ClassUtils;
import com.easyjf.web.exception.FrameworkException;
/**
 * JSon与PO对象互相转换实用工具
 * @author 大峡
 *
 */
public class JSonConvertUtil {
	public final static String NULL_STRING = "null";

	public static boolean isSimpleType(Class cls) {
		return ClassUtils.isPrimitiveOrWrapper(cls) ||  String.class.isAssignableFrom(cls)
				|| Number.class.isAssignableFrom(cls)
				|| Date.class.isAssignableFrom(cls)||cls.isEnum();
	}
	
	public static Object simpleConvert(String value, Class paramType) {
		if (paramType == String.class) {
			return value;
		}

		if (paramType == Character.class || paramType == Character.TYPE) {
			if (value.length() == 1) {
				return new Character(value.charAt(0));
			} else {
				throw new IllegalArgumentException(
						"Can't more than one character in string - can't convert to char: '" + value + "'"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}

		String trimValue = value.trim();

		if (paramType == Boolean.class || paramType == Boolean.TYPE) {
			return Boolean.valueOf(trimValue);
		}

		if (paramType == Integer.class || paramType == Integer.TYPE) {
			if (trimValue.length() == 0) {
				return new Integer(0);
			}
			return Integer.valueOf(trimValue);
		}

		if (paramType == Short.class || paramType == Short.TYPE) {
			if (trimValue.length() == 0) {
				return new Short((short) 0);
			}
			return Short.valueOf(trimValue);
		}

		if (paramType == Byte.class || paramType == Byte.TYPE) {
			if (trimValue.length() == 0) {
				return new Byte((byte) 0);
			}
			return Byte.valueOf(trimValue);
		}

		if (paramType == Long.class || paramType == Long.TYPE) {
			if (trimValue.length() == 0) {
				return new Long(0);
			}
			return Long.valueOf(trimValue);
		}

		if (paramType == Float.class || paramType == Float.TYPE) {
			if (trimValue.length() == 0) {
				return new Float(0);
			}
			return Float.valueOf(trimValue);
		}

		if (paramType == Double.class || paramType == Double.TYPE) {
			if (trimValue.length() == 0) {
				return new Double(0);
			}
			return Double.valueOf(trimValue);
		}
		throw new IllegalArgumentException(
				"Unsupported conversion type: " + paramType.getName()); //$NON-NLS-1$
	}

	public static Object collectionConvert(String value, Class paramType) {
		if (NULL_STRING.equals(value))return null;		
		Object ret=null;
		if(java.util.Set.class.isAssignableFrom(paramType))
		{
			ret=new HashSet();
		}
		else//默认情况下使用ArrayList进行
		{		
			ret=Arrays.asList((Object[])arrayConvert(value,String[].class));
		}
		return ret;
	}

	public static Object arrayConvert(String value, Class paramType) {		
		if (NULL_STRING.equals(value))
			return null;
		if (!paramType.isArray()) {
			throw new FrameworkException("ArrayConverter.ClassIsNotAnArray:"+paramType.getName()); //$NON-NLS-1$
		}
		if (value.startsWith("[")) {
			value = value.substring(1);
		}
		if (value.endsWith("]")) {
			value = value.substring(0, value.length() - 1);
		}
		try {
			StringTokenizer st = new StringTokenizer(value,",");
			int size = st.countTokens();
			Class componentType = paramType.getComponentType();
			Object array = Array.newInstance(componentType, size);
			for (int i = 0; i < size; i++) {
				String token = st.nextToken();
				Object output = simpleConvert(token, componentType);			
				Array.set(array, i, output);
			}
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
