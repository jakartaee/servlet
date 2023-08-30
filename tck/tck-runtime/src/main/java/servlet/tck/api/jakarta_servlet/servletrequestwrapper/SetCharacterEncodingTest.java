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

package servlet.tck.api.jakarta_servlet.servletrequestwrapper;

import java.io.IOException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestWrapper;
import jakarta.servlet.ServletResponse;

public class SetCharacterEncodingTest extends SetCharacterEncodingTestWrapper {

  public void init(ServletConfig servletConfig) throws ServletException {
    super.init(servletConfig);
  }

  // merely validate the default behavior of RequestWrapper -- delegates call
  // to the ServletRequest object it wraps.

  public void service(ServletRequest servletRequest,
      ServletResponse servletResponse) throws ServletException, IOException {
    ServletRequestWrapper wrapper = new ServletRequestWrapper(servletRequest);
    super.service(wrapper, servletResponse);
  }

}
