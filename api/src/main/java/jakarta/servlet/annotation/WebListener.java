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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to declare a WebListener.
 *
 * Any class annotated with WebListener must implement one or more of the
 * {@link jakarta.servlet.ServletContextListener}, {@link jakarta.servlet.ServletContextAttributeListener},
 * {@link jakarta.servlet.ServletRequestListener}, {@link jakarta.servlet.ServletRequestAttributeListener},
 * {@link jakarta.servlet.http.HttpSessionListener}, or {@link jakarta.servlet.http.HttpSessionAttributeListener}, or
 * {@link jakarta.servlet.http.HttpSessionIdListener} interfaces.
 *
 * @since Servlet 3.0
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebListener {
    /**
     * Description of the listener
     *
     * @return description of the listener
     */
    String value() default "";
}
