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
package com.sun.ts.tests.servlet.spec.annotationservlet.weblistener;

import java.io.IOException;

import com.sun.ts.tests.servlet.common.servlets.GenericTCKServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class TestServlet extends GenericTCKServlet {

  public void ContextListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    response.getWriter()
        .println(getServletContext().getAttribute("ContextListener"));
    getServletContext().removeAttribute("SRList");
    getServletContext().removeAttribute("SRAList");
    getServletContext().removeAttribute("ContextListener");
  }

  public void ContextAttributeListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    getServletContext().setAttribute("Test", "tmp");
    getServletContext().removeAttribute("Test");
    response.getWriter().println(getServletContext().getAttribute("SCAList"));
    getServletContext().removeAttribute("SRList");
    getServletContext().removeAttribute("SRAList");
    getServletContext().removeAttribute("SCAList");
  }

  public void RequsetListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    response.getWriter().println(getServletContext().getAttribute("SRList"));
    getServletContext().removeAttribute("SRList");
    getServletContext().removeAttribute("SRAList");
    getServletContext().removeAttribute("SCAList");
  }

  public void RepeatRequsetListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    response.getWriter().println(getServletContext().getAttribute("SRList"));
    getServletContext().removeAttribute("SRAList");
    getServletContext().removeAttribute("SCAList");
  }

  public void RequsetAttributeListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    request.setAttribute("Test", "RequestAttribute");
    request.removeAttribute("Test");
    response.getWriter().println(getServletContext().getAttribute("SRAList"));
    getServletContext().removeAttribute("SRList");
    getServletContext().removeAttribute("SRAList");
    getServletContext().removeAttribute("SCAList");
  }

  /*
   * This test is used to trigger events in the HttpSessionAttributeListener but
   * the REAL testing is done in HttpSessionAttributeListenerTest. So this
   * method is not the real test, but instead does preparation work for the real
   * test. (thus this is a pre-test) This pre-test will act on session
   * attributes in order to force the invocation of the
   * HttpSessionAttributeListener methods.
   */
  public void HttpSessionAttributeListenerPreLude(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    response.getWriter().println("in HttpSessionAttributeListenerPreLude");

    // create new attribute, change that attribute, and remove that attribute
    // in attempt to trigger the HttpSessionAttributeListener events
    HttpServletRequest httpServletReq = (HttpServletRequest) request;

    // create new session attribute to trigger attributeAdded() notification
    httpServletReq.getSession().setAttribute("Test",
        "HttpSessionAttributeListenerPreludeB");

    // replace session attribute to trigger attributeReplaced() notification
    httpServletReq.getSession().setAttribute("Test",
        "HttpSessionAttributeListenerPrelude");

    // remove session attribute to trigger attributeRemoved notification
    httpServletReq.getSession().removeAttribute("Test");

  }

  public void HttpSessionAttributeListenerTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    request.setAttribute("Test", "HttpSessionAttributeListenerTest"); // was
                                                                      // RequestAttribute
    request.removeAttribute("Test");
    System.out.println("TestServlet.HttpSessionAttributeListenerTest HSAList = "
        + getServletContext().getAttribute("HSAList"));
    response.getWriter().println(getServletContext().getAttribute("HSAList"));
  }

}
