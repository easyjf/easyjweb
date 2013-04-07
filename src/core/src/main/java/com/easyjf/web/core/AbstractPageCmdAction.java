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

import com.easyjf.web.Globals;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;

/**
 * 用来支持更加灵活的参数调用方式。
 * 
 * @author 大峡
 * 
 */

abstract public class AbstractPageCmdAction extends AbstractCmdAction {
	public Page execute(WebForm form, Module module) throws Exception {
		Page forward = super.execute(form, module);
		if (forward == null && !"".equals(this.command)) {
			forward = this.forwardPage;
			if (forward == null)
				forward = module.findPage(this.command);
			if (forward == null) {
				String templateUrl = module.getViews() + module.getPath() + "/"
						+ this.command;
				if (this.getTemplatePerfix() != null)
					templateUrl = this.getTemplatePerfix() + templateUrl;
				String ext = getTemplateExt();
				if (ext.charAt(0) == '.')
					ext = ext.substring(1);
				forward = new Page(this.getClass() + this.command, templateUrl
						+ "." + ext);
			}			
		}			
		reset();
		return forward;
	}

	protected String getTemplateExt() {
		return Globals.DEFAULT_TEMPLATE_EXT;
	}

	/**
	 * 设置模板路径前缀
	 * 
	 * @return
	 */
	protected String getTemplatePerfix() {
		return "/";
	}

	public Page doInit(WebForm form, Module module) {
		return forward("index");
	}
}
