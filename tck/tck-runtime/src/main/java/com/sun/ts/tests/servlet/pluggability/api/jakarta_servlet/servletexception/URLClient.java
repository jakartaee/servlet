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
package com.sun.ts.tests.servlet.pluggability.api.jakarta_servlet.servletexception;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.pluggability.common.RequestListener1;
import com.sun.ts.tests.servlet.pluggability.common.TestServlet1;
import com.sun.ts.tests.servlet.api.jakarta_servlet.servletexception.TestServlet;
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
            .addAsResource(URLClient.class.getResource("servlet_plu_servletexception_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_servletexception_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: getRootCauseTest
   *
   * @assertion_ids: Servlet:SPEC:83; Servlet:JAVADOC:108; Servlet:JAVADOC:109;
   * Servlet:JAVADOC:8;
   *
   * @test_Strategy: A Test for getRootCause method
   */
  @Test
  public void getRootCauseTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRootCause");
    invoke();
  }

  /*
   * @testName: servletExceptionConstructor1Test
   *
   * @assertion_ids: Servlet:SPEC:83; Servlet:JAVADOC:105;
   *
   * @test_Strategy: A Test for ServletException() constructor method
   */
  @Test
  public void servletExceptionConstructor1Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "servletExceptionConstructor1");
    invoke();
  }

  /*
   * @testName: servletExceptionConstructor2Test
   *
   * @assertion_ids: Servlet:SPEC:83; Servlet:JAVADOC:106;
   *
   * @test_Strategy: A Test for ServletException(String) constructor method
   */
  @Test
  public void servletExceptionConstructor2Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "servletExceptionConstructor2");
    invoke();
  }

  /*
   * @testName: servletExceptionConstructor3Test
   *
   * @assertion_ids: Servlet:SPEC:83; Servlet:JAVADOC:108; Servlet:JAVADOC:109;
   * Servlet:JAVADOC:8;
   *
   * @test_Strategy: A Test for ServletException(Throwable) constructor method
   */
  @Test
  public void servletExceptionConstructor3Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "servletExceptionConstructor3");
    invoke();

  }

  /*
   * @testName: servletExceptionConstructor4Test
   *
   * @assertion_ids: Servlet:SPEC:83; Servlet:JAVADOC:107; Servlet:JAVADOC:109;
   * Servlet:JAVADOC:8;
   *
   * @test_Strategy: A Test for ServletException(String,Throwable) constructor
   * method
   */
  @Test
  public void servletExceptionConstructor4Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "servletExceptionConstructor4");
    invoke();
  }
}
