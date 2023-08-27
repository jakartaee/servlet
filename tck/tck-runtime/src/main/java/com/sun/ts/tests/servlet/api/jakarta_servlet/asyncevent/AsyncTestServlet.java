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

package com.sun.ts.tests.servlet.api.jakarta_servlet.asyncevent;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AsyncTestServlet extends GenericTCKServlet {

  public void constructorTest1(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac);
    if (ae == null) {
      response.getWriter().println(
          "The constructor for AsyncEvent(AsyncContext) returned a null. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void constructorTest2(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response);
    if (ae == null) {
      response.getWriter().println(
          "The constructor for AsyncEvent(AsyncContext, ServletRequest, ServletResponse) returned a null. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void constructorTest3(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, new IOException("AsyncEvent"));
    if (ae == null) {
      response.getWriter().println(
          "The constructor for AsyncEvent(AsyncContext, Throwable) returned a null. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void constructorTest4(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response,
        new IOException("AsyncEvent"));
    if (ae == null) {
      response.getWriter().println(
          "The constructor for AsyncEvent(AsyncContext, Throwable) returned a null. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void getSuppliedRequestTest1(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response);
    if (request != ae.getSuppliedRequest()) {
      response.getWriter()
          .println("getSuppliedRequest() returned are incorrect. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void getSuppliedRequestTest2(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response,
        new IOException("AsyncEvent"));
    if (request != ae.getSuppliedRequest()) {
      response.getWriter()
          .println("getSuppliedRequest() returned are incorrect. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void getSuppliedResponseTest1(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response);
    if (response != ae.getSuppliedResponse()) {
      response.getWriter()
          .println("getSuppliedResponse() returned are incorrect. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void getSuppliedResponseTest2(ServletRequest request,
      ServletResponse response) throws IOException {
    AsyncContext ac = request.startAsync();
    AsyncEvent ae = new AsyncEvent(ac, request, response,
        new IOException("AsyncEvent"));
    if (response != ae.getSuppliedResponse()) {
      response.getWriter()
          .println("getSuppliedResponse() returned are incorrect. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }

  public void getThrowableTest(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();
    IOException ie = new IOException("AsyncEvent");
    AsyncEvent ae = new AsyncEvent(ac, request, response, ie);
    if (ie != ae.getThrowable()) {
      response.getWriter()
          .println("getThrowable() returned are incorrect. Test FAILED");
    } else {
      response.getWriter().println("Test PASSED");
    }
    ac.complete();
  }
}
