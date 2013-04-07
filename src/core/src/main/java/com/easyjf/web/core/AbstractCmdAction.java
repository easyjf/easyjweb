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
package com.easyjf.web.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.easyjf.container.annonation.InjectDisable;
import com.easyjf.container.annonation.WebCache;
import com.easyjf.util.CommUtil;
import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Globals;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.interceptor.IActionCommandInterceptor;
import com.easyjf.web.tools.widget.Html;
import com.easyjf.web.validate.ValidateType;

/**
 * 
 * <p>
 * Title: 通过命令式Action类
 * </p>
 * <p>
 * Description: 这是一个对Action命令进行封装的抽象类,用于为提供命令式WebAction的写法用户直接调用,可以减少书写繁锁的if语句
 * </p>
 * easyJWebCommand命令del(或Del)对应Action类的doDel方法
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * 1:35 2007-6-28 CHANGELOG 改变execute(WebForm form,Module module) 为
 * execute(WebForm f,Module m) ----DRY 改变easyJWebCommand为cmd，同时支持
 * 
 * @author 蔡世友 郭朝斌
 * @version 1.0
 */
public abstract class AbstractCmdAction implements IWebAction {

	/*
	 * 禁止自动注入
	 */
	@InjectDisable
	protected String command;// 当前执行的command

	@InjectDisable
	protected Page forwardPage;

	@InjectDisable
	private TokenProcessor token = TokenProcessor.getInstance();
		
	@InjectDisable
	private static final Logger logger = Logger
			.getLogger(AbstractCmdAction.class);	
	@InjectDisable
	private static List<IActionCommandInterceptor> cmdInterceptors=new java.util.ArrayList<IActionCommandInterceptor>();
	/**
	 * 清除当前form表单中的参数，开启一个新的跳转，跳转到当前module中的某一个模板
	 * 
	 * @param command
	 * @return
	 */
	protected Page go(String command) {
		return forward(command, true);
	}

	/**
	 * 开启一个新的跳转，跳到某一个模块中的其它页面
	 * 
	 * @param command
	 * @param moduleName
	 * @return
	 */
	protected Page go(String command, String moduleName) {
		return go(moduleName + "." + command);
	}

	/**
	 * 带环境的跳转
	 * 
	 * @param command
	 * @param moduleName
	 * @return
	 */
	protected Page forward(String command, String moduleName) {
		return forward(moduleName + "." + command);
	}

	/**
	 * 跑转到某一个当前module中的某一个命令继续执行，注意不能嵌套调用
	 * 
	 * @param command
	 *            要执行的命令名称
	 * @return 返回该命令的执行结果
	 */
	protected Page forward(String command) {
		return forward(command, false);
	}

	protected Page page(String pageName) {
		Module module = ActionContext.getContext().getWebInvocationParam()
				.getModule();
		if (module != null)
			this.forwardPage = module.findPage(pageName);
		return this.forwardPage;
	}

	protected Page forward(String command, boolean renew) {
		if (renew)// 全新的跳转
		{
			this.forwardPage = new Page(command, getUrl(command), "html");
		} else {
			if (command.indexOf(".") > 0) {
				return new Page("actionJump", command, "action");
			}
			WebForm form = ActionContext.getContext().getWebInvocationParam()
					.getForm();
			form.getTextElement().put("easyJWebCommand", command);
			Module module = ActionContext.getContext().getWebInvocationParam()
					.getModule();
			try {
				this.forwardPage = execute(form, module);
			} catch (Exception e) {

			}
		}
		return this.forwardPage;
	}

	private String getUrl(String command) {
		String cmd = command/*
							 * , module = ActionContext.getContext()
							 * .getWebInvocationParam().getModule().getPath()
							 */;
		if (cmd.toLowerCase().indexOf("http://") == 0)
			return cmd;// 若为http开头，则直接跳转到指定http地址
		/*
		 * if (command.indexOf('.') > 0)// 若包含.格式的跳转，则为module.command的形式 {
		 * module = "/" + command.substring(0, command.indexOf('.')); cmd =
		 * command.substring(command.indexOf('.') + 1); } String url =
		 * ActionContext.getContext().getRequest().getRequestURI(); int l =
		 * url.indexOf("/ejf/"); if (l == 0 || l == 1) { url = "/ejf/" + module +
		 * "/" + cmd; url = url.replaceAll("//+", "/"); } else url = module +
		 * ".ejf?easyJWebCommand=" + cmd;
		 */
		String url = Html.getInstance().forward(command);
		return url;
	}

