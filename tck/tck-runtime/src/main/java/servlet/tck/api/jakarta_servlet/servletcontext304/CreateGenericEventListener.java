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

import java.util.EventListener;

import servlet.tck.common.util.StaticLog;

import jakarta.servlet.ServletRequestEvent;

public final class CreateGenericEventListener implements EventListener {

  public void listenerDestroyed(ServletRequestEvent event) {
    StaticLog.add("CreateGenericEventListener_Destroyed.");
  }

  public void listenerInitialized(ServletRequestEvent event) {
    StaticLog.add("CreateGenericEventListener_Initialized.");
  }
}
