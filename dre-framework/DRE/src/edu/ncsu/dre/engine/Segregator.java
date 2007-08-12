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
 * File: edu.ncsu.dre.engine.Segregator.java
 * Created by: Santthosh
 * TimeStamp: Jul 24, 2007 + 1:34:30 AM
 */
package edu.ncsu.dre.engine;

/**
 * <code>Segregator</code> is the general term for all DRE components that can be used to 
 * divide an {@link edu.ncsu.dre.data.Artifact} into a manageable/requisite pieces of information 
 * which can be provided to the {@link edu.ncsu.dre.engine.ResearchScheduler} for further research.
 * <p>
 * Most applications will not need to deal with this abstract <code>Segregator</code> interface.
 * DRE Developers who need to introduce new types of Segregator however, will need to implement
 * this interface.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public interface Segregator extends java.io.Serializable{
	
	/**
	 * Generalized method for processing query segregation on the given artifact.
	 * 
	 * @param artifact
	 * @return java.util.Collection<Object>
	 */
	public java.util.Collection<Object> segregateArtifact(Object artifact);
}
