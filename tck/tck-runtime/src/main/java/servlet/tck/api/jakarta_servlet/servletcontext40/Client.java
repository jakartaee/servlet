/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * $Id: URLClient.java 73856 2014-06-23 22:39:25Z sdimilla $
 */

package servlet.tck.api.jakarta_servlet.servletcontext40;

import servlet.tck.util.WebUtil;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;

public class Client extends AbstractUrlClient {

    private WebUtil.Response response = null;

    private String request = null;

    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        JavaArchive javaArchive = ShrinkWrap.create(JavaArchive.class, "tldjar.jar")
                .addAsResource(Client.class.getResource("listener.tld"), "META-INF/listener.tld");
        return ShrinkWrap.create(WebArchive.class, "servlet_js_servletcontext40_web.war")
                .addAsLibraries(CommonServlets.getCommonServletsArchive())
                .addAsLibraries(javaArchive)
                .addClasses(AddListener.class, TestListener.class, TestServlet.class, TestServlet2.class)
                .addAsWebResource("api/jakarta_servlet/servletcontext40/addJspFile.jsp", "addJspFile.jsp")
                .setWebXML(Client.class.getResource("servlet_js_servletcontext40_web.xml"));
    }

    /*
     * @testName: addJspTest
     *
     * @assertion_ids: NA
     *
     * @test_Strategy:
     */
    @Test
    public void addJspTest() throws Exception {
        try {
            request = getContextRoot() + "/servlet/addJspFile";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("addJspTest failed.");
            }

            if (response.content.indexOf("addJspFile is accessed") < 0) {
                logErr("The test jsp is accessed incorrectly: " + request);
                throw new Exception("addJspTest failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("addJspTest failed: ", e);
        }
    }

    /*
     * @testName: changeSessionTimeoutTest
     *
     * @assertion_ids: servlet40:changeSessionTimeoutTest;
     *
     * @test_Strategy:
     */
    @Test
    public void changeSessionTimeoutTest() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("changeSessionTimeoutTest failed.");
            }

            if (response.content.indexOf("changeSessionTimeout_correctly") < 0) {
                throw new Exception("changeSessionTimeoutTest failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("changeSessionTimeoutTest failed: ", e);
        }
    }

    /*
     * @testName: addJspContextInitialized
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: IllegalStateException - if this ServletContext has already
     * been initialized
     */
    @Test
    public void addJspContextInitialized() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("addJspContextInitialized failed.");
            }

            if (response.content.indexOf(
                    "IllegalStateException_when_addJsp__ContextInitialized") < 0) {
                logErr(
                        "IllegalStateException should be thrown if this ServletContext has already been initialized");
                throw new Exception("addJspContextInitialized failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("addJspContextInitialized failed: ", e);
        }
    }

    /*
     * @testName: setSessionTimeoutContextInitialized
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: IllegalStateException - if this ServletContext has already
     * been initialized
     */
    @Test
    public void setSessionTimeoutContextInitialized() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("setSessionTimeoutContextInitialized failed.");
            }

            if (response.content.indexOf(
                    "IllegalStateException_when_setSessionTimeout_ContextInitialized") < 0) {
                logErr(
                        "IllegalStateException should be thrown if this ServletContext has already been initialized");
                throw new Exception("setSessionTimeoutContextInitialized failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("setSessionTimeoutContextInitialized failed: ", e);
        }
    }

    /*
     * @testName: addJspContextListenerInTLD
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: UnsupportedOperationException - if this ServletContext was
     * passed to the ServletContextListener.contextInitialized(jakarta.servlet.
     * ServletContextEvent) method of a ServletContextListener that was neither
     * declared in web.xml or web-fragment.xml, nor annotated with WebListener
     */
    @Test
    public void addJspContextListenerInTLD() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("addJspContextListenerInTLD failed.");
            }

            if (response.content.indexOf(
                    "UnsupportedOperationException_when_addJsp_addedListener") < 0) {
                logErr("UnsupportedOperationException should be thrown.");
                throw new Exception("addJspContextListenerInTLD failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("addJspContextListenerInTLD failed: ", e);
        }
    }

    /*
     * @testName: setSessionTimeoutContextListenerInTLD
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: UnsupportedOperationException - if this ServletContext was
     * passed to the ServletContextListener.contextInitialized(jakarta.servlet.
     * ServletContextEvent) method of a ServletContextListener that was neither
     * declared in web.xml or web-fragment.xml, nor annotated with WebListener
     */
    @Test
    public void setSessionTimeoutContextListenerInTLD() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("setSessionTimeoutContextListenerInTLD failed.");
            }

            if (response.content.indexOf(
                    "UnsupportedOperationException_when_setSessionTimeout_addedListener") < 0) {
                logErr("UnsupportedOperationException should be thrown.");
                throw new Exception("setSessionTimeoutContextListenerInTLD failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("setSessionTimeoutContextListenerInTLD failed: ", e);
        }
    }

    /*
     * @testName: addJspEmptyAndNullName
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: IllegalArgumentException - if servletName is null or an
     * empty String
     */
    @Test
    public void addJspEmptyAndNullName() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("addJspEmptyAndNullName failed.");
            }

            if (response.content
                    .indexOf("IllegalArgumentException_when_empty_name") < 0) {
                logErr(
                        "IllegalArgumentException should be thrown if servletName is an empty String");
                throw new Exception("addJspEmptyAndNullName failed.");
            }

            if (response.content
                    .indexOf("IllegalArgumentException_when_null_name") < 0) {
                logErr(
                        "IllegalArgumentException should be thrown if servletName is null");
                throw new Exception("addJspEmptyAndNullName failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("addJspEmptyAndNullName failed: ", e);
        }
    }

    /*
     * @testName: getAttributeWithNullName
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: NullPointerException - if name is null
     */
    @Test
    public void getAttributeWithNullName() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("getAttributeWithNullName failed.");
            }

            if (response.content
                    .indexOf("NullPointerException_when_getAttribute_with_null") < 0) {
                logErr(
                        "NullPointerException should be thrown if getAttribute with a null name.");
                throw new Exception("getAttributeWithNullName failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("getAttributeWithNullName failed: ", e);
        }
    }

    /*
     * @testName: getInitParameterWithNullName
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: NullPointerException - if name is null
     */
    @Test
    public void getInitParameterWithNullName() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("getInitParameterWithNullName failed.");
            }

            if (response.content.indexOf(
                    "NullPointerException_when_getInitParameter_with_null") < 0) {
                logErr(
                        "NullPointerException should be thrown if getInitParameter with a null name.");
                throw new Exception("getAttributeWithNullName failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("getInitParameterWithNullName failed: ", e);
        }
    }

    /*
     * @testName: setAttributeWithNullName
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: NullPointerException - if name is null
     */
    @Test
    public void setAttributeWithNullName() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("setAttributeWithNullName failed.");
            }

            if (response.content
                    .indexOf("NullPointerException_when_setAttribute_with_null") < 0) {
                logErr(
                        "NullPointerException should be thrown if setAttribute with a null name.");
                throw new Exception("setAttributeWithNullName failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("setAttributeWithNullName failed: ", e);
        }
    }

    /*
     * @testName: setInitParameterWithNullName
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: NullPointerException - if name is null
     */
    @Test
    public void setInitParameterWithNullName() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("setInitParameterWithNullName failed.");
            }

            if (response.content.indexOf(
                    "NullPointerException_when_setInitParameter_with_null") < 0) {
                logErr(
                        "NullPointerException should be thrown if setInitParameter with a null name.");
                throw new Exception("setAttributeWithNullName failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("setInitParameterWithNullName failed: ", e);
        }
    }

    /*
     * @testName: getEffectiveSessionTrackingModes
     *
     * @assertion_ids: NA
     *
     * @test_Strategy: with no setEffectiveSesssionTrackingModes, default is in
     * effective
     */
    @Test
    public void getEffectiveSessionTrackingModes() throws Exception {
        try {
            request = getContextRoot() + "/TestServlet2";
            logMsg("Sending request \"" + request + "\"");

            response = WebUtil.sendRequest("GET", InetAddress.getByName(_hostname),
                    _port, getRequest(request), null, null);

            logMsg("response.statusToken:" + response.statusToken);
            logMsg("response.content:" + response.content);

            // Check that the page was found (no error).
            if (response.isError()) {
                logErr("Could not find " + request);
                throw new Exception("getEffectiveSessionTrackingModes failed.");
            }

            if (response.content
                    .indexOf("getEffectiveSessionTrackingModes_test_passed") < 0) {
                logErr("getEffectiveSessionTrackingModes return a wrong set.");
                throw new Exception("getEffectiveSessionTrackingModes failed.");
            }

        } catch (Exception e) {
            logErr("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("getEffectiveSessionTrackingModes failed: ", e);
        }
    }
}