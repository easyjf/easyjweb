package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.SystemDictionary;
/**
 * SystemDictionaryService
 * @author EasyJWeb 1.0-m2
 * $Id: SystemDictionaryService.java,v 0.0.1 2010-6-5 15:28:28 EasyJWeb 1.0-m2 Exp $
 */
public interface ISystemDictionaryService {
	/**
	 * 保存一个SystemDictionary，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addSystemDictionary(SystemDictionary instance);
	
	/**
	 * 根据一个ID得到SystemDictionary
	 * 
	 * @param id
	 * @return
	 */
	SystemDictionary getSystemDictionary(Long id);
	
	/**
	 * 删除一个SystemDictionary
	 * @param id
	 * @return
	 */
	boolean delSystemDictionary(Long id);
	
	/**
	 * 批量删除SystemDictionary
	 * @param ids
	 * @return
	 */
	boolean batchDelSystemDictionarys(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到SystemDictionary
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getSystemDictionaryBy(IQueryObject queryObject);
	
	/**
	  * 更新一个SystemDictionary
	  * @param id 需要更新的SystemDictionary的id
	  * @param dir 需要更新的SystemDictionary
	  */
	boolean updateSystemDictionary(Long id,SystemDictionary instance);
}
