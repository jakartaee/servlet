/*
 * Copyright (c) 2015, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.util.ArrayList;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener(value = "HttpSessionAttributeListener")
public final class HSAttributeListener implements HttpSessionAttributeListener {

  public void attributeAdded(HttpSessionBindingEvent event) {
    System.out.println("in HSAttributeListener.attributeAdded");
    ArrayList al = null;

    al = (ArrayList) event.getSession().getServletContext()
        .getAttribute("HSAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("HSAttributeListener.attributeAdded");
    event.getSession().getServletContext().setAttribute("HSAList", al);
  }

  public void attributeRemoved(HttpSessionBindingEvent event) {
    System.out.println("in HSAttributeListener.attributeRemoved");
    ArrayList al = null;

    al = (ArrayList) event.getSession().getServletContext()
        .getAttribute("HSAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("HSAttributeListener.attributeRemoved");
    event.getSession().getServletContext().setAttribute("HSAList", al);
  }

  public void attributeReplaced(HttpSessionBindingEvent event) {
    System.out.println("in HSAttributeListener.attributeReplaced");
    ArrayList al = null;

    al = (ArrayList) event.getSession().getServletContext()
        .getAttribute("HSAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("HSAttributeListener.attributeReplaced");
    event.getSession().getServletContext().setAttribute("HSAList", al);
  }
}
