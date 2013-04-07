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
package com.easyjf.web.tools.widget;

import com.easyjf.util.StringUtils;
import com.easyjf.util.TagUtil;
import com.easyjf.web.ActionContext;
import com.easyjf.web.Globals;
import com.easyjf.web.IPathMappingRuler;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.tools.AutoChangeLink;

/**
 * 页面自动载入javascript css 等资源
 * 
 * @author WLR 13:36 2007-6-30
 * 
 */
public class Html {

	public final static String SHOW_HTML_PAGE = "showHtmlPage";
	private static Html singleton = new Html();

	public static Html getInstance() {
		return singleton;
	}

	private Html() {
	}

	public String getToken() {
		String token = (String) ActionContext.getContext().getSession()
				.getAttribute(Globals.TOKEN_NAME);
		if (token == null || "".equals(token)) {
			return null;
		} else {
			return "<input type=\"hidden\" id=\"" + Globals.TOKEN_NAME
					+ "\" name=\"" + Globals.TOKEN_NAME + "\" value=\"" + token
					+ "\" />";
		}
	}

	public String js(String path) {
		String s[] = path.split(",");
		String ret = "";
		for (int i = 0; i < s.length; i++) {
			String f = s[i];
			if (!f.endsWith(".js"))
				f = (new StringBuilder(String.valueOf(f))).append(".js")
						.toString();
			s[i] = f;
			ret = (new StringBuilder(String.valueOf(ret))).append(
					"<script type=\"text/javascript\" src=\"").append(
					this.url(f)).append("\"></script>\r\n").toString();
		}

		return ret;
	}

	public String css(String path) {
		String s[] = path.split(",");
		String ret = "";
		for (int i = 0; i < s.length; i++) {
			String f = s[i];
			if (!f.endsWith(".css"))
				f = (new StringBuilder(String.valueOf(f))).append(".css")
						.toString();
			s[i] = f;
			ret = (new StringBuilder(String.valueOf(ret))).append(
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"")
					.append(this.url(f)).append("\" />\r\n").append(
							"<style type=\"text/css\">\r\n").append(
							"\t@import url(\"").append(this.url(f)).append(
							"\");\r\n").append("</style>\r\n").toString();
		}

		return ret;
	}

	/**
	 * 导入ext支持
	 * 
	 * @return
	 */
	public String extjs2() {
		StringBuffer f = new StringBuffer();
		f.append(css("/plugins/extjs/ext-2.2/resources/css/ext-all.css"))
				.toString();
		f.append(js("/plugins/extjs/ext-2.2/adapter/ext/ext-base.js"))
				.toString();
		f.append(js("/plugins/extjs/ext-2.2/ext-all.js")).toString();
		return f.toString();
	}

	public String extjs2Path() {
		return this.url("/plugins/extjs/ext-2.2");
	}

	/**
	 * 导入ext支持
	 * 
	 * @return
	 */
	public String extjs() {
		StringBuffer f = new StringBuffer();
		f.append(css("/plugins/ext/resources/css/ext-all")).toString();
		f.append(js("/plugins/ext/adapter/ext/ext-base")).toString();
		f.append(js("/plugins/ext/ext-all")).toString();
		f
				.append("<script>Ext.BLANK_IMAGE_URL=\"/plugins/resources/images/default/s.gif\";</script>");
		return f.toString();
	}

	public String tinymce() {
		String f = "";
		f = (new StringBuilder(String.valueOf(f))).append(
				js(this.url("/javascript/tiny_mce/tiny_mce"))).toString();
		// f = (new StringBuilder(String.valueOf(f))).append(
		// js("javascript/tiny_mce/inittinymce")).toString();
		return f;
	}

