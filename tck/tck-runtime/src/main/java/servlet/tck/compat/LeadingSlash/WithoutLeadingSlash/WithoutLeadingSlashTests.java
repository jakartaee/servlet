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

package servlet.tck.compat.LeadingSlash.WithoutLeadingSlash;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class WithoutLeadingSlashTests extends AbstractTckTest {

  // TOFIX setGeneralURI("/servlet/compat/LeadingSlash/WithoutLeadingSlash");

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_compat_LeadingSlash_Without_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClass(WithoutLeadingSlashTestServlet.class)
            .setWebXML(WithoutLeadingSlashTests.class.getResource("servlet_compat_LeadingSlash_Without_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: WithoutLeadingSlashTest
   * 
   * @assertion: A 2.2 web application deployment descriptor who's url mapping
   * does not begin with a "/" can be deployed in a 2.3 environment, specified
   * in the Java Servlet Pages Specification v2.3, Sec 11
   * 
   * @test_Strategy: The DD url-pattern has a "/" at the beginning of the
   * string. The web app should deploy and be able to be called by a client
   *
   */
  @Test
  public void WithoutLeadingSlashTest() throws Exception {
    TEST_PROPS.get().setProperty(STANDARD, "WithoutLeadingSlashTest");
    invoke();
  }
}
