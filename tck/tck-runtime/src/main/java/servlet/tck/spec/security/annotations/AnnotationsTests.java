/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.spec.security.annotations;

import servlet.tck.util.WebUtil;
import servlet.tck.common.client.BaseTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/*
 *
 */
public class AnnotationsTests extends BaseTckTest {

  // TOFIX

  // Constants:
  private static final String USERNAME = "user";

  private static final String PASSWORD = "password";

  private static final String UNAUTH_USERNAME = "authuser";

  private static final String UNAUTH_PASSWORD = "authpassword";

  private static final String USER_PRINCIPAL_SEARCH = "The user principal is: "; // (+username)

  private static final String REMOTE_USER_SEARCH = "getRemoteUser(): "; // (+username)

  // fields:
  private String pageDeny = null;

  private String pageSec = null;

  private String pageGuest = null;

  private String pageUnprotected = null;

  private String pageTrans = null;

  private String pagePartial = null;

  private String username = null;

  private String password = null;

  private String unauthUsername = null;

  private String unauthPassword = null;

  private String realm = null;

  private WebUtil.Response response = null;

  private String request = null;

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_sec_annotations_web.war")
            .addClasses(DenyAllServlet.class, GuestPageTestServlet.class, PartialDDServlet.class,
                    ServletSecTestServlet.class, UnProtectedTestServlet.class)
            .setWebXML(AnnotationsTests.class.getResource("servlet_sec_annotations_web.xml"));
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; securedWebServicePort;
   * user; password; authuser; authpassword; ts_home;
   *
   */
  public void setup(String[] args, Properties p) throws Exception {
    super.setup(args, p);

    // user=j2ee
    // password=j2ee
    // authuser=javajoe
    // authpassword=javajoe

    //portnum = Integer.parseInt(p.getProperty("securedWebServicePort"));

    // TOFIX configurable
    try {
      username = System.getProperty("tck.servlet.username", "j2ee");
      password = System.getProperty("tck.servlet.password", "j2ee");
      unauthUsername = System.getProperty("tck.servlet.unauth.username", "javajoe");
      unauthPassword = System.getProperty("tck.servlet.unauth.password", "javajoe");
      realm = System.getProperty("tck.servlet.realm", "");

      String pageServletBase = getContextRoot();//"/servlet_sec_annotations_web";

      String pageServletDeny = pageServletBase + "/ServletDenyAll";

      String pageServletSec = pageServletBase + "/ServletSecTest";

      String pageServletGuest = pageServletBase + "/GuestPageTest";

      String pageServletUnprotected = pageServletBase + "/UnProtectedTest";

      String pageTransport = pageServletBase + "/TransportServlet";

      String pagePartialDD = pageServletBase + "/PartialDDTest";

      pageSec = pageServletSec;
      pageDeny = pageServletDeny;
      pageGuest = pageServletGuest;
      pageUnprotected = pageServletUnprotected;
      pageTrans = pageTransport;
      pagePartial = pagePartialDD;

    } catch (Exception e) {
      logErr("Error: got exception: ", e);
    }
  }

