/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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
package servlet.tck.api.jakarta_servlet_http.httpupgradehandler;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;

import java.util.Objects;

public class TCKReadListener implements ReadListener {

  private ServletInputStream input = null;

  private ServletOutputStream output = null;

  private String delimiter = null;

  TCKReadListener(String del, ServletInputStream in, ServletOutputStream out) {
    delimiter = Objects.requireNonNull(del,
        "Delimiter cannot be null - HttpUpgradeHandler was not properly initialized before init() was called"
    );
    input = in;
    output = out;
  }

  public String getDelimiter() {
    return delimiter;
  }

  public void onDataAvailable() {
    try {
      output.println("=onDataAvailable");
      StringBuilder sb = new StringBuilder();
      int len = -1;
      byte b[] = new byte[1024];
      while (input.isReady() && (len = input.read(b)) != -1) {
        String data = new String(b, 0, len);
        sb.append(data);
      }
      output.println(delimiter + sb.toString());
      output.flush();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  public void onAllDataRead() {
    try {
      output.println("=onAllDataRead");
      output.close();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  public void onError(final Throwable t) {
    t.printStackTrace();
  }
}
