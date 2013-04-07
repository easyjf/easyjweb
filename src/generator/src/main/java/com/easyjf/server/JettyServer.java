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
 
package com.easyjf.server;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyServer {
	private static JettyServer instance;
	private final static Object keyForEasyJFJetty = new Object();

	public static JettyServer getInstance() {
		if (JettyServer.instance == null) {
			synchronized (JettyServer.keyForEasyJFJetty) {
				if (JettyServer.instance == null) {
					JettyServer.instance = new JettyServer();
				}
			}
		}
		return JettyServer.instance;
	}

	private JettyServer() {

	}

	public static void main(String[] s) {
		JettyServer js = new JettyServer();
		js.run(s);
	}

	public void run(String... s) {
		int port = 82;
		// String webapp = "../webapp";
		String webapp = "src/main/webapp";
		String contextPath = "/";

		if (s.length == 1) {
			port = Integer.parseInt(s[0]);
		}
		if (s.length == 2) {
			port = Integer.parseInt(s[0]);
			webapp = s[1];
		}

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		Server server = new Server();
		Connector connector = new SelectChannelConnector();
		connector.setPort(port);
		server.addConnector(connector);
		WebAppContext wac = new WebAppContext();
		wac.setContextPath(contextPath);
		wac.setWar(webapp);
		String[] welcomeFile = { "index.ejf", "index.html" };
		wac.setWelcomeFiles(welcomeFile);
		server.setHandler(wac);
		server.setStopAtShutdown(true);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("成功启动WEB服务器，默认监听端口" + port);
		System.out.println("可以用http://127.0.0.1:" + port + contextPath
				+ "status查看服务是否正常");
		System.out.println("按Ctrl+C键终止服务.");
	}
}*/