package com.easyjf.web;

import java.util.Map;

import com.easyjf.web.core.PathMappingRulerImpl;

import junit.framework.TestCase;

public class PathMappingRulerImplTest extends TestCase {

	private PathMappingRulerImpl ruler = null;

	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		ruler = null;
	}

	public void testNothing()
	{
		
	}
	
	// test:/module/command/cid=12345/name=xxx.ejf
	// expect:moduleName=/module commandName=command params={cid=12345;name=xxx}
	public void testFactOne() {
	/*	String testPath = "/module/command/cid=12345/name=xxx.ejf";
		ruler = new PathMappingRulerImpl( testPath);
		ruler.doPathParse();
		System.out.println(ruler
				.getModuleName());
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());
		assertEquals("commandName should be command", "command", ruler
				.getCommand());
		Map params = ruler.getParams();
		assertNotNull("in this case, params should not be null", params
				.get("cid"));

		assertTrue("and params must contain cid", params.keySet().contains(
				"cid"));
		assertEquals("paramone's value should be 12345", "12345", params
				.get("cid"));
		assertTrue("and params must contain name", params.keySet().contains(
				"name"));
		assertEquals("paramtwo's vaule should be xxx", "xxx", params
				.get("name"));*/

	}

// test: /module
// expect:moduleName=/module
	public void testFactTwo() {
		/*String testPath = "/module";
		ruler = new PathMappingRulerImpl(testPath);
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());*/
	}

//	 test: /module/command
//	 expect:moduleName=/module commandName=command
	public void testFactThree() {
		/*String testPath = "/module/command";
		ruler = new PathMappingRulerImpl(testPath);
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());
		assertEquals("commandName should be command", "command", ruler
				.getCommand());*/
	}

//	 test :/module/command/12345
//	 expect:moduleName=/module commandName=command params={cid=12345}
	public void testFactFour() {
	/*	String testPath = "/module/command/12345";
		ruler = new PathMappingRulerImpl(testPath);
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());
		assertEquals("commandName should be command", "command", ruler
				.getCommand());
		Map params = ruler.getParams();
		assertNotNull("in this case, params should not be null", params
				.get("cid"));

		assertTrue("and params must contain cid", params.keySet().contains(
				"cid"));
		assertEquals("paramone's value should be 12345", "12345", params
				.get("cid"));*/
	}

//	 test :/module/cid=12345.ejf
//	 expect:moduleName=/module params={cid=12345}
	public void testFactFive() {
	/*	String testPath = "/module/cid=12345";
		ruler = new PathMappingRulerImpl( testPath);
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());
		Map params = ruler.getParams();
		assertNotNull("in this case, params should not be null", params
				.get("cid"));

		assertTrue("and params must contain cid", params.keySet().contains(
				"cid"));
		assertEquals("paramone's value should be 12345", "12345", params
				.get("cid"));*/
	}

//	 test :/module/command/12345/showpage/gg.ejf
//	 expect:moduleName=/module commandName=command params={cid=12345;page=showpage;other=gg}
	public void testFactSix() {
	/*	String testPath = "/module/command/12345/showpage/gg.ejf";
		ruler = new PathMappingRulerImpl(testPath);
		assertEquals("moduleName should be /module", "/module", ruler
				.getModuleName());
		assertEquals("commandName should be command", "command", ruler
				.getCommand());
		Map params = ruler.getParams();
		assertNotNull("in this case, params should not be null", params
				.get("cid"));

		assertTrue("and params must contain cid", params.keySet().contains(
				"cid"));
		assertEquals("paramone's value should be 12345", "12345", params
				.get("cid"));
		assertTrue("and params must contain page", params.keySet().contains(
				"page"));
		assertEquals("paramtwo's vaule should be showpage", "showpage", params
				.get("page"));
		assertTrue("and params must contain other", params.keySet().contains(
				"other"));
		assertEquals("paramtwo's vaule should be gg", "gg", params.get("other"));*/
	}
	
	public void testFactSeven(){
	/*	String testPath="";
		ruler = new PathMappingRulerImpl(testPath);
		assertEquals("moduleName should be null ", "", ruler
				.getModuleName());*/
	}	

}
