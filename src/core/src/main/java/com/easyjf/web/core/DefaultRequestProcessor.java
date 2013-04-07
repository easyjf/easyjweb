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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

import com.easyjf.util.I18n;
import com.easyjf.util.StringUtils;
import com.easyjf.web.ActionContext;
import com.easyjf.web.ActionServlet;
import com.easyjf.web.Globals;
import com.easyjf.web.IPageVender;
import com.easyjf.web.IPathMappingRuler;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.RequestProcessor;
import com.easyjf.web.WebConfig;
import com.easyjf.web.WebForm;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.core.support.DefaultPageVender;
import com.easyjf.web.errorhandler.IErrorHandlerManager;
import com.easyjf.web.exception.DefaultExceptionHandle;
import com.easyjf.web.exception.FrameworkException;
import com.easyjf.web.exception.TokenVaildException;
import com.easyjf.web.exception.ValidateException;
import com.easyjf.web.interceptor.AfterInterceptor;
import com.easyjf.web.interceptor.BeforeInterceptor;
import com.easyjf.web.interceptor.ExceptionInterceptor;
import com.easyjf.web.interceptor.Interceptor;
import com.easyjf.web.validate.ValidatorManager;

/**
 * 
 * <p>
 * Title:框架默认处理器
 * </p>
 * <p>
 * Description:EasyJWeb框架的中心处理器,由ActionServlet调用,然后调用相应的Action并返回用户服务
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友,stef,tony
 * @version 1.0
 * @since 2006/9
 */
public class DefaultRequestProcessor implements RequestProcessor {

	private static final long serialVersionUID = -4362786097761398444L;

	private ActionServlet servlet;

	private IErrorHandlerManager errorHandlerManager;

	private WebConfig webConfig;

	private static List<IPageVender> pageVenders;

	private static final Logger logger = Logger
			.getLogger(DefaultRequestProcessor.class);

	private TokenProcessor token = TokenProcessor.getInstance();

	public DefaultRequestProcessor() {
		this(null, null);
	}

	/**
	 * Processor只能由ActionServlet来启动
	 * 
	 * @param servlet
	 */
	public DefaultRequestProcessor(ActionServlet servlet, WebConfig webConfig) {
		this.servlet = servlet;
		this.webConfig = webConfig;
	}

	public void setServlet(ActionServlet servlet) {
		this.servlet = servlet;
	}

