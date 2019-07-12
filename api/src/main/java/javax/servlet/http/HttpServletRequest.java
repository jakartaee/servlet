/*
 * Copyright (c) 1997-2018 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet.http;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

/**
 *
 * Extends the {@link javax.servlet.ServletRequest} interface to provide
 * request information for HTTP servlets.
 *
 * <p>The servlet container creates an <code>HttpServletRequest</code>
 * object and passes it as an argument to the servlet's service
 * methods (<code>doGet</code>, <code>doPost</code>, etc).
 *
 *
 * @author 	Various
 */

public interface HttpServletRequest extends ServletRequest {

    /**
     * String identifier for Basic authentication. Value "BASIC"
     */
    public static final String BASIC_AUTH = "BASIC";

    /**
     * String identifier for Form authentication. Value "FORM"
     */
    public static final String FORM_AUTH = "FORM";

    /**
     * String identifier for Client Certificate authentication. Value "CLIENT_CERT"
     */
    public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";

    /**
     * String identifier for Digest authentication. Value "DIGEST"
     */
    public static final String DIGEST_AUTH = "DIGEST";

    /**
     * Returns the name of the authentication scheme used to protect
     * the servlet. All servlet containers support basic, form and client
     * certificate authentication, and may additionally support digest
     * authentication.
     * If the servlet is not authenticated <code>null</code> is returned.
     *
     * <p>Same as the value of the CGI variable AUTH_TYPE.
     *
     * @return		one of the static members BASIC_AUTH,
     *			FORM_AUTH, CLIENT_CERT_AUTH, DIGEST_AUTH
     *			(suitable for == comparison) or
     *			the container-specific string indicating
     *			the authentication scheme, or
     *			<code>null</code> if the request was
     *			not authenticated.
     */
    public String getAuthType();

    /**
     * Returns an array containing all of the <code>Cookie</code>
     * objects the client sent with this request.
     * This method returns <code>null</code> if no cookies were sent.
     *
     * @return		an array of all the <code>Cookies</code>
     *			included with this request, or <code>null</code>
     *			if the request has no cookies
     */
    public Cookie[] getCookies();

    /**
     * Returns the value of the specified request header
     * as a <code>long</code> value that represents a
     * <code>Date</code> object. Use this method with
     * headers that contain dates, such as
     * <code>If-Modified-Since</code>.
     *
     * <p>The date is returned as
     * the number of milliseconds since January 1, 1970 GMT.
     * The header name is case insensitive.
     *
     * <p>If the request did not have a header of the
     * specified name, this method returns -1. If the header
     * can't be converted to a date, the method throws
     * an <code>IllegalArgumentException</code>.
     *
     * @param name		a <code>String</code> specifying the
     *				name of the header
     *
     * @return			a <code>long</code> value
     *				representing the date specified
     *				in the header expressed as
     *				the number of milliseconds
     *				since January 1, 1970 GMT,
     *				or -1 if the named header
     *				was not included with the
     *				request
     *
     * @exception	IllegalArgumentException	If the header value
     *							can't be converted
     *							to a date
     */
    public long getDateHeader(String name);

    /**
     * Returns the value of the specified request header
     * as a <code>String</code>. If the request did not include a header
     * of the specified name, this method returns <code>null</code>.
     * If there are multiple headers with the same name, this method
     * returns the first head in the request.
     * The header name is case insensitive. You can use
     * this method with any request header.
     *
     * @param name		a <code>String</code> specifying the
     *				header name
     *
     * @return			a <code>String</code> containing the
     *				value of the requested
     *				header, or <code>null</code>
     *				if the request does not
     *				have a header of that name
     */
    public String getHeader(String name);

    /**
     * Returns all the values of the specified request header
     * as an <code>Enumeration</code> of <code>String</code> objects.
     *
     * <p>Some headers, such as <code>Accept-Language</code> can be sent
     * by clients as several headers each with a different value rather than
     * sending the header as a comma separated list.
     *
     * <p>If the request did not include any headers
     * of the specified name, this method returns an empty
     * <code>Enumeration</code>.
     * The header name is case insensitive. You can use
     * this method with any request header.
     *
     * @param name		a <code>String</code> specifying the
     *				header name
     *
     * @return			an <code>Enumeration</code> containing
     *                  	the values of the requested header. If
     *                  	the request does not have any headers of
     *                  	that name return an empty
     *                  	enumeration. If
     *                  	the container does not allow access to
     *                  	header information, return null
     */
    public Enumeration<String> getHeaders(String name);

