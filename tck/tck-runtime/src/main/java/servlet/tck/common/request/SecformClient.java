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

package servlet.tck.common.request;
import servlet.tck.util.WebUtil;
import servlet.tck.util.WebUtil.Response;
import servlet.tck.common.client.BaseTckTest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import static servlet.tck.util.WebUtil.toCookieValue;

public class SecformClient extends BaseTckTest {
  // Configurable constants:

  private String protocol = "http";

  private String hostname = null;

  private int portnum = 0;

  protected String pageBase = null;

  protected String pageSec = null;

  protected String pageGuest = null;

  protected String pageRoleReverse = null;

  protected String pageUnprotected = null;

  private String pageProgAuthen = null;

  private String pageProgLogin = null;

  private String pageProgLogout = null;

  private String pageOne = null;

  private String pageTwo = null;

  private String pageSample = null;

  private String pageallRoles = null;

  // common for JSP and Servlet
  private String pageLogin = "/login.jsp";

  private String pageError = "/error.jsp";

  private String pageSecurityCheck = "/j_security_check";

  private String pageJspBase = "/jsp_sec_secform_web";

  private String pageJspSec = pageJspBase + "/jspSec.jsp";

  private String pageJspUnprotected = pageJspBase + "/unprotected.jsp";

  private String pageJspGuest = pageJspBase + "/guestPage.jsp";

  private String pageJspRoleReverse = pageJspBase + "/rolereverse.jsp";

  private String pageJspOne = pageJspBase + "/One.jsp";

  private String pageJspTwo = pageJspBase + "/Two.jsp";

  private String pageJspSample = pageJspBase + "/Sample.jsp";

  private String pageJspallRoles = pageJspBase + "/allRoles.jsp";

  private String pageServletBase = "/servlet_sec_secform_web";

  private String pageServletSec = pageServletBase + "/ServletSecTest";

  private String pageServletUnprotected = pageServletBase + "/UnProtectedTest";

  private String pageServletProgLogin = pageServletBase
      + "/ServletProgrammaticLogin";

  private String pageServletProgLogout = pageServletBase
      + "/ServletProgrammaticLogout";

  private String pageServletProgAuthen = pageServletBase
      + "/ServletProgrammaticAuthen";

  private String pageServletGuest = pageServletBase + "/GuestPageTest";

  private String pageServletRoleReverse = pageServletBase + "/RoleReverseTest";

  private String pageServletOne = pageServletBase + "/OneTest";

  private String pageServletTwo = pageServletBase + "/TwoTest";

  private String pageServletSample = pageServletBase + "/SampleTest";

  private String pageServletallRoles = pageServletBase + "/allRolesTest";

  private String searchFor = "The user principal is: "; // (+username)

  private String searchForGetRemoteUser = "getRemoteUser(): "; // (+username)

  private String username = "";

  private String password = "";

  private String unauthUsername = "";

  private String unauthPassword = "";

  private String tshome = "";

  // Constants:
  private final String WebHostProp = "webServerHost";

  private final String WebPortProp = "webServerPort";

  private final String UserNameProp = "user";

  private final String PasswordProp = "password";

  private final String unauthUserNameProp = "authuser";

  private final String unauthPasswordProp = "authpassword";

  private final String tsHomeProp = "ts_home";

  private String testDir = System.getProperty("user.dir");

  // Shared test variables:
  private Properties props = null;

  private String request = null;

  private Response response = null;

  private Response loginPageRequestResponse = null;

  private Response errorPageRequestResponse = null;

  private Map<String,String> cookies = null;
  
  /*
   * @class.setup_props: webServerHost; webServerPort; user; password; authuser;
   * authpassword; ts_home;
   */
  public void setup(String[] args, Properties p) throws Exception {
    super.setup(args, p);
    props = p;

    try {
      hostname = p.getProperty(WebHostProp);
      portnum = Integer.parseInt(p.getProperty(WebPortProp));
      username = p.getProperty(UserNameProp);
      password = p.getProperty(PasswordProp);
      unauthUsername = p.getProperty(unauthUserNameProp);
      unauthPassword = p.getProperty(unauthPasswordProp);
      tshome = p.getProperty(tsHomeProp);

      logger.debug("username: {}", username);
      logger.debug("password: {}", password);

//      if (args[0].equals("jsp")) {
//        pageBase = pageJspBase;
//        pageSec = pageJspSec;
//        pageGuest = pageJspGuest;
//        pageUnprotected = pageJspUnprotected;
//        pageRoleReverse = pageJspRoleReverse;
//        pageOne = pageJspOne;
//        pageTwo = pageJspTwo;
//        pageSample = pageJspSample;
//        pageallRoles = pageJspallRoles;
//
//        // prefix pageJspBase to pageLogin, pageError ,pageSecurityCheck
//        pageLogin = pageJspBase + pageLogin;
//        pageError = pageJspBase + pageError;
//        pageSecurityCheck = pageJspBase + pageSecurityCheck;
//
//      } else {
        pageBase = pageServletBase;
        pageSec = pageServletSec;
        pageGuest = pageServletGuest;
        pageUnprotected = pageServletUnprotected;
        pageRoleReverse = pageServletRoleReverse;
        pageOne = pageServletOne;
        pageTwo = pageServletTwo;
        pageSample = pageServletSample;
        pageallRoles = pageServletallRoles;
        pageProgLogin = pageServletProgLogin;
        pageProgLogout = pageServletProgLogout;
        pageProgAuthen = pageServletProgAuthen;

        // prefix pageServletBase to pageLogin, pageError, pageSecurityCheck
        pageLogin = pageServletBase + pageLogin;
        pageError = pageServletBase + pageError;
        pageSecurityCheck = pageServletBase + pageSecurityCheck;

//      }

    } catch (Exception e) {
      logger.error("Error: got exception: " + e.getMessage(), e);
    }
  }

  /*
   * testName: test1
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3. Also tests an assertion in section 11.3.
   *
   * 1. If user has not been authenticated and user attempts to access a
   * protected web resource, the correct login form is returned. 2. If user has
   * not been authenticated and user attempts to access a protected web
   * resource, and user enters a valid username and password, the original web
   * resource is returned and user is authenticated. A call to getRemoteUser()
   * must return the username. 3. If user has been authenticated and user is
   * authorized to access a protected web resource, user gets web resource
   * without the need to re-authenticate. A call to getRemoteUser() still
   * returns the username.
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login
   * page(make sure it the expected login page) 3. Send form response with
   * username and password 4. Receive jspSec.jsp (ensure principal is correct,
   * and ensure getRemoteUser() returns the username, and ensure isUserInRole()
   * is working properly) 5. Re-request jspSec.jsp 6. Ensure principal is still
   * correct and getRemoteUser() still returns the correct username. Also ensure
   * isUserInRole() is still working properly.
   */
  
