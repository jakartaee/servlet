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

package com.sun.ts.tests.servlet.spec.servletcontext;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.descriptor.JspConfigDescriptor;

public class TestServlet extends GenericTCKServlet {

  public void getJspConfigDescriptorTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();
    JspConfigDescriptor results = context.getJspConfigDescriptor();
    if (results != null) {
      passed = false;
      pw.println("Unexpected JspConfigDescriptor exists: " + results);
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
