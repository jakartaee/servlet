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
 * $Id:$
 */
package com.sun.ts.tests.servlet.spec.annotationservlet.webfilter;

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
    return ShrinkWrap.create(WebArchive.class, "servlet_annotationservlet_webfilter_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(Servlet1.class, TestFilter1.class, TestFilter2.class, TestServlet.class)
            .setWebXML(URLClient.class.getResource("servlet_annotationservlet_webfilter_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:JAVADOC:804; Servlet:JAVADOC:811;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Create a filter TestFilter1;
   * Define TestFilter1 using annotation
   * 
   * @WebFilter(...,urlPatterns="servlet1",...,); Send a request to Servlet1;
   * Veriy TestFilter1 is invoked properly.
   */
  @Test
  public void test1() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "FILTER1_INVOKED|Servlet1_INVOKED");
    invoke();
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:JAVADOC:802; Servlet:JAVADOC:804;
   * Servlet:JAVADOC:806; Servlet:JAVADOC:807; Servlet:JAVADOC:809;
   * Servlet:JAVADOC:811; Servlet:JAVADOC:812; Servlet:JAVADOC:814;
   * Servlet:JAVADOC:815; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Create a filter TestFilter1;
   * Define TestFilter1 using annotation
   * 
   * @WebFilter(...,urlPatterns="servlet1",...,dispatcherTypes=REQUEST); Create
   * a filter TestFilter2; Define TestFilter2 using annotation
   * 
   * @WebFilter(...,urlPatterns="servlet1",..., dispatcherTypes=FORWARD); Create
   * another Servlet which forward to Servlet1 Send a request to the second
   * Servlet; Veriy TestFilter2 is invoked. Veriy TestFilter1 is not invoked.
   */
  @Test
  public void test2() throws Exception {
    TEST_PROPS.setProperty(APITEST, "forward1");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "FILTER1_INVOKED");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "FILTER2_INVOKED|" + "FilterName=filter2|"
            + "PName=name1 PVALUE=value1|" + "PName=name2 PVALUE=value2|"
            + "AsyncSupport=false|" + "URL=servlet1|" + "From=TestServlet|"
            + "Servlet1_INVOKED");
    invoke();
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:JAVADOC:804; Servlet:JAVADOC:811;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Create a filter TestFilter1;
   * Define TestFilter1 using annotation
   * 
   * @WebFilter(...,urlPatterns="servlet1",..., dispatcherTypes=REQUEST); Create
   * another Servlet which include to Servlet1 Send a request to the second
   * Servlet; Veriy TestFilter1 is not invoked.
   */
  @Test
  public void test3() throws Exception {
    TEST_PROPS.setProperty(APITEST, "include1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet1_INVOKED");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "FILTER1_INVOKED");
    invoke();
  }
}
