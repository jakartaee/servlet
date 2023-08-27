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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext31;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void getVirtualServerNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    try {
      String expected_virtualhost = request
          .getParameter("VirtualServerNamePlease");
      String tmp = context.getVirtualServerName();
      if (tmp != null) {
        pw.println("VirtualServerName=" + tmp);
        if (!tmp.equals(expected_virtualhost)) {
          passed = false;
          pw.println("Incorrect value returned");
        }
      } else {
        passed = false;
        pw.println("Null value returned");
      }
    } catch (Exception ex) {
      passed = false;
      pw.println("Got Exception when calling getVirtualServerName()");
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
