package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.Client;
import com.lanyotech.pps.service.IClientService;

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
 * ClientAction
 * @author EasyJWeb 1.0-m2
 * $Id: ClientAction.java,v 0.0.1 2010-6-12 19:25:45 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class ClientAction extends AbstractPageCmdAction {
	
	@Inject
	private IClientService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IClientService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getClientBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delClient(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		Client object = form.toPo(Client.class);
		if (!hasErrors())
			service.addClient(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Client object = service.getClient(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateClient(id, object);
		return pageForExtForm(form);
	}
}