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
 * File: edu.ncsu.dre.impl.engine.LexicalSegregator.java
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
 * <code>LexicalSegregator</code> is the default text segregator for DRE framework. It is used to  
 * divide an {@link edu.ncsu.dre.data.Artifact} into a manageable/requisite pieces of literals 
 * and sequences which are then fed to {@link edu.ncsu.dre.engine.ResearchScheduler} for further research.
 * and analysis<p>
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class LexicalSegregator implements Segregator {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.data.engine.LexicalSegregator");
	
	private static final long serialVersionUID = 7863186186756348L;
	
	/**
	 * This method implements the Lexical segeration functionality of a given String
	 * which is available as an Object. Returns List of Objects. The lexical segregator 
	 * splits the given String artifact into words and sequences of words. Prepares them 
	 * into lists of strings as shown below. The scheduler will use these words and sequence 
	 * of words to gather more information on the artifact.
	 * 
	 * @param artifact
	 * 
	 * @return Collection<Object>
	 *  
	 * {@code
	 * ReturnList
	 *    |__List of words 
	 * 	  |__List of sub sequences (of configurable length)
	 * }
	 */
	public Collection<Object> segregateArtifact(Object artifact){
		
		logger.trace("segregateArtifact(Object artifact)");
		
		String sArtifact = (String)artifact;
		
		List<Object> queryList = new ArrayList<Object>();
		
		ArrayList<String> wordList = new ArrayList<String>() ;
		
		try
		{
			StandardAnalyzer analyst = new StandardAnalyzer();
			
			if(sArtifact==null)
				return queryList;
			
			TokenStream tokenStream = analyst.tokenStream("Input Stream", new java.io.StringReader(sArtifact.trim()));
			
			Token word = null;
			do
			{													//Remove duplicates and insert into the list
				if(word!=null && !wordList.contains(word.termText()))
					wordList.add(word.termText());				
				word = tokenStream.next();				
			}while(word!=null);
		}
		catch(java.io.IOException ioe)
		{
			logger.error("IOException occured while parsing input stream!",ioe);
		}	
		queryList.add(wordList);
		return queryList;
	}		
}
