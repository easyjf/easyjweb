/*
 * Copyright 2006-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyjf.web.ajax;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;

import com.easyjf.container.Container;
/**
 * Ajax容器服务类，主要用于存放AjaxService配置信息，并通过该类获取远程调用相关的Ajax远程服务Bean
 * @author 大峡
 *
 */
public class AjaxServiceContainer{
	private Container container;
	private AjaxConfigManager configManager;
    public AjaxServiceContainer()
    {
    	this(AjaxConfigManager.getInstance(),null);
    }
    public AjaxServiceContainer(Container container)
    {
    	this(AjaxConfigManager.getInstance(),container);
    }
    public AjaxServiceContainer(AjaxConfigManager configManager)
    {
    	this(configManager,null);    	
    }
    public AjaxServiceContainer(AjaxConfigManager configManager,Container container)
    {
    	this.configManager=configManager;
    	this.container=container;    	
    }
	/**
	 * 检测指定的远程调用服务是否可用
	 * @param name
	 * @return 若指定名称的远程服务可用则返回true，否则返回false
	 */
	public boolean isAllow(String name) {
		return checkAllow(name)&&(!checkDeny(name));
	}
	/**
	 * 检测指定服务的指定方法是否可用
	 * @param serviceName
	 * @param methodName
	 * @return 指定方法可用，则返回true，否则返回false
	 */
	public boolean isAllowMethod(String serviceName,String methodName)
	{
		RemoteService service=(RemoteService)configManager.getServices().get(serviceName);
		if(service==null)return true;	
		boolean defineAllow=service.getAllowNames().size()<1 ||matcher(service.getAllowNames(),methodName);
		return   defineAllow&& ((! matcher(service.getDenyNames(),methodName)));
	}
	/**
	 * 检测指定ConvertBean的指定属性是否需要转换
	 * @param clz
	 * @param property
	 * @return 指定的属性可对远程可见则返回true，否则返回false
	 */
	public boolean isAllowProperty(Class clz,String property)
	{
		RemoteService service=(RemoteService)configManager.getConvertBeans().get(clz.getName());	
		if(service==null)return true;	
		boolean defineAllow=service.getAllowNames().size()<1 ||matcher(service.getAllowNames(),property);
		return   defineAllow&& (! matcher(service.getDenyNames(),property));
	}
	public Class[] getSignatures(String serviceName,String methodName)
	{
		String key=serviceName+"."+methodName;
		return (Class[])configManager.getSignatures().get(key);
	}
	/**
	 * 从容器中调用指定服务的
	 * @param name
	 * @return 返回指定的业务对象
	 */
	public Object getService(String name) {
		if(isAllow(name))			
		return container.getBean(name);		
		else throw new ServiceNotAvailableException(name);
	}
	public boolean checkAllow(String name)
	{
		//if(allowNames.size()<1)return true;
		return matcher(configManager.getAllowNames(),name);
	}
	public boolean checkDeny(String name)
	{	
		return matcher(configManager.getDenyNames(),name);
	}
	private boolean matcher(Set patterns,String name)
	{	if(name==null)return false;
		java.util.Iterator it=patterns.iterator();
		while(it.hasNext())
		{
			String t=(String)it.next();
			if(Pattern.matches(t,name))
			{			
				return true;
			}
		}		
		return false;
	}
	
	public static void main(String[] args){
		Set patterns = new HashSet();
		patterns.add("id");
		patterns.add("title");
		Iterator it = patterns.iterator();
		while(it.hasNext()){
			String t=(String)it.next();
			//System.out.println(Pattern.matches(t,"id"));
		}
	}
	
	public void addService(String serviceName,RemoteService service)
	{
		configManager.getServices().put(serviceName, service);
	}
	public void addConvertBean(String className,RemoteService service)
	{
		configManager.getConvertBeans().put(className,service);
	}
	public void addAllowName(String name)
	{
		String theName=name;
		if(theName.charAt(0)=='*')theName="."+name;
		configManager.getAllowNames().add(theName);
	}
	public void addDenyName(String name)
	{
		String theName=name;
		if(theName.charAt(0)=='*')theName="."+name;
		configManager.getDenyNames().add(theName);
	}
	public void setContainer(Container container) {
		this.container = container;
	}		
}
