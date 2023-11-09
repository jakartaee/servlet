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
package servlet.tck.pluggability.fragment;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public final class TestFilter implements Filter {

  private FilterConfig filterConfig;

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    PrintWriter pw = response.getWriter();

    pw.println("doFilter in TestFilter invoked");

    if (filterConfig == null) {
      pw.println("filter instance is null ");
    } else {
      Enumeration initP = filterConfig.getInitParameterNames();
      int expectedCount = 2;
      int count = 0;

      if (initP.hasMoreElements()) {
        while (initP.hasMoreElements()) {
          String result = (String) initP.nextElement();
          pw.println("parameter name= " + result);
          pw.println(
              "parameter value= " + filterConfig.getInitParameter(result));
        }
      }
      chain.doFilter(request, response);
    }
  }

  public void destroy() {
  }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
