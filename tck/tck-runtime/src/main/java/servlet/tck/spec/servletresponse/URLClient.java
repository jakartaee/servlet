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

package servlet.tck.spec.servletresponse;

import servlet.tck.common.client.AbstractUrlClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

public class URLClient extends AbstractUrlClient {


    /**
     * Deployment for the test
     */
    @Deployment(testable = false)
    public static WebArchive getTestArchive() throws Exception {
        return ShrinkWrap.create(WebArchive.class, "servlet_spec_servletresponse_web.war")
                .addClasses(HttpTestServlet.class, TestServlet.class)
                .setWebXML(URLClient.class.getResource("servlet_spec_servletresponse_web.xml"));
    }

    /* Run test */
    /*
     * @testName: testFlushBufferHttp
     *
     * @assertion_ids: Servlet:JAVADOC:603
     *
     * @test_Strategy: Servlet writes data in the buffer and flushes it; Verify
     * data is sent back to client due to the flush, not by exiting service method
     * This is done by sleeping a long time between flush and exit in servlet;
     * Then verify time gap on client side.
     */
    @Test
    public void testFlushBufferHttp() throws Exception {
        logger.trace("testFlushBufferHttp");
        URL u = new URL(
                "http://" + _hostname + ":" + _port + getContextRoot() + "/HttpTestServlet");
        logger.trace("URL: {}", u);
        try (InputStream is = u.openStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {
            String line = null;
            long time1 = 0L;
            long time2 = 0L;

            while ((line = bis.readLine()) != null) {
                logger.trace(line);
                if (line.contains("flushBufferTest for compatibility")
                        && (time1 == 0L)) {
                    Calendar cal1 = Calendar.getInstance();
                    time1 = cal1.getTimeInMillis();
                    logger.trace("Buffer flush clocked at time {}", time1);
                }

                if (line.contains("Test Failed") && (time2 == 0L)) {
                    Calendar cal2 = Calendar.getInstance();
                    time2 = cal2.getTimeInMillis();
                    logger.trace("service method exit clocked at time {}", time2);
                }
            }
            if (((time2 - time1) > 5000) && (time1 != 0L) && (time2 != 0L)) {
                logger.trace("Test passed.  There is decent time difference between two clocked time.");
            } else {
                throw new Exception( "Test failed: there is not enough time between two clocked time");
            }

        } catch (Exception ex) {
            throw new Exception("Test failed with the above exception:" + ex.getMessage(), ex);
        }
    }

    /*
     * @testName: testFlushBuffer
     *
     * @assertion_ids: Servlet:JAVADOC:153
     *
     * @test_Strategy: Servlet writes data in the buffer and flushes it; Verify
     * data is sent back to client due to the flush, not by exiting service method
     * This is done by sleeping a long time between flush and exit in servlet;
     * Then verify time gap on client side.
     */
    @Test
    public void testFlushBuffer() throws Exception {
        logger.trace("testFlushBuffer");

        URL u = new URL(
                "http://" + _hostname + ":" + _port + getContextRoot() + "/TestServlet");
        logger.trace("URL: {}}", u);

        try (InputStream is = u.openStream();
             BufferedReader bis = new BufferedReader(new InputStreamReader(is))) {

            String line = null;
            long time1 = 0L;
            long time2 = 0L;

            while ((line = bis.readLine()) != null) {
                logger.trace(line);
                if (line.contains("flushBufferTest for compatibility")
                        && (time1 == 0L)) {
                    Calendar cal1 = Calendar.getInstance();
                    time1 = cal1.getTimeInMillis();
                    logger.trace("Buffer flush clocked at time {}", time1);
                }

                if (line.contains("Test Failed") && (time2 == 0L)) {
                    Calendar cal2 = Calendar.getInstance();
                    time2 = cal2.getTimeInMillis();
                    logger.trace("service method exit clocked at time {}", time2);
                }
            }

            bis.close();

            if (((time2 - time1) > 5000) && (time1 != 0L) && (time2 != 0L)) {
                logger.trace(
                        "Test passed.  There is decent time difference between two clocked time.");
            } else {
                throw new Exception(
                        "Test failed: there is not enough time between two clocked time");
            }

        } catch (Exception ex) {
            throw new Exception("testFlushBuffer failed with the above exception:" + ex.getMessage(), ex);
        }
    }

    public void cleanup() throws Exception {
        logger.info("[BaseUrlClient] Test cleanup OK");
    }

}