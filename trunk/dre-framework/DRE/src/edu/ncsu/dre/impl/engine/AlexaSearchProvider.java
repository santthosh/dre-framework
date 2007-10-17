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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.engine.ServiceProvider;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.*;
import javax.xml.stream.*;

import edu.ncsu.dre.impl.engine.alexa.*;

/**
 * <code>AlexaSearchProvider</code> is a data service provider add-in for the DRE framework, it 
 * collects information on the given subset of artifact using Amazon Alexa Web Search service 
 * gathers information on each of the elements of the subset and returns the results as a map. 
 * The source artifact subset element acts as the key and the results obtained acts as the value 
 * of the (key, value) pair in the Map.   
 *
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class AlexaSearchProvider extends ServiceProvider {
	
	private static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.engine.AlexaSearchProvider");

	private static final long serialVersionUID = 54545658676165L;
	
	private static final String ACTION_NAME = "Search";

	private static final String RESPONSE_GROUP_NAME = "Context";

	private static final String VERSION = "2007-03-15";

	private static final String AWS_BASE_URL = "http://wsearch.amazonaws.com?";

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private static final String DATEFORMAT_AWS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	private static final String SEARCHFIELDS = "Anchor?,Site,Url,Title,Dmoz,SLD;Text";
	
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
	
	/**
	 * Generate a timestamp for use with AWS request signing
	 *
	 * @param date
	 *            current date
	 * @return timestamp
	 */
	public static String getTimestampFromLocalTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(DATEFORMAT_AWS);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		return format.format(date);
	}
	
	/**
	 * Computes RFC 2104-compliant HMAC signature.
	 *
	 * @param data
	 *            The data to be signed.
	 * @param key
	 *            The signing key.
	 * @return The base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException
	 *             when signature generation fails
	 */
	public static String generateSignature(String data, String key) throws java.security.SignatureException {
		String result;
		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
					HMAC_SHA1_ALGORITHM);

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			// result = Encoding.EncodeBase64(rawHmac);
			result = new BASE64Encoder().encode(rawHmac);

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : "
					+ e.getMessage());
		}
		return result;
	}
	
	/**
	 * Make a request to the specified Url and return the results as a String
	 *
	 * @param urlBuffer
	 * @return the XML document as a String
	 * @throws java.net.MalformedURLException
	 * @throws IOException
	 */
	public static String makeRequest(String requestUrl) throws java.net.MalformedURLException, IOException {
		URL url = new URL(requestUrl);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();

		// Read the response

		StringBuffer sb = new StringBuffer();
		int c;
		int lastChar = 0;
		while ((c = in.read()) != -1) {
			if (c == '<' && (lastChar == '>'))
				sb.append('\n');
			sb.append((char) c);
			lastChar = c;
		}
		in.close();
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.engine.ServiceProvider#gatherInformation(java.util.Collection,java.util.Map)
	 * 
	 * Make a test request to the AlexaWebInfo Service WebSearch operation
	 */
	private Map<Object, Object> gatherInformation(Collection<Object> artifactSubset, Map<String,String> options){
		
		logger.trace("gatherInformation(Collection<Object> artifactSubset, Map<String,String> options)");
		
		try
		{
			if(!validateArguments(artifactSubset,options))
				return null;
			
			String accessKey = options.get("accesskey");			
			String secretKey = options.get("secretkey");
			
            List<Object> QueryList = (List<Object>) artifactSubset;
            Map<Object,Object> ResultSetMap = new HashMap<Object, Object>();
            
            for(int a=0;a<QueryList.size();a++)
            {     
            	//Get current time
    			String timestamp = AlexaSearchProvider.getTimestampFromLocalTime(Calendar.getInstance().getTime());

    			// Generate request signature
    			String signature = AlexaSearchProvider.generateSignature(ACTION_NAME + timestamp, secretKey);

    			String query = QueryList.get(a).toString();
    			
    			// Don't return error pages such as 404's 500's or other 'irrelevant' pages
    			query = query.toLowerCase();
    			query = query + " pagetype:(-irrelevant)";

    			StringBuffer urlBuffer = new StringBuffer(AWS_BASE_URL);
    			urlBuffer.append("&Action=");
    			urlBuffer.append(ACTION_NAME);
    			urlBuffer.append("&ResponseGroup=");
    			urlBuffer.append(RESPONSE_GROUP_NAME);
    			urlBuffer.append("&AWSAccessKeyId=");
    			urlBuffer.append(accessKey);
    			urlBuffer.append("&Version=");
    			urlBuffer.append(VERSION);
    			urlBuffer.append("&SearchFields=");
    			urlBuffer.append(SEARCHFIELDS);
    			urlBuffer.append("&Signature=");
    			urlBuffer.append(URLEncoder.encode(signature, "UTF-8"));
    			urlBuffer.append("&Timestamp=");
    			urlBuffer.append(URLEncoder.encode(timestamp, "UTF-8"));
    			urlBuffer.append("&Query=");
    			urlBuffer.append(URLEncoder.encode(query, "UTF-8"));  
    			
            	
            	String ResultSet = "", attributes = "";
            	String xmlResponse = makeRequest(urlBuffer.toString());
            	xmlResponse = xmlResponse.replaceAll(" xmlns=\"http://wsearch.amazonaws.com/doc/2007-03-15/\"","");
            	System.out.println(xmlResponse);
            	            	            	            																	                
                try
      		  	{
      			  StringReader stringReader = new StringReader(xmlResponse);
      			  XMLInputFactory factory = XMLInputFactory.newInstance();
      			  XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(stringReader);
      			  
      			  JAXBContext binderContext = JAXBContext.newInstance("edu.ncsu.dre.impl.engine.alexa");
      			  Unmarshaller unmarshaller = binderContext.createUnmarshaller();
      			  Marshaller marshaller = binderContext.createMarshaller();
      			  
      			  SearchResponse searchResponse = (SearchResponse) unmarshaller.unmarshal(xmlStreamReader);
      			  
      			  //Print out how many hits were found.                
                  logger.trace("Literal:" + QueryList.get(a).toString() + " Total Results: " + searchResponse.getSearchResult().getEstimatedNumberOfDocuments());
                  java.io.StringWriter xmlResult = new java.io.StringWriter();
                  
                  for(int j=0;j<searchResponse.getSearchResult().getDocument().size();j++)
                  {
                	  xmlResult = new java.io.StringWriter();
                	  
                	  ResultComponent resultComponent = searchResponse.getSearchResult().getDocument().get(j);
                	  
                	  xmlResult.append("<Result Source=\""+options.get("ID")+"\" Rank=\""+String.valueOf(j+1)+"\">");
                	  xmlResult.append("<Title>");xmlResult.append(resultComponent.getTitle());xmlResult.append("</Title>");
                	  xmlResult.append("<Description>");xmlResult.append(resultComponent.getContext());xmlResult.append("</Description>");
                	  xmlResult.append("<Url>");xmlResult.append(resultComponent.getUrl());xmlResult.append("</Url>");
                	  xmlResult.append("<CacheSize>");xmlResult.append(resultComponent.getBytes());xmlResult.append("</CacheSize>");
                	  xmlResult.append("</Result>");
                	                  	  
              		  xmlResult.flush();
              		  
                	  ResultSet = ResultSet.concat(xmlResult.toString()); 
                  }            	            	            		            	            		            		            		            			
            	}                   
      		  	catch(JAXBException je)
      		  	{
      				logger.error("JAXBException occured!",je);			
      				throw new DREIllegalArgumentException(DREIllegalArgumentException.CONFIGURATION_FILE_PARSE_ERROR,null);						
      		  	}		  	
      		  	ResultSetMap.put(QueryList.get(a).toString(), ResultSet);            	             	            	
            }
            return ResultSetMap;
		}
		catch(java.rmi.RemoteException e)
		{
			logger.error("Alexa web search service invocation failed!",e);
			throw new DREIllegalArgumentException(DREIllegalArgumentException.ILLEGAL_ARGUMENT,"Search failed. Please check AppID, options and the internet connection!",null);			
		} 
		catch(XMLStreamException e)
		{
			logger.error("XMLStreamException occured while accessing Alexa web search results!",e);
			throw new DRERuntimeException(DRERuntimeException.XML_FAILIURE,"Failed during the construction of result stream!",null);
		}	
		catch(Exception e)
		{
			logger.error("Exception occured while accessing Alexasearch results!",e);
			throw new DRERuntimeException(DRERuntimeException.XML_FAILIURE,"Failed during the construction of response stream!",null);
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
		
		//Check to see if the mandatory APP ID option is specified, if not default to the DRE APPID
		if(options.get("accesskey") == null)
		{
			logger.warn("Amazon Webservices Accesskey was not specified, using defaults");
			options.put("accesskey", "1F6FFJEG9NCDGGS3TYR2");
		}

		//Check to see if the mandatory secret search key option is specified, if not default
		if(options.get("secretkey") == null)
		{
			logger.info("Alexa secret key was set to defaults");				
			options.put("secretkey", "q/HvklQvd8JTHF03+ZRXsxab8Sk/loAKAEPGnTdB");
		}
		
		//Check to see if the mandatory ID option is specified, if not default to the provider ID
		if(options.get("ID") == null)
		{
			logger.warn("ID defaults to NYSE Symbol(AMZN)");
			options.put("ID", "Alexa");
		}
		
		return true;
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
