/*
 * Copyright (c) 2013, 2026 Oracle and/or its affiliates and others.
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
package servlet.tck.api.jakarta_servlet_http.httpupgradehandler;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.ServletTestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

public class HttpUpgradeHandlerTests extends AbstractTckTest {

  private static final String CRLF = "\r\n";

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   *
   * @return The web application archive to be deployed for the test
   *
   * @throws Exception if the archive cannot be created
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_upgradehandler_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TCKHttpUpgradeHandler.class, TCKReadListener.class, TestServlet.class, TestFilter.class);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   * servlet_async_wait;
   */
  /* Run test */
  /*
   * @testName: upgradeTest
   *
   * @assertion_ids: Servlet:JAVADOC:905; Servlet:JAVADOC:909;
   * Servlet:JAVADOC:911; Servlet:JAVADOC:923; Servlet:JAVADOC:925;
   * Servlet:JAVADOC:930; Servlet:JAVADOC:937;
   *
   * @test_Strategy: Create a Servlet TestServlet; From Client, sends upgrade
   * request with two batch of messages to the Servlet; Servlet upgrade the
   * request accordingly; Create a ReadListener; Verify all message received;
   * Verify UpgradeHandler accordingly Verify ReadListener works accordingly
   */
  @Test
  public void upgradeTest() throws Exception {
    boolean pass = false;
    /*
     * This isn't the whole response. Rather it is key, selected parts of the response in the order they are expected to
     * appear
     */
    String EXPECTED_RESPONSE = "HTTP/1.1 101|x-tracking:|filter-before,servlet-upgrade,filter-after|" +
            "TCKHttpUpgradeHandler.init|onDataAvailable|Hello|World";

    String requestUrl = getContextRoot() + "/" + getServletName() + " HTTP/1.1";

    URL url = getURL("http", _hostname, _port, requestUrl.substring(1));
    try (Socket s = new Socket(_hostname, _port);
         OutputStream output = s.getOutputStream();
         InputStream input = s.getInputStream()) {

      // Prevent the test waiting for ever if an incorrect response is provided.
      s.setSoTimeout(30*1000);

      StringBuilder reqStr = new StringBuilder("POST "
          + url.toExternalForm().replace("http://", "").replace(_hostname, "")
              .replace(":" + _port, "")
          + CRLF);
      reqStr.append("User-Agent: Java/1.6.0_33" + CRLF);
      reqStr.append("Host: ").append(_hostname).append(":").append(_port).append(CRLF);
      reqStr
          .append("Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"
              + CRLF);
      reqStr.append("Upgrade: YES" + CRLF);
      reqStr.append("Connection: Upgrade" + CRLF);
      reqStr.append("Content-type: application/x-www-form-urlencoded" + CRLF);
      reqStr.append(CRLF);

      logger.debug("REQUEST========= {}", reqStr);
      output.write(reqStr.toString().getBytes());

      logger.debug("Writing first chunk");
      writeChunk(output, "Hello");

      logger.debug("Writing second chunk");
      writeChunk(output, "World");

      logger.debug("Consuming the response from the server");

      // Consume the response from the server
      int len = -1;
      byte[] b = new byte[1024];
      StringBuilder sb = new StringBuilder();
      while (!pass && (len = input.read(b)) != -1) {
        String line = new String(b, 0, len);
        sb.append(line);
        logger.debug("==============Read from server: {} {} {}", CRLF, sb, CRLF);

        // Check for the expected messages in the expected order. Exit the loop once they have been observed.
        pass = ServletTestUtil.compareString(EXPECTED_RESPONSE, sb.toString());
      }

    System.out.println(sb.toString());
    } catch (Exception ex2) {
      logger.error("exception caught: " + ex2.getMessage(), ex2);
    }


    if (!pass) {
      throw new Exception("Test Failed. ");
    }
  }

  private static void writeChunk(OutputStream out, String data)
      throws IOException {
    if (data != null) {
      out.write(data.getBytes());
    }
    out.flush();
  }
}
