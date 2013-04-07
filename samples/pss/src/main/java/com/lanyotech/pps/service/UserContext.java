package com.lanyotech.pps.service;

import javax.servlet.http.HttpSession;

import com.easyjf.web.ActionContext;
import com.lanyotech.pps.domain.Employee;

public class UserContext {
	public static Employee getUser() {
		HttpSession session = ActionContext.getContext().getSession();
		if(session!=null){
			return (Employee)session.getAttribute("CURRENT_USER");
		}
		return null;
	}

	public  static void setUser(Employee user) {
		HttpSession session = ActionContext.getContext().getSession();
		if(session!=null){
			session.setAttribute("CURRENT_USER", user);
		}
	}
}
