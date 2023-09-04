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
package servlet.tck.pluggability.api.jakarta_servlet.servletresponse;

import servlet.tck.api.jakarta_servlet.servletresponse.SetCharacterEncodingTestServlet;
import servlet.tck.common.response.ResponseClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.servlets.GenericCheckTestResultServlet;
import servlet.tck.pluggability.common.RequestListener1;
import servlet.tck.pluggability.common.TestServlet1;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServletResponseTests extends ResponseClient {

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        JavaArchive javaArchive1 = ShrinkWrap.create(JavaArchive.class, "fragment-1.jar").addClasses(TestServlet1.class, RequestListener1.class).addAsResource(ServletResponseTests.class.getResource("servlet_plu_servletresponse_web-fragment.xml"), "META-INF/web-fragment.xml");
        return ShrinkWrap.create(WebArchive.class, "servlet_plu_servletresponse_web.war").addAsLibraries(CommonServlets.getCommonServletsArchive()).addClasses(SetCharacterEncodingTestServlet.class, GenericCheckTestResultServlet.class).addAsLibraries(javaArchive1);
    }

    /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */
    /* Run test */
    /*
   * @testName: flushBufferTest
   * 
   * @assertion_ids: Servlet:JAVADOC:158
   * 
   * @test_Strategy: Servlet writes data in the buffer and flushes it
   */
    /*
   * @testName: getBufferSizeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:156;Servlet:JAVADOC:157
   * 
   * @test_Strategy: Servlet flushes buffer and verifies the size of the buffer
   */
    /*
   * @testName: getLocaleDefaultTest
   * 
   * @assertion_ids: Servlet:JAVADOC:164;
   * 
   * @test_Strategy: Validate that if setLocale() is not called on the response,
   * the getLocale() will return the default locale of the VM the container is
   * running in.
   */
    /*
   * @testName: getLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:166
   * 
   * @test_Strategy: Servlet set Locale and then verifies it
   *
   */
    /*
   * @testName: getOutputStreamFlushTest
   * 
   * @assertion_ids: Servlet:JAVADOC:140
   * 
   * @test_Strategy: Servlet gets an output stream, writes to it, flushes it
   * then checks that the response was committed
   */
    /*
   * @testName: getOutputStreamTest
   * 
   * @assertion_ids: Servlet:JAVADOC:141
   * 
   * @test_Strategy: Servlet gets an output stream and writes to it.
   */
    /*
   * @testName: getOutputStreamIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:143
   * 
   * @test_Strategy: Servlet tries to get an stream object after calling
   * getWriter
   */
    /*
   * @testName: getWriterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:144; Servlet:JAVADOC:151.3;
   * 
   * @test_Strategy: Client sets the content type. Servlet then gets a Writer
   * object, then sets content type and writes data. Verify that content type
   * didn't get set by servlet
   */
    /*
   * @testName: getWriterFlushTest
   * 
   * @assertion_ids: Servlet:JAVADOC:146
   * 
   * @test_Strategy: Servlet gets a Writer object, writes data then flushes,
   * then verifies response was committed
   */
    /*
   * @testName: getWriterAfterTest
   * 
   * @assertion_ids: Servlet:JAVADOC:145; Servlet:JAVADOC:151.3;
   * 
   * @test_Strategy: Client sets the content type. Servlet then gets a Writer
   * object, then sets content type and writes data. Verify that content type
   * did get set by servlet
   */
    /*
   * @testName: getWriterIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:149;
   * 
   * @test_Strategy: Servlet tries to get a Writer object after calling
   * getOutputStream
   */
    /*
   * @testName: isCommittedTest
   * 
   * @assertion_ids: Servlet:JAVADOC:161
   * 
   * @test_Strategy: Servlet checks before and after response is flushed
   *
   */
    /*
   * @testName: resetBufferTest
   * 
   * @assertion_ids: Servlet:JAVADOC:160; Servlet:SPEC:31;
   * 
   * @test_Strategy: Servlet writes data to the response, resets the buffer and
   * then writes new data
   */
    /*
   * @testName: resetTest
   * 
   * @assertion_ids: Servlet:JAVADOC:162; Servlet:SPEC:31;
   * 
   * @test_Strategy: Servlet writes data to the response, does a reset, then
   * writes new data
   */
    /*
   * @testName: resetTest1
   * 
   * @assertion_ids: Servlet:JAVADOC:162; Servlet:SPEC:31;
   * 
   * @test_Strategy: Servlet writes data to the response, set the Headers, does
   * a reset, then writes new data, set the new Header
   */
    /*
   * @testName: resetIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:163; Servlet:SPEC:31;
   * 
   * @test_Strategy: Servlet writes data, flushes the buffer then tries to do a
   * reset
   */
    /*
   * @testName: getCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:137
   * 
   * @test_Strategy: Servlet sets encoding then checks it.
   */
    /*
   * @testName: getCharacterEncodingDefaultTest
   * 
   * @assertion_ids: Servlet:JAVADOC:138
   * 
   * @test_Strategy: Servlet checks for the default encoding
   */
    /*
   * @testName: setCharacterEncodingTest
   * 
   * @assertion_ids: Servlet:JAVADOC:139; Servlet:JAVADOC:151.3;
   * 
   * @test_Strategy: Servlet set the encoding and client verifies it
   */
    /*
   * @testName: setBufferSizeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:154
   * 
   * @test_Strategy: Servlet sets the buffer size then verifies it was set
   */
    /*
   * @testName: setBufferSizeIllegalStateExceptionTest
   * 
   * @assertion_ids: Servlet:JAVADOC:155
   * 
   * @test_Strategy: Servlet writes data and flushes buffer then tries to get
   * the buffer size
   */
    /*
   * @testName: setContentLengthTest
   * 
   * @assertion_ids: Servlet:JAVADOC:150
   * 
   * @test_Strategy: Servlet sets the content length
   */
    /*
   * @testName: getContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:152; Servlet:JAVADOC:151.3;
   * Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet verifies the content type sent by the client
   */
    /*
   * @testName: getContentType1Test
   * 
   * @assertion_ids: Servlet:JAVADOC:152; Servlet:JAVADOC:151.3;
   * Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet verifies the content type set by the servlet
   */
    /*
   * @testName: getContentTypeNullTest
   * 
   * @assertion_ids: Servlet:JAVADOC:153; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet verifies the content-type when not set by
   * programmer
   */
    /*
   * @testName: getContentTypeNull1Test
   * 
   * @assertion_ids: Servlet:JAVADOC:153; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet verifies the content-type when not set by
   * programmer and only character encoding is set
   */
    /*
   * @testName: getContentTypeNull2Test
   *
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:JAVADOC:151.1;
   * Servlet:JAVADOC:153.1
   * 
   * @test_Strategy: Servlet verifies content-type is being re-set by programmer
   * and character encoding setting does not change
   */
    /*
   * @testName: setContentTypeTest
   * 
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:JAVADOC:151.3;
   * Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet sets the content type Verify Content-Type is set in
   * Client
   */
    /*
   * @testName: setContentType1Test
   * 
   * @assertion_ids: Servlet:JAVADOC:151; Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet sets the content-type, and verifies it with
   * getContentType()
   */
    /*
   * @testName: setContentType2Test
   * 
   * @assertion_ids: Servlet:JAVADOC:151.2; Servlet:JAVADOC:151.3;
   * Servlet:SPEC:34;
   * 
   * @test_Strategy: Servlet sets the content-type, Commit the response; Set the
   * content-type again Verifies that content-type is set the first time The
   * second setting is ignored.
   */
    /*
   * @testName: setLocaleTest
   * 
   * @assertion_ids: Servlet:JAVADOC:164
   * 
   * @test_Strategy: Servlet sets the Locale
   */
    /*
   * @testName: setLocale1Test
   * 
   * @assertion_ids: Servlet:JAVADOC:164; Servlet:JAVADOC:151.3;
   * 
   * @test_Strategy: Servlet sets the Locale
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
    public void getCharacterEncodingDefaultTest() throws Exception {
        super.getCharacterEncodingDefaultTest();
    }

    @Test()
    public void getCharacterEncodingTest() throws Exception {
        super.getCharacterEncodingTest();
    }

    @Test()
    public void getContentType1Test() throws Exception {
        super.getContentType1Test();
    }

    @Test()
    public void getContentTypeNull1Test() throws Exception {
        super.getContentTypeNull1Test();
    }

    @Test()
    public void getContentTypeNull2Test() throws Exception {
        super.getContentTypeNull2Test();
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
    public void getLocaleDefaultTest() throws Exception {
        super.getLocaleDefaultTest();
    }

    @Test()
    public void getLocaleTest() throws Exception {
        super.getLocaleTest();
    }

    @Test()
    public void getOutputStreamFlushTest() throws Exception {
        super.getOutputStreamFlushTest();
    }

    @Test()
    public void getOutputStreamIllegalStateExceptionTest() throws Exception {
        super.getOutputStreamIllegalStateExceptionTest();
    }

    @Test()
    public void getOutputStreamTest() throws Exception {
        super.getOutputStreamTest();
    }

    @Test()
    public void getWriterAfterTest() throws Exception {
        super.getWriterAfterTest();
    }

    @Test()
    public void getWriterFlushTest() throws Exception {
        super.getWriterFlushTest();
    }

    @Test()
    public void getWriterIllegalStateExceptionTest() throws Exception {
        super.getWriterIllegalStateExceptionTest();
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
    public void resetIllegalStateExceptionTest() throws Exception {
        super.resetIllegalStateExceptionTest();
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
    public void setBufferSizeIllegalStateExceptionTest() throws Exception {
        super.setBufferSizeIllegalStateExceptionTest();
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
    public void setContentType1Test() throws Exception {
        super.setContentType1Test();
    }

    @Test()
    public void setContentType2Test() throws Exception {
        super.setContentType2Test();
    }

    @Test()
    public void setContentTypeTest() throws Exception {
        super.setContentTypeTest();
    }

    @Test()
    public void setLocale1Test() throws Exception {
        super.setLocale1Test();
    }

    @Test()
    public void setLocaleTest() throws Exception {
        super.setLocaleTest();
    }
}
