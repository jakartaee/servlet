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

package servlet.tck.spec.security.secform;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.annotation.security.DeclareRoles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This should allow the Aministrator role perms to access any of the servlet
 * methods.  This is an annotation based equivalent of ServletSecTestServlet 
 * with the main difference being that this uses Servlet based annotations whereas
 * ServletSecTestServlet uses DD for configuring its security constraints.
 */

@DeclareRoles({"ADM", "MGR", "EMP"})
@ServletSecurity(value = @HttpConstraint(EmptyRoleSemantic.DENY), httpMethodConstraints = {
    @HttpMethodConstraint(value = "GET", rolesAllowed = "Administrator"),
    @HttpMethodConstraint(value = "POST", rolesAllowed = "Administrator")})
@WebServlet(name = "ServletSecAnnoTestLogicalName", urlPatterns = {
    "/ServletSecAnnoTest"})
public class ServletSecAnnoTestServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    System.out.println("Inside  ServletSecAnnoTestServlet ....." + "<BR>");
    System.out.println("The user principal is: "
        + request.getUserPrincipal().getName() + "<BR>");
    System.err.println("The user principal is: "
        + request.getUserPrincipal().getName() + "<BR>");

    out.println("The user principal is: " + request.getUserPrincipal().getName()
        + "<BR>");
    out.println("getRemoteUser(): " + request.getRemoteUser() + "<BR>");

    out.println("Inside  ServletSecAnnoTestServlet ....." + "<BR>"); // used for
                                                                     // validating
                                                                     // test7

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
    out.println("isUserInRole(\"Administrator\"): !"
        + request.isUserInRole("Administrator") + "!<BR>");
  }
}
