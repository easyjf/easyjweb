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

import javax.servlet.http.HttpServletRequest;

import com.easyjf.container.BeanDefinition;
import com.easyjf.container.Container;
import com.easyjf.container.Scope;
import com.easyjf.container.impl.BeanCreatorUtil;
import com.easyjf.web.ActionContext;

public class RequestScope implements Scope {
	private Container container;
	public RequestScope(Container container)
	{
		this.container=container;
	}
	public Object getBean(String name, BeanDefinition beanDefinition) {
		Object ret=null;
		HttpServletRequest request=ActionContext.getContext().getRequest();
		//Container cotainer=ActionContext.getContext().get
		ret=request.getAttribute(name);
		if(ret==null)
		{
			ret=BeanCreatorUtil.initBean(beanDefinition,container);
			request.setAttribute(name, ret);
		}
		return ret;		
	}
}
