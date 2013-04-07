package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.Depot;
import com.lanyotech.pps.service.IDepotService;

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
 * DepotAction
 * @author EasyJWeb 1.0-m2
 * $Id: DepotAction.java,v 0.0.1 2010-6-12 19:25:40 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class DepotAction extends AbstractPageCmdAction {
	
	@Inject
	private IDepotService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IDepotService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getDepotBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delDepot(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		Depot object = form.toPo(Depot.class);
		if (!hasErrors())
			service.addDepot(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Depot object = service.getDepot(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateDepot(id, object);
		return pageForExtForm(form);
	}
}