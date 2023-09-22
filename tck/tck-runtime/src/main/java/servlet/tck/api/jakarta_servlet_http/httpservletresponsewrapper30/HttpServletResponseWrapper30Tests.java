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
 * $Id:$
 */
package servlet.tck.api.jakarta_servlet_http.httpservletresponsewrapper30;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpServletResponseWrapper30Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_HSRespWrapper30_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(HttpServletResponseWrapper30Tests.class.getResource("servlet_jsh_HSRespWrapper30_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */
  // --------------------- HttpServletResponseWrapper
  // ----------------------------

  /*
   * @testName: getHeadersTest
   * 
   * @assertion_ids: Servlet:JAVADOC:523; Servlet:JAVADOC:525;
   * Servlet:JAVADOC:783;
   * 
   * @test_Strategy: Create a Servlet, wrap the servlet in another one, In the
   * servlet, set a header value; then add multiple values to it; verify that
   * getHeaders(String) works properly
   */
  @Test
  public void getHeadersTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeadersTest");
    invoke();
  }

  /*
   * @testName: getHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:523; Servlet:JAVADOC:523;
   * Servlet:JAVADOC:781;
   * 
   * @test_Strategy: Create a Servlet, wrap the servlet in another one, In the
   * servlet, set a header value; then add multiple values to it; verify that
   * getHeader(String) works properly
   */
  @Test
  public void getHeaderTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeaderTest");
    invoke();
  }

  /*
   * @testName: getHeaderNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:520; Servlet:JAVADOC:522;
   * Servlet:JAVADOC:523; Servlet:JAVADOC:525; Servlet:JAVADOC:526;
   * Servlet:JAVADOC:527; Servlet:JAVADOC:782;
   * 
   * @test_Strategy: Create a Servlet, wrap the servlet in another one, In the
   * servlet, set multiuple header values using: #setHeader, #addHeader,
   * #setDateHeader, #addDateHeader, #setIntHeader, and #addIntHeader, verify
   * that getHeaderNames() works properly
   */
  @Test
  public void getHeaderNamesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeaderNamesTest");
    invoke();
  }

  /*
   * @testName: getStatusTest
   *
   * @assertion_ids: Servlet:JAVADOC:784;
   *
   * @test_Strategy: Create a Servlet, wrap the servlet in another one, In the
   * servlet, set a status value; verify that getStatus() works properly
   */
  @Test
  public void getStatusTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getStatusTest");
    invoke();
  }
  // ------------------- END HttpServletResponseWrapper
  // --------------------------
}
