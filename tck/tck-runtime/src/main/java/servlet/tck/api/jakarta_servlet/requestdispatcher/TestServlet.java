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
package servlet.tck.api.jakarta_servlet.requestdispatcher;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  /**
   * Test for forward(ServletRequest,ServletResponse) method
   */
  public void forwardTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    String path = "/ForwardedServlet";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.forward(request, response);
  }

  public void forward_1Test(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    // response is committed here
    pw.println("Committing some content to buffer");
    pw.flush();
    String path = "/ForwardedServlet";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    try {
      rd.forward(request, response);
      passed = false;
      pw.println("IllegalStateException should have been generated");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Test for Include(ServletRequest,ServletResponse) method
   */
  public void includeTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    String path = "/ForwardedServlet";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.include(request, response);
  }

  /**
   * The included servlet is not supposed to change the Response Headers. Our
   * included servlet changes it we will check whether that changed header value
   * gets reflected in the client side or not
   */
  public void include_1Test(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/sgml");
    String path = "/IncludedServlet";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.include(request, response);
  }

  public void include_2Test(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    String path = "/ForwardedServlet2";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    try {
      rd.include(request, response);
      passed = false;
      pw.println("ServletException should have been generated");
    } catch (Throwable t) {
      if (t instanceof ServletException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of ServletException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void include_3Test(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    String path = "/ForwardedServlet3";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    try {
      rd.include(request, response);
      passed = false;
      pw.println("IOException should have been generated");
    } catch (Throwable t) {
      if (t instanceof IOException) {
        passed = true;
      } else {
        passed = false;
        pw.println("Exception thrown, but was not an instance of IOException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
