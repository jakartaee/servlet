/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext40;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRegistration;

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

    ServletRegistration registration = context.addJspFile("AddJspFile",
        "/addJspFile.jsp");
    registration.addMapping("/servlet/addJspFile");

    List list = new ArrayList();

    int oldTimeout = context.getSessionTimeout();
    context.setSessionTimeout(20);
    int newTimeout = context.getSessionTimeout();

    if (oldTimeout == 54 && newTimeout == 20) {
      list.add("changeSessionTimeout_correctly");
    } else {
      list.add(
          "changeSessionTimeout_fail: expected oldTimeout is 54 and expected newTimeout is 20, now they are "
              + oldTimeout + "and" + newTimeout);
    }

    // negtive test
    try {
      context.addJspFile("", "/addJspFile.jsp");
      list.add("add_jsp_with_empty_name");
    } catch (IllegalArgumentException e) {
      list.add("IllegalArgumentException_when_empty_name");
    }

    try {
      context.addJspFile(null, "/addJspFile.jsp");
      list.add("add_jsp_with_null_name");
    } catch (IllegalArgumentException e) {
      list.add("IllegalArgumentException_when_null_name");
    }
    context.setAttribute("arraylist", list);

    // context.addListener(AddListener.class);
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
