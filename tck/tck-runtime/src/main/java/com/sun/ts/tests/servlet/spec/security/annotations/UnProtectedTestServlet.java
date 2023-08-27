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
 * $Id: UnProtectedTestServlet.java 52684 2007-02-12 04:30:10Z lschwenk $
 */

package com.sun.ts.tests.servlet.spec.security.annotations;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * this sets PERMITALL on everything.
 */

@ServletSecurity
@WebServlet("/UnProtectedTest")
public class UnProtectedTestServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    if ((request != null) && (request.getUserPrincipal() == null)) {
      out.println(
          "The user principal is: " + request.getUserPrincipal() + "<BR>");
    } else {
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
    }

    if ((request != null) && (request.getUserPrincipal() != null)) {
      // Output whether the user is in any of the known or an unknown role.
      // Surround these with !'s so they are easier to search for.
      // (i.e. we can search for !true! or !false!)
      out.println(
          "isUserInRole(\"ADM\"): !" + request.isUserInRole("ADM") + "!<BR>");
      out.println(
          "isUserInRole(\"MGR\"): !" + request.isUserInRole("MGR") + "!<BR>");
      out.println(
          "isUserInRole(\"EMP\"): !" + request.isUserInRole("EMP") + "!<BR>");
    }

    if (request != null) {
      // Test getRemoteUser() functionality:
      out.println("<BR>");
      out.println("getRemoteUser(): " + request.getRemoteUser());
    } else {
      out.println("UnProtectedTestServlet.service() has null request");
    }
  }
}
