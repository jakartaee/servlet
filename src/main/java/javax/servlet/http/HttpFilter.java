/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates and others.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package javax.servlet.http;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * <p>
 * Provides an abstract class to be subclassed to create an HTTP filter suitable for a Web site. A subclass of
 * <code>HttpFilter</code> should override
 * {@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain) }.
 * </p>
 *
 * <p>
 * Filters typically run on multithreaded servers, so be aware that a filter must handle concurrent requests and be
 * careful to synchronize access to shared resources. Shared resources include in-memory data such as instance or class
 * variables and external objects such as files, database connections, and network connections. See the
 * <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/"> Java Tutorial on Multithreaded
 * Programming</a> for more information on handling multiple threads in a Java program.
 *
 * @author Various
 *
 * @since Servlet 4.0
 */

public abstract class HttpFilter extends GenericFilter {

    private static final long serialVersionUID = 7478463438252262094L;

    /**
     * <p>
     * Does nothing, because this is an abstract class.
     * </p>
     * 
     * @since 4.0
     */

    public HttpFilter() {
    }

    /**
     *
     * <p>
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is
     * passed through the chain due to a client request for a resource at the end of the chain. The FilterChain passed
     * in to this method allows the Filter to pass on the request and response to the next entity in the chain. There's
     * no need to override this method.
     * </p>
     * 
     * <p>
     * The default implementation inspects the incoming {@code req} and {@code res} objects to determine if they are
     * instances of {@link HttpServletRequest} and {@link HttpServletResponse}, respectively. If not, a
     * {@link ServletException} is thrown. Otherwise, the protected
     * {@link #doFilter(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)}
     * method is called.
     * </p>
     *
     * @param req   a {@link ServletRequest} object that contains the request the client has made of the filter
     *
     * @param res   a {@link ServletResponse} object that contains the response the filter sends to the client
     * 
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * 
     * @throws IOException      if an input or output error is detected when the filter handles the request
     *
     * @throws ServletException if the request for the could not be handled or either parameter is not an instance of
     *                          the respective {@link HttpServletRequest} or {@link HttpServletResponse}.
     *
     * @since Servlet 4.0
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!(req instanceof HttpServletRequest && res instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        this.doFilter((HttpServletRequest) req, (HttpServletResponse) res, chain);
    }

    /**
     *
     * <p>
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is
     * passed through the chain due to a client request for a resource at the end of the chain. The FilterChain passed
     * in to this method allows the Filter to pass on the request and response to the next entity in the chain.
     * </p>
     * 
     * <p>
     * The default implementation simply calls {@link FilterChain#doFilter}
     * </p>
     *
     * @param req   a {@link HttpServletRequest} object that contains the request the client has made of the filter
     *
     * @param res   a {@link HttpServletResponse} object that contains the response the filter sends to the client
     * 
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * 
     * @throws IOException      if an input or output error is detected when the filter handles the request
     *
     * @throws ServletException if the request for the could not be handled
     *
     * @since Servlet 4.0
     */
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(req, res);
    }

}
