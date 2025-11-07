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
 * $Id:$
 */
package servlet.tck.spec.annotationservlet.webservletapi;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.spec.annotationservlet.webservlet.Servlet1;
import servlet.tck.spec.annotationservlet.webservlet.Servlet2;
import servlet.tck.spec.annotationservlet.webservlet.Servlet3;
import servlet.tck.spec.annotationservlet.webservlet.Servlet4;
import servlet.tck.spec.annotationservlet.webservlet.Servlet5;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebServletApiTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "webservletapi_web.war")
            .addClasses(TestListener.class, Servlet1.class, Servlet2.class, Servlet3.class, Servlet4.class, Servlet5.class);
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
   * Servlet:SPEC:221.9; Servlet:SPEC:221.10; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet1; Define Servlet1 using
   * annotation @WebServlet(String); Add Servlet1 to ServletContext
   * programmatically using different name; Invoke Servlet1 at the URL specified
   * by @WebServlet; Verify that it works as defined at @WebServlet; Invoke
   * Servlet1 at the URL specified by API programming; Veriy Servlet1 is invoked
   * properly; Verify that servlet name is set correctly; Verify that servlet
   * name is set to the default name;
   */
  @Test
  public void test1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1URL HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet1"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1APIURL HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet1_INVOKED" + "|servletname=Servlet1API|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.6;
   * Servlet:SPEC:221.7; Servlet:SPEC:221.9; Servlet:SPEC:221.10;
   * Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet2; Define Servlet2 using
   * annotation @WebServlet(value=""); Add Servlet2 to ServletContext
   * programmatically under different name; Invoke Servlet2 at any of the URLs
   * specified by @WebServlet; Verify that it is invoked properly; Invoke
   * Servlet2 at the URLs specified in program; Veriy Servlet2 is invoked
   * properly; Verify that servlet name is set properly;
   */
  @Test
  public void test2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL1 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2URL2 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/test/xyz HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/x/y/t.html HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=servlet.tck.spec.annotationservlet.webservlet.Servlet2"
        + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2APIURL HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet2_INVOKED" + "|servletname=Servlet2API|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2APIURL2 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet2_INVOKED" + "|servletname=Servlet2API|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/test2/indext.xml HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Servlet2_INVOKED" + "|servletname=Servlet2API|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/ServletAPIURL2/xyz HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_INVOKED"
        + "|servletname=Servlet2API" + "|isAsyncSupported=false");
    invoke();
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:221; Servlet:SPEC:221.1; Servlet:SPEC:221.2;
   * Servlet:SPEC:221.3; Servlet:SPEC:221.4; Servlet:SPEC:221.7;
   * Servlet:SPEC:221.9; Servlet:SPEC:221.10; Servlet:JAVADOC:819;
   * Servlet:JAVADOC:822; Servlet:JAVADOC:825;
   *
   * @test_Strategy: Create a servlet Servlet3; Define Servlet3 using annotation
   * 
   * @WebServlet(value="", initParams={}, name="") Add Servlet3 to
   * ServletContext programmatically under different name; Invoke Servlet3 at
   * the URL specified by @WebServlet; Verify that it is properly invoked;
   * Invoke Servlet3 at the URL specified by program; Veriy Servlet3 is invoked
   * and -- value is set correctly -- all @initParams are passed correctly. --
   * servlet name is set correctly
   */
  @Test
  public void test3() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3URL HTTP/1.1");
    TEST_PROPS.get().setProperty(UNORDERED_SEARCH_STRING,
        "Servlet3_INVOKED|initParams:" + "|name1=value1|name2=value2"
            + "|servletname=Servlet3" + "|isAsyncSupported=false");
    invoke();

    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3APIURL HTTP/1.1");
    TEST_PROPS.get().setProperty(UNORDERED_SEARCH_STRING,
        "Servlet3_INVOKED|initParams:" + "|name1=servlet3|name2=servlet3again"
            + "|servletname=Servlet3API" + "|isAsyncSupported=false");
    invoke();
  }
}
