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
package com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AsyncTestServlet extends GenericTCKServlet {

  // Test for AsyncContext.dispatch()
  public void dispatchZeroArgTest(ServletRequest request,
      ServletResponse response) throws IOException {

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

  // Test for AsyncContext.dispatch()
  public void dispatchZeroArgTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_dispatchZeroArgTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchZeroArgTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      ac.dispatch();
    }
  }
  // Test for AsyncContext.dispatch()

  public void dispatchZeroArgTest2(ServletRequest request,
      ServletResponse response) throws IOException {

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_dispatchZeroArgTest2");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_dispatchZeroArgTest2");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      AsyncContext ac = request.startAsync(new RequestWrapper(request),
          new ResponseWrapper(response));
      request.setAttribute("WHERE", "ASYNC");
      ac.dispatch();
    }
  }

  // Test for AsyncContext.dispatch(ServletContext, String path)
  public void dispatchContextPathTest(ServletRequest request,
      ServletResponse response) throws IOException {
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

  // Test for AsyncContext.dispatch(ServletContext, String path)
  public void dispatchContextPathTest1(ServletRequest request,
      ServletResponse response) throws IOException {
    String path = "/async/AsyncTests?testname=asyncTest";

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchContextPathTest1");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    ac.dispatch(request.getServletContext(), path);
  }

  // Test for AsyncContext.dispatch(ServletContext, String path)
  public void dispatchContextPathTest2(ServletRequest request,
      ServletResponse response) throws IOException {
    String path = "/async/AsyncTests?testname=asyncTest";

    response.getWriter().println("ASYNC_NOT_STARTED_dispatchContextPathTest2");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(new RequestWrapper(request),
        new ResponseWrapper(response));
    ac.dispatch(request.getServletContext(), path);
  }

  public void forwardTest(ServletRequest request, ServletResponse response)
      throws IOException, ServletException {

    response.getWriter().println("forwardTest");

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_forwardTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_forwardTest");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      getServletContext()
          .getRequestDispatcher("/AsyncTestServlet?testname=forwardDummy")
          .forward(request, response);
    }
  }

  public void forwardTest1(ServletRequest request, ServletResponse response)
      throws IOException, ServletException {

    response.getWriter().println("forwardTest1");

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_forwardTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_forwardTest1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
      getServletContext()
          .getRequestDispatcher("/AsyncTestServlet?testname=forwardDummy1")
          .forward(request, response);
    }
  }

  public void forwardDummy(ServletRequest request, ServletResponse response)
      throws IOException {
    response.getWriter().println("forwardDummy");

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_forwardDummy");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_forwardDummy");
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

  public void forwardDummy1(ServletRequest request, ServletResponse response)
      throws IOException {
    response.getWriter().println("forwardDummy1");

    String where = (String) request.getAttribute("WHERE");
    if ("ASYNC".equals(where)) {
      response.getWriter().println("ASYNC_STARTED_forwardDummy1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());
    } else {
      response.getWriter().println("ASYNC_NOT_STARTED_forwardDummy1");
      response.getWriter()
          .println("IsAsyncSupported=" + request.isAsyncSupported());
      response.getWriter()
          .println("IsAsyncStarted=" + request.isAsyncStarted());
      response.getWriter()
          .println("DispatcherType=" + request.getDispatcherType());

      AsyncContext ac = request.startAsync(request, response);
      request.setAttribute("WHERE", "ASYNC");
      ac.dispatch();
    }
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest(ServletRequest request, ServletResponse response)
      throws IOException {
    AsyncContext ac = request.startAsync();

    if (ac.getRequest() == request) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest1(ServletRequest request, ServletResponse response)
      throws IOException {

    AsyncContext ac = request.startAsync(request, response);
    if (ac.getRequest() == request) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest2(ServletRequest request, ServletResponse response)
      throws IOException {
    RequestWrapper newRequest = new RequestWrapper(request);
    ResponseWrapper newResponse = new ResponseWrapper(response);

    AsyncContext ac = request.startAsync(newRequest, newResponse);

    if (ac.getRequest() == newRequest) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest3(ServletRequest request, ServletResponse response)
      throws IOException {
    ResponseWrapper newResponse = new ResponseWrapper(response);

    AsyncContext ac = request.startAsync(request, newResponse);

    if (ac.getRequest() == request) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest4(ServletRequest request, ServletResponse response)
      throws IOException {
    RequestWrapper newRequest = new RequestWrapper(request);

    AsyncContext ac = request.startAsync(newRequest, response);

    if (ac.getRequest() == newRequest) {
      response.getWriter().println("getRequest() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getRequest() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest6(final ServletRequest request,
      ServletResponse response) throws IOException {
    final AsyncContext ac = request.startAsync();

    Timer asyncTimer = new Timer("AsyncTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 0);

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest7(final ServletRequest request,
      ServletResponse response) throws IOException {
    final AsyncContext ac = request.startAsync(request, response);
    Timer asyncTimer = new Timer("AsyncTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 0);

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest8(ServletRequest request, ServletResponse response)
      throws IOException {
    final RequestWrapper newRequest = new RequestWrapper(request);
    ResponseWrapper newResponse = new ResponseWrapper(response);

    final AsyncContext ac = request.startAsync(newRequest, newResponse);

    Timer asyncTimer = new Timer("AsyncTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == newRequest) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 0);

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest9(final ServletRequest request,
      ServletResponse response) throws IOException {
    ResponseWrapper newResponse = new ResponseWrapper(response);

    final AsyncContext ac = request.startAsync(request, newResponse);

    Timer asyncTimer = new Timer("AsyncTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 0);

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest10(ServletRequest request, ServletResponse response)
      throws IOException {
    final RequestWrapper newRequest = new RequestWrapper(request);

    final AsyncContext ac = request.startAsync(newRequest, response);

    Timer asyncTimer = new Timer("AsyncTimer", true);
    asyncTimer.schedule(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == newRequest) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }, 0);
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest12(final ServletRequest request,
      ServletResponse response) throws IOException {
    final AsyncContext ac = request.startAsync();

    ac.start(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });
  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest13(final ServletRequest request,
      ServletResponse response) throws IOException {
    final AsyncContext ac = request.startAsync(request, response);

    ac.start(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest14(ServletRequest request, ServletResponse response)
      throws IOException {
    final RequestWrapper newRequest = new RequestWrapper(request);
    ResponseWrapper newResponse = new ResponseWrapper(response);

    final AsyncContext ac = request.startAsync(newRequest, newResponse);

    ac.start(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == newRequest) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest15(final ServletRequest request,
      ServletResponse response) throws IOException {
    ResponseWrapper newResponse = new ResponseWrapper(response);

    final AsyncContext ac = request.startAsync(request, newResponse);

    ac.start(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == request) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });

  }

  // Test for AsyncContext.getRequest()
  public void getRequestTest16(ServletRequest request, ServletResponse response)
      throws IOException {
    final RequestWrapper newRequest = new RequestWrapper(request);

    final AsyncContext ac = request.startAsync(newRequest, response);

    ac.start(new TimerTask() {

      @Override
      public void run() {
        try {
          if (ac.getRequest() == newRequest) {
            ac.getResponse().getWriter()
                .println("getRequest() worked.  Test PASSED.");
          } else {
            ac.getResponse().getWriter()
                .println("getRequest() didnot work as expected.  Test FAILED.");
          }
          ac.complete();
        } catch (java.io.IOException ioe) {
          ioe.printStackTrace();
        }
      }
    });
  }

  // Test for AsyncContext.createListener and
  // AsyncContext.addListener(AsyncListener)
  public void asyncListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener.class);
    ac.addListener(acl);

    ac.complete();
  }

  // Negative test for AsyncContext.createListener
  public void asyncListenerTest1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    long timeout_set = 5015L;

    AsyncContext ac = request.startAsync();
    response.getWriter().println("Default timeout: " + ac.getTimeout());

    try {
      AsyncListener acl = ac.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListenerBad.class);
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
  public void asyncListenerTest2(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    AsyncContext ac = request.startAsync();
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener.class);
    ac.addListener(acl, request, response);

    ac.complete();
  }

  // Test for AsyncContext.createListener and
  // AsyncContext.addListener(AsyncListener)
  public void asyncListenerTest5(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    final AsyncContext ac = request.startAsync();

    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener.class);
    ac.addListener(acl);
    ac.start(new TimerTask() {

      @Override
      public void run() {
        ac.complete();

      }
    });
  }

  // Negative test for AsyncContext.createListener
  public void asyncListenerTest6(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    long timeout_set = 5015L;

    final AsyncContext ac = request.startAsync();
    response.getWriter().println("Default timeout: " + ac.getTimeout());

    try {
      AsyncListener acl = ac.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListenerBad.class);
      response.getWriter()
          .println("Test FAILED without throwing expected exception.");
    } catch (ServletException ex) {
      response.getWriter()
          .println("Test PASSED with exception: " + ex.getMessage());
    } catch (Exception ex1) {
      response.getWriter().println(
          "Test FAILED with wrong type exception: " + ex1.getMessage());
    }

    ac.start(new TimerTask() {

      @Override
      public void run() {
        ac.complete();

      }
    });
  }

  // Test for AsyncContext.createListener and
  // AsyncContext.addListener(AsyncListener, ServletRequest, ServletResponse)
  public void asyncListenerTest7(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    final AsyncContext ac = request.startAsync();
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener.class);
    ac.addListener(acl, request, response);

    ac.start(new TimerTask() {

      @Override
      public void run() {
        ac.complete();

      }
    });
  }

  // Test for AsyncContext.setTimeout and AsyncContext.getTimeout
  public void timeOutTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

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

  // Test for AsyncContext.setTimeout(0L) and AsyncContext.getTimeout
  public void timeOutTest1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    long timeout_set = 0L;
    long timeout_actual;

    AsyncContext ac = request.startAsync();
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
  public void timeOutTest2(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    long timeout_default;

    AsyncContext ac = request.startAsync();
    timeout_default = ac.getTimeout();
    response.getWriter().println("Default timeout: " + timeout_default);
    AsyncListener acl2 = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener2.class);
    ac.addListener(acl2);

    ac.setTimeout(timeout_default);

    try {
      Thread.sleep(timeout_default * 2);
    } catch (InterruptedException ex) {
      response.getWriter()
          .println("Test FAILED with exception: " + ex.getMessage());
    }
  }

  // Test for AsyncContext.createListener,
  // AsyncContext.addListener(AsyncListener), and AsyncContext.setTimeout(0L)
  public void timeOutTest3(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    long timeout_default;

    AsyncContext ac = request.startAsync();
    timeout_default = ac.getTimeout();
    response.getWriter().println("Default timeout: " + timeout_default);
    AsyncListener acl = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener.class);
    ac.addListener(acl);

    ac.setTimeout(0L);

    try {
      if (timeout_default * 5 < 3000000L) {
        Thread.sleep(timeout_default * 5);
      } else {
        Thread.sleep(3000000L);
      }
    } catch (InterruptedException ex) {
      response.getWriter()
          .println("Test FAILED with exception: " + ex.getMessage());
    }
    ac.complete();
  }

  // Test for AsyncContext.createListener,
  // AsyncContext.addListener(AsyncListener), and AsyncContext timeout
  public void timeOutTest4(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    long timeout_default;

    AsyncContext ac = request.startAsync();
    timeout_default = ac.getTimeout();
    response.getWriter().println("Default timeout: " + timeout_default);
    AsyncListener acl2 = ac.createListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.asynccontext.ACListener2.class);
    ac.addListener(acl2);

    try {
      Thread.sleep(timeout_default + 18000L);
    } catch (InterruptedException ex) {
      response.getWriter()
          .println("Test FAILED with exception: " + ex.getMessage());
    }
  }

  // Test for AsyncContext.hasOriginalRequestAndResponse()
  public void originalRequestTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

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
  public void originalRequestTest1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

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
  public void originalRequestTest2(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

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
  public void originalRequestTest3(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

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
  public void originalRequestTest4(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

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

  // Test for AsyncContext.getResponse()
  public void getResponseTest(ServletRequest request, ServletResponse response)
      throws IOException {
    String path = "/async/AsyncTests?testname=asyncTest";

    AsyncContext ac = request.startAsync();
    if (ac.getResponse() == response) {
      response.getWriter().println("getResponse() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getResponse() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getResponse()
  public void getResponseTest1(ServletRequest request, ServletResponse response)
      throws IOException {

    AsyncContext ac = request.startAsync(request, response);
    if (ac.getResponse() == response) {
      response.getWriter().println("getResponse() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getResponse() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getResponse()
  public void getResponseTest2(ServletRequest request, ServletResponse response)
      throws IOException {
    RequestWrapper newRequest = new RequestWrapper(request);
    ResponseWrapper newResponse = new ResponseWrapper(response);

    AsyncContext ac = request.startAsync(newRequest, newResponse);

    if (ac.getResponse() == newResponse) {
      response.getWriter().println("getResponse() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getResponse() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getResponse()
  public void getResponseTest3(ServletRequest request, ServletResponse response)
      throws IOException {
    ResponseWrapper newResponse = new ResponseWrapper(response);

    AsyncContext ac = request.startAsync(request, newResponse);

    if (ac.getResponse() == newResponse) {
      response.getWriter().println("getResponse() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getResponse() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for AsyncContext.getResponse()
  public void getResponseTest4(ServletRequest request, ServletResponse response)
      throws IOException {
    RequestWrapper newRequest = new RequestWrapper(request);

    AsyncContext ac = request.startAsync(newRequest, response);

    if (ac.getResponse() == response) {
      response.getWriter().println("getResponse() worked.  Test PASSED.");
    } else {
      response.getWriter()
          .println("getResponse() didnot work as expected.  Test FAILED.");
    }
    ac.complete();
  }

  // Test for ServletRequest.startAsync() called twice
  public void startAsyncAgainTest(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync();
    response.getWriter().println("startAsync called");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
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
  }

  // Test for ServletRequest.startAsync(request, response) called twice
  public void startAsyncAgainTest1(ServletRequest request,
      ServletResponse response) throws IOException {

    response.getWriter().println("ASYNC_NOT_STARTED_startAsyncAgainTest1");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
    response.getWriter()
        .println("DispatcherType=" + request.getDispatcherType());
    AsyncContext ac = request.startAsync(request, response);
    response.getWriter().println("startAsync called");
    response.getWriter()
        .println("IsAsyncSupported=" + request.isAsyncSupported());
    response.getWriter().println("IsAsyncStarted=" + request.isAsyncStarted());
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
  }
}
