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

package servlet.tck.api.jakarta_servlet.unavailableexception;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnAvailableExceptionTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_unavailableexception_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, UnavailableServlet.class)
            .setWebXML(UnAvailableExceptionTests.class.getResource("servlet_js_unavailableexception_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: getUnavailableSecondsTest
   * 
   * @assertion_ids: Servlet:SPEC:11; Servlet:JAVADOC:4; Servlet:JAVADOC:7;
   *
   * @test_Strategy: A test for UnavailableException.getUnavailableSeconds()
   * method.
   */
  @Test
  public void getUnavailableSecondsTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getUnavailableSecondsTest");
    invoke();
  }

  /*
   * @testName: isPermanentTest
   *
   * @assertion_ids: Servlet:SPEC:11; Servlet:JAVADOC:3; Servlet:JAVADOC:4;
   * Servlet:JAVADOC:5;
   *
   * @test_Strategy: A test for UnavailableException.isPermanent() method.
   */
  @Test
  public void isPermanentTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "isPermanentTest");
    invoke();
  }

  /*
   * @testName: unavailableTest
   *
   * @assertion_ids: Servlet:SPEC:11; Servlet:SPEC:11.1; Servlet:JAVADOC:3;
   * Servlet:JAVADOC:268;
   *
   * @test_Strategy: A test for Permanent Unavailable First access the Servlet,
   * and mark it unavailable Second try to access it again, 404 should be
   * returned
   */
  @Test
  public void unavailableTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, " ");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "");
    TEST_PROPS.setProperty(DONOTUSEServletName, "true");
    TEST_PROPS.setProperty(STATUS_CODE, "404");
    TEST_PROPS.setProperty(APITEST, "unavailableTest");
    invoke();
  }

  /*
   * @testName: unavailableException_Constructor1Test
   * 
   * @assertion_ids: Servlet:SPEC:11; Servlet:JAVADOC:3;
   *
   * @test_Strategy: A test for UnavailableException(String mesg). It construts
   * an UnavailabaleException object for the specified servlet. This constructor
   * tests for permanent unavailability
   */
  @Test
  public void unavailableException_Constructor1Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "unavailableException_Constructor1Test");
    invoke();
  }

  /*
   * @testName: unavailableException_Constructor2Test
   * 
   * @assertion_ids: Servlet:SPEC:11; Servlet:JAVADOC:4;
   *
   * @test_Strategy: A test for UnavailableException(String mesg, int sec). It
   * construts an UnavailabaleException object for the specified servlet. This
   * constructor tests for temporarily unavailability
   */
  @Test
  public void unavailableException_Constructor2Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "unavailableException_Constructor2Test");
    invoke();
  }
}
