package $!{packageName}.mvc;

import $!{packageName}.domain.$!{domainName};
import $!{packageName}.service.I$!{domainName}Service;

import com.easyjf.beans.BeanUtils;
import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.Module;
import com.easyjf.web.Page;
import com.easyjf.web.WebForm;
import com.easyjf.web.core.AbstractPageCmdAction;
import com.easyjf.web.tools.IPageList;

##set ($domain = $!domainName.toLowerCase())

/**
 * $!{domainName}Action
 * @author EasyJWeb 1.0-m2
 * $Id: $!{domainName}Action.java,v 0.0.1 $!{nowTime} EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@Action
public class $!{domainName}Action extends AbstractPageCmdAction {
	
	@Inject
	private I$!{domainName}Service service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(I$!{domainName}Service service) {
		this.service = service;
	}
	
	public Page doIndex(WebForm f, Module m) {
		return page("list");
	}

	public Page doList(WebForm form) {
		QueryObject qo = form.toPo(QueryObject.class);
		IPageList pageList = service.get$!{domainName}By(qo);
		form.jsonResult(pageList);
		return Page.JSONPage;
	}

	public Page doRemove(WebForm form) {
		${idType} id=BeanUtils.convertType(form.get("$!{idName}"), ${idType}.class);
		service.del$!{domainName}(id);
		return pageForExtForm(form);
	}

	public Page doSave(WebForm form) {
		$!{domainName} object = form.toPo($!{domainName}.class);
		if (!hasErrors())
			service.add$!{domainName}(object);
		return pageForExtForm(form);
	}
	
	public Page doUpdate(WebForm form) {
		${idType} id=(${idType})BeanUtils.convertType(form.get("$!{idName}"), ${idType}.class);
		$!{domainName} object = service.get$!{domainName}(id);
		form.toPo(object, true);
		if (!hasErrors())
			service.update$!{domainName}(id, object);
		return pageForExtForm(form);
	}
}