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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.context.Context;
import com.easyjf.dbo.EasyJDBEngine;

public class PageTemplateProcess implements TemplateProcess {
	private String tableName;

	PageTemplateProcess()
	{
		
	}
	PageTemplateProcess(String tableName)
	{
		this.tableName=tableName;
	}
	public void process(Context context)throws Exception {
		// TODO Auto-generated method stub
		Connection conn = EasyJDBEngine.getInstance().getConnection();		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from " + tableName);
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			List list = new ArrayList(count);
			for (int i = 1; i <= count; i++) {
				Map map = new HashMap();
				map.put("name", meta.getColumnName(i));
				map.put("type", meta.getColumnTypeName(i));
				map.put("lable", meta.getColumnLabel(i));
				map.put("size", new Integer(meta.getColumnDisplaySize(i)));
				list.add(map);
			}
			rs.close();
			stmt.close();
			context.put("fieldList",list);
			System.out.println("域模型的值："+context.get("domain"));
		context.put("tableName",tableName);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
