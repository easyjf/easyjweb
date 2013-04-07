package com.lanyotech.pps.domain;

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
@Table(name = "employee")
public class Employee implements IJsonObject{
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 密码
	 */
	private String password;
	
	private String trueName;
	private String sex;
	private String email;
	
	private String remark;
	private String duty;
	private String tel;
	private String address;
	
	/**
	 * 0普通员工,1超级管理员
	 */
	private Integer types;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@POLoad
	private Department dept;
	private Integer status;
	private Integer loginTimes=0;
	private String lastLoginIp;
	private Date lastLoginTime;
	private Date lastLogoutTime;
	
	public Object toJSonObject() {
		Map map=CommUtil.obj2mapExcept(this, new String[]{"dept"});
		if(dept!=null){
			map.put("dept",CommUtil.obj2map(dept, new String[]{"id","name","sn"}));
		}
		return map;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getTrueName() {
		return trueName;
	}
	public String getEmail() {
		return email;
	}
	public String getTel() {
		return tel;
	}
	public String getRemark() {
		return remark;
	}
	public Integer getTypes() {
		return types;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setTypes(Integer types) {
		this.types = types;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public String getSex() {
		return sex;
	}
	public String getDuty() {
		return duty;
	}
	public String getAddress() {
		return address;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
	
}
