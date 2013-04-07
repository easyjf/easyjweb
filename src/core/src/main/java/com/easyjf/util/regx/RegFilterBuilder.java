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
package com.easyjf.util.regx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 过滤实现
 * @author stef_wu
 *
 */
public class RegFilterBuilder implements FilterBuilder {

	private String regex = null;

	private String rpStr = null;

	private String source = null;

	private String result = null;

	public RegFilterBuilder(String regex, String rpStr, String source) {
		super();
		this.regex = regex;
		this.rpStr = rpStr;
		this.source = source;
	}

	public void buildFilter() {
		if (this.regex == null) {
			return;
		}
		Pattern p = Pattern.compile(regex, 2);
		Matcher matcher = p.matcher(this.source);
		StringBuffer sb = new StringBuffer();
		String tempString = rpStr;
		int rpL = rpStr.split("\\$[0-9]+").length;
		while (matcher.find()) {
			for (int i = 0; (i < rpL) && (i < matcher.groupCount()); i++) {
				tempString = tempString.replaceAll("\\$" + i, matcher.group(i));
			}
			matcher.appendReplacement(sb, tempString);
		}
		matcher.appendTail(sb);
		this.result = sb.toString();
	}

	public Filter getFilter() {
		return (new RegexFilter() {
			public String getFilteredStr() {
				return result;
			}
		});
	}

}