	/*
	 * 新版本使有简化的f 和 m 以DRY Don't repeat yourself
	 * 
	 * @see com.easyjf.web.IWebAction#execute(com.easyjf.web.WebForm,
	 *      com.easyjf.web.Module)
	 */
	public Page execute(WebForm f, Module m) throws Exception {
		// 前置过滤器
		Object beforeCheck = doBefore(f, m); // 模版方法
		if (beforeCheck != null) {
			// 如果Action执行前检查，返回Page则直接跳转
			if (beforeCheck.getClass() == Page.class) {
				return (Page) beforeCheck;
			}
		}
		Page forward = null;
		Object o = f.get("easyJWebCommand");
		if (o == null)
			o = f.get("cmd");
		if (o != null) {
			if (o.getClass().isArray()) {
				String[] s = (String[]) o;
				StringBuffer sb = new StringBuffer(I18n.getLocaleMessage("core.web.execute")).append(
						this.getClass().toString()).append(I18n.getLocaleMessage("core.web.found.a.number.of.commands"));
				for (int i = 0; i < s.length; i++) {
					if (command == null && s[i] != null)
						command = s[i];
					sb.append(s[i]).append(",");
				}
				logger.info(sb.append(I18n.getLocaleMessage("core.web.from.here")).append(command).append(I18n.getLocaleMessage("core.web.value")));
			} else
				command = o.toString();
		}
		/*
		 * command = CommUtil.null2String(f.get("easyJWebCommand")); if
		 * ("".equals(command)) command = CommUtil.null2String(f.get("cmd"));
		 */
		try {

			if (command == null || "".equals(command))
				command = "init";
			f.addResult("command", command);
			Class[] paras = new Class[2];
			paras[0] = WebForm.class;
			paras[1] = Module.class;
			MethodHolder mh = findCmdMethod(this.getClass(), command);
			if (mh != null) {
				if(cmdInterceptors.isEmpty())cmdInterceptors=FrameworkEngine.getContainer().getBeans(IActionCommandInterceptor.class);
				//ActionCommand前拦截
				if(cmdInterceptors!=null && !cmdInterceptors.isEmpty()){
					for(int i=0;i<cmdInterceptors.size();i++){
					IActionCommandInterceptor it=cmdInterceptors.get(i);
					Page page=it.doBefore(mh.getMethod());
					if(page!=null)return page;
					}
				}
				///在这里加入Action中的方法执行权限判断代码比较适合　
				/*PermissionVerify pv=this.getClass().getAnnotation(PermissionVerify.class);
				RoleVerify rv = mh.getMethod().getAnnotation(RoleVerify.class);
				if((pv!=null && !pv.disable())||rv!=null||FrameworkEngine.getWebConfig().isPermissionVerify()){//执行权限查找					
					if(permissionCheck==null){//首先从容器中尝试加载
						permissionCheck=(IPermissionCheck) FrameworkEngine.getContainer().getBean(IPermissionCheck.class);
						if(permissionCheck==null){//如果加载不到，则直接创建一个默认的权限检查器
							permissionCheck=new DefaultPermissionCheck();
						}
					}
					if(!permissionCheck.checkPermission(mh.getMethod())){//如果权限通不过
						ActionContext.getContext().getResponse().setHeader("Unauthorized", "true");
						Page p=m.findPage("PERMISSION_PAGE");//允许自定义配置权限不足显示页面						
						if(p==null)p=new Page("PERMISSION_PAGE","classpath:com/easyjf/web/exception/permissionError.html");
					    return p;
					}
				}*/
				Object[] objs = new Object[mh.paras.length];
				if (objs.length > 1)
					objs[1] = m;
				if (objs.length > 0)
					objs[0] = f;
				try {
					WebCache cache = mh.getMethod().getAnnotation(
							WebCache.class);
					if (cache != null) {
						WebCacheManager manager = WebCacheManager.getInstance();
						forward = manager.handleCache(ActionContext
								.getContext().getWebInvocationParam(), cache);
					}
					if (forward == null) {						
						Object ret = mh.getMethod().invoke(this, objs);
						if (ret instanceof Page)
							forward = (Page) ret;
					}
				} catch (java.lang.reflect.InvocationTargetException ite) {
					// ite.printStackTrace();
					if (ite.getCause() != null
							&& ite.getCause() instanceof Exception)
						throw (Exception) ite.getCause();
				}
				//ActionCommand的后拦截
				if(cmdInterceptors!=null && !cmdInterceptors.isEmpty()){
					for(int i=0;i<cmdInterceptors.size();i++){
					IActionCommandInterceptor it=cmdInterceptors.get(i);
					it.doAfter(mh.getMethod());
					}
				}
			} else
				throw new Exception(I18n.getLocaleMessage("core.web.methods.name.is.not.correct.in") + this.getClass() + I18n.getLocaleMessage("core.web.no.find")
						+ command + I18n.getLocaleMessage("core.web.method.Please.confirm.your.pages.easyJWebCommand.or.cmd.in.the.parameter.values.are"));

		} catch (NoSuchMethodException nomethod)// 找不到处理的方法，则尝试直接加载视图
		{
			Page p = this.forwardPage;
			if (p == null)
				p = m.findPage(command);
			if (p != null)
				forward = p;
			else
				throw nomethod;
		}
		doAfter(f, m);		
		if (forward == null)
			forward = this.forwardPage;
		return forward;
	}

