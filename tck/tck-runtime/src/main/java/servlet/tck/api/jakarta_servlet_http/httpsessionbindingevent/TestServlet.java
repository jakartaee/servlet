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

package servlet.tck.api.jakarta_servlet_http.httpsessionbindingevent;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;
import servlet.tck.common.util.StaticLog;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionBindingEvent;

public class TestServlet extends HttpTCKServlet {

  public void addedTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    StaticLog.clear();

    HttpSession session = request.getSession(true);

    String[] expected = {
        "attributeAdded: Name=addedTest, Value=attribute1, sessionid="
            + session.getId()};

    session.setAttribute("addedTest", "attribute1");

    ArrayList result = StaticLog.getClear();
    boolean b = ServletTestUtil.checkArrayList(result, expected, true, false);
    if (!b) {
      ServletTestUtil.printFailureData(pw, result, expected);
    }
    ServletTestUtil.printResult(pw, b);

    session.invalidate();

  }

  public void removedTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    StaticLog.clear();

    HttpSession session = request.getSession(true);

    String[] expected = {
        "attributeAdded: Name=removedTest, Value=attribute1, sessionid="
            + session.getId(),
        "attributeRemoved: Name=removedTest, Value=attribute1, sessionid="
            + session.getId()};

    session.setAttribute("removedTest", "attribute1");
    session.removeAttribute("removedTest");

    ArrayList result = StaticLog.getClear();
    boolean b = ServletTestUtil.checkArrayList(result, expected, true, false);
    if (!b) {
      ServletTestUtil.printFailureData(pw, result, expected);
    }
    ServletTestUtil.printResult(pw, b);

    session.invalidate();

  }

  public void replacedTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    StaticLog.clear();

    HttpSession session = request.getSession(true);

    String[] expected = {
        "attributeAdded: Name=replacedTest, Value=attribute1, sessionid="
            + session.getId(),
        "attributeReplaced: Name=replacedTest, Value=attribute1, sessionid="
            + session.getId()};

    session.setAttribute("replacedTest", "attribute1");
    session.setAttribute("replacedTest", "attribute2");

    ArrayList result = StaticLog.getClear();
    boolean b = ServletTestUtil.checkArrayList(result, expected, true, false);
    if (!b) {
      ServletTestUtil.printFailureData(pw, result, expected);
    }
    ServletTestUtil.printResult(pw, b);

    session.invalidate();

  }

  public void constructor_StringTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    HttpSession session = request.getSession(true);

    HttpSessionBindingEvent hsbe = new HttpSessionBindingEvent(session,
        "constructor_StringTest");

    if (hsbe != null) {
      ServletTestUtil.printResult(pw, true);
    } else {
      pw.println("Error:HttpSessionBindingEvent returned a null");
      ServletTestUtil.printResult(pw, false);
    }

  }

  public void constructor_String_ObjectTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    PrintWriter pw = response.getWriter();

    HttpSession session = request.getSession(true);
    HSBindingEvent hsbe1 = new HSBindingEvent();

    HttpSessionBindingEvent hsbe = new HttpSessionBindingEvent(session,
        "constructor_String_ObjectTest", hsbe1);

    if (hsbe != null) {
      ServletTestUtil.printResult(pw, true);
    } else {
      pw.println("Error:HttpSessionBindingEvent returned a null");
      ServletTestUtil.printResult(pw, false);
    }

  }

}
