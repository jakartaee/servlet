/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.api.jakarta_servlet.dispatchertype;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void valuesTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();

    DispatcherType[] types = jakarta.servlet.DispatcherType.values();
    DispatcherType[] expected_types = { DispatcherType.FORWARD,
        DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,
        DispatcherType.ERROR };

    int i = 0;
    for (DispatcherType type : types) {
      if (!type.equals(expected_types[i])) {
        pw.println(
            "The DispatcherType value at position " + i + " is incorrect.");
        pw.println("Expecting " + expected_types[i] + "; got " + type + ".");
        pass = false;
      }
      i++;
    }
    ServletTestUtil.printResult(pw, pass);
  }

  public void valueOfTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();

    String[] types = { "FORWARD", "INCLUDE", "REQUEST", "ASYNC", "ERROR" };
    DispatcherType[] expected_types = { DispatcherType.FORWARD,
        DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,
        DispatcherType.ERROR };

    int i = 0;
    for (DispatcherType type : expected_types) {
      if (!type.equals(DispatcherType.valueOf(types[i]))) {
        pw.println("DispatcherType.valueOf does not work correctly.");
        pw.println("Expecting " + expected_types[i] + "; got "
            + DispatcherType.valueOf(types[i]) + ".");
        pass = false;
      }
      i++;
    }
    ServletTestUtil.printResult(pw, pass);
  }

  public void valueOfNullTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();
    String name = null;

    try {
      DispatcherType.valueOf(name);
      pass = false;
      pw.println("DispatcherType.valueOf(null) does not work correctly.");
      pw.println("Expected NullPointerException not thrown.");
    } catch (NullPointerException npe) {
      pw.println("Test Passed. Expected NullPointerException thrown for name ="
          + name + ".");
    }

    ServletTestUtil.printResult(pw, pass);
  }

  public void valueOfInvalidTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean pass = true;
    PrintWriter pw = response.getWriter();

    String[] names = { "Forward", " INCLUDE", "REQUEST ", "ASYNc", "ERROr ",
        "bogus" };

    for (String name : names) {
      try {
        DispatcherType.valueOf(name);
        pass = false;
        pw.println(
            "DispatcherType.valueOf(bad dispatchertype) does not work correctly.");
        pw.println("Expected IllegalArgumentException not thrown.");
      } catch (IllegalArgumentException ile) {
        pw.println(
            "Test Passed. Expected IllegalArgumentException thrown for name ="
                + name + ".");
      }
    }
    ServletTestUtil.printResult(pw, pass);
  }
}
