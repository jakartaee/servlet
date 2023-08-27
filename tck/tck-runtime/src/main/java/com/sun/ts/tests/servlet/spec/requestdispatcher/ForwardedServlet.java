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

package com.sun.ts.tests.servlet.spec.requestdispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class ForwardedServlet extends GenericTCKServlet {

  private static final String TEST_HEADER = "testname";

  private static final Class[] TEST_ARGS = { ServletRequest.class,
      ServletResponse.class };

  private static final String TEST1_HEADER = "TestName";

  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    String test = req.getParameter(TEST_HEADER);
    try {
      Method method = this.getClass().getMethod(test, TEST_ARGS);
      method.invoke(this, new Object[] { req, res });
    } catch (InvocationTargetException ite) {
      throw new ServletException(ite.getTargetException());
    } catch (NoSuchMethodException nsme) {
      if (req.getAttribute(TEST1_HEADER) != null) {
        String tmp = (String) req.getAttribute(TEST1_HEADER);
        if (tmp.indexOf("attributes") < 0)
          throw new ServletException("Test: " + test + " does not exist");
        else
          attributes(req, res);
      } else
        throw new ServletException("Test: " + test + " does not exist");
    } catch (Throwable t) {
      throw new ServletException("Error executing test: " + test, t);
    }
  }

  public void thrownIOException(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    throw new IOException("IOException_from_checkedException");
  }

  public void thrownServletException(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    throw new ServletException("ServletException from thrownServletException");
  }

  public void thrownUnCheckedException(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    throw new RuntimeException("RuntimeException_from_unCheckedException");
  }

  public void thrownCheckedException(ServletRequest request,
      ServletResponse response)
      throws ServletException, IOException, ClassNotFoundException {
    throw new ClassNotFoundException(
        "ClassNotFoundException from throwCheckedException");
  }

  public void bufferContent(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("bufferContent_in_ForwardedServlet_invoked");
  }

  public void attributes(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String[] aname = { "jakarta.servlet.forward.request_uri",
        "jakarta.servlet.forward.context_path",
        "jakarta.servlet.forward.servlet_path", "jakarta.servlet.forward.path_info",
        "jakarta.servlet.forward.query_string" };

    String[] avalue = { "/servlet_spec_requestdispatcher_web/TestServlet",
        "/servlet_spec_requestdispatcher_web", "/TestServlet", "*",
        "testname=" + request.getParameter("query") };
    String[] types = { "=SET_GOOD;", "=SET_WRONGVALUE;", "=SET_BADTYPE;",
        "=SET_NO;" };
    String[] results = new String[5];

    for (int i = 0; i < aname.length; i++) {
      Object o = request.getAttribute(aname[i]);

      if (o != null) {
        if (o instanceof String) {
          String attr = (String) o;
          if (!attr.equals(avalue[i])) {
            results[i] = types[1];
            pw.println("attribute " + aname[i] + " set with incorrect value="
                + attr + ", expecting " + avalue[i]);
          } else {
            results[i] = types[0];
            pw.println(
                "attribute " + aname[i] + " set with correct value=" + attr);
          }
        } else {
          results[i] = types[2];
          pw.println("attribute " + aname[i] + " set to non-String type");
        }
      } else {
        results[i] = types[3];
        pw.println("attribute " + aname[i] + " not set");
      }
    }

    for (int i = 0; i < 5; i++) {
      pw.print(aname[i] + results[i]);
    }
    pw.println("");
  }
}
