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
 * File: edu.ncsu.dre.artifact.Artifact.java
 * Created by: Santthosh
 * TimeStamp: Jul 24, 2007 + 2:00:16 AM
 */
package edu.ncsu.dre.data;

/**
 * <code>Artifact</code> is the general term for all DRE inputs that can be acquired and used
 * by an application (or by other components of DRE).
 * <p>
 * Most applications will not need to deal with this abstract <code>Artifact</code> interface.
 * DRE Developers who need to introduce new types of Artifacts, however, will need to implement
 * this interface.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public interface Artifact extends java.io.Serializable{
	
	/**
	 * Generalized method for returning the query object. This normally used by
	 * a Segregator where it is divided and passed onto scheduler for further 
	 * research.
	 * 
	 * @return java.lang.Object
	 */
	public Object getQuery();
	
	/**
	 * Sets the generalized artifact to the artifact provided 
	 *  
	 * @param set the given object as the general artifact  
	 */
	public void setArtifact(Object artifact);
}
