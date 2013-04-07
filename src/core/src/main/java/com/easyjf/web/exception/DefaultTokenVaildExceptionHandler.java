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
package com.easyjf.web.exception;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.interceptor.ExceptionInterceptor;

public class DefaultTokenVaildExceptionHandler implements ExceptionInterceptor {

	private String msg = I18n.getLocaleMessage("core.web.Requests.are.being.processed.or.have.been.dealt.with.please.do.not.repeat.the");

	public final String getMsg() {
		return msg;
	}

	public final void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean handle(Throwable e, Object target, Method method,
			Object[] args) {
		if (TokenVaildException.class.isAssignableFrom(e.getClass())) {
			if (args.length == 1) {
				WebInvocationParam param = (WebInvocationParam) args[0];
				param.getForm().addResult("msg", msg);
				showInfo();
			}
			return true;
		}
		return false;
	}

	public void showInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(msg);
		sb.append("<a href='javascript:history.go(-1);'>");
		sb.append(I18n.getLocaleMessage("core.web.return"));
		sb.append("</a>");
		try {
			ActionContext.getContext().getResponse()
					.setContentType("text/html");
			ActionContext.getContext().getResponse().setCharacterEncoding(
					"UTF-8");
			Writer w = ActionContext.getContext().getResponse().getWriter();
			w.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ActionContext.getContext().getResponse().getWriter().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
