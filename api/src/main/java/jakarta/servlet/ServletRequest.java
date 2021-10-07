/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates and others.
 * All rights reserved.
 * Copyright 2004 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.servlet;

import java.io.*;
import java.util.*;

/**
 * Defines an object to provide client request information to a servlet. The servlet container creates a
 * <code>ServletRequest</code> object and passes it as an argument to the servlet's <code>service</code> method.
 *
 * <p>
 * A <code>ServletRequest</code> object provides data including parameter name and values, attributes, and an input
 * stream. Interfaces that extend <code>ServletRequest</code> can provide additional protocol-specific data (for
 * example, HTTP data is provided by {@link jakarta.servlet.http.HttpServletRequest}.
 * 
 * @author Various
 *
 * @see jakarta.servlet.http.HttpServletRequest
 *
 */
public interface ServletRequest {

    /**
     * Returns the value of the named attribute as an <code>Object</code>, or <code>null</code> if no attribute of the given
     * name exists.
     *
     * <p>
     * Attributes can be set two ways. The servlet container may set attributes to make available custom information about a
     * request. For example, for requests made using HTTPS, the attribute
     * <code>jakarta.servlet.request.X509Certificate</code> can be used to retrieve information on the certificate of the
     * client. Attributes can also be set programmatically using {@link ServletRequest#setAttribute}. This allows
     * information to be embedded into a request before a {@link RequestDispatcher} call.
     *
     * <p>
     * Attribute names should follow the same conventions as package names. This specification reserves names matching
     * <code>java.*</code>, <code>javax.*</code>, and <code>sun.*</code>.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     *
     * @return an <code>Object</code> containing the value of the attribute, or <code>null</code> if the attribute does not
     * exist
     */
    public Object getAttribute(String name);

    /**
     * Returns an <code>Enumeration</code> containing the names of the attributes available to this request. This method
     * returns an empty <code>Enumeration</code> if the request has no attributes available to it.
     * 
     * @return an <code>Enumeration</code> of strings containing the names of the request's attributes
     */
    public Enumeration<String> getAttributeNames();

    /**
     * Returns the name of the character encoding used in the body of this request. This method returns <code>null</code> if
     * no request encoding character encoding has been specified. The following methods for specifying the request character
     * encoding are consulted, in decreasing order of priority: per request, per web app (using
     * {@link ServletContext#setRequestCharacterEncoding}, deployment descriptor), and per container (for all web
     * applications deployed in that container, using vendor specific configuration).
     * 
     * @return a <code>String</code> containing the name of the character encoding, or <code>null</code> if the request does
     * not specify a character encoding
     */
    public String getCharacterEncoding();

    /**
     * Overrides the name of the character encoding used in the body of this request. This method must be called prior to
     * reading request parameters or reading input using getReader(). Otherwise, it has no effect.
     * 
     * @param env <code>String</code> containing the name of the character encoding.
     *
     * @throws UnsupportedEncodingException if this ServletRequest is still in a state where a character encoding may be
     * set, but the specified encoding is invalid
     */
    public void setCharacterEncoding(String env) throws UnsupportedEncodingException;

    /**
     * Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not
     * known or is greater than Integer.MAX_VALUE.
     *
     * @return an integer containing the length of the request body or -1 if the length is not known or is greater than
     * Integer.MAX_VALUE.
     */
    public int getContentLength();

    /**
     * Returns the length, in bytes, of the request body and made available by the input stream, or -1 if the length is not
     * known.
     *
     * @return a long containing the length of the request body or -1L if the length is not known
     *
     * @since Servlet 3.1
     */
    public long getContentLengthLong();

    /**
     * Returns the MIME type of the body of the request, or <code>null</code> if the type is not known.
     *
     * @return a <code>String</code> containing the name of the MIME type of the request, or null if the type is not known
     */
    public String getContentType();

