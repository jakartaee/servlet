/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;

import com.sun.ts.tests.servlet.common.servlets.HttpTCKServlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(value = "/TestServlet")
@MultipartConfig(maxFileSize = 200)
public class TestServlet extends HttpTCKServlet {

  public void getPartTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    // Do getParameter first, to test if it works if getParts not called.
    out.write("getParameter(\"xyz\"): " + request.getParameter("xyz"));
    out.write("\n\n");

    Collection<Part> parts = request.getParts();
    for (Part p : parts) {
      out.write("Part name: " + p.getName() + "\n");
      out.write("Submitted File Name: " + p.getSubmittedFileName() + "\n");
      out.write("Size: " + p.getSize() + "\n");
      out.write("Content Type: " + p.getContentType() + "\n");
      out.write("Header Names:");
      for (String name : p.getHeaderNames()) {
        out.write(" " + name);
      }
      out.write("\n");
      out.write("getPart(String) test=" + (p == request.getPart(p.getName())));
      out.write("\n");
    }

    out.flush();
  }

  public void getPartTest1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    try {
      request.getPart("xyz");
      out.write("Expected ServletException not thrown");
    } catch (ServletException ioex) {
      out.write("Expected ServletException thrown");
    }
    out.write("\n");

    out.flush();
  }

  public void getPartsTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    try {
      request.getParts();
      out.write("Expected ServletException not thrown");
    } catch (ServletException ioex) {
      out.write("Expected ServletException thrown");
    }
    out.write("\n");

    out.flush();
  }

  public void getPartsTest1(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    // Do getParameter first, to test if it works if getParts not called.
    out.write("getParameter(\"xyz\"): " + request.getParameter("xyz"));
    out.write("\n\n");

    Collection<Part> parts = request.getParts();
    for (Part p : parts) {
      out.write("Part name: " + p.getName() + "\n");
      out.write("Submitted File Name: " + p.getSubmittedFileName() + "\n");
      out.write("Size: " + p.getSize() + "\n");
      out.write("Content Type: " + p.getContentType() + "\n");
      out.write("Header Names:");
      for (String name : p.getHeaderNames()) {
        out.write(" " + name);
      }
      out.write("\n");
      out.write("getPart(String) test=" + (p == request.getPart(p.getName())));
      out.write("\n");
    }

    out.flush();
  }

  public void getHeaderTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    Collection<Part> parts = request.getParts();
    for (Part p : parts) {
      out.write("Part name: " + p.getName() + "\n");
      out.write("Header Names:");
      for (String name : p.getHeaderNames()) {
        out.write(" " + name + ": " + p.getHeader(name));
        out.write("\n");
      }
      String name = "TCKDummyNameNonExistant";
      out.write(" " + name + ": " + p.getHeader(name));
    }
    out.flush();
  }

  public void getHeadersTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    Collection<Part> parts = request.getParts();
    for (Part p : parts) {
      out.write("Part name: " + p.getName() + "\n");
      out.write("Header Names:");
      for (String name : p.getHeaderNames()) {
        out.write(" " + name + ": ");
        for (Object header : p.getHeaders(name)) {
          out.write(header.toString());
        }
        out.write("\n");
      }
      String name = "TCKDummyNameNonExistant";
      out.write(" " + name + ": " + p.getHeaders(name).size());
      out.write("\n");
    }
    out.flush();
  }

  public void getInputStreamTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    response.setContentType("text/html");

    Collection<Part> parts = request.getParts();
    out.write("Parts size=" + parts.size());
    out.write("\n");
    for (Part p : parts) {
      out.write("Part name: " + p.getName() + "\n");
      InputStream is = p.getInputStream();
      BufferedReader bis = new BufferedReader(new InputStreamReader(is));

      String line = null;
      while ((line = bis.readLine()) != null) {
        out.write(line);
        out.write("\n");
      }
    }
    out.flush();
  }
}
