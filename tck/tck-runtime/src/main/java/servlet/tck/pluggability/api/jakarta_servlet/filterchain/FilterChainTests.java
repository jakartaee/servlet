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
package servlet.tck.pluggability.api.jakarta_servlet.filterchain;

import servlet.tck.api.jakarta_servlet.filterchain.FilterChainTestServlet;
import servlet.tck.api.jakarta_servlet.filterchain.FilterChain_Filter1;
import servlet.tck.api.jakarta_servlet.filterchain.FilterChain_Filter2;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class FilterChainTests extends AbstractTckTest {


  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(FilterChainTests.class.getResource("servlet_plu_filterchain_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_filterchain_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(FilterChain_Filter1.class, FilterChain_Filter2.class, FilterChainTestServlet.class)
            .addAsLibraries(javaArchive);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: filterChainTest
   *
   * @assertion_ids: Servlet:SPEC:48; Servlet:SPEC:50; Servlet:SPEC:52;
   * Servlet:JAVADOC:287; Servlet:JAVADOC:293;
   *
   * @test_Strategy: Client attempts to access a servlet and both filters
   * configured for that servlet should be invoked.
   */
  @Test
  void filterChainTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "FilterChainTest");
    invoke();
  }
}
