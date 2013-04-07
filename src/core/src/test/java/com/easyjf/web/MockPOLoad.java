package com.easyjf.web;

import java.io.Serializable;

public class MockPOLoad implements POLoadDao {
	public Object get(Class clz, Serializable id) {		
		if(clz==PO.class && id.equals(2l))
		{
			PO po=new PO();
			po.setId(2l);
			po.setTitle("标题");			
			return po;
		}
		return null;
	}
}
