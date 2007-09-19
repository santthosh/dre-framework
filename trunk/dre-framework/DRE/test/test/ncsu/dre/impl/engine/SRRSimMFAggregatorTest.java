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
 * File: test.ncsu.dre.impl.engine.XMLAggregatorTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 24, 2007 2:59:06 PM
 */
package test.ncsu.dre.impl.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.dre.DREFramework;

/**
 * 
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class SRRSimMFAggregatorTest {

	DREFramework normalFramework = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		normalFramework = new DREFramework(new java.io.File("conf/SRRSimMFConfiguration.xml"));
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.engine.XMLAggregator#aggregateResults(edu.ncsu.dre.data.Artifact, java.util.List, java.util.List)}.
	 */
	@Test
	public void testAggregateResults() {
		javax.xml.transform.stream.StreamResult result = normalFramework.processLiteralArtifact(new edu.ncsu.dre.impl.data.TextArtifact("Metasearch engines Engines"));
		System.out.println(result.getOutputStream().toString());		
		assert(true);
	}
}
