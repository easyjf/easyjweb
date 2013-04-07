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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.easyjf.web.Globals;
/**
 * 
 * <p>
 * Title: 用户提交ID的生成、验证以及销毁 * </p>
 * <p>
 * Description: 用户提交表单时生成唯一的标识ID，并保存在会话中
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 张钰
 * @version 0.1
 */
public class ForbitRepProcessor {

	private static ForbitRepProcessor instance = new ForbitRepProcessor();

	public static ForbitRepProcessor getInstance() {
		return instance;
	}

	protected ForbitRepProcessor() {
		super();
	}

	public synchronized boolean isForbitRepValid(HttpServletRequest request) {
		return this.isForbitRepValid(request, false);
	}

	public synchronized boolean isForbitRepValid(HttpServletRequest request,
			boolean reset) {

	    HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}

		
		String saved = (String) session
				.getAttribute(Globals.TRANSACTION_FORBITREP_KEY);
		if (saved == null) {
			return false;
		}

		if (reset) {
			this.resetForbitRep(request);
		}

		
		String forbit = request.getParameter(Globals.TRANSACTION_FORBITREP_KEY);
		if (forbit == null) {
			return false;
		}

		return saved.equals(forbit);
	}

	public synchronized void resetForbitRep(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(Globals.TRANSACTION_FORBITREP_KEY);
	}

	public synchronized void saveForbitRep(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String forbitRep = generateForbitRep(request);
		if (forbitRep != null) {
			session.setAttribute(Globals.TRANSACTION_FORBITREP_KEY, forbitRep);
		}

	}

	public String generateForbitRep(HttpServletRequest request) {

		HttpSession session = request.getSession();
		try {
			byte id[] = session.getId().getBytes();
			byte now[] = new Long(System.currentTimeMillis()).toString()
					.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(id);
			md.update(now);
			return this.toHex(md.digest());

		} catch (IllegalStateException e) {
			return null;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

	private String toHex(byte buffer[]) {
		StringBuffer sb = new StringBuffer();
		String s = null;

		for (int i = 0; i < buffer.length; i++) {
			s = Integer.toHexString((int) buffer[i] & 0xff);
			if (s.length() < 2) {
				sb.append('0');
			}
			sb.append(s);
		}

		return sb.toString();
	}

}
