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
package com.easyjf.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.easyjf.beans.BeanWrapper;

/**
 * 用于生成并处理各种各样标签的工具类
 * 
 * @author 大峡
 * 
 */
public class TagUtil {

	private static TagUtil singleton = new TagUtil();

	public static String checkBox(final Object value) {
		String ret = "";
		if (value instanceof Boolean)
			if (((Boolean) value).booleanValue())
				ret = "checked";
		return ret;
	}

	public static String checkBox(final String obj, final Object value) {
		String ret = "";
		if (obj != null && obj.equals(value))
			ret = "checked";
		return ret;
	}

	public static TagUtil getInstance() {
		return TagUtil.singleton;
	}

	/**
	 * @param currentPage
	 * @param pages
	 * @return 101207 add the miniPagination Mini 分页 current page / total page
	 *         pages ... turn to page |< <<< >>> >|
	 */
	public static String miniPagination(final int currentPage, final int pages) {
		final StringBuffer sb = new StringBuffer();
		final int firstPage = 1;
		final int prePage = new Integer(currentPage - 1);
		final int nextPage = new Integer(currentPage + 1);
		final int lastPage = pages;
		if (currentPage > 1) {
			sb.append("		<li onclick=\"return gotoPage(" + firstPage
					+ ");\"><span title=\"首页\">|&lt;</span></li>\r\n");
			sb.append("		<li onclick=\"return gotoPage(" + prePage
					+ ");\"><span title=\"上一页\">&lt;&lt;</span></li>\r\n");
		} else {
			sb.append("		<li class=\"disabled\"><span>|&lt;</span></li>\r\n");
			sb
					.append("		<li class=\"disabled\"><span>&lt;&lt;</span></li>\r\n");
		}

		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages)
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					sb.append("		<li class=\"currentPage\"><span>" + i
							+ "</span></li>");
				else
					sb.append("		<li onclick=\"return gotoPage(" + i
							+ ");\"><span title=\"转到第" + i + "页\">" + i
							+ "</span></li>\r\n");

		if (pages > 1)
			sb
					.append("		<li><span>到<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0&&this.value<="
							+ lastPage
							+ "&&this.value!="
							+ currentPage
							+ "){return gotoPage(this.value);}}\"  onblur=\"if(this.value>0&&this.value<="
							+ lastPage
							+ "){var f = document.getElementById('ListForm');f.currentPage.value=this.value;f.submit();}\" />页</span></li>\r\n");
		if (currentPage < pages) {
			sb.append("		<li onclick=\"return gotoPage(" + nextPage
					+ ");\"><span title=\"下一页\">&gt;&gt;</span></li>\r\n");
			sb.append("		<li onclick=\"return gotoPage(" + lastPage
					+ ");\"><span title=\"未页\">&gt;|</span></li>\r\n");
		} else {
			sb
					.append("		<li class=\"disabled\"><span>&gt;&gt;</span></li>\r\n");
			sb.append("		<li class=\"disabled\"><span>&gt;|</span></li>\r\n");
		}

