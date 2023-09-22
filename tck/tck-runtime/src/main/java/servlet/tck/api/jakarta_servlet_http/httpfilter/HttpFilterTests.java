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

package servlet.tck.api.jakarta_servlet_http.httpfilter;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class HttpFilterTests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpfilter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HttpFilter_Filter1.class, HttpFilter_Filter2.class, HttpFilterTestServlet.class)
            .setWebXML(HttpFilterTests.class.getResource("servlet_jsh_httpfilter_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: dofilterTest
   *
   * @assertion_ids: NA;
   *
   * @test_Strategy: Client attempts to access a servlet and both filters
   * extending HttpFilter configured for that servlet should be invoked.
   */
  @Test
  public void dofilterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "HttpFilterTest");
    invoke();
  }
}
