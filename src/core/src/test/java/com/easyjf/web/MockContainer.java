package com.easyjf.web;

import java.util.Collection;
import java.util.List;

import com.easyjf.container.Container;
import com.easyjf.container.annonation.POLoad;

public class MockContainer implements Container {

	public boolean containsBean(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getBean(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getBean(Class type) {		
		if(type==POLoadDao.class)return new MockPOLoad();
		return null;
	}

	public Collection getBeansName() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getBeans(Class type) {
		// TODO Auto-generated method stub
		return null;
	}

}
