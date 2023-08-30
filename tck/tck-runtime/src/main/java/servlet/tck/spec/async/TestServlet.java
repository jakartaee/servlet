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
 * $Id: $
 */
package servlet.tck.spec.async;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import servlet.tck.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void test1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    final AsyncContext ac = request.startAsync();

    Timer asyncTimer = new Timer("TestTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          ac.getResponse().getWriter().println("test1_INVOKED");
          ac.getResponse().getWriter().flush();
          ac.complete();
        } catch (IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 1000);
  }
}
