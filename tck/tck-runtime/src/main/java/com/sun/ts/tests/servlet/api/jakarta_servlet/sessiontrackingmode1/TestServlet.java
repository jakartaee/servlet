/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.api.jakarta_servlet.sessiontrackingmode1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.SessionTrackingMode;

public class TestServlet extends GenericTCKServlet {

  public void setSessionTrackingModes1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean passed = true;
    PrintWriter pw = response.getWriter();

    ServletContext context = request.getServletContext();

    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<SessionTrackingMode>();
    complete.add(SessionTrackingMode.COOKIE);

    Set<SessionTrackingMode> complete_set = new HashSet(complete);

    try {
      context.setSessionTrackingModes(complete_set);
      passed = false;
      pw.println("No expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalArgumentException ilae) {
      pw.println("IllegalArgumentException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalStateException ilex) {
      pw.println("Expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (Throwable tr) {
      passed = false;
      pw.println("Unexpected Exception throw when "
          + "setSessionTrackingModes is called.");
      pw.println(tr.getMessage());
      pw.println(tr.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setSessionTrackingModes2(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean passed = true;
    PrintWriter pw = response.getWriter();

    ServletContext context = request.getServletContext();

    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<SessionTrackingMode>();
    complete.add(SessionTrackingMode.URL);

    Set<SessionTrackingMode> complete_set = new HashSet(complete);

    try {
      context.setSessionTrackingModes(complete_set);
      passed = false;
      pw.println("No expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalArgumentException ilae) {
      pw.println("IllegalArgumentException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalStateException ilex) {
      pw.println("Expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (Throwable tr) {
      passed = false;
      pw.println("Unexpected Exception throw when "
          + "setSessionTrackingModes is called.");
      pw.println(tr.getMessage());
      pw.println(tr.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setSessionTrackingModes3(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean passed = true;
    PrintWriter pw = response.getWriter();

    ServletContext context = request.getServletContext();

    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<SessionTrackingMode>();
    complete.add(SessionTrackingMode.SSL);

    Set<SessionTrackingMode> complete_set = new HashSet(complete);

    try {
      context.setSessionTrackingModes(complete_set);
      passed = false;
      pw.println("No expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalArgumentException ilae) {
      pw.println("IllegalArgumentException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalStateException ilex) {
      pw.println("Expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (Throwable tr) {
      passed = false;
      pw.println("Unexpected Exception throw when "
          + "setSessionTrackingModes is called.");
      pw.println(tr.getMessage());
      pw.println(tr.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setSessionTrackingModes4(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    Boolean passed = true;
    PrintWriter pw = response.getWriter();

    ServletContext context = request.getServletContext();

    List<SessionTrackingMode> complete = new CopyOnWriteArrayList<SessionTrackingMode>();
    complete.add(SessionTrackingMode.COOKIE);
    complete.add(SessionTrackingMode.URL);

    Set<SessionTrackingMode> complete_set = new HashSet(complete);

    try {
      context.setSessionTrackingModes(complete_set);
      passed = false;
      pw.println("No expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalArgumentException ilae) {
      pw.println("IllegalArgumentException throw when "
          + "setSessionTrackingModes is called");
    } catch (IllegalStateException ilex) {
      pw.println("Expected IllegalStateException throw when "
          + "setSessionTrackingModes is called");
    } catch (Throwable tr) {
      passed = false;
      pw.println("Unexpected Exception throw when "
          + "setSessionTrackingModes is called.");
      pw.println(tr.getMessage());
      pw.println(tr.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
