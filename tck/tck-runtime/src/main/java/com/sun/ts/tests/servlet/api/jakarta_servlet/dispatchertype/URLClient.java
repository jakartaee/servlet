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
 * $Id:$
 */

package com.sun.ts.tests.servlet.api.jakarta_servlet.dispatchertype;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_dispatchertype_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClass(TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_dispatchertype_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: valuesTest
   *
   * @assertion_ids: Servlet:JAVADOC:654;
   *
   * @test_Strategy: 1. Create a servlet - TestServlet 2. Client send a request
   * to TestServlet 3. In TestServlet, call DispatcherType.values 4. Verify that
   * DispatcherType.values works properly.
   */
  @Test
  public void valuesTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "valuesTest");
    invoke();
  }

  /*
   * @testName: valueOfTest
   *
   * @assertion_ids: Servlet:JAVADOC:653;
   *
   * @test_Strategy: 1. Create a servlet - TestServlet 2. Client send a request
   * to TestServlet 3. In TestServlet, call DispatcherType.valueOf 4. Verify
   * that DispatcherType.valueOf works properly.
   */
  @Test
  public void valueOfTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "valueOfTest");
    invoke();
  }
  /*
   * @testName: valueOfNullTest
   *
   * @assertion_ids: Servlet:JAVADOC:653;
   *
   * @test_Strategy: 1. Create a servlet - TestServlet 2. Client send a request
   * to TestServlet 3. In TestServlet, call DispatcherType.valueOf(String name)
   * 4. Verify that DispatcherType.valueOf throws NullPointerException when name
   * is null
   */
  @Test
  public void valueOfNullTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "valueOfNullTest");
    invoke();
  }
  /*
   * @testName: valueOfInvalidTest
   *
   * @assertion_ids: Servlet:JAVADOC:653;
   *
   * @test_Strategy: 1. Create a servlet - TestServlet 2. Client send a request
   * to TestServlet 3. In TestServlet, call DispatcherType.valueOf(String name)
   * 4. Verify that DispatcherType.valueOf throws IllegalArgumentException when
   * name is invalid Dispatcher type
   */
  @Test
  public void valueOfInvalidTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "valueOfInvalidTest");
    invoke();
  }
}
