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
 * $Id$
 */
package servlet.tck.pluggability.fragment;

import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FragmentTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setContextRoot("/servlet_spec_fragment_web");
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {

    JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, TestServlet2.class, TestFilter.class)
            .addAsResource(FragmentTests.class.getResource("servlet_spec_fragment_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    JavaArchive javaArchive2 = ShrinkWrap.create(JavaArchive.class, "fragment-2.jar")
            .addClasses(TestServlet3.class, TestFilter1.class)
            .addAsResource(FragmentTests.class.getResource("servlet_spec_fragment_web-fragment_1.xml"),
                    "META-INF/web-fragment.xml");
    JavaArchive javaArchive3 = ShrinkWrap.create(JavaArchive.class, "fragment-3.jar")
            .addClasses(TestServlet4.class, TestFilter2.class)
            .addAsResource(FragmentTests.class.getResource("servlet_spec_fragment_web-fragment_2.xml"),
                    "META-INF/web-fragment.xml");
    JavaArchive javaArchive4 = ShrinkWrap.create(JavaArchive.class, "fragment-4.jar")
            .addClasses(TestServlet5.class, TestFilter3.class)
            .addAsResource(FragmentTests.class.getResource("servlet_spec_fragment_web-fragment_3.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_fragment_web.war")
            .addAsLibraries(javaArchive1, javaArchive2, javaArchive3, javaArchive4)
            .addClasses(TestFilter.class, TestServlet1.class, TestServlet2.class)
            .setWebXML(FragmentTests.class.getResource("servlet_spec_fragment_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /*
   * @testName: initParamTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:258.1; Servlet:SPEC:258.5.1;
   * Servlet:SPEC:258.6.1; Servlet:SPEC:258.7.1;
   *
   * @test_Strategy: 1. Define servlet TestServlet1 in web.xml as well as
   * web-fragment.xml; 2. Send request to /TestServlet1, verify TestServlet1 is
   * invoked 3. Also verify that <init-param> defined in both web.xml and
   * web-fragment.xml are considered, and the one defined in web.xml take
   * precedence.
   */
  @Test
  public void initParamTest() throws Exception {
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "TestServlet1|msg1=first|msg2=second");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet1" + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: addServletTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:258.1; Servlet:SPEC:258.5.1;
   *
   * @test_Strategy: 1. Define servlet TestServlet3 web-fragment.xml; 2. Send
   * request to /TestServlet3, verify TestServlet3 is invoked 3. Also verify
   * that filter is invoked too.
   */
  @Test
  public void addServletTest() throws Exception {
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "TestFilter3|fragment|three|TestServlet3|msg1=third|msg2=third");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet3" + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: addServletURLTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:258.1; Servlet:SPEC:258.5.1;
   * Servlet:SPEC:258.6.1; Servlet:SPEC:258.7.6;
   *
   * @test_Strategy: 1. Define servlet TestServlet2 web-fragment.xml and
   * web.xml; 2. Send request to URL /TestServlet2 defined in web.xml, verify
   * TestServlet2 is invoked 3. Send request to URL /TestServlet22 defined in
   * web-fragment.xml, verify 404 is returned.
   */
  @Test
  public void addServletURLTest() throws Exception {
    TEST_PROPS.get().setProperty(SEARCH_STRING, "TestServlet2");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet2" + " HTTP/1.1");
    invoke();

    TEST_PROPS.get().setProperty(STATUS_CODE, NOT_FOUND);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet22" + " HTTP/1.1");
    invoke();
  }

  /*
   * @testName: welcomefileTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:258.1; Servlet:SPEC:258.5.1;
   * Servlet:SPEC:258.6.1; Servlet:SPEC:258.7.5;
   *
   * @test_Strategy: 1. Define servlet TestServlet4 web-fragment.xml in
   * <welcome-file>; 2. Send request to URL /, verify TestServlet4 is invoked
   */
  @Test
  public void welcomefileTest() throws Exception {
    TEST_PROPS.get().setProperty(SEARCH_STRING, "TestServlet4");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + " HTTP/1.1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    invoke();
  }

  /*
   * @testName: filterOrderingTest
   *
   * @assertion_ids: Servlet:SPEC:232; Servlet:SPEC:241; Servlet:SPEC:242;
   * Servlet:SPEC:244; Servlet:SPEC:245; Servlet:SPEC:246; Servlet:SPEC:258.1;
   * Servlet:SPEC:258.5.1; Servlet:SPEC:258.6.1;
   *
   * @test_Strategy: 1. Define four Filters that mapping to TestServlet5 in
   * web.xml and two web-fragment.xml; 2. Send request to TestServlet5 3. Verify
   * that web.xml is always processed first; 4. Verify that <ordering> works
   * accordingly.
   */
  @Test
  public void filterOrderingTest() throws Exception {
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "TestFilter|fragment|none|" + "TestFilter3|fragment|three|"
            + "TestFilter2|fragment|two|"
            + "TestFilter1|fragment|one|TestServlet5");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet5" + " HTTP/1.1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "ignore");
    invoke();
  }
}
