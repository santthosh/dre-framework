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

import com.microsoft.schemas.msnsearch.MSNSearchServiceStub;
import com.microsoft.schemas.msnsearch.MSNSearchServiceStub.*;

import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.engine.ServiceProvider;

import javax.xml.namespace.*;
import javax.xml.stream.*;
import javax.xml.transform.stream.StreamResult;

/**
 * <code>LiveSearchProvider</code> is a data service provider add-in for the DRE framework, it 
 * collects information on the given subset of artifact using Microsoft LiveSearch SOAP service 
 * gathers information on each of the elements of the subset and returns the results as a map. 
 * The source artifact subset element acts as the key and the results obtained acts as the value 
 * of the (key, value) pair in the Map.   
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class LiveSearchProvider implements ServiceProvider {

	private static final long serialVersionUID = 54545658676165L;
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#gatherInformation(java.util.Collection,java.util.Map)
	 */
	@Override
	public Map<Object, Object> gatherInformation(Collection<Object> artifactSubset, Map<String,String> options){		
		try
		{
			int arraySize = 1;	
			
			if(!validateArguments(artifactSubset,options))
				return null;
		
			MSNSearchServiceStub s = new MSNSearchServiceStub();			
			//If you don't set the request to be CHUNKED you will get HTTP error for this call.
			s._getServiceClient().getOptions().setProperty(HTTPConstants.CHUNKED,false);

			//Format the result set types
            ResultFieldMask_type0[] rsfmt = new ResultFieldMask_type0[arraySize];
            rsfmt[0] = ResultFieldMask_type0.All;            
            ResultFieldMask rsfm = new ResultFieldMask();
            rsfm.setResultFieldMask_type0(rsfmt);
            
            SearchFlags sf = new SearchFlags();               
            
            //Form the request variable
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setFlags(sf);
            
            SourceRequest[] sr = new SourceRequest[arraySize];
            
            ArrayOfSourceRequestRequests srAr = new ArrayOfSourceRequestRequests();
            srAr.setSourceRequest(sr);
            
            sr[0] = new SourceRequest();
            sr[0].setResultFields(rsfm);
                                 
            //Set the source type, choose from 9 options Ads/Image/InlineAnswers/News/PhoneBook/QueryLocation/Spelling/Web/WordBreaker
            if(options.get("source").compareToIgnoreCase("Web")==0)
            	sr[0].setSource(SourceType.Web);
            if(options.get("source").compareToIgnoreCase("Image")==0)
            	sr[0].setSource(SourceType.Image);
            if(options.get("source").compareToIgnoreCase("InlineAnswers")==0)
            	sr[0].setSource(SourceType.InlineAnswers);
            if(options.get("source").compareToIgnoreCase("News")==0)
            	sr[0].setSource(SourceType.News);
            if(options.get("source").compareToIgnoreCase("PhoneBook")==0)
            	sr[0].setSource(SourceType.PhoneBook);
            if(options.get("source").compareToIgnoreCase("QueryLocation")==0)
            	sr[0].setSource(SourceType.QueryLocation);
            if(options.get("source").compareToIgnoreCase("Spelling")==0)
            	sr[0].setSource(SourceType.Spelling);
            if(options.get("source").compareToIgnoreCase("Ads")==0)
            	sr[0].setSource(SourceType.Ads);
            if(options.get("source").compareToIgnoreCase("WordBreaker")==0)
            	sr[0].setSource(SourceType.WordBreaker);            
            if(sr[0].getSource() == null)
            {
            	System.out.println("[Warning]Livesearch source was invalid, defaulting to Web search.");
            	sr[0].setSource(SourceType.Web);
            }
                   
            searchRequest.setRequests(srAr);
            
            //Set the application ID given by Microsoft onto the SOAP query
            searchRequest.setAppID(options.get("appid").toUpperCase());
            
            //Set the language preference example: en-US
            searchRequest.setCultureInfo(options.get("culture"));     
            
            //Set the SafeSearch options from the options list
            if(options.get("safesearch").compareToIgnoreCase("off")==0)
            	searchRequest.setSafeSearch(SafeSearchOptions.Off);	
            if(options.get("safesearch").compareToIgnoreCase("strict")==0)
            	searchRequest.setSafeSearch(SafeSearchOptions.Strict);
            if(options.get("safesearch").compareToIgnoreCase("moderate")==0)
            	searchRequest.setSafeSearch(SafeSearchOptions.Moderate);
            if(searchRequest.getSafeSearch()==null)
            {
            	System.out.println("[Warning]Livesearch safety level was invalid, defaulting to Strict search.");
            	searchRequest.setSafeSearch(SafeSearchOptions.Strict);
            }
                                    
            Search srch = new Search();
            
            List<Object> QueryList = (List<Object>) artifactSubset;
            
            for(int a=0;a<QueryList.size();a++)
            {    
            	System.out.println("Hai");
            	searchRequest.setQuery(QueryList.get(a).toString());            
            	srch.setRequest(searchRequest);
            	
            	

            	SearchResponse0 searchResponse0 = s.Search(srch);
            	SearchResponse searchResponse = searchResponse0.getResponse();
            	
            	System.out.println("Hello");

            	SourceResponse[] srcResponse = searchResponse.getResponses().getSourceResponse();
            	
            	

            	for(int i=0;i<srcResponse.length;i++)
            	{
            		Result[] sourceResults = srcResponse[i].getResults().getResult();

            		System.out.println( QueryList.get(a).toString() + " " + 
            								srcResponse[i].getSource().toString() + 
            								" Total Results: " + srcResponse[i].getTotal());            
            		
            		java.io.StringWriter xmlResult = new java.io.StringWriter();            		
            		
            		for(int j=0;j<sourceResults.length;j++)
            		{            							
            			XMLOutputFactory factory = XMLOutputFactory.newInstance();
            			XMLStreamWriter writer = factory.createXMLStreamWriter(xmlResult);	
            									
            			sourceResults[j].serialize(new QName("http://schemas.microsoft.com/MSNSearch/2005/09/fex","source"), new OMDOMFactory(), writer);
            			writer.close();	
            			            			            			
            			/*if(sourceResults[j].getTitle()!=null && !sourceResults[j].getTitle().isEmpty())
            				System.out.println("Title: " + sourceResults[j].getTitle());
            			if(sourceResults[j].getDescription()!=null && !sourceResults[j].getDescription().isEmpty())
            				System.out.println("Description: " + sourceResults[j].getDescription());
            			if(sourceResults[j].getUrl()!=null && !sourceResults[j].getUrl().isEmpty())
            				System.out.println("URL: " + sourceResults[j].getUrl());
            			System.out.println("***");*/
            		}
            		System.out.println(xmlResult.toString());
            	}
            }
		}
		catch(java.rmi.RemoteException e)
		{
			e.printStackTrace();
		}
		catch(XMLStreamException e)
		{
			e.printStackTrace();
		}
		catch(DRERuntimeException e)
		{			
			return null;								//Return an empty result set
		}

		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#validateArugments(java.util.Collection,java.util.Map)
	 */
	
	public boolean validateArguments(java.util.Collection<Object> artifactSubset,java.util.Map<String,String> options)
	throws DRERuntimeException, DREIllegalArgumentException	
	{
		//Check for the validity of the arguments
		if(artifactSubset==null || artifactSubset.isEmpty())			
			throw new DRERuntimeException(DRERuntimeException.NULL_QUERY,null);
		
		//Check to see if the mandatory source option is specified, if not default to the web search
		if(options.get("source") == null)
		{
			System.out.println("[Info]Livesearch source was not specified, defaulting to Web search.");
			options.put("source", "web");
		}
		
		//Check to see if the mandatory APP ID option is specified, if not default to the DRE APPID
		if(options.get("appid") == null)
		{
			System.out.println("[Warning]Livesearch APPID was not specified, defaulting to DRE Livesearch Application ID. It is highly" +
					" encouraged to use your own APPID");
			options.put("appid", "7E8E2A6CDEEE7248E0EBF23EDD20303F86364CCE");
		}
		
		//Check to see if the mandatory culture option is specified, if not default to the en-US
		if(options.get("culture") == null)
		{
			System.out.println("[Info]Livesearch language was set to en-US");				
			options.put("culture", "en-US");
		}

		//Check to see if the mandatory safe search option is specified, if not default to strict
		if(options.get("safesearch") == null)
		{
			System.out.println("[Info]Livesearch SafeSearch option was set to strict");				
			options.put("safesearch", "strict");
		}
		
		return true;
	}
	
	/**
	 * 
	 */
	
	public void ResultXMLSerialization(Result result)
	{
		try
		{			
			java.io.StringWriter xmlResult = new java.io.StringWriter();						
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = factory.createXMLStreamWriter(xmlResult);	
									
			result.serialize(new QName("http://schemas.microsoft.com/MSNSearch/2005/09/fex","source"), new OMDOMFactory(), writer);
			writer.close();	
			
			System.out.println(xmlResult.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return;
	}
}
