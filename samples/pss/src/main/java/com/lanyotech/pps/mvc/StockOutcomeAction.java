package com.lanyotech.pps.mvc;

import java.util.Date;
import java.util.List;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.ActionUtil;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.StockOutcome;
import com.lanyotech.pps.domain.StockOutcomeItem;
import com.lanyotech.pps.query.BaseBillQuery;
import com.lanyotech.pps.service.IBaseCountService;
import com.lanyotech.pps.service.IProductStockService;
import com.lanyotech.pps.service.IStockDetailAccountService;
import com.lanyotech.pps.service.IStockOutcomeService;
import com.lanyotech.pps.service.UserContext;


/**
 * StockOutcomeAction
 * @author EasyJWeb 1.0-m2
 * $Id: StockOutcomeAction.java,v 0.0.1 2010-6-13 23:27:16 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class StockOutcomeAction extends AbstractPageCmdAction {
	@Inject
	private IStockOutcomeService service;
	@Inject
	private IStockDetailAccountService accountService;
	@Inject
	private IProductStockService productStockService;
	@Inject
	private IBaseCountService countService;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IStockOutcomeService service) {
		this.service = service;
	}
	
	public void setAccountService(IStockDetailAccountService accountService) {
		this.accountService = accountService;
	}

	public void setProductStockService(IProductStockService productStockService) {
		this.productStockService = productStockService;
	}

	public void setCountService(IBaseCountService countService) {
		this.countService = countService;
	}

	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(BaseBillQuery.class);
		IPageList pageList = service.getStockOutcomeBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delStockOutcome(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		StockOutcome object = form.toPo(StockOutcome.class);
		handleItems(form,object);
		if (!hasErrors()){
			service.addStockOutcome(object);
			countService.recordNewSn("StockOutcome", new Integer(object.getSn()));
		}
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		StockOutcome object = service.getStockOutcome(id);
		form.toPo(object, true);
		handleItems(form,object);
		if (!hasErrors())
			service.updateStockOutcome(id, object);
		return pageForExtForm(form);
	}
	public Page doView(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		StockOutcome object = service.getStockOutcome(id);
		form.jsonResult(object.toJSonObjectWithItems());
		return Page.JSONPage;
	}
	protected void handleItems(WebForm form, StockOutcome object) {
		List list = ActionUtil.parseMulitItems(form, StockOutcomeItem.class, new String[] { "id", "product", "price","salePrice", "num", "amount","saleAmount", "remark" },
				"item_");
		List<Long> deletes=object.updateItems(list);
		if(!deletes.isEmpty()){
			for(Long id:deletes){
			this.service.delStockOutcomeItem(id);
			}
		}
	}
	public Page doAudit(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		StockOutcome object = service.getStockOutcome(id);
		if(object.getAuditing()==null || !object.getAuditing()){
			accountService.createStockDetailAccount(object);
			productStockService.changeProductStock(object);
			object.setAuditing(true);
			object.setAuditTime(new Date());
			object.setAuditor(UserContext.getUser());
			this.service.updateStockOutcome(object.getId(), object);
		}
		else {
			this.addError("msg", "该单据已经审核，无法重复审核!");
		}
		return this.pageForExtForm(form);
	}
}