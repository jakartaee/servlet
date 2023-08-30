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
 * $Id:i$
 */
package servlet.tck.api.jakarta_servlet.servletcontext30;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import servlet.tck.common.util.ServletTestUtil;
import servlet.tck.common.util.StaticLog;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class CreateServlet extends GenericServlet {

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    pw.println("CreateServlet is invoked correctly");

    ArrayList result = (ArrayList) getServletContext()
        .getAttribute("arraylist");

    for (Object tmp : result) {
      pw.println(tmp.toString());

    }
    getServletContext().removeAttribute("arraylist");

    result = StaticLog.getClear();
    if (result != null) {
      for (Object tmp : result) {
        if (tmp != null) {
          pw.println(tmp.toString());
        }
      }
    }
    StaticLog.clear();
    ServletTestUtil.printResult(pw, true);
  }
}
