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

/*
 * $URL$ $LastChangedDate$
 */

package com.sun.ts.tests.servlet.api.jakarta_servlet.servletoutputstream;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletoutputstream_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_servletoutputstream_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: print_StringTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:73;
   *
   * @test_Strategy: Test for print(java.lang.String s) method
   */
  @Test
  public void print_StringTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "some text");

    TEST_PROPS.setProperty(APITEST, "print_StringTest");
    invoke();
  }

  /*
   * @testName: print_booleanTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:75;
   *
   * @test_Strategy: Test for print(boolean b) method
   */
  @Test
  public void print_booleanTest() throws Exception {
    String s = Boolean.TRUE.toString();

    StringBuffer ss = new StringBuffer(s).append(s);

    TEST_PROPS.setProperty(SEARCH_STRING, ss.toString());

    TEST_PROPS.setProperty(APITEST, "print_booleanTest");
    invoke();
  }

  /*
   * @testName: print_charTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:77;
   *
   * @test_Strategy: Test for print(char c) method
   */
  @Test
  public void print_charTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "TEXT");

    TEST_PROPS.setProperty(APITEST, "print_charTest");
    invoke();
  }

  /*
   * @testName: print_doubleTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:85;
   *
   * @test_Strategy: Test for print(double d) method
   */
  @Test
  public void print_doubleTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "12345.612345.6");

    TEST_PROPS.setProperty(APITEST, "print_doubleTest");
    invoke();
  }

  /*
   * @testName: print_floatTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:83;
   *
   * @test_Strategy: Test for println(float f) method
   */
  @Test
  public void print_floatTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "1234.51234.5");

    TEST_PROPS.setProperty(APITEST, "print_floatTest");
    invoke();
  }

  /*
   * @testName: print_intTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:79;
   *
   * @test_Strategy: Test for print(integer i) method
   */
  @Test
  public void print_intTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "11");

    TEST_PROPS.setProperty(APITEST, "print_intTest");
    invoke();
  }

  /*
   * @testName: print_longTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:81;
   *
   * @test_Strategy: Test for print(long l) method
   */
  @Test
  public void print_longTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "12345678901234567890");

    TEST_PROPS.setProperty(APITEST, "print_longTest");
    invoke();
  }

  /*
   * @testName: printlnTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:87;
   * Servlet:JAVADOC:89;
   *
   * @test_Strategy: Test for println () method
   */
  @Test
  public void printlnTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "some test");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "some test text");

    TEST_PROPS.setProperty(APITEST, "printlnTest");
    invoke();
  }

  /*
   * @testName: println_StringTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:89;
   *
   * @test_Strategy: Test for println(java.lang.String s) method
   */
  @Test
  public void println_StringTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "some|text");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "sometext");

    TEST_PROPS.setProperty(APITEST, "println_StringTest");
    invoke();
  }

  /*
   * @testName: println_booleanTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:91;
   *
   * @test_Strategy: Test for println(boolean b) method
   */
  @Test
  public void println_booleanTest() throws Exception {
    String s = Boolean.TRUE.toString();

    StringBuffer ss = new StringBuffer(s);
    ss = ss.append(s);

    TEST_PROPS.setProperty(SEARCH_STRING, s);
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, ss.toString());

    TEST_PROPS.setProperty(APITEST, "println_booleanTest");
    invoke();
  }

  /*
   * @testName: println_charTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:93;
   *
   * @test_Strategy: Test for println(char c) method
   */
  @Test
  public void println_charTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "T|E|X|T");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "TEXT");

    TEST_PROPS.setProperty(APITEST, "println_charTest");
    invoke();
  }

  /*
   * @testName: println_doubleTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:101;
   *
   * @test_Strategy: Test for println(double d) method
   */
  @Test
  public void println_doubleTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "12345.6");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "12345.612345.6");

    TEST_PROPS.setProperty(APITEST, "println_doubleTest");
    invoke();
  }

  /*
   * @testName: println_floatTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:99;
   *
   * @test_Strategy: Test for print(float f) method
   */
  @Test
  public void println_floatTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "1234.5");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "1234.51234.5");

    TEST_PROPS.setProperty(APITEST, "println_floatTest");
    invoke();
  }

  /*
   * @testName: println_intTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:95;
   *
   * @test_Strategy: Test for println(integer i) method
   */
  @Test
  public void println_intTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "1");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "11");

    TEST_PROPS.setProperty(APITEST, "println_intTest");
    invoke();
  }

  /*
   * @testName: println_longTest
   *
   * @assertion_ids: Servlet:JAVADOC:140; Servlet:JAVADOC:97;
   *
   * @test_Strategy: Test for println(long l) method
   */
  @Test
  public void println_longTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "1234567890");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "12345678901234567890");

    TEST_PROPS.setProperty(APITEST, "println_longTest");
    invoke();
  }
}
