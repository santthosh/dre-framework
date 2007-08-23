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
 * File: edu.ncsu.dre.impl.engine.DummySegregator.java
 * Created by: selvas
 * TimeStamp: Jul 24, 2007 11:12:47 AM
 */
package edu.ncsu.dre.impl.engine;

import java.util.*;
import edu.ncsu.dre.engine.Segregator;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;


/**
 * <code>DummySegregator</code> is the default text segregator for DRE framework. It is used to  
 * divide an {@link edu.ncsu.dre.data.Artifact} into a manageable/requisite pieces of literals 
 * and sequences which are then fed to {@link edu.ncsu.dre.engine.ResearchScheduler} for further research.
 * and analysis<p>
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DummySegregator implements Segregator {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.data.engine.DummySegregator");
	
	private static final long serialVersionUID = 786312109382138L;
	
	/**
	 * This method implements the Dummy segeration functionality of a given String
	 * which is available as an Object. Returns List of Objects. The Dummy segregator 
	 * does not split the given String artifact into words and sequences of words. Just
	 * bounces it back
	 * 
	 * @param artifact
	 * 
	 * @return Collection<Object>
	 *  
	 * {@code
	 * ReturnList
	 *    |__Quote as is 
	 * 	  
	 * }
	 */
	public Collection<Object> segregateArtifact(Object artifact){
		
		logger.trace("segregateArtifact(Object artifact)");
		
		String sArtifact = (String)artifact;
		
		List<Object> queryList = new ArrayList<Object>();
		
		ArrayList<String> wordList = new ArrayList<String>() ;
		
		wordList.add(sArtifact);
		
		queryList.add(wordList);
		return queryList;
	}		
}
