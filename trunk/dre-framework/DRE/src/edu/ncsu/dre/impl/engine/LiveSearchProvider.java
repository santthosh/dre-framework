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

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axiom.om.impl.dom.factory.*;
import org.apache.log4j.Logger;

import com.microsoft.schemas.msnsearch.MSNSearchServiceStub;
import com.microsoft.schemas.msnsearch.MSNSearchServiceStub.*;

import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.engine.ServiceProvider;

import javax.xml.namespace.*;
import javax.xml.stream.*;

/**
 * <code>LiveSearchProvider</code> is a data service provider add-in for the DRE framework, it 
 * collects information on the given subset of artifact using Microsoft LiveSearch SOAP service 
 * gathers information on each of the elements of the subset and returns the results as a map. 
 * The source artifact subset element acts as the key and the results obtained acts as the value 
 * of the (key, value) pair in the Map.   
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class LiveSearchProvider extends ServiceProvider {
	
	private static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.engine.LiveSearchProvider");

	private static final long serialVersionUID = 54545658676165L;		
	
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
	private Map<Object, Object> gatherInformation(Collection<Object> artifactSubset, Map<String,String> options){
		
		logger.trace("gatherInformation(Collection<Object> artifactSubset, Map<String,String> options)");
		
		try
		{
			int arraySize = 1;	
			
			if(!validateArguments(artifactSubset,options))
				return null;
		
			MSNSearchServiceStub s = new MSNSearchServiceStub();			
			//If you don't set the request to be CHUNKED you will get HTTP error for this call.
			s._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED,false);

            ResultFieldMask_type0[] rsfmt = new ResultFieldMask_type0[arraySize];						//Format the result set types
            rsfmt[0] = ResultFieldMask_type0.All;            
            ResultFieldMask rsfm = new ResultFieldMask();
            rsfm.setResultFieldMask_type0(rsfmt);
            
            SearchFlags sf = new SearchFlags();                           
            SearchRequest searchRequest = new SearchRequest();		            						//Form the request variable
            searchRequest.setFlags(sf);
            
            SourceRequest[] sr = new SourceRequest[arraySize];            
            ArrayOfSourceRequestRequests srAr = new ArrayOfSourceRequestRequests();
            srAr.setSourceRequest(sr);
            
            sr[0] = new SourceRequest();
            sr[0].setResultFields(rsfm);                                 
            sr[0].setSource(populateSource(options.get("source")));            
                   
            searchRequest.setRequests(srAr);            
            searchRequest.setAppID(options.get("appid").toUpperCase());						            //Set the application ID given by Microsoft onto the SOAP query            
            searchRequest.setCultureInfo(options.get("culture"));							            //Set the language preference example: en-US              
            searchRequest.setSafeSearch(populateSafeSearch(options.get("safesearch")));                                    
            
            Search srch = new Search();            
            List<Object> QueryList = (List<Object>) artifactSubset;
            Map<Object,Object> ResultSetMap = new HashMap<Object, Object>();
            
            for(int a=0;a<QueryList.size();a++)
            {                	
            	searchRequest.setQuery(QueryList.get(a).toString());            	
            	srch.setRequest(searchRequest);
            	            	
            	SearchResponse0 searchResponse0 = s.Search(srch);
            	SearchResponse searchResponse = searchResponse0.getResponse();

            	SourceResponse[] srcResponse = searchResponse.getResponses().getSourceResponse();
            	
            	String ResultSet = "", attributes = "";
            	            	
            	for(int i=0;i<srcResponse.length;i++)
            	{
            		Result[] sourceResults = srcResponse[i].getResults().getResult();
            		
            		ResultSet = "";
            		logger.trace("Literal:" + QueryList.get(a).toString() + " Source:" + srcResponse[i].getSource().toString() + " Total Results: " + srcResponse[i].getTotal());                        		            	
            		java.io.StringWriter xmlResult;            		
            		
            		for(int j=0;j<sourceResults.length;j++)
            		{            					
            			xmlResult = new java.io.StringWriter();
            			
            			XMLOutputFactory factory = XMLOutputFactory.newInstance();
            			XMLStreamWriter writer = factory.createXMLStreamWriter(xmlResult);	
            			            			
            			if(options.get("highlight").compareToIgnoreCase("true")==0)
            			{
            				//To do highlight mechanism
            			}
            			
            			sourceResults[j].serialize(new QName("http://schemas.microsoft.com/MSNSearch/2005/09/fex","Result"), new OMDOMFactory(), writer);
            			writer.close();
            			xmlResult.flush();
            			
            			attributes = " Source=\""+options.get("ID")+"\" Rank=\""+String.valueOf(j+1)+"\"";
            			
            			ResultSet = ResultSet.concat(xmlResult.toString().replaceAll(" xmlns=\"http://schemas.microsoft.com/MSNSearch/2005/09/fex\"",attributes));            			
            		}            		
            		ResultSetMap.put(QueryList.get(a).toString(), ResultSet);
            	}               	
            }
            return ResultSetMap;
		}
		catch(java.rmi.RemoteException e)
		{
			logger.error("Livesearch SOAP service invocation failed!",e);
			throw new DREIllegalArgumentException(DREIllegalArgumentException.ILLEGAL_ARGUMENT,"Search failed. Please check AppID, options and the internet connection!",null);			
		} 
		catch(XMLStreamException e)
		{
			logger.error("XMLStreamException occured while accessing Livesearch results!",e);
			throw new DRERuntimeException(DRERuntimeException.XML_FAILIURE,"Failed during the construction of result stream!",null);
		}				
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#validateArugments(java.util.Collection,java.util.Map)
	 */
	
	private boolean validateArguments(java.util.Collection<Object> artifactSubset,java.util.Map<String,String> options)
	throws DRERuntimeException, DREIllegalArgumentException	
	{		
		logger.trace("validateArguments(java.util.Collection<Object> artifactSubset,java.util.Map<String,String> options)");
		
		//Check for the validity of the arguments
		if(artifactSubset==null || artifactSubset.isEmpty())			
			throw new DRERuntimeException(DRERuntimeException.NULL_QUERY,null);
		
		//Check to see if the mandatory source option is specified, if not default to the web search
		if(options.get("source") == null)
		{
			logger.info("Livesearch source was not specified, defaulting to Web search.");
			options.put("source", "web");
		}
		
		//Check to see if the mandatory APP ID option is specified, if not default to the DRE APPID
		if(options.get("appid") == null)
		{
			logger.warn("Livesearch APPID was not specified, defaulting to DRE Livesearch Application ID. It is highly encouraged to use your own APPID");
			options.put("appid", "7E8E2A6CDEEE7248E0EBF23EDD20303F86364CCE");
		}
		
		//Check to see if the mandatory culture option is specified, if not default to the en-US
		if(options.get("culture") == null)
		{
			logger.info("Livesearch language was set to en-US");				
			options.put("culture", "en-US");
		}

		//Check to see if the mandatory safe search option is specified, if not default to strict
		if(options.get("safesearch") == null)
		{
			logger.info("Livesearch SafeSearch option was set to strict");				
			options.put("safesearch", "strict");
		}
		
		//Check to see if the mandatory highlight option is specified, if not default to the false
		if(options.get("highlight") == null)
		{
			logger.warn("Livesearch highlight has be turned on");
			options.put("highlight", "true");
		}
		
		//Check to see if the mandatory ID option is specified, if not default to the provider ID
		if(options.get("ID") == null)
		{
			logger.warn("ID defaults to NYSE Symbol(MSFT)");
			options.put("ID", "Livesearch");
		}
		
		return true;
	}

	/**
	 * Function to set the source type based on the input option
	 * 
	 * @param Source represented as string specified in the service provider options 
	 * 
	 * @return SourceType 
	 */
	private SourceType populateSource(String source)
	{		
		logger.trace("populateSource(String source)");
		
		 //Set the source type, choose from 9 options Ads/Image/InlineAnswers/News/PhoneBook/QueryLocation/Spelling/Web/WordBreaker
        if(source.compareToIgnoreCase("Web")==0)
        	return SourceType.Web;
        if(source.compareToIgnoreCase("Image")==0)
        	return SourceType.Image;
        if(source.compareToIgnoreCase("InlineAnswers")==0)
        	return SourceType.InlineAnswers;
        if(source.compareToIgnoreCase("News")==0)
        	return SourceType.News;
        if(source.compareToIgnoreCase("PhoneBook")==0)
        	return SourceType.PhoneBook;
        if(source.compareToIgnoreCase("QueryLocation")==0)
        	return SourceType.QueryLocation;
        if(source.compareToIgnoreCase("Spelling")==0)
        	return SourceType.Spelling;
        if(source.compareToIgnoreCase("Ads")==0)
        	return SourceType.Ads;
        if(source.compareToIgnoreCase("WordBreaker")==0)
        	return SourceType.WordBreaker;            

       	logger.warn("Livesearch source was invalid, defaulting to Web search.");
        	return SourceType.Web;
	}
	
	/**
	 * Function to set the SafeSearch preference based on the input option
	 * 
	 * @param SafeSearch preference represented as string specified in the service provider options 
	 * 
	 * @return SafeSearchOptions
	 */
	private SafeSearchOptions populateSafeSearch(String safesearch)
	{		
		logger.trace("populateSafeSearch(String safesearch)");
		
		//Set the SafeSearch options from the options list
        if(safesearch.compareToIgnoreCase("off")==0)
        	return SafeSearchOptions.Off;	
        if(safesearch.compareToIgnoreCase("strict")==0)
        	return SafeSearchOptions.Strict;
        if(safesearch.compareToIgnoreCase("moderate")==0)
        	return SafeSearchOptions.Moderate;
        
        logger.warn("Livesearch safety level was invalid, defaulting to Strict search.");
        	return SafeSearchOptions.Strict;        
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
