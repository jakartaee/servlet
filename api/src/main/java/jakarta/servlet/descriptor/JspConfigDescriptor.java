/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates and others.
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

package jakarta.servlet.descriptor;

import java.util.Collection;

/**
 * This interface provides access to the <code>&lt;jsp-config&gt;</code> related configuration of a web application.
 *
 * <p>
 * The configuration is aggregated from the <code>web.xml</code> and <code>web-fragment.xml</code> descriptor files of
 * the web application.
 *
 * @since Servlet 3.0
 */
public interface JspConfigDescriptor {

    /**
     * Gets the <code>&lt;taglib&gt;</code> child elements of the <code>&lt;jsp-config&gt;</code> element represented by
     * this <code>JspConfigDescriptor</code>.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>JspConfigDescriptor</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the <code>&lt;taglib&gt;</code> child elements of the
     * <code>&lt;jsp-config&gt;</code> element represented by this <code>JspConfigDescriptor</code>
     */
    public Collection<TaglibDescriptor> getTaglibs();

    /**
     * Gets the <code>&lt;jsp-property-group&gt;</code> child elements of the <code>&lt;jsp-config&gt;</code> element
     * represented by this <code>JspConfigDescriptor</code>.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>JspConfigDescriptor</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the <code>&lt;jsp-property-group&gt;</code> child elements of
     * the <code>&lt;jsp-config&gt;</code> element represented by this <code>JspConfigDescriptor</code>
     */
    public Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();
}
