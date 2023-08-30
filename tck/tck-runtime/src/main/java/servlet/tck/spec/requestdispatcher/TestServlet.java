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

package servlet.tck.spec.requestdispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import servlet.tck.common.servlets.HttpTCKServlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class TestServlet extends HttpTCKServlet {

  public void includeAttributes(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=attributes";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.include(request, response);
  }

  public void includeAttributes1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "include/IncludedServlet?testname=attributes";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.include(request, response);
  }

  public void includeAttributes2(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "IncludedServlet";
    RequestDispatcher rd = getServletContext().getNamedDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      request.setAttribute("TestName", "attributes");
      rd.include(request, response);
    }
  }

  public void includeIOAndServletException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Map testMap = new HashMap();
    testMap.put("IOException",
        "include/IncludedServlet?testname=thrownIOException");
    testMap.put("ServletException",
        "include/IncludedServlet?testname=thrownServletException");

    testMap.keySet().iterator();
    for (Iterator i = testMap.keySet().iterator(); i.hasNext();) {
      String type = (String) i.next();
      String path = (String) testMap.get(type);
      RequestDispatcher rd = request.getRequestDispatcher(path);

      if (rd == null)
        pw.println("Null RequestDispatcher got for path=" + path);
      else {
        try {
          rd.include(request, response);
          pw.println("Test FAILED with no propagation of " + type + " from "
              + "IncludedServlet to TestServlet.");
        } catch (Throwable t) {
          Throwable targetThrowable = ((ServletException) t).getRootCause();
          if (type.equals("IOException")) {
            if (targetThrowable instanceof IOException) {
              pw.println("Test PASSED with proper propagation of " + type
                  + " from IncludedServlet to TestServlet:"
                  + targetThrowable.getMessage());
            } else {
              pw.println("Test FAILED with propagation of " + type + " from "
                  + "IncludedServlet to TestServlet - wrong type of "
                  + "Exception caught by TestServlet: "
                  + targetThrowable.getClass().getName());
            }
          }
          if (type.equals("ServletException")) {
            if (targetThrowable instanceof ServletException) {
              ServletException se = (ServletException) targetThrowable;
              if (se.getRootCause() == null) {
                pw.println("Test PASSED with proper propagation of " + type
                    + " from IncludedServlet to TestServlet:"
                    + targetThrowable.getMessage());
              } else {
                pw.println(
                    "Test FAILED.  ServletException thrown during RD operation resulted in that "
                        + "exception being wrapped by a ServletException.  ServletExceptions must be propagated "
                        + "as is to the caller.");
              }
            } else {
              pw.println("Test FAILED with propagation of " + type + " from "
                  + "IncludedServlet to TestServlet - wrong type of "
                  + "Exception caught by TestServlet: "
                  + targetThrowable.getClass().getName());
            }
          }
        }
      }
    }
  }

  public void includeUnCheckedException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "include/IncludedServlet?testname=thrownUnCheckedException";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      try {
        rd.include(request, response);
        pw.println("Test FAILED with no propagation of RuntimeException from "
            + "IncludedServlet to TestServlet.");
      } catch (Throwable t) {
        // unwrap the throwable from the ServletException
        Throwable targetThrowable = ((ServletException) t).getRootCause();
        if (targetThrowable instanceof RuntimeException) {
          pw.println("Test PASSED.  RuntimeException properly propagated.");
        } else {
          pw.println(
              "Test FAILED with incorrect propagation of RuntimeException "
                  + "Exception from IncludedServlet to TestServlet "
                  + "- wrong type of Exception caught by TestServlet: "
                  + t.getClass().getName());
        }
      }
    }
  }

  public void includeCheckedException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "include/IncludedServlet?testname=thrownCheckedException";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      try {
        rd.include(request, response);
        pw.println("Test FAILED with no propagation of Exception from "
            + "IncludedServlet to TestServlet.");

      } catch (ServletException se) {
        if (se.getRootCause() instanceof java.lang.ClassNotFoundException)
          pw.println(
              "Test PASSED with proper propagation of ClassNotFoundException "
                  + "from IncludedServlet to TestServlet.");
      } catch (Throwable t) {
        pw.println("Test FAILED with incorrect propagation of RuntimeException "
            + "Exception from IncludedServlet to TestServlet "
            + "- wrong type of Exception caught by TestServlet: "
            + t.getClass().getName());
      }
    }
  }

  public void forwardAttributes(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=attributes&query=forwardAttributes";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void forwardAttributes1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=attributes&query=forwardAttributes1";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void forwardAttributes2(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "ForwardedServlet";
    RequestDispatcher rd = getServletContext().getNamedDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      request.setAttribute("TestName", "attributes");
      rd.forward(request, response);
    }
  }

  public void forwardAttributes6(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/MultiForwardedServlet?testname=attributes&query=forwardAttributes";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      request.setAttribute("TestName", "attributes");
      rd.forward(request, response);
    }
  }

  public void forwardIOAndServletException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Map testMap = new HashMap();
    testMap.put("IOException",
        "forward/ForwardedServlet?testname=thrownIOException");
    testMap.put("ServletException",
        "forward/ForwardedServlet?testname=thrownServletException");

    testMap.keySet().iterator();
    for (Iterator i = testMap.keySet().iterator(); i.hasNext();) {
      String type = (String) i.next();
      String path = (String) testMap.get(type);
      RequestDispatcher rd = request.getRequestDispatcher(path);

      if (rd == null)
        pw.println("Null RequestDispatcher got for path=" + path);
      else {
        try {
          rd.include(request, response);
          pw.println("Test FAILED with no propagation of " + type + " from "
              + "ForwardedServlet to TestServlet.");

        } catch (Throwable t) {
          Throwable targetThrowable = ((ServletException) t).getRootCause();
          if (type.equals("IOException")) {
            if (targetThrowable instanceof IOException) {
              pw.println("Test PASSED with proper propagation of " + type
                  + " from IncludedServlet to TestServlet:"
                  + targetThrowable.getMessage());
            } else {
              pw.println("Test FAILED with propagation of " + type + " from "
                  + "IncludedServlet to TestServlet - wrong type of "
                  + "Exception caught by TestServlet: "
                  + targetThrowable.getClass().getName());
            }
          }
          if (type.equals("ServletException")) {
            if (targetThrowable instanceof ServletException) {
              ServletException se = (ServletException) targetThrowable;
              if (se.getRootCause() == null) {
                pw.println("Test PASSED with proper propagation of " + type
                    + " from IncludedServlet to TestServlet:"
                    + targetThrowable.getMessage());
              } else {
                pw.println(
                    "Test FAILED.  ServletException thrown during RD operation resulted in that "
                        + "exception being wrapped by a ServletException.  ServletExceptions must be propagated "
                        + "as is to the caller.");
              }
            } else {
              pw.println("Test FAILED with propagation of " + type + " from "
                  + "IncludedServlet to TestServlet - wrong type of "
                  + "Exception caught by TestServlet: "
                  + targetThrowable.getClass().getName());
            }
          }
        }
      }
    }
  }

  public void forwardUnCheckedException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "forward/ForwardedServlet?testname=thrownUnCheckedException";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      try {
        rd.include(request, response);
        pw.println("Test FAILED with no propagation of RuntimeException from "
            + "ForwardedServlet to TestServlet.");
      } catch (Throwable t) {
        // unwrap the throwable from the ServletException
        Throwable targetThrowable = ((ServletException) t).getRootCause();
        if (targetThrowable instanceof RuntimeException) {
          pw.println("Test PASSED.  RuntimeException properly propagated.");
        } else {
          pw.println(
              "Test FAILED with incorrect propagation of RuntimeException "
                  + "Exception from IncludedServlet to TestServlet "
                  + "- wrong type of Exception caught by TestServlet: "
                  + t.getClass().getName());
        }
      }
    }
  }

  public void forwardCheckedException(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "forward/ForwardedServlet?testname=thrownCheckedException";
    RequestDispatcher rd = request.getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else {
      try {
        rd.include(request, response);
        pw.println("Test FAILED with no propagation of Exception from "
            + "ForwardedServlet to TestServlet.");

      } catch (ServletException se) {
        if (se.getRootCause() instanceof java.lang.ClassNotFoundException)
          pw.println(
              "Test PASSED with proper propagation of ClassNotFoundException "
                  + "from ForwardServlet to TestServlet.");
      } catch (Throwable t) {
        pw.println("Test FAILED with incorrect propagation of RuntimeException "
            + "Exception from ForwardedServlet to TestServlet "
            + "- wrong type of Exception caught by TestServlet: "
            + t.getClass().getName());
      }
    }
  }

  public void bufferContent(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=bufferContent";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    pw.println("Test FAILED if you see this message");

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void rdNoWrappingTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    // when calling the request dispatcher, the objects passed to the included
    // or forwarded servlet must be the same objects passed to the
    // RequestDispatcher

    int operation = Integer.parseInt(request.getParameter("operation"));
    System.out.println("Operation: " + operation);
    HttpServletRequestWrapper reqw = new HttpServletRequestWrapper(request);
    HttpServletResponseWrapper resw = new HttpServletResponseWrapper(response);

    ServletContext ctx = getServletConfig().getServletContext();
    ctx.setAttribute("tck.request", reqw);
    ctx.setAttribute("tck.response", resw);

    RequestDispatcher rd = request.getRequestDispatcher("/WrapServlet");

    if (rd != null) {
      switch (operation) {
      case 0:
        System.out.println("PERFORMING RD.FORWARD....");
        System.out.println("COMMITTED: " + response.isCommitted());
        rd.forward(reqw, resw);
        break;
      case 1:
        System.out.println("PERFORMING RD.INCLUDE...");
        rd.include(reqw, resw);
        break;
      }
    }
  }

  public void getRequestURIIncludeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getRequestURIIncludeTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.include(request, response);
  }

  public void getRequestURIForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getRequestURIForwardTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void getRequestURIMultiForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getRequestURIMultiForwardTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void getRequestURLIncludeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getRequestURLIncludeTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.include(request, response);
  }

  public void getRequestURLForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getRequestURLForwardTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

  public void getQueryStringIncludeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getQueryStringTestInclude";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.include(request, response);
  }

  public void getQueryStringForwardTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/other/HttpTestServlet?testname=getQueryStringTestForward";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);

    if (rd == null)
      pw.println("Null RequestDispatcher got for path=" + path);
    else
      rd.forward(request, response);
  }

}
