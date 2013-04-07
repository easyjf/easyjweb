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

import java.lang.reflect.Method;
import java.util.Map;

import com.easyjf.util.CommUtil;
import com.easyjf.util.I18n;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.exception.ValidateException;
import com.easyjf.web.validate.ValidatorManager;
/**
 * 处理远程脚本调用的Action引擎
 * @author 大峡
 *
 */

public class AjaxEngineAction implements IWebAction {	
	public Page execute(WebForm form, Module module) throws Exception {
		Page forward = null;
		String command = CommUtil.null2String(form.get("easyJWebCommand"));
		if("".equals(command))command=CommUtil.null2String(form.get("cmd"));
		boolean getAjaxStub=true;
		if ("".equals(command)) {

		} else {
			if (command.endsWith(".js"))
			{
				command = command.substring(0, command.length() - 3);
				//getAjaxStub=true;
			}
			Class[] paras = new Class[2];
			paras[0] = WebForm.class;
			paras[1] = Module.class;
			String cmd = "do" + command.substring(0, 1).toUpperCase()
					+ command.substring(1);
			try{
			Method m = this.getClass().getMethod(cmd, paras);
			if (m != null) {
				Object[] objs = new Object[2];
				objs[0] = form;
				objs[1] = module;
				Object ret = m.invoke(this, objs);
				if (ret instanceof Page)
					forward = (Page) ret;
			}
			}
			catch(java.lang.NoSuchMethodException e)
			{
				if(getAjaxStub)
				{
					return doAjaxHome(form,module);
				}
				throw new Exception(I18n.getLocaleMessage("core.ajax.methods.name.is.not.correct.in.the")+ this.getClass() + I18n.getLocaleMessage("core.ajax.no.find")
						+ cmd + I18n.getLocaleMessage("core.ajax.method.Please.check.your.page.for.easyJWebCommand.parameter.values.are.correct"));
			}				
		}
		return forward;
	}

	/**
	 * 生成ajax调试页面
	 * 
	 * @param form
	 * @param module
	 * @return js骨架模板
	 * @throws Exception
	 */
	public Page doAjaxHome(WebForm form, Module module) throws Exception {
		String command = CommUtil.null2String(form.get("easyJWebCommand"));
		if("".equals(command))command=CommUtil.null2String(form.get("cmd"));
		if (command.endsWith(".js"))
		{
			command = command.substring(0, command.length() - 3);
		}
		form.addResult("scriptName", command);
		form.addResult("methodList", AjaxUtil.getAjaxStub(command));
		return module.findPage("stub");
	}

	/**
	 * 显示engine脚本引擎
	 * 
	 * @param form
	 * @param module
	 * @return engine-js视图模板
	 * @throws Exception
	 */
	public Page doEngine(WebForm form, Module module) throws Exception {
		return module.findPage("engine-js");
	}
	public Page doDWRUtil(WebForm form, Module module) throws Exception {
		return module.findPage("engine-util");
	}
	public Page doUtil(WebForm form, Module module) throws Exception {
		return module.findPage("engine-util");
	}
	public Page doPrototype(WebForm form, Module module) throws Exception {
		return module.findPage("prototype");
	}
	public Page doRico(WebForm form, Module module) throws Exception {
		return module.findPage("rico");
	}
	public Page doValidate(WebForm form, Module module) throws Exception {
		String result="{";			
		try
		{
			ValidatorManager vm=FrameworkEngine.findValidatorManager();	
			if(vm!=null)
			{
				String name=CommUtil.null2String(form.get("validateObjectName"));
				String property=CommUtil.null2String(form.get("validateField"));
			
				if(!"".equals(name))
				{
					//form.toPo(Person.class);///Ajax验证
				}				
				if(vm.getErrors().hasError())
				{
					form.addResult("error",true);
						
					java.util.Iterator it=vm.getErrors().getErrors().entrySet().iterator();
					while(it.hasNext())
					{
					Map.Entry en=(Map.Entry)it.next();
					if("".equals(property)||property.equals(en.getKey()))
					result+=en.getKey()+":\""+en.getValue()+"\",";			
					}
					if(result.endsWith(","))result=result.substring(0,result.length()-1);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		result+="}";		
		form.addResult("result", result);
		return module.findPage("plain");
	}
	
	/**
	 * 生成并显示支持远程调用服务器端对象的JS存根
	 * 
	 * @param form
	 * @param module
	 * @return 存根显示模板
	 * @throws Eception
	 */
	public Page doAjaxStub(WebForm form, Module module) throws Exception {
		
		return null;
	}

	public Page doAjaxTest(WebForm form, Module module) throws Exception {
		return null;
	}

	/**
	 * 执行Ajax调用
	 * 
	 * @param form
	 * @param module
	 * @return 调用结果显示模板
	 * @throws Eception
	 */
	public Page doAjaxCall(WebForm form, Module module) throws Exception {
		String methodName = (String) form.get("methodName");
		String scriptName=(String)form.get("scriptName");
		//String ajaxCallId=(String)form.get("remoteCallId");
		RemoteCall remoteCall=new RemoteCall(scriptName,methodName,form.getTextElement());		
		try{				
		form.addResult("result", remoteCall.execute());	
		}	
		catch(ValidateException ve)
		{
			form.addResult("error","true");			
			form.addResult("result","return \"数据验证错误:"+CommUtil.convert2unicode(FrameworkEngine.getValidateManager().getErrors().getMessage(),false)+"\"");			
		}
		catch(Exception e)
		{	
			form.addResult("error","true");			
			form.addResult("result","return '服务器端调用出错:"+(e.getCause()!=null&&e.getCause().getMessage()!=null?e.getCause().getMessage():e.getMessage())+"'");
			e.printStackTrace();
		}	
		return module.findPage("plain");
	}
	
}
