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

import javax.servlet.http.HttpSession;

import com.easyjf.web.ActionContext;
import com.easyjf.web.Globals;

/**
 * 令牌产生器，用于防止重复提交
 * 
 * @author stefanie_wu
 * 
 */
public class TokenProcessor {

	private static TokenProcessor instance;

	/**
	 * 最近使用的时间值
	 */
	private long previous;

	private final static Object keyForTokenProcessor = new Object();

	public static TokenProcessor getInstance() {
		if (TokenProcessor.instance == null) {
			synchronized (TokenProcessor.keyForTokenProcessor) {
				if (TokenProcessor.instance == null) {
					TokenProcessor.instance = new TokenProcessor();
				}
			}
		}
		return TokenProcessor.instance;
	}

	private TokenProcessor() {
	}

	/**
	 * 检测令牌是否合法，并清空令牌值。判断令牌是否合法 如果Session中保存有令牌并且在提交的请求中令牌和Session中的令牌相等的话，返回true
	 * 如果Request中没有Session对象，返回false 如果session中没有token，返回false
	 * 如果session中的token和请求中的token不一致，返回false
	 * @param tokenInRequest 存放在request中的token值
	 * @return 如果令牌与所检测的值合法，则返回true，否则返回false;
	 */	
	public synchronized boolean isTokenValid(String tokenInRequest) {
		return this.isTokenValid(true,tokenInRequest);
	}

	/**
	 * 同isTokenValid，
	 * 
	 * @param request
	 * @param reset
	 *            是否在验证token后清空Session中的token
	 * @return
	 */
	public synchronized boolean isTokenValid(boolean reset,String tokenInRequest) {
		HttpSession session = ActionContext.getContext().getSession();

		if (session == null) {
			return false;
		}

		String saved = (String) session.getAttribute(Globals.TOKEN_NAME);

		if (saved == null) {
			return false;
		}

		if (reset) {
			this.resetToken();
		}
		
		return saved.equals(tokenInRequest);
	}

	/**
	 * 清空Session中的token域
	 * 
	 * @param request
	 */
	public synchronized void resetToken() {
		HttpSession session = ActionContext.getContext().getSession();

		if (session == null) {
			return;
		}

		session.removeAttribute(Globals.TOKEN_NAME);
	}

	/**
	 * 保存Token
	 */
	public synchronized void saveToken() {
		String token = generateToken();

		if (token != null) {
			ActionContext.getContext().getSession().setAttribute(
					Globals.TOKEN_NAME, token);
		}
	}

	/**
	 * 生成token，并添加到Session中。
	 * 
	 * @return
	 */
	public synchronized String generateToken() {
		HttpSession session = ActionContext.getContext().getSession();

		return generateToken(session.getId());
	}

	public synchronized String generateToken(String id) {
		try {
			long current = System.currentTimeMillis();

			if (current == previous) {
				current++;
			}

			previous = current;

			byte[] now = new Long(current).toString().getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(id.getBytes());
			md.update(now);

			return toHex(md.digest());
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	private String toHex(byte[] buffer) {
		StringBuffer sb = new StringBuffer(buffer.length * 2);

		for (int i = 0; i < buffer.length; i++) {
			sb.append(Character.forDigit((buffer[i] & 0xf0) >> 4, 16));
			sb.append(Character.forDigit(buffer[i] & 0x0f, 16));
		}

		return sb.toString();
	}

}
