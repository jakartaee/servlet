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
 * $Id: URLClient.java 52425 2007-01-18 19:31:04Z djiao $
 */

/**
 * Tests that focus on cross-context
 */
package servlet.tck.api.jakarta_servlet_http.httpsessionx;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionxTests extends AbstractTckTest {
  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpsessionx_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(HttpSessionxTests.class.getResource("servlet_jsh_httpsessionx_web.xml"));
  }

  @Deployment(testable = false, name = "servlet_jsh_httpsession2x_web")
  public static WebArchive getTestArchive2() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpsessionx2_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ExpireHttpSession.class, GetNewSession.class,
                    InvalidateHttpSession.class, SetMaxInterval.class)
            .setWebXML(HttpSessionxTests.class.getResource("servlet_jsh_httpsessionx2_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /*
   * @testName: getMaxInactiveIntervalTest
   * 
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   * 
   * @test_Strategy: 1. Client sends request to TestServlet which - create a new
   * HttpSession using request.getSession(true); - set the session's
   * maxInactiveInterval to 10 secs. Client captures the JSESSIONID returned in
   * the response. 2. Within maxInactiveInterval, client sends a request that
   * contains the JSESSIONID in a request cookie to the servlet again, verify
   * that MaxInactiveInterval is set correctly
   */
  @Test
  public void getMaxInactiveIntervalTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSession");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "getMaxInactiveIntervalTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: expireHttpSessionTest
   *
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which - creates a
   * session using request.getSession() - and sets the session's
   * maxInactiveInterval to 10 secs. Client captures the JSESSIONID returned in
   * the response.
   *
   * 2. Client waits for longer than maxInactiveInterval, and sends a
   * request(including JSESSIONID) to the servlet. The servlet tries to resume
   * the session by calling request.getSession(false). Verify that no session is
   * returned this time
   */
  @Test
  public void expireHttpSessionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSession");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    TEST_PROPS.setProperty(APITEST, "expireHttpSessionTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: expireHttpSessionxTest
   *
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which - creates a
   * session using request.getSession() - and sets the session's
   * maxInactiveInterval to 10 secs. Client captures the JSESSIONID returned in
   * the response.
   *
   * 2. Client waits for longer than maxInactiveInterval, sends a request with
   * JSESSIONID to a servlet with different context. The servlet tries to resume
   * the session by calling request.getSession(false). Verify that no session is
   * returned this time
   */
  @Test
  public void expireHttpSessionxTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSession");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    TEST_PROPS.setProperty(REQUEST, "GET " + "/servlet_jsh_httpsessionx2_web"
        + "/expireHttpSession" + " HTTP/1.1");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, "Session expired as expected.");

    invoke();
  }

  /*
   * @testName: expireHttpSessionxriTest
   *
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which - creates a
   * session using request.getSession() - and sets the session's
   * maxInactiveInterval to 10 secs. Client captures the JSESSIONID returned in
   * the response.
   *
   * 2. Client waits for longer than maxInactiveInterval, sends a request with
   * JSESSIONID to the servlet. The servlet send request to second servlet using
   * RequestDispatcher. Then the second servlet tries to resume the session by
   * calling request.getSession(false). Verify that no session is returned this
   * time
   */
  @Test
  public void expireHttpSessionxriTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSession");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    TEST_PROPS.setProperty(APITEST, "expireHttpSessionxriTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: expireHttpSessionxri1Test
   *
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which The servlet
   * send request to second servlet using RequestDispatcher.include.
   *
   * 2. The second servlet tries to - creates a session using
   * request.getSession() - and sets the session's maxInactiveInterval to 10
   * secs. Client captures the JSESSIONID returned in the response.
   *
   * 3. Client waits for longer than maxInactiveInterval, sends a request with
   * JSESSIONID to the first servlet. Which again send request to second servlet
   * using RequestDispatcher.
   *
   * 4. The second servlet tries to resume the session by calling
   * request.getSession(false). Verify that no session is returned this time
   */
  @Test
  public void expireHttpSessionxri1Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalxiTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    TEST_PROPS.setProperty(APITEST, "expireHttpSessionxriTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: expireHttpSessionxrfTest
   *
   * @assertion_ids: Servlet:JAVADOC:473; Servlet:JAVADOC:474; Servlet:SPEC:202;
   * Servlet:SPEC:66; Servlet:JAVADOC:565; Servlet:JAVADOC:566;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which The servlet
   * send request to second servlet using RequestDispatcher.forward.
   *
   * 2. The second servlet tries to - creates a session using
   * request.getSession() - and sets the session's maxInactiveInterval to 10
   * secs. Client captures the JSESSIONID returned in the response.
   *
   * 3. Client waits for longer than maxInactiveInterval, sends a request with
   * JSESSIONID to the first servlet. Which again send request to second servlet
   * using RequestDispatcher.forward.
   *
   * 4. The second servlet tries to resume the session by calling
   * request.getSession(false). Verify that no session is returned this time
   */
  @Test
  public void expireHttpSessionxrfTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setMaxInactiveIntervalxfTest");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    try {
      Thread.sleep(11000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }

    TEST_PROPS.setProperty(APITEST, "expireHttpSessionxrfTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: invalidateHttpSessionTest
   *
   * @assertion_ids: Servlet:JAVADOC:566; Servlet:JAVADOC:565; Servlet:SPEC:202;
   * Servlet:JAVADOC:496; Servlet:JAVADOC:568;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which - creates a
   * session using request.getSession(true) Client captures the JSESSIONID
   * returned in the response.
   *
   * 2. Client sends a request(including JSESSIONID) to the servlet. The servlet
   * tries to resume the session, then invalidate the session by calling
   * Session.invalidate(). Verify that session is invalidated
   */
  @Test
  public void invalidateHttpSessionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSession");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "invalidateSessionTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }

  /*
   * @testName: invalidateHttpSessionxTest
   *
   * @assertion_ids: Servlet:JAVADOC:566; Servlet:JAVADOC:565; Servlet:SPEC:202;
   * Servlet:JAVADOC:496; Servlet:JAVADOC:568;
   *
   * @test_Strategy: 1. Client sends request to TestServlet which - send request
   * to a second servlet using RequestDispatcher - which creates a session using
   * request.getSession(true) Client captures the JSESSIONID returned in the
   * response.
   *
   * 2. Client sends a request with JSESSIONID to the first servlet. The first
   * servlet invoke the second servlet using RequestDispatcher, The second
   * servlet invalidate the session by calling Session.invalidate(). Verify that
   * session is invalidated
   */
  @Test
  public void invalidateHttpSessionxTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getNewSessionx");
    TEST_PROPS.setProperty(SAVE_STATE, "true");
    invoke();

    TEST_PROPS.setProperty(APITEST, "invalidateSessionxTest");
    TEST_PROPS.setProperty(USE_SAVED_STATE, "true");
    invoke();
  }
}
