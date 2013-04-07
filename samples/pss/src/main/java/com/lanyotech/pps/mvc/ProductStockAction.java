package com.lanyotech.pps.mvc;

import com.lanyotech.pps.domain.ProductStock;
import com.lanyotech.pps.service.IProductStockService;

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
 * ProductStockAction
 * @author EasyJWeb 1.0-m2
 * $Id: ProductStockAction.java,v 0.0.1 2010-6-12 23:01:11 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class ProductStockAction extends AbstractPageCmdAction {
	
	@Inject
	private IProductStockService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IProductStockService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.getProductStockBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delProductStock(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		ProductStock object = form.toPo(ProductStock.class);
		if (!hasErrors())
			service.addProductStock(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		ProductStock object = service.getProductStock(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateProductStock(id, object);
		return pageForExtForm(form);
	}
}