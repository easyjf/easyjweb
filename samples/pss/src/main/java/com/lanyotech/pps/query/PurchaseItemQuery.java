package com.lanyotech.pps.query;

import java.util.Date;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.core.support.query.QueryObject;
import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.domain.ProductDir;
import com.lanyotech.pps.domain.Supplier;

public class PurchaseItemQuery extends QueryObject {
	@POLoad
	private Supplier supplier;
	@POLoad
	private Employee buyer;
	private String sn = "";
	private String name = "";
	private String groupBy = "";
	private Date vdate1;
	private Date vdate2;
	@POLoad
	private ProductDir dir;
	
	@Override
	public void customizeQuery() {
		if(supplier!=null){
			this.addQuery("bill.supllier_id",supplier.getId(),"=");
		}
		if(buyer!=null){
			this.addQuery("bill.buyer_id",buyer.getId(),"=");
		}
		if(dir!=null){
			this.addQuery("p.dir_id",dir.getId(),"=");
		}
		if(!"".equals(sn)){
			this.addQuery("p.sn",sn+"%","like");
		}
		if(!"".equals(name)){
			this.addQuery("p.name",name+"%","like");
		}
		if(vdate1!=null){
			this.addQuery("bill.vdate",vdate1,">=");
		}
		if(vdate2!=null){
			this.addQuery("bill.vdate",vdate2,"<");
		}
		super.customizeQuery();
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public Employee getBuyer() {
		return buyer;
	}
	public String getSn() {
		return sn;
	}
	public String getName() {
		return name;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public Date getVdate1() {
		return vdate1;
	}
	public Date getVdate2() {
		return vdate2;
	}
	public ProductDir getDir() {
		return dir;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public void setBuyer(Employee buyer) {
		this.buyer = buyer;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public void setVdate1(Date vdate1) {
		this.vdate1 = vdate1;
	}
	public void setVdate2(Date vdate2) {
		this.vdate2 = vdate2;
	}
	public void setDir(ProductDir dir) {
		this.dir = dir;
	}
}
