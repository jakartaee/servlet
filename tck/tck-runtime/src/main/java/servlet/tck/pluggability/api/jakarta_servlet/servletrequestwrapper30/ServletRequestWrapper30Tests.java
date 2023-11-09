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
package servlet.tck.pluggability.api.jakarta_servlet.servletrequestwrapper30;

import servlet.tck.api.jakarta_servlet.servletrequest30.AsyncTestServlet;
import servlet.tck.api.jakarta_servlet.servletrequest30.AsyncTests;
import servlet.tck.api.jakarta_servlet.servletrequest30.SecondServlet;
import servlet.tck.api.jakarta_servlet.servletrequest30.TestServlet;
import servlet.tck.api.jakarta_servlet.servletrequestwrapper30.AsyncTestServletWrapper;
import servlet.tck.api.jakarta_servlet.servletrequestwrapper30.ErrorServletWrapper;
import servlet.tck.api.jakarta_servlet.servletrequestwrapper30.TestServletWrapper;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class ServletRequestWrapper30Tests extends AbstractTckTest {


  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(ServletRequestWrapper30Tests.class.getResource("servlet_plu_servletrequestwrapper30_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_servletrequestwrapper30_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AsyncTestServletWrapper.class, ErrorServletWrapper.class, TestServletWrapper.class,
                    TestServlet.class, AsyncTestServlet.class, AsyncTests.class, SecondServlet.class)
            .addAsLibraries(javaArchive1);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */
  /*
   * @testName: getServletContextTest
   *
   * @assertion_ids: Servlet:JAVADOC:721;
   *
   * @test_Strategy: Create a ServletRequestWrapper that wrap another servlet;
   * calls ServletRequestWrapper.getServletContext(); verifies that returned
   * ServletContext instance is consistent with the one stored in
   * ServletConfigs.
   */
  @Test
  void getServletContextTest() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getServletContextTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "TEST FAILED");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestRequest
   *
   * @assertion_ids: Servlet:JAVADOC:718;
   *
   * @test_Strategy: Create a ServletRequestWrapper that wraps TestServlet;
   * Client send a request the ServletRequestWrapper; call
   * ServletRequestWrapper.getDispatcherType() verifies that
   * DispatcherType.REQUEST is returned.
   */
  @Test
  void getDispatcherTypeTestRequest() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestRequest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=REQUEST");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestForward
   *
   * @assertion_ids: Servlet:JAVADOC:718;
   *
   * @test_Strategy: Create a ServletRequestWrapper that wraps TestServlet;
   * Client send a request the ServletRequestWrapper; Forward to SecondServlet
   * call ServletRequestWrapper.getDispatcherType() verifies that
   * DispatcherType.FORWARD is returned.
   */
  @Test
  void getDispatcherTypeTestForward() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestForward");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=FORWARD");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestInclude
   *
   * @assertion_ids: Servlet:JAVADOC:718;
   *
   * @test_Strategy: Create a ServletRequestWrapper that wraps TestServlet;
   * Client send a request the ServletRequestWrapper; Include to SecondServlet
   * call ServletRequestWrapper.getDispatcherType() verifies that
   * DispatcherType.INCLUDE is returned.
   */
  @Test
  void getDispatcherTypeTestInclude() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestInclude");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=INCLUDE");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestError
   *
   * @assertion_ids: Servlet:JAVADOC:718;
   *
   * @test_Strategy: Create a ServletRequestWrapper that wraps SecondServlet;
   * Maps 404 to the ServletRequestWrapper; Client send a request to
   * nowhereland; In SecondServlet, call
   * ServletRequestWrapper.getDispatcherType() verifies that
   * DispatcherType.ERROR is returned.
   */
  @Test
  void getDispatcherTypeTestError() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/nowheretobefound/  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=ERROR");
    TEST_PROPS.get().setProperty(STATUS_CODE, NOT_FOUND);
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestAsync
   *
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet and AsyncTests, all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * StartAsync in AsyncTestServlet; Invoke AsyncTests using
   * AsyncContext.dispatch(String) call
   * ServletRequestWrapper.getDispatcherType() verifies that
   * DispatcherType.ASYNC is returned.
   */
  @Test
  void getDispatcherTypeTestAsync() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestAsync");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: asyncStartedTest1
   *
   * @assertion_ids: Servlet:JAVADOC:722; Servlet:JAVADOC:727;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet and AsyncTests, all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * StartAsync in AsyncTestServlet ServletRequestWrapper.startAsync(); call
   * ServletRequestWrapper.isAsyncStarted() verifies that true is returned.
   */
  @Test
  void asyncStartedTest1() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "asyncStartedTest1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=true");
    invoke();
  }

  /*
   * @testName: asyncStartedTest2
   *
   * @assertion_ids: Servlet:JAVADOC:722;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet and AsyncTests, all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * call ServletRequestWrapper.isAsyncStarted() without start async mode
   * verifies that false is returned.
   */
  @Test
  void asyncStartedTest2() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "asyncStartedTest2");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=false");
    invoke();
  }

  /*
   * @testName: asyncStartedTest3
   *
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:722;
   * Servlet:JAVADOC:727;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet and AsyncTests, all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * StartAsync in AsyncTestServlet ServletRequestWrapper.startAsync(); call
   * ServletRequestWrapper.isAsyncStarted() after AsyncContext.complete() called
   * verifies that true is returned before it dispatch return to the container
   */
  @Test
  void asyncStartedTest3() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "asyncStartedTest3");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=true");
    invoke();
  }

  /*
   * @testName: asyncStartedTest4
   *
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:722;
   * Servlet:JAVADOC:727;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet and AsyncTests, all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * StartAsync in AsyncTestServlet ServletRequestWrapper.startAsync(); Invoke
   * AsyncTests using AsyncContext.dispatch(String) call
   * ServletRequestWrapper.isAsyncStarted() verifies that false is returned.
   */
  @Test
  void asyncStartedTest4() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "asyncStartedTest4");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=false");
    invoke();
  }

  /*
   * @testName: isAsyncSupportedTest1
   *
   * @assertion_ids: Servlet:JAVADOC:723;
   *
   * @test_Strategy: Create two Servlets AsyncTestServletWrapper,
   * AsyncTestServlet all support async; AsyncTestServletWrapper wraps
   * AsyncTestServlet; Client send a request to AsyncTestServletWrapper; call
   * ServletRequestWrapper.isAsyncSupported() verifies that true is returned.
   */
  @Test
  void isAsyncSupportedTest1() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "isAsyncSupportedTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "isAsyncSupported=true");
    invoke();
  }

  /*
   * @testName: isAsyncSupportedTest2
   *
   * @assertion_ids: Servlet:JAVADOC:723;
   *
   * @test_Strategy: Create two Servlets TestServletWrapper, TestServlet all
   * does not support async; TestServletWrapper wraps TestServlet; Client send a
   * request to TestServletWrapper; call
   * ServletRequestWrapper.isAsyncSupported() verifies that false is returned.
   */
  @Test
  void isAsyncSupportedTest2() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "isAsyncSupportedTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: startAsyncTest1
   *
   * @assertion_ids: Servlet:JAVADOC:728;
   *
   * @test_Strategy: Create two Servlets TestServletWrapper, TestServlet all
   * does not support async; TestServletWrapper wraps TestServlet; Client send a
   * request to TestServletWrapper; call ServletRequestWrapper.startAsyncTest()
   * verifies that IllegalStateException is thrown.
   */
  @Test
  void startAsyncTest1() throws Exception {
    setServletName("TestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "startAsyncTest");
    invoke();
  }

  /*
   * @testName: startAsyncTest2
   *
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:722;
   * Servlet:JAVADOC:727; Servlet:JAVADOC:728;
   *
   * @test_Strategy: Create three Servlets AsyncTestServletWrapper,
   * AsyncTestServlet, AsyncTest all support async; AsyncTestServletWrapper
   * wraps AsyncTestServlet; Client send a request to AsyncTestServletWrapper;
   * Start Async in AsyncTestServlet; Invoke AsyncTests using
   * AsyncContext.dispatch(String) Call ServletRequestWrapper.startAsyncTest()
   * outside of dispatch verifies that IllegalStateException is thrown.
   */
  @Test
  void startAsyncTest2() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "startAsyncTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: getAsyncContextTest
   *
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:716;
   * Servlet:JAVADOC:727;
   *
   * @test_Strategy: Create two Servlets AsyncTestServletWrapper,
   * AsyncTestServlet all support async; AsyncTestServletWrapper wraps
   * AsyncTestServlet; Client send a request to AsyncTestServletWrapper; Start
   * Async in AsyncTestServlet; call ServletRequestWrapper.getAsyncContext()
   * verifies it works.
   */
  @Test
  void getAsyncContextTest() throws Exception {
    setServletName("AsyncTestServletWrapper");
    TEST_PROPS.get().setProperty(APITEST, "getAsyncContextTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }
}
