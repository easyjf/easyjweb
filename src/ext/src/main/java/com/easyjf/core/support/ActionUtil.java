/*
 * Copyright 2006-2009 the original author or authors.
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
package com.easyjf.core.support;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.easyjf.util.CommUtil;
import com.easyjf.web.ActionContext;
import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.FrameworkEngine;

public class ActionUtil {

	private static final Logger logger = Logger.getLogger(ActionUtil.class);

	// private static final SimpleDateFormat df = new
	// SimpleDateFormat("yyyyMMdd");

	// private static final String SEPARATOR = "/";

	public static WebForm getWebForm() {
		return getForm();
	}

	public static String getUrlType() {
		return ActionContext.getContext().getWebInvocationParam().getUrlType();
	}

	public static IWebAction getAction() {
		return ActionContext.getContext().getWebInvocationParam().getAction();
	}

	public static WebForm getForm() {
		return ActionContext.getContext().getWebInvocationParam().getForm();
	}

	public static Module getModule() {
		return ActionContext.getContext().getWebInvocationParam().getModule();
	}

	public static List<Serializable> processIds(WebForm form) {
		return processIds(form, "");
	}

	public static List<Serializable> processIds(WebForm form, String idsName) {
		if (logger.isDebugEnabled()) {
			logger.debug("processIds(WebForm) - start");
		}
		String key;
		if (idsName.equals("")) {
			key = "mulitId";
		} else {
			key = idsName;
		}
		String mulitId = CommUtil.null2String(form.get(key));
		if (mulitId.endsWith(","))
			mulitId = mulitId.substring(0, mulitId.length() - 1);
		String[] idsStr = mulitId.split(",");
		if (idsStr.length > 0) {
			List<Serializable> ids = new ArrayList<Serializable>(idsStr.length);
			for (String id : idsStr) {
				if (!"".equals(id))
					ids.add(Long.parseLong(id));
			}
			return ids;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("processIds(WebForm) - end");
		}
		return null;
	}

	/**
	 * private static boolean saveFile(InputStream in, String fileName) {
	 * logger.debug("fileName" + fileName); File outFile = new File(fileName);
	 * if (!outFile.getParentFile().exists()) outFile.getParentFile().mkdirs();
	 * try { FileOutputStream out = new FileOutputStream(outFile); byte[] temp =
	 * new byte[11024]; int length = -1; while ((length = in.read(temp)) > 0) {
	 * out.write(temp, 0, length); } out.flush(); out.close(); in.close(); }
	 * catch (Exception e) { logger.error("saveFile(InputStream, String)", e);
	 * 
	 * e.printStackTrace();
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("saveFile(InputStream,
	 * String) - end"); } return false; }
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("saveFile(InputStream,
	 * String) - end"); } return true; }
	 */
	/**
	 * private static InputStream getInputStream(FileItem file) { if
	 * (logger.isDebugEnabled()) { logger.debug("getInputStream(FileItem) -
	 * start"); }
	 * 
	 * try { InputStream returnInputStream = file.getInputStream(); if
	 * (logger.isDebugEnabled()) { logger.debug("getInputStream(FileItem) -
	 * end"); } return returnInputStream; } catch (Exception e) {
	 * logger.error("getInputStream(FileItem)", e);
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("getInputStream(FileItem) -
	 * end"); } return null; } }
	 */
	public static void removeImage(String path) {
		if (path != null) {
			File bigFile = new File(com.easyjf.web.Globals.APP_BASE_DIR + path);
			File smallFile = new File(com.easyjf.web.Globals.APP_BASE_DIR
					+ smallPath(path));
			if (bigFile.exists()) {
				bigFile.delete();
			}
			if (smallFile.exists()) {
				smallFile.delete();
			}
		}
	}

	private static String smallPath(String path) {
		int dotIndex = path.lastIndexOf('.');
		String preffix = path.substring(0, dotIndex);
		String suffix = path.substring(dotIndex);
		String name = preffix + "_small" + suffix;
		return name;
	}

	public static void removeFile(String path) {
		if (path != null) {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
		}

	}

	public String generateOption(String value) {
		return "<option value=" + value + ">" + value + "</option>";
	}

	public static Page download(java.io.File f) {
		try {
			InputStream in = new java.io.FileInputStream(f);
			if (in != null) {
				ActionContext.getContext().getResponse().setContentType(
						"APPLICATION/OCTET-STREAM");
				ActionContext.getContext().getResponse().setContentLength(
						in.available());
				ActionContext.getContext().getResponse().setHeader(
						"Content-Disposition",
						"attachment; filename=\"" + new String(f.getName().getBytes(),"iso8859-1") + "\"");
				byte[] buff = new byte[1000];
				OutputStream out = ActionContext.getContext().getResponse()
						.getOutputStream();
				int c;
				while ((c = in.read(buff, 0, 1000)) > 0) {
					out.write(buff, 0, c);
					out.flush();
				}
				out.close();
				in.close();
			}

		} catch (Exception e) {
			System.out.println("下载错误:" + e.getMessage());
		}
		return Page.nullPage;
	}

	/**
	 * 转换列表数据
	 * 
	 * @param form
	 * @param clz
	 * @return
	 */
	public static List parseMulitItems(WebForm form, Class clz) {
		return parseMulitItems(form, clz, "");
	}

	/**
	 * 把列表数据转换成表格
	 * 
	 * @param form
	 * @param clz
	 * @param prefix
	 * @return
	 */
	public static List parseMulitItems(WebForm form, Class clz, String prefix) {
		Field[] fs = clz.getDeclaredFields();
		String[] fields = new String[fs.length];
		for (int i = 0; i < fs.length; i++)
			fields[i] = fs[i].getName();
		return parseMulitItems(form, clz, fields, prefix);
	}

	/**
	 * 用来把表单中的表格数据转换成对象数组
	 * 
	 * @param form
	 *            form
	 * @param clz
	 *            类名
	 * @param fields
	 *            需要转换的字段
	 * @param prefix
	 *            前缀
	 * @return
	 */
	public static List parseMulitItems(WebForm form, Class clz,
			String[] fields, String prefix) {
		Map datas = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			String[] objs = CommUtil.getStringArray(form
					.get(prefix + fields[i]));
			datas.put(fields[i], objs);
		}
		List list = new java.util.ArrayList();
		String[] objs = (String[]) datas.get(fields[0]);
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				Map map = new java.util.HashMap();
				for (int j = 0; j < fields.length; j++) {
					String[] obj = (String[]) datas.get(fields[j]);
					if (obj != null)
						map.put(fields[j], obj[i]);
				}
				try {
					Object obj = clz.newInstance();					
					FrameworkEngine.form2Obj(map, obj, false, true);
					list.add(obj);
				} catch (Exception e) {
					logger.error("数据转换出错"+e);
				}
			}
		}
		return list;
	}
	
}
