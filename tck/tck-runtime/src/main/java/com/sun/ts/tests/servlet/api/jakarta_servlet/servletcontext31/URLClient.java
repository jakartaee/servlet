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
 * $Id:$
 */
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext31;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext31_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_servletcontext31_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   * logical.hostname.servlet;
   */
  /* Run test */
  /*
   * @testName: getVirtualServerNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:954;
   *
   * @test_Strategy: 1. Create a Servlet Servlet, define it in web.xml; 2.
   * verify ServletContext.getVirtualServerName() returns correctly;
   */
  @Test
  public void getVirtualServerNameTest() throws Exception {
    String expected_virtualservername = _props
        .getProperty("logical.hostname.servlet").trim();
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName()
            + "?testname=getVirtualServerNameTest&VirtualServerNamePlease="
            + expected_virtualservername + " HTTP/1.0");
    invoke();
  }
}
