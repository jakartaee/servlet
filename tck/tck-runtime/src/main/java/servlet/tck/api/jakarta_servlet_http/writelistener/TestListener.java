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
package servlet.tck.api.jakarta_servlet_http.writelistener;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

public class TestListener implements WriteListener {

  private final ServletOutputStream output;

  private final AsyncContext ac;

  TestListener(ServletOutputStream sos, AsyncContext c) {
    output = sos;
    ac = c;
  }

  public void onWritePossible() {
    String message = "=onWritePossible";
    try {
      output.write(message.getBytes());
      ac.complete();
    } catch (Exception ex) {
      ac.complete();
      throw new IllegalStateException(ex);
    }
  }

  public void onError(final Throwable t) {
    ac.complete();
    t.printStackTrace();
  }
}
