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

package com.sun.ts.tests.servlet.common.client;

import com.sun.ts.tests.servlet.common.request.HttpRequest;
import com.sun.ts.tests.servlet.common.request.WebTestCase;
import org.apache.commons.httpclient.HttpState;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Properties;

/**
 * <PRE>
 * Provides base test initialization and runtime
 * logic into a common class.
 * All test areas will need to extend this class
 * to provided area specific functionality needed for
 * that particular technology.
 * </PRE>
 */
@ExtendWith({ArquillianExtension.class})
public abstract class BaseUrlClient {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @ArquillianResource @OperateOnDeployment("_DEFAULT_")
  public URL url;

  /**
   * Properties parameters
   */
  public Properties _props;

  /**
   * 401 - Unauthorized
   */
  protected static final String UNAUTHORIZED = "401";

  /**
   * 403 - Forbidden
   */
  protected static final String FORBIDDEN = "403";

  /**
   * 404 - not found
   */
  protected static final String NOT_FOUND = "404";

  /**
   * 200 - ok
   */
  protected static final String OK = "200";

  /**
   * 201 - created
   */
  protected static final String CREATED = "201";

  /**
   * 500 - internal server error
   */
  protected static final String INTERNAL_SERVER_ERROR = "500";

  /**
   * 503 - service unavailable
   */
  protected static final String SERVICE_UNAVAILABLE = "503";

  /**
   * 100 - continue
   */
  protected static final String CONTINUE = "100";

  /**
   * 302 - moved temporarily
   */
  protected static final String MOVED_TEMPORARY = "302";

  /**
   * 410 - GONE
   */
  protected static final String GONE = "410";

  /**
   * 411 - length required
   */
  protected static final String LENGTH_REQUIRED = "411";

  /**
   * TS Webserver host property
   */
  protected static final String SERVLETHOSTPROP = "webServerHost";

  /**
   * TS Webserver port property
   */
  protected static final String SERVLETPORTPROP = "webServerPort";

  /**
   * TS home property
   */
  protected static final String TSHOME = "ts_home";

  /**
   * Test properties
   */
  protected static final Properties TEST_PROPS = new Properties();

  /**
   * StatusCode property
   */
  protected static final String STATUS_CODE = "status-code";

  /**
   * Reason-Phrase property
   */
  protected static final String REASON_PHRASE = "reason-phrase";

  /**
   * Expected headers property
   */
  protected static final String EXPECTED_HEADERS = "expected_headers";

  /**
   * Unexpected header property
   */
  protected static final String UNEXPECTED_HEADERS = "unexpected_headers";

  /**
   * Expect response body property
   */
  protected static final String EXPECT_RESPONSE_BODY = "expect_response_body";

  /**
   * Request property
   */
  protected static final String REQUEST = "request";

  /**
   * Request headers property
   */
  protected static final String REQUEST_HEADERS = "request_headers";

  /**
   * Search string property
   */
  protected static final String SEARCH_STRING = "search_string";

  /**
   * Search string case insensitive property
   */
  protected static final String SEARCH_STRING_IGNORE_CASE = "search_string_ignore_case";

  /**
   * Basic Auth username
   */
  protected static final String BASIC_AUTH_USER = "basic_auth_user";

  /**
   * Basic Auth password
   */
  protected static final String BASIC_AUTH_PASSWD = "basic_auth_passwd";

  /**
   * Basic Auth realm
   */
  protected static final String BASIC_AUTH_REALM = "basic_auth_realm";

  protected static final String UNAUTH_USERNAME = "authuser";

  protected static final String UNAUTH_PASSWORD = "authpassword";


  // Constants:
  protected static final String USERNAME = "user";

  protected static final String PASSWORD = "password";

  /**
   * Unordered search string property
   */
  protected static final String UNORDERED_SEARCH_STRING = "unordered_search_string";

  /**
   * Content property
   */
  protected static final String CONTENT = "content";

  /**
   * Test name property
   */
  protected static final String TEST_NAME = "testname";

