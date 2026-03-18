/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/TestServlet"})
public class TestFilter extends GenericFilter {

    private static final long serialVersionUID = 1L;

    public static final String TRACKING_HEADER_NAME = "x-tracking";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        /*
         * The case - and the test - will fail for non-HTTP requests. This is OK since this is an HTTP upgrade test.
         */
        HttpServletResponse hResponse = (HttpServletResponse) response;

        String value = hResponse.getHeader(TRACKING_HEADER_NAME);
        // value should be null here but just in case...
        if (value == null) {
            value = "filter-before";
        } else {
            value = value + ",filter-before";
        }
        hResponse.setHeader(TRACKING_HEADER_NAME, value);

        chain.doFilter(request, response);

        value = hResponse.getHeader(TRACKING_HEADER_NAME);
        // value should not be null here but just in case...
        if (value == null) {
            value = "filter-after";
        } else {
            value = value + ",filter-after";
        }
        hResponse.setHeader(TRACKING_HEADER_NAME, value);
    }
}
