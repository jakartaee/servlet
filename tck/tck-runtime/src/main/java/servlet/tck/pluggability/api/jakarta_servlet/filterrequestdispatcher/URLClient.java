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
package servlet.tck.pluggability.api.jakarta_servlet.filterrequestdispatcher;

import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.DummyServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.ErrorPage;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.ForwardTest1Servlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.ForwardTestServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.ForwardedServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.IncludeTest1Servlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.IncludeTestServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.IncludedServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.RequestTestServlet;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.TestServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import servlet.tck.api.jakarta_servlet.filterrequestdispatcher.Test_Filter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(URLClient.class.getResource("servlet_plu_filterrequestdispatcher_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_filterrequestdispatcher_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DummyServlet.class, ErrorPage.class, ForwardedServlet.class,
                    ForwardTest1Servlet.class, ForwardTestServlet.class, IncludedServlet.class,
                    IncludeTest1Servlet.class, IncludeTestServlet.class, RequestTestServlet.class,
                    Test_Filter.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: RequestTest
   *
   * @assertion_ids: Servlet:SPEC:54; Servlet:SPEC:59;
   *
   * @test_Strategy: 1. Create a servlet, a JSP page and a web page -
   * /generic/DummyServlet, /generic/dummyJSP, and /dummy.html. 2. Map a filter
   * Test_Filter on all above three with dispatcher value set to REQUEST using
   * url-pattern, as well as ERROR, FORWARD and INCLUDE. 3. Client try to access
   * all of them directly. 4. Verify that filter is properly invoked.
   */
  @Test
  public void RequestTest() throws Exception {
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(APITEST, "generic/DummyServlet");
    invoke();

    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(APITEST, "generic/dummyJSP");
    invoke();

    TEST_PROPS.setProperty(APITEST, "dummy.html");
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    invoke();
  }

  /*
   * @testName: RequestTest1
   *
   * @assertion_ids: Servlet:SPEC:54;
   *
   * @test_Strategy: 1. Create a servlet - RequestTestServlet. 2. Map a filter
   * Test_Filter on RequestTestServlet with dispatcher value set to REQUEST
   * using servlet-name 3. Client try to access RequestTestServlet directly. 4.
   * Verify that filter is properly invoked.
   */
  @Test
  public void RequestTest1() throws Exception {
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(APITEST, "request/RequestTest");
    invoke();
  }

  /*
   * @testName: RequestTest2
   *
   * @assertion_ids: Servlet:SPEC:54;
   *
   * @test_Strategy: 1. Create a servlet - forward/ForwardedServlet. 2. Map a
   * filter Test_Filter on forward/ForwardedServlet with dispatcher value not
   * set to REQUEST but to FORWARD only. 3. Client try to access
   * forward/ForwardedServlet directly. 4. Verify that filter is not invoked.
   */
  @Test
  public void RequestTest2() throws Exception {
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(SEARCH_STRING, Data.FAILED);
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, Data.PASSED);

    TEST_PROPS.setProperty(APITEST, "forward/ForwardedServlet");
    invoke();
  }

  /*
   * @testName: ForwardTest
   *
   * @assertion_ids: Servlet:SPEC:55; Servlet:JAVADOC:273;
   *
   * @test_Strategy: 1. Create two servlets - TestServlet, ForwardedServlet. 2.
   * Map a filter Test_Filter using <servlet-name> for ForwardedServlet with
   * dispacther value FORWARD. 3. Client try to use the RequestDispatcher to
   * forward to ForwardedServlet through TestServlet. 4. Verify that filter is
   * properly invoked.
   */
  @Test
  public void ForwardTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "forwardTest");
    invoke();
  }

  /*
   * @testName: ForwardTest1
   *
   * @assertion_ids: Servlet:SPEC:55; Servlet:SPEC:59; Servlet:JAVADOC:273;
   *
   * @test_Strategy: 1. Create two servlets - ForwardTest1Servlet,
   * /generic/TestServlet 2. Map a filter Test_Filter using <url-pattern> for
   * TestServlet with dispacther value FORWARD, as well as ERROR, INCLUDE and
   * REQUEST. 3. Client try to access ForwardTest1Servlet which in turn use the
   * RequestDispatcher to forward to TestServlet. 4. Verify that filter is
   * properly invoked.
   */
  @Test
  public void ForwardTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "forwardServletTest");
    invoke();

    TEST_PROPS.setProperty(APITEST, "forwardJSPTest");
    invoke();

    TEST_PROPS.setProperty(APITEST, "forwardHTMLTest");
    invoke();
  }

  /*
   * @testName: IncludeTest
   *
   * @assertion_ids: Servlet:SPEC:56; Servlet:SPEC:59;
   *
   * @test_Strategy: 1. Create two servlets - TestServlet, IncludedServlet. 2.
   * Map a filter Test_Filter on IncludedServlet with dispacther value set to
   * INCLUDE only. 3. Client try to use the RequestDispatcher's include to
   * access IncludedServlet through TestServlet. 4. Verify that filter is
   * properly invoked.
   */
  @Test
  public void IncludeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "includeTest");
    invoke();
  }

  /*
   * @testName: IncludeTest1
   *
   * @assertion_ids: Servlet:SPEC:56; Servlet:SPEC:59;
   *
   * @test_Strategy: 1. Create two servlet - TestServlet, /generic/DummyServlet,
   * a JSP dummyJSP and a HTML file dummy.html. 2. Map a filter Test_Filter on
   * /generic/DummyServlet, dummyJSP and dummy.html with dispacther value set to
   * INCLUDE as well as ERROR, FORWARD and REQUEST using url-pattern. 3. Client
   * try to use the RequestDispatcher's include to access all three through
   * TestServlet. 4. Verify that filter is properly invoked.
   */
  @Test
  public void IncludeTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "includeJSPTest");
    invoke();

    TEST_PROPS.setProperty(APITEST, "includeServletTest");
    invoke();

    TEST_PROPS.setProperty(APITEST, "includeHTMLTest");
    invoke();
  }

  /*
   * @testName: ErrorTest
   *
   * @assertion_ids: Servlet:SPEC:57; Servlet:SPEC:59;
   *
   * @test_Strategy: 1. Create an Error Page /generic/ErrorPage handling
   * error-code 404. 2. Map a filter Test_Filter on /generic/ErrorPage with
   * dispacther value set to ERROR as well as FORWARD INCLUDE and REQUEST. 3.
   * Client try to access a non-existent Servlet 4. Verify that filter is
   * properly invoked.
   */
  @Test
  public void ErrorTest() throws Exception {
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(APITEST, "forward/IncludedServlet");
    TEST_PROPS.setProperty(STATUS_CODE, "404");
    invoke();
  }
}
