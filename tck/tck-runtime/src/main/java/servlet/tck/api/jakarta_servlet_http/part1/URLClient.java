/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates and others.
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
package servlet.tck.api.jakarta_servlet_http.part1;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import servlet.tck.api.jakarta_servlet_http.part.TestServlet;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import servlet.tck.common.util.ServletTestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class URLClient extends AbstractUrlClient {

    private static final String CRLF = "\r\n";

    @BeforeEach
    public void setupServletName() throws Exception {
        setServletName("TestServlet");
    }

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "part1.war")
                .addAsLibraries(CommonServlets.getCommonServletsArchive())
                .addClasses(TestServletWrapper.class, TestServlet.class);
    }

    /*
     * @class.setup_props: webServerHost; webServerPort; ts_home;
     */
    /* Run test */
    /*
     * @testName: getPartTest
     *
     * @assertion_ids: Servlet:JAVADOC:767; Servlet:JAVADOC:770;
     * Servlet:JAVADOC:787; Servlet:JAVADOC:789; Servlet:JAVADOC:793;
     * Servlet:JAVADOC:794; Servlet:JAVADOC:955;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send multi-part form without file Verify that the data is
     * received correctly Verify all relevant API works correctly
     */
    @Test
    public void getPartTest() throws Exception {
        String testname = "getPartTest";
        Boolean passed = true;
        String EXPECTED_RESPONSE = "getParameter(\"xyz\"): 1234567abcdefg"
                + "|Part name: xyz|Submitted File Name: null|Size: 14|Content Type: text/plain|Header Names: content-disposition content-type"
                + "|getPart(String) test=true";

        StringBuilder test_log = new StringBuilder();


        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url = null;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }


        try (ByteArrayOutputStream ba = new ByteArrayOutputStream()) {
            // First compose the post request data

            addFile(ba, "xyz", null, "1234567abcdefg");
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type: multipart/form-data; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            test_log.append("Data sent");

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getPartTest1
     *
     * @assertion_ids: Servlet:JAVADOC:769;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send a non-multi-part form data request with a form data
     * Verify getPart(String name) throw ServletException
     */
    @Test
    public void getPartTest1() throws Exception {
        String testname = "getPartTest1";
        Boolean passed = true;
        String EXPECTED_RESPONSE = "Expected ServletException thrown";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url = null;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }

        try {
            // First compose the post request data
            ByteArrayOutputStream ba = new ByteArrayOutputStream();

            addFile(ba, "xyz", null, "1234567abcdefg");
            ba.write("\r\n--AaB03x--\r\n".getBytes());
            logger.debug("Content: {}", ba);

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type:  text/plain; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            test_log.append("Data sent");

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getPartsTest
     *
     * @assertion_ids: Servlet:JAVADOC:772;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send a non-multi-part form data request with a few form data
     * Verify getParts() throw ServletException
     */
    @Test
    public void getPartsTest() throws Exception {
        String testname = "getPartsTest";
        Boolean passed = true;
        String EXPECTED_RESPONSE = "Expected ServletException thrown";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url;
        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }


        try {
            // First compose the post request data
            ByteArrayOutputStream ba = new ByteArrayOutputStream();

            addFile(ba, "myFile", "test.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "myFile2", "test2.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "xyz", null, "1234567abcdefg");
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            logger.debug("Content: {}", ba);

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type:  text/plain; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            test_log.append("Data sent");

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getPartsTest1
     *
     * @assertion_ids: Servlet:JAVADOC:767; Servlet:JAVADOC:770;
     * Servlet:JAVADOC:787; Servlet:JAVADOC:789; Servlet:JAVADOC:793;
     * Servlet:JAVADOC:794; Servlet:JAVADOC:955;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send multi-part form with several parts, with and without file
     * Verify that the data is received correctly Verify all relevant API works
     * correctly
     */
    @Test
    public void getPartsTest1() throws Exception {
        String testname = "getPartsTest1";

        Boolean passed = true;
        String EXPECTED_RESPONSE = "getParameter(\"xyz\"): 1234567abcdefg"
                + "|Part name: myFile|Submitted File Name: test.txt|Size: 36|Content Type: text/plain|Header Names: content-disposition content-type"
                + "|Part name: myFile2|Submitted File Name: test2.txt|Size: 37|Content Type: text/plain|Header Names: content-disposition content-type"
                + "|Part name: xyz|Submitted File Name: null|Size: 14|Content Type: text/plain|Header Names: content-disposition content-type";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }


        try {
            // First compose the post request data
            ByteArrayOutputStream ba = new ByteArrayOutputStream();

            addFile(ba, "myFile", "test.txt", null);
            logger.debug("first file: {}", ba);
            ba.write("\r\n".getBytes());
            addFile(ba, "myFile2", "test2.txt", null);
            ba.write("\r\n".getBytes());
            logger.debug("second file: {}", ba);
            addFile(ba, "xyz", null, "1234567abcdefg");
            logger.debug("third: {}", ba);
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            data = ba.toByteArray();
            logger.debug("Data: {}", data);

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type: multipart/form-data; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))){

            os.write(header.toString().getBytes());
            os.write(data);
            logger.debug("Data sent: {}", data);

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getHeaderTest
     *
     * @assertion_ids: Servlet:JAVADOC:788;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send multi-part form with several parts, with and without file
     * Verify that Part.getHeader(String) works correctly
     */
    @Test
    public void getHeaderTest() throws Exception {
        String testname = "getHeaderTest";

        Boolean passed = true;
        String EXPECTED_RESPONSE = "Part name: myFile|content-disposition:|form-data;|name=\"myFile\";|filename=\"test.txt\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: null"
                + "|Part name: myFile2|content-disposition:|form-data;|name=\"myFile2\";|filename=\"test2.txt\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: null"
                + "|Part name: xyz|content-disposition:|form-data;|name=\"xyz\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: null";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }


        try (ByteArrayOutputStream ba = new ByteArrayOutputStream()){
            // First compose the post request data

            addFile(ba, "myFile", "test.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "myFile2", "test2.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "xyz", null, "1234567abcdefg");
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type: multipart/form-data; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            logger.debug("Data sent: {}", data.toString());

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log.toString());
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getHeadersTest
     *
     * @assertion_ids: Servlet:JAVADOC:790;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send multi-part form with several parts, with and without file
     * Verify that Part.getHeaders(String) works correctly
     */
    @Test
    public void getHeadersTest() throws Exception {
        String testname = "getHeadersTest";

        Boolean passed = true;
        String EXPECTED_RESPONSE = "Part name: myFile|content-disposition:|form-data;|name=\"myFile\";|filename=\"test.txt\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: 0"
                + "|Part name: myFile2|content-disposition:|form-data;|name=\"myFile2\";|filename=\"test2.txt\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: 0"
                + "|Part name: xyz|content-disposition:|form-data;|name=\"xyz\"|content-type: text/plain"
                + "|TCKDummyNameNonExistant: 0";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url = null;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }

        try (ByteArrayOutputStream ba = new ByteArrayOutputStream()) {
            // First compose the post request data

            addFile(ba, "myFile", "test.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "myFile2", "test2.txt", null);
            ba.write("\r\n".getBytes());
            addFile(ba, "xyz", null, "1234567abcdefg");
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type: multipart/form-data; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            logger.debug("Data sent: {}", data.toString());

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    /*
     * @testName: getInputStreamTest
     *
     * @assertion_ids: Servlet:JAVADOC:791;
     *
     * @test_Strategy: Create a Servlet TestServletWrapper that wraps the Request;
     * From client, send multi-part form with several parts, with and without file
     * Verify that Part.getInputStream() works correctly
     */
    @Test
    public void getInputStreamTest() throws Exception {
        String testname = "getInputStreamTest";

        Boolean passed = true;
        String EXPECTED_RESPONSE = "Parts size=1" + "|Part name: myFile"
                + "|First line." + "|Second line." + "|Last line.";

        StringBuilder test_log = new StringBuilder();

        byte[] data;
        StringBuilder header = new StringBuilder();

        String requestUrl = getContextRoot() + "/" + getServletName() + "?testname="
                + testname + " HTTP/1.1";
        URL url = null;

        try {
            url = getURL("http", _hostname, _port, requestUrl.substring(1));
            logger.debug("url: {}", url.toExternalForm());
        } catch (MalformedURLException ex) {
            passed = false;
            throw new Exception("EXception getting URL " + requestUrl + " with host "
                    + _hostname + " at port " + _port, ex);
        }


        try (ByteArrayOutputStream ba = new ByteArrayOutputStream()) {
            // First compose the post request data

            addFile(ba, "myFile", "test.txt", null);
            ba.write("\r\n--AaB03x--\r\n".getBytes());

            data = ba.toByteArray();

            // Compose the post request header
            header.append("POST ").append(url.toExternalForm().replace("http://", "")
                            .replace(_hostname, "").replace(":" + Integer.toString(_port), ""))
                    .append(CRLF);
            header.append("Host: " + _hostname + "\r\n");
            header.append("Connection: close\r\n");
            header.append("Content-Type: multipart/form-data; boundary=AaB03x\r\n");
            header.append("Content-Length: " + data.length + "\r\n\r\n");
            logger.debug("Header: {}", header);
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception creating data", ex);
        }

        try (Socket sock = new Socket(_hostname, _port);
             InputStream is = sock.getInputStream();
             OutputStream os = sock.getOutputStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            os.write(header.toString().getBytes());
            os.write(data);
            logger.debug("Data sent: {}", data.toString());

            String line = null;
            while ((line = bis.readLine()) != null) {
                test_log.append("Received: " + line + CRLF);

            }
        } catch (IOException ex) {
            passed = false;
            throw new Exception("Exception reading data", ex);
        }

        if (!ServletTestUtil.compareString(EXPECTED_RESPONSE,
                test_log.toString())) {
            passed = false;
        }

        logger.debug("test_log: {}", test_log);
        if (!passed) {
            throw new Exception("Test failed due to incorrect response");
        }
    }

    void addFile(ByteArrayOutputStream ba, String partname, String filename,
                 String content) throws IOException {
        ba.write("--AaB03x\r\n".getBytes());

        if (filename != null) {
            // Write header
            ba.write(("Content-Disposition: form-data; name=\"" + partname
                    + "\"; filename=\"" + filename + "\"\r\n").getBytes());
            ba.write("Content-Type: text/plain\r\n\r\n".getBytes());
            // Write content of the file
            URL url = servlet.tck.api.jakarta_servlet_http.part.URLClient.class.getResource(filename);
            byte[] file1Bytes = url.openStream().readAllBytes();;
            ba.write(file1Bytes, 0, file1Bytes.length);
        } else {
            // Write header
            ba.write(("Content-Disposition: form-data; name=\"" + partname + "\"\r\n")
                    .getBytes());
            ba.write("Content-Type: text/plain\r\n\r\n".getBytes());
        }

        if (content != null) {
            // Write content
            ba.write(content.getBytes());
        }
    }
}