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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.easyjf.util.regx.UBBFilter;

/**
 * 
 * <p>
 * Title:Html文本处理的简单工具
 * </p>
 * <p>
 * Description:主要处理html文本,处理正则表达式等
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */
public class HtmlUtil {
	private static final HtmlUtil util = new HtmlUtil();

	public static String addBr(String Content) {
		if (Content == null)
			return null;
		String makeContent = Content.replaceAll("\\n", "<br>");
		/*
		 * new String();
		 * 
		 * StringTokenizer strToken = new StringTokenizer(Content, "\n"); while (strToken.hasMoreTokens()) { makeContent
		 * = makeContent + "<br>" + strToken.nextToken(); }
		 */
		return makeContent;
	}

	/**
	 * 字符串编码转换
	 * 
	 * @param str
	 *            需转码的字符串
	 * @param coding
	 *            编码格式
	 * @return 编码后的新字符串
	 */
	public static String convert(String str, String coding) {
		String newStr = "";
		if (str != null)
			try {
				newStr = new String(str.getBytes("ISO-8859-1"), coding);
			} catch (Exception e) {
				return newStr;
			}
		return newStr;
	}

	/**
	 * 把URL编码解码成普通字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		String ret = s;
		try {
			ret = URLDecoder.decode(s.trim(), "UTF-8");
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 把URL编码解码成普通字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String decode(String url, String decode) {
		String ret = url;
		try {
			ret = URLDecoder.decode(url.trim(), decode);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 编码成URL编码形式
	 * 
	 * @param s
	 * @return
	 */
	public static String encodeUrl(String s) {
		String ret = s;
		try {
			ret = URLEncoder.encode(s.trim(), "UTF-8");
		} catch (Exception e) {
		}
		return ret;
	}

	public static String encodeUrl(String url, String encode) {
		String ret = url;
		try {
			ret = URLEncoder.encode(url.trim(), encode);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 使HTML的标签失去作用
	 * 
	 * @param input
	 *            被操作的字符串
	 * @return String
	 */
	public static final String escapeHTMLTag(String input) {
		if (input == null) {
			input = "";
			return input;
		}
		input = input.trim().replaceAll("&", "&amp;");
		input = input.trim().replaceAll("<", "&lt;");
		input = input.trim().replaceAll(">", "&gt;");
		input = input.trim().replaceAll("\t", "    ");
		input = input.trim().replaceAll("\r\n", "\n");
		input = input.trim().replaceAll("\n", "<br>");
		input = input.trim().replaceAll("  ", " &nbsp;");
		input = input.trim().replaceAll("'", "&#39;");
		input = input.trim().replaceAll("\\\\", "&#92;");
		return input;
	}

	public static HtmlUtil getInstance() {
		return util;
	}

	public static String getUBB2HTML(String txt) {
		if (txt != null) {
			UBBFilter af = new UBBFilter(txt);
			txt = af.getFilteredStr();
		}
		return txt;
	}

	public static String ubbEncoder(String str) {

		// str = ubbPattern(str,"\\[b\\](.*?)\\[/b\\]","<strong>$1</strong>");
		// //粗体字

		// str = ubbPattern(str, "\\[color=(.*?)\\](.*?)\\[/color\\]","<span
		// style=color:$1>$2</span>"); //颜色

		// str = ubbPattern(str, "\\[url\\](.*?)\\[/url]","<a href=\"$1\"
		// target=\"_blank\">$1</a>"); //url网址替换

		// str = ubbPattern(str, "(\\[URL\\])(.[^\\[]*)(\\[\\/URL\\])" ,"<a
		// href=\"$1\" target=\"_blank\">$2</a>"); //url网址替换

		str = ubbPattern(str, "\\[url=(.*?)\\](.*?)\\[/url\\]", "<a href=\"$1\" target=\"_blank\">$2</a>");
		return str;
	}

	public static String ubbPattern(String str, String cp, String mc) {
		// str 准备对其操作的字符串，cp匹配的内容，mc准备替换成的内容
		String txt = new String();
		txt = str;
		if (str != null && !str.equals("")) {
			txt = str;
			Pattern p = Pattern.compile(cp, 2); // 参数2表示大小写不区分
			Matcher m = p.matcher(txt);
			StringBuffer sb = new StringBuffer();
			int i = 0;
			boolean result = m.find();
			// System.out.print(cp);
			// 使用循环将句子里所有匹配的内容找出并替换再将内容加到sb里
			while (result) {
				i++;
				m.appendReplacement(sb, mc);
				// 继续查找下一个匹配对象
				result = m.find();
			}
			// 最后调用appendTail()方法将最后一次匹配后的剩余字符串加到sb里；
			m.appendTail(sb);
			txt = String.valueOf(sb);
		} else
			txt = "";

		return txt;
	}

}
