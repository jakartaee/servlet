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

import jakarta.servlet.ServletContext;
import servlet.tck.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class DispatchTestServlet extends GenericTCKServlet {

  public static String DISPATCH_TEST1_CTX_KEY = "DISPATCH_TEST1_CTX_KEY";

  public static String DISPATCH_TEST_CTX_KEY = "DISPATCH_TEST_CTX_KEY";

  public static String getDispatcherContextRoot() {
    return System.getProperty(DISPATCH_TEST_CTX_KEY, "/servlet_js_dispatchtest_web");
  }

  public static String getDispatcher1ContextRoot() {
    return System.getProperty(DISPATCH_TEST1_CTX_KEY, "/servlet_js_dispatchtest1_web");
  }

  // Test for AsyncContext.dispatch()
  public void dispatchReturnTest(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_dispatchReturnTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch(request, response)
  public void dispatchReturnTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_dispatchReturnTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch(String)
  public void dispatchReturnTest2(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest2");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(String)
  public void dispatchReturnTest3(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest3");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext, String)
  public void dispatchReturnTest4(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest4");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ServletContext context = request.getServletContext().getContext(getDispatcher1ContextRoot());
    if (context!=null) {
      ac.dispatch(context,"/DispatchTests10?testname=dispatchTest10");
    }
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext, String)
  public void dispatchReturnTest5(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchReturnTest5");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ServletContext servletContext = request.getServletContext().getContext(getDispatcher1ContextRoot());
    if(servletContext!=null){
      ac.dispatch(servletContext, "/DispatchTests10?testname=dispatchTest10");
    }
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch() when startAsync called twice
  public void startAsyncAgainTest(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "LAST");
      response.getWriter()
          .println("Before second dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("second dispatch return=" + System.currentTimeMillis());
    } else if ("LAST".equals(where)) {
      response.getWriter()
          .println("After second dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_AGAIN_startAsyncAgainTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch() when startAsync called twice
  public void startAsyncAgainTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "LAST");
      response.getWriter()
          .println("Before second dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("second dispatch return=" + System.currentTimeMillis());
    } else if ("LAST".equals(where)) {
      response.getWriter()
          .println("After second dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_AGAIN_startAsyncAgainTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch() when startAsync called twice
  public void startAsyncAgainTest2(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest2");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      response.getWriter()
          .println("Before complete=" + System.currentTimeMillis());
      ac.complete();
      response.getWriter()
          .println("After complete=" + System.currentTimeMillis());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest2");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch() when startAsync called twice
  public void startAsyncAgainTest3(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest3");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      response.getWriter()
          .println("Before complete=" + System.currentTimeMillis());
      ac.complete();
      response.getWriter()
          .println("After complete=" + System.currentTimeMillis());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest3");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test AsyncContext.dispatch() when startAsync() called in an asynchrounous
  // thread
  public void startAsyncAgainTest4(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest4");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      response.getWriter().println("startAsync called");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      try {
        request.startAsync();
      } catch (java.lang.IllegalStateException ex) {
        response.getWriter().println("startAsync called again");
        response.getWriter()
            .println("Expected IllegalStateException thrown" + ex.getMessage());
      }
      ac.complete();
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest4");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test AsyncContext.dispatch() when startAsync(request, response) called in
  // an asynchrounous thread
  public void startAsyncAgainTest5(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_startAsyncAgainTest5");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      response.getWriter().println("startAsync called");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      try {
        request.startAsync(request, response);
      } catch (java.lang.IllegalStateException ex) {
        response.getWriter().println("startAsync called again");
        response.getWriter()
            .println("Expected IllegalStateException thrown" + ex.getMessage());
      }
      ac.complete();
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest5");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test for AsyncContext.dispatch(URI) when startAsync called twice
  public void startAsyncAgainTest6(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest6");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    request.setAttribute("WHERE", "ASYNC");
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests1?testname=dispatchTest1");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(URI) when startAsync called twice
  public void startAsyncAgainTest7(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest7");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests2?testname=dispatchTest2");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(URI) when startAsync called twice
  public void startAsyncAgainTest8(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest8");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests3?testname=dispatchTest3");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(URI) when startAsync called twice
  public void startAsyncAgainTest9(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest9");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests4?testname=dispatchTest4");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(URI) when startAsync() called in an
  // asynchrounous thread
  public void startAsyncAgainTest10(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest10");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests5?testname=dispatchTest5");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(URI) when startAsync(request, response) called
  // in an asynchrounous thread
  public void startAsyncAgainTest11(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest11");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests6?testname=dispatchTest6");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext,URI) when startAsync called
  // twice
  public void startAsyncAgainTest12(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest12");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ServletContext servletContext = request.getServletContext().getContext(getDispatcher1ContextRoot());
    if(servletContext!=null) {
      ac.dispatch(servletContext, "/DispatchTests11?testname=dispatchTest11");
    }
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext,URI) when startAsync called
  // twice
  public void startAsyncAgainTest13(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest13");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests12?testname=dispatchTest12");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext, URI) when startAsync called
  // twice
  public void startAsyncAgainTest14(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest14");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests13?testname=dispatchTest13");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test for AsyncContext.dispatch(ServletContext, URI) when startAsync called
  // twice
  public void startAsyncAgainTest15(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest15");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests14?testname=dispatchTest14");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(ServletContext, URI) when startAsync() called in
  // an asynchrounous thread
  public void startAsyncAgainTest16(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest16");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests15?testname=dispatchTest15");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(ServletContext, URI) when startAsync(request,
  // response) called in an asynchrounous thread
  public void startAsyncAgainTest17(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest17");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ServletContext servletContext = request.getServletContext().getContext(getDispatcher1ContextRoot());
    if(servletContext!=null) {
      ac.dispatch(servletContext, "/DispatchTests16?testname=dispatchTest16");
    }
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Negative test AsyncContext.dispatch() - calling dispatch() after it already
  // happened.
  public void negativeDispatchTest(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_negativeDispatchTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());

      try {
        response.getWriter().println("dispatch again");
        ac.dispatch();
      } catch (IllegalStateException ex) {
        response.getWriter().println("dispatch() called again");
        response.getWriter()
            .println("Expected IllegalStateException thrown" + ex.getMessage());
      }
    }
  }

  // Negative test AsyncContext.dispatch() - calling dispatch() after it already
  // happened.
  public void negativeDispatchTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_negativeDispatchTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());

      try {
        response.getWriter().println("dispatch again");
        ac.dispatch();
      } catch (IllegalStateException ex) {
        response.getWriter().println("dispatch() called again");
        response.getWriter()
            .println("Expected IllegalStateException thrown" + ex.getMessage());
      }
    }
  }

  // Negative test AsyncContext.dispatch(URI) - calling dispatch(URI) after it
  // already happened.
  public void negativeDispatchTest4(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest4");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch("/DispatchTests?testname=dispatchTest");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch(URI) called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Negative test AsyncContext.dispatch(URI) - calling dispatch(URI) after it
  // already happened.
  public void negativeDispatchTest5(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest5");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch("/DispatchTests?testname=dispatchTest");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch(URI) called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Negative test AsyncContext.dispatch(ServletContext, URI)
  // calling dispatch(ServletContext, URI) after it already happened.
  public void negativeDispatchTest8(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest8");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch(
          request.getServletContext()
              .getContext(getDispatcher1ContextRoot()),
          "/DispatchTests19?testname=dispatchTest19");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch() called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Negative test AsyncContext.dispatch(ServletContext,URI)
  // calling dispatch(ServletContext, URI) after it already happened.
  public void negativeDispatchTest9(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest9");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch(
          request.getServletContext()
              .getContext(getDispatcher1ContextRoot()),
          "/DispatchTests19?testname=dispatchTest19");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch() called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Negative test AsyncContext.dispatch(ServletContext,URI)
  // calling dispatch(URI) after it already happened.
  public void negativeDispatchTest12(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest12");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch("/DispatchTests?testname=dispatchTest");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch(URI) called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Negative test AsyncContext.dispatch(ServletContext,URI)
  // calling dispatch(URI) after it already happened.
  public void negativeDispatchTest13(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_negativeDispatchTest13");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());

    try {
      response.getWriter().println("dispatch again");
      ac.dispatch("/DispatchTests?testname=dispatchTest");
    } catch (IllegalStateException ex) {
      response.getWriter().println("dispatch(URI) called again");
      response.getWriter()
          .println("Expected IllegalStateException thrown" + ex.getMessage());
    }
  }

  // Test AsyncContext.dispatch() - test dispatch after response has been
  // committed
  public void dispatchAfterCommitTest(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_dispatchAfterCommitTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchAfterCommitTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      response.flushBuffer();
      response.getWriter().println("After commmit");
      AsyncContext ac = request.startAsync();
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test AsyncContext.dispatch() - test dispatch after response has been
  // committed
  public void dispatchAfterCommitTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter()
          .println("After dispatch=" + System.currentTimeMillis());
      response.getWriter().println("ASYNC_STARTED_dispatchAfterCommitTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter()
          .println("ASYNC_NOT_STARTED_dispatchAfterCommitTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      response.flushBuffer();
      response.getWriter().println("After commmit");
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      response.getWriter()
          .println("Before dispatch=" + System.currentTimeMillis());
      ac.dispatch();
      response.getWriter()
          .println("dispatch return=" + System.currentTimeMillis());
    }
  }

  // Test AsyncContext.dispatch(URI) - test dispatch after response has been
  // committed
  public void dispatchAfterCommitTest2(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_dispatchAfterCommitTest2");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    response.flushBuffer();
    response.getWriter().println("After commmit");
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(URI) - test dispatch after response has been
  // committed
  public void dispatchAfterCommitTest3(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchAfterCommitTest3");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    response.flushBuffer();
    response.getWriter().println("After commmit");
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch("/DispatchTests?testname=dispatchTest");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(ServletContext,URI) - test dispatch after
  // response has been committed
  public void dispatchAfterCommitTest4(ServletRequest request,
      ServletResponse response) throws IOException {
    response.getWriter().println("ASYNC_NOT_STARTED_dispatchAfterCommitTest4");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    response.flushBuffer();
    response.getWriter().println("After commmit");
    AsyncContext ac = request.startAsync();
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }

  // Test AsyncContext.dispatch(ServletContext,URI) - test dispatch after
  // response has been committed
  public void dispatchAfterCommitTest5(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchAfterCommitTest5");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    response.flushBuffer();
    response.getWriter().println("After commmit");
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter()
        .println("Before dispatch=" + System.currentTimeMillis());
    ac.dispatch(
        request.getServletContext().getContext(getDispatcher1ContextRoot()),
        "/DispatchTests10?testname=dispatchTest10");
    response.getWriter()
        .println("dispatch return=" + System.currentTimeMillis());
  }
}
