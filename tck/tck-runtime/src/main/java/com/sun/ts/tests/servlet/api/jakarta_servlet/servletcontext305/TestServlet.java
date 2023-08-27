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

/*
 * $Id:$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext305;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;
import com.sun.ts.tests.servlet.common.util.StaticLog;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void testAddFilterString(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    String path = "/addServletString";

    try {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      rd.forward(request, response);
    } catch (Exception ex) {
      response.getWriter()
          .println("Got Exception in testAddFilterString: " + ex.getMessage());
    }
    response.flushBuffer();
    getServletContext().removeAttribute("arraylist");
  }

  public void testAddFilterClass(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    String path = "/SecondaddServletClass";

    try {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      rd.include(request, response);
    } catch (Exception ex) {
      passed = false;
      pw.println("Got Exception in testAddFilterClass: " + ex.getMessage());
    }

    sendtoclient(response);
  }

  public void testCreateFilterForward(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    String path = "/SecondCreateServlet";

    try {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      rd.forward(request, response);
    } catch (Exception ex) {
      passed = false;
      pw.println(
          "Got Exception in testCreateFilterForward: " + ex.getMessage());
    }
    ServletTestUtil.printResult(pw, passed);
    getServletContext().removeAttribute("arraylist");
  }

  public void testCreateFilterInclude(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    String path = "/ThirdCreateServlet";

    try {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      rd.include(request, response);
    } catch (Exception ex) {
      passed = false;
      pw.println(
          "Got Exception in testCreateFilterInclude: " + ex.getMessage());
    }

    sendtoclient(response);
  }

  public void testCreateSRAListener(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    String path = "/ThirdAddServletClass";

    request.setAttribute("TestCreateSRAListener", "See_what_happens");

    try {
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      rd.include(request, response);
    } catch (Exception ex) {
      response.getWriter().println(
          "Got Exception in TestCreateSRAListener: " + ex.getMessage());
    }
    sendtoclient(response);
  }

  public void negativeCreateTests(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String[] names = { "SERVLET_TEST", "FILTER_TEST", "LISTENER_TEST",
        "GC_LISTENER_TEST", "GS_LISTENER_TEST", "CGC_LISTENER_TEST" };

    for (String name : names) {
      pw.println(name + "="
          + getServletContext().getInitParameter(name).toUpperCase());
      getServletContext().removeAttribute(name);
    }

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  private void sendtoclient(ServletResponse response) throws IOException {
    PrintWriter pw = response.getWriter();
    ArrayList result = (ArrayList) getServletContext()
        .getAttribute("arraylist");

    if (result != null) {
      for (Object tmp : result) {
        if (tmp != null) {
          pw.println(tmp.toString());
        }
      }
    }
    getServletContext().removeAttribute("arraylist");

    result = StaticLog.getClear();
    if (result != null) {
      for (Object tmp : result) {
        if (tmp != null) {
          pw.println(tmp.toString());
        }
      }
    }
    StaticLog.clear();

    ServletTestUtil.printResult(pw, true);
  }
}
