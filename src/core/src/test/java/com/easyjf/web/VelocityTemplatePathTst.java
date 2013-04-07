package com.easyjf.web;

import org.apache.velocity.app.Velocity;

import junit.framework.TestCase;

public class VelocityTemplatePathTst extends TestCase {
public void testFilePath()
{
	Velocity.setProperty("resource.loader","file,class,jar");
	Velocity.setProperty("file.resource.loader.path","D:/easyjf/easyjweb/webapps/WEB-INF/easyjweb");
	Velocity.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	try{
	org.apache.velocity.Template t=Velocity.getTemplate("hello.html");
	org.apache.velocity.Template t2=Velocity.getTemplate("classpath:com/easyjf/web/util.js");
	System.out.println(t.toString());
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public void testClassLoader()
{
	java.io.InputStream is=this.getClass().getResourceAsStream("/com/easyjf/web/easyjf-web.xml");
	try{
	System.out.println(is.available());
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
}
