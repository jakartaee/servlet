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

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletoutputstream;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  /**
   * Test for println (java.lang.String) method
   **/

  public void print_StringTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();

      sos.print("some ");
      sos.print("text");
      sos.print("\n");
    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(boolean) */

  public void print_booleanTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();
      sos.print(true);
      sos.print(true);
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(char) */

  public void print_charTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();
      sos.print('T');
      sos.print('E');
      sos.print('X');
      sos.print('T');
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(double) */

  public void print_doubleTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    double doubleval = 12345.6;

    try {
      sos = response.getOutputStream();
      sos.print(doubleval);
      sos.print(doubleval);
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(float) */

  public void print_floatTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    float floatval = 1234.5f;

    try {
      sos = response.getOutputStream();
      sos.print(floatval);
      sos.print(floatval);
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(int) */

  public void print_intTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    int num = 1;

    try {
      sos = response.getOutputStream();
      sos.print(num);
      sos.print(num);
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* print(long) */

  public void print_longTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    long ago = 1234567890;

    try {
      sos = response.getOutputStream();
      sos.print(ago);
      sos.print(ago);
      sos.print("\n");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /**
   * Test for println () method
   **/

  public void printlnTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();

      sos.print("some test ");
      sos.println();
      sos.print("text");
      sos.println();

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /**
   * Test for println (java.lang.String) method
   **/

  public void println_StringTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();

      sos.println("some");
      sos.println("text");

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(boolean) */

  public void println_booleanTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();
      sos.println(true);
      sos.println(true);

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(char) */

  public void println_charTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;

    try {
      sos = response.getOutputStream();
      sos.println('T');
      sos.println('E');
      sos.println('X');
      sos.println('T');

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(double) */

  public void println_doubleTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    ServletOutputStream sos = null;
    double doubleval = 12345.6;

    try {
      sos = response.getOutputStream();
      sos.println(doubleval);
      sos.println(doubleval);

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(float) */

  public void println_floatTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    ServletOutputStream sos = null;
    float floatval = 1234.5f;

    try {
      sos = response.getOutputStream();
      sos.println(floatval);
      sos.println(floatval);

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(int) */

  public void println_intTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    int num = 1;

    try {
      sos = response.getOutputStream();
      sos.println(num);
      sos.println(num);

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }

  /* println(long) */

  public void println_longTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    ServletOutputStream sos = null;
    long ago = 1234567890;

    try {
      sos = response.getOutputStream();
      sos.println(ago);
      sos.println(ago);

    } catch (Throwable t) {
      ServletTestUtil.printResult(sos, false);
      throw new ServletException(t);
    }
  }
}
