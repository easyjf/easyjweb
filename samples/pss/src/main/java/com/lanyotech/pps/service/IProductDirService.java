package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.ProductDir;
/**
 * ProductDirService
 * @author EasyJWeb 1.0-m2
 * $Id: ProductDirService.java,v 0.0.1 2010-6-5 17:11:26 EasyJWeb 1.0-m2 Exp $
 */
public interface IProductDirService {
	/**
	 * 保存一个ProductDir，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addProductDir(ProductDir instance);
	
	/**
	 * 根据一个ID得到ProductDir
	 * 
	 * @param id
	 * @return
	 */
	ProductDir getProductDir(Long id);
	
	/**
	 * 删除一个ProductDir
	 * @param id
	 * @return
	 */
	boolean delProductDir(Long id);
	
	/**
	 * 批量删除ProductDir
	 * @param ids
	 * @return
	 */
	boolean batchDelProductDirs(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到ProductDir
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getProductDirBy(IQueryObject queryObject);
	
	/**
	  * 更新一个ProductDir
	  * @param id 需要更新的ProductDir的id
	  * @param dir 需要更新的ProductDir
	  */
	boolean updateProductDir(Long id,ProductDir instance);
}
