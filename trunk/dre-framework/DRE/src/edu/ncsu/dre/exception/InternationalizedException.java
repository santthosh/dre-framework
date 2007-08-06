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
 * File: edu.ncsu.dre.exception.InternationalizedException.java
 * Created by: santthosh
 * TimeStamp: Jul 22, 2007 + 8:14:05 PM
 */
package edu.ncsu.dre.exception;

/**
 * 
 * @author <a href="mailto:sbselvad@ncsu.edu">Santthosh Babu Selvadurai</a>
 */
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The <code>InternationalizedException</code> class adds internationalization support to the
 * standard functionality provided by <code>java.lang.Exception</code>.
 * <p>
 * To support internationalization, the thrower of an exception must not specify a hardcoded message
 * string. Instead, the thrower specifies a key that identifies the message. That key is then looked
 * up in a locale-specific {@link java.util.ResourceBundle ResourceBundle} to find the actual
 * message associated with this exception.
 * <p>
 * This class also supports arguments to messages. The full message will be constructed using the
 * {@link java.text.MessageFormat MessageFormat} class. For more information on
 * internationalization, see the <a href="http://java.sun.com/javase/6/docs/technotes/guides/intl/overview.html">
 * Java Internationalization Guide</a>.
 * <p>
 * 
 */
public class InternationalizedException extends Exception 
{
	private static final long serialVersionUID = 2306587442280738385L;

	/**
	 * The base name of the resource bundle in which the message for this
	 * exception is located.
	 */
	private String mResourceBundleName;

	/**
	 * An identifier that maps to the message for this exception.
	 */
	private String mMessageKey;

	/**
	 * The arguments to this exception's message, if any. This allows an
	 * <code>InternationalizedException</code> to have a compound message,
	 * made up of multiple parts that are concatenated in a language-neutral
	 * way.
	 */
	private Object[] mArguments;

	/**
	 * The exception that caused this exception to occur.
	 */
	private Throwable mCause;

	/**
	 * Creates a new <code>InternationalizedException</code> with a null
	 * message.
	 */
	public InternationalizedException() {
		this(null, null, null, null);
	}

