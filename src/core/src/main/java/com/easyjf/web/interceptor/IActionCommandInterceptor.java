package com.easyjf.web.interceptor;

import java.lang.reflect.Method;

import com.easyjf.web.Page;

/**
 * 专为AbstractCmdAction设置的拦截器，命令模块拦截器
 * @author 大峡
 *
 */
public interface IActionCommandInterceptor {
	/**
	 * 在指定的模块执行之前执行，如果返回null则继续往下执行，否则停止模块执行，并跳转到其它模块，也可以直接在拦截器中抛出Exception
	 * @param form
	 * @return
	 */
	Page doBefore(Method method);
	/**
	 * 在模块执行完之后执行
	 * @param form
	 */
	void doAfter(Method method);
}
