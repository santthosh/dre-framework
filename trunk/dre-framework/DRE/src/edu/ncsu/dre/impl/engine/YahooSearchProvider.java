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
 * File: edu.ncsu.dre.impl.engine.LiveSearchProvider.java
 * Created by: <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 * TimeStamp: Jul 30, 2007 7:56:27 PM
 */
package edu.ncsu.dre.impl.engine;

import java.util.*;

import org.apache.log4j.Logger;

import com.yahoo.search.*;

import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.engine.ServiceProvider;

import javax.xml.stream.*;

/**
 * <code>YahooSearchProvider</code> is a data service provider add-in for the DRE framework, it 
 * collects information on the given subset of artifact using Yahoo Search service 
 * gathers information on each of the elements of the subset and returns the results as a map. 
 * The source artifact subset element acts as the key and the results obtained acts as the value 
 * of the (key, value) pair in the Map.   
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class YahooSearchProvider extends ServiceProvider {
	
	private static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.engine.YahooSearchProvider");

	private static final long serialVersionUID = 32483294009890L;
	
	private Collection<Object> artifactSubset;
	private Map<String,String> options;
	private Map<Object,Object> resultSet;
	
	/**
	 * Kick start the thread to collect the results, the arguments have to be set prior to the invocation
	 * and the result set will be populated after the execution
	 * 
	 *  @parm none
	 *  
	 *  @return none
	 */
	public void run()
	{
		this.setResultSet(gatherInformation(this.getArtifactSubset(),this.getOptions()));		
	}
		
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#gatherInformation(java.util.Collection,java.util.Map)
	 */
	protected synchronized Map<Object, Object> gatherInformation(Collection<Object> artifactSubset, Map<String,String> options){
		
		logger.trace("gatherInformation(Collection<Object> artifactSubset, Map<String,String> options)");
		
		try
		{	
			if(!validateArguments(artifactSubset,options))
				return null;
			
			// Create the search client. Pass it your application ID.
            SearchClient client = new SearchClient(options.get("appid"));
		
		    List<Object> QueryList = (List<Object>) artifactSubset;
            Map<Object,Object> ResultSetMap = new HashMap<Object, Object>();
            
            for(int a=0;a<QueryList.size();a++)
            {            	          										//Create the web search request.            	
                WebSearchRequest request = new WebSearchRequest(QueryList.get(a).toString());                                													
                WebSearchResults results = client.webSearch(request);		//Execute the search.
                
                String ResultSet = "";										//Print out how many hits were found.                
                logger.trace("Literal:" + QueryList.get(a).toString() + " Total Results: " + results.getTotalResultsReturned());
                java.io.StringWriter xmlResult;
                	                                
                for (int i = 0; i < results.listResults().length; i++)		 
                {                	        								//Iterate over the results.
                    xmlResult = serializeResult(results.listResults()[i],options.get("ID"));                                                                                
                    ResultSet = ResultSet.concat(xmlResult.toString());                    
                }    
                ResultSetMap.put(QueryList.get(a).toString(), ResultSet);
            }
            return ResultSetMap;
		}
		catch(java.io.IOException e) 
		{
			logger.error("Yahoo search service invocation failed!",e);
            throw new DREIllegalArgumentException(DREIllegalArgumentException.ILLEGAL_ARGUMENT,"Search failed. Please check AppID, options and the internet connection!",null);            
        }
        catch (SearchException se) 
        { 
        	logger.error("Yahoo search exception occured! Could not retrieve results",se);
			throw new DRERuntimeException(DRERuntimeException.XML_FAILIURE,"Yahoo search failed!",null);
        }       
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#validateArugments(java.util.Collection,java.util.Map)
	 */
	
	private synchronized boolean validateArguments(java.util.Collection<Object> artifactSubset,java.util.Map<String,String> options)
	throws DRERuntimeException, DREIllegalArgumentException	
	{		
		logger.trace("validateArguments(java.util.Collection<Object> artifactSubset,java.util.Map<String,String> options)");
		
		//Check for the validity of the arguments
		if(artifactSubset==null || artifactSubset.isEmpty())			
			throw new DRERuntimeException(DRERuntimeException.NULL_QUERY,null);
		
		//Check to see if the mandatory APP ID option is specified, if not default to the DRE APPID
		if(options.get("appid") == null)
		{
			logger.warn("Yahoo search APPID was not specified, defaulting to DRE Yahoo search Application ID. It is highly encouraged to use your own APPID");
			options.put("appid", "eCBIC3LV34EN3FHl35AMrMhA7JoOi4jfPPy1VQrSNr51qWbeO43DkDDLSqG0jmVg");
		}
		
		//Check to see if the mandatory ID option is specified, if not default to the provider ID
		if(options.get("ID") == null)
		{
			logger.warn("ID defaults to NYSE Symbol(YHOO)");
			options.put("ID", "YHOO");
		}
		
		return true;
	}
	
	/**
	 * Format the XML Stream output, from the search results returned by Yahoo Search API
	 * 
	 * @param 	WebSearchResult
	 * 			String
	 * 
	 * @return  java.io.StringWriter
	 */	
	private synchronized java.io.StringWriter serializeResult(WebSearchResult result, String source)
	{
		java.io.StringWriter xmlResult = new java.io.StringWriter();
				    
        try
        {
        	XMLOutputFactory factory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = factory.createXMLStreamWriter(xmlResult);     
            
            writer.writeStartElement("Result");									//Yahoo does not provide inherent serialization so you will have to do it.
            writer.writeAttribute("source", source);
			
            if(result.getTitle()!=null)
            {
            	writer.writeStartElement("Title");
            	writer.writeCharacters(result.getTitle());
            	writer.writeEndElement();
            }

            if(result.getSummary()!=null)
            {
            	writer.writeStartElement("Description");
            	writer.writeCharacters(result.getSummary());
            	writer.writeEndElement();
            }

            if(result.getClickUrl()!=null)
            {
            	writer.writeStartElement("Url");
            	writer.writeCharacters(result.getClickUrl());                                                                             
            	writer.writeEndElement();
            }

            if(result.getUrl()!=null)
            {
            	writer.writeStartElement("DisplayUrl");
            	writer.writeCharacters(result.getUrl());                                                                             
            	writer.writeEndElement();
            }

            if(result.getCache()!=null)
            {
            	if(result.getCache().getUrl()!=null)
            	{
            		writer.writeStartElement("CacheUrl");
            		writer.writeCharacters(result.getCache().getUrl());                                                                             
            		writer.writeEndElement();
            	}

            	if(result.getCache().getSize()!=null)
            	{
            		writer.writeStartElement("CacheSize");
            		writer.writeCharacters(result.getCache().getSize());                                                                             
            		writer.writeEndElement();
            	}
            }

            if(result.getMimeType()!=null)
            {
            	writer.writeStartElement("Mimetype");
            	writer.writeCharacters(result.getMimeType());                                                                             
            	writer.writeEndElement();
            }

            if(result.getModificationDate()!=null)
            {
            	writer.writeStartElement("ModificationDate");
            	writer.writeCharacters(result.getModificationDate());                                                                             
            	writer.writeEndElement();
            }

            writer.writeEndElement();

            writer.close();
            
            xmlResult.flush();
        }
        catch(XMLStreamException e)
		{
			logger.error("XMLStreamException occured while accessing Yahoo search results!",e);
			throw new DRERuntimeException(DRERuntimeException.XML_FAILIURE,"Failed during the construction of result stream!",null);
		}
        
        return xmlResult;
	}

	/**
	 * @return the artifactSubset
	 */
	public synchronized Collection<Object> getArtifactSubset() {
		return artifactSubset;
	}

	/**
	 * @param artifactSubset the artifactSubset to set
	 */
	public synchronized void setArtifactSubset(Collection<Object> artifactSubset) {
		this.artifactSubset = artifactSubset;
	}

	/**
	 * @return the options
	 */
	public synchronized Map<String, String> getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public synchronized void setOptions(Map<String, String> options) {
		this.options = options;
	}

	/**
	 * @return the resultSet
	 */
	public synchronized Map<Object, Object> getResultSet() {
		return resultSet;
	}

	/**
	 * @param resultSet the resultSet to set
	 */
	public synchronized void setResultSet(Map<Object, Object> resultSet) {
		this.resultSet = resultSet;
	}
} 
