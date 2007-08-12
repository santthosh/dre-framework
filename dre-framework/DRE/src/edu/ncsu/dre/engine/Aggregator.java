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
 * File: edu.ncsu.dre.engine.Aggregator.java
 * Created by: Santthosh
 * TimeStamp: Jul 24, 2007 + 1:35:02 AM
 */
package edu.ncsu.dre.engine;

/**
 * <code>Aggregator</code> is the general term for all DRE components that can be used to 
 * collate the results of an {@link edu.ncsu.dre.data.Artifact} so that it can be displayed 
 * onto a formatted HTML or GUI. The input is provided by the {@link edu.ncsu.dre.engine.ResearchScheduler}.
 * <p>
 * Most applications will not need to deal with this abstract <code>Aggregator</code> interface.
 * DRE Developers who need to introduce new types of Aggregator however, will need to implement
 * this interface.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public interface Aggregator {
	
	/**
	 * Generalized method for processing result aggregation on the given artifact search results.
	 * 
	 * @param artifact
	 * @return java.lang.Collection<Object>
	 */
	public java.util.Collection<Object> aggregateResults(Object artifact);
}
