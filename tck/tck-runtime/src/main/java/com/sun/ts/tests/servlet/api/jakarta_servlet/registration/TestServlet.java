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
package com.sun.ts.tests.servlet.api.jakarta_servlet.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void servletURLMappingTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String URL_MAPPING = "URL_MAPPING_TEST";

    pw.println(URL_MAPPING + "="
        + getServletContext().getInitParameter(URL_MAPPING).toUpperCase());
    getServletContext().removeAttribute(URL_MAPPING);

    ServletTestUtil.printResult(pw, true);
  }

  public void filterServletMappingTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String FILTER_SERVLET_MAPPING = "FILTER_SERVLET_MAPPING";

    pw.println(FILTER_SERVLET_MAPPING + "="
        + getServletContext().getInitParameter(FILTER_SERVLET_MAPPING));
    getServletContext().removeAttribute(FILTER_SERVLET_MAPPING);

    ServletTestUtil.printResult(pw, true);
  }

  public void getServletRegistrationsTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String SERVLET_REGISTRIONS = "SERVLET_REGISTRATIONS";

    pw.println(SERVLET_REGISTRIONS + ":");
    String servlets = getServletContext().getInitParameter(SERVLET_REGISTRIONS)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(SERVLET_REGISTRIONS);

    ServletTestUtil.printResult(pw, true);
  }

  public void getServletRegistrationTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String SERVLET_REGISTRATION = "SERVLET_REGISTRATION";

    pw.println(SERVLET_REGISTRATION + ":");
    String servlets = getServletContext().getInitParameter(SERVLET_REGISTRATION)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(SERVLET_REGISTRATION);

    ServletTestUtil.printResult(pw, true);
  }

  public void getFilterRegistrationsTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String FILTER_REGISTRIONS = "FILTER_REGISTRATIONS";

    pw.println(FILTER_REGISTRIONS + ":");
    String filters = getServletContext().getInitParameter(FILTER_REGISTRIONS);

    StringTokenizer st = new StringTokenizer(filters, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(FILTER_REGISTRIONS);

    ServletTestUtil.printResult(pw, true);
  }

  public void getFilterRegistrationTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String FILTER_REGISTRION = "FILTER_REGISTRATION";

    pw.println(FILTER_REGISTRION + ":");
    String filters = getServletContext().getInitParameter(FILTER_REGISTRION);

    StringTokenizer st = new StringTokenizer(filters, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(FILTER_REGISTRION);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String REGISTRATION_NAME = "REGISTRION_NAME";

    pw.println(REGISTRATION_NAME + ":");
    String servlets = getServletContext().getInitParameter(REGISTRATION_NAME)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(REGISTRATION_NAME);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationClassNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String REGISTRATION_CLASS_NAME = "REGISTRATION_CLASS_NAME";

    pw.println(REGISTRATION_CLASS_NAME + ":");
    String servlets = getServletContext()
        .getInitParameter(REGISTRATION_CLASS_NAME).toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(REGISTRATION_CLASS_NAME);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationInitParameterTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String REGISTRATION_INIT_PARAMETER = "REGISTRATION_INIT_PARAMETER";

    pw.println(REGISTRATION_INIT_PARAMETER + ":");
    String servlets = getServletContext()
        .getInitParameter(REGISTRATION_INIT_PARAMETER);

    if (servlets != null) {
      StringTokenizer st = new StringTokenizer(servlets, "|");
      while (st.hasMoreTokens()) {
        pw.println(st.nextToken());
      }
      ServletTestUtil.printResult(pw, true);
    } else {
      ServletTestUtil.printResult(pw, false);
    }
    getServletContext().removeAttribute(REGISTRATION_INIT_PARAMETER);
  }

  public void getRegistrationInitParametersTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String REGISTRATION_INIT_PARAMETERS = "REGISTRATION_INIT_PARAMETERS";

    pw.println(REGISTRATION_INIT_PARAMETERS + ":");
    String all_stuff = getServletContext()
        .getInitParameter(REGISTRATION_INIT_PARAMETERS);

    if (all_stuff != null) {
      StringTokenizer st = new StringTokenizer(all_stuff, "|");
      while (st.hasMoreTokens()) {
        pw.println(st.nextToken());
      }
      ServletTestUtil.printResult(pw, true);
    } else {
      ServletTestUtil.printResult(pw, false);
    }
    getServletContext().removeAttribute(REGISTRATION_INIT_PARAMETERS);
  }
}
