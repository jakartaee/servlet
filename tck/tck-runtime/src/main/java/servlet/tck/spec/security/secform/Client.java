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
package servlet.tck.spec.security.secform;

import servlet.tck.common.request.SecformClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;
import java.util.Properties;

/*
 * This  class uses the SecformClient to do most of its actual testing.
 * The tests within this file will either test security constraints using the
 * DD file or by using annotations.  The naming scheme in this file is as
 * follows:
 *   test1()      -  tests security constaints using DD file
 *   test1_anno() - tests security constraints using annotations
 * In both cases (testing using DD or annotations) the actual tests are
 * being done in the secformClient.  The real difference regards whether
 * the servlet under test is annotation or DD based.
 *
 */
public class Client extends SecformClient {

    // TOFIX
    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_sec_secform_web.war").addAsWebResource("spec/security/secform/login.jsp", "login.jsp").addAsWebResource("spec/security/secform/error.jsp", "error.jsp").addClasses(allRolesTestServlet.class, ControlServlet.class, ForwardedServlet.class, GuestPageAnnoTestServlet.class, GuestPageTestServlet.class, IncludedServlet.class, OneTestServlet.class, RoleReverseTestServlet.class, RoleReverseAnnoTestServlet.class, SampleTestServlet.class, ServletProgrammaticAuthen.class, ServletProgrammaticLogin.class, ServletProgrammaticLogout.class, ServletSecAnnoTestServlet.class, ServletSecTestServlet.class, TwoTestServlet.class, UnProtectedAnnoTestServlet.class, UnProtectedTestServlet.class).setWebXML(Client.class.getResource("servlet_sec_secform_web.xml"));
    }

    // Shared test variables:
    private Properties props = null;

    // Note: To share the commoncode between servlet and JSP,
    // the commoncode is kept under
    // (TS_HOME)/src/com/sun/ts/tests/common/jspservletsec/secformClient.java
    // This subclass(Client.java) is used to flag the superclass
    // to run servlet related secform tests
    // 
    /*
   * setup() passes "servlet" as the argument to its parent class setup()
   *
   */
    /*
   * @class.setup_props: webServerHost; webServerPort; user; password; authuser;
   * authpassword;
   *
   */
    public void setup(String[] args, Properties p) throws Exception {
        super.setup(args, p);
        props = p;
    }

    /*
   * @testName: test1
   *
   * @assertion_ids: Servlet:SPEC:142; JavaEE:SPEC:21
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login
   * page(make sure it the expected login page) 3. Send form response with
   * username and password 4. Receive jspSec.jsp (ensure principal is correct,
   * and ensure getRemoteUser() returns the username, and ensure isUserInRole()
   * is working properly) 5. Re-request jspSec.jsp 6. Ensure principal is still
   * correct and getRemoteUser() still returns the correct username. Also ensure
   * isUserInRole() is still working properly.
   *
   */
    /*
   * @testName: test1_anno
   *
   * @assertion_ids: Servlet:SPEC:142; JavaEE:SPEC:21; Servlet:SPEC:290;
   * Servlet:SPEC:291; Servlet:SPEC:293; Servlet:SPEC:296; Servlet:SPEC:297;
   * Servlet:SPEC:298;
   *
   * @test_Strategy: This does the same thing as test1() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1. Send
   * request to access jspSec.jsp 2. Receive login page(make sure it the
   * expected login page) 3. Send form response with username and password 4.
   * Receive jspSec.jsp (ensure principal is correct, and ensure getRemoteUser()
   * returns the username, and ensure isUserInRole() is working properly) 5.
   * Re-request jspSec.jsp 6. Ensure principal is still correct and
   * getRemoteUser() still returns the correct username. Also ensure
   * isUserInRole() is still working properly.
   *
   */
    @Test
    public void test1_anno() throws Exception {
        // save off pageSec so that we can reuse it
        String tempPageSec = pageSec;
        pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";
        try {
            super.test1();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageSec to orig value
            pageSec = tempPageSec;
        }
    }

    /*
   * @testName: test2
   *
   * @assertion_ids: Servlet:SPEC:142.4.3
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login page
   * 3. Send form response with username and incorrect password 4. Receive error
   * page (make sure it is the expected error page)
   *
   */
    /*
   * @testName: test2_anno
   *
   * @assertion_ids: Servlet:SPEC:142.4.3; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:293; Servlet:SPEC:296;
   *
   * @test_Strategy: This does the same thing as test2() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1. Send
   * request to access jspSec.jsp 2. Receive login page 3. Send form response
   * with username and incorrect password 4. Receive error page (make sure it is
   * the expected error page)
   *
   */
    @Test
    public void test2_anno() throws Exception {
        // save off pageSec so that we can reuse it
        String tempPageSec = pageSec;
        pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";
        try {
            super.test2();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageSec to orig value
            pageSec = tempPageSec;
        }
    }

    /*
   * @testName: test3
   *
   * @assertion_ids: Servlet:SPEC:142
   *
   * @test_Strategy: 1. Send request to access guestPage.jsp 2. Receive login
   * page 3. Send form response with username(javajoe) and password 4. Receive
   * resource (check user principal) Note: If user has not been authenticated
   * and user attempts to access a protected web resource, and user enters
   * correct username and password of a user that is authorized to access the
   * resource, the resource is returned (similar to test1, but uses user javajoe
   * instead of j2ee). This test establishes that the javajoe user is set up
   * properly.
   *
   *
   */
    /*
   * @testName: test3_anno
   *
   * @assertion_ids: Servlet:SPEC:142; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:298;
   *
   * @test_Strategy: This does the same thing as test3() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1. Send
   * request with correct authentication for user javajoe for a page javajoe is
   * allowed to access. 2. Receive login page (this verifies that the javajoe
   * user is set up properly). 3. Send form response with username(javajoe) and
   * password 4. Receive resource (check user principal)
   *
   * If user has not been authenticated and user attempts to access a protected
   * web resource, and user enters correct username and password of a user that
   * is authorized to access the resource, the resource is returned (similar to
   * test1, but uses user javajoe instead of j2ee). This test establishes that
   * the javajoe user is set up properly.
   *
   */
    @Test
    public void test3_anno() throws Exception {
        // save off pageGuest so that we can reuse it
        String tempPageGuest = pageGuest;
        pageGuest = "/servlet_sec_secform_web/GuestPageAnnoTest";
        try {
            super.test3();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageGuest to orig value
            pageGuest = tempPageGuest;
        }
    }

    /*
   * @testName: test4
   *
   * @assertion_ids: Servlet:SPEC:149;Servlet:SPEC:160;Servlet:SPEC:162
   *
   * @test_Strategy: 1. Send request to access jspSec.jsp 2. Receive login page
   * 3. Send form response with username and password 4. Receive an error
   * (expected unauthorized error) 5. Send request to access unprotected.jsp 6.
   * Receive unprotected.jsp. Note: If user has not been authenticated and user
   * attempts to access a protected web resource, and user enters correct
   * username and password of a user that is not authorized to access the
   * resource, the resource is not returned. The authenticated user is not
   * denied access to an unprotected page.
   *
   */
    /*
   * @testName: test4_anno
   *
   * @assertion_ids: Servlet:SPEC:149;Servlet:SPEC:160;Servlet:SPEC:162;
   * Servlet:SPEC:290; Servlet:SPEC:291; Servlet:SPEC:293; Servlet:SPEC:296;
   *
   * @test_Strategy: This does the same thing as test4() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1. Send
   * request to access jspSec.jsp 2. Receive login page 3. Send form response
   * with username and password 4. Receive an error (expected unauthorized
   * error) 5. Send request to access unprotected.jsp 6. Receive
   * unprotected.jsp. Note: If user has not been authenticated and user attempts
   * to access a protected web resource, and user enters correct username and
   * password of a user that is not authorized to access the resource, the
   * resource is not returned. The authenticated user is not denied access to an
   * unprotected page.
   *
   */
    @Test
    public void test4_anno() throws Exception {
        // save off pageSec so that we can reuse it
        String tempPageSec = pageSec;
        pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";
        try {
            super.test4();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageSec to orig value
            pageSec = tempPageSec;
        }
    }

    /*
   * @testName: test5
   *
   * @assertion_ids: Servlet:JAVADOC:368; Servlet:JAVADOC:369;Servlet:SPEC:154.1
   *
   * @test_Strategy: 1. Send request to access unprotected.jsp 2. Receive
   * unprotected.jsp 3. Search the returned page for "!true!", which would
   * indicate that at least one call to isUserInRole attempted by
   * unprotected.jsp returned true. 4. Check that the call to getRemoteUser()
   * returned null. Note: If user has not been authenticated and user attempts
   * to access an unprotected web resource, the resource is returned, and the
   * user is not forced to authenticate. Also, isUserInRole() must return false
   * for any valid or invalid role reference. A call to getRemoteUser() must
   * return null.
   *
   */
    /*
   * @testName: test5_anno
   *
   * @assertion_ids: Servlet:JAVADOC:368;
   * Servlet:JAVADOC:369;Servlet:SPEC:154.1; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:298;
   *
   * @test_Strategy: This does the same thing as test5() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1. Send
   * request to access unprotected.jsp 2. Receive unprotected.jsp 3. Search the
   * returned page for "!true!", which would indicate that at least one call to
   * isUserInRole attempted by unprotected.jsp returned true. 4. Check that the
   * call to getRemoteUser() returned null. Note: If user has not been
   * authenticated and user attempts to access an unprotected web resource, the
   * resource is returned, and the user is not forced to authenticate. Also,
   * isUserInRole() must return false for any valid or invalid role reference. A
   * call to getRemoteUser() must return null.
   *
   */
    @Test
    public void test5_anno() throws Exception {
        // save off pageUnprotected so that we can reuse it
        String tempPageUnprotected = pageUnprotected;
        pageUnprotected = "/servlet_sec_secform_web/UnProtectedAnnoTest";
        try {
            super.test5();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset to orig value
            pageUnprotected = tempPageUnprotected;
        }
    }

    /*
   * @testName: test6
   *
   * @assertion_ids: Servlet:SPEC:149
   *
   * @test_Strategy: Given two servlets in the same application, each of which
   * calls isUserInRole(X), and where X is linked to different roles in the
   * scope of each of the servlets (i.e. R1 for servlet 1 and R2 for servlet 2),
   * then a user whose identity is mapped to R1 but not R2, shall get a true
   * return value from isUserInRole( X ) in servlet 1, and a false return value
   * from servlet 2 (a user whose identity is mapped to R2 but not R1 should get
   * the inverse set of return values).
   *
   * Since test1 already verifies the functionality for isUserInRole returning
   * true, this test needs only verify that it should return false for the other
   * jsp. For this test, MGR and ADM are swapped, so isUserInRole() should
   * return opposite values from test1.
   *
   * 1. Send request to access rolereverse.jsp 2. Receive login page 3. Send
   * form response with username and password 4. Receive resource (check
   * isUserInRole for all known roles)
   *
   */
    /*
   * @testName: test6_anno
   *
   * @assertion_ids: Servlet:SPEC:149; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:296; Servlet:SPEC:297;
   *
   * @test_Strategy: This does the same thing as test6() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following:
   *
   * Given two servlets in the same application, each of which calls
   * isUserInRole(X), and where X is linked to different roles in the scope of
   * each of the servlets (i.e. R1 for servlet 1 and R2 for servlet 2), then a
   * user whose identity is mapped to R1 but not R2, shall get a true return
   * value from isUserInRole( X ) in servlet 1, and a false return value from
   * servlet 2 (a user whose identity is mapped to R2 but not R1 should get the
   * inverse set of return values).
   *
   * Since test1 already verifies the functionality for isUserInRole returning
   * true, this test needs only verify that it should return false for the other
   * jsp. For this test, MGR and ADM are swapped, so isUserInRole() should
   * return opposite values from test1.
   *
   * 1. Send request to access rolereverse.jsp 2. Receive login page 3. Send
   * form response with username and password 4. Receive resource (check
   * isUserInRole for all known roles)
   *
   */
    @Test
    public void test6_anno() throws Exception {
        // save off pageRoleReverse so that we can reuse it
        String tempPageReverse = pageRoleReverse;
        pageSec = "/servlet_sec_secform_web/RoleReverseAnnoTest";
        try {
            super.test6();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset tempPageReverse to orig value
            pageRoleReverse = tempPageReverse;
        }
    }

    /*
   * @testName: test7
   *
   * @assertion_ids: Servlet:SPEC:89
   *
   * @test_Strategy: 1) send a http request to WEB-INF directory 2) expect 404
   * or 403 3) repeat step 1 and 2 for the following a) web-inf (for case
   * insensitive platforms) b) WEB-INF/web.xml c) web-inf/web.xml 4) based on
   * the http return code, report test status
   *
   */
    /*
   * @testName: test8
   *
   * @assertion_ids: Servlet:SPEC:92.1
   *
   * @test_Strategy: 1) send a http request to META-INF directory 2) expect 404
   * or 403 3) repeat step 1 and 2 for the following a) meta-inf (for case
   * insensitive platforms) b) META-INF/MANIFEST.MF c) meta-inf/manifest.mf 4)
   * based on the http return code, report test status
   *
   */
    /*
   * @testName: test9
   *
   * @assertion_ids: Servlet:SPEC:134
   *
   * @test_Strategy: 1) Deploy a two webcomponents One.jsp and Two.jsp
   * exercising various mapping rules 2) Make a http request with a URL(based on
   * the above mapping rules) 3) Make a http request with a absolute match URL.
   * 4) compare the results obtained through step 2 and 3 and declare test
   * result Note:
   *
   * 1) A string beginning with a / character and ending with a /* postfix is
   * used as a path mapping. 2) A string beginning with a *. prefix is used as
   * an extension mapping. 3) All other strings are used as exact matches only
   * 4) A string containing only the / character indicates that servlet
   * specified by the mapping becomes the "deException" servlet of the application.
   * In this case the servlet path is the request URI minus the context path and
   * the path info is null.
   *
   */
    /*
   * @testName: test10
   *
   * @assertion_ids: Servlet:SPEC:138; Servlet:SPEC:139; Servlet:SPEC:294;
   * 
   * @test_Strategy: Note: test5 and test6 verifies the first part of the
   * assertion. This test verifies only the second part of this assertion
   *
   * 1. Send request to access SampleTestServlet 2. Receive login page(make sure
   * it is the expected login page) 3. Send form response with username and
   * password 4. Receive Sample.jsp (ensure principal is correct, and ensure
   * getRemoteUser() returns the username, and ensure isUserInRole() is working
   * properly)
   *
   */
    /*
   * @testName: test11
   *
   * @assertion_ids: Servlet:SPEC:150
   *
   * @test_Strategy: Configure allRoles.jsp to be accessible by allRoles (
   * Administrator and * )
   *
   * 1) Try accesing allRoles.jsp as the following user a) j2ee b) javajoe 2)
   * Based on the http reply, report test status
   *
   * Note: The auth-constraint element indicates the user roles that should be
   * permitted access to this resource collection. The role used here must
   * either in a security-role-ref element, or be the specially reserved
   * role-name * that is a compact syntax for indicating all roles in the web
   * application. If both * and rolenames appear, the container interprets this
   * as all roles.
   *
   */
    /* ***NOTE:test12 is only for JSP */
    /*
   * @testName: test13
   *
   * @assertion_ids: Servlet:SPEC:137; JavaEE:SPEC:24
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
    /*
   * @testName: test14
   *
   * @assertion_ids: Servlet:SPEC:144
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
   * working properly. Note: servlet container is required to track
   * authentication information at the container level (rather than at the web
   * application level). This allows users authenticated for one web application
   * to access other resources managed by the container permitted to the same
   * security identity.
   *
   */
    /*
   * @testName: test14_anno
   *
   * @assertion_ids: Servlet:SPEC:144; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:293; Servlet:SPEC:296;
   *
   * @test_Strategy: This does the same thing as test14() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following: 1.
   * Configure pageSec(jspSec.jsp or ServletSecTest) and pageSample(Sample.jsp
   * or SampleTest ) to be accessible only by Administrator 2. Send request to
   * access jspSec.jsp 3. Receive login page 4. Send form response with username
   * and password 5. Receive jspSec.jsp (ensure principal is correct, and ensure
   * getRemoteUser() returns the username, and ensure isUserInRole() is working
   * properly) 6. Try accessing pageSample(Sample.jsp or SampleTest) which is
   * also configured to be accessible with the same security identity, since we
   * are already authenticated we should be able to access pageSample without
   * going through login page again. 7. Ensure principal is still correct and
   * getRemoteUser() still returns the correct username. Also ensure
   * isUserInRole() is still working properly. Note: servlet container is
   * required to track authentication information at the container level (rather
   * than at the web application level). This allows users authenticated for one
   * web application to access other resources managed by the container
   * permitted to the same security identity.
   *
   */
    @Test
    public void test14_anno() throws Exception {
        // save off pageSec so that we can reuse it
        String tempPageSec = pageSec;
        pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";
        try {
            super.test14();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageSec to orig value
            pageSec = tempPageSec;
        }
    }

    /*
   * @testName: test15
   *
   * @test_Strategy: This is similar to test14 except this is validating that we
   * can not bypass security constraints when sso is on by simply adding
   * "/j_security_check" to the request url. By adding "j_security_check" to the
   * end of a request but not specifying authN creds, we should NOT be
   * redirected to the requested/restricted page as we have not yet
   * authenticated (even though we tried to trick/confuse the system by
   * appending 'j_security_check' onto our request.) 1. attempt to access a
   * protected resource by: Sending a request to access url:
   * "<pageSec>/j_security_check" 2. We should not be authenticated yet so
   * should get a response back from server with either an error or login form
   * (we must verify that we are not authenticated and that we did NOT get the
   * requested(and restricted) form back from server.
   *
   */
    /*
   * @testName: test15_anno
   *
   * @assertion_ids: Servlet:SPEC:144; Servlet:SPEC:290; Servlet:SPEC:291;
   * Servlet:SPEC:293; Servlet:SPEC:296;
   *
   * @test_Strategy: This does the same thing as test15() with the difference
   * being that this test is using a servlet w/ security constraints defined
   * thru annotations instead of DD. This test validates the following:
   *
   * This is similar to test14 except this is validating that we can not bypass
   * security constraints when sso is on by simply adding "/j_security_check" to
   * the request url. By adding "j_security_check" to the end of a request but
   * not specifying authN creds, we should NOT be redirected to the
   * requested/restricted page as we have not yet authenticated (even though we
   * tried to trick/confuse the system by appending 'j_security_check' onto our
   * request.)
   *
   * 1. attempt to access a protected resource by: Sending a request to access
   * url: "<pageSec>/j_security_check" 2. We should not be authenticated yet so
   * should get a response back from server with either an error or login form
   * (we must verify that we are not authenticated and that we did NOT get the
   * requested(and restricted) form back from server.
   *
   */
    @Test
    public void test15_anno() throws Exception {
        // save off pageSec so that we can reuse it
        String tempPageSec = pageSec;
        pageSec = "/servlet_sec_secform_web/ServletSecAnnoTest";
        try {
            super.test15();
        } catch (Exception e) {
            throw e;
        } finally {
            // reset pageSec to orig value
            pageSec = tempPageSec;
        }
    }

    /*
   * @testName: test16
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
   *
   */
    /*
   * @testName: test17
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
    /*
   * @testName: test18
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
   *
   */
    @Test()
    public void test1() throws Exception {
        super.test1();
    }

    @Test()
    public void test10() throws Exception {
        super.test10();
    }

    @Test()
    public void test11() throws Exception {
        super.test11();
    }

    @Test()
    public void test13() throws Exception {
        super.test13();
    }

    @Test()
    public void test14() throws Exception {
        super.test14();
    }

    @Test()
    public void test15() throws Exception {
        super.test15();
    }

    @Test()
    public void test16() throws Exception {
        super.test16();
    }

    @Test()
    public void test17() throws Exception {
        super.test17();
    }

    @Test()
    public void test18() throws Exception {
        super.test18();
    }

    @Test()
    public void test2() throws Exception {
        super.test2();
    }

    @Test()
    public void test3() throws Exception {
        super.test3();
    }

    @Test()
    public void test4() throws Exception {
        super.test4();
    }

    @Test()
    public void test5() throws Exception {
        super.test5();
    }

    @Test()
    public void test6() throws Exception {
        super.test6();
    }

    @Test()
    public void test7() throws Exception {
        super.test7();
    }

    @Test()
    public void test8() throws Exception {
        super.test8();
    }

    @Test()
    public void test9() throws Exception {
        super.test9();
    }
}
