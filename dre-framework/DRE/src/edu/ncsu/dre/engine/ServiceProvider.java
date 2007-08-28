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
 * File: edu.ncsu.dre.engine.ServiceProvider.java
 * Created by: Santthosh
 * TimeStamp: Jul 24, 2007 + 1:36:03 AM
 */
package edu.ncsu.dre.engine;

import java.util.Collection;
import java.util.Map;

/**
 * <code>ServiceProvider</code> is the general term for all DRE components that can be used to 
 * collect information on a subset of the artifact. The subset of artifacts is provided to this   
 * abstract class and its derivatives by a {@link edu.ncsu.dre.engine.ResearchScheduler}.
 * <p>
 * Most applications will not need to deal with this abstract <code>ServiceProvider</code> class.
 * DRE Developers who need to introduce new types of ServiceProviders however, will need to extend
 * this class.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public abstract class ServiceProvider extends Thread implements java.io.Serializable
{	
	/**
	 * @return the artifactSubset
	 */
	public abstract Collection<Object> getArtifactSubset();

	/**
	 * @param artifactSubset the artifactSubset to set
	 */
	public abstract void setArtifactSubset(Collection<Object> artifactSubset);

	/**
	 * @return the options
	 */
	public abstract Map<String, String> getOptions();

	/**
	 * @param options the options to set
	 */
	public abstract void setOptions(Map<String, String> options);

	/**
	 * @return the resultSet
	 */
	public abstract Map<Object, Object> getResultSet();

	/**
	 * @param resultSet the resultSet to set
	 */
	public abstract void setResultSet(Map<Object, Object> resultSet);
}