  public void test1() throws Exception {
    try {
      // The first part of this test is used in test2 and test3 as
      // well, so the code has been moved to a helper method.
      requestAndGetLoginPage(pageSec, 1);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logger.debug("Sending request '{}' with login information.", request);
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      logger.debug("response.statusToken: {}", response.statusToken);
      logger.debug("response.content: {}", response.content);

      // Check that the page was found (no error).
      if (response.isError()) {
        logger.error("Could not find {}", request);
        throw new Exception("test1 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 1);

      // Print response content
      logger.debug("received response content  1: {}", response.content);

      // Check to make sure we are authenticated by checking the page
      // content. The jsp should output "The user principal is: j2ee"
      String searchString = searchFor + username;
      if (!response.content.contains(searchString)) {
        logger.error("User Principal incorrect.  Page received:");
        logger.error(response.content);
        logger.error("(Should say: \"" + searchString + "\")");
        throw new Exception("test1 failed.");
      }
      logger.debug("User Principal correct.");

      // Check to make sure getRemoteUser returns the user name.
      searchString = searchForGetRemoteUser + username;
      if (!response.content.contains(searchString)) {
        logger.error("getRemoteUser() did not return " + username + ":");
        logger.error(response.content);
        logger.error("(Should say: \"" + searchString + "\")");
        throw new Exception("test1 failed.");
      }
      logMsg("getRemoteUser() correct.");

      // Check to make sure isUserInRole is working properly:
      Map<String, Boolean> roleCheck = new HashMap<>();
      roleCheck.put("ADM", Boolean.TRUE);
      roleCheck.put("MGR", Boolean.FALSE);
      roleCheck.put("VP", Boolean.FALSE);
      roleCheck.put("EMP", Boolean.TRUE);
      // roleCheck.put( "Administrator", new Boolean( false ) );
      if (!checkRoles(response.content, roleCheck)) {
        logger.error("isUserInRole() does not work correctly. Page Received:");
        logger.error(response.content);
        throw new Exception("test1 failed.");
      }
      logMsg("isUserInRole() correct.");

      // Now that we are authenticated, try accessing the resource again
      // to ensure we need not go through the login page again.
      request = pageSec;
      logger.debug("Cookies = {}", cookies.toString());
      logger.debug("Re-sending request {}", request);
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logger.error("Could not find " + pageSec);
        throw new Exception("test1 failed.");
      }

      // Check to make sure we are still authenticated.
      if (!response.content.contains(searchString)) {
        logger.error("User Principal incorrect.  Page received:");
        logger.error(response.content);
        logger.error("(Should say: \"" + searchString + "\")");
        throw new Exception("test1 failed.");
      }
      logger.debug("User Principal still correct.");

      // Check to make sure getRemoteUser still returns the user name.
      searchString = searchForGetRemoteUser + username;
      if (!response.content.contains(searchString)) {
        logger.error("getRemoteUser() did not return " + username
            + " after lazy authentication:");
        logger.error(response.content);
        logger.error("(Should say: \"" + searchString + "\")");
        throw new Exception("test1 failed.");
      }
      logMsg("getRemoteUser() still correct.");

      // Check to make sure isUserInRole is still working properly:
      if (!checkRoles(response.content, roleCheck)) {
        logger.error("isUserInRole() does not work correctly.");
        logger.error("Page Received:");
        logger.error(response.content);
        throw new Exception("test1 failed.");
      }
      logMsg("isUserInRole() still correct.");

    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception("test1 failed: " + e.getMessage(), e);
    }
  }

  /*
   * testName: test2
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3.
   *
   * If user has not been authenticated and user attempts to access a protected
   * web resource, and user enters incorrect username and password, the error
   * page is returned.
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login page
   * 3. Send form response with username and incorrect password 4. Receive error
   * page (make sure it is the expected error page)
   */
  
  public void test2() throws Exception {
    try {
      // The first part of this test is used in test1 and test3 as
      // well, so the code has been moved to a helper method.
      requestAndGetLoginPage(pageSec, 2);

      // Send response to login form with session id cookie and username
      // and incorrect password:
      request = pageSecurityCheck;
      logger.debug("Sending request {} with incorrect login information.", request);
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", "incorrect" + password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);
      logger.debug("response.statusToken: {}", response.statusToken);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 2);

      // Check to make sure the user principal is null:
      String searchString = searchFor + "null";
      if (!response.content.contains(searchString)) {
        logger.error("User principal is not null in error page: {}", response.content);
        throw new Exception("test2 failed.");
      }

      logger.debug("User Principal is null as expected.");

      // Request error page
      request = pageError;
      logger.debug("Sending request {}", request);
      errorPageRequestResponse = WebUtil.sendRequest("GET",
          InetAddress.getByName(hostname), portnum, request,
          null, cookies);

      // Check that the page was found (no error).
      if (errorPageRequestResponse.isError()) {
        logger.error("Could not find {}", request);
        throw new Exception("test2  failed.");
      }

      // Compare the received error page with the expected error page
      // i.e Check whether
      // response.content ==errorPageRequestResponse.content
      if (response.content.equals(errorPageRequestResponse.content)) {
        logger.debug("Received the expected error page");
      } else {
        logger.error("Received incorrect error page");
        throw new Exception("test2  failed.");
      }

    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception("test2 failed: " + e.getMessage(), e);
    }
  }

  /*
   * testName: test3
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3.
   *
   * If user has not been authenticated and user attempts to access a protected
   * web resource, and user enters correct username and password of a user that
   * is authorized to access the resource, the resource is returned (similar to
   * test1, but uses user javajoe instead of j2ee). This test establishes that
   * the javajoe user is set up properly.
   *
   * @test_Strategy: 1. Send request to access guestPage.jsp 2. Receive login
   * page 3. Send form response with username(javajoe) and password 4. Receive
   * resource (check user principal)
   * 
   */
  
  public void test3() throws Exception {
    try {
      // The first part of this test is used in test2 and test3 as
      // well, so the code has been moved to a helper method.
      requestAndGetLoginPage(pageGuest, 3);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logger.debug("Sending request {} with login information (as {}).", request, unauthUsername);
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", unauthUsername);
      postData.put("j_password", unauthPassword);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logger.error("Could not find {}", request);
        throw new Exception("test3 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 3);

      // Check to make sure we are authenticated by checking the page
      // content. The jsp should output "The user principal is: javajoe"
      String searchString = searchFor + unauthUsername;
      if (!response.content.contains(searchString)) {
        logger.error("User Principal incorrect.  Page received: {}", response.content);
        logger.error("(Should say: {}", searchString);
        throw new Exception("test3 failed.");
      }
      logger.debug("User Principal correct.");
    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test3 failed: " + e.getMessage(), e);
    }
  }

  /*
   * testName: test4
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3.
   *
   * If user has not been authenticated and user attempts to access a protected
   * web resource, and user enters correct username and password of a user that
   * is not authorized to access the resource, the resource is not returned. The
   * authenticated user is not denied access to an unprotected page.
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login page
   * 3. Send form response with username and password 4. Receive an error
   * (expected unauthorized error) 5. Send request to access unprotected.jsp 6.
   * Receive unprotected.jsp.
   */
  
  public void test4() throws Exception {
    try {
      // The first part of this test is used in test1 and test2 as
      // well, so the code has been moved to a helper method.
      requestAndGetLoginPage(pageSec, 4);

      // Send response to login form with session id cookie and username
      // and password:
      request = pageSecurityCheck;
      logger.debug("Sending request {} with correct login information ({}), but incorrect authorization for this resource.",
              request, unauthUsername);
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", unauthUsername);
      postData.put("j_password", unauthPassword);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      logger.debug("response.content = {}", response.content);

      if (response.statusToken.equals("302")) {
        // We should receive a redirection page
        if (response.location == null) {
          logger.error("No redirection to login page received.");
          throw new Exception("test4 failed.");
        }

        // Extract location from redirection and format new request:
        request = WebUtil.getRequestFromURL(response.location);
        logger.debug("Redirect to: {}", response.location);

        // update cookies if the webserver choose to send cookies
        addNewCookies(cookies, response.cookies);

        // Request redirected page (login page):
        logger.debug("Sending request {}", request);
        response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
            portnum, request, null, cookies);
      }

      // Receive "403" or "404" error code for unauthorized access (forbidden).
      if ((response.statusToken.equals("403"))
          || (response.statusToken.equals("404"))) {
        logger.debug("Status Token {}, Received expected unauthorized access error", response.statusToken);
      } else {
        logger.error("Did not receive error for unauthorized access: {}", request);
        logger.error("Status Token {}", response.statusToken);
        logger.error("Page content: {}", response.content);
        throw new Exception("test4 failed.");
      }

      // Request unprotected page (unprotected.jsp page):
      request = pageUnprotected;
      logger.debug("Sending request {}", request);
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Check that we did not receive an error and that we did not
      // receive a redirection:
      if (response.isError()) {
        logger.error("Error retrieving {}", request);
        throw new Exception("test4 failed.");
      }

      // Check that the page returned is the correct one. The principal
      // is not checked.
      String searchString = searchFor;
      if (!response.content.contains(searchString)) {
        logger.error("Incorrect page received: {}", response.content);
        logger.error("(Should contain: {}", searchString);
        throw new Exception("test4 failed.");
      }
      logger.debug("Access to unprotected page granted.");

    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception("test4 failed: " + e.getMessage(), e);
    }
  }

