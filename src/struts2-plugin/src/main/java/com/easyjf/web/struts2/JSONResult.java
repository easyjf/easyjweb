package com.easyjf.web.struts2;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyjf.web.ajax.AjaxUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class JSONResult implements Result {
	private String defaultEncoding="UTF-8";
	private boolean enableGZIP;
	private boolean noCache;
	private String root;

	boolean isGzipInRequest(HttpServletRequest request) {
		String header = request.getHeader("Accept-Encoding");
		return header != null && header.indexOf("gzip") >= 0;
	}

	public void execute(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(
				"com.opensymphony.xwork2.dispatcher.HttpServletRequest");
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(
				"com.opensymphony.xwork2.dispatcher.HttpServletResponse");
		response.setContentType("application/json;charset="+this.defaultEncoding);
		Object rootObject;
		if (root != null) {
			ValueStack stack = invocation.getStack();
			rootObject = stack.findValue(root);
		} else {
			rootObject = invocation.getAction();
		}
		if(noCache)
		 {
	            response.setHeader("Cache-Control", "no-cache");
	            response.setHeader("Expires", "0");
	            response.setHeader("Pragma", "No-cache");
	        }
		
		String json = "function(){" + AjaxUtil.getJSON(rootObject, true) + "}()";
		boolean gzip = enableGZIP && isGzipInRequest(request);
		if (gzip) {
			response.addHeader("Content-Encoding", "gzip");
			GZIPOutputStream out = null;
			InputStream in = null;
			try {
				out = new GZIPOutputStream(response.getOutputStream());
				in = new ByteArrayInputStream(json.getBytes());
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} finally {
				if (in != null)
					in.close();
				if (out != null) {
					out.finish();
					out.close();
				}
			}
		} else {
			response.setContentLength(json.getBytes(defaultEncoding).length);
			
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public void setEnableGZIP(boolean enableGZIP) {
		this.enableGZIP = enableGZIP;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public boolean isEnableGZIP() {
		return enableGZIP;
	}

	public boolean isNoCache() {
		return noCache;
	}

	public String getRoot() {
		return root;
	}

}
