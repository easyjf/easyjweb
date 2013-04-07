/*package com.easyjf.web.ajax;

import com.easyjweb.business.Person;

import junit.framework.TestCase;

public class AjaxServiceContainerTest extends TestCase {
public void testCheckAllow()
{
	AjaxServiceContainer container=new AjaxServiceContainer(new AjaxConfigManager());
	container.addAllowName(".*dd");
	//container.addAllowName(".*");
	assertTrue(container.checkAllow("ddd"));
	assertTrue(container.checkAllow("3843248dd"));
	assertTrue(container.checkAllow("dd"));
	assertFalse(container.checkAllow("775d"));
	assertFalse(container.checkAllow("dddl"));
	assertFalse(container.checkAllow("ddou"));
}
public void testCheckDeny()
{
	AjaxServiceContainer container=new AjaxServiceContainer(new AjaxConfigManager());
	container.addDenyName("*dd");
	//container.addAllowName(".*");
	assertTrue(container.checkDeny("ddd"));
	assertTrue(container.checkDeny("3843248dd"));
	assertTrue(container.checkDeny("dd"));
	assertFalse(container.checkDeny("775d"));
	assertFalse(container.checkDeny("dddl"));
	assertFalse(container.checkDeny("ddou"));
}
public void testIsAllow()
{
	AjaxServiceContainer container=new AjaxServiceContainer(new AjaxConfigManager());
	//allow Nothing
	assertFalse(container.isAllow("xxxxx"));
	container.addAllowName("*dd");
	container.addDenyName("3.*");
	assertTrue(container.isAllow("dkdkdd"));
	assertTrue(container.isAllow("43xxxdd"));
	assertFalse(container.isAllow("3dddd"));
	assertFalse(container.isAllow("4kdlfk"));
	assertFalse(container.isAllow("3dddd3dd"));
}
public void testIsAllowMethod()
{
	AjaxServiceContainer container=new AjaxServiceContainer(new AjaxConfigManager());
	//没有配置前，所有方法均开通
	assertTrue(container.isAllowMethod("UserService","getUser"));
	
	RemoteService remote=new RemoteService();
	container.addService("UserService", remote);
	remote.setName("UserService");
	remote.addAllowName("getUser");
	//只开通allowMethod中的方法
	assertTrue(container.isAllowMethod("UserService","getUser"));
	assertFalse(container.isAllowMethod("UserService","queryUser"));
	//排除denyMethod中的方法
	remote.addDenyName("get.*Deny");
	assertTrue(container.isAllowMethod("UserService","getUser"));
	assertFalse(container.isAllowMethod("UserService","getUserDeny"));
}
public void testIsAllowProperty()
{
	AjaxServiceContainer container=new AjaxServiceContainer(new AjaxConfigManager());
	//没有配置前，所有方法均开通
	assertTrue(container.isAllowProperty(Person.class,"name"));
	
	RemoteService remote=new RemoteService();
	container.addConvertBean(Person.class.getName(), remote);
	remote.setName(Person.class.getName());
	remote.addAllowName("n.*me");
	//只开通allowMethod中的方法
	assertTrue(container.isAllowProperty(Person.class,"name"));
	assertFalse(container.isAllowProperty(Person.class,"age"));
	//排除denyMethod中的方法
	remote.addDenyName("n1*me");
	assertTrue(container.isAllowProperty(Person.class,"ndddme"));
	assertFalse(container.isAllowProperty(Person.class,"n1111me"));	
}
}
*/