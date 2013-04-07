package com.lanyotech.pps.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.easyjf.beans.BeanUtils;
import com.easyjf.container.annonation.POLoad;
import com.easyjf.util.CommUtil;
import com.easyjf.web.ajax.IJsonObject;

@Entity
@Table(name = "stockincome")
public class StockIncome implements IJsonObject{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(unique = true)
	private String sn;
	/**
	 * 0-期初入库单,1-采购入库单,2-其它入库单
	 */
	private Integer types;
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Employee keeper;
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Supplier supplier;
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Depot depot;
	private Date vdate;
	private Date inputTime=new Date();
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee inputUser;
	private String remark;
	private BigDecimal amount;
	private Boolean auditing;
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee auditor;
	private Date auditTime;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
	private List<StockIncomeItem> items = new java.util.ArrayList<StockIncomeItem>();
	private Integer status;

	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"supplier","keeper","depot","auditor","inputUser","items"});
		if(supplier!=null){
			map.put("supplier", CommUtil.obj2map(supplier, new String[]{"id","name"}));
		}
		if(keeper!=null){
			map.put("keeper", CommUtil.obj2map(keeper, new String[]{"id","name","trueName"}));
		}
		if(auditor!=null){
			map.put("auditor", CommUtil.obj2map(auditor, new String[]{"id","name","trueName"}));
		}
		if(inputUser!=null){
			map.put("inputUser", CommUtil.obj2map(inputUser, new String[]{"id","name","trueName"}));
		}
		if(depot!=null){
			map.put("depot", CommUtil.obj2map(depot, new String[]{"id","name"}));
		}
		return map;
	}
	public Object toJSonObjectWithItems() {
		Map map=(Map)this.toJSonObject();
		if(items!=null && !items.isEmpty()){
			List list=new ArrayList();
			for(StockIncomeItem item:items){
				list.add(item.toJSonObject());
			}
			map.put("items", list);
		}
		return map;
	}
	public Long getId() {
		return id;
	}

	public String getSn() {
		return sn;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public Depot getDepot() {
		return depot;
	}

	public Date getVdate() {
		return vdate;
	}

	public String getRemark() {
		return remark;
	}

	public Boolean getAuditing() {
		return auditing;
	}

	public Employee getAuditor() {
		return auditor;
	}

	public Date getAuditTime() {
		return auditTime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAuditing(Boolean auditing) {
		this.auditing = auditing;
	}

	public void setAuditor(Employee auditor) {
		this.auditor = auditor;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getTypes() {
		return types;
	}

	public Employee getKeeper() {
		return keeper;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public void setKeeper(Employee keeper) {
		this.keeper = keeper;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	public List<StockIncomeItem> getItems() {
		return items;
	}

	public void setItems(List<StockIncomeItem> items) {
		this.items = items;
	}
	
	public Employee getInputUser() {
		return inputUser;
	}
	public void setInputUser(Employee inputUser) {
		this.inputUser = inputUser;
	}
	public void countAmount(){
		java.math.BigDecimal s=new BigDecimal(0);
		if(items!=null&&items.size()>0){
			for(StockIncomeItem it:this.getItems()){
				if(it.getAmount()!=null)s=s.add(it.getAmount());
			}
		}
		this.amount=s;
	}
	public List<Long> updateItems(List<StockIncomeItem> newItems) {
		List<Long> deletes = new java.util.ArrayList<Long>();
		for (StockIncomeItem flow : this.items) {
			boolean exist = false;
			for (StockIncomeItem f : newItems) {
				if (f.getId() != null) {
					if (f.getId().equals(flow.getId())) {
						exist = true;
						break;
					}
				}
			}
			if (!exist)
				deletes.add(flow.getId());
		}
		for (int i = 0; i < deletes.size(); i++) {
			this.removeItem((Long) deletes.get(i));
		}

		for (StockIncomeItem flow : newItems) {
			StockIncomeItem item = null;
			if (flow.getId() != null) {
				item = getItem(flow.getId());
			}
			if (item != null) {
				Long id = item.getId();
				BeanUtils.copyProperties(flow, item);
				item.setId(id);
				item.setBill(this);
			} else
				addItem(flow);
		}
		return deletes;
	}

	public StockIncomeItem getItem(Long itemId) {
		StockIncomeItem item = null;
		for (StockIncomeItem i : items) {
			if (i != null && itemId.equals(i.getId())) {
				item = i;
				break;
			}
		}
		return item;
	}

	public void addItem(StockIncomeItem item) {
		item.setBill(this);
		this.items.add(item);
	}

	public void removeItem(Long itemId) {
		StockIncomeItem p = getItem(itemId);
		if (p != null) {
			p.setBill(null);
			this.items.remove(p);
		}
	}

}
