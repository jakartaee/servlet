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
package servlet.tck.api.jakarta_servlet.sessiontrackingmode4;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SessionTrackingMode4Tests extends AbstractTckTest {

  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_sessiontrackingmode4_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestListener.class, TestServlet.class)
            .setWebXML(SessionTrackingMode4Tests.class.getResource("servlet_js_sessiontrackingmode4_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: setSessionTrackingModes7
   *
   * @assertion_ids: Servlet:JAVADOC:695.3;
   *
   * @test_Strategy: Create a ServletListener, in which call
   * ServletContext.setSessionTrackingModes(Set) Make the Set to include
   * SessionTrackingMode.SSL; Add two different SessionTrackingMode to the Set;
   * Verify that IllegalArgumentException is thrown.
   */
  @Test
  void setSessionTrackingModes7() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSessionTrackingModes7");
    invoke();
  }
}