		return sb.toString();
	}

	public static String MNPagination(final int currentPage, final int pages) {
		final StringBuffer sb = new StringBuffer();
		final int firstPage = 1;
		final int prePage = new Integer(currentPage - 1);
		final int nextPage = new Integer(currentPage + 1);
		final int lastPage = pages;
		if (currentPage > 1) {
			sb.append("		<li onclick=\"return MNPagination.to(" + firstPage
					+ ");\"><span title=\"首页\">|&lt;</span></li>\r\n");
			sb.append("		<li onclick=\"return MNPagination.to(" + prePage
					+ ");\"><span title=\"上一页\">&lt;&lt;</span></li>\r\n");
		} else {
			sb.append("		<li class=\"disabled\"><span>|&lt;</span></li>\r\n");
			sb
					.append("		<li class=\"disabled\"><span>&lt;&lt;</span></li>\r\n");
		}

		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages)
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					sb.append("		<li class=\"currentPage\"><span>" + i
							+ "</span></li>");
				else
					sb.append("		<li onclick=\"return MNPagination.to(" + i
							+ ");\"><span title=\"转到第" + i + "页\">" + i
							+ "</span></li>\r\n");

		if (pages > 1)
			sb
					.append("		<li><span>&nbsp;<input title=\"turn to page\" type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0&&this.value<="
							+ lastPage
							+ "&&this.value!="
							+ currentPage
							+ "){return MNPagination.to(this.value);}}\"  onblur=\"if(this.value>0&&this.value<="
							+ lastPage
							+ "){return MNPagination.to(this.value);}\" />&nbsp;</span></li>\r\n");
		if (currentPage < pages) {
			sb.append("		<li onclick=\"return MNPagination.to(" + nextPage
					+ ");\"><span title=\"下一页\">&gt;&gt;</span></li>\r\n");
			sb.append("		<li onclick=\"return MNPagination.to(" + lastPage
					+ ");\"><span title=\"未页\">&gt;|</span></li>\r\n");
		} else {
			sb
					.append("		<li class=\"disabled\"><span>&gt;&gt;</span></li>\r\n");
			sb.append("		<li class=\"disabled\"><span>&gt;|</span></li>\r\n");
		}

		return sb.toString();
	}

	public static String options(final int min, final int max, final int value) {
		String s = "";
		for (int i = min; i <= max; i++)
			s += "<option value='" + i + "' " + (i == value ? "selected" : "")
					+ ">" + i + "</option>";
		return s;
	}

	// var f = document.createElement('form');
	// f.style.display = 'none';
	// this.parentNode.appendChild(f); f.method = 'GET';
	// f.currentPage.value=; f.action =
	// window.location.href;f.submit();

	public static String options(final List list, final String valuePoperty,
			final String titlePoperty, final String value) {
		if (list == null)
			return "";
		final String[][] items = new String[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			final BeanWrapper wrapper = new BeanWrapper(list.get(i));
			items[i][0] = wrapper.getPropertyValue(valuePoperty).toString();
			items[i][1] = wrapper.getPropertyValue(titlePoperty).toString();

		}
		return TagUtil.options(items, value);
	}

	public static String options(final String[][] items, final String value) {
		String s = "";
		for (final String[] element : items)
			s += "<option value='" + element[0] + "' "
					+ (element[0].equals(value) ? "selected" : "") + ">"
					+ element[1] + "</option>";
		return s;
	}

	/**
	 * DIV+CSS标准分页效果
	 * 
	 * @param currentPage
	 *            当前页
	 * @param pages
	 *            总页数
	 * @param rowCount
	 *            总记录数
	 * @return 返回客户端HTML分页字符串
	 */
	public static String paginationDC(final int currentPage, final int pages,
			final int rowCount) {
		final StringBuffer sb = new StringBuffer();
		final int prePage = new Integer(currentPage - 1);
		final int nextPage = new Integer(currentPage + 1);
		final int beginPage = currentPage - 2 < 1 ? 1 : currentPage - 2;
		sb.append("<div class=\"p_bar\"><a title=\"共" + rowCount
				+ "条记录\" class=\"p_total\">" + rowCount + "</a><a title=\"当前第"
				+ currentPage + "页/共" + pages + "页\" class=\"p_pages\">"
				+ currentPage + "/" + pages + "</a>");
		if (currentPage > 3)
			sb
					.append("<a title=\"第一页\" class=\"p_redirect\" onclick=\"return gotoPage("
							+ 1 + ")\">|&lsaquo;</a>");
		if (currentPage > 2)
			sb
					.append("<a title=\"上一页\" class=\"p_redirect\" onclick=\"return gotoPage("
							+ prePage + ")\" style=\"font-weight: bold\">«</a>");
		if (currentPage >= 1)
			for (int i = beginPage, j = 0; i <= pages && j < 10; i++, j++)
				if (i == currentPage)
					sb.append("<a class=\"p_curpage\">" + i + "</a>");
				else
					sb.append("<a class=\"p_num\" title=\"转到第" + i
							+ "页\" onclick=\"return gotoPage(" + i + ")\">" + i
							+ "</a>");
		if (currentPage < pages)
			sb
					.append("<a title=\"下一页\" class=\"p_redirect\" onclick=\"return gotoPage("
							+ nextPage
							+ ")\" style=\"font-weight: bold\">»</a>");
		if (pages > 10 && currentPage < pages - 7)
			sb
					.append("<a title=\"末页\" class=\"p_redirect\" onclick=\"return gotoPage("
							+ pages + ")\">&#8250;|</a>");
		sb
				.append("<a class=\"p_pages\" style=\"padding:0\">"
						+ "<input type=\"text\" class=\"p_input\" onkeydown=\"if(event.keyCode==13){return gotoPage(this.value)}\" /></a></div>");
		return sb.toString();
	}

	public static String showPageHtml(final int currentPage, final int pages) {
		final StringBuffer sb = new StringBuffer();
		final int firstPage = 1;
		final int prePage = new Integer(currentPage - 1);
		final int nextPage = new Integer(currentPage + 1);
		final int lastPage = pages;
		if (currentPage > 1) {
			sb.append("		<li onclick=\"return gotoPage(" + firstPage
					+ ");\"><span title=\""+I18n.get("application.homePage")+"\">|&lt;</span></li>\r\n");
			sb.append("		<li onclick=\"return gotoPage(" + prePage
					+ ");\"><span title=\""+I18n.get("application.previous")+"\">&lt;&lt;</span></li>\r\n");
		} else {
			sb.append("		<li class=\"disabled\"><span>|&lt;</span></li>\r\n");
			sb
					.append("		<li class=\"disabled\"><span>&lt;&lt;</span></li>\r\n");
		}

		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			sb
					.append("		<li><span>"+I18n.get("application.to")+"<select onchange=\"return gotoPage(this.value);\">");
			for (int i = 0; i < pages; i++) {
				sb.append("			<option value=\"" + (i + 1) + "\"");

				if (currentPage == (i + 1))
					sb.append(" selected='true';");

				sb.append(">" + (i + 1) + "</option>");
			}
			sb.append("		</select>"+I18n.get("application.page")+"</span></li>\r\n");

			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					sb.append("		<li class=\"currentPage\"><span>" + i
							+ "</span></li>");
				else
					sb.append("		<li onclick=\"return gotoPage(" + i
							+ ");\"><span title=\""+I18n.get("application.turnto")+"" + i + ""+I18n.get("application.page")+"\">" + i
							+ "</span></li>\r\n");
		}

		if (pages > 1)
			// sb.+="<li><span>每页<input type=\"text\"
			// size=\"2\" onkeydown='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// keyCode = event.keyCode ? event.keyCode :
			// event.which ? event.which :
			// event.charCode;if(keyCode==13){if(this.value>0&&E$(\"pageSize\").value!=this.value){var
			// cp=E$(\"currentPage\").value;window.location.href=\"pageSize=\"+this.value;E$(\"currentPage\").value=cp;}}'
			// />条</span></li>";
			sb
					.append("		<li><span>"+I18n.get("application.to")+"<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0&&this.value<="
							+ lastPage
							+ "&&this.value!="
							+ currentPage
							+ "){return gotoPage(this.value);}}\"  onblur=\"if(this.value>0&&this.value<="
							+ lastPage
							+ "){var f = document.getElementById('ListForm');f.currentPage.value=this.value;f.submit();}\" />"+I18n.get("application.page")+"</span></li>\r\n");
		sb
				.append("		<li><span>"+I18n.get("application.perpage")+"<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0){var f = document.getElementById('ListForm'); f.pageSize.value=this.value;f.submit();}}\"  onblur=\"if(this.value>0){var f = document.getElementById('ListForm'); f.pageSize.value"
						+ "=this.value;f.submit();}\" />"+I18n.get("application.records")+"</span></li>\r\n");
		if (currentPage < pages) {

			// 测试动态创建元素

			// s += "<li class=\"currentPage\" onclick='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// f=document.createElement(\"form\");f.id=\"easyjweblistform\";f.method=\"POST\";f.action=window.location.href;this.parentNode.appendChild(f);var
			// c=document.createElement(\"input\");c.style.display=\"none\";c.name=\"currentPage\";c.value=E$(\"currentPage\").value;f.appendChild(c);var
			// p=document.createElement(\"input\");p.style.display=\"none\";p.name=\"pageSize\";p.value=E$(\"pageSize\").value;f.appendChild(p);f.submit();//alert(f.method);//alert(E$(\"easyjweblistform\").method);'><span>测试</span></li>\r\n";

			sb.append("		<li onclick=\"return gotoPage(" + nextPage
					+ ");\"><span title=\""+I18n.get("application.nextpage")+"\">&gt;&gt;</span></li>\r\n");
			sb.append("		<li onclick=\"return gotoPage(" + lastPage
					+ ");\"><span title=\""+I18n.get("application.lastpage")+"\">&gt;|</span></li>\r\n");
		} else {
			sb
					.append("		<li class=\"disabled\"><span>&gt;&gt;</span></li>\r\n");
			sb.append("		<li class=\"disabled\"><span>&gt;|</span></li>\r\n");
		}

		return sb.toString();
	}

	public static String showPageHtml_inner(final int currentPage,
			final int pages) {
		final StringBuffer sb = new StringBuffer();
		final int firstPage = 1;
		final int prePage = new Integer(currentPage - 1);
		final int nextPage = new Integer(currentPage + 1);
		final int lastPage = pages;

		if (currentPage > 1) {
			sb
					.append("		<li onclick=\"var f = document.getElementById('ListForm');f.currentPage.value="
							+ firstPage
							+ ";f.submit();\"><span title=\"首页\">|<</span></li>\r\n");
			sb
					.append("		<li onclick=\"var f = document.getElementById('ListForm');f.currentPage.value="
							+ prePage
							+ ";f.submit();\"><span title=\"上一页\"><<</span></li>\r\n");
		} else {
			sb.append("		<li class=\"disabled\">|<</span></li>\r\n");
			sb.append("		<li class=\"disabled\"><<</span></li>\r\n");
		}

		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			sb
					.append("		<li><span>到<select onchange=\"var f = document.getElementById('ListForm');f.currentPage.value=this.value;f.submit();\">");
			for (int i = 0; i < pages; i++) {
				sb.append("			<option value=\"" + (i + 1) + "\"");

				if (currentPage == (i + 1))
					sb.append(" selected='true';");

				sb.append("/>" + (i + 1) + "</option>");
			}
			sb.append("		</select>页</span></li>\r\n");

			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					sb.append("		<li class=\"currentPage\"><span>" + i
							+ "</span></li>");
				else
					sb
							.append("		<li onclick=\"var f = document.getElementById('ListForm');f.currentPage.value="
									+ i
									+ ";f.submit();\"><span title=\"转到第"
									+ i + "页\">" + i + "</span></li>\r\n");
		}

		if (pages > 1)
			// sb.+="<li><span>每页<input type=\"text\"
			// size=\"2\" onkeydown='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// keyCode = event.keyCode ? event.keyCode :
			// event.which ? event.which :
			// event.charCode;if(keyCode==13){if(this.value>0&&E$(\"pageSize\").value!=this.value){var
			// cp=E$(\"currentPage\").value;window.location.href=\"pageSize=\"+this.value;E$(\"currentPage\").value=cp;}}'
			// />条</span></li>";
			sb
					.append("		<li><span>到<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0&&this.value<="
							+ lastPage
							+ "&&this.value!="
							+ currentPage
							+ "){var f = document.getElementById('ListForm');f.currentPage.value=this.value;f.submit();}}\"  onblur=\"if(this.value>0&&this.value<="
							+ lastPage
							+ "){var f = document.getElementById('ListForm');f.currentPage.value=this.value;f.submit();}\" />页</span></li>\r\n");
		sb
				.append("		<li><span>每页<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0){var f = document.getElementById('ListForm'); f.pageSize.value=this.value;f.submit();}}\"  onblur=\"if(this.value>0){var f = document.getElementById('ListForm'); f.pageSize.value"
						+ "=this.value;f.submit();}\" />条</span></li>\r\n");
		if (currentPage < pages) {

			// 测试动态创建元素

			// s += "<li class=\"currentPage\" onclick='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// f=document.createElement(\"form\");f.id=\"easyjweblistform\";f.method=\"POST\";f.action=window.location.href;this.parentNode.appendChild(f);var
			// c=document.createElement(\"input\");c.style.display=\"none\";c.name=\"currentPage\";c.value=E$(\"currentPage\").value;f.appendChild(c);var
			// p=document.createElement(\"input\");p.style.display=\"none\";p.name=\"pageSize\";p.value=E$(\"pageSize\").value;f.appendChild(p);f.submit();//alert(f.method);//alert(E$(\"easyjweblistform\").method);'><span>测试</span></li>\r\n";

			sb
					.append("		<li onclick=\"var f = document.getElementById('ListForm');f.currentPage.value="
							+ nextPage
							+ ";f.submit();\"><span title=\"下一页\">>></span></li>\r\n");
			sb
					.append("		<li onclick=\"var f = document.getElementById('ListForm');f.currentPage.value="
							+ lastPage
							+ ";f.submit();\"><span title=\"未页\">>|</span></li>\r\n");
		} else {
			sb.append("		<li class=\"disabled\">>></span></li>\r\n");
			sb.append("		<li class=\"disabled\">>|</span></li>\r\n");
		}

		return sb.toString();
	}

	/**
	 * 显示页码 Add the css style and mouseevent here
	 * 
	 * @param currentPage
	 * @param pages
	 * @return a static String
	 * @author WilliamRaym /* css for turnpage start
	 */
	/** *******************分页样式开始******************** */
	/**
	 * 定义所有分页内容显示为粗体字
	 */
	/*
	 * #pagination{ clear : both; font-weight : bold; padding-bottom : 10px; }
	 * #pagination ul li{ float : left; align : left; padding : 2px 4px 2px 4px;
	 * border : 1px solid #E6E6E6; cursor : pointer; margin-left: 4px; }
	 * #pagination ul li span.info{ cursor : default; } #pagination li.disabled{
	 * background-color: #fff; color: #e6e6e6; border: 1px solid #e6e6e6;
	 * cursor: default; } #pagination li.currentPage{ background-color :
	 * #D2EAF6; cursor : default; } #pagination li.over{ background-color :
	 * #D2EAF6; } #pagination li.out{ background-color : #fff; }
	 */
	/** *******************分页样式结束******************** */
	/* css for turnpage over */
	/*
	 * when page initial <script type="text/javascript"
	 * src="/script/easyjweb-util.js"></script> <script type="text/javascript"
	 * defer="defer"> function init(){ F=new
	 * FORM("index.ejf","IndexForm");//must be F as the instance of FORM Object;
	 * F.onGotoPageCallback=function(){ $("ListForm").action="index.ejf";
	 * $("ListForm").easyJWebCommand.value="list"; } windowInit(); }
	 * window.onload=init; </script> invoke the turn page function
	 * #if($!{rows}>$!{pageSize}) <div id="pagination"> <ul> <li class="info"><span>第<strong>$!currentPage</strong>页
	 * 共<strong>$!totalPage</strong>页<span class="txt1">[共<b>$!rows</b>条记录]
	 * </span></li> $!{pagination} </ul> </div> #else #if($!{rows}==0) <div
	 * id="pagination"> <ul> <li class="info"><span> No Content here yet
	 * </span></li> </ul> </div> #end #end
	 */

	// var f = document.createElement('form');
	// f.style.display = 'none';
	// this.parentNode.appendChild(f); f.method = 'GET';
	// f.currentPage.value=; f.action =
	// window.location.href;f.submit();
	public static String showPageHtml_old(final int currentPage, final int pages) {
		final StringBuffer sb = new StringBuffer();
		final int allPages = pages;

		if (currentPage > 1) {
			sb
					.append("		<li onclick=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value=1; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.appendChild(f);f.submit();\" onmouseover=\"this.className='over';window.status='首页';return false;\"  onmouseout=\"this.className='out';window.status='';return false;\"><span title=\"首页\">|<</span></li>\r\n");
			sb
					.append("		<li onclick=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value="
							+ (currentPage - 1)
							+ "; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.appendChild(f);f.submit();\" onmouseover=\"this.className='over';window.status='上一页';return false;\"  onmouseout=\"this.className='out';window.status='';return false;\"><span title=\"上一页\"><<</span></li>\r\n");
		} else {
			sb
					.append("		<li class=\"disabled\" onmouseover=\"window.status='首页 当前不可操作';return false;\"  onmouseout=\"window.status='';return false;\"><span title=\"首页 当前不可操作\">|<</span></li>\r\n");
			sb
					.append("		<li class=\"disabled\"  onmouseover=\"window.status='上一页 当前不可操作';return false;\"  onmouseout=\"window.status='';return false;\"><span title=\"上一页 当前不可操作\"><<</span></li>\r\n");
		}

		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			sb
					.append("		<li><span>到<select onchange=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value=this.value; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.parentNode.parentNode.parentNode.appendChild(f);f.submit();\">");
			for (int i = 0; i < pages; i++) {
				sb.append("			<option value=\"" + (i + 1) + "\"");

				if (currentPage == (i + 1))
					sb.append(" selected='true';");

				sb.append(">" + (i + 1) + "</option>");
			}
			sb.append("		</select>页</span></li>\r\n");

			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					sb.append("		<li class=\"currentPage\"><span>" + i
							+ "</span></li>");
				else
					sb
							.append("		<li onclick=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value="
									+ i
									+ "; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.appendChild(f);f.submit();\" onmouseover=\"this.className='over';window.status='转到第"
									+ i
									+ "页';return false;\"  onmouseout=\"this.className='out';window.status='';return false;\"><span title=\"转到第"
									+ i + "页\">" + i + "</span></li>\r\n");
		}
		if (pages > 1)
			// s+="<li><span>每页<input type=\"text\"
			// size=\"2\" onkeydown='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// keyCode = event.keyCode ? event.keyCode :
			// event.which ? event.which :
			// event.charCode;if(keyCode==13){if(this.value>0&&E$(\"pageSize\").value!=this.value){var
			// cp=E$(\"currentPage\").value;window.location.href=\"pageSize=\"+this.value;E$(\"currentPage\").value=cp;}}'
			// />条</span></li>";
			sb
					.append("		<li><span>到<input type=\"text\" size=\"2\" onkeydown=\"var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;if(keyCode == 13){if(this.value>0&&this.value<="
							+ allPages
							+ "){var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value=this.value; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.parentNode.parentNode.parentNode.appendChild(f);f.submit();}}\"  onblur=\"if(this.value>0&&this.value<="
							+ allPages
							+ "){var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value=this.value; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.parentNode.parentNode.parentNode.appendChild(f);f.submit();}\" />页</span></li>\r\n");
		if (currentPage < pages) {

			// 测试动态创建元素

			// s += "<li class=\"currentPage\" onclick='var
			// E$=function(element){return
			// document.getElementById(element);};var
			// f=document.createElement(\"form\");f.id=\"easyjweblistform\";f.method=\"POST\";f.action=window.location.href;this.parentNode.appendChild(f);var
			// c=document.createElement(\"input\");c.style.display=\"none\";c.name=\"currentPage\";c.value=E$(\"currentPage\").value;f.appendChild(c);var
			// p=document.createElement(\"input\");p.style.display=\"none\";p.name=\"pageSize\";p.value=E$(\"pageSize\").value;f.appendChild(p);f.submit();//alert(f.method);//alert(E$(\"easyjweblistform\").method);'><span>测试</span></li>\r\n";

			sb
					.append("		<li onclick=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value="
							+ (currentPage + 1)
							+ "; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.appendChild(f);f.submit();\" onmouseover=\"this.className='over';window.status='下一页';return false;\"  onmouseout=\"this.className='out';window.status='';return false;\"><span title=\"下一页\">>></span></li>\r\n");
			sb
					.append("		<li onclick=\"var f = document.createElement('form'); var c=document.createElement('input');c.type='hidden';c.name='currentPage'; c.value="
							+ allPages
							+ "; f.appendChild(c); f.style.display = 'block'; f.style.height='0'; f.method = 'POST'; f.action = window.location.href;this.parentNode.appendChild(f);f.submit();\" onmouseover=\"this.className='over';window.status='未页';return false;\"  onmouseout=\"this.className='out';window.status='';return false;\"><span title=\"未页\">>|</span></li>\r\n");
		} else {
			sb
					.append("		<li class=\"disabled\"><span title=\"下一页 当前不可操作\" onmouseover=\"window.status='下一页 当前不可操作';return false;\"  onmouseout=\"window.status='';return false;\">>></span></li>\r\n");
			sb
					.append("		<li class=\"disabled\"><span title=\"未页 当前不可操作\" onmouseover=\"window.status='未页 当前不可操作';return false;\"  onmouseout=\"window.status='';return false;\">>|</span></li>\r\n");
		}
		return sb.toString();
	}

	/**
	 * 返回ajax分页
	 * 
	 * @param currentPage
	 * @param pages
	 * @return 基于ajax的分页信息
	 */
	public static String showPageHtmlAjax(final int currentPage, final int pages) {
		return "Ajax pagination";
	}

	public static String showPublishPageHtml(final String path,
			final int currentPage, final int pages) {
		String s = "";
		boolean isDir = false;
		if (path.endsWith("/"))
			isDir = true;
		if (currentPage > 1) {
			s += "<a href=" + path + (isDir ? "1" : "") + ".html>首页</a> ";
			s += "<a href=" + path
					+ (currentPage - 1 > 1 ? (currentPage - 1) + "" : "")
					+ ".html>上一页</a> ";
		}
		final int beginPage = currentPage - 3 < 1 ? 1 : currentPage - 3;
		if (beginPage < pages) {
			s += "第　";
			for (int i = beginPage, j = 0; i <= pages && j < 6; i++, j++)
				if (i == currentPage)
					s += "<font color=red>" + i + "</font> ";
				else
					s += "<a href=" + path
							+ (i > 1 ? i + "" : (isDir ? i + "" : ""))
							+ ".html>" + i + "</a> ";
			s += "页　";
		}
		if (currentPage < pages) {
			s += "<a href=" + path + (currentPage + 1) + ".html>下一页</a> ";
			s += "<a href=" + path + pages + ".html>末页</a> ";
		}
		// s+=" 转到<input type=text size=2>页";
		return s;
	}

	/**
	 * Verify That the given String is in valid URL format.
	 * 
	 * @param url
	 *            The url string to verify.
	 * @return a boolean indicating whether the URL seems to be incorrect.
	 */
	public final static boolean verifyUrl(String url) {
		if (url == null)
			return false;

		if (url.startsWith("https://"))
			// URL doesn't understand the https protocol,
			// hack it
			url = "http://" + url.substring(8);

		try {
			new URL(url);

			return true;
		} catch (MalformedURLException e) {
			return false;
		}
	}
}
