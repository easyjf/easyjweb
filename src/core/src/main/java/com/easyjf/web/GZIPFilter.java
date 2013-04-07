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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * the gzipfilter
 * 
 * @author wlr
 * 
 */
public class GZIPFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("accept-encoding");
			if ((ae != null && ae.indexOf("gzip") != -1)) {
				// Vary: Accept-Encoding
				// System.out.println("GZIP 支持, 压缩文件中");
				GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper(
						response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
				return;
			}
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig filterConfig) {
	}

	public void destroy() {
	}
}
