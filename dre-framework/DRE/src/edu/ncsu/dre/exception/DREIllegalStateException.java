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
 * File: edu.ncsu.dre.exception.DREIllegalStateException.java
 * Created by: santthosh
 * TimeStamp: Jul 22, 2007 + 9:03:11 PM
 */
package edu.ncsu.dre.exception;

/**
 * Signals that a method has been invoked at an illegal or inappropriate time. In other words, the
 * object on which the method was called is not in an appropriate state for the requested operation.
 * This extends <code>RuntimeException</code> and so does not need to be declared in the throws
 * clause of methods.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREIllegalStateException extends DRERuntimeException {

	private static final long serialVersionUID = -8081807814100358556L;

	/**
	 * Message key for a standard DRE exception message: "The DRE framework
	 * implementation (class {0}) could not be created."
	 */
	public static final String COULD_NOT_CREATE_FRAMEWORK = "could_not_create_framework";

	/**
	 * Message key for a standard DRE exception message: "The method {0} must
	 * be called before the method {1}."
	 */
	public static final String REQUIRED_METHOD_CALL = "required_method_call";

	/**
	 * Message key for a standard DRE exception message: Invalid framework configuration
	 * failed to construct research engine.
	 */
	public static final String INVALID_CONFIGURATION = "invalid_configuration";

	/**
	 * Creates a new exception with a null message.
	 */
	public DREIllegalStateException() {
		super();
	}

	/**
	 * Creates a new exception with the specified cause and a null message.
	 * 
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public DREIllegalStateException(Throwable aCause) {
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
	public DREIllegalStateException(String aResourceBundleName,
			String aMessageKey, Object[] aArguments) {
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
	public DREIllegalStateException(String aResourceBundleName,
			String aMessageKey, Object[] aArguments, Throwable aCause) {
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
	public DREIllegalStateException(String aMessageKey, Object[] aArguments) {
		super(aMessageKey, aArguments);
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
	public DREIllegalStateException(String aMessageKey, Object[] aArguments,
			Throwable aCause) {
		super(aMessageKey, aArguments, aCause);
	}	
}
