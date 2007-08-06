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
 * File: edu.ncsu.dre.exception.DREUnsupportedOperationException.java
 * Created by: Santthosh
 * TimeStamp: Jul 23, 2007 + 10:19:06 PM
 */
package edu.ncsu.dre.exception;

/**
 * Thrown to indicate that the requested operation is not supported. This extends
 * <code>RuntimeException</code> and so does not need to be declared in the throws clause of
 * methods.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREUnsupportedOperationException extends DRERuntimeException {

	private static final long serialVersionUID = 9056907160021698405L;

	/**
	 * Message key for a standard DRE exception message: "Class {0} does not support method {1}."
	 */
	public static final String UNSUPPORTED_METHOD = "unsupported_method";

	/**
	 * Message key for a standard DRE exception message: "Attribute {0} of class {1} is not
	 * modifiable."
	 */
	public static final String NOT_MODIFIABLE = "not_modifiable";

	/**
	 * Message key for a standard DRE exception message: "This is a shared resource and cannot be
	 * reconfigured."
	 */
	public static final String SHARED_RESOURCE_NOT_RECONFIGURABLE = "shared_resource_not_reconfigurable";	

	/**
	 * Creates a new exception with a null message.
	 */
	public DREUnsupportedOperationException() {
		super();
	}

	/**
	 * Creates a new exception with the specified cause and a null message.
	 * 
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREUnsupportedOperationException(Throwable aCause) {
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
	 *          place holders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 */
	public DREUnsupportedOperationException(String aResourceBundleName,
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
	 *          place holders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREUnsupportedOperationException(String aResourceBundleName,
			String aMessageKey, Object[] aArguments, Throwable aCause) {
		super(aResourceBundleName, aMessageKey, aArguments, aCause);
	}

	/**
	 * Creates a new exception with a message from the {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          place holders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 */
	public DREUnsupportedOperationException(String aMessageKey,
			Object[] aArguments) {
		super(aMessageKey, aArguments);
	}

	/**
	 * Creates a new exception with the specified cause and a message from the
	 * {@link #STANDARD_MESSAGE_CATALOG}.
	 * 
	 * @param aMessageKey
	 *          an identifier that maps to the message for this exception. The message may contain
	 *          place holders for arguments as defined by the
	 *          {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *          The arguments to the message. <code>null</code> may be used if the message has no
	 *          arguments.
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREUnsupportedOperationException(String aMessageKey,
			Object[] aArguments, Throwable aCause) {
		super(aMessageKey, aArguments, aCause);
	}
}
