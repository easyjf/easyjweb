/*
 * EsayJF.com Inc.  
 *
 * Copyright (c) 2006-2008 All Rights Reserved.
 
package com.easyjf.container;

import com.easyjf.BaseTest;
import com.easyjf.container.impl.BeanDefinitionImpl;
import com.easyjf.container.impl.DefaultContainer;
import com.easyjf.web.IWebAction;
import com.easyjweb.action.GuiceTestAction;


*//**
 * 
 * @author ecsoftcn@hotmail.com
 *
 * @version $Id: GuiceContainerTest.java, 2007-4-23 上午01:54:32 Tony Exp $
 *//*
public class GuiceContainerTest extends BaseTest {

	 
	 * @see com.easyjf.BaseTest#registeCustomDefinitions(com.easyjf.container.impl.DefaultContainer)
	 
	@Override
	protected void registeCustomDefinitions(DefaultContainer container) {

		BeanDefinitionImpl definition = new BeanDefinitionImpl();
		definition.setBeanName("guice");
		definition.setBeanClass(GuiceTestAction.class);
		definition.setScope("prototype");
		
		container.registerBeanDefinition(definition.getBeanName(), definition);
	}
	
	public void testGuiceContainerInfo(){
		InnerContainer innerContainer = (InnerContainer)this.container.getBean("GuiceContainer");
		assertEquals(innerContainer.getContainerInfo(),"Guice");
	}
	
	public void testGuiceTestAction() throws Exception{
		InnerContainer innerContainer = (InnerContainer)this.container.getBean("GuiceContainer");
		IWebAction action = (IWebAction)innerContainer.getBean("guice");
		action.execute(null, null);
	}
	
	public void testGuiceTestActionFromContainer() throws Exception{
		IWebAction action = (IWebAction)container.getBean("guice");
		action.execute(null, null);
	}

}
*/