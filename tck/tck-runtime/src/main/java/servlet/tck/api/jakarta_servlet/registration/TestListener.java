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
package servlet.tck.api.jakarta_servlet.registration;

import java.util.Collection;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.Registration;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.tck.api.jakarta_servlet.servletcontext30.*;

public class TestListener implements ServletContextListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestListener.class);

  /**
   * Receives notification that the web application initialization process is
   * starting.
   *
   * @param sce
   *          The servlet context event
   */
  public void contextInitialized(ServletContextEvent sce) {
    ServletContext context = sce.getServletContext();

    final String addServletName1 = "AddServletString";
    final String addServletName2 = "AddServletClass";
    final String addServletName3 = "CreateServlet";
    final String addServletName4 = "AddServletNotFound";

    final String addFilterName1 = "AddFilterString";
    final String addFilterName2 = "AddFilterClass";
    final String addFilterName3 = "CreateFilter";
    final String addFilterName4 = "AddFilterNotFound";

    final String[] param_names = { "Filter", "FilterName", "Servlet",
        "DISPATCH", "ServletName" };

    /*
     * Add Servlet AddServletString
     */
    ServletRegistration srString = context.addServlet(addServletName1,
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString");
    srString.addMapping("/addServletString");
    srString.setInitParameter("FILTER", addFilterName1);
    Map<String, String> params = new HashMap<>();
    params.put("Filter", "No");
    params.put("Servlet", "Yes");
    params.put("FilterName", addFilterName1);
    params.put("ServletName", addServletName1);
    params.put("DISPATCH", DispatcherType.FORWARD.toString());
    srString.setInitParameters(params);
    params.clear();

    FilterRegistration frString = context.addFilter(addFilterName1,
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString");
    frString.addMappingForServletNames(EnumSet.of(DispatcherType.FORWARD), true,
        addServletName1);
    frString.setInitParameter("SERVLET", addServletName1);
    params.put("Filter", "Yes");
    params.put("Servlet", "No");
    params.put("FilterName", addFilterName1);
    params.put("ServletName", addServletName1);
    params.put("DISPATCH", DispatcherType.FORWARD.toString());
    frString.setInitParameters(params);
    params.clear();

    /*
     * Add Servlet AddServletClass
     */
    ServletRegistration srClass = context.addServlet(addServletName2,
        AddServletClass.class);
    srClass.addMapping("/addServletClass", "/SecondaddServletClass",
        "/ThirdAddServletClass", "/AddServletClass/*");
    srClass.setInitParameter("FILTER", addFilterName2);
    params.put("Filter", "No");
    params.put("Servlet", "Yes");
    params.put("FilterName", addFilterName2);
    params.put("ServletName", addServletName2);
    params.put("DISPATCH", DispatcherType.REQUEST.toString());
    srClass.setInitParameters(params);
    params.clear();

    FilterRegistration frClass = context.addFilter(addFilterName2,
        AddFilterClass.class);
    frClass.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true,
        addServletName2);
    frClass.setInitParameter("SERVLET", addServletName2);
    params.put("Filter", "Yes");
    params.put("Servlet", "No");
    params.put("FilterName", addFilterName2);
    params.put("ServletName", addServletName2);
    params.put("DISPATCH", DispatcherType.REQUEST.toString());
    frClass.setInitParameters(params);
    params.clear();

    /*
     * Add Servlet CreateServlet
     */
    ServletRegistration srServlet = null;
    FilterRegistration frFilter = null;
    try {
      Servlet servlet3 = context.createServlet(
          CreateServlet.class);
      srServlet = context.addServlet(addServletName3, servlet3);
      srServlet.addMapping("/createServlet", "/SecondCreateServlet",
          "/ThirdCreateServlet");
      srServlet.setInitParameter("FILTER", addFilterName3);
      params.put("Filter", "No");
      params.put("Servlet", "Yes");
      params.put("FilterName", addFilterName3);
      params.put("ServletName", addServletName3);
      params.put("DISPATCH", DispatcherType.REQUEST.toString());
      srServlet.setInitParameters(params);
      params.clear();

      Filter filter3 = context.createFilter(
          CreateFilter.class);
      frFilter = context.addFilter(addFilterName3, filter3);
      frFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),
          true, addServletName3);
      frFilter.setInitParameter("SERVLET", addServletName3);
      params.put("Filter", "Yes");
      params.put("Servlet", "No");
      params.put("FilterName", addFilterName3);
      params.put("ServletName", addServletName3);
      params.put("DISPATCH", DispatcherType.REQUEST.toString());
      frFilter.setInitParameters(params);
      params.clear();
    } catch (ServletException ex) {
      LOGGER.error("Error creating Servlet");
    }

    /*
     * Add Servlet AddServletNotFound
     */
    ServletRegistration srNotFound = context.addServlet(addServletName4,
        AddServletNotFound.class);
    srNotFound.addMapping("/addServletNotFound", "/TestServlet");
    srNotFound.setInitParameter("FILTER", addFilterName4);
    srNotFound.setInitParameter(addFilterName4, "ALL");
    params.put("Filter", "No");
    params.put("Servlet", "Yes");
    params.put("FilterName", addFilterName4);
    params.put("ServletName", addServletName4);
    params.put("DISPATCH", "ALL");
    srNotFound.setInitParameters(params);
    params.clear();

    FilterRegistration frNotFound = context.addFilter(addFilterName4,
        AddFilterNotFound.class);
    frNotFound.addMappingForServletNames(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE,
            DispatcherType.FORWARD, DispatcherType.ERROR),
        true, addServletName4);
    frNotFound.setInitParameter("SERVLET", addServletName4);
    params.put("Filter", "Yes");
    params.put("Servlet", "No");
    params.put("FilterName", addFilterName4);
    params.put("ServletName", addServletName4);
    params.put("DISPATCH", "ALL");
    frNotFound.setInitParameters(params);
    params.clear();

    /*
     * Negative tests for - createServlet - createFilter - createListener
     */

    Boolean servlet_test = false;
    Boolean filter_test = false;
    Boolean listener_test = false;
    String SERVLET_TEST = "SERVLET_TEST";
    String FILTER_TEST = "FILTER_TEST";
    String LISTENER_TEST = "LISTENER_TEST";

    try {
      Servlet badservlet = context.createServlet(
          BadServlet.class);
    } catch (ServletException ex) {
      servlet_test = true;
    }
    context.setInitParameter(SERVLET_TEST, servlet_test.toString());

    try {
      Filter badfilter = context.createFilter(
          BadFilter.class);
    } catch (ServletException ex) {
      filter_test = true;
    }
    context.setInitParameter(FILTER_TEST, filter_test.toString());

    try {
      EventListener badlistener = context.createListener(
          BadListener.class);
    } catch (ServletException ex) {
      listener_test = true;
    }
    context.setInitParameter(LISTENER_TEST, listener_test.toString());

    /*
     * Test for ServletRegistration.getMappings()
     */
    String URL_MAPPING = "URL_MAPPING_TEST";
    StringBuilder Value_OF_URL = new StringBuilder();
    Collection<String> url_mappings = srClass.getMappings();
    for (String url : url_mappings) {
      Value_OF_URL.append(url).append('|');
    }
    context.setInitParameter(URL_MAPPING, Value_OF_URL.toString());

    /*
     * Test for ServletRegistration.getServletRegistrations()
     */
    String SERVLET_REGISTRATIONS = "SERVLET_REGISTRATIONS";
    StringBuilder Value_OF_Servlet_Registrations = new StringBuilder();
    Map<String, ? extends ServletRegistration> servlet_registrations = context.getServletRegistrations();
    Iterator it = servlet_registrations.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry) it.next();
      Value_OF_Servlet_Registrations
          .append(pairs.getKey() + "=" + pairs.getValue() + "|");
    }
    context.setInitParameter(SERVLET_REGISTRATIONS,
        Value_OF_Servlet_Registrations.toString());

    /*
     * Test for ServletRegistration.getServletRegistration(String)
     */
    String SERVLET_REGISTRATION = "SERVLET_REGISTRATION";
    StringBuilder Value_OF_Registration = new StringBuilder();
    String[] servlets = { "AddServletString", "AddServletClass", "AddServletNotFound", "CreateServlet", "TestServlet" };
    for (String servlet : servlets) {
      Value_OF_Registration.append(
          servlet + "=" + context.getServletRegistration(servlet) + "|");
    }
    context.setInitParameter(SERVLET_REGISTRATION,
        Value_OF_Registration.toString());

    /*
     * Test for FilterRegistration.getFilterRegistrations()
     */
    String FILTER_REGISTRATIONS = "FILTER_REGISTRATIONS";
    StringBuilder Value_OF_Filter_Registrations = new StringBuilder();
    Map<String, ? extends FilterRegistration> filter_registrations = context
        .getFilterRegistrations();
    it = filter_registrations.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pairs = (Map.Entry) it.next();
      Value_OF_Filter_Registrations
          .append(pairs.getKey() + "=" + pairs.getValue() + "|");
    }
    context.setInitParameter(FILTER_REGISTRATIONS,
        Value_OF_Filter_Registrations.toString());

    /*
     * Test for ServletRegistration.getFilterRegistration(String)
     */
    String FILTER_REGISTRATION = "FILTER_REGISTRATION";
    StringBuffer Value_OF_Filter_Registration = new StringBuffer();
    String[] filters = { "AddFilterString", "AddFilterClass",
        "AddFilterNotFound", "CreateFilter" };
    for (String filter : filters) {
      Value_OF_Filter_Registration
          .append(filter + "=" + context.getFilterRegistration(filter) + "|");
    }
    context.setInitParameter(FILTER_REGISTRATION,
        Value_OF_Filter_Registration.toString());

    /*
     * Test for FilterRegistration.getServletNameMappings()
     */
    String FILTER_SERVLET_MAPPING = "FILTER_SERVLET_MAPPING";
    StringBuffer Value_OF_Servlet_URL = new StringBuffer();
    FilterRegistration[] Filters = { frString, frClass, frNotFound, frFilter };
    for (FilterRegistration Filter_Test : Filters) {
      Collection<String> servlet_mappings = Filter_Test
          .getServletNameMappings();
      for (String servlet : servlet_mappings) {
        Value_OF_Servlet_URL.append(servlet + "|");
      }
    }
    context.setInitParameter(FILTER_SERVLET_MAPPING,
        Value_OF_Servlet_URL.toString());

    /*
     * Test for Registration.getName() Test for Registration.getClassName() Test
     * for Registration.setInitParameter(String) Test for
     * Registration.getInitParameter(String) Test for
     * Registration.setInitParameters() Test for
     * Registration.getInitParameters()
     */

    String REGISTRATION_NAME = "REGISTRION_NAME";
    String REGISTRATION_CLASS_NAME = "REGISTRATION_CLASS_NAME";
    String REGISTRATION_INIT_PARAMETER = "REGISTRATION_INIT_PARAMETER";
    String REGISTRATION_INIT_PARAMETERS = "REGISTRATION_INIT_PARAMETERS";

    StringBuffer Value_OF_Registration_Name = new StringBuffer();
    StringBuffer Value_OF_Registration_Class_Name = new StringBuffer();
    StringBuffer Value_OF_Registration_InitParameter = new StringBuffer();
    StringBuffer Value_OF_Registration_InitParameters = new StringBuffer();

    Registration[] registrations = { srString, frString, srClass, frClass,
        srServlet, frFilter, srNotFound, frNotFound };

    for (Registration registration : registrations) {
      Value_OF_Registration_Name.append(registration.getName() + "|");

      Value_OF_Registration_Class_Name
          .append(registration.getClassName() + "|");

      Value_OF_Registration_InitParameter
          .append(registration.getInitParameter("FILTER") + "|");
      Value_OF_Registration_InitParameter
          .append(registration.getInitParameter("SERVLET") + "|");

      params = registration.getInitParameters();
      for (int i = 0; i < 5; i++) {
        Value_OF_Registration_InitParameters
            .append(param_names[i] + "=" + params.get(param_names[i]) + "|");
      }
    }

    context.setInitParameter(REGISTRATION_NAME,
        Value_OF_Registration_Name.toString());
    context.setInitParameter(REGISTRATION_CLASS_NAME,
        Value_OF_Registration_Class_Name.toString());
    context.setInitParameter(REGISTRATION_INIT_PARAMETER,
        Value_OF_Registration_InitParameter.toString());
    context.setInitParameter(REGISTRATION_INIT_PARAMETERS,
        Value_OF_Registration_InitParameters.toString());
  }

  /**
   * Receives notification that the servlet context is about to be shut down.
   *
   * @param sce
   *          The servlet context event
   */
  public void contextDestroyed(ServletContextEvent sce) {
    // Do nothing
  }
}
