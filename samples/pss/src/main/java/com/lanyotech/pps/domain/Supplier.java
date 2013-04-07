package com.lanyotech.pps.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "supplier")
public class Supplier extends Client {
	private BigDecimal assureAmount;
	
	public BigDecimal getAssureAmount() {
		return assureAmount;
	}

	public void setAssureAmount(BigDecimal assureAmount) {
		this.assureAmount = assureAmount;
	}
}
