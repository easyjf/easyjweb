package com.easyjf.container;

import org.springframework.test.jpa.AbstractJpaTests;

public abstract class JpaDaoTest extends AbstractJpaTests {

	protected String[] getConfigLocations() {
		return new String[]{"classpath:application.xml"};	
	}

}
