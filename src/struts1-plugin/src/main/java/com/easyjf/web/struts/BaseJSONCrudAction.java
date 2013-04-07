package com.easyjf.web.struts;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.easyjf.web.ajax.AjaxUtil;
import com.easyjf.web.core.ExtResult;
@SuppressWarnings("unchecked")
public abstract class BaseJSONCrudAction extends DispatchAction {
	private ThreadLocal<Object> jsonObject = new ThreadLocal<Object>();
	private ThreadLocal<Map> servletLocal = new ThreadLocal<Map>();
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) servletLocal.get().get("request");
	}

	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) servletLocal.get().get("response");
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map map = new HashMap();
		map.put("request", request);
		map.put("response", response);
		servletLocal.set(map);
		return super.execute(mapping, form, request, response);
	}

	protected void setJSonData(Object obj) {
		jsonObject.set(obj);
	}

	public Object getJsonData() {
		return jsonObject.get();
	}

	protected void addError(String name, String msg) {
		this.addError(getRequest(), name, msg);
	}

	protected void addError(HttpServletRequest request, String name, String msg) {
		ActionMessages ams = new ActionMessages();
		ActionMessage am = new ActionMessage(name, msg);
		ams.add(name, am);
		this.addErrors(request, ams);
	}

	protected ActionForward resultForExtForm() throws Exception {
		return resultForExtForm(getRequest(), getResponse());
	}

	protected ActionForward resultForExtForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExtResult er = new ExtResult();
		ActionMessages ams = this.getErrors(request);
		if (ams != null && ams.size() > 0) {
			er.setSuccess(false);
			java.util.Iterator it = ams.get();
			while (it.hasNext()) {
				ActionMessage am = (ActionMessage) it.next();
				Object[] os = am.getValues();
				StringBuffer ss = new StringBuffer("");
				if (os != null) {
					for (Object o : os) {
						ss.append(o.toString()).append(";");
					}
					if (ss.length() > 1)
						ss.deleteCharAt(ss.length() - 1);
				}
				er.getErrors().put(am.getKey(), ss.toString());
			}
		}
		this.setJSonData(er);
		return jsonForward(request, response);
	}

	boolean isGzipInRequest(HttpServletRequest request) {
		String header = request.getHeader("Accept-Encoding");
		return header != null && header.indexOf("gzip") >= 0;
	}

	@Override
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("list");
	}

	protected ActionForward jsonForward() throws Exception {
		return jsonForward(getRequest(), getResponse());
	}

	protected ActionForward jsonForward(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String json = "function(){" + AjaxUtil.getJSON(this.getJsonData(), true) + "}()";
		response.setContentType("application/json;charset=UTF-8");
		boolean gzip = isGzipInRequest(request);
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
			response.setContentLength(json.getBytes("UTF-8").length);
			PrintWriter out = response.getWriter();
			out.print(json);
			out.close();
		}
		return null;
	}

}
