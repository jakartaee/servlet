/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.scinitializer.addfilter1;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AddFilterClass implements Filter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AddFilterClass.class);

  // The filter configuration object we are associated with. If this value
  // is null, this filter instance is not currently configured.
  private FilterConfig filterConfig;

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    if (filterConfig == null) {
      LOGGER.info(
          "doFilter of AddFilterClass was called but this filter instance is not currently configured");
    } else {
      LOGGER.info("ADD_FILTER_CLASS_INVOKED");
    }

    chain.doFilter(request, response);
  }

  // Remove the filter configuration object for this filter.
  public void destroy() {
  }

  // initialize the filter configuration object for this filter.
  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }
}
