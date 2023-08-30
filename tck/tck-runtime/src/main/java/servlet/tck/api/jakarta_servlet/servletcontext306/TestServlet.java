/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.servletcontext306;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateServlet;

public class TestServlet extends GenericTCKServlet {

  final static String addServletName1 = "AddServletString";

  final static String addServletName2 = "AddServletClass";

  final static String addServletName3 = "CreateServlet";

  final static String addFilterName1 = "AddFilterString";

  final static String addFilterName2 = "AddFilterClass";

  final static String addFilterName3 = "CreateFilter";

  public void addServletStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      getServletContext().addServlet(addServletName1,
          "servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString");
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);
    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void addServletClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      getServletContext().addServlet(addServletName2,
          AddServletClass.class);
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);
    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void addServletTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      Servlet servlet3 = getServletContext().createServlet(
          CreateServlet.class);
      getServletContext().addServlet(addServletName3, servlet3);
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);

    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void addFilterStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      getServletContext().addFilter(addFilterName1,
          "servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString");
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);

    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void addFilterClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      getServletContext().addFilter(addFilterName2,
          AddFilterClass.class);
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);

    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void addFilterTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      Filter filter3 = getServletContext().createFilter(
          CreateFilter.class);
      getServletContext().addFilter(addFilterName3, filter3);
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);

    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }

  public void setInitParameterTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    try {
      getServletContext().addFilter("abc", "xyz");
      pw.append("Expected IllegalStateException not thrown.");
      ServletTestUtil.printResult(pw, false);

    } catch (IllegalStateException ex) {
      pw.append("Expected IllegalStateException thrown: " + ex.getMessage());
      ServletTestUtil.printResult(pw, true);
    }
  }
}
