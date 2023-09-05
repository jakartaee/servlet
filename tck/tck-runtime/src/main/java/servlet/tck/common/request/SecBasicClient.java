/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates and others.
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

package servlet.tck.common.request;

import servlet.tck.util.TestUtil;
import servlet.tck.common.client.BaseTckTest;

import java.util.Properties;

public abstract class SecBasicClient extends BaseTckTest {

  // Constants:
  private static final String USERNAME = "user";

  private static final String PASSWORD = "password";

  private static final String UNAUTH_USERNAME = "authuser";

  private static final String UNAUTH_PASSWORD = "authpassword";

  private static final String CLASS_TRACE_HEADER = "[SecBasicClient]: ";

  private static final String USER_PRINCIPAL_SEARCH = "The user principal is: "; // (+username)

  private static final String REMOTE_USER_SEARCH = "getRemoteUser(): "; // (+username)

  // fields:
  protected String pageSec = null;

  protected String pageGuest = null;

  protected String pageUnprotected = null;

  protected String pageRoleReverse = null;

  private String pageJspBase = "/jsp_sec_secbasic_web";

  private String pageJspSec = pageJspBase + "/jspSec.jsp";

  private String pageJspGuest = pageJspBase + "/guestPage.jsp";

  private String pageJspUnprotected = pageJspBase + "/unprotected.jsp";

  private String pageJspRoleReverse = pageJspBase + "/rolereverse.jsp";

  private String pageServletBase = "/servlet_sec_secbasic_web";

  private String pageServletSec = pageServletBase + "/ServletSecTest";

  private String pageServletGuest = pageServletBase + "/GuestPageTest";

  private String pageServletUnprotected = pageServletBase + "/UnProtectedTest";

  private String pageServletRoleReverse = pageServletBase + "/RoleReverseTest";

  private String username = null;

  private String password = null;

  private String unauthUsername = null;

  private String unauthPassword = null;

  /*
   * @class.setup_props: webServerHost; webServerPort; user; password; authuser;
   * authpassword; ts_home;
   *
   *
   */

  public void setup(String[] args, Properties p) throws Exception {
    super.setup(args, p);

      // TOFIX configurable
//    user=j2ee
//    password=j2ee
//    authuser=javajoe
//    authpassword=javajoe

    p.setProperty(USERNAME, "j2ee");
    p.setProperty(PASSWORD, "j2ee");
    p.setProperty(UNAUTH_USERNAME, "javajoe");
    p.setProperty(UNAUTH_PASSWORD, "javajoe");

    try {
      username = p.getProperty(USERNAME);
      password = p.getProperty(PASSWORD);
      unauthUsername = p.getProperty(UNAUTH_USERNAME);
      unauthPassword = p.getProperty(UNAUTH_PASSWORD);


      pageSec = pageServletSec;
      pageGuest = pageServletGuest;
      pageUnprotected = pageServletUnprotected;
      pageRoleReverse = pageServletRoleReverse;


    } catch (Exception e) {
      logErr("Error: got exception: ", e);
    }
  }

  /*
   * testName: test1
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource, the web server requests authentication.
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive
   * authentication request.
   */
  
  public void test1() throws Exception {
    logMessage(
        "Sending request to validate presence of www-authenticate header...");
    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test1");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.setProperty(EXPECTED_HEADERS, "www-authenticate:<somevalue>");
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED);
    invoke();

    dumpResponse(); // debug aid

