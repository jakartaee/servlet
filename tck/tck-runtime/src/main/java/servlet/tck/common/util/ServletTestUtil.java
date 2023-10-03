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

package servlet.tck.common.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A set of useful utility methods to help perform test functions.
 */
public class ServletTestUtil {

  private static Logger LOGGER = LoggerFactory.getLogger(ServletTestUtil.class);

  /**
   * Private as this class contains only public static methods.
   */
  private ServletTestUtil() {
  }

  /**
   * Compares the String values in an Enumeration against the provides String
   * array of values. The number of elements in the enumeration must be the same
   * as the size of the array, or false will be returned. False will also be
   * returned if the provided Enumeration or String array is null.
   *
   * If all values are found, true will be returned.
   *
   * <em>Note:</em> This method isn't concerned with the presence of duplicate
   * values contained in the enumeration.
   *
   * The comparison is performed in a case sensitive manner.
   * 
   * @param e
   *          - Enumeration to validate
   * @param values
   *          - the values expected to be found in the Enumeration
   *
   * @return true if all the expected values are found, otherwise false.
   */
  public static boolean checkEnumeration(Enumeration<String> e, String[] values) {
    return checkEnumeration(e, values, true, true);
  }

  /**
   * Compares the String values in an Enumeration against the provides String
   * array of values. The number of elements in the enumeration must be the same
   * as the size of the array, or false will be returned. False will also be
   * returned if the provided Enumeration or String array is null.
   *
   * If all values are found, true will be returned.
   *
   * <em>Note:</em> This method isn't concerned with the presence of duplicate
   * values contained in the enumeration.
   *
   * The comparison is performed in a case sensitive manner.
   * 
   * @param e
   *          - Enumeration to validate
   * @param values
   *          - the values expected to be found in the Enumeration
   * @param enforceSizes
   *          - ensures that the number of elements in the Enumeration matches
   *          the number of elements in the array of values
   * @param allowDuplicates
   *          - If true, the method will true if duplicate elements are found in
   *          the Enumeration, if false, then false will be return if duplicate
   *          elements have been found.
   *
   * @return true if all the expected values are found, otherwise false.
   */
  public static boolean checkEnumeration(Enumeration<?> e, String[] values,
      boolean enforceSizes, boolean allowDuplicates) {
    List<Object> foundValues = null;

    if (e == null || !e.hasMoreElements() || values == null) {
      return false;
    }

    if (!allowDuplicates) {
      foundValues = new ArrayList<>();
    }

    boolean valuesFound = true;
    Arrays.sort(values);
    int count = 0;
    while (e.hasMoreElements()) {
      Object val;
      try {
        val = e.nextElement();
        count++;
        if (!allowDuplicates) {
          if (foundValues.contains(val)) {
            LOGGER.debug("[ServletTestUtil] Duplicate values found in "
                + "Enumeration when duplicates are not allowed."
                + "Values found in the Enumeration: {}", getAsString(e));
            valuesFound = false;
            break;
          }
          foundValues.add(val);
        }

      } catch (NoSuchElementException nsee) {
        LOGGER.info("[ServletTestUtil] There were less elements in the "
            + "Enumeration than expected");
        valuesFound = false;
        break;
      }
      LOGGER.debug("[ServletTestUtil] Looking for '{}' in values: {}", val, getAsString(values));
      if ((Arrays.binarySearch(values, val) < 0) && (enforceSizes)) {
        LOGGER.info("[ServletTestUtil] Value '{}' not found.", val);
        valuesFound = false;
      }
    }

    if (enforceSizes) {
      if (e.hasMoreElements()) {
        // more elements than should have been.
        LOGGER.info("[ServletTestUtil] There were more elements in the Enumeration than expected.");
        valuesFound = false;
      }
      if (count != values.length) {
        LOGGER.info("[ServletTestUtil] There number of elements in the Enumeration did not match number of expected values."
            + "Expected number of Values= {}, Actual number of Enumeration elements= {}", values.length, count);

        valuesFound = false;
      }
    }
    return valuesFound;
  }

  public static boolean checkArrayList(ArrayList al, String[] values,
      boolean enforceSizes, boolean allowDuplicates) {
    List foundValues = null;

    if (al == null || al.isEmpty() || values == null) {
      return false;
    }

    if (!allowDuplicates) {
      foundValues = new ArrayList<>();
    }

    al.trimToSize();
    boolean valuesFound = true;
    Arrays.sort(values);
    int len = al.size();
    for (int i = 0; i < len; i++) {
      Object val = null;
      val = (String) al.get(i);
      LOGGER.debug("[ServletTestUtil] val= {}", val);
      if (!allowDuplicates) {
        if (foundValues.contains(val)) {
          LOGGER.info("[ServletTestUtil] Duplicate values found in ArrayList when duplicates are not allowed."
              + "Values found in the ArrayList: {}", getAsString(al));
          valuesFound = false;
          break;
        }
        foundValues.add(val);
      }
      LOGGER.debug("[ServletTestUtil] Looking for '{}' in values: {}", val, getAsString(values));
      if ((Arrays.binarySearch(values, val) < 0) && (enforceSizes)) {
        LOGGER.info("[ServletTestUtil] Value '{}' not found.", val);
        valuesFound = false;
        continue;
      }
    }

    if (enforceSizes) {
      if (len != values.length) {
        LOGGER.info("[ServletTestUtil] There number of elements in the ArrayList "
            + "did not match number of expected values."
            + "Expected number of Values= {}, Actual number of ArrayList elements= {}", values.length, len);

        valuesFound = false;
      }
    }
    return valuesFound;
  }

