package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.PurchaseBill;
import com.lanyotech.pps.query.PurchaseItemQuery;
/**
 * PurchaseBillService
 * @author EasyJWeb 1.0-m2
 * $Id: PurchaseBillService.java,v 0.0.1 2010-6-13 18:52:19 EasyJWeb 1.0-m2 Exp $
 */
public interface IPurchaseBillService {
	/**
	 * 保存一个PurchaseBill，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addPurchaseBill(PurchaseBill instance);
	
	/**
	 * 根据一个ID得到PurchaseBill
	 * 
	 * @param id
	 * @return
	 */
	PurchaseBill getPurchaseBill(Long id);
	
	/**
	 * 删除一个PurchaseBill
	 * @param id
	 * @return
	 */
	boolean delPurchaseBill(Long id);
	
	/**
	 * 批量删除PurchaseBill
	 * @param ids
	 * @return
	 */
	boolean batchDelPurchaseBills(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到PurchaseBill
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getPurchaseBillBy(IQueryObject queryObject);
	
	/**
	  * 更新一个PurchaseBill
	  * @param id 需要更新的PurchaseBill的id
	  * @param dir 需要更新的PurchaseBill
	  */
	boolean updatePurchaseBill(Long id,PurchaseBill instance);
	/**
	 * 删除采购单明细条目
	 * @param id
	 * @return
	 */
	boolean delPurchaseBillItem(Long id);
	
	/**
	 * 采购统计分析
	 * @param query
	 * @param groupBy
	 * @return
	 */
	List<Map> statistics(PurchaseItemQuery query,String groupBy);
}
