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

package com.sun.ts.tests.servlet.spec.errorpage1;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.common.util.Data;
import com.sun.ts.tests.servlet.spec.errorpage.ServletErrorPage;
import com.sun.ts.tests.servlet.spec.errorpage.SecondServletErrorPage;
import com.sun.ts.tests.servlet.spec.errorpage.TestException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_errorpage1_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, TestServletException.class, TestException.class, ServletErrorPage.class,
                    SecondServletErrorPage.class)
            .setWebXML(URLClient.class.getResource("servlet_spec_errorpage1_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: nonServletExceptionTest
   *
   * @assertion_ids: Servlet:SPEC:108; Servlet:SPEC:106; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.2; Servlet:SPEC:104.3.3; Servlet:SPEC:104.3.4;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Servlet throws IllegalStateException; Verify the Error Page
   * defined to deal with IllegalStateException is invoked with the appropriate
   * info regarding the error
   */
  @Test
  public void nonServletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "nonServletExceptionTest");
    TEST_PROPS.setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.setProperty(SEARCH_STRING,
        "First ErrorPage|" + "Servlet Name: TestServlet|"
            + "Request URI: /servlet_spec_errorpage1_web/TestServlet|"
            + "Status Code: 500|"
            + "Exception: java.lang.IllegalStateException: error page invoked|"
            + "Message: error page invoked");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();
  }

  /*
   * @testName: servletExceptionTest
   *
   * @assertion_ids: Servlet:SPEC:108; Servlet:SPEC:107; Servlet:SPEC:104.3.1;
   * Servlet:SPEC:104.3.2; Servlet:SPEC:104.3.3; Servlet:SPEC:104.3.4;
   * Servlet:SPEC:104.3.5; Servlet:SPEC:104.3.6;
   *
   * @test_Strategy: Invoke TestServlet The Servlet throws TestServletException
   * which wraps a TestException; --- TestServletException extends
   * ServletException; There is an error page defined for TestServletException;
   * --- No error pages are defined to deal with TestException; Verify this
   * Error Page is invoked with the appropriate info regarding the error
   */
  @Test
  public void servletExceptionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "ServletExceptionTest");
    TEST_PROPS.setProperty(STATUS_CODE, INTERNAL_SERVER_ERROR);
    TEST_PROPS.setProperty(SEARCH_STRING, "Second ErrorPage|"
        + "Servlet Name: TestServlet|"
        + "Request URI: /servlet_spec_errorpage1_web/TestServlet|"
        + "Status Code: 500|"
        + "Exception: com.sun.ts.tests.servlet.spec.errorpage1.TestServletException: |"
        + "error page invoked|" + "Message: error page invoked");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, Data.FAILED);
    invoke();
  }

}