  public static boolean compareString(String expected, String actual) {
    String[] list_expected = expected.split("[|]");
    boolean found = true;
    for (int i = 0, n = list_expected.length, startIdx = 0, bodyLength = actual
        .length(); i < n; i++) {

      String search = list_expected[i];
      if (startIdx >= bodyLength) {
        startIdx = bodyLength;
      }

      int searchIdx = actual.toLowerCase().indexOf(search.toLowerCase(),
          startIdx);

      LOGGER.debug("[ServletTestUtil] Scanning response for search string: '{}' starting at index " + "location: {}",
              search , startIdx);
      if (searchIdx < 0) {
        found = false;
        StringBuffer sb = new StringBuffer(255);
        sb.append("[ServletTestUtil] Unable to find the following search string in the server's response: '")
          .append(search).append("' at index: ")
          .append(startIdx)
          .append("\n[ServletTestUtil] Server's response:\n")
          .append("-------------------------------------------\n")
          .append(actual)
          .append("\n-------------------------------------------\n");
        LOGGER.debug(sb.toString());
        break;
      }

      LOGGER.debug("[ServletTestUtil] Found search string: '{}' at index '{}' in the server's response",
              search, searchIdx);
      // the new searchIdx is the old index plus the lenght of the
      // search string.
      startIdx = searchIdx + search.length();
    }
    return found;
  }

  /**
   * Returns the provided String array in the following format:
   * <tt>[n1,n2,n...]</tt>
   * 
   * @param sArray
   *          - an array of Objects
   * @return - a String based off the values in the array
   */
  public static String getAsString(Object[] sArray) {
    return sArray == null ? null : Stream.of(sArray).map(Object::toString).collect(Collectors.joining(",","[","]"));

  }

  public static String getAsString(List<String> al) {
    return al == null ? null : al.stream().collect(Collectors.joining(",","[","]"));

  }

  /**
   * Returns the provided Enumeration as a String in the following format:
   * <tt>[n1,n2,n...]</tt>
   * 
   * @param e
   *          - an Enumeration
   * @return - a printable version of the contents of the Enumeration
   */
  public static String getAsString(Enumeration e) {
    return getAsString(getAsArray(e));
  }

  /**
   * Returnes the provides Enumeration as an Array of String Arguments.
   * 
   * @param e
   *          - an Enumeration
   * @return - the elements of the Enumeration as an array of Objects
   */
  public static Object[] getAsArray(Enumeration<Object> e) {
    List<Object> list = new ArrayList<>();
    while (e.hasMoreElements()) {
      list.add(e.nextElement());
    }
    return list.toArray(new Object[0]);
  }

  /**
   * Returnes the provided string as an Array of Strings.
   * 
   * @param value String
   * @return - the elements of the String as an array of Strings
   */
  public static String[] getAsArray(String value) {
    StringTokenizer st = new StringTokenizer(value, ",");
    String[] retValues = new String[st.countTokens()];
    for (int i = 0; st.hasMoreTokens(); i++) {
      retValues[i] = st.nextToken();
    }
    return retValues;
  }

  public static void printResult(PrintWriter pw, String s) {

    // if string is null or empty, then it passed
    if (s == null || s.equals("")) {
      pw.println(Data.PASSED);
    } else {
      pw.println(Data.FAILED + ": " + s);
    }
  }

  public static void printResult(PrintWriter pw, boolean b) {
    if (b) {
      pw.println(Data.PASSED);
    } else {
      pw.println(Data.FAILED);
    }
  }

  public static void printResult(ServletOutputStream pw, boolean b)
      throws IOException {
    if (b) {
      pw.println(Data.PASSED);
    } else {
      pw.println(Data.FAILED);
    }
  }

  public static void printFailureData(PrintWriter pw, ArrayList result,
      Object[] expected) {
    pw.println("Unable to find the expected values:\n " + "   "
        + ServletTestUtil.getAsString(expected)
        + "\nin the results returned by the test which were:\n" + "   "
        + ServletTestUtil.getAsString(result));
  }

  public static void printFailureData(PrintWriter pw, Enumeration result,
      Object[] expected) {
    pw.println("Unable to find the expected values:\n " + "   "
        + ServletTestUtil.getAsString(expected)
        + "\nin the results returned by the test which were:\n" + "   "
        + ServletTestUtil.getAsString(result));
  }

  public static int findCookie(Cookie[] cookie, String name) {
    boolean found = false;
    int i = 0;
    if (cookie != null) {
      while ((!found) && (i < cookie.length)) {
        if (cookie[i].getName().equals(name)) {
          found = true;
        } else {
          i++;
        }
      }
    } else {
      found = false;
    }
    if (found) {
      return i;
    } else {
      return -1;
    }
  }
}
