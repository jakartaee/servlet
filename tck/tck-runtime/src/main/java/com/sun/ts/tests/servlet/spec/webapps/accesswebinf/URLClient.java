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

package com.sun.ts.tests.servlet.spec.webapps.accesswebinf;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_webapps_accesswebinf_web.war")
            .addAsWebInfResource("spec/webapps/accesswebinf/test.html", "test.html")
            .setWebXML(URLClient.class.getResource("servlet_spec_webapps_accesswebinf_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: accessWebInfTest
   * 
   * @assertion_ids: Servlet:SPEC:89
   * 
   * @test_Strategy:
   */
  @Test
  public void accessWebInfTest() throws Exception {
    String testName = "accessWebInfTest";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "/test.html HTTP/1.1");
    TEST_PROPS.setProperty(STATUS_CODE, NOT_FOUND);

    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "test html page");
    invoke();
  }
}
