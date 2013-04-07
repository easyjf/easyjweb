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
package com.easyjf.core.support;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easyjf.util.FileUtil;
import com.easyjf.web.Globals;

public class CmsFileUtil extends FileUtil {
	
	public final static String ATTACHE_FILE_EXT = "doc;zip;rar;pdf;html;htm;";
	
	public final static String VIDIO_FILE_EXT = "wav;wma;wmv;rm;rmvb;mpg;mpeg;asf;avi;swf;";
	
	public static boolean isAttacheFile(String fileName) {
		return checkExtFile(ATTACHE_FILE_EXT, fileName);
	}
	
	public static boolean isVidioFile(String fileName){
		return checkExtFile(VIDIO_FILE_EXT, fileName);
	}
	
	public static String moveFile(String shortPath, String baseDir) {
		File file = new File(Globals.APP_BASE_DIR + shortPath);
		if (file.exists()) {
			String target = Globals.APP_BASE_DIR + baseDir + "/"
					+ file.getName();
			File targetFile = new File(target);
			if(!targetFile.getParentFile().exists())targetFile.getParentFile().mkdirs();
			if (!targetFile.exists()) {
				if (file.renameTo(targetFile)) {
					moveFile(smallPath(shortPath), baseDir);// 移动小图片
					return baseDir.replaceAll("\\\\", "/") + "/"
							+ file.getName();
				}
			}
		}
		return shortPath;
	}

	private static String smallPath(String path) {
		int dotIndex = path.lastIndexOf('.');
		String preffix = path.substring(0, dotIndex);
		String suffix = path.substring(dotIndex);
		String name = preffix + "_small" + suffix;
		return name;
	}

	public static boolean removeFile(String shortPath) {
		File file = new File(Globals.APP_BASE_DIR + shortPath);
		if (file.exists())
			return file.delete();
		return false;
	}

	public static String getBaseDir(String shortPath) {
		int rootLenth = (int) new File(Globals.APP_BASE_DIR).getAbsolutePath()
				.length();
		File file = new File(Globals.APP_BASE_DIR + shortPath);
		String path = "";
		System.out.println(file.getAbsolutePath());
		if (file.exists()) {
			path = file.getParentFile().getAbsolutePath().substring(rootLenth);
		}
		return path;
	}

	public static List getSystemFile(String parent, boolean dirOnly) {
		List list = new ArrayList();
		String baseDir = parent;
		if (baseDir == null || "".equals(baseDir))
			baseDir = "";
		File dir = new File(Globals.APP_BASE_DIR + baseDir);
		int rootLenth = (int) new File(Globals.APP_BASE_DIR).getAbsolutePath()
				.length();
		if (dir.exists() && dir.isDirectory()) {
			if (dir.getAbsolutePath().length() >= rootLenth) {
				File[] ts = dir.listFiles();
				if (ts != null) {
					for (int i = 0; i < ts.length; i++) {
						if (!dirOnly || ts[i].isDirectory()) {
							String path = ts[i].getPath().substring(rootLenth);
							Map map = new java.util.HashMap();
							map.put("id", path);
							map.put("title", path);
							if (ts[i].isDirectory()
									&& ts[i]
											.listFiles(new java.io.FileFilter() {
												public boolean accept(
														File pathname) {
													return pathname
															.isDirectory();
												}

											}).length > 0) {
								List alist = new java.util.ArrayList();
								alist.add(ts[i].list()[0]);
								map.put("children", alist);
							}
							list.add(map);
						}
					}
				}
			}
		}
		return list;
	}
}
