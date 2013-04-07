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
@Table(name = "purchasebill")
public class PurchaseBill implements IJsonObject{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(unique = true)
	private String sn;
	private Integer types;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Supplier supplier;
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Employee buyer;
	
	private Date vdate;
	private Date inputTime=new Date();
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee inputUser;
	private String remark;
	private BigDecimal amount;
	private Boolean auditing;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@POLoad
	private Employee auditor;
	private Date auditTime;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
	private List<PurchaseBillItem> items = new java.util.ArrayList<PurchaseBillItem>();
	private Integer status;
	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"supplier","buyer","auditor","inputUser","items"});
		if(supplier!=null){
			map.put("supplier", CommUtil.obj2map(supplier, new String[]{"id","name"}));
		}
		if(buyer!=null){
			map.put("buyer", CommUtil.obj2map(buyer, new String[]{"id","name","trueName"}));
		}
		if(auditor!=null){
			map.put("auditor", CommUtil.obj2map(auditor, new String[]{"id","name","trueName"}));
		}
		if(inputUser!=null){
			map.put("inputUser", CommUtil.obj2map(inputUser, new String[]{"id","name","trueName"}));
		}
		return map;
	}
	public Object toJSonObjectWithItems() {
		Map map=(Map)this.toJSonObject();
		if(items!=null && !items.isEmpty()){
			List list=new ArrayList();
			for(PurchaseBillItem item:items){
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

	public List<PurchaseBillItem> getItems() {
		return items;
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

	public void setItems(List<PurchaseBillItem> items) {
		this.items = items;
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

	public Employee getBuyer() {
		return buyer;
	}

	public Date getInputTime() {
		return inputTime;
	}

	public void setTypes(Integer types) {
		this.types = types;
	}

	public void setBuyer(Employee buyer) {
		this.buyer = buyer;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
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
			for(PurchaseBillItem it:this.getItems()){
				if(it.getAmount()!=null)s=s.add(it.getAmount());
			}
		}
		this.amount=s;
	}

	public List<Long> updateItems(List<PurchaseBillItem> newItems) {
		List<Long> deletes = new java.util.ArrayList<Long>();
		for (PurchaseBillItem flow : this.items) {
			boolean exist = false;
			for (PurchaseBillItem f : newItems) {
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

		for (PurchaseBillItem flow : newItems) {
			PurchaseBillItem item = null;
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

	public PurchaseBillItem getItem(Long itemId) {
		PurchaseBillItem item = null;
		for (PurchaseBillItem i : items) {
			if (i != null && itemId.equals(i.getId())) {
				item = i;
				break;
			}
		}
		return item;
	}

	public void addItem(PurchaseBillItem item) {
		item.setBill(this);
		this.items.add(item);
	}

	public void removeItem(Long itemId) {
		PurchaseBillItem p = getItem(itemId);
		if (p != null) {
			p.setBill(null);
			this.items.remove(p);
		}
	}
	
}
