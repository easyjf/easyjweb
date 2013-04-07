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
package com.easyjf.web.tools.generator;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.dbo.EasyJDBEngine;

abstract public class GeneratorUtil {
public static List jdbcField2Java(String tableName) throws Exception
{
	List list=null;
	Connection conn = EasyJDBEngine.getInstance().getConnection();	
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from " + tableName);
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		list = new ArrayList(count);
		for (int i = 1; i <= count; i++) {
			Map map = new HashMap();				
			String name= meta.getColumnName(i);
			map.put("name",name);
			map.put("type", GeneratorUtil.jdbcType2Java(meta.getColumnClassName(i)));
			map.put("method",name.substring(0,1).toUpperCase()+name.substring(1));				
			map.put("lable", meta.getColumnLabel(i));
			map.put("size", new Integer(meta.getColumnDisplaySize(i)));
			list.add(map);
		}
		rs.close();
		stmt.close();
	
	return list;
}
  public static String jdbcType2Java(String sqlType) {
		String ret = sqlType.replaceAll("java\\.lang\\.", "");
		if (ret.equals("java.sql.Timestamp"))
			ret = "java.util.Date";
		/**
		 * 以此类推，进行更多的转换操作
		 */
		return ret;
	}
	public static String getRealTemplaeDir(String templateDir)
	{
		String mainDir = new File(System.getProperty("user.dir"))
		.getParentFile().getAbsolutePath();
		return new File(mainDir, templateDir).getAbsolutePath();
	}	
	public static void registSystemGenerator(java.util.Map map)
	{
		map.put("editPage",GeneratorWebEditPage.class);
		map.put("listPage", GeneratorWebListPage.class);
		map.put("action",GeneratorWebAction.class);
		map.put("bean",GeneratorDomainBean.class);
		List crds=new java.util.ArrayList();		
		crds.add(GeneratorWebListPage.class);
		crds.add(GeneratorWebEditPage.class);
		crds.add(GeneratorWebAction.class);
		crds.add(GeneratorDomainBean.class);
		map.put("crud",crds);
	}
	public static String getArgKey(String arg)
	{
		int endPrefix = arg.indexOf('=');		
		String prefix =null;
		if(endPrefix>0)prefix=arg.substring(1, arg.indexOf('='));
		return prefix;
	}
	public static String getArgValue(String arg)
	{		
		int endPrefix = arg.indexOf('=');
		return (endPrefix>0?arg.substring(endPrefix+1):arg);		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
