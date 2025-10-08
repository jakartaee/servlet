/*
 * Copyright (c) 2007, 2024 Oracle and/or its affiliates. All rights reserved.
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class represents a package list file. A package list file is used in
 * conjunction with a set of signature files to execute API signature tests.
 * Users specify which set of package APIs are verified. Each package's
 * signature is verified independently. As such all valid sub-packages must be
 * excluded while a package's signature is being verified. This allows API check
 * to determine incompatible additional packages included in a distribution.
 * <p/>
 * This class builds a package list file when signatures are recorded and
 * provides an API to provide valid sub-package lists when signatures are played
 * back (verified).
 * <p/>
 * In record mode, this class reads the existing package list file, if one
 * exists, and removes the package (as well as sub-packages) that are currently
 * being recorded. All package names read from the existing package list file
 * are held in a tree set which sorts the package names and keeps duplicate
 * package names from occurring. The user can then instruct this class to write
 * out the package list file. At this point this class reads the currently
 * recorded signature file and extracts each package names and adds it to the
 * tree set. After this step the previous package list file is saved as a backup
 * and the new package list file is written to disk.
 * <p/>
 * In playback mode, this class reads the contents of the package list file and
 * stores each package name in a tree set. Users can then invoke the
 * getSubPackages method to retrieve the valid sub-packages for any given
 * package. This is done by simply examining the package names in the tree set
 * and returning any package name that starts with the parent package name and a
 * trailing period character.
 */
class PackageList {

  // Any line in the packageFile starting with this character is a comment
  private static final char COMMENT_CHAR = '#';

  // File containing the list of packages and sub-packages
  private File packageFile;

  // Signature file where the package signatures were recorded
  private File sigFile;

  // Name of the package being recorded
  private String additionalPackageName;

  // Name of packages and sub-packages in the
  private Set<String> packageNames = new TreeSet<>();

  /**
   * Creates an instance of the PackageList class. The PackageList instance
   * reads the specified package file and populates it's internal state with the
   * package names found in this file. Users should use this c'tor when playing
   * back signature files. Users can init the PackageList instance then use the
   * "String[] getSubPackages(String packageName)" method to get the list of
   * valid sub-packages for every package who's signature is being verified.
   *
   * @param packageFileName
   *          The name of the file that contains the package list. This file
   *          contains the names of all the packages that exist across all the
   *          signature files that makeup this deliverable. This file is used to
   *          generate a list of valid sub-packages that must be exclued when
   *          testing theor parent package's signature.
   *
   * @throws Exception
   *           when the packageFileName does not exist.
   */
  public PackageList(String packageFileName) throws Exception {
    packageFile = new File(packageFileName);
    if (packageFile.exists() && packageFile.isFile()) {
      extractExistingPackageNames();
    } else {
      throw new FileNotFoundException(packageFileName);
    }
  }

  /**
   * Creates an instance of the PackageList class. The PackageList instance
   * reads the contents of the packageFileName and stores it in it's internal
   * state. Next, any packages whos name starts with the specified packageName
   * are removed from the internal package list. This is done because this is
   * the package being recorded and we need to remove any previously recorded
   * package names in case any sub-packages have been removed since the last
   * time the signatures were recorded. Users should use this c'tor when they
   * are recording signature files never during playback.
   *
   * @param packageName
   *          The name of the package whos signatures are being recorded (along
   *          with sub-packages).
   * @param sigFileName
   *          The name of the file that contains the recorded signatures.
   * @param packageFileName
   *          The name of the file that contains the package list. This file
   *          contains the names of all the packages that exist across all the
   *          signature files that makeup this deliverable. This file is used to
   *          generate a list of valid sub-packages that must be exclued when
   *          testing their parent package's signature.
   *
   * @throws Exception
   *           when an error occurs reading the packageFileName or the
   *           sigFileName does not exist.
   */
  public PackageList(String packageName, String sigFileName,
      String packageFileName) throws Exception {
    this.additionalPackageName = packageName;
    sigFile = new File(sigFileName);
    if (!sigFile.exists() || !sigFile.isFile()) {
      throw new FileNotFoundException(sigFileName);
    }
    packageFile = new File(packageFileName);
    if (packageFile.exists() && packageFile.isFile()) {
      extractExistingPackageNames();
      removeExistingPackage();
    }
  }

