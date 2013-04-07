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

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;

import com.easyjf.web.ActionContext;
import com.easyjf.web.Page;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.core.FrameworkEngine;

public class DefaultPageVender extends BasePageVender {

	private static final Logger logger = Logger.getLogger(DefaultPageVender.class);

	private final static SimplePool writerPool = new SimplePool(60);

	protected boolean mergeTemplate(Template template, Context context,
			HttpServletResponse response, Page page,
			HttpServletRequest request, WebInvocationParam param) {
		VelocityWriter vw = null;
		Writer writer = null;
		try {			
			response.setCharacterEncoding(template.getEncoding());
			response.setContentType(page.getContentType());
			writer = ActionContext.getContext().getCustomWriter();// 首先判断是否重定向了writer
			if (writer == null)
				writer = FrameworkEngine.getResponseWriter(response);
			vw = (VelocityWriter) writerPool.get();
			if (vw == null) {
				vw = new VelocityWriter(writer, 4 * 1024, true);
			} else {
				vw.recycle(writer);
			}
			template.merge(context, vw);			
		} catch (Exception e) {
			logger.error(e);
			// throw new FrameWorkException("合并模块错误！",e);
		} finally {
			if (vw != null) {
				try {
					vw.flush();
					vw.recycle(null);
					writerPool.put(vw);
					writer.close();
				} catch (Exception e) {
					logger.error("Trouble releasing VelocityWriter: "
							+ e.getMessage());
				}
			}
		}
		return true;
	}

	/**
	 * EasyJWeb中使用Velocity模板引擎来处理所有页面的输出。
	 */
	@Override
	public boolean supports(String suffix) {
		return true;
	}

}
