/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */

package com.sun.ts.tests.servlet.spec.security.secform;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * We have an annotation here that is trying to disable access to
 * all HTTP methods.  Normally, this would work *EXCEPT* for in this
 * case, the DD file declares something completely different for the
 * exact same context, and the DD overrides annotations in case of 
 * duplicate contexts.  Do this annotation should be ignored and 
 * any attempts to access SampleTest must adhere to the DD definitions.
 *
 */
@ServletSecurity(@HttpConstraint(EmptyRoleSemantic.DENY))
@WebServlet(name = "SampleTestServletLogicalName", urlPatterns = {
    "/SampleTest" })
public class SampleTestServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    out.println("The user principal is: " + request.getUserPrincipal().getName()
        + " <BR>");
    out.println("getRemoteUser(): " + request.getRemoteUser() + "<BR>");

    out.println("isUserInRole(\"Administrator\"): !"
        + request.isUserInRole("Administrator") + "!<BR>");

    out.println("HTTP method " + request.getMethod() + " invoked");

  }
}
