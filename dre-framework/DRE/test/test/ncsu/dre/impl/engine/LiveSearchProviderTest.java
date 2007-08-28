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
 * File: test.ncsu.dre.impl.engine.LiveSearchProviderTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 8, 2007 9:49:34 AM
 */
package test.ncsu.dre.impl.engine;

import java.util.*;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.dre.impl.engine.*;
import edu.ncsu.dre.exception.*;

/**
 * Test routine for the Microsoft LiveSearch API
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class LiveSearchProviderTest extends TestCase{
	
	private LiveSearchProvider searchAgent;
	private Collection<Object> artifactSubset;
	private Map<String,String> options;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		searchAgent = new LiveSearchProvider();				
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
			searchAgent.setArtifactSubset(null);
			searchAgent.setOptions(null);
			searchAgent.start();	//Try passing null arguments
			
			do{}while(searchAgent.isAlive());

			assert(true);								//Test was successful, expected result
		}
		catch(DRERuntimeException e)
		{		
			fail("Program accepts null queries! and breaks");				
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
			
			searchAgent.setArtifactSubset(artifactSubset);
			searchAgent.setOptions(options);
			searchAgent.start();	
			
			do{}while(searchAgent.isAlive());
			
			assert(true);
		}
		catch(DRERuntimeException e)
		{												//Test was successful, expected result			
			fail("Program accepts empty lists! and breaks");
		}
	}
	
	/**
	 * Tests the absence of several mandatory options
	 */
	@Test
	public void testOptionsValidateArguments() {
		try
		{
			//Test the non-availability of options
			artifactSubset.add("simple");	
			
			searchAgent.setArtifactSubset(artifactSubset);
			searchAgent.setOptions(options);
			searchAgent.start();	
			
			do{}while(searchAgent.isAlive());
			
			Map<Object,Object> resultSet = searchAgent.getResultSet();
			
			assertEquals(options.get("source"),"web");
			assertEquals(options.get("appid"),"7E8E2A6CDEEE7248E0EBF23EDD20303F86364CCE");
			assertEquals(options.get("culture"),"en-US");
			assertEquals(options.get("safesearch"),"strict");
			
			//Test the availability of options
			options.clear();
			options.put("source", "asdasd");
			options.put("appid", "tempappid");
			options.put("culture", "adsada");
			options.put("safesearch", "asdasd");
			
			searchAgent.setArtifactSubset(artifactSubset);
			searchAgent.setOptions(options);
			searchAgent.start();	
			
			do{}while(searchAgent.isAlive());
			
			resultSet = searchAgent.getResultSet();
			
			assertEquals(options.get("source"),"asdasd");											
			assertEquals(options.get("appid"),"tempappid");
			assertEquals(options.get("culture"),"adsada");			
			assertEquals(options.get("safesearch"),"asdasd");
			
			assertNotNull(resultSet);
		}
		catch(Exception e)
		{
			fail("Mandatory variables were not specified!");
		}
	}

	/**
	 * Tests the gatherInformation functionality
	 */
	@Test
	public void testGatherInformation() {		
		artifactSubset.add("fact");
		options.put("source", "asdasd");		
		//options.put("appid", "tempappid");
		options.put("culture", "en-US");
		options.put("safesearch", "asdasd");
		
		searchAgent.setArtifactSubset(artifactSubset);
		searchAgent.setOptions(options);
		searchAgent.start();	
		
		do{}while(searchAgent.isAlive());
		
		Map<Object,Object> resultSet = searchAgent.getResultSet();

		System.out.println(resultSet.toString());
		assertNotNull(resultSet);
	}
}
