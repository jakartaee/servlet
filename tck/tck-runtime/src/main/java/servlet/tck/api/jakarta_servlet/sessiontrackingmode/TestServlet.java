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
 * $Id:$
 */
package servlet.tck.api.jakarta_servlet.sessiontrackingmode;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.SessionTrackingMode;

public class TestServlet extends GenericTCKServlet {

  public void setSessionTrackingModes(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();
    pw.println((String) context.getAttribute("LOG"));

    Set<SessionTrackingMode> expected_modes = new HashSet();
    String expected = (String) context.getAttribute("MODES");
    StringTokenizer str = new StringTokenizer(expected, ";");

    while (str.hasMoreTokens()) {
      String mode = str.nextToken();
      pw.println("Expected SessionTrackingMode =" + mode);
      if (mode.equals(SessionTrackingMode.COOKIE.toString())) {
        expected_modes.add(SessionTrackingMode.COOKIE);
      } else if (mode.equals(SessionTrackingMode.SSL.toString())) {
        // SSL cannot be set with combination of other SessionTrackingMode
        // expected_modes.add(SessionTrackingMode.SSL);
      } else if (mode.equals(SessionTrackingMode.URL.toString())) {
        expected_modes.add(SessionTrackingMode.URL);
      } else {
        passed = false;
        pw.println("Unrecognized SessionTrackingMode expected: " + mode);
      }

    }

    Set<SessionTrackingMode> results = null;

    if (expected_modes.isEmpty()) {
      results = context.getDefaultSessionTrackingModes();
    } else {
      results = context.getEffectiveSessionTrackingModes();
    }

    if (!results.containsAll(expected_modes)) {
      passed = false;
      pw.append("setSessionTrackingModes and getEffectiveSessionTrackingModes "
          + "returns different set of SessionTrackingModes");

      pw.append("getEffectiveSessionTrackingModes =");
      for (SessionTrackingMode tmp : results) {
        pw.append("           " + tmp);
      }
    }

    ServletTestUtil.printResult(pw, passed);
  }
}
