package com.lanyotech.pps.domain;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.util.CommUtil;
import com.easyjf.web.ajax.IJsonObject;

@Entity
@Table(name = "product")
public class Product implements IJsonObject{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private String name;
	private String sn;
	private BigDecimal salePrice;
	private BigDecimal costPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private ProductDir dir;
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private SystemDictionaryDetail brand;//ProductBrand
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private SystemDictionaryDetail unit;//ProductUnit
	
	private String spec;
	private String model;
	private String color;
	private String pic;
	private String intro;
	@Lob
	private String content;

	private String other1;
	private String other2;
	private String other3;
	private String other4;
	
	public Object toJSonObject() {
		Map map= CommUtil.obj2mapExcept(this, new String[]{"dir","brand","unit"});
		if(dir!=null){
			map.put("dir", CommUtil.obj2map(dir, new String[]{"id","name"}));
		}
		if(brand!=null){
			map.put("brand", CommUtil.obj2map(brand, new String[]{"id","title"}));
		}
		if(unit!=null){
			map.put("unit", CommUtil.obj2map(unit, new String[]{"id","title"}));
		}
		return map;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSn() {
		return sn;
	}
	
	public ProductDir getDir() {
		return dir;
	}
	public String getSpec() {
		return spec;
	}
	public String getModel() {
		return model;
	}
	public String getColor() {
		return color;
	}
	public String getPic() {
		return pic;
	}
	public String getIntro() {
		return intro;
	}
	public String getOther1() {
		return other1;
	}
	public String getOther2() {
		return other2;
	}
	public String getOther3() {
		return other3;
	}
	public String getOther4() {
		return other4;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public void setDir(ProductDir dir) {
		this.dir = dir;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public void setOther1(String other1) {
		this.other1 = other1;
	}
	public void setOther2(String other2) {
		this.other2 = other2;
	}
	public void setOther3(String other3) {
		this.other3 = other3;
	}
	public void setOther4(String other4) {
		this.other4 = other4;
	}
	public BigDecimal getSalePrice() {
		return salePrice;
	}
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	public SystemDictionaryDetail getBrand() {
		return brand;
	}
	public SystemDictionaryDetail getUnit() {
		return unit;
	}
	public String getContent() {
		return content;
	}
	public void setBrand(SystemDictionaryDetail brand) {
		this.brand = brand;
	}
	public void setUnit(SystemDictionaryDetail unit) {
		this.unit = unit;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
