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

package servlet.tck.api.jakarta_servlet_http.httpsessionx;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class InvalidateHttpSession extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;

    PrintWriter pw = response.getWriter();
    HttpSession session = request.getSession(false);
    session.invalidate();

    // session is invalidated. The method should return false.
    if (!request.isRequestedSessionIdValid()) {
      passed = true;
      pw.println("From invalidateHttpSession: Session is invalidated ");
    } else {
      pw.println("From invalidateHttpSession: Session is not invalidated ");
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