  /*
   * testName: test5
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3. Also tests assertions from section 11.3.
   *
   * If user has not been authenticated and user attempts to access an
   * unprotected web resource, the resource is returned, and the user is not
   * forced to authenticate. Also, isUserInRole() must return false for any
   * valid or invalid role reference. A call to getRemoteUser() must return
   * null.
   *
   * @test_Strategy: 1. Send request to access unprotected.jsp 2. Receive
   * unprotected.jsp 3. Search the returned page for "!true!", which would
   * indicate that at least one call to isUserInRole attempted by
   * unprotected.jsp returned true. 4. Check that the call to getRemoteUser()
   * returned null.
   */
  
  public void test5() throws Exception {
    try {
      // Request restricted jsp page.
      String request = pageUnprotected;
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + pageUnprotected);
        throw new Exception("test5 failed.");
      }

      // Check that we did not receive an error and that we did not
      // receive a redirection:
      if (response.isError()) {
        logErr("Error retrieving " + request);
        throw new Exception("test5 failed.");
      }

      // Check that the page returned is the correct one. The principal
      // is not checked.
      String searchString = searchFor;
      if (!response.content.contains(searchString)) {
        logErr("Incorrect page received:");
        logErr(response.content);
        logErr("(Should contain: \"" + searchString + "\")");
        throw new Exception("test5 failed.");
      }

      logMsg("Access to unprotected page granted.");

      // Check to see if any of the calls to isUserInRole returned true:
      logMsg("Checking isUserInRole...");
      searchString = "!true!";
      if (response.content.contains(searchString)) {
        logErr("At least one call to isUserInRole returned true.");
        logErr("Page received:");
        logErr(response.content);
        throw new Exception("test5 failed.");
      }

      logMsg("isUserInRole test passed.");

