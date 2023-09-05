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

package servlet.tck.api.jakarta_servlet.filterconfig;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class FilterConfigTests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_filterconfig_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(GetFilterName_Filter.class, GetFilterNameTestServlet.class, GetInitParam_Filter.class,
                        GetInitParamNames_Filter.class, GetInitParamNamesNull_Filter.class,
                        GetInitParamNamesNullTestServlet.class, GetInitParamNamesTestServlet.class,
                        GetInitParamNull_Filter.class, GetInitParamNullTestServlet.class, GetInitParamTestServlet.class,
                        GetServletContext_Filter.class, GetServletContextTestServlet.class)
            .setWebXML(FilterConfigTests.class.getResource("servlet_js_filterconfig_web.xml"));
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
