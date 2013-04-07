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

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.easyjf.beans.BeanUtils;
import com.easyjf.container.BeanDefinition;
import com.easyjf.container.ConstructorArgumentValue;
import com.easyjf.container.ConstructorArguments;
import com.easyjf.container.Container;
import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.util.Wapper;

/**
 * 负责创建Bean的工具类，在创建的过程中解决构造子注入
 * 
 * @author 大峡
 * 
 */
public class BeanCreatorUtil {

	private static final Logger logger =org.apache.log4j.Logger.getLogger(BeanCreatorUtil.class
			.getName());

	public static boolean argumentsTypeDiffWith(Class[] args, Class[] vs) {
		boolean ret = true;
		for (int j = 0; j < args.length; j++)
			if (!args[j].isAssignableFrom(vs[j])) {
				ret = false;
				break;
			}
		return ret;
	}

	private static boolean compare(Class[] targets, Class[] source) {
		for (int i = 0; i < targets.length; i++)
			if (source[i].isAssignableFrom(targets[i])
					|| isSamePrimitive(source[i], targets[i]))
				continue;
			else
				return false;
		return true;
	}

	public static Class[] getArgumentsValueType(int max,
			ConstructorArgumentValue[] cvs) {
		Class[] c = new Class[max];
		for (int i = 0; i < c.length; i++) {
			Class vtype = null;
			for (ConstructorArgumentValue element : cvs)
				if (element.getIndex().intValue() == i)
					vtype = element.getType();
			c[i] = vtype;
		}
		return c;
	}

	private static Constructor[] getOfTheSameParamsNumbersConst(int expectNum,
			Constructor[] constrs) {
		List<Constructor> rights = new ArrayList<Constructor>();
		for (Constructor constr : constrs)
			if (constr.getParameterTypes().length == expectNum)
				rights.add(constr);
		Constructor[] cons = new Constructor[rights.size()];
		rights.toArray(cons);
		return cons;
	}

	private static Constructor getTheExactOne(ConstructorArguments needs,
			Constructor[] prep) {
		Collection all = needs.getArguments().values();
		Class[] clazz = new Class[all.size()];
		int i = 0;
		for (Iterator it = all.iterator(); it.hasNext(); i++) {
			ConstructorArgumentValue cav = (ConstructorArgumentValue) it.next();
			clazz[i] = cav.getType();
		}
		for (Constructor construct : prep) {
			Class[] prepTypes = construct.getParameterTypes();

			if (compare(clazz, prepTypes))
				return construct;
		}
		throw new IllegalArgumentException();
	}

	public static Object initBean(BeanDefinition beanDefinition,
			Container container) {
		Object bean = null;
//		logger.debug(I18n.getLocaleMessage("core.container.bean.initialize")
//				+ beanDefinition.getBeanName() + ","
//				+ beanDefinition.getBeanClass());
		// 简单的无参工厂调用
		if (StringUtils.hasText(beanDefinition.getFactoryMethod()))
			try {
				java.lang.reflect.Method m = beanDefinition.getBeanClass()
						.getDeclaredMethod(beanDefinition.getFactoryMethod(),
								new Class[] {});
				bean = m.invoke(beanDefinition.getBeanClass(), new Object[] {});
				return bean;
			} catch (java.lang.NoSuchMethodException e) {
				logger.error(I18n
						.getLocaleMessage("core.container.factory.not.exists")
						+ e);
			} catch (Exception e) {
				logger.error(I18n
						.getLocaleMessage("core.container.load.bean.error")
						+ e);
			}

		ConstructorArguments crgs = beanDefinition.getConstructorArguments();
		if (crgs.getArgCount() < 1)
			bean = BeanUtils.instantiateClass(beanDefinition.getBeanClass());
		else {
			// 使用构造子注入
			Constructor[] constructors = beanDefinition.getBeanClass()
					.getDeclaredConstructors();
			Constructor[] sameNum = getOfTheSameParamsNumbersConst(crgs
					.getArgCount(), constructors);
			if (sameNum.length == 0)
				return new java.lang.IllegalArgumentException();
			else {
				Constructor rightOne = getTheExactOne(crgs, sameNum);
				Object[] params = new Object[crgs.getArguments().values()
						.size()];
				Collection cavs = crgs.getArguments().values();
				int i = 0;
				for (Iterator it = cavs.iterator(); it.hasNext(); i++) {
					ConstructorArgumentValue cav = (ConstructorArgumentValue) it
							.next();
					Object v = cav.getValue();
					if (v instanceof com.easyjf.container.BeanDefinition)
						v = container.getBean(((BeanDefinition) v)
								.getBeanName());

					params[i] = v;
				}
				bean = BeanUtils.instantiateClass(rightOne, params);
			}
		}
		return bean;
	}

