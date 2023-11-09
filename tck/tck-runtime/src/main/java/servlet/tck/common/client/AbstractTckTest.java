/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.common.client;

import servlet.tck.common.request.HttpExchange;
import servlet.tck.common.request.WebTestCase;
import servlet.tck.common.util.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

/**
 * Base client for Servlet tests.
 */

public abstract class AbstractTckTest extends BaseTckTest {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected static final String APITEST = "apitest";

  protected static final String DONOTUSEServletName = "NoServletName";

  private InetAddress[] addrs;

  private String _servlet;

  protected AbstractTckTest() {
    // Construct a default context root based on the class name of
    // the concrete subclass of this class.
    String cname = this.getClass().getName();
    String prefix = "com.sun.ts.tests.";
    if (cname.startsWith(prefix)) {
      cname = cname.substring(prefix.length());
    }
    String suffix = ".URLClient";
    if (cname.endsWith(suffix)) {
      cname = cname.substring(0, cname.length() - suffix.length());
    }
    cname = cname.replace('.', '_');
    cname = "/" + cname + "_web";
    setContextRoot(cname);
  }

  protected void setTestProperties(WebTestCase testCase) {
    setStandardProperties(TEST_PROPS.get().getProperty(STANDARD), testCase);
    setApiTestProperties(TEST_PROPS.get().getProperty(APITEST), testCase);
    super.setTestProperties(testCase);
  }

  /**
   * Sets the request, testname, and a search string for test passed. A search
   * is also added for test failure. If found, the test will fail.
   *
   * @param testValue
   *          - a logical test identifier
   * @param testCase
   *          - the current test case
   */
  private void setApiTestProperties(String testValue, WebTestCase testCase) {
    if (testValue == null) {
      return;
    }

    // An API test consists of a request with a request parameter of
    // testname, a search string of Test PASSED, and a logical test name.

    // set the testname
    _testName = testValue;

    // set the request
    StringBuilder sb = new StringBuilder(50);
    if ((_servlet != null)
            && (TEST_PROPS.get().getProperty(DONOTUSEServletName) == null)) {
      sb.append(GET).append(_contextRoot).append(SL);
      sb.append(_servlet).append("?testname=").append(testValue);
      sb.append(HTTP11);
    } else {
      sb.append(GET).append(_contextRoot).append(SL);
      sb.append(testValue).append(HTTP10);
    }
    logger.debug("REQUEST LINE: {}", sb);

    HttpExchange req = new HttpExchange(sb.toString(), _hostname, _port);
    testCase.setRequest(req);

    if ((TEST_PROPS.get().getProperty(SEARCH_STRING) == null)
            || ((TEST_PROPS.get().getProperty(SEARCH_STRING)).isEmpty())) {
      testCase.setResponseSearchString(Data.PASSED);
      testCase.setUnexpectedResponseSearchString(Data.FAILED);
    }

  }

  /**
   * Consists of a test name, a request, and a goldenfile.
   * 
   * @param testValue
   *          - a logical test identifier
   * @param testCase
   *          - the current test case
   */
  private void setStandardProperties(String testValue, WebTestCase testCase) {

    if (testValue == null) {
      return;
    }
    // A standard test sets consists of a testname
    // a request, and a goldenfile. The URI is not used
    // in this case since the JSP's are assumed to be located
    // at the top of the contextRoot
    StringBuffer sb = new StringBuffer(50);

    // set the testname
    _testName = testValue;

    // set the request
    // sb.append(GET).append(_contextRoot).append(SL);
    // sb.append(testValue).append(JSP_SUFFIX).append(HTTP10);
    // setRequest(sb.toString());
    // HttpRequest req = new HttpRequest(sb.toString(), _hostname, _port);
    // testCase.setRequest(req);

    if (_servlet != null) {
      sb.append(GET).append(_contextRoot).append(SL);
      sb.append(_servlet).append("?testname=").append(testValue);
      sb.append(HTTP11);
    } else {
      sb.append(GET).append(_contextRoot).append(SL);
      sb.append(testValue).append(HTTP10);
    }
    logger.debug("REQUEST LINE: {}", sb);
    HttpExchange req = new HttpExchange(sb.toString(), _hostname, _port);
    testCase.setRequest(req);
  }

  /**
   * Sets the name of the servlet to use when building a request for a single
   * servlet API test.
   * 
   * @param servlet
   *          - the name of the servlet
   */
  protected void setServletName(String servlet) {
    _servlet = servlet;
  }

  protected String getServletName() {
    return _servlet;
  }

  protected String getLocalInterfaceInfo(boolean returnAddresses) {
    String result = null;
    initInetAddress();
    if (addrs.length != 0) {
      StringBuilder sb = new StringBuilder(32);
      if (!returnAddresses) {
        // localhost might not show up if aliased
        sb.append("localhost,");
      } else {
        // add 127.0.0.1
        sb.append("127.0.0.1,");
      }

      for (int i = 0; i < addrs.length; i++) {
        if (returnAddresses) {
          String ip = addrs[i].getHostAddress();
          if (!"127.0.0.1".equals(ip)) {
            if (ip.contains("%")) {
              int scopeId = ip.indexOf("%");
              ip = ip.substring(0, scopeId);
            }
            sb.append(ip);
          }
        } else {
          String host = addrs[i].getCanonicalHostName();
          if (!"localhost".equals(host)) {
            sb.append(host);
          }
        }
        if (i + 1 != addrs.length) {
          sb.append(",");
        }
      }
      result = sb.toString();
      logger.trace("[AbstractUrlClient] Interface info: {}", result);
    }
    return result;
  }

  private void initInetAddress() {
    if (addrs == null) {
      try {
        addrs = InetAddress
            .getAllByName(InetAddress.getLocalHost().getCanonicalHostName());
      } catch (UnknownHostException uhe) {
        logger.info(
            "[AbstractUrlClient][WARNING] Unable to obtain local host information.");
      }
    }
  }

  protected String getRequest(String rq) {
    return rq;
  }

  protected String getURLString(String protocol, String hostname, int portnum, String sContext) {
    return protocol + "://" + hostname + ":" + portnum + "/" + sContext;
  }

  protected URL getURL(String protocol, String hostname, int portnum, String sContext) throws MalformedURLException {
    return new URL(protocol + "://" + hostname + ":" + portnum + "/" + sContext);
  }


  public URLConnection getHttpsURLConnection(URL newURL)
          throws IOException {
    // open HttpsURLConnection using TSHttpsURLConnection
    URLConnection httpsURLConn = null;

    httpsURLConn = newURL.openConnection();
    if (httpsURLConn != null) {
      httpsURLConn.setDoInput(true);
      httpsURLConn.setDoOutput(true);
      httpsURLConn.setUseCaches(false);

    } else {
      throw new IOException("Error opening httsURLConnection");
    }

    return httpsURLConn;
  }

}
