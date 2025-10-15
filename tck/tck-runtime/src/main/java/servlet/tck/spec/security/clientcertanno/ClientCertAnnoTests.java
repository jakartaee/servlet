/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.spec.security.clientcertanno;

import servlet.tck.spec.listenerorder.ListenerOrderTests;
import servlet.tck.util.TestUtil;
import servlet.tck.util.WebUtil;
import servlet.tck.common.client.AbstractTckTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author Raja Perumal
 *
 * @description This is a similar test class as what is in the clientcert dir
 *              with the major difference being that the servlet under test uses
 *              Security annotations with the ultimate goal being to test client
 *              cert w/ Transport guarantee mechanism.
 */
public class ClientCertAnnoTests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false, name = "webapp-https")
  @TargetsContainer("https")
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "clientcertanno_web.war")
            .addClasses(ServletSecTestServlet.class)
            .setWebXML(ClientCertAnnoTests.class.getResource("clientcertanno_web.xml"));
  }

  @ArquillianResource
  @OperateOnDeployment("webapp-https") URL urlHttps;

  // Configurable constants:
  private String hostname = null;

  private int portnum = 0;

  private String tlsVersion;

  private String pageBase = "/clientcertanno_web";

  private String authorizedPage = "/ServletSecTest";

  private String user = null;

  // Constants:
  private final String webHostProp = "webServerHost";

  private final String failString = "FAILED!";

  // DN name for CTS certificate
  private final String username = "CN=CTS, OU=Java Software, O=Sun Microsystems Inc., L=Burlington, ST=MA, C=US";

  // Shared test variables:
  private String request = null;

  private WebUtil.Response response = null;

  private HostnameVerifier hostnameVerifier;

  /*
   * @class.setup_props: webServerHost; webServerPort; securedWebServicePort;
   * certLoginUserAlias;
   *
   */
  public void setup(String[] args, Properties p) throws Exception {

    hostname = urlHttps.getHost();

    if ("localhost".equals(hostname)) {
      hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
      //for localhost testing only
      HttpsURLConnection
              .setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("localhost")? true : false);
    }

    p.setProperty(webHostProp, hostname);

    portnum = urlHttps.getPort();
    p.setProperty("securedWebServicePort", Integer.toString(portnum));
    tlsVersion = p.getProperty("client.cert.test.jdk.tls.client.protocols");

    logger.info("securedWebServicePort = {}", p.getProperty("securedWebServicePort"));

    if (tlsVersion != null) {
      logger.info("client.cert.test.jdk.tls.client.protocols = {}", tlsVersion);
    }

  }

  @AfterEach
  public void cleanup() {
    if (hostnameVerifier != null) {
      HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }
  }

  /*
   * @testName: clientCertTest
   *
   * @assertion_ids: Servlet:SPEC:140; Servlet:JAVADOC:368; Servlet:JAVADOC:369;
   * Servlet:SPEC:26; Servlet:SPEC:26.1; Servlet:SPEC:26.2; Servlet:SPEC:26.3;
   * Servlet:JAVADOC:356; Servlet:SPEC:214; Servlet:SPEC:215;
   *
   * @test_strategy: 1. Look for the following request attributes a)
   * cipher-suite b) key-size c) SSL certificate If any of the above attributes
   * are not set report test failure.
   *
   * 2. Verify the request.getAuthType returns CLIENT_CERT 3. test the use
   * of @TransportProtected annotation at the class level (which implies no need
   * for security constraints in the DD file.)
   *
   * Note: If a request has been transmitted over a secure protocol, such as
   * HTTPS, this information must be exposed via the isSecure method of the
   * ServletRequest interface. The web container must expose the following
   * attributes to the servlet programmer. 1) The cipher suite 2) the bit size
   * of the algorithm
   *
   * If there is an SSL certificate associated with the request, it must be
   * exposed by the servlet container to the servlet programmer as an array of
   * objects of type java.security.cert.X509Certificate
   *
   * Important to note is that this is testing the client cert abilities using
   * annotations instead of DD entries which normally fall under the
   * security-constraint element.
   *
   */
  @Test
  public void clientCertTest() throws Exception {

    String testName = "clientCertTest";
    String url = getURLString("https", hostname, portnum,
            pageBase.substring(1) + authorizedPage);

    if (tlsVersion != null) {
      System.setProperty("jdk.tls.client.protocols", tlsVersion);
    }

    URL newURL = new URL(url);
    // open HttpsURLConnection using TSHttpsURLConnection
    URLConnection httpsURLConn = getHttpsURLConnection(newURL);
    try (InputStream content = httpsURLConn.getInputStream();
         BufferedReader in = new BufferedReader(new InputStreamReader(content))) {

      StringBuilder output = new StringBuilder();
      String line;
      while ((line = in.readLine()) != null) {
        output.append(line);
        logger.debug(line);
      }

      // compare getRemoteUser() obtained from server's response
      // with the username stored in ts.jte
      //
      // Even though the output need not be identical (because
      // of appserver realms) the output should have substring
      // match for username stored in ts.jte.
      //
      String userNameToSearch = username;
      if (!output.toString().contains(userNameToSearch)) {
        throw new Exception(testName + ": getRemoteUser(): " + "- did not find \""
                + userNameToSearch + "\" in log.");
      } else {
        logger.debug("Additional verification done");
      }

      // verify output for expected test result
      verifyTestOutput(output.toString(), testName);


    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new Exception(testName + ": FAILED, " + e.getMessage(), e);
    }

  }


  public void verifyTestOutput(String output, String testName) throws Exception {
    // check for the occurrence of <testName>+": PASSED"
    // message in server's response. If this message is not present
    // report test failure.
    if (!output.contains(testName + ": PASSED")) {
      logger.debug("Expected String from the output = {}: PASSED", testName);
      logger.debug("received output = {}", output);
      throw new Exception(testName + ": FAILED");
    }
  }


}