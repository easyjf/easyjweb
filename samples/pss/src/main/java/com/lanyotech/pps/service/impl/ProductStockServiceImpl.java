package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.dao.IProductStockDAO;
import com.lanyotech.pps.domain.Depot;
import com.lanyotech.pps.domain.Product;
import com.lanyotech.pps.domain.ProductStock;
import com.lanyotech.pps.domain.StockIncome;
import com.lanyotech.pps.domain.StockIncomeItem;
import com.lanyotech.pps.domain.StockOutcome;
import com.lanyotech.pps.domain.StockOutcomeItem;
import com.lanyotech.pps.service.IProductStockService;


/**
 * ProductStockServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: ProductStockServiceImpl.java,v 0.0.1 2010-6-12 23:01:11 EasyJWeb 1.0-m2 Exp $
 */
public class ProductStockServiceImpl implements IProductStockService{
	
	private IProductStockDAO productStockDao;
	
	public void setProductStockDao(IProductStockDAO productStockDao){
		this.productStockDao=productStockDao;
	}
	
	public Long addProductStock(ProductStock productStock) {	
		this.productStockDao.save(productStock);
		if (productStock != null && productStock.getId() != null) {
			return productStock.getId();
		}
		return null;
	}
	
	public ProductStock getProductStock(Long id) {
		ProductStock productStock = this.productStockDao.get(id);
		return productStock;
		}
	
	public boolean delProductStock(Long id) {	
			ProductStock productStock = this.getProductStock(id);
			if (productStock != null) {
				this.productStockDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelProductStocks(List<Serializable> productStockIds) {
		
		for (Serializable id : productStockIds) {
			delProductStock((Long) id);
		}
		return true;
	}
	
	public IPageList getProductStockBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, ProductStock.class,this.productStockDao);		
	}
	
	public boolean updateProductStock(Long id, ProductStock productStock) {
		if (id != null) {
			productStock.setId(id);
		} else {
			return false;
		}
		this.productStockDao.update(productStock);
		return true;
	}

	private ProductStock getProductStock(Product product,Depot depot){
		QueryObject qo=new QueryObject();
		qo.addQuery("obj.product",product,"=");
		qo.addQuery("obj.depot",depot,"=");
		List list=this.getProductStockBy(qo).getResult();
		if(list!=null&&list.size()>0)return (ProductStock)list.get(0);
		ProductStock ps=new ProductStock();
		ps.setProduct(product);
		ps.setDepot(depot);
		return ps;
	}
	
	public void changeProductStock(StockIncome income) {
		for(StockIncomeItem item:income.getItems()){
			ProductStock ps=this.getProductStock(item.getProduct(), item.getBill().getDepot());
			ps.setStoreNum(ps.getStoreNum().add(item.getNum()));
			ps.setAmount(ps.getAmount().add(item.getAmount()));
			ps.setPrice(ps.getAmount().divide(ps.getStoreNum()));
			if(ps.getId()==null)this.addProductStock(ps);
			else this.updateProductStock(ps.getId(), ps);
		}
	}

	public void changeProductStock(StockOutcome outcome) {
		for(StockOutcomeItem item:outcome.getItems()){
			ProductStock ps=this.getProductStock(item.getProduct(), item.getBill().getDepot());
			ps.setStoreNum(ps.getStoreNum().add(item.getNum()));
			ps.setAmount(ps.getAmount().add(item.getAmount()));
			ps.setPrice(ps.getAmount().divide(ps.getStoreNum()));
			if(ps.getId()==null)this.addProductStock(ps);
			else this.updateProductStock(ps.getId(), ps);
		}
	}	
	
}
