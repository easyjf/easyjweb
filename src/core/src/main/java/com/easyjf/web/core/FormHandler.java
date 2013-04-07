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
package com.easyjf.web.core;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.BeanWrapper;
import com.easyjf.container.Container;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.container.annonation.InnerProperty;
import com.easyjf.container.annonation.MultiPOLoad;
import com.easyjf.container.annonation.OverrideProperty;
import com.easyjf.container.annonation.Overrides;
import com.easyjf.container.annonation.POLoad;
import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.web.POLoadDao;
import com.easyjf.web.validate.TargetObject;
import com.easyjf.web.validate.ValidateType;
import com.easyjf.web.validate.ValidatorManager;

/**
 * 该类从FrameworkEngine中抽出，主要负责处理WebForm中的数据到Program Object对象中数据的转换 包括验证
 * 
 * @author 大峡
 * 
 */
public class FormHandler {

	private Container container;

	private ValidatorManager validateManager;

	private static final Logger logger = Logger.getLogger(FormHandler.class);

	public FormHandler(Container container, ValidatorManager validateManager) {
		this.container = container;
		this.validateManager = validateManager;
	}

	public boolean checkPoWriteEnabled(Object obj,
			java.beans.PropertyDescriptor property) {
		boolean ret = true;
		/*
		 * FormPO formPO = obj.getClass().getAnnotation(FormPO.class); if
		 * (formPO != null) { String injectEnabled = formPO.inject(); if
		 * (StringUtils.hasLength(injectEnabled)) { ret = ("," + injectEnabled +
		 * ",").indexOf("," + property.getName() + ",") >= 0;// 找不到，则不注入 } else {
		 * String disInject = formPO.disInject(); if
		 * (StringUtils.hasLength(disInject)) ret = ("," + disInject +
		 * ",").indexOf("," + property.getName() + ",") < 0;// 找到，则不注入 } }
		 */
		ret = checkFormPOWriteEnabled(obj.getClass(), property);
		// 如果允许注入，则进一步判断Field标签的editable属性值
		if (ret) {
			com.easyjf.container.annonation.Field f = findAnnotation(
					com.easyjf.container.annonation.Field.class, obj, property);
			if (f != null) {
				if (!f.writeable())
					ret = false;// 如果writeable设置成false，则表示不准注入
			}
		}
		return ret;
	}

	public boolean checkFormPOWriteEnabled(Class clz,
			java.beans.PropertyDescriptor property) {
		boolean ret = true;
		FormPO formPO = (FormPO) clz.getAnnotation(FormPO.class);
		boolean haveFound = false;
		if (formPO != null) {
			String injectEnabled = formPO.inject();

			if (StringUtils.hasLength(injectEnabled)) {
				ret = ("," + injectEnabled + ",").indexOf(","
						+ property.getName() + ",") >= 0;// 找不到，则不注入
				if (ret)
					haveFound = true;
			} else {
				String disInject = formPO.disInject();
				if (StringUtils.hasLength(disInject))
					ret = ("," + disInject + ",").indexOf(","
							+ property.getName() + ",") < 0;// 找到，则不注入
				if (!ret)
					haveFound = true;
			}
		}
		if (!haveFound && clz.getSuperclass() != Object.class)
			ret = checkFormPOWriteEnabled(clz.getSuperclass(), property);
		return ret;
	}

	public boolean checkPoReadEnabled(Object obj,
			java.beans.PropertyDescriptor property) {
		boolean ret = true;
		FormPO formPO = obj.getClass().getAnnotation(FormPO.class);
		if (formPO != null) {
			String disRead = formPO.disRead();
			if (StringUtils.hasLength(disRead)) {
				ret = ("," + disRead + ",").indexOf("," + property.getName()
						+ ",") < 0;// 找不到，则允许读
			}
		}
		// 如果允许注入，则进一步判断Field标签的editable属性值
		if (ret) {
			com.easyjf.container.annonation.Field f = findAnnotation(
					com.easyjf.container.annonation.Field.class, obj, property);
			if (f != null) {
				if (!f.readable())
					ret = false;// 如果writeable设置成false，则表示不准注入
			}
		}
		return ret;
	}

