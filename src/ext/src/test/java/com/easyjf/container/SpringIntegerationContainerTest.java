/*
 * EsayJF.com Inc.  
 * 
 * Copyright (c) 2006-2008 All Rights Reserved.
 
package com.easyjf.container;

import static java.lang.System.out;

import com.easyjf.BaseTest;
import com.easyjf.container.impl.SpringIntegerationContainer;
import com.easyjweb.business.Person;

*//**
 * SpringIntegerationContainer TestCase
 * 
 * @author ecsoftcn@hotmail.com
 *
 * @version $Id: SpringIntegerationContainerTest.java, 2007-4-12 下午11:30:15 Tony Exp $
 *//*
public class SpringIntegerationContainerTest extends BaseTest {

    public void testEasyJFContainer() {

        Person person = (Person) container.getBean("easyjf-person");

        out.println("From EasyJF Container : Person's name is : " + person.getName());
    }

    public void testSpringContainer() {

        Person person = (Person) container.getBean("spring-person");

        out.println("From Spring Container : Person's name is : " + person.getName());
    }

    public void testInnerContainer() {

        SpringIntegerationContainer innerContainer = (SpringIntegerationContainer) container.getBean("springIntegerationContainer");

        out.println("Container Information : " + innerContainer.getContainerInfo());

        assertEquals("Spring Framework", innerContainer.getContainerInfo());
    }

}
*/