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
package com.easyjf.web.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 实现对ZIP文件进行解压缩
 * 
 * 使用时需要下载ant.jar包导入lib下
 * 
 * @author NetGod
 * 
 */
public class ZipUtils {
	public ZipUtils() {
	}

	/**
	 * zip方法对文件或者文件夹进行压缩
	 * 
	 * @param zipstringPath
	 *            为要压缩的文件或者文件夹路径
	 * @param zipFileName
	 *            为压缩之后生成的文件名
	 */
	public void zip(String zipstringPath, String zipFileName) throws Exception {
		try {

			byte[] buf = new byte[1024];
			File zipPath = new File(zipstringPath);
			int filelen = zipPath.listFiles().length;
			String[] filenames = new String[filelen];
			try {
				File[] files = zipPath.listFiles();
				for (int i = 0; i < filelen; i++) {
					filenames[i] = zipPath.getPath() + File.separator
							+ files[i].getName();
				}
				ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
						zipFileName));
				for (int i = 0; i < filenames.length; i++) {
					FileInputStream in = new FileInputStream(filenames[i]);
					out.putNextEntry(new ZipEntry(files[i].getName()));
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					out.closeEntry();
					in.close();
				}

				out.close();
			} catch (IOException e) {
				System.out.println(e);
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);

		}
	}

	/**
	 * unzip方法对文件进行解压缩
	 * 
	 * @param zipFileName
	 *            需要解压缩的文件名
	 * @param outputDirectory
	 *            解压之后的目标路径
	 */
	public void unzip(String zipFileName, String outputDirectory)
			throws Exception {
		try {
			org.apache.tools.zip.ZipFile zipFile = new org.apache.tools.zip.ZipFile(
					zipFileName);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				System.out.println("unziping:" + zipEntry.getName());
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					System.out.println("OutputPath：" + outputDirectory
							+ File.separator + name);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
					System.out.println("CreateDirectory:" + outputDirectory
							+ File.separator + name);

				} else {
					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					int c;
					byte[] by = new byte[1024];
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
}
