/*package com.easyjf.web.ajax;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.dom4j.io.SAXReader;

import com.easyjf.dbo.EasyJDB;
import com.easyjweb.business.Person;
import com.easyjweb.business.SystemBasicData;
import com.easyjweb.business.SystemBasicDataDetail;
import com.easyjweb.business.User;

import junit.framework.TestCase;

public class AjaxTest extends TestCase {
public void testGetSimpleJson()
{
	User u=new User();
	u.setUserName("test");
	u.setEmail("test@easyjf.com");
	System.out.println(AjaxUtil.getJSON(u));
	Person p=new Person("大峡","男",25);
	System.out.println(AjaxUtil.getJSON(p));
}
public void testSpecialJson()
{
	Person[] persons={new Person("大峡","男",25),new Person("abc","男",25)};
	System.out.println(AjaxUtil.getJSON(persons));
	try{
	java.lang.reflect.Method m=Test.class.getMethod("voidMethod",new Class[]{});
	System.out.println(AjaxUtil.getJSON(m.invoke(new Test(),null)));
	m=Test.class.getMethod("nativeMethod",new Class[]{});
	System.out.println(AjaxUtil.getJSON(m.invoke(new Test(),null)));
	m=Test.class.getMethod("booleanMethod",new Class[]{});
	System.out.println(AjaxUtil.getJSON(m.invoke(new Test(),null)));
	m=Test.class.getMethod("numberMethod",new Class[]{});
	System.out.println(AjaxUtil.getJSON(m.invoke(new Test(),null)));
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}	
}
public void testAjaxStub()
{
	Object bean=new com.easyjweb.business.impl.UserServiceImpl();
	List list=AjaxUtil.getAjaxStub(bean);
	for(int i=0;i<list.size();i++)
	{
		Map map=(Map)list.get(i);
		System.out.println(map);
	}
	
}
public void testComplexJson()
{
	Person p=new Person("大峡","男",25);		
	Person p2=new Person("abc","男",25);
	Person c=new Person("小峡","女",25);
	Person c2=new Person("小兵","女",25);
	java.util.List list=new java.util.ArrayList();
	list.add(c);	
	list.add(c2);
	c.setParent(p);
	p.setChildren(list);
	p2.setChildren(list);
	System.out.println(AjaxUtil.getJSON(c));
	System.out.println("---------");
	System.out.println(AjaxUtil.getJSON(p));
	System.out.println("---------");
	System.out.println(AjaxUtil.getJSON(p));	
	System.out.println(AjaxUtil.getJSON(list));
}
public void testLazyObject()
{
	EasyJDB db=EasyJDB.getInstance();
	SystemBasicData bd=new SystemBasicData();
	bd.setId(10);
	bd.setSn("nation");
	bd.setIntro("民族");
	bd.setTitle("民族");
	SystemBasicDataDetail detail=new SystemBasicDataDetail();
	detail.setSequence(new Integer(1));
	detail.setTitle("男");
	detail.setTvalue("man");
	detail.setParent(bd);
	SystemBasicDataDetail detail2=new SystemBasicDataDetail();
	detail2.setSequence(new Integer(2));
	detail2.setTitle("女");
	detail2.setTvalue("woman");
	detail2.setParent(bd);
	bd.getChildren().add(detail);
	bd.getChildren().add(detail2);
	db.add(bd);
	SystemBasicData obj=(SystemBasicData)db.get(SystemBasicData.class, 10);
	try{
	AjaxConfigManager config=new AjaxConfigManager();
	SAXReader reader = new SAXReader();
	config.parseConfig(reader.read(new java.io.FileInputStream(new File("webapps/WEB-INF/easyjf-web1.xml"))));		
	AjaxServiceContainer serviceContainer=new AjaxServiceContainer(config);
	AjaxUtil.setServiceContainer(serviceContainer);	
	System.out.println(AjaxUtil.getJSON(obj));
	}
	catch(Exception e)
	{
		e.printStackTrace();
		fail("出错了！"+e);
	}	
	db.del(obj);
}

public void testIntegraion()
{
	try{
	AjaxConfigManager config=new AjaxConfigManager();
	SAXReader reader = new SAXReader();
	config.parseConfig(reader.read(new java.io.FileInputStream(new File("webapps/WEB-INF/easyjf-web1.xml"))));	
	System.out.println(config.getAllowNames());
	System.out.println(config.getDenyNames());
	System.out.println(config.getServices());
	System.out.println(config.getConvertBeans());
	System.out.println(config.getSignatures());
	AjaxServiceContainer service=new AjaxServiceContainer(config);
	System.out.println(service.isAllow("UserService"));
	}
	catch(Exception e)
	{
		e.printStackTrace();
		fail("出现错误！");
	}
}

class Test
{
	public void voidMethod(){
		
	}
	public int nativeMethod()
	{
		return -3;
	}
	public boolean booleanMethod()
	{
		return true;
	}
	public Number numberMethod()
	{
		return new Long(100);
	}
	
}
}
*/