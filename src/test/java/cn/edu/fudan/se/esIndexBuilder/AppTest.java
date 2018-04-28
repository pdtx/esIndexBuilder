package cn.edu.fudan.se.esIndexBuilder;

import java.util.List;
import java.util.Map;

import cn.edu.fudan.se.esIndexBuilder.esoperation.ClientFactory;
import cn.edu.fudan.se.esIndexBuilder.sqloperation.DatabaseOperation;
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
    
    @org.junit.Test
    public void databaseTest() {
    	System.out.println("Database");
    	//DatabaseOperation databaseOperation = new DatabaseOperation();
    	//databaseOperation.queryAll("repository_java", 0, 100);
    	System.out.println("DatabaseEnd");
    }
    
    
    @org.junit.Test
    public void test1(){
    	System.out.println("elastic");
    	new ClientFactory().getTransportClient();
    }
    
    
}
