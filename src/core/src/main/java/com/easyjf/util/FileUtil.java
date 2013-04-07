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
package com.easyjf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 
 * <p>
 * Title: 文件处理工具类
 * </p>
 * <p>
 * Description:实现文件的简单处理,判断文件类型、文件复制、目录复制等
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友、天一
 * @version 1.0
 */
abstract public class FileUtil {
	public final static String IMAGE_FILE_EXT = "jpg;jpeg;png;gif;bmp;ico";

	public final static String ATTACHE_FILE_EXT = "txt;doc;docx;xls;zip;rar;pdf,xml";// 附件文件

	public final static String FORBID_FILE_EXT = "jsp;com;bat;cmd";// 禁止的文件

	public final static String EXE_FILE_EXT = "exe;com;bat;cmd";
	
	public static boolean checkExtFile(String ext, String fileName) {
		if (ext == null)
			return false;
		String[] exts = ext.split(";");
		String file = fileName.toLowerCase();
		for (String element : exts)
			if (file.endsWith("." + element))
				return true;
		return false;
	}

	public static String getTempFile(String dir, String fileExt) {
		// String tempFileName = CommUtil.getRandString(8) + fileExt;
		String tempFileName = CommUtil.formatDate("yyMMdd-HHmmssS", new Date())
				+ fileExt;
		File file = new File(dir + "/" + tempFileName);
		if (file.exists())
			return getTempFile(dir, fileExt);
		else
			return tempFileName;
	}

	/**
	 * 上传文件是否是附件允许的类型(doc;xls;zip;rar;pdf)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isAttacheFile(String fileName) {
		return checkExtFile(ATTACHE_FILE_EXT, fileName);
	}

	public static boolean isExeFile(String fileName) {
		return checkExtFile(EXE_FILE_EXT, fileName);
	}

	public static boolean isForbidFile(String fileName) {
		return checkExtFile(FORBID_FILE_EXT, fileName);
	}

	/**
	 * 上传文件是否是图形类文件(jpg;jpeg;png;gif;bmp;ico)
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isImgageFile(String fileName) {
		return checkExtFile(IMAGE_FILE_EXT, fileName);
	}

	public static boolean saveFile(InputStream in, String fileName) {
		File outFile = new File(fileName);
		try {
			FileOutputStream out = new FileOutputStream(outFile);
			byte[] temp = new byte[11024];
			int length = -1;
			while ((length = in.read(temp)) > 0)
				out.write(temp, 0, length);
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	public static void delFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			for (String fileName : file.list()) {
				delFile(path + "/" + fileName);
			}
		}
		file.delete();
	}

	/**
	 * 复制目录下的文件（不包括该目录）到指定目录，会连同子目录一起复制过去。
	 * 
	 * @param targetFile
	 * @param path
	 */
	public static void copyFileFromDir(String targetDir, String path) {
		File file = new File(path);
		createFile(targetDir, false);
		if (file.isDirectory()) {
			copyFileToDir(targetDir, listFile(file));
		}
	}

	/**
	 * 复制目录下的文件（不包含该目录和子目录，只复制目录下的文件）到指定目录。
	 * 
	 * @param targetDir
	 * @param path
	 */
	public static void copyFileOnly(String targetDir, String path) {
		File file = new File(path);
		File targetFile = new File(targetDir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				if (subFile.isFile()) {
					copyFile(targetFile, subFile);
				}
			}
		}
	}

	/**
	 * 复制目录到指定目录。targetDir是目标目录，path是源目录。 该方法会将path以及path下的文件和子目录全部复制到目标目录
	 * 
	 * @param targetDir
	 * @param path
	 */
	public static void copyDir(String targetDir, String path) {
		File targetFile = new File(targetDir);
		createFile(targetFile, false);
		File file = new File(path);
		if (targetFile.isDirectory() && file.isDirectory()) {
			copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
					listFile(file));
		}
	}

	/**
	 * 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
	 * 
	 * @param targetDir
	 * @param filePath
	 */
	public static void copyFileToDir(String targetDir, String... filePath) {
		if (targetDir == null || "".equals(targetDir)) {
			System.out.println("参数错误，目标路径不能为空");
			return;
		}
		File targetFile = new File(targetDir);
		if (!targetFile.exists()) {
			targetFile.mkdir();
		} else {
			if (!targetFile.isDirectory()) {
				System.out.println("参数错误，目标路径指向的不是一个目录！");
				return;
			}
		}
		for (String path : filePath) {
			File file = new File(path);
			if (file.isDirectory()) {
				copyFileToDir(targetDir + "/" + file.getName(), listFile(file));
			} else {
				copyFileToDir(targetDir, file, "");
			}
		}
	}

	/**
	 * 复制文件到指定目录。targetDir是目标目录，file是源文件名，newName是重命名的名字。
	 * 
	 * @param targetFile
	 * @param file
	 * @param newName
	 */
	public static void copyFileToDir(String targetDir, File file, String newName) {
		String newFile = "";
		if (newName != null && !"".equals(newName)) {
			newFile = targetDir + "/" + newName;
		} else {
			newFile = targetDir + "/" + file.getName();
		}
		File tFile = new File(newFile);
		copyFile(tFile, file);
	}

	/**
	 * 复制文件。targetFile为目标文件，file为源文件
	 * 
	 * @param targetFile
	 * @param file
	 */
	public static void copyFile(File targetFile, File file) {
		if (targetFile.exists()) {
			System.out.println("文件" + targetFile.getAbsolutePath()
					+ "已经存在，跳过该文件！");
			return;
		} else {
			createFile(targetFile, true);
		}
		System.out.println("复制文件" + file.getAbsolutePath() + "到"
				+ targetFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while (is.read(buffer) != -1) {
				fos.write(buffer);
			}
			is.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cutFile(String targetFile, String file) {

	}

	public static String[] listFile(File dir) {
		String absolutPath = dir.getAbsolutePath();
		String[] paths = dir.list();
		String[] files = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = absolutPath + "/" + paths[i];
		}
		return files;
	}

	public static void createFile(String path, boolean isFile) {
		createFile(new File(path), isFile);
	}

	public static void createFile(File file, boolean isFile) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createFile(file.getParentFile(), false);
			} else {
				if (isFile) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					file.mkdir();
				}
			}
		}
	}

	public static void main(String[] args) {
		copyDir("i:/t3", "i:/t2");
		// copyFileToDir("i:/t3", "i:/t2/1.txt");
		// File file = new File("i:/t2/1.txt");
		// System.out.println(file.getAbsolutePath());
		// File file2 = new File("i:/t3/1.txt");
		// copyFileToDir("i:/t3/", file, "");
		// File file = new File("i:/t2");
		// for(String filepath:file.list()){
		// System.out.println(filepath);
		// }
	}
}