	/*
	 * <link rel="stylesheet" type="text/css" media="all"
	 * href="../javascript/jscalendar-1.0/calendar-brown.css" title="summer" />
	 * <script type="text/javascript"
	 * src="../javascript/jscalendar-1.0/calendar.js"></script> <script
	 * type="text/javascript"
	 * src="../javascript/jscalendar-1.0/lang/cn_utf8.js"></script> <script
	 * type="text/javascript"
	 * src="../javascript/calendar/initcalendar.js"></script>
	 */
	public String calendar() {
		String f = "";
		f = (new StringBuilder(String.valueOf(f))).append(
				css("javascript/jscalendar-1.0/calendar-brown")).toString();
		f = (new StringBuilder(String.valueOf(f))).append(
				js("javascript/jscalendar-1.0/calendar")).toString();
		f = (new StringBuilder(String.valueOf(f))).append(
				js("javascript/jscalendar-1.0/lang/cn_utf8")).toString();
		f = (new StringBuilder(String.valueOf(f))).append(
				js("javascript/calendar/initcalendar")).toString();
		return f;
	}

	public String showCalendar(String element) {
		return showCalendar(element, "%Y-%m-%d");
	}

	public String showCalendar(String element, String format) {
		String path = this.url("/images/icons/dateselect.gif");
		String s = "<img src=\"" + path
				+ "\" border=\"0\" alt=\"请选择\" onclick=\"return showCalendar('"
				+ element + "', '" + format + "');\" />";
		return s;
	}

	/**
	 * 自动加载easyjweb所需js和css文件到页面
	 */
	public String easyjweb() {
		String f = "";
		f = (new StringBuilder(String.valueOf(f))).append(
				js("/javascript/easyjweb/default")).toString();
		f = (new StringBuilder(String.valueOf(f))).append(
				css("/stylesheet/easyjweb/default")).toString();
		return f;
	}

	public String ajaxValidate(String name) {
		String ret = "";
		ret += " onsubmit=\"return function(){alert('执行ajax验证');return true;}();\"";
		return ret;
	}

	public String url(String url) {
		ActionContext context = ActionContext.getContext();
		if (TagUtil.verifyUrl(url))
			return url;
		String ret = url;
		try {

			if (ret.charAt(0) == '/')
				ret = ret.substring(1);
			if (context != null) {
				if (context.getWebInvocationParam() != null
						&& !IPathMappingRuler.CLASSIC_PATTERN.equals(context
								.getWebInvocationParam().getUrlType()))
					ret = context.getRequest().getContextPath() + "/"
							+ context.getWebInvocationParam().getSuffix() + ret;
				else
					ret = context.getRequest().getContextPath() + "/" + ret;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ret;
	}

	public String forward(String command) {
		ActionContext context = ActionContext.getContext();
		String cmd = command, ret = "";
		if (context != null) {
			WebInvocationParam param = context.getWebInvocationParam();
			String module = param.getModule().getPath();
			if (command.indexOf('.') > 0)// 若包含.格式的跳转，则为module.command的形式
			{
				module = "/" + command.substring(0, command.indexOf('.'));
				cmd = command.substring(command.indexOf('.') + 1);
			}
			Object showHtmlPage = param.getForm().get(SHOW_HTML_PAGE);
			if (module.charAt(0) != '/')
				module = "/" + module;
			if ("true".equals(showHtmlPage)) {
				ret = module + "/" + cmd + ".html";
			} else {
				if (IPathMappingRuler.CLASSIC_PATTERN
						.equals(param.getUrlType())) {
					ret = context.getRequest().getContextPath() + module + "."
							+ param.getSuffix() + "?cmd=" + cmd;
				} else
					ret = context.getRequest().getContextPath() + "/"
							+ param.getSuffix() + module + "/" + cmd;
			}
		}
		return ret;
	}

	public String autoLink(AutoChangeLink obj) {
		return url(isShowHtmlPage() ? obj.getStaticUrl() : obj.getDynamicUrl());

	}

	/**
	 * 用来自动处理基于具有分页列表的html连接
	 * 
	 * @param url
	 *            形如[module.comand/sn=mvc/1.]这种格式的字符串
	 * @return
	 */
	public String autoLink(String url) {
		String suffix = "";
		String p = isShowHtmlPage() ? handleModuleCmdHtml(url)
				: handleModuleCmdUrl(url);
		if (isShowHtmlPage()) {
			p = "/" + p;
			suffix = ".html";
		} else {
			String mark = p.indexOf('?') >= 0 ? "&currentPage="
					: "?currentPage=";
			p += mark;
		}
		if (url.charAt(url.length() - 2) == '.')
			p = p + "1" + suffix;
		return url(p);
	}

	public boolean isShowHtmlPage() {
		boolean ret = false;
		ActionContext context = ActionContext.getContext();
		if (context != null) {
			WebInvocationParam param = context.getWebInvocationParam();
			ret = "true".equals(param.getForm().get(SHOW_HTML_PAGE));
		}
		return ret;
	}

	/*
	 * [news.tutorial]对应news.ejf?cmd=tutorial 对应的静态文件为news/tutorial/xx.htm
	 * [news.
	 * list/dirSn.type]=news.ejf?cmd=list&dirSn=WebForm.get(dirSn)&type=WebForm
	 * .get(type) 对应的静态文件为news/list/WebForm.get(dirSn)/WebForm.get(type)
	 */
	public String handleModuleCmdUrl(String ms) {
		String u = ms;
		if (u.charAt(0) != '[')
			return ms;
		String[] ps = ms.substring(1, ms.length() - 1).split("/");// 去除前后字符，然后按/进行分割
		String module_cmd = ps[0];
		if (module_cmd.indexOf(".") > 0) {
			String module = module_cmd.substring(0, module_cmd.indexOf("."));
			String cmd = module_cmd.substring(module_cmd.indexOf(".") + 1);
			module_cmd = module + "." + Globals.MAPPING_SUFFIX + "?cmd=" + cmd
					+ "&";
		} else
			module_cmd += "." + Globals.MAPPING_SUFFIX + "?";
		ActionContext context = ActionContext.getContext();
		if (ps.length > 1 && !ps[1].endsWith(".")) {
			String[] params = ps[1].split("\\.");
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					module_cmd += params[i];
					if (context != null) {
						WebInvocationParam param = context
								.getWebInvocationParam();
						if (param != null && param.getForm() != null) {
							Object obj = param.getForm().get(params[i]);
							if (obj != null)
								module_cmd += "=" + obj;
						}
					}
					module_cmd += "&";
				}
			}
		}
		return module_cmd;
	}

