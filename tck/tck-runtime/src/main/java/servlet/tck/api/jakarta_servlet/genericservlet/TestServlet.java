/*
 *
 * *
 * * The Apache Software License, Version 1.1
 * *
 * * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
 * * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 * * reserved.
 * *
 * * Redistribution and use in source and binary forms, with or without
 * * modification, are permitted provided that the following conditions
 * * are met:
 * *
 * * 1. Redistributions of source code must retain the above copyright
 * *    notice, this list of conditions and the following disclaimer.
 * *
 * * 2. Redistributions in binary form must reproduce the above copyright
 * *    notice, this list of conditions and the following disclaimer in
 * *    the documentation and/or other materials provided with the
 * *    distribution.
 * *
 * * 3. The end-user documentation included with the redistribution, if
 * *    any, must include the following acknowledgement:
 * *       "This product includes software developed by the
 * *        Apache Software Foundation (http://www.apache.org/)."
 * *    Alternately, this acknowledgement may appear in the software itself,
 * *    if and wherever such third-party acknowledgements normally appear.
 * *
 * * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 * *    Foundation" must not be used to endorse or promote products derived
 * *    from this software without prior written permission. For written
 * *    permission, please contact apache@apache.org.
 * *
 * * 5. Products derived from this software may not be called "Apache"
 * *    nor may "Apache" appear in their names without prior written
 * *    permission of the Apache Group.
 * *
 * * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * * SUCH DAMAGE.
 * * ====================================================================
 * *
 * * This software consists of voluntary contributions made by many
 * * individuals on behalf of the Apache Software Foundation.  For more
 * * information on the Apache Software Foundation, please see
 * * <http://www.apache.org/>.
 * *
 */

package servlet.tck.api.jakarta_servlet.genericservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  public void getServletConfigTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;

    if (getServletConfig() == null) {
      passed = false;
      pw.println("getServletConfig method returned a null");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void getServletContextTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;

    if (getServletContext() == null) {
      passed = false;
      pw.println("getServletContext method returned a null");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getServletInfoTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;

    if (getServletInfo() == null) {
      passed = false;
      pw.println("getServletInfo method returned a null");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getServletNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;

    String expected = "TestServlet";
    String actual = getServletName();

    if (actual != null) {
      if (!expected.equals(actual)) {
        passed = false;
        pw.println("getServletName returned the wrong result");
        pw.println("Expected result= " + expected);
        pw.println("Actual result= " + actual);
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getServletName method returned a null");
    }
    ServletTestUtil.printResult(pw, passed);

  }

  public void getInitParameterTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = false;
    String expected = "value1";

    String actual = getInitParameter("parameter1");
    if (!expected.equals(actual)) {
      passed = false;
      pw.println("getInitParameter(String) did not return the correct result");
      pw.println("Expected result=" + expected);
      pw.println("Actual result=" + actual);
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getInitParameterNamesTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    boolean passed = true;

    String expectedResult1 = "parameter1";
    boolean expectedResult1Found = false;
    String expectedResult2 = "parameter2";
    boolean expectedResult2Found = false;
    Enumeration initP = getInitParameterNames();

    if (initP.hasMoreElements()) {
      while (initP.hasMoreElements()) {
        String name = (String) initP.nextElement();
        pw.println("Initialization parameter: " + name);

        if (name.equals(expectedResult1)) {
          if (!expectedResult1Found) {
            expectedResult1Found = true;
          } else {
            passed = false;
            pw.println(
                "getInitParameterNames() method return an attribute name twice ");
            pw.println(
                "The attribute already specified was " + expectedResult1 + " ");
          }
        } else if (name.equals(expectedResult2)) {
          if (!expectedResult2Found) {
            expectedResult2Found = true;
          } else {
            passed = false;
            pw.println(
                "getInitParameterNames() method return an attribute name twice ");
            pw.println(
                "The attribute already specified was " + expectedResult2 + " ");
          }
        }
      }
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
