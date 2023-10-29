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

package servlet.tck.api.jakarta_servlet_http.httpsessionx;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TestServlet extends HttpTCKServlet {
  int expectedResult = 10;

  public void getNewSession(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = true;
    request.getSession(true);
    ServletTestUtil.printResult(pw, passed);
  }

  public void getNewSessionx(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/getNewSession");

      rd.include(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }

  }

  public void setMaxInactiveIntervalTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = true;
    HttpSession session = request.getSession(false);
    session.setMaxInactiveInterval(expectedResult);
    ServletTestUtil.printResult(pw, passed);
  }

  public void getMaxInactiveIntervalTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    HttpSession session = request.getSession(false);
    int result = session.getMaxInactiveInterval();

    if (result != expectedResult) {
      passed = false;
      pw.println("getMaxInactiveInterval() returned incorrect result ");
      pw.println("Expected result = " + expectedResult + " ");
      pw.println("Actual result = |" + result + "| ");
    } else {
      pw.println("getMaxInactiveInterval() returned correct result " + result);
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void expireHttpSessionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    HttpSession session = request.getSession(false);

    if (session != null) {
      passed = false;
      pw.println("From TestServlet: Session is not expired ");
    } else {
      pw.println("From TestServlet: Session is expired as expected");
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setMaxInactiveIntervalxiTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/setMaxInterval");

      rd.include(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }
  }

  public void setMaxInactiveIntervalxfTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/setMaxInterval");
      rd.forward(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }
  }

  public void expireHttpSessionxriTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/expireHttpSession");

      rd.include(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }
  }

  public void expireHttpSessionxrfTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/expireHttpSession");

      rd.forward(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }
  }

  public void invalidateSessionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    HttpSession session = request.getSession(false);
    session.invalidate();

    // session is invalidated. The method should return false.
    if (!request.isRequestedSessionIdValid()) {
      passed = true;
      pw.println("From TestServlet: Session is invalidated ");
    } else {
      passed = false;
      pw.println("From TestServlet: Session is not invalidated ");
    }

    ServletTestUtil.printResult(pw, passed);
  }

  public void invalidateSessionxTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    ServletContext servletContext = getServletContext()
        .getContext("/servlet_jsh_httpsessionx2_web");
    if(servletContext!=null || ServletTestUtil.SUPPORT_CROSS_CONTEXT) {
      RequestDispatcher rd = servletContext
              .getRequestDispatcher("/invalidateHttpSession");

      rd.include(request, response);
    } else {
      response.getWriter().println("Test PASSED");
    }
  }
}
