package myapp.mvc;

import java.io.Serializable;
import java.util.List;

import com.easyjf.container.annonation.Action;
import com.easyjf.container.annonation.Inject;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.AbstractCrudAction;
import com.easyjf.web.tools.IPageList;

import myapp.domain.Customer;
import myapp.service.ICustomerService;

/**
 * CustomerAction
 * @author EasyJWeb 1.0-m2
 * $Id: CustomerAction.java,v 0.0.1 2007-12-19 11:36:56 EasyJWeb 1.0-m2 Exp $
 */
@Action
public class CustomerAction extends AbstractCrudAction {
	@Inject
	private ICustomerService service;
	/*
	 * set the current service
	 * return service
	 */
	public void setService(ICustomerService service) {
		this.service = service;
	}
	
	/*
	 * to get the entity class
	 */
	@SuppressWarnings("unchecked")
	protected Class entityClass() {
		return Customer.class;
	}

	/*
	 * to find the entity object
	 */
	protected Object findEntityObject(Serializable id) {
		return service.getCustomer((Long) id);
	}

	/*
	 * to get the entity query
	 * param queryObject
	 * return IPageList
	 */
	protected IPageList queryEntity(IQueryObject queryObject) {		
		return service.getCustomerBy(queryObject);
	}

	/*
	 * to remove an entity
	 * param id
	 */
	protected void removeEntity(Serializable id) {
		service.delCustomer((Long) id);
	}
	
	/*
	 * to batch remove the entities
	 * param ids
	 */
	protected void batchRemoveEntity(List<Serializable> ids) {
		service.batchDelCustomers(ids);
	}

	/*
	 * save object to entity
	 */
	protected void saveEntity(Object object) {
		service.addCustomer((Customer) object);
	}

	/*
	 * update an entited object 
	 */
	protected void updateEntity(Object object) {
		service.updateCustomer(((Customer) object).getId(), (Customer) object);
	}
	
}