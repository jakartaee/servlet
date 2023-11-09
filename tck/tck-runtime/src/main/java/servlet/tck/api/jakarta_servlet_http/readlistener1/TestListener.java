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
package servlet.tck.api.jakarta_servlet_http.readlistener1;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;

public class TestListener implements ReadListener {

  private final ServletInputStream input;

  private final ServletOutputStream output;

  private final AsyncContext ac;

  TestListener(ServletInputStream in, ServletOutputStream out, AsyncContext c) {
    input = in;
    output = out;
    ac = c;
  }

  public void onDataAvailable() {
    try {
      StringBuilder sb = new StringBuilder();
      output.println("=onDataAvailable");
      int len = -1;
      byte[] b = new byte[1024];
      while (input.isReady() && (len = input.read(b)) != -1) {
        String data = new String(b, 0, len);
        sb.append("=").append(data);
      }
      output.print(sb.toString());
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  public void onAllDataRead() {
    try {
      output.println("=onAllDataRead");
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    } finally {
      ac.complete();
    }
  }

  public void onError(final Throwable t) {
    ac.complete();
    t.printStackTrace();
  }
}
