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
 * File: edu.ncsu.dre.impl.engine.XMLAggregator.java
 * Created by: selvas
 * TimeStamp: Jul 24, 2007 11:21:12 AM
 */
package edu.ncsu.dre.impl.engine;

import java.io.*;
import java.util.*;

import javax.xml.bind.*;
import javax.xml.stream.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.data.results.*;
import edu.ncsu.dre.engine.Aggregator;
import edu.ncsu.dre.util.*;

/**
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class SRRSimCGAggregator implements Aggregator {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.engine.XMLAggregator");
	
	String XMLResultSet = null;
	/**
	 * Generalized method for processing result aggregation on the given artifact search results.
	 * Collates the results and presents a HTML document
	 * 
	 * @param artifact
	 * 		  ObjectList
	 * 		  ResultMap
	 * 
	 * @return javax.xml.transform.stream.StreamResult
	 */
	public @SuppressWarnings("unchecked")  StreamResult aggregateResults(edu.ncsu.dre.data.Artifact artifact, 
																	java.util.List<Object> ObjectList,
																	java.util.List<Map<Object,Object>> ResultMap)
	{		
		for(int i=0;i<ObjectList.size();i++)
		{				
			ArrayList<String> wordList = (ArrayList<String>) ObjectList.get(i);
			
			Map<Object,Object> xmlResultMap = ResultMap.get(i);
			
			for(int j=0;j<wordList.size();j++)
			{
				XMLResultSet = collateSubSetResults(wordList.get(j),xmlResultMap.get(wordList.get(j)).toString()).getOutputStream().toString();
			}
		}
		logger.info("Result: " + XMLResultSet);				
		
		StreamResult HTMLResult = null;
		
		try
		{									
			HTMLResult = applyXSLTransformation(XMLResultSet,"xml/XML2HTML.xslt");
		}
		catch(IOException ie)
		{
			logger.error("IOException occured during the extraction of XSLTFilter",ie);
		}	
		catch(TransformerException te)
		{
			logger.error("XSLTFiltering failed!",te);
		}										
		return HTMLResult;
	}
	
	/**
	 * Method to convert input XML as string into a HTML document and present it as a StreamResult.
	 * this method is thread safe
	 * 
	 * @param artifact
	 * 		  ObjectList
	 * 		  ResultMap
	 * 
	 * @return javax.xml.transform.stream.StreamResult
	 */
	  synchronized  public javax.xml.transform.stream.StreamResult applyXSLTransformation(String sXMLData, String sXSLFile)
	        throws  TransformerException, TransformerConfigurationException,java.io.IOException
	  {	     
	    // Use the static TransformerFactory.newInstance() method to instantiate 
	    // a TransformerFactory. The javax.xml.transform.TransformerFactory 
	    // system property setting determines the actual class to instantiate
	    TransformerFactory tFactory = TransformerFactory.newInstance();
			    
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    // Use the TransformerFactory to instantiate a transformer that will work with  
	    // the style sheet you specify. This method call also processes the style sheet
	    // into a compiled Templates object.
	    Transformer transformer = tFactory.newTransformer(new StreamSource(sXSLFile));
	    
	    javax.xml.transform.stream.StreamResult streamResult = new javax.xml.transform.stream.StreamResult(outputStream);
	    // Use the transformer to apply the associated templates object to an XML document
	    // and write the output to a file with the same name as the XSL template file that 
	    // was passed in sXSLFile.
	    transformer.transform(new StreamSource(new java.io.StringReader(sXMLData)),streamResult);

	    return streamResult;
	  }

	  /**
	   * 
	   */
	  synchronized public javax.xml.transform.stream.StreamResult collateSubSetResults(String artifactSubset,String resultStream)
	  {	
		  resultStream = "<ResultSubSet artifactSubset=\"" + artifactSubset + "\">" + resultStream + "</ResultSubSet>";
		  java.io.ByteArrayOutputStream bufferedOutputStream = new java.io.ByteArrayOutputStream();
		  StreamResult streamResult = new StreamResult(bufferedOutputStream);
		  
		  /*Sndt = NDT/QLEN, where 
		   * NDT is the number of distinct terms in the title matching the query
		   * QLEN is the number of distinct terms in the query 
		   */		  
		  double TitleSndt = 0.0;
		  double SnippetSndt = 0.0;
		  double GuideSndt = 0.0;
		  double GTitleSndt = 0.0;
		  double GSnippetSndt = 0.0;
		  
		  /*Stnt = TDT/TITLEN, where 
		   * TDT is the number of erms in the title or snippet matching the query
		   * TITLEN is the length of the title 
		   */		  
		  double TitleStnt = 0.0;
		  double SnippetStnt = 0.0;
		  double GuideStnt = 0.0;
		  double GTitleStnt = 0.0;
		  double GSnippetStnt = 0.0;
		  
		  /*Sim(T/S,Q) = Sndt + (Stnt/QLEN), where 
		   * QLEN is the number of distinct terms in the query 
		   */		  
		  double simTQ = 0.0;
		  double simSQ = 0.0;
		  double simGQ = 0.0;
		  double simGT = 0.0;
		  double simGS = 0.0;
		  
		  /*
		   * C = 0.2
		   * Similarity = TNDT * (C * Sim(T,Q) + (1-C) * Sim(S,Q))/QLEN
		   */
		  double similarity = 0.0;
		  double C = 0.2;

		  Map<String,Integer> QUERY = getDistinctTerms(artifactSubset);
		  Map<String,Integer> GUIDE = null;
		  double QLEN = (double)QUERY.size();
		  double TLEN=0.0,SLEN=0.0;
		  
		  try
		  {
			  StringReader stringReader = new StringReader(resultStream);
			  System.out.println(resultStream);
			  XMLInputFactory factory = XMLInputFactory.newInstance();
			  XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(stringReader);
			  
			  JAXBContext binderContext = JAXBContext.newInstance("edu.ncsu.dre.data.results");
			  Unmarshaller unmarshaller = binderContext.createUnmarshaller();
			  Marshaller marshaller = binderContext.createMarshaller();
			  
			  ResultSubSet resultSubSet = (ResultSubSet) unmarshaller.unmarshal(xmlStreamReader);
			  
			  System.out.println(resultSubSet.getArtifactSubset());
			  List<ResultComponent> resultList = resultSubSet.getResult();
			  
			  if(contentGuidance.getQuery() != null)
			  {						  
				  GUIDE = getDistinctTerms((String)contentGuidance.getQuery());
				  GuideSndt = (double) getDistinctMatchCount(QUERY,GUIDE)/QLEN;
				  
				  GuideStnt = 0.0;
				  if(getCount(GUIDE)>0)
					  GuideStnt = (double)getMatchCount(QUERY,GUIDE)/(double)getCount(GUIDE);
				  simGQ = GuideSndt + GuideStnt/QLEN;
				  
				  System.out.println("Sim(G,Q) = " + simGQ);
			  }
				  			  			  
			  for(int i=0;i<resultList.size();i++)			  
			  {
				  ResultComponent result = resultList.get(i);
				  				  
				  Map<String,Integer> TITLE = getDistinctTerms(result.getTitle());
				  TLEN = TITLE.size();
				  
				  Map<String,Integer> SNIPPET = getDistinctTerms(result.getDescription());
				  SLEN = SNIPPET.size();
				  
				  TitleSndt = (double)getDistinctMatchCount(QUERY,TITLE)/QLEN;
				  SnippetSndt = (double)getDistinctMatchCount(QUERY,SNIPPET)/QLEN;
				  
				  TitleStnt = 0.0;
				  if(getCount(TITLE)>0)
					  TitleStnt = (double)getMatchCount(QUERY,TITLE)/(double)getCount(TITLE);
				  
				  SnippetStnt = 0.0;
				  if(getCount(SNIPPET)>0)
					  SnippetStnt = (double)getMatchCount(QUERY,SNIPPET)/(double)getCount(SNIPPET);
				  
				  simTQ = TitleSndt + TitleStnt/QLEN;
				  simSQ = SnippetSndt + SnippetStnt/QLEN;
				  				  
				  Map<String,Integer> TITLE_AND_SNIPPET = getDistinctTerms(result.getTitle() + result.getDescription());				  
				  similarity = (double) TITLE_AND_SNIPPET.size() * (C * simTQ + (1-C) * simSQ) / QLEN;
				  
				  if(contentGuidance.getQuery() != null)
				  {					  
					  GTitleSndt = (double)getDistinctMatchCount(TITLE,GUIDE)/TLEN;
					  GSnippetSndt = (double)getDistinctMatchCount(SNIPPET,GUIDE)/SLEN;
					  
					  GTitleStnt = 0.0;
					  if(getCount(TITLE)>0)
						  GTitleStnt = (double)getMatchCount(TITLE,GUIDE)/(double)getCount(GUIDE);
					  
					  GSnippetStnt = 0.0;
					  if(getCount(SNIPPET)>0)
						  GSnippetStnt = (double)getMatchCount(SNIPPET,GUIDE)/(double)getCount(GUIDE);
					  
					  simGT = GTitleSndt + GTitleStnt/TLEN;
					  simGS = GSnippetSndt + GSnippetStnt/SLEN;
					  					  				
					  similarity += (double) GUIDE.size() * (C * simGT + (1-C) * simGS) / (TLEN+SLEN);					  
					  similarity = similarity/2; 
					  
					  System.out.print("Similarity: " + similarity);
					  System.out.println("Guide-Title Similarity: " + simGT + " Guide-Snippet Similarity: " + simGS);
				  }
				  				  				  				
				  logger.debug("Similarity: " + similarity);
				  logger.debug(" Title: SNDT = " + TitleSndt + " STNT = " + TitleStnt + " Sim(T,Q) = " + simTQ);					  				 				 				 
				  logger.debug(" Snippet: SNDT = " + SnippetSndt + " STNT = " + SnippetStnt + " Sim(S,Q) = " + simSQ);
				  
				  result.setRank(similarity);
			  }			  
			  DynamicComparator.sort(resultList,"Rank",false);			  
			  marshaller.marshal(resultSubSet,streamResult);		  
		  }
		  catch(XMLStreamException xse)
		  {
			    logger.error("XMLStreamException occured!",xse);			
				throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION,null);								  
		  }
		  catch(JAXBException je)
		  {
				logger.error("JAXBException occured!",je);			
				throw new DREIllegalArgumentException(DREIllegalArgumentException.CONFIGURATION_FILE_PARSE_ERROR,null);						
		  }		  			  
		  return streamResult;
	  }
	  
		/**
		 * @return the xMLResultSet
		 */
		public synchronized String getXMLResultSet() {
			return XMLResultSet;
		}

		/**
		 * @param resultSet the xMLResultSet to set
		 */
		public synchronized void setXMLResultSet(String resultSet) {
			XMLResultSet = resultSet;
		}
		
		/**
		 * This function retrieves the set of distinct terms from the input query and populates a HashMap
		 * 
		 * @param String input
		 * @return java.util.Map<String,Integer> 
		 */
		public Map<String,Integer> getDistinctTerms(String input)
		{			
			HashMap<String,Integer> termMap = new HashMap<String,Integer>();			
			
			try
			{
				StandardAnalyzer analyst = new StandardAnalyzer();
				
				if(input==null)
					return termMap;
				
				TokenStream tokenStream = analyst.tokenStream("", new java.io.StringReader(input.trim()));				
				Token word = null;
				do
				{	
					if(word!=null)
					{
						if(termMap.containsKey(word.termText()))			//Increment the hash table if it is already present
							termMap.put(word.termText(),termMap.get(word.termText())+1);
						else
							termMap.put(word.termText(),1); 				//Else insert into the hash table													
					}
					
					word = tokenStream.next();				
				}while(word!=null);
			}
			catch(java.io.IOException ioe)
			{
				logger.error("IOException occured while parsing input string!",ioe);
			}				
			return termMap;
		}
		
		/**
		 * This function accepts two hashes of strings and returns the count of distinct terms that appear 
		 * in both the hashes.
		 *  
		 * @param Map<String,Integer> source,Map<String,Integer> destination
		 * @return int 
		 */
		public int getDistinctMatchCount(Map<String,Integer> source,Map<String,Integer> destination)
		{
			int count = 0;
			
			Set<String> keySet = destination.keySet();
			Iterator<String> iterator = keySet.iterator();
			
			while(iterator.hasNext())
			{
				if(source.containsKey(iterator.next()))
					count++;
			}
			
			return count;
		}
		
		/**
		 * This function accepts two hashes of strings and returns the count of terms that appear 
		 * in both the hashes + values of the individual terms in the destination hash
		 *  
		 * @param Map<String,Integer> source,Map<String,Integer> destination
		 * @return int 
		 */
		public int getMatchCount(Map<String,Integer> source,Map<String,Integer> destination)
		{
			int count = 0;
			String term = null;
			
			Set<String> keySet = destination.keySet();
			Iterator<String> iterator = keySet.iterator();
			
			while(iterator.hasNext())
			{
				term = iterator.next();
				if(source.containsKey(term))
					count+=destination.get(term);
			}
			
			return count;
		}
		
		/**
		 * This function accepts a single hash of strings and returns the count of terms that appear 
		 * in the hashes + values of the individual terms in the source hash
		 *  
		 * @param Map<String,Integer> source
		 * @return int 
		 */
		public int getCount(Map<String,Integer> source)
		{
			int count = 0;
			String term = null;
			
			Set<String> keySet = source.keySet();
			Iterator<String> iterator = keySet.iterator();
			
			while(iterator.hasNext())
			{
				term = iterator.next();
				if(source.containsKey(term))
					count+=source.get(term);
			}
			
			return count;
		}
}
