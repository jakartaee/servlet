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

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Test for ServletContext.getNamedDispatcher(String) method
 */

public class GetNamedDispatcherTestServlet extends GenericServlet {

  /**
   * We will try to get the RequestDispatcher for the servlet Registered as
   * config
   */

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String path = "GetNamedDispatcherTest";
    RequestDispatcher rd = context.getNamedDispatcher(path);

    if (rd != null) {
      passed = true;
    } else {
      passed = false;
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
