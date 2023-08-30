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

import java.util.ArrayList;

import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.annotation.WebListener;

@WebListener(value = "ServletRequestAttributeListener")
public final class SRAttributeListener
    implements ServletRequestAttributeListener {

  public void attributeAdded(ServletRequestAttributeEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("SRAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("SRAAdded:" + event.getName() + "," + event.getValue());
    event.getServletContext().setAttribute("SRAList", al);
  }

  public void attributeRemoved(ServletRequestAttributeEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("SRAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("SRARemoved:" + event.getName() + "," + event.getValue());
    event.getServletContext().setAttribute("SRAList", al);
  }

  public void attributeReplaced(ServletRequestAttributeEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("SRAList");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("SRAReplaced:" + event.getName() + "," + event.getValue());
    event.getServletContext().setAttribute("SRAList", al);
  }
}
