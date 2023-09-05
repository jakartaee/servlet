/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.common.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.httpclient.Header;

import java.util.StringTokenizer;

public class ALLOWHandler implements Handler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ALLOWHandler.class);

  private static final Handler HANDLER = new ALLOWHandler();

  private static final String DELIM = "##";

  private ALLOWHandler() {
  }

  public static Handler getInstance() {
    return HANDLER;
  }

  public boolean invoke(Header configuredHeader, Header responseHeader) {
    String ALLOWHeader = responseHeader.getValue().toLowerCase();
    String expectedValues = configuredHeader.getValue().toLowerCase()
        .replace(" ", "");

    LOGGER.trace("[ALLOWHandler] ALLOW header received: {}", ALLOWHeader);

    StringTokenizer conf = new StringTokenizer(expectedValues, ",");
    while (conf.hasMoreTokens()) {
      String token = conf.nextToken();
      String token1 = token;

      if ((!ALLOWHeader.contains(token)) && (!ALLOWHeader.contains(token1))) {
        LOGGER.error("[ALLOWHandler] Unable to find '{}' within the ALLOW header returned by the server.", token);
        return false;
      } else {
        LOGGER.trace("[ALLOWHandler] Found expected value, '{}' in ALLOW header returned by server.", token);
      }
    }
    return true;
  }
}
