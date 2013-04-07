package com.easyjf.util;

import junit.framework.TestCase;

import com.easyjf.beans.BeanWrapper;
import com.easyjf.container.annonation.Field;
import com.easyjf.container.annonation.FormPO;
import com.easyjf.web.core.FormHandler;

public class AnnonationTest extends TestCase {
public void testPropertyAnnonation()
{
Child c=new Child();
BeanWrapper wrapper = new BeanWrapper(c);
java.beans.PropertyDescriptor property=wrapper.getPropertyDescriptor("name");
Class cls=property.getWriteMethod().getDeclaringClass();
FormPO formPo=(FormPO)cls.getAnnotation(FormPO.class);
System.out.println(formPo);
Parent parent=new Parent();
System.out.println(cls.isInstance(parent));
try{
java.lang.reflect.Field field=cls.getDeclaredField("name");
System.out.println(field.getAnnotation(Field.class));
}
catch(Exception e)
{
	e.printStackTrace();
}
}
public void testForPO()
{
	FormHandler hander=new FormHandler(null,null);
	Child obj=new Child();
	BeanWrapper wrapper = new BeanWrapper(obj);
	java.beans.PropertyDescriptor property=wrapper.getPropertyDescriptor("name");
	assertTrue(hander.checkPoWriteEnabled(obj, property));
}
public void testReadPo()
{
	FormHandler hander=new FormHandler(null,null);
	Child obj=new Child();
	BeanWrapper wrapper = new BeanWrapper(obj);
	java.beans.PropertyDescriptor property=wrapper.getPropertyDescriptor("name");
	assertTrue(hander.checkPoReadEnabled(obj, property));
}
public void testValidator()
{
	
}
@FormPO(inject="id,name")
public class Parent {
	private Long id;
	@Field(name="姓名")
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
@FormPO(inject="content")
public class Child extends Parent
{
	@Field(name="内容")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
}
