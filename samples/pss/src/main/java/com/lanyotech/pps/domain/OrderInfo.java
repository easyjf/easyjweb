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
@Table(name = "orderinfo")
public class OrderInfo implements IJsonObject{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	@Column(unique = true)
	private String sn;
	/**
	 * 定单类型
	 */
	private Integer types;
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Client client;
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Employee seller;
	private Date vdate;
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Employee inputUser;
	private Date inputTime=new Date();
	private String remark;
	private BigDecimal amount;
	
	private Boolean auditing;
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Employee auditor;
	private Date auditTime;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "orderInfo")
	private List<OrderInfoItem> items = new java.util.ArrayList<OrderInfoItem>();
	private Integer status;
	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"client","seller","auditor","inputUser","items"});
		if(client!=null){
			map.put("client", CommUtil.obj2map(client, new String[]{"id","name"}));
		}
		if(seller!=null){
			map.put("seller", CommUtil.obj2map(seller, new String[]{"id","name","trueName"}));
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
			for(OrderInfoItem item:items){
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
	public Client getClient() {
		return client;
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
	public List<OrderInfoItem> getItems() {
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
	public void setClient(Client client) {
		this.client = client;
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
	public void setItems(List<OrderInfoItem> items) {
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
	public Employee getSeller() {
		return seller;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public Integer getTypes() {
		return types;
	}
	public void setSeller(Employee seller) {
		this.seller = seller;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public void setTypes(Integer types) {
		this.types = types;
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
			for(OrderInfoItem it:this.getItems()){
				if(it.getAmount()!=null)s=s.add(it.getAmount());
			}
		}
		this.amount=s;
	}
	public List<Long> updateItems(List<OrderInfoItem> newItems) {
		List<Long> deletes = new java.util.ArrayList<Long>();
		for (OrderInfoItem flow : this.items) {
			boolean exist = false;
			for (OrderInfoItem f : newItems) {
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

		for (OrderInfoItem flow : newItems) {
			OrderInfoItem item = null;
			if (flow.getId() != null) {
				item = getItem(flow.getId());
			}
			if (item != null) {
				Long id = item.getId();
				BeanUtils.copyProperties(flow, item);
				item.setId(id);
				item.setOrderInfo(this);
			} else
				addItem(flow);
		}
		return deletes;
	}

	public OrderInfoItem getItem(Long itemId) {
		OrderInfoItem item = null;
		for (OrderInfoItem i : items) {
			if (i != null && itemId.equals(i.getId())) {
				item = i;
				break;
			}
		}
		return item;
	}

	public void addItem(OrderInfoItem item) {
		item.setOrderInfo(this);
		this.items.add(item);
	}

	public void removeItem(Long itemId) {
		OrderInfoItem p = getItem(itemId);
		if (p != null) {
			p.setOrderInfo(null);
			this.items.remove(p);
		}
	}
}
