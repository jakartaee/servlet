/*
 *
 * *
 * * The Apache Software License, Version 1.1
 * *
 * * Copyright (c) 2018, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * *    any, must include the following acknowlegement:
 * *       "This product includes software developed by the
 * *        Apache Software Foundation (http://www.apache.org/)."
 * *    Alternately, this acknowlegement may appear in the software itself,
 * *    if and wherever such third-party acknowlegements normally appear.
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

package com.sun.ts.tests.servlet.spec.i18n.encoding;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpTCKServlet {

  public void spec1Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    boolean passed = true;

    String[] call = { "setContentType", "setLocale", "setCharacterEncoding",
        "getWriter", "setCharacterEncoding", "setLocale", };
    String[] actual = new String[6];
    String[] expected = { "utf-16le", "utf-16le", "utf-8", "utf-8", "utf-8",
        "utf-8", };

    try {
      // setContentType should set character encoding
      response.setContentType("text/html;charset=utf-16le");
      actual[0] = response.getCharacterEncoding();

      // setLocale should not override explicit character encoding request
      response.setLocale(Locale.JAPAN);
      actual[1] = response.getCharacterEncoding();

      // setCharacterEncoding should still be able to change encoding
      response.setCharacterEncoding("utf-8");
      actual[2] = response.getCharacterEncoding();

      // getWriter should freeze the character encoding
      PrintWriter pw = response.getWriter();
      actual[3] = response.getCharacterEncoding();

      // setCharacterEncoding should no longer be able to change the encoding
      response.setCharacterEncoding("iso-8859-1");
      actual[4] = response.getCharacterEncoding();

      // setLocale should not override explicit character encoding request
      response.setLocale(Locale.JAPAN);
      actual[5] = response.getCharacterEncoding();

      for (int i = 0; i < actual.length; i++) {
        if (!(actual[i].toLowerCase(Locale.US)
            .equals(expected[i].toLowerCase(Locale.US)))) {
          passed = false;
          pw.println("Error: Step:" + i + " - encoding after " + call[i]
              + " is " + actual[i] + "; expected " + expected[i]);
        }
      }
      ServletTestUtil.printResult(pw, passed);
    } catch (Throwable t) {
      throw new ServletException(t);
    }
  }

  public void spec2Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    boolean passed = true;

    String[] call = { "setContentType", "setLocale", "setLocale",
        "setContentType", "setCharacterEncoding", "setLocale", "setContentType",
        "setCharacterEncoding", "getWriter", "setCharacterEncoding",
        "setLocale", };
    String[] actual = new String[11];
    String[] expected = { "iso-8859-1", "euc-jp", "gb18030", "gb18030", "utf-8",
        "utf-8", "gb18030", "utf-8", "utf-8", "utf-8", "utf-8", };

    try {
      response.setContentType("text/html");
      actual[0] = response.getCharacterEncoding();

      // setLocale should change character encoding based on
      // locale-encoding-mapping-list
      response.setLocale(Locale.JAPAN);
      actual[1] = response.getCharacterEncoding();

      // setLocale should change character encoding based on
      // locale-encoding-mapping-list
      response.setLocale(Locale.CHINA);
      actual[2] = response.getCharacterEncoding();

      // setContentType here doesn't define character encoding
      response.setContentType("text/html");
      actual[3] = response.getCharacterEncoding();

      // setCharacterEncoding should still be able to change encoding
      response.setCharacterEncoding("utf-8");
      actual[4] = response.getCharacterEncoding();

      // setLocale should not override explicit character encoding request
      response.setLocale(Locale.JAPAN);
      actual[5] = response.getCharacterEncoding();

      // setContentType should still be able to change encoding
      response.setContentType("text/html;charset=gb18030");
      actual[6] = response.getCharacterEncoding();

      // setCharacterEncoding should still be able to change encoding
      response.setCharacterEncoding("utf-8");
      actual[7] = response.getCharacterEncoding();

      // getWriter should freeze the character encoding
      PrintWriter pw = response.getWriter();
      actual[8] = response.getCharacterEncoding();

      // setCharacterEncoding should no longer be able to change the encoding
      response.setCharacterEncoding("iso-8859-1");
      actual[9] = response.getCharacterEncoding();

      // setLocale should not override explicit character encoding request
      response.setLocale(Locale.JAPAN);
      actual[10] = response.getCharacterEncoding();

      for (int i = 0; i < actual.length; i++) {
        if (!(actual[i].toLowerCase(Locale.US)
            .equals(expected[i].toLowerCase(Locale.US)))) {
          passed = false;
          pw.println("Error: Step:" + (i + 1) + " - encoding after " + call[i]
              + " is " + actual[i] + "; expected " + expected[i]);
        }
      }
      ServletTestUtil.printResult(pw, passed);
    } catch (Throwable t) {
      throw new ServletException(t);
    }
  }

  public void spec3Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    boolean passed = true;

    String[] call = { "setContentType", "flushBuffer", "setCharacterEncoding",
        "setLocale", "getWriter", };
    String[] actual = new String[5];
    String[] expected = { "iso-8859-1", "iso-8859-1", "iso-8859-1",
        "iso-8859-1", "iso-8859-1", };
    try {
      // setContentType should set character encoding
      response.setContentType("text/html");
      actual[0] = response.getCharacterEncoding();

      // committing should freeze the character encoding
      response.flushBuffer();
      actual[1] = response.getCharacterEncoding();

      // setCharacterEncoding should no longer be able to change the encoding
      response.setCharacterEncoding("utf-8");
      actual[2] = response.getCharacterEncoding();

      // setLocale should not override explicit character encoding request
      response.setLocale(Locale.JAPAN);
      actual[3] = response.getCharacterEncoding();

      // getWriter should freeze the character encoding
      PrintWriter pw = response.getWriter();
      actual[4] = response.getCharacterEncoding();

      for (int i = 0; i < actual.length; i++) {
        if (!(actual[i].toLowerCase(Locale.US)
            .equals(expected[i].toLowerCase(Locale.US)))) {
          passed = false;
          pw.println("Error: Step:" + i + " - encoding after " + call[i]
              + " is " + actual[i] + "; expected " + expected[i]);
        }
      }
      ServletTestUtil.printResult(pw, passed);
    } catch (Throwable t) {
      throw new ServletException(t);
    }
  }

}
