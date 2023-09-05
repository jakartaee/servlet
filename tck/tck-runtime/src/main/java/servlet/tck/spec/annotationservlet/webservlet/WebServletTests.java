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
package servlet.tck.spec.annotationservlet.webservlet;

import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebServletTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "webservlet.war")
            .addClasses(Servlet1.class, Servlet2.class, Servlet3.class, Servlet4.class, Servlet5.class);
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
   * Servlet:SPEC:221.10; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Define Servlet1 using
   * annotation @WebServlet(String); Invoke Servlet1 at the URL specified
   * by @WebServlet; Veriy Servlet1 is invoked properly; Verify that servlet
   * name is set to the default name;
   */
  @Test
  public void test1() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1URL HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet1_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet1"
        + "|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.6;
   * Servlet:SPEC:221.7; Servlet:SPEC:221.10; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet2; Define Servlet2 using
   * annotation @WebServlet(value=""); Invoke Servlet2 at any of the URLs
   * specified by @WebServlet; Veriy Servlet2 is invoked properly; Verify that
   * servlet name is set to the default name;
   */
  @Test
  public void test2() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test/xyz HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/t.html HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.10; Servlet:JAVADOC:819; Servlet:JAVADOC:822;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet3; Define Servlet3 using annotation
   * 
   * @WebServlet(value="", initParams={}, name="") Invoke Servlet3 at the URL
   * specified by @WebServlet; Veriy Servlet3 is invoked and -- value is set
   * correctly -- all @initParams are passed correctly. -- servlet name is set
   * correctly
   */
  @Test
  public void test3() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3URL HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet3_INVOKED|initParams:" + "|name1=value1|name2=value2"
            + "|servletname=Servlet3" + "|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test4
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.10; Servlet:JAVADOC:817; Servlet:JAVADOC:819;
   * Servlet:JAVADOC:822; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet4; Define Servlet4 using
   * annotation:
   * 
   * @WebServlet(urlPatterns="", asyncSupported = true, initParams={}, name="")
   * Invoke Servlet4 at the URLs specified by @WebServlet; Veriy Servlet4 is
   * invoked and -- all @initParams are passed correctly. -- servlet name is set
   * correctly -- async support is set correctly
   */
  @Test
  public void test4() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet4URL/ HTTP/1.1");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "Servlet4_INVOKED" + "|ServletName=Servlet4" + "isAsyncSupported=true"
            + "|initParams:|name1=value1|name2=value2");
    invoke();
  }

  /*
   * @testName: test5
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.6;
   * Servlet:SPEC:221.7; Servlet:SPEC:221.10; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet5; Define Servlet5 using
   * annotation @WebServlet(urlPatterns=""); Invoke Servlet5 at any of the URLs
   * specified by @WebServlet; Veriy Servlet5 is invoked properly; Verify that
   * servlet name is set to the default name;
   */
  @Test
  public void test5() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5URL1 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5URL2 HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/test5/xyz HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/z.txt HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Servlet5_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet5"
        + "|isAsyncSupported=false");
    invoke();
  }
}
