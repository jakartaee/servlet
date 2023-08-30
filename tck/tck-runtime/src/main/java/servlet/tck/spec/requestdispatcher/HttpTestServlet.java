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

package servlet.tck.spec.requestdispatcher;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.HttpTCKServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpTestServlet extends HttpTCKServlet {

  public void getRequestURIIncludeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "/servlet_spec_requestdispatcher_web/TestServlet";

    String actual = request.getRequestURI();

    if (actual != null) {
      if (actual.equals(expectValue))
        pw.println(
            "Test PASSED from getRequestURIIncludeTest in HttpTestServlet");
      else
        pw.println(
            "Test FAILED - getRequestURI return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getRequestURI return incorrect null value");
  }

  public void getRequestURIForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "/servlet_spec_requestdispatcher_web/other/HttpTestServlet";

    String actual = request.getRequestURI();

    if (actual != null) {
      if (actual.equals(expectValue))
        pw.println(
            "Test PASSED from getRequestURIForwardTest in HttpTestServlet");
      else
        pw.println(
            "Test FAILED - getRequestURI return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getRequestURI return incorrect null value");
  }

  public void getRequestURLIncludeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "/servlet_spec_requestdispatcher_web/TestServlet";

    StringBuffer actual = request.getRequestURL();

    if (actual != null) {
      if (actual.indexOf(expectValue) != -1)
        if (actual.toString().toUpperCase().startsWith("HTTP://"))
          pw.println(
              "Test PASSED from getRequestURLIncludeTest in HttpTestServlet");
        else
          pw.println("Test FAILED - getRequestURL does not start with http://: "
              + actual);
      else
        pw.println(
            "Test FAILED - getRequestURL return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getRequestURL return incorrect null value");
  }

  public void getRequestURLForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "/servlet_spec_requestdispatcher_web/other/HttpTestServlet";

    StringBuffer actual = request.getRequestURL();

    if (actual != null) {
      if (actual.indexOf(expectValue) != -1)
        if (actual.toString().toUpperCase().startsWith("HTTP://"))
          pw.println(
              "Test PASSED from getRequestURLForwardTest in HttpTestServlet");
        else
          pw.println("Test FAILED - getRequestURL does not start with http://: "
              + actual);
      else
        pw.println(
            "Test FAILED - getRequestURL return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getRequestURL return incorrect null value");
  }

  public void getQueryStringTestForward(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "testname=getQueryStringTestForward";

    String actual = request.getQueryString();

    if (actual != null) {
      if (actual.equals(expectValue))
        pw.println(
            "Test PASSED from getQueryStringForwardTest in HttpTestServlet");
      else
        pw.println(
            "Test FAILED - getQueryString return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getQueryString return incorrect null value");
  }

  public void getQueryStringTestInclude(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String expectValue = "testname=getQueryStringIncludeTest";

    String actual = request.getQueryString();

    if (actual != null) {
      if (actual.equals(expectValue))
        pw.println(
            "Test PASSED from getQueryStringIncludeTest in HttpTestServlet");
      else
        pw.println(
            "Test FAILED - getQueryString return incorrect value: " + actual);
    } else
      pw.println("Test FAILED - getQueryString return incorrect null value");
  }
}
