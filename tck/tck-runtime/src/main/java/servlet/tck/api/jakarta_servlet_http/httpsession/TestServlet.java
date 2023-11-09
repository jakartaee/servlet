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

package servlet.tck.api.jakarta_servlet_http.httpsession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TestServlet extends HttpTCKServlet {

  public void getSession(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession(true);
    ServletTestUtil.printResult(pw, true);
  }

  public void getSessionMax(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession(true);
    session.setMaxInactiveInterval(10);
    ServletTestUtil.printResult(pw, true);
  }

  public void getCreationTimeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    long time = session.getCreationTime();
    Date date = new Date(time);

    if (date == null) {
      passed = false;
      pw.println("getCreationTime() returned an invalid result ");
      pw.println("The date returned was a null");
      pw.println("Actual result from request= |" + time + "|");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void getCreationTimeIllegalStateExceptionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();
    try {
      long time = session.getCreationTime();
      passed = false;
      pw.println("getCreationTime() did not generate an IllegalStateException");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void getIdTestServlet(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    if (session.getId() == null) {
      passed = false;
      pw.println("getId() returned a null result ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getIdIllegalStateExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.getId();
      passed = true;
    } catch (Throwable t) {
      passed = false;
      pw.println("Exception thrown" + t.getMessage());
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getLastAccessedTimeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    Date currentDate = new Date();
    long currentTime = currentDate.getTime();
    long testTime = session.getLastAccessedTime();

    // ok if within a second
    if (testTime > currentTime) {
      passed = false;
      pw.println("getLastAccessedTime() returned the wrong result");
      pw.println("expected results= <=" + currentTime);
      pw.println("actual result=" + testTime);
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getLastAccessedTimeIllegalStateExceptionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();
    try {
      session.getLastAccessedTime();
      passed = false;
      pw.println(
          "getLastAccessedTime() should have generated an IllegalStateException");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getLastAccessedTimeSetGetTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    long beforeTime = session.getLastAccessedTime();

    session.setAttribute("name", "value");
    session.getAttribute("name");

    long afterTime = session.getLastAccessedTime();

    if (beforeTime != afterTime) {
      passed = false;
      pw.println("getLastAccessedTime() returned the wrong result");
      pw.println("expected result= " + beforeTime);
      pw.println("actual result=" + afterTime);
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getMaxInactiveIntervalTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    int expectedResult = 500000;
    session.setMaxInactiveInterval(expectedResult);
    int result = session.getMaxInactiveInterval();

    if (result != expectedResult) {
      passed = false;
      pw.println("getMaxInactiveInterval() returned incorrect result ");
      pw.println("Expected result = " + expectedResult + " ");
      pw.println("Actual result = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributeNamesTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    String attribute1 = "attribute1";
    String attribute2 = "attribute2";
    String[] expected = {attribute1, attribute2};

    // Binding values with the Session
    session.setAttribute(attribute1, "value1");
    session.setAttribute(attribute1, "value2");
    session.setAttribute(attribute2, "value1");

    Enumeration e = session.getAttributeNames();
    if (e != null) {
      if (!ServletTestUtil.checkEnumeration(e, expected, false, false)) {
        passed = false;
        ServletTestUtil.printFailureData(pw, e, expected);
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getAttrubuteNames() returned an empty enumeration");
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void getAttributeNamesIllegalStateExceptionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.getAttributeNames();
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.setAttribute("attribute", "value1");
    if (session.getAttribute("attribute") == null) {
      passed = false;
      pw.println("getAttribute(object) returned a null result");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributeIllegalStateExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    session.invalidate();

    try {
      session.getAttribute("object");
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getServletContextTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    if (session.getServletContext() == null) {
      passed = false;
      pw.println("getServletContext method returned null");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void invalidateTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.setAttribute("attribute1", "value1");
    session.invalidate();
    try {
      Object o = session.getAttribute("Attribute1");
      passed = false;
      pw.println("invalidate() did not invalidate the session");
      pw.println("The attribute returned was = " + o);
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void invalidateIllegalStateExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.invalidate();
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void isNewTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);

    if (session.isNew() != true) {
      passed = false;
      pw.println("isNew() returned a false result");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void isNewIllegalStateExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.isNew();
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void removeAttributeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.setAttribute("attribute1", "value1");
    session.removeAttribute("attribute1");
    Object obj = session.getAttribute("attribute1");

    if (obj != null) {
      passed = false;
      pw.println("getAttribute(object) returned a non-null result");
      pw.println(" Actual result = |" + (String) obj + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void removeAttributeDoNothingTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.removeAttribute("attribute1");
    Object obj = session.getAttribute("attribute1");

    if (obj != null) {
      passed = false;
      pw.println("getAttribute(object) returned a non-null result");
      pw.println(" Actual result = |" + (String) obj + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void removeAttributeIllegalStateExceptionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.removeAttribute("object");
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    String attribute1 = "attribute1";
    String value1 = "value1";
    String value2 = "value2";

    session.setAttribute(attribute1, value1);
    session.setAttribute(attribute1, value2);
    String result = (String) session.getAttribute(attribute1);

    if (!result.equals(value2)) {
      passed = false;
      pw.println(
          "getAttribute(" + attribute1 + ") returned an incorrect result");
      pw.println(" Expected result = " + value2);
      pw.println(" Actual result = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeNullTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    String attribute1 = "attribute1";
    String value1 = "value1";
    String value2 = null;

    session.setAttribute(attribute1, value1);
    session.setAttribute(attribute1, value2);
    String result = (String) session.getAttribute(attribute1);

    if (result != null) {
      passed = false;
      pw.println(
          "getAttribute(" + attribute1 + ") returned an incorrect result");
      pw.println(" Expected result = null");
      pw.println(" Actual result = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeIllegalStateExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    session.invalidate();

    try {
      session.setAttribute("attribute", "value");
      passed = false;
      pw.println("Error: IllegalStateException should have been thrown");
    } catch (Throwable t) {
      if (t instanceof IllegalStateException) {
        passed = true;
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of IllegalStateException.");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setMaxInactiveIntervalTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    HttpSession session = request.getSession(true);
    int expectedResult = 500000;
    session.setMaxInactiveInterval(expectedResult);
    int result = session.getMaxInactiveInterval();

    if (result != expectedResult) {
      passed = false;
      pw.println("getMaxInactiveInterval() returned incorrect result ");
      pw.println("Expected result = " + expectedResult + " ");
      pw.println("Actual result = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