    /**
     * Retrieves the body of the request as binary data using a {@link ServletInputStream}. Either this method or
     * {@link #getReader} may be called to read the body, not both.
     *
     * @return a {@link ServletInputStream} object containing the body of the request
     *
     * @exception IllegalStateException if the {@link #getReader} method has already been called for this request
     *
     * @exception IOException if an input or output exception occurred
     */
    public ServletInputStream getInputStream() throws IOException;

    /**
     * Returns the value of a request parameter as a <code>String</code>, or <code>null</code> if the parameter does not
     * exist. Request parameters are extra information sent with the request. For HTTP servlets, parameters are contained in
     * the query string or posted form data.
     *
     * <p>
     * You should only use this method when you are sure the parameter has only one value. If the parameter might have more
     * than one value, use {@link #getParameterValues}.
     *
     * <p>
     * If you use this method with a multivalued parameter, the value returned is equal to the first value in the array
     * returned by <code>getParameterValues</code>.
     *
     * <p>
     * If the parameter data was sent in the request body, such as occurs with an HTTP POST request, then reading the body
     * directly via {@link #getInputStream} or {@link #getReader} can interfere with the execution of this method.
     *
     * @param name a <code>String</code> specifying the name of the parameter
     *
     * @return a <code>String</code> representing the single value of the parameter
     *
     * @see #getParameterValues
     */
    public String getParameter(String name);

    /**
     *
     * Returns an <code>Enumeration</code> of <code>String</code> objects containing the names of the parameters contained
     * in this request. If the request has no parameters, the method returns an empty <code>Enumeration</code>.
     *
     * @return an <code>Enumeration</code> of <code>String</code> objects, each <code>String</code> containing the name of a
     * request parameter; or an empty <code>Enumeration</code> if the request has no parameters
     */
    public Enumeration<String> getParameterNames();

    /**
     * Returns an array of <code>String</code> objects containing all of the values the given request parameter has, or
     * <code>null</code> if the parameter does not exist.
     *
     * <p>
     * If the parameter has a single value, the array has a length of 1.
     *
     * @param name a <code>String</code> containing the name of the parameter whose value is requested
     *
     * @return an array of <code>String</code> objects containing the parameter's values
     *
     * @see #getParameter
     */
    public String[] getParameterValues(String name);

    /**
     * Returns a java.util.Map of the parameters of this request.
     * 
     * <p>
     * Request parameters are extra information sent with the request. For HTTP servlets, parameters are contained in the
     * query string or posted form data.
     *
     * @return an immutable java.util.Map containing parameter names as keys and parameter values as map values. The keys in
     * the parameter map are of type String. The values in the parameter map are of type String array.
     */
    public Map<String, String[]> getParameterMap();

    /**
     * Returns the name and version of the protocol the request uses in the form <i>protocol/majorVersion.minorVersion</i>,
     * for example, HTTP/1.1.
     *
     * @return a <code>String</code> containing the protocol name and version number
     */
    public String getProtocol();

    /**
     * Returns the name of the scheme used to make this request, for example, <code>http</code>, <code>https</code>, or
     * <code>ftp</code>. Different schemes have different rules for constructing URLs, as noted in RFC 1738.
     *
     * @return a <code>String</code> containing the name of the scheme used to make this request
     */
    public String getScheme();

    /**
     * Returns the host name of the server to which the request was sent. It may be derived from a protocol specific
     * mechanism, such as the <code>Host</code> header, or the HTTP/2 authority, or
     * <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a>, otherwise the resolved server name or the server IP
     * address.
     *
     * @return a <code>String</code> containing the name of the server
     */
    public String getServerName();

    /**
     * Returns the port number to which the request was sent. It may be derived from a protocol specific mechanism, such as
     * the <code>Host</code> header, or HTTP authority, or <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a>,
     * otherwise the server port where the client connection was accepted on.
     *
     * @return an integer specifying the port number
     */
    public int getServerPort();

