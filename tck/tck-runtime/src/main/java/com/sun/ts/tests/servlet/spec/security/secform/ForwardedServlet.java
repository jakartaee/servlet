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

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForwardedServlet extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    // HttpSession session = request.getSession(true);
    // session.setAttribute("ForwardedServletAttribute", "100");
    // out.println("HttpSession Id :"+ session.getId());

    // Note: The request to ControlServlet was already forwarded
    // to ForwardedServlet
    out.println("\n\nContents From ForwardedServlet");
    out.println("Using RequestDispatcher's forward() method the request "
        + "for ControlServlet was forwarded to ForwardedServlet");
    out.println("From ForwardedServlet: getRemoteUser(): "
        + request.getRemoteUser() + "<BR>");
    out.println("From ForwardedServlet: isUserInRole(\"Administrator\"): !"
        + request.isUserInRole("Administrator") + "!<BR>");

    // Include "IncludedServlet" through request dispatcher's include() method
    out.println("\nIncluding the responses from IncludedServlet \n");

    String path = "/IncludedServlet";
    RequestDispatcher requestDispatcher = getServletContext()
        .getRequestDispatcher(path);
    requestDispatcher.include(request, response);

  }
}
