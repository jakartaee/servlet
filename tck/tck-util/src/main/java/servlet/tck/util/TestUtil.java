/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package servlet.tck.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * TestUtil is a final utility class responsible for implementing logging across
 * multiple VMs. It also contains many convenience methods for logging property
 * object contents, stacktraces, and header lines.
 *
 * @author Kyle Grucci
 *
 */
public final class TestUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(TestUtil.class);

  // this can be set in TestUtil's start logging method!!
  public static String sTestName;

  private static final String PROPS_FILE_NAME = "-cts-props.txt";

  private static final String PROPS_FILE;

  static {
    String userName = System.getProperty("user.name");
    String tmpDir = System.getProperty("java.io.tmpdir",
        File.separator + "tmp");
    if (tmpDir.endsWith(File.separator)) {
      PROPS_FILE = tmpDir + userName + PROPS_FILE_NAME;
    } else {
      PROPS_FILE = tmpDir + File.separator + userName + PROPS_FILE_NAME;
    }
    System.out.println(
        "************************************************************");
    System.out.println("* props file set to \"" + PROPS_FILE + "\"");
    System.out.println(
        "************************************************************");
  }

  public static String toEncodedString(Properties args) {
    StringBuffer buf = new StringBuffer();
    Enumeration names = args.propertyNames();
    while (names.hasMoreElements()) {
      String name = (String) names.nextElement();
      String value = args.getProperty(name);
      buf.append(URLEncoder.encode(name)).append("=")
          .append(URLEncoder.encode(value));
      if (names.hasMoreElements())
        buf.append("&");
    }
    return buf.toString();
  }

}
