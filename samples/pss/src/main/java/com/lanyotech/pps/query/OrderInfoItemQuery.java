package com.lanyotech.pps.query;

import java.util.Date;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.core.support.query.QueryObject;
import com.lanyotech.pps.domain.Client;
import com.lanyotech.pps.domain.Employee;
import com.lanyotech.pps.domain.ProductDir;

public class OrderInfoItemQuery extends QueryObject {
	@POLoad
	private Client client;
	@POLoad
	private Employee seller;
	private String sn = "";
	private String name = "";
	private String groupBy = "";
	private Date vdate1;
	private Date vdate2;
	@POLoad
	private ProductDir dir;
	
	@Override
	public void customizeQuery() {
		if(client!=null){
			this.addQuery("bill.client_id",client.getId(),"=");
		}
		if(seller!=null){
			this.addQuery("bill.seller_id",seller.getId(),"=");
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

	public Client getClient() {
		return client;
	}

	public Employee getSeller() {
		return seller;
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

	public void setClient(Client client) {
		this.client = client;
	}

	public void setSeller(Employee seller) {
		this.seller = seller;
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
