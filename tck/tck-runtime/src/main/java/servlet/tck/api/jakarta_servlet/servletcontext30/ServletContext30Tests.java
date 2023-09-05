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
package servlet.tck.api.jakarta_servlet.servletcontext30;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletContext30Tests extends AbstractTckTest {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext30_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AddFilterClass.class, AddFilterNotFound.class, AddFilterString.class, AddSCAttributeListenerClass.class,
                    AddSCAttributeListenerString.class, AddSCListenerClass.class, AddSCListenerString.class, AddServletClass.class,
                    AddServletNotFound.class, AddServletString.class, AddSRAttributeListenerClass.class, AddSRAttributeListenerString.class,
                    AddSRListenerClass.class, AddSRListenerString.class, BadFilter.class, BadListener.class, BadServlet.class,
                    CreateFilter.class, CreateSCAttributeListener.class, CreateSCListener.class, CreateServlet.class, CreateSRAttributeListener.class,
                    CreateSRListener.class, DuplicateFilterClass.class, DuplicateFilterString.class, DuplicateServletClass.class, DuplicateServletString.class,
                    FilterTestServlet.class, TestListener.class, TestServlet.class)
            .setWebXML(ServletContext30Tests.class.getResource("servlet_js_servletcontext30_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * Test setup; In Deployment Descriptor, a Servlet TestServlet and Listener
   * TestListener; TestListener extends ServletContextListener;
   *
   * In TestListener, three Servlets are added using the following methods: -
   * ServletContext.addServlet(String, String); -
   * ServletContext.addServlet(String, Class); -
   * ServletContext.createServlet(Class);
   *
   * In TestListener, three Filters are added using the following methods: -
   * ServletContext.addFilter(String, String); -
   * ServletContext.addFilter(String, Class); -
   * ServletContext.createServlet(Class);
   *
   * In TestListener, three Listeners are added using the following methods: -
   * ServletContext.addListener(Listener Class) -
   * ServletContext.addListener(Listener name) - EventListener listener =
   * ServletContext.createListener(class); ServletContext.addListener(listener);
   * for all three following Listeners: - ServletContextAttributeListener -
   * ServletRequestListener - ServletRequestAttributesListener
   */
  /*
   * @testName: getContextPathTest
   *
   * @assertion_ids: Servlet:JAVADOC:124; Servlet:JAVADOC:258;
   * Servlet:JAVADOC:637; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:679;
   *
   * @test_Strategy: In TestServlet verify that the result from the
   * ServletContext.getServletContextPath call returns the context path.
   *
   * In client verify that all Listeners are added correctly and invoked in the
   * order added.
   */
  @Test
  public void getContextPathTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getContextPathTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddSRListenerClass_INVOKED" + "|AddSRListenerString_INVOKED"
            + "|CreateSRListener_INVOKED" + "|AttributeAddedClass"
            + "|AttributeAddedString");
    invoke();
  }

  /*
   * @testName: testAddServletString
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:668;
   * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
   * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
   * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
   * Servlet:JAVADOC:674; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. create a
   * Servlet by calling ServletContext.addServlet(String, String), 2. mapping
   * the new Servlet programmatically. 3. create a Filter by
   * ServletContext.addFilter(String, String) 4. map the filter to the new
   * Servlet programmatically for FORWARD only 5. client send a request to the
   * new servlet, Verify in client that request goes through and Filter is NOT
   * invoked. Verify in client that all Listeners are added correctly and
   * invoked in the order added.
   */
  @Test
  public void testAddServletString() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/addServletString HTTP/1.1");
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "ADD_FILTER_STRING_INVOKED");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddSRListenerClass_INVOKED" + "|AddSRListenerString_INVOKED"
            + "|CreateSRListener_INVOKED" + "|SCAttributeAddedClass:"
            + "|SCAttributeAddedString:" + "|SCAttributeAdded:");
    invoke();
  }

  /*
   * @testName: testAddFilterString
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:668;
   * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
   * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
   * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
   * Servlet:JAVADOC:674; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. create a
   * Servlet by calling ServletContext.addServlet(String, String), 2. mapping
   * the new Servlet programmatically. 3. create a Filter by
   * ServletContext.addFilter(String, String) 4. map the filter to the new
   * Servlet programmatically for FORWARD only 5. client send a request to
   * another servlet, 6. which then forward to the newly created Servlet Verify
   * in client that request goes through and Filter IS invoked. Verify in client
   * that all Listeners are added correctly and invoked in the order added.
   */
  @Test
  public void testAddFilterString() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testAddFilterString");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddServletString" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|ADD_FILTER_STRING_INVOKED" + "|SCAttributeAddedClass:"
            + "|SCAttributeAddedString:" + "|SCAttributeAdded:");
    invoke();
  }

  /*
   * @testName: testAddServletClass
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
   * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. create a
   * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
   * new Servlet programmatically. 3. create a Filter by
   * ServletContext.addFilter(String, Class) 4. map the filter to the new
   * Servlet programmatically for REQUEST only 5. client send a request to the
   * new servlet, Verify in client that request goes through and Filter is
   * invoked. Verify in client that all Listeners are added correctly and
   * invoked in the order added.
   */
  @Test
  public void testAddServletClass() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/addServletClass HTTP/1.1");
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddServletClass" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|ADD_FILTER_CLASS_INVOKED" + "|SCAttributeAddedClass:"
            + "|SCAttributeAddedString:" + "|SCAttributeAdded:");
    invoke();
  }

  /*
   * @testName: testAddFilterClass
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
   * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. create a
   * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
   * new Servlet programmatically. 3. create a Filter by
   * ServletContext.addFilter(String, Class) 4. map the filter to the new
   * Servlet programmatically for REQUEST only 5. client send a request to
   * another servlet, 6. which then dispatch by include to the newly created
   * Servlet Verify in client that request goes through and Filter IS NOT
   * invoked. Verify in client that all Listeners are added correctly and
   * invoked in the order added.
   */
  @Test
  public void testAddFilterClass() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testAddFilterClass");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "ADD_FILTER_CLASS_INVOKED");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddServletClass" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|SCAttributeAddedClass" + "|SCAttributeAddedString");
    invoke();
  }

  /*
   * @testName: testAddServlet
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
   * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
   * Servlet programmatically. 4. create a Filter instance by
   * ServletContext.createFilter; 5 Add the Filter instance:
   * ServletContext.addFilter(String, Filter) 6. map the filter to the new
   * Servlet programmatically for REQUEST only 7. client send a request to the
   * new servlet, Verify in client that request goes through and Filter is
   * invoked. Verify in client that all Listeners are added correctly and
   * invoked in the order added.
   */
  @Test
  public void testAddServlet() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/createServlet HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "CreateServlet" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|SCAttributeAddedClass:" + "|SCAttributeAddedString:"
            + "|SCAttributeAdded:" + "|CREATE_FILTER_INVOKED");

    invoke();
  }

  /*
   * @testName: testAddFilterForward
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
   * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
   * Servlet programmatically. 4. create a Filter instance by
   * ServletContext.createFilter; 5 Add the Filter instance:
   * ServletContext.addFilter(String, Filter) 6. map the filter to the new
   * Servlet programmatically for REQUEST only 7. client send a request to the
   * new servlet via FORWARD, Verify in client that request does go through and
   * Filter is NOT invoked. Verify in client that all Listeners are added
   * correctly and invoked in the order added.
   */
  @Test
  public void testAddFilterForward() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testCreateFilterForward");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "CREATE_FILTER_INVOKED");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "CreateServlet" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|SCAttributeAddedClass:" + "|SCAttributeAddedString:"
            + "|SCAttributeAdded:");
    invoke();
  }

  /*
   * @testName: testAddFilterInclude
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
   * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
   * Servlet programmatically. 4. create a Filter instance by
   * ServletContext.createFilter; 5 Add the Filter instance:
   * ServletContext.addFilter(String, Filter) 6. map the filter to the new
   * Servlet programmatically for REQUEST only 7. client send a request to the
   * new servlet via INCLUDE, Verify in client that request does go through and
   * Filter is NOT invoked. Verify in client that all Listeners are added
   * correctly and invoked in the order added.
   */
  @Test
  public void testAddFilterInclude() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testCreateFilterInclude");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "CREATE_FILTER_INVOKED");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "CreateServlet" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|SCAttributeAddedClass:" + "|SCAttributeAddedString:"
            + "|SCAttributeAdded:");
    invoke();
  }

  /*
   * @testName: testAddServletNotFound
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
   * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
   * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. create a
   * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
   * new Servlet programmatically to multiple URLs, one of them is used by
   * another Servlet already. 3. create a Filter by
   * ServletContext.addFilter(String, Class) 4. map the filter to the new
   * Servlet programmatically for all DispatcherType 5. client send a request to
   * the new servlet, Verify in client that request does NOT go through and
   * Filter is NOT invoked.
   */
  @Test
  public void testAddServletNotFound() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/addServletNotFound HTTP/1.1");
    TEST_PROPS.setProperty(STATUS_CODE, NOT_FOUND);
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH,
        "AddServletNotFound|ADD_FILTER_NOTFOUND");
    invoke();
  }

  /*
   * @testName: testCreateSRAListener
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, one
   * ServletContextAttributeListener, one ServletRequestListener one
   * ServletRequestAttributeListener are added: 1. Create a Servlet instance
   * using ServletContext.createServlet; 2. Add the Servlet instance:
   * ServletContext.addServlet(String, Servlet), 3. mapping the new Servlet
   * programmatically. 4. create a Filter instance by
   * ServletContext.createFilter; 5 Add the Filter instance:
   * ServletContext.addFilter(String, Filter) 6. map the filter to the new
   * Servlet programmatically for REQUEST only 7. client send a request to
   * another servlet, in which, ServletRequestAttributes are added, then
   * dispatch to the new servlet via FORWARD Verify in client that - create
   * Listener works - request does NOT through and Filter is NOT invoked. - all
   * Listeners are added correctly and invoked in the order added.
   */
  @Test
  public void testCreateSRAListener() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testCreateSRAListener");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "CREATE_FILTER_INVOKED");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "AddServletClass" + "|AddSRListenerClass_INVOKED"
            + "|AddSRListenerString_INVOKED" + "|CreateSRListener_INVOKED"
            + "|SCAttributeAddedClass:" + "|SCAttributeAddedString:"
            + "|SCAttributeAdded:" + "|SRAttributeAddedClass:"
            + "|SRAttributeAddedString:" + "|SRAttributeAdded:");
    invoke();
  }

  /*
   * @testName: negativeCreateTests
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:678;
   * Servlet:JAVADOC:680; Servlet:JAVADOC:682; Servlet:JAVADOC:694;
   * Servlet:JAVADOC:671.10; Servlet:JAVADOC:672.10; Servlet:JAVADOC:673.10;
   *
   * @test_Strategy: 1. Create a Servlet which throws ServletException at init;
   * 2. Create a Filter which throws ServletException at init 3. Create a
   * EventListener which throws NullPointerException at init 4. Create a
   * ServletContextListener
   *
   * Create another ServletContextListener, in which: 5. Call
   * ServletContext.createFilter(Filter) which should fail; 6. Call
   * ServletContext.createServlet(Servlet) which fails 7. Call
   * ServletContext.createListener(EventListener) which fails 8. Call
   * ServletContext.addListener(ServletContextListener.class) which fails 9.
   * Call ServletContext.addListener(ServletContextListenerClassName) which
   * fails 10. Call ServletContext.createListener(ServletContextListener.class)
   * which fails 11. Call ServletContext.setInitParameter to pass information
   * about status on 4-6 12. In another Servlet, get all information stored in
   * ServletContext InitParameter 13. Send a request to the new Servlet Verify
   * that - createServlet failed accordingly; - createFilter failed accordingly;
   * - createListener failed accordingly; - addListener failed accordingly; -
   * setInitParameter works properly
   */
  @Test
  public void negativeCreateTests() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeCreateTests");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "SERVLET_TEST=TRUE" + "|FILTER_TEST=TRUE" + "|LISTENER_TEST=TRUE"
            + "|SCC_LISTENER_TEST=TRUE" + "|SCS_LISTENER_TEST=TRUE"
            + "|CSC_|LISTENER_TEST=TRUE");
    invoke();
  }

  /*
   * @testName: duplicateServletTest1
   *
   * @assertion_ids: Servlet:JAVADOC:675.5; Servlet:JAVADOC:676.5;
   *
   * @test_Strategy: 1. Create a Servlet Servlet, define it in web.xml; 2.
   * Create a ServletContextListener, in which: 3. try to add the Servlet again
   * by calling ServletContext.addServlet(servletName, Servlet.class) 6. Verify
   * that the Servlet can be invoked as defined in web.xml1.
   */
  @Test
  public void duplicateServletTest1() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/DuplicateServletClass HTTP/1.0");
    TEST_PROPS.setProperty(SEARCH_STRING, "DuplicateServletClass");
    invoke();
  }

  /*
   * @testName: duplicateServletTest2
   *
   * @assertion_ids: Servlet:JAVADOC:674.5;
   *
   * @test_Strategy: 1. Create a Servlet Servlet, define it in web.xml; 2.
   * Create a ServletContextListener, in which: 3. try to add the Servlet again
   * by calling ServletContext.addServlet(servletName, "Servlet.class") 4.
   * Verify that the Servlet can be invoked as defined in web.xm1.
   */
  @Test
  public void duplicateServletTest2() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/DuplicateServletString HTTP/1.0");
    TEST_PROPS.setProperty(SEARCH_STRING, "DuplicateServletString");
    invoke();
  }

  /*
   * @testName: duplicateServletTest3
   *
   * @assertion_ids: Servlet:JAVADOC:674.5; Servlet:JAVADOC:675.5;
   * Servlet:JAVADOC:676.5;
   *
   * @test_Strategy: 1. Create a Servlet Servlet1, define it in web.xml; 2.
   * Create a Servlet Servlet2, define it in web.xml; 3. Create a
   * ServletContextListener, in which: 4. try to add the Servlet1 again by
   * calling ServletContext.addServlet(servletName, Servlet.class) 4. try to add
   * the Servlet2 again by calling ServletContext.addServlet(servletName,
   * "Servlet.class") 4. Verify null is returned in both cases.
   */
  @Test
  public void duplicateServletTest3() throws Exception {
    TEST_PROPS.setProperty(APITEST, "duplicateServletTest3");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "DUPLICATEC_SERVLET_TEST=TRUE" + "|DUPLICATES_SERVLET_TEST=TRUE");
    invoke();
  }

  /*
   * @testName: duplicateFilterTest
   *
   * @assertion_ids: Servlet:JAVADOC:668.4; Servlet:JAVADOC:669.4;
   *
   * @test_Strategy: 1. Create a Filter Filter1, define it in web.xml; 2. Create
   * a Filter Filter2, define it in web.xml; 3. Create a ServletContextListener,
   * in which: 4. try to add Filter1 again by calling
   * ServletContext.addFilter(FilterName, Filter.class) 5. try to add Filter2
   * again by calling ServletContext.addFilter(FilterName,,"Filter.class") 6.
   * Verify that both Filter can be invoked as defined in web.xml
   */
  @Test
  public void duplicateFilterTest() throws Exception {
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/FilterTestServlet HTTP/1.0");
    TEST_PROPS.setProperty(SEARCH_STRING, "FilterTestServlet");
    invoke();
  }

  /*
   * @testName: duplicateFilterTest1
   *
   * @assertion_ids: Servlet:JAVADOC:668.4; Servlet:JAVADOC:669.4;
   *
   * @test_Strategy: 1. Create a Filter Filter1, define it in web.xml; 2. Create
   * a Filter Filter2, define it in web.xml; 3. Create a ServletContextListener,
   * in which: 4. try to add Filter1 again by calling
   * ServletContext.addFilter(FilterName, Filter.class) 5. try to add Filter2
   * again by calling ServletContext.addFilter(FilterName,,"Filter.class") 6.
   * Verify that null is returned in both cases
   */
  @Test
  public void duplicateFilterTest1() throws Exception {
    TEST_PROPS.setProperty(APITEST, "duplicateFilterTest1");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "DUPLICATEC_FILTER_TEST=TRUE" + "|DUPLICATES_FILTER_TEST=TRUE");
    invoke();
  }

  /*
   * @testName: getEffectiveMajorVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:685;
   *
   * @test_Strategy: Create a Servlet, in which call
   * ServletContext.getEffectiveMajorVersion() Verify that 5 is returned.
   */
  @Test
  public void getEffectiveMajorVersionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getEffectiveMajorVersionTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "EFFECTIVEMAJORVERSION=5;");
    invoke();
  }

  /*
   * @testName: getEffectiveMinorVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:686;
   *
   * @test_Strategy: Create a Servlet, in which call
   * ServletContext.getEffectiveMinorVersion() Verify that 0 is returned.
   */
  @Test
  public void getEffectiveMinorVersionTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getEffectiveMinorVersionTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "EFFECTIVEMINORVERSION=0;");
    invoke();
  }

  /*
   * @testName: getDefaultSessionTrackingModes
   *
   * @assertion_ids: Servlet:JAVADOC:684;
   *
   * @test_Strategy: Create a Servlet, in which call
   * ServletContext.getDefaultSessionTrackingModes() Verify it works.
   */
  @Test
  public void getDefaultSessionTrackingModes() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getDefaultSessionTrackingModes");
    invoke();
  }

  /*
   * @testName: sessionTrackingModesValueOfTest
   *
   * @assertion_ids: Servlet:JAVADOC:747;
   *
   * @test_Strategy: Create a Servlet, verify SessionTrackingModes.valueOf()
   * works
   */
  @Test
  public void sessionTrackingModesValueOfTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "sessionTrackingModesValueOfTest");
    invoke();
  }

  /*
   * @testName: sessionTrackingModesValuesTest
   *
   * @assertion_ids: Servlet:JAVADOC:748;
   *
   * @test_Strategy: Create a Servlet, verify SessionTrackingModes.values()
   * works
   */
  @Test
  public void sessionTrackingModesValuesTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "sessionTrackingModesValuesTest");
    invoke();
  }
}
