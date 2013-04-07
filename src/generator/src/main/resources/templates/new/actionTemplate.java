package $!{packageName}.mvc;

import java.io.Serializable;
import java.util.List;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.AbstractCrudAction;
import com.easyjf.web.tools.IPageList;
import com.easyjf.container.annonation.Inject;
import $!{packageName}.domain.$!{domainName};
import $!{packageName}.service.I$!{domainName}Service;
##set ($domain = $!domainName.toLowerCase())

/**
 * $!{domainName}Action
 * @author EasyJweb
 * $Id: $!{domainName}Action.java,v 0.0.1 $!{nowTime} EasyJweb Exp $
 */
public class $!{domainName}Action extends AbstractCrudAction {
	@Inject
	private I$!{domainName}Service service;
	
	/*
	 * set the current service
	 * return service
	 */
	public void setService(I$!{domainName}Service service) {
		this.service = service;
	}
	
	/*
	 * to get the entity class
	 */
	@SuppressWarnings("unchecked")
	protected Class entityClass() {
		return $!{domainName}.class;
	}

	/*
	 * to find the entity object
	 */
	protected Object findEntityObject(Serializable id) {
		return service.get$!{domainName}((Long) id);
	}

	/*
	 * to get the entity query
	 * param queryObject
	 * return IPageList
	 */
	protected IPageList queryEntity(IQueryObject queryObject) {
		return service.get$!{domainName}By(queryObject);
	}

	/*
	 * to remove an entity
	 * param id
	 */
	protected void removeEntity(Serializable id) {
		service.del$!{domainName}((Long) id);
	}
	
	/*
	 * to batch remove the entities
	 * param ids
	 */
	protected void batchRemoveEntity(List<Serializable> ids) {
		service.batchDel$!{domainName}s(ids);
	}

	/*
	 * save object to entity
	 */
	protected void saveEntity(Object object) {
		service.add$!{domainName}(($!{domainName}) object);
	}

	/*
	 * update an entited object 
	 */
	protected void updateEntity(Object object) {
		service.update$!{domainName}((($!{domainName}) object).getId(), ($!{domainName}) object);
	}
}