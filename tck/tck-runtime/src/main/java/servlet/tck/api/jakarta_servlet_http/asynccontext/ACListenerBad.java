/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
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

public final class ACListenerBad implements AsyncListener {

  public ACListenerBad() throws IOException {
    throw new IOException("Make sure that ACListenerBad does not instantiate");
  }

  public void onError(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onError method of ACListenerBad");
  }

  public void onStartAsync(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onStartAsync method of ACListenerBad");
  }

  public void onComplete(AsyncEvent event) throws IOException {
    // commented out as part of bug fix for:
    // https://bug.oraclecorp.com/pls/bug/webbug_edit.edit_info_top?rptno=19258007
    // event.getAsyncContext().getResponse().getWriter().println("in onComplete
    // method of ACListenerBad");
  }

  public void onTimeout(AsyncEvent event) throws IOException {
    event.getAsyncContext().getResponse().getWriter()
        .println("in onTimeout method of ACListenerBad");
  }
}
