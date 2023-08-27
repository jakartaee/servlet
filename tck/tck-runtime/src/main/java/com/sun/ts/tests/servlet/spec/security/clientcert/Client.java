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

package com.sun.ts.tests.servlet.spec.security.clientcert;

import com.sun.ts.lib.util.WebUtil;
import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.Properties;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Raja Perumal
 */
public class Client extends AbstractUrlClient {

    // TOFIX

    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    /**
     * Deployment for the test
     */
    @Deployment(testable = false, name = "webapp-https")
    @TargetsContainer("https")
    public static WebArchive getTestArchive() throws Exception {
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());

//        try {
//            // specific glassfish so ignore any issue with
//            File clientKeyStoreFile = new File("target/test-classes/certificates/clientcert.jks");
//            assertTrue(clientKeyStoreFile.exists());
//            System.setProperty("javax.net.ssl.keyStore", clientKeyStoreFile.getAbsolutePath());
//            System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
//
//            File clientPublicCertFile = new File("target/test-classes/certificates/cts_cert");
//            assertTrue(clientPublicCertFile.exists());
//
//            Collection<? extends Certificate> clientPublicCertificates;
//            try {
//                clientPublicCertificates = CertificateFactory.getInstance("X.509")
//                        .generateCertificates(new ByteArrayInputStream(readFileToByteArray(clientPublicCertFile)));
//            } catch (CertificateException | IOException e) {
//                throw new IllegalStateException(e);
//            }
//
//            for (Certificate certificate : clientPublicCertificates) {
//                addCertificateToContainerTrustStore("tcktest", certificate);
//            }
//
//            String keystorePath = System.getProperty("keystore.path");
//            if (keystorePath!=null) {
//                Path trustStorePath = Paths.get(keystorePath);
//                assertNotNull(trustStorePath);
//                assertTrue(Files.exists(trustStorePath));
//
//                LOGGER.info("Using truststore from: {}", trustStorePath.toAbsolutePath());
//
//                System.setProperty("javax.net.ssl.trustStore", trustStorePath.toAbsolutePath().toString());
//            }
//
//            System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
//        } catch (Throwable e) {
//            LOGGER.debug("ignore glassfish specific", e);
//        }
        return ShrinkWrap.create(WebArchive.class, "clientcert_web.war")
                .addClasses(ServletSecTestServlet.class)
                .addAsWebInfResource("com/sun/ts/tests/servlet/spec/security/clientcert/clientcert_web.war.sun-web.xml",
                        "sun-web.xml")
                .setWebXML(Client.class.getResource("clientcert_web.xml"));
    }

    public static void addCertificateToContainerTrustStore(String alias, Certificate clientCertificate) {

        Path cacertsPath = Paths.get(System.getProperty("keystore.path"));
        String domain = System.getProperty("domain1");

        if (!cacertsPath.toFile().exists()) {
            throw new IllegalStateException("The container trust store at " + cacertsPath.toAbsolutePath() + " does not exists");
        }


        LOGGER.info("*** Adding certificate to container trust store: " + cacertsPath.toAbsolutePath());

        KeyStore keyStore = null;
        try (InputStream in = new FileInputStream(cacertsPath.toAbsolutePath().toFile())) {
            keyStore = KeyStore.getInstance("JKS");
            keyStore.load(in, "changeit".toCharArray());

            Certificate existingCertificate = keyStore.getCertificate(alias);
            if (!clientCertificate.equals(existingCertificate)) {
                keyStore.setCertificateEntry(alias, clientCertificate);
                keyStore.store(new FileOutputStream(cacertsPath.toAbsolutePath().toFile()), "changeit".toCharArray());
                //restartDomain();
            } else {
                System.out.println("Server's truststore already contains certificate");
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Path getAdminCliJar() {
        return Paths.get(System.getProperty("glassfish.home") , "glassfish/modules/admin-cli.jar");
    }
    private static void restartDomain() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar",
                getAdminCliJar().toAbsolutePath().toString(), "restart-domain", "domain1").inheritIO();
        processBuilder.start();
    }

    @ArquillianResource
    @OperateOnDeployment("webapp-https") URL urlHttps;

    // Configurable constants:
    private String hostname = null;

    private int portnum = 0;

    private String tlsVersion;

    private String pageBase = "/clientcert_web";

    private String authorizedPage = "/ServletSecTest";

    private String user = null;

    // Constants:
    private final String webHostProp = "webServerHost";

    private final String webPortProp = "webServerPort";

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
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("localhost"));
        }

        p.setProperty(webHostProp, hostname);
        portnum = Integer.getInteger("securedWebServicePort", urlHttps.getPort());// Integer.parseInt(p.getProperty("securedWebServicePort"));
        p.setProperty("securedWebServicePort", Integer.toString(portnum));
        tlsVersion = System.getProperty("client.cert.test.jdk.tls.client.protocols", p.getProperty("client.cert.test.jdk.tls.client.protocols"));

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
     * @assertion_ids: Servlet:SPEC:140; Servlet:SPEC:368; Servlet:SPEC:369;
     * Servlet:SPEC:26; Servlet:SPEC:26.1; Servlet:SPEC:26.2; Servlet:SPEC:26.3;
     * Servlet:JAVADOC:356
     *
     * @test_strategy: 1. Look for the following request attributes a)
     * cipher-suite b) key-size c) SSL certificate If any of the above attributes
     * are not set report test failure.
     *
     * 2. Verify the request.getAuthType returns CLIENT_CERT
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
     */
    @Test
    public void clientCertTest() throws Exception {

        String testName = "clientCertTest";
        String url = getURLString("https", hostname, portnum, pageBase.substring(1) + authorizedPage);

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
                logger.trace("line: {}", line);
            }

            // compare getRemoteUser() obtained from server's response
            // with the username stored in ts.jte
            //
            // Even though the output need not be identical (because
            // of appserver realms) the output should have substring
            // match for username stored in ts.jte.
            //
            String userNameToSearch = username;
            if (output.indexOf(userNameToSearch) == -1) {
                throw new Exception(testName + ": getRemoteUser(): " + "- did not find \""
                        + userNameToSearch + "\" in log.");
            } else {
                logger.debug("Additional verification done");
            }

            // verify output for expected test result
            verifyTestOutput(output.toString(), testName);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Exception(testName + ": FAILED", e);
        }

    }

    public void verifyTestOutput(String output, String testName) throws Exception {
        // check for the occurance of <testName>+": PASSED"
        // message in server's response. If this message is not present
        // report test failure.
        if (!output.contains(testName + ": PASSED")) {
            logger.error("Test {} Not Expected String from the output = {}: PASSED", testName, output);
            throw new Exception(testName + ": FAILED");
        }
    }

}