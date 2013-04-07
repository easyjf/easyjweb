package com.lanyotech.pps.query;

import java.util.Date;

import com.easyjf.container.annonation.POLoad;
import com.easyjf.core.support.query.QueryObject;
import com.lanyotech.pps.domain.Depot;
import com.lanyotech.pps.domain.ProductDir;

public class StockDetailAccountQuery extends QueryObject {
	private Date vdate1;
	private Date vdate2;
	@POLoad
	private Depot depot;
	private String sn="";
	private String name="";
	@POLoad
	private ProductDir dir;
	private String groupBy = "";
	@Override
	public void customizeQuery() {
		if(vdate1!=null){
			this.addQuery("obj.vdate",vdate1,">=");
		}
		if(vdate2!=null){
			this.addQuery("obj.vdate",vdate2,"<");
		}
		if(depot!=null){
			this.addQuery("obj.depot_id",depot.getId(),"=");
		}
		if(!"".equals(sn)){
			this.addQuery("p.sn",sn+"%","like");
		}
		if(!"".equals(name)){
			this.addQuery("p.name",name+"%","like");
		}
		if(dir!=null){
			this.addQuery("p.dir_id",dir.getId(),"=");
		}
	}
	public Date getVdate1() {
		return vdate1;
	}
	public Date getVdate2() {
		return vdate2;
	}
	public void setVdate1(Date vdate1) {
		this.vdate1 = vdate1;
	}
	public void setVdate2(Date vdate2) {
		this.vdate2 = vdate2;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public Depot getDepot() {
		return depot;
	}
	public void setDepot(Depot depot) {
		this.depot = depot;
	}
	public String getSn() {
		return sn;
	}
	public String getName() {
		return name;
	}
	public ProductDir getDir() {
		return dir;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDir(ProductDir dir) {
		this.dir = dir;
	}
	
}
