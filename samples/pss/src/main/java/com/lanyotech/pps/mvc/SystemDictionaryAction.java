package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.SystemDictionary;
import com.lanyotech.pps.service.ISystemDictionaryService;

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
 * SystemDictionaryAction
 * @author EasyJWeb 1.0-m2
 * $Id: SystemDictionaryAction.java,v 0.0.1 2010-6-5 15:28:29 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class SystemDictionaryAction extends AbstractPageCmdAction {
	
	@Inject
	private ISystemDictionaryService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(ISystemDictionaryService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getSystemDictionaryBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delSystemDictionary(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		SystemDictionary object = form.toPo(SystemDictionary.class);
		if (!hasErrors())
			service.addSystemDictionary(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		SystemDictionary object = service.getSystemDictionary(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateSystemDictionary(id, object);
		return pageForExtForm(form);
	}
}