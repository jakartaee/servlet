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
package servlet.tck.api.jakarta_servlet.dispatchtest;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URL;

public class DispatchTests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("DispatchTestServlet");
  }

  @ArquillianResource
  @OperateOnDeployment("servlet_js_dispatchtest_web1")
  public URL url2;

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_dispatchtest_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DispatchTestServlet.class, DispatchTestsServlet.class, DispatchTests1.class, DispatchTests2.class,
                        DispatchTests3.class, DispatchTests4.class, DispatchTests5.class, DispatchTests6.class, TestListener.class,
                        TestListener0.class, TestListener1.class, TestListener2.class, TestListener3.class,
                        DispatchTestServlet.class)
            .setWebXML(DispatchTests.class.getResource("servlet_js_dispatchtest_web.xml"));
  }

  @Deployment(testable = false, name = "servlet_js_dispatchtest_web1")
  public static WebArchive getTestArchive1() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_dispatchtest1_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DispatchTests10.class, DispatchTests11.class, DispatchTests12.class,
                        DispatchTests13.class, DispatchTests14.class, DispatchTests15.class,
                        DispatchTests16.class, DispatchTests17.class, DispatchTests18.class,
                        DispatchTests19.class, DispatchTests20.class,
                        DispatchTestServlet.class)
            .setWebXML(DispatchTests.class.getResource("servlet_js_dispatchtest1_web.xml"));
  }




  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */

  /* Run test */
  /*
   * @testName: dispatchReturnTest
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. verifies all work
   * accordingly.
   */
  @Test
  public void dispatchReturnTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchReturnTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchReturnTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. verifies all work accordingly.
   */
  @Test
  public void dispatchReturnTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchReturnTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchReturnTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
   * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
   * call ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. verifies all work
   * accordingly.
   */
  @Test
  public void dispatchReturnTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest2");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest2|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchReturnTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. verifies all work accordingly.
   */
  @Test
  public void dispatchReturnTest3() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest3");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest3|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchReturnTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ac.dispatch(ServletContext,URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. verifies all work
   * accordingly.
   */
  @Test
  public void dispatchReturnTest4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest4");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchReturnTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(ServletContext,URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. verifies all work
   * accordingly.
   */
  @Test
  public void dispatchReturnTest5() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchReturnTest5");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchReturnTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync and
   * dispatch again, and check all above; verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After second dispatch|"
            + "ASYNC_STARTED_AGAIN_startAsyncAgainTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. StartAsync and dispatch again, and check all above;
   * verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After second dispatch|"
            + "ASYNC_STARTED_AGAIN_startAsyncAgainTest1|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.10; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync again in
   * dispatched thread, and check all above; ac.complete(); verifies all work
   * accordingly.
   */
  @Test
  public void startAsyncAgainTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest2");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest2|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest2|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.10; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. StartAsync again in dispatched thread, and check all
   * above; call ac.complete(); verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest3() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest3");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest3|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest3|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync in
   * dispatched thread, and check all above; StartAsync again in the
   * asynchrounous thread verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest4");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. StartAsync in dispatched thread, and check all above;
   * StartAsync again in the asynchrounous thread verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest5() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest5");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_startAsyncAgainTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest6
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
   * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
   * call ServletRequest.getDispatcherType() check request's attributes:
   * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
   * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
   * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
   * returns dispatch operation starts. StartAsync again, and check all above;
   * call ac.dispatch(URI); verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest6() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest6");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest6|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest7
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
   * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
   * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync again, and
   * check all above; call ac.dispatch(URI); verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest7() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest7");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest7|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest2|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest8
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
   * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
   * call ServletRequest.getDispatcherType() check request's attributes:
   * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
   * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
   * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
   * returns dispatch operation starts. StartAsync again in dispatched thread,
   * and check all above; ac.complete(); verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest8() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest8");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest8|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest3|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest9
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
   * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
   * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync again in
   * dispatched thread, and check all above; call ac.complete(); verifies all
   * work accordingly.
   */
  @Test
  public void startAsyncAgainTest9() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest9");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest9|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest10
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.3; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
   * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
   * call ServletRequest.getDispatcherType() check request's attributes:
   * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
   * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
   * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
   * returns dispatch operation starts. StartAsync in dispatched thread, and
   * check all above; StartAsync again in the asynchrounous thread verifies all
   * work accordingly.
   */
  @Test
  public void startAsyncAgainTest10() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest10");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest11
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
   * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.3; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
   * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
   * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync in
   * dispatched thread, and check all above; StartAsync again in the
   * asynchrounous thread verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest11() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest11");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest11|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest6|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest12
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync and
   * dispatch again, and check all above; verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest12() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest12");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest12|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest11|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest13
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync and
   * dispatch again, and check all above; verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest13() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest13");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest13|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest12|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before second dispatch|" + "second dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest14
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:706;; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync again in
   * dispatched thread, and check all above; ac.complete(); verifies all work
   * accordingly.
   */
  @Test
  public void startAsyncAgainTest14() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest14");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest14|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest13|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest15
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
   * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(); call ServletRequest.isAsyncSupported() call
   * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. StartAsync again in dispatched thread, and check all
   * above; call ac.complete(); verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest15() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest15");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest15|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest14|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "Before complete");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest16
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.3; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync in
   * dispatched thread, and check all above; StartAsync again in the
   * asynchrounous thread verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest16() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest16");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest16|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest15|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: startAsyncAgainTest17
   * 
   * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.3; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
   * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
   * call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() check System times: before calling
   * dispatch; dispatch returns dispatch operation starts. StartAsync in
   * dispatched thread, and check all above; StartAsync again in the
   * asynchrounous thread verifies all work accordingly.
   */
  @Test
  public void startAsyncAgainTest17() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "startAsyncAgainTest17");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_startAsyncAgainTest17|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest16|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC|"
            + "startAsync called|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=true|" + "DispatcherType=ASYNC|"
            + "startAsync called again|"
            + "Expected IllegalStateException thrown");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.11; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(); check System times:
   * before calling dispatch; dispatch returns dispatch operation starts. call
   * ac.dispatch() again verifies all work accordingly.
   */
  @Test
  public void negativeDispatchTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch() called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_negativeDispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
   * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
   * Servlet:JAVADOC:639.11; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(); check System times:
   * before calling dispatch; dispatch returns dispatch operation starts. call
   * ac.dispatch() again verifies all work accordingly.
   */
  @Test
  public void negativeDispatchTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch() called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_negativeDispatchTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.3;
   * Servlet:JAVADOC:640.8; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(URI); check System
   * times: before calling dispatch; dispatch returns dispatch operation starts.
   * call ac.dispatch(URI) again verifies all work accordingly.
   */
  @Test
  public void negativeDispatchTest4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest4");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch(URI) called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.3;
   * Servlet:JAVADOC:640.8; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(URI); check System
   * times: before calling dispatch; dispatch returns dispatch operation starts.
   * call ac.dispatch(URI) again verifies all work accordingly.
   */
  @Test
  public void negativeDispatchTest5() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest5");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch(URI) called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest8
   * 
   * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext, URI);
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. call ac.dispatch(ServletContext, URI) again verifies all
   * work accordingly.
   */
  @Test
  public void negativeDispatchTest8() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest8");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest8|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch() called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest9
   * 
   * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext, URI);
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. call ac.dispatch(ServletContext, URI) again verifies all
   * work accordingly.
   */
  @Test
  public void negativeDispatchTest9() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest9");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest9|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch() called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest12
   * 
   * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext,URI);
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. call ac.dispatch(URI) again verifies all work
   * accordingly.
   */
  @Test
  public void negativeDispatchTest12() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest12");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest12|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch(URI) called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: negativeDispatchTest13
   * 
   * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
   * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
   * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; StartAsync in
   * DispatchTestServlet ServletRequest.startAsync(request, response); call
   * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
   * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext,URI);
   * check System times: before calling dispatch; dispatch returns dispatch
   * operation starts. call ac.dispatch(URI) again verifies all work
   * accordingly.
   */
  @Test
  public void negativeDispatchTest13() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "negativeDispatchTest13");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_negativeDispatchTest13|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "Before dispatch|" + "dispatch return|" + "dispatch again|"
            + "dispatch(URI) called again|"
            + "Expected IllegalStateException thrown|" + "After dispatch|"
            + "ASYNC_STARTED_dispatchTest10|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest
   * 
   * @assertion_ids: Servlet:JAVADOC:639.12; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:710; Servlet:JAVADOC:866;
   * Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(); call ac.dispatch(); verifies all works
   */
  @Test
  public void dispatchAfterCommitTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchAfterCommitTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:639.12; Servlet:JAVADOC:707;
   * Servlet:JAVADOC:708; Servlet:JAVADOC:712; Servlet:JAVADOC:866;
   * Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(request, response); call ac.dispatch(); verifies
   * all works
   */
  @Test
  public void dispatchAfterCommitTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest1|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchAfterCommitTest1|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(); call ac.dispatch(URI); verifies all works
   */
  @Test
  public void dispatchAfterCommitTest2() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest2");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest2|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest3
   * 
   * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(request, response); call ac.dispatch(URI);
   * verifies all works
   */
  @Test
  public void dispatchAfterCommitTest3() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest3");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest3|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest4
   * 
   * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:710; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(); call ac.dispatch(ServletContext,URI); verifies
   * all works
   */
  @Test
  public void dispatchAfterCommitTest4() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest4");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest4|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest10|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }

  /*
   * @testName: dispatchAfterCommitTest5
   * 
   * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
   * Servlet:JAVADOC:712; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
   *
   * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
   * Client send a request to DispatchTestServlet; DispatchTestServlet commits
   * the response; StartAsync in DispatchTestServlet
   * ServletRequest.startAsync(request, response); call
   * ac.dispatch(ServletContext,URI); verifies all works
   */
  @Test
  public void dispatchAfterCommitTest5() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "dispatchAfterCommitTest5");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "ASYNC_NOT_STARTED_dispatchAfterCommitTest5|" + "IsAsyncSupported=true|"
            + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
            + "After commmit|" + "Before dispatch|" + "dispatch return|"
            + "After dispatch|" + "ASYNC_STARTED_dispatchTest10|"
            + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
            + "DispatcherType=ASYNC");
    invoke();
  }
}
