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

package servlet.tck.common.client.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import servlet.tck.common.request.Header;

import java.util.StringTokenizer;

public final class SetCookieHandler implements Handler {
  private static final Logger LOGGER = LoggerFactory.getLogger(SetCookieHandler.class);
  private static final Handler HANDLER = new SetCookieHandler();

  private static final String DELIM = "##";

  private SetCookieHandler() {
  }

  public static Handler getInstance() {
    return HANDLER;
  }

  public boolean invoke(Header configuredHeader, Header responseHeader) {
    String setCookieHeader = responseHeader.getValue().toLowerCase();
    String expectedValues = configuredHeader.getValue().toLowerCase();

    LOGGER.trace("[SetCookieHandler] Set-Cookie header received: {}", setCookieHeader);

    StringTokenizer conf = new StringTokenizer(expectedValues, DELIM);
    while (conf.hasMoreTokens()) {
      String token = conf.nextToken();
      String token1 = token;

      if (token.endsWith("\"") && (token.indexOf("=\"", 1) > -1)) {
        token1 = token.replace("=\"", "=");
        token1 = token1.substring(0, token.length() - 2);
      }

      if (token.startsWith("!")) {
        String attr = token.substring(1);
        String attr1 = token1.substring(1);
        if ((setCookieHeader.contains(attr))
            || (setCookieHeader.contains(attr1))) {
          LOGGER.trace("[SetCookieHandler] Unexpected attribute found "
              + " Set-Cookie header.  Attribute: {}"
              + "\nSet-Cookie header: {}", attr, setCookieHeader);
          return false;
        }
      } else {
        if ((!setCookieHeader.contains(token))
            && (!setCookieHeader.contains(token1))) {
          LOGGER.error("[SetCookieHandler] Unable to find '{}' within the Set-Cookie header returned by the server.", token);
          return false;
        } else {
          LOGGER.trace("[SetCookieHandler] Found expected value, '{}' in Set-Cookie header returned by server.", token);
        }
      }
    }

    return true;
  }
}
