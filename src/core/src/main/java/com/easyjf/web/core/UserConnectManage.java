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
package com.easyjf.web.core;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.easyjf.util.I18n;

/**
 * 
 * <p>
 * Title:用户入侵检测信息
 * </p>
 * <p>
 * Description:用于判断用户刷新情况检查，默认为10秒钟之内连续连接10次为超时
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: www.easyjf.com
 * </p>
 * 
 * @author 蔡世友
 * @version 1.0
 */
public class UserConnectManage {
	private static final Logger logger = (Logger) Logger
			.getLogger(UserConnectManage.class.getName());

	private static int maxFailureTimes = 10;// 最大登录失败次数

	private static long maxFailureInterval = 10000;// 毫秒，达到最大登录次数且在这个时间范围内

	private static long waitInterval = 60000;// 失败后接受连接的等待时间，默认1分钟

	private static int maxOnlineUser = 200;// 同时在线的最大数

	private final static Map users = new HashMap();// 使用ip+userName为key存放用户登录信息UserLoginAuth

	private static Thread checkThread = null;

	private static class CheckTimeOut implements Runnable {
		private Thread parentThread;

		public CheckTimeOut(Thread parentThread) {
			this.parentThread = parentThread;
			synchronized (this) {
				if (checkThread == null) {
					checkThread = new Thread(this);
					// System.out.println("创建一个新线程！");
					checkThread.start();
				}
			}
		}

		public void run() {
			while (true) {
				if (parentThread.isAlive()) {
					try {
						Thread.sleep(2000);
						int i = 0;
						if (users.size() > maxOnlineUser)// 当达到最大用户数时清除
						{
							synchronized (users) {// 执行删除操作
								Iterator it = users.keySet().iterator();
								Set set = new HashSet();
								Date now = new Date();
								while (it.hasNext()) {
									Object key = it.next();
									UserConnect user = (UserConnect) users
											.get(key);
									if (now.getTime()
											- user.getFirstFailureTime()
													.getTime() > maxFailureInterval)// 删除超时的用户
									{
										set.add(key);
										logger.info(I18n.getLocaleMessage("core.web.delete.a.link.overtime") + i);
										i++;
									}
								}
								if (i < 5)// 如果删除少于5个，则强行删除1/2在线记录，牺牲性能的情况下保证内存
								{
									int num = maxOnlineUser / 2;
									it = users.keySet().iterator();
									while (it.hasNext() && i < num) {
										set.add(it.next());
										logger.info(I18n.getLocaleMessage("core.web.delete.a.redundant.link") + i);
										i++;
									}
								}
								users.keySet().removeAll(set);
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					break;
				}
			}
			logger.info("监视程序运行结束！");
		}
	}

	// 通过checkLoginValidate判断是否合法的登录连接，如果合法则继续，非法则执行
	public static boolean checkLoginValidate(String ip, String userName)// 只检查认证失败次数
	{
		boolean ret = true;
		Date now = new Date();
		String key = ip + ":" + userName;
		UserConnect auth = (UserConnect) users.get(key);
		if (auth == null)// 把用户当前的访问信息加入到users容器中
		{
			auth = new UserConnect();
			auth.setIp(ip);
			auth.setUserName(userName);
			auth.setFailureTimes(0);
			auth.setFirstFailureTime(now);
			users.put(key, auth);
			if (checkThread == null)
				new CheckTimeOut(Thread.currentThread());
		} else {
			if (auth.getFailureTimes() > maxFailureTimes) {
				// 如果在限定的时间间隔内，则返回拒绝用户连接的信息
				if ((now.getTime() - auth.getFirstFailureTime().getTime()) < maxFailureInterval) {
					ret = false;
					auth.setStatus(-1);
				} else if (auth.getStatus() == -1
						&& (now.getTime()
								- auth.getFirstFailureTime().getTime() < (maxFailureInterval + waitInterval)))// 重置计数器
				{
					ret = false;
				} else {
					auth.setFailureTimes(0);
					auth.setFirstFailureTime(now);
					auth.setStatus(0);
				}

			}
			// 登录次数加1
			auth.setFailureTimes(auth.getFailureTimes() + 1);
		}
		// System.out.println(key+":"+auth.getFailureTimes()+":"+ret+":"+(now.getTime()-auth.getFirstFailureTime().getTime()));
		return ret;
	}

	public static void reset(String ip, String userName)// 重置用户信息
	{
		Date now = new Date();
		String key = ip + ":" + userName;
		UserConnect auth = (UserConnect) users.get(key);
		if (auth == null)// 把用户当前的访问信息加入到users容器中
		{
			auth = new UserConnect();
			auth.setIp(ip);
			auth.setUserName(userName);
			auth.setFailureTimes(0);
			auth.setFirstFailureTime(now);
			users.put(key, auth);
		} else {
			auth.setFailureTimes(0);
			auth.setFirstFailureTime(now);
		}
	}

	public static void remove(String ip, String userName)// 删除用户在容器中的记录
	{
		String key = ip + ":" + userName;
		users.remove(key);
	}

	public static void clear()// 清空容器中内容
	{
		if (!users.isEmpty())
			users.clear();
	}

	public static long getMaxFailureInterval() {
		return maxFailureInterval;
	}

	public static void setMaxFailureInterval(long maxFailureInterval) {
		UserConnectManage.maxFailureInterval = maxFailureInterval;
	}

	public static int getMaxFailureTimes() {
		return maxFailureTimes;
	}

	public static void setMaxFailureTimes(int maxFailureTimes) {
		UserConnectManage.maxFailureTimes = maxFailureTimes;
	}

	public static int getMaxOnlineUser() {
		return maxOnlineUser;
	}

	public static void setMaxOnlineUser(int maxOnlineUser) {
		UserConnectManage.maxOnlineUser = maxOnlineUser;
	}

	public static long getWaitInterval() {
		return waitInterval;
	}

	public static void setWaitInterval(long waitInterval) {
		UserConnectManage.waitInterval = waitInterval;
	}

	public static void test() {
		System.out.println(I18n.getLocaleMessage("core.web.running"));
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				UserConnectManage.checkLoginValidate("127.0.0." + j, "csy" + j);
			}
			try {
				Thread.sleep(1000);// 暂停1秒
				// return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(I18n.getLocaleMessage("core.web.end.running"));
	}

	

}
