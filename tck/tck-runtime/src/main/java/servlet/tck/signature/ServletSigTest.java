/*
 * Copyright (c) 2007, 2024 Oracle and/or its affiliates and others.
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

package servlet.tck.signature;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;

import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.lang.System.Logger;

/*
 * This class is a simple example of a signature test that extends the
 * SigTest framework class.  This signature test is run outside of the
 * EE containers.
 */
public class ServletSigTest extends SigTest {

  private static final Logger logger = System.getLogger(ServletSigTest.class.getName());

  public ServletSigTest(){
    setup();
  }

  /***** Abstract Method Implementation *****/

  /**
   * Returns a list of strings where each string represents a package name. Each
   * package name will have it's signature tested by the signature test
   * framework.
   *
   * @return String[] The names of the packages whose signatures should be
   *         verified.
   */
  @Override
  protected String[] getPackages() {
    return new String[] {
    		"jakarta.servlet",
    		"jakarta.servlet.annotation",
    		"jakarta.servlet.descriptor",
    		"jakarta.servlet.http" };
  }


  /*
   * The following comments are specified in the base class that defines the
   * signature tests. This is done so the test finders will find the right class
   * to run. The implementation of these methods is inherited from the super
   * class which is part of the signature test framework.
   */

  // NOTE: If the API under test is not part of your testing runtime
  // environment, you may use the property sigTestClasspath to specify
  // where the API under test lives. This should almost never be used.
  // Normally the API under test should be specified in the classpath
  // of the VM running the signature tests. Use either the first
  // comment or the one below it depending on which properties your
  // signature tests need. Please do not use both comments.

  /*
   * @testName: signatureTest
   *
   * @assertion: An EL container must implement the required classes and APIs
   * specified in the EL Specification.
   *
   * @test_Strategy: Using reflection, gather the implementation specific
   * classes and APIs. Compare these results with the expected (required)
   * classes and APIs.
   *
   */
  @Test
  public void signatureTest() throws Exception {

    logger.log(Logger.Level.INFO, "$$$ SigTestIT.signatureTest() called");
    String mapFile = null;
    String packageFile = null;
    Properties mapFileAsProps = null;
    String[] packages = getPackages();
    String apiPackage = "jakarta.servlet";

    try {

    InputStream inStreamMapfile = ServletSigTest.class.getClassLoader().getResourceAsStream("servlet/tck/signature/sig-test.map");
    File mFile = writeStreamToTempFile(inStreamMapfile, "sig-test", ".map");
    mapFile = mFile.getCanonicalPath();
    logger.log(Logger.Level.INFO, "mapFile location is :"+mapFile);

    InputStream inStreamPackageFile = ServletSigTest.class.getClassLoader().getResourceAsStream("servlet/tck/signature/sig-test-pkg-list.txt");
    File pFile = writeStreamToTempFile(inStreamPackageFile, "sig-test-pkg-list", ".txt");
    packageFile = pFile.getCanonicalPath();
    logger.log(Logger.Level.INFO, "packageFile location is :"+packageFile);

    mapFileAsProps = getSigTestDriver().loadMapFile(mapFile);
    String packageVersion = mapFileAsProps.getProperty(apiPackage);
    logger.log(Logger.Level.INFO, "Package version from mapfile :"+packageVersion);

    InputStream inStreamSigFile = ServletSigTest.class.getClassLoader().getResourceAsStream("servlet/tck/signature/jakarta.servlet.sig_"+packageVersion);
    File sigFile = writeStreamToSigFile(inStreamSigFile, apiPackage, packageVersion);
    logger.log(Logger.Level.INFO, "signature File location is :"+sigFile.getCanonicalPath());

    } catch(IOException ex) {
        logger.log(Logger.Level.ERROR, "Exception while creating temp files :"+ex);
    }

    super.signatureTest(mapFile, packageFile, mapFileAsProps, packages);
  }


  /*
   * Call the parent class's cleanup method.
   */

  /*
   * define which sig driver we will use
   */
  @Override
  protected SignatureTestDriver getSigTestDriver() {
    driver = SignatureTestDriverFactory
        .getInstance(SignatureTestDriverFactory.SIG_TEST);

    return driver;

  } // END getSigTestDriver
}
