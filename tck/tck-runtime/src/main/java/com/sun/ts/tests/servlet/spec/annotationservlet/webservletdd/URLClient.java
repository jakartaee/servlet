/*
 * Copyright (c) 2011, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.spec.annotationservlet.webservletdd;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet1;
import com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet2;
import com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet3;
import com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet4;
import com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet5;
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
    return ShrinkWrap.create(WebArchive.class, "servlet_annotationservlet_webservletdd_web.war")
            .addClasses(Servlet1.class, Servlet2.class, Servlet3.class, Servlet4.class, Servlet5.class)
            .setWebXML(URLClient.class.getResource("servlet_annotationservlet_webservletdd_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.6; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.8; Servlet:SPEC:221.10; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Define Servlet1 using
   * annotation @WebServlet(String); Define Servlet1 in deployment descritpor
   * named as TestServlet1; Invoke Servlet1 at the URL specified by @WebServlet;
   * Verify that Servlet1 is invoked properly; Verify that servlet name is set
   * correctly; Invoke Servlet1 at the URL specified by deployment descritpor;
   * Veriy Servlet1 is invoked properly; Verify that servlet name is set
   * correctly;
   */
  @Test
  public void test1() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1URL HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet1_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet1"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet1_INVOKED"
        + "|servletname=TestServlet1|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.6;
   * Servlet:SPEC:221.7; Servlet:SPEC:221.8; Servlet:SPEC:221.10;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet2; Define Servlet2 using
   * annotation @WebServlet(value=""); Define Servlet2 in deployment descritpor
   * as well; Invoke Servlet2 at any of the URLs specified by @WebServlet;
   * Verify that Servlet2 is invoked properly; Verify that servlet name is set
   * properly; Invoke Servlet2 at the URLs specified by deployment descritpor;
   * Veriy Servlet2 is invoked properly; Verify that servlet name is set
   * properly;
   */
  @Test
  public void test2() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test/xyz HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/t.html HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=TestServlet2|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Test2Servlet2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=TestServlet2|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test2/indext.html HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=TestServlet2|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/xyz.jsp HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=TestServlet2|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.8; Servlet:SPEC:221.10; Servlet:JAVADOC:819;
   * Servlet:JAVADOC:822; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet3; Define Servlet3 using annotation
   * 
   * @WebServlet(value="", initParams={}, name="") Define Servlet3 in deployment
   * descritpor as well; Invoke Servlet3 at the URL specified by @WebServlet; --
   * value is set correctly -- all @initParams are passed correctly. -- servlet
   * name is set correctly Invoke Servlet3 at the URL specified by deployment
   * descritpor; Veriy Servlet3 is invoked and -- value is set correctly --
   * all @initParams are passed correctly. -- servlet name is set correctly
   */
  @Test
  public void test3() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3URL HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet3_INVOKED|initParams:" + "|name1=value1|name2=value2"
            + "|servletname=Servlet3|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet3 HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet3_INVOKED|initParams:" + "|msg1=first|msg2=ignore"
            + "|servletname=TestServlet3|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test4
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.8; Servlet:SPEC:221.10; Servlet:JAVADOC:817;
   * Servlet:JAVADOC:819; Servlet:JAVADOC:822; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet4; Define Servlet4 using
   * annotation:
   * 
   * @WebServlet(urlPatterns="", asyncSupported = true, initParams={}, name="")
   * Define Servlet4 in deployment descritpor as well; Invoke Servlet4 at the
   * URLs specified by @WebServlet; Veriy Servlet4 is invoked and --
   * all @initParams are passed correctly. -- servlet name is set correctly --
   * async support is set correctly Invoke Servlet4 at the URL specified by
   * deployment descritpor; Veriy Servlet4 is invoked and -- all @initParams are
   * passed correctly. -- servlet name is set correctly -- async support is set
   * correctly
   */
  @Test
  public void test4() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet4URL HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet4_INVOKED" + "|ServletName=Servlet4" + "|isAsyncSupported=true"
            + "|initParams:|name1=value1|name2=value2");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet4 HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet4_INVOKED" + "|ServletName=TestServlet4"
            + "|isAsyncSupported=false"
            + "|initParams:|msg1=second|msg2=ignore_again");
    invoke();
  }

  /*
   * @testName: test5
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.6;
   * Servlet:SPEC:221.7; Servlet:SPEC:221.8; Servlet:SPEC:221.10;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet5; Define Servlet5 using
   * annotation @WebServlet(urlPatterns=""); Define Servlet5 in deployment
   * descritpor as well; Invoke Servlet5 at any of the URLs specified
   * by @WebServlet; Veriy Servlet5 is invoked properly; Verify that servlet
   * name is set correctly; Verify that async support is set correctly; Invoke
   * Servlet5 at the URLs specified by deployment descritpor; Veriy Servlet5 is
   * invoked properly; Verify that servlet name is set correctly; Verify that
   * async support is set correctly;
   */
  @Test
  public void test5() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5URL1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5URL2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test5/xyz HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/z.txt HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=com.sun.ts.tests.servlet.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet5 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "Servlet5_INVOKED" + "|servletname=TestServlet5|isAsyncSupported=true");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Test5Servlet5 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "Servlet5_INVOKED" + "|servletname=TestServlet5|isAsyncSupported=true");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test55/xyz HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "Servlet5_INVOKED" + "|servletname=TestServlet5|isAsyncSupported=true");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/z.xml HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "Servlet5_INVOKED" + "|servletname=TestServlet5|isAsyncSupported=true");
    invoke();
  }
}
