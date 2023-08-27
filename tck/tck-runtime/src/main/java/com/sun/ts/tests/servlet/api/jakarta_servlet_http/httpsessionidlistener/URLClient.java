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
 * $Id:$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.httpsessionidlistener;

import com.sun.ts.tests.servlet.common.request.HttpRequestClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends HttpRequestClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpsessionidlistener_web.war").addAsLibraries(CommonServlets.getCommonServletsArchive()).addClasses(TCKHttpSessionIDListener.class).setWebXML(URLClient.class.getResource("servlet_jsh_httpsessionidlistener_web.xml"));
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    /*
   * @testName: changeSessionIDTest1
   *
   * @assertion_ids: Servlet:JAVADOC:304; Servlet:JAVADOC:467;
   * Servlet:JAVADOC:476; Servlet:JAVADOC:484; Servlet:JAVADOC:565;
   * Servlet:JAVADOC:566; Servlet:JAVADOC:929; Servlet:JAVADOC:935;
   *
   * @test_Strategy: Send an HttpServletRequest to server; Verify that
   * request.changeSessionId() works.
   */
    @Test()
    public void changeSessionIDTest1() throws Exception {
        super.changeSessionIDTest1();
    }
}
