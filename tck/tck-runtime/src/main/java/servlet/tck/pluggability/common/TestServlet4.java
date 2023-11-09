/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.pluggability.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet4 extends GenericServlet {

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    pw.println("TestServlet4 is invoked");
    pw.append("msg1=" + getInitParameter("msg1"));
    pw.append("msg2=" + getInitParameter("msg2"));
    pw.append("msg3=" + getInitParameter("msg3"));
    pw.append("msg4=" + getInitParameter("msg4"));

    ArrayList result = (ArrayList) getServletContext()
        .getAttribute("testmessage");

    Object[] results = result.toArray();
    for (int i = 0; i < results.length; i++) {
      pw.println(results[i].toString());
    }
  }
}
