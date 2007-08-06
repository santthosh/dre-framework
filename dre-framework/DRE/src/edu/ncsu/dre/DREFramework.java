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
 * File: edu.ncsu.ece.dref.DREFramework.java
 * Created by: santthosh
 * TimeStamp: Jul 22, 2007 + 3:52:00 PM
 */
package edu.ncsu.dre;

import javax.xml.bind.*;

import edu.ncsu.dre.data.Artifact;
import edu.ncsu.dre.exception.*;
import edu.ncsu.dre.engine.*;
import edu.ncsu.dre.configuration.*;

/**
 * This is the application's main interface point to the DRE Framework. 
 * <p>
 * This class provides the basic methods and constructors for building the 
 * application.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */

public class DREFramework {
		
	/**
	 * This is the framework configuration associated with the singleton instance;
	 */
	private static DREConfiguration configuration; 	
	
	/**
	 * Gets the framework implementation's version number as a string. This will be the major version
	 * number, minor version number, and build revision in that order, separated by dots.
	 * 
	 * @return the version.
	 */
	public String getVersionString() {
		return "" + getMajorVersion() + "." + getMinorVersion() + "." + getBuildRevision();
	}

	/**
	 * Gets the major version number of the framework implementation.
	 * 
	 * @return the major version number
	 */
	public short getMajorVersion() {
		return 0;
	}

	/**
	 * Gets the minor version number of the framework implementation.
	 *   	
     * @return the minor version number
	 */
	public short getMinorVersion() {
		return 1;
	}

	/**
	 * Gets the build revision number of the framework implementation.
	 * 
	 * @return the build revision number
	 */
	public short getBuildRevision() {
		return 1;
	}
	
	/**
	 * Gets the <code>DREConfiguration</code> currently in use.
	 * 
	 * @return the configuration
	 */
	public DREConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the <code>DREConfiguration</code> to the given configuration
	 * 
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(DREConfiguration configuration) {
		DREFramework.configuration = configuration;
		
		if(!hasValidConfiguration())
			throw new DREIllegalStateException(DREIllegalStateException.INVALID_CONFIGURATION,null);
	}
	
	/**
	 * This sets the current instance's configuration to the one loaded from the
	 * XML configuration file. The mapping is done through JAXB 
	 * 
	 * @param File from which configuration can be loaded, typically an XML file. 
	 */
	public void setConfiguration(java.io.File configurationFile)
	{		
		try
		{
			DREConfiguration dreConfiguration = new DREConfiguration();
			
			JAXBContext binderContext = JAXBContext.newInstance("edu.ncsu.dre.configuration");
			Unmarshaller unmarshaller = binderContext.createUnmarshaller();
			
			EngineConfiguration  configuration = (EngineConfiguration) unmarshaller.unmarshal(configurationFile);
						
			//Identify and populate the segregator class used for the configuration
			Class segregatorClass = Class.forName(configuration.getSegregator().getHandler());
			Segregator segregator = (Segregator) segregatorClass.newInstance();			
			dreConfiguration.setSegregator(segregator);
									
			//Identify and populate the aggregator class used for the configuration
			Class aggregatorClass = Class.forName(configuration.getAggregator().getHandler());
			Aggregator aggregator = (Aggregator) aggregatorClass.newInstance();
			dreConfiguration.setAggregator(aggregator);
									
			//Identify and populate the scheduler used for the configuration
			Class researchSchedulerClass = Class.forName(configuration.getResearchScheduler().getHandler());
			ResearchScheduler researchScheduler = (ResearchScheduler) researchSchedulerClass.newInstance();			
			dreConfiguration.setScheduler(researchScheduler);

			//Identify and populate any optional parameters passed			
			dreConfiguration.setSegregatorOptions(list2Map(configuration.getSegregator().getOption()));			
			dreConfiguration.setSchedulerOptions(list2Map(configuration.getResearchScheduler().getOption()));
			dreConfiguration.setAggregatorOptions(list2Map(configuration.getAggregator().getOption()));
			dreConfiguration.setParameters(list2Map(configuration.getParameter()));
			
			//Identify and populate the ServiceProvider list
			dreConfiguration.setServiceProvider(configuration.getServiceProvider());
			
			DREFramework.configuration = dreConfiguration;	
			
			if(!hasValidConfiguration())
				throw new DREIllegalStateException(DREIllegalStateException.INVALID_CONFIGURATION,null);
		}
		catch(JAXBException je)
		{
			je.printStackTrace();
			throw new DREIllegalArgumentException(DREIllegalArgumentException.CONFIGURATION_FILE_PARSE_ERROR,null);						
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new DRERuntimeException(DRERuntimeException.CONFIGURATION_FILE_ERROR, null);
		}
	}
	
