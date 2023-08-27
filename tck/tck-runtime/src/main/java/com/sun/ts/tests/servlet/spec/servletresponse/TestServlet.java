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

package com.sun.ts.tests.servlet.spec.servletresponse;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestServlet extends GenericServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestServlet.class);

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    response.setContentType("text/plain");
    PrintWriter pw = response.getWriter();

    pw.write("flushBufferTest for compatibility\n\r");
    LOGGER.info("flushBufferTest for compatibility");
    response.flushBuffer();

    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // write the second part of the document and flush by exit method
    pw.write("Test Failed\n\r");
  }

  public void doGet(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    service(request, response);
  }

  public void doPost(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    service(request, response);
  }
}
