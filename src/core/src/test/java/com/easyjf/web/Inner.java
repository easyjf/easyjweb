package com.easyjf.web;

public class Inner {
	private String amount;
	private String dw;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String toString()
	{
		return "amount:"+this.amount+",dw:"+dw;
	}
}
