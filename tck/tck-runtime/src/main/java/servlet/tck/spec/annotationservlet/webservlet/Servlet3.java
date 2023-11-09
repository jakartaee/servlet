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
 * $Id:$
 */
package servlet.tck.spec.annotationservlet.webservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/Servlet3URL", initParams = {
    @WebInitParam(name = "name1", value = "value1"),
    @WebInitParam(name = "name2", value = "value2")}, name = "Servlet3")
public class Servlet3 extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.write("Servlet3_INVOKED");
    pw.write("initParams: ");
    String name = null;
    String value = null;

    Enumeration names = getInitParameterNames();

    while (names.hasMoreElements()) {
      name = (String) names.nextElement();
      value = getInitParameter(name);
      pw.print(name + "=" + value);
    }

    pw.println("servletname=" + getServletName());
    pw.print("isAsyncSupported=" + request.isAsyncSupported());
  }
}
