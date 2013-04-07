package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.SystemDictionaryDetail;
/**
 * SystemDictionaryDetailService
 * @author EasyJWeb 1.0-m2
 * $Id: SystemDictionaryDetailService.java,v 0.0.1 2010-6-5 17:10:19 EasyJWeb 1.0-m2 Exp $
 */
public interface ISystemDictionaryDetailService {
	/**
	 * 保存一个SystemDictionaryDetail，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addSystemDictionaryDetail(SystemDictionaryDetail instance);
	
	/**
	 * 根据一个ID得到SystemDictionaryDetail
	 * 
	 * @param id
	 * @return
	 */
	SystemDictionaryDetail getSystemDictionaryDetail(Long id);
	
	/**
	 * 删除一个SystemDictionaryDetail
	 * @param id
	 * @return
	 */
	boolean delSystemDictionaryDetail(Long id);
	
	/**
	 * 批量删除SystemDictionaryDetail
	 * @param ids
	 * @return
	 */
	boolean batchDelSystemDictionaryDetails(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到SystemDictionaryDetail
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getSystemDictionaryDetailBy(IQueryObject queryObject);
	
	/**
	  * 更新一个SystemDictionaryDetail
	  * @param id 需要更新的SystemDictionaryDetail的id
	  * @param dir 需要更新的SystemDictionaryDetail
	  */
	boolean updateSystemDictionaryDetail(Long id,SystemDictionaryDetail instance);
}
