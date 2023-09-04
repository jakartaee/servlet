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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpsessionlistener;

import servlet.tck.api.jakarta_servlet_http.httpsessionlistener.HSListener;
import servlet.tck.api.jakarta_servlet_http.httpsessionlistener.TestServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionListenerTests extends AbstractUrlClient {

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
            .addAsResource(HttpSessionListenerTests.class.getResource("servlet_pluh_httpsessionlistener_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_httpsessionlistener_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HSListener.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: createdTest
   * 
   * @assertion_ids: Servlet:JAVADOC:454
   * 
   * @test_Strategy: Client calls a servlet that creates a session. The listener
   * should detect the creation and write a message to a static log. The Servlet
   * then reads the log and verifies the result. As a result of the test, the
   * jakarta.servlet.http.Event.getSession() method is tested.
   *
   */
  @Test
  public void createdTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "createdTest");
    invoke();
  }

  /*
   * @testName: destroyedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:455
   * 
   * @test_Strategy: Client calls a servlet that creates and the invalidates a
   * session. The listener should detect the changes and write a message to a
   * static log. The Servlet then reads the log file and verifies the result. As
   * a result of the test, the jakarta.servlet.http.Event.getSession() method is
   * tested.
   */
  @Test
  public void destroyedTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "destroyedTest");
    invoke();
  }
}
