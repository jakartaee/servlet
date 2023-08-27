/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.ts.tests.servlet.spec.security.metadatacomplete;

import com.sun.ts.tests.servlet.common.client.BaseUrlClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.util.Properties;

/*
 * These tests are going to be similar to the tests that are in 
 * com.sun.ts.tests.servlet.spec.security.annotations with the key difference
 * being that these tests have a DD file which states metadata-complete=true.
 * When metadata-complete=true, then annotations should be ignored for the
 * application(s) in this jar.
 *
 */
public class Client extends BaseUrlClient {



  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_sec_metadatacomplete_web.war")
            .addClasses(DenyAllServlet.class, GuestPageTestServlet.class, ServletSecTestServlet.class,
                    UnProtectedTestServlet.class)
            .setWebXML(Client.class.getResource("servlet_sec_metadatacomplete_web.xml"));
  }


  private static final String CLASS_TRACE_HEADER = "[Client]: ";

  private static final String USER_PRINCIPAL_SEARCH = "The user principal is: "; // (+username)

  // fields:
  private String pageDeny = null;

  private String pageSec = null;

  private String pageGuest = null;

  private String pageUnprotected = null;

  private String pageServletBase = "/servlet_sec_metadatacomplete_web";

  private String pageServletDeny = pageServletBase + "/ServletDenyAll";

  private String pageServletSec = pageServletBase + "/ServletSecTest";

  private String pageServletGuest = pageServletBase + "/GuestPageTest";

  private String pageServletUnprotected = pageServletBase + "/UnProtectedTest";

  private String username = null;

  private String password = null;

  private String unauthUsername = null;

  private String unauthPassword = null;


  /*
   * @class.setup_props: webServerHost; webServerPort; securedWebServicePort;
   * user; password; authuser; authpassword; ts_home;
   *
   */
  public void setup(String[] args, Properties p) throws Exception {
    super.setup(args, p);

    try {
      username = p.getProperty(USERNAME);
      password = p.getProperty(PASSWORD);
      unauthUsername = p.getProperty(UNAUTH_USERNAME);
      unauthPassword = p.getProperty(UNAUTH_PASSWORD);

      pageSec = pageServletSec;
      pageDeny = pageServletDeny;
      pageGuest = pageServletGuest;
      pageUnprotected = pageServletUnprotected;

    } catch (Exception e) {
      logErr("Error: got exception: ", e);
    }
  }

  /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:215; Servlet:SPEC:228;
   * Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:217; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
   *
   * @assertion: 1. annotations that permit all must be supported by the Web
   * container. However, if the metadata-complete flag == true in the DD, then
   * the annotation must be ignored. (per assertion Servlet:SPEC:228)
   *
   * @test_Strategy: 1. Send request to access DenyAllServlet - which is going
   * to be granted all access via annotation but will be marked as DenyAll in
   * the DD. The DD MUST take precedence. 2. Receive an access denied
   */
  @Test
  public void test1() throws Exception {
    logger.trace("testing that we can NOT access: {}", pageDeny);

    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test1");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageDeny));
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN (403) code instead
      // of
      // UNAUTHORIZED (401) so retry and check for FORBIDDEN code. If it still
      // fails then we have an issue.
      logger.trace(
          "we tested for Status Code=401 but we could have a 403 code, so check for that.");
      TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test1");
      TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageDeny));
      TEST_PROPS.setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    logger.trace("test1 passed:  we were not allowed to perform GET on servlet: {}", pageDeny);
  }

  /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:217; Servlet:SPEC:218;
   * Servlet:SPEC:228; Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
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
   * statement, the annotation must be ignored since metadata-complete=true in
   * the DD. 2. attempt to POST & GET as user javajoe should allow access since
   * DD grants it and since metadata-complete is true, the annotations security
   * constraints do NOT get used.
   */
  @Test
  public void test2() throws Exception {

    // attempt to POST as "javajoe" should be allowed
    logger.trace("POST w/ user= {} should be allowed due to DD declaration", unauthUsername);
    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test2");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageGuest));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, unauthUsername);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, unauthPassword);
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    invoke();

    // attempt to GET as "javajoe" should be allowed due to DD and
    // the RolesAllowed anno in GuestPageTestServlet should be ignored.
    // note: doGet metho prints out userprincipal name that we are going to
    // check
    logger.trace("GET w/ user= {} should be allowed due to DD declaration", unauthUsername);
    TEST_PROPS.setProperty(SEARCH_STRING, USER_PRINCIPAL_SEARCH+unauthUsername);
    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test2");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageGuest));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, unauthUsername); // "javajoe"
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, unauthPassword); // "javajoe"
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    invoke();

    logger.trace("success - DD's role access was honored while the conflicting annotation was ignored.");
    logger.trace("test2 passed.");
  }

  /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:216; Servlet:SPEC:217;
   * Servlet:SPEC:228; Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
   *
   * @assertion: 1. Servlet 3.0 spec (section 13.4 - 3rd from last para) states:
   * "When a security-constraint in the portable deployment descriptor includes
   * a url-pattern that matches a request URL, the security annotations
   * described in this section have no effect on the access policy that applies
   * to the request URL."
   * 
   *
   * @test_Strategy: This is another variation of a test which validates that DD
   * settings override annotation settings when the url-pattern of both match
   * AND when the DD's metadata-complete = true. 1. create ServletSecTestServlet
   * with a declared annotation at the class level as well as a conflicting DD
   * declaration. The annotation should get ignored and the DD should take
   * precedence since the DD has set metadata-complete=true. 2. validate that
   * the annotation declaration which states ServletSecTest POST can be accessed
   * is WRONG since the DD declares that POST is set to be deny for all roles.
   * 3. try to access GET using valid creds (for Administrator=j2ee) since the
   * DD specifies GET for role=Adminstrator. (Note that annotation declares GET
   * should be deny by role=Adminstrator - so verify this annotation is NOT
   * used.)
   *
   */
  @Test
  public void test3() throws Exception {

    // Post is set to be accessed by Administrator in the annotation
    // declaration *but* Post is also set to be accessed only by
    // Manager(javajoe)
    // in DD. So attempts to POST as Administrator=j2ee should fail.
    logger.trace("Attempting to POST as user= {} should be denied due to DD security.", username);
    TEST_PROPS.setProperty(TEST_NAME, "SecurityAnno/Test3");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageSec));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // this is username for
                                                       // Administrator not
                                                       // Manager
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED); // check for status code
                                                       // 401
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN (403) code instead
      // of
      // UNAUTHORIZED (401) so retry and check for FORBIDDEN code. If it still
      // fails then we have an issue.
      TEST_PROPS.setProperty(TEST_NAME, "SecurityAnno/Test3");
      TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageSec));
      TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // this is username for
                                                         // Administrator not
                                                         // Manager
      TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
      TEST_PROPS.setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    // now verify that GET can be done by role=Administrator (per DD definition)
    logger.trace("Attempting to GET as user= {} should be allowed due to DD security.", username);
    TEST_PROPS.setProperty(TEST_NAME, "BasicSec/Test3");
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // this is username for
                                                       // Administrator
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password);
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageSec));
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    invoke();

    logger.trace("Class level annotation setting was overridden by DD.");
    logger.trace("test3 passed.");
  }

  /*
   * @testName: test4
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:216; Servlet:SPEC:217;
   * Servlet:SPEC:228; Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
   *
   * @assertion: 1. Servlet 3.0 (section 13.4) states: "When an annotation is
   * specified at both the class and method level, the method targeted
   * annotation overrides that on the class (for the method) " 2. DenyAll can be
   * applied to class and method level so here we are validating its use at the
   * method level.
   *
   * @test_Strategy: 1. create ServletSecTestServlet with annotation on the
   * servlet that sets GET & POST to be denied access by all 2. do Post w/
   * correct credentials (for Manager) and should be allowed since DD declares
   * this constraint.
   *
   */
  @Test
  public void test4() throws Exception {

    // now see if we get access denied - since DenyAll anno set on doPost method
    TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageSec));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, unauthUsername);
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, unauthPassword);
    TEST_PROPS.setProperty(STATUS_CODE, OK); // check for status code 401
    invoke();

    logger.trace("Success - DD allowed POST by user={}", unauthUsername);

    logger.trace("test4 passed.");
  }

  /*
   * @testName: test5
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:215; Servlet:SPEC:217;
   * Servlet:SPEC:228; Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
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

    logger.trace("GET w/ user= {} should be allowed access as DD leaves this servlet unprotected.", unauthUsername);
    TEST_PROPS.setProperty(TEST_NAME, "BasicSec/Test5");
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // try using valid creds
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password); // and it should still
                                                         // work
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageUnprotected));
    TEST_PROPS.setProperty(STATUS_CODE, OK);
    invoke();
    logger.trace("Class level PermitAll anno returned expected results");
    logger.trace("test5 passed.");
  }

  /*
   * @testName: test6
   *
   * @assertion_ids: Servlet:SPEC:214; Servlet:SPEC:217; Servlet:SPEC:218;
   * Servlet:SPEC:228; Servlet:SPEC:294; Servlet:SPEC:295; Servlet:SPEC:198;
   * Servlet:SPEC:289; Servlet:SPEC:258.3;
   *
   * @assertion: This validates Servlet 3.0 spec section 13.4, which says: "When
   * a security-constraint in the portable deployment descriptor includes a
   * url-pattern that matches a request URL, the security annotations described
   * in this section have no effect on the access policy that applies to the
   * request URL."
   *
   * @test_Strategy: 1. do POST or GET with incorrect authentication (ie "j2ee")
   * should NOT allows access since "j2ee" is not in roles as defined in DD. The
   * DD only allows role=Manager (with user=javajoe) to GET or POST.
   * 
   */
  @Test
  public void test6() throws Exception {

    logger.trace("Sending request to resource where DD allows access to override any restricting annotation...");
    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test6");

    // attempt to GET as "j2ee" should NOT be allowed since the DD only
    // states to allow Manager role (ie javajoe). The annotation
    // defined in GuestPageTestServlet declares that GET can be accessed
    // by Administrator role (e.g. user=j2ee) but this annotation
    // must be completely ignored sine the DD has set metadata-complete=true.
    logger.trace("GET w/ user= {} should NOT be allowed due to DD declaration", username);
    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test6");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageGuest));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // "j2ee"
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN code
      // so retry with that code - if it still fails then we have an issue.
      logger.trace("retrying: GET w/ user= {} should still NOT be allowed due to DD declaration", username);
      TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test6");
      TEST_PROPS.setProperty(REQUEST, getRequestLine("GET", pageGuest));
      TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // "j2ee"
      TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
      TEST_PROPS.setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    // attempt to POST as "j2ee" should NOT be allowed since the DD only
    // states to allow Manager role (ie javajoe).
    logger.trace("POST w/ user= {} should NOT be allowed due to DD declaration", username);
    TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test6");
    TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageGuest));
    TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // "j2ee"
    TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
    TEST_PROPS.setProperty(STATUS_CODE, UNAUTHORIZED);
    try {
      invoke();
    } catch (Exception e) {
      // its possible we were denied access with a FORBIDDEN code
      // so retry with that code - if it still fails then we have an issue.
      logger.trace("retrying: POST w/ user= {} should still NOT be allowed due to DD declaration", username);
      TEST_PROPS.setProperty(TEST_NAME, "SecAnnotations/Test6");
      TEST_PROPS.setProperty(REQUEST, getRequestLine("POST", pageGuest));
      TEST_PROPS.setProperty(BASIC_AUTH_USER, username); // "j2ee"
      TEST_PROPS.setProperty(BASIC_AUTH_PASSWD, password); // "j2ee"
      TEST_PROPS.setProperty(STATUS_CODE, FORBIDDEN);
      invoke();
    }

    logger.trace("Success - we were not allowed to POST or GET as role=Administrator (user=j2ee).");
    logger.trace("Test6 passed.");
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

}
