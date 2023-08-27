/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.spec.listenerorder;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_listenerorder_web.war")
            .addClasses(ServletRequestListener1.class, ServletRequestListener2.class, ServletRequestListener3.class,
                    TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_spec_listenerorder_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /*
   * @testName: requestListenerOrderTest
   *
   * @assertion_ids:
   *
   * @test_Strategy: 1. Define servlet TestServlet in web.xml; 2. Define three
   * RequestListeners in web.xml in the order of ServletRequestListener1,
   * ServletRequestListener2, ServletRequestListener3; 3. Send request to
   * /TestServlet, verify TestServlet is invoked 3. Also verify in all
   * RequestListeners that they are invoked in the order declared in web.xml
   */
  @Test
  public void requestListenerOrderTest() throws Exception {
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    TEST_PROPS.setProperty(SEARCH_STRING, "TestServlet is invoked");
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet" + " HTTP/1.1");
    invoke();
  }
}
