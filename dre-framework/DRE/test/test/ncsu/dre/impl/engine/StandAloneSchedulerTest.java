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
 * File: test.ncsu.dre.impl.engine.StandAloneSchedulerTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 10, 2007 2:40:22 PM
 */
package test.ncsu.dre.impl.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

import edu.ncsu.dre.DREFramework;
import edu.ncsu.dre.impl.data.TextArtifact;
import edu.ncsu.dre.impl.engine.StandAloneScheduler;

/**
 * Test cases to check the StandAloneScheduler
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class StandAloneSchedulerTest extends TestCase{

	DREFramework framework = null;
	TextArtifact artifact = null;
	List<Object> ObjectList = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		framework = new DREFramework(new java.io.File("configuration.xml"));
		artifact = new TextArtifact(new java.io.File("sample.pdf"));
		
		ObjectList = (List<Object>) framework.getConfiguration().getSegregator().segregateArtifact(artifact.getQuery());		
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.engine.StandAloneScheduler#scheduleResearch(java.util.Collection, java.util.List)}.
	 */
	@Test
	public @SuppressWarnings("unchecked") void testScheduleResearch() {		
		
		for(int i=0;i<ObjectList.size();i++)
		{
			ArrayList<String> wordList = (ArrayList<String>) ObjectList.get(i);
			for(int j=0;j<wordList.size();j++)
			{System.out.println(wordList.get(j));}
			
			StandAloneScheduler ss = new StandAloneScheduler();
			ss.scheduleResearch((Collection<Object>) ObjectList.get(i), framework.getConfiguration().getServiceProvider());
		}
	}
}
