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

package servlet.tck.spec.errorpage;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ErrorPageTests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_errorpage_web.war")
            .addAsWebResource("spec/errorpage/HTMLErrorPage.html","HTMLErrorPage.html")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(SecondServletErrorPage.class, ServletErrorPage.class, TestException.class, TestServlet.class,
                    WrappedException.class)
            .setWebXML(ErrorPageTests.class.getResource("servlet_spec_errorpage_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: servletToDifferentErrorPagesTest
   *
   * @assertion_ids: Servlet:SPEC:108; Servlet:SPEC:106; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.2; Servlet:SPEC:104.3.3; Servlet:SPEC:104.3.4;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Servlet throws two exceptions which is caught by two error
   * pages; one is a servlet; thother HTML page
   */
  @Test
  void servletToDifferentErrorPagesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "servletErrorPageTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet Name: TestServlet|Request URI: /servlet_spec_errorpage_web/TestServlet|Status Code: 500|Exception Type: java.lang.IllegalStateException|Exception: java.lang.IllegalStateException: error page invoked|Message: error page invoked");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();

    TEST_PROPS.get().setProperty(APITEST, "htmlErrorPageTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "<html>|<head>|<title>HTML Error Page</title>|</head>|<body>|Error page mechanism invoked.|</body>|</html>");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();
  }

  /*
   * @testName: statusCodeErrorPageTest
   *
   * @assertion_ids: Servlet:SPEC:109; Servlet:SPEC:105; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Servlet uses SendError and the Error Page should be invoked
   * with the appropriate info regarding the error
   */
  @Test
  void statusCodeErrorPageTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "statusCodeErrorPageTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, "501");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet Name: TestServlet|Request URI: /servlet_spec_errorpage_web/TestServlet|Status Code: 501|Exception Type: null|Exception: null|Message: error page invoked");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();
  }

  /*
   * @testName: heirarchyErrorMatchTest
   *
   * @assertion_ids: Servlet:SPEC:108; Servlet:SPEC:106; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.2; Servlet:SPEC:104.3.3; Servlet:SPEC:104.3.4;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Servlet throws IllegalThreadStateException; No error pages
   * are defined to deal with IllegalThreadStateException; The closest Exception
   * defined to be dealt by error page is java.lang.IllegalStateException;
   * Verify this Error Page is invoked with the appropriate info regarding the
   * error
   */
  @Test
  void heirarchyErrorMatchTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "heirarchyErrorMatchTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet Name: TestServlet|Request URI: /servlet_spec_errorpage_web/TestServlet|Status Code: 500|Exception Type: java.lang.IllegalThreadStateException|Exception: java.lang.IllegalThreadStateException: error page invoked|Message: error page invoked");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();
  }

  /*
   * @testName: wrappedExceptionTest
   *
   * @assertion_ids: Servlet:SPEC:108; Servlet:SPEC:107; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.2; Servlet:SPEC:104.3.3; Servlet:SPEC:104.3.4;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Servlet throws ServletException which wraps a
   * TestException; No error pages are defined to deal with ServletException;
   * But there is an error page defined for TestException; Verify this Error
   * Page is invoked with the appropriate info regarding the error
   */
  @Test
  void wrappedExceptionTest() throws Exception {
    String testName = "WrappedException";

    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Second ErrorPage|"
        + "Servlet Name: WrappedException|"
        + "Request URI: /servlet_spec_errorpage_web/WrappedException|Status Code: 500|"
        + "Exception Type: servlet.tck.spec.errorpage.TestException|"
        + "Exception: servlet.tck.spec.errorpage.TestException: error page invoked|Message: error page invoked");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    invoke();
  }

}
