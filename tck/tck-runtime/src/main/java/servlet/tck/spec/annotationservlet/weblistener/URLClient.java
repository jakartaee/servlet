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

/*
 * $Id:$
 */
package servlet.tck.spec.annotationservlet.weblistener;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_annotationservlet_weblistener_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ContextListener.class, HSAttributeListener.class, HSListener.class, HttpTestServlet.class,
                    SCAttributeListener.class, SRAttributeListener.class, SRListener.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_annotationservlet_weblistener_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: ContextListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.3;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * ServletContextListener using @WebListener; Send request to TestServlet;
   * Veriy ServletContextListener is invoked properly.
   */
  @Test
  public void ContextListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "ContextListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "ContextInitialized");
    invoke();
  }

  /*
   * @testName: ContextAttributeListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.4;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * ServletContextAttributeListener using @WebListener; Send request to
   * TestServlet; Veriy ServletContextAttributeListener is invoked properly.
   */
  @Test
  public void ContextAttributeListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "ContextAttributeListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "SCAAdded:ContextListener|" + "ContextInitialized|" + "SCAAdded:SRList|"
            + "in requestInitialized method of listener|" + "SCAAdded:Test,tmp|"
            + "SCARemoved:Test,tmp");
    invoke();
  }

  /*
   * @testName: RequsetListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.5;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * ServletRequestListener using @WebListener; Send request to TestServlet;
   * Veriy ServletRequestListener is invoked properly.
   */
  @Test
  public void RequsetListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "RequsetListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "in requestInitialized method of listener");
    invoke();
  }

  /*
   * @testName: RepeatRequsetListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.5;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * ServletRequestListener using @WebListener; Send request to TestServlet;
   * Send a second request to TestServlet; Veriy ServletRequestListener is
   * invoked properly.
   */
  @Test
  public void RepeatRequsetListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "RepeatRequsetListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "in requestInitialized method of listener");
    invoke();

    TEST_PROPS.setProperty(APITEST, "RequsetListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "in requestInitialized method of listener|"
            + "in requestDestroyed method of listener|"
            + "in requestInitialized method of listener");
    invoke();
  }

  /*
   * @testName: RequsetAttributeListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.6;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * ServletRequestListener using @WebListener; Send request to TestServlet;
   * Veriy ServletRequestListener is invoked properly.
   */
  @Test
  public void RequsetAttributeListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "RequsetAttributeListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "SRAAdded:Test,RequestAttribute|" + "SRARemoved:Test,RequestAttribute");
    invoke();
  }

  /*
   * @testName: HttpSessionListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:195.7;
   *
   * @test_Strategy: Create a servlet HttpTestServlet; Create a
   * HttpSessionListener using @WebListener; Send request to HttpTestServlet;
   * Create a HttpSession and then invlalidate it; Veriy HttpSessionListener is
   * invoked properly.
   */
  @Test
  public void HttpSessionListenerTest() throws Exception {
    setServletName("HttpTestServlet");
    TEST_PROPS.setProperty(APITEST, "HttpSessionListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "HSListener:sessionCreated|" + "HSListener:sessionDestroyed");
    invoke();
  }

  /*
   * @testName: HttpSessionAttributeListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:865; Servlet:SPEC:289; Servlet:SPEC:195.8;
   *
   * @test_Strategy: Create a servlet TestServlet; Create a
   * HttpSessionAttributeListener using @WebListener; Send request to
   * TestServlet, that will set some attributes in; its first call to
   * HttpSessionAttributeListenerPreLude then once; in this servlet method, the
   * attributes are added, replaced, and; removed. This should cause the
   * HttpSessionAttributeListener; event to get triggered for all 3 methods.
   * Verification of; the 3 triggered event methods is then checked in the 2nd;
   * invocation to HttpSessionAttributeListenerTest below.
   */
  @Test
  public void HttpSessionAttributeListenerTest() throws Exception {

    // first invocation is to do some session attribute manipulations
    // which should trigger HttpSessionAttributeListener notifications
    TEST_PROPS.setProperty(APITEST, "HttpSessionAttributeListenerPreLude");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "in HttpSessionAttributeListenerPreLude");
    invoke();

    // 2nd invocation checks that the HttpSessionAttributeListener notifications
    // occurred by verifying certain strings were written to HSAList attribute
    TEST_PROPS.setProperty(APITEST, "HttpSessionAttributeListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "HSAttributeListener.attributeAdded|"
            + "HSAttributeListener.attributeReplaced|"
            + "HSAttributeListener.attributeRemoved");
    invoke();

  }

}