	/**
	 * 判断一个Bean是否需要在容器加载的时候加载,不以&开头，lazy为flase，scope为singleton或application的Bean将会在容器初始化的时候加载
	 * 
	 * @param definition
	 * @return 若该定义的bean需要在启动时创建，则返回true，否则返回false
	 */
	public static boolean isCreateOnStart(BeanDefinition definition) {
		return (definition.getBeanName().charAt(0) != '&'
				&& ("singleton".equalsIgnoreCase(definition.getScope()) || "application"
						.equalsIgnoreCase(definition.getScope())) && (!definition
				.isLazy()));
	}

	private static boolean isSamePrimitive(Class clz, Class clz2) {
		return Wapper.toWapperClass(clz).equals(Wapper.toWapperClass(clz2));
	}

	public static Constructor resolverConstructor(Class type,
			ConstructorArguments args) {
		Constructor[] all = type.getDeclaredConstructors();
		Constructor constructorToUser = null;
		ConstructorArgumentValue[] values = new ConstructorArgumentValue[args
				.getArgCount()];
		args.getArguments().values().toArray(values);
		sortConstructors(all);
		int argsLength = values.length;
		if (values[values.length - 1].getIndex() != null
				&& values[values.length - 1].getIndex().intValue() > argsLength)
			argsLength = values[values.length - 1].getIndex().intValue();
		for (Constructor c : all)
			if (c.getParameterTypes().length == argsLength
					&& argumentsTypeDiffWith(c.getParameterTypes(),
							getArgumentsValueType(argsLength, values))) {
				constructorToUser = c;
				break;
			}
		return constructorToUser;
	}

	public static void sortArgumentsValue(ConstructorArgumentValue[] args) {
		Arrays.sort(args, new Comparator() {

			public int compare(Object o1, Object o2) {
				ConstructorArgumentValue cv1 = (ConstructorArgumentValue) o1;
				ConstructorArgumentValue cv2 = (ConstructorArgumentValue) o2;
				if (cv1.getIndex() != null && cv2.getIndex() != null)
					return cv1.getIndex().intValue()
							- cv2.getIndex().intValue();
				if (cv1.getIndex() == null)
					return -1;
				else if (cv2.getIndex() == null)
					return 1;
				else
					return 0;
			}
		});
	}

	/**
	 * Sort the given constructors, preferring public constructors and "greedy"
	 * ones with a maximum of arguments. The result will contain public
	 * constructors first, with decreasing number of arguments, then non-public
	 * constructors, again with decreasing number of arguments.
	 * 
	 * @param constructors
	 *            the constructor array to sort
	 */
	public static void sortConstructors(Constructor[] constructors) {
		Arrays.sort(constructors, new Comparator() {

			public int compare(Object o1, Object o2) {
				Constructor c1 = (Constructor) o1;
				Constructor c2 = (Constructor) o2;
				boolean p1 = Modifier.isPublic(c1.getModifiers());
				boolean p2 = Modifier.isPublic(c2.getModifiers());
				if (p1 != p2)
					return (p1 ? -1 : 1);
				int c1pl = c1.getParameterTypes().length;
				int c2pl = c2.getParameterTypes().length;
				return (new Integer(c1pl)).compareTo(new Integer(c2pl)) * -1;
			}
		});
	}

}
