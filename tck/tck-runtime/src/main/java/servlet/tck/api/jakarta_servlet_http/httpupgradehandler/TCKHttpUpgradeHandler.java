/*
 * Copyright (c) 2013, 2026 Oracle and/or its affiliates and others.
 * All rights reserved.
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
package servlet.tck.api.jakarta_servlet_http.httpupgradehandler;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.WebConnection;

public class TCKHttpUpgradeHandler implements HttpUpgradeHandler {

  private String delimiter = null;

  private TCKReadListener readListener;

  public TCKHttpUpgradeHandler() {
  }

  @Override
  public void init(WebConnection wc) {
    try {
      ServletInputStream input = wc.getInputStream();
      ServletOutputStream output = wc.getOutputStream();

      readListener = new TCKReadListener(delimiter, input, output);
      input.setReadListener(readListener);

      output.println("===============TCKHttpUpgradeHandler.init");
      output.flush();
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void destroy() {
    System.out.println("===============destroy");
  }

  public void setDelimiter(String delimiter) {
    System.out.println("===============setDelimiter");
    this.delimiter = delimiter;
  }
}
