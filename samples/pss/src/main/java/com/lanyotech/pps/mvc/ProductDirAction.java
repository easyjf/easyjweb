package com.lanyotech.pps.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lanyotech.pps.domain.Department;
import com.lanyotech.pps.domain.ProductDir;
import com.lanyotech.pps.service.IProductDirService;

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
 * ProductDirAction
 * @author EasyJWeb 1.0-m2
 * $Id: ProductDirAction.java,v 0.0.1 2010-6-5 17:11:26 EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class ProductDirAction extends AbstractPageCmdAction {
	
	@Inject
	private IProductDirService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(IProductDirService service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		String parent=CommUtil.null2String(form.get("parent"));
		if("root".equals(parent)||"".equals(parent)){
			//只查询顶级节点
			qo.addQuery("obj.parent is EMPTY", null);
		}
		else {
			qo.addQuery("obj.parent.id", new Long(parent), "=");
		}
		IPageList pageList = service.getProductDirBy(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doGetTree(WebForm form){
		QueryObject qo = form.toPo(QueryObject.class);
		String parent=CommUtil.null2String(form.get("parent"));
		if("root".equals(parent)||"".equals(parent)){
			//只查询顶级节点
			qo.addQuery("obj.parent is EMPTY", null);
		}
		else {
			qo.addQuery("obj.parent.id", new Long(parent), "=");
		}
		List list= service.getProductDirBy(qo).getResult();
		List ret=new java.util.ArrayList();
		if(list!=null){
			for(int i=0;i<list.size();i++){
				ProductDir dept=(ProductDir)list.get(i);
				Map map=new HashMap();
				map.put("text", dept.getName());
				map.put("id", dept.getId());
				if(dept.getChildren()==null||dept.getChildren().isEmpty()){
					map.put("leaf", true);
				}
				ret.add(map);
			}
		}
		form.jsonResult(ret);
		return Page.JSONPage;
	}
	
	public Page doRemove(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		service.delProductDir(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		ProductDir object = form.toPo(ProductDir.class);
		if (!hasErrors())
			service.addProductDir(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		Long id = new Long(CommUtil.null2String(form.get("id")));
		ProductDir object = service.getProductDir(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.updateProductDir(id, object);
		return pageForExtForm(form);
	}
}