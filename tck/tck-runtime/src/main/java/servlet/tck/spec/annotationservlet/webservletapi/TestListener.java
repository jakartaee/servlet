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
 * $Id$
 */
package servlet.tck.spec.annotationservlet.webservletapi;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.annotation.WebListener;
import servlet.tck.spec.annotationservlet.webservlet.Servlet2;
import servlet.tck.spec.annotationservlet.webservlet.Servlet3;

@WebListener("")
public class TestListener implements ServletContextListener {

  /**
   * Receives notification that the web application initialization process is
   * starting.
   *
   * @param sce
   *          The servlet context event
   */
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    final String addServletName1 = "Servlet1API";
    final String addServletName2 = "Servlet2API";
    final String addServletName3 = "Servlet3API";

    Map<String, String> params = new HashMap<String, String>();

    /*
     * Add Servlet1
     */
    ServletRegistration sr1 = context.addServlet(addServletName1,
        "com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet1");
    sr1.addMapping("/Servlet1APIURL");

    /*
     * Add Servlet2
     */
    ServletRegistration sr2 = context.addServlet(addServletName2,
        Servlet2.class);
    sr2.addMapping("/Servlet2APIURL", "/Servlet2APIURL2", "*.xml",
        "/ServletAPIURL2/*");

    /*
     * Add Servlet3
     */
    ServletRegistration sr3 = null;
    try {
      Servlet servlet3 = context.createServlet(
          Servlet3.class);
      sr3 = context.addServlet(addServletName3, servlet3);
      sr3.addMapping("/Servlet3APIURL");
      params.put("name1", "servlet3");
      params.put("name2", "servlet3again");
      sr3.setInitParameters(params);
      params.clear();

    } catch (ServletException ex) {
      System.out.println("Error creating Servlet");
    }
  }

  /**
   * Receives notification that the servlet context is about to be shut down.
   *
   * @param sce
   *          The servlet context event
   */
  public void contextDestroyed(ServletContextEvent sce) {
    // Do nothing
  }
}
