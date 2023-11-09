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
package servlet.tck.api.jakarta_servlet.servletcontext305;

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
import servlet.tck.api.jakarta_servlet.servletcontext30.*;
import servlet.tck.api.jakarta_servlet.servletcontext301.AddGenericEventListenerClass;
import servlet.tck.api.jakarta_servlet.servletcontext304.CreateGenericEventListener;

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
     * Add ServletContextListener
     */
    context.addListener(
        AddSCListenerClass.class);

    context.addListener(
        "servlet.tck.api.jakarta_servlet.servletcontext305.AddSCListenerString");
    try {
      EventListener sclistener = context.createListener(
          CreateSCListener.class);
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
        AddSCAttributeListenerClass.class);

    context.addListener(
        "servlet.tck.api.jakarta_servlet.servletcontext30.AddSCAttributeListenerString");
    try {
      EventListener scalistener = context.createListener(
          CreateSCAttributeListener.class);
      context.addListener(scalistener);
    } catch (ServletException ex) {
      LOGGER.error("Error creating Listener CreateSCAttributeListener: "
          + ex.getMessage(), ex);
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
      System.out.println("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
      ex.printStackTrace();
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
      System.out.println("Error creating Listener CreateSRAttributeListener: "
          + ex.getMessage());
      ex.printStackTrace();
    }

    /*
     * Negative tests for - createServlet - createFilter - createListener
     */

    Boolean servletTest = false;
    Boolean filterTest = false;
    Boolean listenerTest = false;
    String SERVLET_TEST = "SERVLET_TEST";
    String FILTER_TEST = "FILTER_TEST";
    String LISTENER_TEST = "LISTENER_TEST";
    String gcListenerTest = "GC_LISTENER_TEST";
    String gsListenerTest = "GS_LISTENER_TEST";
    String cgcListenerTest = "CGC_LISTENER_TEST";

    try {
      Servlet badservlet = context.createServlet(
          BadServlet.class);
    } catch (ServletException ex) {
      servletTest = true;
    }
    context.setInitParameter(SERVLET_TEST, servletTest.toString());

    try {
      Filter badfilter = context.createFilter(
          BadFilter.class);
    } catch (ServletException ex) {
      filterTest = true;
    }
    context.setInitParameter(FILTER_TEST, filterTest.toString());

    try {
      context.createListener(
          BadListener.class);
    } catch (ServletException ex) {
      listenerTest = true;
    }
    context.setInitParameter(LISTENER_TEST, listenerTest.toString());

    try {
      context.addListener(
          AddGenericEventListenerClass.class);
    } catch (IllegalArgumentException ex) {
      System.out
          .println("Error adding Listener addListener: " + ex.getMessage());
    }
    context.setInitParameter(gcListenerTest, listenerTest.toString());

    try {
      context.addListener(
          "servlet.tck.api.jakarta_servlet.servletcontext302.AssGenericEventListenerString");
    } catch (IllegalArgumentException ex) {
      listenerTest = true;
    }
    context.setInitParameter(gsListenerTest, listenerTest.toString());

    try {
      context.createListener(
          CreateGenericEventListener.class);
    } catch (IllegalArgumentException ex) {
      System.out.println("Error creating Listener CreateGenericEventListener: "
          + ex.getMessage());
    } catch (ServletException exs) {
      System.out.println("Error creating Listener CreateGenericEventListener: "
          + exs.getMessage());
    }
    context.setInitParameter(cgcListenerTest, listenerTest.toString());
  }
}
