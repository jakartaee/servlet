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
 * $Id$
 */
package servlet.tck.pluggability.aordering3;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.pluggability.common.CommonArchives;
import servlet.tck.pluggability.common.RequestListener;
import servlet.tck.pluggability.common.RequestListener6;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive6 = ShrinkWrap.create(JavaArchive.class, "fragment-6.jar")
            .addClasses(RequestListener6.class, TestServlet1.class)
            .addAsResource("servlet/tck/pluggability/common/web-fragment_6.xml",
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_aordering3_web.war")
            .addAsLibraries(CommonArchives.getCommonWebFragmentArchives())
            .addAsLibraries(javaArchive6)
            .addClasses(RequestListener.class, TestServlet1.class)
            .setWebXML(URLClient.class.getResource("servlet_spec_aordering3_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /*
   * @testName: absoluteOrderingTest
   *
   * @assertion_ids: Servlet:SPEC:230; Servlet:SPEC:231; Servlet:SPEC:232;
   * Servlet:SPEC:233; Servlet:SPEC:235;
   *
   * @test_Strategy: 1. Define seven RequestListeners and two servlets, in
   * web.xml and seven web-fragment.xml: web.xml - define and package
   * TestServlet1, RequestListener, with <absolute-ordering> of no fragments 2.
   * seven fragments: fragment1 - define and package TestServlet1,
   * RequestListener1, fragment2 - define and package RequestListener2 fragment3
   * - define and package RequestListener3, fragment4 - define and package
   * RequestListener4, fragment5 - define and package RequestListener5,
   * fragment6 (no <name>) defines and packages TestServlet2, RequestListener6
   * 3. Send request to TestServlet1 4. Verify that web.xml is processed; 5.
   * Verify that <absolute-ordering> works accordingly when empty, that all
   * fragnments are ignored.
   */
  @Test
  public void absoluteOrderingTest() throws Exception {
    TEST_PROPS.setProperty(SEARCH_STRING, "msg1=first|RequestListener");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "msg2=second|TestServlet2|"
            + "RequestListener1|RequestListener2|RequestListener3|"
            + "RequestListener4|RequestListener5|RequestListener6");
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet1" + " HTTP/1.1");
    invoke();

    TEST_PROPS.setProperty(STATUS_CODE, NOT_FOUND);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet2" + " HTTP/1.1");
    invoke();
  }
}
