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
 * File: edu.ncsu.dre.DREConfiguration.java
 * Created by: selvas
 * TimeStamp: Jul 24, 2007 10:49:00 AM
 */
package edu.ncsu.dre;

import java.util.*;

import org.apache.log4j.Logger;

import edu.ncsu.dre.engine.*;
import edu.ncsu.dre.configuration.*;


/**
 * This class specifies the configuration of the Distributed Research Engine and 
 * its components.<br>
 * 
 * In order to use a framework instance one has use the <code>setDREConfiguration()</code> 
 * method to set the components. If attempted to use DREFramework without specifying a 
 * valid configuration the DREIllegalStateException will be thrown.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREConfiguration {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.DREConfiguration");
	/**
	 * General Segregator object that is used inside the DRE framework 
	 */
	private Segregator segregator;
	
	/**
	 * Optional parameters that can be passed to the Segregator engine
	 */
	private Map<Object,Object> segregatorOptions;
	
	/**
	 * General Aggregator object that is used inside the DRE framework 
	 */
	private Aggregator aggregator;
	
	/**
	 * Optional parameters that can be passed to the Aggregator engine
	 */
	private Map<Object,Object> aggregatorOptions;
	
	/**
	 * General ResearchScheduler object that is used inside the DRE framework 
	 */
	private ResearchScheduler scheduler;
	
	/**
	 * Optional parameters that can be passed to the Scheduling mechanism
	 */
	private Map<Object,Object> schedulerOptions;
	
	/**
	 * List of service providers that provide data search service
	 */
	private List<Component> ServiceProvider;
	
	/**
	 * Optional parameters that can be passed to this framework configuration as
	 * a whole
	 */
	private Map<Object,Object> parameters;
	
	/**
	 * Gets the Segregator associated with this configuration.
	 * 
	 * @return the segregator
	 */
	public Segregator getSegregator() {
		logger.trace("getSegregator()");
		return segregator;
	}
	/**
	 * Sets this configuration with the given Segregator. Developers can use the default
	 * {@link edu.ncsu.dre.impl.engine.LexicalSegregator} for text searches, extend it or create
	 * their own Segregator by implementing {@link edu.ncsu.dre.engine.Segregator} interface.
	 * 
	 * @param segregator the segregator to set
	 */
	public void setSegregator(Segregator segregator) {
		logger.trace("setSegregator(Segregator segregator)");
		this.segregator = segregator;
	}
	
	/**
	 * Gets the Segregator options associated with this configuration.
	 * 
	 * @return the segregatorOptions
	 */
	public Map<Object,Object> getSegregatorOptions() {
		logger.trace("getSegregatorOptions()");
		return segregatorOptions;
	}
	/**
	 * Sets the Segregator options that will be consumed by the Segregator object
	 * 
	 * @param segregatorOptions the segregatorOptions to set
	 */
	public void setSegregatorOptions(Map<Object,Object> segregatorOptions) {
		logger.trace("setSegregatorOptions(Map<Object,Object> segregatorOptions)");
		this.segregatorOptions = segregatorOptions;
	}	
	
	/**
	 * Gets the Aggregator associated with this configuration. 
	 * 
	 * @return the aggregator
	 */
	public Aggregator getAggregator() {
		logger.trace("getAggregator()");
		return aggregator;
	}
	/**
	 * Sets this configuration with the given Aggregator. Developers can use the default
	 * {@link edu.ncsu.dre.impl.engine.XMLAggregator}, extend it or create their own
	 * Aggregator by implementing {@link edu.ncsu.dre.engine.Aggregator} interface.
	 * 
	 * @param aggregator the aggregator to set
	 */
	public void setAggregator(Aggregator aggregator) {
		logger.trace("setAggregator(Aggregator aggregator)");
		this.aggregator = aggregator;
	}
	/**
	 * Gets the Aggregator options associated with this configuration.
	 * 
	 * @return the aggregatorOptions
	 */
	public Map<Object,Object> getAggregatorOptions() {
		logger.trace("getAggregatorOptions()");
		return aggregatorOptions;
	}
	/**
	 * Sets the Aggregator options that will be consumed by the Aggregator object
	 * 
	 * @param aggregatorOptions the aggregatorOptions to set
	 */
	public void setAggregatorOptions(Map<Object,Object> aggregatorOptions) {
		logger.trace("setAggregatorOptions(Map<Object,Object> aggregatorOptions)");
		this.aggregatorOptions = aggregatorOptions;
	}
	
	/**
	 * Gets the ResearchScheduler associated with this configuration
	 * 
	 * @return the scheduler
	 */
	public ResearchScheduler getScheduler() {
		logger.trace("getScheduler()");
		return scheduler;
	}
	/**
	 * Sets this configuration with the given ResearchScheduler. Developers can use the
	 * default {@link edu.ncsu.dre.impl.engine.StandAloneScheduler}, extend it or create
	 * their own ResearchScheduler by implementing {@link edu.ncsu.dre.engine.ResearchScheduler} 
	 * 
	 * @param scheduler the scheduler to set
	 */
	public void setScheduler(ResearchScheduler scheduler) {
		logger.trace("setScheduler(ResearchScheduler scheduler)");
		this.scheduler = scheduler;
	}	
	/**
	 * Gets the Scheduler options associated with this configuration.
	 * 
	 * @return the schedulerOptions
	 */
	public Map<Object,Object> getSchedulerOptions() {
		logger.trace("getSchedulerOptions()");
		return schedulerOptions;
	}
	/**
	 * Sets the Scheduler options that will be consumed by the Scheduler object
	 * 
	 * @param schedulerOptions the schedulerOptions to set
	 */
	public void setSchedulerOptions(Map<Object,Object> schedulerOptions) {
		logger.trace("setSchedulerOptions(Map<Object,Object> schedulerOptions)");
		this.schedulerOptions = schedulerOptions;
	}
	/**
	 * Gets the optional parameters associated with this framework configuration.
	 * 
	 * @return the parameters
	 */
	public Map<Object,Object> getParameters() {
		logger.trace("getParameters()");
		return parameters;
	}
	/**
	 * Sets the optional parameters that will be consumed by the framework configuration.
	 * 
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<Object,Object> parameters) {
		logger.trace("setParameters(Map<Object,Object> parameters)");
		this.parameters = parameters;
	}
	/**
	 * Gets the list of service providers configured for this research engine
	 * 
	 * @return the serviceProvider
	 */
	public List<Component> getServiceProvider() {
		logger.trace("getServiceProvider()");
		return ServiceProvider;
	}
	/**
	 * Sets the list of service providers that will perform the distributed search
	 * across a variety of data repositories
	 * 
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(List<Component> serviceProvider) {
		logger.trace("setServiceProvider(List<Component> serviceProvider)");
		ServiceProvider = serviceProvider;
	}
}
