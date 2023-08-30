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
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * The DD is using metadata-complete=true which means that the DD
 * security  constraints will get used and the annotation defined
 * security constraints will be ignored.
 * 
 * Using annotations, this servlet declares roles and then deny's all as a 
 * default, however GET is specified with a RolesAllowed="Administrator".
 * There is a duplicate Servlet url/context defined in the DD - which means
 * that the DD should override the annotations defined in this class.
 * The DD indicates that role Manager (user=javajoe) has perm to do GET/POST
 * requess.  This will take precedence over the annotations of this class which
 * state that role Administrator (user=j2ee) can issue GET requests.  
 */

@ServletSecurity(value = @HttpConstraint(EmptyRoleSemantic.DENY), httpMethodConstraints = {
    @HttpMethodConstraint(value = "GET", rolesAllowed = "Administrator") })
@WebServlet(name = "GuestPageTestLogicalName", urlPatterns = {
    "/GuestPageTest" })
public class GuestPageTestServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    if ((request != null) && (request.getUserPrincipal() != null)) {
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
    } else {
      out.println(
          "Could not get principal via request.getUserPrincipal() " + "<BR>");
    }

  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    if ((request != null) && (request.getUserPrincipal() != null)) {
      out.println("The user principal is: "
          + request.getUserPrincipal().getName() + "<BR>");
    } else {
      out.println(
          "Could not get principal via request.getUserPrincipal() " + "<BR>");
    }

  }

}