  /**
   * Response Match property
   */
  protected static final String RESPONSE_MATCH = "response_match";

  /**
   * Unexpected response match property
   */
  protected static final String UNEXPECTED_RESPONSE_MATCH = "unexpected_response_match";

  /**
   * Standard test property
   */
  protected static final String STANDARD = "standard";

  /**
   * Ignore response body
   */
  protected static final String IGNORE_BODY = "ignore_body";

  /**
   * Validation strategy
   */
  protected static final String STRATEGY = "strategy";

  /**
   * Current test directory
   */
  protected String TESTDIR = null;

  /**
   * Goldenfile directory
   */
  protected String GOLDENFILEDIR = "/src/web";

  /**
   * Default request method
   */
  protected static final String GET = "GET ";

  /**
   * HTTP 1.0
   */
  protected static final String HTTP10 = " HTTP/1.0";

  /**
   * HTTP 1.1
   */
  protected static final String HTTP11 = " HTTP/1.1";

  /**
   * Forward slash
   */
  protected static final String SL = "/";

  /**
   * Goldenfile suffix
   */
  protected static final String GF_SUFFIX = ".gf";

  /**
   * JSP suffix
   */
  protected static final String JSP_SUFFIX = ".jsp";

  /**
   * Use any saved state
   */
  protected static final String USE_SAVED_STATE = "use_saved_state";

  /**
   * Save current HTTP state.
   */
  protected static final String SAVE_STATE = "save_state";

  /**
   * Ignore HTTP status codes
   */
  protected static final String IGNORE_STATUS_CODE = "ignore_status_code";

  /**
   * Current test name
   */
  protected String _testName = null;

  /**
   * location of _tsHome
   */
  protected String _tsHome = null;

  /**
   * Context root of target tests
   */
  protected String _contextRoot = null;

  /**
   * General file/request URI for both gfiles and tests
   */
  protected String _generalURI = null;

  /**
   * Target webserver hostname
   */
  protected String _hostname = null;

  /**
   * Target webserver port
   */
  protected int _port = 0;

  /**
   * HttpState that may be used for multiple invocations requiring state.
   */
  protected HttpState _state = null;

  /**
   * Test case.
   */
  protected WebTestCase _testCase = null;

  /**
   * Use saved state.
   */
  protected boolean _useSavedState = false;

  /**
   * Save state.
   */
  protected boolean _saveState = false;

  /**
   * Follow redirect.
   */
  protected static final String FOLLOW_REDIRECT = "follow_redirect";

  protected boolean _redirect = false;

  /*
   * public methods
   * ========================================================================
   */

  /**
   * <code>setTestDir</code> sets the current test directory.
   *
   * @param testDir
   *          a <code>String</code> value
   */
  public void setTestDir(String testDir) {
    TESTDIR = testDir;
    // setGoldenFileDir(testDir);
  }

  public void setGeneralURI(String URI) {
    _generalURI = URI;
  }

  public void setContextRoot(String root) {
    _contextRoot = root;
  }

  public String getContextRoot() {
    return _contextRoot;
  }

  /**
   * Sets the goldenfile directory
   * 
   * @param goldenDir
   *          goldenfile directory based off test directory
   */
  public void setGoldenFileDir(String goldenDir) {
    GOLDENFILEDIR = goldenDir;
  }

