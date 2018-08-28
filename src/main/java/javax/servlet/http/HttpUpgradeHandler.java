/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates and others.
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

package javax.servlet.http;

/**
 * This interface encapsulates the upgrade protocol processing. A HttpUpgradeHandler implementation would allow the
 * servlet container to communicate with it.
 *
 * @since Servlet 3.1
 */
public interface HttpUpgradeHandler {
    /**
     * It is called once the HTTP Upgrade process has been completed and the upgraded connection is ready to start using
     * the new protocol.
     *
     * @param wc the WebConnection object associated to this upgrade request
     */
    public void init(WebConnection wc);

    /**
     * It is called when the client is disconnected.
     */
    public void destroy();
}
