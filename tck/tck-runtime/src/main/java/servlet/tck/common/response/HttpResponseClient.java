/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.common.response;

import servlet.tck.common.util.Data;

public class HttpResponseClient extends ResponseClient {

  
  public void addCookieTest() throws Exception {
    String testName = "addCookieTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "set-Cookie:cookie1=value1|set-Cookie:cookie2=value2");
    invoke();
  }

  
  public void addDateHeaderTest() throws Exception {
    String testName = "addDateHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "DateInfo:Sat, 25 Apr 1970 07:29:03 GMT");
    invoke();
  }

  
  public void addHeaderTest() throws Exception {
    String testName = "addHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "header1:value1|header1:value11|header2:value2");
    invoke();
  }

  
  public void addIntHeaderTest() throws Exception {
    String testName = "addIntHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "intHeader1:1|intHeader1:11|intHeader2:2");
    invoke();
  }

  
  public void containsHeaderTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "containsHeaderTest");
    invoke();
  }

  
  public void sendErrorClearBufferTest() throws Exception {
    String testName = "sendErrorClearBufferTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, GONE);
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH,
        "THIS TEXT SHOULD NOT APPEAR");
    invoke();
  }

  
  public void sendErrorIllegalStateExceptionTest() throws Exception {
    String testName = "sendErrorIllegalStateExceptionTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "THIS TEXT SHOULD APPEAR|" + Data.PASSED);
    invoke();
  }

  
  public void sendError_StringTest() throws Exception {
    String testName = "sendError_StringTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, GONE);
    TEST_PROPS.get().setProperty(SEARCH_STRING, "in sendError_StringTest servlet");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Content-Type:text/html|header:sendError_StringTest|set-Cookie:cookie1=value1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH,
        "THIS TEXT SHOULD NOT APPEAR");
    invoke();
  }

  
  public void sendError_StringIllegalStateExceptionTest() throws Exception {
    String testName = "sendError_StringIllegalStateExceptionTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "THIS TEXT SHOULD APPEAR|" + Data.PASSED);
    invoke();
  }

  
  public void sendError_StringErrorPageTest() throws Exception {
    String testName = "sendError_StringErrorPageTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(STATUS_CODE, "411");
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "Status Code: 411");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH,
        "THIS TEXT SHOULD NOT APPEAR");
    invoke();
  }

  
  public void sendRedirectWithLeadingSlashTest() throws Exception {
    String testName = "sendRedirectWithLeadingSlashTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Location: http://" + _hostname + ":" + _port + "/RedirectedTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, MOVED_TEMPORARY);
    invoke();
  }

  
  public void sendRedirectWithoutLeadingSlashTest() throws Exception {
    String testName = "sendRedirectWithoutLeadingSlashTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Location: http://" + _hostname
        + ":" + _port + "" + getContextRoot() + "/RedirectedTest");
    TEST_PROPS.get().setProperty(STATUS_CODE, MOVED_TEMPORARY);
    invoke();
  }

  
  public void sendRedirectIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "sendRedirectIllegalStateExceptionTest");
    invoke();
  }

  
  public void setDateHeaderTest() throws Exception {
    String testName = "setDateHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "DateInfo:Sat, 25 Apr 1970 07:29:03 GMT");
    invoke();
  }

  
  public void setDateHeaderOverrideTest() throws Exception {
    String testName = "setDateHeaderOverrideTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "DateInfo:Sat, 25 Apr 1970 07:29:04 GMT");
    invoke();
  }

  
  public void setHeaderTest() throws Exception {
    String testName = "setHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "header:value1");
    invoke();
  }

  
  public void setHeaderOverrideTest() throws Exception {
    String testName = "setHeaderOverrideTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "header:value2");
    invoke();
  }

  
  public void setMultiHeaderTest() throws Exception {
    String testName = "setMultiHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "header:value3");
    invoke();
  }

  
  public void setIntHeaderTest() throws Exception {
    String testName = "setIntHeaderTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "intHeader:2");
    invoke();
  }

  
  public void setStatusTest() throws Exception {
    String testName = "setStatusTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();
  }

  
  public void setStatusTest1() throws Exception {
    String testName = "setStatusTest1";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(STATUS_CODE, NOT_FOUND);
    invoke();
  }

  /*
   * Servlet 3.0 tests
   */
  
  public void getHeadersTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeadersTest");
    invoke();
  }

  
  public void getHeaderTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeaderTest");
    invoke();
  }

  
  public void getHeaderNamesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHeaderNamesTest");
    invoke();
  }

  
  public void getStatusTest() throws Exception {
    String testName = "getStatusTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }
}
