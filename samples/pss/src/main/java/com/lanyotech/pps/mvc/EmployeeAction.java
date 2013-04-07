package com.lanyotech.pps.mvc;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.service.IEmployeeService;
import com.lanyotech.pps.service.LogicException;


/**
 * EmployeeAction
 * @author EasyJWeb 1.0-m2
 * $Id: EmployeeAction.java,v 0.0.1 2010-6-6 18:34:50 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class EmployeeAction extends AbstractPageCmdAction {
	
	@Inject
	private IEmployeeService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IEmployeeService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getEmployeeBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		//service.delEmployee(id);
		this.addError("msg","不能删除用户!");
		return pageForExtForm(form);
	}
	public Page doChangePassword(WebForm form) {
		String password=CommUtil.null2String(form.get("oldPassword"));
		String newPassword=CommUtil.null2String(form.get("password"));
		try{
		service.changePassword(password,newPassword);
		}
		catch(LogicException e){
			this.addError("oldPassword", e.getMessage());
			this.addError("msg", e.getMessage());
		}
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		Employee object = form.toPo(Employee.class);
		if (!hasErrors())
			service.addEmployee(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Employee object = service.getEmployee(id);
		String password=object.getPassword();
		form.toPo(object, true);
		object.setPassword(password);
		if (!hasErrors())
			service.updateEmployee(id, object);
		return pageForExtForm(form);
	}
}