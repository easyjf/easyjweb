package com.easyjf;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    public void testSome()
    {
    	System.out.println(java.util.Locale.CHINA.getLanguage());
    	System.out.println(java.util.Locale.ENGLISH.getLanguage());
    	System.out.println(java.util.Locale.getDefault().getDisplayLanguage());
    	System.out.println(Math.pow(2, 0));
    	System.out.println(Math.pow(2, 1));
    	System.out.println(Math.pow(2, 2));
    	System.out.println(Math.pow(2, 3));
    }
  
}
