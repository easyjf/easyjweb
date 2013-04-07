package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.Client;
/**
 * ClientService
 * @author EasyJWeb 1.0-m2
 * $Id: ClientService.java,v 0.0.1 2010-6-12 19:25:45 EasyJWeb 1.0-m2 Exp $
 */
public interface IClientService {
	/**
	 * 保存一个Client，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addClient(Client instance);
	
	/**
	 * 根据一个ID得到Client
	 * 
	 * @param id
	 * @return
	 */
	Client getClient(Long id);
	
	/**
	 * 删除一个Client
	 * @param id
	 * @return
	 */
	boolean delClient(Long id);
	
	/**
	 * 批量删除Client
	 * @param ids
	 * @return
	 */
	boolean batchDelClients(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到Client
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getClientBy(IQueryObject queryObject);
	
	/**
	  * 更新一个Client
	  * @param id 需要更新的Client的id
	  * @param dir 需要更新的Client
	  */
	boolean updateClient(Long id,Client instance);
}
