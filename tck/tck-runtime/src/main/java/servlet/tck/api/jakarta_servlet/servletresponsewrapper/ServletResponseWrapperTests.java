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
 * $URL$ $LastChangedDate$
 */
package servlet.tck.api.jakarta_servlet.servletresponsewrapper;

import servlet.tck.common.response.HttpResponseClient;
import servlet.tck.common.response.ResponseTestServlet;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletResponseWrapperTests extends HttpResponseClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_js_servletresponsewrapper_web.war").addAsLibraries(CommonServlets.getCommonServletsArchive()).addClasses(SetCharacterEncodingTestServlet.class, TestServlet.class, ResponseTestServlet.class).setWebXML(ServletResponseWrapperTests.class.getResource("servlet_js_servletresponsewrapper_web.xml"));
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    /*
   * @testName: responseWrapperConstructorTest
   * 
   * @assertion_ids: Servlet:JAVADOC:9
   * 
   * @test_Strategy: Servlet calls wrapper constructor
   */
    @Test
    public void responseWrapperConstructorTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "responseWrapperConstructorTest");
        invoke();
    }

    /*
   * @testName: responseWrapperGetResponseTest
   * 
   * @assertion_ids: Servlet:JAVADOC:10
   * 
   * @test_Strategy: Servlet gets wrapped response object
   */
    @Test
    public void responseWrapperGetResponseTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "responseWrapperGetResponseTest");
        invoke();
    }

    /*
   * @testName: responseWrapperSetResponseTest
   * 
   * @assertion_ids: Servlet:JAVADOC:11
   * 
   * @test_Strategy: Servlet sets wrapped response object
   */
    @Test
    public void responseWrapperSetResponseTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "responseWrapperSetResponseTest");
        invoke();
    }

    /*
   * @testName: responseWrapperSetResponseIllegalArgumentExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:12
   * 
   * @test_Strategy: Servlet sets wrapped response object
   */
    @Test
    public void responseWrapperSetResponseIllegalArgumentExceptionTest() throws Exception {
        TEST_PROPS.get().setProperty(APITEST, "responseWrapperSetResponseIllegalArgumentExceptionTest");
        invoke();
    }

    /*
   * @testName: flushBufferTest
   * 
   * @assertion_ids: Servlet:JAVADOC:24
   * 
   * @test_Strategy: Servlet wraps response. Servlet writes data in the buffer
   * and flushes it
   */
    /*
   * @testName: getBufferSizeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:23
   * 
   * @test_Strategy: Servlet wraps response. Servlet flushes buffer and verifies
   * the size of the buffer
   */
    /*
   * @testName: getLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:30
   * 
   * @test_Strategy: Servlet wraps response. Servlet set Locale and then
   * verifies it
   *
   */
    /*
   * @testName: getOutputStreamTest
   * 
   * @assertion_ids: Servlet:JAVADOC:15
   * 
   * @test_Strategy: Servlet wraps response. Servlet gets an output stream and
   * writes to it.
   */
    /*
   * @testName: getWriterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:17
   * 
   * @test_Strategy: Servlet wraps response. Servlet gets a Writer object, then
   * sets the content type; Verify that content type didn't get set by servlet
   */
    /*
   * @testName: isCommittedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:26
   * 
   * @test_Strategy: Servlet wraps response. Servlet checks before and after
   * response is flushed
   *
   */
    /*
   * @testName: resetBufferTest
   * 
   * @assertion_ids: Servlet:JAVADOC:28
   * 
   * @test_Strategy: Servlet wraps response. Servlet writes data to the
   * response, resets the buffer and then writes new data
   */
    /*
   * @testName: resetTest
   * 
   * @assertion_ids: Servlet:JAVADOC:27
   * 
   * @test_Strategy: Servlet wraps response. Servlet writes data to the
   * response, does a reset, then writes new data
   */
    /*
   * @testName: resetTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:27; Servlet:JAVADOC:162; Servlet:SPEC:31;
   * 
   * @test_Strategy: Servlet writes data to the response, set the Headers, does
   * a reset, then writes new data, set the new Header
   */
    /*
   * @testName: getCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:14
   * 
   * @test_Strategy: Servlet wraps response. Servlet checks for the default
   * encoding
   */
    /*
   * @testName: setCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:13
   * 
   * @test_Strategy: Servlet wraps response. Servlet set the encoding and client
   * verifies it
   */
    /*
   * @testName: setBufferSizeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:22
   * 
   * @test_Strategy: Servlet wraps response. Servlet sets the buffer size then
   * verifies it was set
   */
    /*
   * @testName: setContentLengthTest
   * 
   * @assertion_ids: Servlet:JAVADOC:19
   * 
   * @test_Strategy: Servlet wraps response. Servlet sets the content length
   */
    /*
   * @testName: getContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:21; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet wraps response. Servlet verifies the content type
   * sent by the client
   */
    /*
   * @testName: setContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:20; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet wraps response. Servlet sets the content type
   *
   */
    /*
   * @testName: setLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:29
   * 
   * @test_Strategy: Servlet wraps response. Servlet sets the Locale
   */
    @Test()
    public void flushBufferTest() throws Exception {
        super.flushBufferTest();
    }

    @Test()
    public void getBufferSizeTest() throws Exception {
        super.getBufferSizeTest();
    }

    @Test()
    public void getCharacterEncodingTest() throws Exception {
        super.getCharacterEncodingTest();
    }

    @Test()
    public void getContentTypeTest() throws Exception {
        super.getContentTypeTest();
    }

    @Test()
    public void getLocaleTest() throws Exception {
        super.getLocaleTest();
    }

    @Test()
    public void getOutputStreamTest() throws Exception {
        super.getOutputStreamTest();
    }

    @Test()
    public void getWriterTest() throws Exception {
        super.getWriterTest();
    }

    @Test()
    public void isCommittedTest() throws Exception {
        super.isCommittedTest();
    }

    @Test()
    public void resetBufferTest() throws Exception {
        super.resetBufferTest();
    }

    @Test()
    public void resetTest() throws Exception {
        super.resetTest();
    }

    @Test()
    public void resetTest1() throws Exception {
        super.resetTest1();
    }

    @Test()
    public void setBufferSizeTest() throws Exception {
        super.setBufferSizeTest();
    }

    @Test()
    public void setCharacterEncodingTest() throws Exception {
        super.setCharacterEncodingTest();
    }

    @Test()
    public void setContentLengthTest() throws Exception {
        super.setContentLengthTest();
    }

    @Test()
    public void setContentTypeTest() throws Exception {
        super.setContentTypeTest();
    }

    @Test()
    public void setLocaleTest() throws Exception {
        super.setLocaleTest();
    }
}