    /**
     * Retrieves the body of the request as character data using a <code>BufferedReader</code>. The reader translates the
     * character data according to the character encoding used on the body. Either this method or {@link #getInputStream}
     * may be called to read the body, not both.
     * 
     * @return a <code>BufferedReader</code> containing the body of the request
     *
     * @exception UnsupportedEncodingException if the character set encoding used is not supported and the text cannot be
     * decoded
     *
     * @exception IllegalStateException if {@link #getInputStream} method has been called on this request
     *
     * @exception IOException if an input or output exception occurred
     *
     * @see #getInputStream
     */
    public BufferedReader getReader() throws IOException;

    /**
     * Returns the Internet Protocol (IP) of the remote end of the connection on which the request was received. By default
     * this is either the address of the client or last proxy that sent the request. In some cases a protocol specific
     * mechanism, such as <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a>, may be used to obtain an address
     * different to that of the actual TCP/IP connection.
     *
     * @return a <code>String</code> containing an IP address
     */
    public String getRemoteAddr();

    /**
     * Returns the fully qualified name of the address returned by {@link #getRemoteAddr()}. If the engine cannot or chooses
     * not to resolve the hostname (to improve performance), this method returns the IP address.
     *
     * @return a <code>String</code> containing a fully qualified name or IP address.
     */
    public String getRemoteHost();

    /**
     * Stores an attribute in this request. Attributes are reset between requests. This method is most often used in
     * conjunction with {@link RequestDispatcher}.
     *
     * <p>
     * Attribute names should follow the same conventions as package names. <br>
     * If the object passed in is null, the effect is the same as calling {@link #removeAttribute}. <br>
     * It is warned that when the request is dispatched from the servlet resides in a different web application by
     * <code>RequestDispatcher</code>, the object set by this method may not be correctly retrieved in the caller servlet.
     *
     * @param name a <code>String</code> specifying the name of the attribute
     *
     * @param o the <code>Object</code> to be stored
     *
     */
    public void setAttribute(String name, Object o);

    /**
     *
     * Removes an attribute from this request. This method is not generally needed as attributes only persist as long as the
     * request is being handled.
     *
     * <p>
     * Attribute names should follow the same conventions as package names. Names beginning with <code>java.*</code>,
     * <code>javax.*</code>, and <code>com.sun.*</code>, are reserved for use by Sun Microsystems.
     *
     * @param name a <code>String</code> specifying the name of the attribute to remove
     */
    public void removeAttribute(String name);

    /**
     * Returns the preferred <code>Locale</code> that the client will accept content in, based on the Accept-Language
     * header. If the client request doesn't provide an Accept-Language header, this method returns the default locale for
     * the server.
     *
     * @return the preferred <code>Locale</code> for the client
     */
    public Locale getLocale();

    /**
     * Returns an <code>Enumeration</code> of <code>Locale</code> objects indicating, in decreasing order starting with the
     * preferred locale, the locales that are acceptable to the client based on the Accept-Language header. If the client
     * request doesn't provide an Accept-Language header, this method returns an <code>Enumeration</code> containing one
     * <code>Locale</code>, the default locale for the server.
     *
     * @return an <code>Enumeration</code> of preferred <code>Locale</code> objects for the client
     */
    public Enumeration<Locale> getLocales();

    /**
     *
     * Returns a boolean indicating whether this request was made using a secure channel, such as HTTPS.
     *
     * @return a boolean indicating if the request was made using a secure channel
     */
    public boolean isSecure();

