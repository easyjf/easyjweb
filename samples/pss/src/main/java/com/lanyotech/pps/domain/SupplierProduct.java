package com.lanyotech.pps.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyjf.web.ajax.IJsonObject;

@Entity
@Table(name = "supplierproduct")
public class SupplierProduct{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	private Product product;
	private BigDecimal price;
	private String intro;
	
	public Long getId() {
		return id;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
