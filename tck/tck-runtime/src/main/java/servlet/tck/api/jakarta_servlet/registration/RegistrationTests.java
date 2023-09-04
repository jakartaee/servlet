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
 * $Id: URLClient.java 58928 2009-08-03 21:08:09Z djiao $
 */
package servlet.tck.api.jakarta_servlet.registration;

import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterNotFound;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletClass;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletNotFound;
import servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadListener;
import servlet.tck.api.jakarta_servlet.servletcontext30.BadServlet;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateFilter;
import servlet.tck.api.jakarta_servlet.servletcontext30.CreateServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTests extends AbstractUrlClient {


  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_js_registration_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestListener.class, TestServlet.class, AddServletString.class, AddFilterString.class,
                    AddServletClass.class, AddFilterClass.class, CreateServlet.class, CreateFilter.class,
                    AddServletNotFound.class, AddFilterNotFound.class, BadServlet.class, BadFilter.class,
                    BadListener.class)
            .setWebXML(RegistrationTests.class.getResource("servlet_js_registration_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * Test strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), Add another Servlet
   * ServletContext.addServlet(String, String), Add the third Servlet
   * ServletContext.addServlet(String, Servlet), Add the fourth Servlet
   * ServletContext.createServlet(Class), 2. mapping the new Servlets
   * programmatically. 3. Create a FilterRegistration
   * ServletContext.addFilter(String, Class) Create another FilterRegistration
   * ServletContext.addFilter(String, String), Create the third
   * FilterRegistration ServletContext.addFilter(String, Filter), Create the
   * fourth FilterRegistration ServletContext.createFilter(Class), 4. Map all
   * FilterRegistration calling addMappingForServletNames 5. Store server side
   * information returned in ServletContextAttribute 6. Define a servlet in
   * web.xml; 7. client send a request to the last servlet to get server
   * information
   */
  /*
   * @testName: servletURLMappingTest
   *
   * @assertion_ids: Servlet:JAVADOC:664; Servlet:JAVADOC:676;
   * Servlet:JAVADOC:696; Servlet:JAVADOC:697;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), 2. mapping the new
   * Servlet programmatically. 3. Store information returned by
   * jakarta.servlet.ServletRegistration.getMappings in ServletContextAttribute 4.
   * client send a request to another servlet to get the information Verify in
   * client that getMapping works
   */
  @Test
  public void servletURLMappingTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "servletURLMappingTest");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "URL_MAPPING_TEST" + "|/ADDSERVLETCLASS" + "|/SECONDADDSERVLETCLASS"
            + "|/THIRDADDSERVLETCLASS" + "|/ADDSERVLETCLASS/*");
    invoke();
  }

  /*
   * @testName: filterServletMappingTest
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:655;
   * Servlet:JAVADOC:657; Servlet:JAVADOC:668; Servlet:JAVADOC:668.3;
   * Servlet:JAVADOC:669; Servlet:JAVADOC:669.3; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:670.3; Servlet:JAVADOC:677; Servlet:JAVADOC:677.1;
   * Servlet:JAVADOC:694;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * FilterRegistration ServletContext.addFilter(String, Class) 2. Create
   * another FilterRegistration ServletContext.addFilter(String, String), 3.
   * Create the third FilterRegistration ServletContext.addServlet(String,
   * Filter), 4. Map all FilterRegistration calling addMappingForServletNames 5.
   * Store information in ServletContextAttribute:
   * jakarta.servlet.FilterRegistration.getServletNameMappings 6. client send a
   * request to another servlet to get the information Verify in client that
   * getServletNameMappings works
   */
  @Test
  public void filterServletMappingTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "filterServletMappingTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "FILTER_SERVLET_MAPPING" + "|AddServletString" + "|AddServletClass"
            + "|AddServletNotFound" + "|CreateServlet");
    invoke();
  }

  /*
   * @testName: servletRegistrationsTest
   *
   * @assertion_ids: Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:681; Servlet:JAVADOC:692;
   * Servlet:JAVADOC:694; Servlet:JAVADOC:696;
   *
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), 2. Add another Servlet
   * ServletContext.addServlet(String, String), 3. Add the third Servlet
   * ServletContext.addServlet(String, Servlet), 4. Add the fourth Servlet
   * ServletContext.createServlet(Class), 5. Define a servlet in web.xml; 6.
   * Store information returned in ServletContextAttribute by
   * jakarta.servlet.ServletRegistration.getServletRegistrations 7. client send a
   * request to another servlet to get the information Verify in client that
   * getServletRegistrations works
   */
  @Test
  public void servletRegistrationsTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletRegistrationsTest");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "SERVLET_REGISTRATIONS:" + "|ADDSERVLETCLASS" + "|ADDSERVLETNOTFOUND"
            + "|ADDSERVLETSTRING" + "|CREATESERVLET" + "|TESTSERVLET");
    invoke();
  }

  /*
   * @testName: getServletRegistrationTest
   *
   * @assertion_ids: Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:681; Servlet:JAVADOC:691;
   * Servlet:JAVADOC:694; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), 2. Add another Servlet
   * ServletContext.addServlet(String, String), 3. Add the third Servlet
   * ServletContext.addServlet(String, Servlet), 4. Add the fourth Servlet
   * ServletContext.createServlet(Class), 5. Define a servlet in web.xml; 6.
   * Store information returned in ServletContextAttribute by
   * jakarta.servlet.ServletRegistration.getServletRegistration(String) 7. client
   * send a request to another servlet to get the information Verify in client
   * that getServletRegistration(String) works
   */
  @Test
  public void getServletRegistrationTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getServletRegistrationTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "SERVLET_REGISTRATION:" + "|ADDSERVLETSTRING" + "|ADDSERVLETCLASS"
            + "|ADDSERVLETNOTFOUND" + "|CREATESERVLET" + "|TESTSERVLET");
    invoke();
  }

  /*
   * @testName: getFilterRegistrationsTest
   *
   * @assertion_ids: Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:681; Servlet:JAVADOC:689;
   * Servlet:JAVADOC:694; Servlet:JAVADOC:696;
   *
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), 2. Add another Servlet
   * ServletContext.addServlet(String, String), 3. Add the third Servlet
   * ServletContext.addServlet(String, Servlet), 4. Add the fourth Servlet
   * ServletContext.createServlet(Class), 5. Define a servlet in web.xml; 6.
   * Store information returned in ServletContextAttribute by
   * jakarta.servlet.ServletRegistration.getFilterRegistrations 7. client send a
   * request to another servlet to get the information Verify in client that
   * getFilterRegistrations works
   */
  @Test
  public void getFilterRegistrationsTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getFilterRegistrationsTest");
    TEST_PROPS.setProperty(UNORDERED_SEARCH_STRING,
        "FILTER_REGISTRATIONS:" + "|AddFilterClass" + "|AddFilterNotFound"
            + "|AddFilterString" + "|CreateFilter");
    invoke();
  }

  /*
   * @testName: getFilterRegistrationTest
   *
   * @assertion_ids: Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:681; Servlet:JAVADOC:688;
   * Servlet:JAVADOC:694; Servlet:JAVADOC:696;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Add the
   * Servlet ServletContext.addServlet(String, Class), 2. Add another Servlet
   * ServletContext.addServlet(String, String), 3. Add the third Servlet
   * ServletContext.addServlet(String, Servlet), 4. Add the fourth Servlet
   * ServletContext.createServlet(Class), 5. Define a servlet in web.xml; 6.
   * Store information returned in ServletContextAttribute by
   * jakarta.servlet.ServletRegistration.getFilterRegistration(String) 7. client
   * send a request to another servlet to get the information Verify in client
   * that getFilterRegistration(String) works
   */
  @Test
  public void getFilterRegistrationTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getFilterRegistrationTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "FILTER_REGISTRATION:" + "|AddFilterString" + "|AddFilterClass"
            + "|AddFilterNotFound" + "|CreateFilter");
    invoke();
  }

  /*
   * @testName: getRegistrationNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:662;
   * Servlet:JAVADOC:668; Servlet:JAVADOC:669; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:674; Servlet:JAVADOC:675; Servlet:JAVADOC:676;
   * Servlet:JAVADOC:677; Servlet:JAVADOC:681; Servlet:JAVADOC:694;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * ServletRegistration ServletContext.addServlet(String, Class) 2. Create
   * another ServletRegistration ServletContext.addServlet(String, String), 3.
   * Create the third ServletRegistration ServletContext.addServlet(String,
   * Servlet), 4. Add the fourth Servlet ServletContext.createServlet(Class), 5.
   * Create a FilterRegistration ServletContext.addFilter(String, Class) 6.
   * Create another FilterRegistration ServletContext.addFilter(String, String),
   * 7. Create the third FilterRegistration ServletContext.addFilter(String,
   * Filter), 8. Create the fourth FilterRegistration
   * ServletContext.CreateFilter(Class), 9. Store information returned in
   * ServletContextAttribute by jakarta.servlet.Registration.getName() 10. client
   * send a request to another servlet to get the information Verify in client
   * that jakarta.servlet.Registration.getName() works
   */
  @Test
  public void getRegistrationNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRegistrationNameTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "REGISTRION_NAME:" + "|ADDSERVLETSTRING" + "|ADDFILTERSTRING"
            + "|ADDSERVLETCLASS" + "|ADDFILTERCLASS" + "|CREATESERVLET"
            + "|CREATEFILTER" + "|ADDSERVLETNOTFOUND" + "|ADDFILTERNOTFOUND");
    invoke();
  }

  /*
   * @testName: getRegistrationClassNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:659;
   * Servlet:JAVADOC:668; Servlet:JAVADOC:669; Servlet:JAVADOC:670;
   * Servlet:JAVADOC:674; Servlet:JAVADOC:675; Servlet:JAVADOC:676;
   * Servlet:JAVADOC:677; Servlet:JAVADOC:681; Servlet:JAVADOC:694;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * ServletRegistration ServletContext.addServlet(String, Class) 2. Create
   * another ServletRegistration ServletContext.addServlet(String, String), 3.
   * Create the third ServletRegistration ServletContext.addServlet(String,
   * Servlet), 4. Add the fourth Servlet ServletContext.createServlet(Class), 5.
   * Create a FilterRegistration ServletContext.addFilter(String, Class) 6.
   * Create another FilterRegistration ServletContext.addFilter(String, String),
   * 7. Create the third FilterRegistration ServletContext.addFilter(String,
   * Filter), 8. Create the fourth FilterRegistration
   * ServletContext.CreateFilter(Class), 9. Store information returned in
   * ServletContextAttribute by jakarta.servlet.Registration.getName() 10. client
   * send a request to another servlet to get the information Verify in client
   * that jakarta.servlet.Registration.getClassName() works
   */
  @Test
  public void getRegistrationClassNameTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRegistrationClassNameTest");
    TEST_PROPS.setProperty(SEARCH_STRING, "REGISTRATION_CLASS_NAME:"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDSERVLETSTRING"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDFILTERSTRING"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDSERVLETCLASS"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDFILTERCLASS"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.CREATESERVLET"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.CREATEFILTER"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDSERVLETNOTFOUND"
        + "|SERVLET.TCK.API.JAKARTA_SERVLET.SERVLETCONTEXT30.ADDFILTERNOTFOUND");
    invoke();
  }

  /*
   * @testName: getRegistrationInitParameterTest
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:660;
   * Servlet:JAVADOC:663; Servlet:JAVADOC:668; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:677; Servlet:JAVADOC:681;
   * Servlet:JAVADOC:694;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * ServletRegistration ServletContext.addServlet(String, Class) 2. Create
   * another ServletRegistration ServletContext.addServlet(String, String), 3.
   * Create the third ServletRegistration ServletContext.addServlet(String,
   * Servlet), 4. Create a FilterRegistration ServletContext.addFilter(String,
   * Class) 5. Create another FilterRegistration
   * ServletContext.addFilter(String, String), 6. Create the third
   * FilterRegistration ServletContext.addFilter(String, Filter), 7. Call
   * setInitParameter(String, String) on all above Registratyion Object; 8.
   * Store information returned in ServletContextAttribute by
   * jakarta.servlet.Registration.getInitParameter(String) 9. client send a
   * request to another servlet to get the information Verify in client that
   * jakarta.servlet.Registration.getInitParameter(String) works
   */
  @Test
  public void getRegistrationInitParameterTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRegistrationInitParameterTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "REGISTRATION_INIT_PARAMETER:" + "|AddFilterString"
            + "|AddServletString" + "|AddFilterClass" + "|AddServletClass"
            + "|CreateFilter" + "|CreateServlet" + "|AddFilterNotFound"
            + "|AddServletNotFound");
    invoke();
  }

  /*
   * @testName: getRegistrationInitParametersTest
   *
   * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:661;
   * Servlet:JAVADOC:664; Servlet:JAVADOC:668; Servlet:JAVADOC:669;
   * Servlet:JAVADOC:670; Servlet:JAVADOC:674; Servlet:JAVADOC:675;
   * Servlet:JAVADOC:676; Servlet:JAVADOC:677; Servlet:JAVADOC:681;
   * Servlet:JAVADOC:694;
   *
   * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
   * ServletRegistration ServletContext.addServlet(String, Class) 2. Create
   * another ServletRegistration ServletContext.addServlet(String, String), 3.
   * Create the third ServletRegistration ServletContext.addServlet(String,
   * Servlet), 4. Create a FilterRegistration ServletContext.addFilter(String,
   * Class) 5. Create another FilterRegistration
   * ServletContext.addFilter(String, String), 6. Create the third
   * FilterRegistration ServletContext.addFilter(String, Filter), 7. Call
   * setInitParameters(Map) on all above Registratyion Object; 8. Store
   * information returned in ServletContextAttribute by
   * jakarta.servlet.Registration.getInitParameters() 9. client send a request to
   * another servlet to get the information Verify in client that
   * jakarta.servlet.Registration.getInitParameters() works
   */
  @Test
  public void getRegistrationInitParametersTest() throws Exception {
    TEST_PROPS.setProperty(APITEST, "getRegistrationInitParametersTest");
    TEST_PROPS.setProperty(SEARCH_STRING,
        "REGISTRATION_INIT_PARAMETERS:"
            + "|Filter=No|FilterName=AddFilterString|Servlet=Yes"
            + "|DISPATCH=FORWARD|ServletName=AddServletString"
            + "|Filter=Yes|FilterName=AddFilterString|Servlet=No"
            + "|DISPATCH=FORWARD|ServletName=AddServletString"
            + "|Filter=No|FilterName=AddFilterClass|Servlet=Yes"
            + "|DISPATCH=REQUEST|ServletName=AddServletClass"
            + "|Filter=Yes|FilterName=AddFilterClass|Servlet=No"
            + "|DISPATCH=REQUEST|ServletName=AddServletClass"
            + "|Filter=No|FilterName=CreateFilter|Servlet=Yes"
            + "|DISPATCH=REQUEST|ServletName=CreateServlet"
            + "|Filter=Yes|FilterName=CreateFilter|Servlet=No"
            + "|DISPATCH=REQUEST|ServletName=CreateServlet"
            + "|Filter=No|FilterName=AddFilterNotFound|Servlet=Yes"
            + "|DISPATCH=ALL|ServletName=AddServletNotFound"
            + "|Filter=Yes|FilterName=AddFilterNotFound|Servlet=No"
            + "|DISPATCH=ALL|ServletName=AddServletNotFound");
    invoke();
  }
}
