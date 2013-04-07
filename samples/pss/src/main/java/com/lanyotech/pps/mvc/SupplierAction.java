package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.Supplier;
import com.lanyotech.pps.service.ISupplierService;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;


/**
 * SupplierAction
 * @author EasyJWeb 1.0-m2
 * $Id: SupplierAction.java,v 0.0.1 2010-6-12 19:25:50 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class SupplierAction extends AbstractPageCmdAction {
	
	@Inject
	private ISupplierService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(ISupplierService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getSupplierBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delSupplier(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		Supplier object = form.toPo(Supplier.class);
		if (!hasErrors())
			service.addSupplier(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Supplier object = service.getSupplier(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateSupplier(id, object);
		return pageForExtForm(form);
	}
}