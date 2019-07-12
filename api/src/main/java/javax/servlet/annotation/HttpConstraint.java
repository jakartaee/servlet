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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

/**
 * This annotation is used within the {@link ServletSecurity} annotation to
 * represent the security constraints to be applied to all HTTP protocol
 * methods for which a corresponding {@link HttpMethodConstraint} element does
 * NOT occur within the {@link ServletSecurity} annotation.
 *
 * <p>For the special case where an <code>@HttpConstraint</code> that returns
 * all default values occurs in combination with at least one
 * {@link HttpMethodConstraint} that returns other than all default values, the
 * <code>@HttpConstraint</code> represents that no security constraint is to be
 * applied to any of the HTTP protocol methods to which a security constraint
 * would otherwise apply. This exception is made to ensure that such
 * potentially non-specific uses of <code>@HttpConstraint</code> do not yield
 * constraints that will explicitly establish unprotected access for such
 * methods; given that they would not otherwise be covered by a constraint.
 *
 * @since Servlet 3.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpConstraint {

    /**
     * The default authorization semantic.
     * This value is insignificant when <code>rolesAllowed</code> returns a
     * non-empty array, and should not be specified when a non-empty
     * array is specified for <tt>rolesAllowed</tt>.
     *
     * @return the {@link EmptyRoleSemantic} to be applied when
     * <code>rolesAllowed</code> returns an empty (that is, zero-length) array.
     */
    EmptyRoleSemantic value() default EmptyRoleSemantic.PERMIT;

    /**
     * The data protection requirements (i.e., whether or not SSL/TLS is
     * required) that must be satisfied by the connections on which requests
     * arrive.
     * 
     * @return the {@link TransportGuarantee}
     * indicating the data protection that must be provided by the connection.
     */
    TransportGuarantee transportGuarantee() default TransportGuarantee.NONE;

    /**
     * The names of the authorized roles.
     *
     * Duplicate role names appearing in rolesAllowed are insignificant and
     * may be discarded during runtime processing of the annotation. The String
     * <tt>"*"</tt> has no special meaning as a role name (should it occur in
     * rolesAllowed).
     *
     * @return an array of zero or more role names. When the array contains
     * zero elements, its meaning depends on the <code>EmptyRoleSemantic</code>
     * returned by the <code>value</code> method. If <code>value</code> returns
     * <tt>DENY</tt>, and <code>rolesAllowed</code> returns a zero length array,
     * access is to be denied independent of authentication state and identity.
     * Conversely, if <code>value</code> returns <code>PERMIT</code>, it
     * indicates that access is to be allowed independent of authentication
     * state and identity. When the array contains the names of one or more
     * roles, it indicates that access is contingent on membership in at
     * least one of the named roles (independent of the
     * <code>EmptyRoleSemantic</code> returned by the <code>value</code> method).
     */
    String[] rolesAllowed() default {};
}
