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

package servlet.tck.api.jakarta_servlet.servletinputstream;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletInputStreamTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletinputstream_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ReadLineTestServlet.class)
            .setWebXML(ServletInputStreamTests.class.getResource("servlet_js_servletinputstream_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: readLineTest
   * 
   * @assertion_ids: Servlet:JAVADOC:103
   * 
   * @test_Strategy: Client posts a request to the servlet and the servlet reads
   * and verifies the correct behavior for readLine()
   *
   */
  @Test
  public void readLineTest() throws Exception {
    String testName = "readLineTest";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "POST " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(CONTENT, "test data");
    TEST_PROPS.setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }
}
