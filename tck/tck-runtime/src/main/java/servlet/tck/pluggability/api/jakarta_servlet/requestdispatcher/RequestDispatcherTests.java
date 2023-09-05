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
package servlet.tck.pluggability.api.jakarta_servlet.requestdispatcher;

import servlet.tck.api.jakarta_servlet.requestdispatcher.ForwardedServlet;
import servlet.tck.api.jakarta_servlet.requestdispatcher.ForwardedServlet2;
import servlet.tck.api.jakarta_servlet.requestdispatcher.ForwardedServlet3;
import servlet.tck.api.jakarta_servlet.requestdispatcher.IncludedServlet;
import servlet.tck.api.jakarta_servlet.requestdispatcher.TestServlet;
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

public class RequestDispatcherTests extends AbstractTckTest {

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
            .addAsResource(RequestDispatcherTests.class.getResource("servlet_plu_requestdispatcher_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_requestdispatcher_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ForwardedServlet.class, ForwardedServlet2.class, ForwardedServlet3.class,
                    IncludedServlet.class, TestServlet.class)
            .addAsLibraries(javaArchive);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */

  /*
   * @testName: forwardTest
   *
   * @assertion_ids: Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:272;
   * Servlet:JAVADOC:274;
   *
   * @test_Strategy: Create a servlet, get its RequestDispatcher and use it to
   * forward to a servlet
   */
  @Test
  public void forwardTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "forwardTest");
    invoke();
  }

  /*
   * @testName: forward_1Test
   *
   * @assertion_ids: Servlet:SPEC:77; Servlet:SPEC:80; Servlet:JAVADOC:230;
   * Servlet:JAVADOC:277;
   *
   * @test_Strategy: A negative test for RequestDispatcher.forward() method.
   * Create a servlet, print a string to the buffer, flush the buffer to commit
   * the string, get its RequestDispatcher and use it to forward to a servlet.
   */
  @Test
  public void forward_1Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "forward_1Test");
    invoke();
  }

  /*
   * @testName: includeTest
   *
   * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
   *
   * @test_Strategy: Create a servlet, get its RequestDispatcher and use it to
   * include a servlet
   */
  @Test
  public void includeTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "includeTest");
    invoke();
  }

  /*
   * @testName: include_1Test
   *
   * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
   *
   * @test_Strategy: A negative test for RequestDispatcher.include() test.
   * Create a servlet, set its Content-Type to be 'text/html', get its
   * RequestDispatcher and use it to include a servlet. The included servlet
   * tries to change the Content-Type to be text/html. Test at the client side
   * for correct Content-Type.
   */
  @Test
  public void include_1Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "include_1Test");
    TEST_PROPS.setProperty(EXPECTED_HEADERS, "Content-Type: text/sgml");
    invoke();
  }

  /*
   * @testName: include_2Test
   *
   * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230;
   * Servlet:JAVADOC:279;
   *
   * @test_Strategy: A negative test for RequestDispatcher.include() method.
   * Create a servlet with service() method throws ServletException. Use
   * RequestDispatcher to include to this servlet. Verify that include() method
   * throws ServletException.
   */
  @Test
  public void include_2Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "include_2Test");
    invoke();
  }

  /*
   * @testName: include_3Test
   *
   * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230;
   * Servlet:JAVADOC:280;
   *
   * @test_Strategy: A negative test for RequestDispatcher.include() method.
   * Create a servlet with service() method throws IOException. Use
   * RequestDispatcher to include to this servlet. Verify that include() method
   * throws IOException.
   */
  @Test
  public void include_3Test() throws Exception {
    TEST_PROPS.setProperty(APITEST, "include_3Test");
    invoke();
  }
}
