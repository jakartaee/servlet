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
package servlet.tck.spec.requestdispatcher;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestDispatcherTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_requestdispatcher_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ForwardedServlet.class, HttpTestServlet.class, IncludedServlet.class,
                    MultiForwardedServlet.class, TestServlet.class, WrapServlet.class)
            .setWebXML(RequestDispatcherTests.class.getResource("servlet_spec_requestdispatcher_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /*
   * @testName: getRequestAttributes
   *
   * @assertion_ids: Servlet:SPEC:76.1; Servlet:SPEC:76.2; Servlet:SPEC:76.3;
   * Servlet:SPEC:76.4; Servlet:SPEC:76.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and IncludedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(String), and access IncludedServlet
   * using RequestDispatcher.include. 3. Verify in IncludedServlet the following
   * request attributes set correctly required by Servlet 2.4 Spec:
   * jakarta.servlet.include.request_uri; jakarta.servlet.include.context_path;
   * jakarta.servlet.include.servlet_path; jakarta.servlet.include.path_info;
   * jakarta.servlet.include.query_string;
   */
  @Test
  public void getRequestAttributes() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.include.request_uri=SET_GOOD;jakarta.servlet.include.context_path=SET_GOOD;jakarta.servlet.include.servlet_path=SET_GOOD;jakarta.servlet.include.path_info=SET_NO;jakarta.servlet.include.query_string=SET_GOOD;");

    TEST_PROPS.setProperty(APITEST, "includeAttributes");
    invoke();
  }

  /*
   * @testName: getRequestAttributes1
   *
   * @assertion_ids: Servlet:SPEC:76.1; Servlet:SPEC:76.2; Servlet:SPEC:76.3;
   * Servlet:SPEC:76.4; Servlet:SPEC:76.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and IncludedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletRequest.getRequestDispatcher(String), and access IncludedServlet
   * using RequestDispatcher.include. 3. Verify in IncludedServlet the following
   * request attributes set correctly required by Servlet 2.4 Spec:
   * jakarta.servlet.include.request_uri; jakarta.servlet.include.context_path;
   * jakarta.servlet.include.servlet_path; jakarta.servlet.include.path_info;
   * jakarta.servlet.include.query_string;
   */
  @Test
  public void getRequestAttributes1() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.include.request_uri=SET_GOOD;jakarta.servlet.include.context_path=SET_GOOD;jakarta.servlet.include.servlet_path=SET_GOOD;jakarta.servlet.include.path_info=SET_NO;jakarta.servlet.include.query_string=SET_GOOD;");

    TEST_PROPS.setProperty(APITEST, "includeAttributes1");
    invoke();
  }

  /*
   * @testName: getRequestAttributes2
   *
   * @assertion_ids: Servlet:SPEC:76.1; Servlet:SPEC:76.2; Servlet:SPEC:76.3;
   * Servlet:SPEC:76.4; Servlet:SPEC:76.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and IncludedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getNamedDispatcher(String), and access IncludedServlet using
   * RequestDispatcher.include. 3. Verify in IncludedServlet the following
   * request attributes not set required by Servlet 2.4 Spec:
   * jakarta.servlet.include.request_uri; jakarta.servlet.include.context_path;
   * jakarta.servlet.include.servlet_path; jakarta.servlet.include.path_info;
   * jakarta.servlet.include.query_string;
   */
  @Test
  public void getRequestAttributes2() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.include.request_uri=SET_NO;jakarta.servlet.include.context_path=SET_NO;jakarta.servlet.include.servlet_path=SET_NO;jakarta.servlet.include.path_info=SET_NO;jakarta.servlet.include.query_string=SET_NO;");

    TEST_PROPS.setProperty(APITEST, "includeAttributes2");
    invoke();
  }

  /*
   * @testName: requestDispatcherIncludeIOAndServletExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82; Servlet:JAVADOC:279; Servlet:JAVADOC:280;
   * 
   * @test_Strategy: Validate an exception thrown during a
   * RequestDispatcher.include() operation results in an IOException or
   * ServletException being thrown, the Servlet or IOException will be
   * propagated back to the caller and will not be wrapped by a
   * ServletException.
   */
  @Test
  public void requestDispatcherIncludeIOAndServletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=includeIOAndServletException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED|Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: requestDispatcherIncludeRuntimeExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82
   * 
   * @test_Strategy: Validate a RuntimeException thrown during a
   * RequestDispacher.include() operation results in the RuntimeException being
   * propagated back to the caller and will not be wrapped by a
   * ServletException.
   */
  @Test
  public void requestDispatcherIncludeRuntimeExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=includeUnCheckedException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: requestDispatcherIncludeCheckedExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82
   * 
   * @test_Strategy: Validate a checked exception that is thrown during a
   * RequetDispatcher.include() operation and is not an instance of
   * ServletException or IOException is returned to the caller wrapped by
   * ServletException.
   */
  @Test
  public void requestDispatcherIncludeCheckedExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=includeCheckedException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: requestDispatcherForwardIOAndServletExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82; Servlet:JAVADOC:275; Servlet:JAVADOC:276;
   * 
   * @test_Strategy: Validate an exception thrown during a
   * RequestDispatcher.forward() operation results in an IOException or
   * ServletException being thrown, the Servlet or IOException will be
   * propagated back to the caller and will not be wrapped by a
   * ServletException.
   */
  @Test
  public void requestDispatcherForwardIOAndServletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=forwardIOAndServletException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED|Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: requestDispatcherForwardRuntimeExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82
   * 
   * @test_Strategy: Validate a RuntimeException thrown during a
   * RequestDispacher.forward() operation results in the RuntimeException being
   * propagated back to the caller and will not be wrapped by a
   * ServletException.
   */
  @Test
  public void requestDispatcherForwardRuntimeExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=forwardUnCheckedException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: requestDispatcherForwardCheckedExceptionTest
   * 
   * @assertion_ids: Servlet:SPEC:82
   * 
   * @test_Strategy: Validate a checked exception that is thrown during a
   * RequetDispatcher.forward() operation and is not an instance of
   * ServletException or IOException is returned to the caller wrapped by
   * ServletException.
   */
  @Test
  public void requestDispatcherForwardCheckedExceptionTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=forwardCheckedException HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: getRequestAttributes3
   *
   * @assertion_ids: Servlet:SPEC:180.1; Servlet:SPEC:180.2; Servlet:SPEC:180.3;
   * Servlet:SPEC:180.4; Servlet:SPEC:180.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and ForwardedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(String), and access ForwardedServlet
   * using RequestDispatcher.forward. 3. Verify in ForwardedServlet the
   * following request attributes set correctly required by Servlet 2.4 Spec:
   * jakarta.servlet.forward.request_uri; jakarta.servlet.forward.context_path;
   * jakarta.servlet.forward.servlet_path; jakarta.servlet.forward.path_info;
   * jakarta.servlet.forward.query_string;
   */
  @Test
  public void getRequestAttributes3() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.forward.request_uri=SET_GOOD;"
            + "jakarta.servlet.forward.context_path=SET_GOOD;"
            + "jakarta.servlet.forward.servlet_path=SET_GOOD;"
            + "jakarta.servlet.forward.path_info=SET_NO;"
            + "jakarta.servlet.forward.query_string=SET_GOOD;");

    TEST_PROPS.setProperty(APITEST, "forwardAttributes");
    invoke();
  }

  /*
   * @testName: getRequestAttributes4
   *
   * @assertion_ids: Servlet:SPEC:180.1; Servlet:SPEC:180.2; Servlet:SPEC:180.3;
   * Servlet:SPEC:180.4; Servlet:SPEC:180.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and ForwardedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletRequest.getRequestDispatcher(String), and access ForwardedServlet
   * using RequestDispatcher.forward. 3. Verify in ForwardedServlet the
   * following request attributes set correctly required by Servlet 2.4 Spec:
   * jakarta.servlet.forward.request_uri; jakarta.servlet.forward.context_path;
   * jakarta.servlet.forward.servlet_path; jakarta.servlet.forward.path_info;
   * jakarta.servlet.forward.query_string;
   */
  @Test
  public void getRequestAttributes4() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.forward.request_uri=SET_GOOD;"
            + "jakarta.servlet.forward.context_path=SET_GOOD;"
            + "jakarta.servlet.forward.servlet_path=SET_GOOD;"
            + "jakarta.servlet.forward.path_info=SET_NO;"
            + "jakarta.servlet.forward.query_string=SET_GOOD;");

    TEST_PROPS.setProperty(APITEST, "forwardAttributes1");
    invoke();
  }

  /*
   * @testName: getRequestAttributes5
   *
   * @assertion_ids: Servlet:SPEC:181.1; Servlet:SPEC:181.2; Servlet:SPEC:181.3;
   * Servlet:SPEC:181.4; Servlet:SPEC:181.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and ForwardedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getNamedDispatcher(String), and access ForwardedServlet
   * using RequestDispatcher.forward. 3. Verify in ForwardedServlet the
   * following request attributes are not set required by Servlet 2.4 Spec:
   * jakarta.servlet.forward.request_uri; jakarta.servlet.forward.context_path;
   * jakarta.servlet.forward.servlet_path; jakarta.servlet.forward.path_info;
   * jakarta.servlet.forward.query_string;
   */
  @Test
  public void getRequestAttributes5() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.forward.request_uri=SET_NO;"
            + "jakarta.servlet.forward.context_path=SET_NO;"
            + "jakarta.servlet.forward.servlet_path=SET_NO;"
            + "jakarta.servlet.forward.path_info=SET_NO;"
            + "jakarta.servlet.forward.query_string=SET_NO;");

    TEST_PROPS.setProperty(APITEST, "forwardAttributes2");
    invoke();
  }

  /*
   * @testName: getRequestAttributes6
   *
   * @assertion_ids: Servlet:SPEC:181.1; Servlet:SPEC:181.2; Servlet:SPEC:181.3;
   * Servlet:SPEC:181.4; Servlet:SPEC:181.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet, ForwardedServlet and
   * MultiForwardedServlet; 2. In TestServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(String), and access
   * MultiForwardedServlet using RequestDispatcher.forward. 3. In
   * MultiForwardedServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(String), and access ForwardedServlet
   * using RequestDispatcher.forward. 4. Verify in ForwardedServlet the
   * following request attributes are set required by Servlet 2.4 Spec:
   * jakarta.servlet.forward.request_uri; jakarta.servlet.forward.context_path;
   * jakarta.servlet.forward.servlet_path; jakarta.servlet.forward.path_info;
   * jakarta.servlet.forward.query_string;
   */
  @Test
  public void getRequestAttributes6() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "jakarta.servlet.forward.request_uri=SET_GOOD;"
            + "jakarta.servlet.forward.context_path=SET_GOOD;"
            + "jakarta.servlet.forward.servlet_path=SET_GOOD;"
            + "jakarta.servlet.forward.path_info=SET_NO;"
            + "jakarta.servlet.forward.query_string=SET_GOOD;");

    TEST_PROPS.setProperty(APITEST, "forwardAttributes6");
    invoke();
  }

  /*
   * @testName: bufferContent
   *
   * @assertion_ids: Servlet:SPEC:77;
   *
   * @test_Strategy: 1. Create servlets TestServlet and ForwardedServlet; 2. In
   * TestServlet, first write "Test FAILED" to ServletResponse; then access
   * ForwardedServlet using RequestDispatcher.forward. 3. Verify that the
   * message "Test FAILED" wrote to ServletResponse is cleared and not sent to
   * Client as required by Servlet 2.4 Spec.
   */
  @Test
  public void bufferContent() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING,
        "bufferContent_in_ForwardedServlet_invoked");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    TEST_PROPS.setProperty(APITEST, "bufferContent");
    invoke();
  }

  /*
   * @testName: requestDispatcherNoWrappingTest
   * 
   * @assertion_ids: Servlet:SPEC:50;
   * 
   * @test_Strategy: Validate the container passes the same objects from a
   * RequestDispatcher operation to the target entity. The container should not
   * wrap the object at any point.
   */
  @Test
  public void requestDispatcherNoWrappingTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=rdNoWrappingTest&operation=0 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
    TEST_PROPS.setProperty(REQUEST,
        "GET /servlet_spec_requestdispatcher_web/TestServlet?testname=rdNoWrappingTest&operation=1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: getRequestURIIncludeTest
   * 
   * @assertion_ids: Servlet:SPEC:76; Servlet:JAVADOC:561;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. In
   * TestServlet;, access HttpTestServlet using RequestDispatcher.include. 3.
   * Verify in HttpTestServlet, that getRequestURI returns correct URI according
   * to 8.3
   */
  @Test
  public void getRequestURIIncludeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRequestURIIncludeTest");
    invoke();
  }

  /*
   * @testName: getRequestURLIncludeTest
   * 
   * @assertion_ids: Servlet:SPEC:76; Servlet:JAVADOC:562;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. In
   * TestServlet;, access HttpTestServlet using RequestDispatcher.include. 3.
   * Verify in HttpTestServlet, that getRequestURL returns correct URI according
   * to 8.3
   */
  @Test
  public void getRequestURLIncludeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRequestURLIncludeTest");
    invoke();
  }

  /*
   * @testName: getRequestURIForwardTest
   * 
   * @assertion_ids: Servlet:SPEC:78;; Servlet:JAVADOC:561;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. In
   * TestServlet;, access HttpTestServlet using RequestDispatcher.forward. 3.
   * Verify in HttpTestServlet, that getRequestURI returns correct URI according
   * to 8.4
   */
  @Test
  public void getRequestURIForwardTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRequestURIForwardTest");
    invoke();
  }

  /*
   * @testName: getRequestURLForwardTest
   * 
   * @assertion_ids: Servlet:SPEC:78; Servlet:JAVADOC:562;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. In
   * TestServlet;, access HttpTestServlet using RequestDispatcher.forward. 3.
   * Verify in HttpTestServlet, that getRequestURL returns correct URL according
   * to 8.4
   */
  @Test
  public void getRequestURLForwardTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRequestURLForwardTest");
    invoke();
  }

  /*
   * @testName: getQueryStringIncludeTest
   * 
   * @assertion_ids: Servlet:SPEC:192; Servlet:JAVADOC:552;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. Send
   * request to TestServlet with ?testname=getQueryStringIncludeTest; 3. In
   * TestServlet, access HttpTestServlet using RequestDispatcher.include, with
   * ?testname=getQueryStringTestInclude; 4. Verify in HttpTestServlet, that
   * getQueryString returns correct QueryString
   * testname=getQueryStringIncludeTest according to 8.3
   */
  @Test
  public void getQueryStringIncludeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getQueryStringIncludeTest");
    invoke();
  }

  /*
   * @testName: getQueryStringForwardTest
   * 
   * @assertion_ids: Servlet:SPEC:78; Servlet:JAVADOC:552;
   * 
   * @test_Strategy: 1. Create servlets TestServlet and HttpTestServlet; 2. Send
   * request to TestServlet with ?testname=getQueryStringForwardTest; 3. In
   * TestServlet, access HttpTestServlet using RequestDispatcher.forward, with
   * ?testname=getQueryStringTestForward; 4. Verify in HttpTestServlet, that
   * getQueryString returns correct QueryString
   * testname=getQueryStringTestForward according to 8.4.2
   */
  @Test
  public void getQueryStringForwardTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getQueryStringForwardTest");
    invoke();
  }
}