    /**
     * Returns an enumeration of all the header names
     * this request contains. If the request has no
     * headers, this method returns an empty enumeration.
     *
     * <p>Some servlet containers do not allow
     * servlets to access headers using this method, in
     * which case this method returns <code>null</code>
     *
     * @return			an enumeration of all the
     *				header names sent with this
     *				request; if the request has
     *				no headers, an empty enumeration;
     *				if the servlet container does not
     *				allow servlets to use this method,
     *				<code>null</code>
     */
    public Enumeration<String> getHeaderNames();

    /**
     * Returns the value of the specified request header
     * as an <code>int</code>. If the request does not have a header
     * of the specified name, this method returns -1. If the
     * header cannot be converted to an integer, this method
     * throws a <code>NumberFormatException</code>.
     *
     * <p>The header name is case insensitive.
     *
     * @param name		a <code>String</code> specifying the name
     *				of a request header
     *
     * @return			an integer expressing the value
     * 				of the request header or -1
     *				if the request doesn't have a
     *				header of this name
     *
     * @exception	NumberFormatException		If the header value
     *							can't be converted
     *							to an <code>int</code>
     */
    public int getIntHeader(String name);

    /**
     * <p>Return the {@link HttpServletMapping} by which the {@link
     * HttpServlet} for this {@code HttpServletRequest} was invoked.
     * The mappings for any applicable {@link javax.servlet.Filter}s are
     * not indicated in the result.  If the currently active {@link
     * javax.servlet.Servlet} invocation was obtained by a call to
     * {@link ServletRequest#getRequestDispatcher} followed by a call to
     * {@link RequestDispatcher#forward}, the returned {@code
     * HttpServletMapping} is the one corresponding to the path used to
     * obtain the {@link RequestDispatcher}.  If the currently active
     * {@code Servlet} invocation was obtained by a call to {@link
     * ServletRequest#getRequestDispatcher} followed by a call to {@link
     * RequestDispatcher#include}, the returned {@code
     * HttpServletMapping} is the one corresponding to the path that
     * caused the first {@code Servlet} in the invocation sequence to be
     * invoked.  If the currently active {@code Servlet} invocation was
     * obtained by a call to {@link
     * javax.servlet.AsyncContext#dispatch}, the returned {@code
     * HttpServletMapping} is the one corresponding to the path that
     * caused the first {@code Servlet} in the invocation sequence to be
     * invoked.  See {@link
     * javax.servlet.RequestDispatcher#FORWARD_MAPPING}, {@link
     * javax.servlet.RequestDispatcher#INCLUDE_MAPPING} and {@link
     * javax.servlet.AsyncContext#ASYNC_MAPPING} for additional request
     * attributes related to {@code HttpServletMapping}. If the
     * currently active {@code Servlet} invocation was obtained by a
     * call to {@link javax.servlet.ServletContext#getNamedDispatcher},
     * the returned {@code HttpServletMapping} is the one corresponding
     * to the path for the mapping last applied to this request.</p>
     * 
     * <p>The returned object is immutable.  Servlet 4.0 compliant
     * implementations must override this method.</p>
     * 
     * @implSpec The default implementation returns a {@code
     * HttpServletMapping} that returns the empty string for the match
     * value, pattern and servlet name and {@code null} for the match
     * type.
     *
     * @return An instance of {@code HttpServletMapping} describing the manner in which
     * the current request was invoked.
     * 
     * @since 4.0
     */
    
    default public HttpServletMapping getHttpServletMapping() {
        return new HttpServletMapping() {
            @Override
            public String getMatchValue() {
                return "";
            }

            @Override
            public String getPattern() {
                return "";
            }

            @Override
            public String getServletName() {
                return "";
            }

            @Override
            public MappingMatch getMappingMatch() {
               return null;
            }

            @Override
            public String toString() {
                return "MappingImpl{" + "matchValue=" + getMatchValue()
                        + ", pattern=" + getPattern() + ", servletName=" 
                        + getServletName() + ", mappingMatch=" + getMappingMatch() 
                        + "} HttpServletRequest {" + HttpServletRequest.this.toString()
                        + '}';
            }
            
            
            
        };
    }
    
    /**
     * Returns the name of the HTTP method with which this
     * request was made, for example, GET, POST, or PUT.
     * Same as the value of the CGI variable REQUEST_METHOD.
     *
     * @return			a <code>String</code>
     *				specifying the name
     *				of the method with which
     *				this request was made
     */
    public String getMethod();

