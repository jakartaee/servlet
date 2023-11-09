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

package servlet.tck.spec.security.secform;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * This servlet will be used by test18.  The output from this servlet will
 * get parsed from within secformClient.java (in method test18).  Changing
 * the output statements in here could affect the test output so dont change 
 * the output statements unless you need to.
 *
 * This is testing that the HttpServletRequest.authenticate() method behaves correctly
 * and that other Servlet Security methods from HttpServletRequest also behave
 * according to the spec and javadoc.
 *
 */
public class ServletProgrammaticAuthen extends HttpServlet {

  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    out.println("enterred ServletProgrammaticAuthen.service()");
    System.out.println("enterred ServletProgrammaticAuthen.service()");

    // get user creds that are set in secformClient.test16()
    String theUsername = request.getParameter("the_username");
    String thePassword = request.getParameter("the_password");

    // we are not yet logged in nor authenticated so at this point calls
    // to getRemoteUser(), getUserPrincipal(), and getAuthType()
    // MUST return null (per Servlet 3.1 speec (section 13.3).
    if ((request.getRemoteUser() != null)
        || (request.getUserPrincipal() != null)
        || (request.getAuthType() != null)) {
      String str = "ERROR - HttpServletRequest.login() test failure.  ";
      str += "We did not get null for the following calls: ";
      str += " getRemoteUser(), getUserPrincipal(), getAuthType()";
      sendOutput(str, out);
    }

    // note: was planning to invoke request.authenticate(response) here to
    // validate it returns false but we ended up getting redirected to
    // login.jsp so not doing this. Spec is not clear here so omit.

    try {
      request.login(theUsername, thePassword);

      // per javadoc, if login() worked with no exception then there must
      // be non-null values for getUserPrincipal, getRemoteUser, and
      // getAuthType.
      if ((request.getRemoteUser() == null)
          || (request.getUserPrincipal() == null)
          || (request.getAuthType() == null)) {
        sendOutput("ERROR - HttpServletRequest.login() failed", out);
        sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
        sendOutput("request.getUserPrincipal() = " + request.getUserPrincipal(),
            out);
        sendOutput("request.getRemoteUser() = " + request.getRemoteUser(), out);
      } else {
        sendOutput("request.getRemoteUser()=" + request.getRemoteUser(), out);
      }
    } catch (ServletException e) {
      // per javadoc - if here, login failed.
      sendOutput("ERROR - HttpServletRequest.login() failed", out);
    }

    try {
      // a call to authenticate should return true since we should be
      // authenticated now
      if (request.authenticate(response) != true) {
        // Error - this should have returned true
        sendOutput("ERROR - HttpServletRequest.authenticate failed.", out);
        sendOutput(
            "authenticate should have returned true since we are authenticated.",
            out);
      } else {
        sendOutput("HttpServletRequest.authenticate passed.", out);
      }

      request.logout(); // some cleanup
    } catch (Exception ex) {
      sendOutput("Exception calling authenticate()", out);
      ex.printStackTrace();
    }

  }

  public void sendOutput(String str, PrintWriter out) {
    String hdr = "ServletProgrammaticAuthen:  ";
    out.println(hdr + str); // this line is used for test validation
    System.out.println(hdr + str); // this line is for debug aid
  }

}
