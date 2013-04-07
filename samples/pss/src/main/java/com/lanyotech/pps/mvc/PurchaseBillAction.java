package com.lanyotech.pps.mvc;

import java.util.List;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.ActionUtil;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.util.StringUtils;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.ajax.AjaxUtil;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;
import com.easyjf.web.tools.ListQuery;
import com.easyjf.web.tools.PageList;
import com.lanyotech.pps.domain.PurchaseBill;
import com.lanyotech.pps.domain.PurchaseBillItem;
import com.lanyotech.pps.query.BaseBillQuery;
import com.lanyotech.pps.query.PurchaseItemQuery;
import com.lanyotech.pps.service.IBaseCountService;
import com.lanyotech.pps.service.IPurchaseBillService;


/**
 * PurchaseBillAction
 * @author EasyJWeb 1.0-m2
 * $Id: PurchaseBillAction.java,v 0.0.1 2010-6-13 18:52:19 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class PurchaseBillAction extends AbstractPageCmdAction {
	@Inject
	private IPurchaseBillService service;
	
	@Inject
	private IBaseCountService countService;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IPurchaseBillService service) {
		this.service = service;
	}
	
	public void setCountService(IBaseCountService countService) {
		this.countService = countService;
	}

	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(BaseBillQuery.class);
		IPageList pageList = service.getPurchaseBillBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delPurchaseBill(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		PurchaseBill object = form.toPo(PurchaseBill.class);
		if(StringUtils.hasLength(object.getSn())){
		QueryObject qo=new QueryObject();
		qo.addQuery("sn",object.getSn(),"=");
		}
		
		handleItems(form,object);
		if (!hasErrors()){
			service.addPurchaseBill(object);
			countService.recordNewSn("PurchaseBill", new Integer(object.getSn()));
		}
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		PurchaseBill object = service.getPurchaseBill(id);
		form.toPo(object, true);
		handleItems(form,object);
		if (!hasErrors())
			service.updatePurchaseBill(id, object);
		return pageForExtForm(form);
	}
	public Page doView(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		PurchaseBill object = service.getPurchaseBill(id);
		form.jsonResult(object.toJSonObjectWithItems());
		return Page.JSONPage;
	}
	protected void handleItems(WebForm form, PurchaseBill object) {
		List list = ActionUtil.parseMulitItems(form, PurchaseBillItem.class, new String[] { "id", "product", "price", "num", "amount", "remark" },
				"item_");
		List<Long> deletes=object.updateItems(list);
		if(!deletes.isEmpty()){
			for(Long id:deletes){
			this.service.delPurchaseBillItem(id);
			}
		}
	}
	public Page doStatistics(WebForm form) {
		System.out.println(AjaxUtil.getJSON(11));
		PurchaseItemQuery qo=form.toPo(PurchaseItemQuery.class);
		List list=service.statistics(qo, qo.getGroupBy());
		IPageList pageList=new PageList(new ListQuery(list));
		pageList.doList(-1, 1, "", "");
		form.jsonResult(pageList);
		return Page.JSONPage;
	}
}