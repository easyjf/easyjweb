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
package com.easyjf.web.core.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.exception.FrameworkException;

/**
 * 基础的页面合成支持类，完成了除了最终合成页面以外的所有工作， 如得到模板，创建Velocity上下文对象等 基于Velocity的最终合成模板
 * 
 * @author stefanie_wu,大峡
 * 
 */
abstract public class BasePageVender extends AbstractPageVender {

	private static final Logger logger = Logger.getLogger(BasePageVender.class);

	private Map globalUtils;

	private final static Map templateCache = new HashMap();

	public boolean venderPage(HttpServletRequest request,
			HttpServletResponse response, Page page, WebInvocationParam param) {
		String uri = page.getUrl();
		try {
			Template template =null;			
			if("string".equals(page.getType())&&page.getContent()!=null){				
				template=new MemoryTemplate(page.getContent().getBytes(page.getEncode()),page.getEncode());
				template.process();
			}
			if(template==null)
			template=getTemplate(uri, request.getCharacterEncoding()!=null?request.getCharacterEncoding():"ISO-8859-1");
			if (template != null) {
				Context context = createContext(param.getForm());
				
				
				context.put("session", PageVenderSupport.session2map(request
						.getSession()));
				context.put("request", PageVenderSupport.request2map(request));
				context.put("application", PageVenderSupport.request2map(ActionContext.getContext().getServletContext()));
				if (FrameworkEngine.getValidateManager() != null)
					context.put("errors", FrameworkEngine.getValidateManager()
							.getErrors().getErrors());
				
				//把ActionContext中的数据也放置到结果集中，因此也可以通过ActionContext来往客户端发传递参数
				java.util.Iterator it=ActionContext.getContext().getContextMap().entrySet().iterator();
				while(it.hasNext()){
					Map.Entry en=(Map.Entry) it.next();
					if(!context.containsKey(en.getKey())){
						context.put(en.getKey().toString(), en.getValue());
					}
				}	
				
				return mergeTemplate(template, context, response, page,
						request, param);
			} else {
				throw new FrameworkException(I18n.getLocaleMessage("core.web.template.vender.error"));
			}
		} catch (ResourceNotFoundException rnfe) {
			throw new FrameworkException(I18n.getLocaleMessage("core.web.not.find.tempate")
					+ FrameworkEngine.getWebConfig().getTemplateBasePath()
					+ uri, rnfe);
		} catch (ParseErrorException pee) {
			throw new FrameworkException(I18n.getLocaleMessage("core.web.in.the.template.file.syntax.error.not.the.normal.analytical")
					+ FrameworkEngine.getWebConfig().getTemplateBasePath()
					+ uri, pee);
		} catch (MethodInvocationException mie) {
			throw new FrameworkException(I18n.getLocaleMessage("core.web.template.Method.Invocation.error"), mie);
		} catch (Exception e) {
			throw new FrameworkException(I18n.getLocaleMessage("core.web.find.a.template.error"), e);
		}
	}

	/**
	 * 模板方法，子类需要实现该方法来完成最终的合成页面步骤 返回的boolea值作为最终venderPage方法的返回值
	 * 
	 * @param template
	 *            处理完成的Velocity模板对象
	 * @param context
	 *            处理完成的Velocity上下文对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param page
	 *            处理完成的Page对象
	 * @return
	 */
	abstract protected boolean mergeTemplate(Template template,
			Context context, HttpServletResponse response, Page page,
			HttpServletRequest request, WebInvocationParam param);

	/**
	 * 填充Velocity上下文， 从form的resultMap，并加入公共工具类
	 * 
	 * @param form
	 * @return
	 */
	protected Context createContext(WebForm form) {
		Map result = form.getEasyJWebResult();
		Context context = new VelocityContext();
		for (Iterator it = result.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			context.put(name, result.get(name));
		}
		PageVenderSupport.createUtilContext(context, this.globalUtils);
		return context;
	}

	/**
	 * 根据模板名字和编码完成Velocity模板对象的创建工作
	 * 
	 * @param templateName
	 * @param encoding
	 * @return
	 * @throws ResourceNotFoundException
	 * @throws ParseErrorException
	 * @throws MethodInvocationException
	 * @throws Exception
	 */
	protected Template getTemplate(String templateName, String encoding)
			throws ResourceNotFoundException, ParseErrorException,
			MethodInvocationException, Exception {
		String name = parseTemplateName(templateName);
		if (FrameworkEngine.getWebConfig().isDebug())
			return RuntimeSingleton.getTemplate(name, encoding);// 若为Debug状态，则每次都重新载入模板
		String templatePath = (String) Velocity
				.getProperty("file.resource.loader.path");
		java.io.File f = new java.io.File(new java.io.File(templatePath), name);
		Template template = (Template) templateCache.get(name);// 先从Cache中读取模板文件
		// 这里得进一步完善，当用户已经更改模板文件后，需要能够自动加载，同时增加Cache数量的限制
		if (template == null
				|| (f.exists() && f.lastModified() > template.getLastModified()))
			synchronized (templateCache) {
				{
					logger.info(I18n.getLocaleMessage("core.web.template.file.reloads") + name);
					template = RuntimeSingleton.getTemplate(name, encoding);// name
					templateCache.put(name, template);
				}
			}
		return template;
	}

	private String parseTemplateName(String templateName) {
		String name = templateName;
		if (name.length() > 10) {
			String begin = name.substring(0, 10);
			if (begin.equalsIgnoreCase("classpath:"))
				name = templateName.substring(10);
		}
		String language = I18n.getLocalePrefix(I18n.getLocale());
		String templatePath = (String) Velocity
				.getProperty("file.resource.loader.path");
		java.io.File f = new java.io.File(new java.io.File(templatePath), name);
		String tname = "/" + language + "/" + f.getName();
		java.io.File i18nFile = new java.io.File(f.getParentFile(), tname);
		if (i18nFile.exists()) {
			name = name.substring(0, name.lastIndexOf("/")) + tname;
		}
		return name;
	}
	public class MemoryTemplate extends Template{
		private byte[] content;
		public MemoryTemplate(){
			super();
		}
		public MemoryTemplate(byte[] content,String encoding){
			this.content=content;
			if(encoding!=null)
			this.encoding=encoding;
		}
		@Override
		public boolean process() throws ResourceNotFoundException,
				ParseErrorException, Exception {
				super.rsvc=RuntimeSingleton.getRuntimeServices();
				if(content==null) throw new ResourceNotFoundException("Unknown resource error for resource " + super.name);
				super.data = null;				 
		        InputStream is = new java.io.ByteArrayInputStream(content);
		        if(is != null)
		        {
		            try
		            {
		                BufferedReader br = new BufferedReader(new InputStreamReader(is, super.encoding));
		                super.data = super.rsvc.parse(br, super.name);
		                initDocument();
		                boolean flag = true;
		                return flag;
		            }
		            catch(UnsupportedEncodingException uce)
		            {
		                String msg = "Template.process : Unsupported input encoding : " + super.encoding + " for template " + super.name;
		                throw new ParseErrorException(msg);
		            }
		            catch(ParseException pex)
		            {
		               throw new ParseErrorException(pex.getMessage());
		            }
		            catch(Exception e)
		            {   
		                throw e;
		            }
		            finally
		            {
		                is.close();
		            }
		        } else
		        {
		            throw new ResourceNotFoundException("Unknown resource error for resource " + super.name);
		        }
		}
		
	}
}