      // Check to make sure the call to getRemoteUser() returned null.
      logMsg("Checking getRemoteUser()...");
      searchString = searchForGetRemoteUser + "null";
      if (!response.content.contains(searchString)) {
        logErr("A call to getRemoteUser() did not return null.");
        logErr("Page received:");
        logErr(response.content);
        throw new Exception("test5 failed.");
      }
      logMsg("getRemoteUser() test passed.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test5 failed: ", e);
    }
  }

  /*
   * testName: test6
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3. Also tests assertions from section 11.3.
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
   * 1. Send request to access rolereverse.jsp 2. Receive login page 3. Send
   * form response with username and password 4. Receive resource (check
   * isUserInRole for all known roles)
   */
  
  public void test6() throws Exception {
    try {
      // The first part of this test is used in test2 and test3 as
      // well, so the code has been moved to a helper method.
      requestAndGetLoginPage(pageRoleReverse, 6);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logMsg("Sending request \"" + request
          + "\" with login information (as " + username + ").");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test6 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 6);

      // Check to make sure we are authenticated by checking the page
      // content. The jsp should output "The user principal is: j2ee"
      String searchString = searchFor + username;
      if (!response.content.contains(searchString)) {
        logErr("User Principal incorrect.  Page received:");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test6 failed.");
      }
      logMsg("User Principal correct.");

      // Check to make sure isUserInRole is working properly:
      Map<String, Boolean> roleCheck = new HashMap<>();
      roleCheck.put("ADM", Boolean.FALSE);
      roleCheck.put("MGR", Boolean.TRUE);
      roleCheck.put("VP", Boolean.FALSE);
      roleCheck.put("EMP", Boolean.TRUE);
      // roleCheck.put( "Manager", new Boolean( false ) );
      if (!checkRoles(response.content, roleCheck)) {
        logErr("isUserInRole() does not work correctly.");
        logErr("Page Received:");
        logErr(response.content);
        throw new Exception("test6 failed.");
      }
      logMsg("isUserInRole() correct.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test6 failed: ", e);
    }
  }

  /*
   * testName: test7
   *
   * @assertion: Servlet Specification v2.3, sec 9.4 A special directory exists
   * within the application hierarchy named WEB-INF. This directory contains all
   * things related to the application that aren't in the document root of the
   * application. It is important to note that the WEB-INF node is not part of
   * the public document tree of the application. No file contained in the
   * WEB-INF directory may be served directly to a client.
   *
   * @test_Strategy: 1) send a http request to WEB-INF directory 2) expect 404
   * or 403 3) repeat step 1 and 2 for the following a) web-inf (for case
   * insensitive platforms) b) WEB-INF/web.xml c) web-inf/web.xml 4) based on
   * the http return code report test status
   */
  
  public void test7() throws Exception {
    List<String> statusCodes;

    try {
      // Try accessing WEB-INF
      request = pageBase + "/WEB-INF/";

      statusCodes = new ArrayList<>();
      statusCodes.add("404");
      statusCodes.add("403");

      this.testStatusCodes(request, statusCodes, "test7");

      // Try accessing /web-inf (for case insensitive platforms)
      request = pageBase + "/web-inf/";

      statusCodes = new ArrayList<>();
      statusCodes.add("404");
      statusCodes.add("403");

      this.testStatusCodes(request, statusCodes, "test7");

      // Try accessing WEB-INF/web.xml
      request = pageBase + "/WEB-INF/web.xml";

      statusCodes = new ArrayList<>();
      statusCodes.add("404");

      this.testStatusCodes(request, statusCodes, "test7");

      // Try accessing web-inf/web.xml (for case insensitive platforms)
      request = pageBase + "/web-inf/web.xml";

      statusCodes = new ArrayList<>();
      statusCodes.add("404");

      this.testStatusCodes(request, statusCodes, "test7");

      // Try accessing WEB-INF/web.xml
      request = pageBase + "/WEB-INF/web.xml";

      statusCodes = new ArrayList<>();
      statusCodes.add("404");

      this.testStatusCodes(request, statusCodes, "test7");

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
  }

  /*
   * testName: test8
   *
   * @assertion: Servlet Specification v2.3, sec 9.5 Web applications can be
   * packaged and signed, using the standard Java Archive tools, into a Web
   * ARchive format (war) file. When packaged into such a form, a META-INF
   * directory will be present which contains information useful to the Java
   * Archive tools. If this directory is present, the servlet container must not
   * allow it be served as content to a web client's request.
   *
   * @test_Strategy: 1) send a http request to META-INF directory 2) expect 404
   * or a 403 3) repeat step 1 and 2 for the following a) meta-inf (for case
   * insensitive platforms) b) META-INF/MANIFEST.MF c) meta-inf/manifest.mf 4)
   * based on the http return code, report test status
   */
  
  public void test8() throws Exception {
    try {

      // Try accessing META-INF
      request = pageBase + "/META-INF/";
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 8);

      // Receive "404" or "403" error code.
      if (response.statusToken.equals("404")
          || response.statusToken.equals("403")) {
        logMsg("Status Token " + response.statusToken);
        logMsg("Received expected error code");
      } else {
        logErr("Did not receive expected error code" + request);
        logMsg("Status Token " + response.statusToken);
        logErr("Page content:");
        logErr(response.content);
        throw new Exception("test8 failed.");
      }

      // Try accessing /meta-inf (for case insensitive platforms)
      request = pageBase + "/meta-inf/";
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 8);

      // Receive "404" or "403" error code.
      if (response.statusToken.equals("404")
          || response.statusToken.equals("403")) {
        logMsg("Status Token " + response.statusToken);
        logMsg("Received expected error code");
      } else {
        logErr("Did not receive expected error code" + request);
        logMsg("Status Token " + response.statusToken);
        logErr("Page content:");
        logErr(response.content);
        throw new Exception("test8 failed.");
      }

      // Try accessing META-INF/MANIFEST.MF
      request = pageBase + "/META-INF/MANIFEST.MF";
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 8);

      // Receive "404" or "403" error code.
      if (response.statusToken.equals("404")
          || response.statusToken.equals("403")) {
        logMsg("Status Token " + response.statusToken);
        logMsg("Received expected error code");
      } else {
        logErr("Did not receive expected error code" + request);
        logMsg("Status Token " + response.statusToken);
        logErr("Page content:");
        logErr(response.content);
        throw new Exception("test8 failed.");
      }

      // Try accessing meta-inf/manifest.mf(for case insensitive platforms)
      request = pageBase + "/meta-inf/manifest.mf";
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 8);

      // Receive "404" or "403" error code.
      if (response.statusToken.equals("404")
          || response.statusToken.equals("403")) {
        logMsg("Status Token " + response.statusToken);
        logMsg("Received expected error code");
      } else {
        logErr("Did not receive expected error code" + request);
        logMsg("Status Token " + response.statusToken);
        logErr("Page content:");
        logErr(response.content);
        throw new Exception("test8 failed.");
      }

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test8 failed: ", e);
    }
  }

  /*
   * testName: test9
   * 
   * @assertion: URLMapping from Servlet Specification v2.3, sec 11.2
   * 
   * 1) A string beginning with a / character and ending with a /* postfix is
   * used as a path mapping. 2) A string beginning with a *. prefix is used as
   * an extension mapping. 3) All other strings are used as exact matches only
   * 4) A string containing only the / character indicates that servlet
   * specified by the mapping becomes the "deException" servlet of the application.
   * In this case the servlet path is the request URI minus the context path and
   * the path info is null.
   *
   *
   * @test_Strategy: 1) Deploy a two webcomponents One.jsp and Two.jsp
   * exercising various mapping rules 2) Make a http request with a URL(based on
   * the above mapping rules) 3) Make a http request with a absolute match URL.
   * 4) compare the results obtained through step 2 and 3 and declare test
   * result
   *
   */
  
  public void test9() throws Exception {
    try {
      String testURL = null;
      String exactMatchURL = null;

      // This tests exercises the URL mapping rules
      // See compareURLContents() method description for more info

      // Note: pageOne can be a JSP or Servlet
      // 1) for JSP
      // pageOne = pageBase + "/One.jsp";
      // 2) for servlet
      // pageOne = pageBase + "/OneTest";

      // Try accessing pageOne using "/One/index.html"
      testURL = pageBase + "/One/index.html";
      exactMatchURL = pageOne;
      compareURLContents(testURL, 9, exactMatchURL);

      // Try accessing pageOne using "/One/*"
      testURL = pageBase + "/One/*";
      exactMatchURL = pageOne;
      compareURLContents(testURL, 9, exactMatchURL);

      // Note: pageTwo can be a JSP or Servlet
      // 1) for JSP
      // pageTwo = pageBase + "/Two.jsp";
      // 2) for servlet
      // pageTwo = pageBase + "/TwoTest";

      // Try accessing pageTwo using "*.jsp"
      testURL = pageBase + "/*.jsp";
      exactMatchURL = pageTwo;
      compareURLContents(testURL, 9, exactMatchURL);

      // Try accessing pageTwo using "*.two"
      testURL = pageBase + "/*.two";
      exactMatchURL = pageTwo;
      compareURLContents(testURL, 9, exactMatchURL);

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test9 failed: ", e);
    }
  }

  /*
   * testName: test10
   *
   * @assertion: Test isUserInRole(), specified in the Java Servlet
   * Specification v2.3, Sec 12.3.
   *
   * The isUserInRole method expets a String rolename. In order that this
   * rolename can be adjusted by the application assembler, or the deployer
   * without having to recompile the Servlet making the call a
   * <security-role-ref> element should be declared in the deployment descriptor
   * with the <role-name> sub-element containing the rolename passed into this
   * call. The value of the <role-link> sub-element is the <role-name> of the
   * <security-role> that the programmer is testing that the caller is mapped to
   * or not. The container is required to respect this mapping of
   * <security-role-ref> to <security-role> in this manner when determining the
   * return value of the call.
   *
   * If, however, no <security-role-ref> has been declared with <role-name> that
   * matches the argument to isUserInRole, the container must deException to
   * checking the argument against the list of <security-role>s for this web
   * application to determine whether the caller is mapped to the rolename
   * passed in.
   *
   * @test_Strategy: Note: test5 and test6 verifies the first part of the
   * assertion. This test verifies only the second part of this assertion
   *
   * 1. Send request to access Sample.jsp 2. Receive login page(make sure it is
   * the expected login page) 3. Send form response with username and password
   * 4. Receive Sample.jsp (ensure principal is correct, and ensure
   * getRemoteUser() returns the username, and ensure isUserInRole() is working
   * properly)
   */
  
  public void test10() throws Exception {
    try {
      requestAndGetLoginPage(pageSample, 10);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test10 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 10);

      // Check to make sure we are authenticated by checking the page
      // content. The jsp should output "The user principal is: j2ee"
      String searchString = searchFor + username;
      if (!response.content.contains(searchString)) {
        logErr("User Principal incorrect.  Page received:");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test10 failed.");
      }
      logMsg("User Principal correct.");

      // Check to make sure getRemoteUser returns the user name.
      searchString = searchForGetRemoteUser + username;
      if (!response.content.contains(searchString)) {
        logErr("getRemoteUser() did not return " + username + ":");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test10 failed.");
      }
      logMsg("getRemoteUser() correct.");

      // Check to make sure isUserInRole is working properly:
      Map<String, Boolean> roleCheck = new HashMap<>();
      roleCheck.put("Administrator", Boolean.TRUE);
      if (!checkRoles(response.content, roleCheck)) {
        logErr("isUserInRole() does not work correctly.");
        logErr("Page Received:");
        logErr(response.content);
        throw new Exception("test10 failed.");
      }
      logMsg("isUserInRole() correct.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test10 failed: ", e);
    }
  }

  /*
   * testName: test11
   *
   * @assertion: Servlet Specification v2.3, sec 13.2 DTD The auth-constraint
   * element indicates the user roles that should be permitted access to this
   * resource collection. The role used here must either in a security-role-ref
   * element, or be the specially reserved role-name * that is a compact syntax
   * for indicating all roles in the web application. If both * and rolenames
   * appear, the container interprets this as all roles.
   *
   * @test_Strategy: Configure allRoles.jsp to be accessible by allRoles (*)
   *
   * 1) Try accesing allRoles.jsp as the following user a) j2ee b) javajoe 2)
   * Based on the http reply, report test status
   * 
   *
   */
  
  public void test11() throws Exception {
    try {

      // Access allRoles.jsp as user "j2ee"
      logMsg("\nAccessing allRoles.jsp as user j2ee");
      requestAndGetLoginPage(pageallRoles, 11);
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test11 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 11);
      logMsg("Successfully accessed allRoles.jsp as user j2ee");

      // Access allRoles.jsp as user "javajoe"
      logMsg("\nAccessing allRoles.jsp as user javajoe");
      requestAndGetLoginPage(pageallRoles, 11);
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      postData.clear();
      postData.put("j_username", unauthUsername);
      postData.put("j_password", unauthPassword);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test11 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 11);

      logMsg("Successfully accessed allRoles.jsp as user javajoe");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test11 failed: ", e);
    }
  }

  /*
   * testName: test12
   *
   * @assertion: Servlet Specification v2.3, sec 13.2 (DTD) The
   * web-resource-collection element is used to identify a subset of the
   * resources and HTTP methods on those resources within a web application to
   * which a security constraint applies. If no HTTP methods are specified, then
   * the security constraint applies to all HTTP methods.
   *
   * @test_Strategy: 1) Do not specify any HTTP methods in the security
   * constraints of Sample.jsp
   *
   * 2) Access Sample.jsp using HTTP methods GET, HEAD, POST, DELETE and PUT.
   * 
   * 3) If Sample.jsp is accessible with all of the above HTTP methods then
   * declare test successfull otherwise report test failure
   * 
   * Note: test12 is ONLY for JSP Area
   *
   */
  
  public void test12() throws Exception {
    try {
      // Access Sample.jsp using HTTP method POST
      logMsg("\nAccessing pageSample Using HTTP method POST");
      requestAndGetLoginPage(pageSample, 12);
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test12 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 12);

      logMsg("response.content :" + response.content);
      logMsg("Successfully accessed " + pageSample + " with \"POST\"");

      // Change the request to pageSample
      request = pageSample;

      // Access pageSample using HTTP method GET
      logMsg("\nAccessing pageSample Using HTTP method GET");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test12 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 12);

      logMsg("response.content :" + response.content);
      logMsg("Successfully accessed " + pageSample + " with \"GET\"");

      // Access pageSample using HTTP method HEAD
      logMsg("\nAccessing pageSample Using HTTP method HEAD");
      response = WebUtil.sendRequest("HEAD", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test12 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 12);

      logMsg("response.content :" + response.content);

      logMsg("Successfully accessed " + pageSample + " with \"HEAD\"");

      // Read the contents of Sample.jsp and
      // upload it using HTTP method PUT
