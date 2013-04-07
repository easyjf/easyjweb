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
package com.easyjf.web.components;

import com.easyjf.container.annonation.InjectDisable;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Page;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.core.AbstractPageCmdAction;
/**
 * 
 * @author 大峡
 *
 */
public abstract class RichComponentAction extends AbstractPageCmdAction {
	@InjectDisable
	protected static final Page  componentPage=new Page("blank",
	"classpath:com/easyjf/web/components/component.html");
	protected Page componentPage() {
		Page page =componentPage;
		this.forwardPage=page;
		return page;
	}

	protected void addComponent(Object component) {
		WebInvocationParam param = ActionContext.getContext()
				.getWebInvocationParam();
		if (param != null && param.getForm() != null) {
			param.getForm().addComponent(component);
		}
	}

	protected void addComponents(Object... components) {
		if (components != null) {
			for (Object c : components) {
				addComponent(c);
			}
		}
	}
}
