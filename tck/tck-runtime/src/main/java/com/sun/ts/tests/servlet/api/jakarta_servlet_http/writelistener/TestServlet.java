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
 * $Id$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.writelistener;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/TestServlet", asyncSupported = true)
public class TestServlet extends HttpTCKServlet {

  private static final int LENGTH = 587952;

  public void nioOutputTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    ServletOutputStream output = response.getOutputStream();
    TestListener writeListener = new TestListener(output, ac);
    output.setWriteListener(writeListener);
  }
}
