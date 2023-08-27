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
 * $Id$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.getfilterregistration;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {

  /**
   * Receives notification that the web application initialization process is
   * starting.
   *
   * @param sce
   *          The ServletContextEvent
   */
  public void contextInitialized(ServletContextEvent sce) {
    boolean passed = true;
    ServletContext context = sce.getServletContext();
    StringBuilder log = new StringBuilder();

    try {
      context.getFilterRegistration(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.getfilterregistration.TestFilter");
      log.append(
          "Expected UnsupportedOperationException not thrown by getFilterRegistration(String).");
      passed = false;
    } catch (UnsupportedOperationException ex) {
      log.append(
          "Expected UnsupportedOperationException thrown by getFilterRegistration(String).");
    }

    context.setAttribute("TCK_TEST_STATUS", log.toString());
    context.setAttribute("TCK_TEST_PASS_STATUS", passed);
  }

  /**
   * Receives notification that the Servlet Context is about to be shut down.
   *
   * @param sce
   *          The Servlet Context event
   */
  public void contextDestroyed(ServletContextEvent sce) {
    // Do nothing
  }
}
