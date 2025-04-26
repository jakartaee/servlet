/*
 *
 *  *
 *  * The Apache Software License, Version 1.1
 *  *
 *  * Copyright (c) 2001, 2024 Oracle and/or its affiliates and others.
 *  * All rights reserved.
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
 *  *    any, must include the following acknowlegement:
 *  *       "This product includes software developed by the
 *  *        Apache Software Foundation (http://www.apache.org/)."
 *  *    Alternately, this acknowlegement may appear in the software itself,
 *  *    if and wherever such third-party acknowlegements normally appear.
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
 */

package servlet.tck.api.jakarta_servlet_http.cookie;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import servlet.tck.common.servlets.HttpTCKServlet;
import servlet.tck.common.util.ServletTestUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TestServlet extends HttpTCKServlet {

  private static final long serialVersionUID = 1L;

  private static final String EMPTY_STRING = "";

  public static String CUSTOM_HEADER_DATE_FORMAT = "yyyy-MM-dd HH:mm";

  public void cloneTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;

    Cookie testCookie = new Cookie("cookie1", "value1");
    String cookieName = testCookie.getName();

    Cookie testCookieclone = (Cookie) testCookie.clone();
    String cloneName = testCookieclone.getName();

    if (!cloneName.equals(cookieName)) {
      passed = false;
      pw.println("Expected = " + cookieName);
      pw.println("Actual =" + cloneName);
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void constructorTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;

    String name1 = "name1";
    String value1 = "value1";
    Cookie testCookie = new Cookie(name1, value1);

    String cookieName = testCookie.getName();
    String cookieValue = testCookie.getValue();

    if ((!cookieName.equals(name1)) || (!cookieValue.equals(value1))) {
      passed = false;
      pw.println("Expected = " + name1 + "," + value1);
      pw.println("Actual = " + cookieName + "," + cookieValue);
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void constructorIllegalArgumentExceptionTest(
      @SuppressWarnings("unused") HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String[] invalidNameValues = { ",test", ";test", " test", "\ttest", "\ntest" };

    PrintWriter pw = response.getWriter();

    for (int i = 0; i < invalidNameValues.length; i++) {
      pw.println("Attempting to create new Cookie with invalid name "
          + "value: '" + invalidNameValues[i] + "'");
      try {
        @SuppressWarnings("unused")
        Cookie c = new Cookie(invalidNameValues[i], "someValue");
        pw.println("Test FAILED.  IllegalArgumentException not thrown"
            + " for invalid name value.");
      } catch (Throwable t) {
        if (!(t instanceof IllegalArgumentException)) {
          pw.println("Test FAILED.  Exception thrown by Cookie "
              + "constructor, but it wasn't an instance of IllegalArgumentExcepiton.");
          pw.println("Actual Exception type: " + t.getClass().getName());
        } else {
          pw.println("IllegalArgumentException thrown for invalid value: '"
              + invalidNameValues[i] + "'");
        }
      }
      pw.println();
    }
  }

  public void getDomainTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    // set and get
    String expectedResult = "foo.bar";
    testCookie.setDomain(expectedResult);
    String result = testCookie.getDomain();

    response.addCookie(testCookie);
    if (expectedResult.equals(result)) {
      passed = true;
    } else {
      passed = false;
      pw.println("getDomain() returned an incorrect result");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getMaxAgeTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = true;
    Cookie testCookie = new Cookie("name1", "value1");
    int expectedResult = 300;
    // set and get
    testCookie.setMaxAge(expectedResult);
    int result = testCookie.getMaxAge();

    if (result != expectedResult) {
      passed = false;
      pw.println("getMaxAge() returned an incorrect result");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");

    }
    pw.println("default test");
    testCookie = new Cookie("name2", "value1");
    expectedResult = -1;
    // we expect a negative value
    result = testCookie.getMaxAge();

    if (result != expectedResult) {
      passed = false;
      pw.println("getMaxAge() returned an incorrect result");
      pw.println("Expected = " + expectedResult + "");
      pw.println("Actual = |" + result + "|");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getNameTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie[] cookie = request.getCookies();
    int index = ServletTestUtil.findCookie(cookie, "name1");
    if (index < 0) {
      passed = false;
      pw.println("Error: The expected cookie was not received from the client");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getPathTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    // set and get
    String expectedResult = request.getContextPath();
    testCookie.setPath(expectedResult);
    String result = testCookie.getPath();

    response.addCookie(testCookie);
    if (expectedResult.equals(result)) {
      passed = true;
    } else {
      passed = false;
      pw.println("getPath() returned an incorrect result");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getSecureTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = false;
    boolean result = testCookie.getSecure();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("getSecure() returned an incorrect result");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributeSecureTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    String result = testCookie.getAttribute("secure");

    response.addCookie(testCookie);
    if (result != null) {
      passed = false;
      pw.println("getAttribute(\"Secure\") returned non-null result");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getValueTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    doGetValueTest(request, response, "value1");
  }

  public void getValueQuotedTest(HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    doGetValueTest(request, response, "\"value1\"");
  }

  private void doGetValueTest(HttpServletRequest request,
	      HttpServletResponse response, String expectedResult) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie[] cookie = request.getCookies();
    int index = ServletTestUtil.findCookie(cookie, "name1");
    if (index >= 0) {
      String result = cookie[index].getValue();
      if (result != null) {
        if (!result.equals(expectedResult)) {
          passed = false;
          pw.println("getValue() returned an incorrect result ");
          pw.println("Expected = " + expectedResult + " ");
          pw.println("Actual = |" + result + "| ");
        } else {
          passed = true;
        }
      } else {
        passed = false;
        pw.println("Error: getValue() returned a null result");
      }
    } else {
      passed = false;
      pw.println("Error: The expected cookie was not received from the client");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setDomainTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    String expectedResult = "ENG.COM";
    testCookie.setDomain(expectedResult);
    String result = testCookie.getDomain();

    response.addCookie(testCookie);
    if (result != null) {
      if (!result.equalsIgnoreCase(expectedResult)) {
        passed = false;
        pw.println("setDomain(" + expectedResult
            + ") did not set the domain properly ");
        pw.println("Expected = " + expectedResult + " ");
        pw.println("Actual = |" + result + "| ");
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getDomain() returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setMaxAgePositiveTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    Cookie testCookie = new Cookie("name1", "value1");

    testCookie.setMaxAge(2);
    response.addCookie(testCookie);

    // Use a custom format to ensure Locale independence
    SimpleDateFormat sdf = new SimpleDateFormat(CUSTOM_HEADER_DATE_FORMAT);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date currDate = new Date();
    String dateString = sdf.format(currDate);
    response.addHeader("testDate", dateString);

    ServletTestUtil.printResult(pw, true);
  }

  public void setMaxAgeZeroTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    Cookie testCookie = new Cookie("name1", "value1");

    testCookie.setMaxAge(0);
    response.addCookie(testCookie);

    ServletTestUtil.printResult(pw, true);
  }

  public void setMaxAgeNegativeTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    Cookie testCookie = new Cookie("name1", "value1");

    testCookie.setMaxAge(-1);
    response.addCookie(testCookie);

    ServletTestUtil.printResult(pw, true);
  }

  public void setPathTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    String expectedResult = "\"/servlet_jsh_cookie_web\"";
    testCookie.setPath(expectedResult);
    String result = testCookie.getPath();

    response.addCookie(testCookie);
    if (result != null) {
      if (!result.equals(expectedResult)) {
        passed = false;
        pw.println(
            "setPath(" + expectedResult + ") returned an incorrect result");
        pw.println("Expected = " + expectedResult + " ");
        pw.println("Actual = |" + result + "| ");
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getPath() returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setSecureTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = true;
    testCookie.setSecure(expectedResult);
    boolean result = testCookie.getSecure();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("getSecure() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeSecureTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = true;
    testCookie.setAttribute("secure", EMPTY_STRING);
    boolean result = testCookie.getSecure();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("getSecure() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeSecureInvalidTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = false;
    testCookie.setAttribute("secure", "other");
    boolean result = testCookie.getSecure();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("getSecure() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setValueTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    String expectedResult = "value2";
    testCookie.setValue(expectedResult);
    String result = testCookie.getValue();

    response.addCookie(testCookie);
    if (result != null) {
      if (!result.equals(expectedResult)) {
        passed = false;
        pw.println(
            "setValue(" + expectedResult + ") did not set the value properly");
        pw.println("Expected = " + expectedResult + " ");
        pw.println("Actual = |" + result + "| ");
      } else {
        passed = true;
      }
    } else {
      passed = false;
      pw.println("getValue() returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = true;
    Cookie testCookie = new Cookie("name1", "value1");

    String attrName = "some-name";
    String attrValue = "some-value";
    testCookie.setAttribute(attrName, attrValue);
    String result = testCookie.getAttribute(attrName);

    response.addCookie(testCookie);
    if (result != null) {
      if (!result.equalsIgnoreCase(attrValue)) {
        passed = false;
        pw.println("setAttribute(" + attrName + "," + attrValue +
            ") did not set the attribute properly ");
        pw.println("Expected value = " + attrValue + " ");
        pw.println("Actual value = |" + result + "| ");
      }
    } else {
      passed = false;
      pw.println("getAttribute(" + attrName + ") returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributesTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = true;
    String name = "name1";
    String value = "value1";
    Cookie testCookie = new Cookie(name, value);

    String attrName = "some-name";
    String attrValue = "some-value";
    testCookie.setAttribute(attrName, attrValue);
    Map<String,String> result = testCookie.getAttributes();

    response.addCookie(testCookie);
    if (result != null) {
      if (result.size() == 1) {
        if (!result.get(attrName).equals(attrValue)) {
          passed = false;
          pw.println("getAttributes() returned a map that contained [" + result.get(attrName) +
              "] as the value for key [" + attrName + "] rather than [" + attrValue + "]");
        }
      } else {
        passed = false;
        pw.println("getAttributes() returned a map of size [" + result.size() + "] rather than 1.");
      }
    } else {
      passed = false;
      pw.println("getAttributes() returned a null result ");
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getHttpOnlyTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = false;
    boolean result = testCookie.isHttpOnly();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("isHttpOnly() returned an incorrect result");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void getAttributeHttpOnlyTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    String result = testCookie.getAttribute("httponly");

    response.addCookie(testCookie);
    if (result != null) {
      passed = false;
      pw.println("getAttribute(\"HttpOnly\") returned non-null result");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setHttpOnlyTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = true;
    testCookie.setHttpOnly(expectedResult);
    boolean result = testCookie.isHttpOnly();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("isHttpOnly() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeHttpOnlyTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = true;
    testCookie.setAttribute("httponly", EMPTY_STRING);
    boolean result = testCookie.isHttpOnly();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("isHttpOnly() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setAttributeHttpOnlyInvalidTest(@SuppressWarnings("unused") HttpServletRequest request,
	      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    boolean passed = false;
    Cookie testCookie = new Cookie("name1", "value1");

    boolean expectedResult = false;
    testCookie.setAttribute("httponly", "other");
    boolean result = testCookie.isHttpOnly();

    response.addCookie(testCookie);
    if (result != expectedResult) {
      passed = false;
      pw.println("isHttpOnly() returned an incorrect result ");
      pw.println("Expected = " + expectedResult + " ");
      pw.println("Actual = |" + result + "| ");
    } else {
      passed = true;
    }
    ServletTestUtil.printResult(pw, passed);
  }

  public void setEmptyAttributeTest(@SuppressWarnings("unused") HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    PrintWriter pw = response.getWriter();
    Cookie testCookie = new Cookie("name1", "value1");

    testCookie.setAttribute("EmptyAttribute", EMPTY_STRING);
    response.addCookie(testCookie);

    ServletTestUtil.printResult(pw, true);
  }
}
