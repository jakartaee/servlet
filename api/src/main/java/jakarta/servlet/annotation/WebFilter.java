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

package jakarta.servlet.annotation;

import jakarta.servlet.DispatcherType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to declare a servlet filter.
 *
 * <p>
 * This annotation is processed by the container at deployment time, and the corresponding filter applied to the
 * specified URL patterns, servlets, and dispatcher types.
 *
 * @see jakarta.servlet.Filter
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFilter {

    /**
     * The description of the filter
     *
     * @return the description of the filter
     */
    String description() default "";

    /**
     * The display name of the filter
     *
     * @return the display name of the filter
     */
    String displayName() default "";

    /**
     * The init parameters of the filter
     *
     * @return the init parameters of the filter
     */
    WebInitParam[] initParams() default {};

    /**
     * The name of the filter
     *
     * @return the name of the filter
     */
    String filterName() default "";

    /**
     * The small-icon of the filter
     *
     * @return the small-icon of the filter
     */
    String smallIcon() default "";

    /**
     * The large-icon of the filter
     *
     * @return the large-icon of the filter
     */
    String largeIcon() default "";

    /**
     * The names of the servlets to which the filter applies.
     *
     * @return the names of the servlets to which the filter applies
     */
    String[] servletNames() default {};

    /**
     * The URL patterns to which the filter applies The default value is an empty array.
     *
     * @return the URL patterns to which the filter applies
     */
    String[] value() default {};

    /**
     * The URL patterns to which the filter applies
     *
     * @return the URL patterns to which the filter applies
     */
    String[] urlPatterns() default {};

    /**
     * The dispatcher types to which the filter applies
     *
     * @return the dispatcher types to which the filter applies
     */
    DispatcherType[] dispatcherTypes() default { DispatcherType.REQUEST };

    /**
     * Declares whether the filter supports asynchronous operation mode.
     *
     * @return {@code true} if the filter supports asynchronous operation mode
     * @see jakarta.servlet.ServletRequest#startAsync
     * @see jakarta.servlet.ServletRequest#startAsync( jakarta.servlet.ServletRequest,jakarta.servlet.ServletResponse)
     */
    boolean asyncSupported() default false;

}
