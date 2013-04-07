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
/**
 * 过滤器实现
 * @author stef_wu
 *
 */
public class RegexFilter implements Filter {
	protected String regex = "";

	protected String rpStr = "";

	protected String source = "";

	protected String tempSource = "";

	protected Filter filter = null;

	public RegexFilter() {
	}

	public RegexFilter(String regex, String rpStr, String source) {
		this.regex = regex;
		this.rpStr = rpStr;
		this.source = source;
		this.tempSource = source;
		this.applyFilter();
	}

	protected RegexFilter(String source) {
		this.source = source;
		this.tempSource = source;
	}

	protected void applyFilter() {
		FilterBuilder builder = new RegFilterBuilder(regex, rpStr, tempSource);
		FilterDirector direct = new FilterDirector(builder);
		direct.construct();

		this.filter = builder.getFilter();
	}

	public String getFilteredStr() {
		return filter.getFilteredStr();
	}

	protected void doFiltration() {
		this.applyFilter();
		this.tempSource = filter.getFilteredStr();
	}

}
