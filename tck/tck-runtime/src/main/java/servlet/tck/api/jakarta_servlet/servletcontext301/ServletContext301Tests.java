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
package servlet.tck.api.jakarta_servlet.servletcontext301;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletContext301Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext301_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AddGenericEventListenerClass.class, TestListener.class, TestServlet.class)
            .setWebXML(ServletContext301Tests.class.getResource("servlet_js_servletcontext301_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */

  /*
   * @testName: addListenerTest
   *
   * @assertion_ids: Servlet:JAVADOC:672.9; Servlet:JAVADOC:673.9;
   *
   * @test_Strategy: In a ServletContextListener, call:
   * ServletContext.addListener(GenericListener.class) GenericListener does not
   * implement any of the Listener class on the list: -
   * ServletContextAttributeListener - HttpSessionListener -
   * HttpSessionAttributeListener - ServletRequestListener -
   * ServletRequestAttributeListener Verify the expected
   * IllegalArgumentException is thrown.
   */
  @Test
  public void addListenerTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addListenerTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "LISTENER_TEST=TRUE");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "AddGenericEventListenerClass");
    invoke();
  }
}
