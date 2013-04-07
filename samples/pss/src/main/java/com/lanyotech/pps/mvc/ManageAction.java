package com.lanyotech.pps.mvc;

import com.easyjf.web.IWebAction;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.lanyotech.pps.service.UserContext;

public class ManageAction implements IWebAction {
	public Page execute(WebForm form, Module module) throws Exception {
		//System.out.println(UserContext.getUser());
		if(UserContext.getUser()==null){
			return new Page("homePage","/login.html","html");
		}
		else {
			form.addResult("user", UserContext.getUser());
			return new Page("/index.html");
		}
	}
}
