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

/*
 * $URL$ $LastChangedDate$
 */

package com.sun.ts.tests.servlet.api.jakarta_servlet.srattributelistener;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_js_srattributelistener_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(SRAttributeListener.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_js_srattributelistener_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: addedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:169
   * 
   * @test_Strategy: Servlet adds an attribute. The listener should detect the
   * add and write a message out to a static log. Servlet then reads the log and
   * verifys the result.
   *
   */
  @Test
  public void addedTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addedTest");
    invoke();
  }

  /*
   * @testName: removedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:170
   * 
   * @test_Strategy: Servlet adds/removes an attribute. The listener should
   * detect the add and write a message out to a static log. Servlet then reads
   * the log and verifys the result.
   */
  @Test
  public void removedTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "removedTest");
    invoke();
  }

  /*
   * @testName: replacedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:171
   * 
   * @test_Strategy: Servlet adds/replaces an attribute. The listener should
   * detect the add and write a message out to a static log. Servlet then reads
   * the log and verifys the result.
   */
  @Test
  public void replacedTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "replacedTest");
    invoke();
  }
}
