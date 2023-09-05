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
package servlet.tck.pluggability.api.jakarta_servlet.sessiontrackingmode;

import servlet.tck.api.jakarta_servlet.sessiontrackingmode.TestListener;
import servlet.tck.api.jakarta_servlet.sessiontrackingmode.TestServlet;
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

public class SessionTrackingModeTests extends AbstractTckTest {

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
            .addAsResource(SessionTrackingModeTests.class.getResource("servlet_plu_sessiontrackingmode_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_sessiontrackingmode_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestListener.class, TestServlet.class)
            .addAsLibraries(javaArchive1);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: setSessionTrackingModes
   *
   * @assertion_ids: Servlet:JAVADOC:695; Servlet:JAVADOC:684;
   * Servlet:JAVADOC:687;
   *
   * @test_Strategy: Create a ServletListener, in which call
   * ServletContext.setSessionTrackingModes() Create a Servlet, verify the above
   * works using getEffectiveSessionTrackingModes()
   */
  @Test
  public void setSessionTrackingModes() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setSessionTrackingModes");
    invoke();
  }
}
