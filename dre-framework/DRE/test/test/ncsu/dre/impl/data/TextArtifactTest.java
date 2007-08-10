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
 * File: test.ncsu.dre.impl.data.TextArtifactTest.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Aug 9, 2007 7:58:07 PM
 */
package test.ncsu.dre.impl.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.dre.impl.data.*;
import edu.ncsu.dre.exception.*;

/**
 * 
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class TextArtifactTest {

	TextArtifact stringArtifact = null;
	TextArtifact textFileArtifact = null;
	TextArtifact pdfFileArtifact = null; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.data.TextArtifact#TextArtifact(java.lang.String)}.
	 */
	@Test
	public void testTextArtifactString() {
		stringArtifact = new TextArtifact("Hello World");
		assertEquals(stringArtifact.getQuery().toString(),"Hello World");
	}

	/**
	 * Test method for {@link edu.ncsu.dre.impl.data.TextArtifact#TextArtifact(java.io.File)}.
	 */
	@Test
	public void testTextArtifactFile() {
		pdfFileArtifact = new TextArtifact(new java.io.File("sample.pdf"));		
		assertEquals(pdfFileArtifact.getQuery().toString().subSequence(0, 10),"This is a ");
		
		textFileArtifact = new TextArtifact(new java.io.File("sample.txt"));
		assertEquals(textFileArtifact.getQuery(),"This is Santthosh Babu Selvadurai");
		
		try{textFileArtifact = new TextArtifact(new java.io.File("resources"));}
		catch(DREIllegalArgumentException e){assert(true);} //Expected behavior}
		
		try{textFileArtifact = new TextArtifact(new java.io.File("unavailable.xml"));}
		catch(DREIllegalArgumentException e){assert(true);} //Expected behavior}
		
		try{textFileArtifact = new TextArtifact(new java.io.File("configuration.xml"));}
		catch(DREIllegalArgumentException e){assert(true);} //Expected behavior}		
	}

}
