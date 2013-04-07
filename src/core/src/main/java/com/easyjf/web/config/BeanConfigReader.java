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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.easyjf.beans.BeanUtils;
import com.easyjf.beans.MutablePropertyValues;
import com.easyjf.container.AutoInject;
import com.easyjf.container.BeanDefinition;
import com.easyjf.container.ConstructorArguments;
import com.easyjf.container.annonation.Inject;
import com.easyjf.container.annonation.InjectDisable;
import com.easyjf.container.impl.BeanDefinitionImpl;
import com.easyjf.util.ClassUtils;
import com.easyjf.util.I18n;
import com.easyjf.util.XmlElementUtil;
import com.easyjf.web.Module;

/**
 * 读取配置文件中的Bean信息
 * 
 * @author 大峡
 * 
 */
public class BeanConfigReader {
	private static final Logger logger = Logger
			.getLogger(BeanConfigReader.class);

	public static List parseBeansFromDocument(Document doc) {
		List ret = new ArrayList();
		Element beans = XmlElementUtil.findElement("beans", doc
				.getRootElement());
		if (beans == null)
			return ret;
		List list = beans.elements();
		try {
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Element e = (Element) list.get(i);
					BeanDefinitionImpl definition = new BeanDefinitionImpl();
					// 处理基本属性
					definition.setBeanName(e.attributeValue("name"));
					Class clz = ClassUtils.forName(e.attributeValue("class"));
					definition.setBeanClass(clz);
					if (e.attributeValue("scope") != null)
						definition.setScope(e.attributeValue("scope"));
					if (e.attributeValue("factory-method") != null)
						definition.setFactoryMethod(e
								.attributeValue("factory-method"));
					// 解析处理设值方法配置

					List ps = XmlElementUtil.findElements("property", e);
					MutablePropertyValues mpv = parsePropertyValues(ps);
					definition.setPropertyValues(mpv);
					// 解析处理构造子方法配置
					String autoInject = e.attributeValue("auto");// 通过auto属性来设置自动注入的类型
					if (autoInject == null)
						autoInject = e.attributeValue("inject");
					handleAutoInject(definition, autoInject);// 处理自动注入
					ps = XmlElementUtil.findElements("constructor-arg", e);
					ps = e.selectNodes("constructor-arg");
					definition
							.setConstructorArguments(parseConstructorArguments(ps));
					ret.add(definition);
				}
			}
		} catch (Exception e) {
			logger.error(I18n.getLocaleMessage("core.web.loading.Bean.configuration.information.error") + e);
			throw new com.easyjf.web.exception.FrameworkException(
					I18n.getLocaleMessage("core.web.loading.Bean.configuration.information.error"), e);
		}
		return ret;
	}

	// 根据配置信息处理自动注入
	public static void handleAutoInject(BeanDefinition definition,
			String autoInject) {
		// 根据Inject标签处理自动属性注入
		// 自动注入@Inject标识的字段
		// 此处是根据字段名称及其上的标签进行判断，将会存在一定的不一致，要求每一个属性都要有一个setter方法．
		List<java.lang.reflect.Field> list = new ArrayList<java.lang.reflect.Field>();
		Class clz = definition.getBeanClass();
		while (!clz.equals(Object.class)) {
			java.lang.reflect.Field[] fds = clz.getDeclaredFields();
			list.addAll(java.util.Arrays.asList(fds));
			clz = clz.getSuperclass();
		}
		if (definition.getBeanClass().getSuperclass().equals(Object.class)) {
			java.lang.reflect.Field[] fds1 = definition.getBeanClass()
					.getSuperclass().getDeclaredFields();
		}
		for (int t = 0; t < list.size(); t++) {
			java.lang.reflect.Field field = list.get(t);
			if (definition.getPropertyValues()
					.getPropertyValue(field.getName()) == null) {
				boolean isAutoInject = false;
				boolean autoInjectByType = true;// 默认情况下，全部按照类刑进行自动注入
				String autoBeanName = field.getName();
				if (field.isAnnotationPresent(Inject.class)) {// 首先检测是否通过使用注解方式注入
					isAutoInject = true;
					Inject inject = field.getAnnotation(Inject.class);
					if (!inject.name().equals(Inject.autoInjectByType))// 按类别自动注入
					{
						autoInjectByType = false;
						autoBeanName = inject.name();
					}
				} else if (autoInject != null
						&& !"none".equals(autoInject.toLowerCase())
						&& (field.getAnnotation(InjectDisable.class) == null))// 然后进一步检测是否使用自动注入
				{
					isAutoInject = true;
					if (BeanDefinition.Inject_By_Name.equals(autoInject
							.toLowerCase()))
						autoInjectByType = false;
				}
				if (isAutoInject)// 需要进行自动注入
				{
					definition.getPropertyValues().addPropertyValue(
							field.getName(),
							autoInjectByType ? new AutoInject(field.getType())
									: new AutoInject(autoBeanName));
				}
			}
		}
	}

	public static MutablePropertyValues parsePropertyValues(List ps) {
		MutablePropertyValues mpv = new MutablePropertyValues();
		for (int j = 0; j < ps.size(); j++) {
			Element pe = (Element) ps.get(j);
			mpv.addPropertyValue(pe.attributeValue("name"),
					parsePropertyValue(pe));
		}
		return mpv;
	}

	public static ConstructorArguments parseConstructorArguments(
			List<Element> ps) {
		ConstructorArguments crgs = new ConstructorArguments();
		int i = 0;
		for (Iterator<Element> it = ps.iterator(); it.hasNext(); i++) {
			try {
				Element arg = it.next();
				Class type = ClassUtils.forName(arg.attributeValue("type"));
				Integer index = arg.attributeValue("index") == null ? i
						: new Integer(arg.attributeValue("index"));
				Object value = parsePropertyValue(arg);
				if (!(value instanceof com.easyjf.container.BeanDefinition))
					value = BeanUtils.convertType(value, type);
				crgs.addArgument(index, type, value);

			} catch (ClassNotFoundException e) {
				logger.error(I18n.getLocaleMessage("core.web.construction.of.loading.into.the.wrong.type") + e);
			}

		}
		return crgs;
	}

	public static List parseBeansFromModules(Map modules) {
		List list = new ArrayList();
		if (modules.values() != null && modules.values().size() > 0) {
			Iterator it = modules.values().iterator();
			while (it.hasNext()) {
				try {
					Module m = (Module) it.next();
					BeanDefinitionImpl definition = new BeanDefinitionImpl(m
							.getPath(), ClassUtils.forName(m.getAction()), m
							.getScope());
					definition.setPropertyValues(m.getPropertyValues());
					definition.setInjectType(m.getInject());
					if ("guice".equalsIgnoreCase(m.getInject())) {
						definition.setInjectType(m.getInject());// 由Guice进行依赖注入
					} else {
						handleAutoInject(definition, m.getInject());// 处理自动注入
					}
					list.add(definition);
				} catch (Exception e) {
					logger.error(I18n.getLocaleMessage("core.web.module.information.into.beanc.ause.the.errors") + e);
				}
			}
		}
		return list;
	}

	public static Object parsePropertyValue(Element e) {
		// 首先判断value属性
		Object value = e.attributeValue("value");
		if (value == null && XmlElementUtil.findElement("value", e) != null)
			value = XmlElementUtil.findElement("value", e).getText();

		// 使用ref属性
		if (value == null) {
			String ref = e.attributeValue("ref");
			if (ref == null && XmlElementUtil.findElement("ref", e) != null) {
				ref = XmlElementUtil.findElement("ref", e).attributeValue(
						"value") != null ? XmlElementUtil.findElement("ref", e)
						.attributeValue("value") : XmlElementUtil.findElement(
						"ref", e).getText();
			}
			if (ref != null) {
				BeanDefinitionImpl innerBean = new BeanDefinitionImpl();
				innerBean.setBeanName(ref);
				value = innerBean;
			}
		}
		// 判断集合
		if (value == null) {
			value = parseCollectionValue(e);
		}
		// System.out.println(e.asXML());
		// 解析内部Bean，此处代码还未完成
		if(value == null){
			value = parseInnerBean(e);
		}
		return value;
	}
	
	public static Object parseInnerBean(Element e){
		BeanDefinitionImpl innerBean = new BeanDefinitionImpl();
		innerBean.setBeanName(e.attributeValue("name"));
		try {
			innerBean.setBeanClass(Class.forName(e.attributeValue("class")));
		} catch (ClassNotFoundException e1) {
			logger.error(I18n.getLocaleMessage("core.web.module.information.into.beanc.ause.the.errors") + e);
		}
		return innerBean;
	}

	public static Collection parseCollectionValue(Element e) {
		java.util.Collection values = null;
		Element node = XmlElementUtil.findElement("list", e);
		if (node != null) {
			List ps = XmlElementUtil.findElements("value", node);
			if (ps != null && ps.size() > 0) {
				values = new ArrayList();
				for (int i = 0; i < ps.size(); i++) {
					Element parent = DocumentHelper.createElement("property");
					parent.add((Element) ((org.dom4j.Node) ps.get(i)).clone());
					values.add(parsePropertyValue(parent));
				}
			} else {

			}
		}
		return values;
	}
}
