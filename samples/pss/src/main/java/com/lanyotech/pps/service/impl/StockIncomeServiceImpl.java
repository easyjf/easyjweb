package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.StockIncome;
import com.lanyotech.pps.service.IStockIncomeService;
import com.lanyotech.pps.dao.IStockIncomeDAO;


/**
 * StockIncomeServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: StockIncomeServiceImpl.java,v 0.0.1 2010-6-13 23:27:23 EasyJWeb 1.0-m2 Exp $
 */
public class StockIncomeServiceImpl implements IStockIncomeService{
	
	private IStockIncomeDAO stockIncomeDao;
	
	public void setStockIncomeDao(IStockIncomeDAO stockIncomeDao){
		this.stockIncomeDao=stockIncomeDao;
	}
	
	public Long addStockIncome(StockIncome stockIncome) {
		stockIncome.countAmount();
		this.stockIncomeDao.save(stockIncome);
		if (stockIncome != null && stockIncome.getId() != null) {
			return stockIncome.getId();
		}
		return null;
	}
	
	public StockIncome getStockIncome(Long id) {
		StockIncome stockIncome = this.stockIncomeDao.get(id);
		return stockIncome;
		}
	
	public boolean delStockIncome(Long id) {	
			StockIncome stockIncome = this.getStockIncome(id);
			if (stockIncome != null) {
				this.stockIncomeDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelStockIncomes(List<Serializable> stockIncomeIds) {
		
		for (Serializable id : stockIncomeIds) {
			delStockIncome((Long) id);
		}
		return true;
	}
	
	public IPageList getStockIncomeBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, StockIncome.class,this.stockIncomeDao);		
	}
	
	public boolean updateStockIncome(Long id, StockIncome stockIncome) {
		if (id != null) {
			stockIncome.setId(id);
		} else {
			return false;
		}
		stockIncome.countAmount();
		this.stockIncomeDao.update(stockIncome);
		return true;
	}

	public boolean delStockIncomeItem(Long id) {
		int ret=this.stockIncomeDao.batchUpdate("delete from StockIncomeItem obj where obj.id=?", new Object[]{id});
		return ret>0;
	}	
	
}
