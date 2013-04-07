package com.easyjf.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import junit.framework.TestCase;

public class TestFileUtil extends TestCase {
	public void setUp(){
		Date d=new Date();
		System.out.println(d.toString());
	}
	
	public void testFileType(){
		assertTrue(FileUtil.isImgageFile("test.jpg"));
		assertTrue(FileUtil.isImgageFile("test.jpeg"));
		assertTrue(FileUtil.isImgageFile("test.png"));
		assertTrue(FileUtil.isImgageFile("test.gif"));
		assertTrue(FileUtil.isImgageFile("test.jpg"));
		assertTrue(FileUtil.isImgageFile("test.bmp"));
		assertTrue(FileUtil.isImgageFile("test.ico"));
		assertTrue(FileUtil.isAttacheFile("test.doc"));
		assertTrue(FileUtil.isAttacheFile("test.zip"));
		assertTrue(FileUtil.isAttacheFile("test.rar"));
		assertTrue(FileUtil.isAttacheFile("test.pdf"));
		assertTrue(FileUtil.isForbidFile("test.jsp"));
		assertTrue(FileUtil.isForbidFile("test.com"));
		assertTrue(FileUtil.isForbidFile("test.bat"));
		assertTrue(FileUtil.isForbidFile("test.cmd"));
		assertTrue(FileUtil.isExeFile("test.exe"));
		assertTrue(FileUtil.isExeFile("test.com"));
		assertTrue(FileUtil.isExeFile("test.bat"));
		assertTrue(FileUtil.isExeFile("test.cmd"));
	}
	
	public static void testCheckExtFile(){
		assertTrue(FileUtil.checkExtFile("jpg;jpeg;pdf;doc","test.pdf"));
		assertFalse(FileUtil.checkExtFile("jpg;jpeg;pdf","test.exe"));
	}
	
	public void testGetTempFile(){
		assertNotNull(FileUtil.getTempFile("test",".java"));
		assertTrue(FileUtil.getTempFile("test",".java").indexOf(".java")>0);
		//assertTrue(FileUtil.getTempFile("test",".txt").matches("[0-9]{23}[/.txt]"));
		System.out.println(FileUtil.getTempFile("test",".txt"));
		System.out.println(new java.util.Date().getTime());
	}
	
	public void testSaveFile(){
		String str = "test";
		byte by;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = new ByteArrayInputStream(str.getBytes());
		assertTrue(FileUtil.saveFile(in,"test.txt"));
		String fileName = "test.txt";
		File fileObj = new File(fileName);
		try {
			FileInputStream inputFile = new FileInputStream(fileObj);
			while((by=(byte)inputFile.read())!=-1){
				baos.write(by);
			}
			System.out.println(baos.toString());
			assertTrue(str.equals(baos.toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
