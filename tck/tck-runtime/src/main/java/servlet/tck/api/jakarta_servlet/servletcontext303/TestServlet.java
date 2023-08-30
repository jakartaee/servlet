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
package servlet.tck.api.jakarta_servlet.servletcontext303;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import servlet.tck.api.common.sharedfiles.SCAttributeListener;
import servlet.tck.api.common.sharedfiles.SCListener;
import servlet.tck.api.common.sharedfiles.SRAttributeListener;
import servlet.tck.api.common.sharedfiles.SRListener;

public class TestServlet extends GenericTCKServlet {

  public void negativeaddSRAListenerClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          SRAttributeListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSRAListenerStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "com.sun.ts.tests.servlet.api.common.sharedfiles.SRAttributeListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSRListenerClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          SRListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSRListenerStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "com.sun.ts.tests.servlet.api.common.sharedfiles.SRListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSCAListenerClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          SCAttributeListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSCAListenerStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "com.sun.ts.tests.servlet.api.common.sharedfiles.SCAttributeListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSCListenerClassTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          SCListener.class);
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }

  public void negativeaddSCListenerStringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean passed = false;

    try {
      getServletContext().addListener(
          "com.sun.ts.tests.servlet.api.common.sharedfiles.SCListener");
    } catch (Exception exf) {
      if (exf instanceof java.lang.IllegalStateException) {
        passed = true;
        pw.print("Expected IllegalStateException is thrown: ");
      } else {
        pw.print("Unexpected Exception type is thrown: ");
        exf.printStackTrace(pw);
      }
      ServletTestUtil.printResult(pw, passed);
    }
  }
}
