/*
 * EsayJF.com Inc.  
 * 
 * Copyright (c) 2006-2008 All Rights Reserved.
 */
package com.easyjf;

import static java.lang.System.out;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.easyjf.container.InnerContainer;
import com.easyjf.container.impl.DefaultContainer;
import com.easyjf.web.config.BeanConfigReader;

/**
 * Base TestCase for Containers
 * 
 * @author ecsoftcn@hotmail.com
 *
 * @version $Id: BaseTest.java, 2007-4-12 下午11:26:23 Tony Exp $
 */
public class BaseTest extends TestCase {

	protected DefaultContainer	container;

	private static final String	EASYJF_WEB_FILE	= "/conf/easyjf-web.xml";

	private static final String	BLANK_SPAR		= "    ";

	/*
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {

		/*super.setUp();

		Document doc = new SAXReader().read(this.getClass().getResourceAsStream(EASYJF_WEB_FILE));

		container = new DefaultContainer();
		container.registerBeanDefinitions(BeanConfigReader.parseBeansFromDocument(doc));
		registeCustomDefinitions(container);
		container.refresh();

		showAllBeans();*/

	}
	
	protected void registeCustomDefinitions(DefaultContainer container){
		
	}

	/*
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {

		/*super.tearDown();
		container = null;*/
	}

	/**
	 * List all beans in Container , include beans in InnerContainer .
	 * 
	 */
	protected void showAllBeans() {

	/*	out.println("-------- All beans in Container ----------");

		int index = 0;
		out.println("Beans in EasyJWeb Container");
		Collection beanNames = container.getBeansName();
		for (Iterator iter = beanNames.iterator(); iter.hasNext();) {
			index++;
			String name = (String) iter.next();
			out.println(index + " : " + name);
			Object bean = container.getBean(name);
			if (bean instanceof InnerContainer) {
				InnerContainer innerContainer = (InnerContainer) bean;
				Collection innerBeanNames = innerContainer.getBeansName();
				int innerIndex = 0;				
				for (Iterator innerIter = innerBeanNames.iterator(); innerIter.hasNext();) {
					innerIndex++;
					out.println(BLANK_SPAR + innerIndex + " : " + (String) innerIter.next());
				}
			}
		}

		out.println("------------------------------------------");*/
	}
	public void testNothing()
	{
		
	}
}
