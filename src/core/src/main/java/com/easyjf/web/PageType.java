package com.easyjf.web;

/**
 * 页面视图Page类型
 * 
 * @Author <a href="mailto:easyjf@163.com">daxia</a>
 * @Creation date: 2010-7-29 下午12:10:50
 * @Intro
 */
public enum PageType {
	HTML,REDIRECT,TEMPLATE, FORWARD, ACTION, nullPage, STRING,JSP;
	@Override
	public String toString() {
		switch(this){
			case HTML:return "html";
			case REDIRECT:return "html";
			case TEMPLATE:return "template";
			case FORWARD:return "forward";
			case ACTION:return "action";
			case nullPage:return "null";
			case STRING:return "string";
			case JSP:return "forward";
		}
		return super.toString();
	}
	
}
