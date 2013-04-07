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
package com.easyjf.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EasyJFServerStatus extends HttpServlet {
	private static final long serialVersionUID = -1102881164133545535L;

	private String getVersion() {
		String driver = "org.apache.derby.jdbc.ClientDriver";
		String dbName = "easyjweb";
		String connectionURL = "jdbc:derby://localhost:1527/" + dbName
				+ ";user=easyjf;pwd=easyjf;";
		String ver = "";
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(connectionURL);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from easyjweb");
			while (rs.next()) {
				ver = rs.getString(1);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return ver;

	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setCharacterEncoding("UTF-8");
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		out
				.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\r\n");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
		out.println("<head><title>EasyJWeb " + this.getVersion()
				+ "</title>\r\n");
		out.println("<style type=\"text/css\">\r\n");
		out.println("#content{\r\n");
		out.println("	clear : both;\r\n");
		out.println("}\r\n");
		out.println("ul,li{\r\n");
		out.println("	list-style-type : none;\r\n");
		out.println("}\r\n");
		out.println("#content .title{\r\n");
		out.println("	font-weight : bold;\r\n");
		out.println("	width : 95%;\r\n");
		out.println("	border : 1px solid #E6E6E6;\r\n");
		out.println("	background-color : #FFFFAF;\r\n");
		out.println("	text-align : left;\r\n");
		out.println("	line-height : 28px;\r\n");
		out.println("	font-size : 12px;\r\n");
		out.println("	padding-left : 30px;\r\n");
		out.println("}\r\n");
		out.println("#content .welcome{\r\n");
		out.println("	padding : 10px 30px 0 30px;\r\n");
		out.println("	font-size : 12px;\r\n");
		out.println("	word-break: break-all;\r\n");
		out.println("	word-wrap:break-word;\r\n");
		out.println("}\r\n");
		out.println("#content #details{\r\n");
		out.println("	border : 1px solid #E6E6E6;\r\n");
		out.println("	background-color : #FAFAFA;\r\n");
		out.println("	padding : 0 30px 0 30px;\r\n");
		out.println("	font-size : 12px;\r\n");
		out.println("	word-wrap:break-word;\r\n");
		out.println("	overflow : hidden;\r\n");
		out.println("}\r\n");
		out.println(".line{\r\n");
		out.println("	border : 1px solid #E6E6E6;\r\n");
		out.println("	height : 1px;\r\n");
		out.println("	width : 95%;\r\n");
		out.println("	clear : both;\r\n");
		out.println("}\r\n");
		out.println(".copyright{\r\n");
		out.println("	clear : both;\r\n");
		out.println("	color : #aaa;\r\n");
		out.println("	text-align : center;\r\n");
		out.println("	font-size : 12px;\r\n");
		out.println("	padding-bottom : 20px;\r\n");
		out.println("}\r\n");
		out.println("</style>\r\r\n");
		out.println("</head>\r\n");
		out.println("<body>\r\n");
		out.println("<div id=\"content\">\r\n");
		out.println("<div class=\"title\">EasyJWeb</div>\r\n");
		out.println("		<ul class=\"welcome\"><li>\r\n");

		out.println("欢迎使用");

		out.println("</li></ul>\r\n");
		out.println("	<div id=\"details\">\r\n");
		out.println("	<p>\r\n");
		out
				.println("	欢迎来到EasyJF <a href=\"http://localhost:82/ejf/person/list\">进入演示</a><br />\r\n");
		out.println("	EasyJWeb " + this.getVersion() + " 使用说明：<br />\r\n");
		out.println("	简易JAVA企业级开发框架，还你简易开发。<br />\r\n");
		out.println("	" + this.getVersion() + "版，增加代码自动生成，本示例为自动生成！<br />\r\n");
		out.println("	1、源码包+lib包<br />\r\n");
		out.println("	<a href=\"http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion() + "/easyjweb-" + this.getVersion()
				+ ".zip\">http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion() + "/easyjweb-" + this.getVersion()
				+ ".zip</a><br />\r\n");
		out.println("	解压到任意目录<br />\r\n");
		out.println("	如：z:/opensource/easyjf/easyjweb-" + this.getVersion()
				+ "<br />\r\n");
		out.println("	<a href=\"http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion()
				+ "/lib.zip\">http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion() + "/lib.zip</a><br />\r\n");
		out.println("	再将lib.zip解压到easyjweb-" + this.getVersion()
				+ "/src/main/webapp/WEB-INF/lib下<br />\r\n");
		out.println("	2、完整包<br />\r\n");
		out.println("	<a href=\"http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion() + "/easyjweb-" + this.getVersion()
				+ "_full.zip\">http://dl.easyjf.com/easyjf/easyjweb/"
				+ this.getVersion() + "/easyjweb-" + this.getVersion()
				+ "_full.zip</a><br />\r\n");
		out.println("	解压到任意目录<br />\r\n");
		out.println("	如：z:/opensource/easyjf/easyjweb-" + this.getVersion()
				+ "<br />\r\n");
		out.println("	3、SVN检出<br />\r\n");
		out
				.println("	<a href=\"http://svn.easyjf.com/repository/easyjf/easyjweb/branches/easyjweb-"
						+ this.getVersion()
						+ "/\" target=\"_blank\">http://svn.easyjf.com/repository/easyjf/easyjweb/branches/easyjweb-"
						+ this.getVersion() + "/</a><br />\r\n");
		out.println("	4、服务器<br />");
		out
				.println("	<a href=\"http://dl.easyjf.com/easyjf/server/easyjf-server-0.0.1.zip\">http://dl.easyjf.com/easyjf/server/easyjf-server-0.0.1.zip</a><br />");
		out.println("	解压到z:/usr/local/easyjf-server-0.0.1<br />");
		out.println("	运行bin\run.bat<br />");
		out.println("	将启动数据库服务器和WEB服务器<br />");
		out
				.println("	浏览器打开<a href=\"http://127.0.0.1:82/status\">http://127.0.0.1:82/status</a>就可以看到本页面了<br />");
		out.println("	5、集成其它应用<br />");
		out
				.println("	修改bin\\web.bat文件com.easyjf.server.JettyServer 82 ..\\easyjf-server-0.0.1 处为你的端口号和WEB应用路径<br />");
		out
				.println("	如：80 <a href=\"z:/opensource/easyjf/easyjweb-0.9.2/src/main/webapp\">z:/opensource/easyjf/easyjweb-0.9.2/src/main/webapp</a> 注意：中间有一空格<br />");
		out.println("	运行run.bat文件，将启动服务器<br />");
		out
				.println("	浏览器打开<a href=\"http://127.0.0.1:80/ejf/person/list\">http://127.0.0.1:80/ejf/person/list</a>就可以看到easyjweb中的示例应用了<br />");
		out
				.println("	更多详细信息请随时关注<a href=\"http://www.easyjf.com\" target=\"_blank\">http://www.easyjf.com</a>官方网站。<br /></p>");
		out.println("	</div>");
		out.println("</div>");
		out.println("<hr class=\"line\" />");
		out.println("<div class=\"copyright\">");
		out.println("	<span>&copy; 2005-2007 EasyJF 简易JAVA开源框架</span>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
