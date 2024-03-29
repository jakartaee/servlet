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

package servlet.tck.common.request;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.util.Data;

public class RequestClient extends AbstractTckTest {

  
  public void getAttributeNamesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeNamesTest");
    invoke();
  }

  
  public void getAttributeNamesEmptyEnumTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeNamesEmptyEnumTest");
    invoke();
  }

  
  public void getAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeTest");
    invoke();
  }

  
  public void getAttributeDoesNotExistTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeDoesNotExistTest");
    invoke();
  }

  
  public void getCharacterEncodingTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
        "Content-Type:text/plain; charset=ISO-8859-1");
    invoke();
  }

  
  public void getContentLengthTest() throws Exception {
    String testName = "getContentLengthTest";
    TEST_PROPS.get().setProperty(REQUEST, "POST " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/plain");
    TEST_PROPS.get().setProperty(CONTENT, "calling getContentLengthTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getContentTypeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/plain");
    invoke();
  }

  
  public void getContentTypeNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getContentTypeNullTest");
    invoke();
  }

  
  public void getInputStreamTest() throws Exception {
    String testName = "getInputStreamTest";
    TEST_PROPS.get().setProperty(REQUEST, "POST " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/plain");
    TEST_PROPS.get().setProperty(CONTENT, "calling getInputStreamTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getInputStreamIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getInputStreamIllegalStateExceptionTest");
    invoke();
  }

  
  public void getParameterValuesDoesNotExistTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getParameterValuesDoesNotExistTest");
    invoke();
  }

  
  public void getLocaleTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocaleTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Accept-Language:en-US");
    invoke();
  }

  
  public void getLocaleDefaultTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocaleDefaultTest");
    invoke();
  }

  
  public void getLocalesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocalesTest");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Accept-Language:en-US,en-GB");
    invoke();
  }

  
  public void getLocalesDefaultTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getLocalesDefaultTest");
    invoke();
  }

  
  public void getParameterMapTest() throws Exception {
    String testName = "getParameterMapTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&parameter1=value1&parameter2=value2 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getParameterNamesTest() throws Exception {
    String testName = "getParameterNamesTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&parameter1=value1&parameter2=value2 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getParameterNamesEmptyEnumTest() throws Exception {
    String testName = "getParameterNamesEmptyEnumTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getParameterTest() throws Exception {
    String testName = "getParameterTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&parameter1=value1 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getParameterDoesNotExistTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getParameterDoesNotExistTest");
    invoke();
  }

  
  public void getParameterValuesTest() throws Exception {
    String testName = "getParameterValuesTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&Names=value1&Names=value2 HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getProtocolTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getProtocolTest");
    invoke();
  }

  
  public void getReaderTest() throws Exception {
    String testName = "getReaderTest";
    TEST_PROPS.get().setProperty(REQUEST, "POST " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/plain");
    TEST_PROPS.get().setProperty(CONTENT, "calling getReaderTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getReaderIllegalStateExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getReaderIllegalStateExceptionTest");
    invoke();
  }

  
  public void getReaderUnsupportedEncodingExceptionTest() throws Exception {
    String testName = "getReaderUnsupportedEncodingExceptionTest";
    TEST_PROPS.get().setProperty(REQUEST,
        "POST " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
        "Content-Type:text/plain; charset=DoesNonExist");
    TEST_PROPS.get().setProperty(CONTENT,
        "calling getReaderUnsupportedEncodingExceptionTest");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getRemoteAddrTest() throws Exception {
    String localIps = getLocalInterfaceInfo(true);
    String testName = "getRemoteAddrTest";
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&Address=" + localIps + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getLocalAddrTest() throws Exception {
    String testName = "getLocalAddrTest";
    TEST_PROPS.get().setProperty(APITEST, testName);
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getRemoteHostTest() throws Exception {
    String testName = "getRemoteHostTest";
    String localIps = getLocalInterfaceInfo(true);
    String localHosts = getLocalInterfaceInfo(false);
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&Address=" + localIps + "," + localHosts
            + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getRequestDispatcherTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getRequestDispatcherTest");
    invoke();
  }

  
  public void getSchemeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSchemeTest");
    invoke();
  }

  
  public void getServerNameTest() throws Exception {
    String testName = "getServerNameTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&hostname=" + _hostname + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void getServerPortTest() throws Exception {
    String testName = "getServerPortTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&port=" + _port + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void isSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "isSecureTest");
    invoke();
  }

  
  public void removeAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "removeAttributeTest");
    invoke();
  }

  
  public void setAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeTest");
    invoke();
  }

  
  public void getCharacterEncodingNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingNullTest");
    invoke();
  }

  
  public void setCharacterEncodingUnsupportedEncodingExceptionTest()
      throws Exception {
    String testName = "setCharacterEncodingUnsupportedEncodingExceptionTest";
    TEST_PROPS.get().setProperty(REQUEST,
        "POST " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void setCharacterEncodingTest() throws Exception {
    String testName = "setCharacterEncodingTest";
    TEST_PROPS.get().setProperty(REQUEST,
        "POST " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }

  
  public void setCharacterEncodingTest1() throws Exception {
    String testName = "setCharacterEncodingTest1";
    TEST_PROPS.get().setProperty(APITEST, testName);
    invoke();
  }

  
  public void getLocalNameTest() throws Exception {
    String testName = "getLocalNameTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&hostname=" + _hostname + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);

    invoke();
  }

  
  public void getLocalPortTest() throws Exception {
    String testName = "getLocalPortTest";
    TEST_PROPS.get().setProperty(TEST_NAME, testName);
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + getServletName() + "?testname="
            + testName + "&hostport=" + _port + " HTTP/1.1");
    TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
    invoke();
  }
}
