/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.pluggability.api.jakarta_servlet_http.httpservletrequestwrapper;

import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.SetCharacterEncodingTest;
import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.SetCharacterEncodingTestWrapper;
import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.SetCharacterEncodingUnsupportedEncodingExceptionTest;
import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.SetCharacterEncodingUnsupportedEncodingExceptionTestWrapper;
import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.TCKHttpSessionIDListener;
import servlet.tck.api.jakarta_servlet_http.httpservletrequestwrapper.TestServlet;
import servlet.tck.common.request.HttpRequestClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HttpServletRequestWrapperTests extends HttpRequestClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar").addClasses(TestServlet1.class, RequestListener1.class).addAsResource(HttpServletRequestWrapperTests.class.getResource("servlet_pluh_HSReqWrapper_web-fragment.xml"), "META-INF/web-fragment.xml");
        return ShrinkWrap.create(WebArchive.class, "servlet_pluh_HSReqWrapper_web.war").addAsLibraries(CommonServlets.getCommonServletsArchive()).addClasses(SetCharacterEncodingTest.class, SetCharacterEncodingTestWrapper.class, SetCharacterEncodingUnsupportedEncodingExceptionTest.class, SetCharacterEncodingUnsupportedEncodingExceptionTestWrapper.class, TCKHttpSessionIDListener.class, TestServlet.class).addAsLibraries(javaArchive);
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    // --------------------------- ServletRequestWrapper
    // ---------------------------
    /*
   * @testName: getAttributeNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:385
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then sets some
   * attributes and verifies they can be retrieved.
   */
    /*
   * @testName: getAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:384
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then sets an attribute
   * and retrieves it.
   */
    /*
   * @testName: getCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:386
   * 
   * @test_Strategy: Client sets an encoding. Servlet wraps the request. Servlet
   * then tries to retrieve it.
   */
    /*
   * @testName: getContentLengthTest
   * 
   * @assertion_ids: Servlet:JAVADOC:389
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then compares this
   * length to the actual length of the content body read in using
   * getInputStream
   */
    /*
   * @testName: getContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:390; Servlet:SPEC:34;
   * 
   * @test_Strategy: Client sets the content type. Servlet wraps the request.
   * Servlet reads it from wrapped request.
   *
   */
    /*
   * @testName: getInputStreamTest
   * 
   * @assertion_ids: Servlet:JAVADOC:391
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then tries to read the
   * input stream.
   */
    /*
   * @testName: getLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:407
   * 
   * @test_Strategy: Client specifics a locale, Servlet wraps the request.
   * Servlet then verifies it.
   */
    /*
   * @testName: getLocalesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:408
   * 
   * @test_Strategy: Client specifics 2 locales.Servlet wraps the request.
   * Servlet then verifies it.
   */
    /*
   * @testName: getParameterMapTest
   * 
   * @assertion_ids: Servlet:JAVADOC:394
   * 
   * @test_Strategy: Client sets several parameters.Servlet wraps the request.
   * Servlet then attempts to access them.
   */
    /*
   * @testName: getParameterNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:395
   * 
   * @test_Strategy: Client sets several parameters.Servlet wraps the request.
   * Servlet then attempts to access them.
   */
    /*
   * @testName: getParameterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:393
   * 
   * @test_Strategy: Client sets a parameter.Servlet wraps the request. Servlet
   * then retrieves parameter.
   */
    /*
   * @testName: getParameterValuesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:396
   * 
   * @test_Strategy: Client sets a parameter which has 2 values.Servlet wraps
   * the request. Servlet then verifies both values.
   */
    /*
   * @testName: getProtocolTest
   * 
   * @assertion_ids: Servlet:JAVADOC:397
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then verifies the
   * protocol used by the client
   */
    /*
   * @testName: getReaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:401
   * 
   * @test_Strategy: Client sets some content.Servlet wraps the request. Servlet
   * then reads the content
   */
    /*
   * @testName: getRemoteAddrTest
   * 
   * @assertion_ids: Servlet:JAVADOC:403
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then reads and verifies
   * where the request originated
   */
    /*
   * @testName: getRemoteHostTest
   * 
   * @assertion_ids: Servlet:JAVADOC:404
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then reads and verifies
   * where the request originated
   */
    /*
   * @testName: getRequestDispatcherTest
   * 
   * @assertion_ids: Servlet:JAVADOC:410
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then tries to get a
   * dispatcher
   */
    /*
   * @testName: getSchemeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:398
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then verifies the scheme
   * of the url used in the request
   */
    /*
   * @testName: getServerNameTest
   * 
   * @assertion_ids: Servlet:JAVADOC:399
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then verifies the
   * destination of the request
   */
    /*
   * @testName: getServerPortTest
   * 
   * @assertion_ids: Servlet:JAVADOC:400
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then verifies the
   * destination port of the request
   */
    /*
   * @testName: isSecureTest
   * 
   * @assertion_ids: Servlet:JAVADOC:409
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then verifies the
   * isSecure method for the non-secure case.
   */
    /*
   * @testName: removeAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:406
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then adds then removes
   * an attribute, then verifies it was removed.
   */
    /*
   * @testName: setAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:405
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then adds an attribute,
   * then verifies it was added
   */
    /*
   * @testName: setCharacterEncodingUnsupportedEncodingExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:388
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then tries to set an
   * invalid encoding.
   */
    /*
   * @testName: setCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:387
   * 
   * @test_Strategy: Servlet wraps the request. Servlet then sets a new encoding
   * and tries to retrieve it.
   */
    /*
   * @testName: setCharacterEncodingTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:387; Servlet:JAVADOC:386; Servlet:SPEC:28;
   * Servlet:SPEC:213;
   * 
   * @test_Strategy: Servlet wraps the HttpServletRequest. HttpServletRequest
   * calls getReader(); then sets a new encoding and tries to retrieve it.
   * verifies that the new encoding is ignored.
   */
    // ---------------------- END ServletRequestWrapper
    // ----------------------------
    // ------------------------ HttpServletRequestWrapper
    // --------------------------
    /*
   * @testName: httpRequestWrapperConstructorTest
   * 
   * @assertion_ids: Servlet:JAVADOC:355
   * 
   * @test_Strategy: Validate an IllegalArgumentException is thrown is a null
   * request is passed to the Wrapper's constructor.
   */
    @Test
    public void httpRequestWrapperConstructorTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "httpRequestWrapperConstructorTest");
        invoke();
    }

    /*
   * @testName: httpRequestWrapperConstructorIllegalArgumentExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:626
   * 
   * @test_Strategy: Validate an IllegalArgumentException is thrown is a null
   * request is passed to the Wrapper's constructor.
   */
    @Test
    public void httpRequestWrapperConstructorIllegalArgumentExceptionTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "httpRequestWrapperConstructorIllegalArgumentExceptionTest");
        invoke();
    }

    /*
   * @testName: getAuthTypeWithoutProtectionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:356
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies correct result
   */
    /*
   * @testName: getContextPathTest
   * 
   * @assertion_ids: Servlet:JAVADOC:366
   * 
   * @test_Strategy: Client sets header and servlet verifies the result
   */
    /*
   * @testName: getCookiesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:357
   * 
   * @test_Strategy:Client sets a cookie and servlet tries to read it
   */
    /*
   * @testName: getDateHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:358
   * 
   * @test_Strategy: client sets a dateheader and servlet tries to read it.
   */
    /*
   * @testName: getHeaderNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:361
   * 
   * @test_Strategy: Client sets some headers and servlet tries to read them
   */
    /*
   * @testName: getHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:359
   * 
   * @test_Strategy: Client sets a header and servlet tries to read it.
   */
    /*
   * @testName: getHeadersTest
   * 
   * @assertion_ids: Servlet:JAVADOC:360
   * 
   * @test_Strategy: Client sets some headers and servlet tries to read them
   */
    /*
   * @testName: getIntHeaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:362
   * 
   * @test_Strategy: Client sets a header and servlet reads it
   */
    /*
   * @testName: getMethodTest
   * 
   * @assertion_ids: Servlet:JAVADOC:363
   * 
   * @test_Strategy: Client makes 3 calls using GET/POST/HEAD
   */
    /*
   * @testName: getPathInfoTest
   * 
   * @assertion_ids: Servlet:JAVADOC:364; Servlet:SPEC:25;
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies path info
   */
    /*
   * @testName: getPathTranslatedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:365
   * 
   * @test_Strategy: client sets extra path info and servlet verifies it
   */
    /*
   * @testName: getQueryStringTest
   * 
   * @assertion_ids: Servlet:JAVADOC:367
   * 
   * @test_Strategy: Client sets query string and servlet verifies it
   */
    /*
   * @testName: getRemoteUserTest
   * 
   * @assertion_ids: Servlet:JAVADOC:368
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies the result of a
   * non-authed user
   */
    /*
   * @testName: getRequestURITest
   * 
   * @assertion_ids: Servlet:JAVADOC:372
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies URI data
   */
    /*
   * @testName: getRequestURLTest
   * 
   * @assertion_ids: Servlet:JAVADOC:373
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies URL info
   */
    /*
   * @testName: getRequestedSessionIdNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:371
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies null result
   */
    /*
   * @testName: getServletPathTest
   * 
   * @assertion_ids: Servlet:JAVADOC:374; Servlet:SPEC:24;
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies path info
   */
    /*
   * @testName: getSessionTrueTest
   * 
   * @assertion_ids: Servlet:JAVADOC:375
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies
   * getSession(boolean) call
   */
    /*
   * @testName: getSessionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:376
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies getSession()
   * call
   */
    /*
   * @testName: isRequestedSessionIdFromCookieTest
   * 
   * @assertion_ids: Servlet:JAVADOC:378
   * 
   * @test_Strategy: Access Servlet through URL; Servlet wraps the request;
   * Servlet verifies API isRequestedSessionIdFromCookie return false; Negative
   * test
   */
    /*
   * @testName: isRequestedSessionIdFromCookieTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:378
   * 
   * @test_Strategy: Access Servlet through URL; Servlet wraps the request;
   * Servlet starts a HttpSession; Client saves SessionID from Server and use it
   * to access Servlet again; Servlet verifies API
   * isRequestedSessionIdFromCookie return true; Positive test
   */
    /*
   * @testName: isRequestedSessionIdFromURLTest
   * 
   * @assertion_ids: Servlet:JAVADOC:379
   * 
   * @test_Strategy: Servlet wraps the request. Servlet verifies correct result
   */
    /*
   * @testName: isRequestedSessionIdValidTest
   * 
   * @assertion_ids: Servlet:JAVADOC:377; Servlet:SPEC:211;
   * 
   * @test_Strategy: Client sends request without session ID; Servlet wraps the
   * request; Verifies isRequestedSessionIdValid() returns false;
   */
    /*
   * @testName: getRequestedSessionIdTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:371;
   * 
   * @test_Strategy: Client sends request with a session ID; Verifies
   * getRequestedSessionId() returns the same;
   */
    /*
   * @testName: getRequestedSessionIdTest2
   * 
   * @assertion_ids: Servlet:JAVADOC:371;
   * 
   * @test_Strategy: Client sends request to a servlet with a session ID; Servlet
   * start a sesison; Verifies getRequestedSessionId() returns the same;
   */
    /*
   * @testName: getLocalPortTest
   *
   * @assertion_ids: Servlet:JAVADOC:631;
   *
   * @test_Strategy: Send an HttpServletRequestWrapper to server; Test Servlet
   * API getLocalPort();
   */
    /*
   * @testName: getLocalNameTest
   *
   * @assertion_ids: Servlet:JAVADOC:634;
   *
   * @test_Strategy: Send an HttpServletRequestWrapper to server; Test Servlet
   * API getLocalName();
   */
    /*
   * @testName: httpRequestWrapperGetRequestTest
   * 
   * @assertion_ids: Servlet:JAVADOC:381
   * 
   * @test_Strategy: Servlet gets wrapped response object
   */
    @Test
    public void httpRequestWrapperGetRequestTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "httpRequestWrapperGetRequestTest");
        invoke();
    }

    /*
   * @testName: httpRequestWrapperSetRequestTest
   * 
   * @assertion_ids: Servlet:JAVADOC:382
   * 
   * @test_Strategy: Servlet sets wrapped response object
   */
    @Test
    public void httpRequestWrapperSetRequestTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "httpRequestWrapperSetRequestTest");
        invoke();
    }

    /*
   * @testName: httpRequestWrapperSetRequestIllegalArgumentExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:383
   * 
   * @test_Strategy: Servlet sets wrapped response object
   */
    @Test
    public void httpRequestWrapperSetRequestIllegalArgumentExceptionTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "httpRequestWrapperSetRequestIllegalArgumentExceptionTest");
        invoke();
    }

    // ---------------------- END HttpServletRequestWrapper
    // ------------------------
    @Test()
    public void getAttributeNamesTest() throws Exception {
        super.getAttributeNamesTest();
    }

    @Test()
    public void getAttributeTest() throws Exception {
        super.getAttributeTest();
    }

    @Test()
    public void getAuthTypeWithoutProtectionTest() throws Exception {
        super.getAuthTypeWithoutProtectionTest();
    }

    @Test()
    public void getCharacterEncodingTest() throws Exception {
        super.getCharacterEncodingTest();
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
    public void getCookiesTest() throws Exception {
        super.getCookiesTest();
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
    public void getHeaderTest() throws Exception {
        super.getHeaderTest();
    }

    @Test()
    public void getHeadersTest() throws Exception {
        super.getHeadersTest();
    }

    @Test()
    public void getInputStreamTest() throws Exception {
        super.getInputStreamTest();
    }

    @Test()
    public void getIntHeaderTest() throws Exception {
        super.getIntHeaderTest();
    }

    @Test()
    public void getLocalNameTest() throws Exception {
        super.getLocalNameTest();
    }

    @Test()
    public void getLocalPortTest() throws Exception {
        super.getLocalPortTest();
    }

    @Test()
    public void getLocaleTest() throws Exception {
        super.getLocaleTest();
    }

    @Test()
    public void getLocalesTest() throws Exception {
        super.getLocalesTest();
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
    public void getParameterNamesTest() throws Exception {
        super.getParameterNamesTest();
    }

    @Test()
    public void getParameterTest() throws Exception {
        super.getParameterTest();
    }

    @Test()
    public void getParameterValuesTest() throws Exception {
        super.getParameterValuesTest();
    }

    @Test()
    public void getPathInfoTest() throws Exception {
        super.getPathInfoTest();
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
    public void getQueryStringTest() throws Exception {
        super.getQueryStringTest();
    }

    @Test()
    public void getReaderTest() throws Exception {
        super.getReaderTest();
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
    public void getServletPathTest() throws Exception {
        super.getServletPathTest();
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
    public void isRequestedSessionIdFromCookieTest1() throws Exception {
        super.isRequestedSessionIdFromCookieTest1();
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
