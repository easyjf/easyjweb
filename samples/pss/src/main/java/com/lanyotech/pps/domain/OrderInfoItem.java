package com.lanyotech.pps.domain;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.util.CommUtil;
import com.easyjf.web.ajax.IJsonObject;

@Entity
@Table(name = "orderinfoitem")
public class OrderInfoItem implements IJsonObject{
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	private OrderInfo orderInfo;
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Product product;
	private BigDecimal price=new BigDecimal(0);
	private BigDecimal num=new BigDecimal(0);
	private BigDecimal amount=new BigDecimal(0);
	private String remark;
	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"orderInfo","product"});
		if(product!=null){
			map.put("product", product.toJSonObject());
		}
		return map;
	}
	public Long getId() {
		return id;
	}
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public Product getProduct() {
		return product;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public BigDecimal getNum() {
		return num;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public void setNum(BigDecimal num) {
		this.num = num;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}
