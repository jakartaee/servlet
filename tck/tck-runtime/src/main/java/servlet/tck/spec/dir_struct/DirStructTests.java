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

package servlet.tck.spec.dir_struct;

import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DirStructTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_dirstruct_web.war")
            .addClasses(ClassFileTestServlet.class, JarFileTestServlet.class)
            .setWebXML(DirStructTests.class.getResource("servlet_spec_dirstruct_web.xml"));
  }

  /* Test setup */

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: loadClassesTest
   * 
   * @assertion_ids: Servlet:SPEC:91
   * 
   * @test_Strategy: The servlet which is in the WEB-INF/classes directory is
   * called by the client and should execute. Then the servlet which is in the
   * WEB-INF/lib directory is called by the client and should execute.
   *
   */
  @Test
  public void loadClassesTest() throws Exception {
    String testName = "classFileTest";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "in ClassFileTestServlet");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "in jarFileTestServlet");
    invoke();

    testName = "jarFileTest";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "in JarFileTestServlet");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "in ClassFileTestServlet");
    invoke();
  }
}
