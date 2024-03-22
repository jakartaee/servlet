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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import java.lang.System.Logger;

/**
 * Allows the sigtest framework to be extended using different signature test
 * implementations (e.g. ApiCheck, or SigTest)
 */
public abstract class SignatureTestDriver {

  private static final Logger logger = System.getLogger(SignatureTestDriver.class.getName());

  private static final String SIG_FILE_EXT = ".sig";

  private static final String SIG_FILE_VER_SEP = "_";

  // ---------------------------------------------------------- Public Methods

  /**
   * <p>
   * Execute the signature test. By default, this method passes the result of
   * {@link #createTestArguments(String, String, String, String, String)} and
   * passes the result to {@link #runSignatureTest(String, String[])}.
   *
   * @param packageListFile
   *          - file containing the packages/classes that are to be verified
   * @param mapFile
   *          sig-test.map file
   * @param signatureRepositoryDir
   *          directory containing the recorded signatures
   * @param packagesUnderTest
   *          packages, defined by the test client, that should be tested
   * @param classesUnderTest
   *          classes, defined by the test client, that should be tested
   * @param classpath
   *          The location of the API being verified. Normally the checked API
   *          will be available in the test environment and testClasspath will
   *          be null. In some rare cases the tested API may not be part of the
   *          test environment and will have to specified using this parameter.
   *
   * @return a {@link SigTestResult} containing the result of the test execution
   */
  public SigTestResult executeSigTest(String packageListFile, String mapFile,
      String signatureRepositoryDir, String[] packagesUnderTest,
      String[] classesUnderTest, String classpath)
      throws Exception {

    SigTestResult result = new SigTestResult();

    if (packagesUnderTest != null && packagesUnderTest.length > 0) {
      logger.log(Logger.Level.INFO, "********** BEGIN PACKAGE LEVEL SIGNATURE "
          + "VALIDATION **********\n\n");
      for (int i = 0; i < packagesUnderTest.length; i++) {

        String packageName = packagesUnderTest[i];

        logger.log(Logger.Level.INFO, "********** BEGIN VALIDATE PACKAGE '"
            + packagesUnderTest[i] + "' **********\n");

        logger.log(Logger.Level.INFO,
            "********** VALIDATE IN STATIC MODE - TO CHECK CONSANT VALUES ****");
        logger.log(Logger.Level.INFO, "Static mode supports checks of static constants values ");

        String[] args = createTestArguments(packageListFile, mapFile,
            signatureRepositoryDir, packageName, classpath, true);
        dumpTestArguments(args);

        if (runSignatureTest(packageName, args)) {
          logger.log(Logger.Level.INFO, "********** Package '" + packageName
              + "' - PASSED (STATIC MODE) **********");
          result.addPassedPkg(packageName + "(static mode)");
        } else {
          result.addFailedPkg(packageName + "(static mode)");
          logger.log(Logger.Level.INFO, "********** Package '" + packageName
              + "' - FAILED (STATIC MODE) **********");
        }

        logger.log(Logger.Level.INFO, "\n\n");
        logger.log(Logger.Level.INFO, "********** VALIDATE IN REFLECTIVE MODE  ****");
        logger.log(Logger.Level.INFO,
            "Reflective mode supports verification within containers (ie ejb, servlet, etc)");

        String[] args2 = createTestArguments(packageListFile, mapFile,
            signatureRepositoryDir, packageName, classpath, false);
        dumpTestArguments(args2);

        if (runSignatureTest(packageName, args2)) {
          logger.log(Logger.Level.INFO, "********** Package '" + packageName
              + "' - PASSED (REFLECTION MODE) **********");
          result.addPassedPkg(packageName + "(reflection mode)");
        } else {
          result.addFailedPkg(packageName + "(reflection mode)");
          logger.log(Logger.Level.INFO, "********** Package '" + packageName
              + "' - FAILED (REFLECTION MODE) **********");
        }

        logger.log(Logger.Level.INFO, "********** END VALIDATE PACKAGE '"
            + packagesUnderTest[i] + "' **********\n");

        logger.log(Logger.Level.INFO, "\n");
        logger.log(Logger.Level.INFO, "\n");

      }
    }

    if (classesUnderTest != null && classesUnderTest.length > 0) {
      logger.log(Logger.Level.INFO, "********** BEGIN CLASS LEVEL SIGNATURE "
          + "VALIDATION **********\n\n");

      for (int i = 0; i < classesUnderTest.length; i++) {

        String className = classesUnderTest[i];

        logger.log(Logger.Level.INFO, "********** BEGIN VALIDATE CLASS '"
            + classesUnderTest[i] + "' **********\n");

        logger.log(Logger.Level.INFO,
            "********** VALIDATE IN STATIC MODE - TO CHECK CONSANT VALUES ****");
        logger.log(Logger.Level.INFO, "Static mode supports checks of static constants values ");

        String[] args = createTestArguments(packageListFile, mapFile,
            signatureRepositoryDir, className, classpath, true);
        dumpTestArguments(args);

        if (runSignatureTest(className, args)) {
          logger.log(Logger.Level.INFO, "********** Class '" + className
              + "' - PASSED (STATIC MODE) **********");
          result.addPassedClass(className + "(static mode)");
        } else {
          logger.log(Logger.Level.INFO, "********** Class '" + className
              + "' - FAILED (STATIC MODE) **********");
          result.addFailedClass(className + "(static mode)");
        }

        logger.log(Logger.Level.INFO, "\n\n");
        logger.log(Logger.Level.INFO, "********** VALIDATE IN REFLECTIVE MODE  ****");
        logger.log(Logger.Level.INFO,
            "Reflective mode supports verification within containers (ie ejb, servlet, etc)");

        String[] args2 = createTestArguments(packageListFile, mapFile,
            signatureRepositoryDir, className, classpath, false);
        dumpTestArguments(args2);

        if (runSignatureTest(className, args2)) {
          logger.log(Logger.Level.INFO, "********** Class '" + className
              + "' - PASSED (REFLECTION MODE) **********");
          result.addPassedClass(className + "(reflection mode)");
        } else {
          logger.log(Logger.Level.INFO, "********** Class '" + className
              + "' - FAILED (REFLECTION MODE) **********");
          result.addFailedClass(className + "(reflection mode)");
        }

        logger.log(Logger.Level.INFO, "********** END VALIDATE CLASS '" + classesUnderTest[i]
            + "' **********\n");

        logger.log(Logger.Level.INFO, "\n");
        logger.log(Logger.Level.INFO, "\n");

      }
    }

    return result;

  } // END executeSigTest

