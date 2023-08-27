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
 * @(#)IncludedServlet.java	1.6 04/02/18
 */

package com.sun.ts.tests.servlet.api.jakarta_servlet.dofilter;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class IncludedServlet extends GenericServlet {

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    response.setContentType("text/html");
    ServletTestUtil.printResult(pw, "from IncludedServlet");
    pw.flush();
    pw.close();
  }
}
