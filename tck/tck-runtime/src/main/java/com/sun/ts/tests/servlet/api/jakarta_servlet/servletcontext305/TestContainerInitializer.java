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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext305;

import java.util.EnumSet;
import java.util.EventListener;
import java.util.Set;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContainerInitializer implements ServletContainerInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestContainerInitializer.class);

  public void onStartup(Set<Class<?>> arg0, ServletContext context)
      throws ServletException {

    final String addServletName1 = "AddServletString";
    final String addServletName2 = "AddServletClass";
    final String addServletName3 = "CreateServlet";
    final String addServletName4 = "AddServletNotFound";

    final String addFilterName1 = "AddFilterString";
    final String addFilterName2 = "AddFilterClass";
    final String addFilterName3 = "CreateFilter";
    final String addFilterName4 = "AddFilterNotFound";
    /*
     * Add Servlet AddServletString
     */
    ServletRegistration srString = context.addServlet(addServletName1,
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddServletString");
    srString.addMapping("/addServletString");
    srString.setInitParameter("FILTER", addFilterName1);
    srString.setInitParameter(addFilterName1,
        DispatcherType.FORWARD.toString());

    FilterRegistration frString = context.addFilter(addFilterName1,
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddFilterString");
    frString.addMappingForServletNames(EnumSet.of(DispatcherType.FORWARD), true,
        addServletName1);
    frString.setInitParameter("SERVLET", addServletName1);
    frString.setInitParameter(addServletName1,
        DispatcherType.FORWARD.toString());

    /*
     * Add Servlet AddServletClass
     */
    ServletRegistration srClass = context.addServlet(addServletName2,
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddServletClass.class);
    srClass.addMapping("/addServletClass", "/SecondaddServletClass",
        "/ThirdAddServletClass", "/AddServletClass/*");
    srClass.setInitParameter("FILTER", addFilterName2);
    srClass.setInitParameter(addFilterName2, DispatcherType.REQUEST.toString());

    FilterRegistration frClass = context.addFilter(addFilterName2,
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddFilterClass.class);
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
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.CreateServlet.class);
      srServlet = context.addServlet(addServletName3, servlet3);
      srServlet.addMapping("/createServlet", "/SecondCreateServlet",
          "/ThirdCreateServlet");
      srServlet.setInitParameter("FILTER", addFilterName3);
      srServlet.setInitParameter(addFilterName3,
          DispatcherType.REQUEST.toString());

      Filter filter3 = context.createFilter(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.CreateFilter.class);
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
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddServletNotFound.class);
    srNotFound.addMapping("/addServletNotFound", "/TestServlet");
    srNotFound.setInitParameter("FILTER", addFilterName4);
    srNotFound.setInitParameter(addFilterName4, "ALL");

    FilterRegistration frNotFound = context.addFilter(addFilterName4,
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddFilterNotFound.class);
    frNotFound.addMappingForServletNames(
        EnumSet.of(DispatcherType.REQUEST, DispatcherType.INCLUDE,
            DispatcherType.FORWARD, DispatcherType.ERROR),
        true, addServletName4);
    frNotFound.setInitParameter("SERVLET", addServletName4);
    frNotFound.setInitParameter(addServletName4, "ALL");

    /*
     * Add ServletContextListener
     */
    context.addListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext305.AddSCListenerClass.class);

    context.addListener(
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext305.AddSCListenerString");
    try {
      EventListener sclistener = context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext305.CreateSCListener.class);
      context.addListener(sclistener);
    } catch (ServletException ex) {
      LOGGER.error(
          "Error creating Listener CreateSCListener: " + ex.getMessage(), ex);
      ex.printStackTrace();
    }

    /*
     * Add ServletContextAttributeListener
     */
    context.addListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerClass.class);

    context.addListener(
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerString");
    try {
      EventListener scalistener = context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.CreateSCAttributeListener.class);
      context.addListener(scalistener);
    } catch (ServletException ex) {
      LOGGER.error("Error creating Listener CreateSCAttributeListener: "
          + ex.getMessage(), ex);
    }

    /*
     * Add ServletRequestListener
     */
    context.addListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSRListenerClass.class);
    context.addListener(
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSRListenerString");
    try {
      EventListener srlistener = context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.CreateSRListener.class);
      context.addListener(srlistener);
    } catch (ServletException ex) {
      System.out.println("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
      ex.printStackTrace();
    }

    /*
     * Add ServletRequestAttributesListener
     */
    context.addListener(
        com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSRAttributeListenerClass.class);
    context.addListener(
        "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.AddSRAttributeListenerString");
    try {
      EventListener sralistener = context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.CreateSRAttributeListener.class);
      context.addListener(sralistener);
    } catch (ServletException ex) {
      System.out.println("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
      ex.printStackTrace();
    }

    /*
     * Negative tests for - createServlet - createFilter - createListener
     */

    Boolean servlet_test = false;
    Boolean filter_test = false;
    Boolean listener_test = false;
    String SERVLET_TEST = "SERVLET_TEST";
    String FILTER_TEST = "FILTER_TEST";
    String LISTENER_TEST = "LISTENER_TEST";
    String GC_LISTENER_TEST = "GC_LISTENER_TEST";
    String GS_LISTENER_TEST = "GS_LISTENER_TEST";
    String CGC_LISTENER_TEST = "CGC_LISTENER_TEST";

    try {
      Servlet badservlet = context.createServlet(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.BadServlet.class);
    } catch (ServletException ex) {
      servlet_test = true;
    }
    context.setInitParameter(SERVLET_TEST, servlet_test.toString());

    try {
      Filter badfilter = context.createFilter(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.BadFilter.class);
    } catch (ServletException ex) {
      filter_test = true;
    }
    context.setInitParameter(FILTER_TEST, filter_test.toString());

    try {
      context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext30.BadListener.class);
    } catch (ServletException ex) {
      listener_test = true;
    }
    context.setInitParameter(LISTENER_TEST, listener_test.toString());

    try {
      context.addListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext301.AddGenericEventListenerClass.class);
    } catch (IllegalArgumentException ex) {
      System.out
          .println("Error adding Listener addListener: " + ex.getMessage());
    }
    context.setInitParameter(GC_LISTENER_TEST, listener_test.toString());

    try {
      context.addListener(
          "com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext302.AssGenericEventListenerString");
    } catch (IllegalArgumentException ex) {
      listener_test = true;
    }
    context.setInitParameter(GS_LISTENER_TEST, listener_test.toString());

    try {
      context.createListener(
          com.sun.ts.tests.servlet.api.jakarta_servlet.servletcontext304.CreateGenericEventListener.class);
    } catch (java.lang.IllegalArgumentException ex) {
      System.out.println("Error creating Listener CreateGenericEventListener: "
          + ex.getMessage());
    } catch (ServletException exs) {
      System.out.println("Error creating Listener CreateGenericEventListener: "
          + exs.getMessage());
    }
    context.setInitParameter(CGC_LISTENER_TEST, listener_test.toString());
  }
}