	/**
	 * Function to convert the optional parameter list to a Map for each individual component as well as 
	 * the framework as a whole read from an XML file. 
	 * 
	 * @param optionsList as java.util.List
	 * 
	 * @return parameters for that component as java.util.Map 
	 */
	protected java.util.Map<Object,Object> list2Map(java.util.List<Arguments> optionsList)
	{
		java.util.Map<Object,Object> componentOptions = new java.util.HashMap<Object,Object>();
		for(int i=0;i<optionsList.size();i++)
		{	
			Arguments option = (Arguments)optionsList.get(i);
			componentOptions.put(option.getKey(), option.getValue());
		}		
		
		return componentOptions;
	}
	
	/**
	 * Constructor for the DREFramework, users will have to explicitly use either of the 
	 * <code>setConfiguration</code> method to define the components of the framework. Else 
	 * a DREIllegalStateException will be thrown.
	 * 
	 * The <code>hasValidConfiguration()</code> method can be used to check if a valid configuration
	 * has been set for this instance. Extensions to this class can enforce a default configuration 
	 * if necessary.  
	 */	
	public DREFramework()
	{}
	
	/**
	 * Constructor for the DREFramework given a configuration information. The constructor
	 * automatically checks for the validity of the configuration information by calling the 
	 * <code>hasValidConfiguration()</code>
	 * 
	 *  @param configuration the configuration to set
	 */
	public DREFramework(DREConfiguration configuration) throws DREIllegalStateException
	{
		DREFramework.configuration = configuration;
		if(!hasValidConfiguration())
			throw new DREIllegalStateException(DREIllegalStateException.INVALID_CONFIGURATION,null);					
	}
	
	/**
	 * Constructor for the DREFramework given a XML configuration file. The constructor
	 * automatically checks for the validity of the configuration information by calling the 
	 * <code>hasValidConfiguration()</code>
	 * 
	 *  @param configuration the configuration to set
	 */
	public DREFramework(java.io.File configurationFile) throws DREIllegalStateException
	{
		setConfiguration(configurationFile);
		if(!hasValidConfiguration())
			throw new DREIllegalStateException(DREIllegalStateException.INVALID_CONFIGURATION,null);
	}
	
	/**
	 * This method initiates the research operation. First the artifact is passed through the 
	 * Segregator component (as per configuration) where it is divided into sub queries. The 
	 * sub queries are given to the Scheduler component which then schedules the processing
	 * onto different service providers. Once all service providers have responded, the scheduler
	 * passes the results to Aggregator which collates the result set.
	 *   
	 * @param inputArtifact
	 */
	public void processArtifact(Artifact inputArtifact)
	{}//Todo list
	
	/**
	 * This method checks to see if the framework has been set with a valid configuration 
	 * 
	 * @return, true if this instance of framework has a valid Segregator, Aggregator and a
	 * scheduler
	 */
	public boolean hasValidConfiguration(){
		if(configuration != null &&
		   configuration.getAggregator()!= null &&
		   configuration.getSegregator()!= null &&
		   configuration.getScheduler()!= null)
			return true;
		return false;
	}
}
