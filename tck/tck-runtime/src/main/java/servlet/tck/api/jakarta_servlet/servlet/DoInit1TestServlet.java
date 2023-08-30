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

/* @Author : Ramesh.Mandava
*/

package servlet.tck.api.jakarta_servlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.UnavailableException;

/**
 * Negative Test for Servlet.init(ServletConfig) method
 */

public class DoInit1TestServlet extends CoreServletTest {

  /**
   * We will throw UnavailableException from inside init The Servlet should not
   * be initialized
   */

  public void init(ServletConfig sc) throws ServletException {
    // throw new UnavailableException(this,"Negative Init Test");
    throw new UnavailableException("Negative Init Test");
  }

  public void service(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    PrintWriter pw = response.getWriter();
    pw.println(
        "service method getting called even when we throw UnavailableException in init()");
    ServletTestUtil.printResult(pw, false);
  }
}
