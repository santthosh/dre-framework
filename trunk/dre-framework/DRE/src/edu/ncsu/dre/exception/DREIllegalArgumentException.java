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
 * File: edu.ncsu.dre.exception.DREIllegalArgumentException.java
 * Created by: Santthosh
 * TimeStamp: Jul 23, 2007 + 10:24:56 PM
 */
package edu.ncsu.dre.exception;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREIllegalArgumentException extends DRERuntimeException 
{
	private static final long serialVersionUID = -4820565402946868828L;

	/**
	 * Message key for a standard DRE exception message: "Value "{0}" is not valid for argument "{1}"
	 * of method {2}."
	 */
	public static final String ILLEGAL_ARGUMENT = "illegal_argument";
	
	/**
	 * Message key for a standard DRE exception message: "Given file system object {0} is not valid 
	 * for {1} or the object does not exist!.
	 */
	public static final String FILE_SYSTEM_OBJECT_ERROR = "file_system_object_error";

	/**
	 * Message key for a standard DRE exception message: "The value {0} does not match the data type
	 * of metadata attribute {1}."
	 */
	public static final String METADATA_ATTRIBUTE_TYPE_MISMATCH = "metadata_attribute_type_mismatch";

	/**
	 * Message key for a standard DRE exception message: "Unsupported MIME type. Only text MIME types
	 * are supported by {0}."
	 */
	public static final String UNSUPPORTED_MIME = "unsupported_mime";
	
	/**
	 * Message key for a standard DRE exception message: "XML Configuration file contains errors! 
	 * Could not infer configuration of research engine."
	 */
	public static final String CONFIGURATION_FILE_PARSE_ERROR = "configuration_file_parse_error";

	/**
	 * Creates a new exception with a null message.
	 */
	public DREIllegalArgumentException() {
		super();
	}

	/**
	 * Creates a new exception with the specified cause and a null message.
	 * 
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREIllegalArgumentException(Throwable aCause) {
		super(aCause);
	}

	/**
	 * Creates a new exception with a the specified message.
	 * 
	 * @param aResourceBundleName
	 *          the base name of the resource bundle in which the message for this exception is
	 *          located.
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          placeholders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 */
	public DREIllegalArgumentException(String aResourceBundleName,
			String aMessageKey, Object[] aArguments) {
		super(aResourceBundleName, aMessageKey, aArguments);
	}

	/**
	 * Creates a new exception with the specified message and cause.
	 * 
	 * @param aResourceBundleName
	 *          the base name of the resource bundle in which the message for this exception is
	 *          located.
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          placeholders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREIllegalArgumentException(String aResourceBundleName,
			String aMessageKey, Object[] aArguments, Throwable aCause) {
		super(aResourceBundleName, aMessageKey, aArguments, aCause);
	}

	/**
	 * Creates a new exception with a message from the {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          placeholders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 */
	public DREIllegalArgumentException(String aMessageKey, Object[] aArguments) {
		super(aMessageKey, aArguments);
	}

	/**
	 * Creates a new exception with the specified cause and a message from the
	 * {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          placeholders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREIllegalArgumentException(String aMessageKey,
			Object[] aArguments, Throwable aCause) {
		super(aMessageKey, aArguments, aCause);
	}
}
