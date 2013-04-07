package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.beans.BeanUtils;
import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.dao.IStockDetailAccountDAO;
import com.lanyotech.pps.domain.Depot;
import com.lanyotech.pps.domain.Product;
import com.lanyotech.pps.domain.StockDetailAccount;
import com.lanyotech.pps.domain.StockIncome;
import com.lanyotech.pps.domain.StockIncomeItem;
import com.lanyotech.pps.domain.StockOutcome;
import com.lanyotech.pps.domain.StockOutcomeItem;
import com.lanyotech.pps.query.StockDetailAccountQuery;
import com.lanyotech.pps.service.IStockDetailAccountService;


/**
 * StockDetailAccountServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: StockDetailAccountServiceImpl.java,v 0.0.1 2010-6-14 11:42:52 EasyJWeb 1.0-m2 Exp $
 */
public class StockDetailAccountServiceImpl implements IStockDetailAccountService{
	private IStockDetailAccountDAO stockDetailAccountDao;
	
	public void setStockDetailAccountDao(IStockDetailAccountDAO stockDetailAccountDao){
		this.stockDetailAccountDao=stockDetailAccountDao;
	}
	
	public Long addStockDetailAccount(StockDetailAccount stockDetailAccount) {	
		this.stockDetailAccountDao.save(stockDetailAccount);
		if (stockDetailAccount != null && stockDetailAccount.getId() != null) {
			return stockDetailAccount.getId();
		}
		return null;
	}
	
	public StockDetailAccount getStockDetailAccount(Long id) {
		StockDetailAccount stockDetailAccount = this.stockDetailAccountDao.get(id);
		return stockDetailAccount;
		}
	
	public boolean delStockDetailAccount(Long id) {	
			StockDetailAccount stockDetailAccount = this.getStockDetailAccount(id);
			if (stockDetailAccount != null) {
				this.stockDetailAccountDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelStockDetailAccounts(List<Serializable> stockDetailAccountIds) {
		
		for (Serializable id : stockDetailAccountIds) {
			delStockDetailAccount((Long) id);
		}
		return true;
	}
	
	public IPageList getStockDetailAccountBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, StockDetailAccount.class,this.stockDetailAccountDao);		
	}
	
	public boolean updateStockDetailAccount(Long id, StockDetailAccount stockDetailAccount) {
		if (id != null) {
			stockDetailAccount.setId(id);
		} else {
			return false;
		}
		this.stockDetailAccountDao.update(stockDetailAccount);
		return true;
	}

	public void createStockDetailAccount(StockIncome object) {
		for(StockIncomeItem item:object.getItems()){
			StockDetailAccount sda=new StockDetailAccount();
			sda.setDebitOrCredit(1);//入库
			sda.setAmount(item.getAmount());
			sda.setNum(item.getNum());
			sda.setPrice(item.getPrice());
			sda.setProduct(item.getProduct());
			sda.setDepot(item.getBill().getDepot());
			sda.setTypes("1"+item.getBill().getTypes());
			sda.setBillId(item.getBill().getId());
			sda.setBillSn(item.getBill().getSn());
			sda.setBillItemId(item.getId());
			sda.setVdate(item.getBill().getVdate());
			this.addStockDetailAccount(sda);
		}
	}

	public void createStockDetailAccount(StockOutcome object) {
		for(StockOutcomeItem item:object.getItems()){
			StockDetailAccount sda=new StockDetailAccount();
			sda.setDebitOrCredit(-1);//入库
			sda.setAmount(item.getAmount());
			sda.setNum(item.getNum());
			sda.setPrice(item.getPrice());
			sda.setProduct(item.getProduct());
			sda.setDepot(item.getBill().getDepot());
			sda.setTypes("0"+item.getBill().getTypes());
			sda.setBillId(item.getBill().getId());
			sda.setBillSn(item.getBill().getSn());
			sda.setBillItemId(item.getId());
			sda.setVdate(item.getBill().getVdate());
			this.addStockDetailAccount(sda);
		}
	}
	
