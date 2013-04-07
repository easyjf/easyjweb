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
package com.easyjf.web.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.container.annonation.FormPO;
import com.easyjf.util.CommUtil;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.tools.annotations.After;
import com.easyjf.web.tools.annotations.Before;
import com.easyjf.web.tools.annotations.Do;
/**
 * @deprecated
 * 
 *
 */
public abstract class AbstractCmdAAction implements IWebAction {
	private Method init;

	private List<Method> before;

	private List<Method> after;

	private List<Field> cmds = new ArrayList<Field>();

	private Map<String, Method> actions = new HashMap<String, Method>();

	private ThreadLocal<Boolean> ret = new ThreadLocal<Boolean>();

	public Page execute(WebForm form, Module module) throws Exception {
		if (this.ret.get() == null || !this.ret.get()) {
			this.registerAction();
		}
		for (Field f : this.cmds)
			this.processForm2Po(form, f);
		if (before != null) {
			for (Method m : before) {
				Object beforeCheck = m.invoke(this, form, module); // 模版方法
				if (beforeCheck != null) {
					if (beforeCheck.getClass() == Page.class)
						return (Page) beforeCheck;
				}
			}
		}
		Page forward = null;
		String command = CommUtil.null2String(form.get("easyJWebCommand"));
		if (!"".equals(command)) {
			Method m = this.actions.get(command);
			if (m != null) {
				Object ret = m.invoke(this, form, module);
				if (ret instanceof Page) {
					forward = (Page) ret;
				}
			}
		} else {
			if (this.init != null) {
				forward = (Page) init.invoke(this, form, module);
			}
		}
		if (this.after != null) {
			for (Method m : after)
				m.invoke(this, form, module);
		}
		return forward;
	}

	private void processForm2Po(WebForm form, Field f) throws Exception {
		FormPO fpo = f.getAnnotation(FormPO.class);
		String inject = fpo.inject();
		String disInject = fpo.disInject();
		if ("".equals(inject) && "".equals(disInject) || !"".equals(inject)
				&& !"".equals(disInject)) {
			form.toPo(f.get(this));
		} else {
			List<String> injects = Arrays.asList(inject.split(","));
			List<String> disInjects = Arrays.asList(disInject.split(","));
			if (injects.size() > 0 && disInjects.size() == 1
					&& disInjects.get(0).equals("")) {
				for (Field fs : f.getType().getDeclaredFields()) {
					if (injects.contains(fs.getName())) {
						fs.setAccessible(true);
						fs.set(f.get(this), CommUtil.null2String(form.get(fs
								.getName())));
					}
				}
			} else if (injects.size() == 1 && injects.get(0).equals("")
					&& disInjects.size() > 0) {
				for (Field fs : f.getType().getDeclaredFields()) {
					if (!disInjects.contains(fs.getName())) {
						fs.setAccessible(true);
						fs.set(f.get(this), CommUtil.null2String(form.get(fs
								.getName())));
					}
				}
			}
		}
	}

	private void registerAction() {
		try {
			for (Field f : this.getClass().getDeclaredFields()) {
				FormPO q = f.getAnnotation(FormPO.class);
				if (q != null) {
					Class c = f.getType();
					f.setAccessible(true);
					f.set(this, c.newInstance());
					this.cmds.add(f);
				}
			}
			for (Method m : this.getClass().getDeclaredMethods()) {
				Do ann = m.getAnnotation(Do.class);
				if (ann != null) {
					String method = ann.value();
					if (method != null && !"".equals(method))
						this.actions.put(ann.value(), m);
					else
						this.actions.put(m.getName(), m);
					continue;
				}
				if (m.isAnnotationPresent(Before.class)) {
					if (before == null) {
						before = new ArrayList<Method>();
					}
					this.before.add(m);
					continue;
				}
				if (m.isAnnotationPresent(After.class)) {
					if (after == null) {
						after = new ArrayList<Method>();
					}
					this.after.add(m);
					continue;
				}
			}
			if (before != null && before.size() > 0) {
				Collections.sort(before, new Comparator<Method>() {
					public int compare(Method m1, Method m2) {
						Before b1 = m1.getAnnotation(Before.class);
						Before b2 = m2.getAnnotation(Before.class);
						return b1.value() - b2.value();
					}
				});
			}
			if (after != null && after.size() > 0) {
				Collections.sort(after, new Comparator<Method>() {
					public int compare(Method m1, Method m2) {
						After a1 = m1.getAnnotation(After.class);
						After a2 = m2.getAnnotation(After.class);
						return a1.value() - a2.value();
					}
				});
			}
			this.ret.set(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
