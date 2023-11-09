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

package servlet.tck.pluggability.api.jakarta_servlet.asyncevent;

import servlet.tck.api.jakarta_servlet.asyncevent.AsyncTestServlet;
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

public class AsyncEventTests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("AsyncTestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(AsyncEventTests.class.getResource("servlet_plu_asyncevent_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_asyncevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AsyncTestServlet.class)
            .addAsLibraries(javaArchive);
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
  void constructorTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest1");
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
  void constructorTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest2");
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
  void constructorTest3() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest3");
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
  void constructorTest4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest4");
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
  void getSuppliedRequestTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSuppliedRequestTest1");
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
  void getSuppliedRequestTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSuppliedRequestTest2");
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
  void getSuppliedResponseTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSuppliedResponseTest1");
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
  void getSuppliedResponseTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSuppliedResponseTest2");
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
  void getThrowableTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getThrowableTest");
    invoke();
  }
}
