/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.sessiontrackingmode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.SessionTrackingMode;

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
    Boolean pass = true;
    StringBuilder log = new StringBuilder();
    StringBuilder modes = new StringBuilder();
    List<SessionTrackingMode> defaults = new CopyOnWriteArrayList<SessionTrackingMode>(
        context.getDefaultSessionTrackingModes());
    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<SessionTrackingMode>();

    complete.add(SessionTrackingMode.COOKIE);
    complete.add(SessionTrackingMode.URL);
    complete.add(SessionTrackingMode.SSL);

    for (SessionTrackingMode tmp : defaults) {
      if (tmp == SessionTrackingMode.COOKIE || tmp == SessionTrackingMode.URL
          || tmp == SessionTrackingMode.SSL) {
        log.append("getDefaultSessionTrackingModes=").append(tmp).append(";");
        complete.remove(tmp);
      } else {
        pass = false;
        log.append("Unrecognized SessionTrackingModes ").append(tmp)
            .append(";");
      }
    }

    for (SessionTrackingMode tmp : complete) {
      Set<SessionTrackingMode> test = new HashSet();
      test.add(tmp);
      try {
        context.setSessionTrackingModes(test);
      } catch (Exception ex) {
        log.append("Unsupported SessionTrackingModes=").append(tmp).append(";");
        complete.remove(tmp);
      }
    }

    // context.setSessionTrackingModes
    // throws IllegalArgumentException - if sessionTrackingModes specifies a
    // combination
    // of SessionTrackingMode.SSL with a session tracking mode other than
    // SessionTrackingMode.SSL
    complete.remove(SessionTrackingMode.SSL);
    if (!complete.isEmpty()) {
      context.setSessionTrackingModes(new HashSet(complete));
      for (SessionTrackingMode tmp : complete) {
        modes.append(tmp).append(";");
        log.append("Setting SessionTrackingModes to= ").append(tmp).append(";");
      }
    } else {
      log.append("Only default SessionTrackingModes are supported");
      defaults.remove(SessionTrackingMode.SSL);
      context.setSessionTrackingModes(new HashSet(defaults));
      for (SessionTrackingMode tmp : defaults) {
        modes.append(tmp).append(";");
        log.append("Setting SessionTrackingModes to Defaults = ").append(tmp)
            .append(";");
      }
    }

    context.setAttribute("LOG", log.toString());
    context.setAttribute("MODES", modes.toString());
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
