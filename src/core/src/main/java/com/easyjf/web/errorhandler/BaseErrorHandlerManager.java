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
package com.easyjf.web.errorhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class BaseErrorHandlerManager implements IErrorHandlerManager{
	private Map error2HandlerMap;

	public Map getError2HandlerMap() {
		if(this.error2HandlerMap==null){
			this.error2HandlerMap=new HashMap();
		}
		return error2HandlerMap;
	}
	
	public BaseErrorHandlerManager(){
		init();
	}
	 
	abstract protected void init();

	public void setError2HandlerMap(Map error2HandlerMap) {
		this.error2HandlerMap = error2HandlerMap;
	}
	
	private IErrorHandler defaultHandler;

	public IErrorHandler getDefaultHandler() {
		return defaultHandler;
	}

	public void setDefaultHandler(IErrorHandler defaultHandler) {
		this.defaultHandler = defaultHandler;
	}

	public List findHandler(HttpServletRequest request, HttpServletResponse response, Throwable error){
		// TODO Auto-generated method stub
		List ret=new ArrayList();
		if(this.getError2HandlerMap()!=null&&this.getError2HandlerMap().size()>0){
			for(Iterator it=this.getError2HandlerMap().keySet().iterator();it.hasNext();){
				String exClazz=(String)it.next();
				if(exClazz.equalsIgnoreCase(error.getClass().getName())){
					ret.add(this.getError2HandlerMap().get(exClazz));
				}else if(exClazz.equalsIgnoreCase(error.getClass().getName().substring(error.getClass().getName().lastIndexOf(".")))){
					ret.add(this.getError2HandlerMap().get(exClazz));
				}				
			}
		}else if(this.getDefaultHandler()!=null){
			ret.add(this.getDefaultHandler());
		}
		return ret;
	}
	
	protected void registerHandler(String exClazz,IErrorHandler handler) throws Exception{
		if(exClazz.equals("java.lang.Exception")){
			this.setDefaultHandler(handler);
		}else{
			this.getError2HandlerMap().put(exClazz, handler);
		}		
	}	
	
}
