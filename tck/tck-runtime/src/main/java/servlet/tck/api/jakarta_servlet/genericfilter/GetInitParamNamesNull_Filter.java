/*
 *
 *  *
 *  *
 *  * The Apache Software License, Version 1.1
 *  *
 *  * Copyright (c) 2018, 2020 Oracle and/or its affiliates. All rights reserved.
 *  * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 *  * reserved.
 *  *
 *  * Redistribution and use in source and binary forms, with or without
 *  * modification, are permitted provided that the following conditions
 *  * are met:
 *  *
 *  * 1. Redistributions of source code must retain the above copyright
 *  *    notice, this list of conditions and the following disclaimer.
 *  *
 *  * 2. Redistributions in binary form must reproduce the above copyright
 *  *    notice, this list of conditions and the following disclaimer in
 *  *    the documentation and/or other materials provided with the
 *  *    distribution.
 *  *
 *  * 3. The end-user documentation included with the redistribution, if
 *  *    any, must include the following acknowledgement:
 *  *       "This product includes software developed by the
 *  *        Apache Software Foundation (http://www.apache.org/)."
 *  *    Alternately, this acknowledgement may appear in the software itself,
 *  *    if and wherever such third-party acknowledgements normally appear.
 *  *
 *  * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *  *    Foundation" must not be used to endorse or promote products derived
 *  *    from this software without prior written permission. For written
 *  *    permission, please contact apache@apache.org.
 *  *
 *  * 5. Products derived from this software may not be called "Apache"
 *  *    nor may "Apache" appear in their names without prior written
 *  *    permission of the Apache Group.
 *  *
 *  * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *  * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 *  * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 *  * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 *  * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 *  * SUCH DAMAGE.
 *  * ====================================================================
 *  *
 *  * This software consists of voluntary contributions made by many
 *  * individuals on behalf of the Apache Software Foundation.  For more
 *  * information on the Apache Software Foundation, please see
 *  * <http://www.apache.org/>.
 *  *
 *  * [Additional notices, if required by prior licensing conditions]
 *  *
 */

package servlet.tck.api.jakarta_servlet.genericfilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public final class GetInitParamNamesNull_Filter extends GenericFilter {

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    pw.println(
        "doFilter was successfully called in GetInitParamNamesNull_Filter");

    if (getFilterConfig() == null) {
      passed = false;
      pw.println(
          "doFilter of GetInitParamNamesNull_Filter was called but this filter instance is not currently configured ");
    } else {

      Object o = getInitParameterNames();

      if (o == null) {
        passed = true;
      } else if (!((Enumeration) o).hasMoreElements()) {
        passed = true;
      } else {
        passed = false;
        pw.println("The following initialization parameters exist:");

        while (((Enumeration) o).hasMoreElements()) {
          String name = (String) ((Enumeration) o).nextElement();
          pw.println(name);
        }
      }
    }
    ServletTestUtil.printResult(pw, passed);

  }

  // remove the filter configuration object for this filter.
  public void destroy() {
  }

}
