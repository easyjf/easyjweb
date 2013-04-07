package com.lanyotech.pps.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="depot")
public class Depot{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private String name;
	/**
	 * 仓库类型
	 */
	private Integer types;
	private BigDecimal maxCapacity;
	private BigDecimal capcity;
	private BigDecimal amount;
	private Integer sequence;
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getMaxCapacity() {
		return maxCapacity;
	}
	public BigDecimal getCapcity() {
		return capcity;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setMaxCapacity(BigDecimal maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public void setCapcity(BigDecimal capcity) {
		this.capcity = capcity;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getTypes() {
		return types;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
}
