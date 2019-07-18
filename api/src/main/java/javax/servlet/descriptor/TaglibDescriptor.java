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

package javax.servlet.descriptor;

/**
 * This interface provides access to the <code>&lt;taglib&gt;</code> related configuration of a web application.
 *
 * <p>
 * The configuration is aggregated from the <code>web.xml</code> and <code>web-fragment.xml</code> descriptor files of
 * the web application.
 *
 * @since 3.0
 */
public interface TaglibDescriptor {

    /**
     * Gets the unique identifier of the tag library represented by this TaglibDescriptor.
     * 
     * @return the unique identifier of the tag library represented by this TaglibDescriptor
     */
    public String getTaglibURI();

    /**
     * Gets the location of the tag library represented by this TaglibDescriptor.
     * 
     * @return the location of the tag library represented by this TaglibDescriptor
     */
    public String getTaglibLocation();
}
