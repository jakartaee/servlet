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
 * $Id$
 */
package servlet.tck.api.jakarta_servlet.servletconfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  /**
   * getInitParameterNames returns an Enumeration of values associated with the
   * init parameters
   */
  public void getServletConfigInitParameterNames(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    Enumeration initP = config.getInitParameterNames();

    String expectedResult1 = "Team";
    boolean expectedResult1Found = false;
    String expectedResult2 = "org";
    boolean expectedResult2Found = false;

    if (initP.hasMoreElements()) {
      while (initP.hasMoreElements()) {
        String name = (String) initP.nextElement();
        pw.println("Initialization Parameter: " + name);

        if (name.equals(expectedResult1)) {
          if (!expectedResult1Found) {
            expectedResult1Found = true;
          } else {
            passed = false;
            pw.println(
                "getInitParameterNames() method return an attribute name twice");
            pw.println(
                "The attribute already specified was " + expectedResult1);
          }
        } else if (name.equals(expectedResult2)) {
          if (!expectedResult2Found) {
            expectedResult2Found = true;
          } else {
            passed = false;
            pw.println(
                "getInitParameterNames() method return an attribute name twice");
            pw.println(
                "The attribute already specified was " + expectedResult2);
          }
        }
      }

      if (!expectedResult1Found && expectedResult2Found) {
        passed = false;
        pw.println("At least one InitParameter is not returned");
      }

    } else {
      passed = false;
      pw.println(
          "HttpServletRequest.getInitParameterNames() an empty enumeration");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletConfig getInitParameter(String)
   */
  public void getServletConfigInitParameter(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    // Init parameters specified in web.xml for
    // GetServletConfigInitParameterTest servlet
    String initParam = config.getInitParameter("Team");

    if (initParam != null) {
      if (initParam.equals("WebAccess")) {
        passed = true;
      } else {
        passed = false;
        pw.println("getInitParameter(String) gives incorrect results");
        pw.println(
            "getInitParameter(String) != 'WebAccess' as specified in web.xml for GetServletConfigInitParameterTest servlet");
      }
    } else {
      passed = false;
      pw.println("getInitParameter(String) is null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Negative test for ServletConfig getInitParameter(String)
   */
  public void getServletConfigInitParameterNull(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    // No Init parameter specified anywhere named
    // "Nothing_is_set_for_Negative_compatibility_test_only"
    String initParam = config.getInitParameter(
        "Nothing_is_set_for_Negative_compatibility_test_only");

    if (initParam != null) {
      pw.println("getInitParameter(String) gives incorrect results");
      pw.println("Expecting null; got " + initParam);
    } else {
      passed = true;
      pw.println("getInitParameter(String) is null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getServletName(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    String expected = "TestServlet";
    // get this servlets name
    String servletName = config.getServletName();

    if (servletName.indexOf(expected) > -1) {
      passed = true;
    } else {
      passed = false;
      pw.println("getServletName() did not return the correct result:");
      pw.println("Expected Result=" + expected);
      pw.println("Actual Result -> " + servletName);
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * We'll try to get the ServletContext for this servlet itself
   */
  public void getServletContext(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    String anotherServlet = "/servlet_js_servletconfig_web/TestServlet";
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    // we expect ServletContext object that corresponds to the named URL
    ServletContext context2 = context.getContext(anotherServlet);

    if ((context2 == context)) {
      passed = true;
    } else {
      passed = true;
      pw.println("getServletContext() returned incorrect result");
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