	private MethodHolder findCmdMethod(Class actionClz, String command)
			throws NoSuchMethodException {
		String name = "do" + command.substring(0, 1).toUpperCase()
				+ command.substring(1);
		MethodHolder ret = null;
		try {
			ret = findCmdMethod1(actionClz, name);// 查找doXxxx方法
		} catch (NoSuchMethodException e) {
			try {
				if (command.indexOf("do") == 0 && command.length() > 2
						&& Character.isUpperCase(command.charAt(2)))
					throw e;
				ret = findCmdMethod1(actionClz, command);// 查找xxxx方法
			} catch (NoSuchMethodException innerE) {
				throw e;
			}
		}
		return ret;
	}

	private MethodHolder findCmdMethod1(Class actionClz, String name)
			throws NoSuchMethodException {
		Method method = null;
		Class[] paras = null;
		// 首先查询参数为WebForm,Module的方法
		try {
			paras = new Class[] { WebForm.class, Module.class };
			method = actionClz.getMethod(name, paras);
			if (method != null) {				
				return new MethodHolder(method, paras);
			}
		} catch (java.lang.NoSuchMethodException e) {
		}
		// 查询参数为WebForm的方法
		try {
			paras = new Class[] { WebForm.class };
			method = actionClz.getMethod(name, paras);
			if (method != null) {
				return new MethodHolder(method, paras);
			}
		} catch (java.lang.NoSuchMethodException e) {
		}
		// 查询参数为Module的方法
		try {
			paras = new Class[] { Module.class };
			method = actionClz.getMethod(name, paras);
			if (method != null) {
				return new MethodHolder(method, paras);
			}
		} catch (java.lang.NoSuchMethodException e) {
		}
		// 查询没有任何参数的方法,出错后将抛出NoSuchMethodException异常
		paras = new Class[] {};
		method = actionClz.getMethod(name, paras);
		if (method != null) {
			return new MethodHolder(method, paras);
		}
		return null;
	}

	/**
	 * 在执行具体的doCommond方法前执行
	 * 
	 * @param form
	 * @param module
	 * @return 若要放弃执行后台的内容，则返回一个Page对象，否则返回null
	 */
	public Object doBefore(WebForm form, Module module) {
		return null;
	};

	/**
	 * 在没有输入任何命令的时候执行
	 * 
	 * @param form
	 * @param module
	 * @return 在没有传输任何参数时，将理解为执行模块初始化
	 */
	public abstract Page doInit(WebForm form, Module module);// 初始页、默认页或相当index首页

	/**
	 * 在执行doCommand方法后执行
	 * 
	 * @param form
	 * @param module
	 * @return Page对象或其它
	 */
	public Object doAfter(WebForm form, Module module) {
		return null;
	};

	/**
	 * 在Action中也可以直接通过调用addError方法来往请求中发送错误命令，这样使得可以非常方便的在具体的Action中调用错误处理的方法
	 * 
	 * @param name
	 * @param msg
	 */
	protected void addError(String name, String msg) {
		FrameworkEngine.findValidatorManager().addCustomError(name, msg,
				ValidateType.Action);
	}

