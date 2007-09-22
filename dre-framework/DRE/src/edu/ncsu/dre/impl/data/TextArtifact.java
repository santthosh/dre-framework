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
 * File: edu.ncsu.dre.impl.TextArtifact.java
 * Created by: Santthosh
 * TimeStamp: Jul 25, 2007 12:32:57 PM
 */
package edu.ncsu.dre.impl.data;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import edu.ncsu.dre.util.*;
import edu.ncsu.dre.data.Artifact;
import edu.ncsu.dre.exception.*;

/**
 * This implementation of artifact handles all text based research queries. The 
 * class has been properly overloaded to accept text input as String or Files.
 * 
 * Files can be of MIME type text/* or PDF. Microsoft File Formats are not 
 * supported yet.
 * 
 * DRE Developers can extend this to add support for new file formats and other
 * types of text inputs. 
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class TextArtifact implements Artifact {
	
	static Logger logger = Logger.getLogger("edu.ncsu.dre.impl.data.TextArtifact");

	private static final long serialVersionUID = -481270147011237L;
	
	private String 	   method = null;
	/**
	 * Generalized artifact object, needs to be type casted as per the requirements of
	 * Segregator
	 */
	public Object artifact = null;
	
	/* (non-Javadoc)
	 * @see edu.ncsu.dre.Artifact#getQuery()
	 */
	@Override
	public Object getQuery() {	
		logger.trace("getQuery()");		
		
		if (this.artifact.getClass().getName().compareToIgnoreCase("java.io.File")==0)
		{				
			TextArtifact childArtifact = new TextArtifact((java.io.File)this.artifact);
			return childArtifact.getQuery();
		}			

		return this.artifact;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.dre.Artifact#setArtifact(java.lang.Object)
	 */
	@Override
	public void setArtifact(Object inputArtifact) {
		logger.trace("setArtifact(Object inputArtifact)");
		this.artifact = inputArtifact;		
	}
	
	
	/**
	 * Constructor overload, accepts a String and uses the <code>setArtifact</code>
	 * method to generalize the object
	 * 
	 */
	public TextArtifact()
	{
		logger.trace("TextArtifact Constructor");		
	}
	
	/**
	 * Constructor overload, accepts a String and uses the <code>setArtifact</code>
	 * method to generalize the object
	 * 
	 * @param inputArtifact
	 */
	public TextArtifact(String inputArtifact)
	{
		logger.trace("TextArtifact(String inputArtifact)");
		setArtifact((Object)inputArtifact);
	}
	
	/**
	 * Constructor overload, accepts a File and uses the file utilities from
	 * {@link edu.ncsu.dre.util} to extract the entire string from the file. 
	 * 
	 * The function can process text files, unencrypted PDF files and encrypted PDF files with 
	 * out any passwords attached to them. 
	 * 
	 * @param inputArtifact
	 * 
	 * @throws DREIllegalArgumentException
	 * 
	 * @throws DRERuntimeException
	 */	
	public TextArtifact(java.io.File inputArtifact) throws DREIllegalArgumentException,DRERuntimeException
	{	
		logger.trace("TextArtifact(java.io.File inputArtifact)");
		
		if (!inputArtifact.exists() || !inputArtifact.isFile())
			throw new DREIllegalArgumentException(DREIllegalArgumentException.FILE_SYSTEM_OBJECT_ERROR,
					new Object[] { inputArtifact.getName(),this.getClass().getName() });

		java.net.FileNameMap fileNameMap = java.net.URLConnection.getFileNameMap();
		String mimeType = fileNameMap.getContentTypeFor(inputArtifact.getAbsolutePath()+ "." + inputArtifact.getName());		

		boolean isTextFile = mimeType.toLowerCase().indexOf("text".toLowerCase()) == 0 ? true : false;
		boolean isPDFFile = mimeType.toLowerCase().compareToIgnoreCase("application/pdf") == 0 ? true : false;

		if (!isTextFile && !isPDFFile)
			throw new DREIllegalArgumentException(DREIllegalArgumentException.UNSUPPORTED_MIME, new Object[] {
							"text and PDF", this.getClass().getName() });
		try 
		{
			if(isTextFile)
				setArtifact((Object) FileUtils.file2String(inputArtifact));
			
			if(isPDFFile)
				setArtifact((Object) PDFUtils.pdf2String(inputArtifact));			
		} 
		catch (java.io.IOException ioe) 
		{			
			logger.error("IOException while parsing text artifact.",ioe);
			throw new DRERuntimeException(DRERuntimeException.FAILED_CONTENT_EXTRACTION, ioe.getMessage(), 
					new Object[] { this.getClass().getName() });
		}	
	}	
}