	/**
	 * 异常处理系统还有一定的问题
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		try {
			ValidatorManager vm = FrameworkEngine.getValidateManager();
			if (vm != null)
				vm.cleanErrors();		
			IPathMappingRuler mappingRuler = new PathMappingRulerImpl(request);			
			/**
			 * 加入国际化环境
			 */
			I18NResourceCache resource = new I18NResourceCache(FrameworkEngine
					.getResourcePath());			
			resource.setLocale(I18n.getLocale());			
			FrameworkEngine.setResourceCache(resource);
			int jumpActionTimes = 0;
			process(request, response, vm, mappingRuler, null, jumpActionTimes);
		} catch (Exception e) {
			logger.error(I18n.getLocaleMessage("core.web.error") + e);
			doErrorHandler(e, ActionContext.getContext()
					.getWebInvocationParam());
		}
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response, ValidatorManager vm,
			IPathMappingRuler mappingRuler, WebForm webForm, int jumpTimes)
			throws FrameworkException, Throwable, Exception, ValidateException,
			IOException, ServletException {
		// 模块名称
		String moduleName = mappingRuler.getModuleName();
		// 取模块
		Module module = FrameworkEngine.findModule(moduleName);
		Page page = null;
		if (module != null) {
			String formName = module.getForm();
			// 创建WebForm
			WebForm form = webForm == null ? FrameworkEngine.creatWebForm(
					request, formName, module) : webForm;

			form.setUrl(ActionContext.getContext().getRequest().getHeader("REFERER"));
			// 取得命令，主要用于支持modern形式的url
			if (mappingRuler.getCommand() != null) {
				form.getTextElement().put("easyJWebCommand",mappingRuler.getCommand());
			}
			// 装载页面参数
			if (mappingRuler.getParams() != null) {
				form.getTextElement().putAll(mappingRuler.getParams());
			}
			// 装载action
			IWebAction action = FrameworkEngine.findAction(module);
			if (action == null) {
				throw new FrameworkException(I18n.getLocaleMessage("core.web.did.not.find.the.class.with.the.template") + module.getAction());
			}
			if (form == null) {
				throw new FrameworkException(I18n.getLocaleMessage("core.web.create.the.error.form") + formName);
			}
			WebInvocationParam param = new WebInvocationParam(action, form,module);
			param.setUrlType(mappingRuler.getUrlPattern());
			param.setSuffix(mappingRuler.getSuffix());
			ActionContext.getContext().put(ActionContext.WEBINVOCATION, param);// 把调用参数值设置到上下文中
			// 开始执行Action中的主体方法，包括全局拦截、Action前置拦截、Action的execute、Action后置拦截等方法
			try {
				// 执行全局拦截操作,对非法输入进行拦截
				invokeGlobalInterceptors(param);
				// 执行前拦截操作
				invokeActionBeforeInterceptors(module, param);
				// 处理自动token
				this.processAutoToken(param);
				// 处理业务操作并返回form对象
				page = getResult(param);
				// 执行后拦截
				invokeActionAfterInterceptors(module, page, param);
				// 如果Action正常执行，根据module中validate属性的配置，以及执行过程中的错误，判断是否使启动自动验证功能。
				if (module.isValidate() && vm != null
						&& vm.getErrors().hasError()) {
					Page tempPage = page;
					page = findValidatePage(param); // 查找并返回自动验证页面
					if (page == null)
						page = tempPage;
				}
			} catch (ValidateException ve) {
				// 执行验证处理
				form.getEasyJWebResult().putAll(form.getTextElement());// 把form中填写的数据存入结果集中
				page = findValidatePage(param);
				if (page == null)throw ve;// 找不到验证错误页面，把异常交给异常拦截器统一处理
			}
			if (page != null) {
				Map result = form.getEasyJWebResult();
				for (Iterator it = result.keySet().iterator(); it.hasNext();) {
					String name = (String) it.next();
					request.setAttribute(name, result.get(name));
				}
				// 如果配置的是模版则合成模版
				if (page.getType().equals(Globals.PAGE_TEMPLATE_TYPE)||"string".equals(page.getType())) {

					doVenderPage(page, form, request, response, param);
					// 执行回调
					if (ActionContext.getContext().getRequestCallback() != null) {
						ActionContext.getContext().getRequestCallback().doFinish();
					}
					if (ActionContext.getContext().getUri() != null) {
						doForward(ActionContext.getContext().getUri(), request,response);
					}
				} else if ("null".equals(page.getType())) {
					// 空结果集页面，表示不返回任何结果集，
				} else if ("action".equals(page.getType())) {
					processActionPage(request, response, vm, page, param,
							jumpTimes);
				} else if ("forward".equals(page.getType())) {
					doForward(page.getUrl(), request, response);
				} else {
					// 其它类型全部当作html来处理
					// logger.debug("跳转到指定页面："+page.getUrl());
					// doForward(page.getUrl(),request,response);
					String gotoUrl=page.getUrl();
					if(gotoUrl.indexOf(':')<0)gotoUrl=request.getContextPath()+gotoUrl;
					response.sendRedirect(gotoUrl);
				}
			} else {
				logger.debug(I18n.getLocaleMessage("core.web.no.pages.to.jump") + module.getPath()+ module.getDefaultPage());
				// servlet.error(request,response,new
				// Exception("没有设置跳转的页面！"));
			}
		}
	}

	/**
	 * 处理模板合成 首先初始化模板合成器链， 然后调用页面合成器完成页面合成。
	 * 
	 * @param page
	 *            Page对象
	 * @param form
	 *            WebForm对象
	 * @param request
	 *            HttpservletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doVenderPage(Page page, WebForm form,
			HttpServletRequest request, HttpServletResponse response,
			WebInvocationParam param) throws IOException, ServletException {
		if (pageVenders == null) {
			pageVenders = FrameworkEngine.getContainer().getBeans(IPageVender.class);
			pageVenders.add(new DefaultPageVender());
		}
		if (pageVenders != null) {
			String suffix="";
			int p=page.getUrl().lastIndexOf('.');
			if(p>0)
			{
				suffix=page.getUrl().substring(p+1);
			}		
			for (IPageVender vender : pageVenders) {
				if(vender.supports(suffix)){
//				logger.debug(I18n.getLocaleMessage("core.web.treatment.of.pages.with.venderPage")+ vender.getClass().getCanonicalName());
				if (vender.venderPage(request, response, page, param)) {
					break;
				}
				}
			}
		}
	}

	/**
	 * 处理action类型的Page对象
	 * 对于该类型对象，会记录已经跳转的次数，并在跳转次数达到框架设置的最大跳转次数后抛出FrameworkException。
	 * 如果能继续传递该跳转，会根据page的url样式构建一个新的pathRuler对象，并将当前的WebForm和pathRuler对象一起传递给process方法进行下一个action的执行
	 * 
	 * @param request
	 * @param response
	 * @param vm
	 * @param page
	 * @param param
	 * @param jumpTimes
	 * @throws FrameworkException
	 * @throws Throwable
	 * @throws Exception
	 * @throws ValidateException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processActionPage(HttpServletRequest request,
			HttpServletResponse response, ValidatorManager vm, Page page,
			WebInvocationParam param, int jumpTimes) throws FrameworkException,
			Throwable, Exception, ValidateException, IOException,
			ServletException {
		if (jumpTimes + 1 > this.webConfig.getMaxDirectJumpToActionTimes()) {
			throw new FrameworkException(
					"has jump direct to another action more than "
							+ this.webConfig.getMaxDirectJumpToActionTimes()
							+ " times,please check if there has a dead lock jump circle.");
		} else {
			jumpTimes += 1;
			if (page.getUrl().indexOf(".") < 0) {
				throw new FrameworkException(
						"you send a page to jump to a action,but you give a wrong page url,the correct pattern must be module.command");
			}
			PathMappingRulerImpl ruler = new PathMappingRulerImpl(page.getUrl());
			ruler.setUrlPattern(ActionContext.getContext()
					.getWebInvocationParam().getUrlType());
			this.process(request, response, vm, ruler, param.getForm(),
					jumpTimes);
		}
	}

	/**
	 * 该方法根据调用参数返回出现验证错误时，应该返回的页面
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	protected Page findValidatePage(WebInvocationParam param) throws Exception {
		Page page = param.getModule().findPage("input");
		if (page == null) {
			String input = ActionContext.getContext().getRequest().getHeader(
					"Referer");
			String[] ps = input.split("/");
			// System.out.println(ps[ps.length-1]);
			page = param.getModule().findPage(ps[ps.length - 1]);
		}
		return page;
	}

	private void processAutoToken(WebInvocationParam param) throws Exception {
		Module m = param.getModule();
		if (m.getAutoToken()) {
			String tokenInRequest = (String) param.getForm().get(
					Globals.TOKEN_NAME);
			if (tokenInRequest != null && !"".equals(tokenInRequest)) {
				if (!this.token.isTokenValid(tokenInRequest)) {
					logger.debug("token vaild");
					throw new TokenVaildException("token vaild!");
				}
			}
			token.saveToken();
		}
	}

	/**
	 * 执行错误处理,如果从异常处理链中抛出了异常，则将把异常抛给框架外的过滤器、Web容器及用户
	 * 
	 * @param error
	 *            程序执行过程中的异常
	 * @param param
	 *            调用参数
	 * @throws Exception
	 *             异常处理器出现异常，则表示异常将不由EasyJWeb处理
	 */
	protected void doErrorHandler(Throwable error, WebInvocationParam param)
			throws Exception {
		// this.container.getBean(ExceptionInterceptor.class)
		java.util.Iterator it = webConfig.getErrorHandler().iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			Class handlerClz=null;
			if(obj instanceof Map){
				//处理在easyjf-web中定义的异常
				Map ei=(Map)obj;
				Class clz=(Class)ei.get("exception");
				if(clz.isAssignableFrom(error.getClass())){//只有与指定exception的才可以处理
					if(ei.get("class")!=null){
						handlerClz=(Class)ei.get("class");
					}
					else if(StringUtils.hasLength((String)ei.get("path"))){
						String gotoUrl=ei.get("path").toString();
						if(gotoUrl.indexOf(':')<0)gotoUrl=ActionContext.getContext().getRequest().getContextPath()+gotoUrl;
						ActionContext.getContext().getResponse().sendRedirect(gotoUrl);
						return;
					}
				}
				else {
					//直接跳转到下一个
					continue;
				}
			}
			else if(obj instanceof Class){
				handlerClz=(Class)obj;
			}
			if(handlerClz !=null && ExceptionInterceptor.class.isAssignableFrom(handlerClz) && handlerClz!=DefaultExceptionHandle.class){//基于拦截器的异常处理方式
			ExceptionInterceptor ei = (ExceptionInterceptor) FrameworkEngine
					.getContainer().getBean(handlerClz);
			//如果没有在容器中配置，则直接初始化一个
			if(ei==null)ei=(ExceptionInterceptor)handlerClz.newInstance();
			//如果导演处理返回false，则继续下一个处理
			if (ei.handle(error, param != null ? param.getAction() : null,null, new Object[] { param }))
					return;
			}
			
		}
		// 调用最后一个异常处理器，也就是DefaultExceptionHandle，若前面的异常处理没处理这个异常，则该处理器总是执行
		ExceptionInterceptor defaultException = (ExceptionInterceptor) FrameworkEngine
				.getContainer().getBean(DefaultExceptionHandle.class);
		if (defaultException == null)
			defaultException = new DefaultExceptionHandle();
		defaultException.handle(error,
				param != null ? param.getAction() : null, null,
				new Object[] { param });
	}

	// 需要拦截非法输入 比如 ff' and 'fff 避免组合为SQL语句
	// 执行全局拦截操作,全局拦截必须是BeforeInterceptors
	// 还需要改动
	protected void invokeGlobalInterceptors(WebInvocationParam param)
			throws Throwable {
		List apps = webConfig.getInterceptors();
		for (int i = 0; i < apps.size(); i++) {
			Map app = (Map) apps.get(i);
			Method init = (Method) app.get("method");
			if (init != null) {
				init.invoke(app.get("classname"), new Object[] {});
			}
		}
	}

	// 执行前拦截操作,前拦截类型为BeforeInterceptor
	protected void invokeActionBeforeInterceptors(Module module,
			WebInvocationParam param) throws Throwable {
		Iterator interceptors = module.getInterceptors().iterator();
		if (interceptors != null) {
			while (interceptors.hasNext()) {
				Interceptor interceptor = (Interceptor) interceptors.next();
				if (interceptor instanceof BeforeInterceptor)
					((BeforeInterceptor) interceptor).before(param.getAction(),
							null, new Object[] { param });
			}
		}
	}

	// 执行后拦截,后拦截类型为AfterInterceptors
	protected void invokeActionAfterInterceptors(Module module, Page page,
			WebInvocationParam param) throws Throwable {
		Iterator interceptors = module.getInterceptors().iterator();
		if (interceptors != null) {
			while (interceptors.hasNext()) {
				Interceptor interceptor = (Interceptor) interceptors.next();
				if (interceptor instanceof AfterInterceptor)
					((AfterInterceptor) interceptor).after(page, param
							.getAction(), null, new Object[] { param });
			}
		}
	}

	public Page getResult(WebInvocationParam param) throws Exception {
		WebForm form = param.getForm();
		Module module = param.getModule();
		IWebAction action = param.getAction();
		Page page = null;
		try {
			// 执行
			page = action.execute(form, module);
		} catch (Exception e) {
			throw e;
		}
		// 保存表单form中的数据
		// 处理没有定义属性property的表单
		if (form != null && form.getClass() == WebForm.class) {
			form.setProperty(form.getTextElement());// 只保存文本属性
			form.getProperty().putAll(form.getFileElement());
		}
		if (form != null && (form.getProperty() != null)) {
			Iterator it = form.getProperty().keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				form.addResult(name, form.get(name));
			}
		}
		return page;
	}

	protected void doForward(String uri, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(uri);
		rd.forward(request, response);
	}

	protected ServletContext getServletContext() {
		return (servlet.getServletContext());
	}

	public WebConfig getWebConfig() {
		return webConfig;
	}

	public void setWebConfig(WebConfig webConfig) {
		this.webConfig = webConfig;
	}

	public IErrorHandlerManager getErrorHandlerManager() {
		return errorHandlerManager;
	}

	public void setErrorHandlerManager(IErrorHandlerManager errorHandlerManager) {
		this.errorHandlerManager = errorHandlerManager;
	}
}
