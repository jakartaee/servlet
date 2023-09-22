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

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.util.Data;

public class ResponseClient extends AbstractTckTest {

  
  public void flushBufferTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "flushBufferTest");
    invoke();
  }

  
  public void getBufferSizeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getBufferSizeTest");
    invoke();
  }

  
  public void getLocaleDefaultTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocaleDefaultTest");
    invoke();
  }

  
  public void getLocaleTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocaleTest");
    invoke();
  }

  
  public void getOutputStreamTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getOutputStreamTest");
    invoke();
  }

  
  public void getOutputStreamFlushTest() throws Exception {
    String testName = "getOutputStreamFlushTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING,
        "in getOutputStreamFlushTest|" + Data.PASSED);
    invoke();
  }

  
  public void getOutputStreamIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getOutputStreamIllegalStateExceptionTest");
    invoke();
  }

  
  public void getWriterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getWriterTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
        "Content-Type: text/html;charset=ISO-8859-1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Type: text/html");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getWriterFlushTest() throws Exception {
    String testName = "getWriterFlushTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "in test|" + Data.PASSED);
    invoke();
  }

  
  public void getWriterAfterTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getWriterAfterTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
        "Content-Type: text/html;charset=ISO-8859-1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Content-Type: text/html;charset=ISO-8859-7");
    invoke();
  }

  
  public void getWriterIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getWriterIllegalStateExceptionTest");
    invoke();
  }

  
  public void getWriterUnsupportedEncodingExceptionTest() throws Exception {
    String testName = "getWriterUnsupportedEncodingExceptionTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    invoke();
    testName = "checkTestResult";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void isCommittedTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "isCommittedTest");
    invoke();
  }

  
  public void resetBufferTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "resetBufferTest");
    invoke();
  }

  
  public void resetTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "resetTest");
    invoke();
  }

  
  public void resetTest1() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "resetTest1");
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS,
        "Content-Type: application/java-archive; charset=Shift_Jis");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "BigNoNo");
    TEST_PROPS.get().setProperty(SEARCH_STRING, "YesPlease");
    invoke();
  }

  
  public void resetIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "resetIllegalStateExceptionTest");
    invoke();
  }

  
  public void getCharacterEncodingTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingTest");
    invoke();
  }

  
  public void getCharacterEncodingDefaultTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingDefaultTest");
    invoke();
  }

  
  public void setCharacterEncodingTest() throws Exception {
    String testName = "setCharacterEncodingTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
        "Content-Type: text/html;charset=ISO-8859-1");
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Content-Type: text/html;charset=ISO-8859-7");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void setBufferSizeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setBufferSizeTest");
    invoke();
  }

  
  public void setBufferSizeIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setBufferSizeIllegalStateExceptionTest");
    invoke();
  }

  
  public void setContentLengthTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setContentLengthTest");
    int lenn = Data.PASSED.length();
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Length:" + lenn);
    invoke();
  }

  
  public void getContentTypeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/html");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Content-Type:text/html;charset=ISO-8859-1");
    invoke();
  }

  
  public void getContentType1Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentType1Test");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Type:text/html");
    invoke();
  }

  
  public void getContentTypeNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeNullTest");
    invoke();
  }

  
  public void getContentTypeNull1Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeNull1Test");
    invoke();
  }

  
  public void getContentTypeNull2Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeNull2Test");
    invoke();
  }

  
  public void setContentTypeTest() throws Exception {
    String testName = "setContentTypeTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Type:text/html");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
  }

  
  public void setContentType1Test() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setContentType1Test");
    invoke();
  }

  
  public void setContentType2Test() throws Exception {
    String testName = "setContentType2Test";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Type:text/html");
    TEST_PROPS.get().setProperty(UNEXPECTED_HEADERS, "Content-Type:text/plain");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
  }

  
  public void setLocaleTest() throws Exception {
    String testName = "setLocaleTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Content-Language:en-US");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
  }

  
  public void setLocale1Test() throws Exception {
    String testName = "setLocale1Test";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
        "Content-Type:text/html;charset=Shift_Jis");
    TEST_PROPS.get().setProperty(IGNORE_BODY, "true");
    invoke();
  }
}