  /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:215;
   *
   * @assertion: 1. teh DenyAll annotation must be supported by the Web
   * container. Access a web resource that uses the DenyAll annotation applied
   * at the class level should result in an access denied.
   *
   * @test_Strategy: 1. Send request to access DenyAllServlet 2. Receive an
   * access denied
   */
  @Test
  public void test1() throws Exception {
    trace("testing DenyAll");

    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test1");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pageDeny));
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN (403) code instead
      // of
      // UNAUTHORIZED (401) so retry and check for FORBIDDEN code. If it still
      // fails then we have an issue.
      trace(
          "we tested for Status Code=401 but we could have a 403 code, so check for that.");
      TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test1");
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pageDeny));
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    trace(
        "test1 passed:  we were not allowed to perform GET on a servlet with DenyAll anno");
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:218; Servlet:SPEC:294;
   *
   * @assertion: 1. Servlet 3.0 spec (section 13.4 - 3rd from last para) states:
   * "When a security-constraint in the portable deployment descriptor includes
   * a url-pattern that matches a request URL, the security annotations
   * described in this section have no effect on the access policy that applies
   * to the request URL."
   * 
   *
   * @test_Strategy: 1. We have GuestPageTestServlet setup with DenyAll anno but
   * we have DD setup with roles and security-constraints that say POST can be
   * accessed by Manager role (via user=javajoe) and according to spec
   * statement, the DenyAll anno should be ignored. 2. attempt to POST as user
   * javajoe should allow access since DD grants it. 3. do POST with incorrect
   * authentication (ie "j2ee") should NOT allows access since "j2ee" is not in
   * roles as defined in DD.
   */
  @Test
  public void test2() throws Exception {

    StringBuilder sb = new StringBuilder(100);
    sb.append(USER_PRINCIPAL_SEARCH).append(unauthUsername);

    // attempt to doPost as "javajoe" should be allowed
    trace(
        "Sending request to resource with valid username/password, but not the right roles...");
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test2");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, unauthUsername); // "javajoe"
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, unauthPassword); // "javajoe"
    //TEST_PROPS.get().setProperty(BASIC_AUTH_REALM, realm); // default
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception ex) {
      TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test2");
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
      TEST_PROPS.get().setProperty(BASIC_AUTH_USER, unauthUsername); // "javajoe"
      TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, unauthPassword); // "javajoe"
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
    }

    // attempt to doGet as "javajoe" should be allowed due to DD and
    // the RolesAllowed anno in GuestPageTestServlet should be ignored.
    // note: doGet metho prints out userprincipal name that we are going to
    // check
    trace(
        "Sending request to resource with valid username/password, but not the right roles...");
    TEST_PROPS.get().setProperty(SEARCH_STRING, sb.toString());
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test2");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pageGuest));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, unauthUsername); // "javajoe"
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, unauthPassword); // "javajoe"
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();

    // attempt to doGet as "j2ee" should NOT be allowed since the DD only
    // states to allow Manager role (ie javajoe). The RolesAllowed anno
    // defined in GuestPageTestServlet.doGet should be completely ignored per
    // spec statement cited in the javadoc for this test.
    trace(
        "Sending request to resource with valid username/password, but not the right roles...");
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test2");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // "j2ee"
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN code
      // so retry with that code - if it still fails then we have an issue.
      TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test2");
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
      TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // "j2ee"
      TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    trace("test2");
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:216;
   *
   * @assertion: 1. Servlet 3.0 (section 13.4) states: "When an annotation is
   * specified at both the class and method level, the method targeted
   * annotation overrides that on the class (for the method)" 2. PermitAll func
   * must be supported by web container
   *
   * @test_Strategy: 1. create ServletSecTestServlet with DeclareRoles
   * annotation at the class level as well as the ServletSecurity anno. 2. we
   * created ServletSecTestServlet.doGet() method with PermitAll access 3. try
   * to access doGet using creds that would normally fail to ensure PermitAll
   * really does work.
   *
   */
  @Test
  public void test3() throws Exception {
    String invalidUser = "invalid";

    // this should all work as @PermitAll is set on ServletSecTestServlet.doGet
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test3");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, unauthUsername); // try using
                                                             // "invalid" creds
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, unauthPassword); // and it should
                                                               // still work
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();
    trace(
        "Class level annotation of Roles allowed overridden by method level permit all access.");

    trace("test3 passed.");
  }

  /*
   * @testName: test4
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:216;
   *
   * @assertion: 1. Servlet 3.0 (section 13.4) states: "When an annotation is
   * specified at both the class and method level, the method targeted
   * annotation overrides that on the class (for the method) " 2. DenyAll can be
   * applied to class and method level so here we are validating its use at the
   * method level.
   *
   * @test_Strategy: 1. create ServletSecTestServlet with RolesAllowed
   * annotation at the class level. 2. create ServletSecTestServlet.doPost
   * method with DenyAll access set 3. try to access doPost using creds that
   * normally work to ensure that setting deny all access really does work.
   *
   */
  @Test
  public void test4() throws Exception {
    String invalidUser = "invalid";

    // now see if we get access denied - since DenyAll anno set on doPost method
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageSec));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username);
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED); // check for status code
                                                       // 401
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN (403) code instead
      // of
      // UNAUTHORIZED (401) so retry and check for FORBIDDEN code. If it still
      // fails then we have an issue.
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageSec));
      TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username);
      TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password);
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    trace(
        "Class level setting of roles allowed was overridden by deny all access at method level.");

    trace("test4 passed.");
  }

  /*
   * @testName: test5
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:215;
   *
   * @assertion: Servlet 3.0 spec (section 13.4) states: "These annotations may
   * be specified on (that is, targeted to) an HttpServlet implementation class
   * or on specific method(s) of the implementation class as defined below."
   * 
   * @test_Strategy: 1. Send request for unprotected servlet that uses the
   * PermitAll access at the class level. 2. Receive page
   */
  @Test
  public void test5() throws Exception {

    trace("Sending request to resource that uses the PermitAll annotation....");
    TEST_PROPS.get().setProperty(TEST_NAME, "BasicSec/Test5");
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, unauthUsername); // try using
                                                             // "invalid" creds
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, unauthPassword); // and it should
                                                               // still work
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pageUnprotected));
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();
    trace("Class level PermitAll anno returned expected results");
    trace("test5 passed.");
  }

  /*
   * @testName: test6
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:218; Servlet:SPEC:294;
   *
   * @assertion: This validates Servlet 3.0 spec section 13.4, which says: "When
   * a security-constraint in the portable deployment descriptor includes a
   * url-pattern that matches a request URL, the security annotations described
   * in this section have no effect on the access policy that applies to the
   * request URL."
   *
   * @test_Strategy: 1. Send request with correct authentication for url pattern
   * that is defined with a DD that has security-constraints 2. Even if the
   * servlet (eg url pattern) is defined with DenyAll anno, it should be ignored
   * since the DD has overriding security-constraint note: pageGuest is defined
   * with both: DenyAll and DD with security-constraint 3. In this case, the
   * GuestPage should be accessible and the DenyAll access setting should be
   * ignored. 4. Additionally, the DD has an authconstraint set for Manager
   * (user==javajoe) so we want to verify that user is the principal passed into
   * the servlet.
   */
  @Test
  public void test6() throws Exception {

    trace(
        "Sending request to resource where DD allows access to override any restricting annotation...");
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test6");

    // attempt to doGet as "j2ee" should NOT be allowed since the DD only
    // states to allow Manager role (ie javajoe). The RolesAllowed annon
    // defined in GuestPageTestServlet.doGet should be completely ignored per
    // spec statement cited in the javadoc for this test.
    trace(
        "Sending request to resource with valid username/password, but not the right roles...");

    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test6");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // "j2ee"
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN code
      // so retry with that code - if it still fails then we have an issue.
      TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test6");
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pageGuest));
      TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // "j2ee"
      TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    trace("User successfully accessed the resource");
  }

  /*
   *
   * @testName: test7
   *
   * @assertion_ids:
   *
   * @assertion: 1. http-method-omission
   * 
   * 
   *
   * @test_Strategy: 1. Send request to access servlet where there is a
   * corresponding DD that excludes POST method via the http-method-omission DD
   * element. (This means that all access to the PartialDD only allowed by
   * Administrator EXCEPT for POST which has NO security constraints and is thus
   * allowed by all. 2. Receive an access denied when trying to access GET with
   * no cred (if the http-method-omission does its job.)
   * 
   */
  @Test
  public void test7() throws Exception {
    trace("testing http-method-omission");

    // try to access servlet via GET with NO creds/roles should fail
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test7");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pagePartial));
    TEST_PROPS.get().setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN (403) code instead
      // of
      // UNAUTHORIZED (401) so retry and check for FORBIDDEN code. If it still
      // fails then we have an issue.
      trace(
          "we tested for Status Code=401 but we could have a 403 code, so check for that.");
      TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test7");
      TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pagePartial));
      TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // try as "j2ee"
      TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // try as "j2ee"
      TEST_PROPS.get().setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }
    trace("test7:  complete doGet() with no creds - now starting doPost");

    // try to access servlet via GET with creds/roles should pass
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test7");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("GET", pagePartial));
    TEST_PROPS.get().setProperty(BASIC_AUTH_USER, username); // try as "j2ee"
    TEST_PROPS.get().setProperty(BASIC_AUTH_PASSWD, password); // try as "j2ee"
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();
    trace("test7:  complete doGet() with creds - now starting doPost");

    // we should be allowed to do POST with NO creds
    TEST_PROPS.get().setProperty(TEST_NAME, "SecAnnotations/Test7");
    TEST_PROPS.get().setProperty(REQUEST, getRequestLine("POST", pagePartial));
    TEST_PROPS.get().setProperty(STATUS_CODE, OK);
    invoke();
    trace("test7:  complete doPost() with no creds.");

    trace("test7 passed:  servlet with http-method-omission settings.");
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
   * Simple wrapper around TestUtil.logTrace().
   * 
   * @param message
   *          - the message to log
   */
  private void trace(String message) {
    logger.debug("[Client]: {}", message);
  }

}