    /**
     *
     * Returns a {@link RequestDispatcher} object that acts as a wrapper for the resource located at the given path. A
     * <code>RequestDispatcher</code> object can be used to forward a request to the resource or to include the resource in
     * a response. The resource can be dynamic or static.
     *
     * <p>
     * The pathname specified may be relative, although it cannot extend outside the current servlet context. If the path
     * begins with a "/" it is interpreted as relative to the current context root. This method returns <code>null</code> if
     * the servlet container cannot return a <code>RequestDispatcher</code>.
     *
     * <p>
     * Using a RequestDispatcher, requests may be dispatched to any part of the web application bypassing both implicit (no
     * direct access to WEB-INF or META-INF) and explicit (defined by the web application) security constraints. Unsanitized
     * user provided data must not be used to construct the path passed to the RequestDispatcher as it is very likely to
     * create a security vulnerability in the application.
     *
     * <p>
     * The difference between this method and {@link ServletContext#getRequestDispatcher} is that this method can take a
     * relative path.
     *
     * @param path a <code>String</code> specifying the pathname to the resource. If it is relative, it must be relative
     * against the current servlet.
     *
     * @return a <code>RequestDispatcher</code> object that acts as a wrapper for the resource at the specified path, or
     * <code>null</code> if the servlet container cannot return a <code>RequestDispatcher</code>
     *
     * @see RequestDispatcher
     * @see ServletContext#getRequestDispatcher
     */
    public RequestDispatcher getRequestDispatcher(String path);

    /**
     * Returns the Internet Protocol (IP) source port the remote end of the connection on which the request was received. By
     * default this is either the port of the client or last proxy that sent the request. In some cases, protocol specific
     * mechanisms such as <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a> may be used to obtain a port different
     * to that of the actual TCP/IP connection.
     *
     * @return an integer specifying the port number
     *
     * @since Servlet 2.4
     */
    public int getRemotePort();

    /**
     * Returns the fully qualified name of the address returned by {@link #getLocalAddr()}. If the engine cannot or chooses
     * not to resolve the hostname (to improve performance), this method returns the IP address.
     * 
     * @return a <code>String</code> containing the host name of the IP on which the request was received.
     *
     * @since Servlet 2.4
     */
    public String getLocalName();

    /**
     * Returns the Internet Protocol (IP) address representing the interface on which the request was received. In some
     * cases a protocol specific mechanism, such as <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a>, may be used
     * to obtain an address different to that of the actual TCP/IP connection.
     * 
     * @return a <code>String</code> containing an IP address.
     *
     * @since Servlet 2.4
     */
    public String getLocalAddr();

    /**
     * Returns the Internet Protocol (IP) port number representing the interface on which the request was received. In some
     * cases, a protocol specific mechanism such as <a href="https://tools.ietf.org/html/rfc7239">RFC 7239</a> may be used
     * to obtain an address different to that of the actual TCP/IP connection.
     *
     * @return an integer specifying a port number
     *
     * @since Servlet 2.4
     */
    public int getLocalPort();

    /**
     * Gets the servlet context to which this ServletRequest was last dispatched.
     *
     * @return the servlet context to which this ServletRequest was last dispatched
     *
     * @since Servlet 3.0
     */
    public ServletContext getServletContext();

    /**
     * Puts this request into asynchronous mode, and initializes its {@link AsyncContext} with the original (unwrapped)
     * ServletRequest and ServletResponse objects.
     *
     * <p>
     * Calling this method will cause committal of the associated response to be delayed until {@link AsyncContext#complete}
     * is called on the returned {@link AsyncContext}, or the asynchronous operation has timed out.
     *
     * <p>
     * Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on the returned AsyncContext will return
     * <code>true</code>. Any filters invoked in the <i>outbound</i> direction after this request was put into asynchronous
     * mode may use this as an indication that any request and/or response wrappers that they added during their
     * <i>inbound</i> invocation need not stay around for the duration of the asynchronous operation, and therefore any of
     * their associated resources may be released.
     *
     * <p>
     * This method clears the list of {@link AsyncListener} instances (if any) that were registered with the AsyncContext
     * returned by the previous call to one of the startAsync methods, after calling each AsyncListener at its
     * {@link AsyncListener#onStartAsync onStartAsync} method.
     *
     * <p>
     * Subsequent invocations of this method, or its overloaded variant, will return the same AsyncContext instance,
     * reinitialized as appropriate.
     *
     * @return the (re)initialized AsyncContext
     * 
     * @throws IllegalStateException if this request is within the scope of a filter or servlet that does not support
     * asynchronous operations (that is, {@link #isAsyncSupported} returns false), or if this method is called again without
     * any asynchronous dispatch (resulting from one of the {@link AsyncContext#dispatch} methods), is called outside the
     * scope of any such dispatch, or is called again within the scope of the same dispatch, or if the response has already
     * been closed
     *
     * @see AsyncContext#dispatch()
     * @since Servlet 3.0
     */
    public AsyncContext startAsync() throws IllegalStateException;

