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
 * This annotation essentially sets Deny all access on the servlet.  However, our
 * DD is set with metadata-complete=true so the DD will cause any annotations
 * in this servlet to be ignored and the DD settings currently leave the 
 * servlet unprotected so a Permit all should ultmiately be set on this servlet.
 */
@ServletSecurity(@HttpConstraint(EmptyRoleSemantic.DENY))
@WebServlet(name = "UnProtectedTestLogicalName", urlPatterns = {
    "/UnProtectedTest" })
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
  }
}