    /**
     * Returns any extra path information associated with
     * the URL the client sent when it made this request.
     * The extra path information follows the servlet path
     * but precedes the query string and will start with
     * a "/" character.
     *
     * <p>This method returns <code>null</code> if there
     * was no extra path information.
     *
     * <p>Same as the value of the CGI variable PATH_INFO.
     *
     * @return		a <code>String</code>, decoded by the
     *			web container, specifying
     *			extra path information that comes
     *			after the servlet path but before
     *			the query string in the request URL;
     *			or <code>null</code> if the URL does not have
     *			any extra path information
     */
    public String getPathInfo();

    /**
     * Returns any extra path information after the servlet name
     * but before the query string, and translates it to a real
     * path. Same as the value of the CGI variable PATH_TRANSLATED.
     *
     * <p>If the URL does not have any extra path information,
     * this method returns <code>null</code> or the servlet container
     * cannot translate the virtual path to a real path for any reason
     * (such as when the web application is executed from an archive).
     *
     * The web container does not decode this string.
     *
     * @return		a <code>String</code> specifying the
     *			real path, or <code>null</code> if
     *			the URL does not have any extra path
     *			information
     */
    public String getPathTranslated();

    /**
     * Instantiates a new instance of {@link PushBuilder} for issuing server
     * push responses from the current request. This method returns null
     * if the current connection does not support server push, or server
     * push has been disabled by the client via a
     * {@code SETTINGS_ENABLE_PUSH} settings frame value of {@code 0} (zero).
     *
     * @implSpec
     * The default implementation returns null.
     *
     * @return a {@link PushBuilder} for issuing server push responses
     * from the current request, or null if push is not supported
     *
     * @since Servlet 4.0
     */
     default public PushBuilder newPushBuilder() {
         return null;
     }

    /**
     * Returns the portion of the request URI that indicates the context
     * of the request. The context path always comes first in a request
     * URI. The path starts with a "/" character but does not end with a "/"
     * character. For servlets in the default (root) context, this method
     * returns "". The container does not decode this string.
     *
     * <p>It is possible that a servlet container may match a context by
     * more than one context path. In such cases this method will return the
     * actual context path used by the request and it may differ from the
     * path returned by the
     * {@link javax.servlet.ServletContext#getContextPath()} method.
     * The context path returned by
     * {@link javax.servlet.ServletContext#getContextPath()}
     * should be considered as the prime or preferred context path of the
     * application.
     *
     * @return		a <code>String</code> specifying the
     *			portion of the request URI that indicates the context
     *			of the request
     *
     * @see javax.servlet.ServletContext#getContextPath()
     */
    public String getContextPath();

    /**
     * Returns the query string that is contained in the request
     * URL after the path. This method returns <code>null</code>
     * if the URL does not have a query string. Same as the value
     * of the CGI variable QUERY_STRING.
     *
     * @return		a <code>String</code> containing the query
     *			string or <code>null</code> if the URL
     *			contains no query string. The value is not
     *			decoded by the container.
     */
    public String getQueryString();

    /**
     * Returns the login of the user making this request, if the
     * user has been authenticated, or <code>null</code> if the user
     * has not been authenticated.
     * Whether the user name is sent with each subsequent request
     * depends on the browser and type of authentication. Same as the
     * value of the CGI variable REMOTE_USER.
     *
     * @return		a <code>String</code> specifying the login
     *			of the user making this request, or <code>null</code>
     *			if the user login is not known
     */
    public String getRemoteUser();

    /**
     * Returns a boolean indicating whether the authenticated user is included
     * in the specified logical "role".  Roles and role membership can be
     * defined using deployment descriptors.  If the user has not been
     * authenticated, the method returns <code>false</code>.
     *
     * <p>The role name "*" should never be used as an argument in calling
     * <code>isUserInRole</code>. Any call to <code>isUserInRole</code> with
     * "*" must return false.
     * If the role-name of the security-role to be tested is "**", and
     * the application has NOT declared an application security-role with
     * role-name "**", <code>isUserInRole</code> must only return true if
     * the user has been authenticated; that is, only when
     * {@link #getRemoteUser} and {@link #getUserPrincipal} would both return
     * a non-null value. Otherwise, the container must check
     * the user for membership in the application role.
     *
     * @param role		a <code>String</code> specifying the name
     *				of the role
     *
     * @return		a <code>boolean</code> indicating whether
     *			the user making this request belongs to a given role;
     *			<code>false</code> if the user has not been
     *			authenticated
     */
    public boolean isUserInRole(String role);

