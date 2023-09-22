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
package servlet.tck.pluggability.api.jakarta_servlet.servletresponsewrapper30;

import servlet.tck.api.jakarta_servlet.servletresponsewrapper30.IsWrapperForTest;
import servlet.tck.api.jakarta_servlet.servletresponsewrapper30.TCKServletResponseWrapper;
import servlet.tck.api.jakarta_servlet.servletresponsewrapper30.TCKServletResponsesubWrapper;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class ServletResponseWrapper30Tests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(ServletResponseWrapper30Tests.class.getResource("servlet_plu_servletresponsewrapper30_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_plu_servletresponsewrapper30_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(IsWrapperForTest.class, TCKServletResponsesubWrapper.class, TCKServletResponseWrapper.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: isWrapperForTest
   *
   * @assertion_ids: Servlet:JAVADOC:731; Servlet:JAVADOC:732;
   *
   * @test_Strategy: Create a Servlets isWrapperForTest; In the servlet, wrap
   * the Response a few time and in different ways; verifies the following
   * works: - jakarta.servlet.ServletResponseWrapper.isWrapperFor(ServletResponse)
   * - jakarta.servlet.ServletResponseWrapper.isWrapperFor(Class)
   */
  @Test
  public void isWrapperForTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/IsWrapperForTest  HTTP/1.1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test Failed");
    invoke();
  }
}
