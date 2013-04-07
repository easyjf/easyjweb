/*
 * Copyright 2002-2008 the original author or authors.
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
package com.easyjf.web.ajax;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.easyjf.util.StringUtils;
import com.easyjf.util.XmlElementUtil;
import com.easyjf.web.config.ConfigManager;

/**
 * Ajax配置信息管理器
 * 
 * @author 大峡
 * 
 */
public class AjaxConfigManager implements ConfigManager {
	private Set allowNames = new HashSet();// 合法的服务名集合

	private Set denyNames = new HashSet();// 限制的服务名集合

	private Map services = new HashMap(); // 服务定义集合

	private Map convertBeans = new HashMap();// 转换Bean集合

	private Map signatures = new HashMap();// 针对方法的签名

	private static AjaxConfigManager singleton = new AjaxConfigManager();

	private static final Logger logger =org.apache.log4j.Logger.getLogger(AjaxConfigManager.class
			.getName());
	public static AjaxConfigManager getInstance() {
		return singleton;
	}

	public void parseConfig(Document doc) {
		if (doc == null)
			return;
		Element root = XmlElementUtil.findElement("ajax", doc.getRootElement());
		Element maxDepth=XmlElementUtil.findElement("json-max-depth", root);
		if(maxDepth!=null){
			try{
			Integer depth=new Integer(maxDepth.getText());
			AjaxUtil.JSON_OBJECT_MAX_DEPTH=depth;//设置JSON对象转换的最大深度
			}
			catch(NumberFormatException e){
			}
		}
		Element serviceRoot = XmlElementUtil.findElement("services", root);
		if (serviceRoot != null) {
			parseServices(serviceRoot);
		}

		List converts = XmlElementUtil.findElements("convert", root);
		if (converts != null) {
			parseConvert(converts);
		}
		Element signatures = XmlElementUtil.findElement("signatures", root);
		if (signatures != null) {
			parseSignatures(signatures);
		}
	}

	public void parseServices(Element serviceRoot) {
		String allowName = serviceRoot.attributeValue("allowName");
		if (allowName != null) {
			String[] names = allowName.split(",");
			for (int i = 0; i < names.length; i++) {
				allowNames.add(formatRegx(names[i]));
			}
		}
		String denyName = serviceRoot.attributeValue("denyName");
		if (denyName != null) {
			String[] names = denyName.split(",");
			for (int i = 0; i < names.length; i++) {
				denyNames.add(formatRegx(names[i]));
			}
		}
		List list = XmlElementUtil.findElements("service", serviceRoot);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Element service = (Element) list.get(i);
				RemoteService remote = new RemoteService();
				String name = service.attributeValue("name");
				if (StringUtils.hasText(name)) {
					remote.setName(name);
					// 处理include属性
					String attrInclude = service.attributeValue("include");
					if (attrInclude != null) {
						String[] is = StringUtils.tokenizeToStringArray(
								attrInclude, ",");
						if (is != null) {
							for (int t = 0; t < is.length; t++)
								remote.addAllowName(formatRegx(is[t]));
						}
					}
					// 处理indlue节点
					List includes = XmlElementUtil.findElements("include",
							service);
					if (includes != null) {
						for (int j = 0; j < includes.size(); j++) {
							Element e = (Element) includes.get(j);
							remote.addAllowName(formatRegx(e
									.attributeValue("method")));
						}
					}
					String attrExclude = service.attributeValue("exclude");
					if (attrExclude != null) {
						String[] is = StringUtils.tokenizeToStringArray(
								attrExclude, ",");
						if (is != null) {
							for (int t = 0; t < is.length; t++)
								remote.addDenyName(formatRegx(is[t]));
						}
					}
					List excludes = XmlElementUtil.findElements("exclude",
							service);
					if (excludes != null) {
						for (int j = 0; j < excludes.size(); j++) {
							Element e = (Element) excludes.get(j);
							remote.addDenyName(formatRegx(e
									.attributeValue("method")));
						}
					}
					allowNames.add(formatRegx(remote.getName()));
					services.put(remote.getName(), remote);
				}
			}
		}
	}

	public void parseConvert(List list) {
		for (int i = 0; i < list.size(); i++) {
			Element convert = (Element) list.get(i);
			String name = convert.attributeValue("name");
			RemoteService remote = new RemoteService();
			if (StringUtils.hasText(name)) {
				remote.setName(name);
				List includes = XmlElementUtil.findElements("include", convert);
				if (includes != null) {
					for (int j = 0; j < includes.size(); j++) {
						Element e = (Element) includes.get(j);
						remote.addAllowName(formatRegx(e
								.attributeValue("property")));
					}
				}
				List excludes = XmlElementUtil.findElements("exclude", convert);
				if (excludes != null) {
					for (int j = 0; j < excludes.size(); j++) {
						Element e = (Element) excludes.get(j);
						remote.addDenyName(formatRegx(e
								.attributeValue("property")));
					}
				}
			}
			convertBeans.put(remote.getName(), remote);
		}
	}

	public void parseSignatures(Element serviceRoot) {
		String[] value=StringUtils.tokenizeToStringArray(serviceRoot.getText().trim(),";");
		if(value!=null)
		{
			for(int i=0;i<value.length;i++)
			{
				String line=value[i].trim();
				int b=line.indexOf("(");
				String name=line.substring(0,b);
				String v=line.substring(b+1,line.lastIndexOf(")"));		
				String[] pas=StringUtils.tokenizeToStringArray(v,",");
				if(pas!=null)
				{
					Class[] clzs=new Class[pas.length];
					for(int j=0;j<pas.length;j++)
					{
					try{
					clzs[j]=Class.forName(pas[j].trim());
					}
					catch(java.lang.ClassNotFoundException e)
					{
						logger.error("load ajax signatures config errors:"+e.getMessage());
						continue;
					}
					}
					this.signatures.put(name,clzs);
				}
			}
		}
	}

	private String formatRegx(String reg) {
		if (!StringUtils.hasText(reg))
			return "";
		String name = reg;
		if (name.charAt(0) == '*')
			name = "." + name;
		return name;
	}

	public Set getAllowNames() {
		return allowNames;
	}

	public Map getConvertBeans() {
		return convertBeans;
	}

	public Set getDenyNames() {
		return denyNames;
	}

	public Map getServices() {
		return services;
	}

	public Map getSignatures() {
		return signatures;
	}
}
