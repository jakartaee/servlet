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
 * * [Additional notices, if required by prior licensing conditions]
 * *
 */

package servlet.tck.api.jakarta_servlet.filterconfig;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Vector;

import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public final class GetInitParamNames_Filter implements Filter {

  // The filter configuration object we are associated with. If this value
  // is null, this filter instance is not currently configured.
  private FilterConfig filterConfig = null;

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    pw.println("doFilter was successfully called in GetInitParamNames_Filter");

    if (filterConfig == null) {
      passed = false;
      pw.println(
          "doFilter of GetInitParamNames_Filter was called but this filter instance is not currently configured ");
    } else {

      String expectedResult1 = "GetInitParamNames_Filter_attribute1";
      String expectedResult2 = "GetInitParamNames_Filter_attribute2";

      boolean expectedResult1Found = false;
      boolean expectedResult2Found = false;
      Enumeration initP = filterConfig.getInitParameterNames();
      int expectedCount = 2;
      int count = 0;

      if (initP.hasMoreElements()) {
        Vector v = new Vector();

        while (initP.hasMoreElements()) {
          String result = (String) initP.nextElement();

          if (result.equals(expectedResult1)) {
            if (!expectedResult1Found) {
              count++;
              expectedResult1Found = true;
            } else {
              passed = false;
              pw.println(
                  "FilterConfig.getInitParameterNames() method return the same parameter name twice  ");
              pw.println(
                  "The parameter name already received was " + expectedResult1);
            }
          } else if (result.equals(expectedResult2)) {
            if (!expectedResult2Found) {
              count++;
              expectedResult2Found = true;
            } else {
              passed = false;
              pw.println(
                  "FilterConfig.getInitParameterNames() method return the same parameter name twice ");
              pw.println(
                  "The parameter name already received was " + expectedResult2);
            }
          } else {
            v.add(result);
          }
        }

        if (count != expectedCount) {
          passed = false;
          pw.println("FilterConfig.getInitParameterNames() method FAILED  ");
          pw.println("Expected count = " + expectedCount);
          pw.println("Actual count = " + count);
          pw.println("The expected parameter names received were :");

          if (expectedResult1Found) {
            pw.println(expectedResult1);
          }

          if (expectedResult2Found) {
            pw.println(expectedResult2);
          }

          pw.println("    Other parameter names received were :");

          for (int i = 0; i <= v.size() - 1; i++) {
            pw.println("     " + v.elementAt(i).toString());
          }
        } else {
          passed = true;
        }
      } else {
        passed = false;
        pw.println(
            "FilterConfig.getInitParameterNames() returned an empty enumeration");
      }
    }
    ServletTestUtil.printResult(pw, passed);

  }

  // remove the filter configuration object for this filter.
  public void destroy() {
  }

  // initialize the filter configuration object for this filter.

  public void init(FilterConfig filterConfig) {
    this.filterConfig = filterConfig;
  }

}
