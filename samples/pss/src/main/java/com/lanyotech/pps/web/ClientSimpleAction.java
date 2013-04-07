package com.lanyotech.pps.web;

import java.util.List;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.lanyotech.pps.domain.Client;
import com.lanyotech.pps.service.IClientSimpleService;

@Action
public class ClientSimpleAction extends AbstractPageCmdAction {
	@Inject
	private IClientSimpleService service;

	public Page doIndex(WebForm form, Module module) {
		return doList(form);
	}

	public Page doList(WebForm form) {
		List result = service.findClient(null, null, 0, -1);
		form.addResult("list", result);
		return new Page("clientSimple/list.html");
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.removeClient(id);
		return doList(form);
	}

	public Page doSave(WebForm form) {
		Client object = form.toPo(Client.class);
		if (!hasErrors())
			service.saveClient(object);
		return doList(form);
	}

	public Page doCreate(WebForm form) {
		return new Page("clientSimple/edit.html");
	}
	
	public Page doEdit(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Client object = service.getClient(id);
		form.addResult("obj", object);
		//form.addPo(object);
		return new Page("clientSimple/edit.html");
	}

	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		Client object = service.getClient(id);
		if (!hasErrors())
			service.updateClient(object);
		return doList(form);
	}

	public void setService(IClientSimpleService service) {
		this.service = service;
	}
}
