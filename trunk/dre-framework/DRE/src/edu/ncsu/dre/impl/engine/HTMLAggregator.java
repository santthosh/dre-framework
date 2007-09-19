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
 * File: edu.ncsu.dre.impl.engine.HTMLAggregator.java
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
import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.data.results.*;
import edu.ncsu.dre.engine.Aggregator;
import edu.ncsu.dre.util.*;

/**
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class HTMLAggregator implements Aggregator {
	
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
		  
		  try
		  {
			  StringReader stringReader = new StringReader(resultStream);
			  XMLInputFactory factory = XMLInputFactory.newInstance();
			  XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(stringReader);
			  
			  JAXBContext binderContext = JAXBContext.newInstance("edu.ncsu.dre.data.results");
			  Unmarshaller unmarshaller = binderContext.createUnmarshaller();
			  Marshaller marshaller = binderContext.createMarshaller();
			  
			  ResultSubSet resultSubSet = (ResultSubSet) unmarshaller.unmarshal(xmlStreamReader);
			  
			  System.out.println(resultSubSet.getArtifactSubset());
			  List<ResultComponent> resultList = resultSubSet.getResult();
			  			  
			  DynamicComparator.sort(resultList,"DisplayUrl",false);
			  
			  int NegativeRanker = 1;
			  
			  for(int i=0;i<resultList.size();i++)
			  {
				  ResultComponent result = resultList.get(i);
				  ResultComponent resultNext = null;

				  if(i==(resultList.size()-1)){break;}
				  else{resultNext = resultList.get(i+1);}

				  if(result.getDisplayUrl().compareToIgnoreCase(resultNext.getDisplayUrl())==0)
				  {
					  if(result.getRank() > 0)
					  {
						  NegativeRanker--;
						  result.setRank((double)NegativeRanker);						 
						  resultNext.setRank((double)NegativeRanker);
					  }
					  else
					  {
						  resultNext.setRank(result.getRank());
					  }
				  }				  				 				 				 
			  }			  
			  DynamicComparator.sort(resultList,"Rank",true);			  
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
}