	/*
	 * [news.tutorial]对应news.ejf?cmd=tutorial 对应的静态文件为news/tutorial/xx.htm
	 * [news.
	 * list/dirSn.type]=news.ejf?cmd=list&dirSn=WebForm.get(dirSn)&type=WebForm
	 * .get(type) 对应的静态文件为news/list/WebForm.get(dirSn)/WebForm.get(type)
	 */
	public String handleModuleCmdHtml(String ms) {
		String u = ms;
		if (u.charAt(0) != '[')
			return ms;
		String[] ps = ms.substring(1, ms.length() - 1).split("/");
		String module_cmd = ps[0];
		if (module_cmd.indexOf(".") > 0) {
			String module = module_cmd.substring(0, module_cmd.indexOf("."));
			String cmd = module_cmd.substring(module_cmd.indexOf(".") + 1);
			module_cmd = module + "/" + cmd + "/";
		} else
			module_cmd += "/";
		ActionContext context = ActionContext.getContext();
		if (ps.length > 1 && !ps[1].endsWith(".")) {
			String[] params = ps[1].split("\\.");
			if (params != null && params.length > 0) {
				for (int i = 0; i < params.length; i++) {
					if (context != null) {
						WebInvocationParam param = context
								.getWebInvocationParam();
						String[] pars = params[i].split("=");
						if (pars.length > 1)
							module_cmd += pars[1];
						else {
							Object obj = param.getForm().get(pars[0]);
							if (obj != null)
								module_cmd += obj;
							else
								module_cmd += pars[0];
						}
					}
					module_cmd += "/";
				}
			}
		}
		return module_cmd;
	}

	/**
	 * 根据URL字符串从中解析出需要生成的最大页面数量
	 * 
	 * @param ms
	 *            URL字符串如：[news.frontList/sn=ly/1.]
	 * @return
	 */
	public Integer handleModuleCmdMaxPage(String ms) {
		Integer ret = -1;
		String u = ms;
		if (u.charAt(0) != '[') {
			return ret;
		}
		String[] ps = ms.substring(1, ms.length() - 1).split("/");
		if (ps.length > 1) {
			String f = ps[ps.length - 1];
			if (f.endsWith("."))
				f = f.substring(0, f.length() - 1);
			try {
				ret = Integer.parseInt(f);
			} catch (NumberFormatException e) {
			}
		}
		return ret;
	}

