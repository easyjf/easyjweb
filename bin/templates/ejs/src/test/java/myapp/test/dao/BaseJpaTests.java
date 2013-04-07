package myapp.test.dao;


import org.springframework.test.jpa.AbstractJpaTests;


public abstract class BaseJpaTests extends AbstractJpaTests {

	@Override
	protected String[] getConfigLocations() {
		return new String[] { "classpath:application.xml" };
	}

}
