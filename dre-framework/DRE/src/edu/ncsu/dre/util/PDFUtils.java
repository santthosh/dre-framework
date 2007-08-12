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
 * File: edu.ncsu.dre.util.PDFUtils.java
 * Created by: Santthosh
 * TimeStamp: Jul 25, 2007 4:10:42 PM
 */
package edu.ncsu.dre.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import edu.ncsu.dre.exception.DRERuntimeException;

/**
 * Some utilities for handling PDF files.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class PDFUtils {
	
	private static Logger logger = Logger.getLogger("edu.ncsu.dre.util.PDFUtils");
	
	/**
	 * Read the contents of a PDF file into a string.
	 * 
	 * @param inputFile
	 *          
	 * @return String
	 * @throws IOException
	 *           Various I/O errors.
	 */
	public static String pdf2String(java.io.File inputFile) throws java.io.IOException {
		
		logger.trace("pdf2String(java.io.File inputFile)");
		
		PDDocument pdfDocument = null;
		try {
			pdfDocument = PDDocument.load(inputFile);

			if (pdfDocument.isEncrypted()) {
				try {
					pdfDocument.decrypt("");
				} catch (org.pdfbox.exceptions.InvalidPasswordException ipe) {
					logger.error("Decryption of PDF document failed!",ipe);
					throw new DRERuntimeException(
							DRERuntimeException.FAILED_CONTENT_EXTRACTION,
							"The PDF document is encrypted.",
							new Object[] {"edu.ncsu.dre.util.PDFUtils"});
				} catch (org.pdfbox.exceptions.CryptographyException ce) {
					logger.error("Cryptanalysis failed on the PDF document!",ce);
					throw new DRERuntimeException(							
							DRERuntimeException.FAILED_CONTENT_EXTRACTION,
							"The PDF document is encrypted, unknown cipher text.",
							new Object[] {"edu.ncsu.dre.util.PDFUtils"});
				}
			}
			PDFTextStripper pdfExtractor = new PDFTextStripper();
			return pdfExtractor.getText(pdfDocument);
		} finally {
			if (pdfDocument != null)
				pdfDocument.close();
		}
	}
}
