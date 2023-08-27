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
 * $Id$
 */
package com.sun.ts.tests.servlet.spec.pluggability.ordering.test1;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.spec.pluggability.common.RequestListener;
import com.sun.ts.tests.servlet.spec.pluggability.common.TestServlet4;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  public static JavaArchive getFragment1() {
    return ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(RequestListener.class, TestServlet4.class)
            .addAsResource(URLClient.class.getResource("web-fragment_1.xml"),
                    "META-INF/web-fragment.xml");
  }

  public static JavaArchive getFragment2() {
    return ShrinkWrap.create(JavaArchive.class, "fragment-2.jar")
            .addClasses(TestServlet4.class)
            .addAsResource(URLClient.class.getResource("web-fragment_2.xml"),
                    "META-INF/web-fragment.xml");
  }

  public static JavaArchive getFragment3() {
    return ShrinkWrap.create(JavaArchive.class, "fragment-3.jar")
            .addClasses(TestServlet4.class)
            .addAsResource(URLClient.class.getResource("web-fragment_3.xml"),
                    "META-INF/web-fragment.xml");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_ordering1_web.war")
            .addAsLibraries(getFragment1(), getFragment2(), getFragment3())
            .setWebXML(URLClient.class.getResource("servlet_spec_ordering1_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /*
   * @testName: initParamTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:241; Servlet:SPEC:242;
   * Servlet:SPEC:244; Servlet:SPEC:245; Servlet:SPEC:258.1;
   * Servlet:SPEC:258.5.1; Servlet:SPEC:258.6.1; Servlet:SPEC:258.7.1;
   *
   * @test_Strategy: 1. Define servlet TestServlet4 in web.xml as well as three
   * web-fragment.xml; 2. Send request to /TestServlet4, verify TestServlet4 is
   * invoked 3. Also verify that <init-param> defined in web.xml and all
   * web-fragment.xml are considered, and the one defined in web.xml take
   * precedence.
   */
  @Test
  public void initParamTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "TestServlet4|"
        + "msg1=first|msg2=second|msg3=third|msg4=fourth|" + "RequestListener");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet4" + " HTTP/1.1");
    invoke();
  }
}
