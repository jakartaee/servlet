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
package servlet.tck.api.jakarta_servlet.servletcontext30;

import java.util.EnumSet;
import java.util.EventListener;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    final String addServletName5 = "DuplicateServletClass";
    final String addServletName6 = "DuplicateServletString";

    final String addFilterName1 = "AddFilterString";
    final String addFilterName2 = "AddFilterClass";
    final String addFilterName3 = "CreateFilter";
    final String addFilterName4 = "AddFilterNotFound";
    final String addFilterName5 = "DuplicateFilterClass";
    final String addFilterName6 = "DuplicateFilterString";

    /*
     * Add Servlet AddServletString
     */
    ServletRegistration srString = context.addServlet(addServletName1,
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddServletString");
    srString.addMapping("/addServletString");
    srString.setInitParameter("FILTER", addFilterName1);
    srString.setInitParameter(addFilterName1,
        DispatcherType.FORWARD.toString());

    FilterRegistration frString = context.addFilter(addFilterName1,
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddFilterString");
    frString.addMappingForServletNames(EnumSet.of(DispatcherType.FORWARD), true,
        addServletName1);
    frString.setInitParameter("SERVLET", addServletName1);
    frString.setInitParameter(addServletName1,
        DispatcherType.FORWARD.toString());

    /*
     * Add Servlet AddServletClass
     */
    ServletRegistration srClass = context.addServlet(addServletName2,
        AddServletClass.class);
    srClass.addMapping("/addServletClass", "/SecondaddServletClass",
        "/ThirdAddServletClass", "/AddServletClass/*");
    srClass.setInitParameter("FILTER", addFilterName2);
    srClass.setInitParameter(addFilterName2, DispatcherType.REQUEST.toString());

    FilterRegistration frClass = context.addFilter(addFilterName2,
        AddFilterClass.class);
    frClass.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), true,
        addServletName2);
    frClass.setInitParameter("SERVLET", addServletName2);
    frClass.setInitParameter(addServletName2,
        DispatcherType.REQUEST.toString());

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
      srServlet.setInitParameter(addFilterName3,
          DispatcherType.REQUEST.toString());

      Filter filter3 = context.createFilter(
          CreateFilter.class);
      frFilter = context.addFilter(addFilterName3, filter3);
      frFilter.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),
          true, addServletName3);
      frFilter.setInitParameter("SERVLET", addServletName3);
      frFilter.setInitParameter(addServletName3,
          DispatcherType.REQUEST.toString());
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

    FilterRegistration frNotFound = context.addFilter(addFilterName4,
        AddFilterNotFound.class);
    frNotFound.addMappingForServletNames(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE,
            DispatcherType.FORWARD, DispatcherType.ERROR),
        true, addServletName4);
    frNotFound.setInitParameter("SERVLET", addServletName4);
    frNotFound.setInitParameter(addServletName4, "ALL");

    /*
     * Add ServletContextAttributeListener
     */
    context.addListener(
        AddSCAttributeListenerClass.class);

    context.addListener(
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerString");
    try {
      EventListener sclistener = context.createListener(
          CreateSCAttributeListener.class);
      context.addListener(sclistener);
    } catch (ServletException ex) {
      LOGGER.error("Error creating Listener CreateSCAttributeListener: "
          + ex.getMessage());
    }

    /*
     * Add ServletRequestListener
     */
    context.addListener(
        AddSRListenerClass.class);
    context.addListener(
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddSRListenerString");
    try {
      EventListener srlistener = context.createListener(
          CreateSRListener.class);
      context.addListener(srlistener);
    } catch (ServletException ex) {
      LOGGER.error("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
    }

    /*
     * Add ServletRequestAttributesListener
     */
    context.addListener(
        AddSRAttributeListenerClass.class);
    context.addListener(
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddSRAttributeListenerString");
    try {
      EventListener sralistener = context.createListener(
          CreateSRAttributeListener.class);
      context.addListener(sralistener);
    } catch (ServletException ex) {
      LOGGER.error("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
    }

    /*
     * Negative tests for - createServlet - createFilter - createListener
     */
    Boolean servlet_test = false;
    Boolean duplicatec_servlet_test = false;
    Boolean duplicates_servlet_test = false;
    Boolean filter_test = false;
    Boolean duplicatec_filter_test = false;
    Boolean duplicates_filter_test = false;
    Boolean listener_test = false;
    Boolean scc_listener_test = false;
    Boolean scs_listener_test = false;
    Boolean csc_listener_test = false;
    String SERVLET_TEST = "SERVLET_TEST";
    String DUPLICATEC_SERVLET_TEST = "DUPLICATEC_SERVLET_TEST";
    String DUPLICATES_SERVLET_TEST = "DUPLICATES_SERVLET_TEST";
    String FILTER_TEST = "FILTER_TEST";
    String DUPLICATEC_FILTER_TEST = "DUPLICATEC_FILTER_TEST";
    String DUPLICATES_FILTER_TEST = "DUPLICATES_FILTER_TEST";
    String LISTENER_TEST = "LISTENER_TEST";
    String SCC_LISTENER_TEST = "SCC_LISTENER_TEST";
    String SCS_LISTENER_TEST = "SCS_LISTENER_TEST";
    String CSC_LISTENER_TEST = "CSC_LISTENER_TEST";

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
      context.createListener(
          BadListener.class);
    } catch (ServletException ex) {
      listener_test = true;
    }
    context.setInitParameter(LISTENER_TEST, listener_test.toString());

    try {
      context.addListener(
          AddSCListenerClass.class);
    } catch (IllegalArgumentException ilex) {
      scc_listener_test = true;
      LOGGER.error(
          "Expected exception thrown adding Listener AddSCListenerClass: "
              + ilex.getMessage());
    } catch (Exception ex) {
      LOGGER.error(
          "Error adding Listener AddSCListenerClass: " + ex.getMessage());
    }
    context.setInitParameter(SCC_LISTENER_TEST, scc_listener_test.toString());

    try {
      context.addListener(
          "servlet.tck.api.jakarta_servlet.servletcontext30.AddSCListenerString");
    } catch (IllegalArgumentException ilex) {
      scs_listener_test = true;
      LOGGER.error(
          "Expected exception thrown adding Listener AddSCListenerString: "
              + ilex.getMessage());
    } catch (Exception ex) {
      LOGGER.error(
          "Error adding Listener AddSCListenerString: " + ex.getMessage());
    }
    context.setInitParameter(SCS_LISTENER_TEST, scs_listener_test.toString());

    try {
      EventListener csclistener = context.createListener(
          CreateSCListener.class);
      context.addListener(csclistener);
    } catch (IllegalArgumentException ilex) {
      csc_listener_test = true;
      LOGGER.error(
          "Expected exception thrown adding Listener CreateSCListener: "
              + ilex.getMessage());
    } catch (Exception ex) {
      LOGGER.error(
          "Error adding Listener AddSCListenerClass: " + ex.getMessage());
    }
    context.setInitParameter(CSC_LISTENER_TEST, csc_listener_test.toString());

    /*
     * Add Servlet DuplicateServletClass
     */
    ServletRegistration srdClass = context.addServlet(addServletName5,
        DuplicateServletClass.class);
    if (srdClass == null) {
      duplicatec_servlet_test = true;
    }
    context.setInitParameter(DUPLICATEC_SERVLET_TEST,
        duplicatec_servlet_test.toString());

    ServletRegistration srdString = context.addServlet(addServletName6,
        "servlet.tck.api.jakarta_servlet.servletcontext30.DuplicateServletString");
    if (srdString == null) {
      duplicates_servlet_test = true;
    }
    context.setInitParameter(DUPLICATES_SERVLET_TEST,
        duplicates_servlet_test.toString());

    FilterRegistration frdClass = context.addFilter(addFilterName6,
        DuplicateFilterClass.class);
    if (frdClass == null) {
      duplicatec_filter_test = true;
    }
    context.setInitParameter(DUPLICATEC_FILTER_TEST,
        duplicatec_filter_test.toString());

    FilterRegistration frdString = context.addFilter(addFilterName6,
        "servlet.tck.api.jakarta_servlet.servletcontext30.DuplicateFilterString");
    if (frdString == null) {
      duplicates_filter_test = true;
    }
    context.setInitParameter(DUPLICATES_FILTER_TEST,
        duplicates_filter_test.toString());

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
