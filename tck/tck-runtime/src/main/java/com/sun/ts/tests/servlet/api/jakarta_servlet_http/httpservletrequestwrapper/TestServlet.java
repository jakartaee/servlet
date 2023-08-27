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

package com.sun.ts.tests.servlet.api.jakarta_servlet_http.httpservletrequestwrapper;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.HttpRequestTestServlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet
    extends HttpRequestTestServlet {

  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
  }

  // Simply validate the default behavior of the RequestWrapper is to delegate
  // the call to the wrapped object.
  public void service(HttpServletRequest servletRequest,
      HttpServletResponse servletResponse)
      throws ServletException, IOException {
    HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(
        servletRequest);
    super.service(wrapper, servletResponse);
  }
}
