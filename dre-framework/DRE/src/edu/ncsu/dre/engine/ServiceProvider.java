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


import edu.ncsu.dre.configuration.*;
/**
 * <code>ServiceProvider</code> is the general term for all DRE components that can be used to 
 * collect information on a subset of the artifact. The subset of artifacts is provided to this   
 * interface and its derivatives by a {@link edu.ncsu.dre.ResearchScheduler}.
 * <p>
 * Most applications will not need to deal with this abstract <code>ServiceProvider</code> interface.
 * DRE Developers who need to introduce new types of ServiceProviders however, will need to implement
 * this interface.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public interface ServiceProvider extends java.io.Serializable
{
	/**
	 * Generalized method for gathering information on the given artifact subset.
	 * 
	 * @param java.util.Collection<Object> artifactSubset, 
	 * 		  java.util.Map<String,String> options
	 * 
	 * @return java.util.Map<Object,Object>
	 */
	public java.util.Map<Object,Object> gatherInformation(java.util.Collection<Object> artifactSubset,
			java.util.Map<String,String> options);
	
	/**
	 * Generalized method for validating information on the given artifact subset.
	 * 
	 * @param java.util.Collection<Object> artifactSubset, 
	 * 		  java.util.Map<String,String> options
	 * 
	 * @return java.util.Map<Object,Object>
	 */
	public boolean validateArguments(java.util.Collection<Object> artifactSubset,
			java.util.Map<String,String> options);
}
