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
 * File: test.ncsu.dre.DRETestSuite.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 10, 2007 4:00:15 PM
 */

package test.ncsu.dre;

import junit.framework.Test;
import junit.framework.TestSuite;

import test.ncsu.dre.impl.data.*;
import test.ncsu.dre.impl.engine.*;

/**
 * Test Suite to integrate all component tests
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DRETestSuite {

	/**
	 * Kick off all the tests
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test.ncsu.dre");
		
		//$JUnit-BEGIN$		
		suite.addTestSuite(DREFrameworkTest.class);
		
		suite.addTestSuite(TextArtifactTest.class);
		
		suite.addTestSuite(LexicalSegregatorTest.class);
		suite.addTestSuite(LiveSearchProviderTest.class);
		suite.addTestSuite(StandAloneSchedulerTest.class);
		//$JUnit-END$
		
		return suite;
	}

}
