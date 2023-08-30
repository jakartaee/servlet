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

package servlet.tck.spec.security.metadatacomplete;

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
 * This servlet is declared to PERMIT all via annotation but is declared
 * to DenyAll via DD.  The DD is flagged with metadata-complete=true so 
 * the DD *must* take precedence over the annotation in here.  Thus
 * this servlet will be set to DENYALL because of the DD settings.
 */
@ServletSecurity(@HttpConstraint(EmptyRoleSemantic.PERMIT))
@WebServlet(name = "DenyAllTestLogicalName", urlPatterns = {
    "/ServletDenyAll" })
public class DenyAllServlet extends HttpServlet {

  //
  // this must be supported per Servlet 3.0 spec (section 13.4), bullet item 1.
  //
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();

    System.out.println("Inside  DenyAllServlet ....." + "<BR>");
    if ((request != null) && (request.getUserPrincipal() != null)) {
      System.out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      System.err.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      out.println("getRemoteUser(): " + request.getRemoteUser() + "<BR>");
    }
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, java.io.IOException {

    PrintWriter out = response.getWriter();

    if ((request != null) && (request.getUserPrincipal() != null)) {
      System.out.println("Inside  DenyAllServlet.doGet() ....." + "<BR>");
      System.out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      System.err.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
      out.println("getRemoteUser(): " + request.getRemoteUser() + "<BR>");
    } else {
      out.println(
          "DenyAllServlet.doGet() - could not get request.getUserPrincipal()");
    }
  }

}
