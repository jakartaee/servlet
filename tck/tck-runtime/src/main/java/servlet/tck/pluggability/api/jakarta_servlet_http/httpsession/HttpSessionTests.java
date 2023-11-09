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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpsession;

import servlet.tck.api.jakarta_servlet_http.httpsession.ExpireHttpSession;
import servlet.tck.api.jakarta_servlet_http.httpsession.GetLastAccessedTime;
import servlet.tck.api.jakarta_servlet_http.httpsession.TestServlet;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionTests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(HttpSessionTests.class.getResource("servlet_pluh_httpsession_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_httpsession_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ExpireHttpSession.class, GetLastAccessedTime.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   * servlet_waittime;
   */

  /* Run test */

  /*
   * @testName: getCreationTimeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:465
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getCreationTimeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCreationTimeTest");
    invoke();
  }

  /*
   * @testName: getCreationTimeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:466
   * 
   * @test_Strategy: Servlet starts session, invalidates it then calls method
   */
  @Test
  void getCreationTimeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCreationTimeIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: getIdTestServlet
   * 
   * @assertion_ids: Servlet:JAVADOC:467
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getIdTestServlet() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getIdTestServlet");
    invoke();
  }

  /*
   * @testName: getIdIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:467
   * 
   * @test_Strategy: Create a HttpSession; invalidate it; Verify that no
   * IllegalStateException is thrown when getId is called.
   */
  @Test
  void getIdIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getIdIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: getLastAccessedTimeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:469
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getLastAccessedTimeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLastAccessedTimeTest");
    invoke();
  }

  /*
   * @testName: getLastAccessedTimeSetGetTest
   * 
   * @assertion_ids: Servlet:JAVADOC:470
   * 
   * @test_Strategy: Servlet does a get/set operation
   */
  @Test
  void getLastAccessedTimeSetGetTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLastAccessedTimeSetGetTest");
    invoke();
  }

  /*
   * @testName: getLastAccessedTimeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:471
   * 
   * @test_Strategy: Servlet verifies exception is generated
   */
  @Test
  void getLastAccessedTimeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSession");
    TEST_PROPS.get().setProperty(SAVE_STATE, "true");
    invoke();
    TEST_PROPS.get().setProperty(APITEST,
        "getLastAccessedTimeIllegalStateExceptionTest");
    TEST_PROPS.get().setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: getMaxInactiveIntervalTest
   * 
   * @assertion_ids: Servlet:JAVADOC:474
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getMaxInactiveIntervalTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getMaxInactiveIntervalTest");
    invoke();
  }

  /*
   * @testName: getAttributeNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:480
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getAttributeNamesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeNamesTest");
    invoke();
  }

  /*
   * @testName: getAttributeNamesIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:481
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getAttributeNamesIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST,
        "getAttributeNamesIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: getAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:476
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeTest");
    invoke();
  }

  /*
   * @testName: getAttributeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:477
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getAttributeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: getServletContextTest
   * 
   * @assertion_ids: Servlet:JAVADOC:472
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getServletContextTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletContextTest");
    invoke();
  }

  /*
   * @testName: invalidateTest
   * 
   * @assertion_ids: Servlet:JAVADOC:496
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void invalidateTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "invalidateTest");
    invoke();
  }

  /*
   * @testName: invalidateIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:497
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void invalidateIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "invalidateIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: isNewTest
   * 
   * @assertion_ids: Servlet:JAVADOC:498
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void isNewTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "isNewTest");
    invoke();
  }

  /*
   * @testName: isNewIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:499
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void isNewIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "isNewIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: removeAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:492
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void removeAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "removeAttributeTest");
    invoke();
  }

  /*
   * @testName: removeAttributeDoNothingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:491
   * 
   * @test_Strategy: Servlet removes non-existant attribute then tries to tries
   * to get it.
   */
  @Test
  void removeAttributeDoNothingTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "removeAttributeDoNothingTest");
    invoke();
  }

  /*
   * @testName: removeAttributeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:493
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void removeAttributeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "removeAttributeIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: setAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:484
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeTest");
    invoke();
  }

  /*
   * @testName: setAttributeNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:487
   * 
   * @test_Strategy: Servlet passes null to setAttribute
   */
  @Test
  void setAttributeNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeNullTest");
    invoke();
  }

  /*
   * @testName: setAttributeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:488
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setAttributeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeIllegalStateExceptionTest");
    invoke();
  }

  /*
   * @testName: setMaxInactiveIntervalTest
   * 
   * @assertion_ids: Servlet:JAVADOC:473
   * 
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setMaxInactiveIntervalTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setMaxInactiveIntervalTest");
    invoke();
  }
}
