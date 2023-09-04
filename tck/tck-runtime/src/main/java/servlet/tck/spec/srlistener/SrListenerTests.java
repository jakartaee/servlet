/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.spec.srlistener;

import servlet.tck.util.TestUtil;
import servlet.tck.common.client.AbstractUrlClient;
import servlet.tck.common.servlets.CommonServlets;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SrListenerTests extends AbstractUrlClient {

  @BeforeEach
  public void setupServletName() throws Exception {
    setServletName("TestServlet");
  }

  /**
   * Deployment for the test
   */
  @Deployment(testable = false)
  public static WebArchive getTestArchive() throws Exception {
    return ShrinkWrap.create(WebArchive.class, "servlet_spec_srlistener_web.war")
            .addAsLibraries(CommonServlets.getCommonServletsArchive())
            .addClasses(ForwardedServlet.class, IncludedServlet.class, SecondForwardedServlet.class,
                    SecondIncludedServlet.class, SRListener.class, TestServlet.class)
            .setWebXML(SrListenerTests.class.getResource("servlet_spec_srlistener_web.xml"));
  }

  /*
   * @class.setup_props: webServerHost; webServerPort; ts_home;
   *
   */

  /* Run test */

  /*
   * @testName: simpleinclude
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleinclude() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "includes");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "IncludedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }
    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: multipleincludes
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void multipleincludes() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "multipleincludes");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "SecondIncludedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: includeforward
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void includeforward() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "includeforward");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "ForwardedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: includeerror
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void includeerror() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(REQUEST, "GET " + getContextRoot()
          + "/TestServlet?testname=includeerror HTTP/1.1");
      TEST_PROPS.setProperty(STATUS_CODE, "403");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: simpleforward
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleforward() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "forward");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "ForwardedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: multipleforwards
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void multipleforwards() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "multipleforwards");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "SecondForwardedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: forwardinclude
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void forwardinclude() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "forwardinclude");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "IncludedServlet Invoked|simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: forwarderror
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void forwarderror() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(REQUEST, "GET " + getContextRoot()
          + "/TestServlet?testname=forwarderror HTTP/1.1");
      TEST_PROPS.setProperty(STATUS_CODE, "403");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: simpleasync
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleasync() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "async");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "TestServlet Invoked|method async|TestServlet_Async=STARTED");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: simpleasyncinclude
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleasyncinclude() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "simpleasyncinclude");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "TestServlet Invoked|method simpleasyncinclude"
              + "|TestServlet_Async=STARTED"
              + "|IncludedServlet Invoked||simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: simpleasyncforward
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleasyncforward() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(APITEST, "simpleasyncforward");
      TEST_PROPS.setProperty(SEARCH_STRING,
          "ForwardedServlet Invoked||simple method");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: simpleasyncerror
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void simpleasyncerror() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(REQUEST, "GET " + getContextRoot()
          + "/TestServlet?testname=simpleasyncerror HTTP/1.1");
      TEST_PROPS.setProperty(STATUS_CODE, "403");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }

  /*
   * @testName: error
   * 
   * @assertion_ids:
   * 
   * @test_Strategy:
   */
  @Test
  public void error() throws Exception {
    Boolean pass = true;
    try {
      TEST_PROPS.setProperty(REQUEST,
          "GET " + getContextRoot() + "/TestServlet?testname=error HTTP/1.1");

      TEST_PROPS.setProperty(STATUS_CODE, "403");
      invoke();
    } catch (Exception flt) {
      pass = false;
      TestUtil.logErr("Test failed at the first invocation."
          + "catch it here so the cleanup can continue", flt);
    }

    TEST_PROPS.setProperty(APITEST, "checkLogSimple");
    invoke();

    if (!pass) {
      throw new Exception("Test failed at the first invocation."
          + "catch it here so the cleanup can continue");
    }
  }
}