  @BeforeEach
  public void setup() throws Exception {
    String ctxRoot = url.getPath();
    setContextRoot(ctxRoot.endsWith("/")?ctxRoot.substring(0, ctxRoot.length()-1):ctxRoot);
    Properties properties = new Properties();
    properties.put(SERVLETHOSTPROP, url.getHost());
    properties.put(SERVLETPORTPROP, Integer.toString(url.getPort()));
    // TODO do we really need this??
    properties.put(TSHOME, Paths.get("target/test-classes").toFile().getName());
    //  TOFIX configuration
    properties.setProperty("servlet_waittime", System.getProperty("servlet_waittime","10"));
    properties.setProperty("servlet_async_wait", System.getProperty("servlet_async_wait","4"));
    properties.setProperty("logical.hostname.servlet", System.getProperty("logical.hostname.servlet","server"));
    properties.setProperty(USERNAME, System.getProperty(USERNAME, "j2ee"));
    properties.setProperty(PASSWORD, System.getProperty(PASSWORD, "j2ee"));
    properties.setProperty(BASIC_AUTH_PASSWD, System.getProperty(UNAUTH_PASSWORD, "j2ee"));
    properties.setProperty(BASIC_AUTH_USER, System.getProperty(BASIC_AUTH_USER, "j2ee"));
    properties.setProperty(BASIC_AUTH_PASSWD, System.getProperty(UNAUTH_PASSWORD, "j2ee"));
    properties.setProperty(UNAUTH_USERNAME, System.getProperty(UNAUTH_USERNAME, "javajoe"));
    properties.setProperty(UNAUTH_PASSWORD, System.getProperty(UNAUTH_PASSWORD, "javajoe"));
    setup(null, properties);
  }


  /**
   * <code>setup</code> is by the test harness to initialize the tests.
   *
   * @param args
   *          a <code>String[]</code> value
   * @param p
   *          a <code>Properties</code> value
   */
  public void setup(String[] args, Properties p) throws Exception {
    _props = p;
    String hostname = p.getProperty(SERVLETHOSTPROP).trim();
    String portnum = p.getProperty(SERVLETPORTPROP).trim();
    String tshome = p.getProperty(TSHOME).trim();

    if (!isNullOrEmpty(hostname)) {
      _hostname = hostname;
    } else {
      throw new IllegalArgumentException(
          "[BaseUrlClient] 'webServerHost' was not set in the" + " ts.jte.");
    }

    if (!isNullOrEmpty(portnum)) {
      _port = Integer.parseInt(portnum);
    } else {
      throw new IllegalArgumentException(
          "[BaseUrlClient] 'webServerPort' was not set in the" + " ts.jte.");
    }

    if (!isNullOrEmpty(tshome)) {
      _tsHome = tshome;
    } else {
      throw new IllegalArgumentException(
          "[BaseUrlClient] 'tshome' was not set in the " + " ts.jte.");
    }

    logger.debug("[BaseUrlClient] Test setup OK using properties {}", p);
  }

  /**
   * <code>cleanup</code> is called by the test harness to cleanup after text
   * execution
   *
   */
  public void cleanup() throws Exception {
    logger.info("[BaseUrlClient] Test cleanup OK");
  }

  /*
   * protected methods
   * ========================================================================
   */

  /**
   * <PRE>
   * Invokes a test based on the properties
    * stored in TEST_PROPS.  Once the test has completed,
    * the properties in TEST_PROPS will be cleared.
   * </PRE>
   *
   */
  protected void invoke() {
    try {
      _testCase = new WebTestCase();
      setTestProperties(_testCase);
      logger.trace("[BaseUrlClient] EXECUTING");
      if (_useSavedState && _state != null) {
        _testCase.getRequest().setState(_state);
      }
      if (_redirect) {
        logger.trace("##########Call setFollowRedirects");
        _testCase.getRequest().setFollowRedirects(_redirect);
      }
      _testCase.execute();
      if (_saveState) {
        _state = _testCase.getResponse().getState();
      }
    } catch (Exception tfe) {
      Throwable t = tfe.getCause();
      if (t != null) {
        logger.warn("Root cause of Failure: " + t.getMessage(), t);
      }
      logger.error("Test: '" + _testName + "' failed.", tfe);
      throw new RuntimeException("[BaseUrlClient] " + _testName + " failed! Check output for cause of failure.", tfe);
    } finally {
      _useSavedState = false;
      _saveState = false;
      _redirect = false;
      clearTestProperties();
    }
  }

