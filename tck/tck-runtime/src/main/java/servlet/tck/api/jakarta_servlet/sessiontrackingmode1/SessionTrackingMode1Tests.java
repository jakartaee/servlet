/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.sessiontrackingmode1;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTrackingMode1Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_sessiontrackingmode1_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(SessionTrackingMode1Tests.class.getResource("servlet_js_sessiontrackingmode1_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */

  /*
   * @testName: setSessionTrackingModes1
   *
   * @assertion_ids: Servlet:JAVADOC:695.2;
   *
   * @test_Strategy: Negative test for method
   * ServletContext.setSessionTrackingModes. Call
   * ServletContext.setSessionTrackingModes inside a servlet; verify
   * IllegalStateException is thrown.
   */
  @Test
  public void setSessionTrackingModes1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSessionTrackingModes1");
    invoke();
  }

  /*
   * @testName: setSessionTrackingModes2
   *
   * @assertion_ids: Servlet:JAVADOC:695.2;
   *
   * @test_Strategy: Negative test for method
   * ServletContext.setSessionTrackingModes. Call
   * ServletContext.setSessionTrackingModes inside a servlet; verify
   * IllegalStateException is thrown.
   */
  @Test
  public void setSessionTrackingModes2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSessionTrackingModes2");
    invoke();
  }
  /*
   * @testName: setSessionTrackingModes3
   *
   * @assertion_ids: Servlet:JAVADOC:695.2;
   *
   * @test_Strategy: Negative test for method
   * ServletContext.setSessionTrackingModes. Call
   * ServletContext.setSessionTrackingModes inside a servlet; verify
   * IllegalStateException is thrown.
   */
  @Test
  public void setSessionTrackingModes3() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSessionTrackingModes3");
    invoke();
  }

  /*
   * @testName: setSessionTrackingModes4
   *
   * @assertion_ids: Servlet:JAVADOC:695.2;
   *
   * @test_Strategy: Negative test for method
   * ServletContext.setSessionTrackingModes. Call
   * ServletContext.setSessionTrackingModes inside a servlet; verify
   * IllegalStateException is thrown.
   */
  @Test
  public void setSessionTrackingModes4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSessionTrackingModes4");
    invoke();
  }
}
