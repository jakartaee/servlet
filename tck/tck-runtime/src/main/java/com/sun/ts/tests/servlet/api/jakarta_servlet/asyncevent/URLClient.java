/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.servlet.api.jakarta_servlet.asyncevent;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;
import jakarta.servlet.GenericServlet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("AsyncTestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_asyncevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AsyncTestServlet.class, GenericTCKServlet.class, GenericServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_asyncevent_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: constructorTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:842;
   * 
   * @test_Strategy: test the constructor AsyncEvent( AsyncContext )
   */
  @Test
  public void constructorTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest1");
    invoke();
  }

  /*
   * @testName: constructorTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:843;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse)
   */
  @Test
  public void constructorTest2() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest2");
    invoke();
  }

  /*
   * @testName: constructorTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:844;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext, Throwable)
   */
  @Test
  public void constructorTest3() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest3");
    invoke();
  }

  /*
   * @testName: constructorTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:845;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse, Throwable)
   */
  @Test
  public void constructorTest4() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest4");
    invoke();
  }

  /*
   * @testName: getSuppliedRequestTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:847;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse) verify AsyncEvent.getSuplliedRequest()
   * works
   */
  @Test
  public void getSuppliedRequestTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getSuppliedRequestTest1");
    invoke();
  }

  /*
   * @testName: getSuppliedRequestTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:847;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse, Throwable) verify
   * AsyncEvent.getSuplliedRequest() works
   */
  @Test
  public void getSuppliedRequestTest2() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getSuppliedRequestTest2");
    invoke();
  }

  /*
   * @testName: getSuppliedResponseTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:848;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse) verify AsyncEvent.getSuplliedResponse()
   * works
   */
  @Test
  public void getSuppliedResponseTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getSuppliedResponseTest1");
    invoke();
  }

  /*
   * @testName: getSuppliedResponseTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:848;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse, Throwable) verify
   * AsyncEvent.getSuplliedResponse() works
   */
  @Test
  public void getSuppliedResponseTest2() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getSuppliedResponseTest2");
    invoke();
  }

  /*
   * @testName: getThrowableTest
   * 
   * @assertion_ids: Servlet:JAVADOC:849;
   * 
   * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
   * ServletRequest, ServletResponse, Throwable) verify
   * AsyncEvent.getThrowable() works
   */
  @Test
  public void getThrowableTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getThrowableTest");
    invoke();
  }
}
