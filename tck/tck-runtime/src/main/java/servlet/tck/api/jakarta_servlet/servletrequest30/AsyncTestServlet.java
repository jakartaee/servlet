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
package servlet.tck.api.jakarta_servlet.servletrequest30;

import java.io.IOException;

import servlet.tck.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AsyncTestServlet extends GenericTCKServlet {

  public void getDispatcherTypeTestAsync(ServletRequest request,
      ServletResponse response) {
    String path = "/async/AsyncTests?testname=getDispatcherTypeTest";
    AsyncContext ac = request.startAsync();
    ac.dispatch(path);
  }

  public void asyncStartedTest1(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    ac.complete();
  }

  public void asyncStartedTest2(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
  }

  public void asyncStartedTest3(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    ac.complete();
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
  }

  public void asyncStartedTest4(ServletRequest request,
      ServletResponse response) {
    String path = "/async/AsyncTests?testname=isAsyncStartedTest";
    AsyncContext ac = request.startAsync();
    ac.dispatch(path);
  }

  public void isAsyncSupportedTest(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter()
        .println("isAsyncSupported=" + request.isAsyncSupported());
  }

  public void startAsyncTest(ServletRequest request, ServletResponse response)
      throws IOException {
    String path = "/async/AsyncTests?testname=getDispatcherTypeTest";
    AsyncContext ac = request.startAsync();
    ac.dispatch(path);
    try {
      request.startAsync();
      response.getWriter().println(
          "Expected IllegalStateException not thrown in AsyncTestServlet#startAsyncTest. Test FAILED.");
    } catch (IllegalStateException ise) {
      response.getWriter().println(
          "Expected IllegalStateException thrown in AsyncTestServlet#startAsyncTest. Test PASSED.");
    }
  }

  public void getAsyncContextTest(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncContext ac1 = request.getAsyncContext();
    if (ac != ac1) {
      response.getWriter()
          .println("getAsyncContext returned is different. Test FAILED.");
    } else {
      response.getWriter()
          .println("getAsyncContext returned is correct. Test PASSED.");
    }
    ac.complete();
  }
}
