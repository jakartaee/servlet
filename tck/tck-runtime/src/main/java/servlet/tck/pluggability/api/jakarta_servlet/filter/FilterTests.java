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
package servlet.tck.pluggability.api.jakarta_servlet.filter;

import servlet.tck.api.jakarta_servlet.filter.DoFilterTestServlet;
import servlet.tck.api.jakarta_servlet.filter.DoFilter_Filter;
import servlet.tck.api.jakarta_servlet.filter.InitFilterConfigTestServlet;
import servlet.tck.api.jakarta_servlet.filter.InitFilter_Filter;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class FilterTests extends AbstractTckTest {


  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(FilterTests.class.getResource("servlet_plu_filter_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_filter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DoFilter_Filter.class, InitFilter_Filter.class, DoFilterTestServlet.class,
                    InitFilterConfigTestServlet.class)
            .addAsLibraries(javaArchive);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: doFilterTest
   *
   * @assertion_ids: Servlet:SPEC:58; Servlet:SPEC:48; Servlet:JAVADOC:293;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet should be invoked.
   */
  @Test
  void doFilterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "DoFilterTest");
    invoke();
  }

  /*
   * @testName: initFilterConfigTest
   *
   * @assertion_ids: Servlet:SPEC:58; Servlet:SPEC:45; Servlet:JAVADOC:290;
   *
   * @test_Strategy: Client attempts to access a servlet and the filter
   * configured for that servlet.
   */
  @Test
  void initFilterConfigTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "InitFilterConfigTest");
    invoke();
  }
}
