/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet.servletcontext306;

import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateServlet;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletContext306Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext306_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, AddServletString.class, AddServletClass.class, CreateServlet.class,
                    AddFilterString.class, AddFilterClass.class, CreateFilter.class)
            .setWebXML(ServletContext306Tests.class.getResource("servlet_js_servletcontext306_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */

  /*
   * @testName: addServletStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:674.6;
   *
   * @test_Strategy: Negative test for ServletContext.addServlet(String, String)
   * Create a Servlet, call ServletContext.addServlet(String, String) Verify the
   * expected IllegalStateException is thrown.
   */
  @Test
  public void addServletStringTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addServletStringTest");
    invoke();
  }

  /*
   * @testName: addServletClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:676.6;
   *
   * @test_Strategy: Negative test for ServletContext.addServlet(String, Class)
   * Create a Servlet, call ServletContext.addServlet(String, Class) Verify the
   * expected IllegalStateException is thrown.
   */
  @Test
  public void addServletClassTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addServletClassTest");
    invoke();
  }

  /*
   * @testName: addServletTest
   *
   * @assertion_ids: Servlet:JAVADOC:675.7;
   *
   * @test_Strategy: Negative test for ServletContext.addServlet(String,
   * Servlet) Create a Servlet, call ServletContext.addServlet(String, Servlet)
   * Verify the expected IllegalStateException is thrown.
   */
  @Test
  public void addServletTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addServletTest");
    invoke();
  }

  /*
   * @testName: addFilterStringTest
   *
   * @assertion_ids: Servlet:JAVADOC:668.5;
   *
   * @test_Strategy: Negative test for ServletContext.addFilter(String, String)
   * Create a Servlet, call ServletContext.addFilter(String, String) Verify the
   * expected IllegalStateException is thrown.
   */
  @Test
  public void addFilterStringTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addFilterStringTest");
    invoke();
  }

  /*
   * @testName: addFilterClassTest
   *
   * @assertion_ids: Servlet:JAVADOC:670.5;
   *
   * @test_Strategy: Negative test for ServletContext.addFilter(String, Class)
   * Create a Servlet, call ServletContext.addFilter(String, Class) Verify the
   * expected IllegalStateException is thrown.
   */
  @Test
  public void addFilterClassTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addFilterClassTest");
    invoke();
  }

  /*
   * @testName: addFilterTest
   *
   * @assertion_ids: Servlet:JAVADOC:669.6;
   *
   * @test_Strategy: Negative test for ServletContext.addFilter(String, Filter)
   * Create a Servlet, call ServletContext.addFilter(String, Filter) Verify the
   * expected IllegalStateException is thrown.
   */
  @Test
  public void addFilterTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "addFilterTest");
    invoke();
  }

  /*
   * @testName: setInitParameterTest
   *
   * @assertion_ids: Servlet:JAVADOC:694.1;
   *
   * @test_Strategy: Negative test for ServletContext.setInitParameter(String,
   * String) Create a Servlet, call ServletContext.setInitParameter(String,
   * String) Verify the expected IllegalStateException is thrown.
   */
  @Test
  public void setInitParameterTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "setInitParameterTest");
    invoke();
  }
}
