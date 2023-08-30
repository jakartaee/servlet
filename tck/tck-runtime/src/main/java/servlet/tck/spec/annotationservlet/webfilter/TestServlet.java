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
package servlet.tck.spec.annotationservlet.webfilter;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void forward1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/Servlet1";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      request.setAttribute("from", "TestServlet");
      rd.forward(request, response);
    }
  }

  public void include1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/Servlet1";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      request.setAttribute("from", "TestServlet");
      rd.include(request, response);
    }
    ServletTestUtil.printResult(pw, true);
  }
}