  /**
   * Read the package names stored in the package list file. Each package name
   * found in the package list file is added to the internal tree set.
   *
   * @throws Exception
   *           if there is an error opening or reading the package list file.
   */
  private void extractExistingPackageNames() throws Exception {
    BufferedReader in = new BufferedReader(new FileReader(packageFile));
    String line;
    String trimLine;
    try {
      while ((line = in.readLine()) != null) {
        trimLine = line.trim();
        if (isComment(trimLine) || "".equals(trimLine)) {
          continue;
        }
        packageNames.add(trimLine);
      }
    } finally {
      try {
        in.close();
      } catch (Exception e) {
      }
    }
  }

  /**
   * Returns true if the specified string starts with a comment character as
   * denoted by the COMMENT_CHAR constant.
   *
   * @param line
   *          Determines of this line is a comment line
   *
   * @return boolean True if the specified line is a comment line else false.
   */
  private boolean isComment(String line) {
    if (line == null) {
      return false;
    }
    String theLine = line.trim();
    if (theLine.length() > 0) {
      return (theLine.charAt(0) == COMMENT_CHAR);
    }

    return false;
  }

  /**
   * Removes package names from the package list file. The packages that are
   * removed are the ones currently being recorded. The packages being recorded
   * is denoted by this.additionalPackageName. This includes any sub-packages of
   * the additionalPackageName. This step is necessary in the cases where a
   * sub-package has been removed from a parent package in between signature
   * recordings.
   */
  private void removeExistingPackage() {
    String delPackage = this.additionalPackageName;
    String packageName;
    List<String> delPkgs = new ArrayList<>();
    // iterate over package set and find package names to remove
    for (Iterator<String> i = packageNames.iterator(); i.hasNext();) {
      packageName = i.next();
      if (packageName.startsWith(delPackage)) {
        delPkgs.add(packageName);
      }
    }
    // actually remove the package names from the set
    for (int i = 0; i < delPkgs.size(); i++) {
      packageName = delPkgs.get(i);
      packageNames.remove(packageName);
      System.out.println(
          "PackageList.removeExistingPackage() \"" + packageName + "\"");
    }
  }

  /**
   * Returns the list of sub-packages that exist in the specified package name.
   *
   * @param pkgName
   *          The name of the package we want the sub-package list for.
   *
   * @return String[] The sub-packages that live under the specified parent
   *         package.
   */
  public String[] getSubPackages(String pkgName) {
    List<String> result = new ArrayList<>();
    String subPackageName = pkgName + ".";
    for (Iterator<String> i = packageNames.iterator(); i.hasNext();) {
      String packageName = i.next();
      if (packageName.startsWith(subPackageName)) {
        result.add(packageName);
      }
    }
    return result.toArray(new String[0]);
  }

  /**
   * Returns the list of sub-packages that exist in the specified package name.
   * The returned string matches the API check format of specifying multiple
   * packages with a single string. Each package name is separated with the "+"
   * character.
   *
   * @param pkgName
   *          The name of the package we want the sub-package list for.
   *
   * @return String The sub-packages that live under the specified parent
   *         package.
   */
  public String getSubPackagesFormatted(String pkgName) {
    StringBuffer formattedResult = new StringBuffer();
    String[] result = getSubPackages(pkgName);
    for (int i = 0; i < result.length; i++) {
      formattedResult.append(result[i]);
      if (i < (result.length - 1)) {
        formattedResult.append("+");
      }
    }
    return formattedResult.toString();
  }

} // end class PackageList
