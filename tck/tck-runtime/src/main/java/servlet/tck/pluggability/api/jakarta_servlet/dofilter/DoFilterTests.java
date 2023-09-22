/*
 * Copyright (c) 2001, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.pluggability.api.jakarta_servlet.dofilter;

import servlet.tck.api.jakarta_servlet.dofilter.CTSResponseWrapper;
import servlet.tck.api.jakarta_servlet.dofilter.ForwardedServlet;
import servlet.tck.api.jakarta_servlet.dofilter.IncludedServlet;
import servlet.tck.api.jakarta_servlet.dofilter.TestServlet;
import servlet.tck.api.jakarta_servlet.dofilter.WrapResponseFilter;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import servlet.tck.api.jakarta_servlet.dofilter.SetHeaderResponseFilter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoFilterTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(DoFilterTests.class.getResource("servlet_plu_dofilter_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_dofilter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(SetHeaderResponseFilter.class, WrapResponseFilter.class, TestServlet.class,
                    IncludedServlet.class, ForwardedServlet.class, CTSResponseWrapper.class)
            .addAsLibraries(javaArchive);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: wrapResponseTest
   *
   * @assertion_ids: Servlet:SPEC:54; Servlet:SPEC:59;
   *
   * @test_Strategy: 1. Create two servlets - TestServlet, ForwardedServlet 2.
   * Invoke ForwardedServlet using forward in TestServlet 3. Map a filter
   * WrapResponseFilter with dispatcher value set to FORWARD 4. In the filter,
   * wrap the response with custom implementation of ServletResponse
   * CTSResponseWrapper 5. Verify that filter is properly invoked.
   */
  @Test
  public void wrapResponseTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "wrapResponseTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "CTSResponseWrapper|WrapResponseFilter|ForwardedServlet");
    invoke();
  }
}
