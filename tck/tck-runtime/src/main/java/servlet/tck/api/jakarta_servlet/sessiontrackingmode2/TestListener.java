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
package servlet.tck.api.jakarta_servlet.sessiontrackingmode2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.SessionTrackingMode;

public class TestListener implements ServletContextListener {

  /**
   * Receives notification that the web application initialization process is
   * starting.
   *
   * @param sce
   *          The ServletContextEvent
   */
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();
    StringBuilder log = new StringBuilder();

    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<>();
    complete.add(SessionTrackingMode.URL);
    complete.add(SessionTrackingMode.SSL);

    Set<SessionTrackingMode> completeSet = new HashSet(complete);

    try {
      context.setSessionTrackingModes(completeSet);
      log.append("Expected IllegalArgumentException not thrown.");
    } catch (IllegalArgumentException ex) {
      log.append("Expected IllegalArgumentException thrown.");
    }

    context.setAttribute("TCK_TEST_STATUS", log.toString());
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
