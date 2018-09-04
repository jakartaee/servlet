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

package javax.servlet;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 *
 * <p>
 * Defines a generic, protocol-independent filter. To write an HTTP filter for use on the Web, extend
 * {@link javax.servlet.http.HttpFilter} instead.
 * </p>
 *
 * <p>
 * <code>GenericFilter</code> implements the <code>Filter</code> and <code>FilterConfig</code> interfaces.
 * <code>GenericFilter</code> may be directly extended by a filter, although it's more common to extend a
 * protocol-specific subclass such as <code>HttpFilter</code>.
 *
 * <p>
 * <code>GenericFilter</code> makes writing filters easier. It provides simple versions of the lifecycle methods
 * <code>init</code> and <code>destroy</code> and of the methods in the <code>FilterConfig</code> interface.
 *
 * <p>
 * To write a generic filter, you need only override the abstract <code>doFilter</code> method.
 *
 * @author Various
 * 
 * @since Servlet 4.0
 */
public abstract class GenericFilter implements Filter, FilterConfig, java.io.Serializable {
    private static final long serialVersionUID = 4060116231031076581L;

    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static final ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    private transient FilterConfig config;

    /**
     *
     * <p>
     * Does nothing. All of the filter initialization is done by one of the <code>init</code> methods.
     * </p>
     *
     * @since Servlet 4.0
     */
    public GenericFilter() {
    }

    /**
     * <p>
     * Returns a <code>String</code> containing the value of the named initialization parameter, or <code>null</code> if
     * the parameter does not exist. See {@link FilterConfig#getInitParameter}.
     * </p>
     *
     * <p>
     * This method is supplied for convenience. It gets the value of the named parameter from the servlet's
     * <code>ServletConfig</code> object.
     *
     * @param name a <code>String</code> specifying the name of the initialization parameter
     *
     * @return String a <code>String</code> containing the value of the initialization parameter
     *
     * @since Servlet 4.0
     *
     */
    @Override
    public String getInitParameter(String name) {
        FilterConfig fc = getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }

        return fc.getInitParameter(name);
    }

    /**
     * <p>
     * Returns the names of the filter's initialization parameters as an <code>Enumeration</code> of <code>String</code>
     * objects, or an empty <code>Enumeration</code> if the filter has no initialization parameters. See
     * {@link FilterConfig#getInitParameterNames}.
     * </p>
     *
     * <p>
     * This method is supplied for convenience. It gets the parameter names from the filter's <code>FilterConfig</code>
     * object.
     *
     * @return Enumeration an enumeration of <code>String</code> objects containing the names of the filter's
     *         initialization parameters
     *
     * @since Servlet 4.0
     */
    @Override
    public Enumeration<String> getInitParameterNames() {
        FilterConfig fc = getFilterConfig();
        if (fc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }

        return fc.getInitParameterNames();
    }

    /**
     * <p>
     * Returns this servlet's {@link ServletConfig} object.
     * </p>
     *
     * @return FilterConfig the <code>FilterConfig</code> object that initialized this filter
     *
     * @since Servlet 4.0
     */
    public FilterConfig getFilterConfig() {
        return config;
    }

    /**
     * <p>
     * Returns a reference to the {@link ServletContext} in which this filter is running. See
     * {@link FilterConfig#getServletContext}.
     * </p>
     *
     * <p>
     * This method is supplied for convenience. It gets the context from the filter's <code>FilterConfig</code> object.
     *
     * @return ServletContext the <code>ServletContext</code> object passed to this filter by the <code>init</code>
     *         method
     *
     * @since Servlet 4.0
     */
    @Override
    public ServletContext getServletContext() {
        FilterConfig sc = getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.filter_config_not_initialized"));
        }

        return sc.getServletContext();
    }

    /**
     * <p>
     * Called by the servlet container to indicate to a filter that it is being placed into service. See
     * {@link Filter#init}.
     * </p>
     * 
     * <p>
     * This implementation stores the {@link FilterConfig} object it receives from the servlet container for later use.
     * When overriding this form of the method, call <code>super.init(config)</code>.
     * 
     * @param config the <code>FilterConfig</code> object that contains configuration information for this filter
     *
     * @exception ServletException if an exception occurs that interrupts the servlet's normal operation
     * 
     * @see UnavailableException
     *
     * @since Servlet 4.0
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        this.init();
    }

    /**
     * <p>
     * A convenience method which can be overridden so that there's no need to call <code>super.init(config)</code>.
     * </p>
     *
     * <p>
     * Instead of overriding {@link #init(FilterConfig)}, simply override this method and it will be called by
     * <code>GenericFilter.init(FilterConfig config)</code>. The <code>FilterConfig</code> object can still be retrieved
     * via {@link #getFilterConfig}.
     * 
     * @exception ServletException if an exception occurs that interrupts the servlet's normal operation
     *
     * @since Servlet 4.0
     */
    public void init() throws ServletException {

    }

    /**
     * <p>
     * Returns the name of this filter instance. See {@link FilterConfig#getFilterName}.
     * </p>
     *
     * @return the name of this filter instance
     *
     * @since Servlet 4.0
     */
    @Override
    public String getFilterName() {
        FilterConfig sc = getFilterConfig();
        if (sc == null) {
            throw new IllegalStateException(lStrings.getString("err.servlet_config_not_initialized"));
        }

        return sc.getFilterName();
    }
}
