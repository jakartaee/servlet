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

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletrequestwrapper30x;

import java.io.IOException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.ServletResponse;

public class IsWrapperForTest extends GenericServlet {

  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
  }

  public void service(ServletRequest req, ServletResponse response)
      throws ServletException, IOException {
    Boolean pass = true;
    ServletRequestWrapper reqWrapper1 = new TCKServletRequestWrapper(req);
    ServletRequestWrapper reqWrapper2 = new TCKServletRequestWrapper(
        reqWrapper1);
    ServletRequestWrapper reqWrapper3 = new TCKServletRequestWrapper(
        reqWrapper2);
    TCKServletRequestsubWrapper myReqWrapper = new TCKServletRequestsubWrapper(
        req);

    if (!reqWrapper3.isWrapperFor(reqWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper3.isWrapperFor(reqWrapper1)");
    }

    if (!reqWrapper3.isWrapperFor(reqWrapper2)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper3.isWrapperFor(reqWrapper2)");
    }

    if (!reqWrapper3.isWrapperFor(TCKServletRequestWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper3.isWrapperFor(TCKServletRequestWrapper.class)");
    }

    if (reqWrapper3.isWrapperFor(myReqWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper3.isWrapperFor(myReqWrapper)");
    }

    if (reqWrapper3.isWrapperFor(TCKServletRequestsubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper3.isWrapperFor(TCKServletRequestsubWrapper.class)");
    }

    if (!reqWrapper2.isWrapperFor(reqWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper2.isWrapperFor(reqWrapper1)");
    }

    if (!reqWrapper2.isWrapperFor(TCKServletRequestWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper2.isWrapperFor(TCKServletRequestWrapper.class)");
    }

    if (reqWrapper2.isWrapperFor(myReqWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper2.isWrapperFor(myReqWrapper)");
    }

    if (reqWrapper2.isWrapperFor(TCKServletRequestsubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper2.isWrapperFor(TCKServletRequestsubWrapper.class)");
    }

    if (reqWrapper1.isWrapperFor(TCKServletRequestWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper1.isWrapperFor(TCKServletRequestWrapper.class)");
    }

    if (reqWrapper1.isWrapperFor(myReqWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: reqWrapper1.isWrapperFor(myReqWrapper)");
    }

    if (reqWrapper1.isWrapperFor(TCKServletRequestsubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: reqWrapper1.isWrapperFor(TCKServletRequestsubWrapper.class)");
    }

    if (myReqWrapper.isWrapperFor(TCKServletRequestWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: myReqWrapper.isWrapperFor(TCKServletRequestWrapper.class)");
    }

    if (myReqWrapper.isWrapperFor(reqWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myReqWrapper.isWrapperFor(reqWrapper1)");
    }

    if (myReqWrapper.isWrapperFor(reqWrapper2)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myReqWrapper.isWrapperFor(reqWrapper2)");
    }

    if (myReqWrapper.isWrapperFor(reqWrapper3)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myReqWrapper.isWrapperFor(reqWrapper3)");
    }

    if (myReqWrapper.isWrapperFor(TCKServletRequestsubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: myReqWrapper.isWrapperFor(TCKServletRequestsubWrapper.class)");
    }

    if (!pass) {
      response.getWriter().println("Test Failed.");
    } else {
      response.getWriter().println("Test Passed.");
    }
  }
}
