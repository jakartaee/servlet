/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext40;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet2 extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter pw = resp.getWriter();
    try {
      ServletRegistration registration = getServletContext()
          .addJspFile("AddJspFile3", "/addJspFile.jsp");
      pw.println("addJsp_contextInitialized");
    } catch (IllegalStateException e) {
      pw.println("IllegalStateException_when_addJsp__ContextInitialized");
    }

    try {
      getServletContext().setSessionTimeout(2);
      pw.println("setSessionTimeout_contextInitialized");
    } catch (IllegalStateException e) {
      pw.println(
          "IllegalStateException_when_setSessionTimeout_ContextInitialized");
    }

    /*
     * JSR-369 change log 2. Modify javadoc for ServletContext getAttribute()
     * and getInitParameter(), specify that NullPointerException must be thrown
     * if the argument 'name' is null.
     */
    try {
      getServletContext().getAttribute(null);
      pw.println("getAttribute_successful");
    } catch (NullPointerException e) {
      pw.println("NullPointerException_when_getAttribute_with_null");
    }

    try {
      getServletContext().getInitParameter(null);
      pw.println("getInitParameter_successful");
    } catch (NullPointerException e) {
      pw.println("NullPointerException_when_getInitParameter_with_null");
    }

    /*
     * JSR-369 change log 3. Modify javadoc for ServletContext setAttribute()
     * and setInitParameter(), specify that NullPointerException must be thrown
     * if the argument 'name' is null.
     */
    try {
      getServletContext().setAttribute(null, new Object());
      pw.println("setAttribute_successful");
    } catch (NullPointerException e) {
      pw.println("NullPointerException_when_setAttribute_with_null");
    } catch (IllegalArgumentException e) {
      pw.println("IllegalArgumentException_when_setAttribute_with_null");
    }

    try {
      getServletContext().setInitParameter(null, "test");
      pw.println("setInitParameter_successful");
    } catch (NullPointerException e) {
      pw.println("NullPointerException_when_setInitParameter_with_null");
    } catch (IllegalArgumentException e) {
      pw.println("IllegalArgumentException_when_setInitParameter_with_null");
    }

    /*
     * JSR-369 change log 12. Modify javadoc for
     * ServletContext.getEffectiveSessionTrackingModes() without specifying the
     * default value.
     */
    try {
      Set<SessionTrackingMode> set = getServletContext()
          .getEffectiveSessionTrackingModes();
      StringBuilder strB = new StringBuilder(
          "getEffectiveSessionTrackingModes_test_passed");
      if (set != null && set.size() > 0) {
        for (SessionTrackingMode stm : set)
          strB.append("_" + stm.toString());
      }

      if (set == null || set.size() == 0)
        throw new Exception(
            "getEffectiveSessionTrackingModes_test_with_set_is_null");

      Set<SessionTrackingMode> defaultSet = getServletContext()
          .getDefaultSessionTrackingModes();
      if (defaultSet == null || defaultSet.size() == 0)
        throw new Exception(
            "getEffectiveSessionTrackingModes_test_with_defaultSet_is_null");

      if (set.size() != defaultSet.size())
        throw new Exception(
            "getEffectiveSessionTrackingModes_test_with_set_is_not_equlto_defalut_"
                + set.size());

      for (SessionTrackingMode stm : set) {
        if (!defaultSet.contains(stm)) {
          throw new Exception(
              "getEffectiveSessionTrackingModes_test_with_set_is_not_equlto_defalut_"
                  + set);
        }
      }

      pw.println(strB.toString());
    } catch (Exception e) {
      pw.println("getEffectiveSessionTrackingModes_test_with_exception" + e);
    }
  }
}
