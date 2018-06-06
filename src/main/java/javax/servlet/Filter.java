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

package javax.servlet;

import java.io.IOException;

/**
 * <p>A filter is an object that performs 
 * filtering tasks on either the request to a resource (a servlet or static content), or on the response
 * from a resource, or both.</p>
 * 
 * <p>Filters perform filtering in the <code>doFilter</code> method.
 * Every Filter has access to a FilterConfig object from which it can obtain
 * its initialization parameters, and a reference to the ServletContext which
 * it can use, for example, to load resources needed for filtering tasks.
 *
 * <p>Filters are configured in the deployment descriptor of a web
 * application.
 *
 * <p>Examples that have been identified for this design are:
 * <ol>
 * <li>Authentication Filters
 * <li>Logging and Auditing Filters
 * <li>Image conversion Filters
 * <li>Data compression Filters
 * <li>Encryption Filters
 * <li>Tokenizing Filters
 * <li>Filters that trigger resource access events
 * <li>XSL/T filters
 * <li>Mime-type chain Filter
 * </ol>
 *
 * @since Servlet 2.3
 */

public interface Filter {

    /** 
     * <p>Called by the web container
     * to indicate to a filter that it is being placed into service.</p>
     *
     * <p>The servlet container calls the init
     * method exactly once after instantiating the filter. The init
     * method must complete successfully before the filter is asked to do any
     * filtering work.</p>
     * 
     * <p>The web container cannot place the filter into service if the init
     * method either</p>
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     * 
     * @implSpec
     * The default implementation takes no action.
     *
     * @param filterConfig a <code>FilterConfig</code> object containing the
     *                     filter's configuration and initialization parameters 
     * @throws ServletException if an exception has occurred that interferes with
     *                          the filter's normal operation
     */
    default public void init(FilterConfig filterConfig) throws ServletException {}
	
	
    /**
     * The <code>doFilter</code> method of the Filter is called by the
     * container each time a request/response pair is passed through the
     * chain due to a client request for a resource at the end of the chain.
     * The FilterChain passed in to this method allows the Filter to pass
     * on the request and response to the next entity in the chain.
     *
     * <p>A typical implementation of this method would follow the following
     * pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain
     * using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to
     * the next entity in the filter chain to
     * block the request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the
     * next entity in the filter chain.
     * </ol>
     *
     * @param request the <code>ServletRequest</code> object contains the client's request
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the
     *                          filter's normal operation
     *
     * @see UnavailableException
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException;


    /**
     * <p>Called by the web container 
     * to indicate to a filter that it is being
     * taken out of service.</p>
     *
     * <p>This method is only called once all threads within the filter's
     * doFilter method have exited or after a timeout period has passed.
     * After the web container calls this method, it will not call the
     * doFilter method again on this instance of the filter.</p>
     *
     * <p>This method gives the filter an opportunity to clean up any
     * resources that are being held (for example, memory, file handles,
     * threads) and make sure that any persistent state is synchronized
     * with the filter's current state in memory.</p>
     * 
     * @implSpec
     * The default implementation takes no action.
     */
    default public void destroy() {}
}
