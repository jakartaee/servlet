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
package servlet.tck.spec.srlistener;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpTCKServlet {

  public void includes(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=simple";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.info("In method includes");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.include(request, response);
    }
  }

  public void multipleincludes(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=includeagain";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method multipleincludes");

    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.include(request, response);
    }
  }

  public void includeforward(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=forward";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method includeforward");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.include(request, response);
    }
  }

  public void includeerror(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/include/IncludedServlet?testname=error";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method includeerror");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      pw.println("In method includeerror");
      rd.include(request, response);
    }
    if (!((String) request.getAttribute("ERROR_TEST_INCLUDE")).equals("403")) {
      pw.println("Test Failed");
    } else {
      response.sendError(403);
    }

  }

  public void forward(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=simple";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method forward");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void multipleforwards(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=forwardagain";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method multipleforward");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void forwardinclude(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=include";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method forwardinclude");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void forwarderror(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    String path = "/forward/ForwardedServlet?testname=error";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    logger.debug("In method forwarderror");
    if (rd == null) {
      pw.println("Null RequestDispatcher got for path=" + path);
    } else {
      rd.forward(request, response);
    }
  }

  public void async(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("TestServlet Invoked");
    pw.println("In method async");
    logger.debug("In method async");
    try {
      AsyncContext asyncc = request.startAsync();
      response.getWriter().println("TestServlet_Async=STARTED");
      asyncc.complete();
    } catch (IllegalStateException ilex) {
      pw.println("TestServlet_Async=NOT_STARTED");
    } catch (Exception ex) {
      pw.println("TestServlet_Async=FAILED: " + ex.getMessage());
    }
  }

  public void simpleasyncinclude(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("TestServlet Invoked");
    pw.println("In method simpleasyncinclude");
    logger.debug("In method simpleasyncinclude");
    try {
      AsyncContext asyncc = request.startAsync();
      response.getWriter().println("TestServlet_Async=STARTED");

      String path = "/include/IncludedServlet?testname=simple";
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      if (rd == null) {
        pw.println("Null RequestDispatcher got for path=" + path);
      } else {
        rd.include(request, response);
      }

      asyncc.complete();
    } catch (IllegalStateException ilex) {
      pw.println("TestServlet_Async=NOT_STARTED");
    } catch (Exception ex) {
      pw.println("TestServlet_Async=FAILED: " + ex.getMessage());
    }
  }

  public void simpleasyncforward(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("TestServlet Invoked");
    pw.println("In method simpleasyncforward");
    logger.debug("In method simpleasyncforward");
    try {
      AsyncContext asyncc = request.startAsync();
      response.getWriter().println("TestServlet_Async=STARTED");

      String path = "/forward/ForwardedServlet?testname=simple";
      RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
      if (rd == null) {
        pw.println("Null RequestDispatcher got for path=" + path);
      } else {
        rd.forward(request, response);
      }

      asyncc.complete();
    } catch (IllegalStateException ilex) {
      pw.println("TestServlet_Async=NOT_STARTED");
    } catch (Exception ex) {
      pw.println("TestServlet_Async=FAILED: " + ex.getMessage());
    }
  }

  public void simpleasyncerror(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("TestServlet Invoked");
    pw.println("In method simpleasyncerror");
    logger.debug("In method simpleasyncerror");
    try {
      AsyncContext asyncc = request.startAsync();
      response.getWriter().println("TestServlet_Async=STARTED");
      response.sendError(403);
      asyncc.complete();
    } catch (IllegalStateException ilex) {
      pw.println("TestServlet_Async=NOT_STARTED");
    } catch (Exception ex) {
      pw.println("TestServlet_Async=FAILED: " + ex.getMessage());
    }
  }

  public void error(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    logger.debug("In TestServlet, error method");
    res.sendError(403);
  }

  public void checkLogSimple(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    Boolean pass = true;
    // the last item in the expected is a result of this servlet being called
    String[] expected = { "in requestInitialized method of listener",
        "in requestDestroyed method of listener",
        "in requestInitialized method of listener" };
    int expected_len = expected.length;

    // verify results
    ArrayList result = (ArrayList) getServletContext()
        .getAttribute("arraylist");
    result.trimToSize();
    int len = result.size();
    if (len == expected_len) {
      pw.write("Got expected " + expected_len + " access times");
      for (int i = 0; i < len; i++) {
        Object val = null;
        val = (String) result.get(i);
        if (!expected[i].equals(val)) {
          pass = false;
          pw.write("Expecting " + expected[i] + " at position " + i);
          pw.write(". Got " + val + " instead.");
        } else {
          pw.write("Got expected value: " + expected[i] + " at position " + i);
        }
      }
    } else {
      pass = false;
      pw.write(
          "RequestListener is invoked/deleted more or less times than required");
      pw.write("Expect " + expected_len + " times, got " + len + ".");

      if (len > expected_len) {
        for (int i = 0; i < expected_len; i++) {
          Object val = null;
          val = (String) result.get(i);
          if (!expected[i].equals(val)) {
            pass = false;
            pw.write("Expecting " + expected[i] + " at position " + i);
            pw.write(". Got " + val + " instead.");
          } else {
            pw.write(
                "Got expected value: " + expected[i] + " at position " + i);
          }
        }

        for (int i = expected_len; i < len; i++) {
          pw.write("Extra access to Listener: " + result.get(i)
              + " at position " + i);
        }

      } else {
        for (int i = 0; i < len; i++) {
          Object val = null;
          val = (String) result.get(i);
          if (!expected[i].equals(val)) {
            pass = false;
            pw.write("Expecting " + expected[i] + " at position " + i);
            pw.write(". Got " + val + " instead.");
          } else {
            pw.write(
                "Got expected value: " + expected[i] + " at position " + i);
          }
        }

        for (int i = len; i < expected_len; i++) {
          pw.write(
              "Missing expected value: " + expected[i] + " at position " + i);
        }
      }
    }
    getServletContext().removeAttribute("arraylist");
    ServletTestUtil.printResult(pw, pass);
  }
}
