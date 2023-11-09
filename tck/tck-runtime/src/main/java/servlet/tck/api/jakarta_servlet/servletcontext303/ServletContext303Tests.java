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
package servlet.tck.api.jakarta_servlet.servletcontext303;

import servlet.tck.api.common.sharedfiles.SCAttributeListener;
import servlet.tck.api.common.sharedfiles.SCListener;
import servlet.tck.api.common.sharedfiles.SRAttributeListener;
import servlet.tck.api.common.sharedfiles.SRListener;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletContext303Tests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext303_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, SRAttributeListener.class, SRListener.class, SRListener.class,
                    SCAttributeListener.class, SCListener.class)
            .setWebXML(ServletContext303Tests.class.getResource("servlet_js_servletcontext303_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */

  /*
   * @testName: negativeaddSRAListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a
   * ServletRequestAttributeListener is added; Verify in servlet that
   * java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSRAListenerClassTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSRAListenerClassTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SRAttributeListener");
    invoke();
  }

  /*
   * @testName: negativeaddSRAListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a
   * ServletRequestAttributeListener is added; Verify in servlet that
   * java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSRAListenerStringTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSRAListenerStringTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SRAttributeListener");
    invoke();
  }

  /*
   * @testName: negativeaddSRListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a ServletRequestListener is
   * added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSRListenerClassTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSRListenerClassTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SRListener");
    invoke();
  }

  /*
   * @testName: negativeaddSRListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a ServletRequestListener is
   * added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSRListenerStringTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSRListenerStringTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SRListener");
    invoke();
  }

  /*
   * @testName: negativeaddSCAListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a
   * ServletContextAttributeListener is added; Verify in servlet that
   * java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSCAListenerClassTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSCAListenerClassTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SRListener");
    invoke();
  }

  /*
   * @testName: negativeaddSCAListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a
   * ServletContextAttributeListener is added; Verify in servlet that
   * java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSCAListenerStringTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSCAListenerStringTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SCAttributeListener");
    invoke();
  }

  /*
   * @testName: negativeaddSCListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a ServletContextListener is
   * added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSCListenerClassTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSCListenerClassTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SCListener");
    invoke();
  }

  /*
   * @testName: negativeaddSCListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a ServletContextListener is
   * added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  void negativeaddSCListenerStringTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeaddSCListenerStringTest");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "SCListener");
    invoke();
  }
}
