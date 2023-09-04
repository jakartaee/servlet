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
 * $Id$
 */
package servlet.tck.pluggability.api.jakarta_servlet_http.sessioncookieconfig;

import servlet.tck.api.jakarta_servlet_http.sessioncookieconfig.TestListener;
import servlet.tck.api.jakarta_servlet_http.sessioncookieconfig.TestServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionCookieConfigTests extends AbstractUrlClient {

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
            .addAsResource(SessionCookieConfigTests.class.getResource("servlet_pluh_sessioncookieconfig_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_sessioncookieconfig_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestListener.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: constructortest1
   *
   * @assertion_ids: Servlet:JAVADOC:693; Servlet:JAVADOC:733;
   * Servlet:JAVADOC:734; Servlet:JAVADOC:735; Servlet:JAVADOC:736;
   * Servlet:JAVADOC:737; Servlet:JAVADOC:738; Servlet:JAVADOC:739;
   * Servlet:JAVADOC:740; Servlet:JAVADOC:741; Servlet:JAVADOC:742;
   * Servlet:JAVADOC:743; Servlet:JAVADOC:744; Servlet:JAVADOC:745;
   * Servlet:JAVADOC:746;
   *
   * @test_Strategy: Create a Servlet TestServlet, with a
   * ServletContextListener; In the Servlet, turn HttpSession on; In
   * ServletContextListener, create a SessionCookieConfig instance, Verify in
   * Client that the SessionCookieConfig instance is created, and all
   * SessionCookieConfig APIs work accordingly.
   */
  @Test
  public void constructortest1() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet?testname=constructortest1 HTTP/1.1");
    TEST_PROPS.setProperty(EXPECTED_HEADERS,
        "Set-Cookie:" + "TCK_Cookie_Name=" + "##Expires="
            + "##Path=" + getContextRoot() + "/TestServlet"
            + "##Secure");
    invoke();
  }

  /*
   * @testName: setNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:744;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setName cannot be
   * called once is set.
   */
  @Test
  public void setNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setNameTest");
    invoke();
  }

  /*
   * @testName: setCommentTest
   *
   * @assertion_ids: Servlet:JAVADOC:740;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setComment cannot be
   * called once is set.
   */
  @Test
  public void setCommentTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setCommentTest");
    invoke();
  }

  /*
   * @testName: setPathTest
   *
   * @assertion_ids: Servlet:JAVADOC:745;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setPath cannot be
   * called once is set.
   */
  @Test
  public void setPathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setPathTest");
    invoke();
  }

  /*
   * @testName: setDomainTest
   *
   * @assertion_ids: Servlet:JAVADOC:741;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setDomain cannot be
   * called once is set.
   */
  @Test
  public void setDomainTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setDomainTest");
    invoke();
  }

  /*
   * @testName: setMaxAgeTest
   *
   * @assertion_ids: Servlet:JAVADOC:743;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setMaxAge cannot be
   * called once is set.
   */
  @Test
  public void setMaxAgeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setMaxAgeTest");
    invoke();
  }

  /*
   * @testName: setHttpOnlyTest
   *
   * @assertion_ids: Servlet:JAVADOC:742;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setHttpOnly cannot be
   * called once is set.
   */
  @Test
  public void setHttpOnlyTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setHttpOnlyTest");
    invoke();
  }

  /*
   * @testName: setSecureTest
   *
   * @assertion_ids: Servlet:JAVADOC:746;
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setSecure cannot be
   * called once is set.
   */
  @Test
  public void setSecureTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setSecureTest");
    invoke();
  }

  /*
   * @testName: setAttributeTest
   *
   * @assertion_ids:
   *
   * @test_Strategy: Create a Servlet TestServlet, In the Servlet, turn
   * HttpSession on; Verify in servlet SessionCookieConfig.setAttribute cannot be
   * called once is set.
   */
  @Test
  public void setAttributeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setAttributeTest");
    invoke();
  }
}
