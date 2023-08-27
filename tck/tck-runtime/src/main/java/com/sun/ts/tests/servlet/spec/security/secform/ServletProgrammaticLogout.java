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
 * $Id: ServletProgrammaticLogout.java 62571 2011-04-18 20:58:13Z kgrucci $
 */

package com.sun.ts.tests.servlet.spec.security.secform;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This servlet will be used by test17.  The output from this servlet will
 * get parsed from within secformClient.java (in method test17).  Changing
 * the output statements in here could affect the test output so dont unless
 * you need to.
 *
 * This is testing that the HttpServletRequest.logout() method behaves correctly
 * and that other Servlet Security methods from HttpServletRequest also behave
 * according to the spec and javadoc.
 *
 * This servlet assumes that there has been successful authentication upon
 * entry into here.  After which, the logout() feature will be validated.
 *
 */
public class ServletProgrammaticLogout extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    out.println("enterred ServletProgrammaticLogout.service()");
    System.out.println("enterred ServletProgrammaticLogout.service()");

    // we should be authenticated at this point so a call to getRemoteUser(),
    // getUserPrincipal(), and getAuthType() MUST NOT return null value
    // (per Servlet 3.1 speec (section 13.3).
    if ((request.getRemoteUser() == null)
        || (request.getUserPrincipal() == null)
        || (request.getAuthType() == null)) {
      String str = "ERROR - HttpServletRequest.logout() - we got null for the following calls: ";
      str += " getRemoteUser(), getUserPrincipal(), getAuthType()";
      sendOutput(str, out);

      // debug aid
      sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
      sendOutput("request.getUserPrincipal() = " + request.getUserPrincipal(),
          out);
      sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
    }

    try {
      request.logout();

      // per javadoc, if logout() worked with no exception then there must
      // be null values for getUserPrincipal, getRemoteUser, and getAuthType.
      if ((request.getRemoteUser() != null)
          || (request.getUserPrincipal() != null)
          || (request.getAuthType() != null)) {
        sendOutput("ERROR - HttpServletRequest.logout() failed", out);

        // debug aid
        sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
        sendOutput("request.getUserPrincipal() = " + request.getUserPrincipal(),
            out);
        sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
      } else {
        sendOutput("request.getRemoteUser()=" + request.getRemoteUser(), out);
      }
    } catch (ServletException e) {
      sendOutput("ERROR - HttpServletRequest.logout() failed", out);
    }

  }

  public void sendOutput(String str, PrintWriter out) {
    String HDR = "ServletProgrammaticLogout:  ";
    out.println(HDR + str); // this line is used for test validation
    System.out.println(HDR + str); // this line is for debug aid
  }

}
