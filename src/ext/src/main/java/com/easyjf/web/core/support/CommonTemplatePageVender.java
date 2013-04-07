package com.easyjf.web.core.support;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.commontemplate.core.Context;
import org.commontemplate.tools.web.EngineHolder;

import com.easyjf.web.LocalManager;
import com.easyjf.web.Page;
import com.easyjf.web.WebInvocationParam;
import com.easyjf.web.core.FrameworkEngine;
import com.easyjf.web.exception.FrameworkException;

/**
 * CommonTemplate模板供给方案.
 * <p/>
 * CommonTemplate模板的具体使用请参见：<a href="http://www.commontemplate.org">http://www.commontemplate.org</a>
 * 
 * @author 梁飞,大峡
 * 
 */
public class CommonTemplatePageVender extends AbstractPageVender {
	
	private static final long serialVersionUID = 1L;
	
	// 初始化标志
	private static volatile boolean isFirst = true;
	
	// venderPage不应该返回boolean值，而应该返回void，并throws Exception，
	// 这里的返回值纯粹是状态位，无业务含义，页面呈现调用总是期望它是成功的，若不成功则一定有它的原因，应该用Exception持有相关信息，
	// 按"契约式设计原则"，也应该返回void，因为此方法是"子过程"，而不是"查询函数" 
	// 如果要实现PageVender链，也应由外部策略控制
	// 并且此接口是容器回调SPI(Service Provide Interface)，所以应该抛出<b>全部</b>异常(throws Exception)，说明所有异常都不要自行catch，由容器统一处理。
	// 也可以考虑throws IOException
	public boolean venderPage(HttpServletRequest request,
			HttpServletResponse response, Page page, WebInvocationParam param) {
		if (isFirst) {// JDK5以上版本的JMM(Java内存模型)规定volatile属性的"双重检查成例"有效
			synchronized (EngineHolder.class) {
				if (isFirst) { // 双重检查
					isFirst = false;
					final ServletContext servletContext = request.getSession().getServletContext();
					EngineHolder.init(servletContext, getInitProperties(servletContext)); // 初始化引擎
				}
			}
		}
		try {
			EngineHolder.renderTemplate(getTemplatePath(request, page, param), 
					getTemplateEncoding(request, page, param),
					getContext(request, response, page, param));
			response.flushBuffer();
		} catch (IOException e) { // 即然每个SPI供给者都要catch后,再throw new FrameworkException,可以考虑由容器统一处理
			throw new FrameworkException("CommonTemplate模板合成错误!", e);
		}
		return true;
	}
	
	// 子类可以通过覆写此函数，更改初始化配置
	protected Properties getInitProperties(ServletContext servletContext) {
		Properties properites = new Properties();
		properites.setProperty("@extends", EngineHolder.STANDARD_CONFIG_PATH); // 继承标准配置
		properites.setProperty("defaultEncoding", "UTF-8"); // 设置模板加载默认编码
		properites.setProperty("virtualDirectory", FrameworkEngine.getWebConfig().getTemplateBasePath()); // 设置模板虑拟根目录
		if (FrameworkEngine.getWebConfig().isDebug()) {
			properites.setProperty("debugMode", "true");
			properites.setProperty("modificationCheckInterval", "1000"); // 设置热加载时间为1秒
		}
		return properites;
	}

	// 子类可以通过覆写此函数，设置Context
	protected Context getContext(HttpServletRequest request, HttpServletResponse response, Page page, WebInvocationParam param) throws IOException {
		return EngineHolder.createContext(request, response, getLocale(request, page, param), param.getForm().getEasyJWebResult());
	}

	// 子类可以通过覆写此函数，更改Locale的供给方案
	protected Locale getLocale(HttpServletRequest request, Page page, WebInvocationParam param) throws IOException {
		Locale locale = LocalManager.getCurrentLocal();
		if (locale != null)
			return locale;
		return request.getLocale();
	}

	// 子类可以通过覆写此函数，更改Template路径
	protected String getTemplatePath(HttpServletRequest request, Page page, WebInvocationParam param) throws IOException {
		return page.getUrl();
	}
	
	// 子类可以通过覆写此函数，更改Template编码
	protected String getTemplateEncoding(HttpServletRequest request, Page page, WebInvocationParam param) throws IOException {
		return request.getCharacterEncoding();
	}

}
