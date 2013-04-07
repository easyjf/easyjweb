package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.Depot;
/**
 * DepotService
 * @author EasyJWeb 1.0-m2
 * $Id: DepotService.java,v 0.0.1 2010-6-12 19:25:40 EasyJWeb 1.0-m2 Exp $
 */
public interface IDepotService {
	/**
	 * 保存一个Depot，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addDepot(Depot instance);
	
	/**
	 * 根据一个ID得到Depot
	 * 
	 * @param id
	 * @return
	 */
	Depot getDepot(Long id);
	
	/**
	 * 删除一个Depot
	 * @param id
	 * @return
	 */
	boolean delDepot(Long id);
	
	/**
	 * 批量删除Depot
	 * @param ids
	 * @return
	 */
	boolean batchDelDepots(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Depot
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getDepotBy(IQueryObject queryObject);
	
	/**
	  * 更新一个Depot
	  * @param id 需要更新的Depot的id
	  * @param dir 需要更新的Depot
	  */
	boolean updateDepot(Long id,Depot instance);
}
