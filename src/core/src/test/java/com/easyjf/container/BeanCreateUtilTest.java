package com.easyjf.container;

import junit.framework.TestCase;

public class BeanCreateUtilTest extends TestCase {
public void testFactory()
{
	try{
	java.lang.reflect.Method m=MyService.class.getDeclaredMethod("getInstance", new Class[]{});
	Object bean=m.invoke(MyService.class,null);
	System.out.println(bean);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
private void print(ClassLoader loader)
{
	if(loader!=null)System.out.println(loader);
	if(loader.getParent()!=null)print(loader.getParent());
}
public void testClassLoader()
{
	print(getClass().getClassLoader());
	java.io.InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream("com/easyjf/web/ajax/engine.txt");
	System.out.println(in==null);
}
}
