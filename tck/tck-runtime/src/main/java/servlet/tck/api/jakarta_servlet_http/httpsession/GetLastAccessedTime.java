/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.api.jakarta_servlet_http.httpsession;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class GetLastAccessedTime extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    String ts1 = request.getParameter("t1");
    String ts2 = request.getParameter("t2");
    pw.println("Session created before " + ts1);
    pw.println("Session accessed before " + ts2);
    long t1 = Long.parseLong(ts1);
    long t2 = Long.parseLong(ts2);

    HttpSession session = request.getSession(false);

    if (session != null) {
      long t3 = session.getLastAccessedTime();
      if (t3 < t1) {
        pw.println("Test failed: session.getLastAccessedTime() "
            + "indicates last accessed at " + t3 + " which is before creation");
        ServletTestUtil.printResult(pw, false);
      } else {
        if (t3 > t2) {
          pw.println("Test failed: session.getLastAccessedTime() "
              + "indicates last accessed at " + t3
              + " which is after it was last accessed");
          ServletTestUtil.printResult(pw, false);
        } else {
          pw.println("Test passed: session.getLastAccessedTime() "
              + "indicates last accessed at " + t3
              + " which is after creation,  "
              + "and before the time returned from last access");
          ServletTestUtil.printResult(pw, true);
        }
      }
    }
  }
}
