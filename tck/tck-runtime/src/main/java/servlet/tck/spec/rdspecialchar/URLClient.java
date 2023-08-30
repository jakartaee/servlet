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

package servlet.tck.spec.rdspecialchar;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_rdspecialchar_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(IncludedServlet.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_spec_rdspecialchar_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /*
   * @testName: querySemicolonInclude
   *
   * @assertion_ids: Servlet:SPEC:76.1; Servlet:SPEC:76.2; Servlet:SPEC:76.3;
   * Servlet:SPEC:76.4; Servlet:SPEC:76.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and IncludedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(path), with special character ";" in
   * path as part of query string, and access IncludedServlet using
   * RequestDispatcher.include. 3. Verify that IncludedServlet is invoked.
   */
  @Test
  public void querySemicolonInclude() throws Exception {
    TEST_PROPS.setProperty(APITEST, "querySemicolonInclude");
    invoke();
  }

  /*
   * @testName: querySemicolonForward
   *
   * @assertion_ids: Servlet:SPEC:76.1; Servlet:SPEC:76.2; Servlet:SPEC:76.3;
   * Servlet:SPEC:76.4; Servlet:SPEC:76.5;
   *
   * @test_Strategy: 1. Create servlets TestServlet and IncludedServlet; 2. In
   * TestServlet, get RequestDispatcher by using
   * ServletContext.getRequestDispatcher(path), with special character ";" in
   * path as part of query string, and access IncludedServlet using
   * RequestDispatcher.forward. 3. Verify that IncludedServlet is invoked.
   */
  @Test
  public void querySemicolonForward() throws Exception {
    TEST_PROPS.setProperty(APITEST, "querySemicolonForward");
    invoke();
  }

}
