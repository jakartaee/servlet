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
package servlet.tck.api.jakarta_servlet.dispatchtest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class DispatchTests1 extends GenericServlet {

  private static final String TEST_HEADER = "testname";

  private static final Class[] TEST_ARGS = {ServletRequest.class,
      ServletResponse.class};

  public void service(ServletRequest req, ServletResponse res)
      throws ServletException, IOException {
    String test = req.getParameter(TEST_HEADER);
    try {
      Method method = this.getClass().getMethod(test, TEST_ARGS);
      method.invoke(this, new Object[]{req, res});
    } catch (InvocationTargetException ite) {
      throw new ServletException(ite.getTargetException());
    } catch (NoSuchMethodException nsme) {
      throw new ServletException("Test: " + test + " does not exist");
    } catch (Throwable t) {
      throw new ServletException("Error executing test: " + test, t);
    }
  }

  public void dispatchTest1(ServletRequest request, ServletResponse response)
      throws IOException {
    response.getWriter()
        .println("After dispatch=" + System.currentTimeMillis());
    response.getWriter().println("ASYNC_STARTED_dispatchTest1");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    response.getWriter()
        .println("REQUEST_URI=" + request.getAttribute("REQUEST_URI"));
    response.getWriter()
        .println("CONTEXT_PATH=" + request.getAttribute("CONTEXT_PATH"));
    response.getWriter()
        .println("PATH_INFO=" + request.getAttribute("PATH_INFO"));
    response.getWriter()
        .println("SERVLET_PATH=" + request.getAttribute("SERVLET_PATH"));
    response.getWriter()
        .println("QUERY_STRING=" + request.getAttribute("QUERY_STRING"));
    response.getWriter().println(
        "ASYNC_REQUEST_URI=" + request.getAttribute("ASYNC_REQUEST_URI"));
    response.getWriter().println(
        "ASYNC_CONTEXT_PATH=" + request.getAttribute("ASYNC_CONTEXT_PATH"));
    response.getWriter()
        .println("ASYNC_PATH_INFO=" + request.getAttribute("ASYNC_PATH_INFO"));
    response.getWriter().println(
        "ASYNC_SERVLET_PATH=" + request.getAttribute("ASYNC_SERVLET_PATH"));
    response.getWriter().println(
        "ASYNC_QUERY_STRING=" + request.getAttribute("ASYNC_QUERY_STRING"));

    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before second dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("second dispatch return=" + System.currentTimeMillis());
  }
}
