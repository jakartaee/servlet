/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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
package com.sun.ts.tests.servlet.api.jakarta_servlet.servletrequest;

import com.sun.ts.tests.servlet.common.request.RequestClient;
import com.sun.ts.tests.servlet.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends RequestClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_js_servletrequest_web.war").addAsLibraries(CommonServlets.getCommonServletsArchive()).addClasses(GetParameterNamesEmptyEnumTestServlet.class, GetReaderUnsupportedEncodingExceptionTestServlet.class, SetCharacterEncodingTest.class, SetCharacterEncodingUnsupportedEncodingExceptionTest.class).setWebXML(URLClient.class.getResource("servlet_js_servletrequest_web.xml"));
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    /*
   * @testName: getAttributeNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:174
   * 
   * @test_Strategy: Servlet sets some attributes and verifies they can be
   * retrieved.
   *
   */
    /*
   * @testName: getAttributeNamesEmptyEnumTest
   * 
   * @assertion_ids: Servlet:JAVADOC:175
   * 
   * @test_Strategy: No attributes exist in request.
   */
    /*
   * @testName: getAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:172
   * 
   * @test_Strategy: Servlet sets an attribute and retrieves it.
   */
    /*
   * @testName: getAttributeDoesNotExistTest
   * 
   * @assertion_ids: Servlet:JAVADOC:173
   * 
   * @test_Strategy: Servlet tries to retrieve a non-existant attribute.
   *
   */
    /*
   * @testName: getCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:176
   * 
   * @test_Strategy: Client sets an encoding and Servlet tries to retrieve it.
   */
    /*
   * @testName: getCharacterEncodingNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:177
   * 
   * @test_Strategy: Client does not set an encoding and Servlet tries to
   * retrieve it.
   *
   */
    /*
   * @testName: getContentLengthTest
   * 
   * @assertion_ids: Servlet:JAVADOC:180
   * 
   * @test_Strategy: Servlet compares this length to the actual length of the
   * content body read in using getInputStream
   *
   */
    /*
   * @testName: getContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:182; Servlet:SPEC:34;
   * 
   * @test_Strategy: Client sets the content type and servlet reads it.
   */
    /*
   * @testName: getContentTypeNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:183; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet tries to read content type.
   */
    /*
   * @testName: getInputStreamTest
   * 
   * @assertion_ids: Servlet:JAVADOC:184
   * 
   * @test_Strategy: Servlet tries to read the input stream.
   */
    /*
   * @testName: getInputStreamIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:186
   * 
   * @test_Strategy: Servlet gets a Reader object using
   * ServletRequest.getReader() then tries to get the inputStream Object
   *
   */
    /*
   * @testName: getLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:206
   * 
   * @test_Strategy: Client specifics a locale and the servlet verifies it.
   */
    /*
   * @testName: getLocaleDefaultTest
   * 
   * @assertion_ids: Servlet:JAVADOC:207
   * 
   * @test_Strategy: Client does not specify a locale and the servlet verifies
   * the default.
   */
    /*
   * @testName: getLocalesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:208
   * 
   * @test_Strategy: Client specifics 2 locales and the servlet verifies it.
   */
    /*
   * @testName: getLocalesDefaultTest
   * 
   * @assertion_ids: Servlet:JAVADOC:209
   * 
   * @test_Strategy: Client does not specify a locale and the servlet verifies
   * the default.
   */
    /*
   * @testName: getParameterMapTest
   * 
   * @assertion_ids: Servlet:JAVADOC:193
   * 
   * @test_Strategy: Client sets several parameters and the servlet attempts to
   * access them.
   */
    /*
   * @testName: getParameterNamesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:189
   * 
   * @test_Strategy: Client sets several parameters and the servlet attempts to
   * access them.
   */
    /*
   * @testName: getParameterNamesEmptyEnumTest
   * 
   * @assertion_ids: Servlet:JAVADOC:190
   * 
   * @test_Strategy: Client does not set any parameters and the servlet attempts
   * to access them.
   */
    /*
   * @testName: getParameterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:187
   * 
   * @test_Strategy: Client sets a parameter and servlet retrieves it.
   */
    /*
   * @testName: getParameterDoesNotExistTest
   * 
   * @assertion_ids: Servlet:JAVADOC:188
   * 
   * @test_Strategy: Servlet tries to access a non-existent parameter
   */
    /*
   * @testName: getParameterValuesTest
   * 
   * @assertion_ids: Servlet:JAVADOC:191
   * 
   * @test_Strategy: Client sets a parameter which has 2 values and servlet
   * verifies boths values.
   */
    /*
   * @testName: getParameterValuesDoesNotExistTest
   * 
   * @assertion_ids: Servlet:JAVADOC:192
   * 
   * @test_Strategy: Servlet tries to retrieve a parameter that does not exist
   */
    /*
   * @testName: getProtocolTest
   * 
   * @assertion_ids: Servlet:JAVADOC:194
   * 
   * @test_Strategy: Servlet verifies the protocol used by the client
   */
    /*
   * @testName: getReaderTest
   * 
   * @assertion_ids: Servlet:JAVADOC:198
   * 
   * @test_Strategy: Client sets some content and servlet reads the content
   */
    /*
   * @testName: getReaderIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:201
   * 
   * @test_Strategy: Servlet gets an InputStream Object then tries to get a
   * Reader Object.
   */
    /*
   * @testName: getReaderUnsupportedEncodingExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:200
   * 
   * @test_Strategy: Client sets some content but with an invalid encoding,
   * servlet tries to read content.
   */
    /*
   * @testName: getRemoteAddrTest
   * 
   * @assertion_ids: Servlet:JAVADOC:202
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getLocalAddrTest
   * 
   * @assertion_ids: Servlet:JAVADOC:704
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getRemoteHostTest
   * 
   * @assertion_ids: Servlet:JAVADOC:203;
   * 
   * @test_Strategy: Servlet reads and verifies where the request originated
   */
    /*
   * @testName: getRequestDispatcherTest
   * 
   * @assertion_ids: Servlet:JAVADOC:211
   * 
   * @test_Strategy: Servlet tries to get a dispatcher
   */
    /*
   * @testName: getSchemeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:195
   * 
   * @test_Strategy: Servlet verifies the scheme of the url used in the request
   */
    /*
   * @testName: getServerNameTest
   * 
   * @assertion_ids: Servlet:JAVADOC:196
   * 
   * @test_Strategy: Servlet verifies the destination of the request
   */
    /*
   * @testName: getServerPortTest
   * 
   * @assertion_ids: Servlet:JAVADOC:197
   * 
   * @test_Strategy: Servlet verifies the destination port of the request
   */
    /*
   * @testName: isSecureTest
   * 
   * @assertion_ids: Servlet:JAVADOC:210
   * 
   * @test_Strategy: Servlet verifies the isSecure method for the non-secure
   * case.
   */
    /*
   * @testName: removeAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:205
   * 
   * @test_Strategy: Servlet adds then removes an attribute, then verifies it
   * was removed.
   */
    /*
   * @testName: setAttributeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:204
   * 
   * @test_Strategy: Servlet adds an attribute, then verifies it was added
   */
    /*
   * @testName: setCharacterEncodingUnsupportedEncodingExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:179
   * 
   * @test_Strategy: Servlet tries to set an invalid encoding.
   *
   */
    /*
   * @testName: setCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:178
   * 
   * @test_Strategy: Servlet sets a new encoding and tries to retrieve it.
   */
    /*
   * @testName: setCharacterEncodingTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:178; Servlet:JAVADOC:177; Servlet:SPEC:28;
   * Servlet:SPEC:213;
   * 
   * @test_Strategy: ServletRequest calls getReader()first; then sets a new
   * encoding and tries to retrieve it. verifies that the new encoding is
   * ignored.
   */
    @Test()
    public void getAttributeDoesNotExistTest() throws Exception {
        super.getAttributeDoesNotExistTest();
    }

    @Test()
    public void getAttributeNamesEmptyEnumTest() throws Exception {
        super.getAttributeNamesEmptyEnumTest();
    }

    @Test()
    public void getAttributeNamesTest() throws Exception {
        super.getAttributeNamesTest();
    }

    @Test()
    public void getAttributeTest() throws Exception {
        super.getAttributeTest();
    }

    @Test()
    public void getCharacterEncodingNullTest() throws Exception {
        super.getCharacterEncodingNullTest();
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
    public void getContentTypeNullTest() throws Exception {
        super.getContentTypeNullTest();
    }

    @Test()
    public void getContentTypeTest() throws Exception {
        super.getContentTypeTest();
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
    public void getLocalAddrTest() throws Exception {
        super.getLocalAddrTest();
    }

    @Test()
    public void getLocaleDefaultTest() throws Exception {
        super.getLocaleDefaultTest();
    }

    @Test()
    public void getLocaleTest() throws Exception {
        super.getLocaleTest();
    }

    @Test()
    public void getLocalesDefaultTest() throws Exception {
        super.getLocalesDefaultTest();
    }

    @Test()
    public void getLocalesTest() throws Exception {
        super.getLocalesTest();
    }

    @Test()
    public void getParameterDoesNotExistTest() throws Exception {
        super.getParameterDoesNotExistTest();
    }

    @Test()
    public void getParameterMapTest() throws Exception {
        super.getParameterMapTest();
    }

    @Test()
    public void getParameterNamesEmptyEnumTest() throws Exception {
        super.getParameterNamesEmptyEnumTest();
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
    public void getParameterValuesDoesNotExistTest() throws Exception {
        super.getParameterValuesDoesNotExistTest();
    }

    @Test()
    public void getParameterValuesTest() throws Exception {
        super.getParameterValuesTest();
    }

    @Test()
    public void getProtocolTest() throws Exception {
        super.getProtocolTest();
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
    public void getRequestDispatcherTest() throws Exception {
        super.getRequestDispatcherTest();
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