  /**
   * <PRE>
   * Sets the appropriate test properties based
    * on the values stored in TEST_PROPS
   * </PRE>
   */
  protected void setTestProperties(WebTestCase testCase) {
    HttpRequest req = testCase.getRequest();

    // Check for a request object. If doesn't exist, then
    // check for a REQUEST property and create the request object.

    if (req == null) {
      String request = TEST_PROPS.getProperty(REQUEST);

      if (request.startsWith("GET") || request.startsWith("POST")
          || request.startsWith("OPTIONS") || request.startsWith("PUT")
          || request.startsWith("DELETE") || request.startsWith("HEAD")
          || request.endsWith(HTTP10) || request.endsWith(HTTP11)) {
        // user has overriden default request behavior
        req = new HttpRequest(request, _hostname, _port);
        testCase.setRequest(req);
      } else {
        req = new HttpRequest(getTSRequest(request), _hostname, _port);
        testCase.setRequest(req);
      }
    }

    String key = null;
    String value = null;
    // process the remainder of the properties
    for (Enumeration e = TEST_PROPS.propertyNames(); e.hasMoreElements();) {
      key = (String) e.nextElement();
      value = TEST_PROPS.getProperty(key);

      switch (key) {
        case TEST_NAME:
          testCase.setName(value);
          _testName = value;
          break;
        case STATUS_CODE:
          testCase.setExpectedStatusCode(value);
          break;
        case IGNORE_STATUS_CODE:
          testCase.setExpectedStatusCode("-1");
          break;
        case REASON_PHRASE:
          testCase.setExpectedReasonPhrase(value);
          break;
        case EXPECTED_HEADERS:
          testCase.addExpectedHeader(value);
          break;
        case UNEXPECTED_HEADERS:
          testCase.addUnexpectedHeader(value);
          break;
        case SEARCH_STRING:
          testCase.setResponseSearchString(value);
          break;
        case SEARCH_STRING_IGNORE_CASE:
          testCase.setResponseSearchStringIgnoreCase(value);
          break;
        case STRATEGY:
          testCase.setStrategy(value);
          break;
        case CONTENT:
          req.setContent(value);
          break;
        case RESPONSE_MATCH:
          // setResponseMatch(TEST_PROPS.getProperty(key));
          break;
        case REQUEST_HEADERS:
          req.addRequestHeader(TEST_PROPS.getProperty(key));
          break;
        case EXPECT_RESPONSE_BODY:
          // FIXME
          // setExpectResponseBody(false);
          break;
        case UNEXPECTED_RESPONSE_MATCH:
          testCase.setUnexpectedResponseSearchString(value);
          break;
        case UNORDERED_SEARCH_STRING:
          testCase.setUnorderedSearchString(value);
          break;
        case USE_SAVED_STATE:
          _useSavedState = true;
          break;
        case SAVE_STATE:
          _saveState = true;
          break;
        case FOLLOW_REDIRECT:
          logger.debug("##########Found redirect Property");
          _redirect = true;
          break;
        case BASIC_AUTH_USER:
        case BASIC_AUTH_PASSWD:
        case BASIC_AUTH_REALM:
          String user = TEST_PROPS.getProperty(BASIC_AUTH_USER);
          String password = TEST_PROPS.getProperty(BASIC_AUTH_PASSWD);
          String realm = TEST_PROPS.getProperty(BASIC_AUTH_REALM);
          req.setAuthenticationCredentials(user, password, HttpRequest.BASIC_AUTHENTICATION, realm);
          break;
        default:
          // no op
      }

    }
  }

  /*
   * private methods
   * ========================================================================
   */

  private String getTSRequest(String request) {
    StringBuilder finReq = new StringBuilder(50);

    finReq.append(GET).append(_contextRoot).append(SL).append(_generalURI);
    finReq.append(SL).append(request).append(HTTP10);

    return finReq.toString();
  }

  /**
   * Clears the contents of TEST_PROPS
   */
  private void clearTestProperties() {
    TEST_PROPS.clear();
  }

  private boolean isNullOrEmpty(String val) {
    return val == null || val.equals("");
  }

  public void logErr(String message) {
    logger.error(message);
  }

  public void logErr(String message, Exception e) {
    logger.error(message, e);
  }

  public void logMsg(String message) {
    logger.info(message);
  }

  public void logTrace(String message) {
    logger.trace(message);
  }

}
