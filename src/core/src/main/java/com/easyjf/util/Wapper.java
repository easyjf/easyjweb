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
package com.easyjf.util;

public class Wapper {
	public static Class toWapperClass(Class clazz) {
		if (!clazz.isPrimitive())
			return clazz;
		else {
			if (int.class.equals(clazz))
				return Integer.class;
			if (char.class.equals(clazz))
				return Character.class;
			if (boolean.class.equals(clazz))
				return Boolean.class;
			if (long.class.equals(clazz))
				return Long.class;
			if (float.class.equals(clazz))
				return Float.class;
			if (byte.class.equals(clazz))
				return Byte.class;
			if (short.class.equals(clazz))
				return Short.class;
			if (double.class.equals(clazz))
				return Double.class;
		}
		return Object.class;
	}
}
