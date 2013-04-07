package com.easyjf.container;

public class MyService {
private static MyService singleton;
private MyService()
{
	
}
public static MyService getInstance()
{
if(singleton==null)singleton=new MyService();
return singleton;
}
}
