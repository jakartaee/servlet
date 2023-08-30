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

package servlet.tck.spec.security.clientcertanno;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.cert.X509Certificate;

import jakarta.annotation.security.DeclareRoles;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.HttpMethodConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import jakarta.servlet.annotation.ServletSecurity.TransportGuarantee;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * There are security annotations being used now which shoudl eliminate the 
 * need for much of the DD elements.  Most notably, the TransportProtected
 * is used to indicate we want secure transport mechanism (eg SSL) though
 * we are still using the DD to specify and auth-metho of CLIENT-CERT.
 * Aside from the use of Roles and the trimmed down DD, this test should
 * be similar to the test in clientcert directory.
 *
 * These annotations are trying to set the following security constraints:
 *  - everyone is permitted to access all methods for /ServletSecTest *except*
 *    for the  methods of POST and GET.  For those methods, only the 
 *    "Administrator" is allowed to access those methods.
 *  - SSL is to be used.
 *
 */

@DeclareRoles({ "Administrator" })
@ServletSecurity(value = @HttpConstraint(EmptyRoleSemantic.PERMIT), httpMethodConstraints = {
    @HttpMethodConstraint(value = "POST", rolesAllowed = "Administrator", transportGuarantee = TransportGuarantee.CONFIDENTIAL),
    @HttpMethodConstraint(value = "GET", rolesAllowed = "Administrator", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@WebServlet("/ServletSecTest")
public class ServletSecTestServlet extends HttpServlet {
  private boolean fail = false;

  private String FAILSTRING = "FAILED";

  private String PASSSTRING = "PASSED";

  private static final Logger LOGGER = LoggerFactory.getLogger(ServletSecTestServlet.class);

  /*
   * testName: clientCertTest
   *
   * 1) If a request has been transmitted over a secure protocol, such as HTTPS,
   * this information must be exposed via the isSecure method of the
   * ServletRequest interface. The web container must expose the following
   * attributes to the servlet programmer. 1) The cipher suite 2) the bit size
   * of the algorithm 3) SSL certificate
   *
   * If any of the above attributes are not set report test failure.
   *
   * 2. Verify the request.getAuthType returns CLIENT_CERT
   *
   * If there is an SSL certificate associated with the request, it must be
   * exposed by the servlet container to the servlet programmer as an array of
   * objects of type java.security.cert.X509Certificate
   *
   * See Also: * Servlet 2.3 Specification section 4.7
   *
   * @test_strategy: 1. Look for the following request attributes a)
   * cipher-suite b) key-size c) SSL certificate If any of the above attributes
   * is not set/incorrect, report test failure.
   *
   * Note: SSL certificate attribute will be set only if there is a client
   * certificate involved in SSL connection.
   *
   *
   */
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    PrintWriter out = response.getWriter();

    out.println("getRemoteUser(): " + request.getRemoteUser() + "<BR>");

    // Surround these with !'s so they are easier to search for.
    // (i.e. we can search for !true! or !false!)
    out.println(
        "isUserInRole(\"ADM\"): !" + request.isUserInRole("ADM") + "!<BR>");

    String testName = "clientCertTest";

    String cipherSuiteAttrib = "jakarta.servlet.request.cipher_suite";
    String keySizeAttrib = "jakarta.servlet.request.key_size";
    String certificateAttrib = "jakarta.servlet.request.X509Certificate";

    String cipherSuite = null;
    Integer keySize = 0;
    X509Certificate[] certificates = null;

    try {
      fail = false;

      if (request.getUserPrincipal() != null) {
        String userPrincipalName = request.getUserPrincipal().getName();
        out.println("Caller principal Name = " + userPrincipalName);
        if (userPrincipalName.equals("")) {
          fail = true;
        }

      } else {
        out.println("Caller principal = null");
        fail = true;
      }

      cipherSuite = (String) request.getAttribute(cipherSuiteAttrib);

      // verify cipher-suite attribute
      if (cipherSuite == null) {
        out.println(
            testName + ": " + FAILSTRING + " - cipher-suite attribute not set");
        fail = true;
      } else
        out.println(testName + ": cipher-suite : " + cipherSuite);

      keySize = (Integer) request.getAttribute(keySizeAttrib);

      // verify key-size attribute
      if (keySize == null) {
        out.println(
            testName + ": " + FAILSTRING + " - key-size attribute not set");
        fail = true;
      } else
        out.println(testName + ": key-size : " + keySize.toString());

      certificates = (X509Certificate[]) request
          .getAttribute(certificateAttrib);

      // verify SSL certificate attribute
      if (certificates != null) {
        for (int i = 0; i < certificates.length; i++) {

          X509Certificate x509Certificate = certificates[i];
          out.println(testName + ": certificate [" + i + "]="
              + x509Certificate.toString());
        }
      } else {
        out.println(
            testName + ": " + FAILSTRING + " - No SSL certificate found");
        fail = true;

      }

      // verify authenticate type
      String authType = request.getAuthType();
      if ((authType != null) && (!authType.equals("CLIENT_CERT"))) {
        out.println(testName + ":" + FAILSTRING
            + " - Server returns wrong authentication type : " + authType
            + " : expected authentication type is CLIENT_CERT");
        fail = true;
      }

      if (!fail) {
        out.println(testName + ": " + PASSSTRING);
      }
    } catch (Exception e) {
      out.println(
          testName + ": " + FAILSTRING + " - Exception: " + e.getMessage());
      e.printStackTrace();
    }
  }

}
