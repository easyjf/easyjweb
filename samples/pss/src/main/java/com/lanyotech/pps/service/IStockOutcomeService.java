package com.lanyotech.pps.service;

import java.io.Serializable;
import java.util.List;
import com.easyjf.web.tools.IPageList;
import com.easyjf.core.support.query.IQueryObject;
import com.lanyotech.pps.domain.StockOutcome;
/**
 * StockOutcomeService
 * @author EasyJWeb 1.0-m2
 * $Id: StockOutcomeService.java,v 0.0.1 2010-6-13 23:27:16 EasyJWeb 1.0-m2 Exp $
 */
public interface IStockOutcomeService {
	/**
	 * 保存一个StockOutcome，如果保存成功返回该对象的id，否则返回null
	 * 
	 * @param instance
	 * @return 保存成功的对象的Id
	 */
	Long addStockOutcome(StockOutcome instance);
	
	/**
	 * 根据一个ID得到StockOutcome
	 * 
	 * @param id
	 * @return
	 */
	StockOutcome getStockOutcome(Long id);
	
	/**
	 * 删除一个StockOutcome
	 * @param id
	 * @return
	 */
	boolean delStockOutcome(Long id);
	
	/**
	 * 批量删除StockOutcome
	 * @param ids
	 * @return
	 */
	boolean batchDelStockOutcomes(List<Serializable> ids);
	
	/**
	 * 通过一个查询对象得到StockOutcome
	 * 
	 * @param properties
	 * @return
	 */
	IPageList getStockOutcomeBy(IQueryObject queryObject);
	
	/**
	  * 更新一个StockOutcome
	  * @param id 需要更新的StockOutcome的id
	  * @param dir 需要更新的StockOutcome
	  */
	boolean updateStockOutcome(Long id,StockOutcome instance);
	/**
	 * 删除出库项目明细
	 * @param id
	 * @return
	 */
	boolean delStockOutcomeItem(Long id);
}
