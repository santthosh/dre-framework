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
 * File: test.ncsu.dre.impl.engine.LexicalSegregatorTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 10, 2007 12:11:42 PM
 */
package test.ncsu.dre.impl.engine;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import edu.ncsu.dre.impl.engine.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test cases to test the LexicalSegregator class
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class LexicalSegregatorTest extends TestCase{

	LexicalSegregator ls = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ls = new LexicalSegregator();
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.engine.LexicalSegregator#segregateArtifact(java.lang.Object)}.
	 */
	@Test
	public @SuppressWarnings("unchecked") void testSegregateArtifact() {

		List<Object> ObjectList = (List<Object>) ls.segregateArtifact("Santthosh the is a Santthosh Santthosh");
		
		for(int i=0;i<ObjectList.size();i++)
		{
			ArrayList<String> wordList = (ArrayList<String>) ObjectList.get(i);
			for(int j=0;j<wordList.size();j++)
			{assertEquals(wordList.get(j),"santthosh");}			
		}
		
		String nullInput = "  ";
		
		ObjectList = (List<Object>) ls.segregateArtifact(nullInput);
		
		for(int i=0;i<ObjectList.size();i++)
		{
			ArrayList<String> wordList = (ArrayList<String>) ObjectList.get(i);
			for(int j=0;j<wordList.size();j++)
			{assertEquals(wordList.get(j),"santthosh");}			
		}
		assert(false);
	}
}
