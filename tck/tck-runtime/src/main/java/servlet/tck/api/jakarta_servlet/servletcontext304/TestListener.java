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
package servlet.tck.api.jakarta_servlet.servletcontext304;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListener implements ServletContextListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

  /**
   * Receives notification that the web application initialization process is
   * starting.
   *
   * @param sce
   *          The servlet context event
   */
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    Boolean listener_test = false;
    String LISTENER_TEST = "LISTENER_TEST";

    try {
      context.createListener(
          CreateGenericEventListener.class);
    } catch (java.lang.IllegalArgumentException ex) {
      listener_test = true;
      LOGGER.error("Error creating Listener CreateGenericEventListener: "
          + ex.getMessage());
    } catch (ServletException exs) {
      LOGGER.error("Error creating Listener CreateGenericEventListener: "
          + exs.getMessage());
    }
    context.setInitParameter(LISTENER_TEST, listener_test.toString());
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