	/**
	 * Creates a new <code>InternationalizedException</code> with the
	 * specified cause and a null message.
	 * 
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public InternationalizedException(Throwable aCause) {
		this(null, null, null, aCause);
	}

	/**
	 * Creates a new <code>InternationalizedException</code> with the
	 * specified message.
	 * 
	 * @param aResourceBundleName
	 *            the base name of the resource bundle in which the message
	 *            for this exception is located.
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception.
	 *            The message may contain placeholders for arguments as
	 *            defined by the
	 *            {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be
	 *            used if the message has no arguments.
	 */
	public InternationalizedException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments) {
		this(aResourceBundleName, aMessageKey, aArguments, null);
	}

	/**
	 * Creates a new <code>InternationalizedException</code> with the
	 * specified message and cause.
	 * 
	 * @param aResourceBundleName
	 *            the base name of the resource bundle in which the message
	 *            for this exception is located.
	 * @param aMessageKey
	 *            an identifier that maps to the message for this exception.
	 *            The message may contain placeholders for arguments as
	 *            defined by the
	 *            {@link java.text.MessageFormat MessageFormat} class.
	 * @param aArguments
	 *            The arguments to the message. <code>null</code> may be
	 *            used if the message has no arguments.
	 * @param aCause
	 *            the original exception that caused this exception to be
	 *            thrown, if any
	 */
	public InternationalizedException(String aResourceBundleName, String aMessageKey,
			Object[] aArguments, Throwable aCause) {
		super();
		mCause = aCause;
		mResourceBundleName = aResourceBundleName;
		mMessageKey = aMessageKey;
		mArguments = aArguments;
		// if null message and mCause is Internationalized exception, "promote"
		// message
		if (mResourceBundleName == null && mMessageKey == null) {
			if (mCause instanceof InternationalizedException) {
				mResourceBundleName = ((InternationalizedException) mCause).getResourceBundleName();
				mMessageKey = ((InternationalizedException) mCause).getMessageKey();
				mArguments = ((InternationalizedException) mCause).getArguments();
			} else if (mCause instanceof InternationalizedRuntimeException) {
				mResourceBundleName = ((InternationalizedRuntimeException) mCause).getResourceBundleName();
				mMessageKey = ((InternationalizedRuntimeException) mCause).getMessageKey();
				mArguments = ((InternationalizedRuntimeException) mCause).getArguments();
			}
		}
	}

	/**
	 * Gets the base name of the resource bundle in which the message for
	 * this exception is located.
	 * 
	 * @return the resource bundle base name. May return <code>null</code>
	 *         if this exception has no message.
	 */
	public String getResourceBundleName() {
		return mResourceBundleName;
	}

	/**
	 * Gets the identifier for this exception's message. This identifier can
	 * be looked up in this exception's
	 * {@link java.util.ResourceBundle ResourceBundle} to get the
	 * locale-specific message for this exception.
	 * 
	 * @return the resource identifier for this exception's message. May
	 *         return <code>null</code> if this exception has no message.
	 */
	public String getMessageKey() {
		return mMessageKey;
	}

	/**
	 * Gets the arguments to this exception's message. Arguments allow a
	 * <code>InternationalizedException</code> to have a compound message,
	 * made up of multiple parts that are concatenated in a language-neutral
	 * way.
	 * 
	 * @return the arguments to this exception's message.
	 */
	public Object[] getArguments() {
		if (mArguments == null)
			return new Object[0];

		Object[] result = new Object[mArguments.length];
		System.arraycopy(mArguments, 0, result, 0, mArguments.length);
		return result;
	}

	/**
	 * Gets the <i>English</i> detail message for this exception. For the
	 * localized message use {@link #getLocalizedMessage()}.
	 * 
	 * @return the English detail message for this exception.
	 */
	public String getMessage() {
		return getLocalizedMessage(Locale.ENGLISH);
	}

	/**
	 * Gets the localized detail message for this exception. This uses the
	 * default Locale for this JVM. A Locale may be specified using
	 * {@link #getLocalizedMessage(Locale)}.
	 * 
	 * @return this exception's detail message, localized for the default
	 *         Locale.
	 */
	public String getLocalizedMessage() {
		return getLocalizedMessage(Locale.getDefault());
	}

	/**
	 * Gets the localized detail message for this exception using the
	 * specified <code>Locale</code>.
	 * 
	 * @param aLocale
	 *            the locale to use for localizing the message
	 * 
	 * @return this exception's detail message, localized for the specified
	 *         <code>Locale</code>.
	 */
	public String getLocalizedMessage(Locale aLocale) {
		// check for null message
		if (getMessageKey() == null)
			return null;

		try {
			// locate the resource bundle for this exception's messages
			ResourceBundle bundle = ResourceBundle.getBundle(getResourceBundleName(), aLocale);
			// retrieve the message from the resource bundle
			String message = bundle.getString(getMessageKey());
			// if arguments exist, use MessageFormat to include them
			if (getArguments().length > 0) {
				MessageFormat fmt = new MessageFormat(message);
				fmt.setLocale(aLocale);
				return fmt.format(getArguments());
			} else
				return message;
		} catch (Exception e) {
			return "EXCEPTION MESSAGE LOCALIZATION FAILED: " + e.toString();
		}
	}

	/**
	 * Gets the cause of this Exception.
	 * 
	 * @return the Throwable that caused this Exception to occur, if any.
	 *         Returns <code>null</code> if there is no such cause.
	 */
	public Throwable getCause() {
		return mCause;
	}

	/**
	 * Checks if this exception, or any of its root causes, has a particular
	 * UIMA message key. This allows checking for particular error condition
	 * 
	 * @param t
	 * @param sofa_mapping_not_supported_for_remote
	 * @return
	 */
	public boolean hasMessageKey(String messageKey) {
		if (messageKey.equals(this.getMessageKey())) {
			return true;
		}
		Throwable cause = getCause();
		if (cause != null && cause instanceof InternationalizedException) {
			return ((InternationalizedException)cause).hasMessageKey(messageKey);    
		}
		return false;
	}    
}
