/*
 * Copyright (c) 2009, 2021 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet_http.sessioncookieconfig;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class TestServlet extends HttpTCKServlet {

  public void constructortest1(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    request.getSession(true);

    String results = (String) getServletContext().getAttribute(TestListener.class.getName());

    if (results.indexOf("-FAILED-") > -1) {
      ServletTestUtil.printResult(
          response.getWriter(), "At least on test failed.  " + results);
    }

  }

  public void setNameTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String name = "WHO_SHOULD_NOT_BE_NAMED_HERE";
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setName");
      getServletContext().getSessionCookieConfig().setName(name);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setPathTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String path = "WHO_SHOULD_NOT_BE_NAMED_HERE";
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setPath");
      getServletContext().getSessionCookieConfig().setPath(path);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setDomainTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String domain = "WHO_SHOULD_NOT_BE_NAMED_HERE";
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setDomain");
      getServletContext().getSessionCookieConfig().setDomain(domain);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setMaxAgeTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    int maxage = 12345;
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setMaxAge");
      getServletContext().getSessionCookieConfig().setMaxAge(maxage);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setHttpOnlyTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    boolean http = true;
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setHttpOnly");
      getServletContext().getSessionCookieConfig().setHttpOnly(http);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setSecureTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    boolean secure = true;
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setSecure");
      getServletContext().getSessionCookieConfig().setSecure(secure);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }

  public void setAttributeTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    String attribute = "WHO_SHOULD_NOT_BE_NAMED_HERE";
    boolean pass = true;
    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession();

    try {
      pw.println("calling method setAttribute");
      getServletContext().getSessionCookieConfig().setAttribute(attribute, attribute);
      pass = false;
      pw.println("Expected IllegalStateException not thrown");
    } catch (IllegalStateException ex) {
      pw.println("Expected IllegalStateException thrown");
    } finally {
      session.invalidate();
      ServletTestUtil.printResult(pw, pass);
    }
  }
}
