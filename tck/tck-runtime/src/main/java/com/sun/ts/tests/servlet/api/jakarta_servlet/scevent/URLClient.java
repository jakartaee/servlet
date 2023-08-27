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

package com.sun.ts.tests.servlet.api.jakarta_servlet.scevent;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_scevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(SContextEventListener.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_scevent_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: constructorTest
   * 
   * @assertion_ids: Servlet:JAVADOC:110
   * 
   * @test_Strategy: Servlet tries to get an instance of ServletContextEvent.
   */
  @Test
  public void constructorTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest");
    invoke();
  }

  /*
   * @testName: getServletContextTest
   * 
   * @assertion_ids: Servlet:JAVADOC:111
   * 
   * @test_Strategy: Deploy a servlet context event listener. When the context
   * gets initialized, write a status message to a static log. Servlet reads the
   * static log looking for a specific message and verifies it exists
   */
  @Test
  public void getServletContextTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletContextTest");
    invoke();
  }

}
