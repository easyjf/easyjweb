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

import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.interceptor.IRequestInterceptor;

/**
 * 用户入侵检测载器
 * 
 * @author 大峡
 * 
 */ 
public class UserConnectInterceptor implements IRequestInterceptor {
	public Object doIntercept() throws Exception {
		//System.out.println(I18n.getLocaleMessage("core.web.starting.application"));
		if(ActionContext.getContext().getRequest()!=null){
			if (!UserConnectManage.checkLoginValidate(ActionContext.getContext()
					.getRequest().getRemoteAddr(), "guest")) {
				//System.out.println(I18n.getLocaleMessage("core.web.refresh.your.pages.too.soon.please.wait"));			
				throw new Exception(I18n.getLocaleMessage("core.web.refresh.your.pages.too.soon.please.wait")
						+ UserConnectManage.getWaitInterval() / 1000 + I18n.getLocaleMessage("core.web.seconds.later.refresh.pages"));		
			}
		}
		return IRequestInterceptor.SUCCESS;
	}
}