  // ------------------------------------------------------- Protected Methods

  /**
   * Using a common set of information, create arguments that are appropriate to
   * be used with the underlying signature test framework.
   *
   * @param packageListFile
   *          - file containing the packages/classes that are to be verified
   * @param mapFile
   *          sig-test.map file
   * @param signatureRepositoryDir
   *          directory containing the recorded signatures
   * @param packageOrClassUnderTest
   *          the class or package
   * @param classpath
   *          The location of the API being verified. Normally the checked API
   *          will be available in the test environment and testClasspath will
   *          be null. In some rare cases the tested API may not be part of the
   *          test environment and will have to specified using this parameter.
   */
  protected abstract String[] createTestArguments(String packageListFile,
      String mapFile, String signatureRepositoryDir,
      String packageOrClassUnderTest, String classpath, boolean bStaticMode)
      throws Exception;

  /**
   * Invoke the underlying signature test framework for the specified package or
   * class.
   *
   * @param packageOrClassName
   *          the package or class to be validated
   * @param testArguments
   *          the arguments necessary to invoke the signature test framework
   *
   * @return <code>true</code> if the test passed, otherwise <code>false</code>
   */
  protected abstract boolean runSignatureTest(String packageOrClassName,
      String[] testArguments) throws Exception;


  /**
   * Loads the specified file into a Properties object provided the specified
   * file exists and is a regular file. The call to new FileInputStream verifies
   * that the specfied file is a regular file and exists.
   *
   * @param mapFile
   *          the path and name of the map file to be loaded
   *
   * @return Properties The Properties object initialized with the contents of
   *         the specified file
   *
   * @throws java.io.IOException
   *           If the specified map file does not exist or is not a regular
   *           file, can also be thrown if there is an error creating an input
   *           stream from the specified file.
   */
  public Properties loadMapFile(String mapFile)
      throws IOException, FileNotFoundException {

    FileInputStream in = null;
    try {
      File map = new File(mapFile);
      Properties props = new Properties();
      in = new FileInputStream(map);
      props.load(in);
      return props;
    } finally {
      try {
        if (in != null) {
          in.close();
        }
      } catch (Throwable t) {
        // do nothing
      }
    }

  } // END loadMapFile