    /**
     * Returns a <code>java.security.Principal</code> object containing
     * the name of the current authenticated user. If the user has not been
     * authenticated, the method returns <code>null</code>.
     *
     * @return		a <code>java.security.Principal</code> containing
     *			the name of the user making this request;
     *			<code>null</code> if the user has not been
     *			authenticated
     */
    public java.security.Principal getUserPrincipal();

    /**
     * Returns the session ID specified by the client. This may
     * not be the same as the ID of the current valid session
     * for this request.
     * If the client did not specify a session ID, this method returns
     * <code>null</code>.
     *
     * @return		a <code>String</code> specifying the session
     *			ID, or <code>null</code> if the request did
     *			not specify a session ID
     *
     * @see     #isRequestedSessionIdValid
     */
    public String getRequestedSessionId();

    /**
     * Returns the part of this request's URL from the protocol
     * name up to the query string in the first line of the HTTP request.
     * The web container does not decode this String.
     * For example:
     *
     * <table summary="Examples of Returned Values">
     * <tr align=left><th>First line of HTTP request      </th>
     * <th>     Returned Value</th>
     * <tr><td>POST /some/path.html HTTP/1.1<td><td>/some/path.html
     * <tr><td>GET http://foo.bar/a.html HTTP/1.0
     * <td><td>/a.html
     * <tr><td>HEAD /xyz?a=b HTTP/1.1<td><td>/xyz
     * </table>
     *
     * <p>To reconstruct an URL with a scheme and host, use
     * {@link HttpUtils#getRequestURL}.
     *
     * @return		a <code>String</code> containing
     *			the part of the URL from the
     *			protocol name up to the query string
     *
     * @see     HttpUtils#getRequestURL
     */
    public String getRequestURI();

    /**
     * Reconstructs the URL the client used to make the request.
     * The returned URL contains a protocol, server name, port
     * number, and server path, but it does not include query
     * string parameters.
     *
     * <p>If this request has been forwarded using
     * {@link javax.servlet.RequestDispatcher#forward}, the server path in the
     * reconstructed URL must reflect the path used to obtain the
     * RequestDispatcher, and not the server path specified by the client.
     *
     * <p>Because this method returns a <code>StringBuffer</code>,
     * not a string, you can modify the URL easily, for example,
     * to append query parameters.
     *
     * <p>This method is useful for creating redirect messages
     * and for reporting errors.
     *
     * @return		a <code>StringBuffer</code> object containing
     *			the reconstructed URL
     */
    public StringBuffer getRequestURL();

    /**
     * Returns the part of this request's URL that calls
     * the servlet. This path starts with a "/" character
     * and includes either the servlet name or a path to
     * the servlet, but does not include any extra path
     * information or a query string. Same as the value of
     * the CGI variable SCRIPT_NAME.
     *
     * <p>This method will return an empty string ("") if the
     * servlet used to process this request was matched using
     * the "/*" pattern.
     *
     * @return		a <code>String</code> containing
     *			the name or path of the servlet being
     *			called, as specified in the request URL,
     *			decoded, or an empty string if the servlet
     *			used to process the request is matched
     *			using the "/*" pattern.
     */
    public String getServletPath();

    /**
     * Returns the current <code>HttpSession</code>
     * associated with this request or, if there is no
     * current session and <code>create</code> is true, returns
     * a new session.
     *
     * <p>If <code>create</code> is <code>false</code>
     * and the request has no valid <code>HttpSession</code>,
     * this method returns <code>null</code>.
     *
     * <p>To make sure the session is properly maintained,
     * you must call this method before
     * the response is committed. If the container is using cookies
     * to maintain session integrity and is asked to create a new session
     * when the response is committed, an IllegalStateException is thrown.
     *
     * @param create	<code>true</code> to create
     *			a new session for this request if necessary;
     *			<code>false</code> to return <code>null</code>
     *			if there's no current session
     *
     * @return 		the <code>HttpSession</code> associated
     *			with this request or <code>null</code> if
     * 			<code>create</code> is <code>false</code>
     *			and the request has no valid session
     *
     * @see #getSession()
     */
    public HttpSession getSession(boolean create);

    /**
     * Returns the current session associated with this request,
     * or if the request does not have a session, creates one.
     *
     * @return		the <code>HttpSession</code> associated
     *			with this request
     *
     * @see	#getSession(boolean)
     */
    public HttpSession getSession();

