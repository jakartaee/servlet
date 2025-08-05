/*
 * Copyright (c) 2009, 2025 Oracle and/or its affiliates and others.
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
package servlet.tck.webxml.api.jakarta_servlet_http.sessioncookieconfig;

import java.io.IOException;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends servlet.tck.api.jakarta_servlet_http.sessioncookieconfig.TestServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void constructortest1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession(true);

        String results = (String) getServletContext().getAttribute(TestListener.class.getName());

        if (results.indexOf("-FAILED-") > -1) {
            ServletTestUtil.printResult(response.getWriter(), "At least one test failed.  " + results);
        }

    }
}
