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

package servlet.tck.spec.listenerorder;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;

public class ServletRequestListener2 implements ServletRequestListener {

  public void requestInitialized(ServletRequestEvent sre) {
    ServletRequest sr = sre.getServletRequest();
    if (sr.getAttribute("name_1") == null) {
      throw new IllegalStateException(
          "Missing request attribute name_1 during requestInitialized");
    }
    if (sr.getAttribute("name_3") != null) {
      throw new IllegalStateException(
          "Unexpected request attribute name_3 during requestInitialized");
    }
    sr.setAttribute("name_2", "value_2");
  }

  public void requestDestroyed(ServletRequestEvent sre) {
    ServletRequest sr = sre.getServletRequest();
    if (sr.getAttribute("name_1") == null) {
      throw new IllegalStateException(
          "Missing request attribute during requestDestroyed");
    }
    if (sr.getAttribute("name_3") != null) {
      throw new IllegalStateException(
          "Unexpected request attribute name_3 during requestInitialized");
    }
    sr.removeAttribute("name_2");
  }

}
