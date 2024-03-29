/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates and others.
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
import java.util.Locale;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.PushBuilder;

public class TestServlet2 extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    PrintWriter pw = resp.getWriter();
    req.getSession(true);
    PushBuilder pb = req.newPushBuilder();
    String defaultMethod = pb.getMethod();
    pw.println("Method:" + defaultMethod);
    PushBuilder pb2 = req.newPushBuilder();
    pw.println("Return new instance:" + String.valueOf(pb != pb2));
    pw.println("JSESSIONID: " + pb.getSessionId());
    pw.println("The headers of PushBuilder: ");
    for (String name : pb.getHeaderNames()) {
      /*
       * Header names are case insensitive. Force to lower case to make client
       * side test logic simpler.
       */
      pw.print(name.toLowerCase(Locale.ENGLISH));
      pw.print("=");
      pw.println(pb.getHeader(name));
    }
  }
}