	/**
	 * 读取一个类中属性或方法的标签，该方法首先从属性访问方法中查找标签定义，若找不到，则进一步通过对象的Field属性查找标签定义
	 * 
	 * @param <A>
	 * @param annotationClass
	 *            指定的标签类型
	 * @param obj
	 *            目标对象
	 * @param property
	 *            具体的属性
	 * @return 如果找到指定的标签，则直接返回，若没找到，则返回null
	 */
	public static <A extends Annotation> A findAnnotation(
			Class<A> annotationClass, Object obj, PropertyDescriptor property) {
		A ret = null;
		if (property.getWriteMethod() != null)
			ret = property.getWriteMethod().getAnnotation(annotationClass);// 查找setter方法
		if (ret == null && property.getReadMethod() != null)
			ret = property.getReadMethod().getAnnotation(annotationClass);// 查找getter方法
		if (ret == null) {
			try {// 查找属性定义
				Class clz = null;
				if (property.getWriteMethod() != null)
					clz = property.getWriteMethod().getDeclaringClass();
				else if (property.getReadMethod() != null)
					clz = property.getReadMethod().getDeclaringClass();
				if (clz != null) {
					java.lang.reflect.Field field = clz
							.getDeclaredField(property.getName());
					ret = field.getAnnotation(annotationClass);
				}
			} catch (java.lang.NoSuchFieldException e) {

			}
		}
		return ret;
	}

