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
 * File: edu.ncsu.dre.engine.ResearchScheduler.java
 * Created by: Santthosh
 * TimeStamp: Jul 24, 2007 + 1:35:32 AM
 */
package edu.ncsu.dre.engine;

import edu.ncsu.dre.configuration.*;

/**
 * <code>ResearchScheduler</code> is the general term for all DRE components that can be used to 
 * schedule information collection over all the artifact subsets. The subsets of artifacts are 
 * provided to this interface and its derivatives by a {@link edu.ncsu.dre.Segregator}.
 * <p>
 * Most applications will not need to deal with this abstract <code>ResearchScheduler</code> interface.
 * DRE Developers who need to introduce new types of Schedulers however, will need to implement
 * this interface.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public interface ResearchScheduler extends java.io.Serializable{
	
	/**
	 * Generalized method for scheduling research on the given artifact.
	 * 
	 * @param java.lang.Object
	 * @return java.lang.Collection<Object>
	 */
	public java.util.Map<Object,Object> scheduleResearch(java.util.Collection<Object> artifact, java.util.List<Component> searchProviders);
}
