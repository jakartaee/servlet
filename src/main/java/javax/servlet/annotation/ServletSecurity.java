/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package javax.servlet.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used on a Servlet implementation class to specify security constraints to be enforced by a Servlet
 * container on HTTP protocol messages. The Servlet container will enforce these constraints on the url-patterns mapped
 * to the servlets mapped to the annotated class.
 *
 * @since Servlet 3.0
 */

@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServletSecurity {

    /**
     * Defines the access semantic to be applied to an empty rolesAllowed array.
     */
    enum EmptyRoleSemantic {
    /**
     * access is to be permitted independent of authentication state and identity.
     */
    PERMIT,
    /**
     * access is to be denied independent of authentication state and identity.
     */
    DENY
    }

    /**
     * Defines the data protection requirements that must be satisfied by the transport
     */
    enum TransportGuarantee {
        /**
         * no protection of user data must be performed by the transport.
         */
        NONE,
        /**
         * All user data must be encrypted by the transport (typically using SSL/TLS).
         */
        CONFIDENTIAL
    }

    /**
     * Get the {@link HttpConstraint} that defines the protection that is to be applied to all HTTP methods that are NOT
     * represented in the array returned by <tt>httpMethodConstraints</tt>.
     *
     * @return a <code>HttpConstraint</code> object.
     */
    HttpConstraint value() default @HttpConstraint;

    /**
     * Get the HTTP method specific constraints. Each {@link HttpMethodConstraint} names an HTTP protocol method and
     * defines the protection to be applied to it.
     *
     * @return an array of {@link HttpMethodConstraint} elements each defining the protection to be applied to one HTTP
     *         protocol method. For any HTTP method name, there must be at most one corresponding element in the
     *         returned array. If the returned array is of zero length, it indicates that no HTTP method specific
     *         constraints are defined.
     */

    HttpMethodConstraint[] httpMethodConstraints() default {};
}
