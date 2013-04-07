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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.easyjf.container.annonation.WebCache;
import com.easyjf.util.CommUtil;
import com.easyjf.util.I18n;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Globals;
import com.easyjf.web.IRequestCallback;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.WebInvocationParam;

/**
 * 用于管理页面缓存信息，包括url地址对应信息等。
 * 
 * @author 大峡
 * 
 */
public class WebCacheManager {
	private final static WebCacheManager singleton = new WebCacheManager();

	public static WebCacheManager getInstance() {
		return singleton;
	}

	public final List urls = new java.util.ArrayList();

	public Page handleCache(WebInvocationParam webParam, WebCache cache) {
		Page ret = null;
		String cacheUrl = getUrl(webParam, cache);
		File f = new File(Globals.APP_BASE_DIR + cacheUrl);
		// System.out.println("执行cache:"+f.getAbsolutePath());
		Object refreshWebCache = webParam.getForm().get("refreshWebCache");
		if (!urls.contains(cacheUrl)) {
			if (f.exists()
					&& (refreshWebCache == null)
					&& (System.currentTimeMillis() - f.lastModified() < 1000 * cache
							.timeout())) {
				// System.out.println("从cache返回数据");
				ret = new Page("cache", cacheUrl, "forward");
			} else {
				try {
					if (!f.getParentFile().exists())
						f.getParentFile().mkdirs();
					java.io.Writer writer = new OutputStreamWriter(
							new FileOutputStream(f), "UTF-8");
					ActionContext.getContext().setCustomWriter(writer);
					ActionContext.getContext().setUri(cacheUrl);
					urls.add(cacheUrl);
					// System.out.println("准备生成缓丰:"+urls.size());
					ActionContext.getContext().setRequestCallback(
							new CacheFinishCallback(cacheUrl));
					// Thread.sleep(5000l);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ret=new Page("action",cache)
			}
		}
		return ret;
	}

	public String getUrl(WebInvocationParam webParam, WebCache cache) {
		Module module = webParam.getModule();
		WebForm form = webParam.getForm();
		String command = CommUtil.null2String(form.get("easyJWebCommand"));
		if ("".equals(command))
			command = CommUtil.null2String(form.get("cmd"));
		if ("".equals(command))
			command = "index";
		String[] params = cache.params();
		String fileName = command;
		for (int i = 0; i < params.length; i++) {
			if (!"CMD".equals(params[i])) {
				String v = CommUtil.null2String(form.get(params[i]));
				fileName += "-" + params[i] + "-" + v;
			}
		}
		String cacheUrl = "/html/cache/" + module.getPath() + "/" + fileName
				+ ".html";
		return cacheUrl;
	}

	public class CacheFinishCallback implements IRequestCallback {
		private String url;

		CacheFinishCallback(String url) {
			this.url = url;
		}

		public void doFinish() {
			if (url != null)
				urls.remove(url);
			System.out.println(I18n
					.getLocaleMessage("core.web.generate.complete.cache")
					+ urls.size());
		}
	}
}
