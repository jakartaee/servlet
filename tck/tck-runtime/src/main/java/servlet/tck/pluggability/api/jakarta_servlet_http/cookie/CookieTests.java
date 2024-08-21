/*
 * Copyright (c) 2012, 2024 Oracle and/or its affiliates and others.
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
package servlet.tck.pluggability.api.jakarta_servlet_http.cookie;

import servlet.tck.api.jakarta_servlet_http.cookie.TestServlet;
import servlet.tck.common.client.AbstractTckTest;
import servlet.tck.common.request.HttpExchange;
import servlet.tck.common.request.HttpResponse;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar")
            .addClasses(TestServlet1.class, RequestListener1.class)
            .addAsResource(CookieTests.class.getResource("servlet_pluh_cookie_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_cookie_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .addAsLibraries(javaArchive);
  }



  /* Run test */

  /*
   * @testName: cloneTest
   *
   * @assertion_ids: Servlet:JAVADOC:453
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method Cookie.clone and verify it works;
   */
  @Test
  public void cloneTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "cloneTest");
    invoke();
  }

  /*
   * @testName: constructorTest
   *
   * @assertion_ids: Servlet:JAVADOC:434
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests constructor method and verify it works;
   */
  @Test
  public void constructorTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "constructorTest");
    invoke();
  }

  /*
   * @testName: constructorIllegalArgumentExceptionTest
   *
   * @assertion_ids: Servlet:JAVADOC:628
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests constructor method throws IllegalArgumentException when
   * invalid names are used(unsupported characters in name);
   */
  @Test
  public void constructorIllegalArgumentExceptionTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet?testname=constructorIllegalArgumentExceptionTest HTTP/1.1");
    TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
    invoke();
  }

  /*
   * @testName: getCommentTest
   *
   * @assertion_ids: Servlet:JAVADOC:436
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method getComment;
   */
  @Test
  public void getCommentTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCommentTest");
    invoke();
  }

  /*
   * @testName: getCommentNullTest
   *
   * @assertion_ids: Servlet:JAVADOC:437
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method getComment when there is no comment;
   */
  @Test
  public void getCommentNullTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getCommentNullTest");
    invoke();
  }

  /*
   * @testName: getDomainTest
   *
   * @assertion_ids: Servlet:JAVADOC:439
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Client sends a version 0 and 1 cookie to the servlet. Servlet verifies
   * values of the cookies;
   */
  @Test
  public void getDomainTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getDomainTest");
    invoke();
  }

  /*
   * @testName: getMaxAgeTest
   *
   * @assertion_ids: Servlet:JAVADOC:443
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method getMaxAge;
   */
  @Test
  public void getMaxAgeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getMaxAgeTest");
    invoke();
  }

  /*
   * @testName: getNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:448
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Client sends a version 0 and 1 cookie to a servlet; Servlet tests method
   * Cookie.getName
   */
  @Test
  public void getNameTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
        + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.get().setProperty(APITEST, "getNameTest");
    invoke();
  }

  /*
   * @testName: getPathTest
   *
   * @assertion_ids: Servlet:JAVADOC:445
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Client sends a version 1 cookie to a servlet; Servlet tests method getPath
   * using the received Cookie
   */
  @Test
  public void getPathTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getPathTest");
    invoke();
  }

  /*
   * @testName: getSecureTest
   *
   * @assertion_ids: Servlet:JAVADOC:447
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method Cookie.getSecure;
   */
  @Test
  public void getSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getSecureTest");
    invoke();
  }

  @Test
  public void getAttributeSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeSecureTest");
    invoke();
  }

  /*
   * @testName: getValueTest
   *
   * @assertion_ids: Servlet:JAVADOC:450
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Client sends a version 0 and 1 cookie to a servlet; Servlet tests method
   * getValue and verify the right cookie received
   */
  @Test
  public void getValueTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
        + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.get().setProperty(APITEST, "getValueTest");
    invoke();
  }

  @Test
  public void getValueQuotedTest() throws Exception {
    TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Cookie: name1=\"value1\"; Domain="
            + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.get().setProperty(APITEST, "getValueQuotedTest");
    invoke();
  }

  /*
   * @testName: getVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:451
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Client sends a version 0 and 1 cookie to a servlet; Servlet tests method
   * Cookie.getVersion and verify the right cookie received
   */
  @Test
  public void getVersionTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getVersionTest");
    invoke();
  }

  /*
   * @testName: setDomainTest
   *
   * @assertion_ids: Servlet:JAVADOC:438
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method Cookie.setDomain
   */
  @Test
  public void setDomainTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setDomainTest");
    invoke();
  }

  /*
   * @testName: setMaxAgePositiveTest
   *
   * @assertion_ids: Servlet:JAVADOC:440
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Cookie and sets values using Cookie.setMaxAge(2) Cookie is
   * sent back to client and client verifies them
   */
  @Test
  public void setMaxAgePositiveTest() throws Exception {
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

      logger.trace("Found {} set-cookie entry", String.valueOf(response.getResponseHeaders("Set-Cookie").size()));

      boolean foundcookie = false;
      List<String> cookiesHeaders = response.getResponseHeaders("Set-Cookie");
      int i = 0;
      while (i < cookiesHeaders.size()) {
        if(logger.isTraceEnabled()) {
          logger.trace("Checking set-cookiei {}:{}", String.valueOf(i), cookiesHeaders.get(i));
        }
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

      if (!foundcookie) {
        throw new Exception("The test cookie was not located in the response");
      }
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

    if (body == null || !body.contains(Data.PASSED)) {
      throw new Exception("The string: " + Data.PASSED + " not found in response");
    }
  }

  /*
   * @testName: setMaxAgeZeroTest
   *
   * @assertion_ids: Servlet:JAVADOC:442
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Cookie and sets values using Cookie.setMaxAge(0) Cookie is
   * sent back to client and client verifies them
   */
  @Test
  public void setMaxAgeZeroTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setMaxAgeZeroTest");
    // RFC 6265 - server should only send +ve values for Max-Age
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Set-Cookie:name1=value1##!Max-Age##Expires=");
    invoke();
  }

  @Test
  public void setMaxAgeZeroDateTest() throws Exception {
    String testName = "setMaxAgeZeroTest";
    HttpResponse response = null;
    Date expiryDate = null;
    String body = null;

    HttpExchange request = new HttpExchange("GET " + getContextRoot() + "/"
            + getServletName() + "?testname=" + testName + " HTTP/1.1", _hostname,
            _port);

    try {
      response = request.execute();

      logger.trace("Found {} set-cookie entry", String.valueOf(response.getResponseHeaders("Set-Cookie").size()));

      boolean foundcookie = false;
      List<String> cookiesHeaders = response.getResponseHeaders("Set-Cookie");
      int i = 0;
      while (i < cookiesHeaders.size()) {
        logger.trace("Checking set-cookie i {}:{}", String.valueOf(i), cookiesHeaders.get(i));
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
      /*
       * RFC 6265 does not allow the server to send a Max-Age attribute with value zero. The implication from the client
       * requirements is that an expires header should be sent with the earliest representable date. There is a fair
       * amount of room for interpretation in all of this so check that whatever has been sent is in the past.
       */
      Date expectedDate = new Date(System.currentTimeMillis());
      if (resultDate.after(expectedDate)) {
        throw new Exception("The expiry date was incorrect, expected ="
                + expectedDate + ", result = " + resultDate);
      }
    } catch (Throwable t) {
      throw new Exception("Exception occurred: " + t);
    }

    if (body == null || !body.contains(Data.PASSED)) {
      throw new Exception("The string: " + Data.PASSED + " not found in response");
    }

  }

  /*
   * @testName: setMaxAgeNegativeTest
   *
   * @assertion_ids: Servlet:JAVADOC:441
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Cookie and sets values using Cookie.setMaxAge(-1) Cookie is
   * sent back to client and client verifies them
   */
  @Test
  public void setMaxAgeNegativeTest() throws Exception {
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
  public void setPathTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setPathTest");
    // The path is set in the Servlet so need to check for that, not the current context path
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS,
            "Set-Cookie:Path=\"/servlet_jsh_cookie_web\"");
    invoke();
  }

  /*
   * @testName: setSecureTest
   *
   * @assertion_ids: Servlet:JAVADOC:446
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Version 0 and Version 1 Cookie and sets values using
   * Cookie.setSecure Cookie is sent back to client and client verifies them
   */
  @Test
  public void setSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setSecureTest");
    invoke();
  }

  @Test
  public void setAttributeSecureTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeSecureTest");
    invoke();
  }

  @Test
  public void setAttributeSecureInvalidTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeSecureInvalidTest");
    invoke();
  }

  /*
   * @testName: setValueTest
   *
   * @assertion_ids: Servlet:JAVADOC:449
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Version 0 and Version 1 Cookie and sets values using
   * Cookie.setValue Cookie is sent back to client and client verifies them
   */
  @Test
  public void setValueTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setValueTest");
    invoke();
  }

  /*
   * @testName: setVersionTest
   *
   * @assertion_ids: Servlet:JAVADOC:452
   *
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet create Version 0 and Version 1 Cookie and sets values using
   * Cookie.setVersion Cookie is sent back to client and client verifies them
   */
  @Test
  public void setVersionTest() throws Exception {
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
   * @test_Strategy: Create a web application with no web.xml and one fragment;
   * Define everything in web-fragment.xml; Package everything in the fragment;
   * Servlet tests method Cookie.setAttribute
   */
  @Test
  public void setAttributeTest() throws Exception {
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
  public void getAttributesTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributesTest");
    invoke();
  }

  @Test
  public void getHttpOnlyTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getHttpOnlyTest");
    invoke();
  }

  @Test
  public void getAttributeHttpOnlyTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "getAttributeHttpOnlyTest");
    invoke();
  }

  @Test
  public void setHttpOnlyTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setHttpOnlyTest");
    invoke();
  }

  @Test
  public void setAttributeHttpOnlyTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeHttpOnlyTest");
    invoke();
  }

  @Test
  public void setAttributeHttpOnlyInvalidTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setAttributeHttpOnlyInvalidTest");
    invoke();
  }

  @Test
  public void setEmptyAttributeTest() throws Exception {
    TEST_PROPS.get().setProperty(APITEST, "setEmptyAttributeTest");
    TEST_PROPS.get().setProperty(EXPECTED_HEADERS, "Set-Cookie:name1=value1##EmptyAttribute##!EmptyAttribute=");
    invoke();
  }
}
