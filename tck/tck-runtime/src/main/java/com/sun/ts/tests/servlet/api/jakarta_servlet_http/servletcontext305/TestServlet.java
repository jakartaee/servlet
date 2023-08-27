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
 * $Id:$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.servletcontext305;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;
import com.sun.ts.tests.servlet.common.util.StaticLog;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TestServlet extends HttpTCKServlet {

  public void addListenerTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String name = "LISTENER_TEST";

    pw.println(
        name + "=" + getServletContext().getInitParameter(name).toUpperCase());
    getServletContext().removeAttribute(name);

    HttpSession hs = request.getSession();
    hs.setAttribute("attributeAddedTest", "Attribute1");
    hs.invalidate();

    ArrayList result = StaticLog.getClear();
    pw.println(result);
    ServletTestUtil.printResult(pw, true);
    getServletContext().removeAttribute("arraylist");
  }
}
