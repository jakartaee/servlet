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

package servlet.tck.api.jakarta_servlet.filter;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_filter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DoFilter_Filter.class, DoFilterTestServlet.class, InitFilter_Filter.class,
                        InitFilterConfigTestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_filter_web.xml"));
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
  public void doFilterTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "DoFilterTest");
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
  public void initFilterConfigTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "InitFilterConfigTest");
    invoke();
  }
}
