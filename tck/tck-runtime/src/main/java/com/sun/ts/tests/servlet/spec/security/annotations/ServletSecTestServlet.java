/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * $Id: ServletSecTestServlet.java 52684 2007-02-12 04:30:10Z lschwenk $
 */

package com.sun.ts.tests.servlet.spec.security.annotations;

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
 * this declares several roles, specifies which roles are allowed, then
 * sets POST to DENYALL and sets GET TO PERMITALL.
 */
@DeclareRoles({ "Administrator", "Manager", "Employee" })
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {
    "Administrator" }), httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", emptyRoleSemantic = EmptyRoleSemantic.DENY),
        @HttpMethodConstraint(value = "GET", emptyRoleSemantic = EmptyRoleSemantic.PERMIT) })
@WebServlet("/ServletSecTest")
public class ServletSecTestServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    System.out
        .println("Inside  ServletSecTestServlet.service()  ....." + "<BR>");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {

    PrintWriter out = response.getWriter();
    System.out
        .println("Inside  ServletSecTestServlet.doTrace() ....." + "<BR>");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {

    PrintWriter out = response.getWriter();
    System.out.println("Inside  ServletSecTestServlet.doGet() ....." + "<BR>");

    if ((request != null) && (request.getUserPrincipal() != null)) {
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
    } else {
      out.println("The user principal is: Null." + "<BR>");
    }

    if (request != null) {
      out.println(
          "isUserInRole(\"ADM\"): !" + request.isUserInRole("ADM") + "!<BR>");
      out.println(
          "isUserInRole(\"MGR\"): !" + request.isUserInRole("MGR") + "!<BR>");
      out.println(
          "isUserInRole(\"EMP\"): !" + request.isUserInRole("EMP") + "!<BR>");
      out.println("isUserInRole(\"Administrator\"): !"
          + request.isUserInRole("Administrator") + "!<BR>");
    }

  }

}
