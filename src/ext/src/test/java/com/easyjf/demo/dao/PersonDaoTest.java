package com.easyjf.demo.dao;

import com.easyjf.container.JpaDaoTest;
import com.easyjf.demo.domain.Person;

public class PersonDaoTest extends JpaDaoTest {
private IPersonDAO dao;

public void setDao(IPersonDAO dao) {
	this.dao = dao;
}
public void testAdd()
{
	Person person=new Person();
	person.setName("test");
	dao.save(person);
}
}
