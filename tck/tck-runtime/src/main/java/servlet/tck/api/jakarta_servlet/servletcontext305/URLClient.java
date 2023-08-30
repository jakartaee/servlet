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
package servlet.tck.api.jakarta_servlet.servletcontext305;

import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterNotFound;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSRAttributeListenerClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSRAttributeListenerString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSRListenerClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddSRListenerString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletNotFound;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadListener;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadServlet;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateSCAttributeListener;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateSRAttributeListener;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateSRListener;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateServlet;
import servlet.tck.api.jakarta_servlet.servletcontext301.AddGenericEventListenerClass;
import servlet.tck.api.jakarta_servlet.servletcontext304.CreateGenericEventListener;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext305_web.war")
            .addAsResource(URLClient.class.getResource("jakarta.servlet.ServletContainerInitializer"),
                    "META-INF/services/jakarta.servlet.ServletContainerInitializer")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(AddSCListenerClass.class, AddSCListenerString.class, CreateSCListener.class,
                    TestContainerInitializer.class, TestServlet.class, AddServletString.class, AddFilterString.class,
                    AddFilterClass.class, AddServletClass.class, CreateServlet.class, CreateFilter.class, AddServletNotFound.class,
                    AddFilterNotFound.class, AddSCAttributeListenerClass.class, CreateSCAttributeListener.class,
                    AddSRListenerClass.class, CreateSRListener.class, AddSRAttributeListenerClass.class, CreateSRAttributeListener.class,
                    BadServlet.class, BadFilter.class, BadListener.class, AddGenericEventListenerClass.class, CreateGenericEventListener.class,
                    AddServletString.class, AddFilterString.class, AddSCAttributeListenerString.class, AddSRListenerString.class,
                    AddSRAttributeListenerString.class)
            .setWebXML(URLClient.class.getResource("servlet_js_servletcontext305_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * Test setup; In Deployment Descriptor, define a Servlet TestServlet; Create
   * a SevletContextInitializer,
   *
   * In SevletContextInitializer, three Servlets are added using the following
   * methods: - ServletContext.addServlet(String, String); -
   * ServletContext.addServlet(String, Class); -
   * ServletContext.createServlet(Class);
   *
   * In SevletContextInitializer, three Filters are added using the following
   * methods: - ServletContext.addFilter(String, String); -
   * ServletContext.addFilter(String, Class); -
   * ServletContext.createServlet(Class);
   *
   * In SevletContextInitializer, four Listeners are added using the following
   * methods: - ServletContext.addListener(Listener Class) -
   * ServletContext.addListener(Listener name) - EventListener listener =
   * ServletContext.createListener(class); ServletContext.addListener(listener);
   * Test Listeners implements one of all following liseners: -
   * ServletContextAttributeListener - ServletRequestListener -
   * ServletRequestAttributesListener - ServletContextListener
   *
   * Package jakarta.servlet.ServletContextInitializer appropriately
   */

  /*
   * @testName: testAddServletString
   *
   * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:668;
   * Servlet:JAVADOC:668.3; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6; Servlet:JAVADOC:671.7;
   * Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6; Servlet:JAVADOC:672.7;
   * Servlet:JAVADOC:672.9; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6; Servlet:JAVADOC:673.7;
   * Servlet:JAVADOC:673.9; Servlet:JAVADOC:674; Servlet:JAVADOC:674.4;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.4; Servlet:JAVADOC:696;
   * Servlet:SPEC:259; Servlet:SPEC:259.1; Servlet:SPEC:259.3;
   * Servlet:SPEC:259.4; Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. create a
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
   * Servlet:JAVADOC:668.3; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6; Servlet:JAVADOC:671.7;
   * Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6; Servlet:JAVADOC:672.7;
   * Servlet:JAVADOC:672.9; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6; Servlet:JAVADOC:673.7;
   * Servlet:JAVADOC:673.9; Servlet:JAVADOC:674; Servlet:JAVADOC:674.4;
   * Servlet:JAVADOC:675; Servlet:JAVADOC:675.4; Servlet:JAVADOC:676;
   * Servlet:JAVADOC:676.4; Servlet:JAVADOC:679; Servlet:JAVADOC:679.1;
   * Servlet:JAVADOC:679.4; Servlet:JAVADOC:696; Servlet:SPEC:259;
   * Servlet:SPEC:259.1; Servlet:SPEC:259.3; Servlet:SPEC:259.4;
   * Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. create a
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
   * Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.6; Servlet:JAVADOC:671.7;
   * Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6; Servlet:JAVADOC:672.7;
   * Servlet:JAVADOC:672.9; Servlet:JAVADOC:676; Servlet:JAVADOC:676.4;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:696; Servlet:SPEC:259; Servlet:SPEC:259.1;
   * Servlet:SPEC:259.3; Servlet:SPEC:259.4; Servlet:SPEC:259.6;
   * Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. create a
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
   * Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.6; Servlet:JAVADOC:671.7;
   * Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6; Servlet:JAVADOC:672.7;
   * Servlet:JAVADOC:672.9; Servlet:JAVADOC:676; Servlet:JAVADOC:676.4;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:696; Servlet:SPEC:259; Servlet:SPEC:259.1;
   * Servlet:SPEC:259.3; Servlet:SPEC:259.4; Servlet:SPEC:259.6;
   * Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. create a
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
   * Servlet:JAVADOC:670; Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.1;
   * Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6;
   * Servlet:JAVADOC:671.7; Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1;
   * Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6;
   * Servlet:JAVADOC:672.7; Servlet:JAVADOC:672.9; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:675.4; Servlet:JAVADOC:677; Servlet:JAVADOC:677.1;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:681; Servlet:JAVADOC:696; Servlet:SPEC:259;
   * Servlet:SPEC:259.1; Servlet:SPEC:259.3; Servlet:SPEC:259.4;
   * Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. Create a
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
   * Servlet:JAVADOC:670; Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.1;
   * Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6;
   * Servlet:JAVADOC:671.7; Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1;
   * Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6;
   * Servlet:JAVADOC:672.7; Servlet:JAVADOC:672.9; Servlet:JAVADOC:673.1;
   * Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6;
   * Servlet:JAVADOC:673.7; Servlet:JAVADOC:673.9; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:675.4; Servlet:JAVADOC:677; Servlet:JAVADOC:677.1;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   *
   * Servlet:JAVADOC:681; Servlet:JAVADOC:696; Servlet:SPEC:259;
   * Servlet:SPEC:259.1; Servlet:SPEC:259.3; Servlet:SPEC:259.4;
   * Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. Create a
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
   * Servlet:JAVADOC:670; Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.1;
   * Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6;
   * Servlet:JAVADOC:671.7; Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1;
   * Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6;
   * Servlet:JAVADOC:672.7; Servlet:JAVADOC:672.9; Servlet:JAVADOC:673.1;
   * Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6;
   * Servlet:JAVADOC:673.7; Servlet:JAVADOC:673.9; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:675.4; Servlet:JAVADOC:677; Servlet:JAVADOC:677.1;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:681; Servlet:JAVADOC:696; Servlet:SPEC:259;
   * Servlet:SPEC:259.1; Servlet:SPEC:259.3; Servlet:SPEC:259.4;
   * Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. Create a
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
   * Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
   * Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6; Servlet:JAVADOC:671.7;
   * Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
   * Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6; Servlet:JAVADOC:672.7;
   * Servlet:JAVADOC:6712.9; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
   * Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6; Servlet:JAVADOC:673.7;
   * Servlet:JAVADOC:673.9; Servlet:JAVADOC:676; Servlet:JAVADOC:676.4;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:696; Servlet:SPEC:259; Servlet:SPEC:259.1;
   * Servlet:SPEC:259.3; Servlet:SPEC:259.4; Servlet:SPEC:259.6;
   * Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, 1. create a
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
   * Servlet:JAVADOC:670; Servlet:JAVADOC:670.3; Servlet:JAVADOC:671.1;
   * Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3; Servlet:JAVADOC:671.6;
   * Servlet:JAVADOC:671.7; Servlet:JAVADOC:671.9; Servlet:JAVADOC:672.1;
   * Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3; Servlet:JAVADOC:672.6;
   * Servlet:JAVADOC:672.7; Servlet:JAVADOC:6712.9; Servlet:JAVADOC:673.1;
   * Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3; Servlet:JAVADOC:673.6;
   * Servlet:JAVADOC:673.7; Servlet:JAVADOC:673.9; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:675.4; Servlet:JAVADOC:677; Servlet:JAVADOC:677.1;
   * Servlet:JAVADOC:679; Servlet:JAVADOC:679.1; Servlet:JAVADOC:679.4;
   * Servlet:JAVADOC:681; Servlet:JAVADOC:696; Servlet:SPEC:259;
   * Servlet:SPEC:259.1; Servlet:SPEC:259.3; Servlet:SPEC:259.4;
   * Servlet:SPEC:259.6; Servlet:SPEC:259.7;
   *
   * @test_Strategy: Create a ServletContextInitializer, in which, one
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
   * Servlet:JAVADOC:679.4; Servlet:JAVADOC:680; Servlet:JAVADOC:682;
   * Servlet:JAVADOC:694; Servlet:SPEC:259; Servlet:SPEC:259.1;
   * Servlet:SPEC:259.3; Servlet:SPEC:259.4; Servlet:SPEC:259.6;
   * Servlet:SPEC:259.7;
   *
   * @test_Strategy: 1. Create a Servlet which throws ServletException at init;
   * 2. Create a Filter which throws ServletException at init 3. Create a
   * EventListener which throws NullPointerException at init 4. Create a
   * EventListener that does not implement any of the following: -
   * ServletContextAttributeListener - ServletRequestListener -
   * ServletRequestAttributesListener - ServletContextListener -
   * HttpSessionListener - HttpSessionAttributeListener
   *
   * Create a ServletContextInitializer, in which: 5. Call
   * ServletContext.createFilter(BadFilter) which should fail; 6. Call
   * ServletContext.createServlet(BadServlet) which fails 7. Call
   * ServletContext.createListener(BadEventListener) which fails 8. Call
   * ServletContext.setInitParameter to pass information about status on 4-6 9.
   * Call ServletContext.addListener(GenericEventListener) which fails 10. Call
   * ServletContext.createListener(GenericEventListener) which fails 11. In
   * another Servlet, get all information stored in ServletContext InitParameter
   * 12. Send a request to the new Servlet Verify that - createServlet failed
   * accordingly; - createFilter failed accordingly; - createListener failed
   * accordingly; - addListener failed accordingly; - setInitParameter works
   * properly
   */
  @Test
  public void negativeCreateTests() throws Exception {
    TEST_PROPS.setProperty(APITEST, "negativeCreateTests");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "SERVLET_TEST=TRUE" + "|FILTER_TEST=TRUE" + "|LISTENER_TEST=TRUE"
            + "|GC_LISTENER_TEST=TRUE" + "|GS_LISTENER_TEST=TRUE"
            + "|CGC_LISTENER_TEST=TRUE");

    invoke();
  }
}
