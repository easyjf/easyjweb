package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.BaseCount;
import com.lanyotech.pps.service.IBaseCountService;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.ajax.AjaxUtil;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;


/**
 * BaseCountAction
 * @author EasyJWeb 1.0-m2
 * $Id: BaseCountAction.java,v 0.0.1 2010-6-22 0:00:56 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action()
public class BaseCountAction extends AbstractPageCmdAction {
	
	@Inject
	private IBaseCountService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IBaseCountService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getBaseCountBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delBaseCount(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		BaseCount object = form.toPo(BaseCount.class);
		if (!hasErrors())
			service.addBaseCount(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		BaseCount object = service.getBaseCount(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateBaseCount(id, object);
		return pageForExtForm(form);
	}
	public Page doGetSn(WebForm form){
		String entityName=CommUtil.null2String(form.get("entityName"));
		String sn=this.service.getNewSn(entityName);
		form.jsonResult(sn);
		return Page.JSONPage;
	}
}