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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import com.easyjf.container.Container;
import com.easyjf.container.InnerContainer;
import com.easyjf.util.I18n;
/**
 *  Spring and EasyJWeb Integeration Container .
 * <p>
 * <b>Some Questions :</b>
 * 
 * 1.Why there is a parent bean in {@link InnerContainer} ?
 * 
 * 2.Should this {@link InnerContainer} be used in representation layer ?
 * 
 * 3.Dose Method <code>getBean(Class type)</code> have any value ?
 * 
 * <p>
 * <b>Usage :</b>
 * 
 * <code>
 *      <bean name="SpringIntegerationContainer" class="com.easyjf.container.impl.SpringIntegerationContainer" scope="singleton">
 *          <property name="beanDescriptions">
 *              <list>
 *                  <value>classpath:conf/beans-service.xml</value>
 *                  <value>classpath:conf/beans-dao.xml</value>
 *              </list> 
 *          </property>
 *      </bean>
 * </code>
 * @author  ecsoftcn@hotmail.com,大峡
 *
 */
public class SpringContainer implements InnerContainer {

	private Container parent;

	private BeanFactory factory;

	private boolean haveStart;

	private String configLocation;
	private static final Logger logger = Logger
	.getLogger(SpringContainer.class);
	public void setConfigLocation(String configLocation) {
		this.configLocation = configLocation;
	}

	public Container getParent() {
		return parent;
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}

	public void setFactory(BeanFactory factory) {
		this.factory = factory;
	}

	public BeanFactory getFactory() {
		return factory;
	}

	public boolean containsBean(String name) {
		return factory.containsBean(name);
	}

	public Object getBean(String name) {
		Object bean = null;
		try {
			bean = factory.getBean(name);
		} catch (Exception e) {
		}
		return bean;
	}

	public Object getBean(Class type) {
		
		if (factory instanceof ListableBeanFactory) {
			ListableBeanFactory context = (ListableBeanFactory) factory;
			Map beans = context.getBeansOfType(type);
			if (beans != null && beans.size() > 0)
				return beans.values().iterator().next();
		}
		return null;
	}

	public Collection getBeansName() {
		if (factory instanceof ApplicationContext) {
			ApplicationContext context = (ApplicationContext) factory;
			return Arrays.asList(context.getBeanDefinitionNames());
		}
		return null;
	}

	public void init() {
		logger.info(I18n.getLocaleMessage("ext.began.pring.containers.initialization"));
		if (factory == null){
			if(this.configLocation!=null&&!"".equals(this.configLocation)){
				this.factory= new ClassPathXmlApplicationContext(this.configLocation.split(";"));
			}else{
				this.factory=new ClassPathXmlApplicationContext("classpath: applicationContext.xml");
			}
		}
		
		if (factory instanceof AbstractApplicationContext) {
			AbstractApplicationContext context = (AbstractApplicationContext) factory;
			if (factory instanceof ConfigurableWebApplicationContext) {
				// 这里的代码不直观
				ConfigurableWebApplicationContext webContext = (ConfigurableWebApplicationContext) factory;
				if (parent != null && parent instanceof WebContextContainer) {
					webContext.setServletContext(((WebContextContainer) parent)
							.getServletContext());
				}
				if (!haveStart) {					
					context.refresh();
					haveStart = true;					
				}			
			}
			if(parent!=null && parent instanceof WebContextContainer)
			{
				this.registerWebContext(((WebContextContainer) parent).getServletContext());
			}		
		}
		logger.info(I18n.getLocaleMessage("ext.Spring.container.initialized"));
	}

	public List getBeans(Class type) {
		List ret=null;
		if (factory instanceof ListableBeanFactory) {
			ListableBeanFactory context = (ListableBeanFactory) factory;
			Map beans = context.getBeansOfType(type);
			if (beans != null && beans.size() > 0)
				ret=new ArrayList(beans.values());
		}
		return ret;
	}
	public BeanFactory getBeanFactory()
	{
		return factory;
	}
	/**
	 * 在servlet上下文中注册Spring WebApplicationContext
	 * @param servletContext
	 */
	public void registerWebContext(ServletContext servletContext)
	{		
		servletContext.setAttribute("org.springframework.web.context.WebApplicationContext.ROOT",this.getFactory());
	}
	/*
	 * @see com.easyjf.container.InnerContainer#getContainerInfo()
	 */
	public String getContainerInfo() {
		return "Spring";
	}
	
}
