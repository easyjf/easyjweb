package com.easyjf.web.core;

import java.util.HashMap;
import java.util.Map;
/**
 * Ext框架提交结果，表示是否成功及错误信息
 * @author 大峡
 *
 */
public class ExtResult {
	private boolean success;
	private Object data; 
	private Map errors = new HashMap();

	public ExtResult() {
		this(true);
	}

	public ExtResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public java.util.Map getErrors() {
		return errors;
	}

	public void setErrors(java.util.Map errors) {
		this.errors = errors;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
