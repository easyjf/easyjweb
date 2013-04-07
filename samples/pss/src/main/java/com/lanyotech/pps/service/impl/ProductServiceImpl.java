package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.List;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.Product;
import com.lanyotech.pps.service.IProductService;
import com.lanyotech.pps.dao.IProductDAO;


/**
 * ProductServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: ProductServiceImpl.java,v 0.0.1 2010-6-12 19:25:34 EasyJWeb 1.0-m2 Exp $
 */
public class ProductServiceImpl implements IProductService{
	
	private IProductDAO productDao;
	
	public void setProductDao(IProductDAO productDao){
		this.productDao=productDao;
	}
	
	public Long addProduct(Product product) {	
		this.productDao.save(product);
		if (product != null && product.getId() != null) {
			return product.getId();
		}
		return null;
	}
	
	public Product getProduct(Long id) {
		Product product = this.productDao.get(id);
		return product;
		}
	
	public boolean delProduct(Long id) {	
			Product product = this.getProduct(id);
			if (product != null) {
				this.productDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelProducts(List<Serializable> productIds) {
		
		for (Serializable id : productIds) {
			delProduct((Long) id);
		}
		return true;
	}
	
	public IPageList getProductBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, Product.class,this.productDao);		
	}
	
	public boolean updateProduct(Long id, Product product) {
		if (id != null) {
			product.setId(id);
		} else {
			return false;
		}
		this.productDao.update(product);
		return true;
	}	
	
}
