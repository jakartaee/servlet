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
package servlet.tck.spec.annotationservlet.weblistener;

import java.util.ArrayList;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener("HttpSessionListener")
public final class HSListener implements HttpSessionListener {

  public void sessionCreated(HttpSessionEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getSession().getServletContext()
        .getAttribute("HSList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("HSListener:sessionCreated");
    event.getSession().getServletContext().setAttribute("HSList", al);
  }

  public void sessionDestroyed(HttpSessionEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getSession().getServletContext()
        .getAttribute("HSList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("HSListener:sessionDestroyed");
    event.getSession().getServletContext().setAttribute("HSList", al);
  }
}
