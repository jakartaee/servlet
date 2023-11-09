/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates and others.
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
package servlet.tck.api.jakarta_servlet_http.httpservletrequest;

import java.util.*;
import java.util.stream.Collectors;

import servlet.tck.common.request.Header;
import servlet.tck.common.request.HttpExchange;
import servlet.tck.common.request.HttpResponse;
import servlet.tck.common.request.HttpRequestClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.Data;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpServletRequestTests extends HttpRequestClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_jsh_httpservletrequest_web.war")
                .addAsLibraries(CommonServlets.getCommonServletsArchive())
                .addClasses(GetParameterNamesEmptyEnumTestServlet.class, GetQueryStringNullTestServlet.class,
                        GetReaderUnsupportedEncodingExceptionTestServlet.class, doHeadTest.class,
                        getServletContextTest.class, GetServletPathEmptyStringTestServlet.class,
                        SetCharacterEncodingTest.class, SetCharacterEncodingUnsupportedEncodingExceptionTest.class)
                .setWebXML(HttpServletRequestTests.class.getResource("servlet_jsh_httpservletrequest_web.xml"));
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    // ------------------------------- ServletRequest
    // ------------------------------
    /*
   * @testName: getAttributeNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:573
   * 
   * @test_Strategy: Servlet verifies attributes
   *
   */
    @Test
    public void getAttributeNamesTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "getAttributeNamesTest");
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getAttributeNamesEmptyEnumTest");
        invoke();
    }

    /*
   * @testName: getAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:572
   * 
   * @test_Strategy: Servlet verifies attribute
   *
   */
    @Test
    public void getAttributeTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "getAttributeTest");
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getAttributeDoesNotExistTest");
        invoke();
    }

    /*
   * @testName: getCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:574
   * 
   * @test_Strategy: Servlet verifies encoding
   */
    @Test
    public void getCharacterEncodingTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingTest");
        TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Content-Type:text/plain; charset=ISO-8859-1");
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getCharacterEncodingNullTest");
        invoke();
    }

    /*
   * @testName: getContentLengthTest
   * 
   * @assertion_ids: Servlet:JAVADOC:575
   * 
   * @test_Strategy: Servlet compares this length to the actual length of the
   * content body read in using getInputStream
   *
   */
    /*
   * @testName: getContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:576; Servlet:SPEC:34;
   * 
   * @test_Strategy: Client sets the content type and servlet reads it.
   *
   */
    /*
   * @testName: getInputStreamTest
   * 
   * @assertion_ids: Servlet:JAVADOC:577
   * 
   * @test_Strategy: Servlet tries to read the input stream.
   */
    /*
   * @testName: getInputStreamIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:579
   * 
   * @test_Strategy: Servlet gets a Reader object using
   * ServletRequest.getReader() then tries to get the inputStream Object
   *
   */
    /*
   * @testName: getLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:580
   * 
   * @test_Strategy: Servlet sends back locale to client.
   */
    @Test
    public void getLocaleTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "getLocaleTest");
        TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Accept-Language:en-US");
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getLocaleDefaultTest");
        invoke();
    }

    /*
   * @testName: getLocalesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:581
   * 
   * @test_Strategy: Servlet sends back locale(s) to client.
   */
    @Test
    public void getLocalesTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "getLocalesTest");
        TEST_PROPS.get().setProperty(REQUEST_HEADERS, "Accept-Language:en-US,en-GB");
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getLocalesDefaultTest");
        invoke();
    }

    /*
   * @testName: getParameterMapTest
   * 
   * @assertion_ids: Servlet:JAVADOC:583
   * 
   * @test_Strategy: Client sets several parameters and the servlet attempts to
   * access them.
   */
    /*
   * @testName: getParameterNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:584
   * 
   * @test_Strategy: Servlet attempts to access parameters.
   */
    @Test
    public void getParameterNamesTest() throws Exception {
        String testName = "getParameterNamesTest";
        TEST_PROPS.get().setProperty(TEST_NAME, testName);
        TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/" + getServletName() + "?testname=" + testName + "&parameter1=value1&parameter2=value2 HTTP/1.1");
        TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
        invoke();
        testName = "getParameterNamesEmptyEnumTest";
        TEST_PROPS.get().setProperty(TEST_NAME, testName);
        TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
        TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
        invoke();
    }

    /*
   * @testName: getParameterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:582
   * 
   * @test_Strategy: Client sets a parameter and servlet retrieves it.
   */
    @Test
    public void getParameterTest() throws Exception {
        String testName = "getParameterTest";
        TEST_PROPS.get().setProperty(TEST_NAME, testName);
        TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/" + getServletName() + "?testname=" + testName + "&parameter1=value1 HTTP/1.1");
        TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getParameterDoesNotExistTest");
        invoke();
    }

    /*
   * @testName: getParameterValuesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:585
   * 
   * @test_Strategy: Servlet verifies values
   */
    @Test
    public void getParameterValuesTest() throws Exception {
        String testName = "getParameterValuesTest";
        TEST_PROPS.get().setProperty(TEST_NAME, testName);
        TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/" + getServletName() + "?testname=" + testName + "&Names=value1&Names=value2 HTTP/1.1");
        TEST_PROPS.get().setProperty(SEARCH_STRING, Data.PASSED);
        invoke();
        TEST_PROPS.get().setProperty(APITEST, "getParameterValuesDoesNotExistTest");
        invoke();
    }

    /*
   * @testName: getProtocolTest
   * 
   * @assertion_ids: Servlet:JAVADOC:586
   * 
   * @test_Strategy: Servlet verifies the protocol used by the client
   */
    /*
   * @testName: getReaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:587
   * 
   * @test_Strategy: Client sets some content and servlet reads the content
   */
    /*
   * @testName: getReaderIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:590
   * 
   * @test_Strategy: Servlet gets an InputStream Object then tries to get a
   * Reader Object.
   */
    /*
   * @testName: getReaderUnsupportedEncodingExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:589
   * 
   * @test_Strategy: Client sets some content but with an invalid encoding,
   * servlet tries to read content.
   */
    /*
   * @testName: getRemoteAddrTest
   * 
   * @assertion_ids: Servlet:JAVADOC:592
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getLocalAddrTest
   * 
   * @assertion_ids: Servlet:JAVADOC:719
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getRemoteHostTest
   * 
   * @assertion_ids: Servlet:JAVADOC:593
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getRequestDispatcherTest
   * 
   * @assertion_ids: Servlet:JAVADOC:594
   * 
   * @test_Strategy: Servlet tries to get a dispatcher
   */
    /*
   * @testName: getSchemeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:595
   * 
   * @test_Strategy: Servlet verifies the scheme of the url used in the request
   */
    /*
   * @testName: getServerNameTest
   * 
   * @assertion_ids: Servlet:JAVADOC:596
   * 
   * @test_Strategy: Servlet verifies the destination of the request
   */
    /*
   * @testName: getServerPortTest
   * 
   * @assertion_ids: Servlet:JAVADOC:597
   * 
   * @test_Strategy: Servlet verifies the destination port of the request
   */
    /*
   * @testName: isSecureTest
   * 
   * @assertion_ids: Servlet:JAVADOC:598
   * 
   * @test_Strategy: Servlet verifies the isSecure method for the non-secure
   * case.
   */
    /*
   * @testName: removeAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:599
   * 
   * @test_Strategy: Servlet adds then removes an attribute, then verifies it
   * was removed.
   */
    /*
   * @testName: setAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:600
   * 
   * @test_Strategy: Servlet adds an attribute, then verifies it was added
   */
    /*
   * @testName: setCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:601
   * 
   * @test_Strategy: Servlet sets a new encoding and tries to retrieve it.
   */
    /*
   * @testName: setCharacterEncodingTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:601; Servlet:JAVADOC:574; Servlet:SPEC:28;
   * Servlet:SPEC:213;
   * 
   * @test_Strategy: HttpServletRequest calls getReader()first; then sets a new
   * encoding and tries to retrieve it. verifies that the new encoding is
   * ignored.
   */
    /*
   * @testName: setCharacterEncodingUnsupportedEncodingExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:602
   * 
   * @test_Strategy: Servlet tries to set an invalid encoding.
   *
   */
    // ---------------------------- END ServletRequest
    // -----------------------------
    // ---------------------------- HttpServletRequest
    // -----------------------------
    /*
   * @testName: getAuthTypeWithoutProtectionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:530
   * 
   * @test_Strategy: Servlet verifies correct result
   */
    /*
   * @testName: getContextPathTest
   * 
   * @assertion_ids: Servlet:JAVADOC:550
   * 
   * @test_Strategy: Client sets header and servlet verifies the result
   */
    /*
   * @testName: getCookiesNoCookiesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:532
   * 
   * @test_Strategy: Servlet tries to get a cookie when none exist
   */
    /*
   * @testName: getCookiesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:531
   * 
   * @test_Strategy:Client sets a cookie and servlet tries to read it
   */
    /*
   * @testName: getDateHeaderIllegalArgumentExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:535
   * 
   * @test_Strategy: Client set invalid date value, servlet tries to read it.
   */
    /*
   * @testName: getDateHeaderNoHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:534
   * 
   * @test_Strategy: Servlet tries to get a dateHeader when none exist
   */
    /*
   * @testName: getDateHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:533
   * 
   * @test_Strategy: client sets a dateheader and servlet tries to read it.
   */
    /*
   * @testName: getHeaderNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:540
   * 
   * @test_Strategy: Client sets some headers and servlet tries to read them.
   */
    /*
   * @testName: getHeaderNoHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:537
   * 
   * @test_Strategy: Servlet tries to read a header when none exist
   */
    /*
   * @testName: getHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:536
   * 
   * @test_Strategy: Client sets a header and servlet tries to read it.
   */
    /*
   * @testName: getHeadersNoHeadersTest
   * 
   * @assertion_ids: Servlet:JAVADOC:539
   * 
   * @test_Strategy: Servlet tries to get all the headers when none have been
   * added
   */
    /*
   * @testName: getHeadersTest
   * 
   * @assertion_ids: Servlet:JAVADOC:538
   * 
   * @test_Strategy: Client sets some headers and servlet tries to read them
   */
    /*
   * @testName: getIntHeaderNoHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:543
   * 
   * @test_Strategy: Servlet tries to read a header when none exist.
   */
    /*
   * @testName: getIntHeaderNumberFoundExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:544
   * 
   * @test_Strategy: Client sets an invalid header and servlet tries to read it.
   */
    /*
   * @testName: getIntHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:542
   * 
   * @test_Strategy: Client sets a header and servlet reads it
   */
    /*
   * @testName: getMethodTest
   * 
   * @assertion_ids: Servlet:JAVADOC:545
   * 
   * @test_Strategy: Client makes 3 calls using GET/POST/HEAD
   */
    /*
   * @testName: getPathInfoNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:547
   * 
   * @test_Strategy:
   */
    /*
   * @testName: getPathInfoTest
   * 
   * @assertion_ids: Servlet:JAVADOC:546; Servlet:SPEC:25;
   * 
   * @test_Strategy: Servlet verifies path info
   */
    /*
   * @testName: getPathTranslatedNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:549
   * 
   * @test_Strategy: Servlet verifies result when there is no path info
   */
    /*
   * @testName: getPathTranslatedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:548
   * 
   * @test_Strategy: client sets extra path info and servlet verifies it
   */
    /*
   * @testName: getQueryStringNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:553
   * 
   * @test_Strategy: Servlet verifies result when no query string exists
   */
    /*
   * @testName: getQueryStringTest
   * 
   * @assertion_ids: Servlet:JAVADOC:552
   * 
   * @test_Strategy: Client sets query string and servlet verifies it
   */
    /*
   * @testName: getRemoteUserTest
   * 
   * @assertion_ids: Servlet:JAVADOC:554
   * 
   * @test_Strategy: Servlet verifies the result of a non-authed user
   */
    /*
   * @testName: getRequestURITest
   * 
   * @assertion_ids: Servlet:JAVADOC:561
   * 
   * @test_Strategy: Servlet verifies URI data
   */
    /*
   * @testName: getRequestURLTest
   * 
   * @assertion_ids: Servlet:JAVADOC:562
   * 
   * @test_Strategy: Servlet verifies URL info
   */
    /*
   * @testName: getRequestedSessionIdNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:560
   * 
   * @test_Strategy: Servlet verifies null result
   */
    /*
   * @testName: getServletPathEmptyStringTest
   * 
   * @assertion_ids: Servlet:JAVADOC:563; Servlet:SPEC:23;
   * 
   * @test_Strategy: Servlet verifies empty string
   */
    /*
   * @testName: getServletPathTest
   * 
   * @assertion_ids: Servlet:JAVADOC:564; Servlet:SPEC:24;
   * 
   * @test_Strategy: Servlet verifies path info
   */
    /*
   * @testName: getSessionTrueTest
   * 
   * @assertion_ids: Servlet:JAVADOC:565
   * 
   * @test_Strategy: Servlet verifies getSession(true) call
   */
    /*
   * @testName: getSessionFalseTest
   * 
   * @assertion_ids: Servlet:JAVADOC:566
   * 
   * @test_Strategy: Servlet verifies getSession(false) call
   */
    /*
   * @testName: getSessionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:567
   * 
   * @test_Strategy: Servlet verifies getSession() call
   */
    /*
   * @testName: isRequestedSessionIdFromCookieTest
   * 
   * @assertion_ids: Servlet:JAVADOC:569
   * 
   * @test_Strategy: Servlet verifies correct result
   */
    /*
   * @testName: isRequestedSessionIdFromURLTest
   * 
   * @assertion_ids: Servlet:JAVADOC:570
   * 
   * @test_Strategy: Servlet verifies correct result
   */
    /*
   * @testName: isRequestedSessionIdValidTest
   * 
   * @assertion_ids: Servlet:JAVADOC:568; Servlet:SPEC:211;
   * 
   * @test_Strategy: Client sends request without session ID; Verifies
   * isRequestedSessionIdValid() returns false;
   */
    /*
   * @testName: getRequestedSessionIdTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:559;
   * 
   * @test_Strategy: Client sends request with a session ID; Verifies
   * getRequestedSessionId() returns the same;
   */
    /*
   * @testName: getRequestedSessionIdTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:559;
   * 
   * @test_Strategy: Client sends request to a servlet with a sesion ID; Servlet
   * start a sesison; Verifies getRequestedSessionId() returns the same;
   */
    /*
   * @testName: sessionTimeoutTest
   * 
   * @assertion_ids: Servlet:SPEC:67;
   * 
   * @test_Strategy: First set a HttpSession's timeout to 60 seconds; then sleep
   * 90 seconds in servlet; verify that the session is still valid after.
   */
    /*
   * @testName: getLocalPortTest
   * 
   * @assertion_ids: Servlet:JAVADOC:630;
   * 
   * @test_Strategy: Send an HttpServletRequest to server; Verify that
   * getLocalPort();
   */
    /*
   * @testName: getServletContextTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Send an HttpServletRequest to server; Verify that
   * getServletContext return the same as stored in ServletConfig
   */
    @Test
    public void getServletContextTest() throws Exception {
        TEST_PROPS.get().setProperty(REQUEST, "GET " + getContextRoot() + "/getServletContextTest HTTP/1.1");
        TEST_PROPS.get().setProperty(UNEXPECTED_RESPONSE_MATCH, "Test FAILED");
        TEST_PROPS.get().setProperty(STATUS_CODE, OK);
        TEST_PROPS.get().setProperty(SEARCH_STRING, "Test PASSED");
        invoke();
    }

    /*
   * @testName: doHeadTest
   * 
   * @assertion_ids:
   * 
   * @test_Strategy: Perform a GET request and a HEAD request for the same
   * resource and confirm that a) HEAD response has no body and b) the header
   * values are the same.
   */
    @Test
    public void doHeadTest() throws Exception {
        HttpExchange requestGet = new HttpExchange("GET " + getContextRoot() + "/doHeadTest HTTP/1.1", _hostname, _port);
        HttpExchange requestHead = new HttpExchange("HEAD " + getContextRoot() + "/doHeadTest HTTP/1.1", _hostname, _port);
        try {
            HttpResponse responseGet = requestGet.execute();
            HttpResponse responseHead = requestHead.execute();
            // Validate the response bodies
            String responseBodyGet = responseGet.getResponseBodyAsString();
            if (responseBodyGet == null || responseBodyGet.isEmpty()) {
                throw new Exception("GET request did not include a response body");
            }
            String responseBodyHead = responseHead.getResponseBodyAsRawString();
            if (responseBodyHead != null && !responseBodyHead.isEmpty()) {
                throw new Exception("HEAD request included a response body");
            }
            // Validate the response headers names
//            Set<String> headersToMatch = new HashSet<>();
//            Map<String, List<String>> headersGet = responseGet.getResponseHeaders();
//            for (Map.Entry<String, List<String>> header : headersGet.entrySet()) {
//                switch(header.getKey().toLowerCase(Locale.ENGLISH)) {
//                    case "date":
//                        // Ignore date header as it will change between requests
//                        break;
//                    default:
//                        headersToMatch.add(header.getKey());
//                }
//            }

            List<Header> headersGet = responseGet.getResponseHeaders();
            Set<String> headersToMatch = headersGet.stream()
                    .map(Header::getName)
                    .filter(name -> !"date".equals(name.toLowerCase(Locale.ENGLISH)))
                    .collect(Collectors.toSet());

            List<Header> headersHead = responseHead.getResponseHeaders();
            for (Header header : headersHead) {
                if ("date".equalsIgnoreCase(header.getName())) {
                    // Skip date header
                    continue;
                }
                if (!headersToMatch.remove(header.getName())) {
                    throw new Exception("HEAD request contained header that was not present for GET: " + header);
                }
            }
            if (!headersToMatch.isEmpty()) {
                throw new Exception("HEAD request did not contain header that was present for GET:" + headersToMatch.iterator().next());
            }
        } catch (Throwable t) {
            throw new Exception("Exception occurred:" + t, t);
        }
    }

    // -------------------------- END HttpServletRequest
    // ---------------------------
    @Test()
    public void getAuthTypeWithoutProtectionTest() throws Exception {
        super.getAuthTypeWithoutProtectionTest();
    }

    @Test()
    public void getContentLengthTest() throws Exception {
        super.getContentLengthTest();
    }

    @Test()
    public void getContentTypeTest() throws Exception {
        super.getContentTypeTest();
    }

    @Test()
    public void getContextPathTest() throws Exception {
        super.getContextPathTest();
    }

    @Test()
    public void getCookiesNoCookiesTest() throws Exception {
        super.getCookiesNoCookiesTest();
    }

    @Test()
    public void getCookiesTest() throws Exception {
        super.getCookiesTest();
    }

    @Test()
    public void getDateHeaderIllegalArgumentExceptionTest() throws Exception {
        super.getDateHeaderIllegalArgumentExceptionTest();
    }

    @Test()
    public void getDateHeaderNoHeaderTest() throws Exception {
        super.getDateHeaderNoHeaderTest();
    }

    @Test()
    public void getDateHeaderTest() throws Exception {
        super.getDateHeaderTest();
    }

    @Test()
    public void getHeaderNamesTest() throws Exception {
        super.getHeaderNamesTest();
    }

    @Test()
    public void getHeaderNoHeaderTest() throws Exception {
        super.getHeaderNoHeaderTest();
    }

    @Test()
    public void getHeaderTest() throws Exception {
        super.getHeaderTest();
    }

    @Test()
    public void getHeadersNoHeadersTest() throws Exception {
        super.getHeadersNoHeadersTest();
    }

    @Test()
    public void getHeadersTest() throws Exception {
        super.getHeadersTest();
    }

    @Test()
    public void getInputStreamIllegalStateExceptionTest() throws Exception {
        super.getInputStreamIllegalStateExceptionTest();
    }

    @Test()
    public void getInputStreamTest() throws Exception {
        super.getInputStreamTest();
    }

    @Test()
    public void getIntHeaderNoHeaderTest() throws Exception {
        super.getIntHeaderNoHeaderTest();
    }

    @Test()
    public void getIntHeaderNumberFoundExceptionTest() throws Exception {
        super.getIntHeaderNumberFoundExceptionTest();
    }

    @Test()
    public void getIntHeaderTest() throws Exception {
        super.getIntHeaderTest();
    }

    @Test()
    public void getLocalAddrTest() throws Exception {
        super.getLocalAddrTest();
    }

    @Test()
    public void getLocalPortTest() throws Exception {
        super.getLocalPortTest();
    }

    @Test()
    public void getMethodTest() throws Exception {
        super.getMethodTest();
    }

    @Test()
    public void getParameterMapTest() throws Exception {
        super.getParameterMapTest();
    }

    @Test()
    public void getPathInfoNullTest() throws Exception {
        super.getPathInfoNullTest();
    }

    @Test()
    public void getPathInfoTest() throws Exception {
        super.getPathInfoTest();
    }

    @Test()
    public void getPathTranslatedNullTest() throws Exception {
        super.getPathTranslatedNullTest();
    }

    @Test()
    public void getPathTranslatedTest() throws Exception {
        super.getPathTranslatedTest();
    }

    @Test()
    public void getProtocolTest() throws Exception {
        super.getProtocolTest();
    }

    @Test()
    public void getQueryStringNullTest() throws Exception {
        super.getQueryStringNullTest();
    }

    @Test()
    public void getQueryStringTest() throws Exception {
        super.getQueryStringTest();
    }

    @Test()
    public void getReaderIllegalStateExceptionTest() throws Exception {
        super.getReaderIllegalStateExceptionTest();
    }

    @Test()
    public void getReaderTest() throws Exception {
        super.getReaderTest();
    }

    @Test()
    public void getReaderUnsupportedEncodingExceptionTest() throws Exception {
        super.getReaderUnsupportedEncodingExceptionTest();
    }

    @Test()
    public void getRemoteAddrTest() throws Exception {
        super.getRemoteAddrTest();
    }

    @Test()
    public void getRemoteHostTest() throws Exception {
        super.getRemoteHostTest();
    }

    @Test()
    public void getRemoteUserTest() throws Exception {
        super.getRemoteUserTest();
    }

    @Test()
    public void getRequestDispatcherTest() throws Exception {
        super.getRequestDispatcherTest();
    }

    @Test()
    public void getRequestURITest() throws Exception {
        super.getRequestURITest();
    }

    @Test()
    public void getRequestURLTest() throws Exception {
        super.getRequestURLTest();
    }

    @Test()
    public void getRequestedSessionIdNullTest() throws Exception {
        super.getRequestedSessionIdNullTest();
    }

    @Test()
    public void getRequestedSessionIdTest1() throws Exception {
        super.getRequestedSessionIdTest1();
    }

    @Test()
    public void getRequestedSessionIdTest2() throws Exception {
        super.getRequestedSessionIdTest2();
    }

    @Test()
    public void getSchemeTest() throws Exception {
        super.getSchemeTest();
    }

    @Test()
    public void getServerNameTest() throws Exception {
        super.getServerNameTest();
    }

    @Test()
    public void getServerPortTest() throws Exception {
        super.getServerPortTest();
    }

    @Test()
    public void getServletPathEmptyStringTest() throws Exception {
        super.getServletPathEmptyStringTest();
    }

    @Test()
    public void getServletPathTest() throws Exception {
        super.getServletPathTest();
    }

    @Test()
    public void getSessionFalseTest() throws Exception {
        super.getSessionFalseTest();
    }

    @Test()
    public void getSessionTest() throws Exception {
        super.getSessionTest();
    }

    @Test()
    public void getSessionTrueTest() throws Exception {
        super.getSessionTrueTest();
    }

    @Test()
    public void isRequestedSessionIdFromCookieTest() throws Exception {
        super.isRequestedSessionIdFromCookieTest();
    }

    @Test()
    public void isRequestedSessionIdFromURLTest() throws Exception {
        super.isRequestedSessionIdFromURLTest();
    }

    @Test()
    public void isRequestedSessionIdValidTest() throws Exception {
        super.isRequestedSessionIdValidTest();
    }

    @Test()
    public void isSecureTest() throws Exception {
        super.isSecureTest();
    }

    @Test()
    public void removeAttributeTest() throws Exception {
        super.removeAttributeTest();
    }

    @Test()
    public void sessionTimeoutTest() throws Exception {
        super.sessionTimeoutTest();
    }

    @Test()
    public void setAttributeTest() throws Exception {
        super.setAttributeTest();
    }

    @Test()
    public void setCharacterEncodingTest() throws Exception {
        super.setCharacterEncodingTest();
    }

    @Test()
    public void setCharacterEncodingTest1() throws Exception {
        super.setCharacterEncodingTest1();
    }

    @Test()
    public void setCharacterEncodingUnsupportedEncodingExceptionTest() throws Exception {
        super.setCharacterEncodingUnsupportedEncodingExceptionTest();
    }
}
