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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletrequest30;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void getServletContextTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    ServletContext actual = request.getServletContext();
    ServletContext expected = this.getServletConfig().getServletContext();

    if (actual != expected) {
      response.getWriter().println(
          "getServletContext() returned inconsistent result. Test FAILED.");
    } else {
      response.getWriter().println("Test PASSED.");
    }
  }

  public void getDispatcherTypeTestRequest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
  }

  public void getDispatcherTypeTestForward(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    String path = "/forward/ForwardedServlet?testname=getDispatcherTypeTest";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null) {
      response.getWriter()
          .println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }

  }

  public void getDispatcherTypeTestInclude(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    String path = "/include/IncludedServlet?testname=getDispatcherTypeTest";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null) {
      response.getWriter()
          .println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.include(request, response);
    }
  }

  public void isAsyncSupportedTest(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter()
        .println("isAsyncSupported=" + request.isAsyncSupported());
  }

  public void startAsyncTest(ServletRequest request, ServletResponse response)
      throws IOException {
    try {
      AsyncContext startAsync = request.startAsync();
      response.getWriter()
          .println("Expected IllegalStateException not thrown. Test FAILED.");
    } catch (IllegalStateException ise) {
      response.getWriter()
          .println("Expected IllegalStateException thrown. Test PASSED.");
    }
  }
}