  /**
   * This method will attempt to build a fully-qualified filename in the format
   * of <code>respositoryDir</code> + </code>baseName</code> +
   * <code>.sig_</code> + </code>version</code>.
   *
   * @param baseName
   *          the base portion of the signature filename
   * @param repositoryDir
   *          the directory in which the signatures are stored
   * @param version
   *          the version of the signature file
   * @throws FileNotFoundException
   *           if the file cannot be validated as existing and is in fact a file
   * @return a valid, fully qualified filename, appropriate for the system the
   *         test is being run on
   */
  protected String getSigFileName(String baseName, String repositoryDir,
      String version) throws FileNotFoundException {

    String sigFile;
    if (repositoryDir.endsWith(File.separator)) {
      sigFile = repositoryDir + baseName + SIG_FILE_EXT + SIG_FILE_VER_SEP
          + version;
    } else {
      sigFile = repositoryDir + File.separator + baseName + SIG_FILE_EXT
          + SIG_FILE_VER_SEP + version;
    }

    File testFile = new File(sigFile);

    if (!testFile.exists() && !testFile.isFile()) {
      throw new FileNotFoundException(
          "Signature file \"" + sigFile + "\" does not exist.");
    }

    // we are actually requiring this normalizeFileName call to get
    // things working on Windows. Without this, if we just return the
    // testFile; we will fail on windows. (Solaris works either way)
    // IMPORTANT UPDATE!! (4/5/2011)
    // in sigtest 2.2: they stopped supporting the normalized version which
    // created a string filename =
    // "file://com/sun/ts/tests/signaturetest/foo.sig"
    // so now use file path and name only.
    // return normalizeFileName(testFile);
    return testFile.toString();

  } // END getSigFileName


  /**
   * Returns the name and path to the signature file that contains the specified
   * package's signatures.
   *
   * @param packageName
   *          The package under test
   * @param mapFile
   *          The name of the file that maps package names to versions
   * @param repositoryDir
   *          The directory that conatisn all signature files
   *
   * @return String The path and name of the siganture file that contains the
   *         specified package's signatures
   *
   * @throws Exception
   *           if the determined signature file is not a regular file or does
   *           not exist
   */
  protected SignatureFileInfo getSigFileInfo(String packageName, String mapFile,
      String repositoryDir) throws Exception {

    String originalPackage = packageName;
    String name = null;
    String version = null;
    Properties props = loadMapFile(mapFile);

    while (true) {
      boolean packageFound = false;
      for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
        name = (String) (e.nextElement());
        if (name.equals(packageName)) {
          version = props.getProperty(name);
          packageFound = true;
          break;
        } // end if
      } // end for

      if (packageFound) {
        break;
      }

      /*
       * If we get here we did not find a package name in the properties file
       * that matches the package name under test. So we look for a package name
       * in the properties file that could be the parent package for the package
       * under test. We do this by removing the specified packages last package
       * name section. So jakarta.ejb.spi would become jakarta.ejb
       */
      int index = packageName.lastIndexOf(".");
      if (index <= 0) {
        throw new Exception("Package \"" + originalPackage
            + "\" not specified in mapping file \"" + mapFile + "\".");
      }
      packageName = packageName.substring(0, index);
    } // end while

    /* Return the expected name of the signature file */

    return new SignatureFileInfo(getSigFileName(name, repositoryDir, version),
        version);

  } // END getSigFileInfo

  // --------------------------------------------------------- Private Methods

  /**
   * Prints the specified list of parameters to the message log. Used for
   * debugging purposes only.
   *
   * @param params
   *          The list of parameters to dump.
   */
  private static void dumpTestArguments(String[] params) {

    if (params != null && params.length > 0) {
      logger.log(Logger.Level.TRACE, "----------------- BEGIN SIG PARAM DUMP -----------------");
      for (int i = 0; i < params.length; i++) {
        logger.log(Logger.Level.TRACE, "   Param[" + i + "]: " + params[i]);
      }
      logger.log(Logger.Level.TRACE, "------------------ END SIG PARAM DUMP ------------------");
    }

  } // END dumpTestArguments

  // ----------------------------------------------------------- Inner Classes

  /**
   * A simple data structure containing the fully qualified path to the
   * signature file as well as the version being tested.
   */
  protected static class SignatureFileInfo {

    private String file;

    private String version;

    // -------------------------------------------------------- Constructors

    public SignatureFileInfo(String file, String version) {

      if (file == null) {
        throw new IllegalArgumentException("'file' argument cannot be null");
      }

      if (version == null) {
        throw new IllegalArgumentException("'version' argument cannot be null");
      }

      this.file = file;
      this.version = version;

    } // END SignatureFileInfo

    // ------------------------------------------------------ Public Methods

    public String getFile() {

      return file;

    } // END getFileIncludingPath

    public String getVersion() {

      return version;

    } // END getVersion

  }

} // END SigTestDriver
