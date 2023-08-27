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
 * $Id$
 */
package com.sun.ts.tests.servlet.spec.pluggability.common;

import java.util.ArrayList;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

public final class RequestListener2 implements ServletRequestListener {

  public void requestDestroyed(ServletRequestEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("testmessage");
    if (al != null) {
      al.add("RequestListener2 requestDestroyed");
      event.getServletContext().setAttribute("testmessage", al);
    }
  }

  public void requestInitialized(ServletRequestEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("testmessage");
    if (al == null) {
      al = new ArrayList();
    }
    al.add("RequestListener2 requestInitialized");
    event.getServletContext().setAttribute("testmessage", al);
  }
}
