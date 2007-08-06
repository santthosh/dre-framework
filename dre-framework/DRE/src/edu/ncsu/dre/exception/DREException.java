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
 * File: edu.ncsu.dre.exception.DREException.java
 * Created by: santthosh
 * TimeStamp: Jul 23, 2007 + 12:32:06 AM
 */
package edu.ncsu.dre.exception;

/**
 * This is the superclass for all exceptions in DRE.
 * <p>
 * <code>DREException</code> extends {@link InternationalizedException} for internationalization
 * support. Since DRE Exceptions are internationalized, the thrower does not supply a hard coded
 * message. Instead, the thrower specifies a key that identifies the message. That key is then
 * looked up in a locale-specific {@link java.util.ResourceBundle ResourceBundle} to find the actual
 * message associated with this exception.
 * <p>
 * The thrower may specify the name of the <code>ResourceBundle</code> in which to find the
 * exception message. Any name may be used. If the name is omitted, the resource bundle identified
 * by {@link #STANDARD_MESSAGE_CATALOG} will be used. This contains the standard DRE exception
 * messages.
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
public class DREException extends InternationalizedException {
	
	private static final long serialVersionUID = 7521732353239537026L;

	/**
	 * The name of the {@link java.util.ResourceBundle ResourceBundle} containing the standard DRE
	 * Exception messages.
	 */
	public static final String STANDARD_MESSAGE_CATALOG = "edu.ncsu.dre.DREException_Messages";

	/**
	 * Creates a new exception with a null message.
	 */
	public DREException() {
		super();
	}

	/**
	 * Creates a new exception with the specified cause and a null message.
	 * 
	 * @param aCause
	 *          the original exception that caused this exception to be thrown, if any
	 */
	public DREException(Throwable aCause) {
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
	public DREException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments) {
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
	public DREException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments, Throwable aCause) {
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
	public DREException(String aMessageKey, Object[] aArguments) {
		super(STANDARD_MESSAGE_CATALOG, aMessageKey, aArguments);
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
	public DREException(String aMessageKey, Object[] aArguments,
			Throwable aCause) {
		super(STANDARD_MESSAGE_CATALOG, aMessageKey, aArguments, aCause);
	}
}
