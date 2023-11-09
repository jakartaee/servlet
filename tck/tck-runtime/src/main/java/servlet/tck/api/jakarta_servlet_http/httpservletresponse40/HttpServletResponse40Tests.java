/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * $Id:$
 */
package servlet.tck.api.jakarta_servlet_http.httpservletresponse40;

import servlet.tck.util.WebUtil;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;

import static servlet.tck.api.jakarta_servlet_http.httpservletrequest40.HttpServletRequest40Tests.DELIMITER;
import static servlet.tck.api.jakarta_servlet_http.httpservletrequest40.HttpServletRequest40Tests.ENCODING;

public class HttpServletResponse40Tests extends AbstractTckTest {

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpservletresponse40_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TrailerTestServlet.class, TrailerTestServlet2.class)
            .setWebXML(HttpServletResponse40Tests.class.getResource("servlet_jsh_httpservletresponse40_web.xml"));
  }

  private WebUtil.Response response;

  private String request;

  /*
   * @testName: TrailerTestWithHTTP10
   * 
   * @assertion_ids: servlet40:TrailerTestWithHTTP10;
   * 
   * @test_Strategy:
   */
  @Test
  public void TrailerTestWithHTTP10() throws Exception {

    String response = simpleTest("TrailerTestWithHTTP10", "HTTP/1.0",
        "/TrailerTestServlet");
    if (!response.contains("Get IllegalStateException when call setTrailerFields")) {
      logger.error(
          "The underlying protocol is HTTP 1.0, the IllegalStateException should be thrown");
      throw new Exception("TrailerTestWithHTTP10 failed.");
    }

  }

  /*
   * @testName: TrailerTestResponseCommitted
   * 
   * @assertion_ids: servlet40:TrailerTestResponseCommitted;
   * 
   * @test_Strategy:
   */
  @Test
  public void TrailerTestResponseCommitted() throws Exception {

    String response = simpleTest("TrailerTestResponseCommitted", "HTTP/1.1",
        "/TrailerTestServlet2");
    if (!response.contains("Get IllegalStateException when call setTrailerFields")) {
      logger.error(
          "The response has been committed, the IllegalStateException should be thrown");
      throw new Exception("TrailerTestResponseCommitted failed.");
    }

  }

  /*
   * @testName: TrailerTest
   * 
   * @assertion_ids: servlet40:TrailerTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void TrailerTest() throws Exception {
    String content = simpleTest("TrailerTest", "HTTP/1.1",
        "/TrailerTestServlet");
    // if (content.indexOf("Trailer: myTrailer") < 0) {
    // TestUtil.logErr("Can not find header, \"Trailer: myTrailer\"");
    // throw new Exception("TrailerTest failed.");
    // }
    int i = content.indexOf("Current trailer field: ");
    if (i < 0) {
      throw new Exception("TrailerTest failed.");
    }
    content = content.substring(i + "Current trailer field: ".length());
    String[] ss = content.split("\r\n");
    if (ss.length != 3) {
      throw new Exception("TrailerTest failed.");
    }
    int lastChunkSize = Integer.parseInt(ss[1], 16);
    if (lastChunkSize != 0 || !"myTrailer:foo".equals(ss[0].trim())) {
      logger.error("The current getTrailerFields is " + ss[0].trim()
          + ", But expected getTrailerFields should be myTrailer:foo");
      throw new Exception("TrailerTest failed.");
    }
    String[] trailer = ss[2].split(":");
    if (trailer.length != 2 || !"myTrailer".equals(trailer[0].trim())
        || !"foo".equals(trailer[1].trim())) {
      logger.error("Expected tailer should be myTrailer:foo");
      throw new Exception("TrailerTest failed.");
    }

  }


  private String simpleTest(String testName, String protocol,
      String servletPath) throws Exception {

    URL url = new URL(
            "http://" + _hostname + ":" + _port + getContextRoot() + servletPath);

    logger.debug("access {}", url);
    try (Socket socket = new Socket(url.getHost(), url.getPort());
         OutputStream output = socket.getOutputStream();
         InputStream input = socket.getInputStream()) {

      socket.setKeepAlive(true);
      String path = url.getPath();
      StringBuilder outputBuffer = new StringBuilder();
      outputBuffer.append("POST ").append(path).append(" ").append(protocol).append(DELIMITER);
      outputBuffer.append("Host: ").append(url.getHost()).append(DELIMITER);
      outputBuffer.append("Content-Type: text/plain").append(DELIMITER);
      outputBuffer.append("Content-Length: 3").append(DELIMITER);
      outputBuffer.append(DELIMITER);
      outputBuffer.append("ABC");

      byte[] outputBytes = outputBuffer.toString().getBytes(ENCODING);
      output.write(outputBytes);
      output.flush();

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      int read = 0;
      while ((read = input.read()) >= 0) {
        bytes.write(read);
      }
      String response = new String(bytes.toByteArray());
      logger.info(response);
      return response;
    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception(testName + " failed: ", e);
    }
  }
}
