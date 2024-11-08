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

package servlet.tck.spec.httpservletresponse;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpServletResponseTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("HttpTestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_httpservletresponse_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HttpTestServlet.class, RedirectedTestServlet.class)
            .setWebXML(HttpServletResponseTests.class.getResource("servlet_spec_httpservletresponse_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: intHeaderTest
   *
   * @assertion_ids: Servlet:SPEC:33;
   *
   * @test_Strategy: 1. Call setIntHeader to set header; 2. Commit it and set
   * the header again; 3. Verify that only the first header value is set, the
   * second set is ignored
   */
  @Test
  public void intHeaderTest() throws Exception {
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "header1: 12345");
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS, "header2: 56789");
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + "intHeaderTest" + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: flushBufferTest
   *
   * @assertion_ids: Servlet:SPEC:32; Servlet:SPEC:33; Servlet:SPEC:42.2;
   *
   * @test_Strategy: 1. First call setContentLength to set the length of
   * content; 2. Then write to the buffer to fill up the buffer. 2. Call
   * setIntHeader to set header 3. Verify that the header value is not set,
   */
  @Ignore @Test
  public void flushBufferTest() throws Exception {
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS, "header1: 12345");
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + "flushBufferTest" + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: sendErrorCommitTest
   *
   * @assertion_ids: Servlet:SPEC:36; Servlet:SPEC:42.3; Servlet:SPEC:41;
   *
   * @test_Strategy: 1. Call sendError; 2. then setIntHeader to set header 3.
   * then write to buffer; 4. Verify that the header value is not set, content
   * wrote to buffer is ignored
   */
  @Test
  public void sendErrorCommitTest() throws Exception {
    String testname = "sendErrorCommitTest";
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS, "header1: 12345");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testname + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: sendRedirectCommitTest
   *
   * @assertion_ids: Servlet:SPEC:37; Servlet:SPEC:42.4; Servlet:SPEC:40;
   *
   * @test_Strategy: 1. Call sendRedirect; 2. then setIntHeader to set header 3.
   * then write to buffer; 4. Verify that the header value is not set, content
   * wrote to buffer is ignored
   */

  @Test
  public void sendRedirectCommitTest() throws Exception {
    String testname = "sendRedirectCommitTest";
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS, "header1: 12345");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    TEST_PROPS.get().setProperty(STATUS_CODE, MOVED_TEMPORARY);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testname + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: sendRedirectClearBufferTest
   *
   * @assertion_ids: Servlet:SPEC:38;
   *
   * @test_Strategy: 1. First write to buffer 2. Call sendRedirect; 3. Verify
   * that content wrote to buffer is cleared
   */
  @Test
  public void sendRedirectClearBufferTest() throws Exception {
    String testname = "sendRedirectClearBufferTest";
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    TEST_PROPS.get().setProperty(STATUS_CODE, MOVED_TEMPORARY);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testname + " HTTP/1.1");
    invoke();
  }

}