	public List<Map> statistics(StockDetailAccountQuery query) {
		String depotSql="select obj.depot_id,obj.product_id from stockdetailaccount obj where "+query.getQuery()+" group by obj.depot_id,obj.product_id";
		List list=this.stockDetailAccountDao.executeNativeQuery(depotSql, query.getParameters().toArray(), 0, -1);
		if(list!= null){
			for(int i=0;i<list.size();i++){
				Object[] os=(Object[])list.get(i);
				Long depotId=((Number)os[0]).longValue(),productId=((Number)os[1]).longValue();
				BigDecimal initNum = new BigDecimal(0), initAmount = new BigDecimal(0), inNum = new BigDecimal(
						0), inAmount = new BigDecimal(0), outNum = new BigDecimal(0), outAmount = new BigDecimal(
						0), endNum = new BigDecimal(0), endAmount = new BigDecimal(0);
				//查询期初数
				if(query.getVdate1()!=null){
				String sql="SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums from stockdetailaccount obj where obj.depot_id=? and obj.product_id and obj.vdate<?";
				List ret=this.stockDetailAccountDao.executeNativeQuery(sql, new Object[]{depotId,productId,query.getVdate1()}, 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					initNum=new  BigDecimal(ros[1].toString());
					initAmount=new  BigDecimal(ros[0].toString());
				}
				}
				
				//查询本期增加
				StockDetailAccountQuery saq1=new StockDetailAccountQuery();
				saq1.setVdate1(query.getVdate1());
				saq1.setVdate2(query.getVdate2());
				saq1.addQuery("obj.depot_id",depotId,"=");
				saq1.addQuery("obj.product_id",productId,"=");
				String sql="SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums from stockdetailaccount obj where ";
				List ret=this.stockDetailAccountDao.executeNativeQuery(sql+" obj.debitOrCredit=1 and  "+saq1.getQuery(), saq1.getParameters().toArray(), 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					if(ros[1]!=null)inNum=new  BigDecimal(ros[1].toString());
					if(ros[0]!=null)inAmount=new  BigDecimal(ros[0].toString());
				}
				
				//查询本期减少
				StockDetailAccountQuery saq2=new StockDetailAccountQuery();
				saq2.setVdate1(query.getVdate1());
				saq2.setVdate2(query.getVdate2());
				saq2.addQuery("obj.depot_id",depotId,"=");
				saq2.addQuery("obj.product_id",productId,"=");
				
				ret=this.stockDetailAccountDao.executeNativeQuery(sql+" obj.debitOrCredit=0 and  "+saq2.getQuery(), saq2.getParameters().toArray(), 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					if(ros[1]!=null)outNum=new  BigDecimal(ros[1].toString());
					if(ros[0]!=null)outAmount=new  BigDecimal(ros[0].toString());
				}
				//计算期末数
				endNum=initNum.add(inNum).subtract(outNum);
				endAmount=initAmount.add(inAmount).subtract(outAmount);
				Depot depot = this.getObject(Depot.class, depotId);
				Product product = this.getObject(Product.class, productId);
				Map map=new HashMap();
				map.put("depot", depot);
				map.put("product", product);
				map.put("inNum", inNum);
				map.put("initNum", initNum);
				map.put("outNum", outNum);
				map.put("num", endNum);
				map.put("inAmount", inAmount);
				map.put("initAmount", initAmount);
				map.put("outAmount", outAmount);
				map.put("amount", endAmount);
				list.set(i, map);
			}
		}
		return list;
	}	
	public List<Map> statisticsTotal(StockDetailAccountQuery query) {
		String depotSql="select distinct obj.depot_id from stockdetailaccount obj";
		List list=this.stockDetailAccountDao.executeNativeQuery(depotSql, null, 0, -1);
		if(list!= null){
			for(int i=0;i<list.size();i++){
				Long depotId=((Number)list.get(0)).longValue();
				BigDecimal initNum = new BigDecimal(0), initAmount = new BigDecimal(0), inNum = new BigDecimal(
						0), inAmount = new BigDecimal(0), outNum = new BigDecimal(0), outAmount = new BigDecimal(
						0), endNum = new BigDecimal(0), endAmount = new BigDecimal(0);
				//查询期初数
				if(query.getVdate1()!=null){
				String sql="SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums from stockdetailaccount obj where obj.depot_id=? and obj.vdate<?";
				List ret=this.stockDetailAccountDao.executeNativeQuery(sql, new Object[]{depotId,query.getVdate1()}, 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					initNum=new  BigDecimal(ros[1].toString());
					initAmount=new  BigDecimal(ros[0].toString());
				}
				}
				//查询本期增加
				StockDetailAccountQuery saq1=new StockDetailAccountQuery();
				BeanUtils.copyProperties(query, saq1);
				saq1.addQuery("obj.depot_id",depotId,"=");
				String sql="SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums from stockdetailaccount obj where ";
				List ret=this.stockDetailAccountDao.executeNativeQuery(sql+" obj.debitOrCredit=1 and  "+saq1.getQuery(), saq1.getParameters().toArray(), 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					if(ros[1]!=null)inNum=new  BigDecimal(ros[1].toString());
					if(ros[0]!=null)inAmount=new  BigDecimal(ros[0].toString());
				}
				//查询本期减少
				StockDetailAccountQuery saq2=new StockDetailAccountQuery();
				BeanUtils.copyProperties(query, saq2);
				saq1.addQuery("obj.depot_id",depotId,"=");
				ret=this.stockDetailAccountDao.executeNativeQuery(sql+" obj.debitOrCredit=0 and  "+saq2.getQuery(), saq2.getParameters().toArray(), 0, -1);
				if(ret!=null&&ret.size()>0){
					Object[] ros=(Object[])ret.get(0);
					if(ros[1]!=null)outNum=new  BigDecimal(ros[1].toString());
					if(ros[0]!=null)outAmount=new  BigDecimal(ros[0].toString());
				}
				//计算期末数
				endNum=initNum.add(inNum).subtract(outNum);
				endAmount=initAmount.add(inAmount).subtract(outAmount);
				Depot depot = this.getObject(Depot.class, depotId);
				Map map=new HashMap();
				map.put("title", depot.getName());
				map.put("inNum", inNum);
				map.put("initNum", initNum);
				map.put("outNum", outNum);
				map.put("num", endNum);
				map.put("inAmount", inAmount);
				map.put("initAmount", initAmount);
				map.put("outAmount", outAmount);
				map.put("amount", endAmount);
				list.set(i, map);
			}
		}
		return list;
	}	
	private <T> T getObject(Class<T> entityClz, Object id) {
		String q = "select obj from " + entityClz.getName() + " obj where obj.id=?";
		List l = this.stockDetailAccountDao.query(q, new Object[] { id }, 0, 1);
		if (l != null && l.size() > 0) {
			return (T)l.get(0);
		}
		return null;
	}
	
}
