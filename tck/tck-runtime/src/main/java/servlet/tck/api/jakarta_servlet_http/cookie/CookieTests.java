/*
 * Copyright (c) 2006, 2021 Oracle and/or its affiliates and others.
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

/*
 * $Id$
 */

package servlet.tck.api.jakarta_servlet_http.cookie;

import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.request.HttpExchange;
import servlet.tck.common.request.HttpResponse;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.HttpCookie;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public class CookieTests extends AbstractTckTest {


  @BeforeEach
  void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {

    return ShrinkWrap.create(WebArchive.class, "servlet_jsh_cookie_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .setWebXML(CookieTests.class.getResource("servlet_jsh_cookie_web.xml"));
  }

  /* Run test */

  /*
   * @testName: cloneTest
   *
   * @assertion_ids: Servlet:JAVADOC:453
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void cloneTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "cloneTest");
    invoke();
  }

  /*
   * @testName: constructorTest
   *
   * @assertion_ids: Servlet:JAVADOC:434
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void constructorTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest");
    invoke();
  }

  /*
   * @testName: constructorIllegalArgumentExceptionTest
   *
   * @assertion_ids: Servlet:JAVADOC:628
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void constructorIllegalArgumentExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
            "GET /servlet_jsh_cookie_web/TestServlet?testname=constructorIllegalArgumentExceptionTest HTTP/1.1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: getCommentTest
   *
   * @assertion_ids: Servlet:JAVADOC:436
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getCommentTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCommentTest");
    invoke();
  }

  /*
   * @testName: getCommentNullTest
   *
   * @assertion_ids: Servlet:JAVADOC:437
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getCommentNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCommentNullTest");
    invoke();
  }

  /*
   * @testName: getDomainTest
   *
   * @assertion_ids: Servlet:JAVADOC:439
   *
   * @test_Strategy: Client sends a version 0 and 1 cookie to the servlet.
   * Servlet verifies values and returns result to client
   */
  @Test
  void getDomainTest() throws Exception {
    // version 1
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
            "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
                    + "; $Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getDomainTest");
    invoke();

  }

  /*
   * @testName: getMaxAgeTest
   *
   * @assertion_ids: Servlet:JAVADOC:443
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getMaxAgeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getMaxAgeTest");
    invoke();
  }

  /*
   * @testName: getNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:448
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getNameTest() throws Exception {
    // version 0
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
            + _hostname + "; Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getNameTest");
    invoke();
    // version 1
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
            "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
                    + "; $Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getNameTest");
    invoke();
  }

  /*
   * @testName: getPathTest
   *
   * @assertion_ids: Servlet:JAVADOC:445
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getPathTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
            "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
                    + "; $Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getPathTest");
    invoke();
  }

  /*
   * @testName: getSecureTest
   *
   * @assertion_ids: Servlet:JAVADOC:447
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSecureTest");
    invoke();
  }

  /*
   * @testName: getValueTest
   *
   * @assertion_ids: Servlet:JAVADOC:450
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getValueTest() throws Exception {
    // version 0
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
            + _hostname + "; Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getValueTest");
    invoke();
    // version 1
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
            "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
                    + "; $Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getValueTest");
    invoke();
  }

  /*
   * @testName: getVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:451
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getVersionTest() throws Exception {
    // version 0
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
            + _hostname + "; Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getVersionVer0Test");
    invoke();
    // version 1
    TEST_PROPS.get().setProperty(REQUEST_HEADERS,
            "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
                    + "; $Path=/servlet_jsh_cookie_web");
    TEST_PROPS.get().setProperty(APITEST, "getVersionVer1Test");
    invoke();
  }

  /*
   * @testName: setDomainTest
   *
   * @assertion_ids: Servlet:JAVADOC:438
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setDomainTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setDomainTest");
    invoke();
  }

  /*
   * @testName: setMaxAgePositiveTest
   *
   * @assertion_ids: Servlet:JAVADOC:440
   *
   * @test_Strategy: Servlet sets values and client verifies them
   */
  @Test
  void setMaxAgePositiveTest() throws Exception {
    String testName = "setMaxAgePositiveTest";
    HttpResponse response = null;
    String dateHeader = null;
    Date expiryDate = null;
    String body = null;

    HttpExchange request = new HttpExchange("GET " + getContextRoot() + "/"
            + getServletName() + "?testname=" + testName + " HTTP/1.1", _hostname,
            _port);

    try {
      response = request.execute();
      dateHeader = response.getResponseHeader("testDate")
              .orElseThrow(() -> new NullPointerException("testDate is empty")).getValue();


      logger.trace("Found {} set-cookie entry", response.getResponseHeaders("Set-Cookie").size());

      boolean foundcookie = false;
      List<String> cookiesHeaders = response.getResponseHeaders("Set-Cookie");
      int i = 0;
      while (i < cookiesHeaders.size()) {
        logger.trace("Checking set-cookiei {}:{}", i, cookiesHeaders.get(i));
        List<HttpCookie> cookies = HttpCookie.parse(cookiesHeaders.get(i));
        Optional<HttpCookie> optionalHttpCookie = cookies.stream().filter(httpCookie -> httpCookie.getName().equals("name1"))
                .findFirst();
        if (optionalHttpCookie.isPresent()) {
          HttpCookie httpCookie = optionalHttpCookie.get();
          expiryDate = new Date(httpCookie.getMaxAge());
          body = response.getResponseBodyAsString();
          foundcookie = true;
          break;
        }
        i++;
      }

      if (!foundcookie)
        throw new Exception("The test cookie was not located in the response");
    } catch (Throwable t) {
      throw new Exception("Exception occurred:" + t, t);
    }

    // put expiry date into GMT
    SimpleDateFormat sdf = new SimpleDateFormat(TestServlet.CUSTOM_HEADER_DATE_FORMAT);
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    String resultStringDate = sdf.format(expiryDate);
    try {
      Date resultDate = sdf.parse(resultStringDate);
      Date expectedDate = sdf
              .parse(dateHeader.substring(dateHeader.indexOf(": ") + 2).trim());
      if (resultDate.before(expectedDate)) {
        throw new Exception("The expiry date was incorrect, expected ="
                + expectedDate + ", result = " + resultDate);
      }
    } catch (Throwable t) {
      throw new Exception("Exception occurred: " + t);
    }

    if (!body.contains(Data.PASSED)) {
      throw new Exception("The string: " + Data.PASSED + " not found in response");
    }
  }

  /*
   * @testName: setMaxAgeZeroTest
   *
   * @assertion_ids: Servlet:JAVADOC:442
   *
   * @test_Strategy: Servlet sets values and client verifies them
   */
  @Test
  void setMaxAgeZeroTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setMaxAgeZeroTest");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Set-Cookie:name1=value1##Max-Age=0");
    invoke();
  }

  /*
   * @testName: setMaxAgeNegativeTest
   *
   * @assertion_ids: Servlet:JAVADOC:441
   *
   * @test_Strategy: Servlet sets values and client verifies them
   */
  @Test
  void setMaxAgeNegativeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setMaxAgeNegativeTest");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
            "Set-Cookie:name1=value1##!Expire##!Max-Age");
    invoke();
  }

  /*
   * @testName: setPathTest
   *
   * @assertion_ids: Servlet:JAVADOC:444
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setPathTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setPathTest");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
            "Set-Cookie:Path=\"/servlet_jsh_cookie_web\"");
    invoke();
  }

  /*
   * @testName: setSecureTest
   *
   * @assertion_ids: Servlet:JAVADOC:446
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSecureVer0Test");
    invoke();
    TEST_PROPS.get().setProperty(APITEST, "setSecureVer1Test");
    invoke();
  }

  /*
   * @testName: setValueTest
   *
   * @assertion_ids: Servlet:JAVADOC:449
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setValueTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setValueVer0Test");
    invoke();
    TEST_PROPS.get().setProperty(APITEST, "setValueVer1Test");
    invoke();
  }

  /*
   * @testName: setVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:452
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setVersionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setVersionVer0Test");
    invoke();
    TEST_PROPS.get().setProperty(APITEST, "setVersionVer1Test");
    invoke();
  }

  /*
   * @testName: setAttributeTest
   *
   * @assertion_ids:
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void setAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeTest");
    invoke();
  }

  /*
   * @testName: getAttributesTest
   *
   * @assertion_ids:
   *
   * @test_Strategy: Servlet tests method and returns result to client
   */
  @Test
  void getAttributesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributesTest");
    invoke();
  }
}
