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

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.SessionCookieConfig;

public class TestListener implements ServletContextListener {

    /**
     * Test for SessionCookieConfig configured via web.xml.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        StringBuffer testData = new StringBuffer("Testing_Session_Cookie_Config");
        String domain = "sun.com";
        String path = sce.getServletContext().getContextPath() + "/TestServlet";
        boolean isSecure = true;
        boolean httpOnly = false;
        int maxage = 50000;
        String attrName = "a1";
        String attrValue = "b2";
        String name = "TCK_Cookie_Name";

        SessionCookieConfig scf = sce.getServletContext().getSessionCookieConfig();

        if (!scf.getPath().equals(path)) {
            testData.append("|getPath-FAILED-expecting-" + path + "-got-" + scf.getPath());
        }

        if (!scf.isSecure()) {
            testData.append("|isSecure-FAILED-expecting-" + isSecure + "-got-" + scf.isSecure());
        }

        if (scf.isHttpOnly()) {
            testData.append("|isHttpOnly-FAILED-expecting-" + httpOnly + "-got-" + scf.isHttpOnly());
        }

        if (!scf.getDomain().equals(domain)) {
            testData.append("|getDomain-FAILED-expecting-" + domain + "-got-" + scf.getDomain());
        }

        if (scf.getMaxAge() != maxage) {
            testData.append("|getMaxAge-FAILED-expecting-" + maxage + "-got-" + scf.getMaxAge());
        }

        if (!scf.getAttribute(attrName).equals(attrValue)) {
            testData.append("|getAttribute-FAILED-expecting-" + attrValue + "-got-" + scf.getAttribute(attrName));
        }

        if (!scf.getName().equals(name)) {
            testData.append("|getName-FAILED-expecting-" + name + "-got-" + scf.getName());
        }

        sce.getServletContext().setAttribute(this.getClass().getName(), testData.toString());
    }
}
