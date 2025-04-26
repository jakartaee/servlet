/*
 * Copyright (c) 2017, 2025 Oracle and/or its affiliates and others.
 * All rights reserved.
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

package servlet.tck.spec.serverpush;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.PushBuilder;

public class TestServlet4 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter pw = resp.getWriter();
    Cookie cookie1 = new Cookie("foo", "bar");
    cookie1.setMaxAge(1000);
    resp.addCookie(cookie1);

    Cookie cookie2 = new Cookie("baz", "qux");
    cookie2.setMaxAge(0);
    resp.addCookie(cookie2);

    Cookie cookie3 = new Cookie("abc", "123");
    cookie3.setMaxAge(-1);
    resp.addCookie(cookie3);

    pw.println("add cookies [foo,bar] [baz,qux] [abc,123] to response");

    HttpSession session = req.getSession(true);
    pw.println("create session: " + session);

    PushBuilder pb = req.newPushBuilder();
    pw.println("Cookie header in PushBuilder: " + pb.getHeader("Cookie"));
    pb.path("index.html");
    pb.push();
  }
}
