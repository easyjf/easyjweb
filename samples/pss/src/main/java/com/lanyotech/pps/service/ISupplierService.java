package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.Supplier;
/**
 * SupplierService
 * @author EasyJWeb 1.0-m2
 * $Id: SupplierService.java,v 0.0.1 2010-6-12 19:25:50 EasyJWeb 1.0-m2 Exp $
 */
public interface ISupplierService {
	/**
	 * 保存一个Supplier，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addSupplier(Supplier instance);
	
	/**
	 * 根据一个ID得到Supplier
	 * 
	 * @param id
	 * @return
	 */
	Supplier getSupplier(Long id);
	
	/**
	 * 删除一个Supplier
	 * @param id
	 * @return
	 */
	boolean delSupplier(Long id);
	
	/**
	 * 批量删除Supplier
	 * @param ids
	 * @return
	 */
	boolean batchDelSuppliers(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Supplier
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getSupplierBy(IQueryObject queryObject);
	
	/**
	  * 更新一个Supplier
	  * @param id 需要更新的Supplier的id
	  * @param dir 需要更新的Supplier
	  */
	boolean updateSupplier(Long id,Supplier instance);
}
