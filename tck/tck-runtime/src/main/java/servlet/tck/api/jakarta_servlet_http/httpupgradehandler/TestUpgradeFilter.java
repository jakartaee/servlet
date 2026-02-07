/*
 * Copyright (c) 2025 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.api.jakarta_servlet_http.httpupgradehandler;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/TestServlet"})
public class TestUpgradeFilter implements Filter {

  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
    if (filterConfig != null && response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("X-Filter-Before-Upgrade", "invoked");
    }

    request.setAttribute("filter.invoked.before.upgrade", true);

    chain.doFilter(request, response);

    if (filterConfig != null && response instanceof HttpServletResponse) {
      HttpServletResponse httpResponse = (HttpServletResponse) response;
      httpResponse.setHeader("X-Filter-After-Upgrade", "processed");
    }
  }

  @Override
  public void destroy(){
    this.filterConfig = null;
  }

  public void init(FilterConfig filterConfig) throws ServletException{
    this.filterConfig = filterConfig;
  }
}