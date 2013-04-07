package com.lanyotech.pps.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BaseCount {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	private String entityName;
	private Integer sequence;
	public Long getId() {
		return id;
	}
	public String getEntityName() {
		return entityName;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
}
