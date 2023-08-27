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
package com.sun.ts.tests.servlet.api.jakarta_servlet_http.readlistener1;

import com.sun.ts.tests.servlet.common.client.AbstractUrlClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import com.sun.ts.tests.servlet.common.util.ServletTestUtil;
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

public class URLClient extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
    setContextRoot("/servlet_jsh_readlistener1_web");
  }




  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_readlistener1_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class, TestListener.class);
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   * servlet_async_wait;
   */
  /* Run test */
  /*
   * @testName: nioInputTest1
   *
   * @assertion_ids: Servlet:JAVADOC:942;
   *
   * @test_Strategy: Create a Servlet TestServlet which supports async; Verify
   * ServletInputStream.setReadListener(null) works accordingly
   */
  @Test
  public void nioInputTest1() throws Exception {
    boolean passed = true;
    int sleepInSeconds = Integer
            .parseInt(_props.getProperty("servlet_async_wait").trim());
    String testName = "nioInputTest1";
    String EXPECTED_RESPONSE = "Test PASSED|NullPointerException";

    //BufferedReader input = null;

    String requestUrl = "http://" + _hostname + ":" + _port +  getContextRoot() + "/" + getServletName() + "?testname="
            + testName;
    URL url = new URL(requestUrl);

    try {
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      logger.trace("======= Connecting {}", url.toExternalForm());
      conn.setChunkedStreamingMode(5);
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");

      logger.trace("======= Header {}", conn);
      conn.connect();

      try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()))) {
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
          logger.error("======= Exception sending message: " + ex.getMessage());
        }

        try (BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
          String line;
          StringBuilder message_received = new StringBuilder();

          while ((line = input.readLine()) != null) {
            logger.trace("======= message received: {}", line);
            message_received.append(line);
          }
          passed = ServletTestUtil.compareString(EXPECTED_RESPONSE,
                  message_received.toString());
        }
      }
    } catch (Exception ex3) {
      passed = false;
      logger.error("Test" + ex3.getMessage());
    }

    if (!passed) {
      throw new Exception("Test Failed.");
    }
  }

  /*
   * @testName: nioInputTest2
   *
   * @assertion_ids: Servlet:JAVADOC:941;
   *
   * @test_Strategy: Create a Servlet TestServlet which supports async; Create a
   * ReadListener; Verify ServletInputStream.setReadListener(ReadListener)
   * throws IllegalStateException without Async or upgrade
   */
  @Test
  public void nioInputTest2() throws Exception {
    boolean passed = true;
    int sleepInSeconds = Integer
            .parseInt(_props.getProperty("servlet_async_wait").trim());
    String testName = "nioInputTest2";
    String EXPECTED_RESPONSE = "Test PASSED|IllegalStateException";

    String requestUrl = "http://" + _hostname + ":" + _port + getContextRoot() + "/" + getServletName() + "?testname="
            + testName;
    try {
      URL url = new URL(requestUrl);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      logger.trace("======= Connecting {}", url.toExternalForm());
      conn.setChunkedStreamingMode(5);
      conn.setDoOutput(true);
      conn.setRequestMethod("POST");

      logger.trace("======= Header {}", conn);
      conn.connect();

      try (BufferedWriter output = new BufferedWriter(
              new OutputStreamWriter(conn.getOutputStream()))) {
        String data = "Hello";
        output.write(data);
        output.flush();
        Thread.sleep(sleepInSeconds * 1000);

        data = "World";
        output.write(data);
        output.flush();
        output.close();

        try (BufferedReader input = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
          String line;
          StringBuilder message_received = new StringBuilder();

          while ((line = input.readLine()) != null) {
            logger.trace("======= message received: {}", line);
            message_received.append(line);
          }
          passed = ServletTestUtil.compareString(EXPECTED_RESPONSE,
                  message_received.toString());
        }
      }
    } catch (Exception ex3) {
      passed = false;
      logger.error("Test" + ex3.getMessage());
    }

    if (!passed) {
      throw new Exception("Test Failed.");
    }
  }
}
