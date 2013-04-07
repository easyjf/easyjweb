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
import com.lanyotech.pps.domain.SystemDictionaryDetail;
import com.lanyotech.pps.service.ISystemDictionaryDetailService;


/**
 * SystemDictionaryDetailAction
 * @author EasyJWeb 1.0-m2
 * $Id: SystemDictionaryDetailAction.java,v 0.0.1 2010-6-5 17:10:19 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class SystemDictionaryDetailAction extends AbstractPageCmdAction {
	
	@Inject
	private ISystemDictionaryDetailService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(ISystemDictionaryDetailService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		String parent=CommUtil.null2String(form.get("parent"));
		String parentSn=CommUtil.null2String(form.get("parentSn"));
		if(!"".equals(parent)){
			qo.addQuery("obj.parent.id",new Long(parent),"=");
		}
		if(!"".equals(parentSn)){
			qo.addQuery("obj.parent.sn",parentSn,"=");
		}
		IPageList pageList = service.getSystemDictionaryDetailBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delSystemDictionaryDetail(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		SystemDictionaryDetail object = form.toPo(SystemDictionaryDetail.class);
		if (!hasErrors())
			service.addSystemDictionaryDetail(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		SystemDictionaryDetail object = service.getSystemDictionaryDetail(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateSystemDictionaryDetail(id, object);
		return pageForExtForm(form);
	}
}