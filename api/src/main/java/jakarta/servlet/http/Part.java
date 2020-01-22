/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates and others.
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

package jakarta.servlet.http;

import java.io.*;
import java.util.*;

/**
 * <p>
 * This class represents a part or form item that was received within a <code>multipart/form-data</code> POST request.
 * 
 * @since Servlet 3.0
 */
public interface Part {

    /**
     * Gets the content of this part as an <tt>InputStream</tt>
     * 
     * @return The content of this part as an <tt>InputStream</tt>
     * @throws IOException If an error occurs in retrieving the content as an <tt>InputStream</tt>
     */
    public InputStream getInputStream() throws IOException;

    /**
     * Gets the content type of this part.
     *
     * @return The content type of this part.
     */
    public String getContentType();

    /**
     * Gets the name of this part
     *
     * @return The name of this part as a <tt>String</tt>
     */
    public String getName();

    /**
     * Gets the file name specified by the client
     *
     * @return the submitted file name
     *
     * @since Servlet 3.1
     */
    public String getSubmittedFileName();

    /**
     * Returns the size of this fille.
     *
     * @return a <code>long</code> specifying the size of this part, in bytes.
     */
    public long getSize();

    /**
     * A convenience method to write this uploaded item to disk.
     * 
     * <p>
     * This method is not guaranteed to succeed if called more than once for the same part. This allows a particular
     * implementation to use, for example, file renaming, where possible, rather than copying all of the underlying
     * data, thus gaining a significant performance benefit.
     *
     * @param fileName The location into which the uploaded part should be stored. Relative paths are relative to 
     *                 {@link jakarta.servlet.MultipartConfigElement#getLocation()}. Absolute paths are used as 
     *                 provided. Note: that this is a system dependent string and URI notation may not be 
     *                 acceptable on all systems. For portability, this string should be generated with the
     *                 File or Path APIs.
     *
     * @throws IOException if an error occurs.
     */
    public void write(String fileName) throws IOException;

    /**
     * Deletes the underlying storage for a file item, including deleting any associated temporary disk file.
     *
     * @throws IOException if an error occurs.
     */
    public void delete() throws IOException;

    /**
     *
     * Returns the value of the specified mime header as a <code>String</code>. If the Part did not include a header of
     * the specified name, this method returns <code>null</code>. If there are multiple headers with the same name, this
     * method returns the first header in the part. The header name is case insensitive. You can use this method with
     * any request header.
     *
     * @param name a <code>String</code> specifying the header name
     *
     * @return a <code>String</code> containing the value of the requested header, or <code>null</code> if the part does
     *         not have a header of that name
     */
    public String getHeader(String name);

    /**
     * Gets the values of the Part header with the given name.
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>Part</code>.
     *
     * <p>
     * Part header names are case insensitive.
     *
     * @param name the header name whose values to return
     *
     * @return a (possibly empty) <code>Collection</code> of the values of the header with the given name
     */
    public Collection<String> getHeaders(String name);

    /**
     * Gets the header names of this Part.
     *
     * <p>
     * Some servlet containers do not allow servlets to access headers using this method, in which case this method
     * returns <code>null</code>
     *
     * <p>
     * Any changes to the returned <code>Collection</code> must not affect this <code>Part</code>.
     *
     * @return a (possibly empty) <code>Collection</code> of the header names of this Part
     */
    public Collection<String> getHeaderNames();

}
