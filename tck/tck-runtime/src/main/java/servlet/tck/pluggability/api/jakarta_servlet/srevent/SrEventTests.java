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
package servlet.tck.pluggability.api.jakarta_servlet.srevent;

import servlet.tck.api.jakarta_servlet.srevent.TestServlet;
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

public class SrEventTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {

    JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(SrEventTests.class.getResource("servlet_plu_srevent_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_srevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .addAsLibraries(javaArchive1);
  }



  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: constructorTest
   * 
   * @assertion_ids: Servlet:JAVADOC:63
   * 
   * @test_Strategy: Servlet tries to get an instance of ServletRequestEvent.
   */
  @Test
  public void constructorTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest");
    invoke();
  }

  /*
   * @testName: getServletContextTest
   * 
   * @assertion_ids: Servlet:JAVADOC:65
   * 
   * @test_Strategy: Servlet calls constructor and tries to get the context that
   * was used in the constructor.
   */
  @Test
  public void getServletContextTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletContextTest");
    invoke();
  }

  /*
   * @testName: getServletRequestTest
   * 
   * @assertion_ids: Servlet:JAVADOC:64
   * 
   * @test_Strategy: Servlet calls constructor and tries to get the request that
   * was used in the constructor.
   */
  @Test
  public void getServletRequestTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getServletRequestTest");
    invoke();
  }
}
