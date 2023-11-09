/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.spec.security.denyUncovered;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import servlet.tck.util.TestUtil;
import servlet.tck.util.WebUtil;
import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

/**
 * This class will be used to perform simple servlet invocations. The servlet
 * invocations should be used to test these assertions on the server.
 *
 * We will check for success or failure from within this file. So the actual
 * testcases in this class will simply consist of checking the server side
 * servlet invocations for success or failure.
 *
 * The tests in this class are intended to test the Servlets
 * deny-uncovered-http-methods DD semantic (Servlet 3.1 spec, section 13.8.4.2).
 *
 */
public class DenyUncoveredTests extends AbstractTckTest {

  private String hostname;

  private int portnum;


  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_sec_denyUncovered_web.war")
            .addClasses(AllMethodsAllowedAnno.class, ExcludeAuthConstraint.class, PartialDDServlet.class,
                    TestServlet.class)
            .setWebXML(DenyUncoveredTests.class.getResource("servlet_sec_denyUncovered_web.xml"));
  }

  // this must be the decoded context path corresponding to the web module
  private final String contextPath = "/servlet_sec_denyUncovered_web";

  private final String ctxtTestServlet = contextPath + "/TestServlet"; // specifies
  // access to
  // get & put
  // only

  private final String ctxtAllMethodsAllowedAnno = contextPath
      + "/AllMethodsAllowedAnno";

  private final String ctxtExcludeAuthConstraint = contextPath
      + "/ExcludeAuthConstraint";

  private final String ctxtPartialDDServlet = contextPath + "/PartialDDServlet";

  private String username = "";

  private String password = "";


  /**
   * @class.setup_props: logical.hostname.servlet; webServerHost; webServerPort;
   *                     authuser; authpassword; user; password;
   *                     securedWebServicePort;
   *
   */
  public void setup(String[] args, Properties p) throws Exception {

    try {
      hostname = p.getProperty("webServerHost");
      portnum = Integer.parseInt(p.getProperty("webServerPort"));
      username = p.getProperty("user");
      password = p.getProperty("password");

    } catch (Exception e) {
      logErr("Error: got exception: ", e);
    }
  }

  public void cleanup() throws Exception {
  }

  /*
   * @testName: testAllMethodsAllowedAnno
   *
   * @assertion_ids: Servlet:SPEC:310
   *
   * @test_Strategy: This validates that we have a deny-uncovered-http-method
   *                 set in our web.xml and NO methods explicitly listed in our
   *                 security constraints. Details for this test include the
   *                 following : - we are working with servlet:
   *                 AllMethodsAllowedAnno which uses servlet annotations only
   *                 and has no web.xml references - the only security that
   *                 should be in effect should be whatever is defined in
   *                 annotations. - attempts to access all denied methods shall
   *                 return 200
   * 
   */
  @Test
  public void testAllMethodsAllowedAnno() throws Exception {

    int httpStatusCode = invokeServlet(ctxtAllMethodsAllowedAnno, "POST");
    if (httpStatusCode != 200) {
      logger.error("Accessing {} (POST) returns = {}", ctxtAllMethodsAllowedAnno, httpStatusCode);
      throw new Exception("testAllMethodsAllowedAnno : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtAllMethodsAllowedAnno, "GET");
    if (httpStatusCode != 200) {
      logger.error("Accessing {} (GET) returns = {}", ctxtAllMethodsAllowedAnno, httpStatusCode);
      throw new Exception("testAllMethodsAllowedAnno : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtAllMethodsAllowedAnno, "PUT");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (PUT) returns = {}", ctxtAllMethodsAllowedAnno, httpStatusCode);
      throw new Exception("testAllMethodsAllowedAnno : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtAllMethodsAllowedAnno, "DELETE");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (DELETE) returns = {}", ctxtAllMethodsAllowedAnno, httpStatusCode);
      throw new Exception("testAllMethodsAllowedAnno : FAILED");
    }

    logger.debug("testAllMethodsAllowedAnno : PASSED");
  }

  /*
   * @testName: testAccessToMethodAllowed
   *
   * @assertion_ids: Servlet:SPEC:309;
   *
   * @test_Strategy: This validates that we have a deny-uncovered-http-method
   *                 set in our web.xml for a servlet that explicitly specifies
   *                 protection for Get and Post. This means that Get and Post
   *                 can be access but because we are using
   *                 deny-uncovered-http-methods element, everything else will
   *                 be protected/denied access. - this uses servlet TestServlet
   *                 and it's Get & Post methods. - get & put are specified in
   *                 security constraint - so we should be able to access them -
   *                 attempts to access get & put must be alloed (return 200=ok)
   * 
   */
  @Test
  public void testAccessToMethodAllowed() throws Exception {

    int httpStatusCode = invokeServlet(ctxtTestServlet, "POST");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (POST) returns = {}", ctxtTestServlet, httpStatusCode);
      throw new Exception("testAccessToMethodAllowed : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtTestServlet, "GET");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (GET) returns = {}", ctxtTestServlet, httpStatusCode);
      throw new Exception("testAccessToMethodAllowed : FAILED");
    }

    logger.debug("testAccessToMethodAllowed : PASSED");
  }

  /*
   * @testName: testDenySomeUncovered
   *
   * @assertion_ids: Servlet:SPEC:309;
   *
   * @test_Strategy: This validates that we have a deny-uncovered-http-method
   *                 set in our web.xml for a servlet that explicitly specifies
   *                 protection for Get and Post. This means that Get and Post
   *                 can be access but because we are using
   *                 deny-uncovered-http-methods element, everything else will
   *                 be protected/denied access. - this uses servlet TestServlet
   *                 and it's Get & Post methods. - get & put are specified in
   *                 security constraint - so we should be able to access them -
   *                 attempts to access get & put must be alloed (return 200=ok)
   * 
   */
  @Test
  public void testDenySomeUncovered() throws Exception {

    int httpStatusCode = invokeServlet(ctxtTestServlet, "DELETE");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {} (DELETE) returns = {}", ctxtTestServlet, httpStatusCode);
      throw new Exception("testDenySomeUncovered : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtTestServlet, "PUT");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {} (PUT) returns = {}", ctxtTestServlet, httpStatusCode);
      throw new Exception("testDenySomeUncovered : FAILED");
    }

    logger.debug("testDenySomeUncovered : PASSED");
  }

  /*
   * @testName: testExcludeAuthConstraint
   *
   * @assertion_ids: Servlet:SPEC:309;
   *
   * @test_Strategy: This validates that we have a deny-uncovered-http-method
   *                 set in our web.xml on a servlet that uses an
   *                 excluded-auth-constraint in combination with the
   *                 http-method-omission element. Normally using
   *                 http-method-omissions with excluded-auth-constraint would
   *                 cause the listed methods (get & put in this case) to be
   *                 considered uncovered. Adding in the
   *                 deny-uncovered-http-method means we should be denied access
   *                 to get & put. This test has the following details: - this
   *                 uses servlet ExcludeAuthConstraint - get & put are
   *                 specified in security constraint within
   *                 http-method-omission - get & put should be uncovered (based
   *                 on http-method-mission) BUT because of the
   *                 deny-uncovered-http-method elelent, they must be denied!
   * 
   */
  @Test
  public void testExcludeAuthConstraint() throws Exception {

    int httpStatusCode = invokeServlet(ctxtExcludeAuthConstraint, "GET");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {}} (GET) returns = {}", ctxtExcludeAuthConstraint, httpStatusCode);
      throw new Exception("testExcludeAuthConstraint : FAILED");
    }

    httpStatusCode = invokeServlet(ctxtExcludeAuthConstraint, "POST");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {} (POST) returns = {}", ctxtExcludeAuthConstraint, httpStatusCode);
      throw new Exception("testExcludeAuthConstraint : FAILED");
    }

    logger.debug("testExcludeAuthConstraint : PASSED");
  }

  /*
   * @testName: testPartialDDServlet
   *
   * @assertion_ids: Servlet:SPEC:309;
   *
   * @test_Strategy: This validates that we have a deny-uncovered-http-method
   *                 set in our web.xml on a servlet that uses settings from
   *                 both DD and annotations. The security constraints are set
   *                 in the annotated servlet but the web.xml defines
   *                 deny-uncovered-http-method - and that will apply to all
   *                 uncovered methods in the annotated servlet.
   * 
   *                 This test has the following details: - this uses servlet
   *                 PartialDDServlet (with both annotation and DD settings) -
   *                 get & put are specified in security constraint within
   *                 annotation - get & put should be covered (and accessible by
   *                 role Administrato) but all other methods should be
   *                 uncovered from annotation POV - DD decares
   *                 deny-uncovered-http-method and servlet refs so should cause
   *                 the other "uncovered" methods to get "denied"
   * 
   */
  @Test
  public void testPartialDDServlet() throws Exception {

    logger.debug("Invoking {} (GET)", ctxtPartialDDServlet);
    int httpStatusCode = invokeServlet(ctxtPartialDDServlet, "GET");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (GET) returns = {}", ctxtPartialDDServlet, httpStatusCode);
      throw new Exception("testPartialDDServlet : FAILED");
    }

    logger.debug("Invoking {} (POST)", ctxtPartialDDServlet);
    httpStatusCode = invokeServlet(ctxtPartialDDServlet, "POST");
    if (httpStatusCode != 200) {
      logger.debug("Accessing {} (POST) returns = {}", ctxtPartialDDServlet, httpStatusCode);
      throw new Exception("testPartialDDServlet : FAILED");
    }

    logger.debug("Invoking {} (PUT)", ctxtPartialDDServlet);
    httpStatusCode = invokeServlet(ctxtPartialDDServlet, "PUT");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {} (PUT) returns = {}", ctxtPartialDDServlet, httpStatusCode);
      throw new Exception("testPartialDDServlet : FAILED");
    }

    logger.debug("Invoking {} (DELETE)", ctxtPartialDDServlet);
    httpStatusCode = invokeServlet(ctxtPartialDDServlet, "DELETE");
    if (httpStatusCode != 403) {
      logger.debug("Accessing {} (DELETE) returns = {}", ctxtPartialDDServlet, httpStatusCode);
      throw new Exception("testPartialDDServlet : FAILED");
    }

    logger.debug("testPartialDDServlet : PASSED");
  }

  /*
   * Convenience method that will establish a url connections and perform a
   * get/post request. A username and password will be passed in the request
   * header and they will be encoded using the BASE64Encoder class. returns the
   * http status code.
   */
  private int invokeServlet(String sContext, String requestMethod) throws Exception {
    int code = 200;

    if (sContext.startsWith("/")) {
      sContext = sContext.substring(1);
    }

    String url = getURLString("http", hostname, portnum, sContext);
    try {
      URL newURL = new URL(url);

      // Encode authData
      // hint: make sure username and password are valid for your
      // (J2EE) security realm otherwise you recieve http 401 error.
      String authData = username + ":" + password;
      logger.debug("authData : {}", authData);

//      BASE64Encoder encoder = new BASE64Encoder();
//      String encodedAuthData = encoder.encode(authData.getBytes());
      String encodedAuthData = WebUtil.encodeBase64(authData);
      logger.debug("encoded authData : {}", encodedAuthData);

      // open URLConnection
      HttpURLConnection conn = (HttpURLConnection) newURL.openConnection();

      // set request property
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setRequestProperty("Authorization", "Basic " + encodedAuthData.trim());
      conn.setRequestMethod(requestMethod); // POST or GET etc
      conn.connect();

      logger.debug("called HttpURLConnection.connect() for url: {}", url);
      code = conn.getResponseCode();
      logger.debug("Got response code of: {}", code);
      String str = conn.getResponseMessage();
      logger.debug("Got response string of: {}", str);
      /*
       * // not used right now but left here in case we need it InputStream
       * content = (InputStream)conn.getInputStream(); BufferedReader in = new
       * BufferedReader(new InputStreamReader(content));
       * 
       * try { String line; while ((line = in.readLine()) != null) {
       * TestUtil.logMsg(line); } } finally { in.close(); }
       */

    } catch (Exception e) {
      logger.error("Abnormal return status encountered while invoking {}", sContext);
      logger.error("Exception Message was:  " + e.getMessage(), e);
      throw e;
    }

    return code;
  } // invokeServlet()

}
