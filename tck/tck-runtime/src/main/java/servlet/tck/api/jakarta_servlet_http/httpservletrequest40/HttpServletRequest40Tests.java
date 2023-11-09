/*
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet_http.httpservletrequest40;

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
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class HttpServletRequest40Tests extends AbstractTckTest {

  public static final String DELIMITER = "\r\n";

  public static final String ENCODING = "ISO-8859-1";
  
  private WebUtil.Response response;

  private String request;


  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpservletrequest40_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(DispatchServlet.class, ForwardFilter.class, ForwardServlet.class,
                    IncludeServlet.class, NamedForwardServlet.class, NamedIncludeServlet.class,
                    TestServlet.class, TrailerTestServlet.class, Utilities.class)
            .setWebXML(HttpServletRequest40Tests.class.getResource("servlet_jsh_httpservletrequest40_web.xml"));
  }  
  
  /*
   * @testName: httpServletMappingTest
   * 
   * @assertion_ids: servlet40:httpServletMappingTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingTest() throws Exception {
    simpleTest("httpServletMappingTest", getContextRoot() + "/TestServlet", "GET",
        "matchValue=TestServlet, pattern=/TestServlet, servletName=TestServlet, mappingMatch=EXACT");
  }

  /*
   * @testName: httpServletMappingTest2
   * 
   * @assertion_ids: servlet40:httpServletMappingTest2;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingTest2() throws Exception {
    simpleTest("httpServletMappingTest2", getContextRoot() + "/a.ts", "GET",
        "matchValue=a, pattern=*.ts, servletName=TestServlet, mappingMatch=EXTENSION");
  }

  /*
   * @testName: httpServletMappingTest3
   * 
   * @assertion_ids: servlet40:httpServletMappingTest3;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingTest3() throws Exception {
    simpleTest("httpServletMappingTest3", getContextRoot() + "/default", "GET", 
        "matchValue=, pattern=/, servletName=defaultServlet, mappingMatch=DEFAULT");
  }

  /*
   * @testName: httpServletMappingForwardTest
   * 
   * @assertion_ids: servlet40:httpServletMappingForwardTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingForwardTest() throws Exception {
    simpleTest("httpServletMappingForwardTest",
        getContextRoot() + "/ForwardServlet", "GET",
        "matchValue=a, pattern=*.ts, servletName=TestServlet, mappingMatch=EXTENSION");
  }

  /*
   * @testName: httpServletMappingNamedForwardTest
   * 
   * @assertion_ids: servlet40:httpServletMappingNamedForwardTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingNamedForwardTest() throws Exception {
    simpleTest("httpServletMappingNamedForwardTest",
        getContextRoot() + "/NamedForwardServlet", "GET",
        "matchValue=NamedForwardServlet, pattern=/NamedForwardServlet, servletName=NamedForwardServlet, mappingMatch=EXACT");
  }

  /*
   * @testName: httpServletMappingNamedIncludeTest
   * 
   * @assertion_ids: servlet40:httpServletMappingNamedIncludeTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingNamedIncludeTest() throws Exception {
    simpleTest("httpServletMappingNamedIncludeTest",
        getContextRoot() + "/NamedIncludeServlet", "GET",
        "matchValue=NamedIncludeServlet, pattern=/NamedIncludeServlet, servletName=NamedIncludeServlet, mappingMatch=EXACT");
  }

  /*
   * @testName: httpServletMappingIncludeTest
   * 
   * @assertion_ids: servlet40:httpServletMappingIncludeTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingIncludeTest() throws Exception {
    simpleTest("httpServletMappingIncludeTest",
        getContextRoot() + "/IncludeServlet", "POST",
        "matchValue=IncludeServlet, pattern=/IncludeServlet, servletName=IncludeServlet, mappingMatch=EXACT");
  }

  /*
   * @testName: httpServletMappingFilterTest
   * 
   * @assertion_ids: servlet40:httpServletMappingFilterTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingFilterTest() throws Exception {
    simpleTest("httpServletMappingFilterTest", getContextRoot() + "/ForwardFilter",
        "GET",
        "matchValue=, pattern=/, servletName=defaultServlet, mappingMatch=DEFAULT");
  }

  /*
   * @testName: httpServletMappingDispatchTest
   * 
   * @assertion_ids: servlet40:httpServletMappingDispatchTest;
   * 
   * @test_Strategy:
   */
  @Test
  public void httpServletMappingDispatchTest() throws Exception {
    simpleTest("httpServletMappingDispatchTest",
            getContextRoot() + "/DispatchServlet", "GET",
            "matchValue=TestServlet, pattern=/TestServlet, servletName=TestServlet, mappingMatch=EXACT");
  }

  @Test
  private void simpleTest(String testName, String request, String method,
      String expected) throws Exception {
    try {
      logger.debug("Sending request {}", request);

      response = WebUtil.sendRequest(method, InetAddress.getByName(_hostname),
          _port, getRequest(request), null, null);

    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception(testName + " failed: ", e);
    }

    logger.debug("response.statusToken: {}", response.statusToken);
    logger.debug("response.content: {}", response.content);

    // Check that the page was found (no error).
    if (response.isError()) {
      logger.error("Could not find {}", request);
      throw new Exception(testName + " failed.");
    }

    if (!response.content.contains(expected)) {
      logger.error("Expected: {} but found {}", expected, response.content);
      throw new Exception(testName + " failed.");
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
    InputStream input;

    URL url = new URL("http://" + _hostname + ":" + _port + getContextRoot()
            + "/TrailerTestServlet");
    try (Socket socket = new Socket(url.getHost(), url.getPort());
         OutputStream output = socket.getOutputStream()) {
      socket.setKeepAlive(true);

      String path = url.getPath();
      StringBuffer outputBuffer = new StringBuffer();
      outputBuffer.append("POST " + path + " HTTP/1.1" + DELIMITER);
      outputBuffer.append("Host: " + url.getHost() + DELIMITER);
      outputBuffer.append("Connection: keep-alive" + DELIMITER);
      outputBuffer.append("Content-Type: text/plain" + DELIMITER);
      outputBuffer.append("Transfer-Encoding: chunked" + DELIMITER);
      outputBuffer.append("Trailer: myTrailer, myTrailer2" + DELIMITER);
      outputBuffer.append(DELIMITER);
      outputBuffer.append("3" + DELIMITER);
      outputBuffer.append("ABC" + DELIMITER);
      outputBuffer.append("0" + DELIMITER);
      outputBuffer.append("myTrailer:foo");
      outputBuffer.append(DELIMITER);
      outputBuffer.append("myTrailer2:bar");
      outputBuffer.append(DELIMITER);
      outputBuffer.append(DELIMITER);

      byte[] outputBytes = outputBuffer.toString().getBytes(ENCODING);
      output.write(outputBytes);
      output.flush();

      input = socket.getInputStream();
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      int read = 0;
      while ((read = input.read()) >= 0) {
        bytes.write(read);
      }
      String response = bytes.toString();
      logger.debug(response);
      if (response.indexOf("isTrailerFieldsReady: true") < 0) {
        logger.error("isTrailerFieldsReady should be true");
        throw new Exception("TrailerTest failed.");
      }

      if (!response.toLowerCase().contains("mytrailer=foo")) {
        logger.error("failed to get trailer field: mytrailer=foo");
        throw new Exception("TrailerTest failed.");
      }

      if (!response.toLowerCase().contains("mytrailer2=bar")) {
        logger.error("failed to get trailer field: mytrailer=foo");
        throw new Exception("TrailerTest failed.");
      }
    } catch (Exception e) {
      logger.error("Caught exception: " + e.getMessage(), e);
      throw new Exception("TrailerTest failed: ", e);
    }
  }


  /*
   * @testName: TrailerTest2
   * 
   * @assertion_ids: servlet40:TrailerTest2;
   * 
   * @test_Strategy:
   */
  @Test
  public void TrailerTest2() throws Exception {
    simpleTest("TrailerTest2", getContextRoot() + "/TrailerTestServlet", "POST",
        "isTrailerFieldsReady: true");
    simpleTest("TrailerTest2", getContextRoot() + "/TrailerTestServlet", "POST",
        "Trailer: {}");
  }
}
