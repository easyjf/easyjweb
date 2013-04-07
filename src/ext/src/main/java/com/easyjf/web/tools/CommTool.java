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
package com.easyjf.web.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import com.easyjf.beans.BeanWrapper;
import com.easyjf.util.CommUtil;
import com.easyjf.util.HtmlUtil;
import com.easyjf.util.NumberUtils;
import com.easyjf.web.WebForm;

import java.beans.PropertyDescriptor;
import java.io.Serializable;

/**
 * 
 * <p>
 * Title: EasyJWeb内钳通用工具集
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: EasyJF开源团队
 * </p>
 * 
 * @author 大峡
 * @version 1.0
 */
public class CommTool {

	private static final Logger logger = Logger.getLogger(CommTool.class);

	/**
	 * 复制一个iterator，可以在不同的classType之间复制
	 * 
	 * @param classType
	 *            iteratro中的类
	 * @param src
	 *            复制源
	 * @return 复制后的迭代集合
	 */
	public static Iterator copyIterator(Class classType, Iterator src) {
		return copyList(classType, src).iterator();
	}

	/**
	 * 采用copy属性的方式复制一个list，可以在不同的class之间复制
	 * 
	 * @param classType
	 *            list中的类
	 * @param src
	 *            复制源
	 * @return 复制后的列表集合
	 */
	public static List copyList(Class classType, Iterator src) {
		List tag = new ArrayList();
		while (src.hasNext()) {
			Object obj = null, ormObj = src.next();
			try {
				obj = classType.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			BeanWrapper wrapper = new BeanWrapper(obj);
			BeanWrapper wrapper1 = new BeanWrapper(ormObj);
			PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				wrapper.setPropertyValue(name, wrapper1.getPropertyValue(name));
			}
			if (obj != null)
				tag.add(obj);
		}
		return tag;
	}

	/**
	 * 把map对象转换成obj，即把map中的key当成obj的属性，只转换两者完全相同的属性
	 * 
	 * @param map
	 * @param obj
	 */
	public static void map2Obj(Map map, Object obj) {
		BeanWrapper wrapper = new BeanWrapper(obj);
		wrapper.setPropertyValues(map);
	}

	/**
	 * 把obj的属性转换成map,执行该操作将把obj.getXX转换成map.get("XX")
	 * 
	 * @param obj
	 * @param map
	 */
	public static void obj2Map(Object obj, Map map) {
		if (map == null)
			map = new java.util.HashMap();
		BeanWrapper wrapper = new BeanWrapper(obj);
		PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();

		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			try {
				if (descriptors[i].getReadMethod() != null) {
					map.put(name, wrapper.getPropertyValue(name));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * EasyJWeb调用
	 * 
	 * @param form
	 * @return 返回他那的Context上下文
	 */
	public static Context createContext(WebForm form) {
		Map result = form.getEasyJWebResult();
		Context context = new VelocityContext();
		Iterator it = result.keySet().iterator();
		while (it.hasNext()) {
			String name = (String) it.next();
			context.put(name, result.get(name));
			// logger.debug("Context:"+name+"="+result.get(name));
			createUtilContext(context);
		}
		return context;
	}

	public static void createUtilContext(Context context) {
		context.put("HtmlUtil", HtmlUtil.getInstance());
		context.put("CommUtil", CommUtil.getInstance());
	}

	/**
	 * 这里假设域对象的主键属性名称统一为id
	 * 
	 * @param value
	 * @param clz
	 * @return 正确类型的id值
	 */
	public static Serializable convertIdValue(Serializable value, Class clz) {
		Serializable ret = value;
		try {
			java.lang.reflect.Field f = clz.getDeclaredField("id");
			if (f.getType().isAssignableFrom(ret.getClass()))
				return ret;
			if (Number.class.isAssignableFrom(f.getType()))
				ret = NumberUtils.parseNumber(value.toString(), f.getType());
		} catch (Exception e) {
			logger.error("ID值转换错误：" + e);
		}
		return ret;
	}
}