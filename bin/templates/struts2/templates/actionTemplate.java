package $!{packageName}.mvc;

import $!{packageName}.domain.$!{domainName};
import $!{packageName}.service.I$!{domainName}Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.easyjf.beans.BeanUtils;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.util.CommUtil;
import com.easyjf.web.struts2.JsonCrudAction;
import com.easyjf.web.tools.IPageList;


##set ($domain = $!domainName.toLowerCase())

/**
 * $!{domainName}Action
 * @author EasyJWeb 1.0-m2
 * $Id: $!{domainName}Action.java,v 0.0.1 $!{nowTime} EasyJWeb 1.0-m3 with ExtJS Exp $
 */
@SuppressWarnings("serial")
@Scope("prototype")
@Controller("$!{domain}Action")
public class $!{domainName}Action extends  JsonCrudAction<$!{domainName}>  {
	
	@Autowired
	private I$!{domainName}Service service;
	private $!{domainName} model;
	private $!{idType} $!{idName};
	/*
	 * set the current service
	 * return service
	 */
	public void setService(I$!{domainName}Service service) {
		this.service = service;
	}
	
	public void setModel($!{domainName} model) {
		this.model = model;
	}

	public $!{domainName} getModel() {
		return model;
	}
	
	public void prepare() throws Exception {
		id=BeanUtils.convertType(getParameter("$!{idName}"), $!{idType}.class);
		if(id!=null){
			model = service.get$!{domainName}(id);
		}
		else model=new $!{domainName}();
	}
	public String list() {
		QueryObject qo = new QueryObject();
		CommUtil.map2obj(getRequest().getParameterMap(), qo);
		IPageList pageList = service.get$!{domainName}By(qo);
		setJSonData(pageList);
		return JSONResult;
	}

	public String remove() {
		service.del$!{domainName}($!{idName});
		return resultForExtForm();
	}

	public String save() {
		$!{domainName} object =getModel();
		if (!hasErrors())
			service.add$!{domainName}(object);
		return resultForExtForm();
	}
	
	public String update() {
		$!{domainName} object =getModel();
		if (!hasErrors())
			service.update$!{domainName}($!{idName}, object);
		return resultForExtForm();
	}
}