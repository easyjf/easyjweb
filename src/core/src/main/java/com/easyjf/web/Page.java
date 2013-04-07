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
package com.easyjf.web;

/**
 * 
 * <p>
 * Title:视图模板页面/转向
 * </p>
 * <p>
 * Description:页面type主要包括两种，一种是模板文件(template)，另外一种是直接跳转地址(html)
 * 还有一种是带环境跳转(action) 2007-9-5 增加一种(forward)类型的跳转，用于支持缓存
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友 郭朝斌
 * @version 1.0
 */

public class Page {
	/**
	 * 页面名称
	 */
	private String name = "";

	/**
	 * 页面路径
	 */
	private String url = "";

	/**
	 * html/template/action/null/string 模版用于执行合并,html用于直接跳转,action用于在模块跳转,string表示字符串作为模板
	 */
	private String type = "";

	/**
	 * 模板内容
	 */
	private String content;
	/**
	 * 模板编码
	 */
	private String encode="UTF-8";
	/**
	 * 用来指定模板的输出类型，可以包含wml、js、html、json、xml、txt等各种内容。
	 */
	private String contentType = "html";// 

	/**
	 * 用于表示不提供任何反馈的空结果集。当得到类型为null的Page时，EasyJWeb将不会给客户端返回任何结果。
	 */
	public final static Page nullPage = new Page("nullPage", "", "null");
	public final static Page JSONPage = new Page("JSONPage","classpath:com/easyjf/web/ajax/jsonobject.txt");
	
	static{
		JSONPage.setContentType("js");
	}
	
	public Page() {
		this("", "");
	}

	/**
	 * 创建一个类型为template的 Page对象 如果没有指定Page类型，则系统默认为template模板
	 * 
	 * @param name
	 *            视图的逻辑名称
	 * @param url
	 *            视图页面模板的路径
	 */
	public Page(String name, String url) {
		this(name, url, Globals.PAGE_TEMPLATE_TYPE);
	}

	/**
	 * 创建一个类型为template的 Page对象 如果没有指定Page类型，则系统默认为template模板 模板名为url文件名
	 * 
	 * @param url
	 *            视图页面模板的路径
	 */
	public Page(String url) {
		String name = url;// "";
		/*
		 * if (url.indexOf("/") > 0 || url.indexOf("\\") > 0) {
		 * url.replace('\\','/'); name = url.substring(url.lastIndexOf("/") +
		 * 1); name = name.substring(0, url.lastIndexOf(".")); }
		 */
		this.name = name;
		this.url = url;
		this.type = Globals.PAGE_TEMPLATE_TYPE;
	}

	/**
	 * 根据所传递的参数创建一个Page对象
	 * 
	 * 
	 * @param name
	 *            视图模板的逻辑名称
	 * @param url
	 *            视图模板的路径
	 * @param type
	 *            视图类型，包括template及html两种
	 */
	public Page(String name, String url, String type) {
		this.name = name;
		this.url = url;
		this.type = type;
	}
	public Page(String name, String url, PageType type) {
		this.name = name;
		this.url = url;
		this.type=type.toString();
	}

	/**
	 * 每一个视图模板均有一个逻辑名称，在必要的时候，EasyJWeb会把这模板缓存起来，从而提交模板加载的效率，模板缓存正是通过这个name来进行的。
	 * 视图模板的逻辑名称一般为字母、数字及下划线组合
	 * 
	 * @return 视图的逻辑名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置当前视图的逻辑名称
	 * 
	 * @param name
	 *            视图的逻辑名称
	 */
	public void setName(String name) {
		this.name = name == null ? "" : name;
	}

	/**
	 * 视图的类型，EasyJWeb中的视图类型有模板template及页面转向html两种。在以后的版本中将会提供更多的支持。
	 * 
	 * @return 视图的类型，若为模板页面则为template，若为页面转向，则为html
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置视图的类型
	 * 
	 * @param type
	 *            视图类型，template表示模板,html表示页面转向。
	 */
	public void setType(String type) {
		this.type = type == null ? "" : type;
	}

	/**
	 * 视图的路径。EasyJWeb中的视图主要有两种，该值分别如下： 1、模板template即type的值为template时
	 * EasyJWeb中的视图默认存放在/WEB-INF/views/目录下。这一路径是指从基路径（即默认路径)开始的路径。
	 * 也可以从classpath或jar中调用模板，这时url为形如classpath:com/easyjf/web/easyjf-web.xml的格式。
	 * 2、页面转向html即type的值为html时
	 * 此时url返回的是一个真实的URL路径，即可是是形如http://www.easyjf.com/easyjweb/这样以http或其它协议形式开头的url，也可以是/news.ejf?dirId=111类似这种站内url
	 * 
	 * @return 得到视图的路径
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置视图的路径
	 * 
	 * @param url
	 *            视图的路径
	 */
	public void setUrl(String url) {
		this.url = url == null ? "" : url;
	}

	/**
	 * 视图模板的内容类型。
	 * EasyJWeb的视图模板可以是多种模板，比如，相对于文本格式的模板来说，包括json、wml、html、xml...等。另外还可以生成其它的标签的ContentType.
	 * 
	 * @return 当页视图的内容类型
	 */
	public String getContentType() {
		return parseContentType(contentType);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	private String parseContentType(String name) {
		if ("js".equalsIgnoreCase(name))
			return "text/javascript;charset=UTF-8";
		else if("json".equalsIgnoreCase(name))
		{
			return "application/x-json;charset=UTF-8";
		} else if ("wml".equalsIgnoreCase(name))
			return "text/vnd.wap.wml;charset=UTF-8";

		else if ("xml".equalsIgnoreCase(name))
			return "text/xml;charset=UTF-8";
		else {
			if (name.indexOf("/") > 0)// 在name上包含/，手动指定模板内容如text/xml;charset=GBK
				return name;
			else
				return "text/html;charset=UTF-8";// 其它的都直接返回html
		}
	}

	/**
	 * 设置视图的内容类型 可以调用的类型逻辑名有json,wml,js,xml,html,htm,txt
	 * wml、js、html、json、xml、txt等。
	 * 也可以直接使用完整的文档类型定义，如text/javascript;charset=UTF-8
	 * 默认值为text/html;charset=UTF-8
	 * 
	 * @param contentType
	 *            模板类型名称或具体值
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Page的详细信息 返回Page属性字符串
	 */
	public String toString() {
		return "name=" + name + ";url=" + url + ";type=" + type;
	}
}
