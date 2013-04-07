package com.lanyotech.pps.domain;

import java.math.BigDecimal;
import java.util.Date;
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
@Table(name = "stockdetailaccount")
public class StockDetailAccount implements IJsonObject {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	/**
	 * 来源单据id
	 */
	private Long billId;

	/**
	 * 来源单据sn
	 */
	private String  billSn;

	/**
	 * 来源单据明细id
	 */
	private Long billItemId;

	/**
	 * 00-两位数,第一位代表入库或出库,第二位代表单据类别 0为出库,1为入库
	 */
	private String types;

	@POLoad
	@ManyToOne(fetch = FetchType.LAZY)
	private Depot depot;

	/**
	 * 单据审核人
	 */
	@POLoad
	@ManyToOne(fetch = FetchType.LAZY)
	private Employee employee;

	/**
	 * 
	 * 1为借方，表示增加，入库  -1为贷方表示减少
	 */
	private Integer debitOrCredit;

	/**
	 * 入账时间
	 */
	private Date vdate;

	/**
	 * 货品
	 */
	@POLoad
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	/**
	 * 数量
	 */
	private BigDecimal num;

	/**
	 * 成本单价
	 */
	private BigDecimal price;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	public Object toJSonObject() {
		Map map = CommUtil.obj2mapExcept(this, new String[] { "depot", "employee", "product" });
		if (depot != null) {
			map.put("depot", CommUtil.obj2map(depot, new String[] { "id", "name" }));
		}
		if (employee != null) {
			map.put("employee", CommUtil.obj2map(employee,
					new String[] { "id", "name", "trueName" }));
		}
		if (product != null) {
			map.put("product", product.toJSonObject());
		}
		return map;
	}

	public Long getId() {
		return id;
	}


	public String getTypes() {
		return types;
	}

	public Depot getDepot() {
		return depot;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Integer getDebitOrCredit() {
		return debitOrCredit;
	}

	public Date getVdate() {
		return vdate;
	}

	public Product getProduct() {
		return product;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	
	public void setTypes(String types) {
		this.types = types;
	}

	public void setDepot(Depot depot) {
		this.depot = depot;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setDebitOrCredit(Integer debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getNum() {
		return num;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getBillId() {
		return billId;
	}

	public String getBillSn() {
		return billSn;
	}

	public Long getBillItemId() {
		return billItemId;
	}

	public void setBillId(Long billId) {
		this.billId = billId;
	}

	public void setBillSn(String billSn) {
		this.billSn = billSn;
	}

	public void setBillItemId(Long billItemId) {
		this.billItemId = billItemId;
	}

}