    /**
     * Puts this request into asynchronous mode, and initializes its {@link AsyncContext} with the given request and
     * response objects.
     *
     * <p>
     * The ServletRequest and ServletResponse arguments must be the same instances, or instances of
     * {@link ServletRequestWrapper} and {@link ServletResponseWrapper} that wrap them, that were passed to the
     * {@link Servlet#service service} method of the Servlet or the {@link Filter#doFilter doFilter} method of the Filter,
     * respectively, in whose scope this method is being called.
     *
     * <p>
     * Calling this method will cause committal of the associated response to be delayed until {@link AsyncContext#complete}
     * is called on the returned {@link AsyncContext}, or the asynchronous operation has timed out.
     *
     * <p>
     * Calling {@link AsyncContext#hasOriginalRequestAndResponse()} on the returned AsyncContext will return
     * <code>false</code>, unless the passed in ServletRequest and ServletResponse arguments are the original ones or do not
     * carry any application-provided wrappers. Any filters invoked in the <i>outbound</i> direction after this request was
     * put into asynchronous mode may use this as an indication that some of the request and/or response wrappers that they
     * added during their <i>inbound</i> invocation may need to stay in place for the duration of the asynchronous
     * operation, and their associated resources may not be released. A ServletRequestWrapper applied during the
     * <i>inbound</i> invocation of a filter may be released by the <i>outbound</i> invocation of the filter only if the
     * given <code>servletRequest</code>, which is used to initialize the AsyncContext and will be returned by a call to
     * {@link AsyncContext#getRequest()}, does not contain said ServletRequestWrapper. The same holds true for
     * ServletResponseWrapper instances.
     *
     * <p>
     * This method clears the list of {@link AsyncListener} instances (if any) that were registered with the AsyncContext
     * returned by the previous call to one of the startAsync methods, after calling each AsyncListener at its
     * {@link AsyncListener#onStartAsync onStartAsync} method.
     *
     * <p>
     * Subsequent invocations of this method, or its zero-argument variant, will return the same AsyncContext instance,
     * reinitialized as appropriate. If a call to this method is followed by a call to its zero-argument variant, the
     * specified (and possibly wrapped) request and response objects will remain <i>locked in</i> on the returned
     * AsyncContext.
     *
     * @param servletRequest the ServletRequest used to initialize the AsyncContext
     * @param servletResponse the ServletResponse used to initialize the AsyncContext
     *
     * @return the (re)initialized AsyncContext
     * 
     * @throws IllegalStateException if this request is within the scope of a filter or servlet that does not support
     * asynchronous operations (that is, {@link #isAsyncSupported} returns false), or if this method is called again without
     * any asynchronous dispatch (resulting from one of the {@link AsyncContext#dispatch} methods), is called outside the
     * scope of any such dispatch, or is called again within the scope of the same dispatch, or if the response has already
     * been closed
     *
     * @since Servlet 3.0
     */
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
            throws IllegalStateException;

