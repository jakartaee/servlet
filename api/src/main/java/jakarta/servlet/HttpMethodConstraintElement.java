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

import jakarta.servlet.annotation.HttpMethodConstraint;

/**
 * Java Class represntation of an {@link HttpMethodConstraint} annotation value.
 *
 * @since Servlet 3.0
 */
public class HttpMethodConstraintElement extends HttpConstraintElement {

    private String methodName;

    /**
     * Constructs an instance with default {@link HttpConstraintElement} value.
     *
     * @param methodName the name of an HTTP protocol method. The name must not be null, or the empty string, and must be a
     * legitimate HTTP Method name as defined by RFC 2616
     */
    public HttpMethodConstraintElement(String methodName) {
        if (methodName == null || methodName.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = methodName;
    }

    /**
     * Constructs an instance with specified {@link HttpConstraintElement} value.
     *
     * @param methodName the name of an HTTP protocol method. The name must not be null, or the empty string, and must be a
     * legitimate HTTP Method name as defined by RFC 2616
     *
     * @param constraint the HTTPconstraintElement value to assign to the named HTTP method
     */
    public HttpMethodConstraintElement(String methodName, HttpConstraintElement constraint) {
        super(constraint.getEmptyRoleSemantic(), constraint.getTransportGuarantee(), constraint.getRolesAllowed());
        if (methodName == null || methodName.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = methodName;
    }

    /**
     * Gets the HTTP method name.
     *
     * @return the Http method name
     */
    public String getMethodName() {
        return this.methodName;
    }
}