	public void po2form(Object obj, Map map) {
		if (map == null)
			map = new java.util.HashMap();
		if (obj instanceof Map) {
			Map data = (Map) obj;
			java.util.Iterator it = ((Map) obj).entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry en = (Map.Entry) it.next();
				map.put(en.getKey(), en.getValue());
			}
		} else {
			BeanWrapper wrapper = new BeanWrapper(obj);
			PropertyDescriptor descriptors[] = wrapper.getPropertyDescriptors();
			for (int i = 0; i < descriptors.length; i++) {
				if (descriptors[i]!=null &&checkPoReadEnabled(obj, descriptors[i])) {
					String name = descriptors[i].getName();
					try {
						if (descriptors[i].getReadMethod() != null) {
							map.put(name, wrapper.getPropertyValue(name));
						}
					} catch (Exception e) {
						logger
								.error(I18n.get("core.web.po.object.to.the.conversion.of.error.in.the.form")
										+ e);
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Map parseForm2Obj(Map map, Object obj, boolean ignoreBlankString) {
		Map ret = new HashMap();
		if (validateManager == null)
			validateManager = FrameworkEngine.findValidatorManager();
		Map gOverrs = new java.util.HashMap();
		Overrides ovs = obj.getClass().getAnnotation(Overrides.class);
		if (ovs != null) {
			for (int j = 0; j < ovs.value().length; j++) {
				OverrideProperty op = ovs.value()[j];
				gOverrs.put(op.name(), op.newName());
			}
		}
		BeanWrapper wrapper = new BeanWrapper(obj);
		java.beans.PropertyDescriptor[] propertys = wrapper
				.getPropertyDescriptors();
		for (int i = 0; i < propertys.length; i++) {
			String name = propertys[i].getName();
			if (!wrapper.isWritableProperty(name) || propertys[i].getWriteMethod()==null)
				continue;
			Object propertyValue = null;
			/**
			 * 在属性值转换处理完成后，执行验证操作
			 */
			TargetObject validateObject = validateManager
					.findValidateObject(propertys[i]);
			// System.out.println(validateObject);
			/**
			 * 对标签进行处理，检测是否需要加载关联对象，以及是否有内嵌属性,根据字段进行处理。
			 */
			POLoad loader = null;
			MultiPOLoad mLoader = null;
			InnerProperty innerProperty = null;
			OverrideProperty ops = null;
			boolean enableInject = checkPoWriteEnabled(obj, propertys[i]);// 检测该属性是否允许自动注入
			if (!enableInject)
				continue;
			Map overrs = new java.util.HashMap(gOverrs);
			// 首先通过属性的写方法（设值方法)判断标签
			loader = propertys[i].getWriteMethod().getAnnotation(POLoad.class);
			mLoader = propertys[i].getWriteMethod().getAnnotation(
					MultiPOLoad.class);
			innerProperty = propertys[i].getWriteMethod().getAnnotation(
					InnerProperty.class);
			ops = propertys[i].getWriteMethod().getAnnotation(
					OverrideProperty.class);
			// 如果在设置方法上没有使用任何设置标签，则进一步通过属性定义名称上的标签来判断属性
			try {
				java.lang.reflect.Field f = propertys[i].getWriteMethod()
						.getDeclaringClass().getDeclaredField(name);
				if (loader == null)
					loader = f.getAnnotation(POLoad.class);
				if (mLoader == null) {
					mLoader = f.getAnnotation(MultiPOLoad.class);
				}
				if (innerProperty == null)
					innerProperty = f.getAnnotation(InnerProperty.class);
				if (ops == null)
					ops = f.getAnnotation(OverrideProperty.class);
			} catch (java.lang.NoSuchFieldException e) {

			}
			if (loader != null && !"".equals(loader.name())) {
				overrs.put(name, loader.name());
			}
			if (mLoader != null && !"".equals(mLoader.name())) {
				overrs.put(name, mLoader.name());
			}
			if (innerProperty != null) {
				OverrideProperty[] o = innerProperty.overrides();
				for (int j = 0; j < o.length; j++) {
					overrs.put(o[j].name(), o[j].newName());
				}
			}

			if (ops != null)overrs.put(ops.name(), ops.newName());
			// logger.debug(mLoader==null);
			// 尝试自动加载POLoad标注的对象
			if (loader != null && container != null)// DAO关联属性
			{
				POLoadDao loadDao = (POLoadDao) container.getBean(loader.loadDao());
				if (loadDao != null) {
					Object rawObj = map.get(overrs.containsKey(name) ? overrs
							.get(name) : name);
					if(rawObj == null)continue;
					logger.debug(I18n
									.get("core.web.loading.related.object.through.DAO")
									+ loadDao
									+ ",rawObj:"
									+ rawObj
									+ ",loader:" + loader.name() + "," + loader);
					if (!"".equals(rawObj)) {
						//System.out.println(propertys[i].getName());
						propertyValue = loadDao.get(propertys[i].getPropertyType(), (Serializable) BeanUtils.convertType(rawObj, loader.pkClz()));
						// wrapper.setPropertyValue(name,v);
					}
					else continue;//忽略该属性
				}
			} else if (innerProperty != null)// 内嵌属性加载,此处写死了，只能加载简单的属性值
			{
				Object innerObj = wrapper.getPropertyValue(propertys[i]
						.getName());
				if (innerObj == null)
					innerObj = com.easyjf.beans.BeanUtils
							.instantiateClass(propertys[i].getPropertyType());
				BeanWrapper innerWrapper = new BeanWrapper(innerObj);
				java.beans.PropertyDescriptor[] innerP = innerWrapper
						.getPropertyDescriptors();
				for (int j = 0; j < innerP.length; j++) {
					String pName = innerP[j].getName();
					if (innerWrapper.isWritableProperty(pName)) {// 对内嵌对象的具体属性的值，不进行验证，只能整体进行验证
						/**
						 * 首先取名为address.city这样的字段
						 */
						String fieldName = propertys[i].getName() + "." + pName;
						Object v = map
								.get(overrs.containsKey(fieldName) ? overrs
										.get(fieldName) : fieldName);
						/**
						 * 然后再取city这段
						 */
						if (v == null)
							v = map.get(overrs.containsKey(pName) ? overrs
									.get(pName) : pName);
						try {
							innerWrapper.setPropertyValue(pName, v);// 进行赋值
						} catch (Exception e) {
							// @see(http://jira.easyjf.com/browse/EASYJWEB-20)
							validateManager
									.addCustomError(
											fieldName,
											I18n.get("core.web.data type conversion error"),
											ValidateType.Field);
						}
					}
				}
				propertyValue = innerObj;
				// wrapper.setPropertyValue(name, innerObj);
			} else if (mLoader != null) {
				// 加载List类型对象
				logger.debug(I18n.get("core.web.multiPOLoad.began.loading"));
				// if(propertys[i] instanceof List){
				List loaderObj = new ArrayList();
				POLoadDao loadDao = (POLoadDao) container.getBean(mLoader
						.loadDao());
				if (loadDao != null) {
					Object rawObj = map.get(overrs.containsKey(name) ? overrs
							.get(name) : name);
					if(rawObj == null)continue;
					logger
							.debug(I18n
									.get("core.web.loading.through.DAO.objects.associated.List")
									+ loadDao
									+ ",rawObj:"
									+ rawObj
									+ ",loader:"
									+ mLoader.name()
									+ ","
									+ mLoader);
					if (!"".equals(rawObj)) {
						String[] tempArr = {};
						if (rawObj instanceof String[]) {
							tempArr = (String[]) rawObj;
						} else {
							String tempStr = (String) rawObj;
							tempArr = tempStr.split(",");
						}
						logger.debug("--------------------tempArr:"+ tempArr.length);
						for (String key : tempArr) {
							if (!"".equals(key)) {
								Object tempValue = loadDao.get(mLoader
										.targetClz(), (Serializable) BeanUtils
										.convertType(key, mLoader.pkClz()));
								if (tempValue != null) {
									loaderObj.add(tempValue);
								}
							}
						}
						propertyValue = loaderObj;
						// wrapper.setPropertyValue(name,
						// v);
					}
				}
				// }
			} else {
				// 普通属性的对象加载
				String fieldName = overrs.containsKey(name) ? (String) overrs
						.get(name) : name;
				Object value = map.get(fieldName);
				/**
				 * 对于Field属性，在这里进行验证
				 */
				if (validateObject != null
						&& validateObject.getType() == ValidateType.Field) {
					validateObject.doValidate(value);
					if (validateObject.getErrors().getErrorObject(fieldName) != null
							|| !map.containsKey(fieldName)) {
						// 验证出错，则不改变PO对象的值
						continue;
					}
				} else if (validateObject == null
						&& !map.containsKey(fieldName)) {
					continue;// 如果不需要验证，表单中又没有指定filedName的值，则直接跳转
				}
				if (map.containsKey(fieldName)) {
					/**
					 * ignoreBlankString是指允许把指定的属性指通过""设置成为null
					 */
					if (ignoreBlankString && "".equals(value)) {
						if (validateObject != null
								&& validateObject.getType() == ValidateType.Property) {
							validateObject.doValidate(propertyValue);
							if (validateObject.getErrors().getErrorObject(name) != null) {
								// 验证出错，则不改变PO对象的值
								continue;
							}
						}
						// continue;
					} else {
						try {
							/**
							 * 如果value为空字符串，则指定的对你将不进行转换
							 */
							if (value != null) {
								propertyValue = BeanUtils.convertType(value,
										propertys[i].getPropertyType());
							}

							// wrapper.setPropertyValue(name,
							// value);
						} catch (java.lang.Exception e) {
							validateManager.addCustomError(
											name,
											I18n.get("core.web.data type conversion error"),
											ValidateType.Field);
							StringBuffer buffer=new StringBuffer(name);
							buffer.append(" ").append(I18n.get("core.web.data type conversion error")).append(
									e.getMessage()).append(" type:").append(
									propertys[i].getPropertyType()).append(" source:").append(obj.getClass());
							logger.error(buffer.toString());
							continue;
						}
					}
				}
			}
			/**
			 * 如果是对属性进行验证，则在属性值处理完毕后再进行属性值验证操作
			 */
			if (validateObject != null
					&& validateObject.getType() == ValidateType.Property) {
				validateObject.doValidate(propertyValue);
				if (validateObject.getErrors().getErrorObject(name) != null) {
					// 验证出错，则不改变PO对象的值
					continue;
				}
			}
			// wrapper.setPropertyValue(name,
			// propertyValue);
			// System.out.println("正常的值:"+name+"="+propertyValue);
			ret.put(name, propertyValue);
		}
		return ret;
	}

	/**
	 * 把map中的数据转换到obj中，可以设定是否需要忽略空格
	 * 
	 * @param map
	 * @param obj
	 * @param ignoreBlankString
	 */
	public void form2Obj(Map map, Object obj, boolean ignoreBlankString) {
		form2Obj(map, obj, ignoreBlankString, false);// 默认情况下不进行数据回滚
	}

	/**
	 * 把map中的数据转换到obj中，可以设定是否需要忽略空格
	 * 
	 * @param map
	 * @param obj
	 * @param ignoreBlankString
	 */
	public void form2Obj(Map map, Object obj, boolean ignoreBlankString,
			boolean validateRollback) {
		if (obj instanceof Map) {
			java.util.Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry en = (Map.Entry) it.next();
				Object v = ((Map) obj).get(en.getKey());
				if (v != null)
					((Map) obj).put(en.getKey(), BeanUtils.convertType(en
							.getValue(), v.getClass()));
				// else ((Map)obj).put(en.getKey(),en.getValue());
			}
		} else {
			Map propertys = parseForm2Obj(map, obj, ignoreBlankString);
			if (validateRollback && validateManager.getErrors().hasError())
				return;
			java.util.Iterator it = propertys.entrySet().iterator();
			BeanWrapper wrapper = new BeanWrapper(obj);
			while (it.hasNext()) {
				java.util.Map.Entry en = (Map.Entry) it.next();
				wrapper.setPropertyValue((String) en.getKey(), en.getValue());
			}
		}
	}

}
