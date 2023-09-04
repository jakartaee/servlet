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
package servlet.tck.api.jakarta_servlet_http.readlistener;

import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.util.ServletTestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReadListenerTests extends AbstractUrlClient {


  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_readlistener.war")
            .addClasses(TestServlet.class, TestListener.class);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   * servlet_async_wait;
   */
  /* Run test */
  /*
   * @testName: nioInputTest
   *
   * @assertion_ids: Servlet:SPEC:282; Servlet:SPEC:282.1; Servlet:SPEC:282.2;
   * Servlet:SPEC:282.6; Servlet:SPEC:282.7; Servlet:JAVADOC:904;
   * Servlet:JAVADOC:905; Servlet:JAVADOC:908;
   * Servlet:JAVADOC:909;Servlet:JAVADOC:577; Servlet:JAVADOC:609;
   * Servlet:JAVADOC:638; Servlet:JAVADOC:710;
   *
   * @test_Strategy: Create a Servlet TestServlet which supports async; Create a
   * ReadListener; From Client, sends two batch of messages use stream; Verify
   * all message received; Verify ReadListener works accordingly
   */
  @Test
  public void nioInputTest() throws Exception {
    int sleepInSeconds = Integer
        .parseInt(_props.getProperty("servlet_async_wait").trim());
    boolean passed = true;

    String EXPECTED_RESPONSE = "=onDataAvailable|=Hello|=onDataAvailable|=World"
        + "|=onAllDataRead";

    BufferedReader input = null;
    BufferedWriter output = null;

    String requestUrl = getContextRoot() + "/" + getServletName();

    try {
      URL url = getURL("http", _hostname, _port, requestUrl.substring(1));

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      logger.debug("Connecting {}", url.toExternalForm());
      conn.setRequestProperty("Content-type", "text/plain; charset=utf-8");
      conn.setChunkedStreamingMode(5);
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);
      logger.debug(" Header {}", conn);
      conn.connect();

      try {
        output = new BufferedWriter(
            new OutputStreamWriter(conn.getOutputStream()));
        try {
          String data = "Hello";
          output.write(data);
          output.flush();
          Thread.sleep(sleepInSeconds * 1000);

          data = "World";
          output.write(data);
          output.flush();
          output.close();
        } catch (Exception ex) {
          passed = false;
          logger.error("======= Exception sending message: " + ex.getMessage(), ex);
        }

        input = new BufferedReader(
            new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder message_received = new StringBuilder();

        while ((line = input.readLine()) != null) {
          logger.debug("======= message received: {}", line);
          message_received.append(line);
        }
        passed = ServletTestUtil.compareString(EXPECTED_RESPONSE,
            message_received.toString());

      } catch (Exception ex) {
        passed = false;
        logger.error("Exception: " + ex.getMessage(), ex);
      } finally {
        try {
          if (input != null) {
            input.close();
          }
        } catch (Exception ex) {
          logger.error("Fail to close BufferedReader" + ex.getMessage(), ex);
        }

        try {
          if (output != null) {
            output.close();
          }
        } catch (Exception ex) {
          logger.error("Fail to close BufferedWriter" + ex.getMessage(), ex);
        }
      }
    } catch (Exception ex3) {
      passed = false;
      logger.error("Test" + ex3.getMessage(), ex3);
    }

    if (!passed) {
      throw new Exception("Test Failed.");
    }
  }
}
