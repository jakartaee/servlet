/*
 * Copyright (c) 2009, 2021 Oracle and/or its affiliates and others.
 * All rights reserved.
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
package servlet.tck.api.jakarta_servlet.servletcontext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import servlet.tck.common.servlets.GenericTCKServlet;
import servlet.tck.common.util.Data;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class TestServlet extends GenericTCKServlet {

  /**
   * Test for ServletContext.getMajorVersion method
   */
  public void getMajorVersion(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    // Version should be 6 for Servlet 6.0 release
    if (context.getMajorVersion() == 6) {
      passed = true;
    } else {
      passed = false;
      pw.println("getMajorVersion() returned " + context.getMajorVersion());
      pw.println("Expected ServletContext.getMajorVersion() -> 6 ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getMinorVersion method
   */
  public void getMinorVersion(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();
    int expectedResult = 0;
    int result = context.getMinorVersion();

    if (result == expectedResult) {
      passed = true;
    } else {
      passed = false;
      pw.println("getMinorVersion() returned incorrect result");
      pw.println("Expected result = " + expectedResult + " ");
      pw.println("Actual result = " + result + " ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  private static final String EXPECTED_MIME_TYPE = "application/x-java-class";

  private static final String MIME_OBJECT = "/WEB-INF/classes/tests/jakarta_servlet/ServletContext/GetMimeTypeTestServlet.class";

  public void getMimeType(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();
    String result = context.getMimeType(MIME_OBJECT);

    if (result != null) {
      if (result.equals(EXPECTED_MIME_TYPE)) {
        passed = true;
      } else {
        passed = false;
        pw.println("getMimeType(" + MIME_OBJECT
            + ") returned incorrect result\n\t" + "Expected result = "
            + EXPECTED_MIME_TYPE + "\n\t" + "Actual result = " + result);
      }
    } else {
      passed = false;
      pw.println("getMimeType( " + MIME_OBJECT + " ) returned a null result\n\t"
          + "Expected result = " + EXPECTED_MIME_TYPE);
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getMimeType(String) method
   */
  /**
   * We set improper mime type We should get null as the result
   */
  public void getMimeType_1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    // abraca.dabra? i hope there is no such mime type so far
    String param = "/META-INF/MANIFEST.MF";
    String mimeType = context.getMimeType(param);

    if (mimeType == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getMimeType(" + param + ") did not return null");
      pw.println("Actual result = " + mimeType + " ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getRealPath(String) method
   */
  public void getRealPath(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();

    String path = "/servlet_js_ServletContext/Test";
    String win32Path = new StringBuffer(45).append(File.separatorChar)
        .append("servlet_js_ServletContext").append(File.separatorChar)
        .append("Test").toString();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String realPath = context.getRealPath(path);
    // a null will be returned if running directly from a jar file
    if ((realPath == null) || (realPath.contains(path)) || // UNIX path
        (realPath.contains(win32Path))) // Win32 path
    {
      pw.println("realPath = " + realPath);
    } else {
      passed = false;
      pw.println("getRealPath(" + path + ") did not contain the named files");
      pw.println("Actual result = " + realPath + " ");
    }
    
    // Leading '/' is optional. Ensure the result is the same with or without it
    String pathNoSolidus = path.substring(1);
    String realPathNoSolidus = context.getRealPath(pathNoSolidus);
    
    if (realPath == null && realPathNoSolidus == null
        || realPath != null && realPath.equals(realPathNoSolidus)) {
      pw.println("realPathNoSolidus = " + realPathNoSolidus);      
    } else {
      passed = false;
      pw.println("getRealPath(" + path + ") returned [" + realPath + "]");
      pw.println("getRealPath(" + pathNoSolidus + ") returned [" + realPathNoSolidus + "]");
      pw.println("The return values should be the same");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getResourcePaths(String) method
   */
  public void getResourcePaths(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();
    Iterator resources = null;
    String tmp = null;

    String path = "/WEB-INF/";
    Set realPath = context.getResourcePaths(path);
    pw.println("GetResourcePathsTest: Start test for path=" + path);
    if (realPath != null) {
      if (!realPath.isEmpty()) {
        resources = realPath.iterator();

        tmp = (String) resources.next();
        pw.println("GetResourcePathsTest: " + tmp);
        while (resources.hasNext()) {
          pw.println("GetResourcePathsTest: " + resources.next());
        }
      } else {
        passed = false;
      }
    } else {
      passed = false;
      pw.println("GetResourcePathsTest: returned null resourcepath");
    }

    path = "/WEB-INF/classes/servlet/tck/api/jakarta_servlet/servletcontext/";
    realPath = context.getResourcePaths(path);
    pw.println("GetResourcePathsTest: Start test for path=" + path);
    if (realPath != null) {
      if (!realPath.isEmpty()) {
        resources = realPath.iterator();

        tmp = (String) resources.next();
        pw.println("GetResourcePathsTest: " + tmp);
        while (resources.hasNext()) {
          pw.println("GetResourcePathsTest: " + resources.next());
        }
      } else {
        passed = false;
        pw.println("Error: missing contents");
      }
    } else {
      passed = false;
      pw.println("Error: null resourcepath");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Test for getResourceAs Stream method
   */
  public void getResourceAsStream(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    // get this servlet itself

    String path = "/WEB-INF/web.xml";

    InputStream in = context.getResourceAsStream(path);

    if (in != null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getResourceAsStream(" + path + ") returned a null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getResourceAsStream(String)
   */
  /**
   * We will supply a non existent URL and should get null
   */
  public void getResourceAsStream_1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    // dummy URL

    String path = "/dummy";

    InputStream in = context.getResourceAsStream(path);

    if (in == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getResourceAsStream(" + path + ") returned a non-null ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * We will use this servlet itself as a resource
   */
  public void getResource(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String path = "/WEB-INF/web.xml";

    URL resourceURL = context.getResource(path);

    if (resourceURL != null) {
      String result = resourceURL.toString();

      if (result.contains(path)) {
        passed = true;
      } else {
        passed = false;
        pw.println("Could not locate " + path + " in the returned URL");
        pw.println("Actual result = " + result + " ");
      }
    } else {
      passed = false;
      pw.println("getResource(" + path + ") returned a null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getResourse(String) method We will give
   * a fake URL we should get null
   */
  public void getResource_1(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String path = "/doesnotexist";

    URL resourceURL = context.getResource(path);

    if (resourceURL == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("ServletContext.getResource(" + path
          + ") did not return correct result");
      pw.println("Expected result = null");
      pw.println("Actual result = |" + resourceURL + "|");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getResource(String path) We get
   * MalformedURLException if path does not start with /.
   */
  public void getResource_2(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    try {
      URL resourceURL = context.getResource("invalid/contextPath");

      passed = false;
      pw.println("Test FAILED.  Expected a MalformedURLException to "
          + "be thrown when ServletContext.getResource(String) "
          + " is provided a value that does not start with a '/'");
    } catch (MalformedURLException ex) {
      passed = true;
      pw.println("GetResource_2Test " + Data.PASSED);
      pw.println("Expected java.net.MalformedURLException to be thrown");
    } catch (Throwable t) {
      passed = false;
      pw.println("GetResource_2Test " + Data.FAILED);
      pw.println("Unexpected Exception is thrown: " + t.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getAttribute(String) method
   */
  public void servletContextGetAttribute(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();
    String param = "ManKind";
    String param2 = "humane";
    // set attribute
    context.setAttribute(param, param2);

    // then get attribute
    Object attr = context.getAttribute(param);

    if (attr != null) {
      // attr should also be an instance of java.lang.String
      if ("java.lang.String".equals(attr.getClass().getName())) {
        String sAttr = (String) attr;

        if (sAttr.equals(param2)) {
          passed = true;
        } else {
          passed = false;
          pw.println("getAttribute(" + param + ") returned incorrect results");
          pw.println("Expected result = " + param2 + " ");
          pw.println("Actual result = |" + sAttr + "| ");
        }
      } else {
        passed = false;
        pw.println("getAttribute(" + param
            + ") did not return an attribute of type String");
      }
    } else {
      passed = false;
      pw.println("getAttribute(" + param + ") returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getAttribute(String) method
   */
  /**
   * We'll try to get the associated value for a non existent attribute name. We
   * should get null as the result
   */
  public void servletContextGetAttribute_1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();
    Object attr = context.getAttribute("Womankind");

    if (attr == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getAttribute(String) did not return null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getContext(String) method
   */
  public void servletContextGetContext(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    String alternateServlet = request.getServletContext().getContextPath()
        + "/GetNamedDispatcherTest";

    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    // we expect ServletContext object that corresponds to the named URL or Null
    ServletContext context2 = context.getContext(alternateServlet);

    if ((context2 == context) || (context2 == null)) {
      passed = true;
    } else {
      passed = false;
      pw.println("getContext(String) returned incorrect result");
      pw.println(
          "Expected getServletContext(String) to return the same context for another servlet ");
      pw.println(
          " in the same context as the current test servlet, or to return a null value.");
      pw.println(
          "Context returned from getServletConfig().getServletContext(): "
              + context);
      pw.println(
          "Context returned from context.getContext(String): " + context2);
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getInitParameterNames() method
   */
  public void servletContextGetInitParameterNames(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = true;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    // We attributed VI to EDITOR and OS to ULTASPARC

    String expectedResult1 = "EDITOR";
    boolean expectedResult1Found = false;
    String expectedResult2 = "OS";
    boolean expectedResult2Found = false;
    Enumeration initP = context.getInitParameterNames();

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

      if (!expectedResult1Found && expectedResult2Found) {
        passed = false;
        pw.println(
            "getInitParameterNames() method did not return all the init parameters");
      }
    } else {
      passed = false;
      pw.println("getInitParameterNames() returned and empty enumeration");
    }

    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getInitParameter(String) method
   */
  public void servletContextGetInitParameter(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    // Init parameters specified in web.xml for
    // ServletContextGetInitParameterTestServlet servlet
    String param = "EDITOR";
    String expectedResult = "VI";
    String result = context.getInitParameter(param);

    if (result != null) {
      if ("VI".equals(result)) {
        passed = true;
      } else {
        passed = false;
        pw.println("getInitParameter(String) gave incorrect results");
        pw.println("Expected result = " + expectedResult + " ");
        pw.println("Actual result = |" + result + "| ");
      }
    } else {
      passed = false;
      pw.println("getInitParameter(" + param + ") returned a null");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Negative Test for ServletContext.getInitParameter(String) method
   */
  public void servletContextGetInitParameterNull(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletContext context = getServletContext();

    String initParam = context
        .getInitParameter("nothing_is_set_here_negative_compatibility_test");

    if (initParam == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getInitParameter(String)  returned incorrect result");
      pw.println("Expected result = null ");
      pw.println("Actual result =|" + initParam + "| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.RemoveAttribute(String) method
   */
  public void servletContextRemoveAttribute(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    String param = "Mankind";
    // first set attribute
    context.setAttribute(param, "humane");

    // try removing it
    context.removeAttribute(param);

    Object result = null;
    // try getting it,should get null
    result = context.getAttribute(param);

    if (result == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("removeAttribute(" + param + ") returned incorrect result ");
      pw.println("Expected result = null ");
      pw.println("Actual result = |" + result + "| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for setAttribute(String,Object) method
   */
  public void servletContextSetAttribute(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();

    ServletContext context = config.getServletContext();

    // set and get
    String param1 = "Mankind";
    String param2 = "humane";

    context.setAttribute(param1, param2);

    Object attr = context.getAttribute(param1);

    if (attr != null) {
      if ("java.lang.String".equals(attr.getClass().getName())) {
        String sAttr = (String) attr;

        if (sAttr.equals(param2)) {
          passed = true;
        } else {
          passed = false;
          pw.println("setAttribute(" + param1 + "," + param2
              + ") did not set the attribute properly");
          pw.println("Expected result = " + param2 + " ");
          pw.println("Actual result = |" + sAttr + "| ");
        }
      } else {
        passed = false;
        pw.println("setAttribute(" + param1 + "," + param2
            + ") did not set an attribute of type String properly");
      }

    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletContextSetAttribute_1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String param1 = "Mankind";
    String param2 = "humane";
    String param3 = "being";

    // set Attribute param1 twice with param2 and param3 respectively
    context.setAttribute(param1, param2);
    context.setAttribute(param1, param3);

    Object attr = context.getAttribute(param1);

    if (attr != null) {
      if ("java.lang.String".equals(attr.getClass().getName())) {
        String sAttr = (String) attr;

        if (sAttr.equals(param3)) {
          passed = true;
        } else if (sAttr.equals(param2)) {
          passed = false;
          pw.println("Failed to override set value");
        } else {
          passed = false;
          pw.println("Expected result = " + param3 + " ");
          pw.println("Actual result = |" + sAttr + "| ");
        }
      } else {
        passed = false;
        pw.println("setAttribute(" + param1 + "," + param2 + ") "
            + "didnot set an attribute of type String properly");
      }
    } else {
      passed = false;
      pw.println("setAttribute(String, Object) " + "didnot set attribute");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletContextSetAttribute_2(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String param = "Mankind";

    // first set attribute to null
    context.setAttribute(param, null);

    Object result = null;
    // try getting it,should get null
    result = context.getAttribute(param);

    if (result == null) {
      passed = true;
    } else {
      passed = false;
      pw.println("getAttribute(" + param + ") returned incorrect result ");
      pw.println("Expected result = null ");
      pw.println("Actual result = |" + result + "| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void servletContextGetAttributeNames(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    // first we have to Set Attributes

    context.setAttribute("Chef", "expert");
    context.setAttribute("chief", "commanding");

    // then get Attributes
    int count = 0;
    String expectedResult1 = "Chef";
    boolean expectedResult1Found = false;
    String expectedResult2 = "chief";
    boolean expectedResult2Found = false;
    int expectedCount = 2;
    Enumeration attrs = context.getAttributeNames();

    if (attrs.hasMoreElements()) {
      Vector v = new Vector();

      while (attrs.hasMoreElements()) {
        String name = (String) attrs.nextElement();

        if (name.equals(expectedResult1)) {
          if (!expectedResult1Found) {
            count++;
            expectedResult1Found = true;
          } else {
            passed = false;
            pw.println(
                "getAttributeNames() method return an attribute name twice ");
            pw.println("    The attribute already specified was "
                + expectedResult1 + " ");
          }
        } else if (name.equals(expectedResult2)) {
          if (!expectedResult2Found) {
            count++;
            expectedResult2Found = true;
          } else {
            passed = false;
            pw.println(
                "getAttributeNames() method return an attribute name twice ");
            pw.println("    The attribute already specified was "
                + expectedResult2 + " ");
          }
        } else {
          v.add(name);
        }
      }

      if (count != expectedCount) {
        passed = false;
        pw.println(
            "getAttributeNames() method did not return the correct number of init parameters");
        pw.println("Expected count = " + expectedCount);
        pw.println("Actual count = " + count);
        pw.println("The expected attribute names received were :");

        if (expectedResult1Found) {
          pw.println(expectedResult1);
        }

        if (expectedResult2Found) {
          pw.println(expectedResult2);
        }

        pw.println("    Other attribute names received were :");

        for (int i = 0; i <= v.size() - 1; i++) {
          pw.println("" + v.elementAt(i).toString());
        }
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getAttributeNames() returned an empty enumeration");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getRequestDispatcher(String) method
   */
  public void servletContextGetRequestDispatcher(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    /**
     * We will try to get the RequestDispatcher for this servlet itself
     */
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    String path = "/WEB-INF/web.xml";

    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    RequestDispatcher rd = context.getRequestDispatcher(path);

    if (rd != null) {
      passed = true;
    } else {
      passed = false;
      pw.println(
          "getRequestDispatcher(" + path + ") returned incorrect result ");
      pw.println("Expected result != null ");
      pw.println("Actual result = |null| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test for ServletContext.getNamedDispatcher(String) method
   */
  /**
   * We will try to get the RequestDispatcher for the servlet Registered as
   * config
   */
  public void getNamedDispatcher(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    String path = "/GetNamedDispatcherTest";
    RequestDispatcher rd = getServletContext().getRequestDispatcher(path);
    rd.forward(request, response);
  }

  /**
   * Negative test for ServletContext.getNamedDispatcher(String) method
   */
  public void getNamedDispatcher_1(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();

    String path = "/DoesNotExistGetNamedDispatcherTest";
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    RequestDispatcher tmp = context.getNamedDispatcher(path);
    if (tmp != null) {
      passed = false;
      pw.println("non-null return");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * Test For getServerInfo mehotd
   */
  public void getServerInfo(ServletRequest request, ServletResponse response)
      throws ServletException, IOException {

    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String info = context.getServerInfo();

    // it just needs to be a not null value
    if (info != null) {
      passed = true;
      pw.println("ServerInfo = " + info);
    } else {
      passed = false;
      pw.println("getServerInfo() returned a null ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  /**
   * A Test for ServletContext.getServletContextName() method
   */
  public void getServletNameTest(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    String name = context.getServletContextName();

    if ("SerJaxSerServletContext".equals(name)) {
      passed = true;
      pw.println("ame = " + name);
    } else {
      passed = false;
      pw.println("getServletContextName() returned " + name);
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getServletTempDir(ServletRequest request,
      ServletResponse response) throws ServletException, IOException {
    boolean passed = false;
    PrintWriter pw = response.getWriter();
    ServletConfig config = this.getServletConfig();
    ServletContext context = config.getServletContext();

    try {
      File tmp = (File) context
          .getAttribute("jakarta.servlet.context.tempdir");
      if (tmp != null) {
        pw.println("jakarta.servlet.context.tempdir=" + tmp.getAbsolutePath());
        if (tmp.exists()) {
          passed = true;
        } else {
          passed = false;
          pw.println(
              "jakarta.servlet.context.tempdir points to a non-existing dir");
        }
      } else {
        passed = false;
        pw.println("jakarta.servlet.temp.dir attribute value is null");
      }
    } catch (Exception ex) {
      passed = false;
      pw.println(
          "Got Exception when retrieving jakarta.servlet.temp.dir attribute:"
              + ex.toString());
    }
    ServletTestUtil.printResult(pw, passed);
  }
}
