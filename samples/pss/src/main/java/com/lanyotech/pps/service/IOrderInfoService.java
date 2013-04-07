package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.OrderInfo;
import com.lanyotech.pps.query.OrderInfoItemQuery;
/**
 * OrderInfoService
 * @author EasyJWeb 1.0-m2
 * $Id: OrderInfoService.java,v 0.0.1 2010-6-13 0:16:14 EasyJWeb 1.0-m2 Exp $
 */
public interface IOrderInfoService {
	/**
	 * 保存一个OrderInfo，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addOrderInfo(OrderInfo instance);
	
	/**
	 * 根据一个ID得到OrderInfo
	 * 
	 * @param id
	 * @return
	 */
	OrderInfo getOrderInfo(Long id);
	
	/**
	 * 删除一个OrderInfo
	 * @param id
	 * @return
	 */
	boolean delOrderInfo(Long id);
	
	/**
	 * 批量删除OrderInfo
	 * @param ids
	 * @return
	 */
	boolean batchDelOrderInfos(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到OrderInfo
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getOrderInfoBy(IQueryObject queryObject);
	
	/**
	  * 更新一个OrderInfo
	  * @param id 需要更新的OrderInfo的id
	  * @param dir 需要更新的OrderInfo
	  */
	boolean updateOrderInfo(Long id,OrderInfo instance);
	
	/**
	 * 删除订单明细条目
	 * @param id
	 * @return
	 */
	boolean delOrderInfoItem(Long id);
	/**
	 * 销售统计
	 * @param query
	 * @param groupBy
	 * @return
	 */
	List<Map> statistics(OrderInfoItemQuery query,String groupBy);
}
