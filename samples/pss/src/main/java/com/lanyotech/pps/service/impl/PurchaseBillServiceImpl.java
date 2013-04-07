package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.util.StringUtils;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.domain.PurchaseBill;
import com.lanyotech.pps.query.PurchaseItemQuery;
import com.lanyotech.pps.service.IPurchaseBillService;
import com.lanyotech.pps.dao.IPurchaseBillDAO;


/**
 * PurchaseBillServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: PurchaseBillServiceImpl.java,v 0.0.1 2010-6-13 18:52:19 EasyJWeb 1.0-m2 Exp $
 */
public class PurchaseBillServiceImpl implements IPurchaseBillService{
	
	private IPurchaseBillDAO purchaseBillDao;
	
	public void setPurchaseBillDao(IPurchaseBillDAO purchaseBillDao){
		this.purchaseBillDao=purchaseBillDao;
	}
	
	public Long addPurchaseBill(PurchaseBill purchaseBill) {
		purchaseBill.countAmount();
		this.purchaseBillDao.save(purchaseBill);
		if (purchaseBill != null && purchaseBill.getId() != null) {
			return purchaseBill.getId();
		}
		return null;
	}
	
	public PurchaseBill getPurchaseBill(Long id) {
		PurchaseBill purchaseBill = this.purchaseBillDao.get(id);
		return purchaseBill;
		}
	
	public boolean delPurchaseBill(Long id) {	
			PurchaseBill purchaseBill = this.getPurchaseBill(id);
			if (purchaseBill != null) {
				this.purchaseBillDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelPurchaseBills(List<Serializable> purchaseBillIds) {
		
		for (Serializable id : purchaseBillIds) {
			delPurchaseBill((Long) id);
		}
		return true;
	}
	
	public IPageList getPurchaseBillBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, PurchaseBill.class,this.purchaseBillDao);		
	}
	
	public boolean updatePurchaseBill(Long id, PurchaseBill purchaseBill) {
		if (id != null) {
			purchaseBill.setId(id);
		} else {
			return false;
		}
		purchaseBill.countAmount();
		this.purchaseBillDao.update(purchaseBill);
		return true;
	}

	public boolean delPurchaseBillItem(Long id) {
		int ret=this.purchaseBillDao.batchUpdate("delete from PurchaseBillItem obj where obj.id=?", new Object[]{id});
		return ret>0;
	}

	public List<Map> statistics(PurchaseItemQuery query, String groupBy) {
		if(!StringUtils.hasLength(groupBy))groupBy="p.dir_id";//默认按产品分组
		if("day".equals(groupBy)){
			//转换成日期函数
			groupBy="DATE_FORMAT(bill.vdate,'%Y-%m-%d')";
		}
		if("month".equals(groupBy)){
			//转换成月份
			groupBy="DATE_FORMAT(bill.vdate,'%Y-%m')";
		}
		StringBuilder jpql = new StringBuilder("SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums," + groupBy + " FROM purchasebillitem obj left join purchasebill bill on bill.id=obj.bill_id left join product p on p.id=obj.product_id ");
		jpql.append(" where ").append(query.getQuery()).append(" GROUP BY ").append(groupBy);
		List list=this.purchaseBillDao.executeNativeQuery(jpql.toString(), query.getParameters().toArray(), 0, -1);
		if(list!= null){
			for(int i=0;i<list.size();i++){
				Object[] os=(Object[])list.get(i);
				Map map=new HashMap();
				map.put("amount", os[0]);
				map.put("num", os[1]);
				Object value=os[2];
				map.put("value", value);
				if(value!=null){
				String title=value.toString();
				if("bill.supplier_id".equals(groupBy)){
					title = this.getFieldTitle("name", "client", new Long(value.toString()));
				}
				else if("p.id".equals(groupBy)){
					title = this.getFieldTitle("name", "product", new Long(value.toString()));
				}
				else if("p.dir_id".equals(groupBy)){
					title = this.getFieldTitle("name", "productdir", new Long(value.toString()));
				}
				else if("bill.buyer_id".equals(groupBy)){
					title = this.getFieldTitle("trueName", "employee", new Long(value.toString()));
				}
				map.put("title", title);
				}
				list.set(i, map);
			}
		}
		return list;
	}	
	
	private String getFieldTitle(String field, String table, Object id) {
		String q = "select obj." + field + " from " + table + " obj where obj.id=?";
		List l = this.purchaseBillDao.executeNativeQuery(q, new Object[] { id }, 0, 1);
		if (l != null && l.size() > 0) {
			return l.get(0).toString();
		}
		return "";
	}
	
}
