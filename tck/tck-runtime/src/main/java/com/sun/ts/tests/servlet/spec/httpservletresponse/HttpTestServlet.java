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

/*
 * $Id$
 */

/*
 * %E% %E%
 */

package com.sun.ts.tests.servlet.spec.httpservletresponse;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HttpTestServlet extends HttpTCKServlet {

  public void intHeaderTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    response.addIntHeader("header1", 12345);
    response.flushBuffer();
    response.addIntHeader("header2", 56789);
  }

  public void flushBufferTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    int size = 40;
    response.setContentLength(size);

    PrintWriter pw = response.getWriter();
    pw.println("Test PASSED");
    StringBuffer tmp = new StringBuffer(2 * size);
    int i = 0;

    while (i < 8) {
      tmp = tmp.append("111111111x");
      i = i + 1;
    }
    pw.println(tmp);
    response.addIntHeader("header1", 12345);
  }

  public void sendErrorCommitTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    response.sendError(HttpServletResponse.SC_OK, "sendErrorCommitTest");
    response.addIntHeader("header1", 12345);

    pw.println("Test FAILED in sendErrorCommitTest");
  }

  public void sendRedirectClearBufferTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("Test FAILED in sendRedirectClearBufferTest");

    response.sendRedirect("/RedirectedTest");

  }

  public void sendRedirectCommitTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    response.sendRedirect("/RedirectedTest");
    response.addIntHeader("header1", 12345);

    pw.println("Test FAILED in sendRedirectCommitTest");
  }
}
