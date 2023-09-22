/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * $Id: URLClient.java 62833 2011-05-18 13:13:23Z djiao $
 */

package servlet.tck.api.jakarta_servlet.genericfilter;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class GenericFilterTests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_genericfilter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(GetFilterName_Filter.class, GetFilterNameTestServlet.class, GetInitParam_Filter.class, GetInitParamNull_Filter.class,
                        GetInitParamNames_Filter.class, GetInitParamNamesNull_Filter.class, GetInitParamNamesNullTestServlet.class,
                        GetInitParamNamesTestServlet.class, GetInitParamNamesNull_Filter.class, GetInitParamNullTestServlet.class,
                        GetInitParamTestServlet.class, GetServletContext_Filter.class, GetServletContextTestServlet.class,
                        InitFilter_Filter.class, InitFilterConfig_Filter.class, InitFilterConfigTestServlet.class,
                        InitFilterTestServlet.class)
            .setWebXML(GenericFilterTests.class.getResource("servlet_js_genericfilter_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: initFilterTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void initFilterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "InitFilterTest");
    invoke();
  }

  /*
   * @testName: initFilterConfigTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void initFilterConfigTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "InitFilterConfigTest");
    invoke();
  }

  /*
   * @testName: GetFilterNameTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetFilterNameTest() throws Exception {
    String testName = "GetFilterNameTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamTest() throws Exception {
    String testName = "GetInitParamTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNamesTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNamesTest() throws Exception {
    String testName = "GetInitParamNamesTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNamesNullTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNamesNullTest() throws Exception {
    String testName = "GetInitParamNamesNullTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetInitParamNullTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetInitParamNullTest() throws Exception {
    String testName = "GetInitParamNullTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  /*
   * @testName: GetServletContextTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  public void GetServletContextTest() throws Exception {
    String testName = "GetServletContextTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }
}