    /**
     * Checks if this request has been put into asynchronous mode.
     *
     * <p>
     * A ServletRequest is put into asynchronous mode by calling {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on it.
     * 
     * <p>
     * This method returns <tt>false</tt> if this request was put into asynchronous mode, but has since been dispatched
     * using one of the {@link AsyncContext#dispatch} methods or released from asynchronous mode via a call to
     * {@link AsyncContext#complete}.
     *
     * @return true if this request has been put into asynchronous mode, false otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncStarted();

    /**
     * Checks if this request supports asynchronous operation.
     *
     * <p>
     * Asynchronous operation is disabled for this request if this request is within the scope of a filter or servlet that
     * has not been annotated or flagged in the deployment descriptor as being able to support asynchronous handling.
     *
     * @return true if this request supports asynchronous operation, false otherwise
     *
     * @since Servlet 3.0
     */
    public boolean isAsyncSupported();

    /**
     * Gets the AsyncContext that was created or reinitialized by the most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on this request.
     *
     * @return the AsyncContext that was created or reinitialized by the most recent invocation of {@link #startAsync} or
     * {@link #startAsync(ServletRequest,ServletResponse)} on this request
     *
     * @throws IllegalStateException if this request has not been put into asynchronous mode, i.e., if neither
     * {@link #startAsync} nor {@link #startAsync(ServletRequest,ServletResponse)} has been called
     *
     * @since Servlet 3.0
     */
    public AsyncContext getAsyncContext();

    /**
     * Gets the dispatcher type of this request.
     *
     * <p>
     * The dispatcher type of a request is used by the container to select the filters that need to be applied to the
     * request: Only filters with matching dispatcher type and url patterns will be applied.
     * 
     * <p>
     * Allowing a filter that has been configured for multiple dispatcher types to query a request for its dispatcher type
     * allows the filter to process the request differently depending on its dispatcher type.
     *
     * <p>
     * The initial dispatcher type of a request is defined as <code>DispatcherType.REQUEST</code>. The dispatcher type of a
     * request dispatched via {@link RequestDispatcher#forward(ServletRequest, ServletResponse)} or
     * {@link RequestDispatcher#include(ServletRequest, ServletResponse)} is given as <code>DispatcherType.FORWARD</code> or
     * <code>DispatcherType.INCLUDE</code>, respectively, while the dispatcher type of an asynchronous request dispatched
     * via one of the {@link AsyncContext#dispatch} methods is given as <code>DispatcherType.ASYNC</code>. Finally, the
     * dispatcher type of a request dispatched to an error page by the container's error handling mechanism is given as
     * <code>DispatcherType.ERROR</code>.
     *
     * @return the dispatcher type of this request
     * 
     * @see DispatcherType
     *
     * @since Servlet 3.0
     */
    public DispatcherType getDispatcherType();

    /**
     * Obtain a unique (within the lifetime of the Servlet container) identifier string for this request.
     * <p>
     * There is no defined format for this string. The format is implementation dependent.
     * 
     * @return A unique identifier for the request
     * 
     * @since Servlet 6.0
     */
    String getRequestId();

    /**
     * Obtain the request identifier for this request as defined by the protocol in use. Note that some protocols do not
     * define such an identifier.
     * <p>
     * Examples of protocol provided request identifiers include:
     * <dl>
     * <dt>HTTP 1.x</dt>
     * <dd>None, so the empty string should be returned</dd>
     * <dt>HTTP 2</dt>
     * <dd>The stream identifier</dd>
     * <dt>HTTP 3</dt>
     * <dd>The stream identifier</dd>
     * <dt>AJP</dt>
     * <dd>None, so the empty string should be returned</dd>
     * </dl>
     *
     * @return The request identifier if one is defined, otherwise an empty string
     * 
     * @since Servlet 6.0
     */
    String getProtocolRequestId();

    /**
     * Obtain details of the network connection to the Servlet container that is being used by this request. The information
     * presented may differ from information presented elsewhere in the Servlet API as raw information is presented without
     * adjustments for, example, use of reverse proxies that may be applied elsewhere in the Servlet API.
     * 
     * @return The network connection details.
     * 
     * @since Servlet 6.0
     */
    ServletConnection getServletConnection();
}
