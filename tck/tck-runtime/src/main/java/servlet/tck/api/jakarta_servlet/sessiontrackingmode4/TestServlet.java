/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.sessiontrackingmode4;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void setSessionTrackingModes7(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    String expected_status = "Expected IllegalArgumentException thrown.";
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();
    String status = (String) context.getAttribute("TCK_TEST_STATUS");
    if (!expected_status.equals(status)) {
      passed = false;
    }

    ServletTestUtil.printResult(pw.append(status), passed);
  }
}
