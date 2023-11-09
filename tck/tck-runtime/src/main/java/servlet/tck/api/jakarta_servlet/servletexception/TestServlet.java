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

package servlet.tck.api.jakarta_servlet.servletexception;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void getRootCause(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;
    ServletException se = new ServletException(new Throwable("cause"));

    if (se.getRootCause() instanceof Throwable) {
      passed = true;
    } else {
      passed = false;
      pw.println(
          "ServletException.getRootCause() did not thrown an instance of Throwable");
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void servletExceptionConstructor1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;
    // construct and throw
    try {
      throw new ServletException();
    } catch (Throwable t) { // catching it here itself
      if (t instanceof ServletException) {
        passed = true;
      } else {
        passed = false;
        pw.println("Exception thrown was not of type ServletException");
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletExceptionConstructor2(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    try {
      throw new ServletException("Exceptional");
    } catch (Throwable t) {
      if (t instanceof ServletException) {
        if ("Exceptional".equals(t.getMessage())) {
          passed = true;
        } else {
          passed = false;
          pw.println("Exception did not contain Exceptional");
        }
      } else {
        passed = false;
        pw.println("Exception thrown was not of type ServletException(String)");
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletExceptionConstructor3(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;
    ServletException se = null;

    // construct one and throw
    try {
      se = new ServletException(new Throwable("irrelevant"));
      throw se;
    } catch (Throwable t) {
      if (t instanceof ServletException) {
        Throwable rootCause = se.getRootCause();

        if (rootCause != null) {
          if ("irrelevant".equals(rootCause.getMessage())) {
            passed = true;
          } else {
            passed = false;
            pw.println("Exception did not contain irrelevant");
          }
        } else {
          passed = false;
          pw.println(
              "rootCause message is null, expecting it to be 'irrelevant'");
        }

      } else {
        passed = false;
        pw.println("Exception thrown is not of type ServletException");
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletExceptionConstructor4(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;
    ServletException se = null;

    // construct and throw
    try {
      se = new ServletException("Exceptional", new Throwable("cos"));
      throw se;
    } catch (Throwable t) {
      if (t instanceof ServletException) {
        Throwable cause = se.getRootCause();
        String mesg = se.getMessage();

        if (cause != null && mesg != null) {
          if ("Exceptional".equals(mesg) && "cos".equals(cause.getMessage())) {
            passed = true;
          } else {
            passed = false;
            pw.println(
                "Exception did not contain Exceptional and a cause of cos");
            pw.println("Actual exception was |" + mesg + "|");
            pw.println("Actual cause was |" + cause.getMessage() + "|");
          }
        } else {
          passed = false;
          pw.println("rootCause message thrown by Exception was null");
        }
      } else {
        passed = false;
        pw.println("Exception thrown was not an instance of ServletException");
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
