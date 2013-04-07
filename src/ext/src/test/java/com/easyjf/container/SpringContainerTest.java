package com.easyjf.container;

import junit.framework.TestCase;


public class SpringContainerTest extends TestCase {
public void testLoad()
{
	String location="classpath:com/easyjf/web/spring.xml";
	String[] locations={"classpath:com/easyjf/web/spring.xml"};
//	ApplicationContext context=new ClassPathXmlApplicationContext(locations);
//	System.out.println(context.getBean("wxp"));
}
}
