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
package servlet.tck.api.jakarta_servlet_http.servletcontext303;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.tck.api.common.sharedfiles.HSessionAttributeListener;
import servlet.tck.api.common.sharedfiles.HSessionListener;

public class TestServlet extends HttpTCKServlet {

  public void negativeaddHListenerClassTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          HSessionListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
      }
      exf.printStackTrace(pw);
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddHListenerStringTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "servlet.tck.api.common.sharedfiles.HSessionListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
      }
      exf.printStackTrace(pw);
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddHAListenerClassTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          HSessionAttributeListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
      }
      exf.printStackTrace(pw);
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddHAListenerStringTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "servlet.tck.api.common.sharedfiles.HSessionAttributeListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
      }
      exf.printStackTrace(pw);
      ServletTestUtil.printResult(pw, passed);
    }
  }
}
