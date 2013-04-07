package myapp.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import myapp.domain.Customer;
/**
 * CustomerService
 * @author EasyJWeb 1.0-m2
 * $Id: CustomerService.java,v 0.0.1 2007-12-19 11:36:56 EasyJWeb 1.0-m2 Exp $
 */
public interface ICustomerService {
	/**
	 * 保存一个Customer，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addCustomer(Customer instance);
	
	/**
	 * 根据一个ID得到Customer
	 * 
	 * @param id
	 * @return
	 */
	Customer getCustomer(Long id);
	
	/**
	 * 删除一个Customer
	 * @param id
	 * @return
	 */
	boolean delCustomer(Long id);
	
	/**
	 * 批量删除Customer
	 * @param ids
	 * @return
	 */
	boolean batchDelCustomers(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Customer
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getCustomerBy(IQueryObject queryObject);
	
	/**
	  * 更新一个Customer
	  * @param id 需要更新的Customer的id
	  * @param dir 需要更新的Customer
	  */
	boolean updateCustomer(Long id,Customer instance);
}
