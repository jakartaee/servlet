/*
 * Copyright (c) 2013, 2021 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.scinitializer.addlistener1;

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
      context.addListener(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.addlistener.SCListener");
      passed = false;
      log.append(
          "Expected UnsupportedOperationException not thrown by addListener(SCListener).");
    } catch (UnsupportedOperationException ex) {
      log.append(
          "Expected UnsupportedOperationException thrown by addListener(SCListener).");
    }

    try {
      context.addListener(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.addlistener.SCAttributeListener");
      passed = false;
      log.append(
          "Expected UnsupportedOperationException not thrown by addListener(SCAttributeListener).");
    } catch (UnsupportedOperationException ex) {
      log.append(
          "Expected UnsupportedOperationException thrown by addListener(SCAttributeListener).");
    }

    try {
      context.addListener(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.addlistener.SRListener");
      passed = false;
      log.append(
          "Expected UnsupportedOperationException not thrown by addListener(SRListener).");
    } catch (UnsupportedOperationException ex) {
      log.append(
          "Expected UnsupportedOperationException thrown by addListener(SRListener).");
    }

    try {
      context.addListener(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.addlistener.SRAttributeListener");
      passed = false;
      log.append(
          "Expected UnsupportedOperationException not thrown by addListener(SRAttributeListene).");
    } catch (UnsupportedOperationException ex) {
      log.append(
          "Expected UnsupportedOperationException thrown by addListener(SRAttributeListener).");
    }

    context.setAttribute("TCK_TEST_STATUS", log.toString());
    context.setAttribute("TCK_TEST_PASS_STATUS", passed);
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
