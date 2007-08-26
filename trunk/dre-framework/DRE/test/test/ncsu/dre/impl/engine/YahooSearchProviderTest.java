/*
 * Licensed to the Santthosh Babu Selvadurai (sbselvad@ncsu.edu) under 
 * one or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information regarding 
 * copyright ownership.
 * 
 * THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL 
 * THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR 
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * WITH THE SOFTWARE.
 *
 * File: test.ncsu.dre.impl.engine.YahooSearchProviderTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 25, 2007 5:58:11 PM
 */
package test.ncsu.dre.impl.engine;

import java.util.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.dre.exception.DRERuntimeException;
import edu.ncsu.dre.impl.engine.YahooSearchProvider;

/**
 * Test routine for the Yahoo Search API
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class YahooSearchProviderTest extends TestCase{

	private YahooSearchProvider searchAgent;
	private Collection<Object> artifactSubset;
	private Map<String,String> options;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		searchAgent = new YahooSearchProvider();				
													//Try passing empty lists and maps as arguments
		artifactSubset = new ArrayList<Object>();
		options = new HashMap<String,String>();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Tests for null arguments
	 * 
	 */
	@Test
	public void testNullQueryValidateArguments() {
		try
		{												
			searchAgent.validateArguments(null, null);	//Try passing null arguments

			fail("Program accepts null queries!");		
		}
		catch(DRERuntimeException e)
		{			
			assert(true);								//Test was successful, expected result
		}
	}
	
	/**
	 * Tests the handling of empty lists
	 */
	@Test
	public void testEmptyQueryValidateArguments() {
		try
		{												//Try passing empty lists and maps as arguments
			Collection<Object> artifactSubset = new ArrayList<Object>();
			Map<String,String> options = new HashMap<String,String>();
			
			searchAgent.validateArguments(artifactSubset, options);
			
			fail("Program accepts empty lists!");
		}
		catch(DRERuntimeException e)
		{												//Test was successful, expected result
			assert(true);
		}
	}
	

	/**
	 * Test method for {@link edu.ncsu.dre.impl.engine.YahooSearchProvider#gatherInformation(java.util.Collection, java.util.Map)}.
	 */
	@Test
	public final void testGatherInformation() {
		artifactSubset.add("simple");	
		artifactSubset.add("fact");
		
		Map<Object,Object> resultSet = searchAgent.gatherInformation(artifactSubset, options);
		
		System.out.println(resultSet.toString());
		assertNotNull(resultSet);
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.engine.YahooSearchProvider#validateArguments(java.util.Collection, java.util.Map)}.
	 */
	@Test
	public final void testValidateArguments() {
		try
		{
			//Test the non-availability of options
			artifactSubset.add("simple");	
			searchAgent.validateArguments(artifactSubset, options);						
			assertEquals(options.get("appid"),"eCBIC3LV34EN3FHl35AMrMhA7JoOi4jfPPy1VQrSNr51qWbeO43DkDDLSqG0jmVg");			
			
			//Test the availability of options
			options.clear();			
			options.put("appid", "tempappid");			
			searchAgent.validateArguments(artifactSubset, options);													
			assertEquals(options.get("appid"),"tempappid");			
		}
		catch(Exception e)
		{
			fail("Mandatory variables were not specified!");
		}
	}

}