    /**
     * Change the session id of the current session associated with this
     * request and return the new session id.
     *
     * @return the new session id
     *
     * @throws IllegalStateException if there is no session associated
     * with the request
     *
     * @since Servlet 3.1
     */
    public String changeSessionId();

    /**
     * Checks whether the requested session ID is still valid.
     *
     * <p>If the client did not specify any session ID, this method returns
     * <code>false</code>.
     *
     * @return			<code>true</code> if this
     *				request has an id for a valid session
     *				in the current session context;
     *				<code>false</code> otherwise
     *
     * @see			#getRequestedSessionId
     * @see			#getSession
     * @see			HttpSessionContext
     */
    public boolean isRequestedSessionIdValid();

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as an HTTP cookie.</p>
     *
     * @return			<code>true</code> if the session ID
     *				was conveyed to the server an an HTTP
     *				cookie; otherwise, <code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromCookie();

    /**
     * <p>Checks whether the requested session ID was conveyed to the
     * server as part of the request URL.</p>
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     *
     * @see         #getSession
     */
    public boolean isRequestedSessionIdFromURL();

    /**
     * @deprecated		As of Version 2.1 of the Java Servlet
     *				API, use {@link #isRequestedSessionIdFromURL}
     *				instead.
     *
     * @return <code>true</code> if the session ID was conveyed to the
     *				server as part of a URL; otherwise,
     *				<code>false</code>
     */
    @Deprecated
    public boolean isRequestedSessionIdFromUrl();

    /**
     * Use the container login mechanism configured for the
     * <code>ServletContext</code> to authenticate the user making
     * this request.
     *
     * <p>This method may modify and commit the argument
     * <code>HttpServletResponse</code>.
     *
     * @param response The <code>HttpServletResponse</code>
     * associated with this <code>HttpServletRequest</code>
     *
     * @return <code>true</code> when non-null values were or have been
     * established as the values returned by <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code>. Return
     * <code>false</code> if authentication is incomplete and the underlying
     * login mechanism has committed, in the response, the message (e.g.,
     * challenge) and HTTP status code to be returned to the user.
     *
     * @throws IOException if an input or output error occurred while
     * reading from this request or writing to the given response
     *
     * @throws IllegalStateException if the login mechanism attempted to
     * modify the response and it was already committed
     *
     * @throws ServletException if the authentication failed and
     * the caller is responsible for handling the error (i.e., the
     * underlying login mechanism did NOT establish the message and
     * HTTP status code to be returned to the user)
     *
     * @since Servlet 3.0
     */
    public boolean authenticate(HttpServletResponse response)
	throws IOException,ServletException;

    /**
     * Validate the provided username and password in the password validation
     * realm used by the web container login mechanism configured for the
     * <code>ServletContext</code>.
     *
     * <p>This method returns without throwing a <code>ServletException</code>
     * when the login mechanism configured for the <code>ServletContext</code>
     * supports username password validation, and when, at the time of the
     * call to login, the identity of the caller of the request had
     * not been established (i.e, all of <code>getUserPrincipal</code>,
     * <code>getRemoteUser</code>, and <code>getAuthType</code> return null),
     * and when validation of the provided credentials is successful.
     * Otherwise, this method throws a <code>ServletException</code> as
     * described below.
     *
     * <p>When this method returns without throwing an exception, it must
     * have established non-null values as the values returned by
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>, and
     * <code>getAuthType</code>.
     *
     * @param username The <code>String</code> value corresponding to
     * the login identifier of the user.
     *
     * @param password The password <code>String</code> corresponding
     * to the identified user.
     *
     * @exception	ServletException    if the configured login mechanism
     *                                      does not support username
     *                                      password authentication, or if a
     *                                      non-null caller identity had
     *                                      already been established (prior
     *                                      to the call to login), or if
     *                                      validation of the provided
     *                                      username and password fails.
     *
     * @since Servlet 3.0
     */
    public void login(String username, String password)
	throws ServletException;

    /**
     * Establish <code>null</code> as the value returned when
     * <code>getUserPrincipal</code>, <code>getRemoteUser</code>,
     * and <code>getAuthType</code> is called on the request.
     *
     * @exception ServletException if logout fails
     *
     * @since Servlet 3.0
     */
    public void logout() throws ServletException;

