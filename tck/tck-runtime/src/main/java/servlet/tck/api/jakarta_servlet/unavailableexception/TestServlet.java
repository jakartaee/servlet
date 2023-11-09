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

package servlet.tck.api.jakarta_servlet.unavailableexception;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.UnavailableException;

public class TestServlet extends GenericTCKServlet {

  public void getUnavailableSecondsTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    // Constructing exception with unavailable secs 20

    UnavailableException ue = new UnavailableException("Message", 20);

    int secs = ue.getUnavailableSeconds();

    if ((secs == 20) || (secs < 0)) {
      passed = true;
    } else {
      passed = false;
      pw.println("getUnavailableSeconds() method returned incorrect result");
      pw.println("Expected result -> > 0 or == 20");
      pw.println("Actual result =|" + secs + "|");
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void unavailableException_Constructor1Test(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    String expected = "Excpetional";
    UnavailableException ue = new UnavailableException(expected);

    try {
      throw ue;
    } catch (Throwable t) {
      if (t instanceof UnavailableException) {
        String actual = t.getMessage();

        if (actual.equals(expected)) {
          passed = true;
        } else {
          passed = false;
          pw.println(
              "getUnavailableSeconds() method returned incorrect result");
          pw.println("Expected result =" + expected);
          pw.println("Actual result =" + actual);
        }
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of UnavailableException .");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * UnavailableException(String mesg,int sec) constructs an
   * UnavailabaleException object for the specified servlet. This constructor
   * reports Temporary Unavailability
   */

  public void unavailableException_Constructor2Test(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    /**
     * Constructing one and throwing it. catching the exception
     */

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    String expectedResult1 = "Exceptional";
    int expectedResult2 = 20;
    UnavailableException ue = new UnavailableException(expectedResult1,
        expectedResult2);

    try {
      throw ue;
    } catch (Throwable t) {
      if (t instanceof UnavailableException) {
        int actual2 = ((UnavailableException) t).getUnavailableSeconds();

        String actual1 = t.getMessage();

        if (actual2 == expectedResult2 && actual1.equals(expectedResult1)) {
          passed = true;
        } else {
          if (!actual1.equals(expectedResult1)) {
            passed = false;
            pw.println("getMessage() method returned incorrect result");
            pw.println("Expected result =" + expectedResult1);
            pw.println("Actual result =" + actual1);
          }

          if (actual2 != expectedResult2) {
            passed = false;
            pw.println(
                "getUnavailableSeconds() method returned incorrect result");
            pw.println("Expected result =" + expectedResult2);
            pw.println("Actual result =" + actual2);
          }
        }
      } else {
        passed = false;
        pw.println(
            "Exception thrown, but was not an instance of UnavailableException .");
        pw.println("instead received: " + t.getClass().getName());
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void isPermanentTest(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    UnavailableException ue = new UnavailableException("Unavailable1", 20);
    UnavailableException ud = new UnavailableException("Unavailable2");

    boolean result1 = ue.isPermanent();
    boolean result2 = ud.isPermanent();

    if (result1 == false && result2) {
      passed = true;
    } else {
      if ( result1) {
        passed = false;
        pw.println(
            "isPermanent() failed to detect that the servlet was not initially unavailable");
      }

      if (result2 != true) {
        passed = false;
        pw.println(
            "isPermanent() failed to detect that the servlet had become unavailable");
      }

    }
    ServletTestUtil.printResult(pw, passed);

  }
}
