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
 * File: edu.ncsu.dre.impl.DefaultDREConfiguration.java
 * Created by: santthosh
 * TimeStamp: Jul 24, 2007 2:48:36 PM
 */
package edu.ncsu.dre.impl;

import edu.ncsu.dre.DREConfiguration;
import edu.ncsu.dre.impl.engine.*;

/**
 * This configuration is provide to default in case no configuration is loaded by the user. 
 * An instance of this class loads the following  
 * 	{@link edu.ncsu.dre.impl.engine.LexicalSegregator}
 *	{@link edu.ncsu.dre.impl.engine.StandAloneScheduler}
 *	{@link edu.ncsu.dre.impl.engine.XMLAggregator}
 * on to the current configuration.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public final class DefaultDREConfiguration extends DREConfiguration {

	/**
	 * Object for {@link edu.ncsu.dre.impl.engine.LexicalSegregator} 
	 */
	private LexicalSegregator lexicalSegregator;
	
	/**
	 * Object for {@link edu.ncsu.dre.impl.engine.StandAloneScheduler}
	 */
	private StandAloneScheduler simpleScheduler;
	
	/**
	 * Object for {@link edu.ncsu.dre.impl.engine.XMLAggregator}
	 */
	private XMLAggregator xmlAggregator;
	
	/**
	 * Constructor for the default DREConfiguration, which will be used 
	 * when no runtime configuration is specified.
	 * 
	 * Populates objects of {@link edu.ncsu.dre.impl.engine.LexicalSegregator},
	 * {@link edu.ncsu.dre.impl.engine.StandAloneScheduler} and 
	 * {@link edu.ncsu.dre.impl.engine.XMLAggregator} onto the configuration.
	 * 
	 * If you wish to override this, then you can use specify a XML configuration file
	 * and call the <code>setConfiguration(File)</code> in {@link edu.ncsu.dre.DREFramework}, or
	 * extend the DREConfiguration yourself to pool in a required set of components. 
	 */	
	public DefaultDREConfiguration()
	{
		lexicalSegregator = new LexicalSegregator();		
		this.setSegregator(lexicalSegregator);
		
		simpleScheduler = new StandAloneScheduler();
		this.setScheduler(simpleScheduler);
		
		xmlAggregator = new XMLAggregator();
		this.setAggregator(xmlAggregator);
	}
}
