package com.easyjf.web.struts2;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.easyjf.web.core.ExtResult;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("serial")
public abstract class JsonCrudAction<T> extends ActionSupport implements ModelDriven<T>, Preparable {
	private Object jsonObject;
	protected String JSONResult="json";
	
	protected void setJSonData(Object obj){
		jsonObject=obj;
	}
	
	public Object getJsonData() {
		return jsonObject;
	}
	
	protected String resultForExtForm(){
		ExtResult er=new ExtResult();
		if(this.hasErrors()){
			er.setSuccess(false);
			java.util.Iterator<Map.Entry<String, List<String>>> es=this.getFieldErrors().entrySet().iterator();
			while(es.hasNext()){
				Map.Entry<String, List<String>> en=es.next();
				StringBuffer ss=new StringBuffer("");
				List<String> vs=en.getValue();
				for(String s:vs){
					ss.append(s).append(";");
				}
				if(ss.length()>1)ss.deleteCharAt(ss.length()-1);
				er.getErrors().put(en.getKey(), ss.toString());
			}
			Collection<String> as=this.getActionErrors();
			if(as!=null && !as.isEmpty()){
				Iterator<String> ies=as.iterator();
				StringBuffer ss=new StringBuffer("");
				while(ies.hasNext()){
					ss.append(ies.next()).append(";");
				}
				if(ss.length()>1)ss.deleteCharAt(ss.length()-1);
				er.getErrors().put("msg",ss.toString());
			}
		}
		this.setJSonData(er);
		return JSONResult;
	}
	boolean isGzipInRequest(HttpServletRequest request) {
		String header = request.getHeader("Accept-Encoding");
		return header != null && header.indexOf("gzip") >= 0;
	}
	
	@Override
	public String execute() throws Exception {
		return index();
	}
	
	protected String getParameter(String name){
		return ServletActionContext.getRequest().getParameter(name);
	}
	protected HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse();
	}
	protected String getActionName(){
		String action=this.getClass().getSimpleName();
		Controller ac=this.getClass().getAnnotation(Controller.class);
		if(ac!=null){
			action=ac.value();
			if(action.lastIndexOf("Action")>0)action=action.substring(0,action.lastIndexOf("Action"));
		}
		return action;
	}
	public String index() {
		ServletActionContext.getRequest().setAttribute("actionName", getActionName());
		return "list";
	}
}
