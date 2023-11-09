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
package servlet.tck.spec.annotationservlet.webfilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import servlet.tck.common.util.StaticLog;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

@WebFilter(filterName = "filter2", dispatcherTypes = {
    DispatcherType.FORWARD}, servletNames = {"servlet1"}, value = {
        "/Servlet1"}, initParams = {
            @WebInitParam(name = "name1", value = "value1"),
            @WebInitParam(name = "name2", value = "value2")})

public final class TestFilter2 implements Filter {

  private FilterConfig filterConfig;

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    if (filterConfig == null) {
      StaticLog.add("FAILED_FILTER2_INVOKED");
    } else {
      StaticLog.add("FILTER2_INVOKED");
      StaticLog.add("FilterName=" + filterConfig.getFilterName());

      for (Enumeration names = filterConfig.getInitParameterNames(); names
          .hasMoreElements(); ) {
        String name = (String) names.nextElement();
        StaticLog.add(
            "PName=" + name + " PVALUE=" + filterConfig.getInitParameter(name));
      }
      StaticLog.add("AsyncSupport=" + request.isAsyncSupported());
      FilterRegistration fr = filterConfig.getServletContext()
          .getFilterRegistration(filterConfig.getFilterName());
      Collection<String> mapping = fr.getServletNameMappings();
      for (String url : mapping) {
        StaticLog.add("URL=" + url);
      }
      StaticLog.add("DispatcherType=" + request.getDispatcherType());
      StaticLog.add("From=" + request.getAttribute("from"));
    }
    chain.doFilter(request, response);
  }

  public void destroy() {
  }

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