//      FileInputStream fstream = new FileInputStream(
//          tshome + "/src/web/jsp/sec/secform/Sample.jsp");

      try (InputStream fstream =
              Thread.currentThread().getContextClassLoader().getResourceAsStream("sec/secform/Sample.jsp")) {
        int fileContentLength = fstream.available();
        byte[] byteArray = new byte[1024];
        int bytesRead = fstream.read(byteArray, 0, fileContentLength);
        // Now use HTTP method DELETE and later use http method PUT

        // Delete pageSample using HTTP method DELETE
        logMsg("\nDELETE " + pageSample + " Using HTTP method DELETE ....");
        response = WebUtil.sendRequest("DELETE", InetAddress.getByName(hostname),
                portnum, request, postData, cookies);

        addNewCookies(cookies, response.cookies);

        // Check that the page was found (no error).
        if (response.isError()) {
          logErr("Could not find " + request);
          throw new Exception("test12 failed.");
        }
        logMsg("response.content :" + response.content);
        logMsg("Successfully accessed " + pageSample + " with \"DELETE\"");

        // put pageSample using HTTP method PUT
        logMsg("\nPUT " + pageSample + " Using HTTP method PUT ....");

        response = uploadUsingHttpMethodPUT("PUT",
                InetAddress.getByName(hostname), portnum, request,
                byteArray, cookies, username, password);

        // Check that the page was found (no error).
        if (response.isError()) {
          logErr("Could not find " + request);
          throw new Exception("test12 failed.");
        }
        logMsg("response.content :" + response.content);
        logMsg("response.statusToken :" + response.statusToken);
        logMsg("uploaded " + pageSample + "using HTTP method PUT");
      }
    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test12 failed: ", e);
    }
  }

  /*
   * testName: test13
   *
   * @assertion: Servlet Specification v2.3, sec 12.2 The security model does
   * not apply when a servlet uses the RequestDispatcher to invoke a static
   * resource or servlet using a forward or an include.
   *
   *
   * @test_Strategy:
   * 
   * 1) Configure two servlets (IncludedServlet and ForwardedServlet) to be
   * accessible only by administrator.
   * 
   * 2) Configure ControlServlet to be accessible by everyone (i.e no security
   * constraints for ControlServlet)
   *
   * 3) Now as a unauthenticated user access ForwardedServlet and
   * IncludedServlet from ControlServlet
   * 
   * ControlServlet ===>ForwardedServlet===>IncludedServlet
   *
   * i.e 3.1) From a ControlServlet access ForwardedServlet through dispatcher's
   * forward method.
   *
   * 3.2) From the ForwardedServlet access/include IncludedServlet through
   * Request dispatcher's include method
   *
   * 4) If the servlets(ForwardedServlet and IncludedServlet) are accessible
   * report the test success otherwise report test failure
   *
   * Note: test13 is ONLY for SERVLET Area
   *
   */
  
  public void test13() throws Exception {
    try {

      request = pageServletBase + "/ControlServlet";

      // Access ControlServlet"
      logMsg("\nAccessing ControlServlet");
      logMsg("Sending request " + request);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, null, null);
      logMsg("response.content 1:" + response.content);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test13 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 13);
      logMsg("response.content 2:" + response.content);
      logMsg("Successfully accessed ControlServlet,"
          + " ForwardedServlet and IncludedServlet");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test13 failed: ", e);
    }
  }

  /*
   * testName: test14
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.3, Sec 12.6
   *
   * Therefore, a servlet container is required to track authentication
   * information at the container level (rather than at the web application
   * level). This allows users authenticated for one web application to access
   * other resources managed by the container permitted to the same security
   * identity.
   *
   * @test_Strategy: 1. Configure pageSec(jspSec.jsp or ServletSecTest) and
   * pageSample(Sample.jsp or SampleTest ) to be accessible only by
   * Administrator 2. Send request to access jspSec.jsp 3. Receive login page 4.
   * Send form response with username and password 5. Receive jspSec.jsp (ensure
   * principal is correct, and ensure getRemoteUser() returns the username, and
   * ensure isUserInRole() is working properly) 6. Try accessing
   * pageSample(Sample.jsp or SampleTest) which is also configured to be
   * accessible with the same security identity, since we are already
   * authenticated we should be able to access pageSample without going through
   * login page again. 7. Ensure principal is still correct and getRemoteUser()
   * still returns the correct username. Also ensure isUserInRole() is still
   * working properly.
   */
  
  public void test14() throws Exception {
    try {
      logMsg("\nAccessing  pageSec: " + pageSec);
      requestAndGetLoginPage(pageSec, 14);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test14 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 14);

      // Check to make sure we are authenticated by checking the page
      // content. The jsp should output "The user principal is: j2ee"
      String searchString = searchFor + username;
      if (response.content.indexOf(searchString) == -1) {
        logErr("User Principal incorrect.  Page received:");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test14 failed.");
      }
      logMsg("User Principal correct.");

      // Check to make sure getRemoteUser returns the user name.
      searchString = searchForGetRemoteUser + username;
      if (!response.content.contains(searchString)) {
        logErr("getRemoteUser() did not return " + username + ":");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test1 failed.");
      }
      logMsg("getRemoteUser() correct.");

      // Check to make sure isUserInRole is working properly:
      Map<String, Boolean> roleCheck = new HashMap<>();
      roleCheck.put("ADM", Boolean.TRUE);
      roleCheck.put("MGR", Boolean.FALSE);
      roleCheck.put("VP", Boolean.FALSE);
      roleCheck.put("EMP", Boolean.TRUE);
      // roleCheck.put( "Administrator", new Boolean( false ) );
      if (!checkRoles(response.content, roleCheck)) {
        logErr("isUserInRole() does not work correctly.");
        logErr("Page Received:");
        logErr(response.content);
        throw new Exception("test14 failed.");
      }
      logMsg("isUserInRole() correct.");

      // Now that we are authenticated, try accessing pageSample
      // without going through the login page again.
      request = pageSample;
      logMsg("\nAccessing pageSample :" + request);
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + pageSec);
        throw new Exception("test14 failed.");
      }

      // Check to make sure we are still authenticated.
      if (!response.content.contains(searchString)) {
        logErr("User Principal incorrect.  Page received:");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test14 failed.");
      }
      logMsg("User Principal still correct.");

      // Check to make sure getRemoteUser still returns the user name.
      searchString = searchForGetRemoteUser + username;
      if (!response.content.contains(searchString)) {
        logErr("getRemoteUser() did not return " + username
            + " after lazy authentication:");
        logErr(response.content);
        logErr("(Should say: \"" + searchString + "\")");
        throw new Exception("test14 failed.");
      }
      logMsg("getRemoteUser() still correct.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test14 failed: ", e);
    }
  }

  /*
   * testName: test15
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.3, Sec 12.6
   *
   * This is similar to test14 except this is validating that we can not bypass
   * security constraints when sso is on by simply adding "/j_security_check" to
   * the request url. By adding "j_security_check" to the end of a request but
   * not specifying authN creds, we should NOT be redirected to the
   * requested/restricted page as we have not yet authenticated (even though we
   * tried to trick/confuse the system by appending 'j_security_check' onto our
   * request.)
   *
   * @test_Strategy: 1. attempt to access a protected resource by: Sending a
   * request to access url: "<pageSec>/j_security_check" 2. We should not be
   * authenticated yet so should get a response back from server with either an
   * error or login form (we must verify that we are not authenticated and that
   * we did NOT get the requested(and restricted) form back from server.
   * 
   */
  
  public void test15() throws Exception {

    String modifiedPageSec = pageSec + pageSecurityCheck;
    try {
      // 1. attempt to access a protected resource
      logger.trace("Sending request {}", modifiedPageSec);
      try {
        response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
            portnum, modifiedPageSec, null, null);
      } catch (Exception ex) {
        // if here, problem as we should have been redirected
        logErr(
            "ERROR - got exception when trying to access restricted page w/out AuthN first.", ex);
        throw new Exception("test15 failed.");
      }

      if (response != null) {
        // if we got directed to login page that is okay too
        logger.trace("response.content= {}", response.content);

        // 2. verify that the requested page was NOT accessed/found
        String searchString = "getAuthType()"; // this string appears on the
                                               // pageSec page
        if (response.content.contains(searchString)) {
          // Error - it looks like we were able to access the requested page!
          String err = "Error - we were not authenticated but were able to access the ";
          err += "following page: " + modifiedPageSec
              + " with a return status = " + response.statusToken;
          logErr(err);
          logErr("response.content = " + response.content);
          throw new Exception("test15 failed.");
        } else {
          logTrace(
              "Good - we were not able to access restricted page without authenticating.");
        }
      } else {
        logTrace("response=null");
      }

      logMsg("test15 passed.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test15 failed: ", e);
    }
  }

  /*
   * testName: test16
   *
   * @assertion: Test ability to login via the HttpServletRequst.login() method.
   * as specified in the Java Servlet Specification v3.1, Sec 13.3
   *
   * If user has not been authenticated and user attempts to access an
   * unprotected web resource, the user should be able to access it. Since the
   * user was not authenticated, calls to getUserPrincipal() should not return
   * the name of user "j2ee" since. Once in the servlet, we should be able to
   * invoke the HttpServletRequest.login() call to authenticate user "j2ee" and
   * then calls to getUserPrincipal() should return user "j2ee"
   * 
   *
   * @test_Strategy: 1. Send request to access ServletProgrammaticLogin 2. the
   * servlet performs tasks and sends response data back 3. we parse the data to
   * see if we got desired output
   */
  
  public void test16() throws Exception {
    try {

      // Send request to ProgrammaticLogin servlet and include username
      // and password for use within servlet
      request = pageProgLogin;

      // set some params that will be needed from within the pageProgLogin
      // servlet
      Map<String, String> postData = new HashMap<>();
      logger.trace("setting request parameter my_username = {}", username);
      logger.trace("setting request parameter my_password = {}", password);
      postData.put("the_username", username);
      postData.put("the_password", password);

      logMsg("Sending request " + request);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      logger.trace("response.content = {} {}", System.lineSeparator(), response.content);

      if (!response.statusToken.equals("200")) {
        logErr(
            "ERROR:  not able to do Programmatic Login in: " + pageProgLogin);
        throw new Exception("test16 failed.");
      }

      // Check that we did not receive an error
      if (response.isError()) {
        logErr("Error retrieving " + request);
        throw new Exception("test16 failed.");
      }

      logger.trace(response.content); // debug aid

      // verify there were no errors detected fom within our servlet
      String searchString = "ERROR - HttpServletRequest.login";
      if (response.content.contains(searchString)) {
        logErr(response.content);
        throw new Exception("test16 failed.");
      }

      // verify that we got success
      searchString = "HttpServletRequest.login() passed";
      if (!response.content.contains(searchString)) {
        logErr(response.content);
        throw new Exception("test16 failed.");
      }

      logMsg("test16 passed.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test16 failed: ", e);
    }
  }

  /*
   * testName: test17
   *
   * @assertion: Test FORM-based authentication, specified in the Java Servlet
   * Specification v2.2, Sec 11.5.3.
   *
   * If user has been authenticated and user attempts to access a protected web
   * resource, and user enters correct username and password of a user that is
   * authorized to access the resource, the resource is returned (similar to
   * test1)
   *
   * @test_Strategy: 1. Send request to access protected page (ie.
   * pageServletProgLogout) 2. Receive login page 3. Send form response with
   * username(j2ee) and password 4. Receive resource 5. make sure no ERRORs
   * occurrred on pageServletProgLogout and that it actually did log us out.
   * 
   */
  
  public void test17() throws Exception {
    try {
      requestAndGetLoginPage(pageServletProgLogout, 1);

      // Send response to login form with session id cookie:
      request = pageSecurityCheck;
      logMsg(
          "Sending request \"" + request + "\" with login information.");
      Map<String, String> postData = new HashMap<>();
      postData.put("j_username", username);
      postData.put("j_password", password);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Check that the page was found (no error).
      logger.trace("response.content = {}", response.content); // debug aid
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test17 failed.");
      }

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 17);
      logger.trace("response.content = {}", response.content); // debug aid

      // Check to make sure we are authenticated by checking the page
      // content. It should contain the string below to indicate we
      // properly accessed the servlet.
      String searchString = "enterred ServletProgrammaticLogout.service()";
      if (!response.content.contains(searchString)) {
        String str = "Error - Did not get expected content from page: "
            + pageServletProgLogout;
        str += "  Content should have contained: " + searchString;
        logErr(str);
        throw new Exception("test17 failed.");
      }

      // now make sure we didnt get any errors in our content
      String errString = "ERROR - HttpServletRequest.logout()";
      if (response.content.contains(errString)) {
        // there was an error msg detected in servlet content
        String str = "Error - returned in content from page: "
            + pageServletProgLogout;
        logErr(str);
        throw new Exception("test17 failed.");
      }
      logMsg("test17 passed.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test17 failed: ", e);
    }
  }

  /*
   * testName: test18
   *
   * @assertion: Test ability to authenticate using
   * HttpServletRequst.authenticate() as specified in the Java Servlet
   * Specification v3.1, Sec 13.3
   *
   * If user has not been authenticated and user attempts to access an
   * unprotected web resource, the user should be able to access it. Since the
   * user was not authenticated, calls to getUserPrincipal() should return null.
   * Calls to authenticate() should return false. Once in the servlet, we should
   * be able to invoke the HttpServletRequest.login() call to login with user
   * "j2ee" and then calls to getUserPrincipal() should return user "j2ee".
   * Calls to authenticate() should return true.
   *
   * @test_Strategy: 1. Send request to access ServletProgrammaticLogin 2. the
   * servlet performs tasks and sends response data back 3. we parse the data to
   * see if we got desired output
   */
  
  public void test18() throws Exception {
    try {

      // Send request to ProgrammaticLogin servlet and include username
      // and password for use within servlet
      request = pageProgAuthen;

      // set some params that will be needed from within the pageProgLogin
      // servlet
      Map<String, String> postData = new HashMap<>();
      postData.put("the_username", username);
      postData.put("the_password", password);

      logMsg("Sending request " + request);
      response = WebUtil.sendRequest("POST", InetAddress.getByName(hostname),
          portnum, request, postData, cookies);

      // Call followRedirect() to make sure we receive the required page
      logger.trace("YYYYY: response.content = {}{}", System.lineSeparator(), response.content);

      if (!response.statusToken.equals("200")) {
        logErr(
            "ERROR:  not able to do Programmatic Login in: " + pageProgLogin);
        throw new Exception("test18 failed.");
      }

      // Check that we did not receive an error
      if (response.isError()) {
        logErr("Error retrieving " + request);
        throw new Exception("test18 failed.");
      }

      // verify there were no errors detected fom within our servlet
      String searchString = "ERROR - HttpServletRequest.authenticate";
      if (response.content.contains(searchString)) {
        logErr(response.content);
        throw new Exception("test18 failed.");
      }

      // now verify the authenticate truely passed
      if (!response.content.contains("HttpServletRequest.authenticate passed")) {
        logErr(response.content);
        throw new Exception("test18 failed.");
      }

      logMsg("test18 passed.");

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception("test18 failed: ", e);
    }
  }

  /**
   * Uploads data from a byteArray to an URL. A WebUtil.Response object is
   * returned with the response information.
   *
   * @param method
   *          http method "PUT"
   * @param addr
   *          Address of web server
   * @param port
   *          Port of web server
   * @param req
   *          The file to request (e.g. /jsp_dep_secContextRoot/jspSec.jsp)
   * @param postData
   *          is a byteArray which contains the data to be posted
   * @param cookieList
   *          A list of cookies to send when requesting the page. null if no
   *          cookie list is to be sent.
   * @param username
   *          The username for authentication, null if no authentication
   *          required.
   * @param password
   *          The password for authentication, null if no authentication
   *          required.
   * @return WebUtil.Response object containing response information
   * @exception IOException
   *              Thrown if request could not be made
   */
  public static Response uploadUsingHttpMethodPUT(String method,
      InetAddress addr, int port, String req, byte[] postData,
      Map<String, String> cookieList, String username, String password)
      throws IOException {
    String protocol = "HTTP/1.0";
    URL requestURL;
    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String line;
    Response response = new Response();

    try {
      requestURL = new URL("http", addr.getHostName(), port, req);
      req = method + " " + req + " " + protocol;

      socket = new Socket(addr, port);

      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      out.println(req);

      if (cookieList != null) {

        // Does at least one cookie exist?
        if (!cookieList.isEmpty()) {
          String cookieString = toCookieValue(cookieList);
          // Write cookies:
          out.println(cookieString);
        }
      }

      // Send authentication information if necessary:
      if (username != null) {
        String code = WebUtil.encodeBase64(username + ":" + password);
        out.println("Authorization: Basic " + code.trim());
      }

      // Send extra header information if we are posting.
      if (postData != null) {
        out.println("Content-type: text/data");
      }

      // Read the file contents from the byte array(postData)
      // and store it in a string(fileContents)
      StringBuilder content = new StringBuilder(1024);
      ByteArrayInputStream bais = new ByteArrayInputStream(postData);
      int c;
      while ((c = bais.read()) != -1) {
        content.append((char) c);
      }
      String fileContents = content.toString();

      // TestUtil.logMsg("File Content: "+ fileContents);

      // If this is a post request, send post data:
      if ((postData != null) && method.equalsIgnoreCase("PUT")) {
        String postString = WebUtil.encodeBase64(fileContents);

        // Skip a line:
        out.println("Content-length: " + postString.length());
        out.println("");
        out.println(postString);
      } else {
        // Skip a line:
        out.println("");
      }

      out.flush();

      // Read first line and check for HTTP version and OK.
      line = in.readLine();
      if (line != null) {

        StringTokenizer st = new StringTokenizer(line.trim());
        response.versionToken = st.nextToken();
        response.statusToken = st.nextToken();
      }

      // Read each line of the header until we hit a blank line
      while ((line = in.readLine()) != null) {

        // Blank line means we are done with the header:
        if (line.trim().isEmpty()) {
          break;
        }

        // Analyze special tags location and set cookie
        if (line.toLowerCase().startsWith("location:")) {
          // This is a redirect. Extract valuable infomration:
          response.location = line.substring(10);
        } else if (line.toLowerCase().startsWith("set-cookie:")) {
          // This is a cookie. Add the cookie to the response
          // object.
          response.parseCookie(line);
        } else if (line.toLowerCase().startsWith("www-authenticate:")) {
          // Request to authenticate this page.
          response.authenticationRequested = true;
        }
      }

      // The rest is content:
      while ((line = in.readLine()) != null) {
        response.content += line + "\n";
      }

      in.close();
      out.close();
    } catch (MalformedURLException e) {
      throw new IOException("MalformedURLException: " + e.getMessage());
    } catch (UnknownHostException e) {
      throw new IOException("UnknownHostException: " + e.getMessage());
    } catch (ConnectException e) {
      throw new IOException("ConnectException: " + e.getMessage());
    }

    return response;
  }

  /**
   * Helper method that is used in tests 1, 2 and 3. Performs the following
   * actions:
   *
   * 1. Send request to access passed in url 2. Receive redirect to login page,
   * extract location and session id cookie 3. Send request to access new
   * location, send cookie 4. Receive login page
   *
   * @param request
   *          The initial page to request
   * @param testNum
   *          The test number for correct display of error messages.
   */
  private void requestAndGetLoginPage(String request, int testNum)
      throws Exception {
    // Request restricted jsp page.
    logMsg("Sending request \"" + request + "\"");
    response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
        portnum, request, null, null);

    // Check that the page was found (no error).
    if (response.isError()) {
      logErr("Could not find " + request);
      throw new Exception("test" + testNum + " failed.");
    }

    // if (response.statusToken=302)
    // then follow redirect to get the login page

    if (response.statusToken.equals("302")) {
      // We should receive a redirection to the login page:
      if (response.location == null) {
        logErr("No redirection to login page received.");
        throw new Exception("test" + testNum + " failed.");
      }

      // Extract location from redirection and format new request:
      request = WebUtil.getRequestFromURL(response.location);
      logMsg("Redirect to: " + response.location);

      // Extract cookies:
      cookies = response.cookies;

      logMsg("Before requesting redirected Page:" + "response.content="
          + response.content);

      // Request redirected page (login page):
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, cookies);

      // Check that the page was found (no error).
      if (response.isError()) {
        logErr("Could not find " + request);
        throw new Exception("test" + testNum + " failed.");
      }

    } else {
      // Extract cookies:
      cookies = response.cookies;
    }

    // Because the authentication data is posted on the registered login form,
    // There is no need to compare login page here.
    /*
     * request = pageLogin;
     * 
     * // Request login page TestUtil.logMsg( "Sending request \"" + request +
     * "\"" ); loginPageRequestResponse = WebUtil.sendRequest( "GET",
     * InetAddress.getByName( hostname ), portnum, request,
     * null, cookies );
     * 
     * // Check that the page was found (no error). if(
     * loginPageRequestResponse.isError() ) { TestUtil.logErr( "Could not find "
     * + request ); throw new Exception( "test" + testNum + " failed." ); }
     * 
     * //Compare the received login page with the expected login page // i.e
     * Check whether response.content ==loginPageRequestResponse.content if
     * (response.content.equals(loginPageRequestResponse.content))
     * TestUtil.logMsg("Received the expected login form"); else {
     * TestUtil.logMsg("response.conent\n"+response.content);
     * TestUtil.logMsg("loginPageRequestResponse.conent\n"+
     * loginPageRequestResponse.content);
     * TestUtil.logMsg("Received incorrect login form"); throw new Exception( "test"
     * + testNum + " failed." ); }
     */

  }

  /**
   * Helper method that is used in test 9. Performs the following actions:
   *
   * 1. Send request to access a jsp using testURL (for example testURL can be
   * "/*.jsp") 2. Checks the status of the http reply 3. If the page is not
   * accessible throw exception 4. If the page is accessible, then compare the
   * content from the testURL with the contents of exact match URL. i.e compare
   * 1) TEST URL : http://hostname:port/context_root/*.jsp 2) Exact match URL:
   * http://hostname:port/context_root/foo.jsp
   *
   * Note: Here *.jsp is mapped to foo.jsp
   *
   * If the contents are same then the mapping is correct, otherwise throw
   * exception
   *
   * @param testURL
   *          The test URL (for example "/*.jsp")
   * @param testNum
   *          The test number for correct display of error messages.
   * @param exactMatchURL
   *          The exact match URL (for example "/foo.jsp")
   */
  private void compareURLContents(String testURL, int testNum,
      String exactMatchURL) throws Exception {
    // Request a jsp page using testURL.
    logMsg("Sending request \"" + testURL + "\"");
    response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
        portnum, testURL, null, null);

    // Check that the page was found (no error).
    if (response.isError()) {
      logErr("Could not find " + testURL);
      throw new Exception("test" + testNum + " failed.");
    }

    Response response2 = null;

    // Request the jsp page using exactMatchURL.
    logMsg("Sending request \"" + exactMatchURL + "\"");
    response2 = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
        portnum, exactMatchURL, null, null);

    // Check that the page was found (no error).
    if (response.isError()) {
      logErr("Could not find " + exactMatchURL);
      throw new Exception("test" + testNum + " failed.");
    }

    logMsg("Comparing contents of " + testURL + " and " + exactMatchURL);

    // compare the contents of testURL and exactMatchURL
    if (!response.content.equals(response2.content)) {
      logErr(
          "MISMATCH in contents of " + testURL + " and " + exactMatchURL);
      logErr("contents of " + testURL);
      logErr(response.content);
      logErr("contents of " + exactMatchURL);
      logErr(response2.content);
      throw new Exception("test" + testNum + " failed.");
    } else {
      logMsg("Contents MATCH : correct URL mapping\n");
    }

  }

  /**
   * Helper method to check that isUserInRole is working correctly. Searches the
   * given page content for "isUserInRole( x ): !y!" for each x = key in
   * Hashtable and y = corresponding value in hashtable. If all results are as
   * expected, returns true, else returns false.
   */
  private boolean checkRoles(String content, Map<String,Boolean> roleCheck) {
    boolean pass = true;

    for (Map.Entry<String, Boolean> entry : roleCheck.entrySet()) {
      String key = entry.getKey();
      boolean expected = entry.getValue();

      String search = "isUserInRole(\"" + key + "\"): !" + expected + "!";
      String logMsg = "Searching for \"" + search + "\": ";

      if (!content.contains(search)) {
        pass = false;
        logMsg += "NOT FOUND!";
      } else {
        logMsg += "found.";
      }

      logMsg(logMsg);
    }

    return pass;
  }

  public void cleanup() throws Exception {
    logMsg("cleanup");
  }

  /**
   * Helper method that is used in tests 1, 2, 3, 5 and 6. Performs the
   * following actions:
   *
   * 1. Checks whether the response.statusToken==302 or 301
   * if(response.statusToken==302) || (response.statusToken==301) send request
   * to redirected URL 2. Returns Response object
   *
   * @param response
   *          The initial page response
   * @param testNum
   *          The test number for correct display of error messages.
   */
  public Response followRedirect(Response response, int testNum)
      throws Exception {

    // if (response.statusToken=302)
    // send request to redirected URL
    if ((response.statusToken.equals("301"))
        || (response.statusToken.equals("302"))) {
      // We should receive a redirection page:
      if (response.location == null) {
        logErr("redirection URL : null");
        throw new Exception("test" + testNum + " failed.");
      }

      // Extract location from redirection and format new request:
      request = WebUtil.getRequestFromURL(response.location);
      logMsg("Redirect to: " + response.location);

      // update cookies if the webserver choose to send cookies,
      // immediately after a successful http post request.
      addNewCookies(cookies, response.cookies);

      // Request redirected page
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, cookies);

      // After a succesful http post request,
      // Sun's Reference Implementation returns a redirected URL
      // (Note: No cookies are sent back to the client at this point)
      // Only when the client accesses the redirected URL,
      // Sun RI sends a cookie (single sign on cookie) back to
      // the client. So update cookies hashtable with new cookies
      addNewCookies(cookies, response.cookies);

      // Do not check for error code for testNum 7 and testNum 8
      // those test access an unauthorized resource and expect
      // error code.
      if (testNum != 7 && testNum != 8) {
        // Check that the page was found (no error).
        if (response.isError()) {
          logErr("Status Token " + response.statusToken);
          logErr("Could not find " + request);
          throw new Exception("test" + testNum + " failed.");
        }
      }
    } else {
      // After a successful post request, if a webserver
      // returns the webresource without redirecting to new URL
      // then update any new cookies received during this process.
      addNewCookies(cookies, response.cookies);

    }

    return response;
  }

  /**
   * Helper method that is used to update cookies
   * 
   * This helper method retrieves cookies from "newCookies" hashtable and
   * updates it to "oldCookies" hashtable
   *
   * @param oldCookies
   *          Hashtable containing original cookies
   * @param newCookies
   *          Hashtable containing new cookies error messages.
   */
  public void addNewCookies(final Map<String, String> oldCookies, Map<String, String> newCookies) {
    newCookies.forEach((key, value) -> oldCookies.put(key.trim(), value.trim()));

  }

  /**
   * Get the HttpResponse, and check the status code to see if matches one of
   * the expected status codes.
   *
   * @param request-
   *          @String request URL
   * @param expectedCodes
   *          - @List<@String> status codes we will test for
   * @param testName
   *          - The name calling test
   */
  private void testStatusCodes(String request, List<String> expectedCodes,
      String testName) throws Exception {

    try {
      logMsg("Sending request \"" + request + "\"");
      response = WebUtil.sendRequest("GET", InetAddress.getByName(hostname),
          portnum, request, null, null);

      // Call followRedirect() to make sure we receive the required page
      response = followRedirect(response, 7);

      // Check status code(s).
      if (expectedCodes.contains(response.statusToken)) {
        logMsg("Status Token " + response.statusToken);
        logMsg("Received expected error code");

      } else {
        logErr("Did not receive expected error code" + request);
        logMsg("Status Token " + response.statusToken);
        logErr("Page content: ");
        logErr(response.content);
        throw new Exception(testName + " failed.");
      }

    } catch (Exception e) {
      logErr("Caught exception: " + e.getMessage(), e);
      throw new Exception(testName + " failed: ", e);
    }

  }
}
