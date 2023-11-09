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

package servlet.tck.api.jakarta_servlet_http.httpsessionattributelistener;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionAttributeListenerTests extends AbstractTckTest {
  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpsessionattributelistener_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HSAttributeListener.class, TestServlet.class)
            .setWebXML(HttpSessionAttributeListenerTests.class.getResource("servlet_jsh_httpsessionattributelistener_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: attributeAddedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:460;Servlet:JAVADOC:485
   * 
   * @test_Strategy: Client calls a servlet that adds an attribute. The listener
   * should detect the add and writes a message out to a static log. Servlet
   * then reads the log and verifies the result
   *
   */
  @Test
  void attributeAddedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "attributeAddedTest");
    invoke();
  }

  /*
   * @testName: attributeRemovedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:461;Servlet:JAVADOC:491
   * 
   * @test_Strategy: Client calls a servlet that adds/Removes an attribute. The
   * listener should detect the changes and writes a message out to a static
   * log. Servlet then reads the log and verifies the result
   */
  @Test
  void attributeRemovedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "attributeRemovedTest");
    invoke();
  }

  /*
   * @testName: attributeReplacedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:462
   * 
   * @test_Strategy: Client calls a servlet that adds/Replaces an attribute. The
   * listener should detect the changes and writes a message out to a static
   * log. Servlet then reads the log and verifies the result
   */
  @Test
  void attributeReplacedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "attributeReplacedTest");
    invoke();
  }
}