    /**
     * Gets all the {@link Part} components of this request, provided
     * that it is of type <code>multipart/form-data</code>.
     *
     * <p>If this request is of type <code>multipart/form-data</code>, but
     * does not contain any <code>Part</code> components, the returned
     * <code>Collection</code> will be empty.
     *
     * <p>Any changes to the returned <code>Collection</code> must not
     * affect this <code>HttpServletRequest</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the
     * <code>Part</code> components of this request
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the {@link Part} components of this request
     *
     * @throws ServletException if this request is not of type
     * <code>multipart/form-data</code>
     *
     * @throws IllegalStateException if the request body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * request is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-config</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Collection<Part> getParts() throws IOException, ServletException;

    /**
     * Gets the {@link Part} with the given name.
     *
     * @param name the name of the requested <code>Part</code>
     *
     * @return The <code>Part</code> with the given name, or
     * <code>null</code> if this request is of type
     * <code>multipart/form-data</code>, but does not
     * contain the requested <code>Part</code>
     *
     * @throws IOException if an I/O error occurred during the retrieval
     * of the requested <code>Part</code>
     * @throws ServletException if this request is not of type
     * <code>multipart/form-data</code>
     * @throws IllegalStateException if the request body is larger than
     * <code>maxRequestSize</code>, or any <code>Part</code> in the
     * request is larger than <code>maxFileSize</code>, or there is no
     * <code>@MultipartConfig</code> or <code>multipart-config</code> in
     * deployment descriptors
     *
     * @see javax.servlet.annotation.MultipartConfig#maxFileSize
     * @see javax.servlet.annotation.MultipartConfig#maxRequestSize
     *
     * @since Servlet 3.0
     */
    public Part getPart(String name) throws IOException, ServletException;

    /**
     * Creates an instance of <code>HttpUpgradeHandler</code> for a given
     * class and uses it for the http protocol upgrade processing.
     *
     * @param <T> The {@code Class}, which extends {@link
     * HttpUpgradeHandler}, of the {@code handlerClass}.

     * @param handlerClass The <code>HttpUpgradeHandler</code> class used for the upgrade.
     *
     * @return an instance of the <code>HttpUpgradeHandler</code>
     *
     * @exception IOException if an I/O error occurred during the upgrade
     * @exception ServletException if the given <code>handlerClass</code> fails to
     * be instantiated
     *
     * @see javax.servlet.http.HttpUpgradeHandler
     * @see javax.servlet.http.WebConnection
     *
     * @since Servlet 3.1
     */
    public <T extends HttpUpgradeHandler> T  upgrade(Class<T> handlerClass)
        throws IOException, ServletException;

    /**
     * Get the request trailer fields.
     *
     * <p>The returned map is not backed by the {@code HttpServletRequest} object,
     * so changes in the returned map are not reflected in the
     * {@code HttpServletRequest} object, and vice-versa.</p>
     * 
     * <p>{@link #isTrailerFieldsReady()} should be called first to determine
     * if it is safe to call this method without causing an exception.</p>
     *
     * @implSpec
     * The default implementation returns an empty map.
     * 
     * @return A map of trailer fields in which all the keys are in lowercase,
     * regardless of the case they had at the protocol level. If there are no
     * trailer fields, yet {@link #isTrailerFieldsReady} is returning true,
     * the empty map is returned.
     *
     * @throws IllegalStateException if {@link #isTrailerFieldsReady()} is false
     *
     * @since Servlet 4.0
     */
    default public Map<String, String> getTrailerFields() {
        return Collections.emptyMap();
    }

    /**
     * Return a boolean indicating whether trailer fields are ready to read
     * using {@link #getTrailerFields}.
     *
     * This methods returns true immediately if it is known that there is no
     * trailer in the request, for instance, the underlying protocol (such
     * as HTTP 1.0) does not supports the trailer fields, or the request is
     * not in chunked encoding in HTTP 1.1.
     * And the method also returns true if both of the following conditions
     * are satisfied:
     * <ol type="a">
     *   <li> the application has read all the request data and an EOF
     *        indication has been returned from the {@link #getReader}
     *        or {@link #getInputStream}.
     *   <li> all the trailer fields sent by the client have been received.
     *        Note that it is possible that the client has sent no trailer fields.
     * </ol>
     *
     * @implSpec
     * The default implementation returns false.
     *
     * @return a boolean whether trailer fields are ready to read
     *
     * @since Servlet 4.0
     */
    default public boolean isTrailerFieldsReady() {
        return true;
    }
}
