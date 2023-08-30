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

package servlet.tck.api.jakarta_servlet_http.asynccontext;

import java.io.IOException;

import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;

public final class ACListener2 implements AsyncListener {

  public ACListener2() throws IOException {
  }

  public void onError(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onError method of ACListener2");
  }

  public void onStartAsync(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onStartAsync method of ACListener2");
  }

  public void onComplete(AsyncEvent event) throws IOException {
    throw new IOException("in onComplete method of ACListener2");
  }

  public void onTimeout(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onTimeout method of ACListener2");
    event.getAsyncContext().complete();
  }
}
