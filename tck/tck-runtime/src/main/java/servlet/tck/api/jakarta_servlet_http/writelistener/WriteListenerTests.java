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

/*
 * $Id$
 */
package servlet.tck.api.jakarta_servlet_http.writelistener;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.ServletTestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WriteListenerTests extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "writelistener.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, TestListener.class);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   */
  /* Run test */
  /*
   * @testName: nioOutputTest
   *
   * @assertion_ids: Servlet:JAVADOC:911; Servlet:JAVADOC:916;
   * Servlet:JAVADOC:917; Servlet:JAVADOC:582; Servlet:JAVADOC:609;
   *
   * @test_Strategy: Create a Servlet TestServlet which supports async; Create a
   * Writeistener; From Servlet, sends one batch of messages use stream; Verify
   * all message received by client; Verify WriteListener works accordingly
   */
    @Test
  public void nioOutputTest() throws Exception {
    boolean passed = true;
    String testName = "nioOutputTest";
    String EXPECTED_RESPONSE = "=onWritePossible";

    String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
        + testName;

    URL url = new URL(getURLString("http", _hostname, _port, requestUrl.substring(1)));
    try {
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      logger.debug("======= Connecting {}", url.toExternalForm());
      conn.setChunkedStreamingMode(5);
      conn.setDoOutput(true);
      logger.trace("======= Header {}", conn);
      conn.connect();

      try (BufferedReader input = new BufferedReader(
              new InputStreamReader(conn.getInputStream()))) {
        String line = null;
        StringBuilder message_received = new StringBuilder();

        while ((line = input.readLine()) != null) {
          logger.debug("======= message received: " + line);
          message_received.append(line);
        }
        passed = ServletTestUtil.compareString(EXPECTED_RESPONSE,
            message_received.toString());

      }
    } catch (Exception ex) {
      passed = false;
      logger.error("Test" + ex.getMessage(), ex);
    }

    if (!passed) {
      throw new Exception("Test Failed.");
    }
  }
}
