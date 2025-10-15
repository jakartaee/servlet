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
package servlet.tck.spec.async;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

public class AsyncTests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_async_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(Filter4.class, Filter5.class, Filter6.class, Filter7.class, Filter8.class, Filter9.class,
                    Filter10.class, Servlet1.class, Servlet2.class, Servlet3.class, Servlet4.class, Servlet5.class,
                    Servlet6.class, Servlet7.class, Servlet8.class, Servlet9.class, Servlet10.class, TestServlet.class)
            .setWebXML(AsyncTests.class.getResource("servlet_spec_async_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: AsyncSupportedTrueTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet1, which is declared
   * async-supported=true in web.xml; verifies in Servlet1 that
   * request.isAsyncSupported()=true.
   */
  @Test
  public void AsyncSupportedTrueTest1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_Async=true");
    invoke();
  }

  /*
   * @testName: AsyncSupportedTrueTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is also declared async-supported=true in web.xml; verifies in
   * Servlet4 that request.isAsyncSupported()=true.
   */
  @Test
  public void AsyncSupportedTrueTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet4?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter4=INVOKED|Servlet4_Async=true");
    invoke();
  }

  /*
   * @testName: AsyncSupportedTrueTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is also declared async-supported=true in web.xml; access
   * Servlet1 from Servlet4, Servlet1 is declared async-supported=true in
   * web.xml; verifies in Servlet1 that request.isAsyncSupported()=true.
   */
  @Test
  public void AsyncSupportedTrueTest3() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=testdirect&id=1 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_Async=true");
    invoke();
  }

  /*
   * @testName: AsyncSupportedTrueTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is also declared async-supported=true in web.xml; access
   * Servlet10 from Servlet4, Servlet10 is declared async-supported=true in
   * web.xml; Servlet10 has a filter Filter10 associated with it which is also
   * declared async-supported=true in web.xml; verifies in Servlet10 that
   * request.isAsyncSupported()=true.
   */
  @Test
  public void AsyncSupportedTrueTest4() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=testdirect&id=10 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter10=INVOKED|Servlet10_Async=true");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet2, which is not declared
   * async-supported in web.xml; verifies in Servlet2 that
   * request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet3, which is declared
   * async-supported=false in web.xml; verifies in Servlet3 that
   * request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet3_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 is also associated with Filter5,
   * which is declared async-supported=false in web.xml; verifies in Servlet5
   * that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest3() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter5=INVOKED|Servlet5_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet6, which is declared
   * async-supported=true in web.xml; Servlet6 is also associated with Filter6,
   * which is not declared async-supported wise in web.xml; verifies in Servlet6
   * that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest4() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet6?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter6=INVOKED|Servlet6_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet7, which is declared
   * async-supported=false in web.xml; Servlet7 is also associated with Filter7,
   * which is declared async-supported=true in web.xml; verifies in Servlet7
   * that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest5() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet7?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter7=INVOKED|Servlet7_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest6
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet8, which is declared
   * async-supported=false in web.xml; Servlet5 is also associated with Filter8,
   * which is declared async-supported=false in web.xml; verifies in Servlet8
   * that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest6() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet8?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter8=INVOKED|Servlet8_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest7
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet9, which is declared
   * async-supported=false in web.xml; Servlet9 is also associated with Filter9,
   * which is not declared async-supported wise in web.xml; verifies in Servlet9
   * that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest7() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet9?testname=direct HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter9=INVOKED|Servlet9_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest8
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 is also associated with Filter4;
   * which is declared async-supported=true in web.xml; send request to Servlet2
   * in Servlet4; Servlet2 is not declared async-supported wise in web.xml;
   * verifies in Servlet2 that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest8() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=testdirect&id=2 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest9
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 is also associated with Filter4;
   * which is declared async-supported=true in web.xml; send request to Servlet3
   * in Servlet4; Servlet3 is declared async-supported=false in web.xml;
   * verifies in Servlet3 that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest9() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=testdirect&id=3 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet3_Async=false");
    invoke();
  }

  /*
   * @testName: AsyncSupportedFalseTest10
   * 
   * @assertion_ids: Servlet:JAVADOC:708;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 is also associated with Filter4;
   * which is declared async-supported=true in web.xml; send request to Servlet5
   * in Servlet4; Servlet5 is declared async-supported=true in web.xml; Servlet5
   * is also associated with Filter5; which is declared async-supported=false in
   * web.xml; verifies in Servlet5 that request.isAsyncSupported()=false.
   */
  @Test
  public void AsyncSupportedFalseTest10() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=testdirect&id=5 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet5_Async=false");
    invoke();
  }

  /*
   * @testName: StartAsyncTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:710; Servlet:JAVADOC:638;
   * 
   * @test_Strategy: Client send request to Servlet1, which is declared
   * async-supported=true in web.xml; call startAsync() in Servlet1, verifies
   * that AsyncContext start and complete.
   */
  @Test
  public void StartAsyncTest1() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet1?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_Async=STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet2, which is declared
   * async-supported=false in web.xml; call startAsync() in Servlet2, verifies
   * that startAsync causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest2() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet2?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet3, which is not declared in
   * web.xml about support for async; call startAsync() in Servlet3, verifies
   * that startAsync causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest3() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet3?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet3_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:710; Servlet:JAVADOC:638;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is also declared async-supported=true in web.xml; call
   * startAsync() in Servlet4, verifies that AsyncContext start and complete.
   */
  @Test
  public void StartAsyncTest4() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet4?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter4=INVOKED|Servlet4_Async=STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; call
   * startAsync() in Servlet5, verifies that startAsync causes
   * IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest5() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet5?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter5=INVOKED|Servlet5_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest6
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet6, which is declared
   * async-supported=true in web.xml; Servlet6 has a filter Filter6 associated
   * with it which is not declared async-supported wise in web.xml; call
   * startAsync() in Servlet6, verifies that startAsync causes
   * IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest6() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet6?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter6=INVOKED|Servlet6_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest7
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet7, which is declared
   * async-supported=false in web.xml; Servlet7 has a filter Filter7 associated
   * with it which is declared async-supported=true in web.xml; call
   * startAsync() in Servlet7, verifies that startAsync causes
   * IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest7() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/Servlet7?testname=startA HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter7=INVOKED|Servlet7_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest8
   * 
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:643;
   * Servlet:JAVADOC:710;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet1 in Servlet4; Servlet1 is declared async-supported=true in web.xml;
   * verifies in Servlet1 that startAsync and complete runs.
   */
  @Test
  public void StartAsyncTest8() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=1 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_Async=STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest9
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet2 in Servlet4; Servlet2 is not declared async-supported wise in
   * web.xml; verify in Servlet2 that startAsync causes IllegalStateException
   * thrown.
   */
  @Test
  public void StartAsyncTest9() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=2 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest10
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet3 in Servlet4; Servlet3 is declared async-supported=false in
   * web.xml; verifies in Servlet3 that startAsync causes IllegalStateException
   * thrown.
   */
  @Test
  public void StartAsyncTest10() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=3 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet3_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest11
   * 
   * @assertion_ids: Servlet:JAVADOC:710; Servlet:JAVADOC:638;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet10 in Servlet4; Servlet10 is declared async-supported=true in
   * web.xml; Servlet10 has a filter Filter10 associated with it which is also
   * declared async-supported=true in web.xml; verifies in Servlet10 that
   * startAsync and complete run.
   */
  @Test
  public void StartAsyncTest11() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=10 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet10_Async=STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest12
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet5 in Servlet4; Servlet5 is declared async-supported=true in web.xml;
   * Servlet5 has a filter Filter5 associated with it which is also declared
   * async-supported=false in web.xml; verifies in Servlet5 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest12() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=5 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter5=INVOKED|Servlet5_Async=false" + "|Servlet5_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest13
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet6 in Servlet4; Servlet6 is not declared async-supported wise in
   * web.xml; Servlet6 has a filter Filter6 associated with it which is declared
   * async-supported=true in web.xml; verifies in Servlet6 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest13() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=6 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter6=INVOKED|Servlet6_Async=false" + "|Servlet6_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest14
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet7 in Servlet4; Servlet7 is declared async-supported=false in
   * web.xml; Servlet7 has a filter Filter7 associated with it which is declared
   * async-supported=true in web.xml; verifies in Servlet7 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest14() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=7 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter7=INVOKED|Servlet7_Async=false" + "|Servlet7_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest15
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet8 in Servlet4; Servlet8 is declared async-supported=false in
   * web.xml; Servlet8 has a filter Filter8 associated with it which is declared
   * async-supported=false in web.xml; verifies in Servlet8 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest15() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=8 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter8=INVOKED|Servlet8_Async=false" + "|Servlet8_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest16
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet4, which is declared
   * async-supported=true in web.xml; Servlet4 has a filter Filter4 associated
   * with it which is declared async-supported=true in web.xml; send request to
   * Servlet9 in Servlet4; Servlet9 is declared async-supported=false in
   * web.xml; Servlet9 has a filter Filter9 associated with it which is
   * undeclared async-supported wise in web.xml; verifies in Servlet9 that
   * startAsync causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest16() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet4?testname=teststartA&id=9 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter9=INVOKED|Servlet9_Async=false" + "|Servlet9_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest17
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet1 in Servlet5; Servlet1 is declared async-supported=true in web.xml;
   * verifies in Servlet1 startAsync causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest17() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=1 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet1_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest18
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet2 in Servlet5; Servlet2 is not declared async-supported wise in
   * web.xml; verify in Servlet2 that startAsync causes IllegalStateException
   * thrown.
   */
  @Test
  public void StartAsyncTest18() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=2 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet2_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest19
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet3 in Servlet5; Servlet3 is declared async-supported=false in
   * web.xml; verifies in Servlet3 that startAsync causes IllegalStateException
   * thrown.
   */
  @Test
  public void StartAsyncTest19() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=3 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Servlet3_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest20
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet4 in Servlet5; Servlet4 is declared async-supported=true in web.xml;
   * Servlet4 has a filter Filter4 associated with it which is also declared
   * async-supported=true in web.xml; verifies in Servlet4 that
   * IllegalStateException is thrown.
   */
  @Test
  public void StartAsyncTest20() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=4 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter4=INVOKED|Servlet4_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest21
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet6 in Servlet5; Servlet6 is declared async-supported=true in web.xml;
   * Servlet6 has a filter Filter6 associated with it which is also declared
   * async-supported=false in web.xml; verifies in Servlet6 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest21() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=6 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter6=INVOKED|Servlet6_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest22
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet7 in Servlet5; Servlet7 is declared async-supported=false in
   * web.xml; Servlet7 has a filter Filter7 associated with it which is declared
   * async-supported=true in web.xml; verifies in Servlet7 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest22() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=7 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter7=INVOKED|Servlet7_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest23
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet8 in Servlet5; Servlet8 is declared async-supported=false in
   * web.xml; Servlet8 has a filter Filter8 associated with it which is declared
   * async-supported=false in web.xml; verifies in Servlet8 that startAsync
   * causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest23() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=8 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter8=INVOKED|Servlet8_Async=false|Servlet8_Async=NOT_STARTED");
    invoke();
  }

  /*
   * @testName: StartAsyncTest24
   * 
   * @assertion_ids: Servlet:JAVADOC:711;
   * 
   * @test_Strategy: Client send request to Servlet5, which is declared
   * async-supported=true in web.xml; Servlet5 has a filter Filter5 associated
   * with it which is declared async-supported=false in web.xml; send request to
   * Servlet9 in Servlet5; Servlet9 is declared async-supported=false in
   * web.xml; Servlet9 has a filter Filter9 associated with it which is
   * undeclared async-supported wise in web.xml; verifies in Servlet9 that
   * startAsync causes IllegalStateException thrown.
   */
  @Test
  public void StartAsyncTest24() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot()
        + "/Servlet5?testname=teststartA&id=9 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "Filter9=INVOKED|Servlet9_Async=NOT_STARTED");
    invoke();
  }
  /*
   * @testName: StartAsyncTest25
   * 
   * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:643;
   * Servlet:JAVADOC:710;
   * 
   * @test_Strategy: Client send request to TestServlet, which is declared
   * async-supported=true in web.xml; verifies in TestServlet that startAsync
   * completes fine.
   */
  @Test
  public void StartAsyncTest25() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet?testname=test1 HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "test1_INVOKED");
    invoke();
  }
}
