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
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.asynccontext;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AsyncTestServlet extends HttpTCKServlet {

  // Test for AsyncContext.dispatch()
  public void dispatchZeroArgTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_dispatchZeroArgTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchZeroArgTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      ac.dispatch();
    }
  }

  // Test for AsyncContext.dispatch(ServletContext, String path)
  public void dispatchContextPathTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String path = "/async/AsyncTests?testname=asyncTest";

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchContextPathTest");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    ac.dispatch(request.getServletContext(), path);
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String path = "/async/AsyncTests?testname=asyncTest";

    AsyncContext ac = request.startAsync();
    if (ac.getRequest() == request) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.createListener and
  // AsyncContext.addListener(AsyncListener)
  public void asyncListenerTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet_http.asynccontext.ACListener.class);
    ac.addListener(acl);

    ac.complete();
  }

  // Test for AsyncContext.setTimeout and AsyncContext.getTimeout
  public void timeOutTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    long timeout_set = 5015L;
    long timeout_actual;

    AsyncContext ac = request.startAsync();
    response.getWriter().println("Default timeout: " + ac.getTimeout());

    ac.setTimeout(timeout_set);
    timeout_actual = ac.getTimeout();

    if (timeout_actual == timeout_set) {
      response.getWriter().println("Test PASSED.");
    } else {
      response.getWriter()
          .println("Test FAILED.  setTimeout to " + timeout_set);
      response.getWriter().println("getTimeout returned " + timeout_actual);
    }

    ac.complete();
  }

  // Test for AsyncContext.createListener,
  // AsyncContext.addListener(AsyncListener), and timeout
  public void timeOutTest1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    long timeout_set = 5015L;

    AsyncContext ac = request.startAsync();
    response.getWriter().println("Default timeout: " + ac.getTimeout());
    AsyncListener acl2 = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet_http.asynccontext.ACListener2.class);
    ac.addListener(acl2);

    ac.setTimeout(timeout_set);

    try {
      Thread.sleep(timeout_set * 2);
    } catch (InterruptedException ex) {
      response.getWriter()
          .println("Test FAILED with exception: " + ex.getMessage());
    }
  }

  // Negative test for AsyncContext.createListener
  public void asyncListenerTest1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    long timeout_set = 5015L;

    AsyncContext ac = request.startAsync();
    response.getWriter().println("Default timeout: " + ac.getTimeout());

    try {
      AsyncListener acl = ac.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet_http.asynccontext.ACListenerBad.class);
      response.getWriter()
          .println("Test FAILED without throwing expected exception.");
    } catch (ServletException ex) {
      response.getWriter()
          .println("Test PASSED with exception: " + ex.getMessage());
    } catch (Exception ex1) {
      response.getWriter().println(
          "Test FAILED with wrong type exception: " + ex1.getMessage());
    }
    ac.complete();
  }

  // Test for AsyncContext.createListener and
  // AsyncContext.addListener(AsyncListener, ServletRequest, ServletResponse)
  public void asyncListenerTest2(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet_http.asynccontext.ACListener.class);
    ac.addListener(acl, request, response);

    ac.complete();
  }

  // Test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    if (ac.hasOriginalRequestAndResponse()) {
      response.getWriter().println(
          "Test PASSED. AsyncContext.hasOriginalRequestAndRespons()=true");
    } else {
      response.getWriter().println(
          "Test FAILED. AsyncContext.hasOriginalRequestAndRespons()=false");
    }
    ac.complete();
  }

  // Test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync(request, response);
    if (ac.hasOriginalRequestAndResponse()) {
      response.getWriter().println(
          "Test PASSED. AsyncContext.hasOriginalRequestAndRespons()=true");
    } else {
      response.getWriter().println(
          "Test FAILED. AsyncContext.hasOriginalRequestAndRespons()=false");
    }
    ac.complete();
  }

  // Negative test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest2(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync(new RequestWrapper(request),
        new ResponseWrapper(response));
    if (ac.hasOriginalRequestAndResponse()) {
      response.getWriter().println(
          "Test FAILED. AsyncContext.hasOriginalRequestAndRespons()=true");
    } else {
      response.getWriter().println(
          "Test PASSED. AsyncContext.hasOriginalRequestAndRespons()=false");
    }
    ac.complete();
  }

  // Negative test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest3(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync(new RequestWrapper(request), response);
    if (ac.hasOriginalRequestAndResponse()) {
      response.getWriter().println(
          "Test FAILED. AsyncContext.hasOriginalRequestAndRespons()=true");
    } else {
      response.getWriter().println(
          "Test PASSED. AsyncContext.hasOriginalRequestAndRespons()=false");
    }
    ac.complete();
  }

  // Negative test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest4(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync(request,
        new ResponseWrapper(response));
    if (ac.hasOriginalRequestAndResponse()) {
      response.getWriter().println(
          "Test FAILED. AsyncContext.hasOriginalRequestAndRespons()=true");
    } else {
      response.getWriter().println(
          "Test PASSED. AsyncContext.hasOriginalRequestAndRespons()=false");
    }
    ac.complete();
  }
}
