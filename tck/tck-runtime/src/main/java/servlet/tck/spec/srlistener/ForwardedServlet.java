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
 * $Id$
 */
package servlet.tck.spec.srlistener;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ForwardedServlet extends HttpServlet {

  private static final String TEST_HEADER = "testname";

  private static final Class[] TEST_ARGS = { HttpServletRequest.class,
      HttpServletResponse.class };

  public void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    String test = req.getParameter(TEST_HEADER);
    try {
      Method method = this.getClass().getMethod(test, TEST_ARGS);
      method.invoke(this, new Object[] { req, res });
    } catch (InvocationTargetException ite) {
      throw new ServletException(ite.getTargetException());
    } catch (NoSuchMethodException nsme) {
      throw new ServletException("Test: " + test + " does not exist");
    } catch (Throwable t) {
      throw new ServletException("Error executing test: " + test, t);
    }
  }

  public void simple(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    PrintWriter pw = res.getWriter();
    pw.print("ForwardedServlet Invoked, simple method");
    System.out.println("In ForwardedServlet, simple method");
    ServletTestUtil.printResult(pw, true);
  }

  public void forwardagain(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/SecondForwardedServlet?testname=simple";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    System.out.println("In forwardedServlet, forwardagain method");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void include(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=simple";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    System.out.println("In forwardedServlet, include method");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void error(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    System.out.println("In ForwardedServlet, error method");
    res.sendError(403);
  }
}
