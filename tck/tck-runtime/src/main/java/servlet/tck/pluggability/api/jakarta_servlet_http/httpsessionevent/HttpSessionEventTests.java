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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpsessionevent;

import servlet.tck.api.jakarta_servlet_http.httpsessionevent.HSEvent;
import servlet.tck.api.jakarta_servlet_http.httpsessionevent.TestServlet;
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

public class HttpSessionEventTests extends AbstractTckTest {

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
            .addAsResource(HttpSessionEventTests.class.getResource("servlet_pluh_httpsessionevent_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_httpsessionevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HSEvent.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */
  /*
   * @testName: getSessionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:304
   * 
   * @test_Strategy: Client calls a servlet that creates a session. The listener
   * writes the sessionid of the event to a static log. The Servlet then reads
   * the log and verifies the result
   */
  @Test
  public void getSessionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getSessionTest");
    invoke();
  }

  /*
   * @testName: constructorTest
   * 
   * @assertion_ids: Servlet:JAVADOC:303
   * 
   * @test_Strategy: servlet calls the constructor
   */
  @Test
  public void constructorTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "constructorTest");
    invoke();
  }
}
