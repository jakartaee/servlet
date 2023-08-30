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

package servlet.tck.spec.multifiltermapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void forwardTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    String param = "parameter1";
    PrintWriter pw = response.getWriter();
    String path = request.getParameter(param);
    pw.println("path to access: " + path);
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.forward(request, response);
  }

  public void includeTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    String param = "parameter1";
    PrintWriter pw = response.getWriter();
    String path = request.getParameter(param);
    pw.println("path to access: " + path);
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    try {
      rd.include(request, response);
    } catch (IOException ex) {
      pw.println("Resource " + path + " not available");
      throw new FileNotFoundException("Resource " + path + " not available");
    }
  }
}
