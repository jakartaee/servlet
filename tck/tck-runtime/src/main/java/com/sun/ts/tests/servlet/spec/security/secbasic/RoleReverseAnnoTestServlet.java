/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.servlet.spec.security.secbasic;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.annotation.security.DeclareRoles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * This should require employee role perms to access the Get or Post
 * methods.  This should be an equivelent to RoleReverseTest with the
 * main difference being that this uses Servlet based annotations whereas
 * RoleReverseTest uses DD for configuring its security constraints.
 * It is worth noting that all methods (besides GET and POST) do NOT 
 * have any security constraints on them and so are unprotected and the
 * access to Get & POST requires you to be an employee role..
 * 
 */

@DeclareRoles({ "Administrator", "Manager", "VP", "Employee" })
@ServletSecurity(httpMethodConstraints = {
    @HttpMethodConstraint(value = "GET", rolesAllowed = "Employee"),
    @HttpMethodConstraint(value = "POST", rolesAllowed = "Employee") })
@WebServlet(name = "RoleReverseAnnoTestLogicalName", urlPatterns = {
    "/RoleReverseAnnoTest" })
public class RoleReverseAnnoTestServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    out.println("The user principal is: " + request.getUserPrincipal().getName()
        + "<BR>");

    // Output whether the user is in any of the known or an unknown role.
    // Surround these with !'s so they are easier to search for.
    // (i.e. we can search for !true! or !false!)
    out.println(
        "isUserInRole(\"ADM\"): !" + request.isUserInRole("ADM") + "!<BR>");
    out.println(
        "isUserInRole(\"MGR\"): !" + request.isUserInRole("MGR") + "!<BR>");
    out.println(
        "isUserInRole(\"VP\"): !" + request.isUserInRole("VP") + "!<BR>");
    out.println(
        "isUserInRole(\"EMP\"): !" + request.isUserInRole("EMP") + "!<BR>");
  }
}
