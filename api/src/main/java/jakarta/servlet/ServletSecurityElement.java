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
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;

/**
 * Java Class representation of a {@link ServletSecurity} annotation value.
 *
 * @since Servlet 3.0
 */
public class ServletSecurityElement extends HttpConstraintElement {

    private Collection<String> methodNames;
    private Collection<HttpMethodConstraintElement> methodConstraints;

    /**
     * Constructs an instance using the default <code>HttpConstraintElement</code> value as the default Constraint element
     * and with no HTTP Method specific constraint elements.
     */
    public ServletSecurityElement() {
        methodConstraints = new HashSet<>();
        methodNames = Collections.emptySet();
    }

    /**
     * Constructs an instance with a default Constraint element and with no HTTP Method specific constraint elements.
     *
     * @param constraint the HttpConstraintElement to be applied to all HTTP methods other than those represented in the
     * <tt>methodConstraints</tt>
     */
    public ServletSecurityElement(HttpConstraintElement constraint) {
        super(constraint.getEmptyRoleSemantic(), constraint.getTransportGuarantee(), constraint.getRolesAllowed());
        methodConstraints = new HashSet<>();
        methodNames = Collections.emptySet();
    }

    /**
     * Constructs an instance using the default <code>HttpConstraintElement</code> value as the default Constraint element
     * and with a collection of HTTP Method specific constraint elements.
     *
     * @param methodConstraints the collection of HTTP method specific constraint elements
     *
     * @throws IllegalArgumentException if duplicate method names are detected
     */
    public ServletSecurityElement(Collection<HttpMethodConstraintElement> methodConstraints) {
        this.methodConstraints = (methodConstraints == null ? new HashSet<>() : methodConstraints);
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * Constructs an instance with a default Constraint element and with a collection of HTTP Method specific constraint
     * elements.
     *
     * @param constraint the HttpConstraintElement to be applied to all HTTP methods other than those represented in the
     * <tt>methodConstraints</tt>
     * @param methodConstraints the collection of HTTP method specific constraint elements.
     *
     * @throws IllegalArgumentException if duplicate method names are detected
     */
    public ServletSecurityElement(HttpConstraintElement constraint,
            Collection<HttpMethodConstraintElement> methodConstraints) {
        super(constraint.getEmptyRoleSemantic(), constraint.getTransportGuarantee(), constraint.getRolesAllowed());
        this.methodConstraints = (methodConstraints == null ? new HashSet<>() : methodConstraints);
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * Constructs an instance from a {@link ServletSecurity} annotation value.
     *
     * @param annotation the annotation value
     *
     * @throws IllegalArgumentException if duplicate method names are detected
     */
    public ServletSecurityElement(ServletSecurity annotation) {
        super(annotation.value().value(), annotation.value().transportGuarantee(), annotation.value().rolesAllowed());
        this.methodConstraints = new HashSet<>();
        for (HttpMethodConstraint constraint : annotation.httpMethodConstraints()) {
            this.methodConstraints.add(new HttpMethodConstraintElement(constraint.value(), new HttpConstraintElement(
                    constraint.emptyRoleSemantic(), constraint.transportGuarantee(), constraint.rolesAllowed())));
        }
        methodNames = checkMethodNames(this.methodConstraints);
    }

    /**
     * Gets the (possibly empty) collection of HTTP Method specific constraint elements.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>ServletSecurityElement</code>.
     *
     *
     * @return the (possibly empty) collection of HttpMethodConstraintElement objects
     */
    public Collection<HttpMethodConstraintElement> getHttpMethodConstraints() {
        return Collections.unmodifiableCollection(methodConstraints);
    }

    /**
     * Gets the set of HTTP method names named by the HttpMethodConstraints.
     *
     * <p>
     * If permitted, any changes to the returned <code>Collection</code> must not affect this
     * <code>ServletSecurityElement</code>.
     *
     *
     * 
     * @return the collection String method names
     */
    public Collection<String> getMethodNames() {
        return Collections.unmodifiableCollection(methodNames);
    }

    /**
     * Checks for duplicate method names in methodConstraints.
     *
     * @param methodConstraints
     *
     * @retrun Set of method names
     *
     * @throws IllegalArgumentException if duplicate method names are detected
     */
    private Collection<String> checkMethodNames(Collection<HttpMethodConstraintElement> methodConstraints) {
        Collection<String> methodNames = new HashSet<>();
        for (HttpMethodConstraintElement methodConstraint : methodConstraints) {
            String methodName = methodConstraint.getMethodName();
            if (!methodNames.add(methodName)) {
                throw new IllegalArgumentException("Duplicate HTTP method name: " + methodName);
            }
        }
        return methodNames;
    }
}
