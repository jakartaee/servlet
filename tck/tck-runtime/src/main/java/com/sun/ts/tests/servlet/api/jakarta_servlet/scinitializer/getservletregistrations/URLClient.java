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
package com.sun.ts.tests.servlet.api.jakarta_servlet.scinitializer.getservletregistrations;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_sci_getservletregistrations_web.war")
            .addAsResource(URLClient.class.getResource("jakarta.servlet.ServletContainerInitializer"),
                    "META-INF/services/jakarta.servlet.ServletContainerInitializer")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TCKServletContainerInitializer.class, TestListener.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_sci_getservletregistrations_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: getServletRegistrationsTest
   *
   * @assertion_ids: Servlet:JAVADOC:692.2;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, Add a
   * ServletContextListener instance using ServletContext.addListener; in the
   * ServletContextListener call ServletContext.getServletRegistrations() Verify
   * that UnsupportedOperationException is thrown.
   */
  @Test
  public void getServletRegistrationsTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletRegistrationsTest");
    invoke();
  }
}