	protected boolean hasErrors() {
		return FrameworkEngine.findValidatorManager().getErrors().hasError();
	}
	protected Map getErrors() {
		if(this.hasErrors())
			return new java.util.HashMap(FrameworkEngine.findValidatorManager().getErrors().getErrors());
		return new java.util.HashMap();
	}
	/**
	 * 用来提供双重模板查找机制，先通过逻辑相应的模板，如果找不到，则直接使用备选的模板
	 * 
	 * @param logicName
	 *            模板逻辑名称
	 * @param optionalPage
	 *            备选模板全路径
	 * @return 如果存在指定逻辑名的模板，则返回该模板，否则返回备选模板
	 */
	protected Page findPage(String logicName, String optionalPage) {
		Page p = page(logicName);
		if (p == null)
			p = new Page(logicName, optionalPage);
		return p;
	}

	private class MethodHolder {

		private Method method;

		private Class[] paras;

		MethodHolder(Method method, Class[] paras) {
			this.method = method;
			this.paras = paras;
		}

		public Method getMethod() {
			return method;
		}

		public void setMethod(Method method) {
			this.method = method;
		}

		public Class[] getParas() {
			return paras;
		}

		public void setParas(Class[] paras) {
			this.paras = paras;
		}
	}

	/**
	 * 保存一个token，用于防止重复提交 在需要防止重复提交的action中合适的地方添加代码saveToken()
	 * 在导向的页面的Form中添加$html.token来加入一个hidden域
	 * 在处理提交的Action中添加isVaildateToken()方法即可验证Token是否合法
	 */
	protected void saveToken() {
		token.saveToken();
	}

	/**
	 * 验证token是否合法
	 * 
	 * @return
	 */
	protected boolean isVaildateToken() {
		return token.isTokenValid((String) ActionContext.getContext()
				.getWebInvocationParam().getForm().get(Globals.TOKEN_NAME));
	}

	/**
	 * 验证token是否合法，并在验证后清空session中的token域
	 * 
	 * @param reset
	 * @return
	 */
	protected boolean isVaildateToken(boolean reset) {
		return token.isTokenValid(reset, (String) ActionContext.getContext()
				.getWebInvocationParam().getForm().get(Globals.TOKEN_NAME));
	}

	/**
	 * 回到上一页
	 * 
	 * @return
	 */
	protected Page goBack() {
		WebForm form = ActionContext.getContext().getWebInvocationParam()
				.getForm();
		String url = form.getUrl();
		String msg = CommUtil.null2String(form.getEasyJWebResult().get("msg"));
		if(!"".equals(msg)){
			form.addResult("url", form.getUrl());
			return new Page("classpath:com/easyjf/web/tools/js.html");
		}
		return new Page("back", url, "html");
	}

	/**
	 * 清除环境变量
	 */
	protected void reset() {
		this.forwardPage = null;
		this.command = null;
	}
	
	public String get(String key, WebForm form){
		String value = CommUtil.null2String(form.get(key));
		return value;
	}
	
	protected Page pageForExtForm(WebForm form)
	{
		String ScriptTag=CommUtil.null2String(form.get("ScriptTagConnection"));
		if("true".equals(ScriptTag)){
			form.addResult("ScriptTagConnectionCallback", form.get("callback"));
		}
		ExtResult r=new ExtResult();
		if(this.hasErrors())
		{	
			r.setSuccess(false);
			java.util.Iterator it=this.getErrors().entrySet().iterator();
			while(it.hasNext())
			{
				Entry en=(Entry)it.next();
				r.getErrors().put(en.getKey().toString(), en.getValue().toString());
			}
		}
		r.setData(form.getEasyJWebResult().get("JSonObject"));
		form.jsonResult(r);
		this.forwardPage=Page.JSONPage;
		return Page.JSONPage;	
	}
	
	protected HttpServletRequest getRequest(){
		return ActionContext.getContext().getRequest();
	}
	
	protected HttpSession getSession(){
		return ActionContext.getContext().getSession();
	}
	
	protected Object getSessionAtrribute(String key){
		return getSession().getAttribute(key);
	}
	
	protected void setSessionAtrribute(String key, Object value){
		this.getSession().setAttribute(key, value);
	}
}
