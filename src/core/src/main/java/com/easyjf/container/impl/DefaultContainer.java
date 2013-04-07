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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.beans.BeanWrapper;
import com.easyjf.beans.MutablePropertyValues;
import com.easyjf.beans.PropertyValue;
import com.easyjf.beans.exception.BeansException;
import com.easyjf.container.AutoInject;
import com.easyjf.container.BeanDefinition;
import com.easyjf.container.Container;
import com.easyjf.container.InnerContainer;
import com.easyjf.container.Scope;
import com.easyjf.util.I18n;
import com.easyjf.web.Order;

/**
 * EasyJWeb中的默认IOC容器实现
 * 
 * @author 大峡,tony,stef_wu
 * 
 */
public class DefaultContainer extends SingletonBeanContainer implements
		Container {

	private static final Logger logger = Logger
			.getLogger(DefaultContainer.class);

	private final Map beanDefinitions = new HashMap();

	private final Map guiceDefinitions = new HashMap();

	private final Map scopes = new HashMap();

	private final Map<String, InnerContainer> containers = new HashMap<String, InnerContainer>();

	public DefaultContainer() {

	}

	public boolean containsBean(String name) {

		boolean ret = beanDefinitions.containsKey(name);
		return ret;
	}

	public Object create(BeanDefinition beanDefinition) {

		Object bean = null;
		if ("singleton".equalsIgnoreCase(beanDefinition.getScope())) {
			bean = BeanCreatorUtil.initBean(beanDefinition, this);
			if (bean != null)
				super.register(beanDefinition.getBeanName(), bean);
		} else if ("prototype".equalsIgnoreCase(beanDefinition.getScope()))
			bean = BeanCreatorUtil.initBean(beanDefinition, this);
		else {
			Scope scope = (Scope) scopes.get(beanDefinition.getScope());

			if (scope != null)
				bean = scope.getBean(beanDefinition.getBeanName(),
						beanDefinition);
		}
		// 设值方法注入
		if (bean != null) {
			BeanWrapper wrapper = new BeanWrapper(bean);
			MutablePropertyValues pvs = new MutablePropertyValues();
			// 根据bean配置信息进行设值方法注入
			for (int i = 0; i < beanDefinition.getPropertyValues()
					.getPropertyValues().length; i++) {
				PropertyValue pv = beanDefinition.getPropertyValues()
						.getPropertyValues()[i];
				if (pv.getValue() instanceof BeanDefinition) {
					String beanName = ((BeanDefinition) pv.getValue())
							.getBeanName();
					Object refBean = getBean(beanName);
					if (refBean == null)
						throw new com.easyjf.beans.exception.BeansException(
								I18n
										.getLocaleMessage("core.container.cant.load.bean.property.name.specified")
										+ beanName);
					pvs.addPropertyValue(new PropertyValue(pv.getName(),
							refBean));
				} else if (pv.getValue() instanceof AutoInject)// 处理自动注入
				{
					AutoInject inject = (AutoInject) pv.getValue();
					if (inject.getName() != null)// 按名称自动注入
					{
						Object refBean = getBean(inject.getName());
						if (refBean == null)
							throw new BeansException(
									I18n
											.getLocaleMessage("core.container.cant.load.bean.property.name.specified")
											+ inject.getName());
						pvs.addPropertyValue(new PropertyValue(pv.getName(),
								refBean));
					} else // 按类别自动注入
					{
						Object refBean = getBean(inject.getType());
						if (refBean == null)
							throw new BeansException(
									I18n
											.getLocaleMessage("core.container.cant.load.bean.property.name.specified")
											+ inject.getType());
						pvs.addPropertyValue(new PropertyValue(pv.getName(),
								refBean));
					}
				} else
					pvs.addPropertyValue(pv);
			}

			// 自动按属名称注入
			// wrapper.getPropertyDescriptors()
			// 自动按属性类型注入

			wrapper.setPropertyValues(pvs);
		}

		// 在这里初始化容器信息
		if (bean != null && bean instanceof InnerContainer
				&& !containers.containsKey(beanDefinition.getBeanName())) {
			logger.info(I18n
					.getLocaleMessage("core.container.loaded.a.container")
					+ bean);
			InnerContainer innerContainer = (InnerContainer) bean;
			innerContainer.setParent(this);
			innerContainer.init();
			containers.put(beanDefinition.getBeanName(), innerContainer);
		}
		return bean;
	}

	protected void filterBeanDefinitions() {
		java.util.Iterator it = beanDefinitions.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			BeanDefinition definition = (BeanDefinition) beanDefinitions
					.get(key);
			if ("guice".equals(definition.getInjectType())) {
				guiceDefinitions.put(definition.getBeanName(), definition);
				it.remove();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> type) {
		T bean = super.getSingletionBean(type);
		if (bean == null) {
			BeanDefinition definition = getBeanDefinition(type);
			if (definition != null)
				bean = (T)create(definition);
			else
				for (Container innerContainer : containers.values()) {
					bean = innerContainer.getBean(type);
					if (bean != null)
						break;
				}
		}
		return bean;
	}

	public Object getBean(String name) {

		Object bean = super.getSingletionBean(name);
		if (bean == null) {
			BeanDefinition definition = (BeanDefinition) beanDefinitions
					.get(name);
			if (definition != null)
				bean = create(definition);
			else
				for (Container innerContainer : containers.values()) {
					bean = innerContainer.getBean(name);
					if (bean != null)
						break;
				}
		}
		return bean;
	}

	public BeanDefinition getBeanDefinition(Class type) {

		BeanDefinition ret = null;
		Iterator it = beanDefinitions.values().iterator();
		while (it.hasNext()) {
			BeanDefinition definition = (BeanDefinition) it.next();
			if (type.isAssignableFrom(definition.getBeanClass())) {
				ret = definition;
				break;
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getBeans(Class<T> type) {
		List<T> l = new ArrayList<T>();
		List beans = super.getBeansByType(type);
		if (beans != null)
			l.addAll(beans);
		for (Container innerContainer : containers.values()) {
			List<T> innerBeans = innerContainer.getBeans(type);
			if (innerBeans != null)
				l.addAll(innerBeans);
		}
		if (Order.class.isAssignableFrom(type))
			Collections.sort(l, new Comparator() {
				public int compare(Object o1, Object o2) {
					return ((Order) o1).getOrder().intValue()
							- ((Order) o2).getOrder().intValue();
				}
			});
		return l;
	}

	public Collection getBeansName() {

		return java.util.Collections.unmodifiableCollection(beanDefinitions
				.keySet());
	}

	public Map<String, InnerContainer> getContainers() {

		return containers;
	}

	protected void injectGuice() {
		// InnerContainer guiceContainer =
		// containers.get("GuiceContainer");
		// if(guiceContainer instanceof GuiceContainer){
		// ((GuiceContainer)guiceContainer).inject(guiceDefinitions);
		// }
	}

	// 初始化并装截容器
	public void refresh() {

		super.removeAll();

//		filterBeanDefinitions();

		refreshContainer();

//		injectGuice();

		// java.util.Iterator it =
		// beanDefinitions.entrySet().iterator();
		// while (it.hasNext()) {
		// Map.Entry en = (Map.Entry) it.next();
		// BeanDefinition definition = (BeanDefinition)
		// en.getValue();
		// if (BeanCreatorUtil.isCreateOnStart(definition))
		// create(definition);
		// }

		// 初始化Guice容器
		// java.util.Iterator iter =
		// containers.entrySet().iterator();
		// while (iter.hasNext()) {
		// Map.Entry en = (Map.Entry) iter.next();
		// InnerContainer container = (InnerContainer)
		// en.getValue();
		// if (container instanceof GuiceContainer) {
		// ((GuiceContainer)
		// container).inject(beanDefinitions);
		// }
		// }
	}

	protected void refreshContainer() {
		java.util.Iterator it = beanDefinitions.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry en = (Map.Entry) it.next();
			BeanDefinition definition = (BeanDefinition) en.getValue();
			if (BeanCreatorUtil.isCreateOnStart(definition))
				create(definition);
		}
	}

	public void registerBeanDefinition(String name, BeanDefinition definition) {

		beanDefinitions.put(name, definition);
	}

	public void registerBeanDefinitions(List definitions) {
		for (int i = 0; i < definitions.size(); i++) {
			BeanDefinition definition = (BeanDefinition) definitions.get(i);
			registerBeanDefinition(definition.getBeanName(), definition);
		}
	}

	public void registerScope(String name, Scope scope) {

		scopes.put(name, scope);
	}
	
}
