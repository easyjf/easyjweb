package com.lanyotech.pps.service;

/**
 * 用来代表后台逻辑错误
 * @Author <a href="mailto:ksmwly@163.com">LengYu</a>
 * @Creation date: 2010-6-6 下午03:25:14
 * @Intro
 */
public class LogicException extends RuntimeException {
	public LogicException() {
		this("发生业务逻辑错误！");
	}

	public LogicException(String msg) {
		super(msg);
	}
}
