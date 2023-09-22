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
package servlet.tck.pluggability.api.jakarta_servlet.servletrequest30;

import servlet.tck.api.jakarta_servlet.servletrequest30.AsyncTestServlet;
import servlet.tck.api.jakarta_servlet.servletrequest30.AsyncTests;
import servlet.tck.api.jakarta_servlet.servletrequest30.SecondServlet;
import servlet.tck.api.jakarta_servlet.servletrequest30.TestServlet;
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

public class ServletRequest30Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(ServletRequest30Tests.class.getResource("servlet_plu_servletrequest30_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_servletrequest30_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AsyncTests.class, AsyncTestServlet.class, SecondServlet.class, TestServlet.class)
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
   * @assertion_ids: Servlet:JAVADOC:706;
   * 
   * @test_Strategy: ServletRequest calls getServletContext(); verifies that
   * returned ServletContext instance is consistent with the one stored in
   * ServletConfigs.
   */
  @Test
  public void getServletContextTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletContextTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "TEST FAILED");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestRequest
   *
   * @assertion_ids: Servlet:JAVADOC:703;
   *
   * @test_Strategy: Create a Servlet TestServlet; Client send a request to
   * TestServlet; call ServletRequest.getDispatcherType() verifies that
   * DispatcherType.REQUEST is returned.
   */
  @Test
  public void getDispatcherTypeTestRequest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestRequest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=REQUEST");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestForward
   *
   * @assertion_ids: Servlet:JAVADOC:703;
   *
   * @test_Strategy: Create two Servlets TestServlet and SecondServlet; Client
   * send a request to TestServlet; Forward to SecondServlet call
   * ServletRequest.getDispatcherType() verifies that DispatcherType.FORWARD is
   * returned.
   */
  @Test
  public void getDispatcherTypeTestForward() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestForward");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=FORWARD");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestInclude
   *
   * @assertion_ids: Servlet:JAVADOC:703;
   *
   * @test_Strategy: Create two Servlets TestServlet and SecondServlet; Client
   * send a request to TestServlet; Invoke SecondServlet using include call
   * ServletRequest.getDispatcherType() verifies that DispatcherType.INCLUDE is
   * returned.
   */
  @Test
  public void getDispatcherTypeTestInclude() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getDispatcherTypeTestInclude");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=INCLUDE");
    invoke();
  }

  /*
   * @testName: getDispatcherTypeTestError
   *
   * @assertion_ids: Servlet:JAVADOC:703;
   *
   * @test_Strategy: Create a Servlet SecondServlet to handle 404 error; Client
   * send a request to nowhereland; In SecondServlet, call
   * ServletRequest.getDispatcherType() verifies that DispatcherType.ERROR is
   * returned.
   */
  @Test
  public void getDispatcherTypeTestError() throws Exception {
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
   * @test_Strategy: Create two Servlets AsyncTestServlet and AsyncTests, both
   * supports async; Client send a request to AsyncTestServlet; StartAsync in
   * AsyncTestServlet; Invoke AsyncTests using AsyncContext.dispatch(String)
   * call ServletRequest.getDispatcherType() verifies that DispatcherType.ASYNC
   * is returned.
   */
  @Test
  public void getDispatcherTypeTestAsync() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=getDispatcherTypeTestAsync  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: asyncStartedTest1
   *
   * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
   * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
   * ServletRequest.startAsync(); call ServletRequest.isAsyncStarted() verifies
   * that true is returned.
   */
  @Test
  public void asyncStartedTest1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=asyncStartedTest1  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=true");
    invoke();
  }

  /*
   * @testName: asyncStartedTest2
   *
   * @assertion_ids: Servlet:JAVADOC:707;
   *
   * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
   * Client send a request to AsyncTestServlet; call
   * ServletRequest.isAsyncStarted() without start async mode verifies that
   * false is returned.
   */
  @Test
  public void asyncStartedTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=asyncStartedTest2  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=false");
    invoke();
  }

  /*
   * @testName: asyncStartedTest3
   *
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:638;
   *
   * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
   * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
   * ServletRequest.startAsync(); call ServletRequest.isAsyncStarted() after
   * AsyncContext.complete() called verifies that true is returned before it
   * dispatch return to the container
   */
  @Test
  public void asyncStartedTest3() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=asyncStartedTest3  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=true");
    invoke();
  }

  /*
   * @testName: asyncStartedTest4
   *
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:707;
   *
   * @test_Strategy: Create two Servlets AsyncTestServlet and AsyncTests, both
   * supports async; Client send a request to AsyncTestServlet; StartAsync in
   * AsyncTestServlet; Invoke AsyncTests using AsyncContext.dispatch(String)
   * call ServletRequest.isAsyncStarted() verifies that false is returned.
   */
  @Test
  public void asyncStartedTest4() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=asyncStartedTest4  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IsAsyncStarted=false");
    invoke();
  }

  /*
   * @testName: isAsyncSupportedTest1
   *
   * @assertion_ids: Servlet:JAVADOC:708;
   *
   * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
   * Client send a request to AsyncTestServlet; call
   * ServletRequest.isAsyncSupported() verifies that true is returned.
   */
  @Test
  public void isAsyncSupportedTest1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=isAsyncSupportedTest  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "isAsyncSupported=true");
    invoke();
  }

  /*
   * @testName: isAsyncSupportedTest2
   *
   * @assertion_ids: Servlet:JAVADOC:708;
   *
   * @test_Strategy: Create a Servlet TestServlet which does not support async,
   * Client send a request to TestServlet; call
   * ServletRequest.isAsyncSupported() verifies that false is returned.
   */
  @Test
  public void isAsyncSupportedTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/TestServlet?testname=isAsyncSupportedTest  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: startAsyncTest1
   *
   * @assertion_ids: Servlet:JAVADOC:711;
   *
   * @test_Strategy: Create a Servlet TestServlet which does not support async,
   * Client send a request to TestServlet; call ServletRequest.startAsyncTest()
   * verifies that IllegalStateException is thrown.
   */
  @Test
  public void startAsyncTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncTest");
    invoke();
  }

  /*
   * @testName: startAsyncTest2
   *
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:710;
   * Servlet:JAVADOC:711;
   *
   * @test_Strategy: Create two Servlets AsyncTestServlet and AsyncTests, both
   * supports async; Client send a request to AsyncTestServlet; StartAsync in
   * AsyncTestServlet; Invoke AsyncTests using AsyncContext.dispatch(String)
   * Call ServletRequest.startAsyncTest() outside of dispatch verifies that
   * IllegalStateException is thrown.
   */
  @Test
  public void startAsyncTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=startAsyncTest  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: getAsyncContextTest
   *
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:701;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlets AsyncTestServlet which supports async;
   * Client send a request to AsyncTestServlet; Start Async in AsyncTestServlet;
   * Call ServletRequest.getAsyncContext() verifies it works.
   */
  @Test
  public void getAsyncContextTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/AsyncTestServlet?testname=getAsyncContextTest  HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }
}
