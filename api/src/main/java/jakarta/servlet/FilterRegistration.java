/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates and others.
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

package jakarta.servlet;

import java.util.*;

/**
 * Interface through which a {@link Filter} may be further configured.
 *
 * @since Servlet 3.0
 */
public interface FilterRegistration extends Registration {

    /**
     * Adds a filter mapping with the given servlet names and dispatcher types for the Filter represented by this
     * FilterRegistration.
     *
     * <p>
     * Filter mappings are matched in the order in which they were added.
     * 
     * <p>
     * Depending on the value of the <tt>isMatchAfter</tt> parameter, the given filter mapping will be considered after or
     * before any <i>declared</i> filter mappings of the ServletContext from which this FilterRegistration was obtained.
     *
     * <p>
     * If this method is called multiple times, each successive call adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping, or null if the default
     * <tt>DispatcherType.REQUEST</tt> is to be used
     * @param isMatchAfter true if the given filter mapping should be matched after any declared filter mappings, and false
     * if it is supposed to be matched before any declared filter mappings of the ServletContext from which this
     * FilterRegistration was obtained
     * @param servletNames the servlet names of the filter mapping
     *
     * @throws IllegalArgumentException if <tt>servletNames</tt> is null or empty
     * @throws IllegalStateException if the ServletContext from which this FilterRegistration was obtained has already been
     * initialized
     */
    public void addMappingForServletNames(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... servletNames);

    /**
     * Gets the currently available servlet name mappings of the Filter represented by this <code>FilterRegistration</code>.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently available servlet name mappings of the Filter
     * represented by this <code>FilterRegistration</code>
     */
    public Collection<String> getServletNameMappings();

    /**
     * Adds a filter mapping with the given url patterns and dispatcher types for the Filter represented by this
     * FilterRegistration.
     *
     * <p>
     * Filter mappings are matched in the order in which they were added.
     * 
     * <p>
     * Depending on the value of the <tt>isMatchAfter</tt> parameter, the given filter mapping will be considered after or
     * before any <i>declared</i> filter mappings of the ServletContext from which this FilterRegistration was obtained.
     *
     * <p>
     * If this method is called multiple times, each successive call adds to the effects of the former.
     *
     * @param dispatcherTypes the dispatcher types of the filter mapping, or null if the default
     * <tt>DispatcherType.REQUEST</tt> is to be used
     * @param isMatchAfter true if the given filter mapping should be matched after any declared filter mappings, and false
     * if it is supposed to be matched before any declared filter mappings of the ServletContext from which this
     * FilterRegistration was obtained
     * @param urlPatterns the url patterns of the filter mapping
     *
     * @throws IllegalArgumentException if <tt>urlPatterns</tt> is null or empty
     * @throws IllegalStateException if the ServletContext from which this FilterRegistration was obtained has already been
     * initialized
     */
    public void addMappingForUrlPatterns(EnumSet<DispatcherType> dispatcherTypes, boolean isMatchAfter,
            String... urlPatterns);

    /**
     * Gets the currently available URL pattern mappings of the Filter represented by this <code>FilterRegistration</code>.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>FilterRegistration</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the currently available URL pattern mappings of the Filter
     * represented by this <code>FilterRegistration</code>
     */
    public Collection<String> getUrlPatternMappings();

    /**
     * Interface through which a {@link Filter} registered via one of the <tt>addFilter</tt> methods on
     * {@link ServletContext} may be further configured.
     */
    interface Dynamic extends FilterRegistration, Registration.Dynamic {
    }
}
