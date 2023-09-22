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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpsessionbindingevent;

import servlet.tck.api.jakarta_servlet_http.httpsessionbindingevent.HSBindingEvent;
import servlet.tck.api.jakarta_servlet_http.httpsessionbindingevent.TestServlet;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpSessionBindingEventTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(HttpSessionBindingEventTests.class.getResource("servlet_pluh_httpsessionbindingevent_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_httpsessionbindingevent_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(HSBindingEvent.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: addedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:309;Servlet:JAVADOC:307;Servlet:JAVADOC:308
   * 
   * @test_Strategy: Client calls a servlet that adds an attribute. The listener
   * should detect the add and writes the values returned by the getName,
   * getSession(), and getValue() methods to a static log. Servlet then reads
   * the log and verifies the result
   */
  @Test
  public void addedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "addedTest");
    invoke();
  }

  /*
   * @testName: removedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:310;Servlet:JAVADOC:307;Servlet:JAVADOC:308
   * 
   * @test_Strategy: Client calls a servlet that adds/removes an attribute. The
   * listener should detect the changes and writes the values returned by the
   * getName, getSession(), and getValue() methods to a static log. Servlet then
   * reads the log and verifies the result
   */
  @Test
  public void removedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "removedTest");
    invoke();
  }

  /*
   * @testName: replacedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:311;Servlet:JAVADOC:307;Servlet:JAVADOC:308
   * 
   * @test_Strategy: Client calls a servlet that adds/replaces an attribute. The
   * listener should detect the changes and writes the values returned by the
   * getName, getSession(), and getValue() methods to a static log. Servlet then
   * reads the log and verifies the result
   */
  @Test
  public void replacedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "replacedTest");
    invoke();
  }

  /*
   * @testName: constructor_StringTest
   * 
   * @assertion_ids: Servlet:JAVADOC:305
   * 
   * @test_Strategy: Servlet creates an object using the 2 argument method.
   */
  @Test
  public void constructor_StringTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructor_StringTest");
    invoke();
  }

  /*
   * @testName: constructor_String_ObjectTest
   * 
   * @assertion_ids: Servlet:JAVADOC:306
   * 
   * @test_Strategy: Servlet creates an object using the 3 argument method.
   */
  @Test
  public void constructor_String_ObjectTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructor_String_ObjectTest");
    invoke();
  }
}
