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
 * File: test.ncsu.dre.DREFrameworkTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 9, 2007 12:30:45 PM
 */
package test.ncsu.dre;

import edu.ncsu.dre.*;
import edu.ncsu.dre.impl.*;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test routines for the DREFramework class
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREFrameworkTest {

	DefaultDREConfiguration defaultConfiguration = null;
	DREFramework framework = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		framework = new DREFramework();
		defaultConfiguration = new DefaultDREConfiguration();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {		
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#getVersionString()}.
	 */
	@Test
	public void testGetVersionString() {
		assertEquals(framework.getVersionString(),"0.1.1"); 
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#getConfiguration()}.
	 */
	@Test
	public void testGetConfiguration() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#setConfiguration(edu.ncsu.dre.DREConfiguration)}.
	 */
	@Test
	public void testSetConfigurationDREConfiguration() {			
		assertNull(framework.getConfiguration());
		framework.setConfiguration(defaultConfiguration);
		assertNotNull(framework.getConfiguration());
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#setConfiguration(java.io.File)}.
	 */
	@Test
	public void testSetConfigurationFile() {		

		framework.setConfiguration(new java.io.File("configuration.xml"));
		assertNotNull(framework.getConfiguration());
		
		java.util.Map<Object,Object> frmMap = framework.getConfiguration().getParameters();
		assertEquals(frmMap.get("Comment"),"SimpleStandAlone DRE Framework.");
		java.util.Map<Object,Object> segMap = framework.getConfiguration().getSegregatorOptions();
		assertEquals(segMap.get("Comment"),"This is the LexicalSegregator");
		java.util.Map<Object,Object> aggMap = framework.getConfiguration().getAggregatorOptions();
		assertEquals(aggMap.get("Comment"),"This is the XMLAggregator");
		java.util.Map<Object,Object> rsMap = framework.getConfiguration().getSchedulerOptions();
		assertEquals(rsMap.get("Comment"),"This is the StandAloneScheduler");
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#DREFramework()}.
	 */
	@Test
	public void testDREFramework() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#DREFramework(edu.ncsu.dre.DREConfiguration)}.
	 */
	@Test
	public void testDREFrameworkDREConfiguration() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#DREFramework(java.io.File)}.
	 */
	@Test
	public void testDREFrameworkFile() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#processArtifact(edu.ncsu.dre.data.Artifact)}.
	 */
	@Test
	public void testProcessArtifact() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ncsu.dre.DREFramework#hasValidConfiguration()}.
	 */
	@Test
	public void testHasValidConfiguration() {
		fail("Not yet implemented"); // TODO
	}

}
