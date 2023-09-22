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

package servlet.tck.spec.i18n.encoding;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncodingTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_i18n_encoding_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(EncodingTests.class.getResource("servlet_spec_i18n_encoding_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: spec1Test
   * 
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:JAVADOC:137;
   * Servlet:JAVADOC:164; Servlet:JAVADOC:139;
   *
   * @test_Strategy:
   */
  @Test
  public void spec1Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "spec1Test");
    invoke();
  }

  /*
   * @testName: spec2Test
   * 
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:JAVADOC:137;
   * Servlet:JAVADOC:164; Servlet:JAVADOC:139;
   * 
   * @test_Strategy:
   */
  @Test
  public void spec2Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "spec2Test");
    invoke();
  }

  /*
   * @testName: spec3Test
   * 
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:JAVADOC:137;
   * Servlet:JAVADOC:164; Servlet:JAVADOC:139;
   * 
   * @test_Strategy:
   */
  @Test
  public void spec3Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "spec3Test");
    invoke();
  }

}
