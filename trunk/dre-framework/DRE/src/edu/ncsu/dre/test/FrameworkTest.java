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
 * File: edu.ncsu.dre.test.FrameworkTest.java
 * Created by: santthosh
 * TimeStamp: Jul 24, 2007 10:26:33 PM
 */
package edu.ncsu.dre.test;

import java.util.*;
import edu.ncsu.dre.*;
import edu.ncsu.dre.impl.data.*;
import edu.ncsu.dre.impl.engine.*;

import org.apache.log4j.*;

/**
 * Framework Test Suite
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class FrameworkTest {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.test.FrameworkTest");
	
	/**
	 * Constructor class
	 */
	public @SuppressWarnings("unchecked") FrameworkTest()
	{		
		DREFramework framework = new DREFramework(new java.io.File("configuration.xml"));
			
		TextArtifact artifact = new TextArtifact(new java.io.File("sample.pdf"));
		
		List<Object> ObjectList = (List<Object>) framework.getConfiguration().getSegregator().segregateArtifact(artifact.getQuery());
		
		for(int i=0;i<ObjectList.size();i++)
		{
			ArrayList<String> wordList = (ArrayList<String>) ObjectList.get(i);
			for(int j=0;j<wordList.size();j++)
			{logger.info(wordList.get(j));}
			
			StandAloneScheduler ss = new StandAloneScheduler();
			ss.scheduleResearch((Collection<Object>) ObjectList.get(i), framework.getConfiguration().getServiceProvider());
		}
	}

	/**
	 * Main method
	 */
	public static void main(String args[])
	{
		logger.info("DREFramework Standalone Tester");
		logger.info("==============================");
		
		new FrameworkTest();		
	}
}
