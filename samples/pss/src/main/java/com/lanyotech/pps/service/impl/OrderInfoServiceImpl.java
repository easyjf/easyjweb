package com.lanyotech.pps.service.impl;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyjf.core.support.query.IQueryObject;
import com.easyjf.core.support.query.QueryUtil;
import com.easyjf.util.StringUtils;
import com.easyjf.web.tools.IPageList;
import com.lanyotech.pps.dao.IOrderInfoDAO;
import com.lanyotech.pps.domain.OrderInfo;
import com.lanyotech.pps.query.OrderInfoItemQuery;
import com.lanyotech.pps.service.IOrderInfoService;


/**
 * OrderInfoServiceImpl
 * @author EasyJWeb 1.0-m2
 * $Id: OrderInfoServiceImpl.java,v 0.0.1 2010-6-13 0:16:14 EasyJWeb 1.0-m2 Exp $
 */
public class OrderInfoServiceImpl implements IOrderInfoService{
	
	private IOrderInfoDAO orderInfoDao;
	
	public void setOrderInfoDao(IOrderInfoDAO orderInfoDao){
		this.orderInfoDao=orderInfoDao;
	}
	
	public Long addOrderInfo(OrderInfo orderInfo) {	
		orderInfo.countAmount();
		this.orderInfoDao.save(orderInfo);
		if (orderInfo != null && orderInfo.getId() != null) {
			return orderInfo.getId();
		}
		return null;
	}
	
	public OrderInfo getOrderInfo(Long id) {
		OrderInfo orderInfo = this.orderInfoDao.get(id);
		return orderInfo;
		}
	
	public boolean delOrderInfo(Long id) {	
			OrderInfo orderInfo = this.getOrderInfo(id);
			if (orderInfo != null) {
				this.orderInfoDao.remove(id);
				return true;
			}			
			return false;	
	}
	
	public boolean batchDelOrderInfos(List<Serializable> orderInfoIds) {
		
		for (Serializable id : orderInfoIds) {
			delOrderInfo((Long) id);
		}
		return true;
	}
	
	public IPageList getOrderInfoBy(IQueryObject queryObject) {	
		return QueryUtil.query(queryObject, OrderInfo.class,this.orderInfoDao);		
	}
	
	public boolean updateOrderInfo(Long id, OrderInfo orderInfo) {
		if (id != null) {
			orderInfo.setId(id);
		} else {
			return false;
		}
		orderInfo.countAmount();
		this.orderInfoDao.update(orderInfo);
		return true;
	}

	public boolean delOrderInfoItem(Long id) {
		int ret=this.orderInfoDao.batchUpdate("delete from OrderInfoItem obj where obj.id=?", new Object[]{id});
		return ret>0;
	}	
	
	public List<Map> statistics(OrderInfoItemQuery query, String groupBy) {
		if(!StringUtils.hasLength(groupBy))groupBy="p.dir_id";//默认按产品分组
		if("day".equals(groupBy)){
			//转换成日期函数
			groupBy="DATE_FORMAT(bill.vdate,'%Y-%m-%d')";
		}
		if("month".equals(groupBy)){
			//转换成月份
			groupBy="DATE_FORMAT(bill.vdate,'%Y-%m')";
		}
		StringBuilder jpql = new StringBuilder("SELECT SUM(obj.amount) as amounts,SUM(obj.num) as nums," + groupBy + " FROM orderinfoitem obj left join orderinfo bill on bill.id=obj.orderInfo_id left join product p on p.id=obj.product_id ");
		jpql.append(" where ").append(query.getQuery()).append(" GROUP BY ").append(groupBy);
		List list=this.orderInfoDao.executeNativeQuery(jpql.toString(), query.getParameters().toArray(), 0, -1);
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
					title = this.getFieldTitle("name", "client", new Long(title));
				}
				else if("p.id".equals(groupBy)){
					title = this.getFieldTitle("name", "product", new Long(title));
				}
				else if("p.dir_id".equals(groupBy)){
					title = this.getFieldTitle("name", "productdir", new Long(title));
				}
				else if("bill.buyer_id".equals(groupBy)){
					title = this.getFieldTitle("trueName", "employee", new Long(title));
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
		List l = this.orderInfoDao.executeNativeQuery(q, new Object[] { id }, 0, 1);
		if (l != null && l.size() > 0) {
			return l.get(0).toString();
		}
		return "";
	}
	
}
