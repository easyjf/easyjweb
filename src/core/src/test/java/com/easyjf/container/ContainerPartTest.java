/*package com.easyjf.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import com.easyjf.beans.BeanWrapper;
import com.easyjf.beans.MutablePropertyValues;
import com.easyjf.beans.PropertyValue;
import com.easyjf.container.impl.BeanDefinitionImpl;
import com.easyjf.container.impl.BeanCreatorUtil;
import com.easyjf.container.impl.DefaultContainer;
import com.easyjf.web.config.DefaultWebConfig;
import com.easyjf.web.core.RequestScope;
import com.easyjf.web.core.SessionScope;
import com.easyjweb.business.Person;

import junit.framework.TestCase;

public class ContainerPartTest extends TestCase {
	
public void testConstructor()
{
	Class type=Person.class;
	ConstructorArguments args=new ConstructorArguments();
	args.addArgument(new Integer(1),new Integer(1));
	args.addArgument(new Integer(0),"小王");
	Constructor c=BeanCreatorUtil.resolverConstructor(Person.class, args);
	System.out.println(c);
	
	System.out.println(int.class.isAssignableFrom(Integer.class));
	Person p=new Person();
	BeanWrapper wrapper=new BeanWrapper(p);
	wrapper.setPropertyValue("age","1");
	System.out.println(p);
}
public void testPropertyInject()
{
	MutablePropertyValues m=new MutablePropertyValues();
	PropertyValue pv1=new PropertyValue("name","小王");
	PropertyValue pv2=new PropertyValue("age","1");
	m.addPropertyValue(pv1);
	m.addPropertyValue(pv2);
	Class type=Person.class;	
	BeanDefinitionImpl definition=new BeanDefinitionImpl();
	definition.setPropertyValues(m);
	definition.setBeanName("person");
	definition.setScope("prototype");
	definition.setBeanClass(Person.class);
	DefaultContainer con=new DefaultContainer();
	con.registerBeanDefinition("person", definition);
	System.out.println(con.getBean("person"));
	System.out.println(con.getBean("person"));
}


public void testConstructorInject(){
	Class type=Person.class;
	ConstructorArguments args=new ConstructorArguments();
	args.addArgument(new Integer(0),"小王");
	//args.addArgument(new Integer(1),"man");
	args.addArgument(new Integer(2),1);
	BeanDefinitionImpl definition=new BeanDefinitionImpl();
	definition.setConstructorArguments(args);
	definition.setBeanClass(Person.class);
	definition.setBeanName("person");
	definition.setScope("prototype");
	DefaultContainer con=new DefaultContainer();
	con.registerBeanDefinition("person", definition);
	System.out.println(con.getBean("person"));
	System.out.println(con.getBean("person"));
	
}
public void testContainerLoad()
{
	InputStream[] configures=new InputStream[2];
	try{
	//System.out.println(new File(".").getAbsolutePath());	
	configures[0]=new FileInputStream(new File("webapps/WEB-INF/easyjf-web1.xml"));
	configures[1]=new FileInputStream(new File("webapps/WEB-INF/easyjf-web2.xml"));
	DefaultWebConfig config=new DefaultWebConfig();
	config.setConfigures(configures);
	config.init();
	DefaultContainer c=new DefaultContainer();
	c.registerBeanDefinitions(config.getBeanDefinitions());
	c.registerScope("request",new RequestScope(c));
	c.registerScope("session",new SessionScope(c));
	c.refresh();
	//System.out.println(c.getBean("springComtainer"));
	System.out.println(c.getBean("stef"));
	System.out.println(c.getBean("person"));
	}
	catch(Exception e)
	{
		e.printStackTrace();
		fail("测试出现了错误！");
	}
}

}
*/