	public String pageScript() {
		StringBuilder sb = new StringBuilder();
		sb
				.append("<script type=\"text/javascript\" src=\"/ejf/easyajax/prototype\"></script>");
		sb.append("<script language='javascript'>");
		sb.append("function gotoPage(n){");
		sb.append("$('ListForm').currentPage.value=n;");
		sb.append("$('ListForm').submit();");
		sb.append("}");
		sb.append("</script>");
		return sb.toString();
	}

	public String pageForm(String action) {
		StringBuilder sb = new StringBuilder();
		sb
				.append("<form name=\"ListForm\" id=\"ListForm\" method=\"post\" action=\""
						+ action + "\">");
		sb.append("<input type=\"hidden\" name=\"currentPage\"   value=\"\"/>");
		sb.append("</form>");
		return sb.toString();
	}

	public String pageation(String currentPages, String pagess) {
		int currentPage = StringUtils.hasText(currentPages) ? Integer
				.valueOf(currentPages) : 1;
		int pages = StringUtils.hasText(pagess) ? Integer.valueOf(pagess) : 1;
		return showPageHtml(currentPage, pages);
	}

	public String pageation(String currentPages, String pagess, String url) {
		try {
			int currentPage = StringUtils.hasText(currentPages) ? Integer
					.valueOf(currentPages) : 1;
			int pages = StringUtils.hasText(pagess) ? Integer.valueOf(pagess)
					: 1;
			return showPageHtmlByUrl(currentPage, pages, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	private String showPageHtml(int currentPage, int pages) {
		String s = "";
		if (currentPage > 1) {
			s += "<a href=# onclick='return gotoPage(1)'>首页</a> ";
			s += "<a href=# onclick='return gotoPage(" + (currentPage - 1)
					+ ")'>上一页</a> ";
		}
		int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			s += "第　";
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++) {
				if (i == currentPage)
					s += "<font color=red>" + i + "</font> ";
				else
					s += "<a href=# onclick='return gotoPage(" + i + ")'>" + i
							+ "</a> ";
			}
			s += "页　";
		}
		if (currentPage < pages) {
			s += "<a href=# onclick='return gotoPage(" + (currentPage + 1)
					+ ")'>下一页</a> ";
			s += "<a href=# onclick='return gotoPage(" + pages + ")'>末页</a> ";
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/*
	 * [news.tutorial]对应news.ejf?cmd=tutorial 对应的静态文件为news/tutorial/xx.htm
	 * [news.
	 * list/dirSn.type]=news.ejf?cmd=list&dirSn=WebForm.get(dirSn)&type=WebForm
	 * .get(type) 对应的静态文件为news/list/WebForm.get(dirSn)/WebForm.get(type)
	 */
	private String showPageHtmlByUrl(int currentPage, int pages, String url) {
		String s = "", suffix = "";
		Html htmlTool = Html.getInstance();
		String p = htmlTool.isShowHtmlPage() ? htmlTool
				.handleModuleCmdHtml(url) : htmlTool.handleModuleCmdUrl(url);
		if (htmlTool.isShowHtmlPage()) {
			p = "/" + p;
			suffix = ".html";
		} else {
			String mark = p.indexOf('?') >= 0 ? "&currentPage="
					: "?currentPage=";
			p += mark;
		}
		if (currentPage > 1) {
			s += "<a href=" + p + "1" + suffix + ">首页</a> ";
			s += "<a href=" + p + (currentPage - 1) + suffix + ">上一页</a> ";
		}
		int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			s += "第　";
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++) {
				if (i == currentPage)
					s += "<font color=red>" + i + "</font> ";
				else
					s += "<a href=" + p + i + suffix + ">" + i + "</a> ";
			}
			s += "页　";
		}
		if (currentPage < pages) {
			s += "<a href=" + p + (currentPage + 1) + suffix + ">下一页</a> ";
			s += "<a href=" + p + pages + suffix + ">末页</a> ";
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	public static void main(String arg[]) {
		Html html = Html.getInstance();
		System.out.println(html.handleModuleCmdUrl("[news.list/dirSn/type]"));
		// System.out.println(html.extjs());
	}
}