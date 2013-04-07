package com.lanyotech.pps.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.service.UserContext;

public class SercurityFilter implements Filter {
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain)
			throws IOException, ServletException {
		Employee user =(Employee)((HttpServletRequest)request).getSession().getAttribute("CURRENT_USER");
		String path=((HttpServletRequest)request).getServletPath();
		if(user==null && !path.equals("/login.ejf")){
			((HttpServletResponse) response).sendRedirect("login.html");
		}
		else filterChain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
