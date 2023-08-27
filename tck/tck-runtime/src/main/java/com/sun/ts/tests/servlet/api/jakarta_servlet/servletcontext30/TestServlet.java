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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;
import com.sun.ts.tests.servlet.common.util.StaticLog;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.SessionTrackingMode;

public class TestServlet extends GenericTCKServlet {

  public void getContextPathTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    try {
      String tmp = context.getContextPath();
      if (tmp != null) {
        pw.println("ServletContextPath=" + tmp);
        if (tmp.equals("/servlet_js_servletcontext30_web")) {
          passed = true;
        } else {
          passed = false;
          pw.println("Incorrect value returned");
        }
      } else {
        passed = false;
        pw.println("Null value returned for context path");
      }
    } catch (Exception ex) {
      passed = false;
      pw.println("Got Exception when calling getContextPath()");
    }

    sendtoclient(response);
  }

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
        "SCC_LISTENER_TEST", "SCS_LISTENER_TEST", "CSC_LISTENER_TEST",
        "DUPLICATEC_SERVLET_TEST", "DUPLICATES_SERVLET_TEST",
        "DUPLICATEC_FILTER_TEST", "DUPLICATES_FILTER_TEST" };

    for (String name : names) {
      pw.println(name + "="
          + getServletContext().getInitParameter(name).toUpperCase());
      getServletContext().removeAttribute(name);
    }

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  public void duplicateServletTest3(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String[] names = { "DUPLICATEC_SERVLET_TEST", "DUPLICATES_SERVLET_TEST" };

    for (String name : names) {
      pw.println(name + "="
          + getServletContext().getInitParameter(name).toUpperCase());
      getServletContext().removeAttribute(name);
    }

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  public void duplicateFilterTest1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String[] names = { "DUPLICATEC_FILTER_TEST", "DUPLICATES_FILTER_TEST" };

    for (String name : names) {
      pw.println(name + "="
          + getServletContext().getInitParameter(name).toUpperCase());
      getServletContext().removeAttribute(name);
    }

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  public void getEffectiveMajorVersionTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    ServletContext context = getServletContext();

    pw.println(
        "EFFECTIVEMAJORVERSION=" + context.getEffectiveMajorVersion() + ";");

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  public void getEffectiveMinorVersionTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    ServletContext context = getServletContext();

    pw.println(
        "EFFECTIVEMINORVERSION=" + context.getEffectiveMinorVersion() + ";");

    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }

  public void getDefaultSessionTrackingModes(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();
    ServletContext context = getServletContext();

    Set<SessionTrackingMode> results = context.getDefaultSessionTrackingModes();
    for (SessionTrackingMode tmp : results) {
      if (tmp == SessionTrackingMode.COOKIE || tmp == SessionTrackingMode.URL
          || tmp == SessionTrackingMode.SSL) {
        pw.println("getDefaultSessionTrackingModes=" + tmp + ";");
      } else {
        pass = false;
      }
    }

    ServletTestUtil.printResult(pw, pass);
    getServletContext().removeAttribute("arraylist");
  }

  public void sessionTrackingModesValueOfTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();

    if (!SessionTrackingMode.valueOf("COOKIE")
        .equals(SessionTrackingMode.COOKIE)) {
      pass = false;
      pw.println("String COOKIE didn't yield coorect SessionTrackingMode.");
    } else if (!SessionTrackingMode.valueOf("URL")
        .equals(SessionTrackingMode.URL)) {
      pass = false;
      pw.println("String URL didn't yield coorect SessionTrackingMode.");
    } else if (!SessionTrackingMode.valueOf("SSL")
        .equals(SessionTrackingMode.SSL)) {
      pass = false;
      pw.println("String SSL didn't yield coorect SessionTrackingMode.");
    }

    ServletTestUtil.printResult(pw, pass);
    getServletContext().removeAttribute("arraylist");
  }

  public void sessionTrackingModesValuesTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();

    SessionTrackingMode[] values_expected = new SessionTrackingMode[3];
    values_expected[0] = SessionTrackingMode.COOKIE;
    values_expected[1] = SessionTrackingMode.URL;
    values_expected[2] = SessionTrackingMode.SSL;

    int i = 0;
    SessionTrackingMode[] values_received = new SessionTrackingMode[3];

    for (SessionTrackingMode c : SessionTrackingMode.values()) {
      values_received[i] = c;
      i++;
    }

    for (i = 0; i < 3; i++) {
      if (!values_expected[i].equals(values_received[i])) {
        pass = false;
        pw.println("The number " + i + " SessionTrackingMode didn't match.");
        pw.println(
            "Expecting " + values_expected[i] + ".  Got " + values_received[i]);
      }
    }

    ServletTestUtil.printResult(pw, pass);
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
