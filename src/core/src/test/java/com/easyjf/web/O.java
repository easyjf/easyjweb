package com.easyjf.web;

import com.easyjf.container.annonation.InnerProperty;
import com.easyjf.container.annonation.OverrideProperty;
import com.easyjf.container.annonation.POLoad;
public class O {
	private Long id;	
	private String userName;
	@InnerProperty
	private Inner inner;
	@InnerProperty(overrides={@OverrideProperty(name="amount",newName="amount1"),@OverrideProperty(name="dw",newName="dw1")})
	private Inner inner2;
	@POLoad(name="poId",pkClz=Long.class)
	private PO po;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Inner getInner() {
		return inner;
	}
	public void setInner(Inner inner) {
		this.inner = inner;
	}
	public Inner getInner2() {
		return inner2;
	}
	public void setInner2(Inner inner2) {
		this.inner2 = inner2;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String toString()
	{
		return "id:"+this.id+",userName:"+this.userName+",inner:["+inner+"],inner2:["+inner2+"],po:["+this.po+"]";
	}
	public PO getPo() {
		return po;
	}
	public void setPo(PO po) {
		this.po = po;
	}
}