    logMessage("Authentication requested");
  }

  /*
   * testName: test2
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1. Also tests API assertions in section 11.3.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource, and user enters a valid username and password, the
   * original web resource is returned and user is authenticated. 2.
   * getRemoteUser() returns the user name that the client authenticated with.
   *
   * @test_Strategy: 1. Send request with correct authentication. 2. Receive
   * page (ensure principal is correct, and ensure that getRemoteUser() returns
   * the correct name)
   */
  
  public void test2() throws Exception {
    logMessage("Sending request with Authroization header...");

    StringBuffer sb = new StringBuffer(100);
    sb.append(USER_PRINCIPAL_SEARCH).append(username).append("|");
    sb.append(REMOTE_USER_SEARCH).append(username).append("|");
    sb.append("isUserInRole(\"ADM\"): !true!").append("|");
    sb.append("isUserInRole(\"MGR\"): !false!").append("|");
    sb.append("isUserInRole(\"VP\"): !false!").append("|");
    sb.append("isUserInRole(\"EMP\"): !true!").append("|");

    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test2");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.setProperty(SEARCH_STRING, sb.toString());
    invoke();

    dumpResponse(); // debug aid

    logMessage("isUserInRole() and getRemoteUser() returned expected results");
  }

  /*
   * testName: test3
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource, and user enters an invalid username and password,
   * the container denies access to the web resource.
   *
   * @test_Strategy: 1. Re-send request with incorrect authentication. 2.
   * Receive authentication request.
   */
  
  public void test3() throws Exception {
    logMessage(
        "Sending an request for a protected resource with invalid username/password...");

    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test3");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, "invalid");
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED);
    invoke();

    dumpResponse(); // debug aid

    logMessage("Access Denied");
  }

  /*
   * testName: test4
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource, and user enters an valid username and password, but
   * for a role that is not authorized to access the resource, the container
   * denies access to the web resource.
   *
   * @test_Strategy: 1. Send request with correct authentication for user
   * javajoe for a page javajoe is allowed to access. 2. Receive page (this
   * verifies that the javajoe user is set up properly). 3. Send request with
   * correct authentication, but incorrect authorization to access resource 4.
   * Receive error
   */
  
  public void test4() throws Exception {

    StringBuffer sb = new StringBuffer(100);
    sb.append(USER_PRINCIPAL_SEARCH).append(unauthUsername);

    logMessage("Sending request to resource the user has access to...");
    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test4");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageGuest));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, unauthUsername);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, unauthPassword);
    TEST_PROPS.setProperty(SEARCH_STRING, sb.toString());
    invoke();

    dumpResponse(); // debug aid

    logMessage("User successfully accessed the resource");

    logMessage(
        "Sending request to resource with valid username/password, but not the right roles...");
    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test4");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, unauthUsername);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, unauthPassword);
    TEST_PROPS.setProperty(STATUS_CODE, FORBIDDEN);
    invoke();

    dumpResponse(); // debug aid

    logMessage("Access Forbidden");
  }

  /*
   * testName: test5
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1. Also tests assertions in section 11.3.
   *
   * 1. If user has not been authenticated and user attempts to access an
   * unprotected web resource, the web resource is returned without need to
   * authenticate. 2. isUserInRole() must return false for any valid or invalid
   * role reference. 3. getRemoteUser() must return false
   *
   * @test_Strategy: 1. Send request for unprotected.jsp with no authentication.
   * 2. Receive page 3. Search the returned page for "!true!", which would
   * indicate that at least one call to isUserInRole attempted by
   * unprotected.jsp returned true. 4. check that getRemoteUser() returns null.
   */
  
  public void test5() throws Exception {
    StringBuffer sb = new StringBuffer(100);
    sb.append(USER_PRINCIPAL_SEARCH).append("|");
    sb.append(REMOTE_USER_SEARCH).append("null");

    logMessage("Sending request to unprotected resource....");
    TEST_PROPS.setProperty(TEST_NAME, "BasicSec/Test5");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageUnprotected));
    TEST_PROPS.setProperty(SEARCH_STRING, sb.toString());
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "!true!");
    invoke();

    dumpResponse(); // debug aid

    logMessage("isUserInRole() and getRemoteUser() returned expected results");
  }

  /*
   * testName: test6
   *
   * @assertion: Test HTTP-Basic authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1. Also tests assertions from section 11.3.
   *
   * Given two servlets in the same application, each of which calls
   * isUserInRole(X), and where X is linked to different roles in the scope of
   * each of the servlets (i.e. R1 for servlet 1 and R2 for servlet 2), then a
   * user whose identity is mapped to R1 but not R2, shall get a true return
   * value from isUserInRole( X ) in servlet 1, and a false return value from
   * servlet 2 (a user whose identity is mapped to R2 but not R1 should get the
   * inverse set of return values).
   *
   * @test_Strategy: Since test1 already verifies the functionality for
   * isUserInRole returning true, this test needs only verify that it should
   * return false for the other jsp. For this test, MGR and ADM are swapped, so
   * isUserInRole() should return opposite values from test1.
   *
   * 1. Send request to access rolereverse.jsp 2. Receive redirect to login
   * page, extract location and session id cookie. 3. Send request to access new
   * location, send cookie 4. Receive login page 5. Send form response with
   * username and password 6. Receive redirect to resource 7. Request resource
   * 8. Receive resource (check isUserInRole for all known roles)
   */
  
  public void test6() throws Exception {

    StringBuffer sb = new StringBuffer(100);
    sb.append(USER_PRINCIPAL_SEARCH).append(username).append("|");
    sb.append("isUserInRole(\"ADM\"): !false!").append("|");
    sb.append("isUserInRole(\"MGR\"): !true!").append("|");
    sb.append("isUserInRole(\"VP\"): !false!").append("|");
    sb.append("isUserInRole(\"EMP\"): !true!").append("|");

    logMessage(
        "Sending request to validate isUserInRole with roles reversed...");
    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test6");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageRoleReverse));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.setProperty(SEARCH_STRING, sb.toString());
    invoke();

    dumpResponse(); // debug aid

    logMessage("isUserInRole() and getRemoteUser() returned expected results");
  }

  /*
   * testName: test7
   *
   * @assertion: Test BASIC authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.1.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource+<j_security_check> , and user enters an invalid
   * username and password, the container denies access to the web resource.
   * IMPORTANT: this is not just trying to access a protected resource but
   * instead is trying to access pageSec + "/j_security_check" when
   * unauthenticated in an attempt to trick/confuse the impl into thinking
   * authentication occurred when it did not.
   *
   * @test_Strategy: 1. send request with incorrect authentication to url +
   * "/j_security_check" 2. Receive authentication request.
   */
  
  public void test7() throws Exception {
    logMessage(
        "Sending an request for a protected resource with invalid username/password...");

    TEST_PROPS.setProperty(TEST_NAME, "SecBasic/Test7");
    TEST_PROPS.setProperty(REQUEST,
        getRequestLine("GET", pageSec + "/j_security_check"));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, "invalid");
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    // The servlet is mapped to "/ServletSecTest" so this request should result
    // in a 404 since no Servlet is mapped to the requested URI or 401 if the container
    // rejects the incoming BASIC header.
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED + "," + NOT_FOUND);
    invoke();

    dumpResponse(); // debug aid

    if ((_testCase != null) && (_testCase.getResponse() != null)) {
      // we got a response so lets check it...
      // if our search string appears in the response, we were erroneously
      // allowed access to a protected page!
      String searchString = "Inside  ServletSecTestServlet";
      try {
        if (_testCase.getResponse().getResponseBodyAsString()
            .indexOf(searchString) != -1) {
          // ohoh - we should NOT have been allowed to access the page and it
          // appears we did access it. log an error
          TestUtil.logErr("(Should say: \"" + searchString + "\")");
          throw new Exception("test7 failed.");
        }
      } catch (Exception ex) {
        // must've had problem getting response so dump exception but continue
        // on
        ex.printStackTrace();
      }
    }

    logMessage("Access properly Denied");
  }

  /**
   * Returns a valid HTTP/1.1 request line.
   * 
   * @param method
   *          the request method
   * @param path
   *          the request path
   * @return a valid HTTP/1.1 request line
   */
  private static String getRequestLine(String method, String path) {
    return method + " " + path + " HTTP/1.1";
  }

  /**
   * Simple wrapper around TestUtil.logMessage().
   * 
   * @param message
   *          - the message to log
   */
  private static void logMessage(String message) {
    TestUtil.logMsg(CLASS_TRACE_HEADER + message);
  }

  /**
   * Simple wrapper around TestUtil.logTrace().
   * 
   * @param message
   *          - the message to log
   */
  private static void trace(String message) {
    TestUtil.logTrace(CLASS_TRACE_HEADER + message);
  }

  private void dumpResponse() {
    try {
      if ((_testCase != null) && (_testCase.getResponse() != null)) {
        trace(_testCase.getResponse().getResponseBodyAsString());
      }
    } catch (Exception ex) {
      // must've had problem getting response so dump exception but continue on
      ex.printStackTrace();
    }
  }

}
