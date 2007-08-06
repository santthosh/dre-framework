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
 * File: edu.ncsu.dre.exception.DRERuntimeException.java
 * Created by: santthosh
 * TimeStamp: Jul 22, 2007 + 8:44:54 PM
 */
package edu.ncsu.dre.exception;

/**
 * This is the superclass for all runtime exceptions in DRE. Runtime exceptions do not need to be
 * declared in the throws clause of methods.
 * <p>
 * <code>DRERuntimeException</code> extends {@link InternationalizedRuntimeException} for
 * internationalization support. Since DRE Runtime Exceptions are internationalized, the thrower
 * does not supply a hardcoded message. Instead, the thrower specifies a key that identifies the
 * message. That key is then looked up in a locale-specific
 * {@link java.util.ResourceBundle ResourceBundle} to find the actual message associated with this
 * exception.
 * <p>
 * The thrower may specify the name of the <code>ResourceBundle</code> in which to find the
 * exception message. Any name may be used. If the name is omitted, the resource bundle identified
 * by {@link #STANDARD_MESSAGE_CATALOG} will be used. This contains the standard DRE exception
 * messages.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */


public class DRERuntimeException extends InternationalizedRuntimeException 
{
	private static final long serialVersionUID = 6738051692628592989L;

	/**
	 * The name of the {@link java.util.ResourceBundle ResourceBundle}
	 * containing the standard DRE Exception messages.
	 */
	public static final String STANDARD_MESSAGE_CATALOG = "edu.ncsu.dre.exception.DREException_Messages";
	
	/**
	 * Message key for a standard DRE exception message: "Failed to extract contents from file. IOException
	 * has occured at {0}."
	 */
	public static final String FAILED_CONTENT_EXTRACTION = "failed_content_extraction";
	
	/**
	 * Message key for a standard DRE exception message: "Could not extract information from the configuration
	 * file."
	 */
	public static final String CONFIGURATION_FILE_ERROR = "configuration_file_error";

	/**
	 * Creates a new exception with a null message.
	 */
	public DRERuntimeException() {
		super();
	}

	/**
	 * Creates a new exception with the specified cause and a null message.
	 * 
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public DRERuntimeException(Throwable aCause) {
		super(aCause);
	}

	/**
	 * Creates a new exception with a the specified message.
	 * 
	 * @param aResourceBundleName
	 *            the base name of the resource bundle in which the message for
	 *            this exception is located.
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception. The
	 *            message may contain placeholders for arguments as defined by
	 *            the {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be used
	 *            if the message has no arguments.
	 */
	public DRERuntimeException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments) {
		super(aResourceBundleName, aMessageKey, aArguments);
	}

	/**
	 * Creates a new exception with the specified message and cause.
	 * 
	 * @param aResourceBundleName
	 *            the base name of the resource bundle in which the message for
	 *            this exception is located.
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception. The
	 *            message may contain placeholders for arguments as defined by
	 *            the {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be used
	 *            if the message has no arguments.
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public DRERuntimeException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments, Throwable aCause) {
		super(aResourceBundleName, aMessageKey, aArguments, aCause);
	}

	/**
	 * Creates a new exception with a message from the
	 * {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception. The
	 *            message may contain placeholders for arguments as defined by
	 *            the {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be used
	 *            if the message has no arguments.
	 */
	public DRERuntimeException(String aMessageKey, Object[] aArguments) {
		super(STANDARD_MESSAGE_CATALOG, aMessageKey, aArguments);
	}

	/**
	 * Creates a new exception with the specified cause and a message from the
	 * {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception. The
	 *            message may contain placeholders for arguments as defined by
	 *            the {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be used
	 *            if the message has no arguments.
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public DRERuntimeException(String aMessageKey, Object[] aArguments,
			Throwable aCause) {
		super(STANDARD_MESSAGE_CATALOG, aMessageKey, aArguments, aCause);
	}
}
