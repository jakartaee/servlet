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
package com.sun.ts.tests.servlet.pluggability.api.jakarta_servlet.filterconfig;

import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetFilterName_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNull_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParam_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNames_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetServletContext_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNamesNull_Filter;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetServletContextTestServlet;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNullTestServlet;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamTestServlet;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNamesNullTestServlet;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetInitParamNamesTestServlet;
import com.sun.ts.tests.servlet.api.jakarta_servlet.filterconfig.GetFilterNameTestServlet;
import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.pluggability.common.RequestListener1;
import com.sun.ts.tests.servlet.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {


  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(URLClient.class.getResource("servlet_plu_filterconfig_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_filterconfig_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(GetFilterName_Filter.class, GetInitParam_Filter.class, GetInitParamNames_Filter.class,
                    GetServletContext_Filter.class, GetInitParamNull_Filter.class, GetInitParamNamesNull_Filter.class,
                    GetServletContextTestServlet.class, GetInitParamNullTestServlet.class, GetInitParamTestServlet.class,
                    GetInitParamNamesNullTestServlet.class, GetInitParamNamesTestServlet.class, GetFilterNameTestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: GetFilterNameTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:281;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetFilterNameTest() throws Exception {
    String testName = "GetFilterNameTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNamesTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:285;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNamesTest() throws Exception {
    String testName = "GetInitParamNamesTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNamesNullTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:286;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNamesNullTest() throws Exception {
    String testName = "GetInitParamNamesNullTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:283;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamTest() throws Exception {
    String testName = "GetInitParamTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNullTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:284;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNullTest() throws Exception {
    String testName = "GetInitParamNullTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetServletContextTest
   *
   * @assertion_ids: Servlet:SPEC:45; Servlet:JAVADOC:282;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetServletContextTest() throws Exception {
    String testName = "GetServletContextTest";
    TEST_PROPS.setProperty(APITEST, testName);
    invoke();
  }
}
