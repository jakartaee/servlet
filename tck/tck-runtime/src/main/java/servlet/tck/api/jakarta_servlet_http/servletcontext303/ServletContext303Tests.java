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
package servlet.tck.api.jakarta_servlet_http.servletcontext303;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletContext303Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_servletcontext303_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(ServletContext303Tests.class.getResource("servlet_jsh_servletcontext303_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: negativeaddHListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a HttpSessionListener is added;
   * Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  public void negativeaddHListenerClassTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeaddHListenerClassTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "HttpSessionListener");
    invoke();
  }

  /*
   * @testName: negativeaddHListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a HttpSessionListener is added;
   * Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  public void negativeaddHListenerStringTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeaddHListenerStringTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "HttpSessionListener");
    invoke();
  }

  /*
   * @testName: negativeaddHAListenerClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.11; Servlet:JAVADOC:673.11;
   *
   * @test_Strategy: Create a Servlet, in which, a HttpSessionAttributeListener
   * is added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  public void negativeaddHAListenerClassTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeaddHAListenerClassTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "HSessionAttribute");
    invoke();
  }

  /*
   * @testName: negativeaddHAListenerStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:671.11;
   *
   * @test_Strategy: Create a Servlet, in which, a HttpSessionAttributeListener
   * is added; Verify in servlet that java.lang.IllegalStateException is thrown.
   */
  @Test
  public void negativeaddHAListenerStringTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeaddHAListenerClassTest");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "HSessionAttribute");
    invoke();
  }
}
