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

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletresponsewrapper30;

import java.io.IOException;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletResponseWrapper;

public class IsWrapperForTest extends GenericServlet {

  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
  }

  public void service(ServletRequest req, ServletResponse response)
      throws ServletException, IOException {
    Boolean pass = true;
    ServletResponseWrapper resWrapper1 = new TCKServletResponseWrapper(
        response);
    ServletResponseWrapper resWrapper2 = new TCKServletResponseWrapper(
        resWrapper1);
    ServletResponseWrapper resWrapper3 = new TCKServletResponseWrapper(
        resWrapper2);
    TCKServletResponsesubWrapper myresWrapper = new TCKServletResponsesubWrapper(
        response);

    if (!resWrapper3.isWrapperFor(resWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper3.isWrapperFor(resWrapper1)");
    }

    if (!resWrapper3.isWrapperFor(resWrapper2)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper3.isWrapperFor(resWrapper2)");
    }

    if (!resWrapper3.isWrapperFor(TCKServletResponseWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper3.isWrapperFor(TCKServletResponseWrapper.class)");
    }

    if (resWrapper3.isWrapperFor(myresWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper3.isWrapperFor(myresWrapper)");
    }

    if (resWrapper3.isWrapperFor(TCKServletResponsesubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper3.isWrapperFor(TCKServletResponsesubWrapper.class)");
    }

    if (!resWrapper2.isWrapperFor(resWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper2.isWrapperFor(resWrapper1)");
    }

    if (!resWrapper2.isWrapperFor(TCKServletResponseWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper2.isWrapperFor(TCKServletResponseWrapper.class)");
    }

    if (resWrapper2.isWrapperFor(myresWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper2.isWrapperFor(myresWrapper)");
    }

    if (resWrapper2.isWrapperFor(TCKServletResponsesubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper2.isWrapperFor(TCKServletResponsesubWrapper.class)");
    }

    if (resWrapper1.isWrapperFor(TCKServletResponseWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper1.isWrapperFor(TCKServletResponseWrapper.class)");
    }

    if (resWrapper1.isWrapperFor(myresWrapper)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: resWrapper1.isWrapperFor(myresWrapper)");
    }

    if (resWrapper1.isWrapperFor(TCKServletResponsesubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: resWrapper1.isWrapperFor(TCKServletResponsesubWrapper.class)");
    }

    if (myresWrapper.isWrapperFor(TCKServletResponseWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: myresWrapper.isWrapperFor(TCKServletResponseWrapper.class)");
    }

    if (myresWrapper.isWrapperFor(resWrapper1)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myresWrapper.isWrapperFor(resWrapper1)");
    }

    if (myresWrapper.isWrapperFor(resWrapper2)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myresWrapper.isWrapperFor(resWrapper2)");
    }

    if (myresWrapper.isWrapperFor(resWrapper3)) {
      pass = false;
      response.getWriter()
          .println("Test Failed: myresWrapper.isWrapperFor(resWrapper3)");
    }

    if (myresWrapper.isWrapperFor(TCKServletResponsesubWrapper.class)) {
      pass = false;
      response.getWriter().println(
          "Test Failed: myresWrapper.isWrapperFor(TCKServletResponsesubWrapper.class)");
    }

    if (!pass) {
      response.getWriter().println("Test Failed.");
    } else {
      response.getWriter().println("Test Passed.");
    }
  }
}
