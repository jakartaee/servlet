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

package servlet.tck.spec.annotationservlet.weblistener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener(value = "ContextListener")
public final class ContextListener implements ServletContextListener {

  private ServletContext context;

  public void contextDestroyed(ServletContextEvent event) {
    createAttribute("ContextDestroyed");
    this.context = null;
  }

  public void contextInitialized(ServletContextEvent event) {
    this.context = event.getServletContext();
    createAttribute("ContextInitialized");
  }

  private void createAttribute(String s) {
    String tmp = null;
    Object o = context.getAttribute("ContextListener");
    if (o != null) {
      if (o instanceof String) {
        tmp = (String) o + s;
      }
    } else {
      tmp = s;
    }
    context.setAttribute("ContextListener", tmp);
  }
}
