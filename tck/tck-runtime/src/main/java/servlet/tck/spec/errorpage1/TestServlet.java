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
 * $URL$ $LastChangedDate$
 */

package servlet.tck.spec.errorpage1;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.spec.errorpage.TestException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpTCKServlet {

  public void init(ServletConfig config) throws ServletException {
    super.init(config);
  }

  public void destroy() {
    super.destroy();
  }

  // ------------------------------------------------- Test Methods

  public void nonServletExceptionTest(HttpServletRequest req,
      HttpServletResponse res) throws ServletException, InstantiationException {
    throw new IllegalStateException("error page invoked");
  }

  public void servletExceptionTest(HttpServletRequest req,
      HttpServletResponse res) throws ServletException {
    throw new TestServletException(new TestException("error page invoked"));
  }
}// ErrorPageTestServlet
