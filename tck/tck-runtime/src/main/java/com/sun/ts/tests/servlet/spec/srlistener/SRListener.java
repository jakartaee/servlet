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
package com.sun.ts.tests.servlet.spec.srlistener;

import java.util.ArrayList;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SRListener implements ServletRequestListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(SRListener.class);

  // Public Methods
  public void requestDestroyed(ServletRequestEvent event) {
    ArrayList al = null;
    al = (ArrayList) event.getServletContext().getAttribute("arraylist");
    if (al != null) {
      LOGGER.info("in requestDestroyed method of listener");
      al.add("in requestDestroyed method of listener");
      event.getServletContext().setAttribute("arraylist", al);
    }
  }

  public void requestInitialized(ServletRequestEvent event) {
    ArrayList al = (ArrayList) event.getServletContext().getAttribute("arraylist");
    if (al == null) {
      al = new ArrayList();
    }
    LOGGER.info("in requestInitialized method of listener");
    al.add("in requestInitialized method of listener");
    event.getServletContext().setAttribute("arraylist", al);
  }
}
