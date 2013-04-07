package com.easyjf.web.config;

import junit.framework.TestCase;

import com.easyjf.util.ResolverUtil;
import com.easyjf.web.IWebAction;

public class DefaultConfigTest extends TestCase {
public void testLoadConfig()
{	
	//System.out.println(new File(".").getAbsolutePath());
	String[] confs=new String[]{"classpath:/com/easyjf/web/config/mvc-app.xml"};
	//configures[0]=new FileInputStream(new File("webapps/WEB-INF/easyjf-web1.xml"));
	//configures[1]=new FileInputStream(new File("webapps/WEB-INF/easyjf-web2.xml"));
	DefaultWebConfig config=new DefaultWebConfig();
	config.setConfigures(confs);
	config.setResourceLoader(new FileResourceLoader());
	config.init();	
	//assertTrue(config.getModules().size()==2);
}
public void testScanPackage()
{
	ResolverUtil<IWebAction> r=new ResolverUtil();
	r.findImplementations(IWebAction.class, "com");
	java.util.Iterator it=r.getClasses().iterator();
	while(it.hasNext())
	{
		System.out.println(it.next());
	}
	//Thread.currentThread().getContextClassLoader().loadClass("");
}
}
