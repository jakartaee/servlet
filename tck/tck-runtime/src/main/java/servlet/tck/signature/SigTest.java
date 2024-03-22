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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Properties;

import java.lang.System.Logger;

/**
 * This class should be extended by TCK developers that wish to create a set of
 * signature tests that run outside of any Java EE container. Developers must
 * implement the getPackages method to specify which packages are to be tested
 * by the signature test framework.
 */
public abstract class SigTest {

  private static final Logger logger = System.getLogger(SigTest.class.getName());

  protected SignatureTestDriver driver;

  /**
   * <p>
   * Returns a {@link SignatureTestDriver} appropriate for the particular TCK
   * (using API check or the Signature Test Framework).
   * </p>
   *
   * <p>
   * The default implementation of this method will return a
   * {@link SignatureTestDriver} that will use API Check. TCK developers can
   * override this to return the desired {@link SignatureTestDriver} for their
   * TCK.
   */
  protected SignatureTestDriver getSigTestDriver() {

    if (driver == null) {
      driver = SignatureTestDriverFactory.getInstance(SignatureTestDriverFactory.SIG_TEST);
    }

    return driver;

  } // END getSigTestDriver


  /**
   * Returns the list of packages that must be tested by the signature test
   * framework. TCK developers must implement this method in their signature
   * test sub-class.
   *
   * @return String A list of packages that the developer wishes to test using
   *         the signature test framework.
   */
  protected abstract String[] getPackages();

  /**
   * Returns an array of individual classes that must be tested by the signature
   * test framwork. TCK developers may override this method when this
   * functionality is needed. Most will only need package level granularity.
   *
   * @return an Array of Strings containing the individual classes the framework
   *         should test. The default implementation of this method returns a
   *         zero-length array.
   */
  protected String[] getClasses() {

    return new String[] {};

  } // END getClasses

  protected SigTestData testInfo;

  /**
   * Called by the test framework to initialize this test. The method simply
   * retrieves some state information that is necessary to run the test when
   * when the test framework invokes the run method (actually the test1 method).
   *
   * @param args
   *          List of arguments passed to this test.
   * @param p
   *          Properties specified by the test user and passed to this test via
   *          the test framework.
   */
  public void setup() {
    try {
      logger.log(Logger.Level.TRACE, "$$$ SigTest.setup() called");
      this.testInfo = new SigTestData();
      logger.log(Logger.Level.TRACE, "$$$ SigTest.setup() complete");
    } catch (Exception e) {
      logger.log(Logger.Level.ERROR, "Unexpected exception " + e.getMessage());
    }
  }

  /**
   * Called by the test framework to run this test. This method utilizes the
   * state information set in the setup method to run the signature tests. All
   * signature test code resides in the utility class so it can be reused by the
   * signature test framework base classes.
   *
   * @throws Exception
   *           When an error occurs executing the signature tests.
   */
  public void signatureTest(String mapFile, String packageFile, @SuppressWarnings("unused") Properties mapFileAsProps,
      String[] packages) throws Exception {

    SigTestResult results = null;
    String repositoryDir = System.getProperty("java.io.tmpdir");
    String[] classes = getClasses();
    String testClasspath = testInfo.getTestClasspath();

    // If testing with Java 9+, extract the JDK's modules so they can be used
    // on the testcase's classpath.

    String jimageDir = testInfo.getJImageDir();
    File f = new File(jimageDir);
    f.mkdirs();

    String javaHome = System.getProperty("java.home");
    logger.log(Logger.Level.INFO, "Executing JImage");

    try {
      ProcessBuilder pb = new ProcessBuilder(javaHome + "/bin/jimage", "extract", "--dir=" + jimageDir, javaHome + "/lib/modules");
      logger.log(Logger.Level.INFO, javaHome + "/bin/jimage extract --dir=" + jimageDir + " " + javaHome + "/lib/modules");
      pb.redirectErrorStream(true);
      Process proc = pb.start();
      BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
      String line = null;
      while ((line = out.readLine()) != null) {
        logger.log(Logger.Level.INFO, line);
      }

      int rc = proc.waitFor();
      logger.log(Logger.Level.INFO,"JImage RC = " + rc);
      out.close();
    } catch (Exception e) {
      logger.log(Logger.Level.INFO, "Exception while executing JImage!  Some tests may fail.");
      e.printStackTrace();
    }


    try {
      results = getSigTestDriver().executeSigTest(packageFile, mapFile,
          repositoryDir, packages, classes, testClasspath);
      logger.log(Logger.Level.INFO,results.toString());
      if (!results.passed()) {
        logger.log(Logger.Level.TRACE, "results.passed() returned false");
        throw new Exception();
      }
      logger.log(Logger.Level.TRACE, "$$$ SigTest.test1() returning");
    } catch (Exception e) {
      if (results != null && !results.passed()) {
        throw new Exception("SigTest.test1() failed!, diffs found");
      } else {
        logger.log(Logger.Level.ERROR, "Unexpected exception " + e.getMessage());
        throw new Exception("test failed with an unexpected exception", e);
      }
    }
  }

  public File writeStreamToTempFile(InputStream inputStream, String tempFilePrefix, String tempFileSuffix) throws IOException {
    FileOutputStream outputStream = null;
    try {
        File file = File.createTempFile(tempFilePrefix, tempFileSuffix);
        file.deleteOnExit();
        outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        return file;
    }
    finally {
        if (outputStream != null) {
            outputStream.close();
        }
    }
  }

  public File writeStreamToSigFile(InputStream inputStream, String apiPackage, String packageVersion) throws IOException {
    FileOutputStream outputStream = null;
    String tmpdir = System.getProperty("java.io.tmpdir");
    try {
        File sigfile = new File(tmpdir+ File.separator + apiPackage + ".sig_"+packageVersion);
        if(sigfile.exists()){
          sigfile.delete();
          logger.log(Logger.Level.INFO, "Existing signature file deleted to create new one");
        }
        if(!sigfile.createNewFile()){
          logger.log(Logger.Level.ERROR, "signature file is not created");
        }
        outputStream = new FileOutputStream(sigfile);
        byte[] buffer = new byte[1024];
        while (true) {
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            outputStream.write(buffer, 0, bytesRead);
        }
        return sigfile;
    }

    finally {
        if (outputStream != null) {
            outputStream.close();
        }
    }
  }

  /**
   * Called by the test framework to cleanup any outstanding state.
   *
   */
  public void cleanup() {
    logger.log(Logger.Level.TRACE, "$$$ SigTest.cleanup()");
  }

} // end class SigTest
