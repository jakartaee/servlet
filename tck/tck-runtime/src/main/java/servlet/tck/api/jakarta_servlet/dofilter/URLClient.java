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

package servlet.tck.api.jakarta_servlet.dofilter;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_dofilter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(CTSResponseWrapper.class, ForwardedServlet.class, IncludedServlet.class,
                        SetHeaderResponseFilter.class, TestServlet.class, WrapResponseFilter.class)
            .setWebXML(URLClient.class.getResource("servlet_js_dofilter_web.xml"));
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
    TEST_PROPS.setProperty(APITEST, "wrapResponseTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "CTSResponseWrapper|WrapResponseFilter|ForwardedServlet");
    invoke();
  }

}
