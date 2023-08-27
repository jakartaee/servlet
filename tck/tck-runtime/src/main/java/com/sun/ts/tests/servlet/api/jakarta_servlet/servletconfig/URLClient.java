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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletconfig;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletconfig_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_servletconfig_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: getServletConfigInitParameterNamesTest
   *
   * @assertion_ids: Servlet:SPEC:7; Servlet:JAVADOC:266; Servlet:JAVADOC:261;
   *
   * @test_Strategy: Set init parameters in the web.xml file and check for the
   * enumerated values in the servlet.
   */
  @Test
  public void getServletConfigInitParameterNamesTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletConfigInitParameterNames");
    invoke();
  }

  /*
   * @testName: getServletConfigInitParameterTest
   *
   * @assertion_ids: Servlet:SPEC:7; Servlet:JAVADOC:266; Servlet:JAVADOC:259;
   *
   * @test_Strategy: Set init parameters in the web.xml file and check for the
   * value in the servlet.
   */
  @Test
  public void getServletConfigInitParameterTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletConfigInitParameter");
    invoke();
  }

  /*
   * @testName: getServletConfigInitParameterTestNull
   *
   * @assertion_ids: Servlet:SPEC:7; Servlet:JAVADOC:266; Servlet:JAVADOC:260;
   *
   * @test_Strategy: Set No init parameter anywhere named:
   * "Nothing_is_set_for_Negative_compatibility_test_only" anywhere and check
   * for the Verify that ServletConfig.getInitParameter(name) return null.
   */
  @Test
  public void getServletConfigInitParameterTestNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletConfigInitParameterNull");
    invoke();
  }

  /*
   * @testName: getServletContextTest
   *
   * @assertion_ids: Servlet:SPEC:7; Servlet:JAVADOC:266; Servlet:JAVADOC:258;
   * Servlet:JAVADOC:219;
   *
   * @test_Strategy: Try to get the ServletContext for this servlet itself
   */
  @Test
  public void getServletContextTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletContext");
    invoke();
  }

  /*
   * @testName: getServletNameTest
   *
   * @assertion_ids: Servlet:SPEC:7; Servlet:JAVADOC:266; Servlet:JAVADOC:257;
   *
   * @test_Strategy: Try to get the ServletName for this servlet itself
   */
  @Test
  public void getServletNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletName");
    invoke();
  }
}
