/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates and others.
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
 * $Id:$
 */
package servlet.tck.pluggability.api.jakarta_servlet_http.cookie;

import servlet.tck.util.TestUtil;
import servlet.tck.api.jakarta_servlet_http.cookie.TestServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.request.HttpRequest;
import servlet.tck.common.request.HttpResponse;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class URLClient extends AbstractUrlClient {

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
            .addAsResource(URLClient.class.getResource("servlet_pluh_cookie_web-fragment.xml"),
                    "META-INF/web-fragment.xml");
    return ShrinkWrap.create(WebArchive.class, "servlet_pluh_cookie_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(TestServlet.class)
            .addAsLibraries(javaArchive);
  }


  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
  private int findCookie(Cookie[] cookie, String name) {
    boolean found = false;
    int i = 0;
    if (cookie != null) {
      while ((!found) && (i < cookie.length)) {
        if (cookie[i].getName().equals(name)) {
          found = true;
        } else {
          i++;
        }
      }
    } else {
      found = false;
    }
    if (found) {
      return i;
    } else {
      return -1;
    }
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
    TEST_PROPS.setProperty(APITEST, "cloneTest");
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
    TEST_PROPS.setProperty(APITEST, "constructorTest");
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
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/TestServlet?testname=constructorIllegalArgumentExceptionTest HTTP/1.1");
    TEST_PROPS.setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
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
    TEST_PROPS.setProperty(APITEST, "getCommentTest");
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
    TEST_PROPS.setProperty(APITEST, "getCommentNullTest");
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
    // version 1
    TEST_PROPS.setProperty(REQUEST_HEADERS,
        "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
            + "; $Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getDomainTest");
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
    TEST_PROPS.setProperty(APITEST, "getMaxAgeTest");
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
    // version 0 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
        + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getNameTest");
    invoke();

    // version 1 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS,
        "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
            + "; $Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getNameTest");
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
    TEST_PROPS.setProperty(REQUEST_HEADERS,
        "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
            + "; $Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getPathTest");
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
    TEST_PROPS.setProperty(APITEST, "getSecureTest");
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
    // version 0 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
        + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getValueTest");
    invoke();
    // version 1 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS,
        "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
            + "; $Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getValueTest");
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
    // version 0 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS, "Cookie: name1=value1; Domain="
        + _hostname + "; Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getVersionVer0Test");
    invoke();
    // version 1 Cookie
    TEST_PROPS.setProperty(REQUEST_HEADERS,
        "Cookie: $Version=1; name1=value1; $Domain=" + _hostname
            + "; $Path=" + getContextRoot());
    TEST_PROPS.setProperty(APITEST, "getVersionVer1Test");
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
    TEST_PROPS.setProperty(APITEST, "setDomainTest");
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
    // version 0 cookie
    String testName = "setMaxAgePositiveTest";
    HttpResponse response = null;
    String dateHeader = null;
    int index = -1;
    Date expiryDate = null;
    String body = null;

    HttpRequest request = new HttpRequest("GET " + getContextRoot() + "/"
        + getServletName() + "?testname=" + testName + " HTTP/1.1", _hostname,
        _port);

    try {
      response = request.execute();
      dateHeader = response.getResponseHeader("testDate").toString();
      CookieSpec spec = CookiePolicy.getCookieSpec(CookiePolicy.NETSCAPE);

      logTrace("Found " + response.getResponseHeaders("Set-Cookie").length
              + " set-cookie entry");

      boolean foundcookie = false;
      Header[] CookiesHeader = response.getResponseHeaders("Set-Cookie");
      int i = 0;
      while (i < CookiesHeader.length) {
        TestUtil.logTrace("Checking set-cookiei " + i + ":" + CookiesHeader[i]);
        Cookie[] cookies = spec.parse(".eng.com", _port, getServletName(),
            false, CookiesHeader[i]);
        index = findCookie(cookies, "name1");
        if (index >= 0) {
          expiryDate = cookies[index].getExpiryDate();
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
      throw new Exception("Exception occurred:" + t);
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

    if (body.indexOf(Data.PASSED) == -1) {
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
    TEST_PROPS.setProperty(APITEST, "setMaxAgeZeroTest");
    TEST_PROPS.setProperty(EXPECTED_HEADERS, "Set-Cookie:name1=value1##Max-Age=0");
    invoke();
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
    TEST_PROPS.setProperty(APITEST, "setMaxAgeNegativeTest");
    TEST_PROPS.setProperty(EXPECTED_HEADERS,
            "Set-Cookie:name1=value1##!Expire##!Max-Age");
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
    TEST_PROPS.setProperty(APITEST, "setSecureVer0Test");
    invoke();
    TEST_PROPS.setProperty(APITEST, "setSecureVer1Test");
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
    TEST_PROPS.setProperty(APITEST, "setValueVer0Test");
    invoke();
    TEST_PROPS.setProperty(APITEST, "setValueVer1Test");
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
    TEST_PROPS.setProperty(APITEST, "setVersionVer0Test");
    invoke();
    TEST_PROPS.setProperty(APITEST, "setVersionVer1Test");
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
    TEST_PROPS.setProperty(APITEST, "setAttributeTest");
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
    TEST_PROPS.setProperty(APITEST, "getAttributesTest");
    invoke();
  }
}
