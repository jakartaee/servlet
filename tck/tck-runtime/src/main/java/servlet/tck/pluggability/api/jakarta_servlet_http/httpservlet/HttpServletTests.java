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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpservlet;

import servlet.tck.api.jakarta_servlet_http.httpservlet.DestroyTestServlet;
import servlet.tck.api.jakarta_servlet_http.httpservlet.InitTestServlet;
import servlet.tck.api.jakarta_servlet_http.httpservlet.Init_ServletConfigTestServlet;
import servlet.tck.api.jakarta_servlet_http.httpservlet.ServiceTestServlet;
import servlet.tck.api.jakarta_servlet_http.httpservlet.TestServlet;
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

public class HttpServletTests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(HttpServletTests.class.getResource("servlet_pluh_httpservlet_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_httpservlet_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DestroyTestServlet.class, Init_ServletConfigTestServlet.class,
                    InitTestServlet.class, ServiceTestServlet.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: destroyTest
   * 
   * @assertion_ids: Servlet:JAVADOC:416
   * 
   * @test_Strategy: Create a GenericServlet and take out of service using
   * destroy method
   *
   */
  @Test
  void destroyTest() throws Exception {
    String testName = "destroyTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
  }

  /*
   * @testName: getServletConfigTest
   * 
   * @assertion_ids: Servlet:JAVADOC:421
   * 
   * @test_Strategy: Create a GenericServlet and check for its ServletConfig
   * object existence
   *
   */
  @Test
  void getServletConfigTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletConfigTest");
    invoke();
  }

  /*
   * @testName: getServletContextTest
   * 
   * @assertion_ids: Servlet:JAVADOC:422
   * 
   * @test_Strategy: Create a GenericServlet and check for its ServletContext
   * object existence
   *
   */
  @Test
  void getServletContextTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletContextTest");
    invoke();
  }

  /*
   * @testName: getServletInfoTest
   * 
   * @assertion_ids: Servlet:JAVADOC:423
   * 
   * @test_Strategy: Create a GenericServlet and check for its ServletInfo
   * object values
   *
   */
  @Test
  void getServletInfoTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletInfoTest");
    invoke();
  }

  /*
   * @testName: getInitParameterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:417
   * 
   * @test_Strategy: Servlet tries to access a parameter that exists
   */
  @Test
  void getInitParameterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getInitParameterTest");
    invoke();
  }

  /*
   * @testName: getInitParameterTestNull
   * 
   * @assertion_ids: Servlet:JAVADOC:418
   * 
   * @test_Strategy: Servlet tries to access a parameter that doesnot exist
   */
  @Test
  void getInitParameterTestNull() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getInitParameterTestNull");
    invoke();
  }

  /*
   * @testName: getInitParameterNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:419
   * 
   * @test_Strategy: Servlet tries to get all parameter names
   */
  @Test
  void getInitParameterNamesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getInitParameterNamesTest");
    invoke();
  }

  /*
   * @testName: getServletNameTest
   * 
   * @assertion_ids: Servlet:JAVADOC:433
   * 
   * @test_Strategy: Servlet gets name of servlet
   */
  @Test
  void getServletNameTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletNameTest");
    invoke();
  }

  /*
   * @testName: serviceTest
   * 
   * @assertion_ids: Servlet:JAVADOC:430
   * 
   * @test_Strategy: Servlet which has a service method that is called
   */
  @Test
  void serviceTest() throws Exception {
    String testName = "serviceTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: initTest
   * 
   * @assertion_ids: Servlet:JAVADOC:426
   * 
   * @test_Strategy: Servlet has init method that puts a value into the context.
   * Servlet when called reads value from context
   */
  @Test
  void initTest() throws Exception {
    String testName = "initTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: init_ServletConfigTest
   * 
   * @assertion_ids: Servlet:JAVADOC:424
   * 
   * @test_Strategy: Servlet has init method that puts a value into the context.
   * Servlet when called reads value from context
   */
  @Test
  void init_ServletConfigTest() throws Exception {
    String testName = "init_ServletConfigTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    invoke();
  }
}
