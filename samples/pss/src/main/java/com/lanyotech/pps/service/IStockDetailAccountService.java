package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.StockDetailAccount;
import com.lanyotech.pps.domain.StockIncome;
import com.lanyotech.pps.domain.StockOutcome;
import com.lanyotech.pps.query.StockDetailAccountQuery;
/**
 * StockDetailAccountService
 * @author EasyJWeb 1.0-m2
 * $Id: StockDetailAccountService.java,v 0.0.1 2010-6-14 11:42:51 EasyJWeb 1.0-m2 Exp $
 */
public interface IStockDetailAccountService {
	/**
	 * 保存一个StockDetailAccount，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addStockDetailAccount(StockDetailAccount instance);
	
	/**
	 * 根据一个ID得到StockDetailAccount
	 * 
	 * @param id
	 * @return
	 */
	StockDetailAccount getStockDetailAccount(Long id);
	
	/**
	 * 删除一个StockDetailAccount
	 * @param id
	 * @return
	 */
	boolean delStockDetailAccount(Long id);
	
	/**
	 * 批量删除StockDetailAccount
	 * @param ids
	 * @return
	 */
	boolean batchDelStockDetailAccounts(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到StockDetailAccount
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getStockDetailAccountBy(IQueryObject queryObject);
	
	/**
	  * 更新一个StockDetailAccount
	  * @param id 需要更新的StockDetailAccount的id
	  * @param dir 需要更新的StockDetailAccount
	  */
	boolean updateStockDetailAccount(Long id,StockDetailAccount instance);
	/**
	 * 创建入库明细账
	 * @param object
	 */
	void createStockDetailAccount(StockIncome object);
	
	/**
	 * 创建入库明细账
	 * @param object
	 */
	void createStockDetailAccount(StockOutcome object);
	
	List<Map> statistics(StockDetailAccountQuery query);
	
	List<Map> statisticsTotal(StockDetailAccountQuery query);
}
