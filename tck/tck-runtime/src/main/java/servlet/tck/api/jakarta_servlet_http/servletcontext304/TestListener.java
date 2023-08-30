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
package servlet.tck.api.jakarta_servlet_http.servletcontext304;

import java.util.EventListener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;

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

    Boolean listener_test = true;
    String LISTENER_TEST = "LISTENER_TEST";

    /*
     * Add HttpSessionListener
     */
    context.addListener(
        AddHttpSessionListenerClass.class);
    context.addListener(
        "servlet.tck.api.jakarta_servlet_http.servletcontext304.AddHttpSessionListenerString");
    try {
      EventListener hslistener = context.createListener(
          CreateHttpSessionListener.class);
      context.addListener(hslistener);
    } catch (ServletException ex) {
      listener_test = false;
      System.out.println("Error creating Listener CreateHttpSessionListener: "
          + ex.getMessage());
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
