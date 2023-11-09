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
package servlet.tck.api.jakarta_servlet.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void servletURLMappingTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String urlMapping = "URL_MAPPING_TEST";

    pw.println(urlMapping + "="
        + getServletContext().getInitParameter(urlMapping).toUpperCase());
    getServletContext().removeAttribute(urlMapping);

    ServletTestUtil.printResult(pw, true);
  }

  public void filterServletMappingTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String filterServletMapping = "FILTER_SERVLET_MAPPING";

    pw.println(filterServletMapping + "="
        + getServletContext().getInitParameter(filterServletMapping));
    getServletContext().removeAttribute(filterServletMapping);

    ServletTestUtil.printResult(pw, true);
  }

  public void getServletRegistrationsTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String servletRegistrions = "SERVLET_REGISTRATIONS";

    pw.println(servletRegistrions + ":");
    String servlets = getServletContext().getInitParameter(servletRegistrions)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(servletRegistrions);

    ServletTestUtil.printResult(pw, true);
  }

  public void getServletRegistrationTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String servletRegistration = "SERVLET_REGISTRATION";

    pw.println(servletRegistration + ":");
    String servlets = getServletContext().getInitParameter(servletRegistration)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(servletRegistration);

    ServletTestUtil.printResult(pw, true);
  }

  public void getFilterRegistrationsTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String filterRegistrions = "FILTER_REGISTRATIONS";

    pw.println(filterRegistrions + ":");
    String filters = getServletContext().getInitParameter(filterRegistrions);

    StringTokenizer st = new StringTokenizer(filters, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(filterRegistrions);

    ServletTestUtil.printResult(pw, true);
  }

  public void getFilterRegistrationTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String filterRegistrion = "FILTER_REGISTRATION";

    pw.println(filterRegistrion + ":");
    String filters = getServletContext().getInitParameter(filterRegistrion);

    StringTokenizer st = new StringTokenizer(filters, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(filterRegistrion);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String registrationName = "REGISTRION_NAME";

    pw.println(registrationName + ":");
    String servlets = getServletContext().getInitParameter(registrationName)
        .toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(registrationName);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationClassNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String registrationClassName = "REGISTRATION_CLASS_NAME";

    pw.println(registrationClassName + ":");
    String servlets = getServletContext()
        .getInitParameter(registrationClassName).toUpperCase();

    StringTokenizer st = new StringTokenizer(servlets, "|");
    while (st.hasMoreTokens()) {
      pw.println(st.nextToken());
    }

    getServletContext().removeAttribute(registrationClassName);

    ServletTestUtil.printResult(pw, true);
  }

  public void getRegistrationInitParameterTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String registrationInitParameter = "REGISTRATION_INIT_PARAMETER";

    pw.println(registrationInitParameter + ":");
    String servlets = getServletContext()
        .getInitParameter(registrationInitParameter);

    if (servlets != null) {
      StringTokenizer st = new StringTokenizer(servlets, "|");
      while (st.hasMoreTokens()) {
        pw.println(st.nextToken());
      }
      ServletTestUtil.printResult(pw, true);
    } else {
      ServletTestUtil.printResult(pw, false);
    }
    getServletContext().removeAttribute(registrationInitParameter);
  }

  public void getRegistrationInitParametersTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String registrationInitParameters = "REGISTRATION_INIT_PARAMETERS";

    pw.println(registrationInitParameters + ":");
    String allStuff = getServletContext()
        .getInitParameter(registrationInitParameters);

    if (allStuff != null) {
      StringTokenizer st = new StringTokenizer(allStuff, "|");
      while (st.hasMoreTokens()) {
        pw.println(st.nextToken());
      }
      ServletTestUtil.printResult(pw, true);
    } else {
      ServletTestUtil.printResult(pw, false);
    }
    getServletContext().removeAttribute(registrationInitParameters);
  }
}
