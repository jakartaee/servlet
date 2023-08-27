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

package com.sun.ts.tests.servlet.spec.defaultmapping;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_defaultmapping_web.war")
            .addClasses(TestServlet6.class)
            .setWebXML(URLClient.class.getResource("servlet_spec_defaultmapping_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: defaultservletTest1
   *
   * @assertion_ids: Servlet:SPEC:134.4;
   *
   * @test_Strategy: 1. Create servlets TestServlet1 with URL /foo/bar/*,
   * TestServlet2 with URL /foo/baR/*, TestServlet3 with URL /TestServlet3,
   * TestServlet4 with URL *.bop, TestServlet5 with URL /foo/bar/TestServlet5.
   * TestServlet6 with URL / 2. Send request with path /TestServlet3/xyz. 3.
   * Verify that TestServlet6 is invoked based on Servlet Spec(11.1)
   */
  @Test
  public void defaultservletTest1() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "TestServlet6");
    TEST_PROPS.setProperty(APITEST, "TestServlet3/xyz");
    invoke();
  }

  /*
   * @testName: defaultservletTest
   *
   * @assertion_ids: Servlet:SPEC:134.4;
   *
   * @test_Strategy: 1. Create servlets TestServlet1 with URL /foo/bar/*,
   * TestServlet2 with URL /foo/baR/*, TestServlet3 with URL /TestServlet3,
   * TestServlet4 with URL *.bop, TestServlet5 with URL /foo/bar/TestServlet5
   * TestServlet6 with URL / 2. Send request with path /test/foo/bar/xxx 3.
   * Verify that default Servlet TestServlet6 should be invoked. Since no match
   * is found.
   */
  @Test
  public void defaultservletTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "TestServlet6");
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test/foo/bar/xxx" + " HTTP/1.1");
    invoke();
  }
}
